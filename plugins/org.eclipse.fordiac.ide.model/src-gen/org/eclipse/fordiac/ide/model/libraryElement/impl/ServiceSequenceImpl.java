/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;

/** <!-- begin-user-doc --> An implementation of the model object '<em><b>Service Sequence</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceSequenceImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceSequenceImpl#getComment <em>Comment</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceSequenceImpl#getServiceTransaction <em>Service
 * Transaction</em>}</li>
 * </ul>
 *
 * @generated */
public class ServiceSequenceImpl extends EObjectImpl implements ServiceSequence {
	/** The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @see #getName()
	 * @generated
	 * @ordered */
	protected static final String NAME_EDEFAULT = ""; //$NON-NLS-1$

	/** The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @see #getName()
	 * @generated
	 * @ordered */
	protected String name = NAME_EDEFAULT;

	/** The default value of the '{@link #getComment() <em>Comment</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see #getComment()
	 * @generated
	 * @ordered */
	protected static final String COMMENT_EDEFAULT = ""; //$NON-NLS-1$

	/** The cached value of the '{@link #getComment() <em>Comment</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see #getComment()
	 * @generated
	 * @ordered */
	protected String comment = COMMENT_EDEFAULT;

	/** The cached value of the '{@link #getServiceTransaction() <em>Service Transaction</em>}' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getServiceTransaction()
	 * @generated
	 * @ordered */
	protected EList<ServiceTransaction> serviceTransaction;

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	protected ServiceSequenceImpl() {
		super();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.SERVICE_SEQUENCE;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public String getName() {
		return name;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void setName(String newName) {
		final String oldName = name;
		name = newName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SERVICE_SEQUENCE__NAME, oldName,
					name));
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public String getComment() {
		return comment;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void setComment(String newComment) {
		final String oldComment = comment;
		comment = newComment;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SERVICE_SEQUENCE__COMMENT,
					oldComment, comment));
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public EList<ServiceTransaction> getServiceTransaction() {
		if (serviceTransaction == null) {
			serviceTransaction = new EObjectContainmentEList<>(ServiceTransaction.class, this,
					LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_TRANSACTION);
		}
		return serviceTransaction;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public Service getService() {
		return (Service) this.eContainer();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_TRANSACTION:
			return ((InternalEList<?>) getServiceTransaction()).basicRemove(otherEnd, msgs);
		default:
			return super.eInverseRemove(otherEnd, featureID, msgs);
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case LibraryElementPackage.SERVICE_SEQUENCE__NAME:
			return getName();
		case LibraryElementPackage.SERVICE_SEQUENCE__COMMENT:
			return getComment();
		case LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_TRANSACTION:
			return getServiceTransaction();
		default:
			return super.eGet(featureID, resolve, coreType);
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case LibraryElementPackage.SERVICE_SEQUENCE__NAME:
			setName((String) newValue);
			return;
		case LibraryElementPackage.SERVICE_SEQUENCE__COMMENT:
			setComment((String) newValue);
			return;
		case LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_TRANSACTION:
			getServiceTransaction().clear();
			getServiceTransaction().addAll((Collection<? extends ServiceTransaction>) newValue);
			return;
		default:
			super.eSet(featureID, newValue);
			return;
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case LibraryElementPackage.SERVICE_SEQUENCE__NAME:
			setName(NAME_EDEFAULT);
			return;
		case LibraryElementPackage.SERVICE_SEQUENCE__COMMENT:
			setComment(COMMENT_EDEFAULT);
			return;
		case LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_TRANSACTION:
			getServiceTransaction().clear();
			return;
		default:
			super.eUnset(featureID);
			return;
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case LibraryElementPackage.SERVICE_SEQUENCE__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case LibraryElementPackage.SERVICE_SEQUENCE__COMMENT:
			return COMMENT_EDEFAULT == null ? comment != null : !COMMENT_EDEFAULT.equals(comment);
		case LibraryElementPackage.SERVICE_SEQUENCE__SERVICE_TRANSACTION:
			return (serviceTransaction != null) && !serviceTransaction.isEmpty();
		default:
			return super.eIsSet(featureID);
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		final StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", comment: "); //$NON-NLS-1$
		result.append(comment);
		result.append(')');
		return result.toString();
	}

} // ServiceSequenceImpl
