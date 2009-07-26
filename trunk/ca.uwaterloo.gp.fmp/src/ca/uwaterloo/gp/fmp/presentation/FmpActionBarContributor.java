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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.ui.action.CreateChildAction;
import org.eclipse.emf.edit.ui.action.CreateSiblingAction;
import org.eclipse.emf.edit.ui.action.DeleteAction;
import org.eclipse.emf.edit.ui.action.EditingDomainActionBarContributor;
import org.eclipse.emf.edit.ui.action.LoadResourceAction;
import org.eclipse.emf.edit.ui.action.ValidateAction;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.SubContributionItem;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.Node;
import ca.uwaterloo.gp.fmp.Project;
import ca.uwaterloo.gp.fmp.Reference;
import ca.uwaterloo.gp.fmp.provider.fmpEditPlugin;
import ca.uwaterloo.gp.fmp.provider.action.MakeMandatoryAction;
import ca.uwaterloo.gp.fmp.provider.action.MakeOptionalAction;
import ca.uwaterloo.gp.fmp.provider.action.SynchronizeAction;
import ca.uwaterloo.gp.fmp.provider.action.UnfoldReferenceAction;
import ca.uwaterloo.gp.fmp.provider.action.XMLExport;
import ca.uwaterloo.gp.fmp.provider.command.RemoveWithPropertiesCommand;
import ca.uwaterloo.gp.fmp.system.RoleQuery;


