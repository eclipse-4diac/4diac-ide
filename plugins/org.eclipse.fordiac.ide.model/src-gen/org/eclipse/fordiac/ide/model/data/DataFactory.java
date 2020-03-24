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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.model.data.DataPackage
 * @generated
 */
public interface DataFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	DataFactory eINSTANCE = org.eclipse.fordiac.ide.model.data.impl.DataFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Any Derived Type</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Any Derived Type</em>'.
	 * @generated
	 */
	AnyDerivedType createAnyDerivedType();

	/**
	 * Returns a new object of class '<em>Array Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Array Type</em>'.
	 * @generated
	 */
	ArrayType createArrayType();

	/**
	 * Returns a new object of class '<em>Directly Derived Type</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Directly Derived Type</em>'.
	 * @generated
	 */
	DirectlyDerivedType createDirectlyDerivedType();

	/**
	 * Returns a new object of class '<em>Enumerated Type</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Enumerated Type</em>'.
	 * @generated
	 */
	EnumeratedType createEnumeratedType();

	/**
	 * Returns a new object of class '<em>Enumerated Value</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Enumerated Value</em>'.
	 * @generated
	 */
	EnumeratedValue createEnumeratedValue();

	/**
	 * Returns a new object of class '<em>Structured Type</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Structured Type</em>'.
	 * @generated
	 */
	StructuredType createStructuredType();

	/**
	 * Returns a new object of class '<em>Subrange</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Subrange</em>'.
	 * @generated
	 */
	Subrange createSubrange();

	/**
	 * Returns a new object of class '<em>Subrange Type</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Subrange Type</em>'.
	 * @generated
	 */
	SubrangeType createSubrangeType();

	/**
	 * Returns a new object of class '<em>Value Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Value Type</em>'.
	 * @generated
	 */
	ValueType createValueType();

	/**
	 * Returns a new object of class '<em>Elementary Type</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Elementary Type</em>'.
	 * @generated
	 */
	ElementaryType createElementaryType();

	/**
	 * Returns a new object of class '<em>Derived Type</em>'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Derived Type</em>'.
	 * @generated
	 */
	DerivedType createDerivedType();

	/**
	 * Returns a new object of class '<em>Event Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Event Type</em>'.
	 * @generated
	 */
	EventType createEventType();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	DataPackage getDataPackage();

} // DataFactory
