/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2022-2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.fordiac.ide.model.data.impl.DataTypeImpl;

import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Adapter Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterTypeImpl#getAdapterFBType <em>Adapter FB Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterTypeImpl#getPlugType <em>Plug Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AdapterTypeImpl extends DataTypeImpl implements AdapterType {
	/**
	 * The cached value of the '{@link #getAdapterFBType() <em>Adapter FB Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdapterFBType()
	 * @generated
	 * @ordered
	 */
	protected AdapterFBType adapterFBType;

	/**
	 * The cached value of the '{@link #getPlugType() <em>Plug Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlugType()
	 * @generated
	 * @ordered
	 */
	protected AdapterFBType plugType;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AdapterTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.ADAPTER_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AdapterFBType getAdapterFBType() {
		return adapterFBType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAdapterFBType(AdapterFBType newAdapterFBType, NotificationChain msgs) {
		AdapterFBType oldAdapterFBType = adapterFBType;
		adapterFBType = newAdapterFBType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ADAPTER_TYPE__ADAPTER_FB_TYPE, oldAdapterFBType, newAdapterFBType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public void setAdapterFBType(AdapterFBType newAdapterFBType) {
		if (newAdapterFBType != adapterFBType) {
			NotificationChain msgs = null;
			if (adapterFBType != null)
				msgs = ((InternalEObject)adapterFBType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.ADAPTER_TYPE__ADAPTER_FB_TYPE, null, msgs);
			if (newAdapterFBType != null)
				msgs = ((InternalEObject)newAdapterFBType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.ADAPTER_TYPE__ADAPTER_FB_TYPE, null, msgs);
			msgs = basicSetAdapterFBType(newAdapterFBType, msgs);
			msgs = updatePlugType(newAdapterFBType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ADAPTER_TYPE__ADAPTER_FB_TYPE, newAdapterFBType, newAdapterFBType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AdapterFBType getPlugType() {
		return plugType;
	}

	private NotificationChain updatePlugType(AdapterFBType newAdapterFBType, NotificationChain msgs) {
		AdapterFBType newPlugType = (newAdapterFBType != null) ? org.eclipse.fordiac.ide.model.Annotations.createPlugType(newAdapterFBType) : null;
		if (newPlugType != plugType) {
			if (plugType != null)
				msgs = ((InternalEObject)plugType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.ADAPTER_TYPE__PLUG_TYPE, null, msgs);
			if (newPlugType != null)
				msgs = ((InternalEObject)newPlugType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.ADAPTER_TYPE__PLUG_TYPE, null, msgs);
			msgs = basicSetPlugType(newPlugType, msgs);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPlugType(AdapterFBType newPlugType, NotificationChain msgs) {
		AdapterFBType oldPlugType = plugType;
		plugType = newPlugType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ADAPTER_TYPE__PLUG_TYPE, oldPlugType, newPlugType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InterfaceList getInterfaceList() {
		return org.eclipse.fordiac.ide.model.Annotations.getInterfaceList(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AdapterFBType getSocketType() {
		//the socket form is the form how adapter types are edited and stored
		return getAdapterFBType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.ADAPTER_TYPE__ADAPTER_FB_TYPE:
				return basicSetAdapterFBType(null, msgs);
			case LibraryElementPackage.ADAPTER_TYPE__PLUG_TYPE:
				return basicSetPlugType(null, msgs);
			default:
				return super.eInverseRemove(otherEnd, featureID, msgs);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LibraryElementPackage.ADAPTER_TYPE__ADAPTER_FB_TYPE:
				return getAdapterFBType();
			case LibraryElementPackage.ADAPTER_TYPE__PLUG_TYPE:
				return getPlugType();
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
			case LibraryElementPackage.ADAPTER_TYPE__ADAPTER_FB_TYPE:
				setAdapterFBType((AdapterFBType)newValue);
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
			case LibraryElementPackage.ADAPTER_TYPE__ADAPTER_FB_TYPE:
				setAdapterFBType((AdapterFBType)null);
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
			case LibraryElementPackage.ADAPTER_TYPE__ADAPTER_FB_TYPE:
				return adapterFBType != null;
			case LibraryElementPackage.ADAPTER_TYPE__PLUG_TYPE:
				return plugType != null;
			default:
				return super.eIsSet(featureID);
		}
	}

} //AdapterTypeImpl
