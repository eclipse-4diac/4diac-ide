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
package org.eclipse.fordiac.ide.contractSpec;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Causal Func Decl</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl#getFuncName <em>Func Name</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl#getPort1 <em>Port1</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl#getPort2 <em>Port2</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl#getRelation <em>Relation</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getCausalFuncDecl()
 * @model
 * @generated
 */
public interface CausalFuncDecl extends TimeSpec
{
  /**
   * Returns the value of the '<em><b>Func Name</b></em>' attribute.
   * The literals are from the enumeration {@link org.eclipse.fordiac.ide.contractSpec.CausalFuncName}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Func Name</em>' attribute.
   * @see org.eclipse.fordiac.ide.contractSpec.CausalFuncName
   * @see #setFuncName(CausalFuncName)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getCausalFuncDecl_FuncName()
   * @model
   * @generated
   */
  CausalFuncName getFuncName();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl#getFuncName <em>Func Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Func Name</em>' attribute.
   * @see org.eclipse.fordiac.ide.contractSpec.CausalFuncName
   * @see #getFuncName()
   * @generated
   */
  void setFuncName(CausalFuncName value);

  /**
   * Returns the value of the '<em><b>Port1</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Port1</em>' reference.
   * @see #setPort1(Port)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getCausalFuncDecl_Port1()
   * @model
   * @generated
   */
  Port getPort1();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl#getPort1 <em>Port1</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Port1</em>' reference.
   * @see #getPort1()
   * @generated
   */
  void setPort1(Port value);

  /**
   * Returns the value of the '<em><b>Port2</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Port2</em>' reference.
   * @see #setPort2(Port)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getCausalFuncDecl_Port2()
   * @model
   * @generated
   */
  Port getPort2();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl#getPort2 <em>Port2</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Port2</em>' reference.
   * @see #getPort2()
   * @generated
   */
  void setPort2(Port value);

  /**
   * Returns the value of the '<em><b>Relation</b></em>' attribute.
   * The literals are from the enumeration {@link org.eclipse.fordiac.ide.contractSpec.CausalRelation}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Relation</em>' attribute.
   * @see org.eclipse.fordiac.ide.contractSpec.CausalRelation
   * @see #setRelation(CausalRelation)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getCausalFuncDecl_Relation()
   * @model
   * @generated
   */
  CausalRelation getRelation();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl#getRelation <em>Relation</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Relation</em>' attribute.
   * @see org.eclipse.fordiac.ide.contractSpec.CausalRelation
   * @see #getRelation()
   * @generated
   */
  void setRelation(CausalRelation value);

} // CausalFuncDecl
