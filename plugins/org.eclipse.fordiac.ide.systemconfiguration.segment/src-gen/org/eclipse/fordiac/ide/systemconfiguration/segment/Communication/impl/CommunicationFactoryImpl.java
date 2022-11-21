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
package org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.CommunicationFactory;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.CommunicationPackage;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnConfiguration;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnWindow;

/** <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 *
 * @generated */
public class CommunicationFactoryImpl extends EFactoryImpl implements CommunicationFactory {
	/** Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public static CommunicationFactory init() {
		try {
			CommunicationFactory theCommunicationFactory = (CommunicationFactory) EPackage.Registry.INSTANCE
					.getEFactory(CommunicationPackage.eNS_URI);
			if (theCommunicationFactory != null) {
				return theCommunicationFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CommunicationFactoryImpl();
	}

	/** Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public CommunicationFactoryImpl() {
		super();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case CommunicationPackage.TSN_CONFIGURATION:
			return createTsnConfiguration();
		case CommunicationPackage.TSN_WINDOW:
			return createTsnWindow();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public TsnConfiguration createTsnConfiguration() {
		TsnConfigurationImpl tsnConfiguration = new TsnConfigurationImpl();
		return tsnConfiguration;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public TsnWindow createTsnWindow() {
		TsnWindowImpl tsnWindow = new TsnWindowImpl();
		return tsnWindow;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public CommunicationPackage getCommunicationPackage() {
		return (CommunicationPackage) getEPackage();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated */
	@Deprecated
	public static CommunicationPackage getPackage() {
		return CommunicationPackage.eINSTANCE;
	}

} // CommunicationFactoryImpl
