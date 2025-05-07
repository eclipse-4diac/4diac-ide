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
package org.eclipse.fordiac.ide.contractSpec.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.fordiac.ide.contractSpec.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ContractSpecFactoryImpl extends EFactoryImpl implements ContractSpecFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static ContractSpecFactory init()
  {
    try
    {
      ContractSpecFactory theContractSpecFactory = (ContractSpecFactory)EPackage.Registry.INSTANCE.getEFactory(ContractSpecPackage.eNS_URI);
      if (theContractSpecFactory != null)
      {
        return theContractSpecFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new ContractSpecFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ContractSpecFactoryImpl()
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
      case ContractSpecPackage.MODEL: return createModel();
      case ContractSpecPackage.TIME_SPEC: return createTimeSpec();
      case ContractSpecPackage.SINGLE_EVENT: return createSingleEvent();
      case ContractSpecPackage.REPETITION: return createRepetition();
      case ContractSpecPackage.REPETITION_OPTIONS: return createRepetitionOptions();
      case ContractSpecPackage.JITTER: return createJitter();
      case ContractSpecPackage.OFFSET: return createOffset();
      case ContractSpecPackage.REACTION: return createReaction();
      case ContractSpecPackage.AGE: return createAge();
      case ContractSpecPackage.CAUSAL_REACTION: return createCausalReaction();
      case ContractSpecPackage.CAUSAL_AGE: return createCausalAge();
      case ContractSpecPackage.EVENT_EXPR: return createEventExpr();
      case ContractSpecPackage.EVENT_LIST: return createEventList();
      case ContractSpecPackage.EVENT_SPEC: return createEventSpec();
      case ContractSpecPackage.PORT: return createPort();
      case ContractSpecPackage.INTERVAL: return createInterval();
      case ContractSpecPackage.TIME_EXPR: return createTimeExpr();
      case ContractSpecPackage.VALUE: return createValue();
      case ContractSpecPackage.CAUSAL_FUNC_DECL: return createCausalFuncDecl();
      case ContractSpecPackage.CLOCK_DEFINITION: return createClockDefinition();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Model createModel()
  {
    ModelImpl model = new ModelImpl();
    return model;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public TimeSpec createTimeSpec()
  {
    TimeSpecImpl timeSpec = new TimeSpecImpl();
    return timeSpec;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public SingleEvent createSingleEvent()
  {
    SingleEventImpl singleEvent = new SingleEventImpl();
    return singleEvent;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Repetition createRepetition()
  {
    RepetitionImpl repetition = new RepetitionImpl();
    return repetition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public RepetitionOptions createRepetitionOptions()
  {
    RepetitionOptionsImpl repetitionOptions = new RepetitionOptionsImpl();
    return repetitionOptions;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Jitter createJitter()
  {
    JitterImpl jitter = new JitterImpl();
    return jitter;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Offset createOffset()
  {
    OffsetImpl offset = new OffsetImpl();
    return offset;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Reaction createReaction()
  {
    ReactionImpl reaction = new ReactionImpl();
    return reaction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Age createAge()
  {
    AgeImpl age = new AgeImpl();
    return age;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public CausalReaction createCausalReaction()
  {
    CausalReactionImpl causalReaction = new CausalReactionImpl();
    return causalReaction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public CausalAge createCausalAge()
  {
    CausalAgeImpl causalAge = new CausalAgeImpl();
    return causalAge;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EventExpr createEventExpr()
  {
    EventExprImpl eventExpr = new EventExprImpl();
    return eventExpr;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EventList createEventList()
  {
    EventListImpl eventList = new EventListImpl();
    return eventList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EventSpec createEventSpec()
  {
    EventSpecImpl eventSpec = new EventSpecImpl();
    return eventSpec;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Port createPort()
  {
    PortImpl port = new PortImpl();
    return port;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Interval createInterval()
  {
    IntervalImpl interval = new IntervalImpl();
    return interval;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public TimeExpr createTimeExpr()
  {
    TimeExprImpl timeExpr = new TimeExprImpl();
    return timeExpr;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Value createValue()
  {
    ValueImpl value = new ValueImpl();
    return value;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public CausalFuncDecl createCausalFuncDecl()
  {
    CausalFuncDeclImpl causalFuncDecl = new CausalFuncDeclImpl();
    return causalFuncDecl;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ClockDefinition createClockDefinition()
  {
    ClockDefinitionImpl clockDefinition = new ClockDefinitionImpl();
    return clockDefinition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ContractSpecPackage getContractSpecPackage()
  {
    return (ContractSpecPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static ContractSpecPackage getPackage()
  {
    return ContractSpecPackage.eINSTANCE;
  }

} //ContractSpecFactoryImpl
