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
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.fordiac.ide.deployment.devResponse.Data;
import org.eclipse.fordiac.ide.deployment.devResponse.DevResponsePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Data</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.DataImpl#getValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.DataImpl#getTime <em>Time</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.DataImpl#getForced <em>Forced</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DataImpl extends EObjectImpl implements Data {
	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected String value = VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getTime() <em>Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTime()
	 * @generated
	 * @ordered
	 */
	protected static final String TIME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTime() <em>Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTime()
	 * @generated
	 * @ordered
	 */
	protected String time = TIME_EDEFAULT;

	/**
	 * The default value of the '{@link #getForced() <em>Forced</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getForced()
	 * @generated
	 * @ordered
	 */
	protected static final String FORCED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getForced() <em>Forced</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getForced()
	 * @generated
	 * @ordered
	 */
	protected String forced = FORCED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DataImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DevResponsePackage.Literals.DATA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setValue(String newValue) {
		String oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DevResponsePackage.DATA__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTime() {
		return time;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTime(String newTime) {
		String oldTime = time;
		time = newTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DevResponsePackage.DATA__TIME, oldTime, time));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getForced() {
		return forced;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setForced(String newForced) {
		String oldForced = forced;
		forced = newForced;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DevResponsePackage.DATA__FORCED, oldForced, forced));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DevResponsePackage.DATA__VALUE:
				return getValue();
			case DevResponsePackage.DATA__TIME:
				return getTime();
			case DevResponsePackage.DATA__FORCED:
				return getForced();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DevResponsePackage.DATA__VALUE:
				setValue((String)newValue);
				return;
			case DevResponsePackage.DATA__TIME:
				setTime((String)newValue);
				return;
			case DevResponsePackage.DATA__FORCED:
				setForced((String)newValue);
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
			case DevResponsePackage.DATA__VALUE:
				setValue(VALUE_EDEFAULT);
				return;
			case DevResponsePackage.DATA__TIME:
				setTime(TIME_EDEFAULT);
				return;
			case DevResponsePackage.DATA__FORCED:
				setForced(FORCED_EDEFAULT);
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
			case DevResponsePackage.DATA__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
			case DevResponsePackage.DATA__TIME:
				return TIME_EDEFAULT == null ? time != null : !TIME_EDEFAULT.equals(time);
			case DevResponsePackage.DATA__FORCED:
				return FORCED_EDEFAULT == null ? forced != null : !FORCED_EDEFAULT.equals(forced);
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
		result.append(" (value: "); //$NON-NLS-1$
		result.append(value);
		result.append(", time: "); //$NON-NLS-1$
		result.append(time);
		result.append(", forced: "); //$NON-NLS-1$
		result.append(forced);
		result.append(')');
		return result.toString();
	}

} //DataImpl
