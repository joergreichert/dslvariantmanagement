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

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;

import ca.uwaterloo.gp.fmp.FeatureGroup;


/**
 * @author Michal Antkiewicz <mantkiew@swen.uwaterloo.ca>
 * This is the item provider adpater for a {@link ca.uwaterloo.gp.fmp.FeatureGroup} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class FeatureGroupItemProvider
	extends NodeItemProvider
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
	public FeatureGroupItemProvider(AdapterFactory adapterFactory) {
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
	 * This returns FeatureGroup.gif.
	 * <!-- begin-user-doc -->
	 * Michal:
	 * 1. display in modeling and configuration view
	 * empty group for min == 1 && max == 1
	 * prohibited for min == 1 && max == 0
	 * filled group for any other  
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Object getImage(Object object) {
		FeatureGroup group = (FeatureGroup) object;
		int min = group.getMin();
		int max = group.getMax();
		
		if (min == 1 && max == group.getChildren().size())
			return getResourceLocator().getImage("full/obj16/FeatureGroup");
		else if (min == 0 && max == 0)
			return getResourceLocator().getImage("full/obj16/FeatureGroupDisabled");
		else
			return getResourceLocator().getImage("full/obj16/FeatureGroupExclusive");
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * Michal: hide cardinality for exclusive and prohibited
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getText(Object object) {
		FeatureGroup featureGroup = (FeatureGroup)object;
		int min = featureGroup.getMin();
		int max = featureGroup.getMax();
		
		if (min == 0 && max == 0 || min == 1 && max == 1 || min == 1 && max == featureGroup.getChildren().size())
			return "";
		else 
			return "<" + min + "-" + max + ">"; 
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void notifyChanged(Notification notification) {
		updateChildren(notification);
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

}
