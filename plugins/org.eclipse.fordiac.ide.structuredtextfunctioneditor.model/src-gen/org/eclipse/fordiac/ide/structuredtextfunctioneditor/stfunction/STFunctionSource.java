/**
 * *******************************************************************************
 * Copyright (c) 2022 Primetals Technologies GmbH,
 *               2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Martin Jobst, Martin Melik Merkumians
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction;

import org.eclipse.emf.common.util.EList;

import org.eclipse.fordiac.ide.structuredtextcore.stcore.STImport;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Source</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource#getImports <em>Imports</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource#getFunctions <em>Functions</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionPackage#getSTFunctionSource()
 * @model
 * @generated
 */
public interface STFunctionSource extends STSource {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionPackage#getSTFunctionSource_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Imports</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STImport}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Imports</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionPackage#getSTFunctionSource_Imports()
	 * @model containment="true"
	 * @generated
	 */
	EList<STImport> getImports();

	/**
	 * Returns the value of the '<em><b>Functions</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Functions</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionPackage#getSTFunctionSource_Functions()
	 * @model containment="true"
	 * @generated
	 */
	EList<STFunction> getFunctions();

} // STFunctionSource
