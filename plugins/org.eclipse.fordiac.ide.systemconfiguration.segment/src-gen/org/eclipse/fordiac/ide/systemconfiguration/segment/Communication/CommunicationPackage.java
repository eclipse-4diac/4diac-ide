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
package org.eclipse.fordiac.ide.systemconfiguration.segment.Communication;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

/** <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.CommunicationFactory
 * @model kind="package"
 * @generated */
public interface CommunicationPackage extends EPackage {
	/** The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	String eNAME = "Communication"; //$NON-NLS-1$

	/** The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	String eNS_URI = "http://Communication/1.0"; //$NON-NLS-1$

	/** The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	String eNS_PREFIX = "comm"; //$NON-NLS-1$

	/** The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	CommunicationPackage eINSTANCE = org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.impl.CommunicationPackageImpl
			.init();

	/** The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.impl.TsnConfigurationImpl <em>Tsn
	 * Configuration</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.impl.TsnConfigurationImpl
	 * @see org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.impl.CommunicationPackageImpl#getTsnConfiguration()
	 * @generated */
	int TSN_CONFIGURATION = 0;

	/** The feature id for the '<em><b>Cycle Time</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered */
	int TSN_CONFIGURATION__CYCLE_TIME = LibraryElementPackage.COMMUNICATION_CONFIGURATION_FEATURE_COUNT + 0;

	/** The feature id for the '<em><b>Windows</b></em>' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered */
	int TSN_CONFIGURATION__WINDOWS = LibraryElementPackage.COMMUNICATION_CONFIGURATION_FEATURE_COUNT + 1;

	/** The number of structural features of the '<em>Tsn Configuration</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered */
	int TSN_CONFIGURATION_FEATURE_COUNT = LibraryElementPackage.COMMUNICATION_CONFIGURATION_FEATURE_COUNT + 2;

	/** The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.impl.TsnWindowImpl <em>Tsn
	 * Window</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.impl.TsnWindowImpl
	 * @see org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.impl.CommunicationPackageImpl#getTsnWindow()
	 * @generated */
	int TSN_WINDOW = 1;

	/** The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered */
	int TSN_WINDOW__NAME = LibraryElementPackage.INAMED_ELEMENT__NAME;

	/** The feature id for the '<em><b>Comment</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered */
	int TSN_WINDOW__COMMENT = LibraryElementPackage.INAMED_ELEMENT__COMMENT;

	/** The feature id for the '<em><b>Duration</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered */
	int TSN_WINDOW__DURATION = LibraryElementPackage.INAMED_ELEMENT_FEATURE_COUNT + 0;

	/** The number of structural features of the '<em>Tsn Window</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated
	 * @ordered */
	int TSN_WINDOW_FEATURE_COUNT = LibraryElementPackage.INAMED_ELEMENT_FEATURE_COUNT + 1;

	/** Returns the meta object for class
	 * '{@link org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnConfiguration <em>Tsn
	 * Configuration</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Tsn Configuration</em>'.
	 * @see org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnConfiguration
	 * @generated */
	EClass getTsnConfiguration();

	/** Returns the meta object for the attribute
	 * '{@link org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnConfiguration#getCycleTime <em>Cycle
	 * Time</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Cycle Time</em>'.
	 * @see org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnConfiguration#getCycleTime()
	 * @see #getTsnConfiguration()
	 * @generated */
	EAttribute getTsnConfiguration_CycleTime();

	/** Returns the meta object for the reference list
	 * '{@link org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnConfiguration#getWindows
	 * <em>Windows</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Windows</em>'.
	 * @see org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnConfiguration#getWindows()
	 * @see #getTsnConfiguration()
	 * @generated */
	EReference getTsnConfiguration_Windows();

	/** Returns the meta object for class
	 * '{@link org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnWindow <em>Tsn Window</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Tsn Window</em>'.
	 * @see org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnWindow
	 * @generated */
	EClass getTsnWindow();

	/** Returns the meta object for the attribute
	 * '{@link org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnWindow#getDuration
	 * <em>Duration</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Duration</em>'.
	 * @see org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnWindow#getDuration()
	 * @see #getTsnWindow()
	 * @generated */
	EAttribute getTsnWindow_Duration();

	/** Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated */
	CommunicationFactory getCommunicationFactory();

	/** <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 *
	 * @generated */
	interface Literals {
		/** The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.impl.TsnConfigurationImpl <em>Tsn
		 * Configuration</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.impl.TsnConfigurationImpl
		 * @see org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.impl.CommunicationPackageImpl#getTsnConfiguration()
		 * @generated */
		EClass TSN_CONFIGURATION = eINSTANCE.getTsnConfiguration();

		/** The meta object literal for the '<em><b>Cycle Time</b></em>' attribute feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 *
		 * @generated */
		EAttribute TSN_CONFIGURATION__CYCLE_TIME = eINSTANCE.getTsnConfiguration_CycleTime();

		/** The meta object literal for the '<em><b>Windows</b></em>' reference list feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated */
		EReference TSN_CONFIGURATION__WINDOWS = eINSTANCE.getTsnConfiguration_Windows();

		/** The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.impl.TsnWindowImpl <em>Tsn
		 * Window</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.impl.TsnWindowImpl
		 * @see org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.impl.CommunicationPackageImpl#getTsnWindow()
		 * @generated */
		EClass TSN_WINDOW = eINSTANCE.getTsnWindow();

		/** The meta object literal for the '<em><b>Duration</b></em>' attribute feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 *
		 * @generated */
		EAttribute TSN_WINDOW__DURATION = eINSTANCE.getTsnWindow_Duration();

	}

} // CommunicationPackage
