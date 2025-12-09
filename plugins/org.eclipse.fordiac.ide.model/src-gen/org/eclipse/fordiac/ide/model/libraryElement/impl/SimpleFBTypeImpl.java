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

import java.util.Collection;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleECState;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Simple FB Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SimpleFBTypeImpl#getSimpleECStates <em>Simple EC States</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SimpleFBTypeImpl extends BaseFBTypeImpl implements SimpleFBType {
	/**
	 * The cached value of the '{@link #getSimpleECStates() <em>Simple EC States</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSimpleECStates()
	 * @generated
	 * @ordered
	 */
	protected EList<SimpleECState> simpleECStates;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SimpleFBTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.SIMPLE_FB_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<SimpleECState> getSimpleECStates() {
		if (simpleECStates == null) {
			simpleECStates = new EObjectContainmentWithInverseEList.Resolving<SimpleECState>(SimpleECState.class, this, LibraryElementPackage.SIMPLE_FB_TYPE__SIMPLE_EC_STATES, LibraryElementPackage.SIMPLE_EC_STATE__SIMPLE_FB_TYPE);
		}
		return simpleECStates;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.SIMPLE_FB_TYPE__SIMPLE_EC_STATES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getSimpleECStates()).basicAdd(otherEnd, msgs);
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
			case LibraryElementPackage.SIMPLE_FB_TYPE__SIMPLE_EC_STATES:
				return ((InternalEList<?>)getSimpleECStates()).basicRemove(otherEnd, msgs);
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
			case LibraryElementPackage.SIMPLE_FB_TYPE__SIMPLE_EC_STATES:
				return getSimpleECStates();
			default:
				return super.eGet(featureID, resolve, coreType);
		}
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
			case LibraryElementPackage.SIMPLE_FB_TYPE__SIMPLE_EC_STATES:
				getSimpleECStates().clear();
				getSimpleECStates().addAll((Collection<? extends SimpleECState>)newValue);
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
			case LibraryElementPackage.SIMPLE_FB_TYPE__SIMPLE_EC_STATES:
				getSimpleECStates().clear();
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
			case LibraryElementPackage.SIMPLE_FB_TYPE__SIMPLE_EC_STATES:
				return simpleECStates != null && !simpleECStates.isEmpty();
			default:
				return super.eIsSet(featureID);
		}
	}

} //SimpleFBTypeImpl
