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
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.FmpFactory;
import ca.uwaterloo.gp.fmp.FmpPackage;
import ca.uwaterloo.gp.fmp.Node;
import ca.uwaterloo.gp.fmp.TypedValue;
import ca.uwaterloo.gp.fmp.system.ModelNavigation;


/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link ca.uwaterloo.gp.fmp.impl.NodeImpl#getMax <em>Max</em>}</li>
 *   <li>{@link ca.uwaterloo.gp.fmp.impl.NodeImpl#getMin <em>Min</em>}</li>
 *   <li>{@link ca.uwaterloo.gp.fmp.impl.NodeImpl#getConfs <em>Confs</em>}</li>
 *   <li>{@link ca.uwaterloo.gp.fmp.impl.NodeImpl#getOrigin <em>Origin</em>}</li>
 *   <li>{@link ca.uwaterloo.gp.fmp.impl.NodeImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link ca.uwaterloo.gp.fmp.impl.NodeImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link ca.uwaterloo.gp.fmp.impl.NodeImpl#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class NodeImpl extends EObjectImpl implements Node {
	/**
	 * The default value of the '{@link #getMax() <em>Max</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMax()
	 * @generated
	 * @ordered
	 */
	protected static final int MAX_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMax() <em>Max</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMax()
	 * @generated
	 * @ordered
	 */
	protected int max = MAX_EDEFAULT;

	/**
	 * The default value of the '{@link #getMin() <em>Min</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMin()
	 * @generated
	 * @ordered
	 */
	protected static final int MIN_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMin() <em>Min</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMin()
	 * @generated
	 * @ordered
	 */
	protected int min = MIN_EDEFAULT;

	/**
	 * The cached value of the '{@link #getConfs() <em>Confs</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfs()
	 * @generated
	 * @ordered
	 */
	protected EList confs = null;

	/**
	 * The cached value of the '{@link #getOrigin() <em>Origin</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrigin()
	 * @generated
	 * @ordered
	 */
	protected Node origin = null;

	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList children = null;

	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected Feature properties = null;

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return FmpPackage.eINSTANCE.getNode();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public int getMax() {
		if(properties == null)
			return max;
		else
		{
			List maxs = ModelNavigation.INSTANCE.getNodes(properties, "Max");
			if (maxs.size() > 0) {
				Feature maxNode = (Feature) maxs.get(0);
				if (maxNode.getTypedValue() != null)
					return maxNode.getTypedValue().getIntegerValue().intValue();
			}
		}		
		// error value
		return -2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setMax(int newMax) {
		int oldMax = max;
		if(properties == null)
		{			
			max = newMax;
		}
		else
		{
			Feature maxNode = (Feature)ModelNavigation.INSTANCE.getNodes(properties, "Max").get(0);
			TypedValue maxValue = FmpFactory.eINSTANCE.createTypedValue();
			maxValue.setIntegerValue(new Integer(newMax));
			maxNode.setTypedValue(maxValue);
		}
		// if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FmpPackage.NODE__MAX, oldMax, max));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public int getMin() {
		if(properties == null)
			return min;
		else
		{
			List mins = ModelNavigation.INSTANCE.getNodes(properties, "Min");
			if (mins.size() > 0) {
				Feature minNode = (Feature) mins.get(0);
				if (minNode.getTypedValue() != null)
					return minNode.getTypedValue().getIntegerValue().intValue();
			}
		}
		// error value
		return -2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setMin(int newMin) {
		int oldMin = min;
		if(properties == null)
		{			
			min = newMin;
		}
		else
		{
			Feature minNode = (Feature)ModelNavigation.INSTANCE.getNodes(properties, "Min").get(0);
			TypedValue minValue = FmpFactory.eINSTANCE.createTypedValue();
			minValue.setIntegerValue(new Integer(newMin));
			minNode.setTypedValue(minValue);
		}
		// if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FmpPackage.NODE__MIN, oldMin, min));
	}



	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getConfs() {
		if (confs == null) {
			confs = new EObjectWithInverseResolvingEList(Node.class, this, FmpPackage.NODE__CONFS, FmpPackage.NODE__ORIGIN);
		}
		return confs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node getOrigin() {
		if (origin != null && origin.eIsProxy()) {
			Node oldOrigin = origin;
			origin = (Node)eResolveProxy((InternalEObject)origin);
			if (origin != oldOrigin) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FmpPackage.NODE__ORIGIN, oldOrigin, origin));
			}
		}
		return origin;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node basicGetOrigin() {
		return origin;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOrigin(Node newOrigin, NotificationChain msgs) {
		Node oldOrigin = origin;
		origin = newOrigin;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FmpPackage.NODE__ORIGIN, oldOrigin, newOrigin);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOrigin(Node newOrigin) {
		if (newOrigin != origin) {
			NotificationChain msgs = null;
			if (origin != null)
				msgs = ((InternalEObject)origin).eInverseRemove(this, FmpPackage.NODE__CONFS, Node.class, msgs);
			if (newOrigin != null)
				msgs = ((InternalEObject)newOrigin).eInverseAdd(this, FmpPackage.NODE__CONFS, Node.class, msgs);
			msgs = basicSetOrigin(newOrigin, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FmpPackage.NODE__ORIGIN, newOrigin, newOrigin));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getChildren() {
		if (children == null) {
			children = new EObjectContainmentEList(Node.class, this, FmpPackage.NODE__CHILDREN);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Feature getProperties() {
		if (properties != null && properties.eIsProxy()) {
			Feature oldProperties = properties;
			properties = (Feature)eResolveProxy((InternalEObject)properties);
			if (properties != oldProperties) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FmpPackage.NODE__PROPERTIES, oldProperties, properties));
			}
		}
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Feature basicGetProperties() {
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProperties(Feature newProperties, NotificationChain msgs) {
		Feature oldProperties = properties;
		properties = newProperties;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FmpPackage.NODE__PROPERTIES, oldProperties, newProperties);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProperties(Feature newProperties) {
		if (newProperties != properties) {
			NotificationChain msgs = null;
			if (properties != null)
				msgs = ((InternalEObject)properties).eInverseRemove(this, FmpPackage.FEATURE__DESCRIBED_NODE, Feature.class, msgs);
			if (newProperties != null)
				msgs = ((InternalEObject)newProperties).eInverseAdd(this, FmpPackage.FEATURE__DESCRIBED_NODE, Feature.class, msgs);
			msgs = basicSetProperties(newProperties, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FmpPackage.NODE__PROPERTIES, newProperties, newProperties));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getId() {
		if(properties == null)		
			return id;
		else
		{
			Feature idNode = (Feature) ModelNavigation.INSTANCE.getNodes(properties, "Id").get(0);
			if (idNode.getTypedValue() !=  null)
				return idNode.getTypedValue().getStringValue();
			else
				return null;
		}
	}
	
	
	

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setId(String newId) {
		String oldId = id;
		
		if(properties == null)
			id = newId;
		else
		{
			Feature idNode = (Feature)ModelNavigation.INSTANCE.getNodes(properties, "Id").get(0);
			TypedValue idValue = FmpFactory.eINSTANCE.createTypedValue();
			idValue.setStringValue(newId);
			idNode.setTypedValue(idValue);
		}
		// if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FmpPackage.FEATURE__ID, oldId, id));
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case FmpPackage.NODE__CONFS:
					return ((InternalEList)getConfs()).basicAdd(otherEnd, msgs);
				case FmpPackage.NODE__ORIGIN:
					if (origin != null)
						msgs = ((InternalEObject)origin).eInverseRemove(this, FmpPackage.NODE__CONFS, Node.class, msgs);
					return basicSetOrigin((Node)otherEnd, msgs);
				case FmpPackage.NODE__PROPERTIES:
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
				case FmpPackage.NODE__CONFS:
					return ((InternalEList)getConfs()).basicRemove(otherEnd, msgs);
				case FmpPackage.NODE__ORIGIN:
					return basicSetOrigin(null, msgs);
				case FmpPackage.NODE__CHILDREN:
					return ((InternalEList)getChildren()).basicRemove(otherEnd, msgs);
				case FmpPackage.NODE__PROPERTIES:
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
			case FmpPackage.NODE__MAX:
				return new Integer(getMax());
			case FmpPackage.NODE__MIN:
				return new Integer(getMin());
			case FmpPackage.NODE__CONFS:
				return getConfs();
			case FmpPackage.NODE__ORIGIN:
				if (resolve) return getOrigin();
				return basicGetOrigin();
			case FmpPackage.NODE__CHILDREN:
				return getChildren();
			case FmpPackage.NODE__PROPERTIES:
				if (resolve) return getProperties();
				return basicGetProperties();
			case FmpPackage.NODE__ID:
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
			case FmpPackage.NODE__MAX:
				setMax(((Integer)newValue).intValue());
				return;
			case FmpPackage.NODE__MIN:
				setMin(((Integer)newValue).intValue());
				return;
			case FmpPackage.NODE__CONFS:
				getConfs().clear();
				getConfs().addAll((Collection)newValue);
				return;
			case FmpPackage.NODE__ORIGIN:
				setOrigin((Node)newValue);
				return;
			case FmpPackage.NODE__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection)newValue);
				return;
			case FmpPackage.NODE__PROPERTIES:
				setProperties((Feature)newValue);
				return;
			case FmpPackage.NODE__ID:
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
			case FmpPackage.NODE__MAX:
				setMax(MAX_EDEFAULT);
				return;
			case FmpPackage.NODE__MIN:
				setMin(MIN_EDEFAULT);
				return;
			case FmpPackage.NODE__CONFS:
				getConfs().clear();
				return;
			case FmpPackage.NODE__ORIGIN:
				setOrigin((Node)null);
				return;
			case FmpPackage.NODE__CHILDREN:
				getChildren().clear();
				return;
			case FmpPackage.NODE__PROPERTIES:
				setProperties((Feature)null);
				return;
			case FmpPackage.NODE__ID:
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
			case FmpPackage.NODE__MAX:
				return max != MAX_EDEFAULT;
			case FmpPackage.NODE__MIN:
				return min != MIN_EDEFAULT;
			case FmpPackage.NODE__CONFS:
				return confs != null && !confs.isEmpty();
			case FmpPackage.NODE__ORIGIN:
				return origin != null;
			case FmpPackage.NODE__CHILDREN:
				return children != null && !children.isEmpty();
			case FmpPackage.NODE__PROPERTIES:
				return properties != null;
			case FmpPackage.NODE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
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
		result.append(" (max: ");
		result.append(max);
		result.append(", min: ");
		result.append(min);
		result.append(", id: ");
		result.append(id);
		result.append(')');
		return result.toString();
	}

} //NodeImpl
