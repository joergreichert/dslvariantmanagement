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

import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.FeatureGroup;
import ca.uwaterloo.gp.fmp.FmpPackage;
import ca.uwaterloo.gp.fmp.Node;
import ca.uwaterloo.gp.fmp.Project;
import ca.uwaterloo.gp.fmp.Reference;
import ca.uwaterloo.gp.fmp.provider.fmpEditPlugin;

/**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>,
 *         Michal Antkiewicz <mantkiew@swen.uwaterloo.ca>
 */
public class RoleQuery {
	public static final RoleQuery INSTANCE = new RoleQuery();
	// LOCATION TYPES
	public static final int CONFIGURATION = 0;
	public static final int CONFIGURATION_PROPERTIES = 1;
	public static final int MODEL = 2; 
	public static final int MODEL_PROPERTIES = 3;
	public static final int METAMODEL = 4;	
	public static final int METAMODEL_PROPERTIES = 5;
	public static final int METAMETAMODEL = 6;
	
	// ELEMENT TYPE
	public static final int UNDEFINED = -1;
	public static final int ROOT_FEATURE = 0;
	public static final int SOLITARY_FEATURE = 1;
	public static final int GROUPED_FEATURE = 2;
	public static final int SOLITARY_REFERENCE = 3;
	public static final int GROUPED_REFERENCE = 4;
	public static final int FEATURE_GROUP = 5;
	public static final int FEATURE_MODEL = 6;
	
	// PROPERTY ROLE
	public static final int NONE = -1;
	public static final int NAME = 0;
	public static final int ID = 1;
	public static final int MIN_OCCUR = 2;
	public static final int MAX_OCCUR = 3;
	public static final int DESCRIPTION = 4;
	public static final int ATTRIBUTE = 5;
	public static final int TYPE_GROUP = 6;
	public static final int TYPE = 7;
	public static final int VALUE = 8;
	public static final int DEFAULT_VALUE = 9;
	public static final int REFERENCED_FEATURE = 10;
	
	
	
	public int getLocationType(Node node)
	{
		if (node == null)
			throw new IllegalArgumentException("RoleQuery.getLocationType(): node must not be null");
		
		int locationType = -1;

		if(node.getProperties() == null)
		{	
			Node rootFeatureOrigin = ModelNavigation.INSTANCE.navigateToRootFeature(node).getOrigin();
			if(rootFeatureOrigin == null)
				locationType = METAMETAMODEL;
			else
			{
				if(rootFeatureOrigin.eContainer().eContainingFeature() == FmpPackage.eINSTANCE.getProject_MetaMetaModel())
					locationType = METAMODEL_PROPERTIES;
				else if(rootFeatureOrigin.eContainer().eContainingFeature() == FmpPackage.eINSTANCE.getProject_MetaModel())
					locationType = MODEL_PROPERTIES;
				else
					locationType = CONFIGURATION_PROPERTIES;
			}
		}
		else
		{
			Node propertiesOrigin = node.getProperties().getOrigin();
			if(propertiesOrigin.eContainer().eContainingFeature() == FmpPackage.eINSTANCE.getProject_MetaMetaModel())
				locationType = METAMODEL;
			else if(propertiesOrigin.eContainer().eContainingFeature() == FmpPackage.eINSTANCE.getProject_MetaModel())
				locationType = MODEL;
			else
				locationType = CONFIGURATION;
		}
		
		return locationType;
	}
	
	public boolean isInConfiguration(Node node)
	{
		if (node == null)
			throw new IllegalArgumentException("RoleQuery.isInConfiguration(): node must not be null");
		
		return (node.getOrigin() != null);
	}
	
	public int getNodeType(Node node)
	{
		if (node == null)
			throw new IllegalArgumentException("RoleQuery.getNodeType(): node must not be null");
		
		int nodeType = UNDEFINED;
		if (node instanceof Feature && node.eContainer() instanceof Project)
			nodeType = FEATURE_MODEL;
		else if((node.eContainer() != null && node.eContainer().eContainer() instanceof Project) ||
		   (node instanceof Feature && node.eContainingFeature() == FmpPackage.eINSTANCE.getFeature_Configurations()))
		    // this is ok if we want any feature with configurations look like a root feature
			// || (node instanceof Feature && ((Feature) node).getConfigurations().size() > 0))
			nodeType = ROOT_FEATURE;
		else if(node instanceof Feature && ((Feature)node).eContainer() instanceof FeatureGroup)
			nodeType = GROUPED_FEATURE;
		else if(node instanceof Feature && ((Feature)node).eContainer() instanceof Feature)
			nodeType = SOLITARY_FEATURE;
		else if(node instanceof Reference && ((Reference)node).eContainer() instanceof FeatureGroup)
			nodeType = GROUPED_REFERENCE;
		else if(node instanceof Reference && ((Reference)node).eContainer() instanceof Feature)
			nodeType = SOLITARY_REFERENCE;
		else if(node instanceof FeatureGroup)
			nodeType = FEATURE_GROUP;
		return nodeType;
	}
	/**
	 * Michal: this assumes location type = *PROPERTIES
	 * When location type != *PROPERTIES function recursively goes up to Project and causes class cast exception
	 */
	public int getPropertyRole(Node node)
	{
		if (node == null)
			throw new IllegalArgumentException("RoleQuery.getPropertyRole(): node must not be null");
		
		int propertyRole = NONE;
		
		try {
			if(node instanceof Feature && ((Feature)node).getId().startsWith("min"))
				propertyRole = MIN_OCCUR;
			else if(node instanceof Feature && ((Feature)node).getId().startsWith("max"))
				propertyRole = MAX_OCCUR;
			else if(node instanceof Feature && ((Feature)node).getId().startsWith("name"))
				propertyRole = NAME;
			else if(node instanceof Feature && ((Feature)node).getId().startsWith("description"))
				propertyRole = DESCRIPTION;
			else if(node instanceof Feature && ((Feature)node).getId().startsWith("attribute"))
				propertyRole = ATTRIBUTE;
			else if(node instanceof FeatureGroup && ((Node)node.eContainer()).getId().startsWith("attribute"))
				propertyRole = TYPE_GROUP;
			else if(node instanceof Feature && node.eContainer() instanceof FeatureGroup && getPropertyRole((Node)node.eContainer()) == TYPE_GROUP)
				propertyRole = TYPE;
			else if(node instanceof Feature && ((Feature)node).getId().startsWith("value"))
				propertyRole = VALUE;
			else if(node instanceof Feature && ((Feature)node).getId().startsWith("default"))
				propertyRole = DEFAULT_VALUE;
			else if (node instanceof Feature && ((Feature)node).getId().startsWith("referencedFeature"))
				propertyRole = REFERENCED_FEATURE;
			else if (node instanceof Feature && ((Feature)node).getId().startsWith("id"))
				propertyRole = ID;
		}
		catch (Exception e) {
			fmpEditPlugin.INSTANCE.log("getPropertyRole for " + node.toString() + " whose parent is: " + node.eContainer());
			e.printStackTrace();
		}
		
		return propertyRole;
	}
}


