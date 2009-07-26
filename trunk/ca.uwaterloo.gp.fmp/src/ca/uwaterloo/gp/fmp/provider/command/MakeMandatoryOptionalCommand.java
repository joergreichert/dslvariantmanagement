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
package ca.uwaterloo.gp.fmp.provider.command;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.FmpPackage;


/**
 * @author Michal Antkiewicz <mantkiew@swen.uwaterloo.ca>
 * TODO should work for selections with multiple objects 
 */
public class MakeMandatoryOptionalCommand extends CompoundCommand {
	EObject owner;
	boolean mandatory;
	
	public MakeMandatoryOptionalCommand(EditingDomain domain, EObject owner, boolean mandatory) {
		this.owner = owner;
		this.mandatory = mandatory;
		
		append(new SetCommand(domain, owner, FmpPackage.eINSTANCE.getNode_Min(), mandatory ? new Integer(1) : new Integer(0)));
		append(new SetCommand(domain, owner, FmpPackage.eINSTANCE.getNode_Max(), new Integer(1)));
	}
	public boolean canExecute() {
		if (owner instanceof Feature)
			return true;
		return false;
	}
}
