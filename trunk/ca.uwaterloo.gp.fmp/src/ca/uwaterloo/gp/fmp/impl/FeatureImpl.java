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
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import ca.uwaterloo.gp.fmp.Clonable;
import ca.uwaterloo.gp.fmp.ConfigState;
import ca.uwaterloo.gp.fmp.Constraint;
import ca.uwaterloo.gp.fmp.Feature;
import ca.uwaterloo.gp.fmp.FmpFactory;
import ca.uwaterloo.gp.fmp.FmpPackage;
import ca.uwaterloo.gp.fmp.Node;
import ca.uwaterloo.gp.fmp.Reference;
import ca.uwaterloo.gp.fmp.TypedValue;
import ca.uwaterloo.gp.fmp.ValueType;
import ca.uwaterloo.gp.fmp.system.ModelNavigation;


/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Feature</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link ca.uwaterloo.gp.fmp.impl.FeatureImpl#getName <em>Name</em>}</li>
 *   <li>{@link ca.uwaterloo.gp.fmp.impl.FeatureImpl#getValueType <em>Value Type</em>}</li>
 *   <li>{@link ca.uwaterloo.gp.fmp.impl.FeatureImpl#getConfigurations <em>Configurations</em>}</li>
 *   <li>{@link ca.uwaterloo.gp.fmp.impl.FeatureImpl#getReferences <em>References</em>}</li>
 *   <li>{@link ca.uwaterloo.gp.fmp.impl.FeatureImpl#getTypedValue <em>Typed Value</em>}</li>
 *   <li>{@link ca.uwaterloo.gp.fmp.impl.FeatureImpl#getDescribedNode <em>Described Node</em>}</li>
 *   <li>{@link ca.uwaterloo.gp.fmp.impl.FeatureImpl#getConstraints <em>Constraints</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FeatureImpl extends ClonableImpl implements Feature {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getValueType() <em>Value Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueType()
	 * @generated
	 * @ordered
	 */
	protected static final ValueType VALUE_TYPE_EDEFAULT = ValueType.FEATURE_LITERAL;

	/**
	 * The cached value of the '{@link #getValueType() <em>Value Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueType()
	 * @generated
	 * @ordered
	 */
	protected ValueType valueType = VALUE_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getConfigurations() <em>Configurations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfigurations()
	 * @generated
	 * @ordered
	 */
	protected EList configurations = null;

	/**
	 * The cached value of the '{@link #getReferences() <em>References</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferences()
	 * @generated
	 * @ordered
	 */
	protected EList references = null;

	/**
	 * The cached value of the '{@link #getTypedValue() <em>Typed Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypedValue()
	 * @generated
	 * @ordered
	 */
	protected TypedValue typedValue = null;

	/**
	 * The cached value of the '{@link #getDescribedNode() <em>Described Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescribedNode()
	 * @generated
	 * @ordered
	 */
	protected Node describedNode = null;

	/**
	 * The cached value of the '{@link #getConstraints() <em>Constraints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList constraints = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FeatureImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return FmpPackage.eINSTANCE.getFeature();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getName() {
		if(properties == null)		
			return name;
		else
		{
			List names = ModelNavigation.INSTANCE.getNodes(properties, "Name");
			if (names.size() > 0) {
				Feature nameNode = (Feature) names.get(0);
				if (nameNode.getTypedValue() !=  null)
					return nameNode.getTypedValue().getStringValue();
			}
				
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setName(String newName) {
		// Setting name on 
		String oldName = name;
		if(properties == null)
			name = newName;
		else
		{
			Feature name_ = (Feature)ModelNavigation.INSTANCE.getNodes(properties, "Name").get(0);
			TypedValue nameValue = FmpFactory.eINSTANCE.createTypedValue();
			nameValue.setStringValue(newName);
			name_.setTypedValue(nameValue);
		}
		// if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FmpPackage.FEATURE__NAME, oldName, name));		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public ValueType getValueType() {
		if(properties == null)
			return valueType;
		else
		{
			List types = ModelNavigation.INSTANCE.getNodes(properties, "Attribute/*");
			List selectedTypes= ModelNavigation.INSTANCE.getSelectedClonables(types);
			if (selectedTypes.size() > 0) {
				Feature valueTypeFeature = (Feature)selectedTypes.get(0);
				Feature valueNode = (Feature) ModelNavigation.INSTANCE.getNodes(valueTypeFeature, "Value").get(0);
				return valueNode.getValueType();
			}
		}		
		return ValueType.NONE_LITERAL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setValueType(ValueType newValueType) {
		ValueType oldValueType = valueType;
		if(properties == null)
		{
			valueType = newValueType == null ? VALUE_TYPE_EDEFAULT : newValueType;		
		}
		else
		{
			String valueTypeFeatureId = "Attribute/";
			if(newValueType == ValueType.INTEGER_LITERAL)
				valueTypeFeatureId += "Integer";
			else if(newValueType == ValueType.STRING_LITERAL)
				valueTypeFeatureId += "String";
			else if(newValueType == ValueType.FLOAT_LITERAL)
				valueTypeFeatureId += "Float";
			else if(newValueType == ValueType.FEATURE_LITERAL)
				valueTypeFeatureId += "Feature";
			else {
				// make sure all values are undecided
				List types = ModelNavigation.INSTANCE.getNodes(properties, "Attribute/*");
				for (Iterator i = types.iterator(); i.hasNext(); ) {
					Feature type = (Feature) i.next();
					type.setState(ConfigState.UNDECIDED_LITERAL);
				}
				return;
			}
			Feature valueTypeFeature_ = (Feature)ModelNavigation.INSTANCE.getNodes(properties, valueTypeFeatureId).get(0);
			valueTypeFeature_.setState(ConfigState.MACHINE_SELECTED_LITERAL);
		}
		// if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FmpPackage.FEATURE__VALUE_TYPE, oldValueType, valueType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getConfigurations() {
		if (configurations == null) {
			configurations = new EObjectContainmentEList(Feature.class, this, FmpPackage.FEATURE__CONFIGURATIONS);
		}
		return configurations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getReferences() {
		if (references == null) {
			references = new EObjectWithInverseResolvingEList(Reference.class, this, FmpPackage.FEATURE__REFERENCES, FmpPackage.REFERENCE__FEATURE);
		}
		return references;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public TypedValue getTypedValue() {
		if(properties == null)
			return typedValue;
		else
		{
			List values = ModelNavigation.INSTANCE.getNodes(properties, "Attribute/*/Value");
			List selectedValues = ModelNavigation.INSTANCE.getSelectedClonables(values);
			if (selectedValues.size() > 0) {
				Feature valueNode = (Feature)selectedValues.get(0);
				return valueNode.getTypedValue();
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTypedValue(TypedValue newTypedValue, NotificationChain msgs) {
		TypedValue oldTypedValue = typedValue;
		typedValue = newTypedValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FmpPackage.FEATURE__TYPED_VALUE, oldTypedValue, newTypedValue);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setTypedValue(TypedValue newTypedValue) {
		if(properties == null)
		{
			if (newTypedValue != typedValue) {
				NotificationChain msgs = null;
				if (typedValue != null)
					msgs = ((InternalEObject)typedValue).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - FmpPackage.FEATURE__TYPED_VALUE, null, msgs);
				if (newTypedValue != null)
					msgs = ((InternalEObject)newTypedValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - FmpPackage.FEATURE__TYPED_VALUE, null, msgs);
				msgs = basicSetTypedValue(newTypedValue, msgs);
				if (msgs != null) msgs.dispatch();
			}
		}
		else
		{
			// retrieve the type
			String type = null;
			switch (this.getValueType().getValue()) {
				case ValueType.FEATURE:
					type = "Feature"; break;
				case ValueType.FLOAT:
					type = "Float"; break;
				case ValueType.INTEGER:
					type = "Integer"; break;
				case ValueType.STRING:
					type = "String"; break;
			}
			if (type != null) {
				List values = ModelNavigation.INSTANCE.getNodes(properties, "Attribute/" + type + "/Value");
				if (values.size() > 0) {
					Feature valueFeature = (Feature)values.get(0);
					valueFeature.setTypedValue(newTypedValue);
					valueFeature.setState(ConfigState.MACHINE_SELECTED_LITERAL);
				}
			}
		}
//		 if (eNotificationRequired())
		eNotify(new ENotificationImpl(this, Notification.SET, FmpPackage.FEATURE__TYPED_VALUE, newTypedValue, newTypedValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node getDescribedNode() {
		if (describedNode != null && describedNode.eIsProxy()) {
			Node oldDescribedNode = describedNode;
			describedNode = (Node)eResolveProxy((InternalEObject)describedNode);
			if (describedNode != oldDescribedNode) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FmpPackage.FEATURE__DESCRIBED_NODE, oldDescribedNode, describedNode));
			}
		}
		return describedNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node basicGetDescribedNode() {
		return describedNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDescribedNode(Node newDescribedNode, NotificationChain msgs) {
		Node oldDescribedNode = describedNode;
		describedNode = newDescribedNode;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, FmpPackage.FEATURE__DESCRIBED_NODE, oldDescribedNode, newDescribedNode);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescribedNode(Node newDescribedNode) {
		if (newDescribedNode != describedNode) {
			NotificationChain msgs = null;
			if (describedNode != null)
				msgs = ((InternalEObject)describedNode).eInverseRemove(this, FmpPackage.NODE__PROPERTIES, Node.class, msgs);
			if (newDescribedNode != null)
				msgs = ((InternalEObject)newDescribedNode).eInverseAdd(this, FmpPackage.NODE__PROPERTIES, Node.class, msgs);
			msgs = basicSetDescribedNode(newDescribedNode, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FmpPackage.FEATURE__DESCRIBED_NODE, newDescribedNode, newDescribedNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getConstraints() {
		if (constraints == null) {
			constraints = new EObjectContainmentEList(Constraint.class, this, FmpPackage.FEATURE__CONSTRAINTS);
		}
		return constraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case FmpPackage.FEATURE__CONFS:
					return ((InternalEList)getConfs()).basicAdd(otherEnd, msgs);
				case FmpPackage.FEATURE__ORIGIN:
					if (origin != null)
						msgs = ((InternalEObject)origin).eInverseRemove(this, FmpPackage.NODE__CONFS, Node.class, msgs);
					return basicSetOrigin((Node)otherEnd, msgs);
				case FmpPackage.FEATURE__PROPERTIES:
					if (properties != null)
						msgs = ((InternalEObject)properties).eInverseRemove(this, FmpPackage.FEATURE__DESCRIBED_NODE, Feature.class, msgs);
					return basicSetProperties((Feature)otherEnd, msgs);
				case FmpPackage.FEATURE__CLONES:
					return ((InternalEList)getClones()).basicAdd(otherEnd, msgs);
				case FmpPackage.FEATURE__PROTOTYPE:
					if (prototype != null)
						msgs = ((InternalEObject)prototype).eInverseRemove(this, FmpPackage.CLONABLE__CLONES, Clonable.class, msgs);
					return basicSetPrototype((Clonable)otherEnd, msgs);
				case FmpPackage.FEATURE__REFERENCES:
					return ((InternalEList)getReferences()).basicAdd(otherEnd, msgs);
				case FmpPackage.FEATURE__DESCRIBED_NODE:
					if (describedNode != null)
						msgs = ((InternalEObject)describedNode).eInverseRemove(this, FmpPackage.NODE__PROPERTIES, Node.class, msgs);
					return basicSetDescribedNode((Node)otherEnd, msgs);
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
				case FmpPackage.FEATURE__CONFS:
					return ((InternalEList)getConfs()).basicRemove(otherEnd, msgs);
				case FmpPackage.FEATURE__ORIGIN:
					return basicSetOrigin(null, msgs);
				case FmpPackage.FEATURE__CHILDREN:
					return ((InternalEList)getChildren()).basicRemove(otherEnd, msgs);
				case FmpPackage.FEATURE__PROPERTIES:
					return basicSetProperties(null, msgs);
				case FmpPackage.FEATURE__CLONES:
					return ((InternalEList)getClones()).basicRemove(otherEnd, msgs);
				case FmpPackage.FEATURE__PROTOTYPE:
					return basicSetPrototype(null, msgs);
				case FmpPackage.FEATURE__CONFIGURATIONS:
					return ((InternalEList)getConfigurations()).basicRemove(otherEnd, msgs);
				case FmpPackage.FEATURE__REFERENCES:
					return ((InternalEList)getReferences()).basicRemove(otherEnd, msgs);
				case FmpPackage.FEATURE__TYPED_VALUE:
					return basicSetTypedValue(null, msgs);
				case FmpPackage.FEATURE__DESCRIBED_NODE:
					return basicSetDescribedNode(null, msgs);
				case FmpPackage.FEATURE__CONSTRAINTS:
					return ((InternalEList)getConstraints()).basicRemove(otherEnd, msgs);
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
			case FmpPackage.FEATURE__MAX:
				return new Integer(getMax());
			case FmpPackage.FEATURE__MIN:
				return new Integer(getMin());
			case FmpPackage.FEATURE__CONFS:
				return getConfs();
			case FmpPackage.FEATURE__ORIGIN:
				if (resolve) return getOrigin();
				return basicGetOrigin();
			case FmpPackage.FEATURE__CHILDREN:
				return getChildren();
			case FmpPackage.FEATURE__PROPERTIES:
				if (resolve) return getProperties();
				return basicGetProperties();
			case FmpPackage.FEATURE__ID:
				return getId();
			case FmpPackage.FEATURE__STATE:
				return getState();
			case FmpPackage.FEATURE__CLONES:
				return getClones();
			case FmpPackage.FEATURE__PROTOTYPE:
				if (resolve) return getPrototype();
				return basicGetPrototype();
			case FmpPackage.FEATURE__NAME:
				return getName();
			case FmpPackage.FEATURE__VALUE_TYPE:
				return getValueType();
			case FmpPackage.FEATURE__CONFIGURATIONS:
				return getConfigurations();
			case FmpPackage.FEATURE__REFERENCES:
				return getReferences();
			case FmpPackage.FEATURE__TYPED_VALUE:
				return getTypedValue();
			case FmpPackage.FEATURE__DESCRIBED_NODE:
				if (resolve) return getDescribedNode();
				return basicGetDescribedNode();
			case FmpPackage.FEATURE__CONSTRAINTS:
				return getConstraints();
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
			case FmpPackage.FEATURE__MAX:
				setMax(((Integer)newValue).intValue());
				return;
			case FmpPackage.FEATURE__MIN:
				setMin(((Integer)newValue).intValue());
				return;
			case FmpPackage.FEATURE__CONFS:
				getConfs().clear();
				getConfs().addAll((Collection)newValue);
				return;
			case FmpPackage.FEATURE__ORIGIN:
				setOrigin((Node)newValue);
				return;
			case FmpPackage.FEATURE__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection)newValue);
				return;
			case FmpPackage.FEATURE__PROPERTIES:
				setProperties((Feature)newValue);
				return;
			case FmpPackage.FEATURE__ID:
				setId((String)newValue);
				return;
			case FmpPackage.FEATURE__STATE:
				setState((ConfigState)newValue);
				return;
			case FmpPackage.FEATURE__CLONES:
				getClones().clear();
				getClones().addAll((Collection)newValue);
				return;
			case FmpPackage.FEATURE__PROTOTYPE:
				setPrototype((Clonable)newValue);
				return;
			case FmpPackage.FEATURE__NAME:
				setName((String)newValue);
				return;
			case FmpPackage.FEATURE__VALUE_TYPE:
				setValueType((ValueType)newValue);
				return;
			case FmpPackage.FEATURE__CONFIGURATIONS:
				getConfigurations().clear();
				getConfigurations().addAll((Collection)newValue);
				return;
			case FmpPackage.FEATURE__REFERENCES:
				getReferences().clear();
				getReferences().addAll((Collection)newValue);
				return;
			case FmpPackage.FEATURE__TYPED_VALUE:
				setTypedValue((TypedValue)newValue);
				return;
			case FmpPackage.FEATURE__DESCRIBED_NODE:
				setDescribedNode((Node)newValue);
				return;
			case FmpPackage.FEATURE__CONSTRAINTS:
				getConstraints().clear();
				getConstraints().addAll((Collection)newValue);
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
			case FmpPackage.FEATURE__MAX:
				setMax(MAX_EDEFAULT);
				return;
			case FmpPackage.FEATURE__MIN:
				setMin(MIN_EDEFAULT);
				return;
			case FmpPackage.FEATURE__CONFS:
				getConfs().clear();
				return;
			case FmpPackage.FEATURE__ORIGIN:
				setOrigin((Node)null);
				return;
			case FmpPackage.FEATURE__CHILDREN:
				getChildren().clear();
				return;
			case FmpPackage.FEATURE__PROPERTIES:
				setProperties((Feature)null);
				return;
			case FmpPackage.FEATURE__ID:
				setId(ID_EDEFAULT);
				return;
			case FmpPackage.FEATURE__STATE:
				setState(STATE_EDEFAULT);
				return;
			case FmpPackage.FEATURE__CLONES:
				getClones().clear();
				return;
			case FmpPackage.FEATURE__PROTOTYPE:
				setPrototype((Clonable)null);
				return;
			case FmpPackage.FEATURE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case FmpPackage.FEATURE__VALUE_TYPE:
				setValueType(VALUE_TYPE_EDEFAULT);
				return;
			case FmpPackage.FEATURE__CONFIGURATIONS:
				getConfigurations().clear();
				return;
			case FmpPackage.FEATURE__REFERENCES:
				getReferences().clear();
				return;
			case FmpPackage.FEATURE__TYPED_VALUE:
				setTypedValue((TypedValue)null);
				return;
			case FmpPackage.FEATURE__DESCRIBED_NODE:
				setDescribedNode((Node)null);
				return;
			case FmpPackage.FEATURE__CONSTRAINTS:
				getConstraints().clear();
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
			case FmpPackage.FEATURE__MAX:
				return max != MAX_EDEFAULT;
			case FmpPackage.FEATURE__MIN:
				return min != MIN_EDEFAULT;
			case FmpPackage.FEATURE__CONFS:
				return confs != null && !confs.isEmpty();
			case FmpPackage.FEATURE__ORIGIN:
				return origin != null;
			case FmpPackage.FEATURE__CHILDREN:
				return children != null && !children.isEmpty();
			case FmpPackage.FEATURE__PROPERTIES:
				return properties != null;
			case FmpPackage.FEATURE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case FmpPackage.FEATURE__STATE:
				return state != STATE_EDEFAULT;
			case FmpPackage.FEATURE__CLONES:
				return clones != null && !clones.isEmpty();
			case FmpPackage.FEATURE__PROTOTYPE:
				return prototype != null;
			case FmpPackage.FEATURE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case FmpPackage.FEATURE__VALUE_TYPE:
				return valueType != VALUE_TYPE_EDEFAULT;
			case FmpPackage.FEATURE__CONFIGURATIONS:
				return configurations != null && !configurations.isEmpty();
			case FmpPackage.FEATURE__REFERENCES:
				return references != null && !references.isEmpty();
			case FmpPackage.FEATURE__TYPED_VALUE:
				return typedValue != null;
			case FmpPackage.FEATURE__DESCRIBED_NODE:
				return describedNode != null;
			case FmpPackage.FEATURE__CONSTRAINTS:
				return constraints != null && !constraints.isEmpty();
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
		result.append(" (name: ");
		result.append(name);
		result.append(", valueType: ");
		result.append(valueType);
		result.append(')');
		return result.toString();
	}

	/**
	 * @generated NOT
	 */
	public TypedValue getDefaultValue() {
		if(properties == null)
			// default value only exists in properties
			return null;
		else
		{
			List values = ModelNavigation.INSTANCE.getNodes(properties, "Attribute/*/DefaultValue");
			List selectedValues = ModelNavigation.INSTANCE.getSelectedClonables(values);
			if (selectedValues.size() > 0) {
				Feature valueNode = (Feature)selectedValues.get(0);
				return valueNode.getTypedValue();
			}
		}
		return null;
	}

	/**
	 * @generated NOT
	 */
	public void setDefaultValue(TypedValue defaultTypedValue) {
		// default value only exists in properties
		if(properties != null) {
//			 retrieve the type
			String type = null;
			switch (this.getValueType().getValue()) {
				case ValueType.FEATURE:
					type = "Feature"; break;
				case ValueType.FLOAT:
					type = "Float"; break;
				case ValueType.INTEGER:
					type = "Integer"; break;
				case ValueType.STRING:
					type = "String"; break;
			}
			if (type != null) {
				List values = ModelNavigation.INSTANCE.getNodes(properties, "Attribute/" + type + "/DefaultValue");
				if (values.size() > 0) {
					Feature valueNode = (Feature)values.get(0);
					valueNode.setTypedValue(defaultTypedValue);
					valueNode.setState(ConfigState.MACHINE_SELECTED_LITERAL);
				}
			}
		}
	}

} //FeatureImpl
