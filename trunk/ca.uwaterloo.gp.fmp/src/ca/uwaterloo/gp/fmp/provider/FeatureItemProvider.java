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
package ca.uwaterloo.gp.fmp.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ViewerNotification;

import ca.uwaterloo.gp.fmp.Clonable;
import ca.uwaterloo.gp.fmp.ConfigState;
import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.FmpFactory;
import ca.uwaterloo.gp.fmp.FmpPackage;
import ca.uwaterloo.gp.fmp.Node;
import ca.uwaterloo.gp.fmp.Project;
import ca.uwaterloo.gp.fmp.TypedValue;
import ca.uwaterloo.gp.fmp.ValueType;
import ca.uwaterloo.gp.fmp.constraints.Configurator;
import ca.uwaterloo.gp.fmp.constraints.ConfiguratorDictionary;
import ca.uwaterloo.gp.fmp.system.ConfigureFeatureCommand;
import ca.uwaterloo.gp.fmp.system.ModelNavigation;
import ca.uwaterloo.gp.fmp.system.RoleQuery;


/**
 * @author Michal Antkiewicz <mantkiew@swen.uwaterloo.ca>
 * This is the item provider adpater for a {@link ca.uwaterloo.gp.fmp.Feature} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class FeatureItemProvider
	extends ClonableItemProvider
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

		}
		return itemPropertyDescriptors;
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Collection getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(FmpPackage.eINSTANCE.getFeature_Configurations());
		}
		return childrenFeatures;
	}

	/**
	 * This returns Feature.gif.
	 * <!-- begin-user-doc -->
	 * Michal: 
	 * 1. display in modeling view
	 * SolitaryFeature
	 * - filled circle for 1-1 or (0-1 & *_SELECTED)
	 * - empty circle for 0-1
	 * - filled circle multi for 1-*
	 * - empty circle multi for 0-*
	 * GroupedFeature
	 * - filled square for 1-1 or (0-1 & *_SELECTED)
	 * - empty square for 0-1
	 * RootFeature (any feature that has configurations)
	 * - root feature icon
	 * 
	 * 2. display in configuration view
	 * appropriate checkboxes
	 * 
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Object getImage(Object object) {
		Clonable feature = (Clonable) object;
		ConfigState state = feature.getState();
		int[] cardinality = feature.getCheckboxViewCardinality(); // (mode==CHECKBOX)? feature.getCheckboxViewCardinality(): feature.getFeatureModelViewCardinality();
		int min = cardinality[0];
		int max = cardinality[1];
		/**
		 * the image depends on:
		 * 1. the type of view (configuration || modeling)
		 * 2. node type (solitary - circle, grouped - square, root - root symbol)
		 * 3. cardinality (optional - empty, mandatory or optional-selected - filled, x-many - multi)
		 */
		boolean optional = min == 0 && max == 1;
		boolean mandatory = min == 1 && max == 1;
		boolean prohibited = min == 0 && max == 0;
		boolean multi = max == -1 || max > 1;
		boolean selected =  feature.getState() == ConfigState.MACHINE_SELECTED_LITERAL ||
							feature.getState() == ConfigState.USER_SELECTED_LITERAL;

		switch (RoleQuery.INSTANCE.getLocationType(feature)) {
		case RoleQuery.MODEL_PROPERTIES:
		case RoleQuery.METAMODEL_PROPERTIES:
		case RoleQuery.CONFIGURATION:
		case RoleQuery.CONFIGURATION_PROPERTIES:
			if (optional)  {
				switch (state.getValue()) {
					case ConfigState.MACHINE_ELIMINATED:
						return getResourceLocator().getImage("full/obj16/MachineDeselected");
					case ConfigState.MACHINE_SELECTED:
						return getResourceLocator().getImage("full/obj16/MachineSelected");
					case ConfigState.USER_ELIMINATED:
						return getResourceLocator().getImage("full/obj16/UserDeselected");
					case ConfigState.USER_SELECTED:
						return getResourceLocator().getImage("full/obj16/UserSelected");
					case ConfigState.UNDECIDED:
						return getResourceLocator().getImage("full/obj16/Unchecked");
				}			
			}
			case RoleQuery.MODEL:
			case RoleQuery.METAMODEL:
				// modeling
				String iconName = "Circle";
				switch (RoleQuery.INSTANCE.getNodeType(feature)) {
				  case RoleQuery.SOLITARY_FEATURE:
				  	iconName = "Circle";
				  	break;
				  case RoleQuery.GROUPED_FEATURE:
				  	iconName = "Square";
				  	break;
				  case RoleQuery.ROOT_FEATURE:
				  	if (feature.eContainingFeature() == FmpPackage.eINSTANCE.getFeature_Configurations())
				  		return getResourceLocator().getImage("full/obj16/ConfRootFeature");
				  	else
				  		return getResourceLocator().getImage("full/obj16/RootFeature");
				  case RoleQuery.FEATURE_MODEL:
				  	return getResourceLocator().getImage("full/obj16/FeatureModel");
				} 					
				if (optional && !selected)
					return getResourceLocator().getImage("full/obj16/Empty" + iconName);
				else if (optional && selected || mandatory)
					return getResourceLocator().getImage("full/obj16/Filled" + iconName);
				else if (multi && min == 0)
					return getResourceLocator().getImage("full/obj16/Empty" + iconName + "Multi");
				else if (multi && min >= 1)
					return getResourceLocator().getImage("full/obj16/Filled" + iconName + "Multi");
				else if (prohibited)
					return getResourceLocator().getImage("full/obj16/Prohibited");
				else
					fmpEditPlugin.INSTANCE.log("Invalid feature cardinality [" + min + ".." + max + "]");
				break;
		}
		return null;	
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * Michal: display cardinality only for root and solitary features.
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getText(Object object) {
		Feature feature = (Feature)object;
		String name = feature.getName();
		int[] cardinality = feature.getCheckboxViewCardinality(); // (mode==CHECKBOX)? feature.getCheckboxViewCardinality(): feature.getFeatureModelViewCardinality();
		int min = cardinality[0];
		int max = cardinality[1];
		String config = "";
		if (feature.eContainingFeature() == FmpPackage.eINSTANCE.getFeature_Configurations()) {
			int i = ((Feature)feature.eContainer()).getConfigurations().indexOf(feature) + 1;
			config = "Configuration " + i + " of ";
		}
		
		switch (RoleQuery.INSTANCE.getNodeType(feature)) {
			case RoleQuery.SOLITARY_FEATURE:
		    case RoleQuery.ROOT_FEATURE:
				String card = "";
				if (max > 1) {
					if (min == max)
						card = "[" + min + "] ";
					else 
						card = "[" + min + ".." + max + "] ";
				}
				else if (max == -1)
					card = "[" + min + "..*] ";
				
				String label = config + card;
				
				if (name == null || name.length() == 0)
					label += "<Unnamed Solitary Feature>";				    
				else
				    label += name + getTypeAndValue(feature);
				
				// check if the feature has a configurator associated with it. 
				// If it does, then show the number of configurations that this root represents
				Configurator configurator = ConfiguratorDictionary.INSTANCE.readOnlyGet(feature);
				if(configurator != null)
				{
					label += " (" + (long) (configurator.getSolutionCount()) + " configuration(s))"; 
				}
				
				return label;
			case RoleQuery.GROUPED_FEATURE:
			case RoleQuery.FEATURE_MODEL:
				return name == null || name.length() == 0 ?
						"<Unnamed Grouped Feature>" :
							config + name + getTypeAndValue(feature);
		}
		
		
		return "<invalid element role>";
	}

	/**
	 * Michal: This is a helper function used in getText method to get value and type.
	 * @generated NOT
	 */
	protected String getTypeAndValue(Feature feature) {
		if (feature != null) {
			if (feature.getValueType().getValue() != ValueType.NONE) {
				TypedValue tv = feature.getTypedValue();
				if (tv != null) {
					String valueWithType = getValueWithTypeToString(feature);
					
					int index = valueWithType.indexOf("\r");
					// if there's no \r check for \n
					if (index < 0)
						index = valueWithType.indexOf("\n");
					
				    if (index >=0)
				        return " (" + valueWithType.substring(0, index) + "...' : "+ feature.getValueType().getName() +")";
			        return " (" + valueWithType + ")";
				}	
				else
					return " (" + feature.getValueType().getName() + ")";
			}
		}
		return "";
	}
	/**
	 * Michal: this helper function 
	 * @generated NOT
	 */
	public String getValueWithTypeToString(Feature feature) {
		String value = null;
		switch (feature.getValueType().getValue()) {
			case  ValueType.FEATURE:
				Feature f = feature.getTypedValue().getFeatureValue();
				if (f != null)
					value = f.getName();
				break;
			case  ValueType.FLOAT:
				Float floatValue = feature.getTypedValue().getFloatValue();
				if (floatValue != null)
					value = floatValue.toString();
				break;
			case  ValueType.INTEGER:
				Integer intValue = feature.getTypedValue().getIntegerValue();
				if (intValue != null)
					value = intValue.toString();
			break;
			case  ValueType.STRING:
				value = feature.getTypedValue().getStringValue();
				break;
		}
		return value != null ? "'" + value + "' : " + feature.getValueType().getName() : feature.getValueType().getName();
	}
	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * Michal: when setting a value, notify described feature
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(Feature.class)) {
			case FmpPackage.FEATURE__TYPED_VALUE:
			case FmpPackage.CLONABLE__STATE:
				EObject notifier = (EObject) notification.getNotifier();
				if (notifier instanceof Feature) {
					int location = RoleQuery.INSTANCE.getLocationType((Node)notifier); 
					if (location == RoleQuery.MODEL_PROPERTIES ||
						location == RoleQuery.METAMODEL_PROPERTIES || 
						location == RoleQuery.CONFIGURATION_PROPERTIES) {
						int role = RoleQuery.INSTANCE.getPropertyRole((Node) notifier);
						if (role != RoleQuery.NONE) {
							int whatChanged = 0;
							switch (role) {
							  case RoleQuery.NAME:
							  	whatChanged = FmpPackage.FEATURE__NAME;
							  	break;
							  case RoleQuery.ID:
							  	whatChanged = FmpPackage.NODE__ID;
							  	break;
							  case RoleQuery.MIN_OCCUR:
							  	whatChanged = FmpPackage.NODE__MIN;
							  	break;
							  case RoleQuery.MAX_OCCUR:
							  	whatChanged = FmpPackage.NODE__MAX;
							  	break;
							  case RoleQuery.DESCRIPTION:
							  	whatChanged = FmpPackage.FEATURE__NAME;
							  	break;
							  case RoleQuery.ATTRIBUTE:
							  case RoleQuery.TYPE_GROUP:
							  case RoleQuery.TYPE:
							  case RoleQuery.VALUE:
							  case RoleQuery.DEFAULT_VALUE:
							  	whatChanged = FmpPackage.FEATURE__TYPED_VALUE;
							  	break;
							  case RoleQuery.REFERENCED_FEATURE:
							  	whatChanged = FmpPackage.REFERENCE__FEATURE;
							  	break;
							}
							Feature properties = ModelNavigation.INSTANCE.navigateToRootFeature((Node) notification.getNotifier());
							Node describedNode = properties.getDescribedNode();
							// make sure described node has an adapter
							adapterFactory.adapt(describedNode, IEditingDomainItemProvider.class);
							describedNode.eNotify(new ENotificationImpl((InternalEObject) describedNode, Notification.SET, whatChanged, this, this));
							fireNotifyChanged(new ViewerNotification(notification, describedNode, false, true));
						}
					}
				}
			case FmpPackage.FEATURE__NAME:
			case FmpPackage.FEATURE__VALUE_TYPE:
			case FmpPackage.FEATURE__REFERENCES:
			case FmpPackage.FEATURE__CONSTRAINTS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case FmpPackage.FEATURE__CONFIGURATIONS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds to the collection of {@link org.eclipse.emf.edit.command.CommandParameter}s
	 * describing all of the children that can be created under this object.
	 * <!-- begin-user-doc -->
	 * Michal: disallow creation of configuration of a model or a metamodel
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected void collectNewChildDescriptors(Collection newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
		
		EObject aux = (EObject) object;
		if (!(aux.eContainer() instanceof Project)) {
			newChildDescriptors.add
				(createChildParameter
					(FmpPackage.eINSTANCE.getFeature_Configurations(),
					 FmpFactory.eINSTANCE.createFeature()));
		}
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCreateChildText(Object owner, Object feature, Object child, Collection selection) {
		boolean qualify =
			feature == FmpPackage.eINSTANCE.getNode_Children() ||
			feature == FmpPackage.eINSTANCE.getFeature_Configurations();
		return getString(
			qualify ? "_UI_CreateChild_text2" : "_UI_CreateChild_text",
			new Object[] { getTypeText(child), getFeatureText(feature), getTypeText(owner) });
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * @generated
	 */
	public ResourceLocator getResourceLocator() {
		return fmpEditPlugin.INSTANCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * Michal: when creating a configuration of the owner return ConfigureFeatureCommand
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Command createCreateChildCommand(EditingDomain domain, EObject owner, EStructuralFeature feature, 
											Object value, int index, Collection collection) {
		if (feature == FmpPackage.eINSTANCE.getFeature_Configurations())
			return new ConfigureFeatureCommand(domain, owner, feature, value, index, collection, this);
		return super.createCreateChildCommand(domain, owner, feature, value, index, collection);
	}
}
