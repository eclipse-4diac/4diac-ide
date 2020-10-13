/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Local
 * Variable</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.LocalVariable#getArrayStart
 * <em>Array Start</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.LocalVariable#getArrayStop
 * <em>Array Stop</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getLocalVariable()
 * @model
 * @generated
 */
public interface LocalVariable extends VarDeclaration {
	/**
	 * Returns the value of the '<em><b>Array Start</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Array Start</em>' attribute.
	 * @see #setArrayStart(int)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getLocalVariable_ArrayStart()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Int"
	 * @generated
	 */
	int getArrayStart();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.LocalVariable#getArrayStart
	 * <em>Array Start</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Array Start</em>' attribute.
	 * @see #getArrayStart()
	 * @generated
	 */
	void setArrayStart(int value);

	/**
	 * Returns the value of the '<em><b>Array Stop</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Array Stop</em>' attribute.
	 * @see #setArrayStop(int)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getLocalVariable_ArrayStop()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Int"
	 * @generated
	 */
	int getArrayStop();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.LocalVariable#getArrayStop
	 * <em>Array Stop</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Array Stop</em>' attribute.
	 * @see #getArrayStop()
	 * @generated
	 */
	void setArrayStop(int value);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc -->
	 * overwrite <!-- end-model-doc -->
	 * 
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 *        unique="false"
	 * @generated
	 */
	@Override
	int getArraySize();

} // LocalVariable
