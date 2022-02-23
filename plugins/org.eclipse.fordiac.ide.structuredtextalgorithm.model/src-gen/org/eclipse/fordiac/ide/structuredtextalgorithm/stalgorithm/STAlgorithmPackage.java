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
package org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
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
 * @see org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmFactory
 * @model kind="package"
 * @generated
 */
public interface STAlgorithmPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "stalgorithm"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/fordiac/ide/structuredtextalgorithm/STAlgorithm"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "stalgorithm"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	STAlgorithmPackage eINSTANCE = org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.impl.STAlgorithmPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.impl.STAlgorithmImpl <em>ST Algorithm</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.impl.STAlgorithmImpl
	 * @see org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.impl.STAlgorithmPackageImpl#getSTAlgorithm()
	 * @generated
	 */
	int ST_ALGORITHM = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ALGORITHM__NAME = 0;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ALGORITHM__BODY = 1;

	/**
	 * The number of structural features of the '<em>ST Algorithm</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ALGORITHM_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.impl.STAlgorithmBodyImpl <em>Body</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.impl.STAlgorithmBodyImpl
	 * @see org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.impl.STAlgorithmPackageImpl#getSTAlgorithmBody()
	 * @generated
	 */
	int ST_ALGORITHM_BODY = 1;

	/**
	 * The feature id for the '<em><b>Var Temp Declarations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ALGORITHM_BODY__VAR_TEMP_DECLARATIONS = 0;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ALGORITHM_BODY__STATEMENTS = 1;

	/**
	 * The number of structural features of the '<em>Body</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ALGORITHM_BODY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.impl.STAlgorithmSourceImpl <em>Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.impl.STAlgorithmSourceImpl
	 * @see org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.impl.STAlgorithmPackageImpl#getSTAlgorithmSource()
	 * @generated
	 */
	int ST_ALGORITHM_SOURCE = 2;

	/**
	 * The feature id for the '<em><b>Algorithms</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ALGORITHM_SOURCE__ALGORITHMS = STCorePackage.ST_SOURCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ALGORITHM_SOURCE_FEATURE_COUNT = STCorePackage.ST_SOURCE_FEATURE_COUNT + 1;


	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm <em>ST Algorithm</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Algorithm</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm
	 * @generated
	 */
	EClass getSTAlgorithm();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm#getName()
	 * @see #getSTAlgorithm()
	 * @generated
	 */
	EAttribute getSTAlgorithm_Name();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm#getBody()
	 * @see #getSTAlgorithm()
	 * @generated
	 */
	EReference getSTAlgorithm_Body();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Body</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmBody
	 * @generated
	 */
	EClass getSTAlgorithmBody();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmBody#getVarTempDeclarations <em>Var Temp Declarations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Var Temp Declarations</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmBody#getVarTempDeclarations()
	 * @see #getSTAlgorithmBody()
	 * @generated
	 */
	EReference getSTAlgorithmBody_VarTempDeclarations();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmBody#getStatements <em>Statements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Statements</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmBody#getStatements()
	 * @see #getSTAlgorithmBody()
	 * @generated
	 */
	EReference getSTAlgorithmBody_Statements();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Source</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource
	 * @generated
	 */
	EClass getSTAlgorithmSource();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource#getAlgorithms <em>Algorithms</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Algorithms</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource#getAlgorithms()
	 * @see #getSTAlgorithmSource()
	 * @generated
	 */
	EReference getSTAlgorithmSource_Algorithms();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	STAlgorithmFactory getSTAlgorithmFactory();

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
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.impl.STAlgorithmImpl <em>ST Algorithm</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.impl.STAlgorithmImpl
		 * @see org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.impl.STAlgorithmPackageImpl#getSTAlgorithm()
		 * @generated
		 */
		EClass ST_ALGORITHM = eINSTANCE.getSTAlgorithm();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ST_ALGORITHM__NAME = eINSTANCE.getSTAlgorithm_Name();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_ALGORITHM__BODY = eINSTANCE.getSTAlgorithm_Body();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.impl.STAlgorithmBodyImpl <em>Body</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.impl.STAlgorithmBodyImpl
		 * @see org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.impl.STAlgorithmPackageImpl#getSTAlgorithmBody()
		 * @generated
		 */
		EClass ST_ALGORITHM_BODY = eINSTANCE.getSTAlgorithmBody();

		/**
		 * The meta object literal for the '<em><b>Var Temp Declarations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_ALGORITHM_BODY__VAR_TEMP_DECLARATIONS = eINSTANCE.getSTAlgorithmBody_VarTempDeclarations();

		/**
		 * The meta object literal for the '<em><b>Statements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_ALGORITHM_BODY__STATEMENTS = eINSTANCE.getSTAlgorithmBody_Statements();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.impl.STAlgorithmSourceImpl <em>Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.impl.STAlgorithmSourceImpl
		 * @see org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.impl.STAlgorithmPackageImpl#getSTAlgorithmSource()
		 * @generated
		 */
		EClass ST_ALGORITHM_SOURCE = eINSTANCE.getSTAlgorithmSource();

		/**
		 * The meta object literal for the '<em><b>Algorithms</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_ALGORITHM_SOURCE__ALGORITHMS = eINSTANCE.getSTAlgorithmSource_Algorithms();

	}

} //STAlgorithmPackage
