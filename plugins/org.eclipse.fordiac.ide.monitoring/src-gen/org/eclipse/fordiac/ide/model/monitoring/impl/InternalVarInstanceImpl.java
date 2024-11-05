/**
 * ******************************************************************************
 * Copyright (c) 2012, 2013, 2015 - 2017 Profactor GmbH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz
 *      - initial API and implementation and/or initial documentation
 * ******************************************************************************
 *
 */
package org.eclipse.fordiac.ide.model.monitoring.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.impl.VarDeclarationImpl;
import org.eclipse.fordiac.ide.model.monitoring.InternalVarInstance;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Internal Var Instance</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.InternalVarInstanceImpl#getFb
 * <em>Fb</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InternalVarInstanceImpl extends VarDeclarationImpl implements InternalVarInstance {
	/**
	 * The cached value of the '{@link #getFb() <em>Fb</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getFb()
	 * @generated
	 * @ordered
	 */
	protected FB fb;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected InternalVarInstanceImpl() {
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MonitoringPackage.Literals.INTERNAL_VAR_INSTANCE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public FB getFb() {
		if (fb != null && fb.eIsProxy()) {
			final InternalEObject oldFb = (InternalEObject) fb;
			fb = (FB) eResolveProxy(oldFb);
			if ((fb != oldFb) && eNotificationRequired()) {
				eNotify(new ENotificationImpl(this, Notification.RESOLVE, MonitoringPackage.INTERNAL_VAR_INSTANCE__FB,
						oldFb, fb));
			}
		}
		return fb;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public FB basicGetFb() {
		return fb;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setFb(final FB newFb) {
		final FB oldFb = fb;
		fb = newFb;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, MonitoringPackage.INTERNAL_VAR_INSTANCE__FB, oldFb,
					fb));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public FBNetworkElement getFBNetworkElement() {
		return getFb();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
		switch (featureID) {
		case MonitoringPackage.INTERNAL_VAR_INSTANCE__FB:
			if (resolve) {
				return getFb();
			}
			return basicGetFb();
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
		case MonitoringPackage.INTERNAL_VAR_INSTANCE__FB:
			setFb((FB) newValue);
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
		case MonitoringPackage.INTERNAL_VAR_INSTANCE__FB:
			setFb((FB) null);
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
		case MonitoringPackage.INTERNAL_VAR_INSTANCE__FB -> fb != null;
		default -> super.eIsSet(featureID);
		};
	}

} // InternalVarInstanceImpl
