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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import ca.uwaterloo.gp.fmp.Clonable;
import ca.uwaterloo.gp.fmp.ConfigState;
import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.FeatureGroup;
import ca.uwaterloo.gp.fmp.FmpFactory;
import ca.uwaterloo.gp.fmp.FmpPackage;
import ca.uwaterloo.gp.fmp.Node;
import ca.uwaterloo.gp.fmp.Project;
import ca.uwaterloo.gp.fmp.Reference;
import ca.uwaterloo.gp.fmp.TypedValue;
import ca.uwaterloo.gp.fmp.ValueType;
import ca.uwaterloo.gp.fmp.constraints.Configurator;
import ca.uwaterloo.gp.fmp.constraints.ConfiguratorDictionary;
import ca.uwaterloo.gp.fmp.constraints.Conflicts;
import ca.uwaterloo.gp.fmp.impl.FmpFactoryImpl;
import ca.uwaterloo.gp.fmp.provider.FeatureItemProvider;
import ca.uwaterloo.gp.fmp.provider.ReferenceItemProvider;


/**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>, 
 *         Michal Antkiewicz <mantkiew@swen.uwaterloo.ca>,
 *         Krzysztof Pietroszek <kmpietro@swen.uwaterloo.ca>
 */
public class ModelManipulation
{
	public static ModelManipulation INSTANCE = new ModelManipulation();
	protected final static Comparator featureComparator = new Comparator() {
		public int compare(Object arg0, Object arg1) {
			if (arg0 == null && arg1 != null)
				return -1;
			else if (arg0 != null && arg1 == null)
				return 1;
			else if (arg0 == null && arg1 == null)
				return 0;
			
			Feature f1 = (Feature) arg0;
			Feature f2 = (Feature) arg1;
			String name1 = f1.getName();
			String name2 = f2.getName();
			if (name1 != null && name2 != null)
			    return name1.compareTo(name2);
			else if (name1 == null && name2 == null)
			    return 0;
			else if (name1 != null && name2 == null)
			    return 1;
			else 
			    return -1;
		}
		public int equals(Object arg0, Object arg1) {
			return compare(arg0, arg1);
		}
	};
	/**
	 * Returns an id that can be used in the construction of propositional formula 
	 * @param id
	 * @return
	 */
	public String getValidId(String id)
	{
		String validId = (id!=null)? id : "feature";
		
		validId = validId.substring(0,1).toLowerCase() + validId.substring(1);
		if(validId.equals("integer") 
			|| validId.equals("string")
			|| validId.equals("float"))
			validId = validId + "Var";
		
		return validId;
	}
	/**
	 * @param node that is to be copied with its children and properties.
	 * @param domain
	 * @param adapterFactory
	 * @param command a compound command used for adding individual commands. When null, operations are
	 * executed immediately. 
	 * @return copy of the node. The copy is added as a sibling of node
	 */
	public Node copy(Node node, EditingDomain domain, AdapterFactory adapterFactory, CompoundCommand command) {
		Node newNode = copyTree(node, domain, adapterFactory, command);
		// add newNode as a sibling of node 
		if (command == null) {
			if (node.eContainingFeature() == FmpPackage.eINSTANCE.getNode_Children())
				((Node)node.eContainer()).getChildren().add(newNode);
			else if (node.eContainingFeature() == FmpPackage.eINSTANCE.getFeature_Configurations())
				((Feature)node.eContainer()).getConfigurations().add(newNode);
		}
		else {
			// implement the above but using commands
			ItemProviderAdapter itemProvider = (ItemProviderAdapter) adapterFactory.adapt(
					node.eContainer(), IEditingDomainItemProvider.class);
			command.appendAndExecute(new CreateChildCommand(domain, node.eContainer(), node.eContainmentFeature(), newNode, Collections.singleton(newNode), itemProvider));
		}
		// when we clone, we need to rewrite the propositional formula - so just
		// delete the configurator - so that it will be recreated lazily when the first checkbox is clicked
		Node rootFeature = ModelNavigation.INSTANCE.navigateToRootFeature(node);
		// Only clear the configuration if the rootFeature contains node in its subtree
		if(rootFeature != null && rootFeature != node)
			ConfiguratorDictionary.INSTANCE.remove(rootFeature);
		
		return newNode;
	}
	public Node copyTree(Node node, EditingDomain domain, AdapterFactory adapterFactory, CompoundCommand command)
	{
		Node newTree = copyNode(node, domain, adapterFactory, command);
		for(Iterator childrenIterator = node.getChildren().iterator(); childrenIterator.hasNext(); )
			newTree.getChildren().add(copyTree((Node)childrenIterator.next(), domain, adapterFactory, command));
		return newTree;
	}
	
