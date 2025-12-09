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

import java.util.stream.Stream;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Adapter Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration#getAdapterFB <em>Adapter FB</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration#getInterfaceOnlyAdapterFB <em>Interface Only Adapter FB</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getAdapterDeclaration()
 * @model
 * @generated
 */
public interface AdapterDeclaration extends IInterfaceElement {
	/**
	 * Returns the value of the '<em><b>Adapter FB</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Adapter FB</em>' reference.
	 * @see #setAdapterFB(AdapterFB)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getAdapterDeclaration_AdapterFB()
	 * @model
	 * @generated
	 */
	AdapterFB getAdapterFB();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration#getAdapterFB <em>Adapter FB</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Adapter FB</em>' reference.
	 * @see #getAdapterFB()
	 * @generated
	 */
	void setAdapterFB(AdapterFB value);

	/**
	 * Returns the value of the '<em><b>Interface Only Adapter FB</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Interface Only Adapter FB</em>' containment reference.
	 * @see #setInterfaceOnlyAdapterFB(AdapterFB)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getAdapterDeclaration_InterfaceOnlyAdapterFB()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	AdapterFB getInterfaceOnlyAdapterFB();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration#getInterfaceOnlyAdapterFB <em>Interface Only Adapter FB</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Interface Only Adapter FB</em>' containment reference.
	 * @see #getInterfaceOnlyAdapterFB()
	 * @generated
	 */
	void setInterfaceOnlyAdapterFB(AdapterFB value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	AdapterType getType();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="org.eclipse.fordiac.ide.model.libraryElement.NamedElementStream"
	 * @generated
	 */
	Stream<INamedElement> findBySimpleName(String name);

} // AdapterDeclaration
