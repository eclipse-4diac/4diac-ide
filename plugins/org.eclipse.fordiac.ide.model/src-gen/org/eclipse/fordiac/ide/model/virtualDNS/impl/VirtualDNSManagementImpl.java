/*******************************************************************************
 * Copyright (c) 2009 Profactor GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.virtualDNS.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSCollection;
import org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSManagement;
import org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Management</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.virtualDNS.impl.VirtualDNSManagementImpl#getAvailableDNSCollections <em>Available DNS Collections</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.virtualDNS.impl.VirtualDNSManagementImpl#getActiveVirtualDNS <em>Active Virtual DNS</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VirtualDNSManagementImpl extends EObjectImpl implements
		VirtualDNSManagement {
	/**
	 * The cached value of the '{@link #getAvailableDNSCollections()
	 * <em>Available DNS Collections</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAvailableDNSCollections()
	 * @generated
	 * @ordered
	 */
	protected EList<VirtualDNSCollection> availableDNSCollections;

	/**
	 * The cached value of the '{@link #getActiveVirtualDNS() <em>Active Virtual DNS</em>}' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getActiveVirtualDNS()
	 * @generated
	 * @ordered
	 */
	protected VirtualDNSCollection activeVirtualDNS;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected VirtualDNSManagementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return VirtualDNSPackage.Literals.VIRTUAL_DNS_MANAGEMENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VirtualDNSCollection> getAvailableDNSCollections() {
		if (availableDNSCollections == null) {
			availableDNSCollections = new EObjectContainmentEList<VirtualDNSCollection>(VirtualDNSCollection.class, this, VirtualDNSPackage.VIRTUAL_DNS_MANAGEMENT__AVAILABLE_DNS_COLLECTIONS);
		}
		return availableDNSCollections;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VirtualDNSCollection getActiveVirtualDNS() {
		if (activeVirtualDNS != null && activeVirtualDNS.eIsProxy()) {
			InternalEObject oldActiveVirtualDNS = (InternalEObject)activeVirtualDNS;
			activeVirtualDNS = (VirtualDNSCollection)eResolveProxy(oldActiveVirtualDNS);
			if (activeVirtualDNS != oldActiveVirtualDNS) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, VirtualDNSPackage.VIRTUAL_DNS_MANAGEMENT__ACTIVE_VIRTUAL_DNS, oldActiveVirtualDNS, activeVirtualDNS));
			}
		}
		return activeVirtualDNS;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public VirtualDNSCollection basicGetActiveVirtualDNS() {
		return activeVirtualDNS;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setActiveVirtualDNS(VirtualDNSCollection newActiveVirtualDNS) {
		VirtualDNSCollection oldActiveVirtualDNS = activeVirtualDNS;
		activeVirtualDNS = newActiveVirtualDNS;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VirtualDNSPackage.VIRTUAL_DNS_MANAGEMENT__ACTIVE_VIRTUAL_DNS, oldActiveVirtualDNS, activeVirtualDNS));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case VirtualDNSPackage.VIRTUAL_DNS_MANAGEMENT__AVAILABLE_DNS_COLLECTIONS:
				return ((InternalEList<?>)getAvailableDNSCollections()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case VirtualDNSPackage.VIRTUAL_DNS_MANAGEMENT__AVAILABLE_DNS_COLLECTIONS:
				return getAvailableDNSCollections();
			case VirtualDNSPackage.VIRTUAL_DNS_MANAGEMENT__ACTIVE_VIRTUAL_DNS:
				if (resolve) return getActiveVirtualDNS();
				return basicGetActiveVirtualDNS();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case VirtualDNSPackage.VIRTUAL_DNS_MANAGEMENT__AVAILABLE_DNS_COLLECTIONS:
				getAvailableDNSCollections().clear();
				getAvailableDNSCollections().addAll((Collection<? extends VirtualDNSCollection>)newValue);
				return;
			case VirtualDNSPackage.VIRTUAL_DNS_MANAGEMENT__ACTIVE_VIRTUAL_DNS:
				setActiveVirtualDNS((VirtualDNSCollection)newValue);
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
			case VirtualDNSPackage.VIRTUAL_DNS_MANAGEMENT__AVAILABLE_DNS_COLLECTIONS:
				getAvailableDNSCollections().clear();
				return;
			case VirtualDNSPackage.VIRTUAL_DNS_MANAGEMENT__ACTIVE_VIRTUAL_DNS:
				setActiveVirtualDNS((VirtualDNSCollection)null);
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
			case VirtualDNSPackage.VIRTUAL_DNS_MANAGEMENT__AVAILABLE_DNS_COLLECTIONS:
				return availableDNSCollections != null && !availableDNSCollections.isEmpty();
			case VirtualDNSPackage.VIRTUAL_DNS_MANAGEMENT__ACTIVE_VIRTUAL_DNS:
				return activeVirtualDNS != null;
		}
		return super.eIsSet(featureID);
	}

	@Override
	public String getReplacedString(String value) {
		StringBuilder replacedValue = new StringBuilder(); 
		
		int start = value.indexOf('%', 0);
		int end = -1;
		
		if(0 != start){
			//the % is not in the first position
			replacedValue.append(value.substring(0, start));
		}
		
		while(-1 != start){
			start++; //pass by the starting %
			end = value.indexOf('%', start); 
			if(-1 != end){
				String name = value.substring(start, end);
				name = getValueForName(name);
				if(null != name){
					replacedValue.append(name);	
				}
				start = value.indexOf('%', end + 1);
				if((-1 != start) && (start != end + 1)){
					replacedValue.append(value.substring(end + 1, start));
				}
			}else{
				replacedValue.append(value.substring(start -1));
				return replacedValue.toString();
			}
		}
		
		replacedValue.append(value.substring(end + 1));		
		return replacedValue.toString();
	}

	private String getValueForName(String name) {
		String retVal = null;
		VirtualDNSCollection collection = getActiveVirtualDNS();
		if(null != collection){
			retVal = getActiveVirtualDNS().getValueFor(name);
		}
		return retVal;
	}
} // VirtualDNSManagementImpl
