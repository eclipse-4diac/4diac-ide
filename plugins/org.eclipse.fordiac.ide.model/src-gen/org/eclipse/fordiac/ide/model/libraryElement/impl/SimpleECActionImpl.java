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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleECAction;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleECState;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Simple EC Action</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SimpleECActionImpl#getSimpleECState <em>Simple EC State</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SimpleECActionImpl extends BaseECActionImpl implements SimpleECAction {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SimpleECActionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.SIMPLE_EC_ACTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SimpleECState getSimpleECState() {
		if (eContainerFeatureID() != LibraryElementPackage.SIMPLE_EC_ACTION__SIMPLE_EC_STATE) return null;
		return (SimpleECState)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleECState basicGetSimpleECState() {
		if (eContainerFeatureID() != LibraryElementPackage.SIMPLE_EC_ACTION__SIMPLE_EC_STATE) return null;
		return (SimpleECState)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSimpleECState(SimpleECState newSimpleECState, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newSimpleECState, LibraryElementPackage.SIMPLE_EC_ACTION__SIMPLE_EC_STATE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSimpleECState(SimpleECState newSimpleECState) {
		if (newSimpleECState != eInternalContainer() || (eContainerFeatureID() != LibraryElementPackage.SIMPLE_EC_ACTION__SIMPLE_EC_STATE && newSimpleECState != null)) {
			if (EcoreUtil.isAncestor(this, newSimpleECState))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newSimpleECState != null)
				msgs = ((InternalEObject)newSimpleECState).eInverseAdd(this, LibraryElementPackage.SIMPLE_EC_STATE__SIMPLE_EC_ACTIONS, SimpleECState.class, msgs);
			msgs = basicSetSimpleECState(newSimpleECState, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SIMPLE_EC_ACTION__SIMPLE_EC_STATE, newSimpleECState, newSimpleECState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.SIMPLE_EC_ACTION__SIMPLE_EC_STATE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetSimpleECState((SimpleECState)otherEnd, msgs);
			default:
				return super.eInverseAdd(otherEnd, featureID, msgs);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.SIMPLE_EC_ACTION__SIMPLE_EC_STATE:
				return basicSetSimpleECState(null, msgs);
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
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case LibraryElementPackage.SIMPLE_EC_ACTION__SIMPLE_EC_STATE:
				return eInternalContainer().eInverseRemove(this, LibraryElementPackage.SIMPLE_EC_STATE__SIMPLE_EC_ACTIONS, SimpleECState.class, msgs);
			default:
				return super.eBasicRemoveFromContainerFeature(msgs);
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
			case LibraryElementPackage.SIMPLE_EC_ACTION__SIMPLE_EC_STATE:
				if (resolve) return getSimpleECState();
				return basicGetSimpleECState();
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
			case LibraryElementPackage.SIMPLE_EC_ACTION__SIMPLE_EC_STATE:
				setSimpleECState((SimpleECState)newValue);
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
			case LibraryElementPackage.SIMPLE_EC_ACTION__SIMPLE_EC_STATE:
				setSimpleECState((SimpleECState)null);
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
			case LibraryElementPackage.SIMPLE_EC_ACTION__SIMPLE_EC_STATE:
				return basicGetSimpleECState() != null;
			default:
				return super.eIsSet(featureID);
		}
	}

} //SimpleECActionImpl
