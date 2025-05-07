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
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl#getP1 <em>P1</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl#getP2 <em>P2</em>}</li>
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
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Func Name</em>' attribute.
   * @see #setFuncName(String)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getCausalFuncDecl_FuncName()
   * @model
   * @generated
   */
  String getFuncName();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl#getFuncName <em>Func Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Func Name</em>' attribute.
   * @see #getFuncName()
   * @generated
   */
  void setFuncName(String value);

  /**
   * Returns the value of the '<em><b>P1</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>P1</em>' reference.
   * @see #setP1(Port)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getCausalFuncDecl_P1()
   * @model
   * @generated
   */
  Port getP1();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl#getP1 <em>P1</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>P1</em>' reference.
   * @see #getP1()
   * @generated
   */
  void setP1(Port value);

  /**
   * Returns the value of the '<em><b>P2</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>P2</em>' reference.
   * @see #setP2(Port)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getCausalFuncDecl_P2()
   * @model
   * @generated
   */
  Port getP2();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl#getP2 <em>P2</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>P2</em>' reference.
   * @see #getP2()
   * @generated
   */
  void setP2(Port value);

  /**
   * Returns the value of the '<em><b>Relation</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Relation</em>' attribute.
   * @see #setRelation(String)
   * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage#getCausalFuncDecl_Relation()
   * @model
   * @generated
   */
  String getRelation();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl#getRelation <em>Relation</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Relation</em>' attribute.
   * @see #getRelation()
   * @generated
   */
  void setRelation(String value);

} // CausalFuncDecl
