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
package org.eclipse.fordiac.ide.deployment.monitoringbase.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseFactory;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBasePackage;
import org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement;
import org.eclipse.fordiac.ide.gef.editparts.IEditPartCreator;
import org.eclipse.fordiac.ide.model.Palette.PalettePackage;
import org.eclipse.fordiac.ide.model.data.DataPackage;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MonitoringBasePackageImpl extends EPackageImpl implements MonitoringBasePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass monitoringBaseElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass portElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iEditPartCreatorEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBasePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private MonitoringBasePackageImpl() {
		super(eNS_URI, MonitoringBaseFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link MonitoringBasePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static MonitoringBasePackage init() {
		if (isInited) return (MonitoringBasePackage)EPackage.Registry.INSTANCE.getEPackage(MonitoringBasePackage.eNS_URI);

		// Obtain or create and register package
		Object registeredMonitoringBasePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		MonitoringBasePackageImpl theMonitoringBasePackage = registeredMonitoringBasePackage instanceof MonitoringBasePackageImpl ? (MonitoringBasePackageImpl)registeredMonitoringBasePackage : new MonitoringBasePackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		PalettePackage.eINSTANCE.eClass();
		LibraryElementPackage.eINSTANCE.eClass();
		DataPackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theMonitoringBasePackage.createPackageContents();

		// Initialize created meta-data
		theMonitoringBasePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theMonitoringBasePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(MonitoringBasePackage.eNS_URI, theMonitoringBasePackage);
		return theMonitoringBasePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMonitoringBaseElement() {
		return monitoringBaseElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMonitoringBaseElement_Port() {
		return (EReference)monitoringBaseElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMonitoringBaseElement_Offline() {
		return (EAttribute)monitoringBaseElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPortElement() {
		return portElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPortElement_Fb() {
		return (EReference)portElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPortElement_InterfaceElement() {
		return (EReference)portElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPortElement_Resource() {
		return (EReference)portElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPortElement_Hierarchy() {
		return (EAttribute)portElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIEditPartCreator() {
		return iEditPartCreatorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MonitoringBaseFactory getMonitoringBaseFactory() {
		return (MonitoringBaseFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		monitoringBaseElementEClass = createEClass(MONITORING_BASE_ELEMENT);
		createEReference(monitoringBaseElementEClass, MONITORING_BASE_ELEMENT__PORT);
		createEAttribute(monitoringBaseElementEClass, MONITORING_BASE_ELEMENT__OFFLINE);

		portElementEClass = createEClass(PORT_ELEMENT);
		createEReference(portElementEClass, PORT_ELEMENT__FB);
		createEReference(portElementEClass, PORT_ELEMENT__INTERFACE_ELEMENT);
		createEReference(portElementEClass, PORT_ELEMENT__RESOURCE);
		createEAttribute(portElementEClass, PORT_ELEMENT__HIERARCHY);

		iEditPartCreatorEClass = createEClass(IEDIT_PART_CREATOR);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);
		LibraryElementPackage theLibraryElementPackage = (LibraryElementPackage)EPackage.Registry.INSTANCE.getEPackage(LibraryElementPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		monitoringBaseElementEClass.getESuperTypes().add(this.getIEditPartCreator());

		// Initialize classes and features; add operations and parameters
		initEClass(monitoringBaseElementEClass, MonitoringBaseElement.class, "MonitoringBaseElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getMonitoringBaseElement_Port(), this.getPortElement(), null, "port", null, 0, 1, MonitoringBaseElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getMonitoringBaseElement_Offline(), theXMLTypePackage.getBoolean(), "offline", "true", 0, 1, MonitoringBaseElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		addEOperation(monitoringBaseElementEClass, theXMLTypePackage.getString(), "getPortString", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(monitoringBaseElementEClass, theXMLTypePackage.getString(), "getResourceString", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(monitoringBaseElementEClass, theXMLTypePackage.getString(), "getFBString", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(monitoringBaseElementEClass, theXMLTypePackage.getString(), "getQualifiedString", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(portElementEClass, PortElement.class, "PortElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getPortElement_Fb(), theLibraryElementPackage.getFB(), null, "fb", null, 0, 1, PortElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getPortElement_InterfaceElement(), theLibraryElementPackage.getIInterfaceElement(), null, "interfaceElement", null, 0, 1, PortElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getPortElement_Resource(), theLibraryElementPackage.getResource(), null, "resource", null, 0, 1, PortElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getPortElement_Hierarchy(), theXMLTypePackage.getString(), "hierarchy", null, 0, -1, PortElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(portElementEClass, theXMLTypePackage.getString(), "getPortString", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(portElementEClass, theLibraryElementPackage.getDevice(), "getDevice", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(portElementEClass, theLibraryElementPackage.getAutomationSystem(), "getSystem", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(iEditPartCreatorEClass, IEditPartCreator.class, "IEditPartCreator", IS_ABSTRACT, IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);
	}

} //MonitoringBasePackageImpl
