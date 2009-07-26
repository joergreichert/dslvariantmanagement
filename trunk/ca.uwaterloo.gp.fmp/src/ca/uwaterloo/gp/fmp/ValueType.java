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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

/**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>, 
 *         Michal Antkiewicz <mantkiew@swen.uwaterloo.ca>
 * @model
 */
public final class ValueType extends AbstractEnumerator
{    
    /**
     * @model
     */
    public static final int FEATURE = 1;
    
    /**
     * @model
     */
    public static final int FLOAT = 2;
    
    /**
     * @model
     */
    public static final int INTEGER = 3;
    
    /**
     * @model
     */
    public static final int NONE = 4;
    
    /**
     * @model
     */
    public static final int STRING = 5;
	/**
	 * The '<em><b>FEATURE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>FEATURE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #FEATURE
	 * @generated
	 * @ordered
	 */
	public static final ValueType FEATURE_LITERAL = new ValueType(FEATURE, "FEATURE");

	/**
	 * The '<em><b>FLOAT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>FLOAT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #FLOAT
	 * @generated
	 * @ordered
	 */
	public static final ValueType FLOAT_LITERAL = new ValueType(FLOAT, "FLOAT");

	/**
	 * The '<em><b>INTEGER</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>INTEGER</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #INTEGER
	 * @generated
	 * @ordered
	 */
	public static final ValueType INTEGER_LITERAL = new ValueType(INTEGER, "INTEGER");

	/**
	 * The '<em><b>NONE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>NONE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NONE
	 * @generated
	 * @ordered
	 */
	public static final ValueType NONE_LITERAL = new ValueType(NONE, "NONE");

	/**
	 * The '<em><b>STRING</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>STRING</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #STRING
	 * @generated
	 * @ordered
	 */
	public static final ValueType STRING_LITERAL = new ValueType(STRING, "STRING");

	/**
	 * An array of all the '<em><b>Value Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ValueType[] VALUES_ARRAY =
		new ValueType[] {
			FEATURE_LITERAL,
			FLOAT_LITERAL,
			INTEGER_LITERAL,
			NONE_LITERAL,
			STRING_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Value Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Value Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ValueType get(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ValueType result = VALUES_ARRAY[i];
			if (result.toString().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Value Type</b></em>' literal with the specified value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ValueType get(int value) {
		switch (value) {
			case FEATURE: return FEATURE_LITERAL;
			case FLOAT: return FLOAT_LITERAL;
			case INTEGER: return INTEGER_LITERAL;
			case NONE: return NONE_LITERAL;
			case STRING: return STRING_LITERAL;
		}
		return null;	
	}

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private ValueType(int value, String name) {
		super(value, name);
	}

}
