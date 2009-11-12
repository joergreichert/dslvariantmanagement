package org.openarchitectureware.var.featureaccess;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.mwe.core.WorkflowInterruptedException;
import org.eclipse.emf.mwe.core.config.ConfigurationModel;

public abstract class ConfigurationModelWrapper implements ConfigurationModel {

	private Object configuration;

	public abstract void loadConfigurationData(String filenameOrUri);

	public void setConfigurationData(Object data) {
		configuration = data;
	}

	public Object getConfigurationData() {
		return configuration;
	}

	public abstract List<String> findSelectedFeatureNames();

	protected EObject getModelRoot(ResourceSet rs, String modelURI) {
		URI xmiURI = URI.createFileURI(modelURI);
		try {
			Resource xmiResource = rs.getResource(xmiURI, true);
			return xmiResource.getContents().get(0);
		} catch (Exception exception) {
			throw new WorkflowInterruptedException("Error when parsing '"
					+ modelURI + "': " + exception.getMessage());
		}
	}
}
