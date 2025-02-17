/**
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
 */
package org.eclipse.fordiac.ide.contractSpec;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.contractSpec.ContractSpecFactory
 * @model kind="package"
 * @generated
 */
public interface ContractSpecPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "contractSpec";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.eclipse.org/fordiac/ide/ContractSpec";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "contractSpec";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  ContractSpecPackage eINSTANCE = org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl.init();

  /**
   * The meta object id for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.ModelImpl <em>Model</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.fordiac.ide.contractSpec.impl.ModelImpl
   * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getModel()
   * @generated
   */
  int MODEL = 0;

  /**
   * The feature id for the '<em><b>Time Spec</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL__TIME_SPEC = 0;

  /**
   * The number of structural features of the '<em>Model</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MODEL_FEATURE_COUNT = 1;


  /**
   * Returns the meta object for class '{@link org.eclipse.fordiac.ide.contractSpec.Model <em>Model</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Model</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Model
   * @generated
   */
  EClass getModel();

  /**
   * Returns the meta object for the attribute list '{@link org.eclipse.fordiac.ide.contractSpec.Model#getTimeSpec <em>Time Spec</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute list '<em>Time Spec</em>'.
   * @see org.eclipse.fordiac.ide.contractSpec.Model#getTimeSpec()
   * @see #getModel()
   * @generated
   */
  EAttribute getModel_TimeSpec();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  ContractSpecFactory getContractSpecFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link org.eclipse.fordiac.ide.contractSpec.impl.ModelImpl <em>Model</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.fordiac.ide.contractSpec.impl.ModelImpl
     * @see org.eclipse.fordiac.ide.contractSpec.impl.ContractSpecPackageImpl#getModel()
     * @generated
     */
    EClass MODEL = eINSTANCE.getModel();

    /**
     * The meta object literal for the '<em><b>Time Spec</b></em>' attribute list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MODEL__TIME_SPEC = eINSTANCE.getModel_TimeSpec();

  }

} //ContractSpecPackage
