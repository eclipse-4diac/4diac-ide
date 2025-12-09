/**
 * *******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.xmiexport.xmiexport;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Initial Values</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportInitialValues#getInitialValues <em>Initial Values</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportPackage#getXMIExportInitialValues()
 * @model
 * @generated
 */
public interface XMIExportInitialValues extends EObject {
	/**
	 * Returns the value of the '<em><b>Initial Values</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportInitialValue}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initial Values</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportPackage#getXMIExportInitialValues_InitialValues()
	 * @model containment="true"
	 * @generated
	 */
	EList<XMIExportInitialValue> getInitialValues();

} // XMIExportInitialValues
