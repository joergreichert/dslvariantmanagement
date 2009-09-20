package org.openarchitectureware.var.featureaccess.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.mwe.core.WorkflowInterruptedException;
import org.eclipse.emf.mwe.core.resources.ResourceLoader;
import org.eclipse.emf.mwe.core.resources.ResourceLoaderFactory;
import org.openarchitectureware.var.featureaccess.FeatureModelWrapper;

public class TextFeatureModelWrapper extends FeatureModelWrapper {

	private List<String> featureNames = null;
	private String resourceUri;

	@Override
	public void loadFeatureData(String filenameOrUri) {
		resourceUri = filenameOrUri;
		ResourceLoader l = ResourceLoaderFactory.createResourceLoader();
		BufferedReader r = new BufferedReader(new InputStreamReader(l
				.getResourceAsStream(filenameOrUri)));
		List<String> lines = new ArrayList<String>();
		try {
			String line;
			while (r.ready()) {
				line = r.readLine().trim();
				if(line.startsWith("+") || line.startsWith("-")) {
					line = line.substring(1);
					lines.add(line);
				}
			}
			r.close();
		} catch (IOException e) {
			throw new WorkflowInterruptedException("cannot read config file "
					+ filenameOrUri + ": " + e.getMessage());
		}
		featureNames = lines;
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
