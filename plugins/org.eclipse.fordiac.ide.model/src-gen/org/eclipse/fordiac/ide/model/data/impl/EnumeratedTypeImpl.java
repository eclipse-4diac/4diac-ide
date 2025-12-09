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
package org.eclipse.fordiac.ide.model.data.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.fordiac.ide.model.data.DataPackage;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.EnumeratedType;
import org.eclipse.fordiac.ide.model.data.EnumeratedValue;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Enumerated Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.data.impl.EnumeratedTypeImpl#getEnumeratedValues <em>Enumerated Values</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EnumeratedTypeImpl extends AnyDerivedTypeImpl implements EnumeratedType {
	/**
	 * The cached value of the '{@link #getEnumeratedValues() <em>Enumerated Values</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnumeratedValues()
	 * @generated
	 * @ordered
	 */
	protected EList<EnumeratedValue> enumeratedValues;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EnumeratedTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DataPackage.Literals.ENUMERATED_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<EnumeratedValue> getEnumeratedValues() {
		if (enumeratedValues == null) {
			enumeratedValues = new EObjectContainmentWithInverseEList<EnumeratedValue>(EnumeratedValue.class, this, DataPackage.ENUMERATED_TYPE__ENUMERATED_VALUES, DataPackage.ENUMERATED_VALUE__TYPE);
		}
		return enumeratedValues;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isAssignableFrom(final DataType other) {
		return org.eclipse.fordiac.ide.model.data.impl.DataTypeAnnotations.isAssignableFrom(this, other);
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
			case DataPackage.ENUMERATED_TYPE__ENUMERATED_VALUES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getEnumeratedValues()).basicAdd(otherEnd, msgs);
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
			case DataPackage.ENUMERATED_TYPE__ENUMERATED_VALUES:
				return ((InternalEList<?>)getEnumeratedValues()).basicRemove(otherEnd, msgs);
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
			case DataPackage.ENUMERATED_TYPE__ENUMERATED_VALUES:
				return getEnumeratedValues();
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
			case DataPackage.ENUMERATED_TYPE__ENUMERATED_VALUES:
				getEnumeratedValues().clear();
				getEnumeratedValues().addAll((Collection<? extends EnumeratedValue>)newValue);
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
			case DataPackage.ENUMERATED_TYPE__ENUMERATED_VALUES:
				getEnumeratedValues().clear();
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
			case DataPackage.ENUMERATED_TYPE__ENUMERATED_VALUES:
				return enumeratedValues != null && !enumeratedValues.isEmpty();
			default:
				return super.eIsSet(featureID);
		}
	}

} //EnumeratedTypeImpl
