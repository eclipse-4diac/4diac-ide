/**
 * *******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Martin Jobst
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Source</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource#getElements <em>Elements</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmPackage#getSTAlgorithmSource()
 * @model
 * @generated
 */
public interface STAlgorithmSource extends STSource {
	/**
	 * Returns the value of the '<em><b>Elements</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSourceElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Elements</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmPackage#getSTAlgorithmSource_Elements()
	 * @model containment="true"
	 * @generated
	 */
	EList<STAlgorithmSourceElement> getElements();

} // STAlgorithmSource
