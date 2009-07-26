package org.eclipse.xtext.var.featureaccess.fmp;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.FeatureGroup;
import ca.uwaterloo.gp.fmp.TypedValue;

public abstract class FMPModelIterator {
	private static FMPModelIterator featureIterator = new FMPFeatureModelIterator();
	private static FMPModelIterator configurationIterator = new FMPConfigurationModelIterator();
	
	public static List<String> getFeatureNames(EObject root) {
		if(root instanceof Feature) {
			Feature rootFeature = (Feature) root;
			return getFeatureNames(rootFeature);
		}
		return featureIterator.getCollectedNames();
	}
	
	public static List<String> getFeatureNames(Feature root) {
		featureIterator.iterateOverFeatureChildren(root.getChildren());
		return featureIterator.getCollectedNames();
	}
	
	public static List<String> getSelectedFeatureNames(EObject root) {
		if(root instanceof Feature) {
			Feature rootFeature = (Feature) root;
			return getSelectedFeatureNames(rootFeature);
		}
		return configurationIterator.getCollectedNames();
	}
	
	public static List<String> getSelectedFeatureNames(Feature root) {
		configurationIterator.iterateOverFeatureChildren(root.getChildren());
		return configurationIterator.getCollectedNames();
	}
	
	protected List<String> names = new ArrayList<String>();
	
	private void iterateOverFeatureChildren(EList childFeatures) {
		for (Object child : childFeatures) {
			if (child instanceof Feature) {
				Feature feature = (Feature) child;
				collect(feature);
				iterateOverFeatureChildren(feature.getChildren());
			} else if (child instanceof FeatureGroup) {
				FeatureGroup group = (FeatureGroup) child;
				iterateOverFeatureChildren(group.getChildren());
			}
		}
	}

	protected static String getFeatureName(Feature feature) {
		Feature properties = feature.getProperties();
		TypedValue value = null;
		for (Object propertyObject : properties.getChildren()) {
			if (propertyObject instanceof Feature) {
				Feature property = (Feature) propertyObject;
				if (property.getId().equals("name")) {
					value = property.getTypedValue();
					if (value != null) {
						return value.getStringValue();
					}
				}
			}
		}
		return null;
	}
	
	protected abstract void collect(Feature feature);
	
	private List<String> getCollectedNames() {
		return names;
	}
}
