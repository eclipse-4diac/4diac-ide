/*******************************************************************************
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.contractSpec;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecFactory
 * @model kind="package"
 * @generated
 */
public interface ContractSpecPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "contractSpec";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.eclipse.org/fordiac/ide/ContractSpec";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "contractSpec";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  ContractSpecPackage eINSTANCE = org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl.init();

  /**
   * The meta object id for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.ModelImpl <em>Model</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.fordiac.ide.contractSpec.impl.ModelImpl
   * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getModel()
   * @generated
   */
  int MODEL = 0;

  /**
   * The feature id for the '<em><b>Time Spec</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL__TIME_SPEC = 0;

  /**
   * The number of structural features of the '<em>Model</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.TimeSpecImpl <em>Time Spec</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.fordiac.ide.contractSpec.impl.TimeSpecImpl
   * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getTimeSpec()
   * @generated
   */
  int TIME_SPEC = 1;

  /**
   * The number of structural features of the '<em>Time Spec</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TIME_SPEC_FEATURE_COUNT = 0;

  /**
   * The meta object id for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.SingleEventImpl <em>Single Event</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.fordiac.ide.contractSpec.impl.SingleEventImpl
   * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getSingleEvent()
   * @generated
   */
  int SINGLE_EVENT = 2;

  /**
   * The feature id for the '<em><b>Events</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SINGLE_EVENT__EVENTS = TIME_SPEC_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Interval</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SINGLE_EVENT__INTERVAL = TIME_SPEC_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Clock</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SINGLE_EVENT__CLOCK = TIME_SPEC_FEATURE_COUNT + 2;

  /**
   * The number of structural features of the '<em>Single Event</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int SINGLE_EVENT_FEATURE_COUNT = TIME_SPEC_FEATURE_COUNT + 3;

  /**
   * The meta object id for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.RepetitionImpl <em>Repetition</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.fordiac.ide.contractSpec.impl.RepetitionImpl
   * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getRepetition()
   * @generated
   */
  int REPETITION = 3;

  /**
   * The feature id for the '<em><b>Events</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REPETITION__EVENTS = TIME_SPEC_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Interval</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REPETITION__INTERVAL = TIME_SPEC_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Repetition Options</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REPETITION__REPETITION_OPTIONS = TIME_SPEC_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Clock</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REPETITION__CLOCK = TIME_SPEC_FEATURE_COUNT + 3;

  /**
   * The number of structural features of the '<em>Repetition</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REPETITION_FEATURE_COUNT = TIME_SPEC_FEATURE_COUNT + 4;

  /**
   * The meta object id for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.RepetitionOptionsImpl <em>Repetition Options</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.fordiac.ide.contractSpec.impl.RepetitionOptionsImpl
   * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getRepetitionOptions()
   * @generated
   */
  int REPETITION_OPTIONS = 4;

  /**
   * The feature id for the '<em><b>Jitter</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REPETITION_OPTIONS__JITTER = 0;

  /**
   * The feature id for the '<em><b>Offset</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REPETITION_OPTIONS__OFFSET = 1;

  /**
   * The number of structural features of the '<em>Repetition Options</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REPETITION_OPTIONS_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.JitterImpl <em>Jitter</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.fordiac.ide.contractSpec.impl.JitterImpl
   * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getJitter()
   * @generated
   */
  int JITTER = 5;

  /**
   * The feature id for the '<em><b>Time</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JITTER__TIME = 0;

  /**
   * The number of structural features of the '<em>Jitter</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int JITTER_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.OffsetImpl <em>Offset</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.fordiac.ide.contractSpec.impl.OffsetImpl
   * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getOffset()
   * @generated
   */
  int OFFSET = 6;

  /**
   * The feature id for the '<em><b>Interval</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OFFSET__INTERVAL = 0;

  /**
   * The number of structural features of the '<em>Offset</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int OFFSET_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.ReactionImpl <em>Reaction</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.fordiac.ide.contractSpec.impl.ReactionImpl
   * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getReaction()
   * @generated
   */
  int REACTION = 7;

  /**
   * The feature id for the '<em><b>Input</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REACTION__INPUT = TIME_SPEC_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Output</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REACTION__OUTPUT = TIME_SPEC_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Interval</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REACTION__INTERVAL = TIME_SPEC_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Once</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REACTION__ONCE = TIME_SPEC_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>N</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REACTION__N = TIME_SPEC_FEATURE_COUNT + 4;

  /**
   * The feature id for the '<em><b>Out Of</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REACTION__OUT_OF = TIME_SPEC_FEATURE_COUNT + 5;

  /**
   * The feature id for the '<em><b>Clock</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REACTION__CLOCK = TIME_SPEC_FEATURE_COUNT + 6;

  /**
   * The number of structural features of the '<em>Reaction</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int REACTION_FEATURE_COUNT = TIME_SPEC_FEATURE_COUNT + 7;

  /**
   * The meta object id for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.AgeImpl <em>Age</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.fordiac.ide.contractSpec.impl.AgeImpl
   * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getAge()
   * @generated
   */
  int AGE = 8;

  /**
   * The feature id for the '<em><b>Output</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AGE__OUTPUT = TIME_SPEC_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Input</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AGE__INPUT = TIME_SPEC_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Interval</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AGE__INTERVAL = TIME_SPEC_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Once</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AGE__ONCE = TIME_SPEC_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>N</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AGE__N = TIME_SPEC_FEATURE_COUNT + 4;

  /**
   * The feature id for the '<em><b>Out Of</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AGE__OUT_OF = TIME_SPEC_FEATURE_COUNT + 5;

  /**
   * The feature id for the '<em><b>Clock</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AGE__CLOCK = TIME_SPEC_FEATURE_COUNT + 6;

  /**
   * The number of structural features of the '<em>Age</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int AGE_FEATURE_COUNT = TIME_SPEC_FEATURE_COUNT + 7;

  /**
   * The meta object id for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.CausalReactionImpl <em>Causal Reaction</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.fordiac.ide.contractSpec.impl.CausalReactionImpl
   * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getCausalReaction()
   * @generated
   */
  int CAUSAL_REACTION = 9;

  /**
   * The feature id for the '<em><b>Input</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CAUSAL_REACTION__INPUT = TIME_SPEC_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Output</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CAUSAL_REACTION__OUTPUT = TIME_SPEC_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Interval</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CAUSAL_REACTION__INTERVAL = TIME_SPEC_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Clock</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CAUSAL_REACTION__CLOCK = TIME_SPEC_FEATURE_COUNT + 3;

  /**
   * The number of structural features of the '<em>Causal Reaction</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CAUSAL_REACTION_FEATURE_COUNT = TIME_SPEC_FEATURE_COUNT + 4;

  /**
   * The meta object id for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.CausalAgeImpl <em>Causal Age</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.fordiac.ide.contractSpec.impl.CausalAgeImpl
   * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getCausalAge()
   * @generated
   */
  int CAUSAL_AGE = 10;

  /**
   * The feature id for the '<em><b>Output</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CAUSAL_AGE__OUTPUT = TIME_SPEC_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Input</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CAUSAL_AGE__INPUT = TIME_SPEC_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Interval</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CAUSAL_AGE__INTERVAL = TIME_SPEC_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Clock</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CAUSAL_AGE__CLOCK = TIME_SPEC_FEATURE_COUNT + 3;

  /**
   * The number of structural features of the '<em>Causal Age</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CAUSAL_AGE_FEATURE_COUNT = TIME_SPEC_FEATURE_COUNT + 4;

  /**
   * The meta object id for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.EventExprImpl <em>Event Expr</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.fordiac.ide.contractSpec.impl.EventExprImpl
   * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getEventExpr()
   * @generated
   */
  int EVENT_EXPR = 11;

  /**
   * The feature id for the '<em><b>Event</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_EXPR__EVENT = 0;

  /**
   * The feature id for the '<em><b>Sequence</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_EXPR__SEQUENCE = 1;

  /**
   * The feature id for the '<em><b>Events</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_EXPR__EVENTS = 2;

  /**
   * The number of structural features of the '<em>Event Expr</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_EXPR_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.EventListImpl <em>Event List</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.fordiac.ide.contractSpec.impl.EventListImpl
   * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getEventList()
   * @generated
   */
  int EVENT_LIST = 12;

  /**
   * The feature id for the '<em><b>Events</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_LIST__EVENTS = 0;

  /**
   * The number of structural features of the '<em>Event List</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_LIST_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.EventSpecImpl <em>Event Spec</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.fordiac.ide.contractSpec.impl.EventSpecImpl
   * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getEventSpec()
   * @generated
   */
  int EVENT_SPEC = 13;

  /**
   * The feature id for the '<em><b>Port</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_SPEC__PORT = 0;

  /**
   * The feature id for the '<em><b>Event Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_SPEC__EVENT_VALUE = 1;

  /**
   * The number of structural features of the '<em>Event Spec</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_SPEC_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.PortImpl <em>Port</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.fordiac.ide.contractSpec.impl.PortImpl
   * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getPort()
   * @generated
   */
  int PORT = 14;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PORT__NAME = 0;

  /**
   * The feature id for the '<em><b>Is Input</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PORT__IS_INPUT = 1;

  /**
   * The number of structural features of the '<em>Port</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PORT_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.IntervalImpl <em>Interval</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.fordiac.ide.contractSpec.impl.IntervalImpl
   * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getInterval()
   * @generated
   */
  int INTERVAL = 15;

  /**
   * The feature id for the '<em><b>Time</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTERVAL__TIME = 0;

  /**
   * The feature id for the '<em><b>LBound</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTERVAL__LBOUND = 1;

  /**
   * The feature id for the '<em><b>Lb Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTERVAL__LB_VALUE = 2;

  /**
   * The feature id for the '<em><b>Ub Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTERVAL__UB_VALUE = 3;

  /**
   * The feature id for the '<em><b>UBound</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTERVAL__UBOUND = 4;

  /**
   * The feature id for the '<em><b>Unit</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTERVAL__UNIT = 5;

  /**
   * The number of structural features of the '<em>Interval</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int INTERVAL_FEATURE_COUNT = 6;

  /**
   * The meta object id for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.TimeExprImpl <em>Time Expr</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.fordiac.ide.contractSpec.impl.TimeExprImpl
   * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getTimeExpr()
   * @generated
   */
  int TIME_EXPR = 16;

  /**
   * The feature id for the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TIME_EXPR__VALUE = 0;

  /**
   * The feature id for the '<em><b>Unit</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TIME_EXPR__UNIT = 1;

  /**
   * The number of structural features of the '<em>Time Expr</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TIME_EXPR_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.CausalFuncDeclImpl <em>Causal Func Decl</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.fordiac.ide.contractSpec.impl.CausalFuncDeclImpl
   * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getCausalFuncDecl()
   * @generated
   */
  int CAUSAL_FUNC_DECL = 17;

  /**
   * The feature id for the '<em><b>Func Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CAUSAL_FUNC_DECL__FUNC_NAME = TIME_SPEC_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Port1</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CAUSAL_FUNC_DECL__PORT1 = TIME_SPEC_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Port2</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CAUSAL_FUNC_DECL__PORT2 = TIME_SPEC_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Relation</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CAUSAL_FUNC_DECL__RELATION = TIME_SPEC_FEATURE_COUNT + 3;

  /**
   * The number of structural features of the '<em>Causal Func Decl</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CAUSAL_FUNC_DECL_FEATURE_COUNT = TIME_SPEC_FEATURE_COUNT + 4;

  /**
   * The meta object id for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.ClockDefinitionImpl <em>Clock Definition</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.fordiac.ide.contractSpec.impl.ClockDefinitionImpl
   * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getClockDefinition()
   * @generated
   */
  int CLOCK_DEFINITION = 18;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLOCK_DEFINITION__NAME = TIME_SPEC_FEATURE_COUNT + 0;

  /**
   * The feature id for the '<em><b>Resolution</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLOCK_DEFINITION__RESOLUTION = TIME_SPEC_FEATURE_COUNT + 1;

  /**
   * The feature id for the '<em><b>Skew</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLOCK_DEFINITION__SKEW = TIME_SPEC_FEATURE_COUNT + 2;

  /**
   * The feature id for the '<em><b>Drift</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLOCK_DEFINITION__DRIFT = TIME_SPEC_FEATURE_COUNT + 3;

  /**
   * The feature id for the '<em><b>Maxdiff</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLOCK_DEFINITION__MAXDIFF = TIME_SPEC_FEATURE_COUNT + 4;

  /**
   * The number of structural features of the '<em>Clock Definition</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int CLOCK_DEFINITION_FEATURE_COUNT = TIME_SPEC_FEATURE_COUNT + 5;

  /**
   * The meta object id for the '{@link org.eclipse.fordiac.ide.contractSpec.Unit <em>Unit</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.fordiac.ide.contractSpec.Unit
   * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getUnit()
   * @generated
   */
  int UNIT = 19;

  /**
   * The meta object id for the '{@link org.eclipse.fordiac.ide.contractSpec.CausalFuncName <em>Causal Func Name</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.fordiac.ide.contractSpec.CausalFuncName
   * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getCausalFuncName()
   * @generated
   */
  int CAUSAL_FUNC_NAME = 20;

  /**
   * The meta object id for the '{@link org.eclipse.fordiac.ide.contractSpec.CausalRelation <em>Causal Relation</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.fordiac.ide.contractSpec.CausalRelation
   * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getCausalRelation()
   * @generated
   */
  int CAUSAL_RELATION = 21;


  /**
   * Returns the meta object for class '{@link org.eclipse.fordiac.ide.contractSpec.Model <em>Model</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Model</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Model
   * @generated
   */
  EClass getModel();

  /**
   * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.contractSpec.Model#getTimeSpec <em>Time Spec</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Time Spec</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Model#getTimeSpec()
   * @see #getModel()
   * @generated
   */
  EReference getModel_TimeSpec();

  /**
   * Returns the meta object for class '{@link org.eclipse.fordiac.ide.contractSpec.TimeSpec <em>Time Spec</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Time Spec</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.TimeSpec
   * @generated
   */
  EClass getTimeSpec();

  /**
   * Returns the meta object for class '{@link org.eclipse.fordiac.ide.contractSpec.SingleEvent <em>Single Event</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Single Event</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.SingleEvent
   * @generated
   */
  EClass getSingleEvent();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.contractSpec.SingleEvent#getEvents <em>Events</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Events</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.SingleEvent#getEvents()
   * @see #getSingleEvent()
   * @generated
   */
  EReference getSingleEvent_Events();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.contractSpec.SingleEvent#getInterval <em>Interval</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Interval</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.SingleEvent#getInterval()
   * @see #getSingleEvent()
   * @generated
   */
  EReference getSingleEvent_Interval();

  /**
   * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.contractSpec.SingleEvent#getClock <em>Clock</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Clock</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.SingleEvent#getClock()
   * @see #getSingleEvent()
   * @generated
   */
  EReference getSingleEvent_Clock();

  /**
   * Returns the meta object for class '{@link org.eclipse.fordiac.ide.contractSpec.Repetition <em>Repetition</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Repetition</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Repetition
   * @generated
   */
  EClass getRepetition();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.contractSpec.Repetition#getEvents <em>Events</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Events</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Repetition#getEvents()
   * @see #getRepetition()
   * @generated
   */
  EReference getRepetition_Events();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.contractSpec.Repetition#getInterval <em>Interval</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Interval</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Repetition#getInterval()
   * @see #getRepetition()
   * @generated
   */
  EReference getRepetition_Interval();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.contractSpec.Repetition#getRepetitionOptions <em>Repetition Options</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Repetition Options</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Repetition#getRepetitionOptions()
   * @see #getRepetition()
   * @generated
   */
  EReference getRepetition_RepetitionOptions();

  /**
   * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.contractSpec.Repetition#getClock <em>Clock</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Clock</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Repetition#getClock()
   * @see #getRepetition()
   * @generated
   */
  EReference getRepetition_Clock();

  /**
   * Returns the meta object for class '{@link org.eclipse.fordiac.ide.contractSpec.RepetitionOptions <em>Repetition Options</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Repetition Options</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.RepetitionOptions
   * @generated
   */
  EClass getRepetitionOptions();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.contractSpec.RepetitionOptions#getJitter <em>Jitter</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Jitter</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.RepetitionOptions#getJitter()
   * @see #getRepetitionOptions()
   * @generated
   */
  EReference getRepetitionOptions_Jitter();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.contractSpec.RepetitionOptions#getOffset <em>Offset</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Offset</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.RepetitionOptions#getOffset()
   * @see #getRepetitionOptions()
   * @generated
   */
  EReference getRepetitionOptions_Offset();

  /**
   * Returns the meta object for class '{@link org.eclipse.fordiac.ide.contractSpec.Jitter <em>Jitter</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Jitter</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Jitter
   * @generated
   */
  EClass getJitter();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.contractSpec.Jitter#getTime <em>Time</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Time</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Jitter#getTime()
   * @see #getJitter()
   * @generated
   */
  EReference getJitter_Time();

  /**
   * Returns the meta object for class '{@link org.eclipse.fordiac.ide.contractSpec.Offset <em>Offset</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Offset</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Offset
   * @generated
   */
  EClass getOffset();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.contractSpec.Offset#getInterval <em>Interval</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Interval</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Offset#getInterval()
   * @see #getOffset()
   * @generated
   */
  EReference getOffset_Interval();

  /**
   * Returns the meta object for class '{@link org.eclipse.fordiac.ide.contractSpec.Reaction <em>Reaction</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Reaction</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Reaction
   * @generated
   */
  EClass getReaction();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.contractSpec.Reaction#getInput <em>Input</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Input</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Reaction#getInput()
   * @see #getReaction()
   * @generated
   */
  EReference getReaction_Input();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.contractSpec.Reaction#getOutput <em>Output</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Output</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Reaction#getOutput()
   * @see #getReaction()
   * @generated
   */
  EReference getReaction_Output();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.contractSpec.Reaction#getInterval <em>Interval</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Interval</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Reaction#getInterval()
   * @see #getReaction()
   * @generated
   */
  EReference getReaction_Interval();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.contractSpec.Reaction#isOnce <em>Once</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Once</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Reaction#isOnce()
   * @see #getReaction()
   * @generated
   */
  EAttribute getReaction_Once();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.contractSpec.Reaction#getN <em>N</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>N</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Reaction#getN()
   * @see #getReaction()
   * @generated
   */
  EAttribute getReaction_N();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.contractSpec.Reaction#getOutOf <em>Out Of</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Out Of</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Reaction#getOutOf()
   * @see #getReaction()
   * @generated
   */
  EAttribute getReaction_OutOf();

  /**
   * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.contractSpec.Reaction#getClock <em>Clock</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Clock</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Reaction#getClock()
   * @see #getReaction()
   * @generated
   */
  EReference getReaction_Clock();

  /**
   * Returns the meta object for class '{@link org.eclipse.fordiac.ide.contractSpec.Age <em>Age</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Age</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Age
   * @generated
   */
  EClass getAge();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.contractSpec.Age#getOutput <em>Output</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Output</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Age#getOutput()
   * @see #getAge()
   * @generated
   */
  EReference getAge_Output();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.contractSpec.Age#getInput <em>Input</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Input</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Age#getInput()
   * @see #getAge()
   * @generated
   */
  EReference getAge_Input();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.contractSpec.Age#getInterval <em>Interval</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Interval</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Age#getInterval()
   * @see #getAge()
   * @generated
   */
  EReference getAge_Interval();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.contractSpec.Age#isOnce <em>Once</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Once</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Age#isOnce()
   * @see #getAge()
   * @generated
   */
  EAttribute getAge_Once();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.contractSpec.Age#getN <em>N</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>N</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Age#getN()
   * @see #getAge()
   * @generated
   */
  EAttribute getAge_N();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.contractSpec.Age#getOutOf <em>Out Of</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Out Of</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Age#getOutOf()
   * @see #getAge()
   * @generated
   */
  EAttribute getAge_OutOf();

  /**
   * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.contractSpec.Age#getClock <em>Clock</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Clock</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Age#getClock()
   * @see #getAge()
   * @generated
   */
  EReference getAge_Clock();

  /**
   * Returns the meta object for class '{@link org.eclipse.fordiac.ide.contractSpec.CausalReaction <em>Causal Reaction</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Causal Reaction</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.CausalReaction
   * @generated
   */
  EClass getCausalReaction();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.contractSpec.CausalReaction#getInput <em>Input</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Input</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.CausalReaction#getInput()
   * @see #getCausalReaction()
   * @generated
   */
  EReference getCausalReaction_Input();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.contractSpec.CausalReaction#getOutput <em>Output</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Output</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.CausalReaction#getOutput()
   * @see #getCausalReaction()
   * @generated
   */
  EReference getCausalReaction_Output();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.contractSpec.CausalReaction#getInterval <em>Interval</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Interval</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.CausalReaction#getInterval()
   * @see #getCausalReaction()
   * @generated
   */
  EReference getCausalReaction_Interval();

  /**
   * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.contractSpec.CausalReaction#getClock <em>Clock</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Clock</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.CausalReaction#getClock()
   * @see #getCausalReaction()
   * @generated
   */
  EReference getCausalReaction_Clock();

  /**
   * Returns the meta object for class '{@link org.eclipse.fordiac.ide.contractSpec.CausalAge <em>Causal Age</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Causal Age</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.CausalAge
   * @generated
   */
  EClass getCausalAge();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.contractSpec.CausalAge#getOutput <em>Output</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Output</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.CausalAge#getOutput()
   * @see #getCausalAge()
   * @generated
   */
  EReference getCausalAge_Output();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.contractSpec.CausalAge#getInput <em>Input</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Input</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.CausalAge#getInput()
   * @see #getCausalAge()
   * @generated
   */
  EReference getCausalAge_Input();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.contractSpec.CausalAge#getInterval <em>Interval</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Interval</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.CausalAge#getInterval()
   * @see #getCausalAge()
   * @generated
   */
  EReference getCausalAge_Interval();

  /**
   * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.contractSpec.CausalAge#getClock <em>Clock</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Clock</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.CausalAge#getClock()
   * @see #getCausalAge()
   * @generated
   */
  EReference getCausalAge_Clock();

  /**
   * Returns the meta object for class '{@link org.eclipse.fordiac.ide.contractSpec.EventExpr <em>Event Expr</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Event Expr</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.EventExpr
   * @generated
   */
  EClass getEventExpr();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.contractSpec.EventExpr#getEvent <em>Event</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Event</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.EventExpr#getEvent()
   * @see #getEventExpr()
   * @generated
   */
  EReference getEventExpr_Event();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.contractSpec.EventExpr#isSequence <em>Sequence</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Sequence</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.EventExpr#isSequence()
   * @see #getEventExpr()
   * @generated
   */
  EAttribute getEventExpr_Sequence();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.contractSpec.EventExpr#getEvents <em>Events</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Events</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.EventExpr#getEvents()
   * @see #getEventExpr()
   * @generated
   */
  EReference getEventExpr_Events();

  /**
   * Returns the meta object for class '{@link org.eclipse.fordiac.ide.contractSpec.EventList <em>Event List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Event List</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.EventList
   * @generated
   */
  EClass getEventList();

  /**
   * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.contractSpec.EventList#getEvents <em>Events</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Events</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.EventList#getEvents()
   * @see #getEventList()
   * @generated
   */
  EReference getEventList_Events();

  /**
   * Returns the meta object for class '{@link org.eclipse.fordiac.ide.contractSpec.EventSpec <em>Event Spec</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Event Spec</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.EventSpec
   * @generated
   */
  EClass getEventSpec();

  /**
   * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.contractSpec.EventSpec#getPort <em>Port</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Port</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.EventSpec#getPort()
   * @see #getEventSpec()
   * @generated
   */
  EReference getEventSpec_Port();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.contractSpec.EventSpec#getEventValue <em>Event Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Event Value</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.EventSpec#getEventValue()
   * @see #getEventSpec()
   * @generated
   */
  EAttribute getEventSpec_EventValue();

  /**
   * Returns the meta object for class '{@link org.eclipse.fordiac.ide.contractSpec.Port <em>Port</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Port</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Port
   * @generated
   */
  EClass getPort();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.contractSpec.Port#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Port#getName()
   * @see #getPort()
   * @generated
   */
  EAttribute getPort_Name();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.contractSpec.Port#getIsInput <em>Is Input</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Is Input</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Port#getIsInput()
   * @see #getPort()
   * @generated
   */
  EAttribute getPort_IsInput();

  /**
   * Returns the meta object for class '{@link org.eclipse.fordiac.ide.contractSpec.Interval <em>Interval</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Interval</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Interval
   * @generated
   */
  EClass getInterval();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.contractSpec.Interval#getTime <em>Time</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Time</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Interval#getTime()
   * @see #getInterval()
   * @generated
   */
  EReference getInterval_Time();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.contractSpec.Interval#getLBound <em>LBound</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>LBound</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Interval#getLBound()
   * @see #getInterval()
   * @generated
   */
  EAttribute getInterval_LBound();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.contractSpec.Interval#getLbValue <em>Lb Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Lb Value</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Interval#getLbValue()
   * @see #getInterval()
   * @generated
   */
  EAttribute getInterval_LbValue();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.contractSpec.Interval#getUbValue <em>Ub Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Ub Value</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Interval#getUbValue()
   * @see #getInterval()
   * @generated
   */
  EAttribute getInterval_UbValue();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.contractSpec.Interval#getUBound <em>UBound</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>UBound</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Interval#getUBound()
   * @see #getInterval()
   * @generated
   */
  EAttribute getInterval_UBound();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.contractSpec.Interval#getUnit <em>Unit</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Unit</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Interval#getUnit()
   * @see #getInterval()
   * @generated
   */
  EAttribute getInterval_Unit();

  /**
   * Returns the meta object for class '{@link org.eclipse.fordiac.ide.contractSpec.TimeExpr <em>Time Expr</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Time Expr</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.TimeExpr
   * @generated
   */
  EClass getTimeExpr();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.contractSpec.TimeExpr#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.TimeExpr#getValue()
   * @see #getTimeExpr()
   * @generated
   */
  EAttribute getTimeExpr_Value();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.contractSpec.TimeExpr#getUnit <em>Unit</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Unit</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.TimeExpr#getUnit()
   * @see #getTimeExpr()
   * @generated
   */
  EAttribute getTimeExpr_Unit();

  /**
   * Returns the meta object for class '{@link org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl <em>Causal Func Decl</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Causal Func Decl</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl
   * @generated
   */
  EClass getCausalFuncDecl();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl#getFuncName <em>Func Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Func Name</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl#getFuncName()
   * @see #getCausalFuncDecl()
   * @generated
   */
  EAttribute getCausalFuncDecl_FuncName();

  /**
   * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl#getPort1 <em>Port1</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Port1</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl#getPort1()
   * @see #getCausalFuncDecl()
   * @generated
   */
  EReference getCausalFuncDecl_Port1();

  /**
   * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl#getPort2 <em>Port2</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Port2</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl#getPort2()
   * @see #getCausalFuncDecl()
   * @generated
   */
  EReference getCausalFuncDecl_Port2();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl#getRelation <em>Relation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Relation</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl#getRelation()
   * @see #getCausalFuncDecl()
   * @generated
   */
  EAttribute getCausalFuncDecl_Relation();

  /**
   * Returns the meta object for class '{@link org.eclipse.fordiac.ide.contractSpec.ClockDefinition <em>Clock Definition</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Clock Definition</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.ClockDefinition
   * @generated
   */
  EClass getClockDefinition();

  /**
   * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.contractSpec.ClockDefinition#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.ClockDefinition#getName()
   * @see #getClockDefinition()
   * @generated
   */
  EAttribute getClockDefinition_Name();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.contractSpec.ClockDefinition#getResolution <em>Resolution</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Resolution</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.ClockDefinition#getResolution()
   * @see #getClockDefinition()
   * @generated
   */
  EReference getClockDefinition_Resolution();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.contractSpec.ClockDefinition#getSkew <em>Skew</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Skew</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.ClockDefinition#getSkew()
   * @see #getClockDefinition()
   * @generated
   */
  EReference getClockDefinition_Skew();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.contractSpec.ClockDefinition#getDrift <em>Drift</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Drift</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.ClockDefinition#getDrift()
   * @see #getClockDefinition()
   * @generated
   */
  EReference getClockDefinition_Drift();

  /**
   * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.contractSpec.ClockDefinition#getMaxdiff <em>Maxdiff</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Maxdiff</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.ClockDefinition#getMaxdiff()
   * @see #getClockDefinition()
   * @generated
   */
  EReference getClockDefinition_Maxdiff();

  /**
   * Returns the meta object for enum '{@link org.eclipse.fordiac.ide.contractSpec.Unit <em>Unit</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Unit</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Unit
   * @generated
   */
  EEnum getUnit();

  /**
   * Returns the meta object for enum '{@link org.eclipse.fordiac.ide.contractSpec.CausalFuncName <em>Causal Func Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Causal Func Name</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.CausalFuncName
   * @generated
   */
  EEnum getCausalFuncName();

  /**
   * Returns the meta object for enum '{@link org.eclipse.fordiac.ide.contractSpec.CausalRelation <em>Causal Relation</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>Causal Relation</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.CausalRelation
   * @generated
   */
  EEnum getCausalRelation();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  ContractSpecFactory getContractSpecFactory();

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
     * The meta object literal for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.ModelImpl <em>Model</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.fordiac.ide.contractSpec.impl.ModelImpl
     * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getModel()
     * @generated
     */
    EClass MODEL = eINSTANCE.getModel();

    /**
     * The meta object literal for the '<em><b>Time Spec</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MODEL__TIME_SPEC = eINSTANCE.getModel_TimeSpec();

    /**
     * The meta object literal for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.TimeSpecImpl <em>Time Spec</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.fordiac.ide.contractSpec.impl.TimeSpecImpl
     * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getTimeSpec()
     * @generated
     */
    EClass TIME_SPEC = eINSTANCE.getTimeSpec();

    /**
     * The meta object literal for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.SingleEventImpl <em>Single Event</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.fordiac.ide.contractSpec.impl.SingleEventImpl
     * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getSingleEvent()
     * @generated
     */
    EClass SINGLE_EVENT = eINSTANCE.getSingleEvent();

    /**
     * The meta object literal for the '<em><b>Events</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SINGLE_EVENT__EVENTS = eINSTANCE.getSingleEvent_Events();

    /**
     * The meta object literal for the '<em><b>Interval</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SINGLE_EVENT__INTERVAL = eINSTANCE.getSingleEvent_Interval();

    /**
     * The meta object literal for the '<em><b>Clock</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference SINGLE_EVENT__CLOCK = eINSTANCE.getSingleEvent_Clock();

    /**
     * The meta object literal for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.RepetitionImpl <em>Repetition</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.fordiac.ide.contractSpec.impl.RepetitionImpl
     * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getRepetition()
     * @generated
     */
    EClass REPETITION = eINSTANCE.getRepetition();

    /**
     * The meta object literal for the '<em><b>Events</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REPETITION__EVENTS = eINSTANCE.getRepetition_Events();

    /**
     * The meta object literal for the '<em><b>Interval</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REPETITION__INTERVAL = eINSTANCE.getRepetition_Interval();

    /**
     * The meta object literal for the '<em><b>Repetition Options</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REPETITION__REPETITION_OPTIONS = eINSTANCE.getRepetition_RepetitionOptions();

    /**
     * The meta object literal for the '<em><b>Clock</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REPETITION__CLOCK = eINSTANCE.getRepetition_Clock();

    /**
     * The meta object literal for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.RepetitionOptionsImpl <em>Repetition Options</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.fordiac.ide.contractSpec.impl.RepetitionOptionsImpl
     * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getRepetitionOptions()
     * @generated
     */
    EClass REPETITION_OPTIONS = eINSTANCE.getRepetitionOptions();

    /**
     * The meta object literal for the '<em><b>Jitter</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REPETITION_OPTIONS__JITTER = eINSTANCE.getRepetitionOptions_Jitter();

    /**
     * The meta object literal for the '<em><b>Offset</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REPETITION_OPTIONS__OFFSET = eINSTANCE.getRepetitionOptions_Offset();

    /**
     * The meta object literal for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.JitterImpl <em>Jitter</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.fordiac.ide.contractSpec.impl.JitterImpl
     * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getJitter()
     * @generated
     */
    EClass JITTER = eINSTANCE.getJitter();

    /**
     * The meta object literal for the '<em><b>Time</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference JITTER__TIME = eINSTANCE.getJitter_Time();

    /**
     * The meta object literal for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.OffsetImpl <em>Offset</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.fordiac.ide.contractSpec.impl.OffsetImpl
     * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getOffset()
     * @generated
     */
    EClass OFFSET = eINSTANCE.getOffset();

    /**
     * The meta object literal for the '<em><b>Interval</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference OFFSET__INTERVAL = eINSTANCE.getOffset_Interval();

    /**
     * The meta object literal for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.ReactionImpl <em>Reaction</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.fordiac.ide.contractSpec.impl.ReactionImpl
     * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getReaction()
     * @generated
     */
    EClass REACTION = eINSTANCE.getReaction();

    /**
     * The meta object literal for the '<em><b>Input</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REACTION__INPUT = eINSTANCE.getReaction_Input();

    /**
     * The meta object literal for the '<em><b>Output</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REACTION__OUTPUT = eINSTANCE.getReaction_Output();

    /**
     * The meta object literal for the '<em><b>Interval</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REACTION__INTERVAL = eINSTANCE.getReaction_Interval();

    /**
     * The meta object literal for the '<em><b>Once</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute REACTION__ONCE = eINSTANCE.getReaction_Once();

    /**
     * The meta object literal for the '<em><b>N</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute REACTION__N = eINSTANCE.getReaction_N();

    /**
     * The meta object literal for the '<em><b>Out Of</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute REACTION__OUT_OF = eINSTANCE.getReaction_OutOf();

    /**
     * The meta object literal for the '<em><b>Clock</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference REACTION__CLOCK = eINSTANCE.getReaction_Clock();

    /**
     * The meta object literal for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.AgeImpl <em>Age</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.fordiac.ide.contractSpec.impl.AgeImpl
     * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getAge()
     * @generated
     */
    EClass AGE = eINSTANCE.getAge();

    /**
     * The meta object literal for the '<em><b>Output</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AGE__OUTPUT = eINSTANCE.getAge_Output();

    /**
     * The meta object literal for the '<em><b>Input</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AGE__INPUT = eINSTANCE.getAge_Input();

    /**
     * The meta object literal for the '<em><b>Interval</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AGE__INTERVAL = eINSTANCE.getAge_Interval();

    /**
     * The meta object literal for the '<em><b>Once</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute AGE__ONCE = eINSTANCE.getAge_Once();

    /**
     * The meta object literal for the '<em><b>N</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute AGE__N = eINSTANCE.getAge_N();

    /**
     * The meta object literal for the '<em><b>Out Of</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute AGE__OUT_OF = eINSTANCE.getAge_OutOf();

    /**
     * The meta object literal for the '<em><b>Clock</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference AGE__CLOCK = eINSTANCE.getAge_Clock();

    /**
     * The meta object literal for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.CausalReactionImpl <em>Causal Reaction</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.fordiac.ide.contractSpec.impl.CausalReactionImpl
     * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getCausalReaction()
     * @generated
     */
    EClass CAUSAL_REACTION = eINSTANCE.getCausalReaction();

    /**
     * The meta object literal for the '<em><b>Input</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CAUSAL_REACTION__INPUT = eINSTANCE.getCausalReaction_Input();

    /**
     * The meta object literal for the '<em><b>Output</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CAUSAL_REACTION__OUTPUT = eINSTANCE.getCausalReaction_Output();

    /**
     * The meta object literal for the '<em><b>Interval</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CAUSAL_REACTION__INTERVAL = eINSTANCE.getCausalReaction_Interval();

    /**
     * The meta object literal for the '<em><b>Clock</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CAUSAL_REACTION__CLOCK = eINSTANCE.getCausalReaction_Clock();

    /**
     * The meta object literal for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.CausalAgeImpl <em>Causal Age</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.fordiac.ide.contractSpec.impl.CausalAgeImpl
     * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getCausalAge()
     * @generated
     */
    EClass CAUSAL_AGE = eINSTANCE.getCausalAge();

    /**
     * The meta object literal for the '<em><b>Output</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CAUSAL_AGE__OUTPUT = eINSTANCE.getCausalAge_Output();

    /**
     * The meta object literal for the '<em><b>Input</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CAUSAL_AGE__INPUT = eINSTANCE.getCausalAge_Input();

    /**
     * The meta object literal for the '<em><b>Interval</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CAUSAL_AGE__INTERVAL = eINSTANCE.getCausalAge_Interval();

    /**
     * The meta object literal for the '<em><b>Clock</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CAUSAL_AGE__CLOCK = eINSTANCE.getCausalAge_Clock();

    /**
     * The meta object literal for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.EventExprImpl <em>Event Expr</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.fordiac.ide.contractSpec.impl.EventExprImpl
     * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getEventExpr()
     * @generated
     */
    EClass EVENT_EXPR = eINSTANCE.getEventExpr();

    /**
     * The meta object literal for the '<em><b>Event</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EVENT_EXPR__EVENT = eINSTANCE.getEventExpr_Event();

    /**
     * The meta object literal for the '<em><b>Sequence</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EVENT_EXPR__SEQUENCE = eINSTANCE.getEventExpr_Sequence();

    /**
     * The meta object literal for the '<em><b>Events</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EVENT_EXPR__EVENTS = eINSTANCE.getEventExpr_Events();

    /**
     * The meta object literal for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.EventListImpl <em>Event List</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.fordiac.ide.contractSpec.impl.EventListImpl
     * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getEventList()
     * @generated
     */
    EClass EVENT_LIST = eINSTANCE.getEventList();

    /**
     * The meta object literal for the '<em><b>Events</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EVENT_LIST__EVENTS = eINSTANCE.getEventList_Events();

    /**
     * The meta object literal for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.EventSpecImpl <em>Event Spec</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.fordiac.ide.contractSpec.impl.EventSpecImpl
     * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getEventSpec()
     * @generated
     */
    EClass EVENT_SPEC = eINSTANCE.getEventSpec();

    /**
     * The meta object literal for the '<em><b>Port</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EVENT_SPEC__PORT = eINSTANCE.getEventSpec_Port();

    /**
     * The meta object literal for the '<em><b>Event Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EVENT_SPEC__EVENT_VALUE = eINSTANCE.getEventSpec_EventValue();

    /**
     * The meta object literal for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.PortImpl <em>Port</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.fordiac.ide.contractSpec.impl.PortImpl
     * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getPort()
     * @generated
     */
    EClass PORT = eINSTANCE.getPort();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PORT__NAME = eINSTANCE.getPort_Name();

    /**
     * The meta object literal for the '<em><b>Is Input</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute PORT__IS_INPUT = eINSTANCE.getPort_IsInput();

    /**
     * The meta object literal for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.IntervalImpl <em>Interval</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.fordiac.ide.contractSpec.impl.IntervalImpl
     * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getInterval()
     * @generated
     */
    EClass INTERVAL = eINSTANCE.getInterval();

    /**
     * The meta object literal for the '<em><b>Time</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference INTERVAL__TIME = eINSTANCE.getInterval_Time();

    /**
     * The meta object literal for the '<em><b>LBound</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INTERVAL__LBOUND = eINSTANCE.getInterval_LBound();

    /**
     * The meta object literal for the '<em><b>Lb Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INTERVAL__LB_VALUE = eINSTANCE.getInterval_LbValue();

    /**
     * The meta object literal for the '<em><b>Ub Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INTERVAL__UB_VALUE = eINSTANCE.getInterval_UbValue();

    /**
     * The meta object literal for the '<em><b>UBound</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INTERVAL__UBOUND = eINSTANCE.getInterval_UBound();

    /**
     * The meta object literal for the '<em><b>Unit</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute INTERVAL__UNIT = eINSTANCE.getInterval_Unit();

    /**
     * The meta object literal for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.TimeExprImpl <em>Time Expr</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.fordiac.ide.contractSpec.impl.TimeExprImpl
     * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getTimeExpr()
     * @generated
     */
    EClass TIME_EXPR = eINSTANCE.getTimeExpr();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TIME_EXPR__VALUE = eINSTANCE.getTimeExpr_Value();

    /**
     * The meta object literal for the '<em><b>Unit</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute TIME_EXPR__UNIT = eINSTANCE.getTimeExpr_Unit();

    /**
     * The meta object literal for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.CausalFuncDeclImpl <em>Causal Func Decl</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.fordiac.ide.contractSpec.impl.CausalFuncDeclImpl
     * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getCausalFuncDecl()
     * @generated
     */
    EClass CAUSAL_FUNC_DECL = eINSTANCE.getCausalFuncDecl();

    /**
     * The meta object literal for the '<em><b>Func Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CAUSAL_FUNC_DECL__FUNC_NAME = eINSTANCE.getCausalFuncDecl_FuncName();

    /**
     * The meta object literal for the '<em><b>Port1</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CAUSAL_FUNC_DECL__PORT1 = eINSTANCE.getCausalFuncDecl_Port1();

    /**
     * The meta object literal for the '<em><b>Port2</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CAUSAL_FUNC_DECL__PORT2 = eINSTANCE.getCausalFuncDecl_Port2();

    /**
     * The meta object literal for the '<em><b>Relation</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CAUSAL_FUNC_DECL__RELATION = eINSTANCE.getCausalFuncDecl_Relation();

    /**
     * The meta object literal for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.ClockDefinitionImpl <em>Clock Definition</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.fordiac.ide.contractSpec.impl.ClockDefinitionImpl
     * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getClockDefinition()
     * @generated
     */
    EClass CLOCK_DEFINITION = eINSTANCE.getClockDefinition();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute CLOCK_DEFINITION__NAME = eINSTANCE.getClockDefinition_Name();

    /**
     * The meta object literal for the '<em><b>Resolution</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CLOCK_DEFINITION__RESOLUTION = eINSTANCE.getClockDefinition_Resolution();

    /**
     * The meta object literal for the '<em><b>Skew</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CLOCK_DEFINITION__SKEW = eINSTANCE.getClockDefinition_Skew();

    /**
     * The meta object literal for the '<em><b>Drift</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CLOCK_DEFINITION__DRIFT = eINSTANCE.getClockDefinition_Drift();

    /**
     * The meta object literal for the '<em><b>Maxdiff</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference CLOCK_DEFINITION__MAXDIFF = eINSTANCE.getClockDefinition_Maxdiff();

    /**
     * The meta object literal for the '{@link org.eclipse.fordiac.ide.contractSpec.Unit <em>Unit</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.fordiac.ide.contractSpec.Unit
     * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getUnit()
     * @generated
     */
    EEnum UNIT = eINSTANCE.getUnit();

    /**
     * The meta object literal for the '{@link org.eclipse.fordiac.ide.contractSpec.CausalFuncName <em>Causal Func Name</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.fordiac.ide.contractSpec.CausalFuncName
     * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getCausalFuncName()
     * @generated
     */
    EEnum CAUSAL_FUNC_NAME = eINSTANCE.getCausalFuncName();

    /**
     * The meta object literal for the '{@link org.eclipse.fordiac.ide.contractSpec.CausalRelation <em>Causal Relation</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.fordiac.ide.contractSpec.CausalRelation
     * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getCausalRelation()
     * @generated
     */
    EEnum CAUSAL_RELATION = eINSTANCE.getCausalRelation();

  }

} //ContractSpecPackage
