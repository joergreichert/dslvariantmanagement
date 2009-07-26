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

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

/**
 * @author Michal Antkiewicz <mantkiew@swen.uwaterloo.ca>
 */
public class ToggleViewModeAction implements IEditorActionDelegate {
	FmpEditor editor;
	boolean enabled;
	
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if (targetEditor instanceof FmpEditor)
			editor = (FmpEditor) targetEditor; 
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		editor.setViewMode(1 - editor.getViewMode());
		if (editor.getViewMode() == 1)
			action.setToolTipText("Toggle Configuration View Mode");
		else 
			action.setToolTipText("Toggle Modeling View Mode");
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}
}
