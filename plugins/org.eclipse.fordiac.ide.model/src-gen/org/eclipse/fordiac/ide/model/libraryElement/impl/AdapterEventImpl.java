/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterEvent;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Adapter Event</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterEventImpl#getAdapterDeclaration <em>Adapter Declaration</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AdapterEventImpl extends EventImpl implements AdapterEvent {
	/**
	 * The cached value of the '{@link #getAdapterDeclaration() <em>Adapter Declaration</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdapterDeclaration()
	 * @generated
	 * @ordered
	 */
	protected AdapterDeclaration adapterDeclaration;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AdapterEventImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.ADAPTER_EVENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AdapterDeclaration getAdapterDeclaration() {
		if (adapterDeclaration != null && adapterDeclaration.eIsProxy()) {
			InternalEObject oldAdapterDeclaration = (InternalEObject)adapterDeclaration;
			adapterDeclaration = (AdapterDeclaration)eResolveProxy(oldAdapterDeclaration);
			if (adapterDeclaration != oldAdapterDeclaration) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.ADAPTER_EVENT__ADAPTER_DECLARATION, oldAdapterDeclaration, adapterDeclaration));
			}
		}
		return adapterDeclaration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AdapterDeclaration basicGetAdapterDeclaration() {
		return adapterDeclaration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAdapterDeclaration(AdapterDeclaration newAdapterDeclaration) {
		AdapterDeclaration oldAdapterDeclaration = adapterDeclaration;
		adapterDeclaration = newAdapterDeclaration;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ADAPTER_EVENT__ADAPTER_DECLARATION, oldAdapterDeclaration, adapterDeclaration));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LibraryElementPackage.ADAPTER_EVENT__ADAPTER_DECLARATION:
				if (resolve) return getAdapterDeclaration();
				return basicGetAdapterDeclaration();
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
			case LibraryElementPackage.ADAPTER_EVENT__ADAPTER_DECLARATION:
				setAdapterDeclaration((AdapterDeclaration)newValue);
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
			case LibraryElementPackage.ADAPTER_EVENT__ADAPTER_DECLARATION:
				setAdapterDeclaration((AdapterDeclaration)null);
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
			case LibraryElementPackage.ADAPTER_EVENT__ADAPTER_DECLARATION:
				return adapterDeclaration != null;
		}
		return super.eIsSet(featureID);
	}

	
	@Override
	public String getName() {
		if (getAdapterDeclaration() == null) {
			return super.getName();
		}
		return getAdapterDeclaration().getName() + "." + super.getName();
	}
	
	
	
} //AdapterEventImpl
