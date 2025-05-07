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
 * A representation of the model object '<em><b>Interval</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.Interval#getTime <em>Time</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.Interval#getLBound <em>LBound</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.Interval#getLbValue <em>Lb Value</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.Interval#getUbValue <em>Ub Value</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.Interval#getUBound <em>UBound</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.Interval#getUnit <em>Unit</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getInterval()
 * @model
 * @generated
 */
public interface Interval extends EObject
{
  /**
   * Returns the value of the '<em><b>Time</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Time</em>' containment reference.
   * @see #setTime(TimeExpr)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getInterval_Time()
   * @model containment="true"
   * @generated
   */
  TimeExpr getTime();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.Interval#getTime <em>Time</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Time</em>' containment reference.
   * @see #getTime()
   * @generated
   */
  void setTime(TimeExpr value);

  /**
   * Returns the value of the '<em><b>LBound</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>LBound</em>' attribute.
   * @see #setLBound(String)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getInterval_LBound()
   * @model
   * @generated
   */
  String getLBound();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.Interval#getLBound <em>LBound</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>LBound</em>' attribute.
   * @see #getLBound()
   * @generated
   */
  void setLBound(String value);

  /**
   * Returns the value of the '<em><b>Lb Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Lb Value</em>' attribute.
   * @see #setLbValue(double)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getInterval_LbValue()
   * @model
   * @generated
   */
  double getLbValue();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.Interval#getLbValue <em>Lb Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Lb Value</em>' attribute.
   * @see #getLbValue()
   * @generated
   */
  void setLbValue(double value);

  /**
   * Returns the value of the '<em><b>Ub Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Ub Value</em>' attribute.
   * @see #setUbValue(double)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getInterval_UbValue()
   * @model
   * @generated
   */
  double getUbValue();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.Interval#getUbValue <em>Ub Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Ub Value</em>' attribute.
   * @see #getUbValue()
   * @generated
   */
  void setUbValue(double value);

  /**
   * Returns the value of the '<em><b>UBound</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>UBound</em>' attribute.
   * @see #setUBound(String)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getInterval_UBound()
   * @model
   * @generated
   */
  String getUBound();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.Interval#getUBound <em>UBound</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>UBound</em>' attribute.
   * @see #getUBound()
   * @generated
   */
  void setUBound(String value);

  /**
   * Returns the value of the '<em><b>Unit</b></em>' attribute.
   * The literals are from the enumeration {@link org.eclipse.fordiac.ide.contractSpec.Unit}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Unit</em>' attribute.
   * @see org.eclipse.fordiac.ide.contractSpec.Unit
   * @see #setUnit(Unit)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getInterval_Unit()
   * @model
   * @generated
   */
  Unit getUnit();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.Interval#getUnit <em>Unit</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Unit</em>' attribute.
   * @see org.eclipse.fordiac.ide.contractSpec.Unit
   * @see #getUnit()
   * @generated
   */
  void setUnit(Unit value);

} // Interval
