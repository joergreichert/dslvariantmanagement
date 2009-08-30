package org.openarchitectureware.var.tailor.model.weaver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.openarchitectureware.util.stdlib.CloningExtensions;
import org.openarchitectureware.util.stdlib.DynamicEcoreHelper;
import org.openarchitectureware.var.featureaccess.ElementRemovalHelper;
import org.openarchitectureware.var.tailor.model.GrammarConstants;
import org.openarchitectureware.xtext.registry.CachingModelLoad;

public class Weaver {
	
	private DynamicEcoreHelper h;

	public void weave( EObject architectureModel, String aspectModelUri ) {
		ResourceSet rs = architectureModel.eResource().getResourceSet();
		EObject aspectModel = null;
		if( ! aspectModelUri.equals("NOT_SET") ){
			aspectModel =  rs.getResource(URI.createURI(aspectModelUri), true)
			 .getContents().get(0);
		} 
		
		h = new DynamicEcoreHelper(architectureModel.eClass().getEPackage());
	
		List<EObject> pointcuts = findAllPointcuts(architectureModel);
		if(aspectModel != null)
			pointcuts.addAll(findAllPointcuts(aspectModel));
		
		List<EObject> aspectElements = extractContainers( pointcuts );
	
		for ( int i=pointcuts.size()-1; i>=0; i-- ) {
			EObject pointcut  = (EObject) pointcuts.get(i);
			EObject aspectElement  = (EObject) aspectElements.get(i);
			List<EObject> targets = resolveTargets( architectureModel, pointcut, aspectElement, aspectElements );
			for (EObject target : targets) {
				List<EStructuralFeature> structFeatures = aspectElement.eClass().getEAllStructuralFeatures();
				for (EStructuralFeature structuralFeature : structFeatures) {
					Object featureContents = aspectElement.eGet(structuralFeature);
					if ( featureContents == null ) continue;
					if ( featureContents instanceof Collection ) {
						cloneAndTransferCollection(target, structuralFeature, (List<EObject>)featureContents);
					} if (featureContents instanceof EObject) {
						cloneAndTranferEObject(target, structuralFeature, (EObject)featureContents);
					} else {
						cloneAndTransferAnythingElse(target, structuralFeature, featureContents);
					}
				}
			}
			ElementRemovalHelper.removeElementFromBase(aspectElement, architectureModel);
		}
	}

	private void cloneAndTransferAnythingElse(EObject target, EStructuralFeature structuralFeature, Object featureContents) {
		if ( target.eGet(structuralFeature) == null ) target.eSet(structuralFeature, featureContents);
	}

	private void cloneAndTranferEObject(EObject target, EStructuralFeature structuralFeature, EObject featureContents) {
		EObject cloned = (EObject)CloningExtensions.clone(featureContents);
		target.eSet(structuralFeature, cloned);
	}

	private void cloneAndTransferCollection(EObject target, EStructuralFeature structuralFeature, List<EObject> featureContents) {
		List<EObject> oldContents = (List<EObject>)target.eGet(structuralFeature);
		for (EObject listElement : featureContents) {
			EObject cloned = (EObject)CloningExtensions.clone((EObject)listElement);
			oldContents.add(cloned);
		}
	}

	private List<EObject> extractContainers(List<EObject> pointcuts) {
		List<EObject> res = new ArrayList<EObject>();
		for (EObject p : pointcuts) {
			res.add(p.eContainer() );
		}
		return res;
	}

	private List<EObject> resolveTargets(EObject architectureModel, EObject pointcut, EObject aspectElement, List<EObject> aspectElements) {
		List<EObject> result = new ArrayList<EObject>();
		loadModel(architectureModel, result);
		return resolveTargets(result, pointcut, aspectElement, aspectElements);
	}

	private void loadModel(EObject model, List<EObject> result) {
		for (Iterator iterator = EcoreUtil.getAllContents(model, true); iterator.hasNext();) {
			EObject o = (EObject) iterator.next();
			result.add( o );
			if ( o.eClass().getName().endsWith(GrammarConstants.MODELIMPORT_CLASSNAME_SUFFIX)) {
				String importedUri = o.eGet( o.eClass().getEStructuralFeature(GrammarConstants.MODELIMPORT_URIPROPERTY) ).toString();
				if( ! importedUri.endsWith(".ftxt")){
					List<EObject> theNextRoots = CachingModelLoad.load(importedUri, model, true);
					for (EObject r : theNextRoots) {
						loadModel(r, result);
					}
				}
			}
		}
	}

