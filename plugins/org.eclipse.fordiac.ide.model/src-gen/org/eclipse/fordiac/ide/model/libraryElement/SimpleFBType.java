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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simple FB Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType#getSimpleECStates <em>Simple EC States</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getSimpleFBType()
 * @model
 * @generated
 */
public interface SimpleFBType extends BaseFBType {

	/**
	 * Returns the value of the '<em><b>Simple EC States</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.SimpleECState}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleECState#getSimpleFBType <em>Simple FB Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Simple EC States</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getSimpleFBType_SimpleECStates()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SimpleECState#getSimpleFBType
	 * @model opposite="simpleFBType" containment="true" resolveProxies="true" required="true"
	 *        extendedMetaData="kind='element' name='SimpleECStates' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<SimpleECState> getSimpleECStates();
} // SimpleFBType
