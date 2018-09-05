/*******************************************************************************
 * Copyright (c) 2012, 2013, 2015 - 2017 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.monitoring.impl;

import java.util.ArrayList;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.fordiac.ide.deployment.monitoringbase.impl.MonitoringBaseElementImpl;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage;
import org.eclipse.fordiac.ide.monitoring.editparts.MonitoringEditPart;
import org.eclipse.gef.EditPart;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Element</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringElementImpl#isForce <em>Force</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringElementImpl#getForceValue <em>Force Value</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringElementImpl#getCurrentValue <em>Current Value</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringElementImpl#getSec <em>Sec</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringElementImpl#getUsec <em>Usec</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MonitoringElementImpl extends MonitoringBaseElementImpl implements
		MonitoringElement {
	/**
	 * The default value of the '{@link #isForce() <em>Force</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isForce()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FORCE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isForce() <em>Force</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isForce()
	 * @generated
	 * @ordered
	 */
	protected boolean force = FORCE_EDEFAULT;

	/**
	 * The default value of the '{@link #getForceValue() <em>Force Value</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getForceValue()
	 * @generated
	 * @ordered
	 */
	protected static final String FORCE_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getForceValue() <em>Force Value</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getForceValue()
	 * @generated
	 * @ordered
	 */
	protected String forceValue = FORCE_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getCurrentValue() <em>Current Value</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getCurrentValue()
	 * @generated
	 * @ordered
	 */
	protected static final String CURRENT_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCurrentValue() <em>Current Value</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getCurrentValue()
	 * @generated
	 * @ordered
	 */
	protected String currentValue = CURRENT_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSec() <em>Sec</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSec()
	 * @generated
	 * @ordered
	 */
	protected static final long SEC_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getSec() <em>Sec</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSec()
	 * @generated
	 * @ordered
	 */
	protected long sec = SEC_EDEFAULT;

	/**
	 * The default value of the '{@link #getUsec() <em>Usec</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getUsec()
	 * @generated
	 * @ordered
	 */
	protected static final long USEC_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getUsec() <em>Usec</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getUsec()
	 * @generated
	 * @ordered
	 */
	protected long usec = USEC_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected MonitoringElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MonitoringPackage.Literals.MONITORING_ELEMENT;
	}

	//private String[] monitoringElementAsArray;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isForce() {
		return force;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setForce(boolean newForce) {
		boolean oldForce = force;
		force = newForce;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MonitoringPackage.MONITORING_ELEMENT__FORCE, oldForce, force));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getForceValue() {
		return forceValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setForceValue(String newForceValue) {
		String oldForceValue = forceValue;
		forceValue = newForceValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MonitoringPackage.MONITORING_ELEMENT__FORCE_VALUE, oldForceValue, forceValue));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String getCurrentValue() {
		return (null == currentValue) ? "N/A" : currentValue;
	}

	private int nrOfHistory = 120;
	private ArrayList<String> historyValues = new ArrayList<String>(120);
	private ArrayList<Long> historySec = new ArrayList<Long>(120);
	private ArrayList<Long> historyUSec = new ArrayList<Long>(120);
	private int currentInt = 0;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public void setCurrentValue(String newCurrentValue) {
		if (currentValue == null
				|| ((currentValue != null) && !currentValue
						.equals(newCurrentValue))) {
			String oldCurrentValue = currentValue;
			currentValue = newCurrentValue;
			if (eNotificationRequired()) {
				eNotify(new ENotificationImpl(this, Notification.SET,
						MonitoringPackage.MONITORING_ELEMENT__CURRENT_VALUE,
						oldCurrentValue, currentValue));
			}
		}
		historyValues.add(currentInt % nrOfHistory, newCurrentValue);
		historySec.add(currentInt % nrOfHistory, getSec());
		historyUSec.add(currentInt % nrOfHistory, getUsec());
		currentInt++;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public long getSec() {
		return sec;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSec(long newSec) {
		long oldSec = sec;
		sec = newSec;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MonitoringPackage.MONITORING_ELEMENT__SEC, oldSec, sec));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public long getUsec() {
		return usec;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUsec(long newUsec) {
		long oldUsec = usec;
		usec = newUsec;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MonitoringPackage.MONITORING_ELEMENT__USEC, oldUsec, usec));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public void forceValue(String value) {
		if (value != null && !value.equals("")) {
			setForce(true);
			setForceValue(value);
		} else {
			setForce(false);
			setForceValue(null);
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MonitoringPackage.MONITORING_ELEMENT__FORCE:
				return isForce();
			case MonitoringPackage.MONITORING_ELEMENT__FORCE_VALUE:
				return getForceValue();
			case MonitoringPackage.MONITORING_ELEMENT__CURRENT_VALUE:
				return getCurrentValue();
			case MonitoringPackage.MONITORING_ELEMENT__SEC:
				return getSec();
			case MonitoringPackage.MONITORING_ELEMENT__USEC:
				return getUsec();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MonitoringPackage.MONITORING_ELEMENT__FORCE:
				setForce((Boolean)newValue);
				return;
			case MonitoringPackage.MONITORING_ELEMENT__FORCE_VALUE:
				setForceValue((String)newValue);
				return;
			case MonitoringPackage.MONITORING_ELEMENT__CURRENT_VALUE:
				setCurrentValue((String)newValue);
				return;
			case MonitoringPackage.MONITORING_ELEMENT__SEC:
				setSec((Long)newValue);
				return;
			case MonitoringPackage.MONITORING_ELEMENT__USEC:
				setUsec((Long)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case MonitoringPackage.MONITORING_ELEMENT__FORCE:
				setForce(FORCE_EDEFAULT);
				return;
			case MonitoringPackage.MONITORING_ELEMENT__FORCE_VALUE:
				setForceValue(FORCE_VALUE_EDEFAULT);
				return;
			case MonitoringPackage.MONITORING_ELEMENT__CURRENT_VALUE:
				setCurrentValue(CURRENT_VALUE_EDEFAULT);
				return;
			case MonitoringPackage.MONITORING_ELEMENT__SEC:
				setSec(SEC_EDEFAULT);
				return;
			case MonitoringPackage.MONITORING_ELEMENT__USEC:
				setUsec(USEC_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MonitoringPackage.MONITORING_ELEMENT__FORCE:
				return force != FORCE_EDEFAULT;
			case MonitoringPackage.MONITORING_ELEMENT__FORCE_VALUE:
				return FORCE_VALUE_EDEFAULT == null ? forceValue != null : !FORCE_VALUE_EDEFAULT.equals(forceValue);
			case MonitoringPackage.MONITORING_ELEMENT__CURRENT_VALUE:
				return CURRENT_VALUE_EDEFAULT == null ? currentValue != null : !CURRENT_VALUE_EDEFAULT.equals(currentValue);
			case MonitoringPackage.MONITORING_ELEMENT__SEC:
				return sec != SEC_EDEFAULT;
			case MonitoringPackage.MONITORING_ELEMENT__USEC:
				return usec != USEC_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (force: "); //$NON-NLS-1$
		result.append(force);
		result.append(", forceValue: "); //$NON-NLS-1$
		result.append(forceValue);
		result.append(", currentValue: "); //$NON-NLS-1$
		result.append(currentValue);
		result.append(", sec: "); //$NON-NLS-1$
		result.append(sec);
		result.append(", usec: "); //$NON-NLS-1$
		result.append(usec);
		result.append(')');
		return result.toString();
	}


	@Override
	public EditPart createEditPart() {
		return new MonitoringEditPart();
	}

} // MonitoringElementImpl
