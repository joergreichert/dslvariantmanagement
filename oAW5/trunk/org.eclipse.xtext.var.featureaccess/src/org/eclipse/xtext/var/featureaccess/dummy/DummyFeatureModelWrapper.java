package org.eclipse.xtext.var.featureaccess.dummy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


import org.eclipse.core.resources.IResource;
import org.eclipse.xtext.var.featureaccess.FeatureModelWrapper;
import org.eclipse.xtext.var.featureaccess.FileCreator;

public class DummyFeatureModelWrapper extends FeatureModelWrapper {
	
	private List<String> featureNames = null;
	private String resourceUri;
	
	@Override
	public void loadFeatureData(String filenameOrUri) {
		resourceUri = filenameOrUri;
		featureNames = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader( new InputStreamReader( FileCreator.getFileContents(filenameOrUri) ) );
			while ( br.ready() ) {
				featureNames.add( br.readLine() );
			}
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public List<String> findAllFeatureNames() {
		return featureNames;
	}
	
	@Override
	public boolean isValidFeatureDataFile(IResource resource) {
		return true;
	}
	
	@Override
	public String getCurrentResourceUri() {
		return resourceUri;
	}
	

}
