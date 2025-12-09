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

import org.eclipse.fordiac.ide.contractSpec.ClockDefinition;
import org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage;
import org.eclipse.fordiac.ide.contractSpec.EventList;
import org.eclipse.fordiac.ide.contractSpec.Interval;
import org.eclipse.fordiac.ide.contractSpec.Repetition;
import org.eclipse.fordiac.ide.contractSpec.RepetitionOptions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Repetition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.RepetitionImpl#getEvents <em>Events</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.RepetitionImpl#getInterval <em>Interval</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.RepetitionImpl#getRepetitionOptions <em>Repetition Options</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.RepetitionImpl#getClock <em>Clock</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RepetitionImpl extends TimeSpecImpl implements Repetition
{
  /**
   * The cached value of the '{@link #getEvents() <em>Events</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEvents()
   * @generated
   * @ordered
   */
  protected EventList events;

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
   * The cached value of the '{@link #getRepetitionOptions() <em>Repetition Options</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRepetitionOptions()
   * @generated
   * @ordered
   */
  protected RepetitionOptions repetitionOptions;

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
  protected RepetitionImpl()
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
    return ContractSpecPackage.Literals.REPETITION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EventList getEvents()
  {
    return events;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetEvents(EventList newEvents, NotificationChain msgs)
  {
    EventList oldEvents = events;
    events = newEvents;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ContractSpecPackage.REPETITION__EVENTS, oldEvents, newEvents);
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
  public void setEvents(EventList newEvents)
  {
    if (newEvents != events)
    {
      NotificationChain msgs = null;
      if (events != null)
        msgs = ((InternalEObject)events).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.REPETITION__EVENTS, null, msgs);
      if (newEvents != null)
        msgs = ((InternalEObject)newEvents).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.REPETITION__EVENTS, null, msgs);
      msgs = basicSetEvents(newEvents, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.REPETITION__EVENTS, newEvents, newEvents));
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
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ContractSpecPackage.REPETITION__INTERVAL, oldInterval, newInterval);
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
        msgs = ((InternalEObject)interval).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.REPETITION__INTERVAL, null, msgs);
      if (newInterval != null)
        msgs = ((InternalEObject)newInterval).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.REPETITION__INTERVAL, null, msgs);
      msgs = basicSetInterval(newInterval, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.REPETITION__INTERVAL, newInterval, newInterval));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public RepetitionOptions getRepetitionOptions()
  {
    return repetitionOptions;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetRepetitionOptions(RepetitionOptions newRepetitionOptions, NotificationChain msgs)
  {
    RepetitionOptions oldRepetitionOptions = repetitionOptions;
    repetitionOptions = newRepetitionOptions;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ContractSpecPackage.REPETITION__REPETITION_OPTIONS, oldRepetitionOptions, newRepetitionOptions);
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
  public void setRepetitionOptions(RepetitionOptions newRepetitionOptions)
  {
    if (newRepetitionOptions != repetitionOptions)
    {
      NotificationChain msgs = null;
      if (repetitionOptions != null)
        msgs = ((InternalEObject)repetitionOptions).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.REPETITION__REPETITION_OPTIONS, null, msgs);
      if (newRepetitionOptions != null)
        msgs = ((InternalEObject)newRepetitionOptions).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.REPETITION__REPETITION_OPTIONS, null, msgs);
      msgs = basicSetRepetitionOptions(newRepetitionOptions, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.REPETITION__REPETITION_OPTIONS, newRepetitionOptions, newRepetitionOptions));
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
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ContractSpecPackage.REPETITION__CLOCK, oldClock, clock));
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
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.REPETITION__CLOCK, oldClock, clock));
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
      case ContractSpecPackage.REPETITION__EVENTS:
        return basicSetEvents(null, msgs);
      case ContractSpecPackage.REPETITION__INTERVAL:
        return basicSetInterval(null, msgs);
      case ContractSpecPackage.REPETITION__REPETITION_OPTIONS:
        return basicSetRepetitionOptions(null, msgs);
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
      case ContractSpecPackage.REPETITION__EVENTS:
        return getEvents();
      case ContractSpecPackage.REPETITION__INTERVAL:
        return getInterval();
      case ContractSpecPackage.REPETITION__REPETITION_OPTIONS:
        return getRepetitionOptions();
      case ContractSpecPackage.REPETITION__CLOCK:
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
      case ContractSpecPackage.REPETITION__EVENTS:
        setEvents((EventList)newValue);
        return;
      case ContractSpecPackage.REPETITION__INTERVAL:
        setInterval((Interval)newValue);
        return;
      case ContractSpecPackage.REPETITION__REPETITION_OPTIONS:
        setRepetitionOptions((RepetitionOptions)newValue);
        return;
      case ContractSpecPackage.REPETITION__CLOCK:
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
      case ContractSpecPackage.REPETITION__EVENTS:
        setEvents((EventList)null);
        return;
      case ContractSpecPackage.REPETITION__INTERVAL:
        setInterval((Interval)null);
        return;
      case ContractSpecPackage.REPETITION__REPETITION_OPTIONS:
        setRepetitionOptions((RepetitionOptions)null);
        return;
      case ContractSpecPackage.REPETITION__CLOCK:
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
      case ContractSpecPackage.REPETITION__EVENTS:
        return events != null;
      case ContractSpecPackage.REPETITION__INTERVAL:
        return interval != null;
      case ContractSpecPackage.REPETITION__REPETITION_OPTIONS:
        return repetitionOptions != null;
      case ContractSpecPackage.REPETITION__CLOCK:
        return clock != null;
    }
    return super.eIsSet(featureID);
  }

} //RepetitionImpl
