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
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Value;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>FB
 * Network Runtime</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.FBNetworkRuntimeImpl#getFbnetwork
 * <em>Fbnetwork</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.FBNetworkRuntimeImpl#getTransferData
 * <em>Transfer Data</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.FBNetworkRuntimeImpl#getTypeRuntimes
 * <em>Type Runtimes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FBNetworkRuntimeImpl extends FBRuntimeAbstractImpl implements FBNetworkRuntime {
	/**
	 * The cached value of the '{@link #getFbnetwork() <em>Fbnetwork</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getFbnetwork()
	 * @generated
	 * @ordered
	 */
	protected FBNetwork fbnetwork;

	/**
	 * The cached value of the '{@link #getTransferData() <em>Transfer Data</em>}'
	 * map. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getTransferData()
	 * @generated
	 * @ordered
	 */
	protected EMap<Connection, Value> transferData;

	/**
	 * The cached value of the '{@link #getTypeRuntimes() <em>Type Runtimes</em>}'
	 * map. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getTypeRuntimes()
	 * @generated
	 * @ordered
	 */
	protected EMap<FBNetworkElement, FBRuntimeAbstract> typeRuntimes;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected FBNetworkRuntimeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OperationalSemanticsPackage.Literals.FB_NETWORK_RUNTIME;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public FBNetwork getFbnetwork() {
		if (fbnetwork != null && fbnetwork.eIsProxy()) {
			InternalEObject oldFbnetwork = (InternalEObject) fbnetwork;
			fbnetwork = (FBNetwork) eResolveProxy(oldFbnetwork);
			if (fbnetwork != oldFbnetwork) {
				InternalEObject newFbnetwork = (InternalEObject) fbnetwork;
				NotificationChain msgs = oldFbnetwork.eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - OperationalSemanticsPackage.FB_NETWORK_RUNTIME__FBNETWORK, null, null);
				if (newFbnetwork.eInternalContainer() == null) {
					msgs = newFbnetwork.eInverseAdd(this,
							EOPPOSITE_FEATURE_BASE - OperationalSemanticsPackage.FB_NETWORK_RUNTIME__FBNETWORK, null,
							msgs);
				}
				if (msgs != null) {
					msgs.dispatch();
				}
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							OperationalSemanticsPackage.FB_NETWORK_RUNTIME__FBNETWORK, oldFbnetwork, fbnetwork));
				}
			}
		}
		return fbnetwork;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public FBNetwork basicGetFbnetwork() {
		return fbnetwork;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetFbnetwork(FBNetwork newFbnetwork, NotificationChain msgs) {
		FBNetwork oldFbnetwork = fbnetwork;
		fbnetwork = newFbnetwork;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					OperationalSemanticsPackage.FB_NETWORK_RUNTIME__FBNETWORK, oldFbnetwork, newFbnetwork);
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
	public void setFbnetwork(FBNetwork newFbnetwork) {
		if (newFbnetwork != fbnetwork) {
			NotificationChain msgs = null;
			if (fbnetwork != null) {
				msgs = ((InternalEObject) fbnetwork).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - OperationalSemanticsPackage.FB_NETWORK_RUNTIME__FBNETWORK, null, msgs);
			}
			if (newFbnetwork != null) {
				msgs = ((InternalEObject) newFbnetwork).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - OperationalSemanticsPackage.FB_NETWORK_RUNTIME__FBNETWORK, null, msgs);
			}
			msgs = basicSetFbnetwork(newFbnetwork, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationalSemanticsPackage.FB_NETWORK_RUNTIME__FBNETWORK, newFbnetwork, newFbnetwork));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EMap<Connection, Value> getTransferData() {
		if (transferData == null) {
			transferData = new EcoreEMap<>(OperationalSemanticsPackage.Literals.CONNECTION_TO_VALUE_MAP,
					ConnectionToValueMapImpl.class, this,
					OperationalSemanticsPackage.FB_NETWORK_RUNTIME__TRANSFER_DATA);
		}
		return transferData;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EMap<FBNetworkElement, FBRuntimeAbstract> getTypeRuntimes() {
		if (typeRuntimes == null) {
			typeRuntimes = new EcoreEMap<>(OperationalSemanticsPackage.Literals.RUNTIME_MAP, RuntimeMapImpl.class, this,
					OperationalSemanticsPackage.FB_NETWORK_RUNTIME__TYPE_RUNTIMES);
		}
		return typeRuntimes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public FBNetwork getModel() {
		return this.fbnetwork;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME__FBNETWORK:
			return basicSetFbnetwork(null, msgs);
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME__TRANSFER_DATA:
			return ((InternalEList<?>) getTransferData()).basicRemove(otherEnd, msgs);
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME__TYPE_RUNTIMES:
			return ((InternalEList<?>) getTypeRuntimes()).basicRemove(otherEnd, msgs);
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
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME__FBNETWORK:
			if (resolve) {
				return getFbnetwork();
			}
			return basicGetFbnetwork();
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME__TRANSFER_DATA:
			if (coreType) {
				return getTransferData();
			} else {
				return getTransferData().map();
			}
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME__TYPE_RUNTIMES:
			if (coreType) {
				return getTypeRuntimes();
			} else {
				return getTypeRuntimes().map();
			}
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
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME__FBNETWORK:
			setFbnetwork((FBNetwork) newValue);
			return;
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME__TRANSFER_DATA:
			((EStructuralFeature.Setting) getTransferData()).set(newValue);
			return;
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME__TYPE_RUNTIMES:
			((EStructuralFeature.Setting) getTypeRuntimes()).set(newValue);
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
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME__FBNETWORK:
			setFbnetwork((FBNetwork) null);
			return;
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME__TRANSFER_DATA:
			getTransferData().clear();
			return;
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME__TYPE_RUNTIMES:
			getTypeRuntimes().clear();
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
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME__FBNETWORK:
			return fbnetwork != null;
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME__TRANSFER_DATA:
			return transferData != null && !transferData.isEmpty();
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME__TYPE_RUNTIMES:
			return typeRuntimes != null && !typeRuntimes.isEmpty();
		default:
			return super.eIsSet(featureID);
		}
	}

} // FBNetworkRuntimeImpl
