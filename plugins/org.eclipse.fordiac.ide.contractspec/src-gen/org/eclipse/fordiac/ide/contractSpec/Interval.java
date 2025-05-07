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
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.Interval#getB1 <em>B1</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.Interval#getV1 <em>V1</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.Interval#getV2 <em>V2</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.Interval#getB2 <em>B2</em>}</li>
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
   * Returns the value of the '<em><b>B1</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>B1</em>' attribute.
   * @see #setB1(String)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getInterval_B1()
   * @model
   * @generated
   */
  String getB1();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.Interval#getB1 <em>B1</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>B1</em>' attribute.
   * @see #getB1()
   * @generated
   */
  void setB1(String value);

  /**
   * Returns the value of the '<em><b>V1</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>V1</em>' containment reference.
   * @see #setV1(Value)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getInterval_V1()
   * @model containment="true"
   * @generated
   */
  Value getV1();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.Interval#getV1 <em>V1</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>V1</em>' containment reference.
   * @see #getV1()
   * @generated
   */
  void setV1(Value value);

  /**
   * Returns the value of the '<em><b>V2</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>V2</em>' containment reference.
   * @see #setV2(Value)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getInterval_V2()
   * @model containment="true"
   * @generated
   */
  Value getV2();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.Interval#getV2 <em>V2</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>V2</em>' containment reference.
   * @see #getV2()
   * @generated
   */
  void setV2(Value value);

  /**
   * Returns the value of the '<em><b>B2</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>B2</em>' attribute.
   * @see #setB2(String)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getInterval_B2()
   * @model
   * @generated
   */
  String getB2();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.Interval#getB2 <em>B2</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>B2</em>' attribute.
   * @see #getB2()
   * @generated
   */
  void setB2(String value);

  /**
   * Returns the value of the '<em><b>Unit</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Unit</em>' attribute.
   * @see #setUnit(String)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getInterval_Unit()
   * @model
   * @generated
   */
  String getUnit();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.Interval#getUnit <em>Unit</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Unit</em>' attribute.
   * @see #getUnit()
   * @generated
   */
  void setUnit(String value);

} // Interval