	public Node copyNode(Node node, EditingDomain domain, AdapterFactory adapterFactory, CompoundCommand command)
	{
		Node newNode = createNodeOfType(node);
		newNode.setOrigin(node.getOrigin());
		
		if (node.getProperties() != null)			 
			newNode.setProperties((Feature)copy(node.getProperties(), domain, adapterFactory, command));
		
		// Set attributes common to all kinds of Node (includes FeatureGroup)
		newNode.setMin(node.getMin());
		newNode.setMax(node.getMax());
		newNode.setId(node.getId());
		
		if(node instanceof Clonable)
		{
			// Set attributes specific to each kind of Node
			if(node instanceof Feature)
			{
				Feature featureNode = (Feature)node;
				Feature featureNewNode = (Feature)newNode;
				featureNewNode.setName(featureNode.getName());
				featureNewNode.setValueType(featureNode.getValueType());
				TypedValue value = featureNode.getTypedValue();
				// copy the value if exists
				if (value != null)
					featureNewNode.setTypedValue(copyTypedValue(value));				
			}
			else if(node instanceof Reference)
				((Reference)newNode).setFeature(((Reference)node).getFeature());
			 
			((Clonable)newNode).setState(((Clonable)node).getState());
		}
		
		return newNode;
	}
	
	public Node configureTree(Node node)
	{
		Node newTree = configureNode(node);
		for(Iterator childrenIterator = node.getChildren().iterator(); childrenIterator.hasNext(); )
			newTree.getChildren().add(configureTree((Node)childrenIterator.next()));
		return newTree;
	}
	
	protected Node configureNode(Node node)
	{
		Node newNode = createNodeOfType(node);
		
		// set the origin in origin-confs relation
		newNode.setOrigin(node);
		
		if(node.getProperties() != null && RoleQuery.INSTANCE.getLocationType(node) != RoleQuery.METAMODEL)
			newNode.setProperties((Feature)configure(node.getProperties()));
		
		newNode.setId(node.getId());
		
		if(node instanceof Clonable)
		{
			// must set the attributes of this node after setting the properties, so that properties
			// are created if necessary (and the get functions will set attributes on the tree of properties 
			// recursively)
			int[] cardinality = ((Clonable)node).getFeatureModelViewCardinality();

			// Set attributes common to all kinds of Node
			newNode.setMin(cardinality[0]);
			newNode.setMax(cardinality[1]);
			
			// Set attributes specific to each kind of Node
			if(node instanceof Feature)
			{
				Feature featureNode = (Feature)node;
				Feature featureNewNode = (Feature)newNode;
				featureNewNode.setName(featureNode.getName());
				featureNewNode.setValueType(featureNode.getValueType());
				TypedValue value = featureNode.getTypedValue();
				// copy the value if exists
				if (value != null)
					featureNewNode.setTypedValue(copyTypedValue(value));
				else {
					// try default value
					value = featureNode.getDefaultValue();
					if (value != null)
						featureNewNode.setTypedValue(copyTypedValue(value));
				}
			}
			else if(node instanceof Reference) {
				Feature refFeature = ((Reference)node).getFeature();
				if (refFeature != null && refFeature.getConfigurations().size() > 0)
					((Reference)newNode).setFeature((Feature) refFeature.getConfigurations().get(0));
			}
				
			 
			((Clonable)newNode).setState(((Clonable)node).getState());
		}
		else if(node instanceof FeatureGroup)
		{
			newNode.setMin(node.getMin());
			newNode.setMax(node.getMax());
		}
		
		return newNode;
	}

	public Feature configure(Feature root) {
		Feature newNode = (Feature) configureTree(root);

		// add to configurations containment
		root.getConfigurations().add(newNode);
		
		return newNode;
	}
	
	/**
	 * Creates a node whose class is the same as that of node
	 * @param node
	 * @return
	 */
	protected Node createNodeOfType(Node node)
	{
		Node newNode = null;
		if(node instanceof Feature)
			newNode = FmpFactoryImpl.eINSTANCE.createFeature();
		else if(node instanceof FeatureGroup)
			newNode = FmpFactoryImpl.eINSTANCE.createFeatureGroup();
		else if(node instanceof Reference)
			newNode = FmpFactoryImpl.eINSTANCE.createReference();
		
		return newNode;
	}
	
