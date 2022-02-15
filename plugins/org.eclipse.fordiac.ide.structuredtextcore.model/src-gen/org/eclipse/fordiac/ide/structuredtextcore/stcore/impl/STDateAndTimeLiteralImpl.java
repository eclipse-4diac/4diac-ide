/**
 * *******************************************************************************
 * Copyright (c) 2022 Primetals Technologies GmbH,
 *               2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Martin Jobst, Martin Melik Merkumians
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.structuredtextcore.stcore.impl;

import java.util.Date;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.fordiac.ide.model.data.DataType;

import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateAndTimeLiteral;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>ST Date And Time Literal</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STDateAndTimeLiteralImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STDateAndTimeLiteralImpl#getDateValue <em>Date Value</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STDateAndTimeLiteralImpl#getTimeOfDayValue <em>Time Of Day Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class STDateAndTimeLiteralImpl extends STExpressionImpl implements STDateAndTimeLiteral {
	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected DataType type;

	/**
	 * The default value of the '{@link #getDateValue() <em>Date Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDateValue()
	 * @generated
	 * @ordered
	 */
	protected static final Date DATE_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDateValue() <em>Date Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDateValue()
	 * @generated
	 * @ordered
	 */
	protected Date dateValue = DATE_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getTimeOfDayValue() <em>Time Of Day Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeOfDayValue()
	 * @generated
	 * @ordered
	 */
	protected static final String TIME_OF_DAY_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTimeOfDayValue() <em>Time Of Day Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeOfDayValue()
	 * @generated
	 * @ordered
	 */
	protected String timeOfDayValue = TIME_OF_DAY_VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected STDateAndTimeLiteralImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return STCorePackage.Literals.ST_DATE_AND_TIME_LITERAL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DataType getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (DataType)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, STCorePackage.ST_DATE_AND_TIME_LITERAL__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataType basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(DataType newType) {
		DataType oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, STCorePackage.ST_DATE_AND_TIME_LITERAL__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Date getDateValue() {
		return dateValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDateValue(Date newDateValue) {
		Date oldDateValue = dateValue;
		dateValue = newDateValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, STCorePackage.ST_DATE_AND_TIME_LITERAL__DATE_VALUE, oldDateValue, dateValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTimeOfDayValue() {
		return timeOfDayValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTimeOfDayValue(String newTimeOfDayValue) {
		String oldTimeOfDayValue = timeOfDayValue;
		timeOfDayValue = newTimeOfDayValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, STCorePackage.ST_DATE_AND_TIME_LITERAL__TIME_OF_DAY_VALUE, oldTimeOfDayValue, timeOfDayValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case STCorePackage.ST_DATE_AND_TIME_LITERAL__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case STCorePackage.ST_DATE_AND_TIME_LITERAL__DATE_VALUE:
				return getDateValue();
			case STCorePackage.ST_DATE_AND_TIME_LITERAL__TIME_OF_DAY_VALUE:
				return getTimeOfDayValue();
			default:
				return super.eGet(featureID, resolve, coreType);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case STCorePackage.ST_DATE_AND_TIME_LITERAL__TYPE:
				setType((DataType)newValue);
				return;
			case STCorePackage.ST_DATE_AND_TIME_LITERAL__DATE_VALUE:
				setDateValue((Date)newValue);
				return;
			case STCorePackage.ST_DATE_AND_TIME_LITERAL__TIME_OF_DAY_VALUE:
				setTimeOfDayValue((String)newValue);
				return;
			default:
				super.eSet(featureID, newValue);
				return;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case STCorePackage.ST_DATE_AND_TIME_LITERAL__TYPE:
				setType((DataType)null);
				return;
			case STCorePackage.ST_DATE_AND_TIME_LITERAL__DATE_VALUE:
				setDateValue(DATE_VALUE_EDEFAULT);
				return;
			case STCorePackage.ST_DATE_AND_TIME_LITERAL__TIME_OF_DAY_VALUE:
				setTimeOfDayValue(TIME_OF_DAY_VALUE_EDEFAULT);
				return;
			default:
				super.eUnset(featureID);
				return;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case STCorePackage.ST_DATE_AND_TIME_LITERAL__TYPE:
				return type != null;
			case STCorePackage.ST_DATE_AND_TIME_LITERAL__DATE_VALUE:
				return DATE_VALUE_EDEFAULT == null ? dateValue != null : !DATE_VALUE_EDEFAULT.equals(dateValue);
			case STCorePackage.ST_DATE_AND_TIME_LITERAL__TIME_OF_DAY_VALUE:
				return TIME_OF_DAY_VALUE_EDEFAULT == null ? timeOfDayValue != null : !TIME_OF_DAY_VALUE_EDEFAULT.equals(timeOfDayValue);
			default:
				return super.eIsSet(featureID);
		}
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
		result.append(" (dateValue: "); //$NON-NLS-1$
		result.append(dateValue);
		result.append(", timeOfDayValue: "); //$NON-NLS-1$
		result.append(timeOfDayValue);
		result.append(')');
		return result.toString();
	}

} //STDateAndTimeLiteralImpl
