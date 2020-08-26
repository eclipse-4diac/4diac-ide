/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Positionable Element</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.PositionableElement#getX
 * <em>X</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.PositionableElement#getY
 * <em>Y</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getPositionableElement()
 * @model
 * @generated
 */
public interface PositionableElement extends EObject {
	/**
	 * Returns the value of the '<em><b>X</b></em>' attribute. The default value is
	 * <code>"0"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>X</em>' attribute isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>X</em>' attribute.
	 * @see #setX(int)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getPositionableElement_X()
	 * @model default="0" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 * @generated
	 */
	int getX();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.PositionableElement#getX
	 * <em>X</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>X</em>' attribute.
	 * @see #getX()
	 * @generated
	 */
	void setX(int value);

	/**
	 * Returns the value of the '<em><b>Y</b></em>' attribute. The default value is
	 * <code>"0"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Y</em>' attribute isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Y</em>' attribute.
	 * @see #setY(int)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getPositionableElement_Y()
	 * @model default="0" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 * @generated
	 */
	int getY();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.PositionableElement#getY
	 * <em>Y</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Y</em>' attribute.
	 * @see #getY()
	 * @generated
	 */
	void setY(int value);

} // PositionableElement
