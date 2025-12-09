/**
 * *******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Martin Jobst
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ST Algorithm</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm#getBody <em>Body</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmPackage#getSTAlgorithm()
 * @model
 * @generated
 */
public interface STAlgorithm extends STAlgorithmSourceElement, ICallable {
	/**
	 * Returns the value of the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Body</em>' containment reference.
	 * @see #setBody(STAlgorithmBody)
	 * @see org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmPackage#getSTAlgorithm_Body()
	 * @model containment="true"
	 * @generated
	 */
	STAlgorithmBody getBody();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm#getBody <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Body</em>' containment reference.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(STAlgorithmBody value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	EList<ITypedElement> getInputParameters();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	EList<ITypedElement> getOutputParameters();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	EList<ITypedElement> getInOutParameters();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	DataType getReturnType();

} // STAlgorithm
