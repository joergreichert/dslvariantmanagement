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
package ca.uwaterloo.gp.fmp.constraints;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import ca.uwaterloo.gp.fmp.Clonable;
import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.FeatureGroup;
import ca.uwaterloo.gp.fmp.Node;
import ca.uwaterloo.gp.fmp.system.RoleQuery;


/**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>
 */
public class ModelToPropositionTranslator
{
	// Symbols
	public static final String EQUALS = " = ";
	public static final String IMPLIES = " -> ";
	public static final String AND = " and\n ";
	public static final String OR = " or ";
	public static final String XOR = " xor ";
	public static final String NOT = "not";
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public static final String EOL = ";";
	
	protected PropositionalFormula formula;
	
	/**
	 * Translates the given feature model to a propositional formula according to Batory's approach
	 * @param feature
	 * @return
	 */
	public PropositionalFormula translate(Feature feature, boolean includeAllNodes)
	{
		formula = new PropositionalFormula();
		formula.setRules(visitNode(feature, includeAllNodes) + ";");
		
		return formula;
	}
	
	
	/**
	 * Visits a node in the starting model and constructs a boolean expression for it
	 * @param obj
	 * @return
	 */
	protected String visitNode(Node obj, boolean includeAllNodes)
	{
		String rulePortion = "";
		
		// we do not consider eliminated trees
		int min = -2;
		int max = -2;
		if(obj instanceof Clonable)
		{
			int[] cardinality = ((Clonable)obj).getCheckboxViewCardinality();
			min = cardinality[0];
			max = cardinality[1];
		}
		else
		{
			min = obj.getMin();
			max = obj.getMax();
		}
	
		if(!((min == 0) && (max == 0)) || (obj instanceof FeatureGroup) || includeAllNodes)
		{
			List children = new Vector();
			int nodeType = RoleQuery.INSTANCE.getNodeType(obj);
			
			// if we're including all nodes, then add this node as a variable if it's not a feature group
			if(includeAllNodes && !(obj instanceof FeatureGroup)) {
				addVariable(obj.getId());
			}
			
			if(nodeType == RoleQuery.ROOT_FEATURE)
			{
				addVariable(obj.getId());
				rulePortion += "(" + obj.getId() + EQUALS + TRUE + ")" + AND;
				children = obj.getChildren();
			}
			else if((nodeType == RoleQuery.SOLITARY_FEATURE) || (nodeType == RoleQuery.SOLITARY_REFERENCE))
			{
				// optional
				if((min == 0) && (max == 1))
				{
					addVariable(obj.getId());
					rulePortion += "(" + obj.getId() + IMPLIES + getAncestorVariableNodeID(obj) + ")" + AND;
				}			
							
				children = obj.getChildren();
			}
			else if(nodeType == RoleQuery.FEATURE_GROUP)
			{
				// CardinalGroup is a construct used to enumerate combinations with <min-max> cardinality on elements
				FeatureGroup featureGroup = (FeatureGroup)obj;
				int mandatoryFeaturesSize = 0;
				List optionalChildren = new Vector();
				CardinalGroup cardinalGroup = new CardinalGroup(-1, -1, new ConcreteSet());
				String ancestorVariableId = getAncestorVariableNodeID(featureGroup);
				String implicationPortion = "(";
				String emptyGroup = "(";
				
				for(int i = 0; i < featureGroup.getChildren().size(); i++)
				{	
					Clonable groupedElement = (Clonable)featureGroup.getChildren().get(i);

					// we'll go through all the children 
					children.add(groupedElement);
					
					// but we'll only permute the optional grouped features
					if((groupedElement.getMin() == 0) && (groupedElement.getMax() == 1))
					{
						addVariable(groupedElement.getId());					 
						cardinalGroup.getGroupElements().add(groupedElement.getId());
						optionalChildren.add(groupedElement);
						implicationPortion += "(" + groupedElement.getId() + IMPLIES + ancestorVariableId + ")" + AND;
						emptyGroup += "(" + groupedElement.getId() + EQUALS + FALSE + ")" + AND;
					}
					else if((groupedElement.getMin() == 1) && (groupedElement.getMax() == 1))
					{
						mandatoryFeaturesSize++;
					}
				}		
				implicationPortion = removeTrailer(implicationPortion, AND) + ")";
				emptyGroup = removeTrailer(emptyGroup, AND) + ")";
								
				int groupMin = Math.min(Math.max(0, featureGroup.getMin()-mandatoryFeaturesSize), optionalChildren.size());
				int groupMax = Math.min(Math.max(0, featureGroup.getMax()-mandatoryFeaturesSize), optionalChildren.size());
				cardinalGroup.setMinOccur(groupMin);
				cardinalGroup.setMaxOccur(groupMax);
							
				// only permute grouped features if the group cardinality is not <0-0>
				if(!((groupMin == 0) && (groupMax == 0)))
				{
					String groupPortion = "(";
										
					// Go through each set of combinations, starting with the set of combo for min, then min+1, ..., max
					// and construct the boolean expressions					
					for(Iterator iterator = cardinalGroup.evaluate().iterator(); iterator.hasNext();)
					{
						Set currentCardinalSet = (Set)iterator.next();						
						groupPortion += "(";
												
						// subsets in currentCardinalSet are ORed, but elements within the subset are ANDed
						Iterator currentCardinalSetIterator = currentCardinalSet.iterator();
						while(currentCardinalSetIterator.hasNext())
						{
							Set subset = (Set)currentCardinalSetIterator.next();
							groupPortion += "(";
							
							for(int childIndex = 0; childIndex < optionalChildren.size(); childIndex++)
							{
								String groupedFeatureID = ((Node)optionalChildren.get(childIndex)).getId();
								// include the grouped feature if it is part of the subset
								if(subset.contains(groupedFeatureID))
								{
									groupPortion += groupedFeatureID + EQUALS + TRUE + AND;
								}
								else
								{							
									groupPortion += groupedFeatureID + EQUALS + FALSE + AND;
								}
							}
							groupPortion = removeTrailer(groupPortion, AND) + ")" + OR;
						}							
						groupPortion = removeTrailer(groupPortion, OR) + ")" + OR;
					}					
					groupPortion = removeTrailer(groupPortion, OR) + ")";					
					rulePortion += "(" + implicationPortion + AND + 
								"(" + groupPortion + OR + "(" + ancestorVariableId + EQUALS + FALSE + AND + emptyGroup + ")))" + AND; 					
				}
			}
			else if((nodeType == RoleQuery.GROUPED_FEATURE) || (nodeType == RoleQuery.GROUPED_REFERENCE))
			{
				children = obj.getChildren();
			}
			
			// Determine expression for children nodes and AND them
			if(children.size() > 0)
			{			
				int i;
				for(i = 0; i < children.size(); ++i)
				{
					String childRulePortion = visitNode((Node)children.get(i), includeAllNodes);
					// only add to string if there is something
					if(!childRulePortion.equals("")) {
						rulePortion += childRulePortion + AND;
					}
				}
			}
			// remove the last AND
			rulePortion = removeTrailer(rulePortion, AND);
		}
						
		return rulePortion;
	}

	
	/**
	 * Returns the ID of the closest ancestor node that is variable or that is the root 
	 */
	private String getAncestorVariableNodeID(Node node)
	{
		Node curNode = (Node)node.eContainer();
		String ancestorID = "";
		int curNodeType = -1;
		while((curNode != null) && ((curNodeType=RoleQuery.INSTANCE.getNodeType(curNode)) != RoleQuery.FEATURE_MODEL))
		{
			if(curNodeType == RoleQuery.ROOT_FEATURE)
			{
				ancestorID = curNode.getId();
				break;
			}
			else if(curNode instanceof Clonable)
			{
				if(((Clonable)curNode).isOptional())
				{
					ancestorID = curNode.getId();
					break;
				}
			}			
				
			curNode = (Node)curNode.eContainer();
		}
		
		return ancestorID;
	}
	
	/**
	 * Removes trailing string if it exists
	 */
	public static final String removeTrailer(String str, String trailingStr)
	{
		String value = str;
		if(str.endsWith(trailingStr))
		{
			value = str.substring(0, str.length() - trailingStr.length());			
		}
		return value;
	}
	
	/**
	 * Adds a variable to "variables", first checking if it exists 
	 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>
	 * 
	 */
	private void addVariable(Object variable)
	{
		boolean variableFound = false;
		
		for(Iterator variablesIterator = formula.getVariables().iterator(); variablesIterator.hasNext();)
		{
			if(variablesIterator.next().equals(variable))
			{
				variableFound = true;
				break;
			}
		}
		
		if(!variableFound) {
			formula.getVariables().add(variable);
		}			
	}
}
