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
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.CausalFuncDeclImpl#getP1 <em>P1</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.impl.CausalFuncDeclImpl#getP2 <em>P2</em>}</li>
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
  protected static final String FUNC_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFuncName() <em>Func Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFuncName()
   * @generated
   * @ordered
   */
  protected String funcName = FUNC_NAME_EDEFAULT;

  /**
   * The cached value of the '{@link #getP1() <em>P1</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getP1()
   * @generated
   * @ordered
   */
  protected Port p1;

  /**
   * The cached value of the '{@link #getP2() <em>P2</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getP2()
   * @generated
   * @ordered
   */
  protected Port p2;

  /**
   * The default value of the '{@link #getRelation() <em>Relation</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRelation()
   * @generated
   * @ordered
   */
  protected static final String RELATION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getRelation() <em>Relation</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRelation()
   * @generated
   * @ordered
   */
  protected String relation = RELATION_EDEFAULT;

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
  public String getFuncName()
  {
    return funcName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setFuncName(String newFuncName)
  {
    String oldFuncName = funcName;
    funcName = newFuncName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.CAUSAL_FUNC_DECL__FUNC_NAME, oldFuncName, funcName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Port getP1()
  {
    if (p1 != null && p1.eIsProxy())
    {
      InternalEObject oldP1 = (InternalEObject)p1;
      p1 = (Port)eResolveProxy(oldP1);
      if (p1 != oldP1)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ContractSpecPackage.CAUSAL_FUNC_DECL__P1, oldP1, p1));
      }
    }
    return p1;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Port basicGetP1()
  {
    return p1;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setP1(Port newP1)
  {
    Port oldP1 = p1;
    p1 = newP1;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.CAUSAL_FUNC_DECL__P1, oldP1, p1));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Port getP2()
  {
    if (p2 != null && p2.eIsProxy())
    {
      InternalEObject oldP2 = (InternalEObject)p2;
      p2 = (Port)eResolveProxy(oldP2);
      if (p2 != oldP2)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, ContractSpecPackage.CAUSAL_FUNC_DECL__P2, oldP2, p2));
      }
    }
    return p2;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Port basicGetP2()
  {
    return p2;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setP2(Port newP2)
  {
    Port oldP2 = p2;
    p2 = newP2;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, ContractSpecPackage.CAUSAL_FUNC_DECL__P2, oldP2, p2));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getRelation()
  {
    return relation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setRelation(String newRelation)
  {
    String oldRelation = relation;
    relation = newRelation;
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
      case ContractSpecPackage.CAUSAL_FUNC_DECL__P1:
        if (resolve) return getP1();
        return basicGetP1();
      case ContractSpecPackage.CAUSAL_FUNC_DECL__P2:
        if (resolve) return getP2();
        return basicGetP2();
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
        setFuncName((String)newValue);
        return;
      case ContractSpecPackage.CAUSAL_FUNC_DECL__P1:
        setP1((Port)newValue);
        return;
      case ContractSpecPackage.CAUSAL_FUNC_DECL__P2:
        setP2((Port)newValue);
        return;
      case ContractSpecPackage.CAUSAL_FUNC_DECL__RELATION:
        setRelation((String)newValue);
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
      case ContractSpecPackage.CAUSAL_FUNC_DECL__P1:
        setP1((Port)null);
        return;
      case ContractSpecPackage.CAUSAL_FUNC_DECL__P2:
        setP2((Port)null);
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
        return FUNC_NAME_EDEFAULT == null ? funcName != null : !FUNC_NAME_EDEFAULT.equals(funcName);
      case ContractSpecPackage.CAUSAL_FUNC_DECL__P1:
        return p1 != null;
      case ContractSpecPackage.CAUSAL_FUNC_DECL__P2:
        return p2 != null;
      case ContractSpecPackage.CAUSAL_FUNC_DECL__RELATION:
        return RELATION_EDEFAULT == null ? relation != null : !RELATION_EDEFAULT.equals(relation);
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
