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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Clock Definition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.ClockDefinition#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.ClockDefinition#getResolution <em>Resolution</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.ClockDefinition#getSkew <em>Skew</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.ClockDefinition#getDrift <em>Drift</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.ClockDefinition#getMaxdiff <em>Maxdiff</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getClockDefinition()
 * @model
 * @generated
 */
public interface ClockDefinition extends TimeSpec
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getClockDefinition_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.ClockDefinition#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Resolution</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Resolution</em>' containment reference.
   * @see #setResolution(TimeExpr)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getClockDefinition_Resolution()
   * @model containment="true"
   * @generated
   */
  TimeExpr getResolution();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.ClockDefinition#getResolution <em>Resolution</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Resolution</em>' containment reference.
   * @see #getResolution()
   * @generated
   */
  void setResolution(TimeExpr value);

  /**
   * Returns the value of the '<em><b>Skew</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Skew</em>' containment reference.
   * @see #setSkew(TimeExpr)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getClockDefinition_Skew()
   * @model containment="true"
   * @generated
   */
  TimeExpr getSkew();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.ClockDefinition#getSkew <em>Skew</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Skew</em>' containment reference.
   * @see #getSkew()
   * @generated
   */
  void setSkew(TimeExpr value);

  /**
   * Returns the value of the '<em><b>Drift</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Drift</em>' containment reference.
   * @see #setDrift(Interval)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getClockDefinition_Drift()
   * @model containment="true"
   * @generated
   */
  Interval getDrift();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.ClockDefinition#getDrift <em>Drift</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Drift</em>' containment reference.
   * @see #getDrift()
   * @generated
   */
  void setDrift(Interval value);

  /**
   * Returns the value of the '<em><b>Maxdiff</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Maxdiff</em>' containment reference.
   * @see #setMaxdiff(TimeExpr)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getClockDefinition_Maxdiff()
   * @model containment="true"
   * @generated
   */
  TimeExpr getMaxdiff();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.ClockDefinition#getMaxdiff <em>Maxdiff</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Maxdiff</em>' containment reference.
   * @see #getMaxdiff()
   * @generated
   */
  void setMaxdiff(TimeExpr value);

} // ClockDefinition
