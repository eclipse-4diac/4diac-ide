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
 * A representation of the model object '<em><b>Event Spec</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.EventSpec#getPort <em>Port</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.EventSpec#getEventValue <em>Event Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getEventSpec()
 * @model
 * @generated
 */
public interface EventSpec extends EObject
{
  /**
   * Returns the value of the '<em><b>Port</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Port</em>' reference.
   * @see #setPort(Port)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getEventSpec_Port()
   * @model
   * @generated
   */
  Port getPort();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.EventSpec#getPort <em>Port</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Port</em>' reference.
   * @see #getPort()
   * @generated
   */
  void setPort(Port value);

  /**
   * Returns the value of the '<em><b>Event Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Event Value</em>' attribute.
   * @see #setEventValue(String)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getEventSpec_EventValue()
   * @model
   * @generated
   */
  String getEventValue();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.EventSpec#getEventValue <em>Event Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Event Value</em>' attribute.
   * @see #getEventValue()
   * @generated
   */
  void setEventValue(String value);

} // EventSpec
