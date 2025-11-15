/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2022-2023 Martin Erich Jobst
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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Mapping;
import org.eclipse.fordiac.ide.model.typelibrary.InterfaceTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Block FB Network Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.BlockFBNetworkElementImpl#getInterface <em>Interface</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class BlockFBNetworkElementImpl extends FBNetworkElementImpl implements BlockFBNetworkElement {
	/**
	 * The cached value of the '{@link #getInterface() <em>Interface</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInterface()
	 * @generated
	 * @ordered
	 */
	protected InterfaceList interface_;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BlockFBNetworkElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.BLOCK_FB_NETWORK_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InterfaceList getInterface() {
		if (interface_ != null && interface_.eIsProxy()) {
			InternalEObject oldInterface = (InternalEObject)interface_;
			interface_ = (InterfaceList)eResolveProxy(oldInterface);
			if (interface_ != oldInterface) {
				InternalEObject newInterface = (InternalEObject)interface_;
				NotificationChain msgs =  oldInterface.eInverseRemove(this, LibraryElementPackage.INTERFACE_LIST__BLOCK_FB_NETWORK_ELEMENT, InterfaceList.class, null);
				if (newInterface.eInternalContainer() == null) {
					msgs =  newInterface.eInverseAdd(this, LibraryElementPackage.INTERFACE_LIST__BLOCK_FB_NETWORK_ELEMENT, InterfaceList.class, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.BLOCK_FB_NETWORK_ELEMENT__INTERFACE, oldInterface, interface_));
			}
		}
		return interface_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InterfaceList basicGetInterface() {
		return interface_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInterface(InterfaceList newInterface, NotificationChain msgs) {
		InterfaceList oldInterface = interface_;
		interface_ = newInterface;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryElementPackage.BLOCK_FB_NETWORK_ELEMENT__INTERFACE, oldInterface, newInterface);
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
	public void setInterface(InterfaceList newInterface) {
		if (newInterface != interface_) {
			NotificationChain msgs = null;
			if (interface_ != null)
				msgs = ((InternalEObject)interface_).eInverseRemove(this, LibraryElementPackage.INTERFACE_LIST__BLOCK_FB_NETWORK_ELEMENT, InterfaceList.class, msgs);
			if (newInterface != null)
				msgs = ((InternalEObject)newInterface).eInverseAdd(this, LibraryElementPackage.INTERFACE_LIST__BLOCK_FB_NETWORK_ELEMENT, InterfaceList.class, msgs);
			msgs = basicSetInterface(newInterface, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.BLOCK_FB_NETWORK_ELEMENT__INTERFACE, newInterface, newInterface));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IInterfaceElement getInterfaceElement(final String name) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.BlockFBNetworkElementAnnotations.getInterfaceElement(this, name);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InterfaceList getTypeInterface() {
		if(getTypeEntry() != null) {
			return getTypeEntry().getInterface();
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IInterfaceElement getInput(final String name) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.BlockFBNetworkElementAnnotations.getInput(this, name);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IInterfaceElement getOutput(final String name) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.BlockFBNetworkElementAnnotations.getOutput(this, name);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void checkConnections() {
		org.eclipse.fordiac.ide.model.libraryElement.impl.BlockFBNetworkElementAnnotations.checkConnections(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMapping(final Mapping newMapping) {
		super.setMapping(newMapping);
		checkConnections();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BlockFBNetworkElement getOpposite() {
		return (BlockFBNetworkElement)super.getOpposite();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InterfaceTypeEntry getTypeEntry() {
		if(super.getTypeEntry() instanceof final org.eclipse.fordiac.ide.model.typelibrary.InterfaceTypeEntry ifTypeEntry){
			return ifTypeEntry;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTypeEntry(final TypeEntry newTypeEntry) {
		if(!(newTypeEntry instanceof org.eclipse.fordiac.ide.model.typelibrary.InterfaceTypeEntry)){
			throw new IllegalArgumentException("Non InterfaceTypeEntry given in setTypeEntry of BlockFBNetworkElement");  //$NON-NLS-1$
		}
		super.setTypeEntry(newTypeEntry);
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IInterfaceElement getInterfaceElement(final IInterfaceElement refElement) {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.BlockFBNetworkElementAnnotations.getInterfaceElement(this, refElement);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.BLOCK_FB_NETWORK_ELEMENT__INTERFACE:
				if (interface_ != null)
					msgs = ((InternalEObject)interface_).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.BLOCK_FB_NETWORK_ELEMENT__INTERFACE, null, msgs);
				return basicSetInterface((InterfaceList)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.BLOCK_FB_NETWORK_ELEMENT__INTERFACE:
				return basicSetInterface(null, msgs);
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
			case LibraryElementPackage.BLOCK_FB_NETWORK_ELEMENT__INTERFACE:
				if (resolve) return getInterface();
				return basicGetInterface();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case LibraryElementPackage.BLOCK_FB_NETWORK_ELEMENT__INTERFACE:
				setInterface((InterfaceList)newValue);
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
			case LibraryElementPackage.BLOCK_FB_NETWORK_ELEMENT__INTERFACE:
				setInterface((InterfaceList)null);
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
			case LibraryElementPackage.BLOCK_FB_NETWORK_ELEMENT__INTERFACE:
				return interface_ != null;
		}
		return super.eIsSet(featureID);
	}

} //BlockFBNetworkElementImpl
