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
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Adapter Declaration</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterDeclarationImpl#getAdapterFB <em>Adapter FB</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterDeclarationImpl#getPaletteEntry <em>Palette Entry</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AdapterDeclarationImpl extends VarDeclarationImpl implements
		AdapterDeclaration {
	
	/**
	 * The cached value of the '{@link #getAdapterFB() <em>Adapter FB</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdapterFB()
	 * @generated
	 * @ordered
	 */
	protected AdapterFB adapterFB;

	/**
	 * The cached value of the '{@link #getPaletteEntry() <em>Palette Entry</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPaletteEntry()
	 * @generated
	 * @ordered
	 */
	protected AdapterTypePaletteEntry paletteEntry;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected AdapterDeclarationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.ADAPTER_DECLARATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AdapterFB getAdapterFB() {
		if (adapterFB != null && adapterFB.eIsProxy()) {
			InternalEObject oldAdapterFB = (InternalEObject)adapterFB;
			adapterFB = (AdapterFB)eResolveProxy(oldAdapterFB);
			if (adapterFB != oldAdapterFB) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_FB, oldAdapterFB, adapterFB));
			}
		}
		return adapterFB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AdapterFB basicGetAdapterFB() {
		return adapterFB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAdapterFB(AdapterFB newAdapterFB, NotificationChain msgs) {
		AdapterFB oldAdapterFB = adapterFB;
		adapterFB = newAdapterFB;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_FB, oldAdapterFB, newAdapterFB);
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
	public void setAdapterFB(AdapterFB newAdapterFB) {
		if (newAdapterFB != adapterFB) {
			NotificationChain msgs = null;
			if (adapterFB != null)
				msgs = ((InternalEObject)adapterFB).eInverseRemove(this, LibraryElementPackage.ADAPTER_FB__ADAPTER_DECL, AdapterFB.class, msgs);
			if (newAdapterFB != null)
				msgs = ((InternalEObject)newAdapterFB).eInverseAdd(this, LibraryElementPackage.ADAPTER_FB__ADAPTER_DECL, AdapterFB.class, msgs);
			msgs = basicSetAdapterFB(newAdapterFB, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_FB, newAdapterFB, newAdapterFB));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AdapterTypePaletteEntry getPaletteEntry() {
		if (paletteEntry != null && paletteEntry.eIsProxy()) {
			InternalEObject oldPaletteEntry = (InternalEObject)paletteEntry;
			paletteEntry = (AdapterTypePaletteEntry)eResolveProxy(oldPaletteEntry);
			if (paletteEntry != oldPaletteEntry) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.ADAPTER_DECLARATION__PALETTE_ENTRY, oldPaletteEntry, paletteEntry));
			}
		}
		return paletteEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AdapterTypePaletteEntry basicGetPaletteEntry() {
		return paletteEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPaletteEntry(AdapterTypePaletteEntry newPaletteEntry) {
		AdapterTypePaletteEntry oldPaletteEntry = paletteEntry;
		paletteEntry = newPaletteEntry;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ADAPTER_DECLARATION__PALETTE_ENTRY, oldPaletteEntry, paletteEntry));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_FB:
				if (adapterFB != null)
					msgs = ((InternalEObject)adapterFB).eInverseRemove(this, LibraryElementPackage.ADAPTER_FB__ADAPTER_DECL, AdapterFB.class, msgs);
				return basicSetAdapterFB((AdapterFB)otherEnd, msgs);
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
			case LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_FB:
				return basicSetAdapterFB(null, msgs);
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
			case LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_FB:
				if (resolve) return getAdapterFB();
				return basicGetAdapterFB();
			case LibraryElementPackage.ADAPTER_DECLARATION__PALETTE_ENTRY:
				if (resolve) return getPaletteEntry();
				return basicGetPaletteEntry();
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
			case LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_FB:
				setAdapterFB((AdapterFB)newValue);
				return;
			case LibraryElementPackage.ADAPTER_DECLARATION__PALETTE_ENTRY:
				setPaletteEntry((AdapterTypePaletteEntry)newValue);
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
			case LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_FB:
				setAdapterFB((AdapterFB)null);
				return;
			case LibraryElementPackage.ADAPTER_DECLARATION__PALETTE_ENTRY:
				setPaletteEntry((AdapterTypePaletteEntry)null);
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
			case LibraryElementPackage.ADAPTER_DECLARATION__ADAPTER_FB:
				return adapterFB != null;
			case LibraryElementPackage.ADAPTER_DECLARATION__PALETTE_ENTRY:
				return paletteEntry != null;
		}
		return super.eIsSet(featureID);
	}	

} // AdapterDeclarationImpl
