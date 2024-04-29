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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Error Marker FBN Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement#getRepairedElement <em>Repaired Element</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getErrorMarkerFBNElement()
 * @model
 * @generated
 */
public interface ErrorMarkerFBNElement extends FBNetworkElement {
	/**
	 * Returns the value of the '<em><b>Repaired Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Repaired Element</em>' reference.
	 * @see #setRepairedElement(FBNetworkElement)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getErrorMarkerFBNElement_RepairedElement()
	 * @model
	 * @generated
	 */
	FBNetworkElement getRepairedElement();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement#getRepairedElement <em>Repaired Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Repaired Element</em>' reference.
	 * @see #getRepairedElement()
	 * @generated
	 */
	void setRepairedElement(FBNetworkElement value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.Double"
	 * @generated
	 */
	double getWidth();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.Double"
	 * @generated
	 */
	double getHeight();

} // ErrorMarkerFBNElement
