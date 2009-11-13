package org.openarchitectureware.var.dotvbuilder.properties;


import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.dialogs.MessageDialog;
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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;
import org.openarchitectureware.var.dotvbuilder.Activator;
import org.openarchitectureware.var.dotvbuilder.PreferenceConstants;

public class DotVPropertyPage extends PropertyPage {

	private static final int TEXT_FIELD_WIDTH = 50;

	private Button projectSpecificSettingsButton;
	private Text featureBeginText;
	private Text featureEndText;
	private Text joinpointBeginText;
	private Text joinpointEndText;
	private Text adviceBeginText;
	private Text adviceEndText;
	private Text uriText;
	private Text fileNameText;

	/**
	 * Constructor for SamplePropertyPage.
	 */
	public DotVPropertyPage() {
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
		uriText.setEditable(b);
		fileNameText.setEditable(b);
		featureBeginText.setEditable(b);
		featureEndText.setEditable(b);
		joinpointBeginText.setEditable(b);
		joinpointEndText.setEditable(b);
		adviceBeginText.setEditable(b);
		adviceEndText.setEditable(b);
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
		label.setText("Feature In Filename");
		fileNameText = createText(composite,
				PreferenceConstants.FEATURE_IN_FILENAME);

		label = new Label(composite, SWT.NONE);
		label.setText("Feature Model URI");
		uriText = createText(composite, PreferenceConstants.FEATURE_MODEL_URI);

		label = new Label(composite, SWT.NONE);
		label.setText("Feature Begin:");
		featureBeginText = createText(composite, PreferenceConstants.FEATURE_BEGIN);
		
		label = new Label(composite, SWT.NONE);
		label.setText("Feature End");
		featureEndText = createText(composite, PreferenceConstants.FEATURE_END);
		
		label = new Label(composite, SWT.NONE);
		label.setText("Joinpoint Begin:");
		joinpointBeginText= createText(composite, PreferenceConstants.JOINPOINT_SECTION_BEGIN);
		
		label = new Label(composite, SWT.NONE);
		label.setText("Joinpoint End:");
		joinpointEndText= createText(composite, PreferenceConstants.JOINPOINT_SECTION_END);
		
		label = new Label(composite, SWT.NONE);
		label.setText("Advice Begin:");
		adviceBeginText= createText(composite, PreferenceConstants.ADVICE_SECTION_BEGIN);
		
		label = new Label(composite, SWT.NONE);
		label.setText("Advice End:");
		adviceEndText= createText(composite, PreferenceConstants.ADVICE_SECTION_END);
		
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
		fileNameText.setText(getDefault(PreferenceConstants.FEATURE_IN_FILENAME));
		uriText.setText(getDefault(PreferenceConstants.FEATURE_MODEL_URI));
		featureBeginText.setText(getDefault(PreferenceConstants.FEATURE_BEGIN));
		featureEndText.setText(getDefault(PreferenceConstants.FEATURE_END));
		joinpointBeginText.setText(getDefault(PreferenceConstants.JOINPOINT_SECTION_BEGIN));
		joinpointEndText.setText(getDefault(PreferenceConstants.JOINPOINT_SECTION_END));
		adviceBeginText.setText(getDefault(PreferenceConstants.ADVICE_SECTION_BEGIN));
		adviceEndText.setText(getDefault(PreferenceConstants.ADVICE_SECTION_END));
	}

	public boolean performOk() {
		// store the value in the owner text field
		try {
			storeValue(PropertyConstants.ENABLE_PROJECT_SETTINGS_PROP, projectSpecificSettingsButton.getSelection()+"");
			storeValue(PreferenceConstants.FEATURE_IN_FILENAME, fileNameText.getText());
			storeValue(PreferenceConstants.FEATURE_MODEL_URI, uriText.getText());
			storeValue(PreferenceConstants.FEATURE_BEGIN, featureBeginText.getText());
			storeValue(PreferenceConstants.FEATURE_END, featureEndText.getText());
			storeValue(PreferenceConstants.JOINPOINT_SECTION_BEGIN, joinpointBeginText.getText());
			storeValue(PreferenceConstants.JOINPOINT_SECTION_END, joinpointEndText.getText());
			storeValue(PreferenceConstants.ADVICE_SECTION_BEGIN, adviceBeginText.getText());
			storeValue(PreferenceConstants.ADVICE_SECTION_END, adviceEndText.getText());
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