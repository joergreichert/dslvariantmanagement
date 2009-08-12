package org.openarchitectureware.var.actions.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.openarchitectureware.var.actions.Activator;
import org.openarchitectureware.var.actions.PreferenceConstants;


public class DotVBuilderPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	public DotVBuilderPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Dot V Builder Configuration");
	}

	public void createFieldEditors() {
		addField(new StringFieldEditor(PreferenceConstants.VARIANTS_SRC_PATH,
				"Variants Soource Path:", getFieldEditorParent()));


	}

	public void init(IWorkbench workbench) {
	}

}