/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2022-2023 Martin Erich Jobst
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

import org.eclipse.emf.common.util.EList;

import org.eclipse.gef.commands.CommandStack;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Automation System</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem#getApplication <em>Application</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem#getMapping <em>Mapping</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem#getSystemConfiguration <em>System Configuration</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem#getCommandStack <em>Command Stack</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getAutomationSystem()
 * @model
 * @generated
 */
public interface AutomationSystem extends LibraryElement {
	/**
	 * Returns the value of the '<em><b>Application</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.Application}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Application</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getAutomationSystem_Application()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Application' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Application> getApplication();

	/**
	 * Returns the value of the '<em><b>Mapping</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.Mapping}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapping</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getAutomationSystem_Mapping()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Mapping' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Mapping> getMapping();

	/**
	 * Returns the value of the '<em><b>System Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>System Configuration</em>' containment reference.
	 * @see #setSystemConfiguration(SystemConfiguration)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getAutomationSystem_SystemConfiguration()
	 * @model containment="true" resolveProxies="true" required="true"
	 * @generated
	 */
	SystemConfiguration getSystemConfiguration();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem#getSystemConfiguration <em>System Configuration</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>System Configuration</em>' containment reference.
	 * @see #getSystemConfiguration()
	 * @generated
	 */
	void setSystemConfiguration(SystemConfiguration value);

	/**
	 * Returns the value of the '<em><b>Command Stack</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Command Stack</em>' attribute.
	 * @see #setCommandStack(CommandStack)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getAutomationSystem_CommandStack()
	 * @model dataType="org.eclipse.fordiac.ide.model.libraryElement.CommandStack" transient="true"
	 * @generated
	 */
	CommandStack getCommandStack();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem#getCommandStack <em>Command Stack</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Command Stack</em>' attribute.
	 * @see #getCommandStack()
	 * @generated
	 */
	void setCommandStack(CommandStack value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model required="true" nameRequired="true"
	 * @generated
	 */
	Device getDeviceNamed(String name);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model required="true" nameRequired="true"
	 * @generated
	 */
	Application getApplicationNamed(String name);

} // AutomationSystem
