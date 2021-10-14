/**
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmendía, Bianca Wiesmayr
 *          - initial implementation and/or documentation
 */
package org.eclipse.fordiac.ide.fb.interpreter.OpSem;

import org.eclipse.emf.ecore.EFactory;

/** <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract class of
 * the model. <!-- end-user-doc -->
 *
 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage
 * @generated */
public interface OperationalSemanticsFactory extends EFactory {
	/** The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	OperationalSemanticsFactory eINSTANCE = org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.OperationalSemanticsFactoryImpl
			.init();

	/** Returns a new object of class '<em>Event Occurrence</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Event Occurrence</em>'.
	 * @generated */
	EventOccurrence createEventOccurrence();

	/** Returns a new object of class '<em>Event Manager</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Event Manager</em>'.
	 * @generated */
	EventManager createEventManager();

	/** Returns a new object of class '<em>Basic FB Type Runtime</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Basic FB Type Runtime</em>'.
	 * @generated */
	BasicFBTypeRuntime createBasicFBTypeRuntime();

	/** Returns a new object of class '<em>FB Type Runtime</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>FB Type Runtime</em>'.
	 * @generated */
	FBTypeRuntime createFBTypeRuntime();

	/** Returns a new object of class '<em>Transaction</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Transaction</em>'.
	 * @generated */
	Transaction createTransaction();

	/** Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the package supported by this factory.
	 * @generated */
	OperationalSemanticsPackage getOperationalSemanticsPackage();

} // OperationalSemanticsFactory
