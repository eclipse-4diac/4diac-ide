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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>ST Struct Init Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STStructInitElementImpl#getVariable <em>Variable</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STStructInitElementImpl#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class STStructInitElementImpl extends MinimalEObjectImpl.Container implements STStructInitElement {
	/**
	 * The cached value of the '{@link #getVariable() <em>Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariable()
	 * @generated
	 * @ordered
	 */
	protected INamedElement variable;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected STInitializerExpression value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected STStructInitElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return STCorePackage.Literals.ST_STRUCT_INIT_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public INamedElement getVariable() {
		if (variable != null && variable.eIsProxy()) {
			InternalEObject oldVariable = (InternalEObject)variable;
			variable = (INamedElement)eResolveProxy(oldVariable);
			if (variable != oldVariable) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, STCorePackage.ST_STRUCT_INIT_ELEMENT__VARIABLE, oldVariable, variable));
			}
		}
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public INamedElement basicGetVariable() {
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setVariable(INamedElement newVariable) {
		INamedElement oldVariable = variable;
		variable = newVariable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, STCorePackage.ST_STRUCT_INIT_ELEMENT__VARIABLE, oldVariable, variable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STInitializerExpression getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetValue(STInitializerExpression newValue, NotificationChain msgs) {
		STInitializerExpression oldValue = value;
		value = newValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, STCorePackage.ST_STRUCT_INIT_ELEMENT__VALUE, oldValue, newValue);
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
	public void setValue(STInitializerExpression newValue) {
		if (newValue != value) {
			NotificationChain msgs = null;
			if (value != null)
				msgs = ((InternalEObject)value).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - STCorePackage.ST_STRUCT_INIT_ELEMENT__VALUE, null, msgs);
			if (newValue != null)
				msgs = ((InternalEObject)newValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - STCorePackage.ST_STRUCT_INIT_ELEMENT__VALUE, null, msgs);
			msgs = basicSetValue(newValue, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, STCorePackage.ST_STRUCT_INIT_ELEMENT__VALUE, newValue, newValue));
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
			case STCorePackage.ST_STRUCT_INIT_ELEMENT__VALUE:
				return basicSetValue(null, msgs);
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
			case STCorePackage.ST_STRUCT_INIT_ELEMENT__VARIABLE:
				if (resolve) return getVariable();
				return basicGetVariable();
			case STCorePackage.ST_STRUCT_INIT_ELEMENT__VALUE:
				return getValue();
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
			case STCorePackage.ST_STRUCT_INIT_ELEMENT__VARIABLE:
				setVariable((INamedElement)newValue);
				return;
			case STCorePackage.ST_STRUCT_INIT_ELEMENT__VALUE:
				setValue((STInitializerExpression)newValue);
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
			case STCorePackage.ST_STRUCT_INIT_ELEMENT__VARIABLE:
				setVariable((INamedElement)null);
				return;
			case STCorePackage.ST_STRUCT_INIT_ELEMENT__VALUE:
				setValue((STInitializerExpression)null);
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
			case STCorePackage.ST_STRUCT_INIT_ELEMENT__VARIABLE:
				return variable != null;
			case STCorePackage.ST_STRUCT_INIT_ELEMENT__VALUE:
				return value != null;
			default:
				return super.eIsSet(featureID);
		}
	}

} //STStructInitElementImpl
