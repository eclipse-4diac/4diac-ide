/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
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
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>EC
 * Action</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECActionImpl#getAlgorithm <em>Algorithm</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECActionImpl#getOutput <em>Output</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECActionImpl#getECState <em>EC State</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ECActionImpl extends EObjectImpl implements ECAction {
	/**
	 * The cached value of the '{@link #getAlgorithm() <em>Algorithm</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getAlgorithm()
	 * @generated
	 * @ordered
	 */
	protected Algorithm algorithm;

	/**
	 * The cached value of the '{@link #getOutput() <em>Output</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getOutput()
	 * @generated
	 * @ordered
	 */
	protected Event output;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ECActionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.EC_ACTION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Algorithm getAlgorithm() {
		if (algorithm != null && algorithm.eIsProxy()) {
			InternalEObject oldAlgorithm = (InternalEObject)algorithm;
			algorithm = (Algorithm)eResolveProxy(oldAlgorithm);
			if (algorithm != oldAlgorithm) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.EC_ACTION__ALGORITHM, oldAlgorithm, algorithm));
			}
		}
		return algorithm;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Algorithm basicGetAlgorithm() {
		return algorithm;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAlgorithm(Algorithm newAlgorithm) {
		Algorithm oldAlgorithm = algorithm;
		algorithm = newAlgorithm;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.EC_ACTION__ALGORITHM, oldAlgorithm, algorithm));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Event getOutput() {
		if (output != null && output.eIsProxy()) {
			InternalEObject oldOutput = (InternalEObject)output;
			output = (Event)eResolveProxy(oldOutput);
			if (output != oldOutput) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.EC_ACTION__OUTPUT, oldOutput, output));
			}
		}
		return output;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Event basicGetOutput() {
		return output;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOutput(Event newOutput) {
		Event oldOutput = output;
		output = newOutput;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.EC_ACTION__OUTPUT, oldOutput, output));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ECState getECState() {
		if (eContainerFeatureID() != LibraryElementPackage.EC_ACTION__EC_STATE) return null;
		return (ECState)eContainer();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ECState basicGetECState() {
		if (eContainerFeatureID() != LibraryElementPackage.EC_ACTION__EC_STATE) return null;
		return (ECState)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetECState(ECState newECState, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newECState, LibraryElementPackage.EC_ACTION__EC_STATE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setECState(ECState newECState) {
		if (newECState != eInternalContainer() || (eContainerFeatureID() != LibraryElementPackage.EC_ACTION__EC_STATE && newECState != null)) {
			if (EcoreUtil.isAncestor(this, newECState))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newECState != null)
				msgs = ((InternalEObject)newECState).eInverseAdd(this, LibraryElementPackage.EC_STATE__EC_ACTION, ECState.class, msgs);
			msgs = basicSetECState(newECState, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.EC_ACTION__EC_STATE, newECState, newECState));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.EC_ACTION__EC_STATE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetECState((ECState)otherEnd, msgs);
			default:
				return super.eInverseAdd(otherEnd, featureID, msgs);
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.EC_ACTION__EC_STATE:
				return basicSetECState(null, msgs);
			default:
				return super.eInverseRemove(otherEnd, featureID, msgs);
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case LibraryElementPackage.EC_ACTION__EC_STATE:
				return eInternalContainer().eInverseRemove(this, LibraryElementPackage.EC_STATE__EC_ACTION, ECState.class, msgs);
			default:
				return super.eBasicRemoveFromContainerFeature(msgs);
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LibraryElementPackage.EC_ACTION__ALGORITHM:
				if (resolve) return getAlgorithm();
				return basicGetAlgorithm();
			case LibraryElementPackage.EC_ACTION__OUTPUT:
				if (resolve) return getOutput();
				return basicGetOutput();
			case LibraryElementPackage.EC_ACTION__EC_STATE:
				if (resolve) return getECState();
				return basicGetECState();
			default:
				return super.eGet(featureID, resolve, coreType);
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case LibraryElementPackage.EC_ACTION__ALGORITHM:
				setAlgorithm((Algorithm)newValue);
				return;
			case LibraryElementPackage.EC_ACTION__OUTPUT:
				setOutput((Event)newValue);
				return;
			case LibraryElementPackage.EC_ACTION__EC_STATE:
				setECState((ECState)newValue);
				return;
			default:
				super.eSet(featureID, newValue);
				return;
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case LibraryElementPackage.EC_ACTION__ALGORITHM:
				setAlgorithm((Algorithm)null);
				return;
			case LibraryElementPackage.EC_ACTION__OUTPUT:
				setOutput((Event)null);
				return;
			case LibraryElementPackage.EC_ACTION__EC_STATE:
				setECState((ECState)null);
				return;
			default:
				super.eUnset(featureID);
				return;
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case LibraryElementPackage.EC_ACTION__ALGORITHM:
				return algorithm != null;
			case LibraryElementPackage.EC_ACTION__OUTPUT:
				return output != null;
			case LibraryElementPackage.EC_ACTION__EC_STATE:
				return basicGetECState() != null;
			default:
				return super.eIsSet(featureID);
		}
	}

} // ECActionImpl
