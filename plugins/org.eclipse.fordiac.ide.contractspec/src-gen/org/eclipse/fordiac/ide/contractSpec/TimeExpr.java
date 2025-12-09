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
 * A representation of the model object '<em><b>Time Expr</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.TimeExpr#getValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.TimeExpr#getUnit <em>Unit</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getTimeExpr()
 * @model
 * @generated
 */
public interface TimeExpr extends EObject
{
  /**
   * Returns the value of the '<em><b>Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' attribute.
   * @see #setValue(double)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getTimeExpr_Value()
   * @model
   * @generated
   */
  double getValue();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.TimeExpr#getValue <em>Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' attribute.
   * @see #getValue()
   * @generated
   */
  void setValue(double value);

  /**
   * Returns the value of the '<em><b>Unit</b></em>' attribute.
   * The literals are from the enumeration {@link org.eclipse.fordiac.ide.contractSpec.Unit}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Unit</em>' attribute.
   * @see org.eclipse.fordiac.ide.contractSpec.Unit
   * @see #setUnit(Unit)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getTimeExpr_Unit()
   * @model
   * @generated
   */
  Unit getUnit();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.TimeExpr#getUnit <em>Unit</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Unit</em>' attribute.
   * @see org.eclipse.fordiac.ide.contractSpec.Unit
   * @see #getUnit()
   * @generated
   */
  void setUnit(Unit value);

} // TimeExpr
