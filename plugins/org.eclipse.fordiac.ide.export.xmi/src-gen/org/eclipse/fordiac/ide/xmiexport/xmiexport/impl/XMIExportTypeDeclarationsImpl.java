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

import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportPackage;
import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportTypeDeclaration;
import org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportTypeDeclarations;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type Declarations</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportTypeDeclarationsImpl#getTypeDeclarations <em>Type Declarations</em>}</li>
 * </ul>
 *
 * @generated
 */
public class XMIExportTypeDeclarationsImpl extends MinimalEObjectImpl.Container implements XMIExportTypeDeclarations {
	/**
	 * The cached value of the '{@link #getTypeDeclarations() <em>Type Declarations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeDeclarations()
	 * @generated
	 * @ordered
	 */
	protected EList<XMIExportTypeDeclaration> typeDeclarations;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XMIExportTypeDeclarationsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return XMIExportPackage.Literals.XMI_EXPORT_TYPE_DECLARATIONS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<XMIExportTypeDeclaration> getTypeDeclarations() {
		if (typeDeclarations == null) {
			typeDeclarations = new EObjectContainmentEList<XMIExportTypeDeclaration>(XMIExportTypeDeclaration.class, this, XMIExportPackage.XMI_EXPORT_TYPE_DECLARATIONS__TYPE_DECLARATIONS);
		}
		return typeDeclarations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case XMIExportPackage.XMI_EXPORT_TYPE_DECLARATIONS__TYPE_DECLARATIONS:
				return ((InternalEList<?>)getTypeDeclarations()).basicRemove(otherEnd, msgs);
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
			case XMIExportPackage.XMI_EXPORT_TYPE_DECLARATIONS__TYPE_DECLARATIONS:
				return getTypeDeclarations();
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
			case XMIExportPackage.XMI_EXPORT_TYPE_DECLARATIONS__TYPE_DECLARATIONS:
				getTypeDeclarations().clear();
				getTypeDeclarations().addAll((Collection<? extends XMIExportTypeDeclaration>)newValue);
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
			case XMIExportPackage.XMI_EXPORT_TYPE_DECLARATIONS__TYPE_DECLARATIONS:
				getTypeDeclarations().clear();
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
			case XMIExportPackage.XMI_EXPORT_TYPE_DECLARATIONS__TYPE_DECLARATIONS:
				return typeDeclarations != null && !typeDeclarations.isEmpty();
			default:
				return super.eIsSet(featureID);
		}
	}

} //XMIExportTypeDeclarationsImpl
