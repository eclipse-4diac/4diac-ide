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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Resource</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Resource#getFBNetwork <em>FB Network</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Resource#getX <em>X</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Resource#getY <em>Y</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Resource#getDevice <em>Device</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Resource#isDeviceTypeResource <em>Device Type Resource</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getResource()
 * @model
 * @generated
 */
public interface Resource extends TypedConfigureableObject, IVarElement {
	/**
	 * Returns the value of the '<em><b>FB Network</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>FB Network</em>' containment reference
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>FB Network</em>' containment reference.
	 * @see #setFBNetwork(FBNetwork)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getResource_FBNetwork()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='FBNetwork' namespace='##targetNamespace'"
	 * @generated
	 */
	FBNetwork getFBNetwork();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Resource#getFBNetwork <em>FB Network</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>FB Network</em>' containment reference.
	 * @see #getFBNetwork()
	 * @generated
	 */
	void setFBNetwork(FBNetwork value);

	/**
	 * Returns the value of the '<em><b>X</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>X</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>X</em>' attribute.
	 * @see #setX(String)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getResource_X()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='x'"
	 * @generated
	 */
	String getX();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Resource#getX <em>X</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>X</em>' attribute.
	 * @see #getX()
	 * @generated
	 */
	void setX(String value);

	/**
	 * Returns the value of the '<em><b>Y</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Y</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Y</em>' attribute.
	 * @see #setY(String)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getResource_Y()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='y'"
	 * @generated
	 */
	String getY();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Resource#getY <em>Y</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Y</em>' attribute.
	 * @see #getY()
	 * @generated
	 */
	void setY(String value);

	/**
	 * Returns the value of the '<em><b>Device</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.libraryElement.Device#getResource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Device</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Device</em>' container reference.
	 * @see #setDevice(Device)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getResource_Device()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Device#getResource
	 * @model opposite="resource" transient="false"
	 * @generated
	 */
	Device getDevice();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Resource#getDevice <em>Device</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Device</em>' container reference.
	 * @see #getDevice()
	 * @generated
	 */
	void setDevice(Device value);

	/**
	 * Returns the value of the '<em><b>Device Type Resource</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Device Type Resource</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Device Type Resource</em>' attribute.
	 * @see #setDeviceTypeResource(boolean)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getResource_DeviceTypeResource()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isDeviceTypeResource();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Resource#isDeviceTypeResource <em>Device Type Resource</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Device Type Resource</em>' attribute.
	 * @see #isDeviceTypeResource()
	 * @generated
	 */
	void setDeviceTypeResource(boolean value);

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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='//this cannot be moved to the annotation class because there we don\'t have the super access!!!\r\norg.eclipse.fordiac.ide.model.libraryElement.LibraryElement type = super.getType();\r\nif(type instanceof ResourceType){\r\n\treturn (ResourceType) type; \r\n}\r\nreturn null;'"
	 * @generated
	 */
	@Override
	ResourceType getType();	

} // Resource
