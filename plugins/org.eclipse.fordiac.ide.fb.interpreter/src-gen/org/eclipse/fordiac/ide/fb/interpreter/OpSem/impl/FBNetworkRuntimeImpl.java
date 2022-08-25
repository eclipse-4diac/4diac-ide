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
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
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
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.FBNetworkRuntimeImpl#getFbRuntimes
 * <em>Fb Runtimes</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.FBNetworkRuntimeImpl#getTransferData
 * <em>Transfer Data</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FBNetworkRuntimeImpl extends FBRuntimeAbstractImpl implements FBNetworkRuntime {
	/**
	 * The cached value of the '{@link #getFbnetwork() <em>Fbnetwork</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getFbnetwork()
	 * @generated
	 * @ordered
	 */
	protected FBNetwork fbnetwork;

	/**
	 * The cached value of the '{@link #getFbRuntimes() <em>Fb Runtimes</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getFbRuntimes()
	 * @generated
	 * @ordered
	 */
	protected EList<FBRuntimeAbstract> fbRuntimes;

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
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							OperationalSemanticsPackage.FB_NETWORK_RUNTIME__FBNETWORK, oldFbnetwork, fbnetwork));
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
	@Override
	public void setFbnetwork(FBNetwork newFbnetwork) {
		FBNetwork oldFbnetwork = fbnetwork;
		fbnetwork = newFbnetwork;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationalSemanticsPackage.FB_NETWORK_RUNTIME__FBNETWORK, oldFbnetwork, fbnetwork));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<FBRuntimeAbstract> getFbRuntimes() {
		if (fbRuntimes == null) {
			fbRuntimes = new EObjectContainmentEList.Resolving<>(FBRuntimeAbstract.class, this,
					OperationalSemanticsPackage.FB_NETWORK_RUNTIME__FB_RUNTIMES);
		}
		return fbRuntimes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EMap<Connection, Value> getTransferData() {
		if (transferData == null) {
			transferData = new EcoreEMap<>(
					OperationalSemanticsPackage.Literals.CONNECTION_TO_VALUE_MAP, ConnectionToValueMapImpl.class, this,
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
	public EObject getModel() {
		return this.getFbnetwork();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME__FB_RUNTIMES:
			return ((InternalEList<?>) getFbRuntimes()).basicRemove(otherEnd, msgs);
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME__TRANSFER_DATA:
			return ((InternalEList<?>) getTransferData()).basicRemove(otherEnd, msgs);
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
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME__FBNETWORK:
			if (resolve)
				return getFbnetwork();
			return basicGetFbnetwork();
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME__FB_RUNTIMES:
			return getFbRuntimes();
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME__TRANSFER_DATA:
			if (coreType)
				return getTransferData();
			else
				return getTransferData().map();
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
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME__FBNETWORK:
			setFbnetwork((FBNetwork) newValue);
			return;
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME__FB_RUNTIMES:
			getFbRuntimes().clear();
			getFbRuntimes().addAll((Collection<? extends FBRuntimeAbstract>) newValue);
			return;
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME__TRANSFER_DATA:
			((EStructuralFeature.Setting) getTransferData()).set(newValue);
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
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME__FBNETWORK:
			setFbnetwork((FBNetwork) null);
			return;
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME__FB_RUNTIMES:
			getFbRuntimes().clear();
			return;
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME__TRANSFER_DATA:
			getTransferData().clear();
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
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME__FBNETWORK:
			return fbnetwork != null;
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME__FB_RUNTIMES:
			return fbRuntimes != null && !fbRuntimes.isEmpty();
		case OperationalSemanticsPackage.FB_NETWORK_RUNTIME__TRANSFER_DATA:
			return transferData != null && !transferData.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // FBNetworkRuntimeImpl
