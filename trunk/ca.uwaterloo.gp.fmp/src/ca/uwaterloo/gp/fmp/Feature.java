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

import org.eclipse.emf.common.util.EList;

/**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>, 
 * 	       Michal Antkiewicz <mantkiew@swen.uwaterloo.ca>
 * @model
 */
public interface Feature extends Clonable{
	/**
	 * @model
	 */
	String getName();
	
	/**
	 * Sets the value of the '{@link ca.uwaterloo.gp.fmp.Feature#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * @model
	 */
	ValueType getValueType();
	
	/**
	 * Sets the value of the '{@link ca.uwaterloo.gp.fmp.Feature#getValueType <em>Value Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Type</em>' attribute.
	 * @see ca.uwaterloo.gp.fmp.ValueType
	 * @see #getValueType()
	 * @generated
	 */
	void setValueType(ValueType value);

	/**
	 * @model containment="true"
	 */
	TypedValue getTypedValue();
	
	/**
	 * Sets the value of the '{@link ca.uwaterloo.gp.fmp.Feature#getTypedValue <em>Typed Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Typed Value</em>' containment reference.
	 * @see #getTypedValue()
	 * @generated
	 */
	void setTypedValue(TypedValue value);

	/**
	 * @model type="Feature" containment="true"
	 */
	EList getConfigurations();
	/**
	 * @model type="Reference" transient="true" opposite="feature"
	 */
	EList getReferences();
	/**
	 * @model opposite="properties"
	 */
	Node getDescribedNode();
	/**
	 * Sets the value of the '{@link ca.uwaterloo.gp.fmp.Feature#getDescribedNode <em>Described Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Described Node</em>' reference.
	 * @see #getDescribedNode()
	 * @generated
	 */
	void setDescribedNode(Node value);

	/**
	 * @model type="Constraint" containment="true"
	 */
	EList getConstraints();

	/**
	 * Michal: convenience, non-model method
	 * @generated NOT
	 */
	TypedValue getDefaultValue();

	/**
	 * Michal: convenience, non-model method
	 * @generated NOT
	 */
	void setDefaultValue(TypedValue defaultTypedValue);
}
