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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Service Sequence</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceSequenceImpl#getServiceTransaction <em>Service Transaction</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceSequenceImpl#getServiceSequenceType <em>Service Sequence Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceSequenceImpl#getStartState <em>Start State</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ServiceSequenceImpl extends ConfigurableObjectImpl implements ServiceSequence {
	/**
	 * The cached value of the '{@link #getServiceTransaction() <em>Service Transaction</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getServiceTransaction()
	 * @generated
	 * @ordered
	 */
	protected EList<ServiceTransaction> serviceTransaction;

	/**
	 * The default value of the '{@link #getServiceSequenceType() <em>Service Sequence Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getServiceSequenceType()
	 * @generated
	 * @ordered
	 */
	protected static final String SERVICE_SEQUENCE_TYPE_EDEFAULT = "POSSIBLE"; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getServiceSequenceType() <em>Service Sequence Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getServiceSequenceType()
	 * @generated
	 * @ordered
	 */
	protected String serviceSequenceType = SERVICE_SEQUENCE_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getStartState() <em>Start State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartState()
	 * @generated
	 * @ordered
	 */
	protected static final String START_STATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStartState() <em>Start State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartState()
	 * @generated
	 * @ordered
	 */
	protected String startState = START_STATE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ServiceSequenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.SERVICE_SEQUENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ServiceTransaction> getServiceTransaction() {
		if (serviceTransaction == null) {
			serviceTransaction = new EObjectContainmentEList<ServiceTransaction>(ServiceTransaction.class, this, LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_TRANSACTION);
		}
		return serviceTransaction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getServiceSequenceType() {
		return serviceSequenceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setServiceSequenceType(String newServiceSequenceType) {
		String oldServiceSequenceType = serviceSequenceType;
		serviceSequenceType = newServiceSequenceType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_SEQUENCE_TYPE, oldServiceSequenceType, serviceSequenceType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getStartState() {
		return startState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStartState(String newStartState) {
		String oldStartState = startState;
		startState = newStartState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SERVICE_SEQUENCE__START_STATE, oldStartState, startState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Service getService() {
		return (Service) this.eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_TRANSACTION:
				return ((InternalEList<?>)getServiceTransaction()).basicRemove(otherEnd, msgs);
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
			case LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_TRANSACTION:
				return getServiceTransaction();
			case LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_SEQUENCE_TYPE:
				return getServiceSequenceType();
			case LibraryElementPackage.SERVICE_SEQUENCE__START_STATE:
				return getStartState();
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
			case LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_TRANSACTION:
				getServiceTransaction().clear();
				getServiceTransaction().addAll((Collection<? extends ServiceTransaction>)newValue);
				return;
			case LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_SEQUENCE_TYPE:
				setServiceSequenceType((String)newValue);
				return;
			case LibraryElementPackage.SERVICE_SEQUENCE__START_STATE:
				setStartState((String)newValue);
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
			case LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_TRANSACTION:
				getServiceTransaction().clear();
				return;
			case LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_SEQUENCE_TYPE:
				setServiceSequenceType(SERVICE_SEQUENCE_TYPE_EDEFAULT);
				return;
			case LibraryElementPackage.SERVICE_SEQUENCE__START_STATE:
				setStartState(START_STATE_EDEFAULT);
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
			case LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_TRANSACTION:
				return serviceTransaction != null && !serviceTransaction.isEmpty();
			case LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_SEQUENCE_TYPE:
				return SERVICE_SEQUENCE_TYPE_EDEFAULT == null ? serviceSequenceType != null : !SERVICE_SEQUENCE_TYPE_EDEFAULT.equals(serviceSequenceType);
			case LibraryElementPackage.SERVICE_SEQUENCE__START_STATE:
				return START_STATE_EDEFAULT == null ? startState != null : !START_STATE_EDEFAULT.equals(startState);
			default:
				return super.eIsSet(featureID);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (serviceSequenceType: "); //$NON-NLS-1$
		result.append(serviceSequenceType);
		result.append(", startState: "); //$NON-NLS-1$
		result.append(startState);
		result.append(')');
		return result.toString();
	}

} //ServiceSequenceImpl
