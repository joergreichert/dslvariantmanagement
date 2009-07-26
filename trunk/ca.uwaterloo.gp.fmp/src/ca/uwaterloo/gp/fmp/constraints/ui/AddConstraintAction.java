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

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.jface.action.Action;

/**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>
 *
 * Action that adds a constraint
 */
public class AddConstraintAction extends Action {
	/**
	 * The root of the constraints being shown
	 */
	protected ConstraintsList constraintsTree;
	protected CommandStack commandStack;
	
	public AddConstraintAction(ConstraintsList constraintsTree, CommandStack commandStack)
	{
		setText("Add constraint");
		this.constraintsTree = constraintsTree;
		this.commandStack = commandStack;
	}
	
	public void run()
	{
	    Command cmd = new AddConstraintCommand(constraintsTree);
	    commandStack.execute(cmd);
	}	
}
