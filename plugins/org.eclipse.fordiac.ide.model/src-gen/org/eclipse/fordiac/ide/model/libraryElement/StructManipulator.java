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

import org.eclipse.fordiac.ide.model.data.StructuredType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Struct Manipulator</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.StructManipulator#getStructType <em>Struct Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getStructManipulator()
 * @model
 * @generated
 */
public interface StructManipulator extends ConfigurableFB {
	/**
	 * Returns the value of the '<em><b>Struct Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Struct Type</em>' reference.
	 * @see #setStructType(StructuredType)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getStructManipulator_StructType()
	 * @model required="true" transient="true"
	 * @generated
	 */
	StructuredType getStructType();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.StructManipulator#getStructType <em>Struct Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Struct Type</em>' reference.
	 * @see #getStructType()
	 * @generated
	 */
	void setStructType(StructuredType value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void setStructTypeElementsAtInterface(StructuredType newStruct);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void updateConfiguration(Attribute config);

} // StructManipulator
