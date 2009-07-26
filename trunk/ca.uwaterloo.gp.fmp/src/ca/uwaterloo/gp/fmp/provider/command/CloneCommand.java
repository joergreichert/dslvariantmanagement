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
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import ca.uwaterloo.gp.fmp.Clonable;
import ca.uwaterloo.gp.fmp.FmpPackage;
import ca.uwaterloo.gp.fmp.system.ModelManipulation;
import ca.uwaterloo.gp.fmp.system.NodeIdDictionary;
import ca.uwaterloo.gp.fmp.system.RoleQuery;


/**
 * @author Michal Antkiewicz <mantkiew@swen.uwaterloo.ca>
 */
public class CloneCommand extends CompoundCommand {
	AdapterFactory adapterFactory;
	Clonable owner;
	EditingDomain domain;
	
	public CloneCommand(EditingDomain domain, AdapterFactory adapterFactory, Clonable owner) {
		this.owner = owner;
		this.adapterFactory = adapterFactory;
		this.domain = domain;
	}
	public void execute() {
		Clonable clone = (Clonable) ModelManipulation.INSTANCE.copy(owner, domain, adapterFactory, this);
		clone.setMin(1);
		clone.setMax(1);
		// set the prototype of a clone
		appendAndExecute(new SetCommand(domain, clone, FmpPackage.eINSTANCE.getClonable_Prototype(), owner));
		NodeIdDictionary.INSTANCE.visit(clone);
	}
	/**
	 * you can clone only if:
	 * - you are in the configuration that is: CONFIGURATION, MODEL_PROPERTIES, METAMODEL_PROPERTIES
	 */
	public boolean canExecute() {
		int locationType = RoleQuery.INSTANCE.getLocationType(owner);
		int[] cardinality = owner.getCheckboxViewCardinality();
		
		return (cardinality[1] > 1 || cardinality[1] == -1) && 
			   (locationType == RoleQuery.MODEL_PROPERTIES ||
			   locationType == RoleQuery.METAMODEL_PROPERTIES ||
		       locationType == RoleQuery.CONFIGURATION || 
			   locationType == RoleQuery.CONFIGURATION_PROPERTIES);
	}
}
