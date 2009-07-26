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

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

import ca.uwaterloo.gp.fmp.Constraint;


/**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>
 *
 */
public class RemoveConstraintCommand extends CompoundCommand {

	ConstraintsList constraintsList = null;
	TableViewer tableViewer = null;
	
	public RemoveConstraintCommand(ConstraintsList constraintsList,TableViewer tableViewer) {
		super(0);
		this.constraintsList = constraintsList;
		this.tableViewer = tableViewer;
	}

	public void execute()
	{
	    super.execute();
		List constraints = constraintsList.getListOfConstraints();
		
		if(constraints != null)
		{
			ISelection selection = tableViewer.getSelection();
			// if the selection is valid
			if(selection != null && (selection instanceof IStructuredSelection) && ((IStructuredSelection)selection).size() > 0)
			{
				
				// List of constraints to be deleted
				Iterator iterator = ((IStructuredSelection)selection).iterator();
				while(iterator.hasNext())
				{
					Constraint constraint = (Constraint)iterator.next();
					// constraintsList.removeConstraint(constraint);
					//((InternalEList)((Feature)constraint.eContainer()).getConstraints()).basicRemove((InternalEObject)constraint, null);
					// this will purge all references
					EcoreUtil.remove(constraint);					
					tableViewer.refresh();
				}
			}
		}
	}

	public boolean canExecute() {
		return true;
	}
}
