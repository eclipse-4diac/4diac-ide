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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Demultiplexer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer#isIsConfigured <em>Is Configured</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getDemultiplexer()
 * @model
 * @generated
 */
public interface Demultiplexer extends StructManipulator {
	/**
	 * Returns the value of the '<em><b>Is Configured</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Configured</em>' attribute.
	 * @see #setIsConfigured(boolean)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getDemultiplexer_IsConfigured()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isIsConfigured();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer#isIsConfigured <em>Is Configured</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Configured</em>' attribute.
	 * @see #isIsConfigured()
	 * @generated
	 */
	void setIsConfigured(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	EList<VarDeclaration> getMemberVars();

} // Demultiplexer
