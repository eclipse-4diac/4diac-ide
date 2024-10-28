/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2022-2023 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.model.libraryElement;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simple EC State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleECState#getSimpleECActions <em>Simple EC Actions</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleECState#getInputEvent <em>Input Event</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleECState#getSimpleFBType <em>Simple FB Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getSimpleECState()
 * @model
 * @generated
 */
public interface SimpleECState extends INamedElement {
	/**
	 * Returns the value of the '<em><b>Simple EC Actions</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.SimpleECAction}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleECAction#getSimpleECState <em>Simple EC State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Simple EC Actions</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getSimpleECState_SimpleECActions()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SimpleECAction#getSimpleECState
	 * @model opposite="simpleECState" containment="true"
	 *        extendedMetaData="kind='element' name='SimpleECActions' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<SimpleECAction> getSimpleECActions();

	/**
	 * Returns the value of the '<em><b>Input Event</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Input Event</em>' reference.
	 * @see #setInputEvent(Event)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getSimpleECState_InputEvent()
	 * @model required="true"
	 * @generated
	 */
	Event getInputEvent();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleECState#getInputEvent <em>Input Event</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Input Event</em>' reference.
	 * @see #getInputEvent()
	 * @generated
	 */
	void setInputEvent(Event value);

	/**
	 * Returns the value of the '<em><b>Simple FB Type</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType#getSimpleECStates <em>Simple EC States</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Simple FB Type</em>' container reference.
	 * @see #setSimpleFBType(SimpleFBType)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getSimpleECState_SimpleFBType()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType#getSimpleECStates
	 * @model opposite="simpleECStates" required="true" transient="false"
	 * @generated
	 */
	SimpleFBType getSimpleFBType();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleECState#getSimpleFBType <em>Simple FB Type</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Simple FB Type</em>' container reference.
	 * @see #getSimpleFBType()
	 * @generated
	 */
	void setSimpleFBType(SimpleFBType value);

} // SimpleECState
