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

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultiBitAccessSpecifier;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>ST Multibit Partial Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STMultibitPartialExpressionImpl#getSpecifier <em>Specifier</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STMultibitPartialExpressionImpl#getIndex <em>Index</em>}</li>
 * </ul>
 *
 * @generated
 */
public class STMultibitPartialExpressionImpl extends STExpressionImpl implements STMultibitPartialExpression {
	/**
	 * The default value of the '{@link #getSpecifier() <em>Specifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifier()
	 * @generated
	 * @ordered
	 */
	protected static final STMultiBitAccessSpecifier SPECIFIER_EDEFAULT = STMultiBitAccessSpecifier.L;

	/**
	 * The cached value of the '{@link #getSpecifier() <em>Specifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifier()
	 * @generated
	 * @ordered
	 */
	protected STMultiBitAccessSpecifier specifier = SPECIFIER_EDEFAULT;

	/**
	 * The default value of the '{@link #getIndex() <em>Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndex()
	 * @generated
	 * @ordered
	 */
	protected static final BigInteger INDEX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIndex() <em>Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndex()
	 * @generated
	 * @ordered
	 */
	protected BigInteger index = INDEX_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected STMultibitPartialExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return STCorePackage.Literals.ST_MULTIBIT_PARTIAL_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STMultiBitAccessSpecifier getSpecifier() {
		return specifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSpecifier(STMultiBitAccessSpecifier newSpecifier) {
		STMultiBitAccessSpecifier oldSpecifier = specifier;
		specifier = newSpecifier == null ? SPECIFIER_EDEFAULT : newSpecifier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, STCorePackage.ST_MULTIBIT_PARTIAL_EXPRESSION__SPECIFIER, oldSpecifier, specifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BigInteger getIndex() {
		return index;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIndex(BigInteger newIndex) {
		BigInteger oldIndex = index;
		index = newIndex;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, STCorePackage.ST_MULTIBIT_PARTIAL_EXPRESSION__INDEX, oldIndex, index));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case STCorePackage.ST_MULTIBIT_PARTIAL_EXPRESSION__SPECIFIER:
				return getSpecifier();
			case STCorePackage.ST_MULTIBIT_PARTIAL_EXPRESSION__INDEX:
				return getIndex();
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
			case STCorePackage.ST_MULTIBIT_PARTIAL_EXPRESSION__SPECIFIER:
				setSpecifier((STMultiBitAccessSpecifier)newValue);
				return;
			case STCorePackage.ST_MULTIBIT_PARTIAL_EXPRESSION__INDEX:
				setIndex((BigInteger)newValue);
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
			case STCorePackage.ST_MULTIBIT_PARTIAL_EXPRESSION__SPECIFIER:
				setSpecifier(SPECIFIER_EDEFAULT);
				return;
			case STCorePackage.ST_MULTIBIT_PARTIAL_EXPRESSION__INDEX:
				setIndex(INDEX_EDEFAULT);
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
			case STCorePackage.ST_MULTIBIT_PARTIAL_EXPRESSION__SPECIFIER:
				return specifier != SPECIFIER_EDEFAULT;
			case STCorePackage.ST_MULTIBIT_PARTIAL_EXPRESSION__INDEX:
				return INDEX_EDEFAULT == null ? index != null : !INDEX_EDEFAULT.equals(index);
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
		result.append(" (specifier: "); //$NON-NLS-1$
		result.append(specifier);
		result.append(", index: "); //$NON-NLS-1$
		result.append(index);
		result.append(')');
		return result.toString();
	}

} //STMultibitPartialExpressionImpl
