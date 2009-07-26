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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import ca.uwaterloo.gp.fmp.ConfigState;
import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.FeatureGroup;
import ca.uwaterloo.gp.fmp.FmpFactory;
import ca.uwaterloo.gp.fmp.Project;
import ca.uwaterloo.gp.fmp.Reference;
import ca.uwaterloo.gp.fmp.TypedValue;
import ca.uwaterloo.gp.fmp.ValueType;
import ca.uwaterloo.gp.fmp.impl.FmpFactoryImpl;



/**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>
 */
public class MetaModel
{	
	public static Project makeProject()
	{
		Project project = FmpFactory.eINSTANCE.createProject();		
		project.setMetaMetaModel(MetaModel.makeMetaMetaModel(project));
		project.setMetaModel(MetaModel.makeMetaModel(project));
		project.setModel(MetaModel.makeModel(project));
		return project;
	}
	public static Feature makeModel(Project project)
	{
		Feature metaModel = project.getMetaModel();

		Feature metaFeature = (Feature) metaModel.getChildren().get(0);
		//Feature metaFeatureGroup = (Feature)metaModel.getChildren().get(1);
		//Feature metaReference = (Feature) metaModel.getChildren().get(2);
	
		Feature model = FmpFactory.eINSTANCE.createFeature();
		model.setProperties(ModelManipulation.INSTANCE.configure(metaFeature));
		setFeatureAttributes(model, "My Feature Model", "My Feature Model", ConfigState.UNDECIDED_LITERAL, ValueType.NONE_LITERAL, null, null, 1, 1);
		
		return model;
	}
	public static Feature makeMetaModel(Project project)
	{
		Feature metaMetaModel = project.getMetaMetaModel();
		// Root of the metamodel
		Feature metaModel = createFeatureWithProperties("MetaModel", "MetaModel", ConfigState.UNDECIDED_LITERAL, ValueType.NONE_LITERAL, null, null, 1, 1, metaMetaModel);
		
		// this block creates "Feature" meta
		{
			Feature metaFeature = createFeatureWithProperties("Feature", "Feature", ConfigState.UNDECIDED_LITERAL, ValueType.NONE_LITERAL, null, null, 1, 1, metaMetaModel);
			metaModel.getChildren().add(metaFeature);
			
			Feature nameOfFeature = createFeatureWithProperties("Name", "Name", ConfigState.UNDECIDED_LITERAL, ValueType.STRING_LITERAL, null, null, 1, 1, metaMetaModel);
			metaFeature.getChildren().add(nameOfFeature);
			
			Feature idOfFeature = createFeatureWithProperties("Id", "Id", ConfigState.UNDECIDED_LITERAL, ValueType.STRING_LITERAL, null, "featureVar", 1, 1, metaMetaModel);
			metaFeature.getChildren().add(idOfFeature);
			
			Feature descOfFeature = createFeatureWithProperties("Description", "Description", ConfigState.UNDECIDED_LITERAL, ValueType.STRING_LITERAL, null, null, 0, 1, metaMetaModel);
			metaFeature.getChildren().add(descOfFeature);
			
			Feature attributeOfFeature = createFeatureWithProperties("Attribute", "Attribute", ConfigState.UNDECIDED_LITERAL, ValueType.NONE_LITERAL, null, null, 0, 1, metaMetaModel);
			metaFeature.getChildren().add(attributeOfFeature);
			FeatureGroup typeGroup = createFeatureGroupWithProperties("TypeGroup", 0, 1, metaMetaModel); 
			attributeOfFeature.getChildren().add(typeGroup);
			typeGroup.getChildren().add(createTypeOfFeatureInMetaModel("Integer", ValueType.INTEGER_LITERAL, metaMetaModel));
			typeGroup.getChildren().add(createTypeOfFeatureInMetaModel("Float", ValueType.FLOAT_LITERAL, metaMetaModel));
			typeGroup.getChildren().add(createTypeOfFeatureInMetaModel("String", ValueType.STRING_LITERAL, metaMetaModel));
			typeGroup.getChildren().add(createTypeOfFeatureInMetaModel("Feature", ValueType.FEATURE_LITERAL, metaMetaModel));
			
			Feature minOfFeature = createFeatureWithProperties("Min", "Min", ConfigState.UNDECIDED_LITERAL, ValueType.INTEGER_LITERAL, null, new Integer(0), 1, 1, metaMetaModel);
			metaFeature.getChildren().add(minOfFeature);
			
			Feature maxOfFeature = createFeatureWithProperties("Max", "Max", ConfigState.UNDECIDED_LITERAL, ValueType.INTEGER_LITERAL, null, new Integer(1), 1, 1, metaMetaModel);
			metaFeature.getChildren().add(maxOfFeature);
		}

		// this block creates "FeatureGroup" meta
		{
			Feature metaFeatureGroup = createFeatureWithProperties("FeatureGroup", "FeatureGroup", ConfigState.UNDECIDED_LITERAL, ValueType.NONE_LITERAL, null, null, 1, 1, metaMetaModel);
			metaModel.getChildren().add(metaFeatureGroup);
			
			Feature idOfFeatureGroup = createFeatureWithProperties("Id", "Id", ConfigState.UNDECIDED_LITERAL, ValueType.STRING_LITERAL, null, "group", 1, 1, metaMetaModel);
			metaFeatureGroup.getChildren().add(idOfFeatureGroup);

			Feature minOfFeatureGroup = createFeatureWithProperties("Min", "Min", ConfigState.UNDECIDED_LITERAL, ValueType.INTEGER_LITERAL, null, new Integer(1), 1, 1, metaMetaModel);
			metaFeatureGroup.getChildren().add(minOfFeatureGroup);
			
			Feature maxOfFeatureGroup = createFeatureWithProperties("Max", "Max", ConfigState.UNDECIDED_LITERAL, ValueType.INTEGER_LITERAL, null, new Integer(1), 1, 1, metaMetaModel);
			metaFeatureGroup.getChildren().add(maxOfFeatureGroup);
		}
		
		// this block creates "Reference" meta
		{
			Feature metaReference = createFeatureWithProperties("Reference", "Reference", ConfigState.UNDECIDED_LITERAL, ValueType.NONE_LITERAL, null, null, 1, 1, metaMetaModel);
			metaModel.getChildren().add(metaReference);

			Reference referenceOfReference = createReferenceWithProperties(null, "Reference", ConfigState.UNDECIDED_LITERAL, 1, 1, metaMetaModel);
			metaReference.getChildren().add(referenceOfReference);
			
			Feature idOfReference = createFeatureWithProperties("Id", "Id", ConfigState.UNDECIDED_LITERAL, ValueType.STRING_LITERAL, null, "reference", 1, 1, metaMetaModel);
			metaReference.getChildren().add(idOfReference);
			
			Feature minOfReference = createFeatureWithProperties("Min", "Min", ConfigState.UNDECIDED_LITERAL, ValueType.INTEGER_LITERAL, null, new Integer(0), 1, 1, metaMetaModel);
			metaReference.getChildren().add(minOfReference);
			
			Feature maxOfReference = createFeatureWithProperties("Max", "Max", ConfigState.UNDECIDED_LITERAL, ValueType.INTEGER_LITERAL, null, new Integer(1), 1, 1, metaMetaModel);
			metaReference.getChildren().add(maxOfReference);
		}
    	
		return metaModel;		
	}
	
