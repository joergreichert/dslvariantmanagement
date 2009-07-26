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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.FmpPackage;
import ca.uwaterloo.gp.fmp.TypedValue;


/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Typed Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link ca.uwaterloo.gp.fmp.impl.TypedValueImpl#getIntegerValue <em>Integer Value</em>}</li>
 *   <li>{@link ca.uwaterloo.gp.fmp.impl.TypedValueImpl#getStringValue <em>String Value</em>}</li>
 *   <li>{@link ca.uwaterloo.gp.fmp.impl.TypedValueImpl#getFloatValue <em>Float Value</em>}</li>
 *   <li>{@link ca.uwaterloo.gp.fmp.impl.TypedValueImpl#getFeatureValue <em>Feature Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TypedValueImpl extends EObjectImpl implements TypedValue {
	/**
	 * The default value of the '{@link #getIntegerValue() <em>Integer Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIntegerValue()
	 * @generated
	 * @ordered
	 */
	protected static final Integer INTEGER_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIntegerValue() <em>Integer Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIntegerValue()
	 * @generated
	 * @ordered
	 */
	protected Integer integerValue = INTEGER_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getStringValue() <em>String Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStringValue()
	 * @generated
	 * @ordered
	 */
	protected static final String STRING_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStringValue() <em>String Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStringValue()
	 * @generated
	 * @ordered
	 */
	protected String stringValue = STRING_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getFloatValue() <em>Float Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFloatValue()
	 * @generated
	 * @ordered
	 */
	protected static final Float FLOAT_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFloatValue() <em>Float Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFloatValue()
	 * @generated
	 * @ordered
	 */
	protected Float floatValue = FLOAT_VALUE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFeatureValue() <em>Feature Value</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatureValue()
	 * @generated
	 * @ordered
	 */
	protected Feature featureValue = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TypedValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return FmpPackage.eINSTANCE.getTypedValue();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getIntegerValue() {
		return integerValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setIntegerValue(Integer newIntegerValue) {
		Integer oldIntegerValue = integerValue;
		integerValue = newIntegerValue;
		if (eNotificationRequired())  {
			eNotify(new ENotificationImpl(this, Notification.SET, FmpPackage.TYPED_VALUE__INTEGER_VALUE, oldIntegerValue, integerValue));
			eContainer.eNotify(new ENotificationImpl(eContainer, Notification.SET, FmpPackage.FEATURE__TYPED_VALUE, this, this));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getStringValue() {
		return stringValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setStringValue(String newStringValue) {
		String oldStringValue = stringValue;
		stringValue = newStringValue;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, FmpPackage.TYPED_VALUE__STRING_VALUE, oldStringValue, stringValue));
			eContainer.eNotify(new ENotificationImpl(eContainer, Notification.SET, FmpPackage.FEATURE__TYPED_VALUE, this, this));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Float getFloatValue() {
		return floatValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setFloatValue(Float newFloatValue) {
		Float oldFloatValue = floatValue;
		floatValue = newFloatValue;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, FmpPackage.TYPED_VALUE__FLOAT_VALUE, oldFloatValue, floatValue));
			eContainer.eNotify(new ENotificationImpl(eContainer, Notification.SET, FmpPackage.FEATURE__TYPED_VALUE, this, this));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Feature getFeatureValue() {
		if (featureValue != null && featureValue.eIsProxy()) {
			Feature oldFeatureValue = featureValue;
			featureValue = (Feature)eResolveProxy((InternalEObject)featureValue);
			if (featureValue != oldFeatureValue) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FmpPackage.TYPED_VALUE__FEATURE_VALUE, oldFeatureValue, featureValue));
			}
		}
		return featureValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Feature basicGetFeatureValue() {
		return featureValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setFeatureValue(Feature newFeatureValue) {
		Feature oldFeatureValue = featureValue;
		featureValue = newFeatureValue;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, FmpPackage.TYPED_VALUE__FEATURE_VALUE, oldFeatureValue, featureValue));
			eContainer.eNotify(new ENotificationImpl(eContainer, Notification.SET, FmpPackage.FEATURE__TYPED_VALUE, this, this));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case FmpPackage.TYPED_VALUE__INTEGER_VALUE:
				return getIntegerValue();
			case FmpPackage.TYPED_VALUE__STRING_VALUE:
				return getStringValue();
			case FmpPackage.TYPED_VALUE__FLOAT_VALUE:
				return getFloatValue();
			case FmpPackage.TYPED_VALUE__FEATURE_VALUE:
				if (resolve) return getFeatureValue();
				return basicGetFeatureValue();
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
			case FmpPackage.TYPED_VALUE__INTEGER_VALUE:
				setIntegerValue((Integer)newValue);
				return;
			case FmpPackage.TYPED_VALUE__STRING_VALUE:
				setStringValue((String)newValue);
				return;
			case FmpPackage.TYPED_VALUE__FLOAT_VALUE:
				setFloatValue((Float)newValue);
				return;
			case FmpPackage.TYPED_VALUE__FEATURE_VALUE:
				setFeatureValue((Feature)newValue);
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
			case FmpPackage.TYPED_VALUE__INTEGER_VALUE:
				setIntegerValue(INTEGER_VALUE_EDEFAULT);
				return;
			case FmpPackage.TYPED_VALUE__STRING_VALUE:
				setStringValue(STRING_VALUE_EDEFAULT);
				return;
			case FmpPackage.TYPED_VALUE__FLOAT_VALUE:
				setFloatValue(FLOAT_VALUE_EDEFAULT);
				return;
			case FmpPackage.TYPED_VALUE__FEATURE_VALUE:
				setFeatureValue((Feature)null);
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
			case FmpPackage.TYPED_VALUE__INTEGER_VALUE:
				return INTEGER_VALUE_EDEFAULT == null ? integerValue != null : !INTEGER_VALUE_EDEFAULT.equals(integerValue);
			case FmpPackage.TYPED_VALUE__STRING_VALUE:
				return STRING_VALUE_EDEFAULT == null ? stringValue != null : !STRING_VALUE_EDEFAULT.equals(stringValue);
			case FmpPackage.TYPED_VALUE__FLOAT_VALUE:
				return FLOAT_VALUE_EDEFAULT == null ? floatValue != null : !FLOAT_VALUE_EDEFAULT.equals(floatValue);
			case FmpPackage.TYPED_VALUE__FEATURE_VALUE:
				return featureValue != null;
		}
		return eDynamicIsSet(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (integerValue: ");
		result.append(integerValue);
		result.append(", stringValue: ");
		result.append(stringValue);
		result.append(", floatValue: ");
		result.append(floatValue);
		result.append(')');
		return result.toString();
	}

	public String getValueToString() {
		if (featureValue != null)
			return featureValue.toString();
		else if (floatValue != null)
			return floatValue.toString();
		else if (integerValue != null)
			return integerValue.toString();
		else if (stringValue != null)
			return stringValue;
		return null;
	}

} //TypedValueImpl
