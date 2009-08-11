package org.eclipse.xtext.var.dotvbuilder.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.xtext.var.dotvbuilder.Activator;
import org.eclipse.xtext.var.dotvbuilder.PreferenceConstants;


/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		
		store.setDefault(PreferenceConstants.FEATURE_IN_FILENAME, "#");
		store.setDefault(PreferenceConstants.FEATURE_MODEL_URI, "");

		store.setDefault(PreferenceConstants.FEATURE_BEGIN, "//#");
		store.setDefault(PreferenceConstants.FEATURE_END, "//~#");

		store.setDefault(PreferenceConstants.ADVICE_SECTION_BEGIN, "//+");
		store.setDefault(PreferenceConstants.ADVICE_SECTION_END, "//~+");
		
		store.setDefault(PreferenceConstants.JOINPOINT_SECTION_BEGIN, "//>");
		store.setDefault(PreferenceConstants.JOINPOINT_SECTION_END, "//~>");
	}

}
