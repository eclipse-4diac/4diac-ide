/**
 * ******************************************************************************
 *  * Copyright (c) 2012, 2013, 2015 - 2017 Profactor GmbH, fortiss GmbH
 *  * 
 *  * All rights reserved. This program and the accompanying materials
 *  * are made available under the terms of the Eclipse Public License v1.0
 *  * which accompanies this distribution, and is available at
 *  * http://www.eclipse.org/legal/epl-v10.html
 *  *
 *  * Contributors:
 *  *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz
 *  *     - initial API and implementation and/or initial documentation
 *  ******************************************************************************
 * 
 */
package org.eclipse.fordiac.ide.deployment.monitoringbase;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBasePackage
 * @generated
 */
public interface MonitoringBaseFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MonitoringBaseFactory eINSTANCE = org.eclipse.fordiac.ide.deployment.monitoringbase.impl.MonitoringBaseFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Port Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Port Element</em>'.
	 * @generated
	 */
	PortElement createPortElement();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	MonitoringBasePackage getMonitoringBasePackage();

} //MonitoringBaseFactory
