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
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterface;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Service</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceImpl#getRightInterface <em>Right Interface</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceImpl#getLeftInterface <em>Left Interface</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceImpl#getServiceSequence <em>Service Sequence</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceImpl#getComment <em>Comment</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ServiceImpl extends EObjectImpl implements Service {
	/**
	 * The cached value of the '{@link #getRightInterface() <em>Right Interface</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRightInterface()
	 * @generated
	 * @ordered
	 */
	protected ServiceInterface rightInterface;

	/**
	 * The cached value of the '{@link #getLeftInterface() <em>Left Interface</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLeftInterface()
	 * @generated
	 * @ordered
	 */
	protected ServiceInterface leftInterface;

	/**
	 * The cached value of the '{@link #getServiceSequence() <em>Service Sequence</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getServiceSequence()
	 * @generated
	 * @ordered
	 */
	protected EList<ServiceSequence> serviceSequence;

	/**
	 * The default value of the '{@link #getComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected static final String COMMENT_EDEFAULT = ""; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected String comment = COMMENT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ServiceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.SERVICE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ServiceInterface getRightInterface() {
		if (rightInterface != null && rightInterface.eIsProxy()) {
			InternalEObject oldRightInterface = (InternalEObject)rightInterface;
			rightInterface = (ServiceInterface)eResolveProxy(oldRightInterface);
			if (rightInterface != oldRightInterface) {
				InternalEObject newRightInterface = (InternalEObject)rightInterface;
				NotificationChain msgs = oldRightInterface.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.SERVICE__RIGHT_INTERFACE, null, null);
				if (newRightInterface.eInternalContainer() == null) {
					msgs = newRightInterface.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.SERVICE__RIGHT_INTERFACE, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.SERVICE__RIGHT_INTERFACE, oldRightInterface, rightInterface));
			}
		}
		return rightInterface;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ServiceInterface basicGetRightInterface() {
		return rightInterface;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRightInterface(ServiceInterface newRightInterface, NotificationChain msgs) {
		ServiceInterface oldRightInterface = rightInterface;
		rightInterface = newRightInterface;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SERVICE__RIGHT_INTERFACE, oldRightInterface, newRightInterface);
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
	public void setRightInterface(ServiceInterface newRightInterface) {
		if (newRightInterface != rightInterface) {
			NotificationChain msgs = null;
			if (rightInterface != null)
				msgs = ((InternalEObject)rightInterface).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.SERVICE__RIGHT_INTERFACE, null, msgs);
			if (newRightInterface != null)
				msgs = ((InternalEObject)newRightInterface).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.SERVICE__RIGHT_INTERFACE, null, msgs);
			msgs = basicSetRightInterface(newRightInterface, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SERVICE__RIGHT_INTERFACE, newRightInterface, newRightInterface));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ServiceInterface getLeftInterface() {
		if (leftInterface != null && leftInterface.eIsProxy()) {
			InternalEObject oldLeftInterface = (InternalEObject)leftInterface;
			leftInterface = (ServiceInterface)eResolveProxy(oldLeftInterface);
			if (leftInterface != oldLeftInterface) {
				InternalEObject newLeftInterface = (InternalEObject)leftInterface;
				NotificationChain msgs = oldLeftInterface.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.SERVICE__LEFT_INTERFACE, null, null);
				if (newLeftInterface.eInternalContainer() == null) {
					msgs = newLeftInterface.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.SERVICE__LEFT_INTERFACE, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.SERVICE__LEFT_INTERFACE, oldLeftInterface, leftInterface));
			}
		}
		return leftInterface;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ServiceInterface basicGetLeftInterface() {
		return leftInterface;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLeftInterface(ServiceInterface newLeftInterface, NotificationChain msgs) {
		ServiceInterface oldLeftInterface = leftInterface;
		leftInterface = newLeftInterface;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SERVICE__LEFT_INTERFACE, oldLeftInterface, newLeftInterface);
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
	public void setLeftInterface(ServiceInterface newLeftInterface) {
		if (newLeftInterface != leftInterface) {
			NotificationChain msgs = null;
			if (leftInterface != null)
				msgs = ((InternalEObject)leftInterface).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.SERVICE__LEFT_INTERFACE, null, msgs);
			if (newLeftInterface != null)
				msgs = ((InternalEObject)newLeftInterface).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.SERVICE__LEFT_INTERFACE, null, msgs);
			msgs = basicSetLeftInterface(newLeftInterface, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SERVICE__LEFT_INTERFACE, newLeftInterface, newLeftInterface));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ServiceSequence> getServiceSequence() {
		if (serviceSequence == null) {
			serviceSequence = new EObjectContainmentEList.Resolving<ServiceSequence>(ServiceSequence.class, this, LibraryElementPackage.SERVICE__SERVICE_SEQUENCE);
		}
		return serviceSequence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getComment() {
		return comment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setComment(String newComment) {
		String oldComment = comment;
		comment = newComment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SERVICE__COMMENT, oldComment, comment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FBType getFBType() {
		return (FBType) this.eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.SERVICE__RIGHT_INTERFACE:
				return basicSetRightInterface(null, msgs);
			case LibraryElementPackage.SERVICE__LEFT_INTERFACE:
				return basicSetLeftInterface(null, msgs);
			case LibraryElementPackage.SERVICE__SERVICE_SEQUENCE:
				return ((InternalEList<?>)getServiceSequence()).basicRemove(otherEnd, msgs);
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
			case LibraryElementPackage.SERVICE__RIGHT_INTERFACE:
				if (resolve) return getRightInterface();
				return basicGetRightInterface();
			case LibraryElementPackage.SERVICE__LEFT_INTERFACE:
				if (resolve) return getLeftInterface();
				return basicGetLeftInterface();
			case LibraryElementPackage.SERVICE__SERVICE_SEQUENCE:
				return getServiceSequence();
			case LibraryElementPackage.SERVICE__COMMENT:
				return getComment();
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
			case LibraryElementPackage.SERVICE__RIGHT_INTERFACE:
				setRightInterface((ServiceInterface)newValue);
				return;
			case LibraryElementPackage.SERVICE__LEFT_INTERFACE:
				setLeftInterface((ServiceInterface)newValue);
				return;
			case LibraryElementPackage.SERVICE__SERVICE_SEQUENCE:
				getServiceSequence().clear();
				getServiceSequence().addAll((Collection<? extends ServiceSequence>)newValue);
				return;
			case LibraryElementPackage.SERVICE__COMMENT:
				setComment((String)newValue);
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
			case LibraryElementPackage.SERVICE__RIGHT_INTERFACE:
				setRightInterface((ServiceInterface)null);
				return;
			case LibraryElementPackage.SERVICE__LEFT_INTERFACE:
				setLeftInterface((ServiceInterface)null);
				return;
			case LibraryElementPackage.SERVICE__SERVICE_SEQUENCE:
				getServiceSequence().clear();
				return;
			case LibraryElementPackage.SERVICE__COMMENT:
				setComment(COMMENT_EDEFAULT);
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
			case LibraryElementPackage.SERVICE__RIGHT_INTERFACE:
				return rightInterface != null;
			case LibraryElementPackage.SERVICE__LEFT_INTERFACE:
				return leftInterface != null;
			case LibraryElementPackage.SERVICE__SERVICE_SEQUENCE:
				return serviceSequence != null && !serviceSequence.isEmpty();
			case LibraryElementPackage.SERVICE__COMMENT:
				return COMMENT_EDEFAULT == null ? comment != null : !COMMENT_EDEFAULT.equals(comment);
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
		result.append(" (comment: "); //$NON-NLS-1$
		result.append(comment);
		result.append(')');
		return result.toString();
	}

} //ServiceImpl
