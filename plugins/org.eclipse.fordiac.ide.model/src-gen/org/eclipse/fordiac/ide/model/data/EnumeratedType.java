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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Enumerated Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.data.EnumeratedType#getEnumeratedValues <em>Enumerated Values</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.data.DataPackage#getEnumeratedType()
 * @model
 * @generated
 */
public interface EnumeratedType extends AnyDerivedType {
	/**
	 * Returns the value of the '<em><b>Enumerated Values</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.data.EnumeratedValue}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enumerated Values</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.data.DataPackage#getEnumeratedType_EnumeratedValues()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='EnumeratedValue' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<EnumeratedValue> getEnumeratedValues();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isAssignableFrom(DataType other);

} // EnumeratedType
