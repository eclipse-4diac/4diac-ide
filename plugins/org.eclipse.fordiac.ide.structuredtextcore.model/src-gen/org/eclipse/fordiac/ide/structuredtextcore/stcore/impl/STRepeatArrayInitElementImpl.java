/**
 * *******************************************************************************
 * Copyright (c) 2022 Primetals Technologies GmbH,
 *               2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Martin Jobst, Martin Melik Merkumians
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.structuredtextcore.stcore.impl;

import java.math.BigInteger;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatArrayInitElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>ST Repeat Array Init Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STRepeatArrayInitElementImpl#getRepetitions <em>Repetitions</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STRepeatArrayInitElementImpl#getInitExpressions <em>Init Expressions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class STRepeatArrayInitElementImpl extends MinimalEObjectImpl.Container implements STRepeatArrayInitElement {
	/**
	 * The default value of the '{@link #getRepetitions() <em>Repetitions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRepetitions()
	 * @generated
	 * @ordered
	 */
	protected static final BigInteger REPETITIONS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRepetitions() <em>Repetitions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRepetitions()
	 * @generated
	 * @ordered
	 */
	protected BigInteger repetitions = REPETITIONS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getInitExpressions() <em>Init Expressions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitExpressions()
	 * @generated
	 * @ordered
	 */
	protected EList<STInitializerExpression> initExpressions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected STRepeatArrayInitElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return STCorePackage.Literals.ST_REPEAT_ARRAY_INIT_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BigInteger getRepetitions() {
		return repetitions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRepetitions(BigInteger newRepetitions) {
		BigInteger oldRepetitions = repetitions;
		repetitions = newRepetitions;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, STCorePackage.ST_REPEAT_ARRAY_INIT_ELEMENT__REPETITIONS, oldRepetitions, repetitions));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<STInitializerExpression> getInitExpressions() {
		if (initExpressions == null) {
			initExpressions = new EObjectContainmentEList<STInitializerExpression>(STInitializerExpression.class, this, STCorePackage.ST_REPEAT_ARRAY_INIT_ELEMENT__INIT_EXPRESSIONS);
		}
		return initExpressions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public INamedElement getResultType() {
		return org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.ExpressionAnnotations.getResultType(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public INamedElement getDeclaredResultType() {
		return org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.ExpressionAnnotations.getDeclaredResultType(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case STCorePackage.ST_REPEAT_ARRAY_INIT_ELEMENT__INIT_EXPRESSIONS:
				return ((InternalEList<?>)getInitExpressions()).basicRemove(otherEnd, msgs);
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
			case STCorePackage.ST_REPEAT_ARRAY_INIT_ELEMENT__REPETITIONS:
				return getRepetitions();
			case STCorePackage.ST_REPEAT_ARRAY_INIT_ELEMENT__INIT_EXPRESSIONS:
				return getInitExpressions();
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
			case STCorePackage.ST_REPEAT_ARRAY_INIT_ELEMENT__REPETITIONS:
				setRepetitions((BigInteger)newValue);
				return;
			case STCorePackage.ST_REPEAT_ARRAY_INIT_ELEMENT__INIT_EXPRESSIONS:
				getInitExpressions().clear();
				getInitExpressions().addAll((Collection<? extends STInitializerExpression>)newValue);
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
			case STCorePackage.ST_REPEAT_ARRAY_INIT_ELEMENT__REPETITIONS:
				setRepetitions(REPETITIONS_EDEFAULT);
				return;
			case STCorePackage.ST_REPEAT_ARRAY_INIT_ELEMENT__INIT_EXPRESSIONS:
				getInitExpressions().clear();
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
			case STCorePackage.ST_REPEAT_ARRAY_INIT_ELEMENT__REPETITIONS:
				return REPETITIONS_EDEFAULT == null ? repetitions != null : !REPETITIONS_EDEFAULT.equals(repetitions);
			case STCorePackage.ST_REPEAT_ARRAY_INIT_ELEMENT__INIT_EXPRESSIONS:
				return initExpressions != null && !initExpressions.isEmpty();
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
		result.append(" (repetitions: "); //$NON-NLS-1$
		result.append(repetitions);
		result.append(')');
		return result.toString();
	}

} //STRepeatArrayInitElementImpl
