/**
 * <copyright>
 * </copyright>
 *

 */
package org.openarchitectureware.var.features.features;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feature Or List</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.openarchitectureware.var.features.features.FeatureOrList#getFeatureList <em>Feature List</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.openarchitectureware.var.features.features.FeaturesPackage#getFeatureOrList()
 * @model
 * @generated
 */
public interface FeatureOrList extends FeatureClause
{
  /**
   * Returns the value of the '<em><b>Feature List</b></em>' attribute list.
   * The list contents are of type {@link java.lang.String}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Feature List</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Feature List</em>' attribute list.
   * @see org.openarchitectureware.var.features.features.FeaturesPackage#getFeatureOrList_FeatureList()
   * @model unique="false"
   * @generated
   */
  EList<String> getFeatureList();

} // FeatureOrList
