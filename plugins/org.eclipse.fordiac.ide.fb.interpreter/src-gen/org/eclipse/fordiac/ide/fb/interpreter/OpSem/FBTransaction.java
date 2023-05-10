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
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/** <!-- begin-user-doc --> A representation of the model object '<em><b>FB Transaction</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction#getOutputEventOccurrences <em>Output Event
 * Occurrences</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction#getInputVariables <em>Input
 * Variables</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction#getTrace <em>Trace</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getFBTransaction()
 * @model
 * @generated */
public interface FBTransaction extends Transaction {
	/** Returns the value of the '<em><b>Output Event Occurrences</b></em>' containment reference list. The list
	 * contents are of type {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence}. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Output Event Occurrences</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getFBTransaction_OutputEventOccurrences()
	 * @model containment="true" resolveProxies="true"
	 * @generated */
	EList<EventOccurrence> getOutputEventOccurrences();

	/** Returns the value of the '<em><b>Input Variables</b></em>' reference list. The list contents are of type
	 * {@link org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration}. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @return the value of the '<em>Input Variables</em>' reference list.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getFBTransaction_InputVariables()
	 * @model
	 * @generated */
	EList<VarDeclaration> getInputVariables();

	/** Returns the value of the '<em><b>Trace</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the value of the '<em>Trace</em>' containment reference.
	 * @see #setTrace(Trace)
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getFBTransaction_Trace()
	 * @model containment="true" resolveProxies="true"
	 * @generated */
	Trace getTrace();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction#getTrace
	 * <em>Trace</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Trace</em>' containment reference.
	 * @see #getTrace()
	 * @generated */
	void setTrace(Trace value);

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @model
	 * @generated */
	void process();

} // FBTransaction
