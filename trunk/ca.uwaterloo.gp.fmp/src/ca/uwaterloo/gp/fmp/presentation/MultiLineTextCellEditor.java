/**************************************************************************************
 * Copyright (c) 2005, 2006 Generative Software Development Lab, University of Waterloo
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   1. Generative Software Development Lab, University of Waterloo,
 *      http://gp.uwaterloo.ca  - initial API and implementation
 **************************************************************************************/
package ca.uwaterloo.gp.fmp.presentation;

import java.text.MessageFormat;

import org.eclipse.emf.common.ui.celleditor.ExtendedTreeEditor;
import org.eclipse.jface.util.Assert;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;

/**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>
 */
public class MultiLineTextCellEditor extends CellEditor {

    private static final int MULTI_LINE_HEIGHT = 150;
	private static final int SINGLE_LINE_HEIGHT = 20;
	private static final String MAXIMIZE_LABEL = "v";
	private static final String MINIMIZE_LABEL = "^";
    private Text text;
    private Composite textPanel;
    private Button button;
	private ModifyListener modifyListener;
	private boolean isSelection = false;
	private boolean isDeleteable = false;
	private boolean isSelectable = false;
	private ExtendedTreeEditor treeEditor;
    private boolean multiline = false;
    private Composite buttonPanel;

    /**
     * @param i
     * @param tree
     * @param editor
     * 
     */
    public MultiLineTextCellEditor(ExtendedTreeEditor editor, Tree parent, int style) {
        super(parent, style);
        this.treeEditor = editor;
        treeEditor.minimumHeight = SINGLE_LINE_HEIGHT;
        treeEditor.verticalAlignment = SWT.TOP;
   	    text.getVerticalBar().setVisible(false);
//   	    button.setImage((Image) featureMdlEditPlugin.INSTANCE.getImage("full/obj16/Unchecked"));
        // TODO Auto-generated constructor stub
    }

     /**
     * Checks to see if the "deleteable" state (can delete/
     * nothing to delete) has changed and if so fire an
     * enablement changed notification.
     */
    private void checkDeleteable() {
    	boolean oldIsDeleteable = isDeleteable;
    	isDeleteable = isDeleteEnabled();
    	if (oldIsDeleteable != isDeleteable) {
    		fireEnablementChanged(DELETE);
    	}
    }
    /**
     * Checks to see if the "selectable" state (can select)
     * has changed and if so fire an enablement changed notification.
     */
    private void checkSelectable() {
    	boolean oldIsSelectable = isSelectable;
    	isSelectable = isSelectAllEnabled();
    	if (oldIsSelectable != isSelectable) {
    		fireEnablementChanged(SELECT_ALL);
    	}
    }
    /**
     * Checks to see if the selection state (selection /
     * no selection) has changed and if so fire an
     * enablement changed notification.
     */
    private void checkSelection() {
    	boolean oldIsSelection = isSelection;
    	isSelection = text.getSelectionCount() > 0;
    	if (oldIsSelection != isSelection) {
    		fireEnablementChanged(COPY);
    		fireEnablementChanged(CUT);
    	}
    }
    /* (non-Javadoc)
     * Method declared on CellEditor.
     */
    
    protected Control createControl(Composite parent) {
    	textPanel = new Composite(parent, SWT.BORDER | SWT.SHADOW_NONE);
    	GridLayout layout = new GridLayout();
    	layout.horizontalSpacing = 0;
    	layout.verticalSpacing = 0;
    	layout.marginHeight = 0;
    	layout.marginWidth = 0;
    	layout.numColumns = 2;
    	textPanel.setLayout(layout);
    	
    	text = new Text(textPanel, getStyle() | SWT.H_SCROLL | SWT.V_SCROLL);
    	GridData textGridData = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL | GridData.VERTICAL_ALIGN_BEGINNING);
    	text.setLayoutData(textGridData);
    	
