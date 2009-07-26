package org.openarchitectureware.var.featureaccess.fmp;

import ca.uwaterloo.gp.fmp.Feature;

public class FMPFeatureModelIterator extends FMPModelIterator {

	protected void collect(Feature feature) {
		String featureName = getFeatureName(feature);
		if (featureName != null) {
			names.add(featureName);
		}
	}
}
