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

import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;

/** <!-- begin-user-doc --> A representation of the model object '<em><b>Simple FB Type Runtime</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.SimpleFBTypeRuntime#getSimpleFBType <em>Simple FB
 * Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getSimpleFBTypeRuntime()
 * @model
 * @generated */
public interface SimpleFBTypeRuntime extends FBRuntimeAbstract {
	/** Returns the value of the '<em><b>Simple FB Type</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the value of the '<em>Simple FB Type</em>' containment reference.
	 * @see #setSimpleFBType(SimpleFBType)
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getSimpleFBTypeRuntime_SimpleFBType()
	 * @model containment="true" resolveProxies="true" required="true"
	 * @generated */
	SimpleFBType getSimpleFBType();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.SimpleFBTypeRuntime#getSimpleFBType
	 * <em>Simple FB Type</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Simple FB Type</em>' containment reference.
	 * @see #getSimpleFBType()
	 * @generated */
	void setSimpleFBType(SimpleFBType value);

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @model kind="operation" required="true"
	 * @generated */
	@Override
	SimpleFBType getModel();

} // SimpleFBTypeRuntime
