package org.openarchitectureware.var.featureaccess;

import java.io.File;

import org.eclipse.emf.mwe.core.ConfigurationException;
import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.WorkflowInterruptedException;
import org.eclipse.emf.mwe.core.config.GlobalConfigurationHolder;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.lib.AbstractWorkflowComponent2;
import org.eclipse.emf.mwe.core.monitor.ProgressMonitor;
import org.openarchitectureware.var.featureaccess.ext.FeatureSupport;

public class ConfigurationModelReader extends AbstractWorkflowComponent2 {

	private static final String COMPONENT_NAME = "Configuration Model Reader";
	private String configurationModelFile;
	private String featureModelFile;

	public ConfigurationModelReader() {
	}

	protected void checkConfigurationInternal(Issues issues) {
		if (configurationModelFile == null) {
			throw new ConfigurationException(
					"No configuration model file is set.");
		}
		if (configurationModelFile.trim().length() == 0) {
			throw new ConfigurationException("The given path to the "
					+ "configuration model file is an empty string.");
		}
		File file = new File(configurationModelFile);
		if (!file.exists()) {
			throw new ConfigurationException(
					"The given configuration model path points to a non existing file.");
		}
		boolean supportedConfigurationModel = FeatureAccessFactory
				.isSupportedConigurationModel(configurationModelFile);
		if (!supportedConfigurationModel) {
			throw new ConfigurationException(
					"The provided configuration model is not supported.");
		}
	}

	@Override
	protected void invokeInternal(WorkflowContext ctx, ProgressMonitor monitor,
			Issues issues) {
		ConfigurationModelWrapper wrapper = FeatureAccessFactory
				.getConfigurationModelWrapper(configurationModelFile);
		if (wrapper == null) {
			throw new WorkflowInterruptedException(configurationModelFile
					+ " is a not supported configuration model.");
		}
		wrapper.loadConfigurationData(configurationModelFile);
		GlobalConfigurationHolder.setConfigurationModel(wrapper, issues);
		FeatureSupport.setConfigurationModelWrapper(wrapper);
	}

	public void setConfigurationModelFile(String configurationModelFile) {
		this.configurationModelFile = configurationModelFile;
	}

	public String getConfigurationModelFile() {
		return configurationModelFile;
	}

	public void setFeatureModelFile(String featureModelFile) {
		this.featureModelFile = featureModelFile;
	}

	public String getFeatureModelFile() {
		return featureModelFile;
	}

	public String getComponentName() {
		return COMPONENT_NAME;
	}
}
