/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2022-2023 Martin Erich Jobst
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
 * A representation of the model object '<em><b>Any String Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.data.AnyStringType#getMaxLength <em>Max Length</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.data.DataPackage#getAnyStringType()
 * @model
 * @generated
 */
public interface AnyStringType extends AnyCharsType {
	/**
	 * Returns the value of the '<em><b>Max Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Length</em>' attribute.
	 * @see #isSetMaxLength()
	 * @see #unsetMaxLength()
	 * @see #setMaxLength(int)
	 * @see org.eclipse.fordiac.ide.model.data.DataPackage#getAnyStringType_MaxLength()
	 * @model unsettable="true"
	 * @generated
	 */
	int getMaxLength();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.data.AnyStringType#getMaxLength <em>Max Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Length</em>' attribute.
	 * @see #isSetMaxLength()
	 * @see #unsetMaxLength()
	 * @see #getMaxLength()
	 * @generated
	 */
	void setMaxLength(int value);

	/**
	 * Unsets the value of the '{@link org.eclipse.fordiac.ide.model.data.AnyStringType#getMaxLength <em>Max Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMaxLength()
	 * @see #getMaxLength()
	 * @see #setMaxLength(int)
	 * @generated
	 */
	void unsetMaxLength();

	/**
	 * Returns whether the value of the '{@link org.eclipse.fordiac.ide.model.data.AnyStringType#getMaxLength <em>Max Length</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Max Length</em>' attribute is set.
	 * @see #unsetMaxLength()
	 * @see #getMaxLength()
	 * @see #setMaxLength(int)
	 * @generated
	 */
	boolean isSetMaxLength();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isAssignableFrom(DataType other);

} // AnyStringType
