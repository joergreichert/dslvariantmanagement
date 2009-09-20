package org.openarchitectureware.var.featureaccess.fmp;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.mwe.core.config.AttributeNotFound;
import org.eclipse.emf.mwe.core.config.FeatureNotFound;
import org.openarchitectureware.var.featureaccess.ConfigurationModelWrapper;

import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.FmpPackage;
import ca.uwaterloo.gp.fmp.Project;

public class FMPConfigurationModelWrapper extends ConfigurationModelWrapper {

	private Feature root = null;

	public void setConfigurationData(Object data) {
		if (data instanceof Project) {
			Project project = (Project) data;
			super.setConfigurationData(data);
			root = project.getModel();
			if (root == null) {
				throw new RuntimeException(
						"Given FMP configuration model is invalid.");
			}
		}
	}

	public List<String> findSelectedFeatureNames() {
		List<String> selectedFeatureNames = new ArrayList<String>();
		selectedFeatureNames = FMPModelIterator
				.getAllSelectedFeatureNames(root);
		return selectedFeatureNames;
	}

	public boolean featureExists(String featureName) {
		return FMPModelIterator.getAllExistingFeatureNames(root).contains(
				featureName);
	}

	public Object getAttributeValue(String featureName, String attributeName)
			throws FeatureNotFound, AttributeNotFound {
		Feature feature = FMPModelIterator
				.getExistingFeature(root, featureName);
		if (feature == null) {
			throw new FeatureNotFound(featureName + " not found.");
		}
		return FMPModelIterator.getAttributeValue(feature, attributeName);
	}

	public Object getFeature(String featureName) {
		return FMPModelIterator.getExistingFeature(root, featureName);
	}

	public boolean isFeatureSelected(String featureName) throws FeatureNotFound {
		return findSelectedFeatureNames().contains(featureName);
	}

	@Override
	public void loadConfigurationData(String filenameOrUri) {
		ResourceSet resourceSet = loadFmpPackages();
		setConfigurationData(getModelRoot(resourceSet, filenameOrUri));
	}
	
	private ResourceSet loadFmpPackages() {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put("fmp", new XMIResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(FmpPackage.eNS_URI,
				FmpPackage.eINSTANCE);
		return resourceSet;
	}
}
