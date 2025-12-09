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
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.fordiac.ide.structuredtextcore.stcore.STImport;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STSourceImpl;

import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionPackage;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Source</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.impl.STFunctionSourceImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.impl.STFunctionSourceImpl#getImports <em>Imports</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.impl.STFunctionSourceImpl#getFunctions <em>Functions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class STFunctionSourceImpl extends STSourceImpl implements STFunctionSource {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;
	/**
	 * The cached value of the '{@link #getImports() <em>Imports</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImports()
	 * @generated
	 * @ordered
	 */
	protected EList<STImport> imports;
	/**
	 * The cached value of the '{@link #getFunctions() <em>Functions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFunctions()
	 * @generated
	 * @ordered
	 */
	protected EList<STFunction> functions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected STFunctionSourceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return STFunctionPackage.Literals.ST_FUNCTION_SOURCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, STFunctionPackage.ST_FUNCTION_SOURCE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<STImport> getImports() {
		if (imports == null) {
			imports = new EObjectContainmentEList<STImport>(STImport.class, this, STFunctionPackage.ST_FUNCTION_SOURCE__IMPORTS);
		}
		return imports;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<STFunction> getFunctions() {
		if (functions == null) {
			functions = new EObjectContainmentEList<STFunction>(STFunction.class, this, STFunctionPackage.ST_FUNCTION_SOURCE__FUNCTIONS);
		}
		return functions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case STFunctionPackage.ST_FUNCTION_SOURCE__IMPORTS:
				return ((InternalEList<?>)getImports()).basicRemove(otherEnd, msgs);
			case STFunctionPackage.ST_FUNCTION_SOURCE__FUNCTIONS:
				return ((InternalEList<?>)getFunctions()).basicRemove(otherEnd, msgs);
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
			case STFunctionPackage.ST_FUNCTION_SOURCE__NAME:
				return getName();
			case STFunctionPackage.ST_FUNCTION_SOURCE__IMPORTS:
				return getImports();
			case STFunctionPackage.ST_FUNCTION_SOURCE__FUNCTIONS:
				return getFunctions();
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
			case STFunctionPackage.ST_FUNCTION_SOURCE__NAME:
				setName((String)newValue);
				return;
			case STFunctionPackage.ST_FUNCTION_SOURCE__IMPORTS:
				getImports().clear();
				getImports().addAll((Collection<? extends STImport>)newValue);
				return;
			case STFunctionPackage.ST_FUNCTION_SOURCE__FUNCTIONS:
				getFunctions().clear();
				getFunctions().addAll((Collection<? extends STFunction>)newValue);
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
			case STFunctionPackage.ST_FUNCTION_SOURCE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case STFunctionPackage.ST_FUNCTION_SOURCE__IMPORTS:
				getImports().clear();
				return;
			case STFunctionPackage.ST_FUNCTION_SOURCE__FUNCTIONS:
				getFunctions().clear();
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
			case STFunctionPackage.ST_FUNCTION_SOURCE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case STFunctionPackage.ST_FUNCTION_SOURCE__IMPORTS:
				return imports != null && !imports.isEmpty();
			case STFunctionPackage.ST_FUNCTION_SOURCE__FUNCTIONS:
				return functions != null && !functions.isEmpty();
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
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //STFunctionSourceImpl
