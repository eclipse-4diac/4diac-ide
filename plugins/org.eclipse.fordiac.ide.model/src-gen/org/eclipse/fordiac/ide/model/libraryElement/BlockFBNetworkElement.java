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

import java.util.Map;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.fordiac.ide.model.typelibrary.InterfaceTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Block FB Network Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement#getInterface <em>Interface</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getBlockFBNetworkElement()
 * @model abstract="true"
 * @generated
 */
public interface BlockFBNetworkElement extends FBNetworkElement {

	/**
	 * Returns the value of the '<em><b>Interface</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getBlockFBNetworkElement <em>Block FB Network Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Interface</em>' containment reference.
	 * @see #setInterface(InterfaceList)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getBlockFBNetworkElement_Interface()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getBlockFBNetworkElement
	 * @model opposite="BlockFBNetworkElement" containment="true" resolveProxies="true"
	 * @generated
	 */
	InterfaceList getInterface();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement#getInterface <em>Interface</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Interface</em>' containment reference.
	 * @see #getInterface()
	 * @generated
	 */
	void setInterface(InterfaceList value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	InterfaceList getTypeInterface();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void checkConnections();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void setMapping(Mapping newMapping);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	BlockFBNetworkElement getOpposite();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.fordiac.ide.model.libraryElement.InterfaceTypeEntry" required="true"
	 * @generated
	 */
	InterfaceTypeEntry getTypeEntry();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model newTypeEntryDataType="org.eclipse.fordiac.ide.model.libraryElement.TypeEntry"
	 * @generated
	 */
	void setTypeEntry(TypeEntry newTypeEntry);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/Ecore invariant='true'"
	 * @generated
	 */
	boolean validateUnused(DiagnosticChain diagnostics, Map<Object, Object> context);
} // BlockFBNetworkElement
