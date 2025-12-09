/**
 * *******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Martin Jobst
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.xmiexport.xmiexport;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportPackage
 * @generated
 */
public interface XMIExportFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	XMIExportFactory eINSTANCE = org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Attribute Values</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute Values</em>'.
	 * @generated
	 */
	XMIExportAttributeValues createXMIExportAttributeValues();

	/**
	 * Returns a new object of class '<em>Attribute Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute Value</em>'.
	 * @generated
	 */
	XMIExportAttributeValue createXMIExportAttributeValue();

	/**
	 * Returns a new object of class '<em>Initial Values</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Initial Values</em>'.
	 * @generated
	 */
	XMIExportInitialValues createXMIExportInitialValues();

	/**
	 * Returns a new object of class '<em>Initial Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Initial Value</em>'.
	 * @generated
	 */
	XMIExportInitialValue createXMIExportInitialValue();

	/**
	 * Returns a new object of class '<em>Type Declarations</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Type Declarations</em>'.
	 * @generated
	 */
	XMIExportTypeDeclarations createXMIExportTypeDeclarations();

	/**
	 * Returns a new object of class '<em>Type Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Type Declaration</em>'.
	 * @generated
	 */
	XMIExportTypeDeclaration createXMIExportTypeDeclaration();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	XMIExportPackage getXMIExportPackage();

} //XMIExportFactory
