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

import java.util.Collections;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.command.ReplaceCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.Node;
import ca.uwaterloo.gp.fmp.Reference;
import ca.uwaterloo.gp.fmp.constraints.ConfiguratorDictionary;
import ca.uwaterloo.gp.fmp.system.ModelManipulation;
import ca.uwaterloo.gp.fmp.system.ModelNavigation;
import ca.uwaterloo.gp.fmp.system.NodeIdDictionary;

/**
 * @author Michal Antkiewicz <mantkiew@swen.uwaterloo.ca>
 * This compound command:

 */
public class UnfoldReferenceCommand extends CompoundCommand {
	Reference reference;
	Feature feature;
	EditingDomain domain;
	AdapterFactory adapterFactory;
	Feature newFeature;
	
	public UnfoldReferenceCommand(EditingDomain domain, AdapterFactory adapterFactory, Reference reference) {
		super(0);
		this.domain = domain;
		this.adapterFactory = adapterFactory;
		this.reference = reference;
		feature = reference.getFeature();
		
	}
	public void execute() {
		Node newFeature = null;
		if (feature != null)
			// replace Reference with Feature
			newFeature = ModelManipulation.INSTANCE.copy(feature, domain, adapterFactory, this);
		else {
			// TODO: configure with compound command!
			feature = ((Reference) reference.getOrigin()).getFeature();
			if (feature != null)
				newFeature = ModelManipulation.INSTANCE.configure(feature);
		}
			
		if (newFeature != null) {
			append(ReplaceCommand.create(domain, reference.eContainer(), reference.eContainingFeature(), reference, Collections.singleton(newFeature)));
			super.execute();
			NodeIdDictionary.INSTANCE.visit(reference.getId() + "IXI", newFeature);
			// when we unfold, we need to rewrite the propositional formula - so just
            // delete the configurator - so that it will be recreated lazily when the first checkbox is clicked
            Node rootFeature = ModelNavigation.INSTANCE.navigateToRootFeature(newFeature);
            // Only clear the configuration if the rootFeature contains node in its subtree
            if(rootFeature != null && rootFeature != reference)
                    ConfiguratorDictionary.INSTANCE.remove(rootFeature);
		}
	}
	public boolean canExecute() {
		return feature != null || 
		(feature == null && reference.getOrigin() != null && ((Reference) reference.getOrigin()).getFeature() != null);
	}
}