    	GridData buttonGridData = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
    	GridData buttonPanelGridData = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
    	GridLayout buttonPanelLayout = new GridLayout();
    	buttonPanelLayout.horizontalSpacing = 0;
    	buttonPanelLayout.verticalSpacing = 0;
    	buttonPanelLayout.marginHeight = 0;
    	buttonPanelLayout.marginWidth = 0;
    	buttonPanel = new Composite(textPanel, SWT.NONE);
    	buttonPanel.setLayout(buttonPanelLayout);
    	buttonPanel.setLayoutData(buttonPanelGridData);
    	button = new Button(buttonPanel, SWT.PUSH);

    	button.setText(MAXIMIZE_LABEL);
    	button.addSelectionListener( new SelectionListener () {
    	    public void widgetSelected(SelectionEvent e) {
    	            performSizeChange();
    	    }

    	    public void widgetDefaultSelected(SelectionEvent e) {
    	        
    	    }
    	});
    	
    	text.addKeyListener(new KeyAdapter() {
    		// hook key pressed - see PR 14201  
    		public void keyPressed(KeyEvent e) {
                keyReleaseOccured(e);
                
    			// as a result of processing the above call, clients may have
    			// disposed this cell editor
    			if ((getControl() == null) || getControl().isDisposed())
    				return;
    			checkSelection(); // see explaination below
    			checkDeleteable();
    			checkSelectable();
    		}
    	});
    	textPanel.addTraverseListener(new TraverseListener() {
    		public void keyTraversed(TraverseEvent e) {
    			if (e.detail == SWT.TRAVERSE_ESCAPE || e.detail == SWT.TRAVERSE_RETURN) {
    				e.doit = false;
    			}
    		}
    	});
    	// We really want a selection listener but it is not supported so we
    	// use a key listener and a mouse listener to know when selection changes
    	// may have occured
    	textPanel.addMouseListener(new MouseAdapter() {
    		public void mouseUp(MouseEvent e) {
    			checkSelection();
    			checkDeleteable();
    			checkSelectable();
    		}
    	});
    	
    	// it is a trick, dealing with focus loosing. If focus is lost on both text and button, dispose textPanel
    	text.addFocusListener(new FocusAdapter() {
    		public void focusLost(FocusEvent e) {
    		    if (!button.isFocusControl()) {
        		    MultiLineTextCellEditor.this.focusLost();
        		    treeEditor.minimumHeight = 0;
        			getControl().dispose();
    		    }
    			
    		}
    	});
    	button.addFocusListener(new FocusAdapter() {
    		public void focusLost(FocusEvent e) {
    		    if (!text.isFocusControl()) {
        		    MultiLineTextCellEditor.this.focusLost();
        		    treeEditor.minimumHeight = 0;
        			getControl().dispose();
    		    }
    			
    		}
    	});

