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
import org.eclipse.fordiac.ide.contractSpec.EventExpr;
import org.eclipse.fordiac.ide.contractSpec.Interval;
import org.eclipse.fordiac.ide.contractSpec.Reaction;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Reaction</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.ReactionImpl#getInput <em>Input</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.ReactionImpl#getOutput <em>Output</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.ReactionImpl#getInterval <em>Interval</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.ReactionImpl#isOnce <em>Once</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.ReactionImpl#getN <em>N</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.ReactionImpl#getOutOf <em>Out Of</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.ReactionImpl#getClock <em>Clock</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ReactionImpl extends TimeSpecImpl implements Reaction
{
  /**
   * The cached value of the '{@link #getInput() <em>Input</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInput()
   * @generated
   * @ordered
   */
  protected EventExpr input;

  /**
   * The cached value of the '{@link #getOutput() <em>Output</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOutput()
   * @generated
   * @ordered
   */
  protected EventExpr output;

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
   * The default value of the '{@link #isOnce() <em>Once</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isOnce()
   * @generated
   * @ordered
   */
  protected static final boolean ONCE_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isOnce() <em>Once</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isOnce()
   * @generated
   * @ordered
   */
  protected boolean once = ONCE_EDEFAULT;

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
  protected ReactionImpl()
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
    return ContractSpecPackage.Literals.REACTION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EventExpr getInput()
  {
    return input;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetInput(EventExpr newInput, NotificationChain msgs)
  {
    EventExpr oldInput = input;
    input = newInput;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ContractSpecPackage.REACTION__INPUT, oldInput, newInput);
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
  public void setInput(EventExpr newInput)
  {
    if (newInput != input)
    {
      NotificationChain msgs = null;
      if (input != null)
        msgs = ((InternalEObject)input).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.REACTION__INPUT, null, msgs);
      if (newInput != null)
        msgs = ((InternalEObject)newInput).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.REACTION__INPUT, null, msgs);
      msgs = basicSetInput(newInput, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.REACTION__INPUT, newInput, newInput));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EventExpr getOutput()
  {
    return output;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetOutput(EventExpr newOutput, NotificationChain msgs)
  {
    EventExpr oldOutput = output;
    output = newOutput;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ContractSpecPackage.REACTION__OUTPUT, oldOutput, newOutput);
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
  public void setOutput(EventExpr newOutput)
  {
    if (newOutput != output)
    {
      NotificationChain msgs = null;
      if (output != null)
        msgs = ((InternalEObject)output).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.REACTION__OUTPUT, null, msgs);
      if (newOutput != null)
        msgs = ((InternalEObject)newOutput).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.REACTION__OUTPUT, null, msgs);
      msgs = basicSetOutput(newOutput, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.REACTION__OUTPUT, newOutput, newOutput));
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
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ContractSpecPackage.REACTION__INTERVAL, oldInterval, newInterval);
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
        msgs = ((InternalEObject)interval).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.REACTION__INTERVAL, null, msgs);
      if (newInterval != null)
        msgs = ((InternalEObject)newInterval).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.REACTION__INTERVAL, null, msgs);
      msgs = basicSetInterval(newInterval, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.REACTION__INTERVAL, newInterval, newInterval));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isOnce()
  {
    return once;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setOnce(boolean newOnce)
  {
    boolean oldOnce = once;
    once = newOnce;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.REACTION__ONCE, oldOnce, once));
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
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.REACTION__N, oldN, n));
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
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.REACTION__OUT_OF, oldOutOf, outOf));
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
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ContractSpecPackage.REACTION__CLOCK, oldClock, clock));
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
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.REACTION__CLOCK, oldClock, clock));
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
      case ContractSpecPackage.REACTION__INPUT:
        return basicSetInput(null, msgs);
      case ContractSpecPackage.REACTION__OUTPUT:
        return basicSetOutput(null, msgs);
      case ContractSpecPackage.REACTION__INTERVAL:
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
      case ContractSpecPackage.REACTION__INPUT:
        return getInput();
      case ContractSpecPackage.REACTION__OUTPUT:
        return getOutput();
      case ContractSpecPackage.REACTION__INTERVAL:
        return getInterval();
      case ContractSpecPackage.REACTION__ONCE:
        return isOnce();
      case ContractSpecPackage.REACTION__N:
        return getN();
      case ContractSpecPackage.REACTION__OUT_OF:
        return getOutOf();
      case ContractSpecPackage.REACTION__CLOCK:
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
      case ContractSpecPackage.REACTION__INPUT:
        setInput((EventExpr)newValue);
        return;
      case ContractSpecPackage.REACTION__OUTPUT:
        setOutput((EventExpr)newValue);
        return;
      case ContractSpecPackage.REACTION__INTERVAL:
        setInterval((Interval)newValue);
        return;
      case ContractSpecPackage.REACTION__ONCE:
        setOnce((Boolean)newValue);
        return;
      case ContractSpecPackage.REACTION__N:
        setN((Integer)newValue);
        return;
      case ContractSpecPackage.REACTION__OUT_OF:
        setOutOf((Integer)newValue);
        return;
      case ContractSpecPackage.REACTION__CLOCK:
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
      case ContractSpecPackage.REACTION__INPUT:
        setInput((EventExpr)null);
        return;
      case ContractSpecPackage.REACTION__OUTPUT:
        setOutput((EventExpr)null);
        return;
      case ContractSpecPackage.REACTION__INTERVAL:
        setInterval((Interval)null);
        return;
      case ContractSpecPackage.REACTION__ONCE:
        setOnce(ONCE_EDEFAULT);
        return;
      case ContractSpecPackage.REACTION__N:
        setN(N_EDEFAULT);
        return;
      case ContractSpecPackage.REACTION__OUT_OF:
        setOutOf(OUT_OF_EDEFAULT);
        return;
      case ContractSpecPackage.REACTION__CLOCK:
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
      case ContractSpecPackage.REACTION__INPUT:
        return input != null;
      case ContractSpecPackage.REACTION__OUTPUT:
        return output != null;
      case ContractSpecPackage.REACTION__INTERVAL:
        return interval != null;
      case ContractSpecPackage.REACTION__ONCE:
        return once != ONCE_EDEFAULT;
      case ContractSpecPackage.REACTION__N:
        return n != N_EDEFAULT;
      case ContractSpecPackage.REACTION__OUT_OF:
        return outOf != OUT_OF_EDEFAULT;
      case ContractSpecPackage.REACTION__CLOCK:
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
    result.append(" (once: ");
    result.append(once);
    result.append(", n: ");
    result.append(n);
    result.append(", outOf: ");
    result.append(outOf);
    result.append(')');
    return result.toString();
  }

} //ReactionImpl
