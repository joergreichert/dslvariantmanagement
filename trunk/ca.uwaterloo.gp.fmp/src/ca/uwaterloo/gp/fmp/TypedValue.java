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
package ca.uwaterloo.gp.fmp;

import org.eclipse.emf.ecore.EObject;

/**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>, 
 *         Michal Antkiewicz <mantkiew@swen.uwaterloo.ca>
 * @model
 */
public interface TypedValue extends EObject{
	/**
	 * @model
	 */
	Integer getIntegerValue();
	
	/**
	 * Sets the value of the '{@link ca.uwaterloo.gp.fmp.TypedValue#getIntegerValue <em>Integer Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Integer Value</em>' attribute.
	 * @see #getIntegerValue()
	 * @generated
	 */
	void setIntegerValue(Integer value);

	/**
	 * @model
	 */
	String getStringValue();
	
	/**
	 * Sets the value of the '{@link ca.uwaterloo.gp.fmp.TypedValue#getStringValue <em>String Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>String Value</em>' attribute.
	 * @see #getStringValue()
	 * @generated
	 */
	void setStringValue(String value);

	/**
	 * @model
	 */
	Float getFloatValue();
	
	/**
	 * Sets the value of the '{@link ca.uwaterloo.gp.fmp.TypedValue#getFloatValue <em>Float Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Float Value</em>' attribute.
	 * @see #getFloatValue()
	 * @generated
	 */
	void setFloatValue(Float value);

	/**
	 * @model
	 */
	Feature getFeatureValue();
	/**
	 * Sets the value of the '{@link ca.uwaterloo.gp.fmp.TypedValue#getFeatureValue <em>Feature Value</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feature Value</em>' reference.
	 * @see #getFeatureValue()
	 * @generated
	 */
	void setFeatureValue(Feature value);

	/**
	 * @return string representation of a value
	 */
	String getValueToString();

}
