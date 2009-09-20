package org.openarchitectureware.var.featureaccess.text;

import java.util.List;

import org.eclipse.emf.mwe.core.config.AttributeNotFound;
import org.eclipse.emf.mwe.core.config.ConfigurationModel;
import org.eclipse.emf.mwe.core.config.FeatureNotFound;

public class TextFeatureConfigurationModel implements ConfigurationModel {
	
	private List<String> allFeatures;
	private List<String> selectedFeatures;

	public TextFeatureConfigurationModel( List<String> selectedFeatures, List<String> allFeatures ) {
		this.selectedFeatures = selectedFeatures;
		this.allFeatures = allFeatures;
	}
	
	public boolean featureExists(String featureName) {
		return allFeatures.contains(featureName);
	}
	
	public Object getAttributeValue(String featureName, String attributeName)
			throws FeatureNotFound, AttributeNotFound {
		throw new UnsupportedOperationException("the operation getAttributeValue is not implemented");
	}
	public boolean isFeatureSelected(String query) throws FeatureNotFound {
		if ( isExpression(query)) {
			throw new UnsupportedOperationException("expressions are not implemented");
		} else {
			return selectedFeatures.contains(query);
		}
	}
	
	private boolean isExpression(String query) {
		return query.endsWith("()");
	}

	public Object getFeature( String query ) {
		return query;
	}
	

	
}
