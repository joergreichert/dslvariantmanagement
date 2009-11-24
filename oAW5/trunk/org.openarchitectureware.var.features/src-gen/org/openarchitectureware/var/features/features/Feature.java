/**
 * <copyright>
 * </copyright>
 *

 */
package org.openarchitectureware.var.features.features;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feature</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.openarchitectureware.var.features.features.Feature#getFeature <em>Feature</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.openarchitectureware.var.features.features.FeaturesPackage#getFeature()
 * @model
 * @generated
 */
public interface Feature extends FeatureClause
{
  /**
   * Returns the value of the '<em><b>Feature</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Feature</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Feature</em>' attribute.
   * @see #setFeature(String)
   * @see org.openarchitectureware.var.features.features.FeaturesPackage#getFeature_Feature()
   * @model
   * @generated
   */
  String getFeature();

  /**
   * Sets the value of the '{@link org.openarchitectureware.var.features.features.Feature#getFeature <em>Feature</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Feature</em>' attribute.
   * @see #getFeature()
   * @generated
   */
  void setFeature(String value);

} // Feature
