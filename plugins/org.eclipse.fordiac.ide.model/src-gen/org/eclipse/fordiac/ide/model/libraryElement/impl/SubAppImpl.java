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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Sub App</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SubAppImpl#getSubAppNetwork <em>Sub App Network</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SubAppImpl#getWidth <em>Width</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SubAppImpl#getHeight <em>Height</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SubAppImpl extends FBNetworkElementImpl implements SubApp {
	/**
	 * The cached value of the '{@link #getSubAppNetwork() <em>Sub App Network</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubAppNetwork()
	 * @generated
	 * @ordered
	 */
	protected FBNetwork subAppNetwork;

	/**
	 * The default value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected static final int WIDTH_EDEFAULT = 200;

	/**
	 * The cached value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected int width = WIDTH_EDEFAULT;

	/**
	 * The default value of the '{@link #getHeight() <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeight()
	 * @generated
	 * @ordered
	 */
	protected static final int HEIGHT_EDEFAULT = 100;

	/**
	 * The cached value of the '{@link #getHeight() <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeight()
	 * @generated
	 * @ordered
	 */
	protected int height = HEIGHT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SubAppImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.SUB_APP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FBNetwork getSubAppNetwork() {
		if (subAppNetwork != null && subAppNetwork.eIsProxy()) {
			InternalEObject oldSubAppNetwork = (InternalEObject)subAppNetwork;
			subAppNetwork = (FBNetwork)eResolveProxy(oldSubAppNetwork);
			if (subAppNetwork != oldSubAppNetwork) {
				InternalEObject newSubAppNetwork = (InternalEObject)subAppNetwork;
				NotificationChain msgs = oldSubAppNetwork.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.SUB_APP__SUB_APP_NETWORK, null, null);
				if (newSubAppNetwork.eInternalContainer() == null) {
					msgs = newSubAppNetwork.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.SUB_APP__SUB_APP_NETWORK, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.SUB_APP__SUB_APP_NETWORK, oldSubAppNetwork, subAppNetwork));
			}
		}
		return subAppNetwork;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FBNetwork basicGetSubAppNetwork() {
		return subAppNetwork;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSubAppNetwork(FBNetwork newSubAppNetwork, NotificationChain msgs) {
		FBNetwork oldSubAppNetwork = subAppNetwork;
		subAppNetwork = newSubAppNetwork;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SUB_APP__SUB_APP_NETWORK, oldSubAppNetwork, newSubAppNetwork);
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
	public void setSubAppNetwork(FBNetwork newSubAppNetwork) {
		if (newSubAppNetwork != subAppNetwork) {
			NotificationChain msgs = null;
			if (subAppNetwork != null)
				msgs = ((InternalEObject)subAppNetwork).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.SUB_APP__SUB_APP_NETWORK, null, msgs);
			if (newSubAppNetwork != null)
				msgs = ((InternalEObject)newSubAppNetwork).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.SUB_APP__SUB_APP_NETWORK, null, msgs);
			msgs = basicSetSubAppNetwork(newSubAppNetwork, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SUB_APP__SUB_APP_NETWORK, newSubAppNetwork, newSubAppNetwork));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getWidth() {
		return width;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setWidth(int newWidth) {
		int oldWidth = width;
		width = newWidth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SUB_APP__WIDTH, oldWidth, width));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getHeight() {
		return height;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHeight(int newHeight) {
		int oldHeight = height;
		height = newHeight;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.SUB_APP__HEIGHT, oldHeight, height));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SubAppType getType() {
		//this cannot be moved to the annotation class because there we don't have the super access!!!
		org.eclipse.fordiac.ide.model.libraryElement.LibraryElement type = super.getType();
		if(type instanceof SubAppType){
		   return (SubAppType) type; 
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isUnfolded() {
		org.eclipse.fordiac.ide.model.libraryElement.Attribute attr = this.getAttribute(org.eclipse.fordiac.ide.model.LibraryElementTags.SUBAPP_REPRESENTATION_ATTRIBUTE);
		return (attr != null) && "true".equals(attr.getValue()); //$NON-NLS-1$
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isTyped() {
		return (getType() != null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FBNetwork loadSubAppNetwork() {
		return org.eclipse.fordiac.ide.model.annotations.SubAppAnnotations.loadSubAppNetwork(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getVisibleWidth() {
		return isUnfolded() ? getWidth() : org.eclipse.fordiac.ide.model.helpers.FBShapeHelper.getWidth(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getVisibleHeight() {
		return isUnfolded() ? getHeight() : org.eclipse.fordiac.ide.model.helpers.FBShapeHelper.getHeight(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.SUB_APP__SUB_APP_NETWORK:
				return basicSetSubAppNetwork(null, msgs);
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
			case LibraryElementPackage.SUB_APP__SUB_APP_NETWORK:
				if (resolve) return getSubAppNetwork();
				return basicGetSubAppNetwork();
			case LibraryElementPackage.SUB_APP__WIDTH:
				return getWidth();
			case LibraryElementPackage.SUB_APP__HEIGHT:
				return getHeight();
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
			case LibraryElementPackage.SUB_APP__SUB_APP_NETWORK:
				setSubAppNetwork((FBNetwork)newValue);
				return;
			case LibraryElementPackage.SUB_APP__WIDTH:
				setWidth((Integer)newValue);
				return;
			case LibraryElementPackage.SUB_APP__HEIGHT:
				setHeight((Integer)newValue);
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
			case LibraryElementPackage.SUB_APP__SUB_APP_NETWORK:
				setSubAppNetwork((FBNetwork)null);
				return;
			case LibraryElementPackage.SUB_APP__WIDTH:
				setWidth(WIDTH_EDEFAULT);
				return;
			case LibraryElementPackage.SUB_APP__HEIGHT:
				setHeight(HEIGHT_EDEFAULT);
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
			case LibraryElementPackage.SUB_APP__SUB_APP_NETWORK:
				return subAppNetwork != null;
			case LibraryElementPackage.SUB_APP__WIDTH:
				return width != WIDTH_EDEFAULT;
			case LibraryElementPackage.SUB_APP__HEIGHT:
				return height != HEIGHT_EDEFAULT;
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
		result.append(" (width: "); //$NON-NLS-1$
		result.append(width);
		result.append(", height: "); //$NON-NLS-1$
		result.append(height);
		result.append(')');
		return result.toString();
	}

} //SubAppImpl
