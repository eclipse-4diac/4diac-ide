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
 * A representation of the model object '<em><b>Causal Age</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.CausalAge#getE1 <em>E1</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.CausalAge#getE2 <em>E2</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.CausalAge#getInterval <em>Interval</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.CausalAge#getClock <em>Clock</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getCausalAge()
 * @model
 * @generated
 */
public interface CausalAge extends TimeSpec
{
  /**
   * Returns the value of the '<em><b>E1</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>E1</em>' containment reference.
   * @see #setE1(EventSpec)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getCausalAge_E1()
   * @model containment="true"
   * @generated
   */
  EventSpec getE1();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.CausalAge#getE1 <em>E1</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>E1</em>' containment reference.
   * @see #getE1()
   * @generated
   */
  void setE1(EventSpec value);

  /**
   * Returns the value of the '<em><b>E2</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>E2</em>' containment reference.
   * @see #setE2(EventSpec)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getCausalAge_E2()
   * @model containment="true"
   * @generated
   */
  EventSpec getE2();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.CausalAge#getE2 <em>E2</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>E2</em>' containment reference.
   * @see #getE2()
   * @generated
   */
  void setE2(EventSpec value);

  /**
   * Returns the value of the '<em><b>Interval</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Interval</em>' containment reference.
   * @see #setInterval(Interval)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getCausalAge_Interval()
   * @model containment="true"
   * @generated
   */
  Interval getInterval();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.CausalAge#getInterval <em>Interval</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Interval</em>' containment reference.
   * @see #getInterval()
   * @generated
   */
  void setInterval(Interval value);

  /**
   * Returns the value of the '<em><b>Clock</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Clock</em>' reference.
   * @see #setClock(ClockDefinition)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getCausalAge_Clock()
   * @model
   * @generated
   */
  ClockDefinition getClock();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.CausalAge#getClock <em>Clock</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Clock</em>' reference.
   * @see #getClock()
   * @generated
   */
  void setClock(ClockDefinition value);

} // CausalAge
