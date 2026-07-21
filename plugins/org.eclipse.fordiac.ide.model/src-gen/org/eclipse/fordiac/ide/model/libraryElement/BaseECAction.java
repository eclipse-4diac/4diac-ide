/**
 * *******************************************************************************
 * Copyright (c) 2008, 2026 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                                                       Martin Erich Jobst, Primetals Technologies Austria GmbH
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
 * A representation of the model object '<em><b>Base EC Action</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.BaseECAction#getOutput <em>Output</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.BaseECAction#getAlgorithm <em>Algorithm</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getBaseECAction()
 * @model abstract="true"
 * @generated
 */
public interface BaseECAction extends EObject {
	/**
	 * Returns the value of the '<em><b>Output</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output</em>' reference.
	 * @see #setOutput(Event)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getBaseECAction_Output()
	 * @model
	 * @generated
	 */
	Event getOutput();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.BaseECAction#getOutput <em>Output</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Output</em>' reference.
	 * @see #getOutput()
	 * @generated
	 */
	void setOutput(Event value);

	/**
	 * Returns the value of the '<em><b>Algorithm</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Algorithm</em>' attribute.
	 * @see #setAlgorithm(String)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getBaseECAction_Algorithm()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getAlgorithm();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.BaseECAction#getAlgorithm <em>Algorithm</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Algorithm</em>' attribute.
	 * @see #getAlgorithm()
	 * @generated
	 */
	void setAlgorithm(String value);

} // BaseECAction
