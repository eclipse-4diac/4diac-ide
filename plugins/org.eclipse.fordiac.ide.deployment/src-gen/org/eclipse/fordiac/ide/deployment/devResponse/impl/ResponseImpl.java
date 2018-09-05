/**
 * ******************************************************************************
 * * Copyright (c) 2012, 2013, 2018 Profactor GmbH, fortiss GmbH, Johannes Kepler University
 * * 
 * * All rights reserved. This program and the accompanying materials
 * * are made available under the terms of the Eclipse Public License v1.0
 * * which accompanies this distribution, and is available at
 * * http://www.eclipse.org/legal/epl-v10.html
 * *
 * * Contributors:
 * *   Gerhard Ebenhofer, Alois Zoitl
 * *     - initial API and implementation and/or initial documentation
 * *   Alois Zoitl - moved to deployment and reworked it to a device response model
 * ******************************************************************************
 * 
 */
package org.eclipse.fordiac.ide.deployment.devResponse.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.fordiac.ide.deployment.devResponse.DevResponsePackage;
import org.eclipse.fordiac.ide.deployment.devResponse.EndpointList;
import org.eclipse.fordiac.ide.deployment.devResponse.FBList;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.devResponse.Watches;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Response</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.ResponseImpl#getID <em>ID</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.ResponseImpl#getWatches <em>Watches</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.ResponseImpl#getReason <em>Reason</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.ResponseImpl#getFblist <em>Fblist</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.ResponseImpl#getEndpointlist <em>Endpointlist</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ResponseImpl extends EObjectImpl implements Response {
	/**
	 * The default value of the '{@link #getID() <em>ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getID()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getID() <em>ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getID()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getWatches() <em>Watches</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWatches()
	 * @generated
	 * @ordered
	 */
	protected Watches watches;

	/**
	 * The default value of the '{@link #getReason() <em>Reason</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReason()
	 * @generated
	 * @ordered
	 */
	protected static final String REASON_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getReason() <em>Reason</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReason()
	 * @generated
	 * @ordered
	 */
	protected String reason = REASON_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFblist() <em>Fblist</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFblist()
	 * @generated
	 * @ordered
	 */
	protected FBList fblist;

	/**
	 * The cached value of the '{@link #getEndpointlist() <em>Endpointlist</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndpointlist()
	 * @generated
	 * @ordered
	 */
	protected EndpointList endpointlist;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ResponseImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DevResponsePackage.Literals.RESPONSE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getID() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setID(String newID) {
		String oldID = id;
		id = newID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DevResponsePackage.RESPONSE__ID, oldID, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Watches getWatches() {
		if (watches != null && watches.eIsProxy()) {
			InternalEObject oldWatches = (InternalEObject)watches;
			watches = (Watches)eResolveProxy(oldWatches);
			if (watches != oldWatches) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DevResponsePackage.RESPONSE__WATCHES, oldWatches, watches));
			}
		}
		return watches;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Watches basicGetWatches() {
		return watches;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setWatches(Watches newWatches) {
		Watches oldWatches = watches;
		watches = newWatches;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DevResponsePackage.RESPONSE__WATCHES, oldWatches, watches));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getReason() {
		return reason;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReason(String newReason) {
		String oldReason = reason;
		reason = newReason;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DevResponsePackage.RESPONSE__REASON, oldReason, reason));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FBList getFblist() {
		if (fblist != null && fblist.eIsProxy()) {
			InternalEObject oldFblist = (InternalEObject)fblist;
			fblist = (FBList)eResolveProxy(oldFblist);
			if (fblist != oldFblist) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DevResponsePackage.RESPONSE__FBLIST, oldFblist, fblist));
			}
		}
		return fblist;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FBList basicGetFblist() {
		return fblist;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFblist(FBList newFblist) {
		FBList oldFblist = fblist;
		fblist = newFblist;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DevResponsePackage.RESPONSE__FBLIST, oldFblist, fblist));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EndpointList getEndpointlist() {
		if (endpointlist != null && endpointlist.eIsProxy()) {
			InternalEObject oldEndpointlist = (InternalEObject)endpointlist;
			endpointlist = (EndpointList)eResolveProxy(oldEndpointlist);
			if (endpointlist != oldEndpointlist) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DevResponsePackage.RESPONSE__ENDPOINTLIST, oldEndpointlist, endpointlist));
			}
		}
		return endpointlist;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EndpointList basicGetEndpointlist() {
		return endpointlist;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEndpointlist(EndpointList newEndpointlist) {
		EndpointList oldEndpointlist = endpointlist;
		endpointlist = newEndpointlist;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DevResponsePackage.RESPONSE__ENDPOINTLIST, oldEndpointlist, endpointlist));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DevResponsePackage.RESPONSE__ID:
				return getID();
			case DevResponsePackage.RESPONSE__WATCHES:
				if (resolve) return getWatches();
				return basicGetWatches();
			case DevResponsePackage.RESPONSE__REASON:
				return getReason();
			case DevResponsePackage.RESPONSE__FBLIST:
				if (resolve) return getFblist();
				return basicGetFblist();
			case DevResponsePackage.RESPONSE__ENDPOINTLIST:
				if (resolve) return getEndpointlist();
				return basicGetEndpointlist();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DevResponsePackage.RESPONSE__ID:
				setID((String)newValue);
				return;
			case DevResponsePackage.RESPONSE__WATCHES:
				setWatches((Watches)newValue);
				return;
			case DevResponsePackage.RESPONSE__REASON:
				setReason((String)newValue);
				return;
			case DevResponsePackage.RESPONSE__FBLIST:
				setFblist((FBList)newValue);
				return;
			case DevResponsePackage.RESPONSE__ENDPOINTLIST:
				setEndpointlist((EndpointList)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case DevResponsePackage.RESPONSE__ID:
				setID(ID_EDEFAULT);
				return;
			case DevResponsePackage.RESPONSE__WATCHES:
				setWatches((Watches)null);
				return;
			case DevResponsePackage.RESPONSE__REASON:
				setReason(REASON_EDEFAULT);
				return;
			case DevResponsePackage.RESPONSE__FBLIST:
				setFblist((FBList)null);
				return;
			case DevResponsePackage.RESPONSE__ENDPOINTLIST:
				setEndpointlist((EndpointList)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case DevResponsePackage.RESPONSE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case DevResponsePackage.RESPONSE__WATCHES:
				return watches != null;
			case DevResponsePackage.RESPONSE__REASON:
				return REASON_EDEFAULT == null ? reason != null : !REASON_EDEFAULT.equals(reason);
			case DevResponsePackage.RESPONSE__FBLIST:
				return fblist != null;
			case DevResponsePackage.RESPONSE__ENDPOINTLIST:
				return endpointlist != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (ID: "); //$NON-NLS-1$
		result.append(id);
		result.append(", Reason: "); //$NON-NLS-1$
		result.append(reason);
		result.append(')');
		return result.toString();
	}

} //ResponseImpl
