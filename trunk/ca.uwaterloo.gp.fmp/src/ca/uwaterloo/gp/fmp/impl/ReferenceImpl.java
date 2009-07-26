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
package ca.uwaterloo.gp.fmp.impl;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.InternalEList;

import ca.uwaterloo.gp.fmp.Clonable;
import ca.uwaterloo.gp.fmp.ConfigState;
import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.FmpPackage;
import ca.uwaterloo.gp.fmp.Node;
import ca.uwaterloo.gp.fmp.Reference;
import ca.uwaterloo.gp.fmp.system.ModelNavigation;


/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link ca.uwaterloo.gp.fmp.impl.ReferenceImpl#getFeature <em>Feature</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ReferenceImpl extends ClonableImpl implements Reference {
	/**
	 * The cached value of the '{@link #getFeature() <em>Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeature()
	 * @generated
	 * @ordered
	 */
	protected Feature feature = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return FmpPackage.eINSTANCE.getReference();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Feature getFeature() {
		if(properties == null)
		{
			if (feature != null && feature.eIsProxy()) {
				Feature oldFeature = feature;
				feature = (Feature)eResolveProxy((InternalEObject)feature);
				if (feature != oldFeature) {
					if (eNotificationRequired())
						eNotify(new ENotificationImpl(this, Notification.RESOLVE, FmpPackage.REFERENCE__FEATURE, oldFeature, feature));
				}
			}
			return feature;
		}
		else
		{
			List references = ModelNavigation.INSTANCE.getNodes(properties, "Reference");
			if (references.size() > 0) {
				Reference referenceFeature = (Reference)references.get(0);
				return referenceFeature.getFeature();
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Feature basicGetFeature() {
		return feature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFeature(Feature newFeature, NotificationChain msgs) {
		Feature oldFeature = feature;
		feature = newFeature;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FmpPackage.REFERENCE__FEATURE, oldFeature, newFeature);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setFeature(Feature newFeature) {
		if(properties == null)
		{
			if (newFeature != feature) {
				NotificationChain msgs = null;
				if (feature != null)
					msgs = ((InternalEObject)feature).eInverseRemove(this, FmpPackage.FEATURE__REFERENCES, Feature.class, msgs);
				if (newFeature != null)
					msgs = ((InternalEObject)newFeature).eInverseAdd(this, FmpPackage.FEATURE__REFERENCES, Feature.class, msgs);
				msgs = basicSetFeature(newFeature, msgs);
				if (msgs != null) msgs.dispatch();
			}
			// else if (eNotificationRequired())
		}
		else
		{
			List references = ModelNavigation.INSTANCE.getNodes(properties, "Reference");
			if (references.size() > 0) {
				Reference referenceFeature = (Reference)references.get(0);
				referenceFeature.setFeature(newFeature);
			}	
		}
		eNotify(new ENotificationImpl(this, Notification.SET, FmpPackage.REFERENCE__FEATURE, newFeature, newFeature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case FmpPackage.REFERENCE__CONFS:
					return ((InternalEList)getConfs()).basicAdd(otherEnd, msgs);
				case FmpPackage.REFERENCE__ORIGIN:
					if (origin != null)
						msgs = ((InternalEObject)origin).eInverseRemove(this, FmpPackage.NODE__CONFS, Node.class, msgs);
					return basicSetOrigin((Node)otherEnd, msgs);
				case FmpPackage.REFERENCE__PROPERTIES:
					if (properties != null)
						msgs = ((InternalEObject)properties).eInverseRemove(this, FmpPackage.FEATURE__DESCRIBED_NODE, Feature.class, msgs);
					return basicSetProperties((Feature)otherEnd, msgs);
				case FmpPackage.REFERENCE__CLONES:
					return ((InternalEList)getClones()).basicAdd(otherEnd, msgs);
				case FmpPackage.REFERENCE__PROTOTYPE:
					if (prototype != null)
						msgs = ((InternalEObject)prototype).eInverseRemove(this, FmpPackage.CLONABLE__CLONES, Clonable.class, msgs);
					return basicSetPrototype((Clonable)otherEnd, msgs);
				case FmpPackage.REFERENCE__FEATURE:
					if (feature != null)
						msgs = ((InternalEObject)feature).eInverseRemove(this, FmpPackage.FEATURE__REFERENCES, Feature.class, msgs);
					return basicSetFeature((Feature)otherEnd, msgs);
				default:
					return eDynamicInverseAdd(otherEnd, featureID, baseClass, msgs);
			}
		}
		if (eContainer != null)
			msgs = eBasicRemoveFromContainer(msgs);
		return eBasicSetContainer(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case FmpPackage.REFERENCE__CONFS:
					return ((InternalEList)getConfs()).basicRemove(otherEnd, msgs);
				case FmpPackage.REFERENCE__ORIGIN:
					return basicSetOrigin(null, msgs);
				case FmpPackage.REFERENCE__CHILDREN:
					return ((InternalEList)getChildren()).basicRemove(otherEnd, msgs);
				case FmpPackage.REFERENCE__PROPERTIES:
					return basicSetProperties(null, msgs);
				case FmpPackage.REFERENCE__CLONES:
					return ((InternalEList)getClones()).basicRemove(otherEnd, msgs);
				case FmpPackage.REFERENCE__PROTOTYPE:
					return basicSetPrototype(null, msgs);
				case FmpPackage.REFERENCE__FEATURE:
					return basicSetFeature(null, msgs);
				default:
					return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
			}
		}
		return eBasicSetContainer(null, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case FmpPackage.REFERENCE__MAX:
				return new Integer(getMax());
			case FmpPackage.REFERENCE__MIN:
				return new Integer(getMin());
			case FmpPackage.REFERENCE__CONFS:
				return getConfs();
			case FmpPackage.REFERENCE__ORIGIN:
				if (resolve) return getOrigin();
				return basicGetOrigin();
			case FmpPackage.REFERENCE__CHILDREN:
				return getChildren();
			case FmpPackage.REFERENCE__PROPERTIES:
				if (resolve) return getProperties();
				return basicGetProperties();
			case FmpPackage.REFERENCE__ID:
				return getId();
			case FmpPackage.REFERENCE__STATE:
				return getState();
			case FmpPackage.REFERENCE__CLONES:
				return getClones();
			case FmpPackage.REFERENCE__PROTOTYPE:
				if (resolve) return getPrototype();
				return basicGetPrototype();
			case FmpPackage.REFERENCE__FEATURE:
				if (resolve) return getFeature();
				return basicGetFeature();
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case FmpPackage.REFERENCE__MAX:
				setMax(((Integer)newValue).intValue());
				return;
			case FmpPackage.REFERENCE__MIN:
				setMin(((Integer)newValue).intValue());
				return;
			case FmpPackage.REFERENCE__CONFS:
				getConfs().clear();
				getConfs().addAll((Collection)newValue);
				return;
			case FmpPackage.REFERENCE__ORIGIN:
				setOrigin((Node)newValue);
				return;
			case FmpPackage.REFERENCE__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection)newValue);
				return;
			case FmpPackage.REFERENCE__PROPERTIES:
				setProperties((Feature)newValue);
				return;
			case FmpPackage.REFERENCE__ID:
				setId((String)newValue);
				return;
			case FmpPackage.REFERENCE__STATE:
				setState((ConfigState)newValue);
				return;
			case FmpPackage.REFERENCE__CLONES:
				getClones().clear();
				getClones().addAll((Collection)newValue);
				return;
			case FmpPackage.REFERENCE__PROTOTYPE:
				setPrototype((Clonable)newValue);
				return;
			case FmpPackage.REFERENCE__FEATURE:
				setFeature((Feature)newValue);
				return;
		}
		eDynamicSet(eFeature, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case FmpPackage.REFERENCE__MAX:
				setMax(MAX_EDEFAULT);
				return;
			case FmpPackage.REFERENCE__MIN:
				setMin(MIN_EDEFAULT);
				return;
			case FmpPackage.REFERENCE__CONFS:
				getConfs().clear();
				return;
			case FmpPackage.REFERENCE__ORIGIN:
				setOrigin((Node)null);
				return;
			case FmpPackage.REFERENCE__CHILDREN:
				getChildren().clear();
				return;
			case FmpPackage.REFERENCE__PROPERTIES:
				setProperties((Feature)null);
				return;
			case FmpPackage.REFERENCE__ID:
				setId(ID_EDEFAULT);
				return;
			case FmpPackage.REFERENCE__STATE:
				setState(STATE_EDEFAULT);
				return;
			case FmpPackage.REFERENCE__CLONES:
				getClones().clear();
				return;
			case FmpPackage.REFERENCE__PROTOTYPE:
				setPrototype((Clonable)null);
				return;
			case FmpPackage.REFERENCE__FEATURE:
				setFeature((Feature)null);
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case FmpPackage.REFERENCE__MAX:
				return max != MAX_EDEFAULT;
			case FmpPackage.REFERENCE__MIN:
				return min != MIN_EDEFAULT;
			case FmpPackage.REFERENCE__CONFS:
				return confs != null && !confs.isEmpty();
			case FmpPackage.REFERENCE__ORIGIN:
				return origin != null;
			case FmpPackage.REFERENCE__CHILDREN:
				return children != null && !children.isEmpty();
			case FmpPackage.REFERENCE__PROPERTIES:
				return properties != null;
			case FmpPackage.REFERENCE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case FmpPackage.REFERENCE__STATE:
				return state != STATE_EDEFAULT;
			case FmpPackage.REFERENCE__CLONES:
				return clones != null && !clones.isEmpty();
			case FmpPackage.REFERENCE__PROTOTYPE:
				return prototype != null;
			case FmpPackage.REFERENCE__FEATURE:
				return feature != null;
		}
		return eDynamicIsSet(eFeature);
	}

} //ReferenceImpl
