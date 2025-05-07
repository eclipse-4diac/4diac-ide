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
 * A representation of the model object '<em><b>Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.Value#getInteger <em>Integer</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.Value#getFraction <em>Fraction</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getValue()
 * @model
 * @generated
 */
public interface Value extends EObject
{
  /**
   * Returns the value of the '<em><b>Integer</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Integer</em>' attribute.
   * @see #setInteger(int)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getValue_Integer()
   * @model
   * @generated
   */
  int getInteger();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.Value#getInteger <em>Integer</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Integer</em>' attribute.
   * @see #getInteger()
   * @generated
   */
  void setInteger(int value);

  /**
   * Returns the value of the '<em><b>Fraction</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fraction</em>' attribute.
   * @see #setFraction(int)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getValue_Fraction()
   * @model
   * @generated
   */
  int getFraction();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.Value#getFraction <em>Fraction</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Fraction</em>' attribute.
   * @see #getFraction()
   * @generated
   */
  void setFraction(int value);

} // Value
