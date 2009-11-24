/**
 * <copyright>
 * </copyright>
 *

 */
package org.openarchitectureware.var.features.features.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.openarchitectureware.var.features.features.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class FeaturesFactoryImpl extends EFactoryImpl implements FeaturesFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static FeaturesFactory init()
  {
    try
    {
      FeaturesFactory theFeaturesFactory = (FeaturesFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.openarchitectureware.org/var/features/Features"); 
      if (theFeaturesFactory != null)
      {
        return theFeaturesFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new FeaturesFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FeaturesFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case FeaturesPackage.FEATURE_CLAUSE: return createFeatureClause();
      case FeaturesPackage.FEATURE_AND_LIST: return createFeatureAndList();
      case FeaturesPackage.FEATURE_OR_LIST: return createFeatureOrList();
      case FeaturesPackage.FEATURE_EXPRESSION: return createFeatureExpression();
      case FeaturesPackage.FEATURE: return createFeature();
      case FeaturesPackage.OR_EXPRESSION: return createOrExpression();
      case FeaturesPackage.AND_EXPRESSION: return createAndExpression();
      case FeaturesPackage.OPERAND: return createOperand();
      case FeaturesPackage.ATOM: return createAtom();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FeatureClause createFeatureClause()
  {
    FeatureClauseImpl featureClause = new FeatureClauseImpl();
    return featureClause;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FeatureAndList createFeatureAndList()
  {
    FeatureAndListImpl featureAndList = new FeatureAndListImpl();
    return featureAndList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FeatureOrList createFeatureOrList()
  {
    FeatureOrListImpl featureOrList = new FeatureOrListImpl();
    return featureOrList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FeatureExpression createFeatureExpression()
  {
    FeatureExpressionImpl featureExpression = new FeatureExpressionImpl();
    return featureExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Feature createFeature()
  {
    FeatureImpl feature = new FeatureImpl();
    return feature;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public OrExpression createOrExpression()
  {
    OrExpressionImpl orExpression = new OrExpressionImpl();
    return orExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AndExpression createAndExpression()
  {
    AndExpressionImpl andExpression = new AndExpressionImpl();
    return andExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Operand createOperand()
  {
    OperandImpl operand = new OperandImpl();
    return operand;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Atom createAtom()
  {
    AtomImpl atom = new AtomImpl();
    return atom;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FeaturesPackage getFeaturesPackage()
  {
    return (FeaturesPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static FeaturesPackage getPackage()
  {
    return FeaturesPackage.eINSTANCE;
  }

} //FeaturesFactoryImpl
