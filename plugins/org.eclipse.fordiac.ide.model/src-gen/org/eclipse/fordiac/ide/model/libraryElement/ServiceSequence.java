/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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

/** <!-- begin-user-doc --> A representation of the model object '<em><b>Service Sequence</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence#getServiceTransaction <em>Service
 * Transaction</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getServiceSequence()
 * @model
 * @generated */
public interface ServiceSequence extends INamedElement {
	/** Returns the value of the '<em><b>Service Transaction</b></em>' containment reference list. The list contents are
	 * of type {@link org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the value of the '<em>Service Transaction</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getServiceSequence_ServiceTransaction()
	 * @model containment="true" extendedMetaData="kind='element' name='ServiceTransaction'
	 *        namespace='##targetNamespace'"
	 * @generated */
	EList<ServiceTransaction> getServiceTransaction();

} // ServiceSequence
