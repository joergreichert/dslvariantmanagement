package org.openarchitectureware.var.featureaccess.fmp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.openarchitectureware.var.featureaccess.FeatureModelWrapper;
import org.openarchitectureware.var.featureaccess.FileCreator;

public class FMPFeatureModelWrapper extends FeatureModelWrapper {

	private List<String> featureNames = null;
	private String resourceUri;

	@Override
	public void loadFeatureData(String filenameOrUri) {
		resourceUri = filenameOrUri;
		featureNames = new ArrayList<String>();
		ResourceSet rs = new ResourceSetImpl();
		String fullPath = FileCreator.getAccessiblePath(filenameOrUri);
		Resource r = rs.getResource(URI.createURI(fullPath), true);
		if (!r.isLoaded()) {
			try {
				r.load(Collections.EMPTY_MAP);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		EObject root = r.getContents().get(0);
		featureNames = FMPModelIterator.getAllExistingFeatureNames(root);
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
