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

/** <!-- begin-user-doc --> A representation of the model object '<em><b>Color</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Color#getRed <em>Red</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Color#getGreen <em>Green</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Color#getBlue <em>Blue</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getColor()
 * @model
 * @generated */
public interface Color extends EObject {
	/** Returns the value of the '<em><b>Red</b></em>' attribute. The default value is <code>"1"</code>. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Red</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Red</em>' attribute.
	 * @see #setRed(int)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getColor_Red()
	 * @model default="1" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 * @generated */
	int getRed();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Color#getRed <em>Red</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Red</em>' attribute.
	 * @see #getRed()
	 * @generated */
	void setRed(int value);

	/** Returns the value of the '<em><b>Green</b></em>' attribute. The default value is <code>"34"</code>. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Green</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Green</em>' attribute.
	 * @see #setGreen(int)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getColor_Green()
	 * @model default="34" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 * @generated */
	int getGreen();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Color#getGreen <em>Green</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Green</em>' attribute.
	 * @see #getGreen()
	 * @generated */
	void setGreen(int value);

	/** Returns the value of the '<em><b>Blue</b></em>' attribute. The default value is <code>"105"</code>. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Blue</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Blue</em>' attribute.
	 * @see #setBlue(int)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getColor_Blue()
	 * @model default="105" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 * @generated */
	int getBlue();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Color#getBlue <em>Blue</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Blue</em>' attribute.
	 * @see #getBlue()
	 * @generated */
	void setBlue(int value);

} // Color
