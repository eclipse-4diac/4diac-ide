/**
 * ******************************************************************************
 * Copyright (c) 2012, 2013, 2015 - 2017 Profactor GmbH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz
 *      - initial API and implementation and/or initial documentation
 * ******************************************************************************
 *
 */
package org.eclipse.fordiac.ide.deployment.monitoringbase;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Port
 * Element</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement#getFb <em>Fb</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement#getInterfaceElement <em>Interface Element</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement#getResource <em>Resource</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement#getHierarchy <em>Hierarchy</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBasePackage#getPortElement()
 * @model
 * @generated
 */
public interface PortElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Fb</b></em>' reference.
	 * <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>Fb</em>' reference isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fb</em>' reference.
	 * @see #setFb(FBNetworkElement)
	 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBasePackage#getPortElement_Fb()
	 * @model
	 * @generated
	 */
	FBNetworkElement getFb();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement#getFb <em>Fb</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fb</em>' reference.
	 * @see #getFb()
	 * @generated
	 */
	void setFb(FBNetworkElement value);

	/**
	 * Returns the value of the '<em><b>Interface Element</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Interface Element</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Interface Element</em>' reference.
	 * @see #setInterfaceElement(IInterfaceElement)
	 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBasePackage#getPortElement_InterfaceElement()
	 * @model
	 * @generated
	 */
	IInterfaceElement getInterfaceElement();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement#getInterfaceElement <em>Interface Element</em>}' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Interface Element</em>' reference.
	 * @see #getInterfaceElement()
	 * @generated
	 */
	void setInterfaceElement(IInterfaceElement value);

	/**
	 * Returns the value of the '<em><b>Resource</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resource</em>' reference isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Resource</em>' reference.
	 * @see #setResource(Resource)
	 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBasePackage#getPortElement_Resource()
	 * @model
	 * @generated
	 */
	Resource getResource();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement#getResource <em>Resource</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resource</em>' reference.
	 * @see #getResource()
	 * @generated
	 */
	void setResource(Resource value);

	/**
	 * Returns the value of the '<em><b>Hierarchy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hierarchy</em>' attribute list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hierarchy</em>' attribute.
	 * @see #setHierarchy(String)
	 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBasePackage#getPortElement_Hierarchy()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getHierarchy();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement#getHierarchy <em>Hierarchy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hierarchy</em>' attribute.
	 * @see #getHierarchy()
	 * @generated
	 */
	void setHierarchy(String value);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getPortString();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	Device getDevice();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	AutomationSystem getSystem();

} // PortElement
