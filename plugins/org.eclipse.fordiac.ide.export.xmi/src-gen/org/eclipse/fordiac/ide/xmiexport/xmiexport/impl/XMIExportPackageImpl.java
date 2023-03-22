/**
 * *******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Martin Jobst
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.xmiexport.xmiexport.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.fordiac.ide.model.data.DataPackage;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;

import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportFactory;
import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportInitialValue;
import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportInitialValues;
import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class XMIExportPackageImpl extends EPackageImpl implements XMIExportPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmiExportInitialValuesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass xmiExportInitialValueEClass = null;

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
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private XMIExportPackageImpl() {
		super(eNS_URI, XMIExportFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link XMIExportPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static XMIExportPackage init() {
		if (isInited) return (XMIExportPackage)EPackage.Registry.INSTANCE.getEPackage(XMIExportPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredXMIExportPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		XMIExportPackageImpl theXMIExportPackage = registeredXMIExportPackage instanceof XMIExportPackageImpl ? (XMIExportPackageImpl)registeredXMIExportPackage : new XMIExportPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		LibraryElementPackage.eINSTANCE.eClass();
		DataPackage.eINSTANCE.eClass();
		STCorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theXMIExportPackage.createPackageContents();

		// Initialize created meta-data
		theXMIExportPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theXMIExportPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(XMIExportPackage.eNS_URI, theXMIExportPackage);
		return theXMIExportPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getXMIExportInitialValues() {
		return xmiExportInitialValuesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getXMIExportInitialValues_InitialValues() {
		return (EReference)xmiExportInitialValuesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getXMIExportInitialValue() {
		return xmiExportInitialValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getXMIExportInitialValue_Variable() {
		return (EReference)xmiExportInitialValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getXMIExportInitialValue_Expression() {
		return (EReference)xmiExportInitialValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public XMIExportFactory getXMIExportFactory() {
		return (XMIExportFactory)getEFactoryInstance();
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
		xmiExportInitialValuesEClass = createEClass(XMI_EXPORT_INITIAL_VALUES);
		createEReference(xmiExportInitialValuesEClass, XMI_EXPORT_INITIAL_VALUES__INITIAL_VALUES);

		xmiExportInitialValueEClass = createEClass(XMI_EXPORT_INITIAL_VALUE);
		createEReference(xmiExportInitialValueEClass, XMI_EXPORT_INITIAL_VALUE__VARIABLE);
		createEReference(xmiExportInitialValueEClass, XMI_EXPORT_INITIAL_VALUE__EXPRESSION);
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
		LibraryElementPackage theLibraryElementPackage = (LibraryElementPackage)EPackage.Registry.INSTANCE.getEPackage(LibraryElementPackage.eNS_URI);
		STCorePackage theSTCorePackage = (STCorePackage)EPackage.Registry.INSTANCE.getEPackage(STCorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(xmiExportInitialValuesEClass, XMIExportInitialValues.class, "XMIExportInitialValues", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getXMIExportInitialValues_InitialValues(), this.getXMIExportInitialValue(), null, "initialValues", null, 0, -1, XMIExportInitialValues.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(xmiExportInitialValueEClass, XMIExportInitialValue.class, "XMIExportInitialValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getXMIExportInitialValue_Variable(), theLibraryElementPackage.getVarDeclaration(), null, "variable", null, 0, 1, XMIExportInitialValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getXMIExportInitialValue_Expression(), theSTCorePackage.getSTInitializerExpression(), null, "expression", null, 0, 1, XMIExportInitialValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);
	}

} //XMIExportPackageImpl
