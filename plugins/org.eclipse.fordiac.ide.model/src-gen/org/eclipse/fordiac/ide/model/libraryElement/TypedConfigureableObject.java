/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.model.libraryElement;

import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Typed Configureable Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject#getTypeEntry <em>Type Entry</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getTypedConfigureableObject()
 * @model
 * @generated
 */
public interface TypedConfigureableObject extends ITypedElement, ConfigurableObject {
	/**
	 * Returns the value of the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Entry</em>' attribute.
	 * @see #setTypeEntry(TypeEntry)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getTypedConfigureableObject_TypeEntry()
	 * @model dataType="org.eclipse.fordiac.ide.model.libraryElement.TypeEntry" transient="true"
	 * @generated
	 */
	TypeEntry getTypeEntry();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject#getTypeEntry <em>Type Entry</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Entry</em>' attribute.
	 * @see #getTypeEntry()
	 * @generated
	 */
	void setTypeEntry(TypeEntry value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	String getTypeName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	LibraryElement getType();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.fordiac.ide.model.libraryElement.TypeLibrary" required="true"
	 * @generated
	 */
	TypeLibrary getTypeLibrary();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	String getFullTypeName();

} // TypedConfigureableObject
