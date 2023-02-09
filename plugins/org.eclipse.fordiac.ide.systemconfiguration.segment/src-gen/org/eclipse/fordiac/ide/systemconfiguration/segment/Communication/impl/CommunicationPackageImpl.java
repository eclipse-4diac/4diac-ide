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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.fordiac.ide.model.data.DataPackage;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.CommunicationFactory;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.CommunicationPackage;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnConfiguration;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnWindow;

/** <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 *
 * @generated */
public class CommunicationPackageImpl extends EPackageImpl implements CommunicationPackage {
	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass tsnConfigurationEClass = null;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private EClass tsnWindowEClass = null;

	/** Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
	 * EPackage.Registry} by the package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
	 * performs initialization of the package, or returns the registered package, if one already exists. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.CommunicationPackage#eNS_URI
	 * @see #init()
	 * @generated */
	private CommunicationPackageImpl() {
		super(eNS_URI, CommunicationFactory.eINSTANCE);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private static boolean isInited = false;

	/** Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>
	 * This method is used to initialize {@link CommunicationPackage#eINSTANCE} when that field is accessed. Clients
	 * should not invoke it directly. Instead, they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated */
	public static CommunicationPackage init() {
		if (isInited)
			return (CommunicationPackage) EPackage.Registry.INSTANCE.getEPackage(CommunicationPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredCommunicationPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		CommunicationPackageImpl theCommunicationPackage = registeredCommunicationPackage instanceof CommunicationPackageImpl
				? (CommunicationPackageImpl) registeredCommunicationPackage
				: new CommunicationPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		LibraryElementPackage.eINSTANCE.eClass();
		DataPackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theCommunicationPackage.createPackageContents();

		// Initialize created meta-data
		theCommunicationPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCommunicationPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CommunicationPackage.eNS_URI, theCommunicationPackage);
		return theCommunicationPackage;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getTsnConfiguration() {
		return tsnConfigurationEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getTsnConfiguration_CycleTime() {
		return (EAttribute) tsnConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EReference getTsnConfiguration_Windows() {
		return (EReference) tsnConfigurationEClass.getEStructuralFeatures().get(1);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EClass getTsnWindow() {
		return tsnWindowEClass;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EAttribute getTsnWindow_Duration() {
		return (EAttribute) tsnWindowEClass.getEStructuralFeatures().get(0);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public CommunicationFactory getCommunicationFactory() {
		return (CommunicationFactory) getEFactoryInstance();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private boolean isCreated = false;

	/** Creates the meta-model objects for the package. This method is guarded to have no affect on any invocation but
	 * its first. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		tsnConfigurationEClass = createEClass(TSN_CONFIGURATION);
		createEAttribute(tsnConfigurationEClass, TSN_CONFIGURATION__CYCLE_TIME);
		createEReference(tsnConfigurationEClass, TSN_CONFIGURATION__WINDOWS);

		tsnWindowEClass = createEClass(TSN_WINDOW);
		createEAttribute(tsnWindowEClass, TSN_WINDOW__DURATION);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	private boolean isInitialized = false;

	/** Complete the initialization of the package and its meta-model. This method is guarded to have no affect on any
	 * invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		LibraryElementPackage theLibraryElementPackage = (LibraryElementPackage) EPackage.Registry.INSTANCE
				.getEPackage(LibraryElementPackage.eNS_URI);
		XMLTypePackage theXMLTypePackage = (XMLTypePackage) EPackage.Registry.INSTANCE
				.getEPackage(XMLTypePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		tsnConfigurationEClass.getESuperTypes().add(theLibraryElementPackage.getCommunicationConfiguration());
		tsnWindowEClass.getESuperTypes().add(theLibraryElementPackage.getINamedElement());

		// Initialize classes and features; add operations and parameters
		initEClass(tsnConfigurationEClass, TsnConfiguration.class, "TsnConfiguration", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTsnConfiguration_CycleTime(), ecorePackage.getEInt(), "cycleTime", null, 1, 1, //$NON-NLS-1$
				TsnConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getTsnConfiguration_Windows(), this.getTsnWindow(), null, "windows", null, 1, 8, //$NON-NLS-1$
				TsnConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tsnWindowEClass, TsnWindow.class, "TsnWindow", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTsnWindow_Duration(), ecorePackage.getEInt(), "duration", "0", 1, 1, TsnWindow.class, //$NON-NLS-1$ //$NON-NLS-2$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(tsnWindowEClass, theXMLTypePackage.getString(), "getName", 1, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);
	}

} // CommunicationPackageImpl
