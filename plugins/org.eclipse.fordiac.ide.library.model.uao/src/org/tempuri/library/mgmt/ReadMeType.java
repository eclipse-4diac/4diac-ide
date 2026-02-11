/**
 * *******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.tempuri.library.mgmt;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Read Me Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.tempuri.library.mgmt.ReadMeType#getPath <em>Path</em>}</li>
 * </ul>
 *
 * @see org.tempuri.library.mgmt.MgmtPackage#getReadMeType()
 * @model extendedMetaData="name='ReadMe_._type' kind='empty'"
 * @generated
 */
public interface ReadMeType extends EObject {
	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path</em>' attribute.
	 * @see #setPath(String)
	 * @see org.tempuri.library.mgmt.MgmtPackage#getReadMeType_Path()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='Path'"
	 * @generated
	 */
	String getPath();

	/**
	 * Sets the value of the '{@link org.tempuri.library.mgmt.ReadMeType#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #getPath()
	 * @generated
	 */
	void setPath(String value);

} // ReadMeType
