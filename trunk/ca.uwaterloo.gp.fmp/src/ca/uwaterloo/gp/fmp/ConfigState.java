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
public final class ConfigState extends AbstractEnumerator
{    
    /**
     * @model
     */
    public static final int USER_SELECTED = 1;
    
    /**
     * @model
     */
    public static final int USER_ELIMINATED = 2;
    
    /**
     * @model
     */
    public static final int MACHINE_SELECTED = 3;
    
    /**
     * @model
     */
    public static final int MACHINE_ELIMINATED = 4;
    
    /**
     * @model
     */
    public static final int UNDECIDED = 5;
	/**
	 * The '<em><b>USER SELECTED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>USER SELECTED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #USER_SELECTED
	 * @generated
	 * @ordered
	 */
	public static final ConfigState USER_SELECTED_LITERAL = new ConfigState(USER_SELECTED, "USER_SELECTED");

	/**
	 * The '<em><b>USER ELIMINATED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>USER ELIMINATED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #USER_ELIMINATED
	 * @generated
	 * @ordered
	 */
	public static final ConfigState USER_ELIMINATED_LITERAL = new ConfigState(USER_ELIMINATED, "USER_ELIMINATED");

	/**
	 * The '<em><b>MACHINE SELECTED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>MACHINE SELECTED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MACHINE_SELECTED
	 * @generated
	 * @ordered
	 */
	public static final ConfigState MACHINE_SELECTED_LITERAL = new ConfigState(MACHINE_SELECTED, "MACHINE_SELECTED");

	/**
	 * The '<em><b>MACHINE ELIMINATED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>MACHINE ELIMINATED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MACHINE_ELIMINATED
	 * @generated
	 * @ordered
	 */
	public static final ConfigState MACHINE_ELIMINATED_LITERAL = new ConfigState(MACHINE_ELIMINATED, "MACHINE_ELIMINATED");

	/**
	 * The '<em><b>UNDECIDED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>UNDECIDED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #UNDECIDED
	 * @generated
	 * @ordered
	 */
	public static final ConfigState UNDECIDED_LITERAL = new ConfigState(UNDECIDED, "UNDECIDED");

	/**
	 * An array of all the '<em><b>Config State</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ConfigState[] VALUES_ARRAY =
		new ConfigState[] {
			USER_SELECTED_LITERAL,
			USER_ELIMINATED_LITERAL,
			MACHINE_SELECTED_LITERAL,
			MACHINE_ELIMINATED_LITERAL,
			UNDECIDED_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Config State</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Config State</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ConfigState get(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ConfigState result = VALUES_ARRAY[i];
			if (result.toString().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Config State</b></em>' literal with the specified value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ConfigState get(int value) {
		switch (value) {
			case USER_SELECTED: return USER_SELECTED_LITERAL;
			case USER_ELIMINATED: return USER_ELIMINATED_LITERAL;
			case MACHINE_SELECTED: return MACHINE_SELECTED_LITERAL;
			case MACHINE_ELIMINATED: return MACHINE_ELIMINATED_LITERAL;
			case UNDECIDED: return UNDECIDED_LITERAL;
		}
		return null;	
	}

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private ConfigState(int value, String name) {
		super(value, name);
	}

}
