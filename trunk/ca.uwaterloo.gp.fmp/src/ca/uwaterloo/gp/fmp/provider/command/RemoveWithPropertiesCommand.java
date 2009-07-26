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

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import ca.uwaterloo.gp.fmp.Node;
import ca.uwaterloo.gp.fmp.system.ModelManipulation;


/**
 * @author Michal Antkiewicz <mantkiew@swen.uwaterloo.ca>
 * Note the constructor is not really removing anything. ModelManipulation.INSTANCE.remove is just appending
 * remove commands to this compound command.
 */
public class RemoveWithPropertiesCommand extends CompoundCommand {
	public RemoveWithPropertiesCommand(EditingDomain domain, Collection selection) {
		Node node = null;
		for (Iterator i = selection.iterator(); i.hasNext(); ) {
			node = (Node) i.next();
			ModelManipulation.INSTANCE.remove(node, domain, this, true);
		}
	}
}
