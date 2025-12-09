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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Connection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.DataConnection#getFBNetwork <em>FB Network</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getDataConnection()
 * @model
 * @generated
 */
public interface DataConnection extends Connection {
	/**
	 * Returns the value of the '<em><b>FB Network</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.libraryElement.FBNetwork#getDataConnections <em>Data Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>FB Network</em>' container reference.
	 * @see #setFBNetwork(FBNetwork)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getDataConnection_FBNetwork()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FBNetwork#getDataConnections
	 * @model opposite="dataConnections" transient="false"
	 * @generated
	 */
	FBNetwork getFBNetwork();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.DataConnection#getFBNetwork <em>FB Network</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>FB Network</em>' container reference.
	 * @see #getFBNetwork()
	 * @generated
	 */
	void setFBNetwork(FBNetwork value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	VarDeclaration getDataSource();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	VarDeclaration getDataDestination();

} // DataConnection
