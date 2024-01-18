/**
 * *******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.xmiexport.xmiexport;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.fordiac.ide.model.libraryElement.Attribute;

import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpression;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportAttributeValue#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportAttributeValue#getExpression <em>Expression</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportAttributeValue#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportPackage#getXMIExportAttributeValue()
 * @model
 * @generated
 */
public interface XMIExportAttributeValue extends EObject {
	/**
	 * Returns the value of the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute</em>' reference.
	 * @see #setAttribute(Attribute)
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportPackage#getXMIExportAttributeValue_Attribute()
	 * @model
	 * @generated
	 */
	Attribute getAttribute();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportAttributeValue#getAttribute <em>Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute</em>' reference.
	 * @see #getAttribute()
	 * @generated
	 */
	void setAttribute(Attribute value);

	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(STInitializerExpression)
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportPackage#getXMIExportAttributeValue_Expression()
	 * @model containment="true"
	 * @generated
	 */
	STInitializerExpression getExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportAttributeValue#getExpression <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' containment reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(STInitializerExpression value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportPackage#getXMIExportAttributeValue_Value()
	 * @model
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportAttributeValue#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

} // XMIExportAttributeValue
