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
public interface Project extends EObject{
	/**
	 * @model containment="true"
	 */
	Feature getModel();
	
	/**
	 * Sets the value of the '{@link ca.uwaterloo.gp.fmp.Project#getModel <em>Model</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Model</em>' containment reference.
	 * @see #getModel()
	 * @generated
	 */
	void setModel(Feature value);

	/**
	 * @model containment="true"
	 */
	Feature getMetaModel();
	/**
	 * Sets the value of the '{@link ca.uwaterloo.gp.fmp.Project#getMetaModel <em>Meta Model</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Meta Model</em>' containment reference.
	 * @see #getMetaModel()
	 * @generated
	 */
	void setMetaModel(Feature value);

	/**
	 * @model containment="true"
	 */
	Feature getMetaMetaModel();
	/**
	 * Sets the value of the '{@link ca.uwaterloo.gp.fmp.Project#getMetaMetaModel <em>Meta Meta Model</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Meta Meta Model</em>' containment reference.
	 * @see #getMetaMetaModel()
	 * @generated
	 */
	void setMetaMetaModel(Feature value);

}
