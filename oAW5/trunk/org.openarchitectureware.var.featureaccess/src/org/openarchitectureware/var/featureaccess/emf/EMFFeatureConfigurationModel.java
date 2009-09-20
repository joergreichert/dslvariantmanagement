package org.openarchitectureware.var.featureaccess.emf;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.mwe.core.config.AttributeNotFound;
import org.eclipse.emf.mwe.core.config.ConfigurationModel;
import org.eclipse.emf.mwe.core.config.FeatureNotFound;
import org.eclipse.emf.mwe.core.container.ConditionEvaluationException;
import org.eclipse.xtend.util.stdlib.DynamicEcoreHelper;

public class EMFFeatureConfigurationModel implements ConfigurationModel {

	private EObject configModel = null;
	private Map<String, EObject> featureByQFN = new HashMap<String, EObject>();
	private DynamicEcoreHelper h = null;
	private ExpressionWrapper expressions = null;
	
	public EMFFeatureConfigurationModel( EObject model, String expressionsFile ) {
		this.configModel = model;
		h = new DynamicEcoreHelper(model.eClass().getEPackage());
		prepareList(configModel);
		if ( expressionsFile != null ) {
			expressions = new ExpressionWrapper( expressionsFile, configModel );
		}
	}
	
	public boolean featureExists(String featureName) {
		return featureByQFN.containsKey(featureName);
	}
	
	public Object getAttributeValue(String featureName, String attributeName)
			throws FeatureNotFound, AttributeNotFound {
		throw new UnsupportedOperationException("the operation getAttributeValue is not implemented");
	}
	public boolean isFeatureSelected(String query) throws FeatureNotFound {
		if ( isExpression(query)) {
			if ( expressions == null ) throw new ConditionEvaluationException( "no expression file specified." );
			Object res = expressions.evaluate( query.substring(0, query.length()-2) );
			if ( res instanceof Boolean ) {
				return ((Boolean)res).booleanValue();
			} else {
				throw new ConditionEvaluationException( "expression "+query+" does not return a boolean result!" );
			}
		} else {
			return getFeature(query) != null;
		}
	}
	
	private boolean isExpression(String query) {
		return query.endsWith("()");
	}

	public Object getFeature( String query ) {
		return featureByQFN.get(query);
	}
	
	private void prepareList(EObject model) {
		for (Iterator iter = model.eAllContents(); iter.hasNext();) {
			EObject element = (EObject) iter.next();
			if ( h.isInstance(element, "Feature")) {
				featureByQFN.put( calcName(element) , element);
			}
		}
	}

	private String calcName(EObject element) {
		StringBuffer b = new StringBuffer();
		b.append(h.getName(element));
		EObject current = element;
		while ( current.eContainer() != null ) {
			current = current.eContainer();
			if ( h.getName(current) != null ) {
				b.insert(0, h.getName(current)+".");
			}
		}
		return b.toString();
	}
	
	@Override
	public boolean equals(Object other) {
		if ( other instanceof EMFFeatureConfigurationModel ) {
			EMFFeatureConfigurationModel om = (EMFFeatureConfigurationModel)other;
			String thisUri = getConfigModel().eClass().eResource().getURI().toString();
			String otherUri = om.getConfigModel().eClass().eResource().getURI().toString();
			return thisUri.equals(otherUri);
		} else return super.equals(other);
	}

	public EObject getConfigModel() {
		return configModel;
	}

	
}
