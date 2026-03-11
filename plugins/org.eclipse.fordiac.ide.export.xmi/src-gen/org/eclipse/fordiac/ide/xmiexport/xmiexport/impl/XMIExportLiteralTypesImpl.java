/**
 * *******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Martin Jobst
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.xmiexport.xmiexport.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportLiteralType;
import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportLiteralTypes;
import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Literal Types</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportLiteralTypesImpl#getLiteralTypes <em>Literal Types</em>}</li>
 * </ul>
 *
 * @generated
 */
public class XMIExportLiteralTypesImpl extends MinimalEObjectImpl.Container implements XMIExportLiteralTypes {
	/**
	 * The cached value of the '{@link #getLiteralTypes() <em>Literal Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLiteralTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<XMIExportLiteralType> literalTypes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XMIExportLiteralTypesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return XMIExportPackage.Literals.XMI_EXPORT_LITERAL_TYPES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<XMIExportLiteralType> getLiteralTypes() {
		if (literalTypes == null) {
			literalTypes = new EObjectContainmentEList<XMIExportLiteralType>(XMIExportLiteralType.class, this, XMIExportPackage.XMI_EXPORT_LITERAL_TYPES__LITERAL_TYPES);
		}
		return literalTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case XMIExportPackage.XMI_EXPORT_LITERAL_TYPES__LITERAL_TYPES:
				return ((InternalEList<?>)getLiteralTypes()).basicRemove(otherEnd, msgs);
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
			case XMIExportPackage.XMI_EXPORT_LITERAL_TYPES__LITERAL_TYPES:
				return getLiteralTypes();
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
			case XMIExportPackage.XMI_EXPORT_LITERAL_TYPES__LITERAL_TYPES:
				getLiteralTypes().clear();
				getLiteralTypes().addAll((Collection<? extends XMIExportLiteralType>)newValue);
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
			case XMIExportPackage.XMI_EXPORT_LITERAL_TYPES__LITERAL_TYPES:
				getLiteralTypes().clear();
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
			case XMIExportPackage.XMI_EXPORT_LITERAL_TYPES__LITERAL_TYPES:
				return literalTypes != null && !literalTypes.isEmpty();
			default:
				return super.eIsSet(featureID);
		}
	}

} //XMIExportLiteralTypesImpl
