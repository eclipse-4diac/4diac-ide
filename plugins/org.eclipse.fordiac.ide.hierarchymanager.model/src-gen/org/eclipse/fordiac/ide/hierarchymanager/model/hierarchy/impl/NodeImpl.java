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
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.impl;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.HierarchyPackage;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Node;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class NodeImpl extends MinimalEObjectImpl.Container implements Node {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HierarchyPackage.Literals.NODE;
	}

} //NodeImpl
