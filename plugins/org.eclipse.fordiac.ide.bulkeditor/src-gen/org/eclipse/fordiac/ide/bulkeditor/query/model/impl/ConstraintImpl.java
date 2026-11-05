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
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.fordiac.ide.bulkeditor.query.model.Constraint;
import org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Constraint</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.ConstraintImpl#isNegate <em>Negate</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.ConstraintImpl#getOrConstraint <em>Or Constraint</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.ConstraintImpl#getAndConstraint <em>And Constraint</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ConstraintImpl extends EObjectImpl implements Constraint {
	/**
	 * The default value of the '{@link #isNegate() <em>Negate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNegate()
	 * @generated
	 * @ordered
	 */
	protected static final boolean NEGATE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isNegate() <em>Negate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNegate()
	 * @generated
	 * @ordered
	 */
	protected boolean negate = NEGATE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOrConstraint() <em>Or Constraint</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrConstraint()
	 * @generated
	 * @ordered
	 */
	protected Constraint orConstraint;

	/**
	 * The cached value of the '{@link #getAndConstraint() <em>And Constraint</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAndConstraint()
	 * @generated
	 * @ordered
	 */
	protected Constraint andConstraint;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConstraintImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return QueryPackage.Literals.CONSTRAINT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isNegate() {
		return negate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNegate(boolean newNegate) {
		boolean oldNegate = negate;
		negate = newNegate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.CONSTRAINT__NEGATE, oldNegate, negate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Constraint getOrConstraint() {
		return orConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOrConstraint(Constraint newOrConstraint, NotificationChain msgs) {
		Constraint oldOrConstraint = orConstraint;
		orConstraint = newOrConstraint;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QueryPackage.CONSTRAINT__OR_CONSTRAINT, oldOrConstraint, newOrConstraint);
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
	public void setOrConstraint(Constraint newOrConstraint) {
		if (newOrConstraint != orConstraint) {
			NotificationChain msgs = null;
			if (orConstraint != null)
				msgs = ((InternalEObject)orConstraint).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QueryPackage.CONSTRAINT__OR_CONSTRAINT, null, msgs);
			if (newOrConstraint != null)
				msgs = ((InternalEObject)newOrConstraint).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QueryPackage.CONSTRAINT__OR_CONSTRAINT, null, msgs);
			msgs = basicSetOrConstraint(newOrConstraint, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.CONSTRAINT__OR_CONSTRAINT, newOrConstraint, newOrConstraint));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Constraint getAndConstraint() {
		return andConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAndConstraint(Constraint newAndConstraint, NotificationChain msgs) {
		Constraint oldAndConstraint = andConstraint;
		andConstraint = newAndConstraint;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QueryPackage.CONSTRAINT__AND_CONSTRAINT, oldAndConstraint, newAndConstraint);
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
	public void setAndConstraint(Constraint newAndConstraint) {
		if (newAndConstraint != andConstraint) {
			NotificationChain msgs = null;
			if (andConstraint != null)
				msgs = ((InternalEObject)andConstraint).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QueryPackage.CONSTRAINT__AND_CONSTRAINT, null, msgs);
			if (newAndConstraint != null)
				msgs = ((InternalEObject)newAndConstraint).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QueryPackage.CONSTRAINT__AND_CONSTRAINT, null, msgs);
			msgs = basicSetAndConstraint(newAndConstraint, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.CONSTRAINT__AND_CONSTRAINT, newAndConstraint, newAndConstraint));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case QueryPackage.CONSTRAINT__OR_CONSTRAINT:
				return basicSetOrConstraint(null, msgs);
			case QueryPackage.CONSTRAINT__AND_CONSTRAINT:
				return basicSetAndConstraint(null, msgs);
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
			case QueryPackage.CONSTRAINT__NEGATE:
				return isNegate();
			case QueryPackage.CONSTRAINT__OR_CONSTRAINT:
				return getOrConstraint();
			case QueryPackage.CONSTRAINT__AND_CONSTRAINT:
				return getAndConstraint();
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
			case QueryPackage.CONSTRAINT__NEGATE:
				setNegate((Boolean)newValue);
				return;
			case QueryPackage.CONSTRAINT__OR_CONSTRAINT:
				setOrConstraint((Constraint)newValue);
				return;
			case QueryPackage.CONSTRAINT__AND_CONSTRAINT:
				setAndConstraint((Constraint)newValue);
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
			case QueryPackage.CONSTRAINT__NEGATE:
				setNegate(NEGATE_EDEFAULT);
				return;
			case QueryPackage.CONSTRAINT__OR_CONSTRAINT:
				setOrConstraint((Constraint)null);
				return;
			case QueryPackage.CONSTRAINT__AND_CONSTRAINT:
				setAndConstraint((Constraint)null);
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
			case QueryPackage.CONSTRAINT__NEGATE:
				return negate != NEGATE_EDEFAULT;
			case QueryPackage.CONSTRAINT__OR_CONSTRAINT:
				return orConstraint != null;
			case QueryPackage.CONSTRAINT__AND_CONSTRAINT:
				return andConstraint != null;
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
		result.append(" (negate: "); //$NON-NLS-1$
		result.append(negate);
		result.append(')');
		return result.toString();
	}

} //ConstraintImpl
