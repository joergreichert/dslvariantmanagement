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

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.ui.celleditor.ExtendedTreeEditor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.ui.provider.PropertyDescriptor;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import ca.uwaterloo.gp.fmp.Clonable;
import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.FeatureGroup;
import ca.uwaterloo.gp.fmp.Node;
import ca.uwaterloo.gp.fmp.ValueType;
import ca.uwaterloo.gp.fmp.provider.command.CloneCommand;
import ca.uwaterloo.gp.fmp.system.ModelManipulation;
import ca.uwaterloo.gp.fmp.system.ModelNavigation;
import ca.uwaterloo.gp.fmp.system.RoleQuery;

/**
 * @author Michal Antkiewicz <mantkiew@swen.uwaterloo.ca>
 */
public class FmpAdapterFactoryTreeEditor extends ExtendedTreeEditor {
	protected AdapterFactory adapterFactory;
	protected EditingDomain editingDomain;
	protected TreeItem currentTreeItem;
	protected Color white;
	protected CellEditor cellEditor;
	protected ICellEditorListener editorListener;
	// currently edited value
	protected Object editValue;
	protected EObject owner;
	//protected IDirectEditItemProvider directEditItemProvider;
    
    //<CONSTRAINT RESOLUTION
    protected TreeViewer treeViewer;
	private boolean doubleClick;
    //>CONSTRAINT RESOLUTION
	
