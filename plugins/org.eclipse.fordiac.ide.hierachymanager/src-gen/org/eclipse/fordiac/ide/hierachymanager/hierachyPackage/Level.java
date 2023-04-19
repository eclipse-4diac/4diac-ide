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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Level</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.Level#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.Level#getComment <em>Comment</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.Level#getChildren <em>Children</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.HierachPackagePackage#getLevel()
 * @model
 * @generated
 */
public interface Level extends Node {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.HierachPackagePackage#getLevel_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.Level#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comment</em>' attribute.
	 * @see #setComment(String)
	 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.HierachPackagePackage#getLevel_Comment()
	 * @model
	 * @generated
	 */
	String getComment();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.Level#getComment <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Comment</em>' attribute.
	 * @see #getComment()
	 * @generated
	 */
	void setComment(String value);

	/**
	 * Returns the value of the '<em><b>Children</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Children</em>' reference.
	 * @see #setChildren(Node)
	 * @see org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.HierachPackagePackage#getLevel_Children()
	 * @model
	 * @generated
	 */
	Node getChildren();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.hierachymanager.hierachyPackage.Level#getChildren <em>Children</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Children</em>' reference.
	 * @see #getChildren()
	 * @generated
	 */
	void setChildren(Node value);

} // Level
