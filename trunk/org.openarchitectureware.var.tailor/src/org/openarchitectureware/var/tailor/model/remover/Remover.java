package org.openarchitectureware.var.tailor.model.remover;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.openarchitectureware.util.stdlib.DynamicEcoreHelper;
import org.openarchitectureware.var.featureaccess.ConfigurationModelWrapper;
import org.openarchitectureware.var.featureaccess.ElementRemovalHelper;
import org.openarchitectureware.var.featureaccess.ext.FeatureSupport;
import org.openarchitectureware.var.tailor.model.GrammarConstants;
import org.openarchitectureware.xtext.registry.CachingModelLoad;


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

			if ( o.eClass().getName().endsWith(GrammarConstants.MODELIMPORT_CLASSNAME_SUFFIX)) {
				String importedUri = o.eGet( o.eClass().getEStructuralFeature(GrammarConstants.MODELIMPORT_URIPROPERTY) ).toString();
				if( ! importedUri.endsWith(".ftxt") ){
					List<EObject> theNextRoots = CachingModelLoad.load(importedUri, model, true);
					for (EObject r : theNextRoots) {
						loadModel(r, result);
					}
				}
			}
		}
	}	

}
