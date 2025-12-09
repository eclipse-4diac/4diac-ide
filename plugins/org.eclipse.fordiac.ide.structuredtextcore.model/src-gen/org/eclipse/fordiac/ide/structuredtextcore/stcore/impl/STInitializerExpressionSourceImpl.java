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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpressionSource;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>ST Initializer Expression Source</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STInitializerExpressionSourceImpl#getInitializerExpression <em>Initializer Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class STInitializerExpressionSourceImpl extends STSourceImpl implements STInitializerExpressionSource {
	/**
	 * The cached value of the '{@link #getInitializerExpression() <em>Initializer Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitializerExpression()
	 * @generated
	 * @ordered
	 */
	protected STInitializerExpression initializerExpression;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected STInitializerExpressionSourceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return STCorePackage.Literals.ST_INITIALIZER_EXPRESSION_SOURCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STInitializerExpression getInitializerExpression() {
		return initializerExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInitializerExpression(STInitializerExpression newInitializerExpression, NotificationChain msgs) {
		STInitializerExpression oldInitializerExpression = initializerExpression;
		initializerExpression = newInitializerExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, STCorePackage.ST_INITIALIZER_EXPRESSION_SOURCE__INITIALIZER_EXPRESSION, oldInitializerExpression, newInitializerExpression);
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
	public void setInitializerExpression(STInitializerExpression newInitializerExpression) {
		if (newInitializerExpression != initializerExpression) {
			NotificationChain msgs = null;
			if (initializerExpression != null)
				msgs = ((InternalEObject)initializerExpression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - STCorePackage.ST_INITIALIZER_EXPRESSION_SOURCE__INITIALIZER_EXPRESSION, null, msgs);
			if (newInitializerExpression != null)
				msgs = ((InternalEObject)newInitializerExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - STCorePackage.ST_INITIALIZER_EXPRESSION_SOURCE__INITIALIZER_EXPRESSION, null, msgs);
			msgs = basicSetInitializerExpression(newInitializerExpression, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, STCorePackage.ST_INITIALIZER_EXPRESSION_SOURCE__INITIALIZER_EXPRESSION, newInitializerExpression, newInitializerExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case STCorePackage.ST_INITIALIZER_EXPRESSION_SOURCE__INITIALIZER_EXPRESSION:
				return basicSetInitializerExpression(null, msgs);
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
			case STCorePackage.ST_INITIALIZER_EXPRESSION_SOURCE__INITIALIZER_EXPRESSION:
				return getInitializerExpression();
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
			case STCorePackage.ST_INITIALIZER_EXPRESSION_SOURCE__INITIALIZER_EXPRESSION:
				setInitializerExpression((STInitializerExpression)newValue);
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
			case STCorePackage.ST_INITIALIZER_EXPRESSION_SOURCE__INITIALIZER_EXPRESSION:
				setInitializerExpression((STInitializerExpression)null);
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
			case STCorePackage.ST_INITIALIZER_EXPRESSION_SOURCE__INITIALIZER_EXPRESSION:
				return initializerExpression != null;
			default:
				return super.eIsSet(featureID);
		}
	}

} //STInitializerExpressionSourceImpl
