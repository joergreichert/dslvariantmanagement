package org.openarchitectureware.var.featureaccess;

import java.util.List;

public abstract class ConfigurationModelWrapper {

	protected Object configuration;

	public void setConfigurationData( Object data ) {
		configuration = data;
	}
	
	public abstract List<String> findSelectedFeatureNames();
	
}
