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

import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Basic FB
 * Type Runtime</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime#getBasicfbtype
 * <em>Basicfbtype</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime#getActiveState
 * <em>Active State</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getBasicFBTypeRuntime()
 * @model annotation="http://www.eclipse.org/emf/2002/GenModel"
 * @generated
 */
public interface BasicFBTypeRuntime extends FBRuntimeAbstract {
	/**
	 * Returns the value of the '<em><b>Basicfbtype</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Basicfbtype</em>' containment reference.
	 * @see #setBasicfbtype(BasicFBType)
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getBasicFBTypeRuntime_Basicfbtype()
	 * @model containment="true" resolveProxies="true" required="true"
	 * @generated
	 */
	BasicFBType getBasicfbtype();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime#getBasicfbtype
	 * <em>Basicfbtype</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @param value the new value of the '<em>Basicfbtype</em>' containment
	 *              reference.
	 * @see #getBasicfbtype()
	 * @generated
	 */
	void setBasicfbtype(BasicFBType value);

	/**
	 * Returns the value of the '<em><b>Active State</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Active State</em>' reference.
	 * @see #setActiveState(ECState)
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getBasicFBTypeRuntime_ActiveState()
	 * @model
	 * @generated
	 */
	ECState getActiveState();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime#getActiveState
	 * <em>Active State</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @param value the new value of the '<em>Active State</em>' reference.
	 * @see #getActiveState()
	 * @generated
	 */
	void setActiveState(ECState value);

} // BasicFBTypeRuntime
