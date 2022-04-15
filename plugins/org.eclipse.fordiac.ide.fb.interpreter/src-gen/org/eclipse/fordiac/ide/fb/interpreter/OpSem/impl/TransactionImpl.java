/**
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmend�a, Bianca Wiesmayr
 *          - initial implementation and/or documentation
 */
package org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;

/** <!-- begin-user-doc --> An implementation of the model object '<em><b>Transaction</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.TransactionImpl#getInputEventOccurrence <em>Input Event
 * Occurrence</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.TransactionImpl#getOutputEventOccurences <em>Output
 * Event Occurences</em>}</li>
 * </ul>
 *
 * @generated */
public class TransactionImpl extends MinimalEObjectImpl.Container implements Transaction {
	/** The cached value of the '{@link #getInputEventOccurrence() <em>Input Event Occurrence</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getInputEventOccurrence()
	 * @generated
	 * @ordered */
	protected EventOccurrence inputEventOccurrence;

	/** The cached value of the '{@link #getOutputEventOccurences() <em>Output Event Occurences</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getOutputEventOccurences()
	 * @generated
	 * @ordered */
	protected EList<EventOccurrence> outputEventOccurences;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	protected TransactionImpl() {
		super();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	protected EClass eStaticClass() {
		return OperationalSemanticsPackage.Literals.TRANSACTION;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
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
				if (msgs != null)
					msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							OperationalSemanticsPackage.TRANSACTION__INPUT_EVENT_OCCURRENCE, oldInputEventOccurrence,
							inputEventOccurrence));
			}
		}
		return inputEventOccurrence;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public EventOccurrence basicGetInputEventOccurrence() {
		return inputEventOccurrence;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public NotificationChain basicSetInputEventOccurrence(EventOccurrence newInputEventOccurrence,
			NotificationChain msgs) {
		EventOccurrence oldInputEventOccurrence = inputEventOccurrence;
		inputEventOccurrence = newInputEventOccurrence;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					OperationalSemanticsPackage.TRANSACTION__INPUT_EVENT_OCCURRENCE, oldInputEventOccurrence,
					newInputEventOccurrence);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void setInputEventOccurrence(EventOccurrence newInputEventOccurrence) {
		if (newInputEventOccurrence != inputEventOccurrence) {
			NotificationChain msgs = null;
			if (inputEventOccurrence != null)
				msgs = ((InternalEObject) inputEventOccurrence).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - OperationalSemanticsPackage.TRANSACTION__INPUT_EVENT_OCCURRENCE, null,
						msgs);
			if (newInputEventOccurrence != null)
				msgs = ((InternalEObject) newInputEventOccurrence).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - OperationalSemanticsPackage.TRANSACTION__INPUT_EVENT_OCCURRENCE, null,
						msgs);
			msgs = basicSetInputEventOccurrence(newInputEventOccurrence, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationalSemanticsPackage.TRANSACTION__INPUT_EVENT_OCCURRENCE, newInputEventOccurrence,
					newInputEventOccurrence));
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EList<EventOccurrence> getOutputEventOccurences() {
		if (outputEventOccurences == null) {
			outputEventOccurences = new EObjectContainmentEList.Resolving<>(EventOccurrence.class, this,
					OperationalSemanticsPackage.TRANSACTION__OUTPUT_EVENT_OCCURENCES);
		}
		return outputEventOccurences;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case OperationalSemanticsPackage.TRANSACTION__INPUT_EVENT_OCCURRENCE:
			return basicSetInputEventOccurrence(null, msgs);
		case OperationalSemanticsPackage.TRANSACTION__OUTPUT_EVENT_OCCURENCES:
			return ((InternalEList<?>) getOutputEventOccurences()).basicRemove(otherEnd, msgs);
		default:
			return super.eInverseRemove(otherEnd, featureID, msgs);
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case OperationalSemanticsPackage.TRANSACTION__INPUT_EVENT_OCCURRENCE:
			if (resolve)
				return getInputEventOccurrence();
			return basicGetInputEventOccurrence();
		case OperationalSemanticsPackage.TRANSACTION__OUTPUT_EVENT_OCCURENCES:
			return getOutputEventOccurences();
		default:
			return super.eGet(featureID, resolve, coreType);
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case OperationalSemanticsPackage.TRANSACTION__INPUT_EVENT_OCCURRENCE:
			setInputEventOccurrence((EventOccurrence) newValue);
			return;
		case OperationalSemanticsPackage.TRANSACTION__OUTPUT_EVENT_OCCURENCES:
			getOutputEventOccurences().clear();
			getOutputEventOccurences().addAll((Collection<? extends EventOccurrence>) newValue);
			return;
		default:
			super.eSet(featureID, newValue);
			return;
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case OperationalSemanticsPackage.TRANSACTION__INPUT_EVENT_OCCURRENCE:
			setInputEventOccurrence((EventOccurrence) null);
			return;
		case OperationalSemanticsPackage.TRANSACTION__OUTPUT_EVENT_OCCURENCES:
			getOutputEventOccurences().clear();
			return;
		default:
			super.eUnset(featureID);
			return;
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case OperationalSemanticsPackage.TRANSACTION__INPUT_EVENT_OCCURRENCE:
			return inputEventOccurrence != null;
		case OperationalSemanticsPackage.TRANSACTION__OUTPUT_EVENT_OCCURENCES:
			return outputEventOccurences != null && !outputEventOccurences.isEmpty();
		default:
			return super.eIsSet(featureID);
		}
	}

} // TransactionImpl
