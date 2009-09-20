package org.openarchitectureware.var.featureaccess;

import java.util.HashMap;
import java.util.Map;

public class FeatureAccessFactory {

	private static Map<String, String> feature = new HashMap<String, String>();
	private static Map<String, String> config = new HashMap<String, String>();
	
	static {
		
		feature.put( ".xfm.xmi", "org.openarchitectureware.var.featureaccess.pv.PVFeatureModelWrapper" );
		feature.put( ".fmp", "org.openarchitectureware.var.featureaccess.fmp.FMPFeatureModelWrapper" );
		feature.put( ".ftxt", "org.openarchitectureware.var.featureaccess.dummy.DummyFeatureModelWrapper" );
		feature.put( ".txt", "org.openarchitectureware.var.featureaccess.text.TextFeatureModelWrapper" );

		config.put( ".vdm.xmi", "org.openarchitectureware.var.featureaccess.pv.PVConfigurationModelWrapper" );
		config.put( ".ctxt", "org.openarchitectureware.var.featureaccess.dummy.DummyConfigurationModelWrapper" );
		config.put( ".fmp", "org.openarchitectureware.var.featureaccess.fmp.FMPConfigurationModelWrapper" );
		config.put( ".txt", "org.openarchitectureware.var.featureaccess.text.TextConfigurationModelWrapper" );
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
		for (String f: config.keySet()) {
			if ( filename.endsWith(f)) {
				try {
					return (ConfigurationModelWrapper)Class.forName(config.get(f)).newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	
	
}
