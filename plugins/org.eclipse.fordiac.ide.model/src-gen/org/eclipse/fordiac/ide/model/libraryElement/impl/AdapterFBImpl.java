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
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Adapter FB</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterFBImpl#getAdapterDecl <em>Adapter Decl</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AdapterFBImpl extends FBImpl implements AdapterFB {
	/**
	 * The cached value of the '{@link #getAdapterDecl() <em>Adapter Decl</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdapterDecl()
	 * @generated
	 * @ordered
	 */
	protected AdapterDeclaration adapterDecl;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected AdapterFBImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.ADAPTER_FB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AdapterDeclaration getAdapterDecl() {
		if (adapterDecl != null && adapterDecl.eIsProxy()) {
			InternalEObject oldAdapterDecl = (InternalEObject)adapterDecl;
			adapterDecl = (AdapterDeclaration)eResolveProxy(oldAdapterDecl);
			if (adapterDecl != oldAdapterDecl) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.ADAPTER_FB__ADAPTER_DECL, oldAdapterDecl, adapterDecl));
			}
		}
		return adapterDecl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AdapterDeclaration basicGetAdapterDecl() {
		return adapterDecl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAdapterDecl(AdapterDeclaration newAdapterDecl, NotificationChain msgs) {
		AdapterDeclaration oldAdapterDecl = adapterDecl;
		adapterDecl = newAdapterDecl;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ADAPTER_FB__ADAPTER_DECL, oldAdapterDecl, newAdapterDecl);
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
	public void setAdapterDecl(AdapterDeclaration newAdapterDecl) {
		if (newAdapterDecl != adapterDecl) {
			NotificationChain msgs = null;
			if (adapterDecl != null)
				msgs = ((InternalEObject)adapterDecl).eInverseRemove(this, LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_FB, AdapterDeclaration.class, msgs);
			if (newAdapterDecl != null)
				msgs = ((InternalEObject)newAdapterDecl).eInverseAdd(this, LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_FB, AdapterDeclaration.class, msgs);
			msgs = basicSetAdapterDecl(newAdapterDecl, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ADAPTER_FB__ADAPTER_DECL, newAdapterDecl, newAdapterDecl));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isPlug() {
		return org.eclipse.fordiac.ide.model.Annotations.isPlug(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.ADAPTER_FB__ADAPTER_DECL:
				if (adapterDecl != null)
					msgs = ((InternalEObject)adapterDecl).eInverseRemove(this, LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_FB, AdapterDeclaration.class, msgs);
				return basicSetAdapterDecl((AdapterDeclaration)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.ADAPTER_FB__ADAPTER_DECL:
				return basicSetAdapterDecl(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSocket() {
		return org.eclipse.fordiac.ide.model.Annotations.isSocket(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FBType getType() {
		return org.eclipse.fordiac.ide.model.Annotations.getType(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LibraryElementPackage.ADAPTER_FB__ADAPTER_DECL:
				if (resolve) return getAdapterDecl();
				return basicGetAdapterDecl();
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
			case LibraryElementPackage.ADAPTER_FB__ADAPTER_DECL:
				setAdapterDecl((AdapterDeclaration)newValue);
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
			case LibraryElementPackage.ADAPTER_FB__ADAPTER_DECL:
				setAdapterDecl((AdapterDeclaration)null);
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
			case LibraryElementPackage.ADAPTER_FB__ADAPTER_DECL:
				return adapterDecl != null;
		}
		return super.eIsSet(featureID);
	}

} // AdapterFBImpl
