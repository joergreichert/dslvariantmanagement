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

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import ca.uwaterloo.gp.fmp.Clonable;
import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.Node;
import ca.uwaterloo.gp.fmp.constraints.ui.AddConstraintAction;
import ca.uwaterloo.gp.fmp.constraints.ui.ConstraintLabelProvider;
import ca.uwaterloo.gp.fmp.constraints.ui.ConstraintTableEditor;
import ca.uwaterloo.gp.fmp.constraints.ui.ConstraintsList;
import ca.uwaterloo.gp.fmp.constraints.ui.ConstraintsViewDropAdapter;
import ca.uwaterloo.gp.fmp.constraints.ui.RemoveConstraintAction;
import ca.uwaterloo.gp.fmp.system.ModelNavigation;
import ca.uwaterloo.gp.fmp.system.RoleQuery;


/**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>
 * 
 * Constraints panel
 */
public class ConstraintsView extends ViewPart implements ISelectionListener, IMenuListener, IPartListener {
    /**
     * The view that displays constraints
     */
    public static final int EDIT_MODE = 0;
    
    public static final int EVALUATION_MODE = 1;

    public static final String CONSTRAINT_COLUMN_HEADER = "Constraint";

    public static final String VALUE_COLUMN_HEADER = "Value";

    public static final String MESSAGE_COLUMN_HEADER = "Message";

    public static final String ICON = "";
    
    protected static final String constraintsColumnNames[] = new String[] {
            ICON, CONSTRAINT_COLUMN_HEADER, VALUE_COLUMN_HEADER,
            MESSAGE_COLUMN_HEADER };

    protected FmpEditor editor;

    /**
     * Controller object that manages interaction between view and model
     */
    protected ConstraintsList constraintsList;

    /**
     * TreeViewer
     */
    protected TableViewer constraintsTableViewer;

    protected ConstraintLabelProvider constraintLabelProvider;

    /**
     * add drag & drop support
     */
    protected int dndOperations = DND.DROP_MOVE;

    protected Transfer[] transfers = new Transfer[] { LocalTransfer
            .getInstance() };

    /**
     * For linking/unlinking
     */
    protected Action toggleLinkingAction;

    /**
     * Flag indicating whether or not the view is linked to the main tree
     * viewers
     */
    protected boolean linked = true;

    public ConstraintsView() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    public void createPartControl(Composite parent) {
        // Layout
        createTable(parent);

        // add "link" button
        addLinkButton();
        
        // Michal: register with selection and part services
        getSite().getPage().addSelectionListener(this);
        getSite().getPage().addPartListener(this);
    }
    
    protected void addLinkButton() {
        toggleLinkingAction = new Action("Link with Editor", IAction.AS_CHECK_BOX) {
            public void run() {
                linked = !linked;
            }
        };
        toggleLinkingAction.setChecked(linked);
        toggleLinkingAction.setToolTipText("Link with Editor");
        toggleLinkingAction.setImageDescriptor(getImageDescriptor("elcl16/synced.gif"));
        IActionBars bars = getViewSite().getActionBars();
        bars.getToolBarManager().add(toggleLinkingAction);
    }

    /**
     * Taken from MetaConfigurationView. Returns the image descriptor with the
     * given relative path.
     */
    protected ImageDescriptor getImageDescriptor(String relativePath) {
    	return AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.ui", "icons/full/" + relativePath);
    }

 
    protected void createTable(Composite parent) {

        Table constraintsTable = new Table(parent, SWT.SINGLE);
        GridData tableGridData = new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL);
        tableGridData.grabExcessHorizontalSpace = true;
        tableGridData.grabExcessVerticalSpace = true;
        constraintsTable.setLayoutData(tableGridData);
        constraintsTable.setHeaderVisible(true);
        constraintsTable.setLinesVisible(true);

        TableColumn iconColumn = new TableColumn(constraintsTable,
                SWT.LEFT);
        iconColumn.setWidth(18);
        iconColumn.setText(constraintsColumnNames[0]);

        TableColumn constraintColumn = new TableColumn(constraintsTable,
                SWT.LEFT);
        constraintColumn.setWidth(400);
        constraintColumn.setText(constraintsColumnNames[1]);

        TableColumn constraintValueColumn = new TableColumn(constraintsTable,
                SWT.LEFT);
        constraintValueColumn.setWidth(100);
        constraintValueColumn.setText(constraintsColumnNames[2]);

