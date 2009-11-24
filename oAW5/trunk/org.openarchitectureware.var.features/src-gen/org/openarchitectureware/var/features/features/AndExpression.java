/**
 * <copyright>
 * </copyright>
 *

 */
package org.openarchitectureware.var.features.features;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>And Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.openarchitectureware.var.features.features.AndExpression#getOperands <em>Operands</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.openarchitectureware.var.features.features.FeaturesPackage#getAndExpression()
 * @model
 * @generated
 */
public interface AndExpression extends EObject
{
  /**
   * Returns the value of the '<em><b>Operands</b></em>' containment reference list.
   * The list contents are of type {@link org.openarchitectureware.var.features.features.Operand}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Operands</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Operands</em>' containment reference list.
   * @see org.openarchitectureware.var.features.features.FeaturesPackage#getAndExpression_Operands()
   * @model containment="true"
   * @generated
   */
  EList<Operand> getOperands();

} // AndExpression