	public static Feature createTypeOfFeatureInMetaModel(String name, ValueType valueType, Feature metaMetaModel)
	{
		Feature typeOfFeature = createFeatureWithProperties(name, name, ConfigState.UNDECIDED_LITERAL, ValueType.NONE_LITERAL, null, null, 0, 1, metaMetaModel);
		Feature valueOfFeature = createFeatureWithProperties("Value", "Value", ConfigState.UNDECIDED_LITERAL, valueType, null, null, 0, 1, metaMetaModel);
		Feature defaultValueOfFeature = createFeatureWithProperties("Default Value", "DefaultValue", ConfigState.UNDECIDED_LITERAL, valueType, null, null, 0, 1, metaMetaModel);
		typeOfFeature.getChildren().add(valueOfFeature);
		typeOfFeature.getChildren().add(defaultValueOfFeature);
		
		return typeOfFeature;
	}
	
	public static Feature makeMetaMetaModel(Project project)
	{
		// Root of the metametamodel
		Feature metaMetaModel = createFeatureWithoutProperties("MetaMetaModel", "MetaMetaModel", ConfigState.UNDECIDED_LITERAL, ValueType.NONE_LITERAL, null, 1, 1);
		
		// this block creates "Feature" metameta
		{
			Feature metaMetaFeature = createFeatureWithoutProperties("Feature", "Feature", ConfigState.UNDECIDED_LITERAL, ValueType.NONE_LITERAL, null, 1, 1);
			metaMetaModel.getChildren().add(metaMetaFeature);
			
			Feature nameOfFeature = createFeatureWithoutProperties("Name", "Name", ConfigState.UNDECIDED_LITERAL, ValueType.STRING_LITERAL, null, 1, 1);
			metaMetaFeature.getChildren().add(nameOfFeature);
			
			Feature idOfFeature = createFeatureWithoutProperties("Id", "Id", ConfigState.UNDECIDED_LITERAL, ValueType.STRING_LITERAL, null, 1, 1);
			metaMetaFeature.getChildren().add(idOfFeature);
			
			Feature descOfFeature = createFeatureWithoutProperties("Description", "Description", ConfigState.UNDECIDED_LITERAL, ValueType.STRING_LITERAL, null, 0, 1);
			metaMetaFeature.getChildren().add(descOfFeature);
			
			Feature attributeOfFeature = createFeatureWithoutProperties("Attribute", "Attribute", ConfigState.UNDECIDED_LITERAL, ValueType.NONE_LITERAL, null, 0, 1);
			metaMetaFeature.getChildren().add(attributeOfFeature);
			FeatureGroup typeGroup = createFeatureGroupWithoutProperties("TypeGroup", 0, 1); 
			attributeOfFeature.getChildren().add(typeGroup);
			typeGroup.getChildren().add(createTypeOfFeatureInMetaMetaModel("Integer", ValueType.INTEGER_LITERAL));
			typeGroup.getChildren().add(createTypeOfFeatureInMetaMetaModel("Float", ValueType.FLOAT_LITERAL));
			typeGroup.getChildren().add(createTypeOfFeatureInMetaMetaModel("String", ValueType.STRING_LITERAL));
			typeGroup.getChildren().add(createTypeOfFeatureInMetaMetaModel("Feature", ValueType.FEATURE_LITERAL));
			
			Feature minOfFeature = createFeatureWithoutProperties("Min", "Min", ConfigState.UNDECIDED_LITERAL, ValueType.INTEGER_LITERAL, new Integer(1), 1, 1);
			metaMetaFeature.getChildren().add(minOfFeature);
			
			Feature maxOfFeature = createFeatureWithoutProperties("Max", "Max", ConfigState.UNDECIDED_LITERAL, ValueType.INTEGER_LITERAL, new Integer(1), 1, 1);
			metaMetaFeature.getChildren().add(maxOfFeature);
		}

		// this block creates "FeatureGroup" metameta
		{
			Feature metaMetaFeatureGroup = createFeatureWithoutProperties("FeatureGroup", "FeatureGroup", ConfigState.UNDECIDED_LITERAL, ValueType.NONE_LITERAL, null, 1, 1);
			metaMetaModel.getChildren().add(metaMetaFeatureGroup);

			Feature idOfFeatureGroup = createFeatureWithoutProperties("Id", "Id", ConfigState.UNDECIDED_LITERAL, ValueType.STRING_LITERAL, null, 1, 1);
			metaMetaFeatureGroup.getChildren().add(idOfFeatureGroup);
			
			Feature minOfFeatureGroup = createFeatureWithoutProperties("Min", "Min", ConfigState.UNDECIDED_LITERAL, ValueType.INTEGER_LITERAL, new Integer(1), 1, 1);
			metaMetaFeatureGroup.getChildren().add(minOfFeatureGroup);
			
			Feature maxOfFeatureGroup = createFeatureWithoutProperties("Max", "Max", ConfigState.UNDECIDED_LITERAL, ValueType.INTEGER_LITERAL, new Integer(1), 1, 1);
			metaMetaFeatureGroup.getChildren().add(maxOfFeatureGroup);
		}
		
		// this block creates "Reference" metameta
		{
			Feature metaMetaReference = createFeatureWithoutProperties("Reference", "Reference", ConfigState.UNDECIDED_LITERAL, ValueType.NONE_LITERAL, null, 1, 1);
			metaMetaModel.getChildren().add(metaMetaReference);

			Reference referenceOfReference = createReferenceWithoutProperties(null, "Reference", ConfigState.UNDECIDED_LITERAL, 1, 1);
			metaMetaReference.getChildren().add(referenceOfReference);
			
			Feature idOfReference = createFeatureWithoutProperties("Id", "Id", ConfigState.UNDECIDED_LITERAL, ValueType.STRING_LITERAL, null, 1, 1);
			metaMetaReference.getChildren().add(idOfReference);
			
			Feature minOfReference = createFeatureWithoutProperties("Min", "Min", ConfigState.UNDECIDED_LITERAL, ValueType.INTEGER_LITERAL, new Integer(1), 1, 1);
			metaMetaReference.getChildren().add(minOfReference);
			
			Feature maxOfReference = createFeatureWithoutProperties("Max", "Max", ConfigState.UNDECIDED_LITERAL, ValueType.INTEGER_LITERAL, new Integer(1), 1, 1);
			metaMetaReference.getChildren().add(maxOfReference);
		}
    	
		return metaMetaModel;
	}	
	
