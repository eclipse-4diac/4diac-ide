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
 * A representation of the model object '<em><b>Single Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.SingleEvent#getEvents <em>Events</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.SingleEvent#getInterval <em>Interval</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.SingleEvent#getClock <em>Clock</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getSingleEvent()
 * @model
 * @generated
 */
public interface SingleEvent extends TimeSpec
{
  /**
   * Returns the value of the '<em><b>Events</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Events</em>' containment reference.
   * @see #setEvents(EventList)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getSingleEvent_Events()
   * @model containment="true"
   * @generated
   */
  EventList getEvents();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.SingleEvent#getEvents <em>Events</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Events</em>' containment reference.
   * @see #getEvents()
   * @generated
   */
  void setEvents(EventList value);

  /**
   * Returns the value of the '<em><b>Interval</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Interval</em>' containment reference.
   * @see #setInterval(Interval)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getSingleEvent_Interval()
   * @model containment="true"
   * @generated
   */
  Interval getInterval();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.SingleEvent#getInterval <em>Interval</em>}' containment reference.
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
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getSingleEvent_Clock()
   * @model
   * @generated
   */
  ClockDefinition getClock();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.SingleEvent#getClock <em>Clock</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Clock</em>' reference.
   * @see #getClock()
   * @generated
   */
  void setClock(ClockDefinition value);

} // SingleEvent
