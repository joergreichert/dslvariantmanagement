package org.openarchitectureware.var.featureaccess.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.config.GlobalConfigurationHolder;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.lib.AbstractWorkflowComponent2;
import org.eclipse.emf.mwe.core.monitor.ProgressMonitor;
import org.eclipse.emf.mwe.core.resources.ResourceLoader;
import org.eclipse.emf.mwe.core.resources.ResourceLoaderFactory;

public class TextConfigurationReader extends AbstractWorkflowComponent2 {

	private static final String COMPONENT_NAME = "Text Configuration Reader";

	public TextConfigurationReader() {
	}

	private String configFile;

	/**
	 * Sets the configuration file.
	 * 
	 * @param file
	 *            name of file
	 */
	public void setConfigFile(String file) {
		this.configFile = file;
	}

	@Override
	protected void checkConfigurationInternal(Issues issues) {
		if (configFile == null) {
			issues.addError(this, "no textFile given.");
		}
		ResourceLoader l = ResourceLoaderFactory.createResourceLoader();
		BufferedReader r = new BufferedReader(new InputStreamReader(l.getResourceAsStream(configFile)));
		List<String> lines = new ArrayList<String>();
		try {
			while (r.ready()) {
				lines.add(r.readLine());
			}
		}
		catch (IOException e) {
			issues.addError(this, "cannot read config file " + configFile + ": " + e.getMessage(), e);
			return;
		}

		List<String> allFeatures = new ArrayList<String>();
		List<String> selFeatures = new ArrayList<String>();
		for (String string : lines) {
			if (string.startsWith("+")) {
				allFeatures.add(string.substring(1).trim());
				selFeatures.add(string.substring(1).trim());
			}
			else if (string.startsWith("-")) {
				allFeatures.add(string.substring(1).trim());
			}
			else {
				issues.addWarning(this, "config file contains an illegal line (" + string
						+ "); it does not start with a + or -");
			}
		}

		TextFeatureConfigurationModel m = new TextFeatureConfigurationModel(selFeatures, allFeatures);
		GlobalConfigurationHolder.setConfigurationModel(m, issues);

	}

	@Override
	protected void invokeInternal(WorkflowContext ctx, ProgressMonitor monitor, Issues issues) {
		// do nothing, intentionally; work is done during the configuratio
		// check!
	}

	/**
	 * @see org.openarchitectureware.workflow.lib.AbstractWorkflowComponent#getLogMessage()
	 */
	@Override
	public String getLogMessage() {
		return "loading global configuration model (text) from " + configFile;
	}

	/**
	 * @see org.openarchitectureware.workflow.WorkflowComponent#getComponentName()
	 */
	public String getComponentName() {
		return COMPONENT_NAME;
	}
}
