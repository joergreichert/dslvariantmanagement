package org.openarchitectureware.var.featureaccess.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.mwe.core.WorkflowInterruptedException;

public class TextModelIterator {
	private static Map<List<String>, TextFeatureValueObject> textfeatureConfToFeatureValueObjectMap = new HashMap<List<String>, TextFeatureValueObject>();
	
	private TextModelIterator() {
	}
	
	private static class TextFeatureValueObject {
		List<String> fileEntries = null;
		List<String> existingFeatureNames = new ArrayList<String>();
		List<String> selectedFeatureNames = new ArrayList<String>();

		TextFeatureValueObject(List<String> fileEntries) {
			this.fileEntries = fileEntries;
		}
	}
	
	public static List<String> getAllExistingFeatures(List<String> fileEntries) {
		return getAllExistingFeatureNames(fileEntries);
	}

	public static List<String> getAllExistingFeatureNames(List<String> fileEntries) {
		return getExistingFeatureList(fileEntries);
	}

	public static List<String> getAllSelectedFeatures(List<String> fileEntries) {
		return getAllSelectedFeatureNames(fileEntries);
	}

	public static List<String> getAllSelectedFeatureNames(List<String> fileEntries) {
		return getSelectedFeatureList(fileEntries);
	}

	private static List<String> getExistingFeatureList(
			List<String> fileEntries) {
		return getFeatureValueObject(fileEntries).existingFeatureNames;
	}

	private static List<String> getSelectedFeatureList(
			List<String> fileEntries) {
		return getFeatureValueObject(fileEntries).selectedFeatureNames;
	}

	private static TextFeatureValueObject getFeatureValueObject(List<String> fileEntries) {
		TextFeatureValueObject featureValueObject;
		if ((featureValueObject = textfeatureConfToFeatureValueObjectMap
				.get(fileEntries)) == null) {
			featureValueObject = new TextFeatureValueObject(fileEntries);
			iterateOverFeatureChildren(featureValueObject);
			textfeatureConfToFeatureValueObjectMap.put(fileEntries, featureValueObject);
		}
		return featureValueObject;
	}

	private static void iterateOverFeatureChildren(
			TextFeatureValueObject featureValueObject) {
		List<String> fileEntries = featureValueObject.fileEntries;
		for (String line : fileEntries) {
			line = line.trim();
			boolean isSelected = line.startsWith("+"); 
			if (isSelected || line.startsWith("-")) {
				line = line.substring(1);
				featureValueObject.existingFeatureNames.add(line);
				if (isSelected) {
					featureValueObject.selectedFeatureNames.add(line);
				}
			} else {
				throw new WorkflowInterruptedException("The given text configuration model is invalid, "
						+ "as in the model file every line have to start with + or -.");
			}
		}
	}
	
	public static void reset() {
		TextModelIterator.textfeatureConfToFeatureValueObjectMap.clear();
	}
}
