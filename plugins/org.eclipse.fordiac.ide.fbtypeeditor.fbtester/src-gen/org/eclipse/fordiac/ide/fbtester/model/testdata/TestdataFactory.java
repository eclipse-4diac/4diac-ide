/*******************************************************************************
 * Copyright (c) 2012 Profactor GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtester.model.testdata;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.TestdataPackage
 * @generated
 */
public interface TestdataFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TestdataFactory eINSTANCE = org.eclipse.fordiac.ide.fbtester.model.testdata.impl.TestdataFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Test Data</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Test Data</em>'.
	 * @generated
	 */
	TestData createTestData();

	/**
	 * Returns a new object of class '<em>Valued Var Decl</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Valued Var Decl</em>'.
	 * @generated
	 */
	ValuedVarDecl createValuedVarDecl();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	TestdataPackage getTestdataPackage();

} //TestdataFactory
