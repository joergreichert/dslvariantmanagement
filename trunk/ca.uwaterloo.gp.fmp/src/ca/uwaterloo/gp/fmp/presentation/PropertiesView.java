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

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.dnd.EditingDomainViewerDropAdapter;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import ca.uwaterloo.gp.fmp.Clonable;
import ca.uwaterloo.gp.fmp.ConfigState;
import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.Node;
import ca.uwaterloo.gp.fmp.provider.fmpEditPlugin;
import ca.uwaterloo.gp.fmp.system.RoleQuery;


/**
 * @author Michal Antkiewicz <mantkiew@swen.uwaterloo.ca>
 */
public class PropertiesView extends ViewPart implements ISelectionListener, IPartListener {
	private FmpEditor editor;
	FmpAdapterFactoryTreeEditor treeEditor;
    private TreeViewer treeViewer;
	private Action toggleAnnotations;
	private Action toggleLinkingAction;
	private boolean selectionListenerActive = true;
	private boolean filter = true;
		
	// add drag & drop support
    int dndOperations = DND.DROP_MOVE;
    Transfer[] transfers = new Transfer[] { LocalTransfer.getInstance() };
	
	public PropertiesView() {
	    super();
	}
	public void createPartControl(Composite parent) {
		treeViewer = new TreeViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.PUSH);
	
		treeViewer.addFilter(
				new ViewerFilter() {
					public boolean select(Viewer viewer, Object parentElement, Object element) {
						// hide everyting that is not defined in the metamodel	                
						if (filter && RoleQuery.INSTANCE.getPropertyRole((Node) element) == RoleQuery.NONE)
							return false;
						// hide configurations
						if (!filter) {
							if (element instanceof Feature &&
								RoleQuery.INSTANCE.getNodeType((Node) element) == RoleQuery.ROOT_FEATURE)
								return false;
						}
						if (parentElement instanceof Clonable) {
							int state = ((Clonable) parentElement).getState().getValue();
							int min = ((Clonable) parentElement).getMin();
							if (min == 0 && 
								(state == ConfigState.UNDECIDED || 
								 state == ConfigState.MACHINE_ELIMINATED || 
								 state == ConfigState.USER_ELIMINATED))
								return false;
						}
						return true;
					}
				}
		);
		toggleAnnotations = new Action("Toggle Annotations", IAction.AS_CHECK_BOX) {
		    public void run() {
		        filter = !filter;
		        treeViewer.refresh();
            }
		};
		toggleAnnotations.setToolTipText("Shows/hides unused annotations");
		toggleAnnotations.setImageDescriptor(
		        ExtendedImageRegistry.getInstance().getImageDescriptor(
		                fmpEditPlugin.INSTANCE.getImage("full/ShowAnnotationsAction")));
		