	public TypedValue copyTypedValue(TypedValue typedValue)
	{
		if (typedValue != null) {
			TypedValue typedValueClone = FmpFactoryImpl.eINSTANCE.createTypedValue();
			
			if(typedValue.getFeatureValue() != null)
				typedValueClone.setFeatureValue(typedValue.getFeatureValue());
			else if(typedValue.getIntegerValue() != null)
				typedValueClone.setIntegerValue(new Integer(typedValue.getIntegerValue().intValue()));
			else if(typedValue.getFloatValue() != null)
				typedValueClone.setFloatValue(new Float(typedValue.getFloatValue().floatValue()));
			else if(typedValue.getStringValue() != null)
				typedValueClone.setStringValue(typedValue.getStringValue());
			
			return typedValueClone;
		}
		return null;
	}

	
	/**
	 * Used by the presentation layer to get the new state of the given "node". Holding down control key
	 * indicates that elimination is desired.
	 * Returns a compound command containing the commands that change state of each node (including choice
	 * propagation).
	 * @param node
	 * @param controlKeyPressed
	 * @return
	 */
	public CompoundCommand getNewState(EditingDomain editingDomain, Clonable node, boolean controlKeyPressed)
	{
		CompoundCommand commands = new CompoundCommand();
		int []cardinality = node.getCheckboxViewCardinality();
		
		if(RoleQuery.INSTANCE.isInConfiguration(node) && cardinality[0] == 0 && cardinality[1] == 1)
		{
			//System.out.println("controlKeyPressed: " + controlKeyPressed);
			
			ConfigState newState = doStateTransition(commands, editingDomain, node, controlKeyPressed);
			updateStatesOfOtherNodes(commands, editingDomain, node, newState);			
		}
		else
			commands.append(UnexecutableCommand.INSTANCE);
		
		return commands;
	}	
		
	/**
	 * Performs state transition on the given node, given that the user has made a click on the current node
	 * If "commands" is non-null, a SetCommand is created for every state change.  Otherwise,
	 * the state change is directly made.
	 * @return
	 */
	protected ConfigState doStateTransition(CompoundCommand commands, EditingDomain editingDomain, Clonable node, boolean controlKeyPressed)
	{
		Feature rootFeature = ModelNavigation.INSTANCE.navigateToRootFeature(node);	
		Configurator configurator = ConfiguratorDictionary.INSTANCE.get(rootFeature);
		
		// Update the state on the ConfNode
		ConfigState curState = ((Clonable)node).getState();
		ConfigState newState = null;
				
		if(node.isOptional())
		{
			if(configurator.getMode() == Configurator.EDIT_MODE_USER_ONLY)
			{
				// Only allow the user to go back between user-selected/user-deselected and undecided
				if(curState == ConfigState.UNDECIDED_LITERAL) 
				{
					if(controlKeyPressed)
					{
						newState = ConfigState.USER_ELIMINATED_LITERAL;
						configurator.assignValue(node.getId(),0, false);
					}
					else
					{
						newState = ConfigState.USER_SELECTED_LITERAL;
						configurator.assignValue(node.getId(),1, false);						
					}
				}			
				else if(curState == ConfigState.USER_ELIMINATED_LITERAL ||
						curState == ConfigState.USER_SELECTED_LITERAL)
				{
					newState = ConfigState.UNDECIDED_LITERAL; 
					configurator.removeSelection(node.getId(), true, true, true);
				}			
			}
			else if(configurator.getMode() == Configurator.EDIT_MODE_INTERACTIVE || 
					configurator.getMode() == Configurator.EDIT_MODE_AUTORESOLVE_CONFLICTS)
			{
				// Save selections before applying the new value so that we can roll back
				// in case the user decides not to force the new selection
//kmpietro		CS_VarSelection[] selections = configurator.getSelections();
				
				if(curState == ConfigState.MACHINE_SELECTED_LITERAL)
				{
					newState = ConfigState.USER_ELIMINATED_LITERAL;
					configurator.removeSelection(node.getId(), true, false, false);
					configurator.assignValue(node.getId(),0, true);
				}
				else if(curState == ConfigState.MACHINE_ELIMINATED_LITERAL)
				{
					newState = ConfigState.USER_SELECTED_LITERAL;
					configurator.removeSelection(node.getId(), true, false, false);
					configurator.assignValue(node.getId(),1, true);
				}
				else if(curState == ConfigState.USER_SELECTED_LITERAL)
				{
					// user wants to express deselection
					if(controlKeyPressed)
					{
						// set it to "deselected"
						newState = ConfigState.USER_ELIMINATED_LITERAL;
						configurator.removeSelection(node.getId(), true, true, true);
						configurator.assignValue(node.getId(),0, true);
					}
					else
					{
						// set it to "unchecked"
						newState = ConfigState.UNDECIDED_LITERAL; 
						configurator.removeSelection(node.getId(), true, true, true);						 
					}
				}
				else if(curState == ConfigState.USER_ELIMINATED_LITERAL)
				{
					if(controlKeyPressed)
					{
						// set it to "unchecked"
						newState = ConfigState.UNDECIDED_LITERAL; 
						configurator.removeSelection(node.getId(), true, true, true);						
					}
					// user wants to express selection
					else
					{
						// set it to "selected"
						newState = ConfigState.USER_SELECTED_LITERAL;
						configurator.removeSelection(node.getId(), true, true, true);
						configurator.assignValue(node.getId(),1, true);
					}				
				}
				
				else if(curState == ConfigState.UNDECIDED_LITERAL)
				{
					if(controlKeyPressed)
					{
						newState = ConfigState.USER_ELIMINATED_LITERAL;
						configurator.assignValue(node.getId(),0, true);	
					}
					else
					{
						newState = ConfigState.USER_SELECTED_LITERAL;
						configurator.assignValue(node.getId(),1, true);
						//configRep.getConfigurator().assignValue(configRep.getFeatureMemberSelected(configRep.getExprID((Feature)confNode)), 1, true);
					}
				}	
				
				// Check if there is any conflict
				Conflicts conflicts = configurator.getConflicts(node.getId());
				if(conflicts != null)
				{
					boolean forceNewSelection = true;
					
					// if interactive mode, then ask
					if(configurator.getMode() == Configurator.EDIT_MODE_INTERACTIVE)
					{
//kmpietro				String question = "Your new selection:\n    " + this.formatSelection(configurator, conflicts.m_forced_selection) + "\n";
						String question = "Your new selection:\n    " + configurator.getVarName(conflicts.m_forced_selection) + "\n";
						question += "\nconflicts with the previous selections:\n";
						for(int i = 0; i < conflicts.m_conflicting_selections.length; i++)
						{
//kmpietro					question += "    " + this.formatSelection(configurator, configurator.getVarName(conflicts.m_conflicting_selections[i])) + "\n";
							question += "    " + configurator.getVarName(conflicts.m_conflicting_selections[i]) + "\n";
						}
						question += "\nWould you like to force the new selection anyway?";
						
						forceNewSelection = MessageDialog.openQuestion(new Shell(), "Conflict", question);
					}
					
					// If the user does not want to force the new selection, roll back the changes
/*kmpietro				
 					if(!forceNewSelection)
					{
						configurator.applySelections(selections);
						newState = curState;
					}
					// otherwise, remove the conflicting selections and force the new selection
					else
*/
					{
						for(int i = 0; i < conflicts.m_conflicting_selections.length; i++)
						{
							configurator.removeSelection
								(configurator.getVarName(conflicts.m_conflicting_selections[i]), true, true, true);
						}	
					}
				}
			}
			
			if(newState != null && newState != curState)
			{
				if(commands != null)
					commands.append(new SetCommand(editingDomain, node, FmpPackage.eINSTANCE.getClonable_State(), newState));
				else
					node.setState(newState);
			}
		}
		
		return newState;
	}
	
