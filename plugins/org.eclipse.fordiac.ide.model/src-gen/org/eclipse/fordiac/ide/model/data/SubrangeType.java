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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Subrange
 * Type</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.data.SubrangeType#getSubrange <em>Subrange</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.data.DataPackage#getSubrangeType()
 * @model
 * @generated
 */
public interface SubrangeType extends DerivedType {
	/**
	 * Returns the value of the '<em><b>Subrange</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subrange</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subrange</em>' containment reference.
	 * @see #setSubrange(Subrange)
	 * @see org.eclipse.fordiac.ide.model.data.DataPackage#getSubrangeType_Subrange()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='Subrange' namespace='##targetNamespace'"
	 * @generated
	 */
	Subrange getSubrange();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.data.SubrangeType#getSubrange <em>Subrange</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Subrange</em>' containment reference.
	 * @see #getSubrange()
	 * @generated
	 */
	void setSubrange(Subrange value);

} // SubrangeType
