/*******************************************************************************
 * Copyright (c) 2012 Profactor GmbH
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
package org.eclipse.fordiac.ide.fbtester.model.testdata.impl;


import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.fordiac.ide.fbtester.model.testdata.TestData;
import org.eclipse.fordiac.ide.fbtester.model.testdata.TestdataFactory;
import org.eclipse.fordiac.ide.fbtester.model.testdata.TestdataPackage;
import org.eclipse.fordiac.ide.fbtester.model.testdata.ValuedVarDecl;
import org.eclipse.fordiac.ide.model.Palette.PalettePackage;
import org.eclipse.fordiac.ide.model.data.DataPackage;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TestdataPackageImpl extends EPackageImpl implements TestdataPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass testDataEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass valuedVarDeclEClass = null;

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
	 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.TestdataPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TestdataPackageImpl() {
		super(eNS_URI, TestdataFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link TestdataPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TestdataPackage init() {
		if (isInited) return (TestdataPackage)EPackage.Registry.INSTANCE.getEPackage(TestdataPackage.eNS_URI);

		// Obtain or create and register package
		TestdataPackageImpl theTestdataPackage = (TestdataPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof TestdataPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new TestdataPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		PalettePackage.eINSTANCE.eClass();
		LibraryElementPackage.eINSTANCE.eClass();
		DataPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theTestdataPackage.createPackageContents();

		// Initialize created meta-data
		theTestdataPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTestdataPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TestdataPackage.eNS_URI, theTestdataPackage);
		return theTestdataPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTestData() {
		return testDataEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTestData_TestName() {
		return (EAttribute)testDataEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTestData_Event() {
		return (EReference)testDataEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTestData_TestIntstance() {
		return (EAttribute)testDataEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTestData_EventOutputs() {
		return (EReference)testDataEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTestData_Values() {
		return (EReference)testDataEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTestData_Results() {
		return (EReference)testDataEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTestData_Line() {
		return (EAttribute)testDataEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTestData_Type() {
		return (EReference)testDataEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getValuedVarDecl() {
		return valuedVarDeclEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getValuedVarDecl_VarDeclaration() {
		return (EReference)valuedVarDeclEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getValuedVarDecl_Value() {
		return (EAttribute)valuedVarDeclEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TestdataFactory getTestdataFactory() {
		return (TestdataFactory)getEFactoryInstance();
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
		testDataEClass = createEClass(TEST_DATA);
		createEAttribute(testDataEClass, TEST_DATA__TEST_NAME);
		createEReference(testDataEClass, TEST_DATA__EVENT);
		createEAttribute(testDataEClass, TEST_DATA__TEST_INTSTANCE);
		createEReference(testDataEClass, TEST_DATA__EVENT_OUTPUTS);
		createEReference(testDataEClass, TEST_DATA__VALUES);
		createEReference(testDataEClass, TEST_DATA__RESULTS);
		createEAttribute(testDataEClass, TEST_DATA__LINE);
		createEReference(testDataEClass, TEST_DATA__TYPE);

		valuedVarDeclEClass = createEClass(VALUED_VAR_DECL);
		createEReference(valuedVarDeclEClass, VALUED_VAR_DECL__VAR_DECLARATION);
		createEAttribute(valuedVarDeclEClass, VALUED_VAR_DECL__VALUE);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(testDataEClass, TestData.class, "TestData", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTestData_TestName(), ecorePackage.getEString(), "testName", "testName", 0, 1, TestData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTestData_Event(), theLibraryElementPackage.getEvent(), null, "event", null, 0, 1, TestData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTestData_TestIntstance(), ecorePackage.getEString(), "testIntstance", null, 0, 1, TestData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTestData_EventOutputs(), theLibraryElementPackage.getEvent(), null, "eventOutputs", null, 0, -1, TestData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTestData_Values(), this.getValuedVarDecl(), null, "values", null, 0, -1, TestData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTestData_Results(), this.getValuedVarDecl(), null, "results", null, 0, -1, TestData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTestData_Line(), ecorePackage.getEString(), "line", null, 0, 1, TestData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTestData_Type(), theLibraryElementPackage.getFBType(), null, "type", null, 0, 1, TestData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(valuedVarDeclEClass, ValuedVarDecl.class, "ValuedVarDecl", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getValuedVarDecl_VarDeclaration(), theLibraryElementPackage.getVarDeclaration(), null, "varDeclaration", null, 0, 1, ValuedVarDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getValuedVarDecl_Value(), ecorePackage.getEString(), "value", null, 0, 1, ValuedVarDecl.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //TestdataPackageImpl
