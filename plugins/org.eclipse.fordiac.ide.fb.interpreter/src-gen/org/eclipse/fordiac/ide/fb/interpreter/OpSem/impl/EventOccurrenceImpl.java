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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Event
 * Occurrence</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.EventOccurrenceImpl#getEvent
 * <em>Event</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.EventOccurrenceImpl#isActive
 * <em>Active</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.EventOccurrenceImpl#isIgnored
 * <em>Ignored</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.EventOccurrenceImpl#getFbRuntime
 * <em>Fb Runtime</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.EventOccurrenceImpl#getCreatedTransactions
 * <em>Created Transactions</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.EventOccurrenceImpl#getParentFB
 * <em>Parent FB</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EventOccurrenceImpl extends MinimalEObjectImpl.Container implements EventOccurrence {
	/**
	 * The cached value of the '{@link #getEvent() <em>Event</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getEvent()
	 * @generated
	 * @ordered
	 */
	protected Event event;

	/**
	 * The default value of the '{@link #isActive() <em>Active</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #isActive()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ACTIVE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isActive() <em>Active</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #isActive()
	 * @generated
	 * @ordered
	 */
	protected boolean active = ACTIVE_EDEFAULT;

	/**
	 * The default value of the '{@link #isIgnored() <em>Ignored</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #isIgnored()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IGNORED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIgnored() <em>Ignored</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #isIgnored()
	 * @generated
	 * @ordered
	 */
	protected boolean ignored = IGNORED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFbRuntime() <em>Fb Runtime</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getFbRuntime()
	 * @generated
	 * @ordered
	 */
	protected FBRuntimeAbstract fbRuntime;

	/**
	 * The cached value of the '{@link #getCreatedTransactions() <em>Created
	 * Transactions</em>}' reference list. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @see #getCreatedTransactions()
	 * @generated
	 * @ordered
	 */
	protected EList<Transaction> createdTransactions;

	/**
	 * The cached value of the '{@link #getParentFB() <em>Parent FB</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getParentFB()
	 * @generated
	 * @ordered
	 */
	protected FBNetworkElement parentFB;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected EventOccurrenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OperationalSemanticsPackage.Literals.EVENT_OCCURRENCE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Event getEvent() {
		if (event != null && event.eIsProxy()) {
			InternalEObject oldEvent = (InternalEObject) event;
			event = (Event) eResolveProxy(oldEvent);
			if (event != oldEvent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							OperationalSemanticsPackage.EVENT_OCCURRENCE__EVENT, oldEvent, event));
			}
		}
		return event;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public Event basicGetEvent() {
		return event;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setEvent(Event newEvent) {
		Event oldEvent = event;
		event = newEvent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OperationalSemanticsPackage.EVENT_OCCURRENCE__EVENT,
					oldEvent, event));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isActive() {
		return active;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setActive(boolean newActive) {
		boolean oldActive = active;
		active = newActive;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OperationalSemanticsPackage.EVENT_OCCURRENCE__ACTIVE,
					oldActive, active));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isIgnored() {
		return ignored;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setIgnored(boolean newIgnored) {
		boolean oldIgnored = ignored;
		ignored = newIgnored;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OperationalSemanticsPackage.EVENT_OCCURRENCE__IGNORED,
					oldIgnored, ignored));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public FBRuntimeAbstract getFbRuntime() {
		if (fbRuntime != null && fbRuntime.eIsProxy()) {
			InternalEObject oldFbRuntime = (InternalEObject) fbRuntime;
			fbRuntime = (FBRuntimeAbstract) eResolveProxy(oldFbRuntime);
			if (fbRuntime != oldFbRuntime) {
				InternalEObject newFbRuntime = (InternalEObject) fbRuntime;
				NotificationChain msgs = oldFbRuntime.eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - OperationalSemanticsPackage.EVENT_OCCURRENCE__FB_RUNTIME, null, null);
				if (newFbRuntime.eInternalContainer() == null) {
					msgs = newFbRuntime.eInverseAdd(this,
							EOPPOSITE_FEATURE_BASE - OperationalSemanticsPackage.EVENT_OCCURRENCE__FB_RUNTIME, null,
							msgs);
				}
				if (msgs != null)
					msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							OperationalSemanticsPackage.EVENT_OCCURRENCE__FB_RUNTIME, oldFbRuntime, fbRuntime));
			}
		}
		return fbRuntime;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public FBRuntimeAbstract basicGetFbRuntime() {
		return fbRuntime;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetFbRuntime(FBRuntimeAbstract newFbRuntime, NotificationChain msgs) {
		FBRuntimeAbstract oldFbRuntime = fbRuntime;
		fbRuntime = newFbRuntime;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					OperationalSemanticsPackage.EVENT_OCCURRENCE__FB_RUNTIME, oldFbRuntime, newFbRuntime);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setFbRuntime(FBRuntimeAbstract newFbRuntime) {
		if (newFbRuntime != fbRuntime) {
			NotificationChain msgs = null;
			if (fbRuntime != null)
				msgs = ((InternalEObject) fbRuntime).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - OperationalSemanticsPackage.EVENT_OCCURRENCE__FB_RUNTIME, null, msgs);
			if (newFbRuntime != null)
				msgs = ((InternalEObject) newFbRuntime).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - OperationalSemanticsPackage.EVENT_OCCURRENCE__FB_RUNTIME, null, msgs);
			msgs = basicSetFbRuntime(newFbRuntime, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationalSemanticsPackage.EVENT_OCCURRENCE__FB_RUNTIME, newFbRuntime, newFbRuntime));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<Transaction> getCreatedTransactions() {
		if (createdTransactions == null) {
			createdTransactions = new EObjectResolvingEList<>(Transaction.class, this,
					OperationalSemanticsPackage.EVENT_OCCURRENCE__CREATED_TRANSACTIONS);
		}
		return createdTransactions;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public FBNetworkElement getParentFB() {
		if (parentFB != null && parentFB.eIsProxy()) {
			InternalEObject oldParentFB = (InternalEObject) parentFB;
			parentFB = (FBNetworkElement) eResolveProxy(oldParentFB);
			if (parentFB != oldParentFB) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							OperationalSemanticsPackage.EVENT_OCCURRENCE__PARENT_FB, oldParentFB, parentFB));
			}
		}
		return parentFB;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public FBNetworkElement basicGetParentFB() {
		return parentFB;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setParentFB(FBNetworkElement newParentFB) {
		FBNetworkElement oldParentFB = parentFB;
		parentFB = newParentFB;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationalSemanticsPackage.EVENT_OCCURRENCE__PARENT_FB, oldParentFB, parentFB));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case OperationalSemanticsPackage.EVENT_OCCURRENCE__FB_RUNTIME:
			return basicSetFbRuntime(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case OperationalSemanticsPackage.EVENT_OCCURRENCE__EVENT:
			if (resolve)
				return getEvent();
			return basicGetEvent();
		case OperationalSemanticsPackage.EVENT_OCCURRENCE__ACTIVE:
			return isActive();
		case OperationalSemanticsPackage.EVENT_OCCURRENCE__IGNORED:
			return isIgnored();
		case OperationalSemanticsPackage.EVENT_OCCURRENCE__FB_RUNTIME:
			if (resolve)
				return getFbRuntime();
			return basicGetFbRuntime();
		case OperationalSemanticsPackage.EVENT_OCCURRENCE__CREATED_TRANSACTIONS:
			return getCreatedTransactions();
		case OperationalSemanticsPackage.EVENT_OCCURRENCE__PARENT_FB:
			if (resolve)
				return getParentFB();
			return basicGetParentFB();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case OperationalSemanticsPackage.EVENT_OCCURRENCE__EVENT:
			setEvent((Event) newValue);
			return;
		case OperationalSemanticsPackage.EVENT_OCCURRENCE__ACTIVE:
			setActive((Boolean) newValue);
			return;
		case OperationalSemanticsPackage.EVENT_OCCURRENCE__IGNORED:
			setIgnored((Boolean) newValue);
			return;
		case OperationalSemanticsPackage.EVENT_OCCURRENCE__FB_RUNTIME:
			setFbRuntime((FBRuntimeAbstract) newValue);
			return;
		case OperationalSemanticsPackage.EVENT_OCCURRENCE__CREATED_TRANSACTIONS:
			getCreatedTransactions().clear();
			getCreatedTransactions().addAll((Collection<? extends Transaction>) newValue);
			return;
		case OperationalSemanticsPackage.EVENT_OCCURRENCE__PARENT_FB:
			setParentFB((FBNetworkElement) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case OperationalSemanticsPackage.EVENT_OCCURRENCE__EVENT:
			setEvent((Event) null);
			return;
		case OperationalSemanticsPackage.EVENT_OCCURRENCE__ACTIVE:
			setActive(ACTIVE_EDEFAULT);
			return;
		case OperationalSemanticsPackage.EVENT_OCCURRENCE__IGNORED:
			setIgnored(IGNORED_EDEFAULT);
			return;
		case OperationalSemanticsPackage.EVENT_OCCURRENCE__FB_RUNTIME:
			setFbRuntime((FBRuntimeAbstract) null);
			return;
		case OperationalSemanticsPackage.EVENT_OCCURRENCE__CREATED_TRANSACTIONS:
			getCreatedTransactions().clear();
			return;
		case OperationalSemanticsPackage.EVENT_OCCURRENCE__PARENT_FB:
			setParentFB((FBNetworkElement) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case OperationalSemanticsPackage.EVENT_OCCURRENCE__EVENT:
			return event != null;
		case OperationalSemanticsPackage.EVENT_OCCURRENCE__ACTIVE:
			return active != ACTIVE_EDEFAULT;
		case OperationalSemanticsPackage.EVENT_OCCURRENCE__IGNORED:
			return ignored != IGNORED_EDEFAULT;
		case OperationalSemanticsPackage.EVENT_OCCURRENCE__FB_RUNTIME:
			return fbRuntime != null;
		case OperationalSemanticsPackage.EVENT_OCCURRENCE__CREATED_TRANSACTIONS:
			return createdTransactions != null && !createdTransactions.isEmpty();
		case OperationalSemanticsPackage.EVENT_OCCURRENCE__PARENT_FB:
			return parentFB != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (active: "); //$NON-NLS-1$
		result.append(active);
		result.append(", ignored: "); //$NON-NLS-1$
		result.append(ignored);
		result.append(')');
		return result.toString();
	}

} // EventOccurrenceImpl
