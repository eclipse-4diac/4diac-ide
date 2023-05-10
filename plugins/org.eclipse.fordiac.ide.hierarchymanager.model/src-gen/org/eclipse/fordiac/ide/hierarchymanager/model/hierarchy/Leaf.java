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
package org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Leaf</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Leaf#getRef <em>Ref</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Leaf#getContainerFileName <em>Container File Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.HierarchyPackage#getLeaf()
 * @model
 * @generated
 */
public interface Leaf extends Node {
	/**
	 * Returns the value of the '<em><b>Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ref</em>' attribute.
	 * @see #setRef(String)
	 * @see org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.HierarchyPackage#getLeaf_Ref()
	 * @model
	 * @generated
	 */
	String getRef();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Leaf#getRef <em>Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ref</em>' attribute.
	 * @see #getRef()
	 * @generated
	 */
	void setRef(String value);

	/**
	 * Returns the value of the '<em><b>Container File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Container File Name</em>' attribute.
	 * @see #setContainerFileName(String)
	 * @see org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.HierarchyPackage#getLeaf_ContainerFileName()
	 * @model
	 * @generated
	 */
	String getContainerFileName();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Leaf#getContainerFileName <em>Container File Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Container File Name</em>' attribute.
	 * @see #getContainerFileName()
	 * @generated
	 */
	void setContainerFileName(String value);

} // Leaf
