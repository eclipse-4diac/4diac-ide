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
import org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSEntry;
import org.eclipse.fordiac.ide.model.virtualDNS.VirtualDNSPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Collection</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.virtualDNS.impl.VirtualDNSCollectionImpl#getVirtualDNSEntries <em>Virtual DNS Entries</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.virtualDNS.impl.VirtualDNSCollectionImpl#getName <em>Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VirtualDNSCollectionImpl extends EObjectImpl implements
		VirtualDNSCollection {
	/**
	 * The cached value of the '{@link #getVirtualDNSEntries()
	 * <em>Virtual DNS Entries</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getVirtualDNSEntries()
	 * @generated
	 * @ordered
	 */
	protected EList<VirtualDNSEntry> virtualDNSEntries;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected VirtualDNSCollectionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return VirtualDNSPackage.Literals.VIRTUAL_DNS_COLLECTION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VirtualDNSEntry> getVirtualDNSEntries() {
		if (virtualDNSEntries == null) {
			virtualDNSEntries = new EObjectContainmentEList<VirtualDNSEntry>(VirtualDNSEntry.class, this, VirtualDNSPackage.VIRTUAL_DNS_COLLECTION__VIRTUAL_DNS_ENTRIES);
		}
		return virtualDNSEntries;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, VirtualDNSPackage.VIRTUAL_DNS_COLLECTION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case VirtualDNSPackage.VIRTUAL_DNS_COLLECTION__VIRTUAL_DNS_ENTRIES:
				return ((InternalEList<?>)getVirtualDNSEntries()).basicRemove(otherEnd, msgs);
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
			case VirtualDNSPackage.VIRTUAL_DNS_COLLECTION__VIRTUAL_DNS_ENTRIES:
				return getVirtualDNSEntries();
			case VirtualDNSPackage.VIRTUAL_DNS_COLLECTION__NAME:
				return getName();
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
			case VirtualDNSPackage.VIRTUAL_DNS_COLLECTION__VIRTUAL_DNS_ENTRIES:
				getVirtualDNSEntries().clear();
				getVirtualDNSEntries().addAll((Collection<? extends VirtualDNSEntry>)newValue);
				return;
			case VirtualDNSPackage.VIRTUAL_DNS_COLLECTION__NAME:
				setName((String)newValue);
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
			case VirtualDNSPackage.VIRTUAL_DNS_COLLECTION__VIRTUAL_DNS_ENTRIES:
				getVirtualDNSEntries().clear();
				return;
			case VirtualDNSPackage.VIRTUAL_DNS_COLLECTION__NAME:
				setName(NAME_EDEFAULT);
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
			case VirtualDNSPackage.VIRTUAL_DNS_COLLECTION__VIRTUAL_DNS_ENTRIES:
				return virtualDNSEntries != null && !virtualDNSEntries.isEmpty();
			case VirtualDNSPackage.VIRTUAL_DNS_COLLECTION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
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
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(')');
		return result.toString();
	}

	@Override
	public String getValueFor(String name) {
		for (VirtualDNSEntry entry : getVirtualDNSEntries()) {
			if (entry.getName().equals(name)) {
				return entry.getValue();
			}
		}
		return null;
	}

} // VirtualDNSCollectionImpl
