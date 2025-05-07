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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reaction</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.Reaction#getTrigger <em>Trigger</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.Reaction#getReaction <em>Reaction</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.Reaction#getInterval <em>Interval</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.Reaction#getN <em>N</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.Reaction#getOutOf <em>Out Of</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.Reaction#getClock <em>Clock</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getReaction()
 * @model
 * @generated
 */
public interface Reaction extends TimeSpec
{
  /**
   * Returns the value of the '<em><b>Trigger</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Trigger</em>' containment reference.
   * @see #setTrigger(EventExpr)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getReaction_Trigger()
   * @model containment="true"
   * @generated
   */
  EventExpr getTrigger();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.Reaction#getTrigger <em>Trigger</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Trigger</em>' containment reference.
   * @see #getTrigger()
   * @generated
   */
  void setTrigger(EventExpr value);

  /**
   * Returns the value of the '<em><b>Reaction</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Reaction</em>' containment reference.
   * @see #setReaction(EventExpr)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getReaction_Reaction()
   * @model containment="true"
   * @generated
   */
  EventExpr getReaction();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.Reaction#getReaction <em>Reaction</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Reaction</em>' containment reference.
   * @see #getReaction()
   * @generated
   */
  void setReaction(EventExpr value);

  /**
   * Returns the value of the '<em><b>Interval</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Interval</em>' containment reference.
   * @see #setInterval(Interval)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getReaction_Interval()
   * @model containment="true"
   * @generated
   */
  Interval getInterval();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.Reaction#getInterval <em>Interval</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Interval</em>' containment reference.
   * @see #getInterval()
   * @generated
   */
  void setInterval(Interval value);

  /**
   * Returns the value of the '<em><b>N</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>N</em>' attribute.
   * @see #setN(int)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getReaction_N()
   * @model
   * @generated
   */
  int getN();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.Reaction#getN <em>N</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>N</em>' attribute.
   * @see #getN()
   * @generated
   */
  void setN(int value);

  /**
   * Returns the value of the '<em><b>Out Of</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Out Of</em>' attribute.
   * @see #setOutOf(int)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getReaction_OutOf()
   * @model
   * @generated
   */
  int getOutOf();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.Reaction#getOutOf <em>Out Of</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Out Of</em>' attribute.
   * @see #getOutOf()
   * @generated
   */
  void setOutOf(int value);

  /**
   * Returns the value of the '<em><b>Clock</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Clock</em>' reference.
   * @see #setClock(ClockDefinition)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getReaction_Clock()
   * @model
   * @generated
   */
  ClockDefinition getClock();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.Reaction#getClock <em>Clock</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Clock</em>' reference.
   * @see #getClock()
   * @generated
   */
  void setClock(ClockDefinition value);

} // Reaction
