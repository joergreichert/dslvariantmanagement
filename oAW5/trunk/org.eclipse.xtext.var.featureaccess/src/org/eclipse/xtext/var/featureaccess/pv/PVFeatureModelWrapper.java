package org.eclipse.xtext.var.featureaccess.pv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xtext.var.featureaccess.FeatureModelWrapper;
import org.eclipse.xtext.var.featureaccess.FileCreator;

public class PVFeatureModelWrapper extends FeatureModelWrapper {
	
	private List<String> featureNames = null;
	private String resourceUri;
	
	@Override
	public void loadFeatureData(String filenameOrUri) {
		resourceUri = filenameOrUri;
		featureNames = new ArrayList<String>();
		ResourceSet rs = new ResourceSetImpl();
		String fullPath = FileCreator.getAccessiblePath( filenameOrUri );
		Resource r = rs.getResource(URI.createURI(fullPath), true);
		if ( !r.isLoaded() ) {
			try {
				r.load(Collections.EMPTY_MAP);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		EObject root = r.getContents().get(0);
		for (Iterator iterator = root.eAllContents(); iterator.hasNext();) {
			EObject o = (EObject) iterator.next();
			if ( o.eClass().getName().equals("Element")) {
				String name = (String)o.eGet(o.eClass().getEStructuralFeature("name"));
				String type = (String)o.eGet(o.eClass().getEStructuralFeature("type"));
				if ( type.equals("ps:feature")) featureNames.add( name );
			}
		}
	}
	
	@Override
	public String getCurrentResourceUri() {
		return resourceUri;
	}
	
	@Override
	public List<String> findAllFeatureNames() {
		return featureNames;
	}
	
	@Override
	public boolean isValidFeatureDataFile(IResource resource) {
		return true;
	}
	

}
