package org.openarchitectureware.var.actions.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.openarchitectureware.var.actions.Activator;
import org.openarchitectureware.var.actions.PreferenceConstants;


/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.VARIANTS_SRC_PATH, "src-variants");
	}

}
