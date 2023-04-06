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

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.fb.interpreter.mm.EventManagerUtils;

/** <!-- begin-user-doc --> An implementation of the model object '<em><b>Event Manager</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.EventManagerImpl#getTransactions
 * <em>Transactions</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.EventManagerImpl#getReadyQueue <em>Ready
 * Queue</em>}</li>
 * </ul>
 *
 * @generated */
public class EventManagerImpl extends MinimalEObjectImpl.Container implements EventManager {
	/** The cached value of the '{@link #getTransactions() <em>Transactions</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getTransactions()
	 * @generated
	 * @ordered */
	protected EList<Transaction> transactions;

	/** The cached value of the '{@link #getReadyQueue() <em>Ready Queue</em>}' reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getReadyQueue()
	 * @generated
	 * @ordered */
	protected EList<Transaction> readyQueue;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	protected EventManagerImpl() {
		super();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	protected EClass eStaticClass() {
		return OperationalSemanticsPackage.Literals.EVENT_MANAGER;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EList<Transaction> getTransactions() {
		if (transactions == null) {
			transactions = new EObjectContainmentEList.Resolving<>(Transaction.class, this,
					OperationalSemanticsPackage.EVENT_MANAGER__TRANSACTIONS);
		}
		return transactions;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EList<Transaction> getReadyQueue() {
		if (readyQueue == null) {
			readyQueue = new EObjectResolvingEList<>(Transaction.class, this,
					OperationalSemanticsPackage.EVENT_MANAGER__READY_QUEUE);
		}
		return readyQueue;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void process() {
		EventManagerUtils.process(this);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void processNetwork() {
		EventManagerUtils.processNetwork(this);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void process(final EventManager eventManager) {
		EventManagerUtils.process(this);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case OperationalSemanticsPackage.EVENT_MANAGER__TRANSACTIONS:
			return ((InternalEList<?>) getTransactions()).basicRemove(otherEnd, msgs);
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
		case OperationalSemanticsPackage.EVENT_MANAGER__TRANSACTIONS:
			return getTransactions();
		case OperationalSemanticsPackage.EVENT_MANAGER__READY_QUEUE:
			return getReadyQueue();
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
		case OperationalSemanticsPackage.EVENT_MANAGER__TRANSACTIONS:
			getTransactions().clear();
			getTransactions().addAll((Collection<? extends Transaction>) newValue);
			return;
		case OperationalSemanticsPackage.EVENT_MANAGER__READY_QUEUE:
			getReadyQueue().clear();
			getReadyQueue().addAll((Collection<? extends Transaction>) newValue);
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
		case OperationalSemanticsPackage.EVENT_MANAGER__TRANSACTIONS:
			getTransactions().clear();
			return;
		case OperationalSemanticsPackage.EVENT_MANAGER__READY_QUEUE:
			getReadyQueue().clear();
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
		case OperationalSemanticsPackage.EVENT_MANAGER__TRANSACTIONS:
			return transactions != null && !transactions.isEmpty();
		case OperationalSemanticsPackage.EVENT_MANAGER__READY_QUEUE:
			return readyQueue != null && !readyQueue.isEmpty();
		default:
			return super.eIsSet(featureID);
		}
	}

} // EventManagerImpl
