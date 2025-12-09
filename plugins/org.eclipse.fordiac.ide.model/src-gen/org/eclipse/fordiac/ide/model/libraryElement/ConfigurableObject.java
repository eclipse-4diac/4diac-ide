/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.model.libraryElement;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.data.DataType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Configurable Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject#getAttributes <em>Attributes</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getConfigurableObject()
 * @model
 * @generated
 */
public interface ConfigurableObject extends EObject {
	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.Attribute}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getConfigurableObject_Attributes()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<Attribute> getAttributes();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  searches for an existing Parameter with the specified parameterName. it is
	 *  NOT case sensitive! If there is no Parameter available a new one will be created. 
	 * <!-- end-model-doc -->
	 * @model attributeNameDataType="org.eclipse.emf.ecore.xml.type.String" valueDataType="org.eclipse.emf.ecore.xml.type.String" commentDataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	void setAttribute(String attributeName, DataType type, String value, String comment);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model valueDataType="org.eclipse.emf.ecore.xml.type.String" commentDataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	void setAttribute(AttributeDeclaration attributeDeclaration, String value, String comment);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model attributeNameDataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	Attribute getAttribute(String attributeName);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" attributeNameDataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getAttributeValue(String attributeName);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean" attributeNameDataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	boolean deleteAttribute(String attributeName);

} // ConfigurableObject
