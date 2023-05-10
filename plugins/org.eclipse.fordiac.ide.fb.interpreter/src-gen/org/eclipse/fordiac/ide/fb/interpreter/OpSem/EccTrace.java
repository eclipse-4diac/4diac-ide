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
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;

/** <!-- begin-user-doc --> A representation of the model object '<em><b>Ecc Trace</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.EccTrace#getTransitions <em>Transitions</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getEccTrace()
 * @model
 * @generated */
public interface EccTrace extends Trace {
	/** Returns the value of the '<em><b>Transitions</b></em>' reference list. The list contents are of type
	 * {@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition}. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Transitions</em>' reference list.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getEccTrace_Transitions()
	 * @model
	 * @generated */
	EList<ECTransition> getTransitions();

} // EccTrace
