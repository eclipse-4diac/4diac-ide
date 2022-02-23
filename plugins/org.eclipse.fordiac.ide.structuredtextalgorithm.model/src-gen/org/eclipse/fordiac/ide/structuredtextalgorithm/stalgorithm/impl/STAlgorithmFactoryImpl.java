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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class STAlgorithmFactoryImpl extends EFactoryImpl implements STAlgorithmFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static STAlgorithmFactory init() {
		try {
			STAlgorithmFactory theSTAlgorithmFactory = (STAlgorithmFactory)EPackage.Registry.INSTANCE.getEFactory(STAlgorithmPackage.eNS_URI);
			if (theSTAlgorithmFactory != null) {
				return theSTAlgorithmFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new STAlgorithmFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public STAlgorithmFactoryImpl() {
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
			case STAlgorithmPackage.ST_ALGORITHM: return createSTAlgorithm();
			case STAlgorithmPackage.ST_ALGORITHM_BODY: return createSTAlgorithmBody();
			case STAlgorithmPackage.ST_ALGORITHM_SOURCE: return createSTAlgorithmSource();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STAlgorithm createSTAlgorithm() {
		STAlgorithmImpl stAlgorithm = new STAlgorithmImpl();
		return stAlgorithm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STAlgorithmBody createSTAlgorithmBody() {
		STAlgorithmBodyImpl stAlgorithmBody = new STAlgorithmBodyImpl();
		return stAlgorithmBody;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STAlgorithmSource createSTAlgorithmSource() {
		STAlgorithmSourceImpl stAlgorithmSource = new STAlgorithmSourceImpl();
		return stAlgorithmSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STAlgorithmPackage getSTAlgorithmPackage() {
		return (STAlgorithmPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static STAlgorithmPackage getPackage() {
		return STAlgorithmPackage.eINSTANCE;
	}

} //STAlgorithmFactoryImpl
