package org.openarchitectureware.var.featureaccess;

import java.util.HashMap;
import java.util.Map;

public class FeatureAccessFactory {

	private static Map<String, String> feature = new HashMap<String, String>();
	private static Map<String, String> config = new HashMap<String, String>();

	private static String PV_FEATURE_MODEL = ".xfm.xmi";
	private static String PV_CONFIGURATION_MODEL = ".vdm.xmi";
	private static String FMP_MODEL = ".fmp";
	private static String TEXT_MODEL = ".txt";

	private static String[] FEATURE_MODELS = { PV_FEATURE_MODEL, FMP_MODEL,
			TEXT_MODEL };
	private static String[] CONF_MODELS = { PV_CONFIGURATION_MODEL, FMP_MODEL,
			TEXT_MODEL };

	static {

		feature
				.put(PV_FEATURE_MODEL,
						"org.openarchitectureware.var.featureaccess.pv.PVFeatureModelWrapper");
		feature
				.put(FMP_MODEL,
						"org.openarchitectureware.var.featureaccess.fmp.FMPFeatureModelWrapper");
		feature
				.put(".ftxt",
						"org.openarchitectureware.var.featureaccess.dummy.DummyFeatureModelWrapper");
		feature
				.put(TEXT_MODEL,
						"org.openarchitectureware.var.featureaccess.text.TextFeatureModelWrapper");

		config
				.put(PV_CONFIGURATION_MODEL,
						"org.openarchitectureware.var.featureaccess.pv.PVConfigurationModelWrapper");
		config
				.put(
						".ctxt",
						"org.openarchitectureware.var.featureaccess.dummy.DummyConfigurationModelWrapper");
		config
				.put(FMP_MODEL,
						"org.openarchitectureware.var.featureaccess.fmp.FMPConfigurationModelWrapper");
		config
				.put(TEXT_MODEL,
						"org.openarchitectureware.var.featureaccess.text.TextConfigurationModelWrapper");
	}

	public static boolean isSupportedConigurationModel(String fileName) {
		return isSupportedModel(fileName, CONF_MODELS);
	}

	private static boolean isSupportedModel(String fileName, String[] fileExts) {
		boolean supported = false;
		if (fileName != null && fileName.length() > 0) {
			for (String fileExt : fileExts) {
				if (fileName.endsWith(fileExt)) {
					supported = true;
					break;
				}
			}
		}
		return supported;
	}

	public static FeatureModelWrapper getFeatureModelWrapper(String filename) {
		for (String f : feature.keySet()) {
			if (filename.endsWith(f)) {
				try {
					return (FeatureModelWrapper) Class.forName(feature.get(f))
							.newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static ConfigurationModelWrapper getConfigurationModelWrapper(
			String filename) {
		for (String f : config.keySet()) {
			if (filename.endsWith(f)) {
				try {
					return (ConfigurationModelWrapper) Class.forName(
							config.get(f)).newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
