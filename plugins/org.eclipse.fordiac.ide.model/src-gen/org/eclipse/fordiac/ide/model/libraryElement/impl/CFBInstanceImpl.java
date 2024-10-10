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

import java.util.stream.Stream;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>CFB Instance</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.CFBInstanceImpl#getCfbNetwork <em>Cfb Network</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CFBInstanceImpl extends FBImpl implements CFBInstance {
	/**
	 * The cached value of the '{@link #getCfbNetwork() <em>Cfb Network</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCfbNetwork()
	 * @generated
	 * @ordered
	 */
	protected FBNetwork cfbNetwork;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CFBInstanceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.CFB_INSTANCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FBNetwork getCfbNetwork() {
		if (cfbNetwork != null && cfbNetwork.eIsProxy()) {
			InternalEObject oldCfbNetwork = (InternalEObject)cfbNetwork;
			cfbNetwork = (FBNetwork)eResolveProxy(oldCfbNetwork);
			if (cfbNetwork != oldCfbNetwork) {
				InternalEObject newCfbNetwork = (InternalEObject)cfbNetwork;
				NotificationChain msgs = oldCfbNetwork.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.CFB_INSTANCE__CFB_NETWORK, null, null);
				if (newCfbNetwork.eInternalContainer() == null) {
					msgs = newCfbNetwork.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.CFB_INSTANCE__CFB_NETWORK, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.CFB_INSTANCE__CFB_NETWORK, oldCfbNetwork, cfbNetwork));
			}
		}
		return cfbNetwork;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FBNetwork basicGetCfbNetwork() {
		return cfbNetwork;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCfbNetwork(FBNetwork newCfbNetwork, NotificationChain msgs) {
		FBNetwork oldCfbNetwork = cfbNetwork;
		cfbNetwork = newCfbNetwork;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CFB_INSTANCE__CFB_NETWORK, oldCfbNetwork, newCfbNetwork);
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
	public void setCfbNetwork(FBNetwork newCfbNetwork) {
		if (newCfbNetwork != cfbNetwork) {
			NotificationChain msgs = null;
			if (cfbNetwork != null)
				msgs = ((InternalEObject)cfbNetwork).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.CFB_INSTANCE__CFB_NETWORK, null, msgs);
			if (newCfbNetwork != null)
				msgs = ((InternalEObject)newCfbNetwork).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.CFB_INSTANCE__CFB_NETWORK, null, msgs);
			msgs = basicSetCfbNetwork(newCfbNetwork, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.CFB_INSTANCE__CFB_NETWORK, newCfbNetwork, newCfbNetwork));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FBNetwork loadCFBNetwork() {
		return org.eclipse.fordiac.ide.model.annotations.FBAnnotations.loadCFBNetwork(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CompositeFBType getType() {
		//this cannot be moved to the annotation class because there we don't have the super access!!!
		org.eclipse.fordiac.ide.model.libraryElement.LibraryElement type = super.getType();
		if(type instanceof CompositeFBType){
		   return (CompositeFBType) type; 
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Stream<INamedElement> findBySimpleName(final String name) {
		return org.eclipse.fordiac.ide.model.annotations.FBAnnotations.findBySimpleName(this, name);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.CFB_INSTANCE__CFB_NETWORK:
				return basicSetCfbNetwork(null, msgs);
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
			case LibraryElementPackage.CFB_INSTANCE__CFB_NETWORK:
				if (resolve) return getCfbNetwork();
				return basicGetCfbNetwork();
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
			case LibraryElementPackage.CFB_INSTANCE__CFB_NETWORK:
				setCfbNetwork((FBNetwork)newValue);
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
			case LibraryElementPackage.CFB_INSTANCE__CFB_NETWORK:
				setCfbNetwork((FBNetwork)null);
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
			case LibraryElementPackage.CFB_INSTANCE__CFB_NETWORK:
				return cfbNetwork != null;
			default:
				return super.eIsSet(featureID);
		}
	}

} //CFBInstanceImpl
