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
package org.eclipse.fordiac.ide.model.data;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Directly Derived Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.data.DirectlyDerivedType#getBaseType <em>Base Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.data.DirectlyDerivedType#getInitialValue <em>Initial Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.data.DataPackage#getDirectlyDerivedType()
 * @model
 * @generated
 */
public interface DirectlyDerivedType extends AnyDerivedType {
	/**
	 * Returns the value of the '<em><b>Base Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base Type</em>' reference.
	 * @see #setBaseType(DataType)
	 * @see org.eclipse.fordiac.ide.model.data.DataPackage#getDirectlyDerivedType_BaseType()
	 * @model required="true"
	 * @generated
	 */
	DataType getBaseType();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.data.DirectlyDerivedType#getBaseType <em>Base Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base Type</em>' reference.
	 * @see #getBaseType()
	 * @generated
	 */
	void setBaseType(DataType value);

	/**
	 * Returns the value of the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initial Value</em>' attribute.
	 * @see #setInitialValue(String)
	 * @see org.eclipse.fordiac.ide.model.data.DataPackage#getDirectlyDerivedType_InitialValue()
	 * @model
	 * @generated
	 */
	String getInitialValue();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.data.DirectlyDerivedType#getInitialValue <em>Initial Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initial Value</em>' attribute.
	 * @see #getInitialValue()
	 * @generated
	 */
	void setInitialValue(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isAssignableFrom(DataType other);

} // DirectlyDerivedType
