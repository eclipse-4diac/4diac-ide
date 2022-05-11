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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ST Array Init Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitElement#getIndexOrInitExpression <em>Index Or Init Expression</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitElement#getInitExpressions <em>Init Expressions</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTArrayInitElement()
 * @model
 * @generated
 */
public interface STArrayInitElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Index Or Init Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Index Or Init Expression</em>' containment reference.
	 * @see #setIndexOrInitExpression(STInitializerExpression)
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTArrayInitElement_IndexOrInitExpression()
	 * @model containment="true"
	 * @generated
	 */
	STInitializerExpression getIndexOrInitExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitElement#getIndexOrInitExpression <em>Index Or Init Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Index Or Init Expression</em>' containment reference.
	 * @see #getIndexOrInitExpression()
	 * @generated
	 */
	void setIndexOrInitExpression(STInitializerExpression value);

	/**
	 * Returns the value of the '<em><b>Init Expressions</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpression}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Init Expressions</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTArrayInitElement_InitExpressions()
	 * @model containment="true"
	 * @generated
	 */
	EList<STInitializerExpression> getInitExpressions();

} // STArrayInitElement
