/**
 * *******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmendía, Bianca Wiesmayr
 *          - initial implementation and/or documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.fb.interpreter.OpSem;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/** <!-- begin-user-doc --> A representation of the model object '<em><b>Event Manager</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager#getTransactions <em>Transactions</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager#getReadyQueue <em>Ready Queue</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getEventManager()
 * @model annotation="http://www.eclipse.org/emf/2002/GenModel"
 * @generated */
public interface EventManager extends EObject {
	/** Returns the value of the '<em><b>Transactions</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction}. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @return the value of the '<em>Transactions</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getEventManager_Transactions()
	 * @model containment="true" resolveProxies="true"
	 * @generated */
	EList<Transaction> getTransactions();

	/** Returns the value of the '<em><b>Ready Queue</b></em>' reference list. The list contents are of type
	 * {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction}. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Ready Queue</em>' reference list.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getEventManager_ReadyQueue()
	 * @model
	 * @generated */
	EList<Transaction> getReadyQueue();

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @model
	 * @generated */
	void process();

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @model
	 * @generated */
	void processNetwork();

} // EventManager
