package org.openarchitectureware.var.featureaccess.pv;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.mwe.core.WorkflowInterruptedException;
import org.eclipse.emf.mwe.core.config.AttributeNotFound;
import org.eclipse.emf.mwe.core.config.FeatureNotFound;
import org.openarchitectureware.var.featureaccess.ConfigurationModelWrapper;

import com.ps.consul.eclipse.ecore.pvmeta.Feature;
import com.ps.consul.eclipse.ecore.pvmeta.Model;
import com.ps.consul.eclipse.ecore.pvmeta.PvmetaPackage;
import com.ps.consul.eclipse.ecore.pvmodel.PvmodelPackage;

public class PVConfigurationModelWrapper extends ConfigurationModelWrapper {

	public void setConfigurationData(Object data) {
		if (!(data instanceof Model)) {
			throw new WorkflowInterruptedException(
					"the file which is defined by configurationModelUri is no pv config model!");
		}
		super.setConfigurationData(data);
	}
	
	public List<String> findSelectedFeatureNames() {
		List<String> selectedFeatureNames = new ArrayList<String>();
		selectedFeatureNames = PVModelIterator
				.getAllSelectedFeatureNames((Model) getConfigurationData());
		return selectedFeatureNames;
	}

	public boolean featureExists(String featureName) {
		return PVModelIterator.getAllExistingFeatureNames(
				(Model) getConfigurationData()).contains(featureName);
	}

	public Object getAttributeValue(String featureName, String attributeName)
			throws FeatureNotFound, AttributeNotFound {
		Feature feature = PVModelIterator
				.getExistingFeature((Model) getConfigurationData(), featureName);
		if (feature == null) {
			throw new FeatureNotFound(featureName + " not found.");
		}
		return PVModelIterator.getAttributeValue(feature, attributeName);
	}

	public Object getFeature(String featureName) {
		return PVModelIterator.getExistingFeature((Model) getConfigurationData(),
				featureName);
	}

	public boolean isFeatureSelected(String featureName) throws FeatureNotFound {
		return findSelectedFeatureNames().contains(featureName);
	}

	@Override
	public void loadConfigurationData(String filenameOrUri) {
		ResourceSet resourceSet = loadPureVariantsPackages();
		setConfigurationData(getModelRoot(resourceSet, filenameOrUri));
	}
	
	private ResourceSet loadPureVariantsPackages() {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put("xmi", new XMIResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(PvmetaPackage.eNS_URI,
				PvmetaPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(PvmodelPackage.eNS_URI,
				PvmodelPackage.eINSTANCE);
		return resourceSet;
	}
}
