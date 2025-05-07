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

import org.eclipse.fordiac.ide.contractSpec.Age;
import org.eclipse.fordiac.ide.contractSpec.ClockDefinition;
import org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage;
import org.eclipse.fordiac.ide.contractSpec.EventExpr;
import org.eclipse.fordiac.ide.contractSpec.Interval;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Age</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.AgeImpl#getTrigger <em>Trigger</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.AgeImpl#getReaction <em>Reaction</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.AgeImpl#getInterval <em>Interval</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.AgeImpl#getN <em>N</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.AgeImpl#getOutOf <em>Out Of</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.AgeImpl#getClock <em>Clock</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AgeImpl extends TimeSpecImpl implements Age
{
  /**
   * The cached value of the '{@link #getTrigger() <em>Trigger</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTrigger()
   * @generated
   * @ordered
   */
  protected EventExpr trigger;

  /**
   * The cached value of the '{@link #getReaction() <em>Reaction</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getReaction()
   * @generated
   * @ordered
   */
  protected EventExpr reaction;

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
   * The default value of the '{@link #getN() <em>N</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getN()
   * @generated
   * @ordered
   */
  protected static final int N_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getN() <em>N</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getN()
   * @generated
   * @ordered
   */
  protected int n = N_EDEFAULT;

  /**
   * The default value of the '{@link #getOutOf() <em>Out Of</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOutOf()
   * @generated
   * @ordered
   */
  protected static final int OUT_OF_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getOutOf() <em>Out Of</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOutOf()
   * @generated
   * @ordered
   */
  protected int outOf = OUT_OF_EDEFAULT;

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
  protected AgeImpl()
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
    return ContractSpecPackage.Literals.AGE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EventExpr getTrigger()
  {
    return trigger;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetTrigger(EventExpr newTrigger, NotificationChain msgs)
  {
    EventExpr oldTrigger = trigger;
    trigger = newTrigger;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ContractSpecPackage.AGE__TRIGGER, oldTrigger, newTrigger);
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
  public void setTrigger(EventExpr newTrigger)
  {
    if (newTrigger != trigger)
    {
      NotificationChain msgs = null;
      if (trigger != null)
        msgs = ((InternalEObject)trigger).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.AGE__TRIGGER, null, msgs);
      if (newTrigger != null)
        msgs = ((InternalEObject)newTrigger).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.AGE__TRIGGER, null, msgs);
      msgs = basicSetTrigger(newTrigger, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.AGE__TRIGGER, newTrigger, newTrigger));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EventExpr getReaction()
  {
    return reaction;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetReaction(EventExpr newReaction, NotificationChain msgs)
  {
    EventExpr oldReaction = reaction;
    reaction = newReaction;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ContractSpecPackage.AGE__REACTION, oldReaction, newReaction);
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
  public void setReaction(EventExpr newReaction)
  {
    if (newReaction != reaction)
    {
      NotificationChain msgs = null;
      if (reaction != null)
        msgs = ((InternalEObject)reaction).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.AGE__REACTION, null, msgs);
      if (newReaction != null)
        msgs = ((InternalEObject)newReaction).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.AGE__REACTION, null, msgs);
      msgs = basicSetReaction(newReaction, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.AGE__REACTION, newReaction, newReaction));
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
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ContractSpecPackage.AGE__INTERVAL, oldInterval, newInterval);
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
        msgs = ((InternalEObject)interval).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.AGE__INTERVAL, null, msgs);
      if (newInterval != null)
        msgs = ((InternalEObject)newInterval).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.AGE__INTERVAL, null, msgs);
      msgs = basicSetInterval(newInterval, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.AGE__INTERVAL, newInterval, newInterval));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int getN()
  {
    return n;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setN(int newN)
  {
    int oldN = n;
    n = newN;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.AGE__N, oldN, n));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int getOutOf()
  {
    return outOf;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setOutOf(int newOutOf)
  {
    int oldOutOf = outOf;
    outOf = newOutOf;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.AGE__OUT_OF, oldOutOf, outOf));
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
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ContractSpecPackage.AGE__CLOCK, oldClock, clock));
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
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.AGE__CLOCK, oldClock, clock));
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
      case ContractSpecPackage.AGE__TRIGGER:
        return basicSetTrigger(null, msgs);
      case ContractSpecPackage.AGE__REACTION:
        return basicSetReaction(null, msgs);
      case ContractSpecPackage.AGE__INTERVAL:
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
      case ContractSpecPackage.AGE__TRIGGER:
        return getTrigger();
      case ContractSpecPackage.AGE__REACTION:
        return getReaction();
      case ContractSpecPackage.AGE__INTERVAL:
        return getInterval();
      case ContractSpecPackage.AGE__N:
        return getN();
      case ContractSpecPackage.AGE__OUT_OF:
        return getOutOf();
      case ContractSpecPackage.AGE__CLOCK:
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
      case ContractSpecPackage.AGE__TRIGGER:
        setTrigger((EventExpr)newValue);
        return;
      case ContractSpecPackage.AGE__REACTION:
        setReaction((EventExpr)newValue);
        return;
      case ContractSpecPackage.AGE__INTERVAL:
        setInterval((Interval)newValue);
        return;
      case ContractSpecPackage.AGE__N:
        setN((Integer)newValue);
        return;
      case ContractSpecPackage.AGE__OUT_OF:
        setOutOf((Integer)newValue);
        return;
      case ContractSpecPackage.AGE__CLOCK:
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
      case ContractSpecPackage.AGE__TRIGGER:
        setTrigger((EventExpr)null);
        return;
      case ContractSpecPackage.AGE__REACTION:
        setReaction((EventExpr)null);
        return;
      case ContractSpecPackage.AGE__INTERVAL:
        setInterval((Interval)null);
        return;
      case ContractSpecPackage.AGE__N:
        setN(N_EDEFAULT);
        return;
      case ContractSpecPackage.AGE__OUT_OF:
        setOutOf(OUT_OF_EDEFAULT);
        return;
      case ContractSpecPackage.AGE__CLOCK:
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
      case ContractSpecPackage.AGE__TRIGGER:
        return trigger != null;
      case ContractSpecPackage.AGE__REACTION:
        return reaction != null;
      case ContractSpecPackage.AGE__INTERVAL:
        return interval != null;
      case ContractSpecPackage.AGE__N:
        return n != N_EDEFAULT;
      case ContractSpecPackage.AGE__OUT_OF:
        return outOf != OUT_OF_EDEFAULT;
      case ContractSpecPackage.AGE__CLOCK:
        return clock != null;
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
    result.append(" (n: ");
    result.append(n);
    result.append(", outOf: ");
    result.append(outOf);
    result.append(')');
    return result.toString();
  }

} //AgeImpl
