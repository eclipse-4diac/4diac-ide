/**
 * *******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr
 *          - initial implementation and/or documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.systemconfiguration.segment.communication;

import org.eclipse.emf.ecore.EFactory;

/** <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract class of
 * the model. <!-- end-user-doc -->
 *
 * @see org.eclipse.fordiac.ide.systemconfiguration.segment.communication.CommunicationPackage
 * @generated */
public interface CommunicationFactory extends EFactory {
	/** The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	CommunicationFactory eINSTANCE = org.eclipse.fordiac.ide.systemconfiguration.segment.communication.impl.CommunicationFactoryImpl
			.init();

	/** Returns a new object of class '<em>Tsn Configuration</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Tsn Configuration</em>'.
	 * @generated */
	TsnConfiguration createTsnConfiguration();

	/** Returns a new object of class '<em>Tsn Window</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Tsn Window</em>'.
	 * @generated */
	TsnWindow createTsnWindow();

	/** Returns a new object of class '<em>Default Configuration</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Default Configuration</em>'.
	 * @generated */
	DefaultConfiguration createDefaultConfiguration();

	/** Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the package supported by this factory.
	 * @generated */
	CommunicationPackage getCommunicationPackage();

} // CommunicationFactory
