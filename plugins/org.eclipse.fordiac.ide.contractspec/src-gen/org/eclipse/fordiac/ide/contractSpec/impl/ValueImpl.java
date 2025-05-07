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
package org.eclipse.fordiac.ide.contractSpec.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage;
import org.eclipse.fordiac.ide.contractSpec.Value;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.ValueImpl#getInteger <em>Integer</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.ValueImpl#getFraction <em>Fraction</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ValueImpl extends MinimalEObjectImpl.Container implements Value
{
  /**
   * The default value of the '{@link #getInteger() <em>Integer</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInteger()
   * @generated
   * @ordered
   */
  protected static final int INTEGER_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getInteger() <em>Integer</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInteger()
   * @generated
   * @ordered
   */
  protected int integer = INTEGER_EDEFAULT;

  /**
   * The default value of the '{@link #getFraction() <em>Fraction</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFraction()
   * @generated
   * @ordered
   */
  protected static final int FRACTION_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getFraction() <em>Fraction</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFraction()
   * @generated
   * @ordered
   */
  protected int fraction = FRACTION_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ValueImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return ContractSpecPackage.Literals.VALUE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int getInteger()
  {
    return integer;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setInteger(int newInteger)
  {
    int oldInteger = integer;
    integer = newInteger;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.VALUE__INTEGER, oldInteger, integer));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int getFraction()
  {
    return fraction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setFraction(int newFraction)
  {
    int oldFraction = fraction;
    fraction = newFraction;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.VALUE__FRACTION, oldFraction, fraction));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case ContractSpecPackage.VALUE__INTEGER:
        return getInteger();
      case ContractSpecPackage.VALUE__FRACTION:
        return getFraction();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case ContractSpecPackage.VALUE__INTEGER:
        setInteger((Integer)newValue);
        return;
      case ContractSpecPackage.VALUE__FRACTION:
        setFraction((Integer)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case ContractSpecPackage.VALUE__INTEGER:
        setInteger(INTEGER_EDEFAULT);
        return;
      case ContractSpecPackage.VALUE__FRACTION:
        setFraction(FRACTION_EDEFAULT);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case ContractSpecPackage.VALUE__INTEGER:
        return integer != INTEGER_EDEFAULT;
      case ContractSpecPackage.VALUE__FRACTION:
        return fraction != FRACTION_EDEFAULT;
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (integer: ");
    result.append(integer);
    result.append(", fraction: ");
    result.append(fraction);
    result.append(')');
    return result.toString();
  }

} //ValueImpl
