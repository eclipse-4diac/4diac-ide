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
import org.eclipse.fordiac.ide.contractSpec.Interval;
import org.eclipse.fordiac.ide.contractSpec.TimeExpr;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Clock Definition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.ClockDefinitionImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.ClockDefinitionImpl#getResolution <em>Resolution</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.ClockDefinitionImpl#getSkew <em>Skew</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.ClockDefinitionImpl#getDrift <em>Drift</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.ClockDefinitionImpl#getMaxdiff <em>Maxdiff</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ClockDefinitionImpl extends TimeSpecImpl implements ClockDefinition
{
  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * The cached value of the '{@link #getResolution() <em>Resolution</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getResolution()
   * @generated
   * @ordered
   */
  protected TimeExpr resolution;

  /**
   * The cached value of the '{@link #getSkew() <em>Skew</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSkew()
   * @generated
   * @ordered
   */
  protected TimeExpr skew;

  /**
   * The cached value of the '{@link #getDrift() <em>Drift</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDrift()
   * @generated
   * @ordered
   */
  protected Interval drift;

  /**
   * The cached value of the '{@link #getMaxdiff() <em>Maxdiff</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMaxdiff()
   * @generated
   * @ordered
   */
  protected TimeExpr maxdiff;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ClockDefinitionImpl()
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
    return ContractSpecPackage.Literals.CLOCK_DEFINITION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setName(String newName)
  {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.CLOCK_DEFINITION__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public TimeExpr getResolution()
  {
    return resolution;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetResolution(TimeExpr newResolution, NotificationChain msgs)
  {
    TimeExpr oldResolution = resolution;
    resolution = newResolution;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ContractSpecPackage.CLOCK_DEFINITION__RESOLUTION, oldResolution, newResolution);
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
  public void setResolution(TimeExpr newResolution)
  {
    if (newResolution != resolution)
    {
      NotificationChain msgs = null;
      if (resolution != null)
        msgs = ((InternalEObject)resolution).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.CLOCK_DEFINITION__RESOLUTION, null, msgs);
      if (newResolution != null)
        msgs = ((InternalEObject)newResolution).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.CLOCK_DEFINITION__RESOLUTION, null, msgs);
      msgs = basicSetResolution(newResolution, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.CLOCK_DEFINITION__RESOLUTION, newResolution, newResolution));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public TimeExpr getSkew()
  {
    return skew;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetSkew(TimeExpr newSkew, NotificationChain msgs)
  {
    TimeExpr oldSkew = skew;
    skew = newSkew;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ContractSpecPackage.CLOCK_DEFINITION__SKEW, oldSkew, newSkew);
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
  public void setSkew(TimeExpr newSkew)
  {
    if (newSkew != skew)
    {
      NotificationChain msgs = null;
      if (skew != null)
        msgs = ((InternalEObject)skew).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.CLOCK_DEFINITION__SKEW, null, msgs);
      if (newSkew != null)
        msgs = ((InternalEObject)newSkew).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.CLOCK_DEFINITION__SKEW, null, msgs);
      msgs = basicSetSkew(newSkew, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.CLOCK_DEFINITION__SKEW, newSkew, newSkew));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Interval getDrift()
  {
    return drift;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetDrift(Interval newDrift, NotificationChain msgs)
  {
    Interval oldDrift = drift;
    drift = newDrift;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ContractSpecPackage.CLOCK_DEFINITION__DRIFT, oldDrift, newDrift);
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
  public void setDrift(Interval newDrift)
  {
    if (newDrift != drift)
    {
      NotificationChain msgs = null;
      if (drift != null)
        msgs = ((InternalEObject)drift).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.CLOCK_DEFINITION__DRIFT, null, msgs);
      if (newDrift != null)
        msgs = ((InternalEObject)newDrift).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.CLOCK_DEFINITION__DRIFT, null, msgs);
      msgs = basicSetDrift(newDrift, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.CLOCK_DEFINITION__DRIFT, newDrift, newDrift));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public TimeExpr getMaxdiff()
  {
    return maxdiff;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetMaxdiff(TimeExpr newMaxdiff, NotificationChain msgs)
  {
    TimeExpr oldMaxdiff = maxdiff;
    maxdiff = newMaxdiff;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ContractSpecPackage.CLOCK_DEFINITION__MAXDIFF, oldMaxdiff, newMaxdiff);
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
  public void setMaxdiff(TimeExpr newMaxdiff)
  {
    if (newMaxdiff != maxdiff)
    {
      NotificationChain msgs = null;
      if (maxdiff != null)
        msgs = ((InternalEObject)maxdiff).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.CLOCK_DEFINITION__MAXDIFF, null, msgs);
      if (newMaxdiff != null)
        msgs = ((InternalEObject)newMaxdiff).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.CLOCK_DEFINITION__MAXDIFF, null, msgs);
      msgs = basicSetMaxdiff(newMaxdiff, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.CLOCK_DEFINITION__MAXDIFF, newMaxdiff, newMaxdiff));
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
      case ContractSpecPackage.CLOCK_DEFINITION__RESOLUTION:
        return basicSetResolution(null, msgs);
      case ContractSpecPackage.CLOCK_DEFINITION__SKEW:
        return basicSetSkew(null, msgs);
      case ContractSpecPackage.CLOCK_DEFINITION__DRIFT:
        return basicSetDrift(null, msgs);
      case ContractSpecPackage.CLOCK_DEFINITION__MAXDIFF:
        return basicSetMaxdiff(null, msgs);
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
      case ContractSpecPackage.CLOCK_DEFINITION__NAME:
        return getName();
      case ContractSpecPackage.CLOCK_DEFINITION__RESOLUTION:
        return getResolution();
      case ContractSpecPackage.CLOCK_DEFINITION__SKEW:
        return getSkew();
      case ContractSpecPackage.CLOCK_DEFINITION__DRIFT:
        return getDrift();
      case ContractSpecPackage.CLOCK_DEFINITION__MAXDIFF:
        return getMaxdiff();
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
      case ContractSpecPackage.CLOCK_DEFINITION__NAME:
        setName((String)newValue);
        return;
      case ContractSpecPackage.CLOCK_DEFINITION__RESOLUTION:
        setResolution((TimeExpr)newValue);
        return;
      case ContractSpecPackage.CLOCK_DEFINITION__SKEW:
        setSkew((TimeExpr)newValue);
        return;
      case ContractSpecPackage.CLOCK_DEFINITION__DRIFT:
        setDrift((Interval)newValue);
        return;
      case ContractSpecPackage.CLOCK_DEFINITION__MAXDIFF:
        setMaxdiff((TimeExpr)newValue);
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
      case ContractSpecPackage.CLOCK_DEFINITION__NAME:
        setName(NAME_EDEFAULT);
        return;
      case ContractSpecPackage.CLOCK_DEFINITION__RESOLUTION:
        setResolution((TimeExpr)null);
        return;
      case ContractSpecPackage.CLOCK_DEFINITION__SKEW:
        setSkew((TimeExpr)null);
        return;
      case ContractSpecPackage.CLOCK_DEFINITION__DRIFT:
        setDrift((Interval)null);
        return;
      case ContractSpecPackage.CLOCK_DEFINITION__MAXDIFF:
        setMaxdiff((TimeExpr)null);
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
      case ContractSpecPackage.CLOCK_DEFINITION__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case ContractSpecPackage.CLOCK_DEFINITION__RESOLUTION:
        return resolution != null;
      case ContractSpecPackage.CLOCK_DEFINITION__SKEW:
        return skew != null;
      case ContractSpecPackage.CLOCK_DEFINITION__DRIFT:
        return drift != null;
      case ContractSpecPackage.CLOCK_DEFINITION__MAXDIFF:
        return maxdiff != null;
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
    result.append(" (name: ");
    result.append(name);
    result.append(')');
    return result.toString();
  }

} //ClockDefinitionImpl
