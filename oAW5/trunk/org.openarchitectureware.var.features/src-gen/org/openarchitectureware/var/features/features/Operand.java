/**
 * <copyright>
 * </copyright>
 *

 */
package org.openarchitectureware.var.features.features;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Operand</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.openarchitectureware.var.features.features.Operand#isIsNot <em>Is Not</em>}</li>
 *   <li>{@link org.openarchitectureware.var.features.features.Operand#getExpression <em>Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.openarchitectureware.var.features.features.FeaturesPackage#getOperand()
 * @model
 * @generated
 */
public interface Operand extends EObject
{
  /**
   * Returns the value of the '<em><b>Is Not</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Is Not</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Is Not</em>' attribute.
   * @see #setIsNot(boolean)
   * @see org.openarchitectureware.var.features.features.FeaturesPackage#getOperand_IsNot()
   * @model
   * @generated
   */
  boolean isIsNot();

  /**
   * Sets the value of the '{@link org.openarchitectureware.var.features.features.Operand#isIsNot <em>Is Not</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Is Not</em>' attribute.
   * @see #isIsNot()
   * @generated
   */
  void setIsNot(boolean value);

  /**
   * Returns the value of the '<em><b>Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Expression</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Expression</em>' containment reference.
   * @see #setExpression(Atom)
   * @see org.openarchitectureware.var.features.features.FeaturesPackage#getOperand_Expression()
   * @model containment="true"
   * @generated
   */
  Atom getExpression();

  /**
   * Sets the value of the '{@link org.openarchitectureware.var.features.features.Operand#getExpression <em>Expression</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Expression</em>' containment reference.
   * @see #getExpression()
   * @generated
   */
  void setExpression(Atom value);

} // Operand
