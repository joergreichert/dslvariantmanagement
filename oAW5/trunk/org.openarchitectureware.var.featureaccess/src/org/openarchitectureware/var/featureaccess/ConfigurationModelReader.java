package org.openarchitectureware.var.featureaccess;

import org.eclipse.emf.mwe.core.WorkflowContext;
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
	
	@Override
	protected void invokeInternal(WorkflowContext ctx, ProgressMonitor monitor,
			Issues issues) {
		ConfigurationModelWrapper wrapper = FeatureAccessFactory.getConfigurationModelWrapper(configurationModelFile);
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
