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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Transition Trace</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.TransitionTrace#getSourceState
 * <em>Source State</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.TransitionTrace#getDestinationState
 * <em>Destination State</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.TransitionTrace#getCondEvent
 * <em>Cond Event</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.TransitionTrace#getCondExpression
 * <em>Cond Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getTransitionTrace()
 * @model
 * @generated
 */
public interface TransitionTrace extends EObject {
	/**
	 * Returns the value of the '<em><b>Source State</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Source State</em>' attribute.
	 * @see #setSourceState(String)
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getTransitionTrace_SourceState()
	 * @model
	 * @generated
	 */
	String getSourceState();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.TransitionTrace#getSourceState
	 * <em>Source State</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @param value the new value of the '<em>Source State</em>' attribute.
	 * @see #getSourceState()
	 * @generated
	 */
	void setSourceState(String value);

	/**
	 * Returns the value of the '<em><b>Destination State</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Destination State</em>' attribute.
	 * @see #setDestinationState(String)
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getTransitionTrace_DestinationState()
	 * @model
	 * @generated
	 */
	String getDestinationState();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.TransitionTrace#getDestinationState
	 * <em>Destination State</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @param value the new value of the '<em>Destination State</em>' attribute.
	 * @see #getDestinationState()
	 * @generated
	 */
	void setDestinationState(String value);

	/**
	 * Returns the value of the '<em><b>Cond Event</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Cond Event</em>' attribute.
	 * @see #setCondEvent(String)
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getTransitionTrace_CondEvent()
	 * @model
	 * @generated
	 */
	String getCondEvent();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.TransitionTrace#getCondEvent
	 * <em>Cond Event</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @param value the new value of the '<em>Cond Event</em>' attribute.
	 * @see #getCondEvent()
	 * @generated
	 */
	void setCondEvent(String value);

	/**
	 * Returns the value of the '<em><b>Cond Expression</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Cond Expression</em>' attribute.
	 * @see #setCondExpression(String)
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getTransitionTrace_CondExpression()
	 * @model
	 * @generated
	 */
	String getCondExpression();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.TransitionTrace#getCondExpression
	 * <em>Cond Expression</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @param value the new value of the '<em>Cond Expression</em>' attribute.
	 * @see #getCondExpression()
	 * @generated
	 */
	void setCondExpression(String value);

} // TransitionTrace
