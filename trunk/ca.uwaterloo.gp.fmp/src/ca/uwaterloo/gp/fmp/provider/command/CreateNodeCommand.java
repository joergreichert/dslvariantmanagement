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

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CommandActionDelegate;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.FeatureGroup;
import ca.uwaterloo.gp.fmp.FmpPackage;
import ca.uwaterloo.gp.fmp.Node;
import ca.uwaterloo.gp.fmp.Reference;
import ca.uwaterloo.gp.fmp.provider.NodeItemProvider;
import ca.uwaterloo.gp.fmp.system.MetaModel;
import ca.uwaterloo.gp.fmp.system.ModelManipulation;
import ca.uwaterloo.gp.fmp.system.NodeIdDictionary;
import ca.uwaterloo.gp.fmp.system.RoleQuery;


/**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>
 */
public class CreateNodeCommand extends CompoundCommand implements CommandActionDelegate {
	protected EditingDomain domain;
	protected EObject owner;
	protected EStructuralFeature feature;
	protected Object value;
	protected int index;
	protected Collection collection;
	NodeItemProvider itemProvider;
	
	public CreateNodeCommand(EditingDomain domain,
			EObject owner, EStructuralFeature feature, Object value, int index,
			Collection collection, NodeItemProvider itemProvider)
	{
		this.domain = domain;
		this.owner = owner;
		this.feature = feature;
		this.value = value;
		this.index = index;
		this.collection = collection;
		this.itemProvider = itemProvider;
	}
	
	public void execute()
	{
		Command newNodeCommand = new CreateChildCommand(domain, owner, feature, value, index, collection, itemProvider);
		
		Feature metaFeature;
		// by default 0 for feature
		int i = 0;
		if (value instanceof FeatureGroup) i = 1;
		else if (value instanceof Reference) i = 2;
		
		if (RoleQuery.INSTANCE.getLocationType((Node) owner) == RoleQuery.MODEL)
			metaFeature = (Feature) MetaModel.getMetaModel(owner).getChildren().get(i);
		else if (RoleQuery.INSTANCE.getLocationType((Node) owner) == RoleQuery.METAMODEL)
			metaFeature = (Feature) MetaModel.getMetaMetaModel(owner).getChildren().get(i);
		else 
			metaFeature = null;
		
		appendAndExecute(newNodeCommand);
		
		if(metaFeature != null)
		{
			Feature newProperties = (Feature) ModelManipulation.INSTANCE.configureTree(metaFeature);
			appendAndExecute(new AddCommand(domain, metaFeature, FmpPackage.eINSTANCE.getFeature_Configurations(), newProperties));
			((Node) value).setProperties(newProperties);
			NodeIdDictionary.INSTANCE.visit(null, newProperties);
		}		
		NodeIdDictionary.INSTANCE.visit(null, (Node) value);
		/**
		 * Michal: we need to make sure root feature has cardinality 1..1
		 */
		if (value instanceof Feature) {
			if (RoleQuery.INSTANCE.getNodeType((Node) value) == RoleQuery.ROOT_FEATURE) {
				((Feature) value).setMin(1);
				((Feature) value).setMax(1);
			}
		}
		super.execute();
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
		// TODO Well I don't know, but it should be always possible
		return true;
	}
}
