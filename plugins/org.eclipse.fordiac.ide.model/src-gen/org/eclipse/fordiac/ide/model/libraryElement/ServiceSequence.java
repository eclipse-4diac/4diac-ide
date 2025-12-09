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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Service Sequence</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence#getServiceTransaction <em>Service Transaction</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence#getServiceSequenceType <em>Service Sequence Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence#getStartState <em>Start State</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence#getEventManager <em>Event Manager</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getServiceSequence()
 * @model
 * @generated
 */
public interface ServiceSequence extends INamedElement, ConfigurableObject {
	/**
	 * Returns the value of the '<em><b>Service Transaction</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Service Transaction</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getServiceSequence_ServiceTransaction()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='ServiceTransaction' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<ServiceTransaction> getServiceTransaction();

	/**
	 * Returns the value of the '<em><b>Service Sequence Type</b></em>' attribute.
	 * The default value is <code>"POSSIBLE"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Service Sequence Type</em>' attribute.
	 * @see #setServiceSequenceType(String)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getServiceSequence_ServiceSequenceType()
	 * @model default="POSSIBLE"
	 * @generated
	 */
	String getServiceSequenceType();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence#getServiceSequenceType <em>Service Sequence Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Service Sequence Type</em>' attribute.
	 * @see #getServiceSequenceType()
	 * @generated
	 */
	void setServiceSequenceType(String value);

	/**
	 * Returns the value of the '<em><b>Start State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start State</em>' attribute.
	 * @see #setStartState(String)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getServiceSequence_StartState()
	 * @model
	 * @generated
	 */
	String getStartState();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence#getStartState <em>Start State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start State</em>' attribute.
	 * @see #getStartState()
	 * @generated
	 */
	void setStartState(String value);

	/**
	 * Returns the value of the '<em><b>Event Manager</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Event Manager</em>' containment reference.
	 * @see #setEventManager(EObject)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getServiceSequence_EventManager()
	 * @model containment="true"
	 * @generated
	 */
	EObject getEventManager();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence#getEventManager <em>Event Manager</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Event Manager</em>' containment reference.
	 * @see #getEventManager()
	 * @generated
	 */
	void setEventManager(EObject value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	Service getService();

} // ServiceSequence
