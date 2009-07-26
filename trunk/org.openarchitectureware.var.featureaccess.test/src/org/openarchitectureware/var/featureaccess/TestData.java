package org.openarchitectureware.var.featureaccess;

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
			
			"CollaborationSystemFeatures", "AccessManagement", "Security",
			"Authentication", "Encryption", "AsynchronousConferencing",
			"Forum", "SynchronousConferencing", "Video", "Data", "X11", "Text",
			"Audio", "VoiceOverIP", "DocumentRepository" };
}
