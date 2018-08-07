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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.fordiac.ide.deployment.monitoringbase.impl.MonitoringBaseElementImpl;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringAdapterElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage;
import org.eclipse.fordiac.ide.monitoring.editparts.MonitoringAdapterEditPart;
import org.eclipse.gef.EditPart;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Adapter Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringAdapterElementImpl#getElements <em>Elements</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringAdapterElementImpl#getMonitoredAdapterFB <em>Monitored Adapter FB</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MonitoringAdapterElementImpl extends MonitoringBaseElementImpl implements MonitoringAdapterElement {
	/**
	 * The cached value of the '{@link #getElements() <em>Elements</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElements()
	 * @generated
	 * @ordered
	 */
	protected EList<MonitoringElement> elements;

	/**
	 * The cached value of the '{@link #getMonitoredAdapterFB() <em>Monitored Adapter FB</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMonitoredAdapterFB()
	 * @generated
	 * @ordered
	 */
	protected AdapterFB monitoredAdapterFB;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MonitoringAdapterElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MonitoringPackage.Literals.MONITORING_ADAPTER_ELEMENT;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<MonitoringElement> getElements() {
		if (elements == null) {
			elements = new EObjectContainmentEList<MonitoringElement>(MonitoringElement.class, this, MonitoringPackage.MONITORING_ADAPTER_ELEMENT__ELEMENTS);
		}
		return elements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AdapterFB getMonitoredAdapterFB() {
		if (monitoredAdapterFB != null && monitoredAdapterFB.eIsProxy()) {
			InternalEObject oldMonitoredAdapterFB = (InternalEObject)monitoredAdapterFB;
			monitoredAdapterFB = (AdapterFB)eResolveProxy(oldMonitoredAdapterFB);
			if (monitoredAdapterFB != oldMonitoredAdapterFB) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MonitoringPackage.MONITORING_ADAPTER_ELEMENT__MONITORED_ADAPTER_FB, oldMonitoredAdapterFB, monitoredAdapterFB));
			}
		}
		return monitoredAdapterFB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AdapterFB basicGetMonitoredAdapterFB() {
		return monitoredAdapterFB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMonitoredAdapterFB(AdapterFB newMonitoredAdapterFB) {
		AdapterFB oldMonitoredAdapterFB = monitoredAdapterFB;
		monitoredAdapterFB = newMonitoredAdapterFB;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MonitoringPackage.MONITORING_ADAPTER_ELEMENT__MONITORED_ADAPTER_FB, oldMonitoredAdapterFB, monitoredAdapterFB));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MonitoringPackage.MONITORING_ADAPTER_ELEMENT__ELEMENTS:
				return ((InternalEList<?>)getElements()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MonitoringPackage.MONITORING_ADAPTER_ELEMENT__ELEMENTS:
				return getElements();
			case MonitoringPackage.MONITORING_ADAPTER_ELEMENT__MONITORED_ADAPTER_FB:
				if (resolve) return getMonitoredAdapterFB();
				return basicGetMonitoredAdapterFB();
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
			case MonitoringPackage.MONITORING_ADAPTER_ELEMENT__ELEMENTS:
				getElements().clear();
				getElements().addAll((Collection<? extends MonitoringElement>)newValue);
				return;
			case MonitoringPackage.MONITORING_ADAPTER_ELEMENT__MONITORED_ADAPTER_FB:
				setMonitoredAdapterFB((AdapterFB)newValue);
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
			case MonitoringPackage.MONITORING_ADAPTER_ELEMENT__ELEMENTS:
				getElements().clear();
				return;
			case MonitoringPackage.MONITORING_ADAPTER_ELEMENT__MONITORED_ADAPTER_FB:
				setMonitoredAdapterFB((AdapterFB)null);
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
			case MonitoringPackage.MONITORING_ADAPTER_ELEMENT__ELEMENTS:
				return elements != null && !elements.isEmpty();
			case MonitoringPackage.MONITORING_ADAPTER_ELEMENT__MONITORED_ADAPTER_FB:
				return monitoredAdapterFB != null;
		}
		return super.eIsSet(featureID);
	}

	@Override
	public EditPart createEditPart() {
		return new MonitoringAdapterEditPart();
	}

} //MonitoringAdapterElementImpl
