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
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;

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
 * @see org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionFactory
 * @model kind="package"
 * @generated
 */
public interface STFunctionPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "stfunction"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/fordiac/ide/structuredtextfunctioneditor/STFunction"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "stfunction"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	STFunctionPackage eINSTANCE = org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.impl.STFunctionPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.impl.STFunctionSourceImpl <em>Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.impl.STFunctionSourceImpl
	 * @see org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.impl.STFunctionPackageImpl#getSTFunctionSource()
	 * @generated
	 */
	int ST_FUNCTION_SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FUNCTION_SOURCE__COMMENTS = STCorePackage.ST_SOURCE__COMMENTS;

	/**
	 * The feature id for the '<em><b>Functions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FUNCTION_SOURCE__FUNCTIONS = STCorePackage.ST_SOURCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FUNCTION_SOURCE_FEATURE_COUNT = STCorePackage.ST_SOURCE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.impl.STFunctionImpl <em>ST Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.impl.STFunctionImpl
	 * @see org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.impl.STFunctionPackageImpl#getSTFunction()
	 * @generated
	 */
	int ST_FUNCTION = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FUNCTION__NAME = LibraryElementPackage.ICALLABLE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FUNCTION__COMMENT = LibraryElementPackage.ICALLABLE__COMMENT;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FUNCTION__RETURN_TYPE = LibraryElementPackage.ICALLABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Var Declarations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FUNCTION__VAR_DECLARATIONS = LibraryElementPackage.ICALLABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Code</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FUNCTION__CODE = LibraryElementPackage.ICALLABLE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>ST Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FUNCTION_FEATURE_COUNT = LibraryElementPackage.ICALLABLE_FEATURE_COUNT + 3;


	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Source</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource
	 * @generated
	 */
	EClass getSTFunctionSource();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource#getFunctions <em>Functions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Functions</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource#getFunctions()
	 * @see #getSTFunctionSource()
	 * @generated
	 */
	EReference getSTFunctionSource_Functions();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction <em>ST Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Function</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction
	 * @generated
	 */
	EClass getSTFunction();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction#getReturnType <em>Return Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Return Type</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction#getReturnType()
	 * @see #getSTFunction()
	 * @generated
	 */
	EReference getSTFunction_ReturnType();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction#getVarDeclarations <em>Var Declarations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Var Declarations</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction#getVarDeclarations()
	 * @see #getSTFunction()
	 * @generated
	 */
	EReference getSTFunction_VarDeclarations();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction#getCode <em>Code</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Code</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction#getCode()
	 * @see #getSTFunction()
	 * @generated
	 */
	EReference getSTFunction_Code();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	STFunctionFactory getSTFunctionFactory();

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
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.impl.STFunctionSourceImpl <em>Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.impl.STFunctionSourceImpl
		 * @see org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.impl.STFunctionPackageImpl#getSTFunctionSource()
		 * @generated
		 */
		EClass ST_FUNCTION_SOURCE = eINSTANCE.getSTFunctionSource();

		/**
		 * The meta object literal for the '<em><b>Functions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_FUNCTION_SOURCE__FUNCTIONS = eINSTANCE.getSTFunctionSource_Functions();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.impl.STFunctionImpl <em>ST Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.impl.STFunctionImpl
		 * @see org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.impl.STFunctionPackageImpl#getSTFunction()
		 * @generated
		 */
		EClass ST_FUNCTION = eINSTANCE.getSTFunction();

		/**
		 * The meta object literal for the '<em><b>Return Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_FUNCTION__RETURN_TYPE = eINSTANCE.getSTFunction_ReturnType();

		/**
		 * The meta object literal for the '<em><b>Var Declarations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_FUNCTION__VAR_DECLARATIONS = eINSTANCE.getSTFunction_VarDeclarations();

		/**
		 * The meta object literal for the '<em><b>Code</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_FUNCTION__CODE = eINSTANCE.getSTFunction_Code();

	}

} //STFunctionPackage