/**
 * This is the action bar contributor for the Fmp model editor.
 * @author Michal Antkiewicz <mantkiew@swen.uwaterloo.ca>
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class FmpActionBarContributor
	extends EditingDomainActionBarContributor
	implements ISelectionChangedListener {
	/**
	 * This keeps track of the active editor.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IEditorPart activeEditorPart;

	/**
	 * This keeps track of the current selection provider.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ISelectionProvider selectionProvider;

	/**
	 * This action opens the Properties view.
	 * <!-- begin-user-doc -->
	 * Michal: show our properties view instead
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected IAction showPropertiesViewAction =
		new Action(fmpEditPlugin.INSTANCE.getString("_UI_ShowPropertiesView_menu_item")) {
			public void run() {
				try {
					getPage().showView("ca.uwaterloo.gp.fmp.PropertiesView");
				}
				catch (PartInitException exception) {
					fmpEditPlugin.INSTANCE.log(exception);
				}
			}
		};
	/**
	 * This action opens the Constraints view.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected IAction showConstraintsViewAction =
		new Action(fmpEditPlugin.INSTANCE.getString("_UI_ShowConstraintsView_menu_item")) {
			public void run() {
				try {
					getPage().showView("ca.uwaterloo.gp.fmp.ConstraintsView");
				}
				catch (PartInitException exception) {
					fmpEditPlugin.INSTANCE.log(exception);
				}
			}
		};
	/**
	 * This action refreshes the viewer of the current editor if the editor
	 * implements {@link org.eclipse.emf.common.ui.viewer.IViewerProvider}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IAction refreshViewerAction =
		new Action(fmpEditPlugin.INSTANCE.getString("_UI_RefreshViewer_menu_item")) {
			public boolean isEnabled() {
				return activeEditorPart instanceof IViewerProvider;
			}

			public void run() {
				if (activeEditorPart instanceof IViewerProvider) {
					Viewer viewer = ((IViewerProvider)activeEditorPart).getViewer();
					if (viewer != null) {
						viewer.refresh();
					}
				}
			}
		};
	XMLExport xmlExportAction;
	/**
	 * This will contain one {@link org.eclipse.emf.edit.ui.action.CreateChildAction} corresponding to each descriptor
	 * generated for the current selection by the item provider.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection createChildActions;

	/**
	 * This is the menu manager into which menu contribution items should be added for CreateChild actions.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IMenuManager createChildMenuManager;

	/**
	 * This will contain one {@link org.eclipse.emf.edit.ui.action.CreateSiblingAction} corresponding to each descriptor
	 * generated for the current selection by the item provider.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection createSiblingActions;

	/**
	 * This is the menu manager into which menu contribution items should be added for CreateSibling actions.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IMenuManager createSiblingMenuManager;
	
	/**
	 * This is the menu manager into which menu contribution items should be added for CreateSibling actions.
	 * <!-- begin-user-doc -->
	 * Michal: this is used to store our custom actions
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected Collection additionalActions;

	/**
	 * This creates an instance of the contributor.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FmpActionBarContributor() {
		loadResourceAction = new LoadResourceAction();
		validateAction = new ValidateAction();
	}

	/**
	 * Michal: we need to use our own DeleteAction
	 * @generated NOT
	 */
	public void init(IActionBars actionBars) {
		super.init(actionBars);
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		// Michal: this should use a special remove command that also removes properties of removed elements 
		deleteAction = new DeleteAction() {
			public Command createCommand(Collection selection) {
			    return new RemoveWithPropertiesCommand(domain, selection);
			}
		};
		deleteAction.setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
	    actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(), deleteAction);
	}

	/**
	 * This adds Separators for editor additions to the tool bar.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void contributeToToolBar(IToolBarManager toolBarManager) {
		toolBarManager.add(new Separator("fmp-settings"));
		toolBarManager.add(new Separator("fmp-additions"));
	}

	/**
	 * This adds to the menu bar a menu and some separators for editor additions,
	 * as well as the sub-menus for object creation items.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 * Michal: we don't need a menu at all.
	 */
	public void contributeToMenu(IMenuManager menuManager) {
		/*super.contributeToMenu(menuManager);

		IMenuManager submenuManager = new MenuManager(fmpEditPlugin.INSTANCE.getString("_UI_FmpEditor_menu"), "fmpMenuID");
		menuManager.insertAfter("additions", submenuManager);
		submenuManager.add(new Separator("settings"));
		submenuManager.add(new Separator("actions"));
		submenuManager.add(new Separator("additions"));
		submenuManager.add(new Separator("additions-end"));

		// Prepare for CreateChild item addition or removal.
		//
		createChildMenuManager = new MenuManager(fmpEditPlugin.INSTANCE.getString("_UI_CreateChild_menu_item"));
		submenuManager.insertBefore("additions", createChildMenuManager);

		// Prepare for CreateSibling item addition or removal.
		//
		createSiblingMenuManager = new MenuManager(fmpEditPlugin.INSTANCE.getString("_UI_CreateSibling_menu_item"));
		submenuManager.insertBefore("additions", createSiblingMenuManager);

		// Force an update because Eclipse hides empty menus now.
		//
		submenuManager.addMenuListener
			(new IMenuListener() {
				 public void menuAboutToShow(IMenuManager menuManager) {
					 menuManager.updateAll(true);
				 }
			 });

		addGlobalActions(submenuManager);*/
	}

	/**
	 * When the active editor changes, this remembers the change and registers with it as a selection provider.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActiveEditor(IEditorPart part) {
		super.setActiveEditor(part);
		activeEditorPart = part;

		// Switch to the new selection provider.
		//
		if (selectionProvider != null) {
			selectionProvider.removeSelectionChangedListener(this);
		}
		if (part == null) {
			selectionProvider = null;
		}
		else {
			selectionProvider = part.getSite().getSelectionProvider();
			selectionProvider.addSelectionChangedListener(this);

			// Fake a selection changed event to update the menus.
			//
			if (selectionProvider.getSelection() != null) {
				selectionChanged(new SelectionChangedEvent(selectionProvider, selectionProvider.getSelection()));
			}
		}
	}

	/**
	 * This implements {@link org.eclipse.jface.viewers.ISelectionChangedListener},
	 * handling {@link org.eclipse.jface.viewers.SelectionChangedEvents} by querying for the children and siblings
	 * that can be added to the selected object and updating the menus accordingly.
	 * <!-- begin-user-doc -->
	 * Michal: add our additional actions
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void selectionChanged(SelectionChangedEvent event) {
		selectionChangedGen(event);
		ISelection selection = event.getSelection();
		if (selection instanceof IStructuredSelection && ((IStructuredSelection)selection).size() == 1) {
			Object object = ((IStructuredSelection)selection).getFirstElement();
			additionalActions = new ArrayList();
			// add XMLExport
			if (object instanceof Node)
				xmlExportAction = new XMLExport((Node) object, activeEditorPart.getSite().getShell());
			
			if (object instanceof Reference) {
				UnfoldReferenceAction action = new UnfoldReferenceAction();
				action.setActiveEditor(getActiveEditor());
				action.updateSelection((IStructuredSelection)selection);
				additionalActions.add(action);
			}	
			else if(object instanceof Feature) {
				Feature feature = (Feature) object;
				if (RoleQuery.INSTANCE.getNodeType(feature) == RoleQuery.ROOT_FEATURE) {
					SynchronizeAction action = new SynchronizeAction();
					action.setActiveEditor(getActiveEditor());
					action.updateSelection((IStructuredSelection)selection);
					additionalActions.add(action);
				}
				if(!feature.getConfigurations().isEmpty()) {
					final Object root = object;
					Action action = new Action("Feature Comparison") {
						public void run() {
							// get feature comparison view
							try {
					            IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					            if (page != null) {
					            	FeatureComparisonView view = (FeatureComparisonView) page.showView("ca.uwaterloo.gp.fmp.views.FeatureComparisonView");
					                view.setInput(root);
					            }
					        } catch (PartInitException exception) {
					            fmpEditPlugin.INSTANCE.log(exception);
					        }
						}
					};
					additionalActions.add(action);
				}
				if (!(feature.eContainer() instanceof Project) &&
					RoleQuery.INSTANCE.getNodeType(feature) != RoleQuery.ROOT_FEATURE) {
					// Michal: make mandatory and make optional convenience actions
					int min = feature.getMin();
					int max = feature.getMax();
					if (min != 1 || max != 1) {
					    MakeMandatoryAction action1 = new MakeMandatoryAction();
						action1.setActiveEditor(getActiveEditor());
						action1.updateSelection((IStructuredSelection)selection);
						additionalActions.add(action1);
					}
					if (min != 0 || max != 1) {
						MakeOptionalAction action2 = new MakeOptionalAction();
						action2.setActiveEditor(getActiveEditor());
						action2.updateSelection((IStructuredSelection)selection);
						additionalActions.add(action2);
					}
				}
			}
			// debug code
			/*if (object instanceof Node) {
				Node node = (Node) object;
				int location = RoleQuery.INSTANCE.getLocationType(node);
				System.out.println(location + " " + RoleQuery.INSTANCE.getNodeType(node)  + " ");
				if (location == RoleQuery.MODEL_PROPERTIES ||
						location == RoleQuery.METAMODEL_PROPERTIES || 
						location == RoleQuery.CONFIGURATION_PROPERTIES)
					System.out.println("PropertyRole: " + RoleQuery.INSTANCE.getPropertyRole(node));
			}*/
		}
	}
	/**
	 * This implements {@link org.eclipse.jface.viewers.ISelectionChangedListener},
	 * handling {@link org.eclipse.jface.viewers.SelectionChangedEvents} by querying for the children and siblings
	 * that can be added to the selected object and updating the menus accordingly.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void selectionChangedGen(SelectionChangedEvent event) {
		// Remove any menu items for old selection.
		//
		if (createChildMenuManager != null) {
			depopulateManager(createChildMenuManager, createChildActions);
		}
		if (createSiblingMenuManager != null) {
			depopulateManager(createSiblingMenuManager, createSiblingActions);
		}

		// Query the new selection for appropriate new child/sibling descriptors
		//
		Collection newChildDescriptors = null;
		Collection newSiblingDescriptors = null;

		ISelection selection = event.getSelection();
		if (selection instanceof IStructuredSelection && ((IStructuredSelection)selection).size() == 1) {
			Object object = ((IStructuredSelection)selection).getFirstElement();

			EditingDomain domain = ((IEditingDomainProvider)activeEditorPart).getEditingDomain();

			newChildDescriptors = domain.getNewChildDescriptors(object, null);
			newSiblingDescriptors = domain.getNewChildDescriptors(null, object);
		}

		// Generate actions for selection; populate and redraw the menus.
		//
		createChildActions = generateCreateChildActions(newChildDescriptors, selection);
		createSiblingActions = generateCreateSiblingActions(newSiblingDescriptors, selection);

		if (createChildMenuManager != null) {
			populateManager(createChildMenuManager, createChildActions, null);
			createChildMenuManager.update(true);
		}
		if (createSiblingMenuManager != null) {
			populateManager(createSiblingMenuManager, createSiblingActions, null);
			createSiblingMenuManager.update(true);
		}
	}

	/**
	 * This generates a {@link org.eclipse.emf.edit.ui.action.CreateChildAction} for each object in <code>descriptors</code>,
	 * and returns the collection of these actions.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection generateCreateChildActions(Collection descriptors, ISelection selection) {
		Collection actions = new ArrayList();
		if (descriptors != null) {
			for (Iterator i = descriptors.iterator(); i.hasNext(); ) {
				actions.add(new CreateChildAction(activeEditorPart, selection, i.next()));
			}
		}
		return actions;
	}

	/**
	 * This generates a {@link org.eclipse.emf.edit.ui.action.CreateSiblingAction} for each object in <code>descriptors</code>,
	 * and returns the collection of these actions.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection generateCreateSiblingActions(Collection descriptors, ISelection selection) {
		Collection actions = new ArrayList();
		if (descriptors != null) {
			for (Iterator i = descriptors.iterator(); i.hasNext(); ) {
				actions.add(new CreateSiblingAction(activeEditorPart, selection, i.next()));
			}
		}
		return actions;
	}

	/**
	 * This populates the specified <code>manager</code> with {@link org.eclipse.jface.action.ActionContributionItem}s
	 * based on the {@link org.eclipse.jface.action.IAction}s contained in the <code>actions</code> collection,
	 * by inserting them before the specified contribution item <code>contributionID</code>.
	 * If <code>ID</code> is <code>null</code>, they are simply added.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void populateManager(IContributionManager manager, Collection actions, String contributionID) {
		if (actions != null) {
			for (Iterator i = actions.iterator(); i.hasNext(); ) {
				IAction action = (IAction)i.next();
				if (contributionID != null) {
					manager.insertBefore(contributionID, action);
				}
				else {
					manager.add(action);
				}
			}
		}
	}
		
	/**
	 * This removes from the specified <code>manager</code> all {@link org.eclipse.jface.action.ActionContributionItem}s
	 * based on the {@link org.eclipse.jface.action.IAction}s contained in the <code>actions</code> collection.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void depopulateManager(IContributionManager manager, Collection actions) {
		if (actions != null) {
			IContributionItem[] items = manager.getItems();
			for (int i = 0; i < items.length; i++) {
				// Look into SubContributionItems
				//
				IContributionItem contributionItem = items[i];
				while (contributionItem instanceof SubContributionItem) {
					contributionItem = ((SubContributionItem)contributionItem).getInnerItem();
				}

				// Delete the ActionContributionItems with matching action.
				//
				if (contributionItem instanceof ActionContributionItem) {
					IAction action = ((ActionContributionItem)contributionItem).getAction();
					if (actions.contains(action)) {
						manager.remove(contributionItem);
					}
				}
			}
		}
	}

	/**
	 * This populates the pop-up menu before it appears.
	 * <!-- begin-user-doc -->
	 * Michal: get rid of create child menu.
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void menuAboutToShow(IMenuManager menuManager) {
	    super.menuAboutToShow(menuManager);
		MenuManager submenuManager = null;

		// submenuManager = new MenuManager(fmpEditPlugin.INSTANCE.getString("_UI_CreateChild_menu_item"));
		// populateManager(submenuManager, createChildActions, null);
		//menuManager.insertBefore("additions", submenuManager);
		populateManager(menuManager, createChildActions, "additions");
		
		submenuManager = new MenuManager(fmpEditPlugin.INSTANCE.getString("_UI_CreateSibling_menu_item"));
		populateManager(submenuManager, createSiblingActions, null);
		menuManager.insertBefore("additions", submenuManager);
		
		populateManager(menuManager, additionalActions, "additions");
	}

	/**
	 * This inserts global actions before the "additions-end" separator.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected void addGlobalActions(IMenuManager menuManager) {
		menuManager.insertAfter("additions-end", new Separator("ui-actions"));
		menuManager.insertAfter("ui-actions", xmlExportAction);
		menuManager.insertAfter("ui-actions", showConstraintsViewAction);
		menuManager.insertAfter("ui-actions", showPropertiesViewAction);
		
		refreshViewerAction.setEnabled(refreshViewerAction.isEnabled());		
		menuManager.insertAfter("ui-actions", refreshViewerAction);

		super.addGlobalActions(menuManager);
	}

}
