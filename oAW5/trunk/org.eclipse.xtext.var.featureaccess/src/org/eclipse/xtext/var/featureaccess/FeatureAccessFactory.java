package org.eclipse.xtext.var.featureaccess;

import java.util.HashMap;
import java.util.Map;

public class FeatureAccessFactory {

	private static Map<String, String> feature = new HashMap<String, String>();
	private static Map<String, String> config = new HashMap<String, String>();
	
	static {
		
		feature.put( ".xfm.xmi", "org.eclipse.xtext.var.featureaccess.pv.PVFeatureModelWrapper" );
		feature.put( ".ftxt", "org.eclipse.xtext.var.featureaccess.dummy.DummyFeatureModelWrapper" );

		config.put( ".vfm.xmi", "org.eclipse.xtext.var.featureaccess.pv.PVConfigurationModelWrapper" );
		config.put( ".ctxt", "org.eclipse.xtext.var.featureaccess.dummy.DummyConfigurationModelWrapper" );
	}
	
	
	public static FeatureModelWrapper getFeatureModelWrapper( String filename ) {
		for (String f: feature.keySet()) {
			if ( filename.endsWith(f)) {
				try {
					return (FeatureModelWrapper)Class.forName(feature.get(f)).newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public static ConfigurationModelWrapper getConfigurationModelWrapper( String filename ) {
		for (String f: feature.keySet()) {
			if ( filename.endsWith(f)) {
				try {
					return (ConfigurationModelWrapper)Class.forName(feature.get(f)).newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	
	
}
