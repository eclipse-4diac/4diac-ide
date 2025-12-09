/**
 * *******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.fordiac.ide.model.data.DataPackage;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmBody;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmFactory;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmPackage;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource;

import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSourceElement;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethodBody;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class STAlgorithmPackageImpl extends EPackageImpl implements STAlgorithmPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stAlgorithmEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stAlgorithmBodyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stAlgorithmSourceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stAlgorithmSourceElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stMethodEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stMethodBodyEClass = null;

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
	 * @see org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private STAlgorithmPackageImpl() {
		super(eNS_URI, STAlgorithmFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link STAlgorithmPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static STAlgorithmPackage init() {
		if (isInited) return (STAlgorithmPackage)EPackage.Registry.INSTANCE.getEPackage(STAlgorithmPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredSTAlgorithmPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		STAlgorithmPackageImpl theSTAlgorithmPackage = registeredSTAlgorithmPackage instanceof STAlgorithmPackageImpl ? (STAlgorithmPackageImpl)registeredSTAlgorithmPackage : new STAlgorithmPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		STCorePackage.eINSTANCE.eClass();
		LibraryElementPackage.eINSTANCE.eClass();
		DataPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theSTAlgorithmPackage.createPackageContents();

		// Initialize created meta-data
		theSTAlgorithmPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSTAlgorithmPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(STAlgorithmPackage.eNS_URI, theSTAlgorithmPackage);
		return theSTAlgorithmPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTAlgorithm() {
		return stAlgorithmEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTAlgorithm_Body() {
		return (EReference)stAlgorithmEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTAlgorithmBody() {
		return stAlgorithmBodyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTAlgorithmBody_VarTempDeclarations() {
		return (EReference)stAlgorithmBodyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTAlgorithmBody_Statements() {
		return (EReference)stAlgorithmBodyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTAlgorithmSource() {
		return stAlgorithmSourceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTAlgorithmSource_Elements() {
		return (EReference)stAlgorithmSourceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTAlgorithmSourceElement() {
		return stAlgorithmSourceElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTMethod() {
		return stMethodEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTMethod_ReturnType() {
		return (EReference)stMethodEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTMethod_Body() {
		return (EReference)stMethodEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSTMethodBody() {
		return stMethodBodyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTMethodBody_VarDeclarations() {
		return (EReference)stMethodBodyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSTMethodBody_Statements() {
		return (EReference)stMethodBodyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STAlgorithmFactory getSTAlgorithmFactory() {
		return (STAlgorithmFactory)getEFactoryInstance();
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
		stAlgorithmEClass = createEClass(ST_ALGORITHM);
		createEReference(stAlgorithmEClass, ST_ALGORITHM__BODY);

		stAlgorithmBodyEClass = createEClass(ST_ALGORITHM_BODY);
		createEReference(stAlgorithmBodyEClass, ST_ALGORITHM_BODY__VAR_TEMP_DECLARATIONS);
		createEReference(stAlgorithmBodyEClass, ST_ALGORITHM_BODY__STATEMENTS);

		stAlgorithmSourceEClass = createEClass(ST_ALGORITHM_SOURCE);
		createEReference(stAlgorithmSourceEClass, ST_ALGORITHM_SOURCE__ELEMENTS);

		stAlgorithmSourceElementEClass = createEClass(ST_ALGORITHM_SOURCE_ELEMENT);

		stMethodEClass = createEClass(ST_METHOD);
		createEReference(stMethodEClass, ST_METHOD__RETURN_TYPE);
		createEReference(stMethodEClass, ST_METHOD__BODY);

		stMethodBodyEClass = createEClass(ST_METHOD_BODY);
		createEReference(stMethodBodyEClass, ST_METHOD_BODY__VAR_DECLARATIONS);
		createEReference(stMethodBodyEClass, ST_METHOD_BODY__STATEMENTS);
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
		DataPackage theDataPackage = (DataPackage)EPackage.Registry.INSTANCE.getEPackage(DataPackage.eNS_URI);
		STCorePackage theSTCorePackage = (STCorePackage)EPackage.Registry.INSTANCE.getEPackage(STCorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		stAlgorithmEClass.getESuperTypes().add(this.getSTAlgorithmSourceElement());
		stAlgorithmEClass.getESuperTypes().add(theLibraryElementPackage.getICallable());
		stAlgorithmSourceEClass.getESuperTypes().add(theSTCorePackage.getSTSource());
		stAlgorithmSourceElementEClass.getESuperTypes().add(theLibraryElementPackage.getINamedElement());
		stMethodEClass.getESuperTypes().add(this.getSTAlgorithmSourceElement());
		stMethodEClass.getESuperTypes().add(theLibraryElementPackage.getICallable());

		// Initialize classes and features; add operations and parameters
		initEClass(stAlgorithmEClass, STAlgorithm.class, "STAlgorithm", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTAlgorithm_Body(), this.getSTAlgorithmBody(), null, "body", null, 0, 1, STAlgorithm.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(stAlgorithmEClass, theLibraryElementPackage.getITypedElement(), "getInputParameters", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(stAlgorithmEClass, theLibraryElementPackage.getITypedElement(), "getOutputParameters", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(stAlgorithmEClass, theLibraryElementPackage.getITypedElement(), "getInOutParameters", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(stAlgorithmEClass, theDataPackage.getDataType(), "getReturnType", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(stAlgorithmBodyEClass, STAlgorithmBody.class, "STAlgorithmBody", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTAlgorithmBody_VarTempDeclarations(), theSTCorePackage.getSTVarTempDeclarationBlock(), null, "varTempDeclarations", null, 0, -1, STAlgorithmBody.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTAlgorithmBody_Statements(), theSTCorePackage.getSTStatement(), null, "statements", null, 0, -1, STAlgorithmBody.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(stAlgorithmSourceEClass, STAlgorithmSource.class, "STAlgorithmSource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTAlgorithmSource_Elements(), this.getSTAlgorithmSourceElement(), null, "elements", null, 0, -1, STAlgorithmSource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(stAlgorithmSourceElementEClass, STAlgorithmSourceElement.class, "STAlgorithmSourceElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(stMethodEClass, STMethod.class, "STMethod", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTMethod_ReturnType(), theDataPackage.getDataType(), null, "returnType", null, 0, 1, STMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTMethod_Body(), this.getSTMethodBody(), null, "body", null, 0, 1, STMethod.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		addEOperation(stMethodEClass, theLibraryElementPackage.getITypedElement(), "getInputParameters", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(stMethodEClass, theLibraryElementPackage.getITypedElement(), "getOutputParameters", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(stMethodEClass, theLibraryElementPackage.getITypedElement(), "getInOutParameters", 0, -1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(stMethodBodyEClass, STMethodBody.class, "STMethodBody", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSTMethodBody_VarDeclarations(), theSTCorePackage.getSTVarDeclarationBlock(), null, "varDeclarations", null, 0, -1, STMethodBody.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getSTMethodBody_Statements(), theSTCorePackage.getSTStatement(), null, "statements", null, 0, -1, STMethodBody.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);
	}

} //STAlgorithmPackageImpl
