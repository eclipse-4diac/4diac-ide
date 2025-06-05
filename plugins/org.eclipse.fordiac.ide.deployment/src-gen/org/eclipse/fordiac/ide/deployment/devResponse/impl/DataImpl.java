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
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.fordiac.ide.deployment.devResponse.Data;
import org.eclipse.fordiac.ide.deployment.devResponse.DevResponsePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Data</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.DataImpl#getValue
 * <em>Value</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.DataImpl#getForced
 * <em>Forced</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DataImpl extends EObjectImpl implements Data {
	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected String value = VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getForced() <em>Forced</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getForced()
	 * @generated
	 * @ordered
	 */
	protected static final String FORCED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getForced() <em>Forced</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getForced()
	 * @generated
	 * @ordered
	 */
	protected String forced = FORCED_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected DataImpl() {
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DevResponsePackage.Literals.DATA;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setValue(final String newValue) {
		final String oldValue = value;
		value = newValue;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, DevResponsePackage.DATA__VALUE, oldValue, value));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getForced() {
		return forced;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setForced(final String newForced) {
		final String oldForced = forced;
		forced = newForced;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, DevResponsePackage.DATA__FORCED, oldForced, forced));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
		return switch (featureID) {
		case DevResponsePackage.DATA__VALUE -> getValue();
		case DevResponsePackage.DATA__FORCED -> getForced();
		default -> super.eGet(featureID, resolve, coreType);
		};
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eSet(final int featureID, final Object newValue) {
		switch (featureID) {
		case DevResponsePackage.DATA__VALUE:
			setValue((String) newValue);
			return;
		case DevResponsePackage.DATA__FORCED:
			setForced((String) newValue);
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
		case DevResponsePackage.DATA__VALUE:
			setValue(VALUE_EDEFAULT);
			return;
		case DevResponsePackage.DATA__FORCED:
			setForced(FORCED_EDEFAULT);
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
		case DevResponsePackage.DATA__VALUE -> VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
		case DevResponsePackage.DATA__FORCED -> FORCED_EDEFAULT == null ? forced != null : !FORCED_EDEFAULT.equals(forced);
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
		result.append(" (value: "); //$NON-NLS-1$
		result.append(value);
		result.append(", forced: "); //$NON-NLS-1$
		result.append(forced);
		result.append(')');
		return result.toString();
	}

} // DataImpl
