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
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage;
import org.eclipse.fordiac.ide.contractSpec.Interval;
import org.eclipse.fordiac.ide.contractSpec.TimeExpr;
import org.eclipse.fordiac.ide.contractSpec.Unit;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Interval</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.IntervalImpl#getTime <em>Time</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.IntervalImpl#getLBound <em>LBound</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.IntervalImpl#getLbValue <em>Lb Value</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.IntervalImpl#getUbValue <em>Ub Value</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.IntervalImpl#getUBound <em>UBound</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.IntervalImpl#getUnit <em>Unit</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IntervalImpl extends MinimalEObjectImpl.Container implements Interval
{
  /**
   * The cached value of the '{@link #getTime() <em>Time</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTime()
   * @generated
   * @ordered
   */
  protected TimeExpr time;

  /**
   * The default value of the '{@link #getLBound() <em>LBound</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLBound()
   * @generated
   * @ordered
   */
  protected static final String LBOUND_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getLBound() <em>LBound</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLBound()
   * @generated
   * @ordered
   */
  protected String lBound = LBOUND_EDEFAULT;

  /**
   * The default value of the '{@link #getLbValue() <em>Lb Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLbValue()
   * @generated
   * @ordered
   */
  protected static final double LB_VALUE_EDEFAULT = 0.0;

  /**
   * The cached value of the '{@link #getLbValue() <em>Lb Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLbValue()
   * @generated
   * @ordered
   */
  protected double lbValue = LB_VALUE_EDEFAULT;

  /**
   * The default value of the '{@link #getUbValue() <em>Ub Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUbValue()
   * @generated
   * @ordered
   */
  protected static final double UB_VALUE_EDEFAULT = 0.0;

  /**
   * The cached value of the '{@link #getUbValue() <em>Ub Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUbValue()
   * @generated
   * @ordered
   */
  protected double ubValue = UB_VALUE_EDEFAULT;

  /**
   * The default value of the '{@link #getUBound() <em>UBound</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUBound()
   * @generated
   * @ordered
   */
  protected static final String UBOUND_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getUBound() <em>UBound</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUBound()
   * @generated
   * @ordered
   */
  protected String uBound = UBOUND_EDEFAULT;

  /**
   * The default value of the '{@link #getUnit() <em>Unit</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUnit()
   * @generated
   * @ordered
   */
  protected static final Unit UNIT_EDEFAULT = Unit.S;

  /**
   * The cached value of the '{@link #getUnit() <em>Unit</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUnit()
   * @generated
   * @ordered
   */
  protected Unit unit = UNIT_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected IntervalImpl()
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
    return ContractSpecPackage.Literals.INTERVAL;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public TimeExpr getTime()
  {
    return time;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetTime(TimeExpr newTime, NotificationChain msgs)
  {
    TimeExpr oldTime = time;
    time = newTime;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ContractSpecPackage.INTERVAL__TIME, oldTime, newTime);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setTime(TimeExpr newTime)
  {
    if (newTime != time)
    {
      NotificationChain msgs = null;
      if (time != null)
        msgs = ((InternalEObject)time).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.INTERVAL__TIME, null, msgs);
      if (newTime != null)
        msgs = ((InternalEObject)newTime).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.INTERVAL__TIME, null, msgs);
      msgs = basicSetTime(newTime, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.INTERVAL__TIME, newTime, newTime));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getLBound()
  {
    return lBound;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setLBound(String newLBound)
  {
    String oldLBound = lBound;
    lBound = newLBound;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.INTERVAL__LBOUND, oldLBound, lBound));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public double getLbValue()
  {
    return lbValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setLbValue(double newLbValue)
  {
    double oldLbValue = lbValue;
    lbValue = newLbValue;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.INTERVAL__LB_VALUE, oldLbValue, lbValue));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public double getUbValue()
  {
    return ubValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setUbValue(double newUbValue)
  {
    double oldUbValue = ubValue;
    ubValue = newUbValue;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.INTERVAL__UB_VALUE, oldUbValue, ubValue));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getUBound()
  {
    return uBound;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setUBound(String newUBound)
  {
    String oldUBound = uBound;
    uBound = newUBound;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.INTERVAL__UBOUND, oldUBound, uBound));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Unit getUnit()
  {
    return unit;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setUnit(Unit newUnit)
  {
    Unit oldUnit = unit;
    unit = newUnit == null ? UNIT_EDEFAULT : newUnit;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.INTERVAL__UNIT, oldUnit, unit));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case ContractSpecPackage.INTERVAL__TIME:
        return basicSetTime(null, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
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
      case ContractSpecPackage.INTERVAL__TIME:
        return getTime();
      case ContractSpecPackage.INTERVAL__LBOUND:
        return getLBound();
      case ContractSpecPackage.INTERVAL__LB_VALUE:
        return getLbValue();
      case ContractSpecPackage.INTERVAL__UB_VALUE:
        return getUbValue();
      case ContractSpecPackage.INTERVAL__UBOUND:
        return getUBound();
      case ContractSpecPackage.INTERVAL__UNIT:
        return getUnit();
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
      case ContractSpecPackage.INTERVAL__TIME:
        setTime((TimeExpr)newValue);
        return;
      case ContractSpecPackage.INTERVAL__LBOUND:
        setLBound((String)newValue);
        return;
      case ContractSpecPackage.INTERVAL__LB_VALUE:
        setLbValue((Double)newValue);
        return;
      case ContractSpecPackage.INTERVAL__UB_VALUE:
        setUbValue((Double)newValue);
        return;
      case ContractSpecPackage.INTERVAL__UBOUND:
        setUBound((String)newValue);
        return;
      case ContractSpecPackage.INTERVAL__UNIT:
        setUnit((Unit)newValue);
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
      case ContractSpecPackage.INTERVAL__TIME:
        setTime((TimeExpr)null);
        return;
      case ContractSpecPackage.INTERVAL__LBOUND:
        setLBound(LBOUND_EDEFAULT);
        return;
      case ContractSpecPackage.INTERVAL__LB_VALUE:
        setLbValue(LB_VALUE_EDEFAULT);
        return;
      case ContractSpecPackage.INTERVAL__UB_VALUE:
        setUbValue(UB_VALUE_EDEFAULT);
        return;
      case ContractSpecPackage.INTERVAL__UBOUND:
        setUBound(UBOUND_EDEFAULT);
        return;
      case ContractSpecPackage.INTERVAL__UNIT:
        setUnit(UNIT_EDEFAULT);
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
      case ContractSpecPackage.INTERVAL__TIME:
        return time != null;
      case ContractSpecPackage.INTERVAL__LBOUND:
        return LBOUND_EDEFAULT == null ? lBound != null : !LBOUND_EDEFAULT.equals(lBound);
      case ContractSpecPackage.INTERVAL__LB_VALUE:
        return lbValue != LB_VALUE_EDEFAULT;
      case ContractSpecPackage.INTERVAL__UB_VALUE:
        return ubValue != UB_VALUE_EDEFAULT;
      case ContractSpecPackage.INTERVAL__UBOUND:
        return UBOUND_EDEFAULT == null ? uBound != null : !UBOUND_EDEFAULT.equals(uBound);
      case ContractSpecPackage.INTERVAL__UNIT:
        return unit != UNIT_EDEFAULT;
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
    result.append(" (lBound: ");
    result.append(lBound);
    result.append(", lbValue: ");
    result.append(lbValue);
    result.append(", ubValue: ");
    result.append(ubValue);
    result.append(", uBound: ");
    result.append(uBound);
    result.append(", unit: ");
    result.append(unit);
    result.append(')');
    return result.toString();
  }

} //IntervalImpl
