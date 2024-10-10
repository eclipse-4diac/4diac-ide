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
 * A representation of the model object '<em><b>CFB Instance</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.CFBInstance#getCfbNetwork <em>Cfb Network</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getCFBInstance()
 * @model
 * @generated
 */
public interface CFBInstance extends FB {
	/**
	 * Returns the value of the '<em><b>Cfb Network</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cfb Network</em>' containment reference.
	 * @see #setCfbNetwork(FBNetwork)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getCFBInstance_CfbNetwork()
	 * @model containment="true" resolveProxies="true" transient="true" derived="true"
	 * @generated
	 */
	FBNetwork getCfbNetwork();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.CFBInstance#getCfbNetwork <em>Cfb Network</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cfb Network</em>' containment reference.
	 * @see #getCfbNetwork()
	 * @generated
	 */
	void setCfbNetwork(FBNetwork value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	FBNetwork loadCFBNetwork();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	CompositeFBType getType();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="org.eclipse.fordiac.ide.model.libraryElement.NamedElementStream"
	 * @generated
	 */
	Stream<INamedElement> findBySimpleName(String name);

} // CFBInstance
