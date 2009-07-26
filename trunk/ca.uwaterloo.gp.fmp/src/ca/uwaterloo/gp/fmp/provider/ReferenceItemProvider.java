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
import org.eclipse.emf.common.command.UnexecutableCommand;
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
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import ca.uwaterloo.gp.fmp.Clonable;
import ca.uwaterloo.gp.fmp.ConfigState;
import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.FmpPackage;
import ca.uwaterloo.gp.fmp.Node;
import ca.uwaterloo.gp.fmp.Reference;
import ca.uwaterloo.gp.fmp.system.ModelNavigation;
import ca.uwaterloo.gp.fmp.system.RoleQuery;


/**
 * @author Michal Antkiewicz <mantkiew@swen.uwaterloo.ca>
 * This is the item provider adpater for a {@link ca.uwaterloo.gp.fmp.Reference} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ReferenceItemProvider
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
	public ReferenceItemProvider(AdapterFactory adapterFactory) {
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
	 * This returns Reference.gif.
	 * <!-- begin-user-doc -->
	 * Michal: for now, show the same icons as for feature
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Object getImage(Object object) {
		Clonable reference = (Clonable) object;
		ConfigState state = reference.getState();
		int[] cardinality = reference.getCheckboxViewCardinality(); // (mode==CHECKBOX)? feature.getCheckboxViewCardinality(): feature.getFeatureModelViewCardinality();
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
		boolean selected =  reference.getState() == ConfigState.MACHINE_SELECTED_LITERAL ||
							reference.getState() == ConfigState.USER_SELECTED_LITERAL;

		switch (RoleQuery.INSTANCE.getLocationType(reference)) {
		case RoleQuery.MODEL_PROPERTIES:
		case RoleQuery.METAMODEL_PROPERTIES:
		case RoleQuery.CONFIGURATION:
		case RoleQuery.CONFIGURATION_PROPERTIES:
			if (optional) {
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
				switch (RoleQuery.INSTANCE.getNodeType(reference)) {
				  case RoleQuery.SOLITARY_REFERENCE:
				  	iconName = "Circle";
				  	break;
				  case RoleQuery.GROUPED_REFERENCE:
				  	iconName = "Square";
				  	break;
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
					fmpEditPlugin.INSTANCE.log("Invalid reference cardinality [" + min + ".." + max + "]");
				break;				
		}
		return null;	
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * Michal: prefix the label with 'ref:'
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getText(Object object) {
		Reference reference = (Reference) object;
		Feature feature = reference.getFeature();
		int[] cardinality = reference.getCheckboxViewCardinality(); // (mode==CHECKBOX)? feature.getCheckboxViewCardinality(): feature.getFeatureModelViewCardinality();
		int min = cardinality[0];
		int max = cardinality[1];
		String card = "";
		
		if (RoleQuery.INSTANCE.getNodeType(reference) == RoleQuery.SOLITARY_REFERENCE) {
			if (max > 1) {
				if (min == max)
					card = "[" + min + "] ";
				else 
					card = "[" + min + ".." + max + "] ";
			}
			else if (max == -1)
				card = "[" + min + "..*] ";
		}
		if (feature != null) {
			ItemProviderAdapter itemProvider = (ItemProviderAdapter) adapterFactory.adapt(feature, ItemProviderAdapter.class);
			return card + "---> " + itemProvider.getText(feature); 
		}
		else {
			if (RoleQuery.INSTANCE.isInConfiguration(reference))
				return card + "<Unfold Reference to create new configuration>";
			else
				return card + "<Unset Reference>";	
		}
			
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * Michal: forward notifications. Make sure all elements have item providers
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */ 
	public void notifyChanged(Notification notification) {
		updateChildren(notification);
		
		switch (notification.getFeatureID(Reference.class)) {
			case FmpPackage.REFERENCE__FEATURE:
				EObject notifier = (EObject) notification.getNotifier();
				int location = RoleQuery.INSTANCE.getLocationType((Node) notifier); 
				if (location == RoleQuery.MODEL_PROPERTIES ||
					location == RoleQuery.METAMODEL_PROPERTIES || 
					location == RoleQuery.CONFIGURATION_PROPERTIES) {
					Feature properties = ModelNavigation.INSTANCE.navigateToRootFeature((Node) notifier);
					Node describedNode = properties.getDescribedNode();
					// make sure described node has an adapter
					adapterFactory.adapt(describedNode, IEditingDomainItemProvider.class);
					describedNode.eNotify(new ENotificationImpl((InternalEObject) describedNode, Notification.SET, FmpPackage.REFERENCE__FEATURE, this, this));
				}
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds to the collection of {@link org.eclipse.emf.edit.command.CommandParameter}s
	 * describing all of the children that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void collectNewChildDescriptors(Collection newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourceLocator getResourceLocator() {
		return fmpEditPlugin.INSTANCE;
	}

	/**
	 * Michal: prevent adding anything as a child of reference
	 */
	protected Command createAddCommand(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Collection collection, int index) {
		return UnexecutableCommand.INSTANCE;
	}
	/**
	 * Michal: prevent adding anything as a child of reference
	 */
	public Command createCreateChildCommand(EditingDomain domain,
			EObject owner, EStructuralFeature feature, Object value, int index,
			Collection collection) {
		return UnexecutableCommand.INSTANCE;
	}
}
