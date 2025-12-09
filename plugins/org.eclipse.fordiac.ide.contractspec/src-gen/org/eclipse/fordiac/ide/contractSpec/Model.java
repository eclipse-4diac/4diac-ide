/*******************************************************************************
 * Copyright (c) 2024 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.contractSpec;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.Model#getTimeSpec <em>Time Spec</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getModel()
 * @model
 * @generated
 */
public interface Model extends EObject
{
  /**
   * Returns the value of the '<em><b>Time Spec</b></em>' containment reference list.
   * The list contents are of type {@link org.eclipse.fordiac.ide.contractSpec.TimeSpec}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Time Spec</em>' containment reference list.
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getModel_TimeSpec()
   * @model containment="true"
   * @generated
   */
  EList<TimeSpec> getTimeSpec();

} // Model
