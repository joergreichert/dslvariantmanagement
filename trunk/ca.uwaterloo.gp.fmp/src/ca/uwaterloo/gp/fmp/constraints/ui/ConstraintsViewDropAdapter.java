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

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.TransferData;

import ca.uwaterloo.gp.fmp.Constraint;
import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.Node;


/**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>
 * Handles drag and drop of features onto ConstraintView.
 */
public class ConstraintsViewDropAdapter extends ViewerDropAdapter
{
	/**
	 * @param viewer
	 */
	public ConstraintsViewDropAdapter(Viewer viewer) {
		super(viewer);		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ViewerDropAdapter#performDrop(java.lang.Object)
	 */
	public boolean performDrop(Object data) {
		// TODO Auto-generated method stub
		StructuredSelection structuredSelection = (StructuredSelection)data;
		
		// dropping only 1 feature is allowed
		if(structuredSelection.size() == 1)
		{
			Object toBeDroppedObj = structuredSelection.getFirstElement();
			
			// check the type of the feature to be dropped
			if(toBeDroppedObj instanceof Feature && !(toBeDroppedObj instanceof Node)) //to be changed
			{
				Feature toBeDroppedFeature = (Feature)toBeDroppedObj;
				
				// Get the constraint to be dropped onto
				Object target = getCurrentTarget();
				
				// if it is confirmed that the feature will be dropped onto an existing constraint
				if(target != null && target instanceof Constraint && ((Constraint)target).getText() != null)
				{
					Constraint constraintToBeModified = (Constraint)target;
					String featureName = toBeDroppedFeature.getName() == null? "unnamed feature" : toBeDroppedFeature.getName();
					constraintToBeModified.setText(constraintToBeModified.getText() + featureName);
					
					// update the view
					((TreeViewer)getViewer()).update(target, null);
				}
			}
		}	
			
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ViewerDropAdapter#validateDrop(java.lang.Object, int, org.eclipse.swt.dnd.TransferData)
	 */
	public boolean validateDrop(Object target, int operation, TransferData transferType) {
		// TODO Auto-generated method stub
		return true;
	}
}