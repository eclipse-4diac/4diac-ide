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

import org.eclipse.fordiac.ide.contractSpec.CausalAge;
import org.eclipse.fordiac.ide.contractSpec.ClockDefinition;
import org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage;
import org.eclipse.fordiac.ide.contractSpec.EventSpec;
import org.eclipse.fordiac.ide.contractSpec.Interval;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Causal Age</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.CausalAgeImpl#getE1 <em>E1</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.CausalAgeImpl#getE2 <em>E2</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.CausalAgeImpl#getInterval <em>Interval</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.CausalAgeImpl#getClock <em>Clock</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CausalAgeImpl extends TimeSpecImpl implements CausalAge
{
  /**
   * The cached value of the '{@link #getE1() <em>E1</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getE1()
   * @generated
   * @ordered
   */
  protected EventSpec e1;

  /**
   * The cached value of the '{@link #getE2() <em>E2</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getE2()
   * @generated
   * @ordered
   */
  protected EventSpec e2;

  /**
   * The cached value of the '{@link #getInterval() <em>Interval</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInterval()
   * @generated
   * @ordered
   */
  protected Interval interval;

  /**
   * The cached value of the '{@link #getClock() <em>Clock</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getClock()
   * @generated
   * @ordered
   */
  protected ClockDefinition clock;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected CausalAgeImpl()
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
    return ContractSpecPackage.Literals.CAUSAL_AGE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EventSpec getE1()
  {
    return e1;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetE1(EventSpec newE1, NotificationChain msgs)
  {
    EventSpec oldE1 = e1;
    e1 = newE1;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ContractSpecPackage.CAUSAL_AGE__E1, oldE1, newE1);
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
  public void setE1(EventSpec newE1)
  {
    if (newE1 != e1)
    {
      NotificationChain msgs = null;
      if (e1 != null)
        msgs = ((InternalEObject)e1).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.CAUSAL_AGE__E1, null, msgs);
      if (newE1 != null)
        msgs = ((InternalEObject)newE1).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.CAUSAL_AGE__E1, null, msgs);
      msgs = basicSetE1(newE1, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.CAUSAL_AGE__E1, newE1, newE1));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EventSpec getE2()
  {
    return e2;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetE2(EventSpec newE2, NotificationChain msgs)
  {
    EventSpec oldE2 = e2;
    e2 = newE2;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ContractSpecPackage.CAUSAL_AGE__E2, oldE2, newE2);
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
  public void setE2(EventSpec newE2)
  {
    if (newE2 != e2)
    {
      NotificationChain msgs = null;
      if (e2 != null)
        msgs = ((InternalEObject)e2).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.CAUSAL_AGE__E2, null, msgs);
      if (newE2 != null)
        msgs = ((InternalEObject)newE2).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.CAUSAL_AGE__E2, null, msgs);
      msgs = basicSetE2(newE2, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.CAUSAL_AGE__E2, newE2, newE2));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Interval getInterval()
  {
    return interval;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetInterval(Interval newInterval, NotificationChain msgs)
  {
    Interval oldInterval = interval;
    interval = newInterval;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ContractSpecPackage.CAUSAL_AGE__INTERVAL, oldInterval, newInterval);
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
  public void setInterval(Interval newInterval)
  {
    if (newInterval != interval)
    {
      NotificationChain msgs = null;
      if (interval != null)
        msgs = ((InternalEObject)interval).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.CAUSAL_AGE__INTERVAL, null, msgs);
      if (newInterval != null)
        msgs = ((InternalEObject)newInterval).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.CAUSAL_AGE__INTERVAL, null, msgs);
      msgs = basicSetInterval(newInterval, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.CAUSAL_AGE__INTERVAL, newInterval, newInterval));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public ClockDefinition getClock()
  {
    if (clock != null && clock.eIsProxy())
    {
      InternalEObject oldClock = (InternalEObject)clock;
      clock = (ClockDefinition)eResolveProxy(oldClock);
      if (clock != oldClock)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ContractSpecPackage.CAUSAL_AGE__CLOCK, oldClock, clock));
      }
    }
    return clock;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ClockDefinition basicGetClock()
  {
    return clock;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setClock(ClockDefinition newClock)
  {
    ClockDefinition oldClock = clock;
    clock = newClock;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.CAUSAL_AGE__CLOCK, oldClock, clock));
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
      case ContractSpecPackage.CAUSAL_AGE__E1:
        return basicSetE1(null, msgs);
      case ContractSpecPackage.CAUSAL_AGE__E2:
        return basicSetE2(null, msgs);
      case ContractSpecPackage.CAUSAL_AGE__INTERVAL:
        return basicSetInterval(null, msgs);
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
      case ContractSpecPackage.CAUSAL_AGE__E1:
        return getE1();
      case ContractSpecPackage.CAUSAL_AGE__E2:
        return getE2();
      case ContractSpecPackage.CAUSAL_AGE__INTERVAL:
        return getInterval();
      case ContractSpecPackage.CAUSAL_AGE__CLOCK:
        if (resolve) return getClock();
        return basicGetClock();
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
      case ContractSpecPackage.CAUSAL_AGE__E1:
        setE1((EventSpec)newValue);
        return;
      case ContractSpecPackage.CAUSAL_AGE__E2:
        setE2((EventSpec)newValue);
        return;
      case ContractSpecPackage.CAUSAL_AGE__INTERVAL:
        setInterval((Interval)newValue);
        return;
      case ContractSpecPackage.CAUSAL_AGE__CLOCK:
        setClock((ClockDefinition)newValue);
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
      case ContractSpecPackage.CAUSAL_AGE__E1:
        setE1((EventSpec)null);
        return;
      case ContractSpecPackage.CAUSAL_AGE__E2:
        setE2((EventSpec)null);
        return;
      case ContractSpecPackage.CAUSAL_AGE__INTERVAL:
        setInterval((Interval)null);
        return;
      case ContractSpecPackage.CAUSAL_AGE__CLOCK:
        setClock((ClockDefinition)null);
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
      case ContractSpecPackage.CAUSAL_AGE__E1:
        return e1 != null;
      case ContractSpecPackage.CAUSAL_AGE__E2:
        return e2 != null;
      case ContractSpecPackage.CAUSAL_AGE__INTERVAL:
        return interval != null;
      case ContractSpecPackage.CAUSAL_AGE__CLOCK:
        return clock != null;
    }
    return super.eIsSet(featureID);
  }

} //CausalAgeImpl