        TableColumn constraintMessageColumn = new TableColumn(constraintsTable,
                SWT.LEFT);
        constraintMessageColumn.setWidth(1500);
        constraintMessageColumn.setText(constraintsColumnNames[3]);

        constraintsTableViewer = new TableViewer(constraintsTable);
        constraintsTableViewer.setUseHashlookup(true);
        constraintsTableViewer.setColumnProperties(constraintsColumnNames);
        
/*	    // Create the cell editors
        CellEditor[] cellEditors = new CellEditor[constraintsColumnNames.length];
        for(int i = 1; i <= 1; i++)
        {
        	cellEditors[i] = new ConstraintTableEditor(constraintsTable);
        }
        
        // Assign the cell editors to the viewer 
        constraintsTableViewer.setCellEditors(cellEditors);
*/
        TableEditor tableEditor = new ConstraintTableEditor(constraintsTableViewer);
        

        // Set the cell modifier for the viewer
/*        constraintCellModifier = new ConstraintCellModifier(constraintList);
        constraintsTableViewer.setCellModifier(constraintCellModifier);
*/
       // set content and label provider
/*        constraintsTableViewer.setContentProvider(new ConstraintContentProvider(
                        constraintList, constraintsTableViewer));
                        
 */       
        constraintsList = new ConstraintsList();
        constraintLabelProvider = new ConstraintLabelProvider(constraintsList);
        constraintsTableViewer.setLabelProvider(constraintLabelProvider);
        constraintsList.addChangeListener(constraintsTableViewer);
        

/*        // The input for the table viewer is the current ConstraintList
        ConstraintsTableContentProvider contentProvider = new ConstraintsTableContentProvider(
                constraintsTableViewer);
        constraintsTableViewer.setContentProvider(contentProvider);
        constraintsTableViewer.setInput(constraintsList);
*/
        //add drag support
        constraintsTableViewer.addDragSupport(dndOperations, transfers,
                new ViewerDragAdapter(constraintsTableViewer));

        // Create the tree viewer and editor
/*        treeViewer = new TreeViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL
                | SWT.PUSH);
        NodeTreeContentProvider contentProvider = new NodeTreeContentProvider(
                treeViewer);
        treeViewer.setContentProvider(contentProvider);

        constraintLabelProvider = new ConstraintLabelProvider();
        treeViewer.setLabelProvider(constraintLabelProvider);
        TreeEditor treeEditor = new ConstraintTreeEditor(treeViewer);

        // Create the root node that will hold the constraints
*/

