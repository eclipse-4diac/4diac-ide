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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;

/** <!-- begin-user-doc --> An implementation of the model object '<em><b>FB Type Runtime</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.FBTypeRuntimeImpl#getFbtype <em>Fbtype</em>}</li>
 * </ul>
 *
 * @generated */
public class FBTypeRuntimeImpl extends FBRuntimeAbstractImpl implements FBTypeRuntime {
	/** The cached value of the '{@link #getFbtype() <em>Fbtype</em>}' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getFbtype()
	 * @generated
	 * @ordered */
	protected FBType fbtype;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	protected FBTypeRuntimeImpl() {
		super();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	protected EClass eStaticClass() {
		return OperationalSemanticsPackage.Literals.FB_TYPE_RUNTIME;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public FBType getFbtype() {
		if (fbtype != null && fbtype.eIsProxy()) {
			InternalEObject oldFbtype = (InternalEObject) fbtype;
			fbtype = (FBType) eResolveProxy(oldFbtype);
			if (fbtype != oldFbtype) {
				InternalEObject newFbtype = (InternalEObject) fbtype;
				NotificationChain msgs = oldFbtype.eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - OperationalSemanticsPackage.FB_TYPE_RUNTIME__FBTYPE, null, null);
				if (newFbtype.eInternalContainer() == null) {
					msgs = newFbtype.eInverseAdd(this,
							EOPPOSITE_FEATURE_BASE - OperationalSemanticsPackage.FB_TYPE_RUNTIME__FBTYPE, null, msgs);
				}
				if (msgs != null)
					msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							OperationalSemanticsPackage.FB_TYPE_RUNTIME__FBTYPE, oldFbtype, fbtype));
			}
		}
		return fbtype;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public FBType basicGetFbtype() {
		return fbtype;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	public NotificationChain basicSetFbtype(FBType newFbtype, NotificationChain msgs) {
		FBType oldFbtype = fbtype;
		fbtype = newFbtype;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					OperationalSemanticsPackage.FB_TYPE_RUNTIME__FBTYPE, oldFbtype, newFbtype);
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
	public void setFbtype(FBType newFbtype) {
		if (newFbtype != fbtype) {
			NotificationChain msgs = null;
			if (fbtype != null)
				msgs = ((InternalEObject) fbtype).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - OperationalSemanticsPackage.FB_TYPE_RUNTIME__FBTYPE, null, msgs);
			if (newFbtype != null)
				msgs = ((InternalEObject) newFbtype).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - OperationalSemanticsPackage.FB_TYPE_RUNTIME__FBTYPE, null, msgs);
			msgs = basicSetFbtype(newFbtype, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OperationalSemanticsPackage.FB_TYPE_RUNTIME__FBTYPE,
					newFbtype, newFbtype));
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case OperationalSemanticsPackage.FB_TYPE_RUNTIME__FBTYPE:
			return basicSetFbtype(null, msgs);
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
		case OperationalSemanticsPackage.FB_TYPE_RUNTIME__FBTYPE:
			if (resolve)
				return getFbtype();
			return basicGetFbtype();
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
		case OperationalSemanticsPackage.FB_TYPE_RUNTIME__FBTYPE:
			setFbtype((FBType) newValue);
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
		case OperationalSemanticsPackage.FB_TYPE_RUNTIME__FBTYPE:
			setFbtype((FBType) null);
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
		case OperationalSemanticsPackage.FB_TYPE_RUNTIME__FBTYPE:
			return fbtype != null;
		default:
			return super.eIsSet(featureID);
		}
	}

} // FBTypeRuntimeImpl
