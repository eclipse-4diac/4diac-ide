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

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Method;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Base FB Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.BaseFBTypeImpl#getInternalVars <em>Internal Vars</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.BaseFBTypeImpl#getInternalFbs <em>Internal Fbs</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.BaseFBTypeImpl#getAlgorithm <em>Algorithm</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.BaseFBTypeImpl#getMethods <em>Methods</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.BaseFBTypeImpl#getCallables <em>Callables</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BaseFBTypeImpl extends FBTypeImpl implements BaseFBType {
	/**
	 * The cached value of the '{@link #getInternalVars() <em>Internal Vars</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInternalVars()
	 * @generated
	 * @ordered
	 */
	protected EList<VarDeclaration> internalVars;

	/**
	 * The cached value of the '{@link #getInternalFbs() <em>Internal Fbs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInternalFbs()
	 * @generated
	 * @ordered
	 */
	protected EList<FB> internalFbs;

	/**
	 * The cached value of the '{@link #getCallables() <em>Callables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCallables()
	 * @generated
	 * @ordered
	 */
	protected EList<ICallable> callables;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BaseFBTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.BASE_FB_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VarDeclaration> getInternalVars() {
		if (internalVars == null) {
			internalVars = new EObjectContainmentEList<VarDeclaration>(VarDeclaration.class, this, LibraryElementPackage.BASE_FB_TYPE__INTERNAL_VARS);
		}
		return internalVars;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<FB> getInternalFbs() {
		if (internalFbs == null) {
			internalFbs = new EObjectContainmentEList<FB>(FB.class, this, LibraryElementPackage.BASE_FB_TYPE__INTERNAL_FBS);
		}
		return internalFbs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Algorithm> getAlgorithm() {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.BaseFBTypeAnnotations.getAlgorithm(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Method> getMethods() {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.BaseFBTypeAnnotations.getMethods(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ICallable> getCallables() {
		if (callables == null) {
			callables = new EObjectContainmentEList<ICallable>(ICallable.class, this, LibraryElementPackage.BASE_FB_TYPE__CALLABLES);
		}
		return callables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Algorithm getAlgorithmNamed(final String name) {
		return org.eclipse.fordiac.ide.model.Annotations.getAlgorithmNamed(this, name);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.BASE_FB_TYPE__INTERNAL_VARS:
				return ((InternalEList<?>)getInternalVars()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.BASE_FB_TYPE__INTERNAL_FBS:
				return ((InternalEList<?>)getInternalFbs()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.BASE_FB_TYPE__CALLABLES:
				return ((InternalEList<?>)getCallables()).basicRemove(otherEnd, msgs);
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
			case LibraryElementPackage.BASE_FB_TYPE__INTERNAL_VARS:
				return getInternalVars();
			case LibraryElementPackage.BASE_FB_TYPE__INTERNAL_FBS:
				return getInternalFbs();
			case LibraryElementPackage.BASE_FB_TYPE__ALGORITHM:
				return getAlgorithm();
			case LibraryElementPackage.BASE_FB_TYPE__METHODS:
				return getMethods();
			case LibraryElementPackage.BASE_FB_TYPE__CALLABLES:
				return getCallables();
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
			case LibraryElementPackage.BASE_FB_TYPE__INTERNAL_VARS:
				getInternalVars().clear();
				getInternalVars().addAll((Collection<? extends VarDeclaration>)newValue);
				return;
			case LibraryElementPackage.BASE_FB_TYPE__INTERNAL_FBS:
				getInternalFbs().clear();
				getInternalFbs().addAll((Collection<? extends FB>)newValue);
				return;
			case LibraryElementPackage.BASE_FB_TYPE__CALLABLES:
				getCallables().clear();
				getCallables().addAll((Collection<? extends ICallable>)newValue);
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
			case LibraryElementPackage.BASE_FB_TYPE__INTERNAL_VARS:
				getInternalVars().clear();
				return;
			case LibraryElementPackage.BASE_FB_TYPE__INTERNAL_FBS:
				getInternalFbs().clear();
				return;
			case LibraryElementPackage.BASE_FB_TYPE__CALLABLES:
				getCallables().clear();
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
			case LibraryElementPackage.BASE_FB_TYPE__INTERNAL_VARS:
				return internalVars != null && !internalVars.isEmpty();
			case LibraryElementPackage.BASE_FB_TYPE__INTERNAL_FBS:
				return internalFbs != null && !internalFbs.isEmpty();
			case LibraryElementPackage.BASE_FB_TYPE__ALGORITHM:
				return !getAlgorithm().isEmpty();
			case LibraryElementPackage.BASE_FB_TYPE__METHODS:
				return !getMethods().isEmpty();
			case LibraryElementPackage.BASE_FB_TYPE__CALLABLES:
				return callables != null && !callables.isEmpty();
			default:
				return super.eIsSet(featureID);
		}
	}

} //BaseFBTypeImpl
