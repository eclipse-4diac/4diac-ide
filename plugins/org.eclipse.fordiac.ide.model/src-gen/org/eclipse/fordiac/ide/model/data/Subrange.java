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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Subrange</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.data.Subrange#getLowerLimit <em>Lower Limit</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.data.Subrange#getUpperLimit <em>Upper Limit</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.data.DataPackage#getSubrange()
 * @model annotation="http://www.obeo.fr/dsl/dnc/archetype archetype='Description'"
 * @generated
 */
public interface Subrange extends EObject {
	/**
	 * Returns the value of the '<em><b>Lower Limit</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lower Limit</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Lower Limit</em>' attribute.
	 * @see #setLowerLimit(int)
	 * @see org.eclipse.fordiac.ide.model.data.DataPackage#getSubrange_LowerLimit()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 *        extendedMetaData="kind='attribute' name='LowerLimit'"
	 * @generated
	 */
	int getLowerLimit();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.data.Subrange#getLowerLimit <em>Lower Limit</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lower Limit</em>' attribute.
	 * @see #getLowerLimit()
	 * @generated
	 */
	void setLowerLimit(int value);

	/**
	 * Returns the value of the '<em><b>Upper Limit</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Upper Limit</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Upper Limit</em>' attribute.
	 * @see #setUpperLimit(int)
	 * @see org.eclipse.fordiac.ide.model.data.DataPackage#getSubrange_UpperLimit()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 *        extendedMetaData="kind='attribute' name='UpperLimit'"
	 * @generated
	 */
	int getUpperLimit();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.data.Subrange#getUpperLimit <em>Upper Limit</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Upper Limit</em>' attribute.
	 * @see #getUpperLimit()
	 * @generated
	 */
	void setUpperLimit(int value);

} // Subrange
