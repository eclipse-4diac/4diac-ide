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
 * <em><b>Collection</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSCollection#getVirtualDNSEntries <em>Virtual DNS Entries</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSCollection#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSPackage#getVirtualDNSCollection()
 * @model
 * @generated
 */
public interface VirtualDNSCollection extends EObject {
	/**
	 * Returns the value of the '<em><b>Virtual DNS Entries</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSEntry}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Virtual DNS Entries</em>' containment reference
	 * list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Virtual DNS Entries</em>' containment reference
	 *         list.
	 * @see org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSPackage#getVirtualDNSCollection_VirtualDNSEntries()
	 * @model containment="true"
	 * @generated
	 */
	EList<VirtualDNSEntry> getVirtualDNSEntries();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSPackage#getVirtualDNSCollection_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSCollection#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	String getValueFor(String name);

} // VirtualDNSCollection
