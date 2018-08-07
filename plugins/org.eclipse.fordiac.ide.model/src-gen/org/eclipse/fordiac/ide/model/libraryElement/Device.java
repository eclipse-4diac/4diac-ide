/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Device</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Device#getResource <em>Resource</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Device#getProfile <em>Profile</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Device#getInConnections <em>In Connections</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getDevice()
 * @model
 * @generated
 */
public interface Device extends TypedConfigureableObject, PositionableElement, ColorizableElement, IVarElement {
	/**
	 * Returns the value of the '<em><b>Resource</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.Resource}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.libraryElement.Resource#getDevice <em>Device</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resource</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resource</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getDevice_Resource()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Resource#getDevice
	 * @model opposite="device" containment="true"
	 *        extendedMetaData="kind='element' name='Resource' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Resource> getResource();

	/**
	 * Returns the value of the '<em><b>Profile</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Profile</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Profile</em>' attribute.
	 * @see #setProfile(String)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getDevice_Profile()
	 * @model
	 * @generated
	 */
	String getProfile();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Device#getProfile <em>Profile</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Profile</em>' attribute.
	 * @see #getProfile()
	 * @generated
	 */
	void setProfile(String value);

	/**
	 * Returns the value of the '<em><b>In Connections</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.Link}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.libraryElement.Link#getDevice <em>Device</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>In Connections</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>In Connections</em>' reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getDevice_InConnections()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Link#getDevice
	 * @model opposite="device"
	 * @generated
	 */
	EList<Link> getInConnections();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return org.eclipse.fordiac.ide.model.Annotations.getAutomationSystem(this);'"
	 * @generated
	 */
	AutomationSystem getAutomationSystem();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return org.eclipse.fordiac.ide.model.Annotations.getSystemConfiguration(this);'"
	 * @generated
	 */
	SystemConfiguration getSystemConfiguration();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='//this cannot be moved to the annotation class because there we don\'t have the super access!!!\r\norg.eclipse.fordiac.ide.model.libraryElement.LibraryElement type = super.getType();\r\nif(type instanceof DeviceType){\r\n\treturn (DeviceType) type; \r\n}\r\nreturn null;'"
	 * @generated
	 */
	@Override
	DeviceType getType();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model nameRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return org.eclipse.fordiac.ide.model.Annotations.getResourceNamed(this, name);'"
	 * @generated
	 */
	Resource getResourceNamed(String name);

} // Device
