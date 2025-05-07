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
package org.eclipse.fordiac.ide.model.libraryElement;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Connection Routing Data</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData#getDx1 <em>Dx1</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData#getDx2 <em>Dx2</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData#getDy <em>Dy</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData#isNeedsValidation <em>Needs Validation</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getConnectionRoutingData()
 * @model
 * @generated
 */
public interface ConnectionRoutingData extends EObject {
	/**
	 * Returns the value of the '<em><b>Dx1</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dx1</em>' attribute.
	 * @see #setDx1(double)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getConnectionRoutingData_Dx1()
	 * @model default="0" dataType="org.eclipse.emf.ecore.xml.type.Double"
	 *        extendedMetaData="kind='attribute' name='dx1'"
	 * @generated
	 */
	double getDx1();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData#getDx1 <em>Dx1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dx1</em>' attribute.
	 * @see #getDx1()
	 * @generated
	 */
	void setDx1(double value);

	/**
	 * Returns the value of the '<em><b>Dx2</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dx2</em>' attribute.
	 * @see #setDx2(double)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getConnectionRoutingData_Dx2()
	 * @model default="0" dataType="org.eclipse.emf.ecore.xml.type.Double"
	 *        extendedMetaData="kind='attribute' name='dx2'"
	 * @generated
	 */
	double getDx2();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData#getDx2 <em>Dx2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dx2</em>' attribute.
	 * @see #getDx2()
	 * @generated
	 */
	void setDx2(double value);

	/**
	 * Returns the value of the '<em><b>Dy</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dy</em>' attribute.
	 * @see #setDy(double)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getConnectionRoutingData_Dy()
	 * @model default="0" dataType="org.eclipse.emf.ecore.xml.type.Double"
	 *        extendedMetaData="kind='attribute' name='dy'"
	 * @generated
	 */
	double getDy();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData#getDy <em>Dy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dy</em>' attribute.
	 * @see #getDy()
	 * @generated
	 */
	void setDy(double value);

	/**
	 * Returns the value of the '<em><b>Needs Validation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Needs Validation</em>' attribute.
	 * @see #setNeedsValidation(boolean)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getConnectionRoutingData_NeedsValidation()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isNeedsValidation();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData#isNeedsValidation <em>Needs Validation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Needs Validation</em>' attribute.
	 * @see #isNeedsValidation()
	 * @generated
	 */
	void setNeedsValidation(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model required="true"
	 * @generated
	 */
	boolean is1SegementData();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model required="true"
	 * @generated
	 */
	boolean is3SegementData();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model required="true"
	 * @generated
	 */
	boolean is5SegementData();

} // ConnectionRoutingData
