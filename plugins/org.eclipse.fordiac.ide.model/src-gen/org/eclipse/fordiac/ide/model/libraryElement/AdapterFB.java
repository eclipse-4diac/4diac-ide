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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Adapter
 * FB</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterFB#getAdapterDecl
 * <em>Adapter Decl</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getAdapterFB()
 * @model
 * @generated
 */
public interface AdapterFB extends FB {
	/**
	 * Returns the value of the '<em><b>Adapter Decl</b></em>' reference. It is
	 * bidirectional and its opposite is
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration#getAdapterFB
	 * <em>Adapter FB</em>}'. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Adapter Decl</em>' reference isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Adapter Decl</em>' reference.
	 * @see #setAdapterDecl(AdapterDeclaration)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getAdapterFB_AdapterDecl()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration#getAdapterFB
	 * @model opposite="adapterFB" required="true"
	 * @generated
	 */
	AdapterDeclaration getAdapterDecl();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterFB#getAdapterDecl
	 * <em>Adapter Decl</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Adapter Decl</em>' reference.
	 * @see #getAdapterDecl()
	 * @generated
	 */
	void setAdapterDecl(AdapterDeclaration value);

	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Plug</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isPlug();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isSocket();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model kind="operation" required="true"
	 * @generated
	 */
	@Override
	FBType getType();

} // AdapterFB