        // for displaying colours (hyperlinks)
//        treeColorDisplaySelectionListener = new TreeColorDisplaySelectionChangedListener();
//        treeViewer.addSelectionChangedListener(treeColorDisplaySelectionListener);
    }

    protected MenuManager menuManager = null;
    public void createPopUpMenu(CommandStack commandStack){
        if (commandStack == null)
        	return;
    	// Menu on the tree
        // Top-level Menu
        menuManager = new MenuManager("#Constraints");
        menuManager.setRemoveAllWhenShown(false);
        menuManager.addMenuListener(this);
        menuManager.add(new AddConstraintAction(constraintsList,commandStack));
        menuManager.add(new RemoveConstraintAction(constraintsList, constraintsTableViewer, commandStack));
        // Create a new context menu - difference between this menu and
        // MenuManager?
        Menu menu = menuManager.createContextMenu(constraintsTableViewer.getTable());
        constraintsTableViewer.getTable().setMenu(menu);
    }
	private CommandStack commandStack;
    
    /**
     * This creates a context menu for the viewer and adds a listener as well
     * registering the menu for extension. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * copied off and changed off of FmpEditor.createContextMenu
     * 
     * protected void createContextMenuFor(TextCellEditor textCellEditor) { //
     * Top-level Menu MenuManager menuManager = new MenuManager("#PopUp");
     * menuManager.setRemoveAllWhenShown(false);
     * menuManager.addMenuListener(this);
     *  // Create a new context menu - difference between this menu and
     * MenuManager? Menu menu =
     * menuManager.createContextMenu(textCellEditor.getControl());
     * textCellEditor.getControl().setMenu(menu);
     *  // TODO: Move the old menu items to the new context menu
     *  // This is the new menu item to be added - with individual templates
     * MenuManager subMenuManager = new MenuManager("Insert template");
     * subMenuManager.add(new InsertConstraintTemplateAction(textCellEditor,
     * "every $x in...", "every $x in {SCOPE} satisfies {EXPRESSION}"));
     * subMenuManager.add(new InsertConstraintTemplateAction(textCellEditor,
     * "some $x in...", "some $x in {SCOPE} satisfies {EXPRESSION}"));
     * 
     * menuManager.add(subMenuManager); }
     */

    public void setFocus() {
    	constraintsTableViewer.getControl().setFocus();
    }
    /**
	 * @param activeEditor
	 */
	private void setInputFromEditorSelection(ISelection selection) {
		if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
			Object input = ((IStructuredSelection) selection).getFirstElement();

            int chosenMode = EDIT_MODE;
            if (RoleQuery.INSTANCE.getNodeType((Node)input) == RoleQuery.CONFIGURATION) {
                chosenMode = EVALUATION_MODE;
            } else {
                chosenMode = EDIT_MODE;
            }

            // Start edit mode for the selected feature - if possible
            if (input instanceof Clonable)
                setConstraintsForNode((Clonable)input, chosenMode);
            else {
                setConstraintsForRootNode(null, chosenMode);
                constraintsTableViewer.refresh();
            }
		}
	}
    /* (non-Javadoc)
	 * @see org.eclipse.ui.ISelectionListener#selectionChanged(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (!linked)
			return;
		if (!(part instanceof FmpEditor))
			return;
		if (constraintsTableViewer != null && !constraintsTableViewer.getTable().isDisposed()) {
            FmpEditor newEditor = (FmpEditor) part;
            // switch to different editor if needed
            if (newEditor != editor)
                registerWithEditor(newEditor);
            
            if (selection instanceof IStructuredSelection && ((IStructuredSelection) selection).size() == 1)
            	setInputFromEditorSelection(selection);
        }
    }

    /**
     * Sets the constraints to be evaluated/modified for the node
     * 
     * @return
     */
    protected void setConstraintsForNode(Clonable input, int mode) {
        // For a given node, show the constraints belonging to its model
        // root
        Feature rootFeature = ModelNavigation.INSTANCE.navigateToRootFeature(input);
        // could be clicking on a FeatureModel node, in which case
        // navigateToRootFeature returns null
        if (rootFeature != null)
        	setConstraintsForRootNode(rootFeature, mode);                
        else
        	// for the feature model clear the view
        	setConstraintsForRootNode(null, mode);
    }

    protected void setConstraintsForRootNode(Feature rootFeature, int mode)
	{
		if (rootFeature != null)
		{
	        constraintsList.setConstraints(rootFeature.getConstraints(), mode);
		}
		else
    		constraintsList.setConstraints(null, mode);	
	}
    
    /*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.IMenuListener#menuAboutToShow(org.eclipse.jface.action.IMenuManager)
	 *      part taken from FeatureMdlActionBarContributor.menuAboutToShow
	 */
	public void menuAboutToShow(IMenuManager manager) {
		// Menu on the tree
        // Top-level Menu
        menuManager = new MenuManager("#Constraints");
        menuManager.setRemoveAllWhenShown(false);
        menuManager.addMenuListener(this);
        menuManager.add(new AddConstraintAction(constraintsList, commandStack));
        menuManager.add(new RemoveConstraintAction(constraintsList, constraintsTableViewer, commandStack));
        // Create a new context menu - difference between this menu and
        // MenuManager?
        Menu menu = menuManager.createContextMenu(constraintsTableViewer.getTable());
        constraintsTableViewer.getTable().setMenu(menu);
	}
	
    public void dispose()
    {
	    constraintsTableViewer.getTable().dispose();
	    // Michal: deregister with the selection and part services
        getSite().getPage().removeSelectionListener(this);
        getSite().getPage().removePartListener(this);
	    super.dispose();
	}
    /**
	 * @param newEditor
	 */
	private void registerWithEditor(FmpEditor newEditor) {
		editor = newEditor;
		createPopUpMenu(newEditor.getEditingDomain().getCommandStack());
		
		// remove drop target from the control.
        DropTarget dropTarget = (DropTarget) constraintsTableViewer
                .getControl().getData("DropTarget");
        if (dropTarget != null)
            dropTarget.dispose();
        constraintsTableViewer.addDropSupport(dndOperations, transfers,
                new ConstraintsViewDropAdapter(constraintsTableViewer));
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
				constraintsList.setConstraints(null, EDIT_MODE);
				constraintsTableViewer.refresh();
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