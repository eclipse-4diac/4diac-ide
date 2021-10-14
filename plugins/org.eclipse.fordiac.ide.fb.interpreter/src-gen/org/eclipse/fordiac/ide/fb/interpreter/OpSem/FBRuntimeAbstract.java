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

/** <!-- begin-user-doc --> A representation of the model object '<em><b>FB Runtime Abstract</b></em>'. <!--
 * end-user-doc -->
 *
 * <!-- begin-model-doc --> How to manage to get the data? <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getFBRuntimeAbstract()
 * @model abstract="true"
 * @generated */
public interface FBRuntimeAbstract extends EObject {
	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @model
	 * @generated */
	EList<EventOccurrence> run();

} // FBRuntimeAbstract
