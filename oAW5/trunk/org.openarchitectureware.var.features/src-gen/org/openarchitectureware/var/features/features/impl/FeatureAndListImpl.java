/**
 * <copyright>
 * </copyright>
 *

 */
package org.openarchitectureware.var.features.features.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EDataTypeEList;

import org.openarchitectureware.var.features.features.FeatureAndList;
import org.openarchitectureware.var.features.features.FeaturesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Feature And List</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.openarchitectureware.var.features.features.impl.FeatureAndListImpl#getFeatureList <em>Feature List</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FeatureAndListImpl extends FeatureClauseImpl implements FeatureAndList
{
  /**
   * The cached value of the '{@link #getFeatureList() <em>Feature List</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFeatureList()
   * @generated
   * @ordered
   */
  protected EList<String> featureList;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected FeatureAndListImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return FeaturesPackage.Literals.FEATURE_AND_LIST;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<String> getFeatureList()
  {
    if (featureList == null)
    {
      featureList = new EDataTypeEList<String>(String.class, this, FeaturesPackage.FEATURE_AND_LIST__FEATURE_LIST);
    }
    return featureList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case FeaturesPackage.FEATURE_AND_LIST__FEATURE_LIST:
        return getFeatureList();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case FeaturesPackage.FEATURE_AND_LIST__FEATURE_LIST:
        getFeatureList().clear();
        getFeatureList().addAll((Collection<? extends String>)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case FeaturesPackage.FEATURE_AND_LIST__FEATURE_LIST:
        getFeatureList().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case FeaturesPackage.FEATURE_AND_LIST__FEATURE_LIST:
        return featureList != null && !featureList.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (featureList: ");
    result.append(featureList);
    result.append(')');
    return result.toString();
  }

} //FeatureAndListImpl
