/**
 * *******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.bulkeditor.query.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.fordiac.ide.bulkeditor.query.model.ApplicationOccurrence;
import org.eclipse.fordiac.ide.bulkeditor.query.model.CompositeFBOccurrence;
import org.eclipse.fordiac.ide.bulkeditor.query.model.NetworkElement;
import org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage;
import org.eclipse.fordiac.ide.bulkeditor.query.model.TypedSubappOccurrence;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Network Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.NetworkElementImpl#getApplicationOccurrence <em>Application Occurrence</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.NetworkElementImpl#getCompositeFBOccurrence <em>Composite FB Occurrence</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.NetworkElementImpl#getTypedSubappOccurrence <em>Typed Subapp Occurrence</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class NetworkElementImpl extends SearchTargetImpl implements NetworkElement {
	/**
	 * The cached value of the '{@link #getApplicationOccurrence() <em>Application Occurrence</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getApplicationOccurrence()
	 * @generated
	 * @ordered
	 */
	protected ApplicationOccurrence applicationOccurrence;

	/**
	 * The cached value of the '{@link #getCompositeFBOccurrence() <em>Composite FB Occurrence</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompositeFBOccurrence()
	 * @generated
	 * @ordered
	 */
	protected CompositeFBOccurrence compositeFBOccurrence;

	/**
	 * The cached value of the '{@link #getTypedSubappOccurrence() <em>Typed Subapp Occurrence</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypedSubappOccurrence()
	 * @generated
	 * @ordered
	 */
	protected TypedSubappOccurrence typedSubappOccurrence;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NetworkElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return QueryPackage.Literals.NETWORK_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ApplicationOccurrence getApplicationOccurrence() {
		return applicationOccurrence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetApplicationOccurrence(ApplicationOccurrence newApplicationOccurrence, NotificationChain msgs) {
		ApplicationOccurrence oldApplicationOccurrence = applicationOccurrence;
		applicationOccurrence = newApplicationOccurrence;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QueryPackage.NETWORK_ELEMENT__APPLICATION_OCCURRENCE, oldApplicationOccurrence, newApplicationOccurrence);
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
	public void setApplicationOccurrence(ApplicationOccurrence newApplicationOccurrence) {
		if (newApplicationOccurrence != applicationOccurrence) {
			NotificationChain msgs = null;
			if (applicationOccurrence != null)
				msgs = ((InternalEObject)applicationOccurrence).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QueryPackage.NETWORK_ELEMENT__APPLICATION_OCCURRENCE, null, msgs);
			if (newApplicationOccurrence != null)
				msgs = ((InternalEObject)newApplicationOccurrence).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QueryPackage.NETWORK_ELEMENT__APPLICATION_OCCURRENCE, null, msgs);
			msgs = basicSetApplicationOccurrence(newApplicationOccurrence, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.NETWORK_ELEMENT__APPLICATION_OCCURRENCE, newApplicationOccurrence, newApplicationOccurrence));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CompositeFBOccurrence getCompositeFBOccurrence() {
		return compositeFBOccurrence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCompositeFBOccurrence(CompositeFBOccurrence newCompositeFBOccurrence, NotificationChain msgs) {
		CompositeFBOccurrence oldCompositeFBOccurrence = compositeFBOccurrence;
		compositeFBOccurrence = newCompositeFBOccurrence;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QueryPackage.NETWORK_ELEMENT__COMPOSITE_FB_OCCURRENCE, oldCompositeFBOccurrence, newCompositeFBOccurrence);
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
	public void setCompositeFBOccurrence(CompositeFBOccurrence newCompositeFBOccurrence) {
		if (newCompositeFBOccurrence != compositeFBOccurrence) {
			NotificationChain msgs = null;
			if (compositeFBOccurrence != null)
				msgs = ((InternalEObject)compositeFBOccurrence).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QueryPackage.NETWORK_ELEMENT__COMPOSITE_FB_OCCURRENCE, null, msgs);
			if (newCompositeFBOccurrence != null)
				msgs = ((InternalEObject)newCompositeFBOccurrence).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QueryPackage.NETWORK_ELEMENT__COMPOSITE_FB_OCCURRENCE, null, msgs);
			msgs = basicSetCompositeFBOccurrence(newCompositeFBOccurrence, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.NETWORK_ELEMENT__COMPOSITE_FB_OCCURRENCE, newCompositeFBOccurrence, newCompositeFBOccurrence));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypedSubappOccurrence getTypedSubappOccurrence() {
		return typedSubappOccurrence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTypedSubappOccurrence(TypedSubappOccurrence newTypedSubappOccurrence, NotificationChain msgs) {
		TypedSubappOccurrence oldTypedSubappOccurrence = typedSubappOccurrence;
		typedSubappOccurrence = newTypedSubappOccurrence;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QueryPackage.NETWORK_ELEMENT__TYPED_SUBAPP_OCCURRENCE, oldTypedSubappOccurrence, newTypedSubappOccurrence);
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
	public void setTypedSubappOccurrence(TypedSubappOccurrence newTypedSubappOccurrence) {
		if (newTypedSubappOccurrence != typedSubappOccurrence) {
			NotificationChain msgs = null;
			if (typedSubappOccurrence != null)
				msgs = ((InternalEObject)typedSubappOccurrence).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QueryPackage.NETWORK_ELEMENT__TYPED_SUBAPP_OCCURRENCE, null, msgs);
			if (newTypedSubappOccurrence != null)
				msgs = ((InternalEObject)newTypedSubappOccurrence).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QueryPackage.NETWORK_ELEMENT__TYPED_SUBAPP_OCCURRENCE, null, msgs);
			msgs = basicSetTypedSubappOccurrence(newTypedSubappOccurrence, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.NETWORK_ELEMENT__TYPED_SUBAPP_OCCURRENCE, newTypedSubappOccurrence, newTypedSubappOccurrence));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case QueryPackage.NETWORK_ELEMENT__APPLICATION_OCCURRENCE:
				return basicSetApplicationOccurrence(null, msgs);
			case QueryPackage.NETWORK_ELEMENT__COMPOSITE_FB_OCCURRENCE:
				return basicSetCompositeFBOccurrence(null, msgs);
			case QueryPackage.NETWORK_ELEMENT__TYPED_SUBAPP_OCCURRENCE:
				return basicSetTypedSubappOccurrence(null, msgs);
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
			case QueryPackage.NETWORK_ELEMENT__APPLICATION_OCCURRENCE:
				return getApplicationOccurrence();
			case QueryPackage.NETWORK_ELEMENT__COMPOSITE_FB_OCCURRENCE:
				return getCompositeFBOccurrence();
			case QueryPackage.NETWORK_ELEMENT__TYPED_SUBAPP_OCCURRENCE:
				return getTypedSubappOccurrence();
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
			case QueryPackage.NETWORK_ELEMENT__APPLICATION_OCCURRENCE:
				setApplicationOccurrence((ApplicationOccurrence)newValue);
				return;
			case QueryPackage.NETWORK_ELEMENT__COMPOSITE_FB_OCCURRENCE:
				setCompositeFBOccurrence((CompositeFBOccurrence)newValue);
				return;
			case QueryPackage.NETWORK_ELEMENT__TYPED_SUBAPP_OCCURRENCE:
				setTypedSubappOccurrence((TypedSubappOccurrence)newValue);
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
			case QueryPackage.NETWORK_ELEMENT__APPLICATION_OCCURRENCE:
				setApplicationOccurrence((ApplicationOccurrence)null);
				return;
			case QueryPackage.NETWORK_ELEMENT__COMPOSITE_FB_OCCURRENCE:
				setCompositeFBOccurrence((CompositeFBOccurrence)null);
				return;
			case QueryPackage.NETWORK_ELEMENT__TYPED_SUBAPP_OCCURRENCE:
				setTypedSubappOccurrence((TypedSubappOccurrence)null);
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
			case QueryPackage.NETWORK_ELEMENT__APPLICATION_OCCURRENCE:
				return applicationOccurrence != null;
			case QueryPackage.NETWORK_ELEMENT__COMPOSITE_FB_OCCURRENCE:
				return compositeFBOccurrence != null;
			case QueryPackage.NETWORK_ELEMENT__TYPED_SUBAPP_OCCURRENCE:
				return typedSubappOccurrence != null;
			default:
				return super.eIsSet(featureID);
		}
	}

} //NetworkElementImpl
