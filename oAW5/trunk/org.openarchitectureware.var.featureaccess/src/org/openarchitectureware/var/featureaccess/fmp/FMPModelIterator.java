package org.openarchitectureware.var.featureaccess.fmp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import ca.uwaterloo.gp.fmp.ConfigState;
import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.FeatureGroup;
import ca.uwaterloo.gp.fmp.Node;
import ca.uwaterloo.gp.fmp.TypedValue;

public class FMPModelIterator {
	private static Map<Feature, FMPFeatureValueObject> rootFeatureToFeatureValueObjectMap = new HashMap<Feature, FMPFeatureValueObject>();

	private FMPModelIterator() {
	}
	
	protected static class FMPFeatureValueObject {
		Feature rootFeature = null;
		Map<String, Feature> featureNameToExistingFeatureMap = new HashMap<String, Feature>();
		Map<String, Feature> featureNameToSelectedFeatureMap = new HashMap<String, Feature>();

		FMPFeatureValueObject(Feature rootFeature) {
			this.rootFeature = rootFeature;
			String featureName = FMPModelIterator.getFeatureName(rootFeature);
			featureNameToExistingFeatureMap.put(featureName, rootFeature);
			featureNameToSelectedFeatureMap.put(featureName, rootFeature);
		}
	}

	public static List<Feature> getAllExistingFeatures(EObject root) {
		return new ArrayList<Feature>(getExistingFeatureMap(root).values());
	}

	public static List<String> getAllExistingFeatureNames(EObject root) {
		return new ArrayList<String>(getExistingFeatureMap(root).keySet());
	}

	public static List<Feature> getAllSelectedFeatures(EObject root) {
		return new ArrayList<Feature>(getSelectedFeatureMap(root).values());
	}

	public static List<String> getAllSelectedFeatureNames(EObject root) {
		return new ArrayList<String>(getSelectedFeatureMap(root).keySet());
	}

	private static Map<String, Feature> getExistingFeatureMap(EObject root) {
		return getFeatureValueObject(root).featureNameToExistingFeatureMap;
	}

	private static Map<String, Feature> getSelectedFeatureMap(EObject root) {
		return getFeatureValueObject(root).featureNameToSelectedFeatureMap;
	}

	private static FMPFeatureValueObject getFeatureValueObject(EObject root) {
		FMPFeatureValueObject featureValueObject = null;
		if (root instanceof Feature) {
			Feature rootFeature = (Feature) root;
			featureValueObject = getFeatureValueObject(rootFeature);
		}
		return featureValueObject;
	}

	private static FMPFeatureValueObject getFeatureValueObject(Feature root) {
		FMPFeatureValueObject featureValueObject;
		if ((featureValueObject = rootFeatureToFeatureValueObjectMap.get(root)) == null) {
			featureValueObject = new FMPFeatureValueObject(root);
			EList<Node> featureChildren = typeSafeConversionToNodeList(root
					.getChildren());
			iterateOverFeatureChildren(root, featureValueObject,
					featureChildren);
			rootFeatureToFeatureValueObjectMap.put(root, featureValueObject);
		}
		return featureValueObject;
	}

	private static void iterateOverFeatureChildren(Node parent,
			FMPFeatureValueObject featureValueObject, EList<Node> childFeatures) {
		EList<Node> featureChildren = new BasicEList<Node>();
		for (Node child : childFeatures) {
			if (child instanceof Feature) {
				Feature feature = (Feature) child;
				collect(child, featureValueObject, feature);
				featureChildren = typeSafeConversionToNodeList(feature
						.getChildren());
			} else if (child instanceof FeatureGroup) {
				FeatureGroup group = (FeatureGroup) child;
				featureChildren = typeSafeConversionToNodeList(group
						.getChildren());
			}
			iterateOverFeatureChildren(child, featureValueObject,
					featureChildren);
		}
	}

	@SuppressWarnings("unchecked")
	private static EList<Node> typeSafeConversionToNodeList(EList children) {
		return (EList<Node>) children;
	}

	private static void collect(Node parent,
			FMPFeatureValueObject featureValueObject, Feature feature) {
		String featureName = getFeatureName(feature);
		if (featureName != null) {
			putValuesToFeatureMap(parent, featureValueObject, featureName,
					feature);
			rootFeatureToFeatureValueObjectMap.put(
					featureValueObject.rootFeature, featureValueObject);
		}
	}

	private static void putValuesToFeatureMap(Node parent,
			FMPFeatureValueObject featureValueObject, String featureName,
			Feature feature) {
		featureValueObject.featureNameToExistingFeatureMap.put(featureName,
				feature);
		if (isFeatureSelected(parent, feature)) {
			featureValueObject.featureNameToSelectedFeatureMap.put(featureName,
					feature);
		}
	}

	private static boolean isFeatureSelected(Node parent, Feature feature) {
		boolean featureSelected = false;
		for (Object confObject : parent.getConfs()) {
			if (confObject instanceof Feature) {
				Feature confFeature = (Feature) confObject;
				if (getFeatureName(confFeature).equals(getFeatureName(feature))) {
					ConfigState configState = confFeature.getState();
					if (configState == ConfigState.USER_SELECTED_LITERAL
							|| configState == ConfigState.MACHINE_SELECTED_LITERAL) {
						featureSelected = true;
						break;
					}
				}
			}
		}
		return featureSelected;
	}

	public static Feature getExistingFeature(Feature root, String featureName) {
		return getExistingFeatureMap(root).get(featureName);
	}

	public static Feature getAttribute(Feature feature, String attributeName) {
		Feature properties = feature.getProperties();
		Feature property = null;
		for (Object propertyObject : properties.getChildren()) {
			if (propertyObject instanceof Feature) {
				property = (Feature) propertyObject;
				if (property.getId().equals(attributeName)) {
					break;
				}
			}
		}
		return property;
	}

	private static String getFeatureName(Feature feature) {
		String attributeName = "name";
		return getAttributeValue(feature, attributeName);
	}

	public static String getAttributeValue(Feature feature, String attributeName) {
		Feature nameAttribute = getAttribute(feature, attributeName);
		TypedValue value = nameAttribute.getTypedValue();
		String attributeValue = null;
		if (value != null) {
			attributeValue = value.getStringValue();
		}
		return attributeValue;
	}
	
	public static void reset() {
		FMPModelIterator.rootFeatureToFeatureValueObjectMap.clear();
	}
}
