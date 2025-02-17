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

import java.util.Map;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.fordiac.ide.model.data.DataType;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Attribute#getAttributeDeclaration <em>Attribute Declaration</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Attribute#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Attribute#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getAttribute()
 * @model
 * @generated
 */
public interface Attribute extends ITypedElement {
	/**
	 * Returns the value of the '<em><b>Attribute Declaration</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Declaration</em>' reference.
	 * @see #setAttributeDeclaration(AttributeDeclaration)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getAttribute_AttributeDeclaration()
	 * @model
	 * @generated
	 */
	AttributeDeclaration getAttributeDeclaration();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Attribute#getAttributeDeclaration <em>Attribute Declaration</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Declaration</em>' reference.
	 * @see #getAttributeDeclaration()
	 * @generated
	 */
	void setAttributeDeclaration(AttributeDeclaration value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(DataType)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getAttribute_Type()
	 * @model
	 * @generated
	 */
	DataType getType();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Attribute#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(DataType value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getAttribute_Value()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='Value'"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Attribute#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getQualifiedName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/Ecore invariant='true'"
	 * @generated
	 */
	boolean validateName(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/Ecore invariant='true'"
	 * @generated
	 */
	boolean validateAttributeDeclaration(DiagnosticChain diagnostics, Map<Object, Object> context);

} // Attribute
