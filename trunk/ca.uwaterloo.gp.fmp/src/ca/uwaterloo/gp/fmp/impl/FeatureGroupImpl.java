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

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.InternalEList;

import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.FeatureGroup;
import ca.uwaterloo.gp.fmp.FmpPackage;
import ca.uwaterloo.gp.fmp.Node;


/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Feature Group</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class FeatureGroupImpl extends NodeImpl implements FeatureGroup {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FeatureGroupImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return FmpPackage.eINSTANCE.getFeatureGroup();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case FmpPackage.FEATURE_GROUP__CONFS:
					return ((InternalEList)getConfs()).basicAdd(otherEnd, msgs);
				case FmpPackage.FEATURE_GROUP__ORIGIN:
					if (origin != null)
						msgs = ((InternalEObject)origin).eInverseRemove(this, FmpPackage.NODE__CONFS, Node.class, msgs);
					return basicSetOrigin((Node)otherEnd, msgs);
				case FmpPackage.FEATURE_GROUP__PROPERTIES:
					if (properties != null)
						msgs = ((InternalEObject)properties).eInverseRemove(this, FmpPackage.FEATURE__DESCRIBED_NODE, Feature.class, msgs);
					return basicSetProperties((Feature)otherEnd, msgs);
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
				case FmpPackage.FEATURE_GROUP__CONFS:
					return ((InternalEList)getConfs()).basicRemove(otherEnd, msgs);
				case FmpPackage.FEATURE_GROUP__ORIGIN:
					return basicSetOrigin(null, msgs);
				case FmpPackage.FEATURE_GROUP__CHILDREN:
					return ((InternalEList)getChildren()).basicRemove(otherEnd, msgs);
				case FmpPackage.FEATURE_GROUP__PROPERTIES:
					return basicSetProperties(null, msgs);
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
			case FmpPackage.FEATURE_GROUP__MAX:
				return new Integer(getMax());
			case FmpPackage.FEATURE_GROUP__MIN:
				return new Integer(getMin());
			case FmpPackage.FEATURE_GROUP__CONFS:
				return getConfs();
			case FmpPackage.FEATURE_GROUP__ORIGIN:
				if (resolve) return getOrigin();
				return basicGetOrigin();
			case FmpPackage.FEATURE_GROUP__CHILDREN:
				return getChildren();
			case FmpPackage.FEATURE_GROUP__PROPERTIES:
				if (resolve) return getProperties();
				return basicGetProperties();
			case FmpPackage.FEATURE_GROUP__ID:
				return getId();
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
			case FmpPackage.FEATURE_GROUP__MAX:
				setMax(((Integer)newValue).intValue());
				return;
			case FmpPackage.FEATURE_GROUP__MIN:
				setMin(((Integer)newValue).intValue());
				return;
			case FmpPackage.FEATURE_GROUP__CONFS:
				getConfs().clear();
				getConfs().addAll((Collection)newValue);
				return;
			case FmpPackage.FEATURE_GROUP__ORIGIN:
				setOrigin((Node)newValue);
				return;
			case FmpPackage.FEATURE_GROUP__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection)newValue);
				return;
			case FmpPackage.FEATURE_GROUP__PROPERTIES:
				setProperties((Feature)newValue);
				return;
			case FmpPackage.FEATURE_GROUP__ID:
				setId((String)newValue);
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
			case FmpPackage.FEATURE_GROUP__MAX:
				setMax(MAX_EDEFAULT);
				return;
			case FmpPackage.FEATURE_GROUP__MIN:
				setMin(MIN_EDEFAULT);
				return;
			case FmpPackage.FEATURE_GROUP__CONFS:
				getConfs().clear();
				return;
			case FmpPackage.FEATURE_GROUP__ORIGIN:
				setOrigin((Node)null);
				return;
			case FmpPackage.FEATURE_GROUP__CHILDREN:
				getChildren().clear();
				return;
			case FmpPackage.FEATURE_GROUP__PROPERTIES:
				setProperties((Feature)null);
				return;
			case FmpPackage.FEATURE_GROUP__ID:
				setId(ID_EDEFAULT);
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
			case FmpPackage.FEATURE_GROUP__MAX:
				return max != MAX_EDEFAULT;
			case FmpPackage.FEATURE_GROUP__MIN:
				return min != MIN_EDEFAULT;
			case FmpPackage.FEATURE_GROUP__CONFS:
				return confs != null && !confs.isEmpty();
			case FmpPackage.FEATURE_GROUP__ORIGIN:
				return origin != null;
			case FmpPackage.FEATURE_GROUP__CHILDREN:
				return children != null && !children.isEmpty();
			case FmpPackage.FEATURE_GROUP__PROPERTIES:
				return properties != null;
			case FmpPackage.FEATURE_GROUP__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
		}
		return eDynamicIsSet(eFeature);
	}

} //FeatureGroupImpl
