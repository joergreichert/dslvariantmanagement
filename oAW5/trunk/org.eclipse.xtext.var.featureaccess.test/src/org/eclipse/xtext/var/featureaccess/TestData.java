package org.eclipse.xtext.var.featureaccess;

public interface TestData {
	String pvFeatureModelURI = "model/org.openarchitectureware.demo.fm.xfm.xmi";
	String pvConfigurationModelURI = "model/org.openarchitectureware.demo.fm.vdm.xmi";

	String fmpModelURI = "model/example.fmp";

	String[] expectedFeatureNames = new String[] {
			"CollaborationSystemFeatures", "AccessManagement", "Security",
			"Authentication", "Encryption", "AsynchronousConferencing",
			"SynchronousConferencing", "DocumentRepository", "Forum",
			"MicroBlogging", "Text", "Audio", "Video", "Data",
			"TelephoneIntegration", "VoiceOverIP", "VNC", "X11" };

	String[] expectedSelectedFeatureNames = new String[] {
			// the configuration instance name (in FMP not selected (has also a
			// different name)
			"Variant: orgopenarchitecturewaredemofmVariant",
			// the feature root (in FMP not selected)
			"CollaborationSystemFeatures", "AccessManagement", "Security",
			"Authentication", "Encryption", "AsynchronousConferencing",
			"Forum", "SynchronousConferencing", "Video", "Data", "X11", "Text",
			"Audio", "VoiceOverIP", "DocumentRepository" };
}
