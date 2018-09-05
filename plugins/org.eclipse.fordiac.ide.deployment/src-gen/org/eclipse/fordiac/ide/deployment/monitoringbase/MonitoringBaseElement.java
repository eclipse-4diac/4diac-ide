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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.gef.editparts.IEditPartCreator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement#getPort <em>Port</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement#isOffline <em>Offline</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBasePackage#getMonitoringBaseElement()
 * @model abstract="true" superTypes="org.eclipse.fordiac.ide.deployment.monitoringbase.IEditPartCreator"
 * @generated
 */
public interface MonitoringBaseElement extends EObject, IEditPartCreator {
	/**
	 * Returns the value of the '<em><b>Port</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Port</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Port</em>' reference.
	 * @see #setPort(PortElement)
	 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBasePackage#getMonitoringBaseElement_Port()
	 * @model
	 * @generated
	 */
	PortElement getPort();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement#getPort <em>Port</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Port</em>' reference.
	 * @see #getPort()
	 * @generated
	 */
	void setPort(PortElement value);

	/**
	 * Returns the value of the '<em><b>Offline</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Offline</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Offline</em>' attribute.
	 * @see #setOffline(boolean)
	 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBasePackage#getMonitoringBaseElement_Offline()
	 * @model default="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isOffline();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement#isOffline <em>Offline</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Offline</em>' attribute.
	 * @see #isOffline()
	 * @generated
	 */
	void setOffline(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='String hierarchy = \"\"; \n\t\tfor (String element : getPort().getHierarchy()) {\n\t\t\thierarchy += element; \n\t\t\thierarchy += \".\"; \n\t\t}\n\t\t\n\t\tString adapter = \"\";\n\t\tif (getPort().getInterfaceElement().eContainer().eContainer() instanceof AdapterFB) {\n\t\t\tadapter += ((PortElementImpl)getPort().eContainer()).getInterfaceElement().getName();\n\t\t\tadapter += \".\";\n\t\t}\n\t\t\n\t\treturn hierarchy +  getPort().getFb().getName() + \".\" +\n\t\t\t\tadapter + getPort().getInterfaceElement().getName();'"
	 * @generated
	 */
	String getPortString();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return getPort().getResource().getName();'"
	 * @generated
	 */
	String getResourceString();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return getPort().getFb().getName();'"
	 * @generated
	 */
	String getFBString();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='String hierarchy = \"\"; \nfor (String element : getPort().getHierarchy()) {\n\thierarchy += element; \n\thierarchy += \".\"; \n}\n\nString adapter = \"\";\nif (getPort().getInterfaceElement().eContainer().eContainer() instanceof AdapterFB) {\n\tadapter += ((PortElementImpl)getPort().eContainer()).getInterfaceElement().getName();\n\tadapter += \".\";\n}\n\nreturn hierarchy +  getPort().getFb().getName() + \".\" +\n\t\tadapter + getPort().getInterfaceElement().getName();'"
	 * @generated
	 */
	String getQualifiedString();

} // MonitoringBaseElement
