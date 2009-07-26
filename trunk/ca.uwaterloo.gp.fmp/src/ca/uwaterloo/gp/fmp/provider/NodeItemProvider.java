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
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import ca.uwaterloo.gp.fmp.FeatureGroup;
import ca.uwaterloo.gp.fmp.FmpFactory;
import ca.uwaterloo.gp.fmp.FmpPackage;
import ca.uwaterloo.gp.fmp.Node;
import ca.uwaterloo.gp.fmp.Project;
import ca.uwaterloo.gp.fmp.Reference;
import ca.uwaterloo.gp.fmp.impl.FeatureGroupImpl;
import ca.uwaterloo.gp.fmp.impl.ReferenceImpl;
import ca.uwaterloo.gp.fmp.provider.command.CreateNodeCommand;


/**
 * @author Michal Antkiewicz <mantkiew@swen.uwaterloo.ca>
 * This is the item provider adpater for a {@link ca.uwaterloo.gp.fmp.Node} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class NodeItemProvider
	extends ItemProviderAdapter
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
	public NodeItemProvider(AdapterFactory adapterFactory) {
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
			childrenFeatures.add(FmpPackage.eINSTANCE.getNode_Children());
		}
		return childrenFeatures;
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getText(Object object) {
		String label = ((Node)object).getId();
		return label == null || label.length() == 0 ?
			getString("_UI_Node_type") :
			getString("_UI_Node_type") + " " + label;
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

		switch (notification.getFeatureID(Node.class)) {
			case FmpPackage.NODE__MAX:
			case FmpPackage.NODE__MIN:
			case FmpPackage.NODE__CONFS:
			case FmpPackage.NODE__ORIGIN:
			case FmpPackage.NODE__PROPERTIES:
			case FmpPackage.NODE__ID:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case FmpPackage.NODE__CHILDREN:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds to the collection of {@link org.eclipse.emf.edit.command.CommandParameter}s
	 * describing all of the children that can be created under this object.
	 * <!-- begin-user-doc -->
	 * Michal: do not allow 
	 * i) creation of FeatureGroup as a child of a FeatureGroup
	 * ii) creation of children of Reference
	 * iii) creation of other children than Feature for FeatureModel or MetaModel 
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected void collectNewChildDescriptors(Collection newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		if (!(object instanceof ReferenceImpl))  {
			newChildDescriptors.add
				(createChildParameter
					(FmpPackage.eINSTANCE.getNode_Children(),
					 FmpFactory.eINSTANCE.createFeature()));
			
			EObject aux = (EObject) object;
			if (!(aux.eContainer() instanceof Project)) {
				if (!(object instanceof FeatureGroupImpl))
					newChildDescriptors.add
						(createChildParameter
							(FmpPackage.eINSTANCE.getNode_Children(),
							 FmpFactory.eINSTANCE.createFeatureGroup()));
		
				newChildDescriptors.add
					(createChildParameter
						(FmpPackage.eINSTANCE.getNode_Children(),
						 FmpFactory.eINSTANCE.createReference()));
			}
		}
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
	 * Michal: Additionally to creating a child, properly configure properties
	 * either from metamodel or from meta-metamodel
	 * @generated NOT
	 */
	public Command createCreateChildCommand(EditingDomain domain, EObject owner, EStructuralFeature feature, 
			Object value, int index, Collection collection) {
		return new CreateNodeCommand(domain, owner, feature, value, index, collection, this);
	}
	/**
	 * Michal: an element has to be added together with its properties. This also has to work, when
	 * the element was first removed and is being added back (drag&drop, cut&paste).
	 * @generated NOT  
	 */
	protected Command createAddCommand(EditingDomain domain, EObject owner,
			EStructuralFeature feature, Collection collection, int index) {
		if (owner instanceof FeatureGroup) {
			// no feature group under feature groups
			for (Iterator i = collection.iterator(); i.hasNext(); ) {
				Object aux = i.next();
				if (aux instanceof FeatureGroup)
					return UnexecutableCommand.INSTANCE;
			}
		}
		else if (owner instanceof Reference)
			// no elements under references
			return UnexecutableCommand.INSTANCE;
		// otherwise if everything is ok
		return new AddCommand(domain, owner, feature, collection, index);
	}
}
