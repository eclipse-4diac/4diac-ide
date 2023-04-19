/**
 * *******************************************************************************
 *  Copyright (c) 2023 Primetals Technologies Austria GmbH
 * 
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0 which is available at
 *  http://www.eclipse.org/legal/epl-2.0.
 * 
 *  SPDX-License-Identifier: EPL-2.0
 * 
 *  Contributors:
 *    Michael Oberlehner , Bianca Wiesmayr- initial API and implementation and/or initial documentation
 *  *******************************************************************************
 */
package org.eclipse.fordiac.ide.hierachymanager.hierachyPackage;

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
 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.HierachPackageFactory
 * @model kind="package"
 * @generated
 */
public interface HierachPackagePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "hierachyPackage";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "org.eclipse.fordiac.ide.hierachymanager";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "hierachyPackage";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	HierachPackagePackage eINSTANCE = org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.impl.HierachPackagePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.impl.NodeImpl <em>Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.impl.NodeImpl
	 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.impl.HierachPackagePackageImpl#getNode()
	 * @generated
	 */
	int NODE = 2;

	/**
	 * The number of structural features of the '<em>Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.impl.LevelImpl <em>Level</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.impl.LevelImpl
	 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.impl.HierachPackagePackageImpl#getLevel()
	 * @generated
	 */
	int LEVEL = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEVEL__NAME = NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEVEL__COMMENT = NODE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Children</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEVEL__CHILDREN = NODE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Level</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEVEL_FEATURE_COUNT = NODE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.impl.RootLevelImpl <em>Root Level</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.impl.RootLevelImpl
	 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.impl.HierachPackagePackageImpl#getRootLevel()
	 * @generated
	 */
	int ROOT_LEVEL = 1;

	/**
	 * The feature id for the '<em><b>Levels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_LEVEL__LEVELS = 0;

	/**
	 * The number of structural features of the '<em>Root Level</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_LEVEL_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.impl.LeafImpl <em>Leaf</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.impl.LeafImpl
	 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.impl.HierachPackagePackageImpl#getLeaf()
	 * @generated
	 */
	int LEAF = 3;

	/**
	 * The feature id for the '<em><b>Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEAF__REF = NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Leaf</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEAF_FEATURE_COUNT = NODE_FEATURE_COUNT + 1;


	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.Level <em>Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Level</em>'.
	 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.Level
	 * @generated
	 */
	EClass getLevel();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.Level#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.Level#getName()
	 * @see #getLevel()
	 * @generated
	 */
	EAttribute getLevel_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.Level#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.Level#getComment()
	 * @see #getLevel()
	 * @generated
	 */
	EAttribute getLevel_Comment();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.Level#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Children</em>'.
	 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.Level#getChildren()
	 * @see #getLevel()
	 * @generated
	 */
	EReference getLevel_Children();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.RootLevel <em>Root Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Root Level</em>'.
	 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.RootLevel
	 * @generated
	 */
	EClass getRootLevel();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.RootLevel#getLevels <em>Levels</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Levels</em>'.
	 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.RootLevel#getLevels()
	 * @see #getRootLevel()
	 * @generated
	 */
	EReference getRootLevel_Levels();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.Node <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node</em>'.
	 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.Node
	 * @generated
	 */
	EClass getNode();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.Leaf <em>Leaf</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Leaf</em>'.
	 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.Leaf
	 * @generated
	 */
	EClass getLeaf();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.Leaf#getRef <em>Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ref</em>'.
	 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.Leaf#getRef()
	 * @see #getLeaf()
	 * @generated
	 */
	EAttribute getLeaf_Ref();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	HierachPackageFactory getHierachPackageFactory();

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
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.impl.LevelImpl <em>Level</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.impl.LevelImpl
		 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.impl.HierachPackagePackageImpl#getLevel()
		 * @generated
		 */
		EClass LEVEL = eINSTANCE.getLevel();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LEVEL__NAME = eINSTANCE.getLevel_Name();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LEVEL__COMMENT = eINSTANCE.getLevel_Comment();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LEVEL__CHILDREN = eINSTANCE.getLevel_Children();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.impl.RootLevelImpl <em>Root Level</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.impl.RootLevelImpl
		 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.impl.HierachPackagePackageImpl#getRootLevel()
		 * @generated
		 */
		EClass ROOT_LEVEL = eINSTANCE.getRootLevel();

		/**
		 * The meta object literal for the '<em><b>Levels</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_LEVEL__LEVELS = eINSTANCE.getRootLevel_Levels();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.impl.NodeImpl <em>Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.impl.NodeImpl
		 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.impl.HierachPackagePackageImpl#getNode()
		 * @generated
		 */
		EClass NODE = eINSTANCE.getNode();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.impl.LeafImpl <em>Leaf</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.impl.LeafImpl
		 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.impl.HierachPackagePackageImpl#getLeaf()
		 * @generated
		 */
		EClass LEAF = eINSTANCE.getLeaf();

		/**
		 * The meta object literal for the '<em><b>Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LEAF__REF = eINSTANCE.getLeaf_Ref();

	}

} //HierachPackagePackage
