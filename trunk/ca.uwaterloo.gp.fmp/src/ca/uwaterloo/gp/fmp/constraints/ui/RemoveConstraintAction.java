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

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TableViewer;

/**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>
 *
 * Action that removes constraint.
 */
public class RemoveConstraintAction extends Action
{
	protected ConstraintsList constraintsList;
	protected TableViewer tableViewer;
	protected CommandStack commandStack;
	
	public RemoveConstraintAction(ConstraintsList constraintsList, TableViewer tableViewer, CommandStack commandStack)
	{
		setText("Remove constraint(s)");
		this.constraintsList = constraintsList;
		this.tableViewer = tableViewer;
		this.commandStack = commandStack;
	}
	
	public void run()
	{
	    commandStack.execute(new RemoveConstraintCommand(constraintsList,tableViewer));
	}
}
