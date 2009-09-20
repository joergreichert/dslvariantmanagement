package org.openarchitectureware.var.featureaccess.emf;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.WorkflowInterruptedException;
import org.eclipse.emf.mwe.core.config.GlobalConfigurationHolder;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.monitor.ProgressMonitor;
import org.eclipse.emf.mwe.utils.AbstractEMFWorkflowComponent;

public class EMFConfigurationReader extends AbstractEMFWorkflowComponent {

	private static final String COMPONENT_NAME = "EMF Configuration Reader";

	public EMFConfigurationReader() {
	}

	private String expressionsFile;

	public void setExpressions(String efn) {
		this.expressionsFile = efn;
	}

	@Override
	public void checkConfiguration(Issues issues) {
		if (expressionsFile != null) {
			ExpressionWrapper w = new ExpressionWrapper(expressionsFile, null);
			if (!w.fileExists()) {
				issues.addError(this, "expression file " + expressionsFile + " not found.");
				return;
			}
		}
		try {
			EList contents = loadResource();
			EMFFeatureConfigurationModel m = new EMFFeatureConfigurationModel((EObject) contents.get(0),
					expressionsFile);
			GlobalConfigurationHolder.setConfigurationModel(m, issues);
		}
		catch (Throwable t) {
			issues.addError(this, "cannot load configuration from " + uri);
		}
	}

	protected EList loadResource() {
		Resource res = resourceSet.getResource(URI.createURI(uri), true);
		if (res == null)
			throw new WorkflowInterruptedException("Couldn't find resource under " + uri);
		try {
			if (!res.isLoaded()) {
				res.load(Collections.EMPTY_MAP);
			}
		}
		catch (IOException e) {
			throw new WorkflowInterruptedException("Couldn't find resource under " + uri + " : " + e.getMessage());
		}
		EList result = res.getContents();
		return result;
	}

	public void invokeInternal(WorkflowContext ctx, ProgressMonitor monitor, Issues issues) {
		// do nothing, intentionally; work is done during the configuration
		// check!
	}

	@Override
	public String getLogMessage() {
		return "loading global configuration model from " + uri;
	}

	public String getComponentName() {
		return COMPONENT_NAME;
	}
}
