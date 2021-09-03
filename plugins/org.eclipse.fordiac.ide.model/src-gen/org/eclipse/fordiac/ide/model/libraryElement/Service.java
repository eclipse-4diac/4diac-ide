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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/** <!-- begin-user-doc --> A representation of the model object '<em><b>Service</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Service#getRightInterface <em>Right Interface</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Service#getLeftInterface <em>Left Interface</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Service#getServiceSequence <em>Service Sequence</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getService()
 * @model
 * @generated */
public interface Service extends EObject {
	/** Returns the value of the '<em><b>Service Sequence</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Service Sequence</em>' reference list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Service Sequence</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getService_ServiceSequence()
	 * @model containment="true" resolveProxies="true" extendedMetaData="kind='element' name='OutputPrimitive'
	 *        namespace='##targetNamespace'"
	 * @generated */
	EList<ServiceSequence> getServiceSequence();

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @model kind="operation" required="true"
	 * @generated */
	FBType getFBType();

	/** Returns the value of the '<em><b>Right Interface</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Right Interface</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Right Interface</em>' containment reference.
	 * @see #setRightInterface(ServiceInterface)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getService_RightInterface()
	 * @model containment="true" resolveProxies="true"
	 * @generated */
	ServiceInterface getRightInterface();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Service#getRightInterface <em>Right
	 * Interface</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Right Interface</em>' containment reference.
	 * @see #getRightInterface()
	 * @generated */
	void setRightInterface(ServiceInterface value);

	/** Returns the value of the '<em><b>Left Interface</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Left Interface</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Left Interface</em>' containment reference.
	 * @see #setLeftInterface(ServiceInterface)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getService_LeftInterface()
	 * @model containment="true" resolveProxies="true"
	 * @generated */
	ServiceInterface getLeftInterface();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Service#getLeftInterface <em>Left
	 * Interface</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Left Interface</em>' containment reference.
	 * @see #getLeftInterface()
	 * @generated */
	void setLeftInterface(ServiceInterface value);

} // Service
