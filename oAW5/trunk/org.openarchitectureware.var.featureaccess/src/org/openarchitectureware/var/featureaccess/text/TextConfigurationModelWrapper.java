package org.openarchitectureware.var.featureaccess.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.mwe.core.WorkflowInterruptedException;
import org.eclipse.emf.mwe.core.config.AttributeNotFound;
import org.eclipse.emf.mwe.core.config.FeatureNotFound;
import org.eclipse.emf.mwe.core.resources.ResourceLoader;
import org.eclipse.emf.mwe.core.resources.ResourceLoaderFactory;
import org.openarchitectureware.var.featureaccess.ConfigurationModelWrapper;

public class TextConfigurationModelWrapper extends ConfigurationModelWrapper {

	public TextConfigurationModelWrapper() {

	}

	@Override
	public void loadConfigurationData(String configFile) {
		ResourceLoader l = ResourceLoaderFactory.createResourceLoader();
		BufferedReader r = new BufferedReader(new InputStreamReader(l
				.getResourceAsStream(configFile)));
		List<String> lines = new ArrayList<String>();
		try {
			while (r.ready()) {
				lines.add(r.readLine());
			}
		} catch (IOException e) {
			throw new WorkflowInterruptedException("cannot read config file "
					+ configFile + ": " + e.getMessage());
		}
		if (lines.size() == 0) {
			throw new WorkflowInterruptedException(
					"The given text configuration model is invalid, "
							+ "as no entries could be found "
							+ "in the model file.");
		}
		setConfigurationData(lines);
	}

	@Override
	public List<String> findSelectedFeatureNames() {
		return TextModelIterator
				.getAllSelectedFeatureNames(getTextConfigurationData());
	}

	public boolean featureExists(String featureName) {
		return TextModelIterator.getAllExistingFeatureNames(
				getTextConfigurationData()).contains(featureName);
	}

	public Object getAttributeValue(String featureName, String attributeName)
			throws FeatureNotFound, AttributeNotFound {
		throw new UnsupportedOperationException(
				"the operation getAttributeValue is not implemented");
	}

	public Object getFeature(String featureName) {
		return (TextModelIterator
				.getAllExistingFeatureNames(getTextConfigurationData())
				.contains(featureName)) ? featureName : null;
	}

	public boolean isFeatureSelected(String featureName) throws FeatureNotFound {
		if (isExpression(featureName)) {
			throw new UnsupportedOperationException(
					"expressions are not implemented");
		} else {
			return TextModelIterator.getAllSelectedFeatureNames(
					getTextConfigurationData()).contains(featureName);
		}
	}

	private boolean isExpression(String query) {
		return query.endsWith("()");
	}

	@SuppressWarnings("unchecked")
	private List<String> getTextConfigurationData() {
		return (List<String>) super.getConfigurationData();
	}
}
