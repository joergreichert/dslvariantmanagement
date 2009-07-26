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

import java.util.Dictionary;
import java.util.Hashtable;

import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.constraints.JavaBDDConfigurator.JavaBDDConfigurator;


/**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>
 */
public class ConfiguratorDictionary
{
	public static ConfiguratorDictionary INSTANCE = new ConfiguratorDictionary();

	protected Dictionary dictionary = new Hashtable();
	
	
	/**
	 * Returns a configurator for the specified root feature.  Does NOT create one if one does not exist.
	 * @param obj
	 * @return
	 */
	public Configurator readOnlyGet(Object obj)
	{
		Configurator configurator = null;
		if(obj != null)			
		{
			configurator = (Configurator)dictionary.get(obj);
		}
		return configurator;
	}
	
	/**
	 * Returns a configurator for the specified root feature. Creates one if one does not exist.
	 * @param obj
	 * @return
	 */
	public Configurator get(Object obj)
	{
		Configurator configurator = null;
		if(obj != null)
		{
			configurator = (Configurator)dictionary.get(obj);
			if(configurator == null)
			{
				// create a new configurator, put it into ConfiguratorDictionary, 
				// and set the feature on the configurator, which will cause the construction
				// of the propositional formula and its compilation

				// ConfigIt Configurator REPLACED with JavaBDDConfigurator
//kmpietro      configurator = new ConfigitConfigurator();
				configurator = new JavaBDDConfigurator();
				configurator.setFeature((Feature)obj);
				dictionary.put(obj, configurator);			
			}
		}
		
		return configurator;
	}	
	
	/**
	 * This allows a configurator to be removed - so that one can be recreated
	 * (for example, when cloning has occurred, a new configurator is required, as the formula
	 * needs to be rewritten).
	 * @param obj
	 */
	public void remove(Object obj)
	{
		dictionary.remove(obj);
	}
}