	public List<EObject> resolveTargets(Collection<EObject> allElements, EObject pointcut, EObject aspectElement, List<EObject> aspectElements) {
		h = new DynamicEcoreHelper(aspectElement.eClass().getEPackage());
		List<EObject> targets = new ArrayList<EObject>();
		List<EObject> candidates = findInstancesOf(allElements, aspectElement.eClass().getName());
		candidates.remove(aspectElement);
		boolean aspectElementsAlreadyRemovedFromCandidates = false;
		if ( aspectElements != null ) {
			candidates.removeAll(aspectElements);
			aspectElementsAlreadyRemovedFromCandidates = true;
		}
		Collection<EObject> matches = (Collection<EObject>)h.get(pointcut, GrammarConstants.MATCH_MATCHPROPERTY);
		for (EObject candidate : candidates) {
			if ( !aspectElementsAlreadyRemovedFromCandidates ) {
				if ( isAspectElement( candidate )) continue;
			}
			String name = h.getName(candidate);
			for (Iterator iterator = matches.iterator(); iterator.hasNext();) {
				EObject match = (EObject) iterator.next();
				boolean keep = false;
				String matchClassName = match.eClass().getName();
				if ( matchClassName.equals(GrammarConstants.MATCH_ALL) ) {
					keep = true;
				}
				if ( matchClassName.equals(GrammarConstants.MATCH_EXACTNAME) ) {
					String matchName = h.getName(match);
					if ( name.equals(matchName)) keep = true;
				}
				if ( matchClassName.equals(GrammarConstants.MATCH_STARTSWITHNAME) ) {
					String matchName = h.getName(match);
					if ( name.startsWith(matchName)) keep = true;
				}
				if ( matchClassName.equals(GrammarConstants.MATCH_ENDSWITHNAME) ) {
					String matchName = h.getName(match);
					if ( name.endsWith(matchName)) keep = true;
				}
				if ( matchClassName.equals(GrammarConstants.MATCH_TAG) ) {
					String expectedTagName = h.getName(match);
					EObject tagClause = h.eget(candidate, GrammarConstants.TAGCLAUSE_TAGPROPERTY);
					if ( tagClause != null ) {
						List<EObject> candidateTags = (List<EObject>)h.get(tagClause, GrammarConstants.TAGMATCH_TAGSPROPERTY);
						for (EObject candTag : candidateTags) {
							String candTagName = h.getName(candTag);
							if ( candTagName.equals(expectedTagName)) keep = true;
						}
					}
				}
				if ( keep ) targets.add(candidate);
			}
		}
		return targets;
	}

	private boolean isAspectElement(EObject candidate) {
		List contents = candidate.eContents();
		for (Object object : contents) {
			if ( object instanceof EObject ) {
				EObject eo = (EObject)object;
				if ( eo.eClass().getName().equals(GrammarConstants.POINTCUT_CLASSNAME)) return true;
			}
		}
		return false;
	}

	private List<EObject> findAllPointcuts(EObject model) {
		return model != null 
		? findInstancesOf(model, GrammarConstants.POINTCUT_CLASSNAME): null;
	}
	
	private List<EObject> findInstancesOf(EObject architectureModel, String className) {
		List<EObject> as = new ArrayList<EObject>();
		for (Iterator iterator = EcoreUtil.getAllContents(architectureModel, true); iterator.hasNext();) {
			EObject object = (EObject) iterator.next();
			if ( object.eClass().getName().equals(className) ) {
				as.add( object );
			}
		}
		return as;
	}	
	
	private List<EObject> findInstancesOf(Collection<EObject> all, String className) {
		List<EObject> as = new ArrayList<EObject>();
		for (Iterator iterator = all.iterator(); iterator.hasNext();) {
			EObject object = (EObject) iterator.next();
			if ( object.eClass().getName().equals(className) ) {
				as.add( object );
			}
		}
		return as;
	}	
	
	
	  /**
	   * @param element
	   * @return the roots of each model in element's resource set  
	   */
	  public static List<EObject> allRootElements( EObject element ) {
	    ResourceSet rs = element.eResource().getResourceSet();
	    List<EObject> roots = new ArrayList<EObject>();
	    List<Resource> resList = rs.getResources();
	    for (Resource r : resList) {
			roots.add(r.getContents().get(0));
		}
	    return roots;
	  }
	  
	  
	 
	

}
