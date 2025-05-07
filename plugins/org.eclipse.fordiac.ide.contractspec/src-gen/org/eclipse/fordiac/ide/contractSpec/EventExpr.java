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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event Expr</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.EventExpr#getEvent <em>Event</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.EventExpr#getEvents <em>Events</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getEventExpr()
 * @model
 * @generated
 */
public interface EventExpr extends EObject
{
  /**
   * Returns the value of the '<em><b>Event</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Event</em>' containment reference.
   * @see #setEvent(EventSpec)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getEventExpr_Event()
   * @model containment="true"
   * @generated
   */
  EventSpec getEvent();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.EventExpr#getEvent <em>Event</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Event</em>' containment reference.
   * @see #getEvent()
   * @generated
   */
  void setEvent(EventSpec value);

  /**
   * Returns the value of the '<em><b>Events</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Events</em>' containment reference.
   * @see #setEvents(EventList)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getEventExpr_Events()
   * @model containment="true"
   * @generated
   */
  EventList getEvents();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.EventExpr#getEvents <em>Events</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Events</em>' containment reference.
   * @see #getEvents()
   * @generated
   */
  void setEvents(EventList value);

} // EventExpr