		toggleLinkingAction = new Action("Link with Editor", IAction.AS_CHECK_BOX) {
		    public void run() {
		        selectionListenerActive = !selectionListenerActive;
            }
		};
		toggleLinkingAction.setChecked(selectionListenerActive);
		toggleLinkingAction.setToolTipText("Link with Editor");
		toggleLinkingAction.setImageDescriptor(
				AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.ui", "icons/full/elcl16/synced.gif"));
		getViewSite().getActionBars().getToolBarManager().add(toggleLinkingAction);
		
		getViewSite().getActionBars().getToolBarManager().add(toggleAnnotations);
		
		// add drag support
		treeViewer.addDragSupport(dndOperations, transfers, new ViewerDragAdapter(treeViewer));
		
		getSite().getPage().addPartListener(this);
		getSite().getPage().addSelectionListener(this);
		
		IEditorPart activeEditor = getSite().getPage().getActiveEditor(); 
		if (activeEditor instanceof FmpEditor) {
			registerWithEditor((FmpEditor) activeEditor);
			setInputFromEditorSelection(editor.getSelection());
		}
	}
	/**
	 * @param activeEditor
	 */
	private void setInputFromEditorSelection(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			Object object = ((IStructuredSelection) selection).getFirstElement();
			if (object instanceof Node)
				object = ((Node) object).getProperties();
			else
				object = null;
	        treeViewer.setInput(object);
	        treeViewer.expandAll();
		}
	}
	public void setFocus() {
		treeViewer.getControl().setFocus();
		if (editor != null) {
		    ((FmpActionBarContributor)editor.getActionBarContributor())
		    	.selectionChanged(new SelectionChangedEvent(treeViewer, treeViewer.getSelection()));
		}
	}
	public TreeViewer getTreeViewer() {
		return treeViewer;
	}
	public void dispose() {
	    treeViewer.getTree().dispose();
	    // remove as selection and part listener when closing the view
	    getSite().getPage().removeSelectionListener(this);
	    getSite().getPage().removePartListener(this);
	    super.dispose();
	}
	public void refresh() {
		treeViewer.refresh();
		expand(treeViewer.getInput());
	}
    private void expand(Object object) {
        if (object != null) {
        	treeViewer.expandToLevel(object, AbstractTreeViewer.ALL_LEVELS);
	        /*Feature config = (Feature) object;
	        for (Iterator i = config.getChildren().iterator(); i.hasNext(); ) {
		        Object aux = i.next();
		        if (aux instanceof SystemAnnotation) {
		            if (((SystemAnnotation) aux).getMetaRole() != MetaRole.NONE_LITERAL)
		                if (aux instanceof ConfNode && ((ConfNode) aux).isChecked())
		                    treeViewer.expandToLevel(aux, TreeViewer.ALL_LEVELS);
		                else if (!(aux instanceof ConfNode))
		                    treeViewer.expandToLevel(aux, TreeViewer.ALL_LEVELS);
		        }
		    }*/
        }
    }
	/* (non-Javadoc)
	 * @see org.eclipse.ui.ISelectionListener#selectionChanged(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (!selectionListenerActive)
			return;
		if (!(part instanceof FmpEditor))
			return;
		if (treeViewer != null && !treeViewer.getTree().isDisposed()) {
			FmpEditor newEditor = (FmpEditor) part;
			// switch to different editor if needed
	        if (newEditor != editor)
	            registerWithEditor(newEditor);            
		    if (selection instanceof IStructuredSelection && ((IStructuredSelection) selection).size() == 1)
		    	setInputFromEditorSelection(selection);
	    }
	}
	/**
	 * @param newEditor
	 */
	private void registerWithEditor(FmpEditor newEditor) {
		editor = newEditor;
		
		AdapterFactory adapterFactory = editor.getAdapterFactory();
		EditingDomain editingDomain = editor.getEditingDomain();
		treeViewer.setContentProvider(new AdapterFactoryContentProvider(adapterFactory));
		treeViewer.addSelectionChangedListener((ISelectionChangedListener) editor.getActionBarContributor());
		treeViewer.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));
		treeViewer.setInput(null);
		
		if (treeEditor == null)
		    // register tree editor with tree widget
		    treeEditor = new FmpAdapterFactoryTreeEditor(treeViewer.getTree(), adapterFactory, editingDomain, treeViewer);
		else
		    treeEditor.reconfigure(adapterFactory, editingDomain);
		
		// remove drop target from the control.
		DropTarget dropTarget = (DropTarget) treeViewer.getControl().getData("DropTarget");
		if (dropTarget != null)
		    dropTarget.dispose();
		treeViewer.addDropSupport(dndOperations, transfers, new EditingDomainViewerDropAdapter(editingDomain, treeViewer));
		
		// add pop-up menu support
		editor.createContextMenuFor(treeViewer);
	}
	public void partActivated(IWorkbenchPart part) {
		if (editor != part && part instanceof FmpEditor) {
			registerWithEditor((FmpEditor) part);
			setInputFromEditorSelection(editor.getSelection());
		}
	}
	public void partBroughtToTop(IWorkbenchPart part) {	
	}
	public void partClosed(IWorkbenchPart part) {
		if (part instanceof FmpEditor) {
			if(part == editor) {
				editor = null;
				treeViewer.setInput(null);
			}
			IEditorReference[] editorReferences = getSite().getPage().getEditorReferences();
			boolean found = false;
			for (int i =0; i < editorReferences.length; i++) {
				if (editorReferences[i].getEditor(false) instanceof FmpEditor) {
					found = true;
					break;
				}
			}
			if (!found)
				getSite().getPage().hideView(this);
			else {
				IEditorPart activeEditor = getSite().getPage().getActiveEditor();
				if (activeEditor instanceof FmpEditor) {
					registerWithEditor((FmpEditor) activeEditor);
					setInputFromEditorSelection(editor.getSelection());
				}
			}
		}
	}
	public void partDeactivated(IWorkbenchPart part) {
	}
	public void partOpened(IWorkbenchPart part) {
	}
}