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

/** <!-- begin-user-doc --> A representation of the model object '<em><b>Adapter Event</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterEvent#getAdapterDeclaration <em>Adapter
 * Declaration</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getAdapterEvent()
 * @model
 * @generated */
public interface AdapterEvent extends Event {
	/** Returns the value of the '<em><b>Adapter Declaration</b></em>' reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Adapter Declaration</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Adapter Declaration</em>' reference.
	 * @see #setAdapterDeclaration(AdapterDeclaration)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getAdapterEvent_AdapterDeclaration()
	 * @model required="true"
	 * @generated */
	AdapterDeclaration getAdapterDeclaration();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterEvent#getAdapterDeclaration
	 * <em>Adapter Declaration</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Adapter Declaration</em>' reference.
	 * @see #getAdapterDeclaration()
	 * @generated */
	void setAdapterDeclaration(AdapterDeclaration value);

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated */
	@Override
	String getName();

} // AdapterEvent
