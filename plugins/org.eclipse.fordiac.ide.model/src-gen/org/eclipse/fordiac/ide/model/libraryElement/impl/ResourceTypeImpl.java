/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Resource Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceTypeImpl#getVarDeclaration <em>Var Declaration</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceTypeImpl#getFBNetwork <em>FB Network</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceTypeImpl#getSupportedFBTypes <em>Supported FB Types</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ResourceTypeImpl extends CompilableTypeImpl implements ResourceType {
	/**
	 * The cached value of the '{@link #getVarDeclaration() <em>Var Declaration</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVarDeclaration()
	 * @generated
	 * @ordered
	 */
	protected EList<VarDeclaration> varDeclaration;

	/**
	 * The cached value of the '{@link #getFBNetwork() <em>FB Network</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFBNetwork()
	 * @generated
	 * @ordered
	 */
	protected FBNetwork fBNetwork;

	/**
	 * The cached value of the '{@link #getSupportedFBTypes() <em>Supported FB Types</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSupportedFBTypes()
	 * @generated
	 * @ordered
	 */
	protected FBType supportedFBTypes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ResourceTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.RESOURCE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VarDeclaration> getVarDeclaration() {
		if (varDeclaration == null) {
			varDeclaration = new EObjectContainmentEList<VarDeclaration>(VarDeclaration.class, this, LibraryElementPackage.RESOURCE_TYPE__VAR_DECLARATION);
		}
		return varDeclaration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FBNetwork getFBNetwork() {
		return fBNetwork;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFBNetwork(FBNetwork newFBNetwork, NotificationChain msgs) {
		FBNetwork oldFBNetwork = fBNetwork;
		fBNetwork = newFBNetwork;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryElementPackage.RESOURCE_TYPE__FB_NETWORK, oldFBNetwork, newFBNetwork);
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
	public void setFBNetwork(FBNetwork newFBNetwork) {
		if (newFBNetwork != fBNetwork) {
			NotificationChain msgs = null;
			if (fBNetwork != null)
				msgs = ((InternalEObject)fBNetwork).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.RESOURCE_TYPE__FB_NETWORK, null, msgs);
			if (newFBNetwork != null)
				msgs = ((InternalEObject)newFBNetwork).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.RESOURCE_TYPE__FB_NETWORK, null, msgs);
			msgs = basicSetFBNetwork(newFBNetwork, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.RESOURCE_TYPE__FB_NETWORK, newFBNetwork, newFBNetwork));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FBType getSupportedFBTypes() {
		if (supportedFBTypes != null && supportedFBTypes.eIsProxy()) {
			InternalEObject oldSupportedFBTypes = (InternalEObject)supportedFBTypes;
			supportedFBTypes = (FBType)eResolveProxy(oldSupportedFBTypes);
			if (supportedFBTypes != oldSupportedFBTypes) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.RESOURCE_TYPE__SUPPORTED_FB_TYPES, oldSupportedFBTypes, supportedFBTypes));
			}
		}
		return supportedFBTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FBType basicGetSupportedFBTypes() {
		return supportedFBTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSupportedFBTypes(FBType newSupportedFBTypes) {
		FBType oldSupportedFBTypes = supportedFBTypes;
		supportedFBTypes = newSupportedFBTypes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.RESOURCE_TYPE__SUPPORTED_FB_TYPES, oldSupportedFBTypes, supportedFBTypes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.RESOURCE_TYPE__VAR_DECLARATION:
				return ((InternalEList<?>)getVarDeclaration()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.RESOURCE_TYPE__FB_NETWORK:
				return basicSetFBNetwork(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LibraryElementPackage.RESOURCE_TYPE__VAR_DECLARATION:
				return getVarDeclaration();
			case LibraryElementPackage.RESOURCE_TYPE__FB_NETWORK:
				return getFBNetwork();
			case LibraryElementPackage.RESOURCE_TYPE__SUPPORTED_FB_TYPES:
				if (resolve) return getSupportedFBTypes();
				return basicGetSupportedFBTypes();
		}
		return super.eGet(featureID, resolve, coreType);
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
			case LibraryElementPackage.RESOURCE_TYPE__VAR_DECLARATION:
				getVarDeclaration().clear();
				getVarDeclaration().addAll((Collection<? extends VarDeclaration>)newValue);
				return;
			case LibraryElementPackage.RESOURCE_TYPE__FB_NETWORK:
				setFBNetwork((FBNetwork)newValue);
				return;
			case LibraryElementPackage.RESOURCE_TYPE__SUPPORTED_FB_TYPES:
				setSupportedFBTypes((FBType)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case LibraryElementPackage.RESOURCE_TYPE__VAR_DECLARATION:
				getVarDeclaration().clear();
				return;
			case LibraryElementPackage.RESOURCE_TYPE__FB_NETWORK:
				setFBNetwork((FBNetwork)null);
				return;
			case LibraryElementPackage.RESOURCE_TYPE__SUPPORTED_FB_TYPES:
				setSupportedFBTypes((FBType)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case LibraryElementPackage.RESOURCE_TYPE__VAR_DECLARATION:
				return varDeclaration != null && !varDeclaration.isEmpty();
			case LibraryElementPackage.RESOURCE_TYPE__FB_NETWORK:
				return fBNetwork != null;
			case LibraryElementPackage.RESOURCE_TYPE__SUPPORTED_FB_TYPES:
				return supportedFBTypes != null;
		}
		return super.eIsSet(featureID);
	}

} //ResourceTypeImpl
