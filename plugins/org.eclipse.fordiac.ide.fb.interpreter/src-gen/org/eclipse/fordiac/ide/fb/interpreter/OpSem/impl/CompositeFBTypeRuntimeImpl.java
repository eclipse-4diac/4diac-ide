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
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.CompositeFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Composite FB Type Runtime</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.CompositeFBTypeRuntimeImpl#getCompositeFBType
 * <em>Composite FB Type</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.CompositeFBTypeRuntimeImpl#getFbElement
 * <em>Fb Element</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.impl.CompositeFBTypeRuntimeImpl#getNetworkRuntime
 * <em>Network Runtime</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompositeFBTypeRuntimeImpl extends FBRuntimeAbstractImpl implements CompositeFBTypeRuntime {
	/**
	 * The cached value of the '{@link #getCompositeFBType() <em>Composite FB
	 * Type</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @see #getCompositeFBType()
	 * @generated
	 * @ordered
	 */
	protected CompositeFBType compositeFBType;

	/**
	 * The cached value of the '{@link #getFbElement() <em>Fb Element</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getFbElement()
	 * @generated
	 * @ordered
	 */
	protected FBNetworkElement fbElement;

	/**
	 * The cached value of the '{@link #getNetworkRuntime() <em>Network
	 * Runtime</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getNetworkRuntime()
	 * @generated
	 * @ordered
	 */
	protected FBNetworkRuntime networkRuntime;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected CompositeFBTypeRuntimeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OperationalSemanticsPackage.Literals.COMPOSITE_FB_TYPE_RUNTIME;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public CompositeFBType getCompositeFBType() {
		if (compositeFBType != null && compositeFBType.eIsProxy()) {
			InternalEObject oldCompositeFBType = (InternalEObject) compositeFBType;
			compositeFBType = (CompositeFBType) eResolveProxy(oldCompositeFBType);
			if (compositeFBType != oldCompositeFBType) {
				InternalEObject newCompositeFBType = (InternalEObject) compositeFBType;
				NotificationChain msgs = oldCompositeFBType.eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- OperationalSemanticsPackage.COMPOSITE_FB_TYPE_RUNTIME__COMPOSITE_FB_TYPE, null, null);
				if (newCompositeFBType.eInternalContainer() == null) {
					msgs = newCompositeFBType.eInverseAdd(this,
							EOPPOSITE_FEATURE_BASE
									- OperationalSemanticsPackage.COMPOSITE_FB_TYPE_RUNTIME__COMPOSITE_FB_TYPE,
							null, msgs);
				}
				if (msgs != null) {
					msgs.dispatch();
				}
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							OperationalSemanticsPackage.COMPOSITE_FB_TYPE_RUNTIME__COMPOSITE_FB_TYPE,
							oldCompositeFBType, compositeFBType));
				}
			}
		}
		return compositeFBType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public CompositeFBType basicGetCompositeFBType() {
		return compositeFBType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetCompositeFBType(CompositeFBType newCompositeFBType, NotificationChain msgs) {
		CompositeFBType oldCompositeFBType = compositeFBType;
		compositeFBType = newCompositeFBType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					OperationalSemanticsPackage.COMPOSITE_FB_TYPE_RUNTIME__COMPOSITE_FB_TYPE, oldCompositeFBType,
					newCompositeFBType);
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
	public void setCompositeFBType(CompositeFBType newCompositeFBType) {
		if (newCompositeFBType != compositeFBType) {
			NotificationChain msgs = null;
			if (compositeFBType != null) {
				msgs = ((InternalEObject) compositeFBType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- OperationalSemanticsPackage.COMPOSITE_FB_TYPE_RUNTIME__COMPOSITE_FB_TYPE, null, msgs);
			}
			if (newCompositeFBType != null) {
				msgs = ((InternalEObject) newCompositeFBType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- OperationalSemanticsPackage.COMPOSITE_FB_TYPE_RUNTIME__COMPOSITE_FB_TYPE, null, msgs);
			}
			msgs = basicSetCompositeFBType(newCompositeFBType, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationalSemanticsPackage.COMPOSITE_FB_TYPE_RUNTIME__COMPOSITE_FB_TYPE, newCompositeFBType,
					newCompositeFBType));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public FBNetworkElement getFbElement() {
		if (fbElement != null && fbElement.eIsProxy()) {
			InternalEObject oldFbElement = (InternalEObject) fbElement;
			fbElement = (FBNetworkElement) eResolveProxy(oldFbElement);
			if (fbElement != oldFbElement) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							OperationalSemanticsPackage.COMPOSITE_FB_TYPE_RUNTIME__FB_ELEMENT, oldFbElement,
							fbElement));
				}
			}
		}
		return fbElement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public FBNetworkElement basicGetFbElement() {
		return fbElement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setFbElement(FBNetworkElement newFbElement) {
		FBNetworkElement oldFbElement = fbElement;
		fbElement = newFbElement;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationalSemanticsPackage.COMPOSITE_FB_TYPE_RUNTIME__FB_ELEMENT, oldFbElement, fbElement));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public FBNetworkRuntime getNetworkRuntime() {
		if (networkRuntime != null && networkRuntime.eIsProxy()) {
			InternalEObject oldNetworkRuntime = (InternalEObject) networkRuntime;
			networkRuntime = (FBNetworkRuntime) eResolveProxy(oldNetworkRuntime);
			if (networkRuntime != oldNetworkRuntime) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							OperationalSemanticsPackage.COMPOSITE_FB_TYPE_RUNTIME__NETWORK_RUNTIME, oldNetworkRuntime,
							networkRuntime));
				}
			}
		}
		return networkRuntime;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public FBNetworkRuntime basicGetNetworkRuntime() {
		return networkRuntime;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setNetworkRuntime(FBNetworkRuntime newNetworkRuntime) {
		FBNetworkRuntime oldNetworkRuntime = networkRuntime;
		networkRuntime = newNetworkRuntime;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					OperationalSemanticsPackage.COMPOSITE_FB_TYPE_RUNTIME__NETWORK_RUNTIME, oldNetworkRuntime,
					networkRuntime));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public CompositeFBType getModel() {
		return compositeFBType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case OperationalSemanticsPackage.COMPOSITE_FB_TYPE_RUNTIME__COMPOSITE_FB_TYPE:
			return basicSetCompositeFBType(null, msgs);
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
		case OperationalSemanticsPackage.COMPOSITE_FB_TYPE_RUNTIME__COMPOSITE_FB_TYPE:
			if (resolve) {
				return getCompositeFBType();
			}
			return basicGetCompositeFBType();
		case OperationalSemanticsPackage.COMPOSITE_FB_TYPE_RUNTIME__FB_ELEMENT:
			if (resolve) {
				return getFbElement();
			}
			return basicGetFbElement();
		case OperationalSemanticsPackage.COMPOSITE_FB_TYPE_RUNTIME__NETWORK_RUNTIME:
			if (resolve) {
				return getNetworkRuntime();
			}
			return basicGetNetworkRuntime();
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
		case OperationalSemanticsPackage.COMPOSITE_FB_TYPE_RUNTIME__COMPOSITE_FB_TYPE:
			setCompositeFBType((CompositeFBType) newValue);
			return;
		case OperationalSemanticsPackage.COMPOSITE_FB_TYPE_RUNTIME__FB_ELEMENT:
			setFbElement((FBNetworkElement) newValue);
			return;
		case OperationalSemanticsPackage.COMPOSITE_FB_TYPE_RUNTIME__NETWORK_RUNTIME:
			setNetworkRuntime((FBNetworkRuntime) newValue);
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
		case OperationalSemanticsPackage.COMPOSITE_FB_TYPE_RUNTIME__COMPOSITE_FB_TYPE:
			setCompositeFBType((CompositeFBType) null);
			return;
		case OperationalSemanticsPackage.COMPOSITE_FB_TYPE_RUNTIME__FB_ELEMENT:
			setFbElement((FBNetworkElement) null);
			return;
		case OperationalSemanticsPackage.COMPOSITE_FB_TYPE_RUNTIME__NETWORK_RUNTIME:
			setNetworkRuntime((FBNetworkRuntime) null);
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
		case OperationalSemanticsPackage.COMPOSITE_FB_TYPE_RUNTIME__COMPOSITE_FB_TYPE:
			return compositeFBType != null;
		case OperationalSemanticsPackage.COMPOSITE_FB_TYPE_RUNTIME__FB_ELEMENT:
			return fbElement != null;
		case OperationalSemanticsPackage.COMPOSITE_FB_TYPE_RUNTIME__NETWORK_RUNTIME:
			return networkRuntime != null;
		default:
			return super.eIsSet(featureID);
		}
	}

} // CompositeFBTypeRuntimeImpl
