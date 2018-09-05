/**
 * ******************************************************************************
 *  * Copyright (c) 2012, 2013, 2015 - 2017 Profactor GmbH, fortiss GmbH
 *  * 
 *  * All rights reserved. This program and the accompanying materials
 *  * are made available under the terms of the Eclipse Public License v1.0
 *  * which accompanies this distribution, and is available at
 *  * http://www.eclipse.org/legal/epl-v10.html
 *  *
 *  * Contributors:
 *  *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz
 *  *     - initial API and implementation and/or initial documentation
 *  ******************************************************************************
 * 
 */
package org.eclipse.fordiac.ide.deployment.monitoringbase;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Port Element</b></em>'.
 * <!-- end-user-doc -->
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
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fb</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fb</em>' reference.
	 * @see #setFb(FB)
	 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBasePackage#getPortElement_Fb()
	 * @model
	 * @generated
	 */
	FB getFb();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement#getFb <em>Fb</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fb</em>' reference.
	 * @see #getFb()
	 * @generated
	 */
	void setFb(FB value);

	/**
	 * Returns the value of the '<em><b>Interface Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Interface Element</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Interface Element</em>' reference.
	 * @see #setInterfaceElement(IInterfaceElement)
	 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBasePackage#getPortElement_InterfaceElement()
	 * @model
	 * @generated
	 */
	IInterfaceElement getInterfaceElement();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement#getInterfaceElement <em>Interface Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Interface Element</em>' reference.
	 * @see #getInterfaceElement()
	 * @generated
	 */
	void setInterfaceElement(IInterfaceElement value);

	/**
	 * Returns the value of the '<em><b>Resource</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resource</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resource</em>' reference.
	 * @see #setResource(Resource)
	 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBasePackage#getPortElement_Resource()
	 * @model
	 * @generated
	 */
	Resource getResource();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement#getResource <em>Resource</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resource</em>' reference.
	 * @see #getResource()
	 * @generated
	 */
	void setResource(Resource value);

	/**
	 * Returns the value of the '<em><b>Hierarchy</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hierarchy</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hierarchy</em>' attribute list.
	 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBasePackage#getPortElement_Hierarchy()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	EList<String> getHierarchy();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='\t\tString hierarchy = \"\"; \r\n\t\tfor (String element : getHierarchy()) {\r\n\t\t\thierarchy += element; \r\n\t\t\thierarchy += \".\"; \r\n\t\t}\r\n\r\n\t\tString adapter = \"\";\r\n\t\tif (interfaceElement.eContainer().eContainer() instanceof AdapterFB) {\r\n\t\t\tadapter += ((PortElementImpl)eContainer()).interfaceElement.getName();\r\n\t\t\tadapter += \".\";\r\n\t\t}\r\n\r\n\t\treturn getResource().getDevice().getName() + \".\"\r\n\t\t\t\t+ resource.getName() + \".\" + hierarchy +  fb.getName() + \".\"\r\n\t\t\t\t+ adapter + interfaceElement.getName();'"
	 * @generated
	 */
	String getPortString();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='\t\treturn getResource().getDevice();'"
	 * @generated
	 */
	Device getDevice();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='\t\treturn getResource().getAutomationSystem();'"
	 * @generated
	 */
	AutomationSystem getSystem();

} // PortElement
