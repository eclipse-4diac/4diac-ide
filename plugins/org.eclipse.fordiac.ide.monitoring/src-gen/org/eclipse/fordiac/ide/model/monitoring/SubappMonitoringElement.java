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

import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Subapp Monitoring Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.SubappMonitoringElement#getAnchor <em>Anchor</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage#getSubappMonitoringElement()
 * @model
 * @generated
 */
public interface SubappMonitoringElement extends MonitoringElement {
	/**
	 * Returns the value of the '<em><b>Anchor</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Anchor</em>' reference.
	 * @see #setAnchor(MonitoringBaseElement)
	 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage#getSubappMonitoringElement_Anchor()
	 * @model
	 * @generated
	 */
	MonitoringBaseElement getAnchor();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.monitoring.SubappMonitoringElement#getAnchor <em>Anchor</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Anchor</em>' reference.
	 * @see #getAnchor()
	 * @generated
	 */
	void setAnchor(MonitoringBaseElement value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	@Override
	String getQualifiedString();

} // SubappMonitoringElement
