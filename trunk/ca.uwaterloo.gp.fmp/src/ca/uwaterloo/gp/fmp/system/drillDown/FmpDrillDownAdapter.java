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
package ca.uwaterloo.gp.fmp.system.drillDown;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchImages;

import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.Node;
import ca.uwaterloo.gp.fmp.provider.nonmodel.ProxyItemProvider;
import ca.uwaterloo.gp.fmp.system.RoleQuery;

/**
 * @author Michal Antkiewicz <mantkiew@swen.uwaterloo.ca>
 */
public class FmpDrillDownAdapter implements ISelectionChangedListener {
    private TreeViewer fChildTree;
	private DrillStack fDrillStack;
	private Action homeAction;
	private Action backAction;
	private Action forwardAction;
	private AdapterFactory adapterFactory;
	/**
	 * Allocates a new DrillDownTreePart.
	 *
	 * @param tree the target tree for refocusing
	 */
	public FmpDrillDownAdapter(TreeViewer tree, AdapterFactory adapterFactory) {
		fDrillStack = new DrillStack();
		fChildTree = tree;
		// Home.	
		homeAction = new Action("Go Home") { //$NON-NLS-1$
			public void run() {
				goHome();
			}
		};
		homeAction.setToolTipText("Go Home"); //$NON-NLS-1$
		homeAction.setImageDescriptor(WorkbenchImages.getImageDescriptor(ISharedImages.IMG_ETOOL_HOME_NAV));
	
		// Back.
		ISharedImages images = PlatformUI.getWorkbench().getSharedImages();
		backAction = new Action("Go Back") { //$NON-NLS-1$
			public void run() {
				goBack();
			}
		};
		backAction.setToolTipText("Go Back"); //$NON-NLS-1$
		backAction.setImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_TOOL_BACK));
		backAction.setDisabledImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_TOOL_BACK_DISABLED));
	
		// Forward.
		forwardAction = new Action("Go Into") { //$NON-NLS-1$
			public void run() {
				goInto();
			}
		};
		forwardAction.setToolTipText("Go Into"); //$NON-NLS-1$
		forwardAction.setImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_TOOL_FORWARD));
		forwardAction.setDisabledImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_TOOL_FORWARD_DISABLED));

		// Update the buttons when a selection change occurs.
		fChildTree.addSelectionChangedListener(this);
	}
	/**
	 * Adds actions for "go back", "go home", and "go into" to a menu manager.
	 * @param manager is the target manager to update
	 */
	public void addNavigationActions(IMenuManager manager) {
		manager.insertAfter("additions", homeAction);
		manager.insertAfter("additions", backAction);
		manager.insertAfter("additions", forwardAction);
		updateNavigationButtons();
	}
	/**
	 * Returns whether expansion is possible for the current selection.  This
	 * will only be true if it has children.
	 *
	 * @param element the object to test for expansion
	 * @return <code>true</code> if expansion is possible; otherwise 
	 *		return <code>false</code
	 */
	public boolean canExpand(Object element) {
		return fChildTree.isExpandable(element);
	}
	/**
	 * Returns whether "go back" is possible for child tree.  This is only possible 
	 * if the client has performed one or more drilling operations.
	 *
	 * @return <code>true</code> if "go back" is possible; <code>false</code> otherwise
	 */
	public boolean canGoBack() {
		return fDrillStack.canGoBack();
	}
	/**
	 * Returns whether "go home" is possible for child tree.  This is only possible 
	 * if the client has performed one or more drilling operations.
	 *
	 * @return <code>true</code> if "go home" is possible; <code>false</code> otherwise
	 */
	public boolean canGoHome() {
		return fDrillStack.canGoHome();
	}
	/**
	 * Returns whether "go into" is possible for child tree.  This is only possible 
	 * if the current selection in the client has one item and it has children.
	 *
	 * @return <code>true</code> if "go into" is possible; <code>false</code> otherwise
	 */
	public boolean canGoInto() {
		IStructuredSelection oSelection = (IStructuredSelection) fChildTree.getSelection();
		if (oSelection == null || oSelection.size() != 1)
			return false;
		Object anElement = oSelection.getFirstElement();
		return (RoleQuery.INSTANCE.getNodeType((Node) anElement) == RoleQuery.ROOT_FEATURE);
	}
	/**
	 * Expands the given items in the tree.  The list of items passed should be
	 * derived by calling <code>getExpanded</code>.
	 *
	 * @param items is a list of items within the tree which should be expanded
	 */
	private void expand(List items) { 
		fChildTree.setExpandedElements(items.toArray()); 
	}
	/**
	 * Returns a list of elements corresponding to expanded nodes in 
	 * child tree.
	 *
	 * @return a list of expandd elements
	 */
	private List getExpanded() {
		return Arrays.asList(fChildTree.getExpandedElements());
	}
	/**
	 * Reverts the input for the tree back to the state when <code>goInto</code>
	 * was last called.
	 * <p>
	 * A frame is removed from the drill stack.  Then that frame is used to reset the
	 * input and expansion state for the child tree.
	 * </p>
	 */
	public void goBack() {
		Object currentInput = fChildTree.getInput();
		DrillFrame oFrame = fDrillStack.goBack();
		Object input = oFrame.getElement();
		fChildTree.setInput(input);
		expand(oFrame.getExpansion());
		// if there was a selection, it should have been preserved,
		// but if not, select the element that was drilled into
		if (fChildTree.getSelection().isEmpty())
			fChildTree.setSelection(new StructuredSelection(currentInput), true);
		updateNavigationButtons();
	}
	/**
	 * Reverts the input for the tree back to the state when the adapter was
	 * created.
	 * <p>
	 * All of the frames are removed from the drill stack.  Then the oldest frame is 
	 * used to reset the input and expansion state for the child tree.
	 * </p>
	 */
	public void goHome() {
		Object currentInput = fChildTree.getInput();
		DrillFrame oFrame = fDrillStack.goHome();
		Object input = oFrame.getElement();
		fChildTree.setInput(input);
		expand(oFrame.getExpansion());
		// if there was a selection, it should have been preserved,
		// but if not, select the element that was last drilled into
		if (fChildTree.getSelection().isEmpty())
			fChildTree.setSelection(new StructuredSelection(currentInput), true);
		updateNavigationButtons();
	}
	/**
	 * Sets the input for the tree to the current selection.
	 * <p>
	 * The current input and expansion state are saved in a frame and added to the 
	 * drill stack.  Then the input for the tree is changed to be the current selection.  
	 * The expansion state for the tree is maintained during the operation.
	 * </p><p>
	 * On return the client may revert back to the previous state by invoking 
	 * <code>goBack</code> or <code>goHome</code>.
	 * </p>
	 */
	public void goInto() {
		IStructuredSelection sel = (IStructuredSelection) fChildTree.getSelection();
		Object element = sel.getFirstElement();
		if (RoleQuery.INSTANCE.getNodeType((Node) element) == RoleQuery.ROOT_FEATURE)  {
		    Feature selected = (Feature) element;
		    
			ProxyItemProvider root = new ProxyItemProvider(adapterFactory, selected.eContainer(), selected);
		    goInto(root);
		} 
	}
	/**
	 * Sets the input for the tree to a particular item in the tree.
	 * <p>
	 * The current input and expansion state are saved in a frame and added to the 
	 * drill stack.  Then the input for the tree is changed to be <code>newInput</code>.  
	 * The expansion state for the tree is maintained during the operation.
	 * </p><p>
	 * On return the client may revert back to the previous state by invoking 
	 * <code>goBack</code> or <code>goHome</code>.
	 * </p>
	 *
	 * @param newInput the new input element
	 */
	public void goInto(Object newInput) {
		
		// Save the old state.
		Object oldInput = fChildTree.getInput();
		List expandedList = getExpanded();
		fDrillStack.add(new DrillFrame(oldInput, "null", expandedList));//$NON-NLS-1$
		
		// Install the new state.
		fChildTree.setInput(newInput);
		expand(expandedList);
		updateNavigationButtons();
	}
	/**
	 * Resets the drill down adapter. 
	 * <p>
	 * This method is typically called when the input for the underlying view
	 * is reset by something other than the adapter.
	 * On return the drill stack has been cleared and the navigation buttons
	 * reflect the new state of the underlying viewer.
	 * </p>
	 */
	public void reset() {
		fDrillStack.reset();
		updateNavigationButtons();
	}
	/**
	 * Updates the navigation buttons when a selection change occurs
	 * in the tree.
	 */
	public void selectionChanged(SelectionChangedEvent event) {
		updateNavigationButtons();
	}
	/**
	 * Updates the enabled state for each navigation button.  
	 */
	protected void updateNavigationButtons() {
		if (homeAction != null) {
			homeAction.setEnabled(canGoHome());
			backAction.setEnabled(canGoBack());
			forwardAction.setEnabled(canGoInto());
		}
	}
}