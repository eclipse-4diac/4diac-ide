/**
 * *******************************************************************************
 * Copyright (c) 2022 Primetals Technologies GmbH,
 *               2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Martin Jobst, Martin Melik Merkumians
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.fordiac.ide.model.data.DataPackage;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;

import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionFactory;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionPackage;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class STFunctionPackageImpl extends EPackageImpl implements STFunctionPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stFunctionSourceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stFunctionEClass = null;

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
	 * @see org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private STFunctionPackageImpl() {
		super(eNS_URI, STFunctionFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link STFunctionPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static STFunctionPackage init() {
		if (isInited) return (STFunctionPackage)EPackage.Registry.INSTANCE.getEPackage(STFunctionPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredSTFunctionPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		STFunctionPackageImpl theSTFunctionPackage = registeredSTFunctionPackage instanceof STFunctionPackageImpl ? (STFunctionPackageImpl)registeredSTFunctionPackage : new STFunctionPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		DataPackage.eINSTANCE.eClass();
		LibraryElementPackage.eINSTANCE.eClass();
		STCorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theSTFunctionPackage.createPackageContents();

		// Initialize created meta-data
		theSTFunctionPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSTFunctionPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(STFunctionPackage.eNS_URI, theSTFunctionPackage);
		return theSTFunctionPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTFunctionSource() {
		return stFunctionSourceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSTFunctionSource_Name() {
		return (EAttribute)stFunctionSourceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTFunctionSource_Imports() {
		return (EReference)stFunctionSourceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTFunctionSource_Functions() {
		return (EReference)stFunctionSourceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTFunction() {
		return stFunctionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTFunction_ReturnType() {
		return (EReference)stFunctionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTFunction_VarDeclarations() {
		return (EReference)stFunctionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTFunction_Code() {
		return (EReference)stFunctionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STFunctionFactory getSTFunctionFactory() {
		return (STFunctionFactory)getEFactoryInstance();
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
		stFunctionSourceEClass = createEClass(ST_FUNCTION_SOURCE);
		createEAttribute(stFunctionSourceEClass, ST_FUNCTION_SOURCE__NAME);
		createEReference(stFunctionSourceEClass, ST_FUNCTION_SOURCE__IMPORTS);
		createEReference(stFunctionSourceEClass, ST_FUNCTION_SOURCE__FUNCTIONS);

		stFunctionEClass = createEClass(ST_FUNCTION);
		createEReference(stFunctionEClass, ST_FUNCTION__RETURN_TYPE);
		createEReference(stFunctionEClass, ST_FUNCTION__VAR_DECLARATIONS);
		createEReference(stFunctionEClass, ST_FUNCTION__CODE);
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
		STCorePackage theSTCorePackage = (STCorePackage)EPackage.Registry.INSTANCE.getEPackage(STCorePackage.eNS_URI);
		LibraryElementPackage theLibraryElementPackage = (LibraryElementPackage)EPackage.Registry.INSTANCE.getEPackage(LibraryElementPackage.eNS_URI);
		DataPackage theDataPackage = (DataPackage)EPackage.Registry.INSTANCE.getEPackage(DataPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		stFunctionSourceEClass.getESuperTypes().add(theSTCorePackage.getSTSource());
		stFunctionEClass.getESuperTypes().add(theLibraryElementPackage.getICallable());

		// Initialize classes and features; add operations and parameters
		initEClass(stFunctionSourceEClass, STFunctionSource.class, "STFunctionSource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getSTFunctionSource_Name(), ecorePackage.getEString(), "name", null, 0, 1, STFunctionSource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTFunctionSource_Imports(), theSTCorePackage.getSTImport(), null, "imports", null, 0, -1, STFunctionSource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTFunctionSource_Functions(), this.getSTFunction(), null, "functions", null, 0, -1, STFunctionSource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(stFunctionEClass, STFunction.class, "STFunction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTFunction_ReturnType(), theDataPackage.getDataType(), null, "returnType", null, 0, 1, STFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTFunction_VarDeclarations(), theSTCorePackage.getSTVarDeclarationBlock(), null, "varDeclarations", null, 0, -1, STFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTFunction_Code(), theSTCorePackage.getSTStatement(), null, "code", null, 0, -1, STFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(stFunctionEClass, theLibraryElementPackage.getITypedElement(), "getInputParameters", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(stFunctionEClass, theLibraryElementPackage.getITypedElement(), "getOutputParameters", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(stFunctionEClass, theLibraryElementPackage.getITypedElement(), "getInOutParameters", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);
	}

} //STFunctionPackageImpl
