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
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Transaction</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.TransactionImpl#getInputEventOccurrence
 * <em>Input Event Occurrence</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.TransactionImpl#getParentEO
 * <em>Parent EO</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.TransactionImpl#getDuration
 * <em>Duration</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class TransactionImpl extends MinimalEObjectImpl.Container implements Transaction {
	/**
	 * The cached value of the '{@link #getInputEventOccurrence() <em>Input Event
	 * Occurrence</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see #getInputEventOccurrence()
	 * @generated
	 * @ordered
	 */
	protected EventOccurrence inputEventOccurrence;

	/**
	 * The cached value of the '{@link #getParentEO() <em>Parent EO</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getParentEO()
	 * @generated
	 * @ordered
	 */
	protected EventOccurrence parentEO;

	/**
	 * The default value of the '{@link #getDuration() <em>Duration</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getDuration()
	 * @generated
	 * @ordered
	 */
	protected static final long DURATION_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getDuration() <em>Duration</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getDuration()
	 * @generated
	 * @ordered
	 */
	protected long duration = DURATION_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected TransactionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OperationalSemanticsPackage.Literals.TRANSACTION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EventOccurrence getInputEventOccurrence() {
		if (inputEventOccurrence != null && inputEventOccurrence.eIsProxy()) {
			InternalEObject oldInputEventOccurrence = (InternalEObject) inputEventOccurrence;
			inputEventOccurrence = (EventOccurrence) eResolveProxy(oldInputEventOccurrence);
			if (inputEventOccurrence != oldInputEventOccurrence) {
				InternalEObject newInputEventOccurrence = (InternalEObject) inputEventOccurrence;
				NotificationChain msgs = oldInputEventOccurrence.eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - OperationalSemanticsPackage.TRANSACTION__INPUT_EVENT_OCCURRENCE, null,
						null);
				if (newInputEventOccurrence.eInternalContainer() == null) {
					msgs = newInputEventOccurrence.eInverseAdd(this,
							EOPPOSITE_FEATURE_BASE - OperationalSemanticsPackage.TRANSACTION__INPUT_EVENT_OCCURRENCE,
							null, msgs);
				}
				if (msgs != null) {
					msgs.dispatch();
				}
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							OperationalSemanticsPackage.TRANSACTION__INPUT_EVENT_OCCURRENCE, oldInputEventOccurrence,
							inputEventOccurrence));
				}
			}
		}
		return inputEventOccurrence;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public EventOccurrence basicGetInputEventOccurrence() {
		return inputEventOccurrence;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetInputEventOccurrence(EventOccurrence newInputEventOccurrence,
			NotificationChain msgs) {
		EventOccurrence oldInputEventOccurrence = inputEventOccurrence;
		inputEventOccurrence = newInputEventOccurrence;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					OperationalSemanticsPackage.TRANSACTION__INPUT_EVENT_OCCURRENCE, oldInputEventOccurrence,
					newInputEventOccurrence);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setInputEventOccurrence(EventOccurrence newInputEventOccurrence) {
		if (newInputEventOccurrence != inputEventOccurrence) {
			NotificationChain msgs = null;
			if (inputEventOccurrence != null) {
				msgs = ((InternalEObject) inputEventOccurrence).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - OperationalSemanticsPackage.TRANSACTION__INPUT_EVENT_OCCURRENCE, null,
						msgs);
			}
			if (newInputEventOccurrence != null) {
				msgs = ((InternalEObject) newInputEventOccurrence).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - OperationalSemanticsPackage.TRANSACTION__INPUT_EVENT_OCCURRENCE, null,
						msgs);
			}
			msgs = basicSetInputEventOccurrence(newInputEventOccurrence, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationalSemanticsPackage.TRANSACTION__INPUT_EVENT_OCCURRENCE, newInputEventOccurrence,
					newInputEventOccurrence));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EventOccurrence getParentEO() {
		if (parentEO != null && parentEO.eIsProxy()) {
			InternalEObject oldParentEO = (InternalEObject) parentEO;
			parentEO = (EventOccurrence) eResolveProxy(oldParentEO);
			if (parentEO != oldParentEO) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							OperationalSemanticsPackage.TRANSACTION__PARENT_EO, oldParentEO, parentEO));
				}
			}
		}
		return parentEO;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public EventOccurrence basicGetParentEO() {
		return parentEO;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetParentEO(EventOccurrence newParentEO, NotificationChain msgs) {
		EventOccurrence oldParentEO = parentEO;
		parentEO = newParentEO;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					OperationalSemanticsPackage.TRANSACTION__PARENT_EO, oldParentEO, newParentEO);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setParentEO(EventOccurrence newParentEO) {
		if (newParentEO != parentEO) {
			NotificationChain msgs = null;
			if (parentEO != null) {
				msgs = ((InternalEObject) parentEO).eInverseRemove(this,
						OperationalSemanticsPackage.EVENT_OCCURRENCE__CREATED_TRANSACTIONS, EventOccurrence.class,
						msgs);
			}
			if (newParentEO != null) {
				msgs = ((InternalEObject) newParentEO).eInverseAdd(this,
						OperationalSemanticsPackage.EVENT_OCCURRENCE__CREATED_TRANSACTIONS, EventOccurrence.class,
						msgs);
			}
			msgs = basicSetParentEO(newParentEO, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, OperationalSemanticsPackage.TRANSACTION__PARENT_EO,
					newParentEO, newParentEO));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public long getDuration() {
		return duration;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setDuration(long newDuration) {
		long oldDuration = duration;
		duration = newDuration;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, OperationalSemanticsPackage.TRANSACTION__DURATION,
					oldDuration, duration));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case OperationalSemanticsPackage.TRANSACTION__PARENT_EO:
			if (parentEO != null) {
				msgs = ((InternalEObject) parentEO).eInverseRemove(this,
						OperationalSemanticsPackage.EVENT_OCCURRENCE__CREATED_TRANSACTIONS, EventOccurrence.class,
						msgs);
			}
			return basicSetParentEO((EventOccurrence) otherEnd, msgs);
		default:
			return super.eInverseAdd(otherEnd, featureID, msgs);
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case OperationalSemanticsPackage.TRANSACTION__INPUT_EVENT_OCCURRENCE:
			return basicSetInputEventOccurrence(null, msgs);
		case OperationalSemanticsPackage.TRANSACTION__PARENT_EO:
			return basicSetParentEO(null, msgs);
		default:
			return super.eInverseRemove(otherEnd, featureID, msgs);
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
		case OperationalSemanticsPackage.TRANSACTION__INPUT_EVENT_OCCURRENCE:
			if (resolve) {
				return getInputEventOccurrence();
			}
			return basicGetInputEventOccurrence();
		case OperationalSemanticsPackage.TRANSACTION__PARENT_EO:
			if (resolve) {
				return getParentEO();
			}
			return basicGetParentEO();
		case OperationalSemanticsPackage.TRANSACTION__DURATION:
			return getDuration();
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
		case OperationalSemanticsPackage.TRANSACTION__INPUT_EVENT_OCCURRENCE:
			setInputEventOccurrence((EventOccurrence) newValue);
			return;
		case OperationalSemanticsPackage.TRANSACTION__PARENT_EO:
			setParentEO((EventOccurrence) newValue);
			return;
		case OperationalSemanticsPackage.TRANSACTION__DURATION:
			setDuration((Long) newValue);
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
		case OperationalSemanticsPackage.TRANSACTION__INPUT_EVENT_OCCURRENCE:
			setInputEventOccurrence((EventOccurrence) null);
			return;
		case OperationalSemanticsPackage.TRANSACTION__PARENT_EO:
			setParentEO((EventOccurrence) null);
			return;
		case OperationalSemanticsPackage.TRANSACTION__DURATION:
			setDuration(DURATION_EDEFAULT);
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
		case OperationalSemanticsPackage.TRANSACTION__INPUT_EVENT_OCCURRENCE:
			return inputEventOccurrence != null;
		case OperationalSemanticsPackage.TRANSACTION__PARENT_EO:
			return parentEO != null;
		case OperationalSemanticsPackage.TRANSACTION__DURATION:
			return duration != DURATION_EDEFAULT;
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
		result.append(" (duration: "); //$NON-NLS-1$
		result.append(duration);
		result.append(')');
		return result.toString();
	}

} // TransactionImpl
