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
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>ST Var Declaration Block</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarDeclarationBlockImpl#isConstant <em>Constant</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarDeclarationBlockImpl#getVarDeclarations <em>Var Declarations</em>}</li>
 * </ul>
 *
 * @generated
 */
public class STVarDeclarationBlockImpl extends MinimalEObjectImpl.Container implements STVarDeclarationBlock {
	/**
	 * The default value of the '{@link #isConstant() <em>Constant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConstant()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CONSTANT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isConstant() <em>Constant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConstant()
	 * @generated
	 * @ordered
	 */
	protected boolean constant = CONSTANT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getVarDeclarations() <em>Var Declarations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVarDeclarations()
	 * @generated
	 * @ordered
	 */
	protected EList<STVarDeclaration> varDeclarations;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected STVarDeclarationBlockImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return STCorePackage.Literals.ST_VAR_DECLARATION_BLOCK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isConstant() {
		return constant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setConstant(boolean newConstant) {
		boolean oldConstant = constant;
		constant = newConstant;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, STCorePackage.ST_VAR_DECLARATION_BLOCK__CONSTANT, oldConstant, constant));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<STVarDeclaration> getVarDeclarations() {
		if (varDeclarations == null) {
			varDeclarations = new EObjectContainmentEList<STVarDeclaration>(STVarDeclaration.class, this, STCorePackage.ST_VAR_DECLARATION_BLOCK__VAR_DECLARATIONS);
		}
		return varDeclarations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case STCorePackage.ST_VAR_DECLARATION_BLOCK__VAR_DECLARATIONS:
				return ((InternalEList<?>)getVarDeclarations()).basicRemove(otherEnd, msgs);
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
			case STCorePackage.ST_VAR_DECLARATION_BLOCK__CONSTANT:
				return isConstant();
			case STCorePackage.ST_VAR_DECLARATION_BLOCK__VAR_DECLARATIONS:
				return getVarDeclarations();
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
			case STCorePackage.ST_VAR_DECLARATION_BLOCK__CONSTANT:
				setConstant((Boolean)newValue);
				return;
			case STCorePackage.ST_VAR_DECLARATION_BLOCK__VAR_DECLARATIONS:
				getVarDeclarations().clear();
				getVarDeclarations().addAll((Collection<? extends STVarDeclaration>)newValue);
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
			case STCorePackage.ST_VAR_DECLARATION_BLOCK__CONSTANT:
				setConstant(CONSTANT_EDEFAULT);
				return;
			case STCorePackage.ST_VAR_DECLARATION_BLOCK__VAR_DECLARATIONS:
				getVarDeclarations().clear();
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
			case STCorePackage.ST_VAR_DECLARATION_BLOCK__CONSTANT:
				return constant != CONSTANT_EDEFAULT;
			case STCorePackage.ST_VAR_DECLARATION_BLOCK__VAR_DECLARATIONS:
				return varDeclarations != null && !varDeclarations.isEmpty();
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
		result.append(" (constant: "); //$NON-NLS-1$
		result.append(constant);
		result.append(')');
		return result.toString();
	}

} //STVarDeclarationBlockImpl
