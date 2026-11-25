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

import org.eclipse.fordiac.ide.bulkeditor.query.model.ConstraintDeclaration;
import org.eclipse.fordiac.ide.bulkeditor.query.model.Place;
import org.eclipse.fordiac.ide.bulkeditor.query.model.Placeholder;
import org.eclipse.fordiac.ide.bulkeditor.query.model.Query;
import org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage;
import org.eclipse.fordiac.ide.bulkeditor.query.model.Target;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Query</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryImpl#getPlaceholder <em>Placeholder</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryImpl#getPlace <em>Place</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryImpl#getConstraintDeclaration <em>Constraint Declaration</em>}</li>
 * </ul>
 *
 * @generated
 */
public class QueryImpl extends EObjectImpl implements Query {
	/**
	 * The cached value of the '{@link #getPlaceholder() <em>Placeholder</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlaceholder()
	 * @generated
	 * @ordered
	 */
	protected EList<Placeholder> placeholder;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected Target target;

	/**
	 * The cached value of the '{@link #getPlace() <em>Place</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlace()
	 * @generated
	 * @ordered
	 */
	protected Place place;

	/**
	 * The cached value of the '{@link #getConstraintDeclaration() <em>Constraint Declaration</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraintDeclaration()
	 * @generated
	 * @ordered
	 */
	protected EList<ConstraintDeclaration> constraintDeclaration;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected QueryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return QueryPackage.Literals.QUERY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Placeholder> getPlaceholder() {
		if (placeholder == null) {
			placeholder = new EObjectContainmentEList<Placeholder>(Placeholder.class, this, QueryPackage.QUERY__PLACEHOLDER);
		}
		return placeholder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Target getTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTarget(Target newTarget, NotificationChain msgs) {
		Target oldTarget = target;
		target = newTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QueryPackage.QUERY__TARGET, oldTarget, newTarget);
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
	public void setTarget(Target newTarget) {
		if (newTarget != target) {
			NotificationChain msgs = null;
			if (target != null)
				msgs = ((InternalEObject)target).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QueryPackage.QUERY__TARGET, null, msgs);
			if (newTarget != null)
				msgs = ((InternalEObject)newTarget).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QueryPackage.QUERY__TARGET, null, msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.QUERY__TARGET, newTarget, newTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Place getPlace() {
		return place;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPlace(Place newPlace, NotificationChain msgs) {
		Place oldPlace = place;
		place = newPlace;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, QueryPackage.QUERY__PLACE, oldPlace, newPlace);
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
	public void setPlace(Place newPlace) {
		if (newPlace != place) {
			NotificationChain msgs = null;
			if (place != null)
				msgs = ((InternalEObject)place).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - QueryPackage.QUERY__PLACE, null, msgs);
			if (newPlace != null)
				msgs = ((InternalEObject)newPlace).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - QueryPackage.QUERY__PLACE, null, msgs);
			msgs = basicSetPlace(newPlace, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QueryPackage.QUERY__PLACE, newPlace, newPlace));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ConstraintDeclaration> getConstraintDeclaration() {
		if (constraintDeclaration == null) {
			constraintDeclaration = new EObjectContainmentEList<ConstraintDeclaration>(ConstraintDeclaration.class, this, QueryPackage.QUERY__CONSTRAINT_DECLARATION);
		}
		return constraintDeclaration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case QueryPackage.QUERY__PLACEHOLDER:
				return ((InternalEList<?>)getPlaceholder()).basicRemove(otherEnd, msgs);
			case QueryPackage.QUERY__TARGET:
				return basicSetTarget(null, msgs);
			case QueryPackage.QUERY__PLACE:
				return basicSetPlace(null, msgs);
			case QueryPackage.QUERY__CONSTRAINT_DECLARATION:
				return ((InternalEList<?>)getConstraintDeclaration()).basicRemove(otherEnd, msgs);
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
			case QueryPackage.QUERY__PLACEHOLDER:
				return getPlaceholder();
			case QueryPackage.QUERY__TARGET:
				return getTarget();
			case QueryPackage.QUERY__PLACE:
				return getPlace();
			case QueryPackage.QUERY__CONSTRAINT_DECLARATION:
				return getConstraintDeclaration();
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
			case QueryPackage.QUERY__PLACEHOLDER:
				getPlaceholder().clear();
				getPlaceholder().addAll((Collection<? extends Placeholder>)newValue);
				return;
			case QueryPackage.QUERY__TARGET:
				setTarget((Target)newValue);
				return;
			case QueryPackage.QUERY__PLACE:
				setPlace((Place)newValue);
				return;
			case QueryPackage.QUERY__CONSTRAINT_DECLARATION:
				getConstraintDeclaration().clear();
				getConstraintDeclaration().addAll((Collection<? extends ConstraintDeclaration>)newValue);
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
			case QueryPackage.QUERY__PLACEHOLDER:
				getPlaceholder().clear();
				return;
			case QueryPackage.QUERY__TARGET:
				setTarget((Target)null);
				return;
			case QueryPackage.QUERY__PLACE:
				setPlace((Place)null);
				return;
			case QueryPackage.QUERY__CONSTRAINT_DECLARATION:
				getConstraintDeclaration().clear();
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
			case QueryPackage.QUERY__PLACEHOLDER:
				return placeholder != null && !placeholder.isEmpty();
			case QueryPackage.QUERY__TARGET:
				return target != null;
			case QueryPackage.QUERY__PLACE:
				return place != null;
			case QueryPackage.QUERY__CONSTRAINT_DECLARATION:
				return constraintDeclaration != null && !constraintDeclaration.isEmpty();
			default:
				return super.eIsSet(featureID);
		}
	}

} //QueryImpl