    	text.setFont(parent.getFont());
    	text.setBackground(parent.getBackground());
    	textPanel.setBackground(parent.getBackground());
    	buttonPanel.setBackground(parent.getBackground());
    	text.setText("");//$NON-NLS-1$
    	text.addModifyListener(getModifyListener());
    	return textPanel;
    }
    
    /**
     * The <code>TextCellEditor</code> implementation of
     * this <code>CellEditor</code> framework method returns
     * the text string.
     *
     * @return the text string
     */
    protected Object doGetValue() {
    	return text.getText();
    }
    /* (non-Javadoc)
     * Method declared on CellEditor.
     */
    protected void doSetFocus() {
    	if (text != null) {
    		text.selectAll();
    		text.setFocus();
    		checkSelection();
    		checkDeleteable();
    		checkSelectable();
    	}
    }
    /**
     * The <code>TextCellEditor</code> implementation of
     * this <code>CellEditor</code> framework method accepts
     * a text string (type <code>String</code>).
     *
     * @param value a text string (type <code>String</code>)
     */
    protected void doSetValue(Object value) {
    	Assert.isTrue(text != null && (value instanceof String));
    	text.removeModifyListener(getModifyListener());
    	text.setText((String) value);
    	text.addModifyListener(getModifyListener());
        if (text.getText().indexOf("\r") >= 0 || text.getText().indexOf("\n") >= 0) {
            performSizeChange();
  		}
    }
    /**
     * Processes a modify event that occurred in this text cell editor.
     * This framework method performs validation and sets the error message
     * accordingly, and then reports a change via <code>fireEditorValueChanged</code>.
     * Subclasses should call this method at appropriate times. Subclasses
     * may extend or reimplement.
     *
     * @param e the SWT modify event
     */
    protected void editOccured(ModifyEvent e) {
    	String value = text.getText();
    	if (value == null)
    		value = "";//$NON-NLS-1$
    	Object typedValue = value;
    	boolean oldValidState = isValueValid();
    	boolean newValidState = isCorrect(typedValue);
    	if (typedValue == null && newValidState)
    		Assert.isTrue(false, "Validator isn't limiting the cell editor's type range");//$NON-NLS-1$
    	if (!newValidState) {
    		// try to insert the current value into the error message.
    		setErrorMessage(MessageFormat.format(getErrorMessage(), new Object[] {value}));
    	}
    	valueChanged(oldValidState, newValidState);
    }
    /**
     * Since a text editor field is scrollable we don't
     * set a minimumSize.
     */
    public LayoutData getLayoutData() {
    	return new LayoutData();
    }
    /**
     * Return the modify listener.
     */
    private ModifyListener getModifyListener() {
    	if (modifyListener == null) {
    		modifyListener = new ModifyListener() {
    			public void modifyText(ModifyEvent e) {
    				editOccured(e);
    			}
    		};
    	}
    	return modifyListener;
    }

    /**
     * Handles a default selection event from the text control by applying the editor
     * value and deactivating this cell editor.
     * 
     * @param event the selection event
     * 
     * @since 3.0
     */
    protected void handleDefaultSelection(SelectionEvent event) {
        // same with enter-key handling code in keyReleaseOccured(e);
        fireApplyEditorValue();
        deactivate();
    }
    /**
     * The <code>TextCellEditor</code>  implementation of this 
     * <code>CellEditor</code> method returns <code>true</code> if 
     * the current selection is not empty.
     */
    public boolean isCopyEnabled() {
    	if (text == null || text.isDisposed())
    		return false;
    	return text.getSelectionCount() > 0;
    }
    /**
     * The <code>TextCellEditor</code>  implementation of this 
     * <code>CellEditor</code> method returns <code>true</code> if 
     * the current selection is not empty.
     */
    public boolean isCutEnabled() {
    	if (text == null || text.isDisposed())
    		return false;
    	return text.getSelectionCount() > 0;
    }
    /**
     * The <code>TextCellEditor</code>  implementation of this 
     * <code>CellEditor</code> method returns <code>true</code>
     * if there is a selection or if the caret is not positioned 
     * at the end of the text.
     */
    public boolean isDeleteEnabled() {
    	if (text == null || text.isDisposed())
    		return false;
    	return text.getSelectionCount() > 0 || text.getCaretPosition() < text.getCharCount();
    }
    /**
     * The <code>TextCellEditor</code>  implementation of this 
     * <code>CellEditor</code> method always returns <code>true</code>.
     */
    public boolean isPasteEnabled() {
    	if (text == null || text.isDisposed())
    		return false;
    	return true;
    }
    /**
     * The <code>TextCellEditor</code>  implementation of this 
     * <code>CellEditor</code> method always returns <code>true</code>.
     */
    public boolean isSaveAllEnabled() {
    	if (text == null || text.isDisposed())
    		return false;
    	return true;
    }
    /**
     * Returns <code>true</code> if this cell editor is
     * able to perform the select all action.
     * <p>
     * This default implementation always returns 
     * <code>false</code>.
     * </p>
     * <p>
     * Subclasses may override
     * </p>
     * @return <code>true</code> if select all is possible,
     *  <code>false</code> otherwise
     */
    public boolean isSelectAllEnabled() {
    	if (text == null || text.isDisposed())
    		return false;
    	return text.getCharCount() > 0;
    }

    /**
     * Processes a key release event that occurred in this cell editor.
     * <p>
     * The <code>TextCellEditor</code> implementation of this framework method 
     * ignores when the RETURN key is pressed since this is handled in 
     * <code>handleDefaultSelection</code>.
     * An exception is made for Ctrl+Enter for multi-line texts, since
     * a default selection event is not sent in this case. 
     * </p>
     *
     * @param keyEvent the key event
     */
    protected void keyReleaseOccured(KeyEvent keyEvent) {
    	if (keyEvent.character == '\r' && multiline) { // Return key
    		// Enter is handled in handleDefaultSelection.
    		// Do not apply the editor value in response to an Enter key event
    		// since this can be received from the IME when the intent is -not-
    		// to apply the value.  
    		// See bug 39074 [CellEditors] [DBCS] canna input mode fires bogus event from Text Control
    		//
    		// An exception is made for Ctrl+Enter for multi-line texts, since
    		// a default selection event is not sent in this case. 
    	    if (text != null && !text.isDisposed() && (text.getStyle() & SWT.MULTI) != 0) {
    	        if ((keyEvent.stateMask & SWT.CTRL) != 0) {
    	            super.keyReleaseOccured(keyEvent);
    	        }
    	    }
    	    return;
    	}
    	super.keyReleaseOccured(keyEvent);
    }

    /**
     * The <code>TextCellEditor</code> implementation of this
     * <code>CellEditor</code> method copies the
     * current selection to the clipboard. 
     */
    public void performCopy() {
    	text.copy();
    }
    /**
     * The <code>TextCellEditor</code> implementation of this
     * <code>CellEditor</code> method cuts the
     * current selection to the clipboard. 
     */
    public void performCut() {
    	text.cut();
    	checkSelection(); 
    	checkDeleteable();
    	checkSelectable();
    }
    /**
     * The <code>TextCellEditor</code> implementation of this
     * <code>CellEditor</code> method deletes the
     * current selection or, if there is no selection,
     * the character next character from the current position. 
     */
    public void performDelete() {
    	if (text.getSelectionCount() > 0)
    		// remove the contents of the current selection
    		text.insert(""); //$NON-NLS-1$
    	else {
    		// remove the next character
    		int pos = text.getCaretPosition();
    		if (pos < text.getCharCount()) {
    			text.setSelection(pos, pos + 1);
    			text.insert(""); //$NON-NLS-1$
    		}
    	}
    	checkSelection(); 
    	checkDeleteable();
    	checkSelectable();
    }
    /**
     * The <code>TextCellEditor</code> implementation of this
     * <code>CellEditor</code> method pastes the
     * the clipboard contents over the current selection. 
     */
    public void performPaste() {
    	text.paste();
    	checkSelection(); 
    	checkDeleteable();
    	checkSelectable();
    }
    /**
     * The <code>TextCellEditor</code> implementation of this
     * <code>CellEditor</code> method selects all of the
     * current text. 
     */
    public void performSelectAll() {
    	text.selectAll();
    	checkSelection(); 
    	checkDeleteable();
    }
    
    public void performSizeChange() {
        if (!multiline) {
        	button.setText(MINIMIZE_LABEL);
		    treeEditor.minimumHeight = MULTI_LINE_HEIGHT;
       	    text.getVerticalBar().setVisible(true);
//       	    button.setImage((Image) featureMdlEditPlugin.INSTANCE.getImage("full/obj16/Checked"));
        } else {
        	button.setText(MAXIMIZE_LABEL);
            treeEditor.minimumHeight = SINGLE_LINE_HEIGHT;;
       	    text.getVerticalBar().setVisible(false);
//       	    button.setImage((Image) featureMdlEditPlugin.INSTANCE.getImage("full/obj16/Unchecked"));
        }
        multiline = !multiline;
        treeEditor.layout();
    }
    
    /**
     * Returns the text control associated with this editor
     * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>
     */
    public Text getTextControl()
    {
    	return text;
    }
}
