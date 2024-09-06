/**
 * ******************************************************************************
 * Copyright (c) 2012, 2013, 2015 - 2017 Profactor GmbH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz
 *      - initial API and implementation and/or initial documentation
 * ******************************************************************************
 *
 */
package org.eclipse.fordiac.ide.model.monitoring;

import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Internal Var Instance</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.InternalVarInstance#getFb <em>Fb</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage#getInternalVarInstance()
 * @model
 * @generated
 */
public interface InternalVarInstance extends VarDeclaration {
	/**
	 * Returns the value of the '<em><b>Fb</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fb</em>' reference.
	 * @see #setFb(FB)
	 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage#getInternalVarInstance_Fb()
	 * @model
	 * @generated
	 */
	FB getFb();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.monitoring.InternalVarInstance#getFb <em>Fb</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fb</em>' reference.
	 * @see #getFb()
	 * @generated
	 */
	void setFb(FB value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	@Override
	FBNetworkElement getFBNetworkElement();

} // InternalVarInstance
