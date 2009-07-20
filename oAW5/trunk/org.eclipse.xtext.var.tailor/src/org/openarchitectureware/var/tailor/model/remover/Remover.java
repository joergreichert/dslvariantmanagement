package org.openarchitectureware.var.tailor.model.remover;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.var.featureaccess.ConfigurationModelWrapper;
import org.eclipse.xtext.var.featureaccess.ElementRemovalHelper;
import org.eclipse.xtext.var.featureaccess.ext.FeatureSupport;
import org.eclipse.xtend.util.stdlib.DynamicEcoreHelper;
import org.openarchitectureware.var.tailor.model.GrammarConstants;


public class Remover {

	public void remove( EObject architectureModel, ConfigurationModelWrapper fm ) {
		DynamicEcoreHelper h = new DynamicEcoreHelper(architectureModel.eClass().getEPackage());
		List<String> selectedFeatureNames = fm.findSelectedFeatureNames();
		Map<EObject, EObject> allFeatureClauses = findAllFeatureClausesAndTheirOwners(architectureModel);
		for (Iterator iterator  = allFeatureClauses.keySet().iterator(); iterator.hasNext();) {
			EObject featureClause = (EObject)iterator.next();
			//don't remove retained features
			if( (Boolean) h.get( featureClause , "retained" ) ) continue;
			//remove owner of FeatureClause, if it evaluates to false
			if ( ! FeatureSupport.isFeatureClauseTrue( featureClause ) )
			{
				EObject owner = (EObject)allFeatureClauses.get(featureClause);
				ElementRemovalHelper.removeElementFromBase( owner , architectureModel );	
			}
		}
	}
	
	private boolean evaluateExpr(EObject exp, List<String> selectedFeatureNames){
    boolean evalResult;
    if ( exp.eClass().getName().equals(GrammarConstants.OREXPRESSION_CLASSNAME) ){
      EList<EObject> andExprList =  exp.eContents();
      evalResult = false;
      for (EObject andOperand : andExprList) {
        evalResult |= evaluateExpr(andOperand, selectedFeatureNames);
      }
      return evalResult;
    }
    if ( exp.eClass().getName().equals(GrammarConstants.ANDEXPRESSION_CLASSNAME) ){
      EList<EObject> operandList = exp.eContents();
      evalResult = true;
      for (EObject operand : operandList) {
        evalResult &= evaluateExpr(operand, selectedFeatureNames);
      }
      return evalResult;      
    }
    if ( exp.eClass().getName().equals(GrammarConstants.OPERANDEXPRESSION_CLASSNAME) ){
      evalResult = evaluateExpr(exp.eContents().get(0), selectedFeatureNames);
      
      Boolean isNot = (Boolean) exp.eGet( exp.eClass().getEStructuralFeature("isNot") );
      if( isNot ) return !evalResult; 
      else return evalResult;      
    }
    if ( exp.eClass().getName().equals(GrammarConstants.ATOMEXPRESSION_CLASSNAME) ){
      if( exp.eContents().size() > 0 ){ // node has or-expression, and is therefore not really atomic
        evalResult = evaluateExpr(exp.eContents().get(0), selectedFeatureNames);
      } else { // node is really atomic
        String featureName = (String) exp.eGet( exp.eClass().getEStructuralFeature("feature") );
        evalResult = selectedFeatureNames.contains(featureName);
      }
      return evalResult;
    }
    throw new InvalidParameterException("no adequate rule found for EObject '" 
      +exp.eClass().getName()+ "'");
	}
	

	private Map<EObject, EObject> findAllFeatureClausesAndTheirOwners(EObject architectureModel) {
		DynamicEcoreHelper h = new DynamicEcoreHelper(architectureModel.eClass().getEPackage());
		Map<EObject, EObject> allFeatureClauses = new HashMap<EObject, EObject>();
		loadModel( architectureModel, allFeatureClauses );
		return allFeatureClauses;
	}

	private void loadModel(EObject model, Map<EObject, EObject> result) {
		for (Iterator iterator = EcoreUtil.getAllContents(model, true); iterator.hasNext();) {
			EObject o = (EObject) iterator.next();			

			if (o.eClass().getName().equals(GrammarConstants.FEATUREANDLIST_CLASSNAME)
					|| o.eClass().getName().equals(GrammarConstants.FEATUREORLIST_CLASSNAME)
					|| o.eClass().getName().equals(GrammarConstants.FEATURECLAUSE_CLASSNAME)
					|| o.eClass().getName().equals(GrammarConstants.FEATUREEXPRESSION_CLASSNAME)){
				result.put( o, o.eContainer() );
			}

			if ( o.eClass().getName().equals(GrammarConstants.MODELIMPORT_CLASSNAME_SUFFIX)) {
				String importedUriString = o.eGet( o.eClass().getEStructuralFeature(GrammarConstants.MODELIMPORT_URIPROPERTY) ).toString();
				
			  try{
				  URI modelURI = model.eResource().getURI();
				  if(modelURI.isPlatform()){
  				  String modelFolderUri = modelURI.toPlatformString(true)
  				    .replaceFirst(model.eResource().getURI().lastSegment(), "");
  				  URI importedUri = URI.createURI( encodeUriString(importedUriString, modelFolderUri)  ); 
  				  List<EObject> theNextRoots = loadModelUri(importedUri, o);
  				  // TODO cycles must be catched!
  				  for (EObject r : theNextRoots) {
  	          loadModel(r, result);
  	        }
				  } else throw new Exception("wrong uri type");
			  } catch (Exception e) {
          e.printStackTrace();
        }
			}
		}
	}	
	
private String encodeUriString(String uri, String folderUri) {
  //"platform:/resource/test/model/components/../deliverable/topo.adsl"
  if(uri.contains("./")) // relative URI (also applies to ../)
    return "platform:/resource" + folderUri + uri;
  else return uri; // absolute URI
}

  private List<EObject> loadModelUri(URI uri, EObject contextElement) {
    Resource contextResource = contextElement.eResource();
    if (contextResource == null) {
      throw new IllegalArgumentException(
          "Context EObject must be in a Resource");
    }
    XtextResourceSet resourceSet = (XtextResourceSet) contextElement.eResource().getResourceSet(); 
    
    Resource r = resourceSet.createResource(uri);
    HashMap map = new HashMap<String,EObject>();
    try {
      r.load(map);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return r != null 
     ? r.getContents() 
     :  Collections.<EObject> emptyList();
	}

}
