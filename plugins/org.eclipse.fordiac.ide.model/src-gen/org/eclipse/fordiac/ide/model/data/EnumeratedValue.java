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

import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Enumerated Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.data.EnumeratedValue#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.data.DataPackage#getEnumeratedValue()
 * @model
 * @generated
 */
public interface EnumeratedValue extends INamedElement {

	/**
	 * Returns the value of the '<em><b>Type</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.data.EnumeratedType#getEnumeratedValues <em>Enumerated Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' container reference.
	 * @see #setType(EnumeratedType)
	 * @see org.eclipse.fordiac.ide.model.data.DataPackage#getEnumeratedValue_Type()
	 * @see org.eclipse.fordiac.ide.model.data.EnumeratedType#getEnumeratedValues
	 * @model opposite="enumeratedValues" transient="false"
	 * @generated
	 */
	EnumeratedType getType();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.data.EnumeratedValue#getType <em>Type</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' container reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(EnumeratedType value);

} // EnumeratedValue
