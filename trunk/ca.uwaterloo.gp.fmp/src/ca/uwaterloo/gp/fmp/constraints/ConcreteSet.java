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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import ca.uwaterloo.gp.fmp.Clonable;




/**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>
 *
 * Concrete implementation of an Set.
 */
public class ConcreteSet implements Set
{
	/**
	 * String representation constants
	 */
	public static final String SET_LEFT_BRACE = "{";
	public static final String SET_RIGHT_BRACE = "}";
	
	/**
	 * Underneath implementation component of this concrete set.
	 * Ideally, this class should extend AbstractSet, but this was more
	 * difficult to do.
	 */
	protected List container;
	
	/**
	 * Constructs a concrete set from the given list
	 */
	public ConcreteSet(List list)
	{
		this();
		for(int i = 0; i < list.size(); i++)
		{
			container.add(list.get(i));
		}
	}
	
	/**
	 * Constructs a concrete set
	 */
	public ConcreteSet()
	{
		container = new Vector();
	}
	
	/* (non-Javadoc)
	 * @see java.util.Set#size()
	 */
	public int size() {
		// TODO Auto-generated method stub
		return container.size();
	}

	/* (non-Javadoc)
	 * @see java.util.Set#clear()
	 */
	public void clear() {
		// TODO Auto-generated method stub
		container.clear();
	}

	/* (non-Javadoc)
	 * @see java.util.Set#isEmpty()
	 */
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return container.isEmpty();
	}

	/* (non-Javadoc)
	 * @see java.util.Set#toArray()
	 */
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return container.toArray();
	}

	/* (non-Javadoc)
	 * @see java.util.Set#add(java.lang.Object)
	 */
	public boolean add(Object arg0) {
		// TODO Auto-generated method stub
		return container.add(arg0);
	}

	/* (non-Javadoc)
	 * @see java.util.Set#contains(java.lang.Object)
	 */
	public boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		return container.contains(arg0);
	}

	/* (non-Javadoc)
	 * @see java.util.Set#remove(java.lang.Object)
	 */
	public boolean remove(Object arg0) {
		// TODO Auto-generated method stub
		return container.remove(arg0);
	}

	/* (non-Javadoc)
	 * @see java.util.Set#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection arg0) {
		// TODO Auto-generated method stub
		return container.addAll(arg0);
	}

	/* (non-Javadoc)
	 * @see java.util.Set#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection arg0) {
		// TODO Auto-generated method stub
		return container.containsAll(arg0);
	}

	/* (non-Javadoc)
	 * @see java.util.Set#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection arg0) {
		// TODO Auto-generated method stub
		return container.removeAll(arg0);
	}

	/* (non-Javadoc)
	 * @see java.util.Set#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection arg0) {
		// TODO Auto-generated method stub
		return container.retainAll(arg0);
	}

	/* (non-Javadoc)
	 * @see java.util.Set#iterator()
	 */
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return container.iterator();
	}

	/* (non-Javadoc)
	 * @see java.util.Set#toArray(java.lang.Object[])
	 */
	public Object[] toArray(Object[] arg0) {
		// TODO Auto-generated method stub
		return container.toArray(arg0);
	}
	
	/**
	 * Given a set to be flattened, puts the flattened elements in flattened set
	 */
	public static void flattenSet(Set setToBeFlattened, Set flattenedSet)
	{
		Iterator iterator = setToBeFlattened.iterator();
		while(iterator.hasNext())
		{
			Object obj = iterator.next();
			if(obj instanceof Set)
			{
				flattenSet((Set)obj, flattenedSet);
			}
			else
			{
				flattenedSet.add(obj);
			}
		}
	}
	
	/**
	 * Source taken from a Java forum. Search for "combinatorial Java" in Google.
	 * Slightly modified.
	 * @param things
	 * @param i
	 * @param j
	 * @param chosen
	 */
	public static void clique(Object[] things, int i, int j, Object[] chosen, Set resultingCombinations)
	{
		if (i == chosen.length)
		{
			// We will return set of sets.
			Set combination = new ConcreteSet();			
			for (int k = 0; k < chosen.length; k++)
			{
				combination.add(chosen[k]);
			}
			resultingCombinations.add(combination);
		}
		else
		{
			for (int k = j; k <= (things.length - chosen.length) + i; k++)
			{
				chosen[i] = things[k];
				clique(things, i + 1, k + 1, chosen, resultingCombinations);
			}
		}
	}
	
	/**
	 * Overriden.
	 */
	public String toString()
	{
		String str = SET_LEFT_BRACE;
		
		Iterator iterator = iterator();
		while(iterator.hasNext())
		{
			Object obj = iterator.next();
			if(obj instanceof Clonable)
			{
				str += ((Clonable)obj).getId();
			}
			else
			{
				str += obj.toString();
			}
			str += ", ";
		}
		
		if(size() > 0) {
			str = str.substring(0, str.length()-2) + SET_RIGHT_BRACE;
		} else {
			str = str + " " + SET_RIGHT_BRACE;
		}
		
		return str;
	}

}
