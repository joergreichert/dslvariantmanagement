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

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.eclipse.jface.viewers.TableViewer;

import ca.uwaterloo.gp.fmp.Constraint;
import ca.uwaterloo.gp.fmp.FmpFactory;


/**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>
 *
 * Controller class that manages constraints being shown in the ConstraintsView tree.
 */
public class ConstraintsList
{
	/**
	 * Model: List of constraints being managed
	 */
	protected List listOfConstraints;
	protected Set changeListeners;
	
	protected int mode;
	
	/**
	 * Takes a TreeViewer that holds the root of all the constraints
	 * @param treeViewer
	 */
	public ConstraintsList()
	{
		changeListeners = new HashSet();
		listOfConstraints = new Vector();
	}
	
	/**
	 * Adds a new constraint to the constraints list associated with 
	 * the current feature and to the view.
	 */
	public void addConstraint()
	{
		if(listOfConstraints != null)
		{
			// make a new constraint
			Constraint constraint = FmpFactory.eINSTANCE.createConstraint();
			constraint.setText("Please specify a constraint here");
			listOfConstraints.add(constraint);
			
			addConstraintToView(constraint);
		}
	}
	
	/**
	 * Adds the specified constraint to the view
	 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>
	 */
	protected void addConstraintToView(Constraint constraint)
	{
		Iterator iterator = changeListeners.iterator();
		while (iterator.hasNext())
		{
			((TableViewer) iterator.next()).add(constraint);
		}
	}
	
	/**
	 * @return Returns the constraints.
	 */
	public List getListOfConstraints() {
		return listOfConstraints;
	}
	
	/**
	 * Removes the specified constraint
	 */
	public void removeConstraint(Constraint constraint)
	{
		// remove model first
		listOfConstraints.remove(constraint);
		removeConstraintFromView(constraint);
	}
	
	/**
	 * Removes the specified constraint from view
	 * @param constraint
	 */
	public void removeConstraintFromView(Constraint constraint)
	{
		/**
		 * TODO: CLEAN UP TreeDisplayInfo and DisplayInfo associated with constraints/messages
		 */
		Iterator iterator = changeListeners.iterator();
		while (iterator.hasNext())
		{
			((TableViewer) iterator.next()).remove(constraint);
		}		
	}
	
	/**
	 * @param constraints The constraints to set. Set it to null to remove the constraints.
	 */
	public void setConstraints(List constr, int mode) {
		this.mode = mode;
		
		// Remove current constraints
		if (listOfConstraints != null) {
            for (int i = 0; i < listOfConstraints.size(); i++) {
                removeConstraintFromView((Constraint) listOfConstraints.get(i));
            }
        }
		
		this.listOfConstraints = constr;
		
		if(listOfConstraints != null)
		{
			// go through the new constraints and add them one by one
			for(int i = 0; i < listOfConstraints.size(); i++)
			{
				addConstraintToView((Constraint)listOfConstraints.get(i));	
			}
		}
	}
	
	public void addChangeListener(TableViewer tableViewer)
	{
		changeListeners.add(tableViewer);
	}
	
	public void removeChangeListener(TableViewer tableViewer)
	{
		changeListeners.remove(tableViewer);
	}	
}
