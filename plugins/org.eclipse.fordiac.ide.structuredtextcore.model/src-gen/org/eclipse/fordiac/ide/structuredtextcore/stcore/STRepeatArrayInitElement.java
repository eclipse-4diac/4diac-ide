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
import org.eclipse.emf.common.util.EList;

import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ST Repeat Array Init Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatArrayInitElement#getRepetitions <em>Repetitions</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatArrayInitElement#getInitExpressions <em>Init Expressions</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTRepeatArrayInitElement()
 * @model
 * @generated
 */
public interface STRepeatArrayInitElement extends STArrayInitElement {
	/**
	 * Returns the value of the '<em><b>Repetitions</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Repetitions</em>' attribute.
	 * @see #setRepetitions(BigInteger)
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTRepeatArrayInitElement_Repetitions()
	 * @model
	 * @generated
	 */
	BigInteger getRepetitions();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatArrayInitElement#getRepetitions <em>Repetitions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Repetitions</em>' attribute.
	 * @see #getRepetitions()
	 * @generated
	 */
	void setRepetitions(BigInteger value);

	/**
	 * Returns the value of the '<em><b>Init Expressions</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpression}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Init Expressions</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTRepeatArrayInitElement_InitExpressions()
	 * @model containment="true"
	 * @generated
	 */
	EList<STInitializerExpression> getInitExpressions();

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

} // STRepeatArrayInitElement
