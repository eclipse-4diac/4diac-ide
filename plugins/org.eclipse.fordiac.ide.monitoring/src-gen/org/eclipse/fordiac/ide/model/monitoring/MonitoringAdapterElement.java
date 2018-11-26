/*******************************************************************************
 * Copyright (c) 2012, 2013, 2015 - 2017 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.monitoring;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Adapter Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.MonitoringAdapterElement#getElements <em>Elements</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.MonitoringAdapterElement#getMonitoredAdapterFB <em>Monitored Adapter FB</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage#getMonitoringAdapterElement()
 * @model
 * @generated
 */
public interface MonitoringAdapterElement extends MonitoringBaseElement {

	/**
	 * Returns the value of the '<em><b>Elements</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.monitoring.MonitoringElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Elements</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Elements</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage#getMonitoringAdapterElement_Elements()
	 * @model containment="true"
	 * @generated
	 */
	EList<MonitoringElement> getElements();

	/**
	 * Returns the value of the '<em><b>Monitored Adapter FB</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Monitored Adapter FB</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Monitored Adapter FB</em>' reference.
	 * @see #setMonitoredAdapterFB(AdapterFB)
	 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage#getMonitoringAdapterElement_MonitoredAdapterFB()
	 * @model
	 * @generated
	 */
	AdapterFB getMonitoredAdapterFB();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.monitoring.MonitoringAdapterElement#getMonitoredAdapterFB <em>Monitored Adapter FB</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Monitored Adapter FB</em>' reference.
	 * @see #getMonitoredAdapterFB()
	 * @generated
	 */
	void setMonitoredAdapterFB(AdapterFB value);
} // MonitoringAdapterElement
