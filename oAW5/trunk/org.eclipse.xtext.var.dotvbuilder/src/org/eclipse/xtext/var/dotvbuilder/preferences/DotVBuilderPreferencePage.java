package org.eclipse.xtext.var.dotvbuilder.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.xtext.var.dotvbuilder.Activator;
import org.eclipse.xtext.var.dotvbuilder.PreferenceConstants;


public class DotVBuilderPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	public DotVBuilderPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Dot V Builder Configuration");
	}

	public void createFieldEditors() {
		addField(new StringFieldEditor(PreferenceConstants.FEATURE_MODEL_URI,
				"Feature Model URI:", getFieldEditorParent()));

		addField(new StringFieldEditor(PreferenceConstants.FEATURE_IN_FILENAME,
				"Feature in Filename:", getFieldEditorParent()));
		
		addField(new StringFieldEditor(PreferenceConstants.FEATURE_BEGIN,
				"Feature Begin:", getFieldEditorParent()));
		
		addField(new StringFieldEditor(PreferenceConstants.FEATURE_END,
				"Feature End:", getFieldEditorParent()));

		addField(new StringFieldEditor(PreferenceConstants.JOINPOINT_SECTION_BEGIN,
				"Joinpoint Section Begin:", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.JOINPOINT_SECTION_END,
				"Joinpoint Section End:", getFieldEditorParent()));

		addField(new StringFieldEditor(PreferenceConstants.ADVICE_SECTION_BEGIN,
				"Advice Section Begin:", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.ADVICE_SECTION_END,
				"Advice Section End:", getFieldEditorParent()));

	}

	public void init(IWorkbench workbench) {
	}

}