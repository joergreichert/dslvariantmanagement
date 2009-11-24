/**
 * <copyright>
 * </copyright>
 *

 */
package org.openarchitectureware.var.features.features;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feature Clause</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.openarchitectureware.var.features.features.FeatureClause#isRetained <em>Retained</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.openarchitectureware.var.features.features.FeaturesPackage#getFeatureClause()
 * @model
 * @generated
 */
public interface FeatureClause extends EObject
{
  /**
   * Returns the value of the '<em><b>Retained</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Retained</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Retained</em>' attribute.
   * @see #setRetained(boolean)
   * @see org.openarchitectureware.var.features.features.FeaturesPackage#getFeatureClause_Retained()
   * @model
   * @generated
   */
  boolean isRetained();

  /**
   * Sets the value of the '{@link org.openarchitectureware.var.features.features.FeatureClause#isRetained <em>Retained</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Retained</em>' attribute.
   * @see #isRetained()
   * @generated
   */
  void setRetained(boolean value);

} // FeatureClause
