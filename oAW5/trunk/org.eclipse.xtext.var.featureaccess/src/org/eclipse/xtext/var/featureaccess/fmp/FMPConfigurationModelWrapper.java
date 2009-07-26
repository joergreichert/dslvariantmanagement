package org.eclipse.xtext.var.featureaccess.fmp;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.xtext.var.featureaccess.ConfigurationModelWrapper;

import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.Project;

public class FMPConfigurationModelWrapper extends ConfigurationModelWrapper {

	public List<String> findSelectedFeatureNames() {
		List<String> selectedFeatureNames = new ArrayList<String>();
		if (configuration instanceof Project) {
			Project project = (Project) configuration;
			Feature root = project.getModel();
			selectedFeatureNames = FMPModelIterator
					.getSelectedFeatureNames(root);

		}
		return selectedFeatureNames;
	}

}
