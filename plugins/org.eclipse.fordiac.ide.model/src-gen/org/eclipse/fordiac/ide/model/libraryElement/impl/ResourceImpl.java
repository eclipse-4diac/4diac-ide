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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IVarElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Resource</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceImpl#getVarDeclarations <em>Var Declarations</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceImpl#getFBNetwork <em>FB Network</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceImpl#getX <em>X</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceImpl#getY <em>Y</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceImpl#getDevice <em>Device</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceImpl#isDeviceTypeResource <em>Device Type Resource</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ResourceImpl extends TypedConfigureableObjectImpl implements Resource {
	/**
	 * The cached value of the '{@link #getVarDeclarations() <em>Var Declarations</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getVarDeclarations()
	 * @generated
	 * @ordered
	 */
	protected EList<VarDeclaration> varDeclarations;

	/**
	 * The cached value of the '{@link #getFBNetwork() <em>FB Network</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getFBNetwork()
	 * @generated
	 * @ordered
	 */
	protected FBNetwork fBNetwork;

	/**
	 * The default value of the '{@link #getX() <em>X</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getX()
	 * @generated
	 * @ordered
	 */
	protected static final String X_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getX() <em>X</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getX()
	 * @generated
	 * @ordered
	 */
	protected String x = X_EDEFAULT;

	/**
	 * The default value of the '{@link #getY() <em>Y</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getY()
	 * @generated
	 * @ordered
	 */
	protected static final String Y_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getY() <em>Y</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getY()
	 * @generated
	 * @ordered
	 */
	protected String y = Y_EDEFAULT;

	/**
	 * The default value of the '{@link #isDeviceTypeResource() <em>Device Type Resource</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isDeviceTypeResource()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DEVICE_TYPE_RESOURCE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeviceTypeResource() <em>Device Type Resource</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isDeviceTypeResource()
	 * @generated
	 * @ordered
	 */
	protected boolean deviceTypeResource = DEVICE_TYPE_RESOURCE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ResourceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.RESOURCE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryElementPackage.RESOURCE__FB_NETWORK, oldFBNetwork, newFBNetwork);
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
				msgs = ((InternalEObject)fBNetwork).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.RESOURCE__FB_NETWORK, null, msgs);
			if (newFBNetwork != null)
				msgs = ((InternalEObject)newFBNetwork).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.RESOURCE__FB_NETWORK, null, msgs);
			msgs = basicSetFBNetwork(newFBNetwork, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.RESOURCE__FB_NETWORK, newFBNetwork, newFBNetwork));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getX() {
		return x;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setX(String newX) {
		String oldX = x;
		x = newX;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.RESOURCE__X, oldX, x));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getY() {
		return y;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setY(String newY) {
		String oldY = y;
		y = newY;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.RESOURCE__Y, oldY, y));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Device getDevice() {
		if (eContainerFeatureID() != LibraryElementPackage.RESOURCE__DEVICE) return null;
		return (Device)eContainer();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Device basicGetDevice() {
		if (eContainerFeatureID() != LibraryElementPackage.RESOURCE__DEVICE) return null;
		return (Device)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDevice(Device newDevice, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newDevice, LibraryElementPackage.RESOURCE__DEVICE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDevice(Device newDevice) {
		if (newDevice != eInternalContainer() || (eContainerFeatureID() != LibraryElementPackage.RESOURCE__DEVICE && newDevice != null)) {
			if (EcoreUtil.isAncestor(this, newDevice))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newDevice != null)
				msgs = ((InternalEObject)newDevice).eInverseAdd(this, LibraryElementPackage.DEVICE__RESOURCE, Device.class, msgs);
			msgs = basicSetDevice(newDevice, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.RESOURCE__DEVICE, newDevice, newDevice));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDeviceTypeResource() {
		return deviceTypeResource;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeviceTypeResource(boolean newDeviceTypeResource) {
		boolean oldDeviceTypeResource = deviceTypeResource;
		deviceTypeResource = newDeviceTypeResource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.RESOURCE__DEVICE_TYPE_RESOURCE, oldDeviceTypeResource, deviceTypeResource));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VarDeclaration> getVarDeclarations() {
		if (varDeclarations == null) {
			varDeclarations = new EObjectContainmentEList.Resolving<VarDeclaration>(VarDeclaration.class, this, LibraryElementPackage.RESOURCE__VAR_DECLARATIONS);
		}
		return varDeclarations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AutomationSystem getAutomationSystem() {
		return org.eclipse.fordiac.ide.model.Annotations.getAutomationSystem(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceType getType() {
		//this cannot be moved to the annotation class because there we don't have the super access!!!
		org.eclipse.fordiac.ide.model.libraryElement.LibraryElement type = super.getType();
		if(type instanceof ResourceType){
			return (ResourceType) type; 
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.RESOURCE__DEVICE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetDevice((Device)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.RESOURCE__VAR_DECLARATIONS:
				return ((InternalEList<?>)getVarDeclarations()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.RESOURCE__FB_NETWORK:
				return basicSetFBNetwork(null, msgs);
			case LibraryElementPackage.RESOURCE__DEVICE:
				return basicSetDevice(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(
	NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case LibraryElementPackage.RESOURCE__DEVICE:
				return eInternalContainer().eInverseRemove(this, LibraryElementPackage.DEVICE__RESOURCE, Device.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LibraryElementPackage.RESOURCE__VAR_DECLARATIONS:
				return getVarDeclarations();
			case LibraryElementPackage.RESOURCE__FB_NETWORK:
				return getFBNetwork();
			case LibraryElementPackage.RESOURCE__X:
				return getX();
			case LibraryElementPackage.RESOURCE__Y:
				return getY();
			case LibraryElementPackage.RESOURCE__DEVICE:
				if (resolve) return getDevice();
				return basicGetDevice();
			case LibraryElementPackage.RESOURCE__DEVICE_TYPE_RESOURCE:
				return isDeviceTypeResource();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case LibraryElementPackage.RESOURCE__VAR_DECLARATIONS:
				getVarDeclarations().clear();
				getVarDeclarations().addAll((Collection<? extends VarDeclaration>)newValue);
				return;
			case LibraryElementPackage.RESOURCE__FB_NETWORK:
				setFBNetwork((FBNetwork)newValue);
				return;
			case LibraryElementPackage.RESOURCE__X:
				setX((String)newValue);
				return;
			case LibraryElementPackage.RESOURCE__Y:
				setY((String)newValue);
				return;
			case LibraryElementPackage.RESOURCE__DEVICE:
				setDevice((Device)newValue);
				return;
			case LibraryElementPackage.RESOURCE__DEVICE_TYPE_RESOURCE:
				setDeviceTypeResource((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case LibraryElementPackage.RESOURCE__VAR_DECLARATIONS:
				getVarDeclarations().clear();
				return;
			case LibraryElementPackage.RESOURCE__FB_NETWORK:
				setFBNetwork((FBNetwork)null);
				return;
			case LibraryElementPackage.RESOURCE__X:
				setX(X_EDEFAULT);
				return;
			case LibraryElementPackage.RESOURCE__Y:
				setY(Y_EDEFAULT);
				return;
			case LibraryElementPackage.RESOURCE__DEVICE:
				setDevice((Device)null);
				return;
			case LibraryElementPackage.RESOURCE__DEVICE_TYPE_RESOURCE:
				setDeviceTypeResource(DEVICE_TYPE_RESOURCE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case LibraryElementPackage.RESOURCE__VAR_DECLARATIONS:
				return varDeclarations != null && !varDeclarations.isEmpty();
			case LibraryElementPackage.RESOURCE__FB_NETWORK:
				return fBNetwork != null;
			case LibraryElementPackage.RESOURCE__X:
				return X_EDEFAULT == null ? x != null : !X_EDEFAULT.equals(x);
			case LibraryElementPackage.RESOURCE__Y:
				return Y_EDEFAULT == null ? y != null : !Y_EDEFAULT.equals(y);
			case LibraryElementPackage.RESOURCE__DEVICE:
				return basicGetDevice() != null;
			case LibraryElementPackage.RESOURCE__DEVICE_TYPE_RESOURCE:
				return deviceTypeResource != DEVICE_TYPE_RESOURCE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == IVarElement.class) {
			switch (derivedFeatureID) {
				case LibraryElementPackage.RESOURCE__VAR_DECLARATIONS: return LibraryElementPackage.IVAR_ELEMENT__VAR_DECLARATIONS;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == IVarElement.class) {
			switch (baseFeatureID) {
				case LibraryElementPackage.IVAR_ELEMENT__VAR_DECLARATIONS: return LibraryElementPackage.RESOURCE__VAR_DECLARATIONS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (x: "); //$NON-NLS-1$
		result.append(x);
		result.append(", y: "); //$NON-NLS-1$
		result.append(y);
		result.append(", deviceTypeResource: "); //$NON-NLS-1$
		result.append(deviceTypeResource);
		result.append(')');
		return result.toString();
	}

	@Override
	public void setName(final String newName) {
		if (newName.equals(name)) {
			return;
		}
		String oldName = name;
		
		name = newName;
		
		NameRepository.checkNameIdentifier(this);
		
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					LibraryElementPackage.CONFIGURABLE_OBJECT__NAME, oldName, name));
		}
	}

} // ResourceImpl
