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

import java.math.BigInteger;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ST Multibit Partial Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression#getSpecifier <em>Specifier</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression#getIndex <em>Index</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTMultibitPartialExpression()
 * @model
 * @generated
 */
public interface STMultibitPartialExpression extends STExpression {
	/**
	 * Returns the value of the '<em><b>Specifier</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultiBitAccessSpecifier}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specifier</em>' attribute.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultiBitAccessSpecifier
	 * @see #setSpecifier(STMultiBitAccessSpecifier)
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTMultibitPartialExpression_Specifier()
	 * @model
	 * @generated
	 */
	STMultiBitAccessSpecifier getSpecifier();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression#getSpecifier <em>Specifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specifier</em>' attribute.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultiBitAccessSpecifier
	 * @see #getSpecifier()
	 * @generated
	 */
	void setSpecifier(STMultiBitAccessSpecifier value);

	/**
	 * Returns the value of the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Index</em>' attribute.
	 * @see #setIndex(BigInteger)
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTMultibitPartialExpression_Index()
	 * @model
	 * @generated
	 */
	BigInteger getIndex();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression#getIndex <em>Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Index</em>' attribute.
	 * @see #getIndex()
	 * @generated
	 */
	void setIndex(BigInteger value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	INamedElement getResultType();

} // STMultibitPartialExpression
