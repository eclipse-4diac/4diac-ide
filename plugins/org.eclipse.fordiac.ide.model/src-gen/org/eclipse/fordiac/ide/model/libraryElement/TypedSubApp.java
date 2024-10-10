/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2022-2023 Martin Erich Jobst
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
 * A representation of the model object '<em><b>Typed Sub App</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp#getSubAppNetwork <em>Sub App Network</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getTypedSubApp()
 * @model
 * @generated
 */
public interface TypedSubApp extends SubApp {
	/**
	 * Returns the value of the '<em><b>Sub App Network</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub App Network</em>' containment reference.
	 * @see #setSubAppNetwork(FBNetwork)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getTypedSubApp_SubAppNetwork()
	 * @model containment="true" resolveProxies="true" transient="true" derived="true"
	 * @generated
	 */
	FBNetwork getSubAppNetwork();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp#getSubAppNetwork <em>Sub App Network</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sub App Network</em>' containment reference.
	 * @see #getSubAppNetwork()
	 * @generated
	 */
	void setSubAppNetwork(FBNetwork value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isTyped();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	FBNetwork loadSubAppNetwork();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="org.eclipse.fordiac.ide.model.libraryElement.NamedElementStream"
	 * @generated
	 */
	Stream<INamedElement> findBySimpleName(String name);

} // TypedSubApp
