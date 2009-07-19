package org.eclipse.xtext.var.featureaccess.dummy;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.xtext.var.featureaccess.ConfigurationModelWrapper;


public class DummyConfigurationModelWrapper extends ConfigurationModelWrapper {
	
	@Override
	public List<String> findSelectedFeatureNames() {
		List<String> l = (List<String>)configuration;
		return l;
	}
	
}
