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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage
 * @generated
 */
public interface ContractSpecFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  ContractSpecFactory eINSTANCE = org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Model</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Model</em>'.
   * @generated
   */
  Model createModel();

  /**
   * Returns a new object of class '<em>Time Spec</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Time Spec</em>'.
   * @generated
   */
  TimeSpec createTimeSpec();

  /**
   * Returns a new object of class '<em>Single Event</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Single Event</em>'.
   * @generated
   */
  SingleEvent createSingleEvent();

  /**
   * Returns a new object of class '<em>Repetition</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Repetition</em>'.
   * @generated
   */
  Repetition createRepetition();

  /**
   * Returns a new object of class '<em>Repetition Options</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Repetition Options</em>'.
   * @generated
   */
  RepetitionOptions createRepetitionOptions();

  /**
   * Returns a new object of class '<em>Jitter</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Jitter</em>'.
   * @generated
   */
  Jitter createJitter();

  /**
   * Returns a new object of class '<em>Offset</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Offset</em>'.
   * @generated
   */
  Offset createOffset();

  /**
   * Returns a new object of class '<em>Reaction</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Reaction</em>'.
   * @generated
   */
  Reaction createReaction();

  /**
   * Returns a new object of class '<em>Age</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Age</em>'.
   * @generated
   */
  Age createAge();

  /**
   * Returns a new object of class '<em>Causal Reaction</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Causal Reaction</em>'.
   * @generated
   */
  CausalReaction createCausalReaction();

  /**
   * Returns a new object of class '<em>Causal Age</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Causal Age</em>'.
   * @generated
   */
  CausalAge createCausalAge();

  /**
   * Returns a new object of class '<em>Event Expr</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Event Expr</em>'.
   * @generated
   */
  EventExpr createEventExpr();

  /**
   * Returns a new object of class '<em>Event List</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Event List</em>'.
   * @generated
   */
  EventList createEventList();

  /**
   * Returns a new object of class '<em>Event Spec</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Event Spec</em>'.
   * @generated
   */
  EventSpec createEventSpec();

  /**
   * Returns a new object of class '<em>Port</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Port</em>'.
   * @generated
   */
  Port createPort();

  /**
   * Returns a new object of class '<em>Interval</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Interval</em>'.
   * @generated
   */
  Interval createInterval();

  /**
   * Returns a new object of class '<em>Time Expr</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Time Expr</em>'.
   * @generated
   */
  TimeExpr createTimeExpr();

  /**
   * Returns a new object of class '<em>Value</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Value</em>'.
   * @generated
   */
  Value createValue();

  /**
   * Returns a new object of class '<em>Causal Func Decl</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Causal Func Decl</em>'.
   * @generated
   */
  CausalFuncDecl createCausalFuncDecl();

  /**
   * Returns a new object of class '<em>Clock Definition</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Clock Definition</em>'.
   * @generated
   */
  ClockDefinition createClockDefinition();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  ContractSpecPackage getContractSpecPackage();

} //ContractSpecFactory
