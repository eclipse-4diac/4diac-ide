/********************************************************************************
 * Copyright (c) 2008, 2010, 2012 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Monika Wenger, Martin Jobst
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.data;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Enumerated Type</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.data.EnumeratedType#getEnumeratedValue
 * <em>Enumerated Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.data.DataPackage#getEnumeratedType()
 * @model
 * @generated
 */
public interface EnumeratedType extends ValueType {
	/**
	 * Returns the value of the '<em><b>Enumerated Value</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link org.eclipse.fordiac.ide.model.data.EnumeratedValue}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enumerated Value</em>' containment reference list
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Enumerated Value</em>' containment reference
	 *         list.
	 * @see org.eclipse.fordiac.ide.model.data.DataPackage#getEnumeratedType_EnumeratedValue()
	 * @model containment="true" required="true" extendedMetaData="kind='element'
	 *        name='EnumeratedValue' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<EnumeratedValue> getEnumeratedValue();

} // EnumeratedType
