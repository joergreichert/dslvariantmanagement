package org.openarchitectureware.var.featureaccess.dummy;

import java.util.List;

import org.eclipse.emf.mwe.core.config.AttributeNotFound;
import org.eclipse.emf.mwe.core.config.FeatureNotFound;
import org.openarchitectureware.var.featureaccess.ConfigurationModelWrapper;

public class DummyConfigurationModelWrapper extends ConfigurationModelWrapper {

	@Override
	public List<String> findSelectedFeatureNames() {
		List<String> l = (List<String>) getConfigurationData();
		return l;
	}

	public boolean featureExists(String featureName) {
		return ((List<String>) getConfigurationData()).contains(featureName);
	}

	public Object getAttributeValue(String featureName, String attributeName)
			throws FeatureNotFound, AttributeNotFound {
		if (!((List<String>) getConfigurationData()).contains(featureName)) {
			throw new FeatureNotFound("feature " + featureName + " not found.");
		}
		throw new AttributeNotFound("attribute " + attributeName
				+ " not found.");
	}

	public Object getFeature(String featureName) {
		return ((List<String>) getConfigurationData())
				.get(((List<String>) getConfigurationData()).indexOf(featureName));
	}

	public boolean isFeatureSelected(String featureName) throws FeatureNotFound {
		return ((List<String>) getConfigurationData()).contains(featureName);
	}

	@Override
	public void loadConfigurationData(String filenameOrUri) {
		// TODO Auto-generated method stub
		
	}
}
