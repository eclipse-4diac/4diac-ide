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
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Compiler Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.CompilerInfoImpl#getCompiler <em>Compiler</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.CompilerInfoImpl#getClassdef <em>Classdef</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.CompilerInfoImpl#getHeader <em>Header</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.CompilerInfoImpl#getPackageName <em>Package Name</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.CompilerInfoImpl#getImports <em>Imports</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompilerInfoImpl extends EObjectImpl implements CompilerInfo {
	/**
	 * The cached value of the '{@link #getCompiler() <em>Compiler</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompiler()
	 * @generated
	 * @ordered
	 */
	protected EList<org.eclipse.fordiac.ide.model.libraryElement.Compiler> compiler;

	/**
	 * The default value of the '{@link #getClassdef() <em>Classdef</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassdef()
	 * @generated
	 * @ordered
	 */
	protected static final String CLASSDEF_EDEFAULT = ""; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getClassdef() <em>Classdef</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassdef()
	 * @generated
	 * @ordered
	 */
	protected String classdef = CLASSDEF_EDEFAULT;

	/**
	 * The default value of the '{@link #getHeader() <em>Header</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeader()
	 * @generated
	 * @ordered
	 */
	protected static final String HEADER_EDEFAULT = ""; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getHeader() <em>Header</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeader()
	 * @generated
	 * @ordered
	 */
	protected String header = HEADER_EDEFAULT;

	/**
	 * The default value of the '{@link #getPackageName() <em>Package Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageName()
	 * @generated
	 * @ordered
	 */
	protected static final String PACKAGE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPackageName() <em>Package Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageName()
	 * @generated
	 * @ordered
	 */
	protected String packageName = PACKAGE_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getImports() <em>Imports</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImports()
	 * @generated
	 * @ordered
	 */
	protected EList<Import> imports;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompilerInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.COMPILER_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<org.eclipse.fordiac.ide.model.libraryElement.Compiler> getCompiler() {
		if (compiler == null) {
			compiler = new EObjectContainmentEList<org.eclipse.fordiac.ide.model.libraryElement.Compiler>(org.eclipse.fordiac.ide.model.libraryElement.Compiler.class, this, LibraryElementPackage.COMPILER_INFO__COMPILER);
		}
		return compiler;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getClassdef() {
		return classdef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setClassdef(String newClassdef) {
		String oldClassdef = classdef;
		classdef = newClassdef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.COMPILER_INFO__CLASSDEF, oldClassdef, classdef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getHeader() {
		return header;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHeader(String newHeader) {
		String oldHeader = header;
		header = newHeader;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.COMPILER_INFO__HEADER, oldHeader, header));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getPackageName() {
		return packageName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPackageName(String newPackageName) {
		String oldPackageName = packageName;
		packageName = newPackageName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.COMPILER_INFO__PACKAGE_NAME, oldPackageName, packageName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Import> getImports() {
		if (imports == null) {
			imports = new EObjectContainmentEList.Resolving<Import>(Import.class, this, LibraryElementPackage.COMPILER_INFO__IMPORTS);
		}
		return imports;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.COMPILER_INFO__COMPILER:
				return ((InternalEList<?>)getCompiler()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.COMPILER_INFO__IMPORTS:
				return ((InternalEList<?>)getImports()).basicRemove(otherEnd, msgs);
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
			case LibraryElementPackage.COMPILER_INFO__COMPILER:
				return getCompiler();
			case LibraryElementPackage.COMPILER_INFO__CLASSDEF:
				return getClassdef();
			case LibraryElementPackage.COMPILER_INFO__HEADER:
				return getHeader();
			case LibraryElementPackage.COMPILER_INFO__PACKAGE_NAME:
				return getPackageName();
			case LibraryElementPackage.COMPILER_INFO__IMPORTS:
				return getImports();
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
			case LibraryElementPackage.COMPILER_INFO__COMPILER:
				getCompiler().clear();
				getCompiler().addAll((Collection<? extends org.eclipse.fordiac.ide.model.libraryElement.Compiler>)newValue);
				return;
			case LibraryElementPackage.COMPILER_INFO__CLASSDEF:
				setClassdef((String)newValue);
				return;
			case LibraryElementPackage.COMPILER_INFO__HEADER:
				setHeader((String)newValue);
				return;
			case LibraryElementPackage.COMPILER_INFO__PACKAGE_NAME:
				setPackageName((String)newValue);
				return;
			case LibraryElementPackage.COMPILER_INFO__IMPORTS:
				getImports().clear();
				getImports().addAll((Collection<? extends Import>)newValue);
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
			case LibraryElementPackage.COMPILER_INFO__COMPILER:
				getCompiler().clear();
				return;
			case LibraryElementPackage.COMPILER_INFO__CLASSDEF:
				setClassdef(CLASSDEF_EDEFAULT);
				return;
			case LibraryElementPackage.COMPILER_INFO__HEADER:
				setHeader(HEADER_EDEFAULT);
				return;
			case LibraryElementPackage.COMPILER_INFO__PACKAGE_NAME:
				setPackageName(PACKAGE_NAME_EDEFAULT);
				return;
			case LibraryElementPackage.COMPILER_INFO__IMPORTS:
				getImports().clear();
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
			case LibraryElementPackage.COMPILER_INFO__COMPILER:
				return compiler != null && !compiler.isEmpty();
			case LibraryElementPackage.COMPILER_INFO__CLASSDEF:
				return CLASSDEF_EDEFAULT == null ? classdef != null : !CLASSDEF_EDEFAULT.equals(classdef);
			case LibraryElementPackage.COMPILER_INFO__HEADER:
				return HEADER_EDEFAULT == null ? header != null : !HEADER_EDEFAULT.equals(header);
			case LibraryElementPackage.COMPILER_INFO__PACKAGE_NAME:
				return PACKAGE_NAME_EDEFAULT == null ? packageName != null : !PACKAGE_NAME_EDEFAULT.equals(packageName);
			case LibraryElementPackage.COMPILER_INFO__IMPORTS:
				return imports != null && !imports.isEmpty();
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
		result.append(" (classdef: "); //$NON-NLS-1$
		result.append(classdef);
		result.append(", header: "); //$NON-NLS-1$
		result.append(header);
		result.append(", packageName: "); //$NON-NLS-1$
		result.append(packageName);
		result.append(')');
		return result.toString();
	}

} //CompilerInfoImpl
