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

import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTypeDeclaration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>ST Type Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STTypeDeclarationImpl#isArray <em>Array</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STTypeDeclarationImpl#getRanges <em>Ranges</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STTypeDeclarationImpl#getCount <em>Count</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STTypeDeclarationImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STTypeDeclarationImpl#getMaxLength <em>Max Length</em>}</li>
 * </ul>
 *
 * @generated
 */
public class STTypeDeclarationImpl extends MinimalEObjectImpl.Container implements STTypeDeclaration {
	/**
	 * The default value of the '{@link #isArray() <em>Array</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isArray()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ARRAY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isArray() <em>Array</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isArray()
	 * @generated
	 * @ordered
	 */
	protected boolean array = ARRAY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRanges() <em>Ranges</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRanges()
	 * @generated
	 * @ordered
	 */
	protected EList<STExpression> ranges;

	/**
	 * The cached value of the '{@link #getCount() <em>Count</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCount()
	 * @generated
	 * @ordered
	 */
	protected EList<String> count;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected INamedElement type;

	/**
	 * The cached value of the '{@link #getMaxLength() <em>Max Length</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxLength()
	 * @generated
	 * @ordered
	 */
	protected STExpression maxLength;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected STTypeDeclarationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return STCorePackage.Literals.ST_TYPE_DECLARATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isArray() {
		return array;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setArray(boolean newArray) {
		boolean oldArray = array;
		array = newArray;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, STCorePackage.ST_TYPE_DECLARATION__ARRAY, oldArray, array));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<STExpression> getRanges() {
		if (ranges == null) {
			ranges = new EObjectContainmentEList<STExpression>(STExpression.class, this, STCorePackage.ST_TYPE_DECLARATION__RANGES);
		}
		return ranges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getCount() {
		if (count == null) {
			count = new EDataTypeEList<String>(String.class, this, STCorePackage.ST_TYPE_DECLARATION__COUNT);
		}
		return count;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public INamedElement getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (INamedElement)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, STCorePackage.ST_TYPE_DECLARATION__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public INamedElement basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(INamedElement newType) {
		INamedElement oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, STCorePackage.ST_TYPE_DECLARATION__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STExpression getMaxLength() {
		return maxLength;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMaxLength(STExpression newMaxLength, NotificationChain msgs) {
		STExpression oldMaxLength = maxLength;
		maxLength = newMaxLength;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, STCorePackage.ST_TYPE_DECLARATION__MAX_LENGTH, oldMaxLength, newMaxLength);
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
	public void setMaxLength(STExpression newMaxLength) {
		if (newMaxLength != maxLength) {
			NotificationChain msgs = null;
			if (maxLength != null)
				msgs = ((InternalEObject)maxLength).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - STCorePackage.ST_TYPE_DECLARATION__MAX_LENGTH, null, msgs);
			if (newMaxLength != null)
				msgs = ((InternalEObject)newMaxLength).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - STCorePackage.ST_TYPE_DECLARATION__MAX_LENGTH, null, msgs);
			msgs = basicSetMaxLength(newMaxLength, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, STCorePackage.ST_TYPE_DECLARATION__MAX_LENGTH, newMaxLength, newMaxLength));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case STCorePackage.ST_TYPE_DECLARATION__RANGES:
				return ((InternalEList<?>)getRanges()).basicRemove(otherEnd, msgs);
			case STCorePackage.ST_TYPE_DECLARATION__MAX_LENGTH:
				return basicSetMaxLength(null, msgs);
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
			case STCorePackage.ST_TYPE_DECLARATION__ARRAY:
				return isArray();
			case STCorePackage.ST_TYPE_DECLARATION__RANGES:
				return getRanges();
			case STCorePackage.ST_TYPE_DECLARATION__COUNT:
				return getCount();
			case STCorePackage.ST_TYPE_DECLARATION__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case STCorePackage.ST_TYPE_DECLARATION__MAX_LENGTH:
				return getMaxLength();
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
			case STCorePackage.ST_TYPE_DECLARATION__ARRAY:
				setArray((Boolean)newValue);
				return;
			case STCorePackage.ST_TYPE_DECLARATION__RANGES:
				getRanges().clear();
				getRanges().addAll((Collection<? extends STExpression>)newValue);
				return;
			case STCorePackage.ST_TYPE_DECLARATION__COUNT:
				getCount().clear();
				getCount().addAll((Collection<? extends String>)newValue);
				return;
			case STCorePackage.ST_TYPE_DECLARATION__TYPE:
				setType((INamedElement)newValue);
				return;
			case STCorePackage.ST_TYPE_DECLARATION__MAX_LENGTH:
				setMaxLength((STExpression)newValue);
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
			case STCorePackage.ST_TYPE_DECLARATION__ARRAY:
				setArray(ARRAY_EDEFAULT);
				return;
			case STCorePackage.ST_TYPE_DECLARATION__RANGES:
				getRanges().clear();
				return;
			case STCorePackage.ST_TYPE_DECLARATION__COUNT:
				getCount().clear();
				return;
			case STCorePackage.ST_TYPE_DECLARATION__TYPE:
				setType((INamedElement)null);
				return;
			case STCorePackage.ST_TYPE_DECLARATION__MAX_LENGTH:
				setMaxLength((STExpression)null);
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
			case STCorePackage.ST_TYPE_DECLARATION__ARRAY:
				return array != ARRAY_EDEFAULT;
			case STCorePackage.ST_TYPE_DECLARATION__RANGES:
				return ranges != null && !ranges.isEmpty();
			case STCorePackage.ST_TYPE_DECLARATION__COUNT:
				return count != null && !count.isEmpty();
			case STCorePackage.ST_TYPE_DECLARATION__TYPE:
				return type != null;
			case STCorePackage.ST_TYPE_DECLARATION__MAX_LENGTH:
				return maxLength != null;
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
		result.append(" (array: "); //$NON-NLS-1$
		result.append(array);
		result.append(", count: "); //$NON-NLS-1$
		result.append(count);
		result.append(')');
		return result.toString();
	}

} //STTypeDeclarationImpl
