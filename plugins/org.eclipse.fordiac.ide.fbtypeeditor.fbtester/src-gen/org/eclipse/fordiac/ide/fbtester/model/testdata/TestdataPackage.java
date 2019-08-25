/*******************************************************************************
 * Copyright (c) 2012 Profactor GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtester.model.testdata;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.TestdataFactory
 * @model kind="package"
 * @generated
 */
public interface TestdataPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "testdata";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "at.ffg4iac.fbtester.model.testdata";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "testdata";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TestdataPackage eINSTANCE = org.eclipse.fordiac.ide.fbtester.model.testdata.impl.TestdataPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.fbtester.model.testdata.impl.TestDataImpl <em>Test Data</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.impl.TestDataImpl
	 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.impl.TestdataPackageImpl#getTestData()
	 * @generated
	 */
	int TEST_DATA = 0;

	/**
	 * The feature id for the '<em><b>Test Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_DATA__TEST_NAME = 0;

	/**
	 * The feature id for the '<em><b>Event</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_DATA__EVENT = 1;

	/**
	 * The feature id for the '<em><b>Test Intstance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_DATA__TEST_INTSTANCE = 2;

	/**
	 * The feature id for the '<em><b>Event Outputs</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_DATA__EVENT_OUTPUTS = 3;

	/**
	 * The feature id for the '<em><b>Values</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_DATA__VALUES = 4;

	/**
	 * The feature id for the '<em><b>Results</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_DATA__RESULTS = 5;

	/**
	 * The feature id for the '<em><b>Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_DATA__LINE = 6;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_DATA__TYPE = 7;

	/**
	 * The number of structural features of the '<em>Test Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_DATA_FEATURE_COUNT = 8;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.fbtester.model.testdata.impl.ValuedVarDeclImpl <em>Valued Var Decl</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.impl.ValuedVarDeclImpl
	 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.impl.TestdataPackageImpl#getValuedVarDecl()
	 * @generated
	 */
	int VALUED_VAR_DECL = 1;

	/**
	 * The feature id for the '<em><b>Var Declaration</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUED_VAR_DECL__VAR_DECLARATION = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUED_VAR_DECL__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Valued Var Decl</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUED_VAR_DECL_FEATURE_COUNT = 2;


	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.fbtester.model.testdata.TestData <em>Test Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Test Data</em>'.
	 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.TestData
	 * @generated
	 */
	EClass getTestData();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.fbtester.model.testdata.TestData#getTestName <em>Test Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Test Name</em>'.
	 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.TestData#getTestName()
	 * @see #getTestData()
	 * @generated
	 */
	EAttribute getTestData_TestName();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.fbtester.model.testdata.TestData#getEvent <em>Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Event</em>'.
	 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.TestData#getEvent()
	 * @see #getTestData()
	 * @generated
	 */
	EReference getTestData_Event();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.fbtester.model.testdata.TestData#getTestIntstance <em>Test Intstance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Test Intstance</em>'.
	 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.TestData#getTestIntstance()
	 * @see #getTestData()
	 * @generated
	 */
	EAttribute getTestData_TestIntstance();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fordiac.ide.fbtester.model.testdata.TestData#getEventOutputs <em>Event Outputs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Event Outputs</em>'.
	 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.TestData#getEventOutputs()
	 * @see #getTestData()
	 * @generated
	 */
	EReference getTestData_EventOutputs();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fordiac.ide.fbtester.model.testdata.TestData#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Values</em>'.
	 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.TestData#getValues()
	 * @see #getTestData()
	 * @generated
	 */
	EReference getTestData_Values();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fordiac.ide.fbtester.model.testdata.TestData#getResults <em>Results</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Results</em>'.
	 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.TestData#getResults()
	 * @see #getTestData()
	 * @generated
	 */
	EReference getTestData_Results();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.fbtester.model.testdata.TestData#getLine <em>Line</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Line</em>'.
	 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.TestData#getLine()
	 * @see #getTestData()
	 * @generated
	 */
	EAttribute getTestData_Line();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.fbtester.model.testdata.TestData#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.TestData#getType()
	 * @see #getTestData()
	 * @generated
	 */
	EReference getTestData_Type();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.fbtester.model.testdata.ValuedVarDecl <em>Valued Var Decl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Valued Var Decl</em>'.
	 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.ValuedVarDecl
	 * @generated
	 */
	EClass getValuedVarDecl();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.fbtester.model.testdata.ValuedVarDecl#getVarDeclaration <em>Var Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Var Declaration</em>'.
	 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.ValuedVarDecl#getVarDeclaration()
	 * @see #getValuedVarDecl()
	 * @generated
	 */
	EReference getValuedVarDecl_VarDeclaration();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.fbtester.model.testdata.ValuedVarDecl#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.ValuedVarDecl#getValue()
	 * @see #getValuedVarDecl()
	 * @generated
	 */
	EAttribute getValuedVarDecl_Value();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TestdataFactory getTestdataFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.fbtester.model.testdata.impl.TestDataImpl <em>Test Data</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.impl.TestDataImpl
		 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.impl.TestdataPackageImpl#getTestData()
		 * @generated
		 */
		EClass TEST_DATA = eINSTANCE.getTestData();

		/**
		 * The meta object literal for the '<em><b>Test Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEST_DATA__TEST_NAME = eINSTANCE.getTestData_TestName();

		/**
		 * The meta object literal for the '<em><b>Event</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_DATA__EVENT = eINSTANCE.getTestData_Event();

		/**
		 * The meta object literal for the '<em><b>Test Intstance</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEST_DATA__TEST_INTSTANCE = eINSTANCE.getTestData_TestIntstance();

		/**
		 * The meta object literal for the '<em><b>Event Outputs</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_DATA__EVENT_OUTPUTS = eINSTANCE.getTestData_EventOutputs();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_DATA__VALUES = eINSTANCE.getTestData_Values();

		/**
		 * The meta object literal for the '<em><b>Results</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_DATA__RESULTS = eINSTANCE.getTestData_Results();

		/**
		 * The meta object literal for the '<em><b>Line</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEST_DATA__LINE = eINSTANCE.getTestData_Line();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_DATA__TYPE = eINSTANCE.getTestData_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.fbtester.model.testdata.impl.ValuedVarDeclImpl <em>Valued Var Decl</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.impl.ValuedVarDeclImpl
		 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.impl.TestdataPackageImpl#getValuedVarDecl()
		 * @generated
		 */
		EClass VALUED_VAR_DECL = eINSTANCE.getValuedVarDecl();

		/**
		 * The meta object literal for the '<em><b>Var Declaration</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VALUED_VAR_DECL__VAR_DECLARATION = eINSTANCE.getValuedVarDecl_VarDeclaration();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALUED_VAR_DECL__VALUE = eINSTANCE.getValuedVarDecl_Value();

	}

} //TestdataPackage