	public static Feature createTypeOfFeatureInMetaMetaModel(String name, ValueType valueType)
	{
		Feature typeOfFeature = createFeatureWithoutProperties(name, name, ConfigState.UNDECIDED_LITERAL, ValueType.NONE_LITERAL, null, 0, 1);
		Feature valueOfFeature = createFeatureWithoutProperties("Value", "Value", ConfigState.UNDECIDED_LITERAL, valueType, null, 0, 1);
		Feature defaultValueOfFeature = createFeatureWithoutProperties("Default Value", "DefaultValue", ConfigState.UNDECIDED_LITERAL, valueType, null, 0, 1);
		typeOfFeature.getChildren().add(valueOfFeature);
		typeOfFeature.getChildren().add(defaultValueOfFeature);
		
		return typeOfFeature;
	}
	
	public static Feature createFeatureWithoutProperties(String name, String id, ConfigState state, ValueType valueType, Object value, int min, int max)
	{
		Feature feature = FmpFactory.eINSTANCE.createFeature();
		// default value is only kept in properties, therefore null
		setFeatureAttributes(feature, name, id, state, valueType, value, null, min, max);
		
		return feature;
	}
	
	public static Feature createFeatureWithProperties(String name, String id, ConfigState state, ValueType valueType, Object value, Object defaultValue, int min, int max, Feature metaMetaModel)
	{
		Feature feature = FmpFactory.eINSTANCE.createFeature();
		Feature metaMetaFeature = (Feature)metaMetaModel.getChildren().get(0);
		feature.setProperties(ModelManipulation.INSTANCE.configure(metaMetaFeature));
		setFeatureAttributes(feature, name, id, state, valueType, value, defaultValue, min, max);
		
		return feature;
	}
	
