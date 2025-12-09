/**
 * *******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmendía, Bianca Wiesmayr
 *          - initial implementation and/or documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.fb.interpreter.OpSem;

import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Function
 * FB Type Runtime</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FunctionFBTypeRuntime#getFunctionFBType
 * <em>Function FB Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getFunctionFBTypeRuntime()
 * @model
 * @generated
 */
public interface FunctionFBTypeRuntime extends FBRuntimeAbstract {
	/**
	 * Returns the value of the '<em><b>Function FB Type</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Function FB Type</em>' containment reference.
	 * @see #setFunctionFBType(FunctionFBType)
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getFunctionFBTypeRuntime_FunctionFBType()
	 * @model containment="true" resolveProxies="true" required="true"
	 * @generated
	 */
	FunctionFBType getFunctionFBType();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FunctionFBTypeRuntime#getFunctionFBType
	 * <em>Function FB Type</em>}' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Function FB Type</em>' containment
	 *              reference.
	 * @see #getFunctionFBType()
	 * @generated
	 */
	void setFunctionFBType(FunctionFBType value);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @model kind="operation" required="true"
	 * @generated
	 */
	@Override
	FunctionFBType getModel();

} // FunctionFBTypeRuntime
