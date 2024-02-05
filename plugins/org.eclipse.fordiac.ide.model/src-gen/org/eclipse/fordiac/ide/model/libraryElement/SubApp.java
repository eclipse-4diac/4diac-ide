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
 * A representation of the model object '<em><b>Sub App</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.SubApp#getSubAppNetwork <em>Sub App Network</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.SubApp#getWidth <em>Width</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.SubApp#getHeight <em>Height</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.SubApp#isLocked <em>Locked</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getSubApp()
 * @model
 * @generated
 */
public interface SubApp extends FBNetworkElement {
	/**
	 * Returns the value of the '<em><b>Sub App Network</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub App Network</em>' containment reference.
	 * @see #setSubAppNetwork(FBNetwork)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getSubApp_SubAppNetwork()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	FBNetwork getSubAppNetwork();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.SubApp#getSubAppNetwork <em>Sub App Network</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sub App Network</em>' containment reference.
	 * @see #getSubAppNetwork()
	 * @generated
	 */
	void setSubAppNetwork(FBNetwork value);

	/**
	 * Returns the value of the '<em><b>Width</b></em>' attribute.
	 * The default value is <code>"200"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Width</em>' attribute.
	 * @see #setWidth(int)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getSubApp_Width()
	 * @model default="200" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 * @generated
	 */
	int getWidth();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.SubApp#getWidth <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Width</em>' attribute.
	 * @see #getWidth()
	 * @generated
	 */
	void setWidth(int value);

	/**
	 * Returns the value of the '<em><b>Height</b></em>' attribute.
	 * The default value is <code>"100"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Height</em>' attribute.
	 * @see #setHeight(int)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getSubApp_Height()
	 * @model default="100" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 * @generated
	 */
	int getHeight();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.SubApp#getHeight <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Height</em>' attribute.
	 * @see #getHeight()
	 * @generated
	 */
	void setHeight(int value);

	/**
	 * Returns the value of the '<em><b>Locked</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Locked</em>' attribute.
	 * @see #setLocked(boolean)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getSubApp_Locked()
	 * @model default="false" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isLocked();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.SubApp#isLocked <em>Locked</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Locked</em>' attribute.
	 * @see #isLocked()
	 * @generated
	 */
	void setLocked(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	SubAppType getType();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isUnfolded();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isTyped();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * if it is an untyped subapp and the fbnetwork is not yet loaded load it from the type
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	FBNetwork loadSubAppNetwork();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 * @generated
	 */
	int getVisibleWidth();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 * @generated
	 */
	int getVisibleHeight();

} // SubApp
