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

import org.eclipse.fordiac.ide.model.data.DataType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Adapter Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterType#getPlugType <em>Plug Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getAdapterType()
 * @model
 * @generated
 */
public interface AdapterType extends DataType, FBType {

	/**
	 * Returns the value of the '<em><b>Plug Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Plug Type</em>' containment reference.
	 * @see #setPlugType(AdapterType)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getAdapterType_PlugType()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	AdapterType getPlugType();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterType#getPlugType <em>Plug Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Plug Type</em>' containment reference.
	 * @see #getPlugType()
	 * @generated
	 */
	void setPlugType(AdapterType value);

} // AdapterType
