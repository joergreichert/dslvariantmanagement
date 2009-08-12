package org.openarchitectureware.var.actions.properties;


import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;
import org.openarchitectureware.var.actions.Activator;
import org.openarchitectureware.var.actions.properties.PropertyConstants;
import org.openarchitectureware.var.actions.PreferenceConstants;


public class VarActionPropertyPage extends PropertyPage {

	private static final int TEXT_FIELD_WIDTH = 50;

	private Button projectSpecificSettingsButton;
	private Text srcVariantsPath;

	/**
	 * Constructor for SamplePropertyPage.
	 */
	public VarActionPropertyPage() {
		super();
	}

	private void addFirstSection(Composite parent) {
		Composite composite = createDefaultComposite(parent, 1);

		projectSpecificSettingsButton = new Button(composite, SWT.CHECK);
		projectSpecificSettingsButton.setText("Enable Project Specific Settings");
		boolean checked;
		try {
			checked = Boolean.parseBoolean(getElement()
					.getPersistentProperty(new QualifiedName("",
							PropertyConstants.ENABLE_PROJECT_SETTINGS_PROP)));
		} catch (Exception e) {
			checked = false;
		}
		projectSpecificSettingsButton.setSelection(checked);
		projectSpecificSettingsButton.addSelectionListener(new SelectionAdapter() {
		
			@Override
			public void widgetSelected(SelectionEvent e) {
				if ( projectSpecificSettingsButton.getSelection()) {
					enableFields(true);
				}else {
					enableFields(false);
				}
			}

		
		});
	}
	private void enableFields(boolean b) {
		srcVariantsPath.setEditable(b);
	}

	private void addSeparator(Composite parent) {
		Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		separator.setLayoutData(gridData);
	}

	private void addSecondSection(Composite parent) {
		Composite composite = createDefaultComposite(parent, 2);

		Label label = null;
		
		label = new Label(composite, SWT.NONE);
		label.setText("Variants Source Path");
		srcVariantsPath = createText(composite,
				PreferenceConstants.VARIANTS_SRC_PATH);
		
	}

	private Text createText(Composite composite, String key) {
		Text text = new Text(composite, SWT.SINGLE | SWT.BORDER);
		GridData gd = new GridData();
		gd.widthHint = convertWidthInCharsToPixels(TEXT_FIELD_WIDTH);
		text.setLayoutData(gd);

		try {
			String begin = getElement()
					.getPersistentProperty(new QualifiedName("", key));
			text.setText((begin != null) ? begin : getDefault(key));
		} catch (CoreException e) {
			text.setText(getDefault(key));
		}
		return text;
	}

	public IProject getElement() {
		return (IProject) super.getElement().getAdapter(IProject.class);
	}

	private String getDefault(String key) {
		return Activator.getDefault().getPreferenceStore().getString(key);
	}

	/**
	 * @see PreferencePage#createContents(Composite)
	 */
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		GridData data = new GridData(GridData.FILL);
		data.grabExcessHorizontalSpace = true;
		composite.setLayoutData(data);

		addFirstSection(composite);
		addSeparator(composite);
		addSecondSection(composite);
		enableFields(projectSpecificSettingsButton.getSelection());
		return composite;
	}

	private Composite createDefaultComposite(Composite parent, int noOfCols) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = noOfCols;
		composite.setLayout(layout);

		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		composite.setLayoutData(data);

		return composite;
	}

	protected void performDefaults() {
		projectSpecificSettingsButton.setSelection(Boolean.parseBoolean(getDefault(PropertyConstants.ENABLE_PROJECT_SETTINGS_PROP)));		
		srcVariantsPath.setText(getDefault(PreferenceConstants.VARIANTS_SRC_PATH));
	}

	public boolean performOk() {
		// store the value in the owner text field
		try {
			
			storeValue(PropertyConstants.ENABLE_PROJECT_SETTINGS_PROP, projectSpecificSettingsButton.getSelection()+"");
			storeValue(PreferenceConstants.VARIANTS_SRC_PATH, srcVariantsPath.getText());
		} catch (CoreException e) {
			return false;
		}
		return true;
	}

	private void storeValue(String key, String value) throws CoreException {
		getElement().setPersistentProperty(new QualifiedName("",
				key), value);
	}

}