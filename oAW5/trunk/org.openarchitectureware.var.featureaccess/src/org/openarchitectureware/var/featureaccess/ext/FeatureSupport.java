package org.openarchitectureware.var.featureaccess.ext;

import java.security.InvalidParameterException;
import java.util.List;

import org.eclipse.xtend.util.stdlib.DynamicEcoreHelper;
import org.eclipse.emf.mwe.core.WorkflowInterruptedException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.openarchitectureware.var.featureaccess.ConfigurationModelWrapper;
import org.openarchitectureware.var.featureaccess.ElementRemovalHelper;
import org.openarchitectureware.var.featureaccess.FeatureAccessFactory;
import org.openarchitectureware.var.featureaccess.FeatureModelWrapper;

public class FeatureSupport {

	private static final String PLATFORM_RESOURCE = "platform:/resource/";
	private static FeatureModelWrapper featureModelWrapper;
	private static ConfigurationModelWrapper configModelWrapper;
	

	public static List getAllFeatures(String featureModelUri) {
		if ( featureModelUri.toLowerCase().startsWith(PLATFORM_RESOURCE)) {
			featureModelUri = featureModelUri.substring(PLATFORM_RESOURCE.length());
		}
		if ( featureModelWrapper == null ) {
			featureModelWrapper = FeatureAccessFactory.getFeatureModelWrapper(featureModelUri);
			featureModelWrapper.loadFeatureData(featureModelUri);
		} 
		if ( !featureModelWrapper.getCurrentResourceUri().equals(featureModelUri) ) {
			featureModelWrapper.loadFeatureData(featureModelUri);
		}
		return featureModelWrapper.findAllFeatureNames();
	}

	public static void setConfigurationModelWrapper( ConfigurationModelWrapper w ) {
		configModelWrapper = w;
	}
	
	public static boolean isFeatureSelected( String featureName ) {
		if ( configModelWrapper == null ) throw new WorkflowInterruptedException("no configModelWrapper defined!");
		return configModelWrapper.findSelectedFeatureNames().contains(featureName);
	}
	
	public static boolean isFeatureClauseTrue(EObject featureClause){
		DynamicEcoreHelper dynEHelper  = new DynamicEcoreHelper(featureClause);
		//single feature in feature-clause
		if ( featureClause.eClass().getName().equals(GrammarConstants.FEATURECLAUSE_CLASSNAME) )
		{	
			String featureName = dynEHelper.sget( featureClause , GrammarConstants.FEATURECLAUSE_FEATUREPROPERTY );
			return isFeatureSelected(featureName);
		}

		//and-list in feature clause
		if ( featureClause.eClass().getName().equals(GrammarConstants.FEATUREANDLIST_CLASSNAME) ){
			EStructuralFeature structFeat = featureClause.eClass().getEStructuralFeature("featureList");
			List<String> andList = (List<String>) featureClause.eGet(structFeat);
			//check if all features in list are selected
			boolean allSelected = true;
			for (String item : andList) {
				allSelected &= isFeatureSelected(item); 	
			}
			return allSelected;
		}
		//or-list in feature clause
		if( featureClause.eClass().getName().equals(GrammarConstants.FEATUREORLIST_CLASSNAME) ){
			EStructuralFeature structFeat = featureClause.eClass().getEStructuralFeature("featureList");
			List<String> orList = (List<String>) featureClause.eGet(structFeat);
			boolean oneSelected = false;
			//check if one feature in or-list is selected
			for (String featureName : orList) {
				if( isFeatureSelected(featureName) )
					return true;
			}
			return false;			  
		}
		//feature expression 
		if ( featureClause.eClass().getName().equals(GrammarConstants.FEATUREEXPRESSION_CLASSNAME) ){
		    EStructuralFeature structFeat = featureClause.eClass().getEStructuralFeature("expression");
		
		    //evaluate expression
		    return evaluateExpr((EObject) featureClause.eGet(structFeat));
		}	
		throw new InvalidParameterException("no adequate evaluation rule found for EObject '" 
			      +featureClause.eClass().getName()+ "'");
	}
	
	private static boolean evaluateExpr(EObject exp){
	    boolean evalResult;
	    if ( exp.eClass().getName().equals(GrammarConstants.OREXPRESSION_CLASSNAME) ){
	      EList<EObject> andExprList =  exp.eContents();
	      evalResult = false;
	      for (EObject andOperand : andExprList) {
	        evalResult |= evaluateExpr(andOperand);
	      }
	      return evalResult;
	    }
	    if ( exp.eClass().getName().equals(GrammarConstants.ANDEXPRESSION_CLASSNAME) ){
	      EList<EObject> operandList = exp.eContents();
	      evalResult = true;
	      for (EObject operand : operandList) {
	        evalResult &= evaluateExpr(operand);
	      }
	      return evalResult;      
	    }
	    if ( exp.eClass().getName().equals(GrammarConstants.OPERANDEXPRESSION_CLASSNAME) ){
	      evalResult = evaluateExpr(exp.eContents().get(0));
	      
	      Boolean isNot = (Boolean) exp.eGet( exp.eClass().getEStructuralFeature("isNot") );
	      if( isNot ) return !evalResult; 
	      else return evalResult;      
	    }
	    if ( exp.eClass().getName().equals(GrammarConstants.ATOMEXPRESSION_CLASSNAME) ){
	      if( exp.eContents().size() > 0 ){ // node has or-expression, and is therefore not really atomic
	        evalResult = evaluateExpr(exp.eContents().get(0));
	      } else { // node is really atomic
	        String featureName = (String) exp.eGet( exp.eClass().getEStructuralFeature("feature") );
	        evalResult = isFeatureSelected(featureName);
	      }
	      return evalResult;
	    }
	    throw new InvalidParameterException("no adequate rule found for EObject '" 
	      +exp.eClass().getName()+ "'");
	}
		
}