	public FmpAdapterFactoryTreeEditor(Tree tree, AdapterFactory adapterFactory, EditingDomain domain, TreeViewer treeViewer)	{
	    super(tree);
	    this.treeViewer = treeViewer;
	    this.adapterFactory = adapterFactory;
	    this.editingDomain = domain;
	    white = tree.getDisplay().getSystemColor(SWT.COLOR_WHITE);
	    createEditorListener();
	}
	public AdapterFactory getAdapterFactory()	{
	    return adapterFactory;
	}
	public void setAdapterFactory(AdapterFactory adapterFactory)	{
	    this.adapterFactory = adapterFactory;
	}
	protected void checkItem(TreeItem treeItem, boolean controlKeyPressed) {
	    final Object object = treeItem.getData();
		if (object instanceof Clonable)  {
		    owner = (EObject) object;
		    
		    CompoundCommand setStateCommand = ModelManipulation.INSTANCE.getNewState(editingDomain, (Clonable)owner, controlKeyPressed);
		    
		    //SetCommand cmd = new SetCommand(editingDomain, (EObject) object, FmpPackage.eINSTANCE.getClonable_State(), newState);
		    if (setStateCommand != null && setStateCommand.canExecute())
		        editingDomain.getCommandStack().execute(setStateCommand);
		    
		    treeViewer.refresh();
		}
	}
	protected void editItem(TreeItem treeItem) {
		final Object object = treeItem.getData();
		if (object instanceof Clonable)  {
		    owner = (EObject) object;
		    
		    // first get property descriptor
		    
		    IItemPropertyDescriptor itemPropertyDescriptor = ModelManipulation.INSTANCE.getDirectEditPropertyDescriptor(adapterFactory, object);
		    
		    if (itemPropertyDescriptor == null)
			    return; 
			PropertyDescriptor propertyDescriptor = new PropertyDescriptor(object, itemPropertyDescriptor); 

			// IF AND ONLY IF editing String attribute, we use special text widget 
			if (owner instanceof Feature && 
					((Feature)owner).getTypedValue() != null && 
					((Feature)owner).getProperties() == null && 
					((Feature)owner).getValueType() == ValueType.STRING_LITERAL) {
			    cellEditor = new MultiLineTextCellEditor(this, tree, SWT.MULTI | SWT.WRAP);
			}   
			else { 
				cellEditor = propertyDescriptor.createPropertyEditor(tree);
			}
			cellEditor.activate();
			
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
			setEditor(control, treeItem);
			
			// give focus to the cell editor
			cellEditor.setFocus();
			editValue = ModelManipulation.getDirectEditValue(object);
			cellEditor.setValue(editValue);
			if (cellEditor.isSelectAllEnabled())
			    cellEditor.performSelectAll();
		}
	}
	protected void cloneItem(TreeItem treeItem) {
	    final Object object = treeItem.getData();
		if (object instanceof Clonable)  {
		    owner = (EObject) object;
		    
		    Command cmd = new CloneCommand(editingDomain, adapterFactory, (Clonable) owner);
		    if (cmd.canExecute())
		        editingDomain.getCommandStack().execute(cmd);
		}
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
				if (changed)  {
					// See if the value is valid
					int locationType = RoleQuery.INSTANCE.getLocationType((Node) owner); 
					if (locationType == RoleQuery.MODEL_PROPERTIES ||
						locationType == RoleQuery.CONFIGURATION_PROPERTIES  ||
						locationType == RoleQuery.METAMODEL_PROPERTIES) {
						// so the owner is a property feature (id, name, min, max, etc.)
						int propertyRole = RoleQuery.INSTANCE.getPropertyRole((Node) owner);
						// check that 0 <= minOccur <= maxOccur
						if (propertyRole == RoleQuery.MIN_OCCUR) {
							if (newValue instanceof Integer) {
								int min = ((Integer) newValue).intValue();
								if (min < 0)
									return;
								// retrieve maxOccur
								EList parentChildren = ((Node) owner.eContainer()).getChildren();
								int index = parentChildren.indexOf(owner);
								Feature maxFeature = (Feature) parentChildren.get(index + 1);
								int max = maxFeature.getTypedValue().getIntegerValue().intValue();
								if (min > max && max != -1)
									return;
							}
							else 
								return;
						} else if (propertyRole == RoleQuery.MAX_OCCUR) {
							if (newValue instanceof Integer) {
								int max = ((Integer) newValue).intValue();
								if (max < 0 && max != -1)
									return;
								// retrieve maxOccur
								EList parentChildren = ((Node) owner.eContainer()).getChildren();
								int index = parentChildren.indexOf(owner);
								Feature minFeature = (Feature) parentChildren.get(index - 1);
								if (max < minFeature.getTypedValue().getIntegerValue().intValue() && max != -1)
									return;
								// for groups max < count(children)
								// get the described node
								Feature config = ModelNavigation.INSTANCE.navigateToRootFeature((Node) owner);
								Node describedNode = config.getDescribedNode(); 
								if (describedNode instanceof FeatureGroup) {
									FeatureGroup group = (FeatureGroup) describedNode;
									if (group.getChildren().size() < max || max == -1)
										return;
								}
							}
							else 
								return;
						}
					}
				    
					Command cmd = ModelManipulation.createDirectEditSetCommand(adapterFactory, editingDomain, owner, newValue);
					if (cmd.canExecute())
						editingDomain.getCommandStack().execute(cmd);
					deactivateCellEditor();
				}
			}
		};
	}
	void deactivateCellEditor() {
		setEditor(null, null);
		if (cellEditor != null) {
			cellEditor.deactivate();
			cellEditor.removeListener(editorListener);
			cellEditor = null;
		}
	}
    public void mouseUp(MouseEvent event) {
        if (event.button == 1 && !doubleClick)	{
        	// get the control state
        	boolean controlKeyPressed = (event.stateMask & SWT.CTRL) == SWT.CTRL;
        	TreeItem treeItem = tree.getItem(new Point(event.x, event.y));
		    if (treeItem != null) {    
		    	Rectangle rect = treeItem.getBounds();
		        if (event.x < rect.x && event.y < rect.y +16)
		        	checkItem(treeItem, controlKeyPressed);
		        else if (editTreeItem == treeItem)	{
			    	editTreeItem = null;
			    	editItem(treeItem);			            
		        }
		    }
        }
        else 
        	doubleClick = false;
    }
    
    //<CONSTRAINT RESOLUTION
    public void keyPressed(KeyEvent event)
    {    	
    	if ((event.stateMask & SWT.ALT) == SWT.ALT && event.keyCode == SWT.ARROW_DOWN)
    	{
    		// expand subtree
    		IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
    		Object element = selection.getFirstElement(); 
    		if (element != null)
    			treeViewer.expandToLevel(element, AbstractTreeViewer.ALL_LEVELS);
    	}
    	else 
    		super.keyPressed(event);
    }
    //>CONSTRAINT RESOLUTION
    
    /**
     * When double-click on the Clonable - clone it
     */
    public void mouseDoubleClick(MouseEvent event) {
        doubleClick = true;
    	TreeItem treeItem = tree.getItem(new Point(event.x, event.y));
	      if (treeItem != null) {
	          Rectangle rect = treeItem.getBounds();
	          if (event.x < rect.x && event.y < rect.y +16)
		          cloneItem(treeItem);
	      }
    }
    /**
     * Michal: this is used when swapping Editors for the Properties view
     */
    public void reconfigure(AdapterFactory af, EditingDomain ed) {
        adapterFactory = af;
        editingDomain = ed;
    }
}
