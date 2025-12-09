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

import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ST Method Body</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethodBody#getVarDeclarations <em>Var Declarations</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethodBody#getStatements <em>Statements</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmPackage#getSTMethodBody()
 * @model
 * @generated
 */
public interface STMethodBody extends EObject {
	/**
	 * Returns the value of the '<em><b>Var Declarations</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Var Declarations</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmPackage#getSTMethodBody_VarDeclarations()
	 * @model containment="true"
	 * @generated
	 */
	EList<STVarDeclarationBlock> getVarDeclarations();

	/**
	 * Returns the value of the '<em><b>Statements</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStatement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Statements</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmPackage#getSTMethodBody_Statements()
	 * @model containment="true"
	 * @generated
	 */
	EList<STStatement> getStatements();

} // STMethodBody