	/**
	 * Set feature attributes using the specified values.
	 * When setting the value type, this function also sets the checkboxes underneath "Attribute" node
	 * appropriately.  Ideally, this should be done using choice propagation, as there is a feature group constraint
	 * underneath the "Attribute" node.  However, because we use lazy construction of configurator, enabling
	 * choice propagation is in efficient when creating so many configurations of metameta and meta features.
	 * So we select and eliminate the type features in the properties view manually.
	 * 
	 * @param feature
	 * @param name
	 * @param id
	 * @param state
	 * @param valueType
	 * @param value
	 * @param min
	 * @param max
	 */
	public static void setFeatureAttributes(Feature feature, String name, String id, ConfigState state, ValueType valueType, Object value, Object defaultValue, int min, int max)
	{
		feature.setName(name);
		feature.setId(id);
		feature.setState(state);
		feature.setValueType(valueType);	
		
		if (valueType != ValueType.NONE_LITERAL)
		{			
			TypedValue typedValue = null;
			if(value != null) {
				typedValue = FmpFactory.eINSTANCE.createTypedValue();
				if(value instanceof Integer)
					typedValue.setIntegerValue((Integer)value);
				else if(value instanceof Float)
					typedValue.setFloatValue((Float)value);
				else if(value instanceof String)
					typedValue.setStringValue((String)value);
				else if(value instanceof Feature)
					typedValue.setFeatureValue((Feature)value);
				
				feature.setTypedValue(typedValue);
			}
			TypedValue defaultTypedValue = null;
			if(defaultValue != null) {
				defaultTypedValue = FmpFactory.eINSTANCE.createTypedValue();
				if(defaultValue instanceof Integer)
					defaultTypedValue.setIntegerValue((Integer)defaultValue);
				else if(defaultValue instanceof Float)
					defaultTypedValue.setFloatValue((Float)defaultValue);
				else if(defaultValue instanceof String)
					defaultTypedValue.setStringValue((String)defaultValue);
				else if(defaultValue instanceof Feature)
					defaultTypedValue.setFeatureValue((Feature)defaultValue);
				
				feature.setDefaultValue(defaultTypedValue);
			}
			
			if(feature.getProperties() != null)
			{
				int typeIndex = -1;
				if(valueType == ValueType.INTEGER_LITERAL)
					typeIndex = 0;
				else if(valueType == ValueType.FLOAT_LITERAL)
					typeIndex = 1;
				else if(valueType == ValueType.STRING_LITERAL)
					typeIndex = 2;
				else if(valueType == ValueType.FEATURE_LITERAL)
					typeIndex = 3;

				Feature attributeNode = (Feature)ModelNavigation.INSTANCE.getNodes(feature.getProperties(), "Attribute").get(0);
				attributeNode.setState(ConfigState.USER_SELECTED_LITERAL);
				
				for(int i = 0; i < 4; i++)
				{
					Feature typeNode = (Feature)((FeatureGroup)attributeNode.getChildren().get(0)).getChildren().get(i);
					ConfigState typeSelectionState = (i == typeIndex)? ConfigState.USER_SELECTED_LITERAL : ConfigState.MACHINE_ELIMINATED_LITERAL;
					
					// set the state of the type node ("Integer", "String", or whatever)
					typeNode.setState(typeSelectionState);
					
					// if the type is eliminated, then eliminate all of its children as well
					if(typeSelectionState == ConfigState.MACHINE_ELIMINATED_LITERAL)
					{
						((Feature)typeNode.getChildren().get(0)).setState(typeSelectionState);
						((Feature)typeNode.getChildren().get(1)).setState(typeSelectionState);
					}
					// otherwise, select the Value node if there is a value
					// and default value node if there is a default value
					else {					
						if(typedValue != null)
							((Feature)typeNode.getChildren().get(0)).setState(typeSelectionState);
						if(defaultTypedValue != null)
							((Feature)typeNode.getChildren().get(1)).setState(typeSelectionState);
					}					
				}
			}			
		}
		feature.setMin(min);
		feature.setMax(max);
	}
	
