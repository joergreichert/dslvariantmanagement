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

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;

import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.Node;


/**
 * Michal: NodeIdDictionary is always recreated when FmpEditor loads the model (regardless if the model 
 * was just created by new model wizard or is an existing model).
 * 
 * Keeping the model and the NodeIdDictionary synchronized:
 * 1. create element 
 *	compute new id within the context. For feature, from name; for others use reference* and featureGroup* 
 * 	(with suffixes). Update NodeIdDictionary.
 * 2. remove element 
 *	remove ids of the element, of all its children and of all its configurations from the NodeIdDictionary 
 * 3. move element 
 *	a) within the tree of the same root -> do nothing, 
 *	b) to other tree -> remove element and than create it in the new location.
 * 4. clone feature 
 *	see. create element
 * 5. manually change id of element 
 *	remove element from dictionary, reinsert with new id. 
 *	update id of all 'confs' of element (synchronize change, otherwise rules won't work)
 * 
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>,
 *         Michal Antkiewicz <mantkiew@swen.uwaterloo.ca>
 */
public class NodeIdDictionary
{
	public static NodeIdDictionary INSTANCE = new NodeIdDictionary();

	// used to append numbers to generate the next id
	public static final String DELIMITER = "_";
	
	protected long currentId = 0;
	protected Dictionary dictionary = new Hashtable();
	
	
	public Dictionary getRootDictionary(Feature rootFeature)
	{
		Dictionary rootDictionary = (Dictionary)dictionary.get(rootFeature);
		if(rootDictionary == null)
		{
			rootDictionary = new Hashtable();
			dictionary.put(rootFeature, rootDictionary);
		}
		return rootDictionary;
	}
	
	public void putRootDictionary(Feature rootFeature, Dictionary rootDictionary)
	{
		dictionary.put(rootFeature, rootDictionary);
	}
	
	public Node getNode(Feature rootFeature, String id)
	{
		// Get the dictionary associated with the rootFeature (an id belongs to the dictionary of a given root)
		Dictionary rootFeatureDictionary = getRootDictionary(rootFeature);
		return (Node)rootFeatureDictionary.get(id);
	}
	
	public void putNode(Feature rootFeature, String id, Node node)
	{
		Dictionary rootFeatureDictionary = getRootDictionary(rootFeature);
		rootFeatureDictionary.put(id, node);
	}
	
	public String getNextAvailableId(String id, Node node)
	{
		// Below code doesn't work because _ is not recognized by XPath scanner
		//StringTokenizer st = new StringTokenizer(id, DELIMITER);
		//String newId = st.nextToken() + DELIMITER + String.valueOf(currentId++);
		String newId = null;
		int indexOfDigit = -1;
		for(int i = 0; i < id.length(); i++)
		{
			if(id.substring(i).matches("\\d{" + String.valueOf(id.length()-i) + "}"))
			{
				indexOfDigit = i;
				break;
			}
		}
		
		// if integer was found at the end, take it and replace it by adding 1 to it
		if(indexOfDigit != -1)
			newId = id.substring(0, indexOfDigit) + String.valueOf(Integer.parseInt(id.substring(indexOfDigit))+1);
		// otherwise, append a number at the end
		else
			newId = id + "0";
		
		return newId;
	}
	public void visit(Node node) {
		visit(null, node);
	}
	public void visit(String prefix, Node node)
	{
		if (node == null)
			throw new IllegalArgumentException("NodeIdDictionary.visit(): node must not be null");
		Feature rootFeature = ModelNavigation.INSTANCE.navigateToRootFeature(node);
		// TODO: maybe we should put IDs on features above root features
		if(rootFeature != null)
		{
			Dictionary rootFeatureDictionary = getRootDictionary(rootFeature);
			
			// Get an id that can be used in the construction of propositional formula
			String newId = ModelManipulation.INSTANCE.getValidId((prefix != null? prefix : "") + node.getId());
		
			// there is a node in the dictionary 
			while((Node)rootFeatureDictionary.get(newId) != null)
				newId = NodeIdDictionary.INSTANCE.getNextAvailableId(newId, node);
			
			node.setId(newId);
			rootFeatureDictionary.put(newId, node);	
		}
		
		for(Iterator nodeChildren = node.getChildren().iterator(); nodeChildren.hasNext();)
			visit(prefix, (Node)nodeChildren.next());
		
		if(node instanceof Feature)
		{
			for(Iterator configurationsIterator = ((Feature)node).getConfigurations().iterator(); configurationsIterator.hasNext();)
				visit(prefix, (Feature)configurationsIterator.next());
		}
	}

	/**
	 * Returns an id generated from a name - removes illegal characters
	 * @param string
	 * @return
	 */
	public String getIdForName(String name) { 		
		String id = "";
		
		for(int i = 0; i < name.length(); i++)
		{
			if(Character.isLetterOrDigit(name.charAt(i)))
				id += name.substring(i, i+1);
		}
		
		if(id.length() > 0)
		{
			// first character cannot be a digit
			if(Character.isDigit(id.charAt(0)))
				id = "a" + id;
		}
		else
			id = "feature";
		
		return id.substring(0,1).toLowerCase() + id.substring(1);
	}

	/**
	 * Michal: removes non-alphanumeric characters and
	 * ensures name doesn't start with a digit
	 */
	public String getJavaNameForName(String name) {
		StringBuffer newName = new StringBuffer();
		
		for(int i = 0; i < name.length(); i++)  {
			char c = name.charAt(i);
			if(Character.isLetterOrDigit(c))
				newName.append(c);
			else if (Character.isWhitespace(c))
				newName.append('_');
		}
		
		if(newName.length() > 0) {
			// first character cannot be a digit
			if(Character.isDigit(newName.charAt(0)))
				newName.insert(0, '_');
		}
		else
			newName.append("feature");
		return newName.toString();
	}

	/**
	 * Michal: remove existing dictionaries for the root feature and all of its configurations
	 * @param rootFeature
	 */
	public void removeDictionaries(Feature rootFeature) {
		putRootDictionary(rootFeature, new Hashtable());
		
		for (Iterator i = rootFeature.getConfigurations().iterator(); i.hasNext(); )
			removeDictionaries((Feature) i.next());
		
	}
}