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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simple EC Action</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleECAction#getAlgorithm <em>Algorithm</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleECAction#getOutput <em>Output</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleECAction#getSimpleECState <em>Simple EC State</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getSimpleECAction()
 * @model
 * @generated
 */
public interface SimpleECAction extends EObject {
	/**
	 * Returns the value of the '<em><b>Algorithm</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Algorithm</em>' attribute.
	 * @see #setAlgorithm(String)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getSimpleECAction_Algorithm()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getAlgorithm();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleECAction#getAlgorithm <em>Algorithm</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Algorithm</em>' attribute.
	 * @see #getAlgorithm()
	 * @generated
	 */
	void setAlgorithm(String value);

	/**
	 * Returns the value of the '<em><b>Output</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output</em>' reference.
	 * @see #setOutput(Event)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getSimpleECAction_Output()
	 * @model
	 * @generated
	 */
	Event getOutput();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleECAction#getOutput <em>Output</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Output</em>' reference.
	 * @see #getOutput()
	 * @generated
	 */
	void setOutput(Event value);

	/**
	 * Returns the value of the '<em><b>Simple EC State</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleECState#getSimpleECActions <em>Simple EC Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Simple EC State</em>' container reference.
	 * @see #setSimpleECState(SimpleECState)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getSimpleECAction_SimpleECState()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SimpleECState#getSimpleECActions
	 * @model opposite="simpleECActions" required="true" transient="false"
	 * @generated
	 */
	SimpleECState getSimpleECState();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleECAction#getSimpleECState <em>Simple EC State</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Simple EC State</em>' container reference.
	 * @see #getSimpleECState()
	 * @generated
	 */
	void setSimpleECState(SimpleECState value);

} // SimpleECAction