	public static FeatureGroup createFeatureGroupWithoutProperties(String id, int min, int max)
	{
		FeatureGroup featureGroup = FmpFactory.eINSTANCE.createFeatureGroup();
		setFeatureGroupAttributes(featureGroup, id, min, max);
		
		return featureGroup;
	}
	
	public static FeatureGroup createFeatureGroupWithProperties(String id, int min, int max, Feature metaMetaModel)
	{
		FeatureGroup featureGroup = FmpFactory.eINSTANCE.createFeatureGroup();	
		Feature metaMetaFeatureGroup = (Feature)metaMetaModel.getChildren().get(1);
		featureGroup.setProperties(ModelManipulation.INSTANCE.configure(metaMetaFeatureGroup));
		setFeatureGroupAttributes(featureGroup, id, min, max);
		
		return featureGroup;
	}
	
	public static void setFeatureGroupAttributes(FeatureGroup featureGroup, String id, int min, int max)
	{
		featureGroup.setId(id);
		featureGroup.setMin(min);
		featureGroup.setMax(max);
	}
	
	public static Reference createReferenceWithoutProperties(Feature feature, String id, ConfigState state, int min, int max)
	{
		Reference reference = FmpFactoryImpl.eINSTANCE.createReference();
		setReferenceAttributes(reference, feature, id, state, min, max);
		return reference;
	}	
	
	public static Reference createReferenceWithProperties(Feature feature, String id, ConfigState state, int min, int max, Feature metaMetaModel)
	{
		Reference reference = FmpFactoryImpl.eINSTANCE.createReference();
		Feature metaMetaReference = (Feature)metaMetaModel.getChildren().get(2);
		reference.setProperties((Feature)ModelManipulation.INSTANCE.configure(metaMetaReference));
		setReferenceAttributes(reference, feature, id, state, min, max);
		
		return reference;
	}
	
	public static void setReferenceAttributes(Reference reference, Feature feature, String id, ConfigState state, int min, int max)
	{
		reference.setFeature(feature);
		reference.setId(id);
		reference.setState(state);
		reference.setMin(min);
		reference.setMax(max);	
	}
	
	public static Feature getModel(EObject object) {
		Resource resource = object.eResource();
		Project project = (Project) resource.getContents().get(0);
		return project.getModel();
	}
	public static Feature getMetaModel(EObject object) {
		Resource resource = object.eResource();
		Project project = (Project) resource.getContents().get(0);
		return project.getMetaModel();
	}
	/**
	 * @param resource
	 * @return
	 */
	public static Feature getMetaMetaModel(EObject object) {
		Resource resource = object.eResource();
		Project project = (Project) resource.getContents().get(0);
		return project.getMetaMetaModel();
	}
}
