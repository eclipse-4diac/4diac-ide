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
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Basic
 * FB Type Runtime</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.BasicFBTypeRuntimeImpl#getBasicfbtype
 * <em>Basicfbtype</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.BasicFBTypeRuntimeImpl#getActiveState
 * <em>Active State</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BasicFBTypeRuntimeImpl extends FBRuntimeAbstractImpl implements BasicFBTypeRuntime {
	/**
	 * The cached value of the '{@link #getBasicfbtype() <em>Basicfbtype</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getBasicfbtype()
	 * @generated
	 * @ordered
	 */
	protected BasicFBType basicfbtype;

	/**
	 * The cached value of the '{@link #getActiveState() <em>Active State</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getActiveState()
	 * @generated
	 * @ordered
	 */
	protected ECState activeState;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected BasicFBTypeRuntimeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OperationalSemanticsPackage.Literals.BASIC_FB_TYPE_RUNTIME;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public BasicFBType getBasicfbtype() {
		if (basicfbtype != null && basicfbtype.eIsProxy()) {
			InternalEObject oldBasicfbtype = (InternalEObject) basicfbtype;
			basicfbtype = (BasicFBType) eResolveProxy(oldBasicfbtype);
			if (basicfbtype != oldBasicfbtype) {
				InternalEObject newBasicfbtype = (InternalEObject) basicfbtype;
				NotificationChain msgs = oldBasicfbtype.eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - OperationalSemanticsPackage.BASIC_FB_TYPE_RUNTIME__BASICFBTYPE, null,
						null);
				if (newBasicfbtype.eInternalContainer() == null) {
					msgs = newBasicfbtype.eInverseAdd(this,
							EOPPOSITE_FEATURE_BASE - OperationalSemanticsPackage.BASIC_FB_TYPE_RUNTIME__BASICFBTYPE,
							null, msgs);
				}
				if (msgs != null)
					msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							OperationalSemanticsPackage.BASIC_FB_TYPE_RUNTIME__BASICFBTYPE, oldBasicfbtype,
							basicfbtype));
			}
		}
		return basicfbtype;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public BasicFBType basicGetBasicfbtype() {
		return basicfbtype;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetBasicfbtype(BasicFBType newBasicfbtype, NotificationChain msgs) {
		BasicFBType oldBasicfbtype = basicfbtype;
		basicfbtype = newBasicfbtype;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					OperationalSemanticsPackage.BASIC_FB_TYPE_RUNTIME__BASICFBTYPE, oldBasicfbtype, newBasicfbtype);
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
	public void setBasicfbtype(BasicFBType newBasicfbtype) {
		if (newBasicfbtype != basicfbtype) {
			NotificationChain msgs = null;
			if (basicfbtype != null)
				msgs = ((InternalEObject) basicfbtype).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - OperationalSemanticsPackage.BASIC_FB_TYPE_RUNTIME__BASICFBTYPE, null,
						msgs);
			if (newBasicfbtype != null)
				msgs = ((InternalEObject) newBasicfbtype).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - OperationalSemanticsPackage.BASIC_FB_TYPE_RUNTIME__BASICFBTYPE, null,
						msgs);
			msgs = basicSetBasicfbtype(newBasicfbtype, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationalSemanticsPackage.BASIC_FB_TYPE_RUNTIME__BASICFBTYPE, newBasicfbtype, newBasicfbtype));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ECState getActiveState() {
		if (activeState != null && activeState.eIsProxy()) {
			InternalEObject oldActiveState = (InternalEObject) activeState;
			activeState = (ECState) eResolveProxy(oldActiveState);
			if (activeState != oldActiveState) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							OperationalSemanticsPackage.BASIC_FB_TYPE_RUNTIME__ACTIVE_STATE, oldActiveState,
							activeState));
			}
		}
		return activeState;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public ECState basicGetActiveState() {
		return activeState;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setActiveState(ECState newActiveState) {
		ECState oldActiveState = activeState;
		activeState = newActiveState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationalSemanticsPackage.BASIC_FB_TYPE_RUNTIME__ACTIVE_STATE, oldActiveState, activeState));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public BasicFBType getModel() {
		return this.getBasicfbtype();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case OperationalSemanticsPackage.BASIC_FB_TYPE_RUNTIME__BASICFBTYPE:
			return basicSetBasicfbtype(null, msgs);
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
		case OperationalSemanticsPackage.BASIC_FB_TYPE_RUNTIME__BASICFBTYPE:
			if (resolve)
				return getBasicfbtype();
			return basicGetBasicfbtype();
		case OperationalSemanticsPackage.BASIC_FB_TYPE_RUNTIME__ACTIVE_STATE:
			if (resolve)
				return getActiveState();
			return basicGetActiveState();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case OperationalSemanticsPackage.BASIC_FB_TYPE_RUNTIME__BASICFBTYPE:
			setBasicfbtype((BasicFBType) newValue);
			return;
		case OperationalSemanticsPackage.BASIC_FB_TYPE_RUNTIME__ACTIVE_STATE:
			setActiveState((ECState) newValue);
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
		case OperationalSemanticsPackage.BASIC_FB_TYPE_RUNTIME__BASICFBTYPE:
			setBasicfbtype((BasicFBType) null);
			return;
		case OperationalSemanticsPackage.BASIC_FB_TYPE_RUNTIME__ACTIVE_STATE:
			setActiveState((ECState) null);
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
		case OperationalSemanticsPackage.BASIC_FB_TYPE_RUNTIME__BASICFBTYPE:
			return basicfbtype != null;
		case OperationalSemanticsPackage.BASIC_FB_TYPE_RUNTIME__ACTIVE_STATE:
			return activeState != null;
		}
		return super.eIsSet(featureID);
	}

} // BasicFBTypeRuntimeImpl
