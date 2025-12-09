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

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage;
import org.eclipse.fordiac.ide.contractSpec.TimeSpec;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Time Spec</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class TimeSpecImpl extends MinimalEObjectImpl.Container implements TimeSpec
{
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TimeSpecImpl()
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
    return ContractSpecPackage.Literals.TIME_SPEC;
  }

} //TimeSpecImpl
