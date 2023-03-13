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
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.SimpleFBTypeRuntime;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;

/** <!-- begin-user-doc --> An implementation of the model object '<em><b>Simple FB Type Runtime</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.SimpleFBTypeRuntimeImpl#getSimpleFBType <em>Simple FB
 * Type</em>}</li>
 * </ul>
 *
 * @generated */
public class SimpleFBTypeRuntimeImpl extends FBRuntimeAbstractImpl implements SimpleFBTypeRuntime {
	/** The cached value of the '{@link #getSimpleFBType() <em>Simple FB Type</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getSimpleFBType()
	 * @generated
	 * @ordered */
	protected SimpleFBType simpleFBType;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	protected SimpleFBTypeRuntimeImpl() {
		super();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	protected EClass eStaticClass() {
		return OperationalSemanticsPackage.Literals.SIMPLE_FB_TYPE_RUNTIME;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public SimpleFBType getSimpleFBType() {
		if (simpleFBType != null && simpleFBType.eIsProxy()) {
			InternalEObject oldSimpleFBType = (InternalEObject) simpleFBType;
			simpleFBType = (SimpleFBType) eResolveProxy(oldSimpleFBType);
			if (simpleFBType != oldSimpleFBType) {
				InternalEObject newSimpleFBType = (InternalEObject) simpleFBType;
				NotificationChain msgs = oldSimpleFBType.eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - OperationalSemanticsPackage.SIMPLE_FB_TYPE_RUNTIME__SIMPLE_FB_TYPE,
						null, null);
				if (newSimpleFBType.eInternalContainer() == null) {
					msgs = newSimpleFBType.eInverseAdd(this,
							EOPPOSITE_FEATURE_BASE - OperationalSemanticsPackage.SIMPLE_FB_TYPE_RUNTIME__SIMPLE_FB_TYPE,
							null, msgs);
				}
				if (msgs != null)
					msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							OperationalSemanticsPackage.SIMPLE_FB_TYPE_RUNTIME__SIMPLE_FB_TYPE, oldSimpleFBType,
							simpleFBType));
			}
		}
		return simpleFBType;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public SimpleFBType basicGetSimpleFBType() {
		return simpleFBType;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public NotificationChain basicSetSimpleFBType(SimpleFBType newSimpleFBType, NotificationChain msgs) {
		SimpleFBType oldSimpleFBType = simpleFBType;
		simpleFBType = newSimpleFBType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					OperationalSemanticsPackage.SIMPLE_FB_TYPE_RUNTIME__SIMPLE_FB_TYPE, oldSimpleFBType,
					newSimpleFBType);
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
	public void setSimpleFBType(SimpleFBType newSimpleFBType) {
		if (newSimpleFBType != simpleFBType) {
			NotificationChain msgs = null;
			if (simpleFBType != null)
				msgs = ((InternalEObject) simpleFBType).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - OperationalSemanticsPackage.SIMPLE_FB_TYPE_RUNTIME__SIMPLE_FB_TYPE,
						null, msgs);
			if (newSimpleFBType != null)
				msgs = ((InternalEObject) newSimpleFBType).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - OperationalSemanticsPackage.SIMPLE_FB_TYPE_RUNTIME__SIMPLE_FB_TYPE,
						null, msgs);
			msgs = basicSetSimpleFBType(newSimpleFBType, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationalSemanticsPackage.SIMPLE_FB_TYPE_RUNTIME__SIMPLE_FB_TYPE, newSimpleFBType,
					newSimpleFBType));
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public SimpleFBType getModel() {
		return simpleFBType;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case OperationalSemanticsPackage.SIMPLE_FB_TYPE_RUNTIME__SIMPLE_FB_TYPE:
			return basicSetSimpleFBType(null, msgs);
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
		case OperationalSemanticsPackage.SIMPLE_FB_TYPE_RUNTIME__SIMPLE_FB_TYPE:
			if (resolve)
				return getSimpleFBType();
			return basicGetSimpleFBType();
		default:
			return super.eGet(featureID, resolve, coreType);
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case OperationalSemanticsPackage.SIMPLE_FB_TYPE_RUNTIME__SIMPLE_FB_TYPE:
			setSimpleFBType((SimpleFBType) newValue);
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
		case OperationalSemanticsPackage.SIMPLE_FB_TYPE_RUNTIME__SIMPLE_FB_TYPE:
			setSimpleFBType((SimpleFBType) null);
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
		case OperationalSemanticsPackage.SIMPLE_FB_TYPE_RUNTIME__SIMPLE_FB_TYPE:
			return simpleFBType != null;
		default:
			return super.eIsSet(featureID);
		}
	}

} // SimpleFBTypeRuntimeImpl
