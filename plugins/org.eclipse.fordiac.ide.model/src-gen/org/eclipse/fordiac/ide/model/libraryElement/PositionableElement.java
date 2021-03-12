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

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;

/** <!-- begin-user-doc --> A representation of the model object '<em><b>Positionable Element</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.PositionableElement#getPosition <em>Position</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getPositionableElement()
 * @model
 * @generated */
public interface PositionableElement extends EObject {
	/** Returns the value of the '<em><b>Position</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Position</em>' reference.
	 * @see #setPosition(Position)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getPositionableElement_Position()
	 * @model required="true"
	 * @generated */
	Position getPosition();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.PositionableElement#getPosition
	 * <em>Position</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Position</em>' reference.
	 * @see #getPosition()
	 * @generated */
	void setPosition(Position value);

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model xDataType="org.eclipse.emf.ecore.xml.type.Int" xRequired="true"
	 *        yDataType="org.eclipse.emf.ecore.xml.type.Int" yRequired="true"
	 * @generated */
	void updatePosition(int x, int y);

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model newPosDataType="org.eclipse.fordiac.ide.model.libraryElement.Point" newPosRequired="true"
	 * @generated */
	void updatePosition(Point newPos);

} // PositionableElement
