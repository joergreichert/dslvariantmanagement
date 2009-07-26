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
import org.eclipse.emf.ecore.EObject;

/**
 * @author Chang Hwan Peter Kim <chpkim@swen.uwaterloo.ca>, 
 * 	       Michal Antkiewicz <mantkiew@swen.uwaterloo.ca>
 * @model abstract="true"
 */
public interface Node extends EObject{
	/**
	 * @model
	 */
	int getMax();
	
	/**
	 * Sets the value of the '{@link ca.uwaterloo.gp.fmp.Node#getMax <em>Max</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max</em>' attribute.
	 * @see #getMax()
	 * @generated
	 */
	void setMax(int value);

	/**
	 * @model
	 */
	int getMin();
	
	/**
	 * Sets the value of the '{@link ca.uwaterloo.gp.fmp.Node#getMin <em>Min</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min</em>' attribute.
	 * @see #getMin()
	 * @generated
	 */
	void setMin(int value);

	/**
	 * @model
	 */
	String getId();
	
	/**
	 * Sets the value of the '{@link ca.uwaterloo.gp.fmp.Node#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * @model type="Node" transient="true" containment="false" opposite="origin"
	 */
	EList getConfs();
	
	/**
	 * @model opposite="confs"
	 */
	Node getOrigin();
	
	/**
	 * Sets the value of the '{@link ca.uwaterloo.gp.fmp.Node#getOrigin <em>Origin</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Origin</em>' reference.
	 * @see #getOrigin()
	 * @generated
	 */
	void setOrigin(Node value);

	/**
	 * @model type="Node" containment="true"
	 */
	EList getChildren();
	
	/**
	 * @model opposite="describedNode"
	 */
	Feature getProperties();
	/**
	 * Sets the value of the '{@link ca.uwaterloo.gp.fmp.Node#getProperties <em>Properties</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Properties</em>' reference.
	 * @see #getProperties()
	 * @generated
	 */
	void setProperties(Feature value);

}