	/**
	 * Updates the states of other nodes given the fact that the specified "node" had its state changed
	 * If "commands" is non-null, a SetCommand is created for every state change.  Otherwise,
	 * the state change is directly made.
	 * 
	 * @param commands
	 * @param editingDomain
	 * @param node
	 */
	protected void updateStatesOfOtherNodes(CompoundCommand commands, EditingDomain editingDomain, Clonable node, ConfigState nodeState)
	{
		Feature rootFeature = ModelNavigation.INSTANCE.navigateToRootFeature(node);		
		Configurator configurator = ConfiguratorDictionary.INSTANCE.get(rootFeature);
		
		// Update other nodes affected as a result
		// to use ConfigIt, please change i <= configurator.getVarCount() to i < configurator.getVarCount()
		for(int i = 0; i <= configurator.getVarCount(); i++)
		{
			String aNodeId = configurator.getVarName(i);
		    Node aNode = configurator.getNode(aNodeId);
			
		    if(aNode instanceof Clonable)
		    {
		    	Clonable aClonable = (Clonable)aNode;
		    	
		    	// we're propagating choices only for optional clonables
				if	(aClonable.isOptional())				
				{
					ConfigState state = (aClonable != node)? aClonable.getState() : nodeState;
					ConfigState newState = null;
	
					int	min = configurator.getMin(aNodeId);
					int	max = configurator.getMax(aNodeId);
											
					// if only one value is allowed
					if(min == max)
					{
						// True - so must be selected
						if(min == 1)
						{
							if(state != ConfigState.MACHINE_SELECTED_LITERAL && state != ConfigState.USER_SELECTED_LITERAL)
								newState = ConfigState.MACHINE_SELECTED_LITERAL;
						}
						// False - so must be deselected
						else
						{
							// if the state has changed
							if(state != ConfigState.MACHINE_ELIMINATED_LITERAL && state != ConfigState.USER_ELIMINATED_LITERAL)
								newState = ConfigState.MACHINE_ELIMINATED_LITERAL;						
						}
					}
					// both true and false allowed - so make it undecided if it was machine selected
					else
					{
						// if the state has changed
						if(state == ConfigState.MACHINE_SELECTED_LITERAL || state == ConfigState.MACHINE_ELIMINATED_LITERAL)
							newState = ConfigState.UNDECIDED_LITERAL;
					}
					
					if(newState != null)
					{
						if(commands != null)
							commands.append(new SetCommand(editingDomain, aClonable, FmpPackage.eINSTANCE.getClonable_State(), newState));
						else
							aClonable.setState(newState);
					}
				}				
		    }
		}
	}
	
/* commented by kmpietro: CS_CtrlMngr used in the method is a part of ConfigIt
 * protected String formatSelection(Configurator configurator, CS_VarSelection selection)
	{
		String exprID = configurator.getVarName(selection.m_variable);
		String featureId = ((Feature)configurator.getNode(exprID)).getId();
		
		String returnValue = "";
		
		 If variable has been assigned an integer value 
		if (selection.m_int_value != CS_CtrlMngr.INT_NOT_ASSIGNED )
		{
			returnValue = featureId + " = " + selection.m_int_value;
		}
		
		 If variable has been assigned a float value 
		else if ( selection.m_float_value != CS_CtrlMngr.FLOAT_NOT_ASSIGNED )
		{
			returnValue = featureId + " = " + selection.m_float_value;
		}
		
		 If variable has only been assigned a single value 
		else if ( selection.m_values != null && selection.m_values.length == 1 &&
				selection.m_int_value == CS_CtrlMngr.INT_NOT_ASSIGNED &&
					selection.m_float_value == CS_CtrlMngr.FLOAT_NOT_ASSIGNED)
		{
			returnValue = featureId + " = " + configurator.getValueName( selection.m_variable,
					selection.m_values[ 0 ] );
		}
		return returnValue;
	}
*/	
	// Direct editing begin
	/**
	 * Michal: 
	 * in the model -> edit name or reference
	 * in the configuration -> edit value
	 * @param object
	 * @return
	 */
	public IItemPropertyDescriptor getDirectEditPropertyDescriptor(AdapterFactory adapterFactory, Object object) {
		if (RoleQuery.INSTANCE.getLocationType((Node) object) == RoleQuery.MODEL ||
			RoleQuery.INSTANCE.getLocationType((Node) object) == RoleQuery.METAMODEL)  {
			if (object instanceof Feature) {
				FeatureItemProvider itemProvider = (FeatureItemProvider) adapterFactory.adapt(object, IEditingDomainItemProvider.class);
				if (itemProvider != null)
					return new ItemPropertyDescriptor (
						((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
						itemProvider.getResourceLocator(),
						itemProvider.getString("_UI_Feature_name_feature"),
						itemProvider.getString("_UI_PropertyDescriptor_description", new Object[]{"_UI_Feature_name_feature", "_UI_Feature_type"}),
						FmpPackage.eINSTANCE.getFeature_Name(),
						true,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE);
			}
			else if (object instanceof Reference) {
				ReferenceItemProvider itemProvider = (ReferenceItemProvider) adapterFactory.adapt(object, IEditingDomainItemProvider.class);
				if (itemProvider != null)
					return new ItemPropertyDescriptor(
						((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
						itemProvider.getResourceLocator(),
						itemProvider.getString("_UI_Reference_feature_feature"),
						itemProvider.getString("_UI_PropertyDescriptor_description", new Object[] {"_UI_Reference_feature_feature", "_UI_Reference_type"}),
						 	FmpPackage.eINSTANCE.getReference_Feature(), true) {
						public Collection getChoiceOfValues(Object object) {
						    Node eobject = (Node) object;
						    Resource resource = eobject.eResource();
						    ArrayList copy = new ArrayList();
						    if (resource != null) {
						        ResourceSet resourceSet = resource.getResourceSet();
						        if (resourceSet != null)  {
						            int type = RoleQuery.INSTANCE.getLocationType(eobject);
						            Project project;
									for (Iterator i = resourceSet.getResources().iterator(); i.hasNext(); )  {
									    Resource res = (Resource) i.next();
										EList contents = res.getContents();    
									    if (!contents.isEmpty()) {
											project = (Project) contents.get(0);
											copy.addAll(project.getModel().getChildren());
								            if (type == RoleQuery.METAMODEL || type == RoleQuery.METAMODEL_PROPERTIES) {
											    copy.addAll(project.getMetaModel().getChildren());
								            }
									    }
									}
						        }
						        Collections.sort(copy, featureComparator);
						        return copy;
						    }
						    return null;
						}
				};
			}	
		}
		else if (RoleQuery.INSTANCE.isInConfiguration((Node) object)) {
			if (object instanceof Feature) {
				Feature feature = (Feature) object;
				TypedValue typedValue = feature.getTypedValue();
				if (typedValue == null) {
					// create one, so that the user can edit it
					typedValue = FmpFactory.eINSTANCE.createTypedValue();
					((Feature) object).setTypedValue(typedValue);
				}
				
				ValueType valueType = ((Feature) object).getValueType();
				if (valueType != ValueType.NONE_LITERAL)  {
 					FeatureItemProvider itemProvider = (FeatureItemProvider) adapterFactory.adapt(object, IEditingDomainItemProvider.class);
					String type = null;
					EStructuralFeature structuralFeature = null;
 					switch (valueType.getValue()) {
 						case ValueType.FEATURE:
 							type = "feature";
 							structuralFeature = FmpPackage.eINSTANCE.getTypedValue_FeatureValue();
 							break;
 						case ValueType.FLOAT:
 							type = "float";
 							structuralFeature = FmpPackage.eINSTANCE.getTypedValue_FloatValue();
 							break;
 						case ValueType.INTEGER:
 							type = "integer";
 							structuralFeature = FmpPackage.eINSTANCE.getTypedValue_IntegerValue();
 							break;
 						case ValueType.STRING:
 							type = "string";
 							structuralFeature = FmpPackage.eINSTANCE.getTypedValue_StringValue();
 							break;
					}
 					if (itemProvider != null) {
 						if (valueType == ValueType.FEATURE_LITERAL) {
 							// have to filter out invalid values
 							return new ItemPropertyDescriptor (
 									((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
 									itemProvider.getResourceLocator(),
 									itemProvider.getString("_UI_TypedValue_" + type + "Value_feature"),
 									itemProvider.getString("_UI_PropertyDescriptor_description", new Object[]{"_UI_TypedValue_" + type + "Value_feature", "_UI_TypedValue_type"}),
 									structuralFeature,
 									true,
 									ItemPropertyDescriptor.GENERIC_VALUE_IMAGE) {
 								public Collection getChoiceOfValues(Object object) {
 									Feature feature = (Feature) object;
 									Feature featureRoot = ModelNavigation.INSTANCE.navigateToRootFeature(feature);
 			                        Feature origin = (Feature) feature.getOrigin();
 			                        if (origin == null)
 			                        	return Collections.EMPTY_LIST;
 			                        
 			                        ArrayList copy = new ArrayList();
 			                        
 			                        switch (RoleQuery.INSTANCE.getPropertyRole(feature)) {
 			                        case RoleQuery.NONE:
 			                        	// get reference type of origin
 	 			     				    Feature ref = (Feature) origin.getDefaultValue().getFeatureValue();
 	 			     					
 			     				    	if (ref != null) {
 			     						    Feature derivedRef = null;
 			     						    // from derived of ref, choose a proper one
 			     						    for (Iterator i = ref.getConfs().iterator(); i.hasNext(); ) {
 			     						        Feature aux = (Feature) i.next();
 			     						        if (ModelNavigation.INSTANCE.navigateToRootFeature(aux).equals(featureRoot)) {
 			     						            derivedRef = aux;
 			     						            break;
 			     						        }
 			     						    }
 			     						    // choice of values is clones of derivedRef
 			     						    if (derivedRef != null)	{
 			     						        // add a derivedRef itself
 			     						        copy.add(derivedRef);
 			     						        // add its clones
 			     						        copy.addAll(((Clonable) derivedRef).getClones());
 			     						    	Collections.sort(copy, featureComparator);
 			     						    	return copy;
 			     						    }
 			     				    	}
 	 			     					return Collections.EMPTY_LIST;
 			                        case RoleQuery.DEFAULT_VALUE:
 			                        	// get the feature to which properties 'feature' belongs
 			                           Feature described = 
 			                        	   (Feature) ModelNavigation.INSTANCE.navigateToRootFeature(feature).getDescribedNode();
 			       				    
 			                           // allow setting of reference type only in the model.
 			                           if (RoleQuery.INSTANCE.getLocationType(described) == RoleQuery.MODEL) {
 			                               copy.addAll(ModelNavigation.INSTANCE.getAllFeatures(described));
 			                               Collections.sort(copy, featureComparator);
 			                               return copy;
 			                           }
 			                           return Collections.EMPTY_LIST;
 			                        case RoleQuery.VALUE:
 			                        	// get the feature to which properties 'feature' belongs
 										described = 
 											(Feature) ModelNavigation.INSTANCE.navigateToRootFeature(feature).getDescribedNode();
 								        featureRoot = ModelNavigation.INSTANCE.navigateToRootFeature(described);
 								        origin = (Feature) described.getOrigin();
 							           /* else {
 							                described = feature;
 							                featureRoot = ModelNavigation.INSTANCE.navigateToRootFeature(described);
 								            origin = (Feature) described.getOrigin();
 							            }*/
 								       if (origin == null)
 	 			                        	return Collections.EMPTY_LIST;
 									    // get reference type of origin
 				     				    ref = origin.getTypedValue().getFeatureValue();
			     				    	if (ref != null) {
			     						    Feature derivedRef = null;
			     						    // from derived of ref, choose a proper one
			     						    for (Iterator i = ref.getConfs().iterator(); i.hasNext(); ) {
			     						        Feature aux = (Feature) i.next();
			     						        if (ModelNavigation.INSTANCE.navigateToRootFeature(aux).equals(featureRoot)) {
			     						            derivedRef = aux;
			     						            break;
			     						        }
			     						    }
			     						    // choice of values is clones of derivedRef
			     						    if (derivedRef != null)	{
			     						        // add a derivedRef itself
			     						        copy.add(derivedRef);
			     						        // add its clones
			     						        copy.addAll(((Clonable)derivedRef).getClones());
			     						    	Collections.sort(copy, featureComparator);
			     						    	return copy;
			     						    }
 				     				    }
 			                        }
 			                       return Collections.EMPTY_LIST;
 								}
 							};
 						}
 						else {
 							// for int, string, and float 
	 						return new ItemPropertyDescriptor (
								((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
								itemProvider.getResourceLocator(),
								itemProvider.getString("_UI_TypedValue_" + type + "Value_feature"),
								itemProvider.getString("_UI_PropertyDescriptor_description", new Object[]{"_UI_TypedValue_" + type + "Value_feature", "_UI_TypedValue_type"}),
								structuralFeature,
								true,
								ItemPropertyDescriptor.GENERIC_VALUE_IMAGE);
 						}
 					}
				}
			}
			else if (object instanceof Reference) {
				ReferenceItemProvider itemProvider = (ReferenceItemProvider) adapterFactory.adapt(object, IEditingDomainItemProvider.class);
				if (itemProvider != null)
					return new ItemPropertyDescriptor(
						((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
						itemProvider.getResourceLocator(),
						itemProvider.getString("_UI_Reference_feature_feature"),
						itemProvider.getString("_UI_PropertyDescriptor_description", new Object[] {"_UI_Reference_feature_feature", "_UI_Reference_type"}),
						 	FmpPackage.eINSTANCE.getReference_Feature(), true) {
						public Collection getChoiceOfValues(Object object) {
						    Reference reference = (Reference) ((Node) object).getOrigin();
						    Feature feature = reference.getFeature();
						    if (feature != null) {
						    	ArrayList copy = new ArrayList();
						    	copy.add(null);
						    	copy.addAll(feature.getConfigurations());
						        Collections.sort(copy, featureComparator);
						        return copy;
						    }
						    return null;
						}
				};
			}
		}
		return null;
	}
	public static Object getDirectEditValue(Object object) {
		if (RoleQuery.INSTANCE.getLocationType((Node) object) == RoleQuery.MODEL ||
			RoleQuery.INSTANCE.getLocationType((Node) object) == RoleQuery.METAMODEL)  {
				
				if (object instanceof Feature) {
					return ((Feature)object).getName();
				}
				else if (object instanceof Reference) {
					return ((Reference)object).getFeature();
				}
		}
		else if (RoleQuery.INSTANCE.isInConfiguration((Node) object)) {
			if (object instanceof Feature) {
				TypedValue value = ((Feature)object).getTypedValue();
				if (value != null)
				    return value.getValueToString();
			}
			else if (object instanceof Reference) {
				return ((Reference)object).getFeature();
			}
		}
		return null;
	}
	/**
	 * Michal:
	 * setting a value in configuration can be constrained for:
	 * 1. min, max of RootFeature, SolitaryFeature, GroupedFeature, FeatureGroup, SolitaryReference and GroupedReference
	 * 2. id of Node has to be unique and start with lower case
	 */
	public static Command createDirectEditSetCommand(AdapterFactory adapterFactory, EditingDomain editingDomain, EObject owner, Object newValue) {
		Command command = UnexecutableCommand.INSTANCE;
		if (RoleQuery.INSTANCE.getLocationType((Node) owner) == RoleQuery.MODEL ||
			RoleQuery.INSTANCE.getLocationType((Node) owner) == RoleQuery.METAMODEL)  {
				
				if (owner instanceof Feature) {
					FeatureItemProvider itemProvider = (FeatureItemProvider) adapterFactory.adapt(owner, IEditingDomainItemProvider.class);
					if (itemProvider != null) {
						Feature feature = (Feature) owner;
						Command setNameCommand = new SetCommand(editingDomain, owner, FmpPackage.eINSTANCE.getFeature_Name(), newValue);
						String name =feature.getName(); 
						if (name != null && name.compareTo("") != 0)
							command = setNameCommand;
						else {
							command = new CompoundCommand();
							((CompoundCommand)command).append(setNameCommand);
							String newId = NodeIdDictionary.INSTANCE.getIdForName((String) newValue);
							((CompoundCommand)command).append(new SetCommand(editingDomain, owner, FmpPackage.eINSTANCE.getNode_Id(), newId));
						}
					}
				}
				else if (owner instanceof Reference) {
					ReferenceItemProvider itemProvider = (ReferenceItemProvider) adapterFactory.adapt(owner, IEditingDomainItemProvider.class);
					if (itemProvider != null)
						command =  new SetCommand(editingDomain, owner, FmpPackage.eINSTANCE.getReference_Feature(), newValue);
				}	
			}
			else if (RoleQuery.INSTANCE.isInConfiguration((Node) owner)) {
				if (owner instanceof Feature) {
					TypedValue typedValue = ((Feature) owner).getTypedValue();
					if (typedValue != null)  {
						ValueType valueType = ((Feature) owner).getValueType();
						if (valueType != ValueType.NONE_LITERAL)  {
		 					FeatureItemProvider itemProvider = (FeatureItemProvider) adapterFactory.adapt(owner, IEditingDomainItemProvider.class);
							String type = null;
							EStructuralFeature structuralFeature = null;
		 					switch (valueType.getValue()) {
		 						case ValueType.FEATURE:
		 							structuralFeature = FmpPackage.eINSTANCE.getTypedValue_FeatureValue();
		 							break;
		 						case ValueType.FLOAT:
		 							structuralFeature = FmpPackage.eINSTANCE.getTypedValue_FloatValue();
		 							break;
		 						case ValueType.INTEGER:
		 							structuralFeature = FmpPackage.eINSTANCE.getTypedValue_IntegerValue();
		 							break;
		 						case ValueType.STRING:
		 							structuralFeature = FmpPackage.eINSTANCE.getTypedValue_StringValue();
		 							break;
							}
		 					// need to make sure, typedValue and its owner have item providers, otherwise notifications won't work
		 					adapterFactory.adapt(typedValue, IEditingDomainItemProvider.class);
		 					adapterFactory.adapt(typedValue.eContainer(), IEditingDomainItemProvider.class);
		 					// when we set the name that was null
							if (RoleQuery.INSTANCE.getPropertyRole((Node)owner) == RoleQuery.NAME) {
								Feature properties = ModelNavigation.INSTANCE.navigateToRootFeature((Node) owner);
								Node describedNode = properties.getDescribedNode();
								Command setNameCommand = new SetCommand(editingDomain, describedNode, FmpPackage.eINSTANCE.getFeature_Name(), newValue);
								command = new CompoundCommand();
								((CompoundCommand)command).append(setNameCommand);
								String newId = NodeIdDictionary.INSTANCE.getIdForName((String) newValue);
								((CompoundCommand)command).append(new SetCommand(editingDomain, describedNode, FmpPackage.eINSTANCE.getNode_Id(), newId));
							}
							else
								command = new SetCommand(editingDomain, typedValue, structuralFeature, newValue);
						}
					}
				}
				else if (owner instanceof Reference) {
					ReferenceItemProvider itemProvider = (ReferenceItemProvider) adapterFactory.adapt(owner, IEditingDomainItemProvider.class);
					if (itemProvider != null)
						command = new SetCommand(editingDomain, owner, FmpPackage.eINSTANCE.getReference_Feature(), newValue);
				}
			}
		return command;
	}
	// Direct editing end
	public void remove(Node node, EditingDomain domain, CompoundCommand command, boolean appendOnly) {
		for(Iterator childrenIterator = node.getChildren().iterator(); childrenIterator.hasNext(); )
			remove((Node)childrenIterator.next(), domain, command, appendOnly);

		// remove the node
		if (command == null) {
			if (node.eContainingFeature() == FmpPackage.eINSTANCE.getNode_Children())
				((Node) node.eContainer()).getChildren().remove(node);
			else if (node.eContainingFeature() == FmpPackage.eINSTANCE.getFeature_Configurations())
				((Feature) node.eContainer()).getConfigurations().remove(node);
		}
		else
		{
			if(appendOnly)
				command.append(new RemoveCommand(domain, node.eContainer(), node.eContainingFeature(), node));
			else
				command.appendAndExecute(new RemoveCommand(domain, node.eContainer(), node.eContainingFeature(), node));
		}
		// remove properties
		Feature properties = node.getProperties();
		if (properties != null)
			remove(properties, domain, command, appendOnly);
	}
}