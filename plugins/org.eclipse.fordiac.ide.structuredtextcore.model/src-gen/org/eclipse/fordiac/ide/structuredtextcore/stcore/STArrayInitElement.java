/**
 * *******************************************************************************
 * Copyright (c) 2022 Primetals Technologies GmbH,
 *               2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Martin Jobst, Martin Melik Merkumians
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.structuredtextcore.stcore;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ST Array Init Element</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTArrayInitElement()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface STArrayInitElement extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	INamedElement getResultType();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	INamedElement getDeclaredResultType();

} // STArrayInitElement
