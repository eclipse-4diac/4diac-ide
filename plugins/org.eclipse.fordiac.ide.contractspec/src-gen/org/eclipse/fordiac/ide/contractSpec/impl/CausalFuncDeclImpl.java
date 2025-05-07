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

import org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl;
import org.eclipse.fordiac.ide.contractSpec.CausalFuncName;
import org.eclipse.fordiac.ide.contractSpec.CausalRelation;
import org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage;
import org.eclipse.fordiac.ide.contractSpec.Port;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Causal Func Decl</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.CausalFuncDeclImpl#getFuncName <em>Func Name</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.CausalFuncDeclImpl#getPort1 <em>Port1</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.CausalFuncDeclImpl#getPort2 <em>Port2</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.CausalFuncDeclImpl#getRelation <em>Relation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CausalFuncDeclImpl extends TimeSpecImpl implements CausalFuncDecl
{
  /**
   * The default value of the '{@link #getFuncName() <em>Func Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFuncName()
   * @generated
   * @ordered
   */
  protected static final CausalFuncName FUNC_NAME_EDEFAULT = CausalFuncName.REACTION;

  /**
   * The cached value of the '{@link #getFuncName() <em>Func Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFuncName()
   * @generated
   * @ordered
   */
  protected CausalFuncName funcName = FUNC_NAME_EDEFAULT;

  /**
   * The cached value of the '{@link #getPort1() <em>Port1</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPort1()
   * @generated
   * @ordered
   */
  protected Port port1;

  /**
   * The cached value of the '{@link #getPort2() <em>Port2</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPort2()
   * @generated
   * @ordered
   */
  protected Port port2;

  /**
   * The default value of the '{@link #getRelation() <em>Relation</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRelation()
   * @generated
   * @ordered
   */
  protected static final CausalRelation RELATION_EDEFAULT = CausalRelation.FIFO;

  /**
   * The cached value of the '{@link #getRelation() <em>Relation</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRelation()
   * @generated
   * @ordered
   */
  protected CausalRelation relation = RELATION_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected CausalFuncDeclImpl()
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
    return ContractSpecPackage.Literals.CAUSAL_FUNC_DECL;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public CausalFuncName getFuncName()
  {
    return funcName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setFuncName(CausalFuncName newFuncName)
  {
    CausalFuncName oldFuncName = funcName;
    funcName = newFuncName == null ? FUNC_NAME_EDEFAULT : newFuncName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.CAUSAL_FUNC_DECL__FUNC_NAME, oldFuncName, funcName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Port getPort1()
  {
    if (port1 != null && port1.eIsProxy())
    {
      InternalEObject oldPort1 = (InternalEObject)port1;
      port1 = (Port)eResolveProxy(oldPort1);
      if (port1 != oldPort1)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ContractSpecPackage.CAUSAL_FUNC_DECL__PORT1, oldPort1, port1));
      }
    }
    return port1;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Port basicGetPort1()
  {
    return port1;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setPort1(Port newPort1)
  {
    Port oldPort1 = port1;
    port1 = newPort1;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.CAUSAL_FUNC_DECL__PORT1, oldPort1, port1));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Port getPort2()
  {
    if (port2 != null && port2.eIsProxy())
    {
      InternalEObject oldPort2 = (InternalEObject)port2;
      port2 = (Port)eResolveProxy(oldPort2);
      if (port2 != oldPort2)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ContractSpecPackage.CAUSAL_FUNC_DECL__PORT2, oldPort2, port2));
      }
    }
    return port2;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Port basicGetPort2()
  {
    return port2;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setPort2(Port newPort2)
  {
    Port oldPort2 = port2;
    port2 = newPort2;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.CAUSAL_FUNC_DECL__PORT2, oldPort2, port2));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public CausalRelation getRelation()
  {
    return relation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setRelation(CausalRelation newRelation)
  {
    CausalRelation oldRelation = relation;
    relation = newRelation == null ? RELATION_EDEFAULT : newRelation;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.CAUSAL_FUNC_DECL__RELATION, oldRelation, relation));
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
      case ContractSpecPackage.CAUSAL_FUNC_DECL__FUNC_NAME:
        return getFuncName();
      case ContractSpecPackage.CAUSAL_FUNC_DECL__PORT1:
        if (resolve) return getPort1();
        return basicGetPort1();
      case ContractSpecPackage.CAUSAL_FUNC_DECL__PORT2:
        if (resolve) return getPort2();
        return basicGetPort2();
      case ContractSpecPackage.CAUSAL_FUNC_DECL__RELATION:
        return getRelation();
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
      case ContractSpecPackage.CAUSAL_FUNC_DECL__FUNC_NAME:
        setFuncName((CausalFuncName)newValue);
        return;
      case ContractSpecPackage.CAUSAL_FUNC_DECL__PORT1:
        setPort1((Port)newValue);
        return;
      case ContractSpecPackage.CAUSAL_FUNC_DECL__PORT2:
        setPort2((Port)newValue);
        return;
      case ContractSpecPackage.CAUSAL_FUNC_DECL__RELATION:
        setRelation((CausalRelation)newValue);
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
      case ContractSpecPackage.CAUSAL_FUNC_DECL__FUNC_NAME:
        setFuncName(FUNC_NAME_EDEFAULT);
        return;
      case ContractSpecPackage.CAUSAL_FUNC_DECL__PORT1:
        setPort1((Port)null);
        return;
      case ContractSpecPackage.CAUSAL_FUNC_DECL__PORT2:
        setPort2((Port)null);
        return;
      case ContractSpecPackage.CAUSAL_FUNC_DECL__RELATION:
        setRelation(RELATION_EDEFAULT);
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
      case ContractSpecPackage.CAUSAL_FUNC_DECL__FUNC_NAME:
        return funcName != FUNC_NAME_EDEFAULT;
      case ContractSpecPackage.CAUSAL_FUNC_DECL__PORT1:
        return port1 != null;
      case ContractSpecPackage.CAUSAL_FUNC_DECL__PORT2:
        return port2 != null;
      case ContractSpecPackage.CAUSAL_FUNC_DECL__RELATION:
        return relation != RELATION_EDEFAULT;
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
    result.append(" (funcName: ");
    result.append(funcName);
    result.append(", relation: ");
    result.append(relation);
    result.append(')');
    return result.toString();
  }

} //CausalFuncDeclImpl
