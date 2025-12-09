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
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage;
import org.eclipse.fordiac.ide.contractSpec.EventSpec;
import org.eclipse.fordiac.ide.contractSpec.Port;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Event Spec</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.EventSpecImpl#getPort <em>Port</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.EventSpecImpl#getEventValue <em>Event Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EventSpecImpl extends MinimalEObjectImpl.Container implements EventSpec
{
  /**
   * The cached value of the '{@link #getPort() <em>Port</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPort()
   * @generated
   * @ordered
   */
  protected Port port;

  /**
   * The default value of the '{@link #getEventValue() <em>Event Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEventValue()
   * @generated
   * @ordered
   */
  protected static final String EVENT_VALUE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getEventValue() <em>Event Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEventValue()
   * @generated
   * @ordered
   */
  protected String eventValue = EVENT_VALUE_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected EventSpecImpl()
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
    return ContractSpecPackage.Literals.EVENT_SPEC;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Port getPort()
  {
    if (port != null && port.eIsProxy())
    {
      InternalEObject oldPort = (InternalEObject)port;
      port = (Port)eResolveProxy(oldPort);
      if (port != oldPort)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ContractSpecPackage.EVENT_SPEC__PORT, oldPort, port));
      }
    }
    return port;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Port basicGetPort()
  {
    return port;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setPort(Port newPort)
  {
    Port oldPort = port;
    port = newPort;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.EVENT_SPEC__PORT, oldPort, port));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getEventValue()
  {
    return eventValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setEventValue(String newEventValue)
  {
    String oldEventValue = eventValue;
    eventValue = newEventValue;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.EVENT_SPEC__EVENT_VALUE, oldEventValue, eventValue));
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
      case ContractSpecPackage.EVENT_SPEC__PORT:
        if (resolve) return getPort();
        return basicGetPort();
      case ContractSpecPackage.EVENT_SPEC__EVENT_VALUE:
        return getEventValue();
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
      case ContractSpecPackage.EVENT_SPEC__PORT:
        setPort((Port)newValue);
        return;
      case ContractSpecPackage.EVENT_SPEC__EVENT_VALUE:
        setEventValue((String)newValue);
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
      case ContractSpecPackage.EVENT_SPEC__PORT:
        setPort((Port)null);
        return;
      case ContractSpecPackage.EVENT_SPEC__EVENT_VALUE:
        setEventValue(EVENT_VALUE_EDEFAULT);
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
      case ContractSpecPackage.EVENT_SPEC__PORT:
        return port != null;
      case ContractSpecPackage.EVENT_SPEC__EVENT_VALUE:
        return EVENT_VALUE_EDEFAULT == null ? eventValue != null : !EVENT_VALUE_EDEFAULT.equals(eventValue);
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
    result.append(" (eventValue: ");
    result.append(eventValue);
    result.append(')');
    return result.toString();
  }

} //EventSpecImpl
