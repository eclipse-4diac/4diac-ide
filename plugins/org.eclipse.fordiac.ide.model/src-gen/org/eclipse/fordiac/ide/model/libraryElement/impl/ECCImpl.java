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
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>ECC</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECCImpl#getECState <em>EC State</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECCImpl#getECTransition <em>EC Transition</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECCImpl#getStart <em>Start</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ECCImpl extends EObjectImpl implements ECC {
	/**
	 * The cached value of the '{@link #getECState() <em>EC State</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getECState()
	 * @generated
	 * @ordered
	 */
	protected EList<ECState> eCState;

	/**
	 * The cached value of the '{@link #getECTransition() <em>EC Transition</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getECTransition()
	 * @generated
	 * @ordered
	 */
	protected EList<ECTransition> eCTransition;

	/**
	 * The cached value of the '{@link #getStart() <em>Start</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStart()
	 * @generated
	 * @ordered
	 */
	protected ECState start;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ECCImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.ECC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ECState> getECState() {
		if (eCState == null) {
			eCState = new EObjectContainmentEList<ECState>(ECState.class, this, LibraryElementPackage.ECC__EC_STATE);
		}
		return eCState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ECTransition> getECTransition() {
		if (eCTransition == null) {
			eCTransition = new EObjectContainmentEList<ECTransition>(ECTransition.class, this, LibraryElementPackage.ECC__EC_TRANSITION);
		}
		return eCTransition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ECState getStart() {
		if (start != null && start.eIsProxy()) {
			InternalEObject oldStart = (InternalEObject)start;
			start = (ECState)eResolveProxy(oldStart);
			if (start != oldStart) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.ECC__START, oldStart, start));
			}
		}
		return start;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ECState basicGetStart() {
		return start;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStart(ECState newStart) {
		ECState oldStart = start;
		start = newStart;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ECC__START, oldStart, start));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.ECC__EC_STATE:
				return ((InternalEList<?>)getECState()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.ECC__EC_TRANSITION:
				return ((InternalEList<?>)getECTransition()).basicRemove(otherEnd, msgs);
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
			case LibraryElementPackage.ECC__EC_STATE:
				return getECState();
			case LibraryElementPackage.ECC__EC_TRANSITION:
				return getECTransition();
			case LibraryElementPackage.ECC__START:
				if (resolve) return getStart();
				return basicGetStart();
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
			case LibraryElementPackage.ECC__EC_STATE:
				getECState().clear();
				getECState().addAll((Collection<? extends ECState>)newValue);
				return;
			case LibraryElementPackage.ECC__EC_TRANSITION:
				getECTransition().clear();
				getECTransition().addAll((Collection<? extends ECTransition>)newValue);
				return;
			case LibraryElementPackage.ECC__START:
				setStart((ECState)newValue);
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
			case LibraryElementPackage.ECC__EC_STATE:
				getECState().clear();
				return;
			case LibraryElementPackage.ECC__EC_TRANSITION:
				getECTransition().clear();
				return;
			case LibraryElementPackage.ECC__START:
				setStart((ECState)null);
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
			case LibraryElementPackage.ECC__EC_STATE:
				return eCState != null && !eCState.isEmpty();
			case LibraryElementPackage.ECC__EC_TRANSITION:
				return eCTransition != null && !eCTransition.isEmpty();
			case LibraryElementPackage.ECC__START:
				return start != null;
		}
		return super.eIsSet(featureID);
	}

} //ECCImpl
