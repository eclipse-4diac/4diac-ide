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
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.ServiceInterfaceFBTypeRuntime;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Service
 * Interface FB Type Runtime</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.ServiceInterfaceFBTypeRuntimeImpl#getServiceFBType
 * <em>Service FB Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ServiceInterfaceFBTypeRuntimeImpl extends FBRuntimeAbstractImpl implements ServiceInterfaceFBTypeRuntime {
	/**
	 * The cached value of the '{@link #getServiceFBType() <em>Service FB
	 * Type</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @see #getServiceFBType()
	 * @generated
	 * @ordered
	 */
	protected ServiceInterfaceFBType serviceFBType;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ServiceInterfaceFBTypeRuntimeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OperationalSemanticsPackage.Literals.SERVICE_INTERFACE_FB_TYPE_RUNTIME;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ServiceInterfaceFBType getServiceFBType() {
		if (serviceFBType != null && serviceFBType.eIsProxy()) {
			InternalEObject oldServiceFBType = (InternalEObject) serviceFBType;
			serviceFBType = (ServiceInterfaceFBType) eResolveProxy(oldServiceFBType);
			if (serviceFBType != oldServiceFBType) {
				InternalEObject newServiceFBType = (InternalEObject) serviceFBType;
				NotificationChain msgs = oldServiceFBType.eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- OperationalSemanticsPackage.SERVICE_INTERFACE_FB_TYPE_RUNTIME__SERVICE_FB_TYPE,
						null, null);
				if (newServiceFBType.eInternalContainer() == null) {
					msgs = newServiceFBType.eInverseAdd(this,
							EOPPOSITE_FEATURE_BASE
									- OperationalSemanticsPackage.SERVICE_INTERFACE_FB_TYPE_RUNTIME__SERVICE_FB_TYPE,
							null, msgs);
				}
				if (msgs != null) {
					msgs.dispatch();
				}
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							OperationalSemanticsPackage.SERVICE_INTERFACE_FB_TYPE_RUNTIME__SERVICE_FB_TYPE,
							oldServiceFBType, serviceFBType));
				}
			}
		}
		return serviceFBType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public ServiceInterfaceFBType basicGetServiceFBType() {
		return serviceFBType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetServiceFBType(ServiceInterfaceFBType newServiceFBType, NotificationChain msgs) {
		ServiceInterfaceFBType oldServiceFBType = serviceFBType;
		serviceFBType = newServiceFBType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					OperationalSemanticsPackage.SERVICE_INTERFACE_FB_TYPE_RUNTIME__SERVICE_FB_TYPE, oldServiceFBType,
					newServiceFBType);
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
	public void setServiceFBType(ServiceInterfaceFBType newServiceFBType) {
		if (newServiceFBType != serviceFBType) {
			NotificationChain msgs = null;
			if (serviceFBType != null) {
				msgs = ((InternalEObject) serviceFBType).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- OperationalSemanticsPackage.SERVICE_INTERFACE_FB_TYPE_RUNTIME__SERVICE_FB_TYPE,
						null, msgs);
			}
			if (newServiceFBType != null) {
				msgs = ((InternalEObject) newServiceFBType).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- OperationalSemanticsPackage.SERVICE_INTERFACE_FB_TYPE_RUNTIME__SERVICE_FB_TYPE,
						null, msgs);
			}
			msgs = basicSetServiceFBType(newServiceFBType, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationalSemanticsPackage.SERVICE_INTERFACE_FB_TYPE_RUNTIME__SERVICE_FB_TYPE, newServiceFBType,
					newServiceFBType));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ServiceInterfaceFBType getModel() {
		return serviceFBType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case OperationalSemanticsPackage.SERVICE_INTERFACE_FB_TYPE_RUNTIME__SERVICE_FB_TYPE:
			return basicSetServiceFBType(null, msgs);
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
		case OperationalSemanticsPackage.SERVICE_INTERFACE_FB_TYPE_RUNTIME__SERVICE_FB_TYPE:
			if (resolve) {
				return getServiceFBType();
			}
			return basicGetServiceFBType();
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
		case OperationalSemanticsPackage.SERVICE_INTERFACE_FB_TYPE_RUNTIME__SERVICE_FB_TYPE:
			setServiceFBType((ServiceInterfaceFBType) newValue);
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
		case OperationalSemanticsPackage.SERVICE_INTERFACE_FB_TYPE_RUNTIME__SERVICE_FB_TYPE:
			setServiceFBType((ServiceInterfaceFBType) null);
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
		case OperationalSemanticsPackage.SERVICE_INTERFACE_FB_TYPE_RUNTIME__SERVICE_FB_TYPE:
			return serviceFBType != null;
		default:
			return super.eIsSet(featureID);
		}
	}

} // ServiceInterfaceFBTypeRuntimeImpl
