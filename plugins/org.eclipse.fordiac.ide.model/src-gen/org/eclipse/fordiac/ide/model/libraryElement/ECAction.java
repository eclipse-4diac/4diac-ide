/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EC Action</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ECAction#getAlgorithm <em>Algorithm</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ECAction#getOutput <em>Output</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ECAction#getECState <em>EC State</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getECAction()
 * @model
 * @generated
 */
public interface ECAction extends EObject {
	/**
	 * Returns the value of the '<em><b>Algorithm</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Algorithm</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Algorithm</em>' reference.
	 * @see #setAlgorithm(Algorithm)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getECAction_Algorithm()
	 * @model
	 * @generated
	 */
	Algorithm getAlgorithm();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.ECAction#getAlgorithm <em>Algorithm</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Algorithm</em>' reference.
	 * @see #getAlgorithm()
	 * @generated
	 */
	void setAlgorithm(Algorithm value);

	/**
	 * Returns the value of the '<em><b>Output</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Output</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output</em>' reference.
	 * @see #setOutput(Event)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getECAction_Output()
	 * @model
	 * @generated
	 */
	Event getOutput();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.ECAction#getOutput <em>Output</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Output</em>' reference.
	 * @see #getOutput()
	 * @generated
	 */
	void setOutput(Event value);

	/**
	 * Returns the value of the '<em><b>EC State</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.libraryElement.ECState#getECAction <em>EC Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EC State</em>' container reference.
	 * @see #setECState(ECState)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getECAction_ECState()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECState#getECAction
	 * @model opposite="eCAction" required="true" transient="false"
	 * @generated
	 */
	ECState getECState();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.ECAction#getECState <em>EC State</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>EC State</em>' container reference.
	 * @see #getECState()
	 * @generated
	 */
	void setECState(ECState value);

} // ECAction
