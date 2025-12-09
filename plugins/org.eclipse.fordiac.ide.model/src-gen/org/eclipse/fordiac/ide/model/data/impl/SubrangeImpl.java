/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.model.data.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.fordiac.ide.model.data.DataPackage;
import org.eclipse.fordiac.ide.model.data.Subrange;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Subrange</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.data.impl.SubrangeImpl#getLowerLimit <em>Lower Limit</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.data.impl.SubrangeImpl#getUpperLimit <em>Upper Limit</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SubrangeImpl extends EObjectImpl implements Subrange {
	/**
	 * The default value of the '{@link #getLowerLimit() <em>Lower Limit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLowerLimit()
	 * @generated
	 * @ordered
	 */
	protected static final int LOWER_LIMIT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLowerLimit() <em>Lower Limit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLowerLimit()
	 * @generated
	 * @ordered
	 */
	protected int lowerLimit = LOWER_LIMIT_EDEFAULT;

	/**
	 * This is true if the Lower Limit attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean lowerLimitESet;

	/**
	 * The default value of the '{@link #getUpperLimit() <em>Upper Limit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpperLimit()
	 * @generated
	 * @ordered
	 */
	protected static final int UPPER_LIMIT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getUpperLimit() <em>Upper Limit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpperLimit()
	 * @generated
	 * @ordered
	 */
	protected int upperLimit = UPPER_LIMIT_EDEFAULT;

	/**
	 * This is true if the Upper Limit attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean upperLimitESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SubrangeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DataPackage.Literals.SUBRANGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getLowerLimit() {
		return lowerLimit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLowerLimit(int newLowerLimit) {
		int oldLowerLimit = lowerLimit;
		lowerLimit = newLowerLimit;
		boolean oldLowerLimitESet = lowerLimitESet;
		lowerLimitESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DataPackage.SUBRANGE__LOWER_LIMIT, oldLowerLimit, lowerLimit, !oldLowerLimitESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetLowerLimit() {
		int oldLowerLimit = lowerLimit;
		boolean oldLowerLimitESet = lowerLimitESet;
		lowerLimit = LOWER_LIMIT_EDEFAULT;
		lowerLimitESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, DataPackage.SUBRANGE__LOWER_LIMIT, oldLowerLimit, LOWER_LIMIT_EDEFAULT, oldLowerLimitESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetLowerLimit() {
		return lowerLimitESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getUpperLimit() {
		return upperLimit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUpperLimit(int newUpperLimit) {
		int oldUpperLimit = upperLimit;
		upperLimit = newUpperLimit;
		boolean oldUpperLimitESet = upperLimitESet;
		upperLimitESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DataPackage.SUBRANGE__UPPER_LIMIT, oldUpperLimit, upperLimit, !oldUpperLimitESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void unsetUpperLimit() {
		int oldUpperLimit = upperLimit;
		boolean oldUpperLimitESet = upperLimitESet;
		upperLimit = UPPER_LIMIT_EDEFAULT;
		upperLimitESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, DataPackage.SUBRANGE__UPPER_LIMIT, oldUpperLimit, UPPER_LIMIT_EDEFAULT, oldUpperLimitESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSetUpperLimit() {
		return upperLimitESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DataPackage.SUBRANGE__LOWER_LIMIT:
				return getLowerLimit();
			case DataPackage.SUBRANGE__UPPER_LIMIT:
				return getUpperLimit();
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
			case DataPackage.SUBRANGE__LOWER_LIMIT:
				setLowerLimit((Integer)newValue);
				return;
			case DataPackage.SUBRANGE__UPPER_LIMIT:
				setUpperLimit((Integer)newValue);
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
			case DataPackage.SUBRANGE__LOWER_LIMIT:
				unsetLowerLimit();
				return;
			case DataPackage.SUBRANGE__UPPER_LIMIT:
				unsetUpperLimit();
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
			case DataPackage.SUBRANGE__LOWER_LIMIT:
				return isSetLowerLimit();
			case DataPackage.SUBRANGE__UPPER_LIMIT:
				return isSetUpperLimit();
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
		result.append(" (lowerLimit: "); //$NON-NLS-1$
		if (lowerLimitESet) result.append(lowerLimit); else result.append("<unset>"); //$NON-NLS-1$
		result.append(", upperLimit: "); //$NON-NLS-1$
		if (upperLimitESet) result.append(upperLimit); else result.append("<unset>"); //$NON-NLS-1$
		result.append(')');
		return result.toString();
	}

} //SubrangeImpl
