/*******************************************************************************
 * Copyright (c) 2012, 2013, 2015 - 2017 Profactor GmbH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.monitoring;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage
 * @generated
 */
public interface MonitoringFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	MonitoringFactory eINSTANCE = org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Element</em>'.
	 * @generated
	 */
	MonitoringElement createMonitoringElement();

	/**
	 * Returns a new object of class '<em>Adapter Element</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Adapter Element</em>'.
	 * @generated
	 */
	MonitoringAdapterElement createMonitoringAdapterElement();

	/**
	 * Returns a new object of class '<em>Adapter Port Element</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Adapter Port Element</em>'.
	 * @generated
	 */
	AdapterPortElement createAdapterPortElement();

	/**
	 * Returns a new object of class '<em>Adapter Monitoring Event</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Adapter Monitoring Event</em>'.
	 * @generated
	 */
	AdapterMonitoringEvent createAdapterMonitoringEvent();

	/**
	 * Returns a new object of class '<em>Adapter Monitoring Var Declaration</em>'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Adapter Monitoring Var Declaration</em>'.
	 * @generated
	 */
	AdapterMonitoringVarDeclaration createAdapterMonitoringVarDeclaration();

	/**
	 * Returns a new object of class '<em>Sub App Port Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Sub App Port Element</em>'.
	 * @generated
	 */
	SubAppPortElement createSubAppPortElement();

	/**
	 * Returns a new object of class '<em>Subapp Monitoring Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Subapp Monitoring Element</em>'.
	 * @generated
	 */
	SubappMonitoringElement createSubappMonitoringElement();

	/**
	 * Returns a new object of class '<em>Internal Var Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Internal Var Instance</em>'.
	 * @generated
	 */
	InternalVarInstance createInternalVarInstance();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	MonitoringPackage getMonitoringPackage();

} // MonitoringFactory
