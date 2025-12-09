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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Repetition Options</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.RepetitionOptions#getJitter <em>Jitter</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.RepetitionOptions#getOffset <em>Offset</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getRepetitionOptions()
 * @model
 * @generated
 */
public interface RepetitionOptions extends EObject
{
  /**
   * Returns the value of the '<em><b>Jitter</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Jitter</em>' containment reference.
   * @see #setJitter(Jitter)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getRepetitionOptions_Jitter()
   * @model containment="true"
   * @generated
   */
  Jitter getJitter();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.RepetitionOptions#getJitter <em>Jitter</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Jitter</em>' containment reference.
   * @see #getJitter()
   * @generated
   */
  void setJitter(Jitter value);

  /**
   * Returns the value of the '<em><b>Offset</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Offset</em>' containment reference.
   * @see #setOffset(Offset)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getRepetitionOptions_Offset()
   * @model containment="true"
   * @generated
   */
  Offset getOffset();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.RepetitionOptions#getOffset <em>Offset</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Offset</em>' containment reference.
   * @see #getOffset()
   * @generated
   */
  void setOffset(Offset value);

} // RepetitionOptions
