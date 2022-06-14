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

import org.eclipse.emf.ecore.EObject;

import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ST Struct Init Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitElement#getVariable <em>Variable</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitElement#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTStructInitElement()
 * @model
 * @generated
 */
public interface STStructInitElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variable</em>' reference.
	 * @see #setVariable(INamedElement)
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTStructInitElement_Variable()
	 * @model
	 * @generated
	 */
	INamedElement getVariable();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitElement#getVariable <em>Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Variable</em>' reference.
	 * @see #getVariable()
	 * @generated
	 */
	void setVariable(INamedElement value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' containment reference.
	 * @see #setValue(STInitializerExpression)
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTStructInitElement_Value()
	 * @model containment="true"
	 * @generated
	 */
	STInitializerExpression getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitElement#getValue <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(STInitializerExpression value);

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

} // STStructInitElement
