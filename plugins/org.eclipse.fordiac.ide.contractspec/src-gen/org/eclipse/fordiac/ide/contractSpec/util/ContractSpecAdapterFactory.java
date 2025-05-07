/**
 * Copyright (c) 2024 Felix Schmid
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Felix Schmid
 *     - initial implementation and/or documentation
 */
package org.eclipse.fordiac.ide.contractSpec.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.fordiac.ide.contractSpec.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage
 * @generated
 */
public class ContractSpecAdapterFactory extends AdapterFactoryImpl
{
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static ContractSpecPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ContractSpecAdapterFactory()
  {
    if (modelPackage == null)
    {
      modelPackage = ContractSpecPackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object object)
  {
    if (object == modelPackage)
    {
      return true;
    }
    if (object instanceof EObject)
    {
      return ((EObject)object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ContractSpecSwitch<Adapter> modelSwitch =
    new ContractSpecSwitch<Adapter>()
    {
      @Override
      public Adapter caseModel(Model object)
      {
        return createModelAdapter();
      }
      @Override
      public Adapter caseTimeSpec(TimeSpec object)
      {
        return createTimeSpecAdapter();
      }
      @Override
      public Adapter caseSingleEvent(SingleEvent object)
      {
        return createSingleEventAdapter();
      }
      @Override
      public Adapter caseRepetition(Repetition object)
      {
        return createRepetitionAdapter();
      }
      @Override
      public Adapter caseRepetitionOptions(RepetitionOptions object)
      {
        return createRepetitionOptionsAdapter();
      }
      @Override
      public Adapter caseJitter(Jitter object)
      {
        return createJitterAdapter();
      }
      @Override
      public Adapter caseOffset(Offset object)
      {
        return createOffsetAdapter();
      }
      @Override
      public Adapter caseReaction(Reaction object)
      {
        return createReactionAdapter();
      }
      @Override
      public Adapter caseAge(Age object)
      {
        return createAgeAdapter();
      }
      @Override
      public Adapter caseCausalReaction(CausalReaction object)
      {
        return createCausalReactionAdapter();
      }
      @Override
      public Adapter caseCausalAge(CausalAge object)
      {
        return createCausalAgeAdapter();
      }
      @Override
      public Adapter caseEventExpr(EventExpr object)
      {
        return createEventExprAdapter();
      }
      @Override
      public Adapter caseEventList(EventList object)
      {
        return createEventListAdapter();
      }
      @Override
      public Adapter caseEventSpec(EventSpec object)
      {
        return createEventSpecAdapter();
      }
      @Override
      public Adapter casePort(Port object)
      {
        return createPortAdapter();
      }
      @Override
      public Adapter caseInterval(Interval object)
      {
        return createIntervalAdapter();
      }
      @Override
      public Adapter caseTimeExpr(TimeExpr object)
      {
        return createTimeExprAdapter();
      }
      @Override
      public Adapter caseValue(Value object)
      {
        return createValueAdapter();
      }
      @Override
      public Adapter caseCausalFuncDecl(CausalFuncDecl object)
      {
        return createCausalFuncDeclAdapter();
      }
      @Override
      public Adapter caseClockDefinition(ClockDefinition object)
      {
        return createClockDefinitionAdapter();
      }
      @Override
      public Adapter defaultCase(EObject object)
      {
        return createEObjectAdapter();
      }
    };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(Notifier target)
  {
    return modelSwitch.doSwitch((EObject)target);
  }


  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.contractSpec.Model <em>Model</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fordiac.ide.contractSpec.Model
   * @generated
   */
  public Adapter createModelAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.contractSpec.TimeSpec <em>Time Spec</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fordiac.ide.contractSpec.TimeSpec
   * @generated
   */
  public Adapter createTimeSpecAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.contractSpec.SingleEvent <em>Single Event</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fordiac.ide.contractSpec.SingleEvent
   * @generated
   */
  public Adapter createSingleEventAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.contractSpec.Repetition <em>Repetition</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fordiac.ide.contractSpec.Repetition
   * @generated
   */
  public Adapter createRepetitionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.contractSpec.RepetitionOptions <em>Repetition Options</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fordiac.ide.contractSpec.RepetitionOptions
   * @generated
   */
  public Adapter createRepetitionOptionsAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.contractSpec.Jitter <em>Jitter</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fordiac.ide.contractSpec.Jitter
   * @generated
   */
  public Adapter createJitterAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.contractSpec.Offset <em>Offset</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fordiac.ide.contractSpec.Offset
   * @generated
   */
  public Adapter createOffsetAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.contractSpec.Reaction <em>Reaction</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fordiac.ide.contractSpec.Reaction
   * @generated
   */
  public Adapter createReactionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.contractSpec.Age <em>Age</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fordiac.ide.contractSpec.Age
   * @generated
   */
  public Adapter createAgeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.contractSpec.CausalReaction <em>Causal Reaction</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fordiac.ide.contractSpec.CausalReaction
   * @generated
   */
  public Adapter createCausalReactionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.contractSpec.CausalAge <em>Causal Age</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fordiac.ide.contractSpec.CausalAge
   * @generated
   */
  public Adapter createCausalAgeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.contractSpec.EventExpr <em>Event Expr</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fordiac.ide.contractSpec.EventExpr
   * @generated
   */
  public Adapter createEventExprAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.contractSpec.EventList <em>Event List</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fordiac.ide.contractSpec.EventList
   * @generated
   */
  public Adapter createEventListAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.contractSpec.EventSpec <em>Event Spec</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fordiac.ide.contractSpec.EventSpec
   * @generated
   */
  public Adapter createEventSpecAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.contractSpec.Port <em>Port</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fordiac.ide.contractSpec.Port
   * @generated
   */
  public Adapter createPortAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.contractSpec.Interval <em>Interval</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fordiac.ide.contractSpec.Interval
   * @generated
   */
  public Adapter createIntervalAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.contractSpec.TimeExpr <em>Time Expr</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fordiac.ide.contractSpec.TimeExpr
   * @generated
   */
  public Adapter createTimeExprAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.contractSpec.Value <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fordiac.ide.contractSpec.Value
   * @generated
   */
  public Adapter createValueAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl <em>Causal Func Decl</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl
   * @generated
   */
  public Adapter createCausalFuncDeclAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.contractSpec.ClockDefinition <em>Clock Definition</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.eclipse.fordiac.ide.contractSpec.ClockDefinition
   * @generated
   */
  public Adapter createClockDefinitionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter()
  {
    return null;
  }

} //ContractSpecAdapterFactory
