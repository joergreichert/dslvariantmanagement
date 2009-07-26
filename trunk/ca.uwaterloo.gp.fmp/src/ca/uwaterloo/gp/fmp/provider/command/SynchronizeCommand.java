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
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.FmpPackage;
import ca.uwaterloo.gp.fmp.Node;
import ca.uwaterloo.gp.fmp.TypedValue;
import ca.uwaterloo.gp.fmp.ValueType;
import ca.uwaterloo.gp.fmp.constraints.ConfiguratorDictionary;
import ca.uwaterloo.gp.fmp.provider.fmpEditPlugin;
import ca.uwaterloo.gp.fmp.system.ModelManipulation;
import ca.uwaterloo.gp.fmp.system.NodeIdDictionary;

;

/**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>, 
 *         Michal Antkiewicz <mantkiew@swen.uwaterloo.ca>
 */
public class SynchronizeCommand extends CompoundCommand {
	Feature rootFeature;
	EditingDomain domain;
	AdapterFactory adapterFactory;
	Feature newFeature;
	CompoundCommand command;
	
	public SynchronizeCommand(EditingDomain domain, AdapterFactory adapterFactory, Feature rootFeature) {
		super(0);
		this.domain = domain;
		this.adapterFactory = adapterFactory;
		this.rootFeature = rootFeature;
	}
	
	public void execute()
	{
		command = new CompoundCommand();
		
		// Synchronize the root feature against each of its configurations
		synchronizeRoot(rootFeature);
	
		CompoundCommand actualCommand = new CompoundCommand();
		for(Iterator commandsIterator = command.getCommandList().iterator(); commandsIterator.hasNext();)
		{
			actualCommand.appendAndExecute((Command)commandsIterator.next());
		}
		
		// recreate node id dictionaries for rootFeature and its configurations
		NodeIdDictionary.INSTANCE.removeDictionaries(rootFeature);
		NodeIdDictionary.INSTANCE.visit(rootFeature);
	}
	
	protected void synchronizeRoot(Feature aRootFeature)
	{
		for(Iterator configurationsIterator = aRootFeature.getConfigurations().iterator(); configurationsIterator.hasNext();)
		{
			// synchronize a root against one of its configurations
			Feature aConfiguration =(Feature)configurationsIterator.next();
			synchronizeNode(aRootFeature, aConfiguration);
			
			// after synchronizing the configuration, clear its Configurator
			ConfiguratorDictionary.INSTANCE.remove(aConfiguration);
			
			// then synchronize against stages of configurations
			synchronizeRoot(aConfiguration);
		}
	}
	
	protected void synchronizeNode(Node modelNode, Node confNode)
	{
		if (confNode.getOrigin() == modelNode) {
			// first synchronize id, min and max
			String id = modelNode.getId();
			if (confNode.getId().compareTo(id) != 0)
				command.append(new SetCommand(domain, confNode, FmpPackage.eINSTANCE.getNode_Id(), id));
			
			/* 
			 * Michal: it is not that simple with cardinality
			 * 1. the number of existing clones can be bigger than new max
			 * 2. for feature groups, the number of selected features can be bigger then max
			 */
			 int min = modelNode.getMin();
			if (confNode.getMin() != min)
				command.append(new SetCommand(domain, confNode, FmpPackage.eINSTANCE.getNode_Min(), new Integer(min)));
			
			int max = modelNode.getMax();
			if (confNode.getMax() != max)
				command.append(new SetCommand(domain, confNode, FmpPackage.eINSTANCE.getNode_Max(), new Integer(max))); 
			
			if (modelNode instanceof Feature) {
				Feature feature = (Feature) modelNode;
				Feature conf = (Feature) confNode;
				// synchronize name, type and attribute value
				String name = feature.getName();
				if (conf.getName().compareTo(name) != 0)
					command.append(new SetCommand(domain, confNode, FmpPackage.eINSTANCE.getFeature_Name(), name));
				
				ValueType type = feature.getValueType();
				ValueType confType = conf.getValueType();
				if (type != null && confType != null) {
					if (confType.getValue() != type.getValue())
						command.append(new SetCommand(domain, confNode, FmpPackage.eINSTANCE.getFeature_ValueType(), type));
				}
				TypedValue value = feature.getTypedValue();
				TypedValue confValue = conf.getTypedValue();
				if (value != null && confValue != null)
					if (value.getValueToString().compareTo(confValue.getValueToString()) != 0)
						command.append(new SetCommand(domain, confNode, FmpPackage.eINSTANCE.getFeature_TypedValue(), ModelManipulation.INSTANCE.copyTypedValue(value)));
			}
				
			if(modelNode.getChildren().size() > 0)
			{
				for(Iterator modelChildrenIterator = modelNode.getChildren().iterator(); modelChildrenIterator.hasNext();)
				{
					Node modelChild = (Node)modelChildrenIterator.next();
					Node modelChildConf = null;
					List modelChildConfs = new Vector();
					
					// see if the model child has an origin-conf relationship with
					// the children of confNode.  If not, then add it. If we find a node
					// with no origin, then we delete it
					for(Iterator confChildrenIterator = confNode.getChildren().iterator(); confChildrenIterator.hasNext();)
					{
						Node confChild = (Node)confChildrenIterator.next();
						
						// if the conf node has no origin, delete it
						if(!doesNodeExist(confChild.getOrigin()))
							ModelManipulation.INSTANCE.remove(confChild, domain, command, true);
						// else if the confChild is a prototype node that corresponds to the modelChild, then add it
						else if(confChild.getOrigin() == modelChild)
						{
							// do not add clones
							if(confChild instanceof Feature && ((Feature)confChild).getPrototype() != null)
							{
								modelChildConf = confChild;
							}
							modelChildConfs.add(confChild);
						}				
					}
					
					// no corresponding node was found - so add
					if(modelChildConfs.size() == 0)
					{
						Node configuredModelChild = ModelManipulation.INSTANCE.configureTree(modelChild);
						command.append(new CreateChildCommand(domain, confNode, modelNode.eContainingFeature(), configuredModelChild, Collections.singleton(configuredModelChild)));				
					}
					// we have to synchronize cardinality, attributes, etc.
					else
					{
						for(Iterator modelChildConfsIterator = modelChildConfs.iterator(); modelChildConfsIterator.hasNext();)
							synchronizeNode(modelChild, (Node)modelChildConfsIterator.next());
					}			
				}
			}
			else
			{
				for(Iterator confChildrenIterator = confNode.getChildren().iterator(); confChildrenIterator.hasNext();)
					ModelManipulation.INSTANCE.remove((Node)confChildrenIterator.next(), domain, command, true);
			}
		}
		else
			fmpEditPlugin.INSTANCE.log("Synchronization: confNode.origin != modelNode !!!");
	}
	
	protected boolean doesNodeExist(Node node)
	{
		return (node != null && node.eContainingFeature() != null && node.eContainer() != null &&
				node.eContainmentFeature() != null);
	}
	
	public boolean canExecute()
	{
		return true;
	}
}
