package org.openarchitectureware.var.featureaccess.pv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend.util.stdlib.DynamicEcoreHelper;

import com.ps.consul.eclipse.ecore.pvmeta.Feature;
import com.ps.consul.eclipse.ecore.pvmeta.Model;

public class PVModelIterator {
	private static Map<EObject, PVFeatureValueObject> pvfeatureConfToFeatureValueObjectMap = new HashMap<EObject, PVFeatureValueObject>();

	private PVModelIterator() {
	}
	
	private static class PVFeatureValueObject {
		Model rootFeature = null;
		Map<String, Feature> featureNameToExistingFeatureMap = new HashMap<String, Feature>();
		Map<String, Feature> featureNameToSelectedFeatureMap = new HashMap<String, Feature>();

		PVFeatureValueObject(Model rootFeature) {
			this.rootFeature = rootFeature;
		}
	}

	public static List<EObject> getAllExistingFeatures(Model pvfeatureConf) {
		return new ArrayList<EObject>(getExistingFeatureMap(pvfeatureConf)
				.values());
	}

	public static List<String> getAllExistingFeatureNames(Model pvfeatureConf) {
		return new ArrayList<String>(getExistingFeatureMap(pvfeatureConf)
				.keySet());
	}

	public static List<EObject> getAllSelectedFeatures(Model pvfeatureConf) {
		return new ArrayList<EObject>(getSelectedFeatureMap(pvfeatureConf)
				.values());
	}

	public static List<String> getAllSelectedFeatureNames(Model pvfeatureConf) {
		return new ArrayList<String>(getSelectedFeatureMap(pvfeatureConf)
				.keySet());
	}

	private static Map<String, Feature> getExistingFeatureMap(
			Model pvfeatureConf) {
		return getFeatureValueObject(pvfeatureConf).featureNameToExistingFeatureMap;
	}

	private static Map<String, Feature> getSelectedFeatureMap(
			Model pvfeatureConf) {
		return getFeatureValueObject(pvfeatureConf).featureNameToSelectedFeatureMap;
	}

	private static PVFeatureValueObject getFeatureValueObject(Model root) {
		PVFeatureValueObject featureValueObject;
		if ((featureValueObject = pvfeatureConfToFeatureValueObjectMap
				.get(root)) == null) {
			featureValueObject = new PVFeatureValueObject(root);
			iterateOverFeatureChildren(featureValueObject);
			pvfeatureConfToFeatureValueObjectMap.put(root, featureValueObject);
		}
		return featureValueObject;
	}

	private static void iterateOverFeatureChildren(
			PVFeatureValueObject featureValueObject) {
		List<Feature> allFeatures = new ArrayList<Feature>();
		Model root = featureValueObject.rootFeature;
		Iterator<EObject> iterator = root.eAllContents();
		Object child;
		for (; iterator.hasNext();) {
			child = iterator.next();
			if (child instanceof Feature) {
				allFeatures.add((Feature) child);
			}
		}
		String featureName;
		for (Feature feature : allFeatures) {
			featureName = feature.getName();
			featureValueObject.featureNameToExistingFeatureMap.put(featureName,
					feature);
			if (feature.isSelected()) {
				featureValueObject.featureNameToSelectedFeatureMap.put(
						featureName, feature);
			}
		}
	}

	public static Feature getExistingFeature(Model pvfeatureConf,
			String featureName) {
		return getExistingFeatureMap(pvfeatureConf).get(featureName);
	}

	public static Object getAttributeValue(Feature feature, String attributeName) {
		DynamicEcoreHelper h = new DynamicEcoreHelper(feature.eClass()
				.getEPackage());
		return h.get(feature, attributeName);
	}
}
