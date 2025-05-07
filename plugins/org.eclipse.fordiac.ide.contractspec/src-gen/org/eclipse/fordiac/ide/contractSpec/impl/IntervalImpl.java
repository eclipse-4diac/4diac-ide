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
import org.eclipse.fordiac.ide.contractSpec.Value;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Interval</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.IntervalImpl#getTime <em>Time</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.IntervalImpl#getB1 <em>B1</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.IntervalImpl#getV1 <em>V1</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.IntervalImpl#getV2 <em>V2</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.IntervalImpl#getB2 <em>B2</em>}</li>
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
   * The default value of the '{@link #getB1() <em>B1</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getB1()
   * @generated
   * @ordered
   */
  protected static final String B1_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getB1() <em>B1</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getB1()
   * @generated
   * @ordered
   */
  protected String b1 = B1_EDEFAULT;

  /**
   * The cached value of the '{@link #getV1() <em>V1</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getV1()
   * @generated
   * @ordered
   */
  protected Value v1;

  /**
   * The cached value of the '{@link #getV2() <em>V2</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getV2()
   * @generated
   * @ordered
   */
  protected Value v2;

  /**
   * The default value of the '{@link #getB2() <em>B2</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getB2()
   * @generated
   * @ordered
   */
  protected static final String B2_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getB2() <em>B2</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getB2()
   * @generated
   * @ordered
   */
  protected String b2 = B2_EDEFAULT;

  /**
   * The default value of the '{@link #getUnit() <em>Unit</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUnit()
   * @generated
   * @ordered
   */
  protected static final String UNIT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getUnit() <em>Unit</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUnit()
   * @generated
   * @ordered
   */
  protected String unit = UNIT_EDEFAULT;

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
  public String getB1()
  {
    return b1;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setB1(String newB1)
  {
    String oldB1 = b1;
    b1 = newB1;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.INTERVAL__B1, oldB1, b1));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Value getV1()
  {
    return v1;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetV1(Value newV1, NotificationChain msgs)
  {
    Value oldV1 = v1;
    v1 = newV1;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ContractSpecPackage.INTERVAL__V1, oldV1, newV1);
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
  public void setV1(Value newV1)
  {
    if (newV1 != v1)
    {
      NotificationChain msgs = null;
      if (v1 != null)
        msgs = ((InternalEObject)v1).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.INTERVAL__V1, null, msgs);
      if (newV1 != null)
        msgs = ((InternalEObject)newV1).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.INTERVAL__V1, null, msgs);
      msgs = basicSetV1(newV1, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.INTERVAL__V1, newV1, newV1));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Value getV2()
  {
    return v2;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetV2(Value newV2, NotificationChain msgs)
  {
    Value oldV2 = v2;
    v2 = newV2;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ContractSpecPackage.INTERVAL__V2, oldV2, newV2);
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
  public void setV2(Value newV2)
  {
    if (newV2 != v2)
    {
      NotificationChain msgs = null;
      if (v2 != null)
        msgs = ((InternalEObject)v2).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.INTERVAL__V2, null, msgs);
      if (newV2 != null)
        msgs = ((InternalEObject)newV2).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.INTERVAL__V2, null, msgs);
      msgs = basicSetV2(newV2, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.INTERVAL__V2, newV2, newV2));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getB2()
  {
    return b2;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setB2(String newB2)
  {
    String oldB2 = b2;
    b2 = newB2;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.INTERVAL__B2, oldB2, b2));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getUnit()
  {
    return unit;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setUnit(String newUnit)
  {
    String oldUnit = unit;
    unit = newUnit;
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
      case ContractSpecPackage.INTERVAL__V1:
        return basicSetV1(null, msgs);
      case ContractSpecPackage.INTERVAL__V2:
        return basicSetV2(null, msgs);
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
      case ContractSpecPackage.INTERVAL__B1:
        return getB1();
      case ContractSpecPackage.INTERVAL__V1:
        return getV1();
      case ContractSpecPackage.INTERVAL__V2:
        return getV2();
      case ContractSpecPackage.INTERVAL__B2:
        return getB2();
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
      case ContractSpecPackage.INTERVAL__B1:
        setB1((String)newValue);
        return;
      case ContractSpecPackage.INTERVAL__V1:
        setV1((Value)newValue);
        return;
      case ContractSpecPackage.INTERVAL__V2:
        setV2((Value)newValue);
        return;
      case ContractSpecPackage.INTERVAL__B2:
        setB2((String)newValue);
        return;
      case ContractSpecPackage.INTERVAL__UNIT:
        setUnit((String)newValue);
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
      case ContractSpecPackage.INTERVAL__B1:
        setB1(B1_EDEFAULT);
        return;
      case ContractSpecPackage.INTERVAL__V1:
        setV1((Value)null);
        return;
      case ContractSpecPackage.INTERVAL__V2:
        setV2((Value)null);
        return;
      case ContractSpecPackage.INTERVAL__B2:
        setB2(B2_EDEFAULT);
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
      case ContractSpecPackage.INTERVAL__B1:
        return B1_EDEFAULT == null ? b1 != null : !B1_EDEFAULT.equals(b1);
      case ContractSpecPackage.INTERVAL__V1:
        return v1 != null;
      case ContractSpecPackage.INTERVAL__V2:
        return v2 != null;
      case ContractSpecPackage.INTERVAL__B2:
        return B2_EDEFAULT == null ? b2 != null : !B2_EDEFAULT.equals(b2);
      case ContractSpecPackage.INTERVAL__UNIT:
        return UNIT_EDEFAULT == null ? unit != null : !UNIT_EDEFAULT.equals(unit);
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
    result.append(" (b1: ");
    result.append(b1);
    result.append(", b2: ");
    result.append(b2);
    result.append(", unit: ");
    result.append(unit);
    result.append(')');
    return result.toString();
  }

} //IntervalImpl
