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
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Typed Configureable Object</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.TypedConfigureableObjectImpl#getPaletteEntry <em>Palette Entry</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TypedConfigureableObjectImpl extends ConfigurableObjectImpl implements TypedConfigureableObject {
	/**
	 * The cached value of the '{@link #getPaletteEntry() <em>Palette Entry</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPaletteEntry()
	 * @generated
	 * @ordered
	 */
	protected PaletteEntry paletteEntry;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TypedConfigureableObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.TYPED_CONFIGUREABLE_OBJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PaletteEntry getPaletteEntry() {
		if (paletteEntry != null && paletteEntry.eIsProxy()) {
			InternalEObject oldPaletteEntry = (InternalEObject)paletteEntry;
			paletteEntry = (PaletteEntry)eResolveProxy(oldPaletteEntry);
			if (paletteEntry != oldPaletteEntry) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.TYPED_CONFIGUREABLE_OBJECT__PALETTE_ENTRY, oldPaletteEntry, paletteEntry));
			}
		}
		return paletteEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PaletteEntry basicGetPaletteEntry() {
		return paletteEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPaletteEntry(PaletteEntry newPaletteEntry) {
		PaletteEntry oldPaletteEntry = paletteEntry;
		paletteEntry = newPaletteEntry;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.TYPED_CONFIGUREABLE_OBJECT__PALETTE_ENTRY, oldPaletteEntry, paletteEntry));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTypeName() {
		return org.eclipse.fordiac.ide.model.Annotations.getTypeName(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LibraryElement getType() {
		return org.eclipse.fordiac.ide.model.Annotations.getType(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LibraryElementPackage.TYPED_CONFIGUREABLE_OBJECT__PALETTE_ENTRY:
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
			case LibraryElementPackage.TYPED_CONFIGUREABLE_OBJECT__PALETTE_ENTRY:
				setPaletteEntry((PaletteEntry)newValue);
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
			case LibraryElementPackage.TYPED_CONFIGUREABLE_OBJECT__PALETTE_ENTRY:
				setPaletteEntry((PaletteEntry)null);
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
			case LibraryElementPackage.TYPED_CONFIGUREABLE_OBJECT__PALETTE_ENTRY:
				return paletteEntry != null;
		}
		return super.eIsSet(featureID);
	}

} //TypedConfigureableObjectImpl
