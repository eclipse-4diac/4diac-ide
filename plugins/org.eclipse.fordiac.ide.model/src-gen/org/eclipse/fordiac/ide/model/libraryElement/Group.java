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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Group#getGroupElements <em>Group Elements</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getGroup()
 * @model
 * @generated
 */
public interface Group extends FBNetworkElement {
	/**
	 * Returns the value of the '<em><b>Group Elements</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group Elements</em>' reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getGroup_GroupElements()
	 * @model resolveProxies="false"
	 *        extendedMetaData="kind='element' name='FB' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<FBNetworkElement> getGroupElements();

} // Group
