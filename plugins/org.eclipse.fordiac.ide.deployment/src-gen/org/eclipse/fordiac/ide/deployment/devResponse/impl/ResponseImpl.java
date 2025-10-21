/**
 * ******************************************************************************
 * * Copyright (c) 2012, 2013, 2018 Profactor GmbH, fortiss GmbH, Johannes Kepler University
 * *
 * * This program and the accompanying materials are made available under the
 * * terms of the Eclipse Public License 2.0 which is available at
 * * http://www.eclipse.org/legal/epl-2.0.
 * *
 * * SPDX-License-Identifier: EPL-2.0
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
import org.eclipse.fordiac.ide.deployment.devResponse.Connection;
import org.eclipse.fordiac.ide.deployment.devResponse.DevResponsePackage;
import org.eclipse.fordiac.ide.deployment.devResponse.EndpointList;
import org.eclipse.fordiac.ide.deployment.devResponse.FBList;
import org.eclipse.fordiac.ide.deployment.devResponse.FBType;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.devResponse.Watches;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Response</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.ResponseImpl#getID
 * <em>ID</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.ResponseImpl#getWatches
 * <em>Watches</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.ResponseImpl#getReason
 * <em>Reason</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.ResponseImpl#getFblist
 * <em>Fblist</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.ResponseImpl#getFbType
 * <em>Fb Type</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.ResponseImpl#getEndpointlist
 * <em>Endpointlist</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.ResponseImpl#getConnection
 * <em>Connection</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ResponseImpl extends EObjectImpl implements Response {
	/**
	 * The default value of the '{@link #getID() <em>ID</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getID()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getID() <em>ID</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getID()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getWatches() <em>Watches</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getWatches()
	 * @generated
	 * @ordered
	 */
	protected Watches watches;

	/**
	 * The default value of the '{@link #getReason() <em>Reason</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getReason()
	 * @generated
	 * @ordered
	 */
	protected static final String REASON_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getReason() <em>Reason</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getReason()
	 * @generated
	 * @ordered
	 */
	protected String reason = REASON_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFblist() <em>Fblist</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getFblist()
	 * @generated
	 * @ordered
	 */
	protected FBList fblist;

	/**
	 * The cached value of the '{@link #getFbType() <em>Fb Type</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getFbType()
	 * @generated
	 * @ordered
	 */
	protected FBType fbType;

	/**
	 * The cached value of the '{@link #getEndpointlist() <em>Endpointlist</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getEndpointlist()
	 * @generated
	 * @ordered
	 */
	protected EndpointList endpointlist;

	/**
	 * The cached value of the '{@link #getConnection() <em>Connection</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getConnection()
	 * @generated
	 * @ordered
	 */
	protected Connection connection;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ResponseImpl() {
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DevResponsePackage.Literals.RESPONSE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getID() {
		return id;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setID(final String newID) {
		final String oldID = id;
		id = newID;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, DevResponsePackage.RESPONSE__ID, oldID, id));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Watches getWatches() {
		if (watches != null && watches.eIsProxy()) {
			final InternalEObject oldWatches = (InternalEObject) watches;
			watches = (Watches) eResolveProxy(oldWatches);
			if ((watches != oldWatches) && eNotificationRequired()) {
				eNotify(new ENotificationImpl(this, Notification.RESOLVE, DevResponsePackage.RESPONSE__WATCHES,
						oldWatches, watches));
			}
		}
		return watches;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public Watches basicGetWatches() {
		return watches;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setWatches(final Watches newWatches) {
		final Watches oldWatches = watches;
		watches = newWatches;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, DevResponsePackage.RESPONSE__WATCHES, oldWatches,
					watches));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getReason() {
		return reason;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setReason(final String newReason) {
		final String oldReason = reason;
		reason = newReason;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, DevResponsePackage.RESPONSE__REASON, oldReason,
					reason));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public FBList getFblist() {
		if (fblist != null && fblist.eIsProxy()) {
			final InternalEObject oldFblist = (InternalEObject) fblist;
			fblist = (FBList) eResolveProxy(oldFblist);
			if ((fblist != oldFblist) && eNotificationRequired()) {
				eNotify(new ENotificationImpl(this, Notification.RESOLVE, DevResponsePackage.RESPONSE__FBLIST,
						oldFblist, fblist));
			}
		}
		return fblist;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public FBList basicGetFblist() {
		return fblist;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setFblist(final FBList newFblist) {
		final FBList oldFblist = fblist;
		fblist = newFblist;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, DevResponsePackage.RESPONSE__FBLIST, oldFblist,
					fblist));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public FBType getFbType() {
		if (fbType != null && fbType.eIsProxy()) {
			final InternalEObject oldFbType = (InternalEObject) fbType;
			fbType = (FBType) eResolveProxy(oldFbType);
			if ((fbType != oldFbType) && eNotificationRequired()) {
				eNotify(new ENotificationImpl(this, Notification.RESOLVE, DevResponsePackage.RESPONSE__FB_TYPE,
						oldFbType, fbType));
			}
		}
		return fbType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public FBType basicGetFbType() {
		return fbType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setFbType(final FBType newFbType) {
		final FBType oldFbType = fbType;
		fbType = newFbType;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, DevResponsePackage.RESPONSE__FB_TYPE, oldFbType,
					fbType));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EndpointList getEndpointlist() {
		if (endpointlist != null && endpointlist.eIsProxy()) {
			final InternalEObject oldEndpointlist = (InternalEObject) endpointlist;
			endpointlist = (EndpointList) eResolveProxy(oldEndpointlist);
			if ((endpointlist != oldEndpointlist) && eNotificationRequired()) {
				eNotify(new ENotificationImpl(this, Notification.RESOLVE, DevResponsePackage.RESPONSE__ENDPOINTLIST,
						oldEndpointlist, endpointlist));
			}
		}
		return endpointlist;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public EndpointList basicGetEndpointlist() {
		return endpointlist;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setEndpointlist(final EndpointList newEndpointlist) {
		final EndpointList oldEndpointlist = endpointlist;
		endpointlist = newEndpointlist;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, DevResponsePackage.RESPONSE__ENDPOINTLIST,
					oldEndpointlist, endpointlist));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Connection getConnection() {
		if (connection != null && connection.eIsProxy()) {
			final InternalEObject oldConnection = (InternalEObject) connection;
			connection = (Connection) eResolveProxy(oldConnection);
			if ((connection != oldConnection) && eNotificationRequired()) {
				eNotify(new ENotificationImpl(this, Notification.RESOLVE, DevResponsePackage.RESPONSE__CONNECTION,
						oldConnection, connection));
			}
		}
		return connection;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public Connection basicGetConnection() {
		return connection;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setConnection(final Connection newConnection) {
		final Connection oldConnection = connection;
		connection = newConnection;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, DevResponsePackage.RESPONSE__CONNECTION,
					oldConnection, connection));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
		switch (featureID) {
		case DevResponsePackage.RESPONSE__ID:
			return getID();
		case DevResponsePackage.RESPONSE__WATCHES:
			if (resolve) {
				return getWatches();
			}
			return basicGetWatches();
		case DevResponsePackage.RESPONSE__REASON:
			return getReason();
		case DevResponsePackage.RESPONSE__FBLIST:
			if (resolve) {
				return getFblist();
			}
			return basicGetFblist();
		case DevResponsePackage.RESPONSE__FB_TYPE:
			if (resolve) {
				return getFbType();
			}
			return basicGetFbType();
		case DevResponsePackage.RESPONSE__ENDPOINTLIST:
			if (resolve) {
				return getEndpointlist();
			}
			return basicGetEndpointlist();
		case DevResponsePackage.RESPONSE__CONNECTION:
			if (resolve) {
				return getConnection();
			}
			return basicGetConnection();
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
	public void eSet(final int featureID, final Object newValue) {
		switch (featureID) {
		case DevResponsePackage.RESPONSE__ID:
			setID((String) newValue);
			return;
		case DevResponsePackage.RESPONSE__WATCHES:
			setWatches((Watches) newValue);
			return;
		case DevResponsePackage.RESPONSE__REASON:
			setReason((String) newValue);
			return;
		case DevResponsePackage.RESPONSE__FBLIST:
			setFblist((FBList) newValue);
			return;
		case DevResponsePackage.RESPONSE__FB_TYPE:
			setFbType((FBType) newValue);
			return;
		case DevResponsePackage.RESPONSE__ENDPOINTLIST:
			setEndpointlist((EndpointList) newValue);
			return;
		case DevResponsePackage.RESPONSE__CONNECTION:
			setConnection((Connection) newValue);
			return;
		default:
			super.eSet(featureID, newValue);
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(final int featureID) {
		switch (featureID) {
		case DevResponsePackage.RESPONSE__ID:
			setID(ID_EDEFAULT);
			return;
		case DevResponsePackage.RESPONSE__WATCHES:
			setWatches((Watches) null);
			return;
		case DevResponsePackage.RESPONSE__REASON:
			setReason(REASON_EDEFAULT);
			return;
		case DevResponsePackage.RESPONSE__FBLIST:
			setFblist((FBList) null);
			return;
		case DevResponsePackage.RESPONSE__FB_TYPE:
			setFbType((FBType) null);
			return;
		case DevResponsePackage.RESPONSE__ENDPOINTLIST:
			setEndpointlist((EndpointList) null);
			return;
		case DevResponsePackage.RESPONSE__CONNECTION:
			setConnection((Connection) null);
			return;
		default:
			super.eUnset(featureID);
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(final int featureID) {
		return switch (featureID) {
		case DevResponsePackage.RESPONSE__ID -> ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
		case DevResponsePackage.RESPONSE__WATCHES -> watches != null;
		case DevResponsePackage.RESPONSE__REASON -> REASON_EDEFAULT == null ? reason != null : !REASON_EDEFAULT.equals(reason);
		case DevResponsePackage.RESPONSE__FBLIST -> fblist != null;
		case DevResponsePackage.RESPONSE__FB_TYPE -> fbType != null;
		case DevResponsePackage.RESPONSE__ENDPOINTLIST -> endpointlist != null;
		case DevResponsePackage.RESPONSE__CONNECTION -> connection != null;
		default -> super.eIsSet(featureID);
		};
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

		final StringBuilder result = new StringBuilder(super.toString());
		result.append(" (ID: "); //$NON-NLS-1$
		result.append(id);
		result.append(", Reason: "); //$NON-NLS-1$
		result.append(reason);
		result.append(')');
		return result.toString();
	}

} // ResponseImpl
