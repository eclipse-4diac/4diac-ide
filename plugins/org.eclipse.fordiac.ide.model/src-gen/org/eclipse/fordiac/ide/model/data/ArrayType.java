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

/** <!-- begin-user-doc --> A representation of the model object '<em><b>Array Type</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.data.ArrayType#getSubranges <em>Subranges</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.data.ArrayType#getInitialValues <em>Initial Values</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.data.ArrayType#getBaseType <em>Base Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.data.DataPackage#getArrayType()
 * @model
 * @generated */
public interface ArrayType extends AnyDerivedType {
	/** Returns the value of the '<em><b>Subranges</b></em>' containment reference list. The list contents are of type
	 * {@link org.eclipse.fordiac.ide.model.data.Subrange}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subranges</em>' containment reference list isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Subranges</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.data.DataPackage#getArrayType_Subranges()
	 * @model containment="true" required="true" extendedMetaData="kind='element' name='Subrange'
	 *        namespace='##targetNamespace'"
	 * @generated */
	EList<Subrange> getSubranges();

	/** Returns the value of the '<em><b>Initial Values</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Initial Values</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Initial Values</em>' attribute.
	 * @see #setInitialValues(String)
	 * @see org.eclipse.fordiac.ide.model.data.DataPackage#getArrayType_InitialValues()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" extendedMetaData="kind='attribute' name='InitialValues'"
	 * @generated */
	String getInitialValues();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.model.data.ArrayType#getInitialValues <em>Initial
	 * Values</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Initial Values</em>' attribute.
	 * @see #getInitialValues()
	 * @generated */
	void setInitialValues(String value);

	/** Returns the value of the '<em><b>Base Type</b></em>' reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base Type</em>' reference isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Base Type</em>' reference.
	 * @see #setBaseType(DataType)
	 * @see org.eclipse.fordiac.ide.model.data.DataPackage#getArrayType_BaseType()
	 * @model
	 * @generated */
	DataType getBaseType();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.model.data.ArrayType#getBaseType <em>Base Type</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Base Type</em>' reference.
	 * @see #getBaseType()
	 * @generated */
	void setBaseType(DataType value);

} // ArrayType
