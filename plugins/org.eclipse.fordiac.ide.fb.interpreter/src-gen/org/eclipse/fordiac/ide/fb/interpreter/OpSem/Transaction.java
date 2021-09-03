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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/** <!-- begin-user-doc --> A representation of the model object '<em><b>Transaction</b></em>'. <!-- end-user-doc -->
 *
 * <!-- begin-model-doc --> commit, roolback? <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction#getInputEventOccurrence <em>Input Event
 * Occurrence</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction#getOutputEventOccurences <em>Output Event
 * Occurences</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getTransaction()
 * @model
 * @generated */
public interface Transaction extends EObject {
	/** Returns the value of the '<em><b>Input Event Occurrence</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Input Event Occurrence</em>' containment reference.
	 * @see #setInputEventOccurrence(EventOccurrence)
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getTransaction_InputEventOccurrence()
	 * @model containment="true" resolveProxies="true" required="true"
	 * @generated */
	EventOccurrence getInputEventOccurrence();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction#getInputEventOccurrence
	 * <em>Input Event Occurrence</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Input Event Occurrence</em>' containment reference.
	 * @see #getInputEventOccurrence()
	 * @generated */
	void setInputEventOccurrence(EventOccurrence value);

	/** Returns the value of the '<em><b>Output Event Occurences</b></em>' containment reference list. The list contents
	 * are of type {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the value of the '<em>Output Event Occurences</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getTransaction_OutputEventOccurences()
	 * @model containment="true" resolveProxies="true"
	 * @generated */
	EList<EventOccurrence> getOutputEventOccurences();

} // Transaction
