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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import ca.uwaterloo.gp.fmp.Clonable;
import ca.uwaterloo.gp.fmp.ConfigState;
import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.FmpPackage;
import ca.uwaterloo.gp.fmp.Node;
import ca.uwaterloo.gp.fmp.system.RoleQuery;


/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Clonable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link ca.uwaterloo.gp.fmp.impl.ClonableImpl#getState <em>State</em>}</li>
 *   <li>{@link ca.uwaterloo.gp.fmp.impl.ClonableImpl#getClones <em>Clones</em>}</li>
 *   <li>{@link ca.uwaterloo.gp.fmp.impl.ClonableImpl#getPrototype <em>Prototype</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class ClonableImpl extends NodeImpl implements Clonable {
	/**
	 * The default value of the '{@link #getState() <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected static final ConfigState STATE_EDEFAULT = ConfigState.UNDECIDED_LITERAL;

	/**
	 * The cached value of the '{@link #getState() <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected ConfigState state = STATE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getClones() <em>Clones</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClones()
	 * @generated
	 * @ordered
	 */
	protected EList clones = null;

	/**
	 * The cached value of the '{@link #getPrototype() <em>Prototype</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrototype()
	 * @generated
	 * @ordered
	 */
	protected Clonable prototype = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ClonableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return FmpPackage.eINSTANCE.getClonable();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigState getState() {
		return state;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setState(ConfigState newState) {
		ConfigState oldState = state;
		state = newState == null ? STATE_EDEFAULT : newState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FmpPackage.CLONABLE__STATE, oldState, state));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getClones() {
		if (clones == null) {
			clones = new EObjectWithInverseResolvingEList(Clonable.class, this, FmpPackage.CLONABLE__CLONES, FmpPackage.CLONABLE__PROTOTYPE);
		}
		return clones;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Clonable getPrototype() {
		if (prototype != null && prototype.eIsProxy()) {
			Clonable oldPrototype = prototype;
			prototype = (Clonable)eResolveProxy((InternalEObject)prototype);
			if (prototype != oldPrototype) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FmpPackage.CLONABLE__PROTOTYPE, oldPrototype, prototype));
			}
		}
		return prototype;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Clonable basicGetPrototype() {
		return prototype;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPrototype(Clonable newPrototype, NotificationChain msgs) {
		Clonable oldPrototype = prototype;
		prototype = newPrototype;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FmpPackage.CLONABLE__PROTOTYPE, oldPrototype, newPrototype);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPrototype(Clonable newPrototype) {
		if (newPrototype != prototype) {
			NotificationChain msgs = null;
			if (prototype != null)
				msgs = ((InternalEObject)prototype).eInverseRemove(this, FmpPackage.CLONABLE__CLONES, Clonable.class, msgs);
			if (newPrototype != null)
				msgs = ((InternalEObject)newPrototype).eInverseAdd(this, FmpPackage.CLONABLE__CLONES, Clonable.class, msgs);
			msgs = basicSetPrototype(newPrototype, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FmpPackage.CLONABLE__PROTOTYPE, newPrototype, newPrototype));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case FmpPackage.CLONABLE__CONFS:
					return ((InternalEList)getConfs()).basicAdd(otherEnd, msgs);
				case FmpPackage.CLONABLE__ORIGIN:
					if (origin != null)
						msgs = ((InternalEObject)origin).eInverseRemove(this, FmpPackage.NODE__CONFS, Node.class, msgs);
					return basicSetOrigin((Node)otherEnd, msgs);
				case FmpPackage.CLONABLE__PROPERTIES:
					if (properties != null)
						msgs = ((InternalEObject)properties).eInverseRemove(this, FmpPackage.FEATURE__DESCRIBED_NODE, Feature.class, msgs);
					return basicSetProperties((Feature)otherEnd, msgs);
				case FmpPackage.CLONABLE__CLONES:
					return ((InternalEList)getClones()).basicAdd(otherEnd, msgs);
				case FmpPackage.CLONABLE__PROTOTYPE:
					if (prototype != null)
						msgs = ((InternalEObject)prototype).eInverseRemove(this, FmpPackage.CLONABLE__CLONES, Clonable.class, msgs);
					return basicSetPrototype((Clonable)otherEnd, msgs);
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
				case FmpPackage.CLONABLE__CONFS:
					return ((InternalEList)getConfs()).basicRemove(otherEnd, msgs);
				case FmpPackage.CLONABLE__ORIGIN:
					return basicSetOrigin(null, msgs);
				case FmpPackage.CLONABLE__CHILDREN:
					return ((InternalEList)getChildren()).basicRemove(otherEnd, msgs);
				case FmpPackage.CLONABLE__PROPERTIES:
					return basicSetProperties(null, msgs);
				case FmpPackage.CLONABLE__CLONES:
					return ((InternalEList)getClones()).basicRemove(otherEnd, msgs);
				case FmpPackage.CLONABLE__PROTOTYPE:
					return basicSetPrototype(null, msgs);
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
			case FmpPackage.CLONABLE__MAX:
				return new Integer(getMax());
			case FmpPackage.CLONABLE__MIN:
				return new Integer(getMin());
			case FmpPackage.CLONABLE__CONFS:
				return getConfs();
			case FmpPackage.CLONABLE__ORIGIN:
				if (resolve) return getOrigin();
				return basicGetOrigin();
			case FmpPackage.CLONABLE__CHILDREN:
				return getChildren();
			case FmpPackage.CLONABLE__PROPERTIES:
				if (resolve) return getProperties();
				return basicGetProperties();
			case FmpPackage.CLONABLE__ID:
				return getId();
			case FmpPackage.CLONABLE__STATE:
				return getState();
			case FmpPackage.CLONABLE__CLONES:
				return getClones();
			case FmpPackage.CLONABLE__PROTOTYPE:
				if (resolve) return getPrototype();
				return basicGetPrototype();
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
			case FmpPackage.CLONABLE__MAX:
				setMax(((Integer)newValue).intValue());
				return;
			case FmpPackage.CLONABLE__MIN:
				setMin(((Integer)newValue).intValue());
				return;
			case FmpPackage.CLONABLE__CONFS:
				getConfs().clear();
				getConfs().addAll((Collection)newValue);
				return;
			case FmpPackage.CLONABLE__ORIGIN:
				setOrigin((Node)newValue);
				return;
			case FmpPackage.CLONABLE__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection)newValue);
				return;
			case FmpPackage.CLONABLE__PROPERTIES:
				setProperties((Feature)newValue);
				return;
			case FmpPackage.CLONABLE__ID:
				setId((String)newValue);
				return;
			case FmpPackage.CLONABLE__STATE:
				setState((ConfigState)newValue);
				return;
			case FmpPackage.CLONABLE__CLONES:
				getClones().clear();
				getClones().addAll((Collection)newValue);
				return;
			case FmpPackage.CLONABLE__PROTOTYPE:
				setPrototype((Clonable)newValue);
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
			case FmpPackage.CLONABLE__MAX:
				setMax(MAX_EDEFAULT);
				return;
			case FmpPackage.CLONABLE__MIN:
				setMin(MIN_EDEFAULT);
				return;
			case FmpPackage.CLONABLE__CONFS:
				getConfs().clear();
				return;
			case FmpPackage.CLONABLE__ORIGIN:
				setOrigin((Node)null);
				return;
			case FmpPackage.CLONABLE__CHILDREN:
				getChildren().clear();
				return;
			case FmpPackage.CLONABLE__PROPERTIES:
				setProperties((Feature)null);
				return;
			case FmpPackage.CLONABLE__ID:
				setId(ID_EDEFAULT);
				return;
			case FmpPackage.CLONABLE__STATE:
				setState(STATE_EDEFAULT);
				return;
			case FmpPackage.CLONABLE__CLONES:
				getClones().clear();
				return;
			case FmpPackage.CLONABLE__PROTOTYPE:
				setPrototype((Clonable)null);
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
			case FmpPackage.CLONABLE__MAX:
				return max != MAX_EDEFAULT;
			case FmpPackage.CLONABLE__MIN:
				return min != MIN_EDEFAULT;
			case FmpPackage.CLONABLE__CONFS:
				return confs != null && !confs.isEmpty();
			case FmpPackage.CLONABLE__ORIGIN:
				return origin != null;
			case FmpPackage.CLONABLE__CHILDREN:
				return children != null && !children.isEmpty();
			case FmpPackage.CLONABLE__PROPERTIES:
				return properties != null;
			case FmpPackage.CLONABLE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case FmpPackage.CLONABLE__STATE:
				return state != STATE_EDEFAULT;
			case FmpPackage.CLONABLE__CLONES:
				return clones != null && !clones.isEmpty();
			case FmpPackage.CLONABLE__PROTOTYPE:
				return prototype != null;
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
		result.append(" (state: ");
		result.append(state);
		result.append(')');
		return result.toString();
	}

	/**
	 * Returns the checkbox view cardinality of a feature or a reference as outlined in "Synchronizing Cardinality-Based
	 * Feature Models and Their Specializations" paper
	 */
	public int[] getCheckboxViewCardinality()
	{
		int min = getMin();
		int max = getMax();
		int []minMax = new int[2];
		minMax[0] = min;
		minMax[1] = max;
		
		int c = getClones().size();
		
		if(max == -1)
		{
			minMax[0] = Math.max(0, min-c);
		}
		else
		{
			if(min-c <= 0 && max-c <= 0)
			{
				minMax[0] = 0;
				minMax[1] = 0;
			}
			else if(min-c <= 0 && max-c == 1)
			{
				minMax[0] = 0;
				minMax[1] = 1;
			}
			else if(min-c == 1 && max-c == 1)
			{
				minMax[0] = 1;
				minMax[1] = 1;
			}
			else if(min-c <= 0 && max-c > 1)
			{
				minMax[0] = 0;
				minMax[1] = max-c;
			}
			else if(min-c >= 1 && max-c > 1)
			{
				minMax[0] = min-c;
				minMax[1] = max-c;
			}
		}
		return minMax;
	}
	
	public int[] getFeatureModelViewCardinality()
	{
		int[] featureModelViewCardinality = getCheckboxViewCardinality(); 
		
		// for optional features, take into account state
		if(featureModelViewCardinality[0] == 0 && featureModelViewCardinality[1] == 1)
		{
			ConfigState state = getState();
			if(state == ConfigState.USER_ELIMINATED_LITERAL || state == ConfigState.MACHINE_ELIMINATED_LITERAL)
			{
				featureModelViewCardinality[0] = 0;
				featureModelViewCardinality[1] = 0;
			}
			else if(state == ConfigState.UNDECIDED_LITERAL)
			{
				featureModelViewCardinality[0] = 0;
				featureModelViewCardinality[1] = 1;
			}
			else if(state == ConfigState.USER_SELECTED_LITERAL || state == ConfigState.MACHINE_SELECTED_LITERAL)
			{
				featureModelViewCardinality[0] = 1;
				featureModelViewCardinality[1] = 1;
			}
		}
		
		return featureModelViewCardinality;
	}
	
	public boolean isOptional()
	{
		int[] checkboxViewCardinality = getCheckboxViewCardinality();
		return (checkboxViewCardinality[0] == 0 && checkboxViewCardinality[1] == 1);
	}
	
	/**
	 * Determines whether or not this node is a truly optional node, as the product of cardinality of itself
	 * and all its parents is [0..1].
	 * @return
	 */
	public boolean isAccumulatedOptional()
	{
		Node curNode = this;
		int[] checkboxViewCardinality = getCheckboxViewCardinality();  
		int accumulatedMin = checkboxViewCardinality[0];
		int accumulatedMax = checkboxViewCardinality[1];
		
		while(RoleQuery.INSTANCE.getNodeType(curNode) != RoleQuery.ROOT_FEATURE
				&& accumulatedMin == 0 && accumulatedMax == 1)
		{
			curNode = (Node)curNode.eContainer();
			
			if(curNode instanceof Clonable)
			{
				Clonable curClonable = (Clonable)curNode;				
				checkboxViewCardinality = curClonable.getCheckboxViewCardinality();
				accumulatedMin *= checkboxViewCardinality[0];
				accumulatedMin *= checkboxViewCardinality[1];
			}
		}
			
		return (accumulatedMin == 0 && accumulatedMax == 1);
	}
} //ClonableImpl
