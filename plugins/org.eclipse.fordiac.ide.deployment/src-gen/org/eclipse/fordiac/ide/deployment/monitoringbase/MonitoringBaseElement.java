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
package org.eclipse.fordiac.ide.deployment.monitoringbase;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.gef.editparts.IEditPartCreator;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Element</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement#getPort <em>Port</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement#isOffline <em>Offline</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBasePackage#getMonitoringBaseElement()
 * @model abstract="true" superTypes="org.eclipse.fordiac.ide.deployment.monitoringbase.IEditPartCreator"
 * @generated
 */
public interface MonitoringBaseElement extends EObject, IEditPartCreator {
	/**
	 * Returns the value of the '<em><b>Port</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Port</em>' reference isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Port</em>' reference.
	 * @see #setPort(PortElement)
	 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBasePackage#getMonitoringBaseElement_Port()
	 * @model
	 * @generated
	 */
	PortElement getPort();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement#getPort <em>Port</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Port</em>' reference.
	 * @see #getPort()
	 * @generated
	 */
	void setPort(PortElement value);

	/**
	 * Returns the value of the '<em><b>Offline</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Offline</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Offline</em>' attribute.
	 * @see #setOffline(boolean)
	 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBasePackage#getMonitoringBaseElement_Offline()
	 * @model default="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isOffline();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement#isOffline <em>Offline</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Offline</em>' attribute.
	 * @see #isOffline()
	 * @generated
	 */
	void setOffline(boolean value);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getPortString();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getResourceString();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getFBString();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getQualifiedString();

} // MonitoringBaseElement
