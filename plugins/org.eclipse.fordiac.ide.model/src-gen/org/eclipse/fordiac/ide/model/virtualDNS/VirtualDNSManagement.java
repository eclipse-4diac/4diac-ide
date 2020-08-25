/*******************************************************************************
 * Copyright (c) 2009 Profactor GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.virtualDNS;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Management</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSManagement#getAvailableDNSCollections <em>Available DNS Collections</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSManagement#getActiveVirtualDNS <em>Active Virtual DNS</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSPackage#getVirtualDNSManagement()
 * @model
 * @generated
 */
public interface VirtualDNSManagement extends EObject {
	/**
	 * Returns the value of the '<em><b>Available DNS Collections</b></em>'
	 * containment reference list. The list contents are of type
	 * {@link org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSCollection}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Available DNS Collections</em>' containment
	 * reference list isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Available DNS Collections</em>' containment
	 *         reference list.
	 * @see org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSPackage#getVirtualDNSManagement_AvailableDNSCollections()
	 * @model containment="true"
	 * @generated
	 */
	EList<VirtualDNSCollection> getAvailableDNSCollections();

	/**
	 * Returns the value of the '<em><b>Active Virtual DNS</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Active Virtual DNS</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Active Virtual DNS</em>' reference.
	 * @see #setActiveVirtualDNS(VirtualDNSCollection)
	 * @see org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSPackage#getVirtualDNSManagement_ActiveVirtualDNS()
	 * @model
	 * @generated
	 */
	VirtualDNSCollection getActiveVirtualDNS();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSManagement#getActiveVirtualDNS <em>Active Virtual DNS</em>}' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Active Virtual DNS</em>' reference.
	 * @see #getActiveVirtualDNS()
	 * @generated
	 */
	void setActiveVirtualDNS(VirtualDNSCollection value);

	String getReplacedString(String value);

} // VirtualDNSManagement
