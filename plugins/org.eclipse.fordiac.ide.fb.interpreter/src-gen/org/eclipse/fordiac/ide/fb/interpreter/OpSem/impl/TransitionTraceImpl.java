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
package org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.TransitionTrace;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Transition Trace</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.TransitionTraceImpl#getSourceState
 * <em>Source State</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.TransitionTraceImpl#getDestinationState
 * <em>Destination State</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.TransitionTraceImpl#getCondEvent
 * <em>Cond Event</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.TransitionTraceImpl#getCondExpression
 * <em>Cond Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TransitionTraceImpl extends MinimalEObjectImpl.Container implements TransitionTrace {
	/**
	 * The default value of the '{@link #getSourceState() <em>Source State</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getSourceState()
	 * @generated
	 * @ordered
	 */
	protected static final String SOURCE_STATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSourceState() <em>Source State</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getSourceState()
	 * @generated
	 * @ordered
	 */
	protected String sourceState = SOURCE_STATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getDestinationState() <em>Destination
	 * State</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getDestinationState()
	 * @generated
	 * @ordered
	 */
	protected static final String DESTINATION_STATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDestinationState() <em>Destination
	 * State</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getDestinationState()
	 * @generated
	 * @ordered
	 */
	protected String destinationState = DESTINATION_STATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getCondEvent() <em>Cond Event</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getCondEvent()
	 * @generated
	 * @ordered
	 */
	protected static final String COND_EVENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCondEvent() <em>Cond Event</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getCondEvent()
	 * @generated
	 * @ordered
	 */
	protected String condEvent = COND_EVENT_EDEFAULT;

	/**
	 * The default value of the '{@link #getCondExpression() <em>Cond
	 * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getCondExpression()
	 * @generated
	 * @ordered
	 */
	protected static final String COND_EXPRESSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCondExpression() <em>Cond
	 * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getCondExpression()
	 * @generated
	 * @ordered
	 */
	protected String condExpression = COND_EXPRESSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected TransitionTraceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OperationalSemanticsPackage.Literals.TRANSITION_TRACE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getSourceState() {
		return sourceState;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setSourceState(String newSourceState) {
		String oldSourceState = sourceState;
		sourceState = newSourceState;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationalSemanticsPackage.TRANSITION_TRACE__SOURCE_STATE, oldSourceState, sourceState));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getDestinationState() {
		return destinationState;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setDestinationState(String newDestinationState) {
		String oldDestinationState = destinationState;
		destinationState = newDestinationState;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationalSemanticsPackage.TRANSITION_TRACE__DESTINATION_STATE, oldDestinationState,
					destinationState));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getCondEvent() {
		return condEvent;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setCondEvent(String newCondEvent) {
		String oldCondEvent = condEvent;
		condEvent = newCondEvent;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationalSemanticsPackage.TRANSITION_TRACE__COND_EVENT, oldCondEvent, condEvent));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getCondExpression() {
		return condExpression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setCondExpression(String newCondExpression) {
		String oldCondExpression = condExpression;
		condExpression = newCondExpression;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationalSemanticsPackage.TRANSITION_TRACE__COND_EXPRESSION, oldCondExpression, condExpression));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case OperationalSemanticsPackage.TRANSITION_TRACE__SOURCE_STATE:
			return getSourceState();
		case OperationalSemanticsPackage.TRANSITION_TRACE__DESTINATION_STATE:
			return getDestinationState();
		case OperationalSemanticsPackage.TRANSITION_TRACE__COND_EVENT:
			return getCondEvent();
		case OperationalSemanticsPackage.TRANSITION_TRACE__COND_EXPRESSION:
			return getCondExpression();
		default:
			return super.eGet(featureID, resolve, coreType);
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case OperationalSemanticsPackage.TRANSITION_TRACE__SOURCE_STATE:
			setSourceState((String) newValue);
			return;
		case OperationalSemanticsPackage.TRANSITION_TRACE__DESTINATION_STATE:
			setDestinationState((String) newValue);
			return;
		case OperationalSemanticsPackage.TRANSITION_TRACE__COND_EVENT:
			setCondEvent((String) newValue);
			return;
		case OperationalSemanticsPackage.TRANSITION_TRACE__COND_EXPRESSION:
			setCondExpression((String) newValue);
			return;
		default:
			super.eSet(featureID, newValue);
			return;
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case OperationalSemanticsPackage.TRANSITION_TRACE__SOURCE_STATE:
			setSourceState(SOURCE_STATE_EDEFAULT);
			return;
		case OperationalSemanticsPackage.TRANSITION_TRACE__DESTINATION_STATE:
			setDestinationState(DESTINATION_STATE_EDEFAULT);
			return;
		case OperationalSemanticsPackage.TRANSITION_TRACE__COND_EVENT:
			setCondEvent(COND_EVENT_EDEFAULT);
			return;
		case OperationalSemanticsPackage.TRANSITION_TRACE__COND_EXPRESSION:
			setCondExpression(COND_EXPRESSION_EDEFAULT);
			return;
		default:
			super.eUnset(featureID);
			return;
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case OperationalSemanticsPackage.TRANSITION_TRACE__SOURCE_STATE:
			return SOURCE_STATE_EDEFAULT == null ? sourceState != null : !SOURCE_STATE_EDEFAULT.equals(sourceState);
		case OperationalSemanticsPackage.TRANSITION_TRACE__DESTINATION_STATE:
			return DESTINATION_STATE_EDEFAULT == null ? destinationState != null
					: !DESTINATION_STATE_EDEFAULT.equals(destinationState);
		case OperationalSemanticsPackage.TRANSITION_TRACE__COND_EVENT:
			return COND_EVENT_EDEFAULT == null ? condEvent != null : !COND_EVENT_EDEFAULT.equals(condEvent);
		case OperationalSemanticsPackage.TRANSITION_TRACE__COND_EXPRESSION:
			return COND_EXPRESSION_EDEFAULT == null ? condExpression != null
					: !COND_EXPRESSION_EDEFAULT.equals(condExpression);
		default:
			return super.eIsSet(featureID);
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (sourceState: "); //$NON-NLS-1$
		result.append(sourceState);
		result.append(", destinationState: "); //$NON-NLS-1$
		result.append(destinationState);
		result.append(", condEvent: "); //$NON-NLS-1$
		result.append(condEvent);
		result.append(", condExpression: "); //$NON-NLS-1$
		result.append(condExpression);
		result.append(')');
		return result.toString();
	}

} // TransitionTraceImpl
