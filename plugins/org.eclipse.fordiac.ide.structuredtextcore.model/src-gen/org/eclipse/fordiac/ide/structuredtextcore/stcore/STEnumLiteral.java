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
package org.eclipse.fordiac.ide.structuredtextcore.stcore;

import org.eclipse.fordiac.ide.model.data.EnumeratedValue;

import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ST Enum Literal</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STEnumLiteral#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTEnumLiteral()
 * @model
 * @generated
 */
public interface STEnumLiteral extends STExpression {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' reference.
	 * @see #setValue(EnumeratedValue)
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTEnumLiteral_Value()
	 * @model
	 * @generated
	 */
	EnumeratedValue getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STEnumLiteral#getValue <em>Value</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(EnumeratedValue value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	INamedElement getResultType();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	INamedElement getDeclaredResultType();

} // STEnumLiteral
