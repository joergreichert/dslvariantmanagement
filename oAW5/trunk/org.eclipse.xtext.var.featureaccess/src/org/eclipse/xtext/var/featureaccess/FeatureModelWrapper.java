package org.eclipse.xtext.var.featureaccess;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

public abstract class FeatureModelWrapper {

	public abstract void loadFeatureData( String filenameOrUri );
	
	public abstract List<String> findAllFeatureNames();
	
	public abstract boolean isValidFeatureDataFile( IResource resource );

	public abstract String getCurrentResourceUri();
	
	public boolean isLoaded() {
		return !(findAllFeatureNames() == null);
	}

	public boolean isFeatureDefined(String feature) {
		List<String> allFeatures = findAllFeatureNames();
		if ( allFeatures == null ) return false;
		return allFeatures.contains(feature);
	}
	
}
