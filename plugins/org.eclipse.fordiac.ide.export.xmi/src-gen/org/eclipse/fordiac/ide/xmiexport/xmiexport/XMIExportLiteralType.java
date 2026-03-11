/**
 * *******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.xmiexport.xmiexport;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;

import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Literal Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportLiteralType#getLiteral <em>Literal</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportLiteralType#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportPackage#getXMIExportLiteralType()
 * @model
 * @generated
 */
public interface XMIExportLiteralType extends EObject {
	/**
	 * Returns the value of the '<em><b>Literal</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Literal</em>' reference.
	 * @see #setLiteral(STExpression)
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportPackage#getXMIExportLiteralType_Literal()
	 * @model
	 * @generated
	 */
	STExpression getLiteral();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportLiteralType#getLiteral <em>Literal</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Literal</em>' reference.
	 * @see #getLiteral()
	 * @generated
	 */
	void setLiteral(STExpression value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' containment reference.
	 * @see #setType(LibraryElement)
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportPackage#getXMIExportLiteralType_Type()
	 * @model containment="true"
	 * @generated
	 */
	LibraryElement getType();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportLiteralType#getType <em>Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' containment reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(LibraryElement value);

} // XMIExportLiteralType
