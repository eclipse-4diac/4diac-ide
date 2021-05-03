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

/** <!-- begin-user-doc --> A representation of the model object '<em><b>INamed Element</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.INamedElement#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.INamedElement#getComment <em>Comment</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getINamedElement()
 * @model interface="true" abstract="true"
 * @generated */
public interface INamedElement extends EObject {
	/** Returns the value of the '<em><b>Name</b></em>' attribute. The default value is <code>""</code>. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getINamedElement_Name()
	 * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated */
	String getName();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.INamedElement#getName <em>Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated */
	void setName(String value);

	/** Returns the value of the '<em><b>Comment</b></em>' attribute. The default value is <code>""</code>. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Comment</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Comment</em>' attribute.
	 * @see #setComment(String)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getINamedElement_Comment()
	 * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated */
	String getComment();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.INamedElement#getComment
	 * <em>Comment</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Comment</em>' attribute.
	 * @see #getComment()
	 * @generated */
	void setComment(String value);

} // INamedElement
