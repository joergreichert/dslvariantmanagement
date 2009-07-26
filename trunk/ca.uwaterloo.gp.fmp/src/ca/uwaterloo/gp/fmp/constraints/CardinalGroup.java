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


import java.util.Set;

/**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>
 */
public class CardinalGroup
{
	/**
	 * Represents the number of all elements in the feature elements.
	 */
	public static final int ALL_FEATURE_ELEMENTS_COUNT = -1;
	
	public static final String ALL_FEATURE_ELEMENTS_STR = "k";
	public static final String CARDINALITY_LEFT_BRACKET = "<";
	public static final String CARDINALITY_RIGHT_BRACKET = ">";
	public static final String CARDINALITY_SEPARATOR = "-";
		
	protected int minOccur;
	protected int maxOccur;
	protected Set groupElements;
	
	public CardinalGroup()
	{		
		
	}
	
	/**
	 * @param minOccur
	 * @param maxOccur
	 * @param scope
	 * @param groupElements
	 */
	public CardinalGroup(int minOccur, int maxOccur,
			Set groupElements) {
		super();
		this.minOccur = minOccur;
		this.maxOccur = maxOccur;
		this.groupElements = groupElements;
	}
	
	/**
	 * @return Returns the groupElements.
	 */
	public Set getGroupElements() {
		return groupElements;
	}
	/**
	 * @param groupElements The groupElements to set.
	 */
	public void setGroupElements(Set groupElements) {
		this.groupElements = groupElements;
	}
	/**
	 * @return Returns the maxOccur.
	 */
	public int getMaxOccur() {
		return maxOccur;
	}
	/**
	 * @param maxOccur The maxOccur to set.
	 */
	public void setMaxOccur(int maxOccur) {
		this.maxOccur = maxOccur;
	}
	/**
	 * @return Returns the minOccur.
	 */
	public int getMinOccur() {
		return minOccur;
	}
	/**
	 * @param minOccur The minOccur to set.
	 */
	public void setMinOccur(int minOccur) {
		this.minOccur = minOccur;
	}
	
	
	/**
	 * Returns a string representation.
	 * @author Peter
	 */
	public String toString()
	{
		String minOccurStr = (minOccur==ALL_FEATURE_ELEMENTS_COUNT) ? ALL_FEATURE_ELEMENTS_STR : String.valueOf(minOccur); 
		String maxOccurStr = (maxOccur==ALL_FEATURE_ELEMENTS_COUNT) ? ALL_FEATURE_ELEMENTS_STR : String.valueOf(maxOccur);
		
		String str = CARDINALITY_LEFT_BRACKET + String.valueOf(minOccurStr) + CARDINALITY_SEPARATOR + 
							String.valueOf(maxOccurStr) + CARDINALITY_RIGHT_BRACKET + groupElements.toString();
		
		return str;
	}

	/**
	 * Returns a flattened CardinalGroup with only elements (and no sets).
	 * Does not modify this CardinalGroup.
	 */
	public CardinalGroup getFlattenedCardinalGroup()
	{
		CardinalGroup flattenedCardinalGroup = new CardinalGroup();
		Set flattenedSet = new ConcreteSet(); 
		
		// Get flattened version of the current set - using recursive function
		ConcreteSet.flattenSet(groupElements, flattenedSet);
		
		// Set all the attributes
		flattenedCardinalGroup.setMinOccur(getMinOccur());
		flattenedCardinalGroup.setMaxOccur(getMaxOccur());
		flattenedCardinalGroup.setGroupElements(flattenedSet);
		
		return flattenedCardinalGroup;
	}
	
	/**
	 * Returns the actual min occur. If min occur is k, this will return
	 * the actual size of the group elements.
	 * @return
	 */
	public int getActualMinOccur()
	{
		return (minOccur == ALL_FEATURE_ELEMENTS_COUNT)? groupElements.size(): minOccur;
	}
	
	/**
	 * Returns the actual max occur. If max occur is k, this will return
	 * the actual size of the group elements.
	 * @return
	 */
	public int getActualMaxOccur()
	{
		return (maxOccur == ALL_FEATURE_ELEMENTS_COUNT)? groupElements.size(): maxOccur;
	}
	
	/**
	 * Determines whether or not this CardinalGroup is valid.
	 * (min, max validity)
	 */
	public boolean isValid()
	{
		boolean valid = false;
		
		int actualMinOccur = getActualMinOccur(); 
		int actualMaxOccur = getActualMaxOccur();
					
		valid = (((actualMinOccur >= 0) && (actualMinOccur <= groupElements.size()))
			&& ((actualMaxOccur >= 0) && (actualMaxOccur <= groupElements.size()))
			&& (actualMinOccur <= actualMaxOccur));
		
		return valid;
	}
	
	
	/*
	 * Evaluates this CardinalGroup, returning a result containing
	 * a value that represents a set with all the possible combinations of
	 * the group elements of this CardinalGroup as a subset.
	 */
	public Set evaluate()
	{
		Set set = new ConcreteSet();
		
		if(isValid())
		{
			// We will work on the flattened cardinal group.
			CardinalGroup flattenedCardinalGroup = getFlattenedCardinalGroup();
				
			/**
			 * Go through from min to max and add each set of combinations to the result set
			 */
			for(int i = flattenedCardinalGroup.getActualMinOccur(); i <= flattenedCardinalGroup.getActualMaxOccur(); i++)
			{
				Set currentSet = new ConcreteSet();
				// This will give you all the combinations of groupElements that are of size i
				ConcreteSet.clique(flattenedCardinalGroup.getGroupElements().toArray(), 0, 0, new Object[i], currentSet);
				
				set.add(currentSet);
			}
		}
		
		return set;
	}
}
