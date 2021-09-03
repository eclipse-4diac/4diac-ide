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

import org.eclipse.fordiac.ide.model.libraryElement.FBType;

/** <!-- begin-user-doc --> A representation of the model object '<em><b>FB Type Runtime</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTypeRuntime#getFbtype <em>Fbtype</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getFBTypeRuntime()
 * @model
 * @generated */
public interface FBTypeRuntime extends FBRuntimeAbstract {
	/** Returns the value of the '<em><b>Fbtype</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the value of the '<em>Fbtype</em>' containment reference.
	 * @see #setFbtype(FBType)
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getFBTypeRuntime_Fbtype()
	 * @model containment="true" resolveProxies="true" required="true"
	 * @generated */
	FBType getFbtype();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTypeRuntime#getFbtype
	 * <em>Fbtype</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Fbtype</em>' containment reference.
	 * @see #getFbtype()
	 * @generated */
	void setFbtype(FBType value);

} // FBTypeRuntime
