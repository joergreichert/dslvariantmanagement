/**
 * <copyright>
 * </copyright>
 *

 */
package org.openarchitectureware.var.features.features;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.openarchitectureware.var.features.features.FeaturesFactory
 * @model kind="package"
 * @generated
 */
public interface FeaturesPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "features";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.openarchitectureware.org/var/features/Features";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "features";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  FeaturesPackage eINSTANCE = org.openarchitectureware.var.features.features.impl.FeaturesPackageImpl.init();

  /**
   * The meta object id for the '{@link org.openarchitectureware.var.features.features.impl.FeatureClauseImpl <em>Feature Clause</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.openarchitectureware.var.features.features.impl.FeatureClauseImpl
   * @see org.openarchitectureware.var.features.features.impl.FeaturesPackageImpl#getFeatureClause()
   * @generated
   */
  int FEATURE_CLAUSE = 0;

  /**
   * The feature id for the '<em><b>Retained</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_CLAUSE__RETAINED = 0;

  /**
   * The number of structural features of the '<em>Feature Clause</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_CLAUSE_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.openarchitectureware.var.features.features.impl.FeatureAndListImpl <em>Feature And List</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.openarchitectureware.var.features.features.impl.FeatureAndListImpl
   * @see org.openarchitectureware.var.features.features.impl.FeaturesPackageImpl#getFeatureAndList()
   * @generated
   */
  int FEATURE_AND_LIST = 1;

  /**
   * The feature id for the '<em><b>Retained</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_AND_LIST__RETAINED = FEATURE_CLAUSE__RETAINED;

  /**
   * The feature id for the '<em><b>Feature List</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_AND_LIST__FEATURE_LIST = FEATURE_CLAUSE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Feature And List</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_AND_LIST_FEATURE_COUNT = FEATURE_CLAUSE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.openarchitectureware.var.features.features.impl.FeatureOrListImpl <em>Feature Or List</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.openarchitectureware.var.features.features.impl.FeatureOrListImpl
   * @see org.openarchitectureware.var.features.features.impl.FeaturesPackageImpl#getFeatureOrList()
   * @generated
   */
  int FEATURE_OR_LIST = 2;

  /**
   * The feature id for the '<em><b>Retained</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_OR_LIST__RETAINED = FEATURE_CLAUSE__RETAINED;

  /**
   * The feature id for the '<em><b>Feature List</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_OR_LIST__FEATURE_LIST = FEATURE_CLAUSE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Feature Or List</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_OR_LIST_FEATURE_COUNT = FEATURE_CLAUSE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.openarchitectureware.var.features.features.impl.FeatureExpressionImpl <em>Feature Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.openarchitectureware.var.features.features.impl.FeatureExpressionImpl
   * @see org.openarchitectureware.var.features.features.impl.FeaturesPackageImpl#getFeatureExpression()
   * @generated
   */
  int FEATURE_EXPRESSION = 3;

  /**
   * The feature id for the '<em><b>Retained</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_EXPRESSION__RETAINED = FEATURE_CLAUSE__RETAINED;

  /**
   * The feature id for the '<em><b>Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_EXPRESSION__EXPRESSION = FEATURE_CLAUSE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Feature Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_EXPRESSION_FEATURE_COUNT = FEATURE_CLAUSE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.openarchitectureware.var.features.features.impl.FeatureImpl <em>Feature</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.openarchitectureware.var.features.features.impl.FeatureImpl
   * @see org.openarchitectureware.var.features.features.impl.FeaturesPackageImpl#getFeature()
   * @generated
   */
  int FEATURE = 4;

  /**
   * The feature id for the '<em><b>Retained</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE__RETAINED = FEATURE_CLAUSE__RETAINED;

  /**
   * The feature id for the '<em><b>Feature</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE__FEATURE = FEATURE_CLAUSE_FEATURE_COUNT + 0;

  /**
   * The number of structural features of the '<em>Feature</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FEATURE_FEATURE_COUNT = FEATURE_CLAUSE_FEATURE_COUNT + 1;

  /**
   * The meta object id for the '{@link org.openarchitectureware.var.features.features.impl.OrExpressionImpl <em>Or Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.openarchitectureware.var.features.features.impl.OrExpressionImpl
   * @see org.openarchitectureware.var.features.features.impl.FeaturesPackageImpl#getOrExpression()
   * @generated
   */
  int OR_EXPRESSION = 5;

  /**
   * The feature id for the '<em><b>Operands</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OR_EXPRESSION__OPERANDS = 0;

  /**
   * The number of structural features of the '<em>Or Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OR_EXPRESSION_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.openarchitectureware.var.features.features.impl.AndExpressionImpl <em>And Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.openarchitectureware.var.features.features.impl.AndExpressionImpl
   * @see org.openarchitectureware.var.features.features.impl.FeaturesPackageImpl#getAndExpression()
   * @generated
   */
  int AND_EXPRESSION = 6;

  /**
   * The feature id for the '<em><b>Operands</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AND_EXPRESSION__OPERANDS = 0;

  /**
   * The number of structural features of the '<em>And Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AND_EXPRESSION_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.openarchitectureware.var.features.features.impl.OperandImpl <em>Operand</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.openarchitectureware.var.features.features.impl.OperandImpl
   * @see org.openarchitectureware.var.features.features.impl.FeaturesPackageImpl#getOperand()
   * @generated
   */
  int OPERAND = 7;

  /**
   * The feature id for the '<em><b>Is Not</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPERAND__IS_NOT = 0;

  /**
   * The feature id for the '<em><b>Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPERAND__EXPRESSION = 1;

  /**
   * The number of structural features of the '<em>Operand</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OPERAND_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.openarchitectureware.var.features.features.impl.AtomImpl <em>Atom</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.openarchitectureware.var.features.features.impl.AtomImpl
   * @see org.openarchitectureware.var.features.features.impl.FeaturesPackageImpl#getAtom()
   * @generated
   */
  int ATOM = 8;

  /**
   * The feature id for the '<em><b>Feature</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ATOM__FEATURE = 0;

  /**
   * The feature id for the '<em><b>Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ATOM__EXPRESSION = 1;

  /**
   * The number of structural features of the '<em>Atom</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ATOM_FEATURE_COUNT = 2;


  /**
   * Returns the meta object for class '{@link org.openarchitectureware.var.features.features.FeatureClause <em>Feature Clause</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Feature Clause</em>'.
   * @see org.openarchitectureware.var.features.features.FeatureClause
   * @generated
   */
  EClass getFeatureClause();

  /**
   * Returns the meta object for the attribute '{@link org.openarchitectureware.var.features.features.FeatureClause#isRetained <em>Retained</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Retained</em>'.
   * @see org.openarchitectureware.var.features.features.FeatureClause#isRetained()
   * @see #getFeatureClause()
   * @generated
   */
  EAttribute getFeatureClause_Retained();

  /**
   * Returns the meta object for class '{@link org.openarchitectureware.var.features.features.FeatureAndList <em>Feature And List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Feature And List</em>'.
   * @see org.openarchitectureware.var.features.features.FeatureAndList
   * @generated
   */
  EClass getFeatureAndList();

  /**
   * Returns the meta object for the attribute list '{@link org.openarchitectureware.var.features.features.FeatureAndList#getFeatureList <em>Feature List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Feature List</em>'.
   * @see org.openarchitectureware.var.features.features.FeatureAndList#getFeatureList()
   * @see #getFeatureAndList()
   * @generated
   */
  EAttribute getFeatureAndList_FeatureList();

  /**
   * Returns the meta object for class '{@link org.openarchitectureware.var.features.features.FeatureOrList <em>Feature Or List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Feature Or List</em>'.
   * @see org.openarchitectureware.var.features.features.FeatureOrList
   * @generated
   */
  EClass getFeatureOrList();

  /**
   * Returns the meta object for the attribute list '{@link org.openarchitectureware.var.features.features.FeatureOrList#getFeatureList <em>Feature List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Feature List</em>'.
   * @see org.openarchitectureware.var.features.features.FeatureOrList#getFeatureList()
   * @see #getFeatureOrList()
   * @generated
   */
  EAttribute getFeatureOrList_FeatureList();

  /**
   * Returns the meta object for class '{@link org.openarchitectureware.var.features.features.FeatureExpression <em>Feature Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Feature Expression</em>'.
   * @see org.openarchitectureware.var.features.features.FeatureExpression
   * @generated
   */
  EClass getFeatureExpression();

  /**
   * Returns the meta object for the containment reference '{@link org.openarchitectureware.var.features.features.FeatureExpression#getExpression <em>Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expression</em>'.
   * @see org.openarchitectureware.var.features.features.FeatureExpression#getExpression()
   * @see #getFeatureExpression()
   * @generated
   */
  EReference getFeatureExpression_Expression();

  /**
   * Returns the meta object for class '{@link org.openarchitectureware.var.features.features.Feature <em>Feature</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Feature</em>'.
   * @see org.openarchitectureware.var.features.features.Feature
   * @generated
   */
  EClass getFeature();

  /**
   * Returns the meta object for the attribute '{@link org.openarchitectureware.var.features.features.Feature#getFeature <em>Feature</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Feature</em>'.
   * @see org.openarchitectureware.var.features.features.Feature#getFeature()
   * @see #getFeature()
   * @generated
   */
  EAttribute getFeature_Feature();

  /**
   * Returns the meta object for class '{@link org.openarchitectureware.var.features.features.OrExpression <em>Or Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Or Expression</em>'.
   * @see org.openarchitectureware.var.features.features.OrExpression
   * @generated
   */
  EClass getOrExpression();

  /**
   * Returns the meta object for the containment reference list '{@link org.openarchitectureware.var.features.features.OrExpression#getOperands <em>Operands</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Operands</em>'.
   * @see org.openarchitectureware.var.features.features.OrExpression#getOperands()
   * @see #getOrExpression()
   * @generated
   */
  EReference getOrExpression_Operands();

  /**
   * Returns the meta object for class '{@link org.openarchitectureware.var.features.features.AndExpression <em>And Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>And Expression</em>'.
   * @see org.openarchitectureware.var.features.features.AndExpression
   * @generated
   */
  EClass getAndExpression();

  /**
   * Returns the meta object for the containment reference list '{@link org.openarchitectureware.var.features.features.AndExpression#getOperands <em>Operands</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Operands</em>'.
   * @see org.openarchitectureware.var.features.features.AndExpression#getOperands()
   * @see #getAndExpression()
   * @generated
   */
  EReference getAndExpression_Operands();

  /**
   * Returns the meta object for class '{@link org.openarchitectureware.var.features.features.Operand <em>Operand</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Operand</em>'.
   * @see org.openarchitectureware.var.features.features.Operand
   * @generated
   */
  EClass getOperand();

  /**
   * Returns the meta object for the attribute '{@link org.openarchitectureware.var.features.features.Operand#isIsNot <em>Is Not</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Is Not</em>'.
   * @see org.openarchitectureware.var.features.features.Operand#isIsNot()
   * @see #getOperand()
   * @generated
   */
  EAttribute getOperand_IsNot();

  /**
   * Returns the meta object for the containment reference '{@link org.openarchitectureware.var.features.features.Operand#getExpression <em>Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expression</em>'.
   * @see org.openarchitectureware.var.features.features.Operand#getExpression()
   * @see #getOperand()
   * @generated
   */
  EReference getOperand_Expression();

  /**
   * Returns the meta object for class '{@link org.openarchitectureware.var.features.features.Atom <em>Atom</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Atom</em>'.
   * @see org.openarchitectureware.var.features.features.Atom
   * @generated
   */
  EClass getAtom();

  /**
   * Returns the meta object for the attribute '{@link org.openarchitectureware.var.features.features.Atom#getFeature <em>Feature</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Feature</em>'.
   * @see org.openarchitectureware.var.features.features.Atom#getFeature()
   * @see #getAtom()
   * @generated
   */
  EAttribute getAtom_Feature();

  /**
   * Returns the meta object for the containment reference '{@link org.openarchitectureware.var.features.features.Atom#getExpression <em>Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expression</em>'.
   * @see org.openarchitectureware.var.features.features.Atom#getExpression()
   * @see #getAtom()
   * @generated
   */
  EReference getAtom_Expression();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  FeaturesFactory getFeaturesFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link org.openarchitectureware.var.features.features.impl.FeatureClauseImpl <em>Feature Clause</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.openarchitectureware.var.features.features.impl.FeatureClauseImpl
     * @see org.openarchitectureware.var.features.features.impl.FeaturesPackageImpl#getFeatureClause()
     * @generated
     */
    EClass FEATURE_CLAUSE = eINSTANCE.getFeatureClause();

    /**
     * The meta object literal for the '<em><b>Retained</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FEATURE_CLAUSE__RETAINED = eINSTANCE.getFeatureClause_Retained();

    /**
     * The meta object literal for the '{@link org.openarchitectureware.var.features.features.impl.FeatureAndListImpl <em>Feature And List</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.openarchitectureware.var.features.features.impl.FeatureAndListImpl
     * @see org.openarchitectureware.var.features.features.impl.FeaturesPackageImpl#getFeatureAndList()
     * @generated
     */
    EClass FEATURE_AND_LIST = eINSTANCE.getFeatureAndList();

    /**
     * The meta object literal for the '<em><b>Feature List</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FEATURE_AND_LIST__FEATURE_LIST = eINSTANCE.getFeatureAndList_FeatureList();

    /**
     * The meta object literal for the '{@link org.openarchitectureware.var.features.features.impl.FeatureOrListImpl <em>Feature Or List</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.openarchitectureware.var.features.features.impl.FeatureOrListImpl
     * @see org.openarchitectureware.var.features.features.impl.FeaturesPackageImpl#getFeatureOrList()
     * @generated
     */
    EClass FEATURE_OR_LIST = eINSTANCE.getFeatureOrList();

    /**
     * The meta object literal for the '<em><b>Feature List</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FEATURE_OR_LIST__FEATURE_LIST = eINSTANCE.getFeatureOrList_FeatureList();

    /**
     * The meta object literal for the '{@link org.openarchitectureware.var.features.features.impl.FeatureExpressionImpl <em>Feature Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.openarchitectureware.var.features.features.impl.FeatureExpressionImpl
     * @see org.openarchitectureware.var.features.features.impl.FeaturesPackageImpl#getFeatureExpression()
     * @generated
     */
    EClass FEATURE_EXPRESSION = eINSTANCE.getFeatureExpression();

    /**
     * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FEATURE_EXPRESSION__EXPRESSION = eINSTANCE.getFeatureExpression_Expression();

    /**
     * The meta object literal for the '{@link org.openarchitectureware.var.features.features.impl.FeatureImpl <em>Feature</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.openarchitectureware.var.features.features.impl.FeatureImpl
     * @see org.openarchitectureware.var.features.features.impl.FeaturesPackageImpl#getFeature()
     * @generated
     */
    EClass FEATURE = eINSTANCE.getFeature();

    /**
     * The meta object literal for the '<em><b>Feature</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FEATURE__FEATURE = eINSTANCE.getFeature_Feature();

    /**
     * The meta object literal for the '{@link org.openarchitectureware.var.features.features.impl.OrExpressionImpl <em>Or Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.openarchitectureware.var.features.features.impl.OrExpressionImpl
     * @see org.openarchitectureware.var.features.features.impl.FeaturesPackageImpl#getOrExpression()
     * @generated
     */
    EClass OR_EXPRESSION = eINSTANCE.getOrExpression();

    /**
     * The meta object literal for the '<em><b>Operands</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference OR_EXPRESSION__OPERANDS = eINSTANCE.getOrExpression_Operands();

    /**
     * The meta object literal for the '{@link org.openarchitectureware.var.features.features.impl.AndExpressionImpl <em>And Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.openarchitectureware.var.features.features.impl.AndExpressionImpl
     * @see org.openarchitectureware.var.features.features.impl.FeaturesPackageImpl#getAndExpression()
     * @generated
     */
    EClass AND_EXPRESSION = eINSTANCE.getAndExpression();

    /**
     * The meta object literal for the '<em><b>Operands</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AND_EXPRESSION__OPERANDS = eINSTANCE.getAndExpression_Operands();

    /**
     * The meta object literal for the '{@link org.openarchitectureware.var.features.features.impl.OperandImpl <em>Operand</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.openarchitectureware.var.features.features.impl.OperandImpl
     * @see org.openarchitectureware.var.features.features.impl.FeaturesPackageImpl#getOperand()
     * @generated
     */
    EClass OPERAND = eINSTANCE.getOperand();

    /**
     * The meta object literal for the '<em><b>Is Not</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute OPERAND__IS_NOT = eINSTANCE.getOperand_IsNot();

    /**
     * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference OPERAND__EXPRESSION = eINSTANCE.getOperand_Expression();

    /**
     * The meta object literal for the '{@link org.openarchitectureware.var.features.features.impl.AtomImpl <em>Atom</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.openarchitectureware.var.features.features.impl.AtomImpl
     * @see org.openarchitectureware.var.features.features.impl.FeaturesPackageImpl#getAtom()
     * @generated
     */
    EClass ATOM = eINSTANCE.getAtom();

    /**
     * The meta object literal for the '<em><b>Feature</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ATOM__FEATURE = eINSTANCE.getAtom_Feature();

    /**
     * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference ATOM__EXPRESSION = eINSTANCE.getAtom_Expression();

  }

} //FeaturesPackage
