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
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Error Marker FBN Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ErrorMarkerFBNElementImpl#getRepairedElement <em>Repaired Element</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ErrorMarkerFBNElementImpl extends FBNetworkElementImpl implements ErrorMarkerFBNElement {
	/**
	 * The cached value of the '{@link #getRepairedElement() <em>Repaired Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRepairedElement()
	 * @generated
	 * @ordered
	 */
	protected FBNetworkElement repairedElement;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ErrorMarkerFBNElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.ERROR_MARKER_FBN_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FBNetworkElement getRepairedElement() {
		if (repairedElement != null && repairedElement.eIsProxy()) {
			InternalEObject oldRepairedElement = (InternalEObject)repairedElement;
			repairedElement = (FBNetworkElement)eResolveProxy(oldRepairedElement);
			if (repairedElement != oldRepairedElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.ERROR_MARKER_FBN_ELEMENT__REPAIRED_ELEMENT, oldRepairedElement, repairedElement));
			}
		}
		return repairedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FBNetworkElement basicGetRepairedElement() {
		return repairedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRepairedElement(FBNetworkElement newRepairedElement) {
		FBNetworkElement oldRepairedElement = repairedElement;
		repairedElement = newRepairedElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ERROR_MARKER_FBN_ELEMENT__REPAIRED_ELEMENT, oldRepairedElement, repairedElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getWidth() {
		return org.eclipse.fordiac.ide.model.helpers.FBShapeHelper.getWidth(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getHeight() {
		return org.eclipse.fordiac.ide.model.helpers.FBShapeHelper.getHeight(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LibraryElementPackage.ERROR_MARKER_FBN_ELEMENT__REPAIRED_ELEMENT:
				if (resolve) return getRepairedElement();
				return basicGetRepairedElement();
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
			case LibraryElementPackage.ERROR_MARKER_FBN_ELEMENT__REPAIRED_ELEMENT:
				setRepairedElement((FBNetworkElement)newValue);
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
			case LibraryElementPackage.ERROR_MARKER_FBN_ELEMENT__REPAIRED_ELEMENT:
				setRepairedElement((FBNetworkElement)null);
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
			case LibraryElementPackage.ERROR_MARKER_FBN_ELEMENT__REPAIRED_ELEMENT:
				return repairedElement != null;
			default:
				return super.eIsSet(featureID);
		}
	}

} //ErrorMarkerFBNElementImpl
