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
package ca.uwaterloo.gp.fmp.constraints.ui;

import org.eclipse.emf.common.ui.celleditor.ExtendedTableEditor;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TableItem;

import ca.uwaterloo.gp.fmp.Constraint;
import ca.uwaterloo.gp.fmp.presentation.MultiLineTableCellEditor;


/**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>
 */
public class ConstraintTableEditor extends ExtendedTableEditor {
	protected Color white;
	protected MultiLineTableCellEditor cellEditor;
	protected ICellEditorListener editorListener;
	// currently edited value
	protected Object editValue;
	protected Constraint constraint;
	protected TableViewer tableViewer;
	
	public ConstraintTableEditor(TableViewer tableViewer)
	{
	    super(tableViewer.getTable());
	    this.tableViewer = tableViewer;
	    white = table.getDisplay().getSystemColor(SWT.COLOR_WHITE);
	    createEditorListener();
	}
	
	protected void editItem(TableItem tableItem,int pos)
	{
		final Object object = tableItem.getData();
		if (pos == 1 && object instanceof Constraint && ((Constraint)object).getText() != null)
		{
			constraint = (Constraint)object;
			cellEditor = new MultiLineTableCellEditor(this, table, SWT.MULTI | SWT.WRAP);			
			cellEditor.activate();
			createContextMenuFor(cellEditor);
			
			Control control = cellEditor.getControl();
			if (control == null) {
				cellEditor.deactivate();
				cellEditor = null;
				return;
			}
			cellEditor.addListener(editorListener);

			// set the layout of the table tree editor to match the cell editor
			CellEditor.LayoutData layout = cellEditor.getLayoutData();
			horizontalAlignment = layout.horizontalAlignment;
			grabHorizontal = layout.grabHorizontal;
			minimumWidth = layout.minimumWidth;
			setEditor(control, tableItem, pos);
			
			// give focus to the cell editor
			cellEditor.setFocus();
			editValue = constraint.getText();
			cellEditor.setValue(editValue);
			if (cellEditor.isSelectAllEnabled())
			    cellEditor.performSelectAll();
		}
	}
	
    /**
     * This creates a context menu for the viewer and adds a listener as well
     * registering the menu for extension. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * copied off and changed off of FeatureMdlEditor.createContextMenu
     */
    protected void createContextMenuFor(MultiLineTableCellEditor cellEditor)
    {
 		// Top-level Menu
		MenuManager menuManager = new MenuManager("#PopUp");
        menuManager.setRemoveAllWhenShown(false);
        
        // Create a new context menu - difference between this menu and MenuManager? 
        Menu menu = menuManager.createContextMenu(cellEditor.getTextControl());
        cellEditor.getTextControl().setMenu(menu);
                        
        // TODO: Move the old menu items to the new context menu                
        
        // This is the new menu item to be added - with individual templates
        MenuManager subMenuManager = new MenuManager("Insert template");
        subMenuManager.add(new InsertConstraintTemplateAction(cellEditor, "every $x in...", "every $x in // satisfies (count($x//) = count($x//))"));
        subMenuManager.add(new InsertConstraintTemplateAction(cellEditor, "some $x in...", "some $x in // satisfies (count($x//) = count($x//))"));

        menuManager.add(subMenuManager);
    }
	
	private void createEditorListener() {
		editorListener = new ICellEditorListener() {
			public void cancelEditor() {
				deactivateCellEditor();
			}
			public void editorValueChanged(boolean oldValidState,
					boolean newValidState) {
				//Do nothing
			}
			public void applyEditorValue() {
			    if (cellEditor == null) 
					return;	
				// Check if editor has a valid value
				if (!cellEditor.isValueValid()) {
					return;
				} 
				// See if the value changed and if so update
				Object newValue = cellEditor.getValue();
				boolean changed = false;
				if (editValue == null) {
					if (newValue != null)
						changed = true;
				} else if (!editValue.equals(newValue)) 
					changed = true;

				// Set the editor value
				if (changed && constraint != null)  {
					// apply change in model
					constraint.setText((String)newValue);
					
					// apply change in view
					tableViewer.update(constraint, null);
					
				    deactivateCellEditor();
				}
			}
		};
	}
	void deactivateCellEditor() {
//		setEditor(null, null,);
		if (cellEditor != null) {
			cellEditor.deactivate();
			cellEditor.removeListener(editorListener);
			cellEditor = null;
		}
	}
//    public void mouseUp(MouseEvent event) {
//        if (event.button == 1)	{
//	          TableItem tableItem = table.getItem(new Point(event.x, event.y));
//		      if (tableItem != null) {    
//		          if (editTableItem == tableItem)	{
//			            editTableItem = null;
//			            editItem(tableItem);			            
//		          }
//		      }
//        }
//    }
}
