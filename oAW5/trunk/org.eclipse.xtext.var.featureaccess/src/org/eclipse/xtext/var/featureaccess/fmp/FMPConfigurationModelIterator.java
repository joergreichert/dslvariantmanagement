package org.eclipse.xtext.var.featureaccess.fmp;

import ca.uwaterloo.gp.fmp.ConfigState;
import ca.uwaterloo.gp.fmp.Feature;

public class FMPConfigurationModelIterator extends FMPModelIterator {

	protected void collect(Feature feature) {
		for (Object confObject : feature.getConfs()) {
			if (confObject instanceof Feature) {
				Feature confFeature = (Feature) confObject;
				ConfigState configState = confFeature.getState();
				if (configState == ConfigState.USER_SELECTED_LITERAL
						|| configState == ConfigState.MACHINE_SELECTED_LITERAL) {
					names.add(getFeatureName(confFeature));
				}
			}
		}
	}
}
