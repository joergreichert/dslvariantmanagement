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

import org.eclipse.emf.common.command.CompoundCommand;


/**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>
 *
 */
public class AddConstraintCommand extends CompoundCommand {
	ConstraintsList constraintsTree = null;
	
	public AddConstraintCommand(ConstraintsList constraintsTree) {
		super(0);
		this.constraintsTree = constraintsTree;
	}

	public void execute() {
	    super.execute();
		constraintsTree.addConstraint();
	}

	
	public boolean canExecute() {
		return true;
	}


}
