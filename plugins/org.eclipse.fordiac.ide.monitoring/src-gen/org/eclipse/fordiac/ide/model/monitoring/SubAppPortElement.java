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

import org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sub App Port Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.SubAppPortElement#getAnchor <em>Anchor</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage#getSubAppPortElement()
 * @model
 * @generated
 */
public interface SubAppPortElement extends PortElement {
	/**
	 * Returns the value of the '<em><b>Anchor</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Anchor</em>' reference.
	 * @see #setAnchor(IInterfaceElement)
	 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage#getSubAppPortElement_Anchor()
	 * @model
	 * @generated
	 */
	IInterfaceElement getAnchor();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.monitoring.SubAppPortElement#getAnchor <em>Anchor</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Anchor</em>' reference.
	 * @see #getAnchor()
	 * @generated
	 */
	void setAnchor(IInterfaceElement value);

} // SubAppPortElement
