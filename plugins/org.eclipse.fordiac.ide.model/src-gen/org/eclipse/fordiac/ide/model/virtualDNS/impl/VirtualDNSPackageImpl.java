/*******************************************************************************
 * Copyright (c) 2009 Profactor GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.virtualDNS.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSCollection;
import org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSEntry;
import org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSFactory;
import org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSManagement;
import org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class VirtualDNSPackageImpl extends EPackageImpl implements VirtualDNSPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass virtualDNSEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass virtualDNSCollectionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass virtualDNSManagementEClass = null;

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
	 * @see org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VirtualDNSPackageImpl() {
		super(eNS_URI, VirtualDNSFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link VirtualDNSPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VirtualDNSPackage init() {
		if (isInited) return (VirtualDNSPackage)EPackage.Registry.INSTANCE.getEPackage(VirtualDNSPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredVirtualDNSPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		VirtualDNSPackageImpl theVirtualDNSPackage = registeredVirtualDNSPackage instanceof VirtualDNSPackageImpl ? (VirtualDNSPackageImpl)registeredVirtualDNSPackage : new VirtualDNSPackageImpl();

		isInited = true;

		// Create package meta-data objects
		theVirtualDNSPackage.createPackageContents();

		// Initialize created meta-data
		theVirtualDNSPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theVirtualDNSPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VirtualDNSPackage.eNS_URI, theVirtualDNSPackage);
		return theVirtualDNSPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVirtualDNSEntry() {
		return virtualDNSEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVirtualDNSEntry_Name() {
		return (EAttribute)virtualDNSEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVirtualDNSEntry_Value() {
		return (EAttribute)virtualDNSEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVirtualDNSCollection() {
		return virtualDNSCollectionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVirtualDNSCollection_VirtualDNSEntries() {
		return (EReference)virtualDNSCollectionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getVirtualDNSCollection_Name() {
		return (EAttribute)virtualDNSCollectionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getVirtualDNSManagement() {
		return virtualDNSManagementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVirtualDNSManagement_AvailableDNSCollections() {
		return (EReference)virtualDNSManagementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getVirtualDNSManagement_ActiveVirtualDNS() {
		return (EReference)virtualDNSManagementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VirtualDNSFactory getVirtualDNSFactory() {
		return (VirtualDNSFactory)getEFactoryInstance();
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
		virtualDNSEntryEClass = createEClass(VIRTUAL_DNS_ENTRY);
		createEAttribute(virtualDNSEntryEClass, VIRTUAL_DNS_ENTRY__NAME);
		createEAttribute(virtualDNSEntryEClass, VIRTUAL_DNS_ENTRY__VALUE);

		virtualDNSCollectionEClass = createEClass(VIRTUAL_DNS_COLLECTION);
		createEReference(virtualDNSCollectionEClass, VIRTUAL_DNS_COLLECTION__VIRTUAL_DNS_ENTRIES);
		createEAttribute(virtualDNSCollectionEClass, VIRTUAL_DNS_COLLECTION__NAME);

		virtualDNSManagementEClass = createEClass(VIRTUAL_DNS_MANAGEMENT);
		createEReference(virtualDNSManagementEClass, VIRTUAL_DNS_MANAGEMENT__AVAILABLE_DNS_COLLECTIONS);
		createEReference(virtualDNSManagementEClass, VIRTUAL_DNS_MANAGEMENT__ACTIVE_VIRTUAL_DNS);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(virtualDNSEntryEClass, VirtualDNSEntry.class, "VirtualDNSEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getVirtualDNSEntry_Name(), ecorePackage.getEString(), "name", null, 0, 1, VirtualDNSEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getVirtualDNSEntry_Value(), ecorePackage.getEString(), "value", null, 0, 1, VirtualDNSEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(virtualDNSCollectionEClass, VirtualDNSCollection.class, "VirtualDNSCollection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getVirtualDNSCollection_VirtualDNSEntries(), this.getVirtualDNSEntry(), null, "virtualDNSEntries", null, 0, -1, VirtualDNSCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getVirtualDNSCollection_Name(), ecorePackage.getEString(), "name", null, 0, 1, VirtualDNSCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(virtualDNSManagementEClass, VirtualDNSManagement.class, "VirtualDNSManagement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getVirtualDNSManagement_AvailableDNSCollections(), this.getVirtualDNSCollection(), null, "availableDNSCollections", null, 0, -1, VirtualDNSManagement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getVirtualDNSManagement_ActiveVirtualDNS(), this.getVirtualDNSCollection(), null, "activeVirtualDNS", null, 0, 1, VirtualDNSManagement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);
	}

} //VirtualDNSPackageImpl
