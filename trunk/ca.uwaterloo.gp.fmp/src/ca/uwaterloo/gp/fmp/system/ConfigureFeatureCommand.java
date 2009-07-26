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
package ca.uwaterloo.gp.fmp.system;

import java.util.Collection;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.CommandActionDelegate;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.Node;
import ca.uwaterloo.gp.fmp.Project;
import ca.uwaterloo.gp.fmp.provider.NodeItemProvider;


/**
 * @author Michal Antkiewicz <mantkiew@swen.uwaterloo.ca>
 */
public class ConfigureFeatureCommand extends CompoundCommand implements CommandActionDelegate{
	EditingDomain domain;
	EObject owner; 
	EStructuralFeature feature;
	Object value;
	int index;
	Collection collection;
	NodeItemProvider itemProvider;
	
	public ConfigureFeatureCommand(EditingDomain domain, EObject owner, EStructuralFeature feature, Object value, int index,
					Collection collection, NodeItemProvider itemProvider) {
		this.domain = domain;
		this.owner = owner;
		this.feature = feature;
		this.value = value;
		this.index = index;
		this.collection = collection;
		this.itemProvider = itemProvider;
	}
	public void execute() {
		Feature newConfiguration = (Feature) ModelManipulation.INSTANCE.configureTree((Node) owner);
		appendAndExecute(new CreateChildCommand(domain, owner, feature, newConfiguration, index, collection, itemProvider));
		NodeIdDictionary.INSTANCE.visit(null, newConfiguration);
	}
	public String getText() {
		return itemProvider.getCreateChildText(owner, feature, value, collection);
	}
	public String getToolTipText() {
		return itemProvider.getCreateChildToolTipText(owner, feature, value, collection);
	}
	public Object getImage() {
		return itemProvider.getCreateChildImage(owner, feature, value, collection);
	}
	public boolean canExecute() {
		// disallow configuring model and metamodel 
		if (owner.eContainer() instanceof Project)
			return false;
		return true;
	}
}
