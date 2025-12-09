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
import org.eclipse.fordiac.ide.contractSpec.Jitter;
import org.eclipse.fordiac.ide.contractSpec.Offset;
import org.eclipse.fordiac.ide.contractSpec.RepetitionOptions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Repetition Options</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.RepetitionOptionsImpl#getJitter <em>Jitter</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.RepetitionOptionsImpl#getOffset <em>Offset</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RepetitionOptionsImpl extends MinimalEObjectImpl.Container implements RepetitionOptions
{
  /**
   * The cached value of the '{@link #getJitter() <em>Jitter</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getJitter()
   * @generated
   * @ordered
   */
  protected Jitter jitter;

  /**
   * The cached value of the '{@link #getOffset() <em>Offset</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOffset()
   * @generated
   * @ordered
   */
  protected Offset offset;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected RepetitionOptionsImpl()
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
    return ContractSpecPackage.Literals.REPETITION_OPTIONS;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Jitter getJitter()
  {
    return jitter;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetJitter(Jitter newJitter, NotificationChain msgs)
  {
    Jitter oldJitter = jitter;
    jitter = newJitter;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ContractSpecPackage.REPETITION_OPTIONS__JITTER, oldJitter, newJitter);
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
  public void setJitter(Jitter newJitter)
  {
    if (newJitter != jitter)
    {
      NotificationChain msgs = null;
      if (jitter != null)
        msgs = ((InternalEObject)jitter).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.REPETITION_OPTIONS__JITTER, null, msgs);
      if (newJitter != null)
        msgs = ((InternalEObject)newJitter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.REPETITION_OPTIONS__JITTER, null, msgs);
      msgs = basicSetJitter(newJitter, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.REPETITION_OPTIONS__JITTER, newJitter, newJitter));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Offset getOffset()
  {
    return offset;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetOffset(Offset newOffset, NotificationChain msgs)
  {
    Offset oldOffset = offset;
    offset = newOffset;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ContractSpecPackage.REPETITION_OPTIONS__OFFSET, oldOffset, newOffset);
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
  public void setOffset(Offset newOffset)
  {
    if (newOffset != offset)
    {
      NotificationChain msgs = null;
      if (offset != null)
        msgs = ((InternalEObject)offset).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.REPETITION_OPTIONS__OFFSET, null, msgs);
      if (newOffset != null)
        msgs = ((InternalEObject)newOffset).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ContractSpecPackage.REPETITION_OPTIONS__OFFSET, null, msgs);
      msgs = basicSetOffset(newOffset, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.REPETITION_OPTIONS__OFFSET, newOffset, newOffset));
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
      case ContractSpecPackage.REPETITION_OPTIONS__JITTER:
        return basicSetJitter(null, msgs);
      case ContractSpecPackage.REPETITION_OPTIONS__OFFSET:
        return basicSetOffset(null, msgs);
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
      case ContractSpecPackage.REPETITION_OPTIONS__JITTER:
        return getJitter();
      case ContractSpecPackage.REPETITION_OPTIONS__OFFSET:
        return getOffset();
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
      case ContractSpecPackage.REPETITION_OPTIONS__JITTER:
        setJitter((Jitter)newValue);
        return;
      case ContractSpecPackage.REPETITION_OPTIONS__OFFSET:
        setOffset((Offset)newValue);
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
      case ContractSpecPackage.REPETITION_OPTIONS__JITTER:
        setJitter((Jitter)null);
        return;
      case ContractSpecPackage.REPETITION_OPTIONS__OFFSET:
        setOffset((Offset)null);
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
      case ContractSpecPackage.REPETITION_OPTIONS__JITTER:
        return jitter != null;
      case ContractSpecPackage.REPETITION_OPTIONS__OFFSET:
        return offset != null;
    }
    return super.eIsSet(featureID);
  }

} //RepetitionOptionsImpl
