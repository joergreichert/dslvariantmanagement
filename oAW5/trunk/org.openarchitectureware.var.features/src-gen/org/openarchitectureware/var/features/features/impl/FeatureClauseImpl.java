/**
 * <copyright>
 * </copyright>
 *

 */
package org.openarchitectureware.var.features.features.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.openarchitectureware.var.features.features.FeatureClause;
import org.openarchitectureware.var.features.features.FeaturesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Feature Clause</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.openarchitectureware.var.features.features.impl.FeatureClauseImpl#isRetained <em>Retained</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FeatureClauseImpl extends MinimalEObjectImpl.Container implements FeatureClause
{
  /**
   * The default value of the '{@link #isRetained() <em>Retained</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isRetained()
   * @generated
   * @ordered
   */
  protected static final boolean RETAINED_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isRetained() <em>Retained</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isRetained()
   * @generated
   * @ordered
   */
  protected boolean retained = RETAINED_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected FeatureClauseImpl()
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
    return FeaturesPackage.Literals.FEATURE_CLAUSE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isRetained()
  {
    return retained;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setRetained(boolean newRetained)
  {
    boolean oldRetained = retained;
    retained = newRetained;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, FeaturesPackage.FEATURE_CLAUSE__RETAINED, oldRetained, retained));
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
      case FeaturesPackage.FEATURE_CLAUSE__RETAINED:
        return isRetained();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case FeaturesPackage.FEATURE_CLAUSE__RETAINED:
        setRetained((Boolean)newValue);
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
      case FeaturesPackage.FEATURE_CLAUSE__RETAINED:
        setRetained(RETAINED_EDEFAULT);
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
      case FeaturesPackage.FEATURE_CLAUSE__RETAINED:
        return retained != RETAINED_EDEFAULT;
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
    result.append(" (retained: ");
    result.append(retained);
    result.append(')');
    return result.toString();
  }

} //FeatureClauseImpl
