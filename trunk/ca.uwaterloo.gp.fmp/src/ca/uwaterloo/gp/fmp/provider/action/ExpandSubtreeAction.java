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
package ca.uwaterloo.gp.fmp.provider.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.TreeViewer;

import ca.uwaterloo.gp.fmp.presentation.FmpEditor;


/**
 * @author Michal Antkiewicz <mantkiew@swen.uwaterloo.ca>
 * This expands a subtree of any element in any view (editor or properties)
 */
public class ExpandSubtreeAction extends Action {
	ISelectionProvider selectionProvider;
	Object object;
	
	public ExpandSubtreeAction(Object object, ISelectionProvider selectionProvider) {
		super("Expand Subtree");
		this.selectionProvider = selectionProvider;
		this.object  = object;
	}
	public void run() {
		if (selectionProvider instanceof FmpEditor) {
			FmpEditor editor = (FmpEditor) selectionProvider;
	        TreeViewer viewer = ((TreeViewer) editor.getViewer());
		    if (viewer != null)
		    	viewer.expandToLevel(object, AbstractTreeViewer.ALL_LEVELS);
		}
		else if (selectionProvider instanceof TreeViewer) {
			TreeViewer viewer = (TreeViewer) selectionProvider;
			if (viewer != null)
		    	viewer.expandToLevel(object, AbstractTreeViewer.ALL_LEVELS);
		}
        
    }
	
}
