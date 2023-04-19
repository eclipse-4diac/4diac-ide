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
package org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class HierachPackageFactoryImpl extends EFactoryImpl implements HierachPackageFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static HierachPackageFactory init() {
		try {
			HierachPackageFactory theHierachPackageFactory = (HierachPackageFactory)EPackage.Registry.INSTANCE.getEFactory(HierachPackagePackage.eNS_URI);
			if (theHierachPackageFactory != null) {
				return theHierachPackageFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new HierachPackageFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HierachPackageFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case HierachPackagePackage.LEVEL: return createLevel();
			case HierachPackagePackage.ROOT_LEVEL: return createRootLevel();
			case HierachPackagePackage.NODE: return createNode();
			case HierachPackagePackage.LEAF: return createLeaf();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Level createLevel() {
		LevelImpl level = new LevelImpl();
		return level;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RootLevel createRootLevel() {
		RootLevelImpl rootLevel = new RootLevelImpl();
		return rootLevel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Node createNode() {
		NodeImpl node = new NodeImpl();
		return node;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Leaf createLeaf() {
		LeafImpl leaf = new LeafImpl();
		return leaf;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public HierachPackagePackage getHierachPackagePackage() {
		return (HierachPackagePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static HierachPackagePackage getPackage() {
		return HierachPackagePackage.eINSTANCE;
	}

} //HierachPackageFactoryImpl
