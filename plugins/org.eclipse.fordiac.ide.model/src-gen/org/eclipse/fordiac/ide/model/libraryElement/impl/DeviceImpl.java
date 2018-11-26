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
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Color;
import org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.DeviceType;
import org.eclipse.fordiac.ide.model.libraryElement.IVarElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Link;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Device</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.DeviceImpl#getX <em>X</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.DeviceImpl#getY <em>Y</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.DeviceImpl#getColor <em>Color</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.DeviceImpl#getVarDeclarations <em>Var Declarations</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.DeviceImpl#getResource <em>Resource</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.DeviceImpl#getProfile <em>Profile</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.DeviceImpl#getInConnections <em>In Connections</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DeviceImpl extends TypedConfigureableObjectImpl implements Device {
	/**
	 * The default value of the '{@link #getX() <em>X</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getX()
	 * @generated
	 * @ordered
	 */
	protected static final int X_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getX() <em>X</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getX()
	 * @generated
	 * @ordered
	 */
	protected int x = X_EDEFAULT;

	/**
	 * The default value of the '{@link #getY() <em>Y</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getY()
	 * @generated
	 * @ordered
	 */
	protected static final int Y_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getY() <em>Y</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getY()
	 * @generated
	 * @ordered
	 */
	protected int y = Y_EDEFAULT;

	/**
	 * The cached value of the '{@link #getColor() <em>Color</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColor()
	 * @generated
	 * @ordered
	 */
	protected Color color;

	/**
	 * The cached value of the '{@link #getVarDeclarations() <em>Var Declarations</em>}' containment reference list.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see #getVarDeclarations()
	 * @generated
	 * @ordered
	 */
	protected EList<VarDeclaration> varDeclarations;

	/**
	 * The cached value of the '{@link #getResource() <em>Resource</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getResource()
	 * @generated
	 * @ordered
	 */
	protected EList<Resource> resource;

	/**
	 * The default value of the '{@link #getProfile() <em>Profile</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getProfile()
	 * @generated
	 * @ordered
	 */
	protected static final String PROFILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProfile() <em>Profile</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getProfile()
	 * @generated
	 * @ordered
	 */
	protected String profile = PROFILE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getInConnections() <em>In Connections</em>}' reference list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getInConnections()
	 * @generated
	 * @ordered
	 */
	protected EList<Link> inConnections;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected DeviceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.DEVICE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Resource> getResource() {
		if (resource == null) {
			resource = new EObjectContainmentWithInverseEList<Resource>(Resource.class, this, LibraryElementPackage.DEVICE__RESOURCE, LibraryElementPackage.RESOURCE__DEVICE);
		}
		return resource;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getX() {
		return x;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setX(int newX) {
		int oldX = x;
		x = newX;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.DEVICE__X, oldX, x));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getY() {
		return y;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setY(int newY) {
		int oldY = y;
		y = newY;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.DEVICE__Y, oldY, y));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Color getColor() {
		if (color != null && color.eIsProxy()) {
			InternalEObject oldColor = (InternalEObject)color;
			color = (Color)eResolveProxy(oldColor);
			if (color != oldColor) {
				InternalEObject newColor = (InternalEObject)color;
				NotificationChain msgs = oldColor.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.DEVICE__COLOR, null, null);
				if (newColor.eInternalContainer() == null) {
					msgs = newColor.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.DEVICE__COLOR, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.DEVICE__COLOR, oldColor, color));
			}
		}
		return color;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Color basicGetColor() {
		return color;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetColor(Color newColor, NotificationChain msgs) {
		Color oldColor = color;
		color = newColor;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryElementPackage.DEVICE__COLOR, oldColor, newColor);
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
	public void setColor(Color newColor) {
		if (newColor != color) {
			NotificationChain msgs = null;
			if (color != null)
				msgs = ((InternalEObject)color).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.DEVICE__COLOR, null, msgs);
			if (newColor != null)
				msgs = ((InternalEObject)newColor).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.DEVICE__COLOR, null, msgs);
			msgs = basicSetColor(newColor, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.DEVICE__COLOR, newColor, newColor));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VarDeclaration> getVarDeclarations() {
		if (varDeclarations == null) {
			varDeclarations = new EObjectContainmentEList.Resolving<VarDeclaration>(VarDeclaration.class, this, LibraryElementPackage.DEVICE__VAR_DECLARATIONS);
		}
		return varDeclarations;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getProfile() {
		return profile;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setProfile(String newProfile) {
		String oldProfile = profile;
		profile = newProfile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.DEVICE__PROFILE, oldProfile, profile));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Link> getInConnections() {
		if (inConnections == null) {
			inConnections = new EObjectWithInverseResolvingEList<Link>(Link.class, this, LibraryElementPackage.DEVICE__IN_CONNECTIONS, LibraryElementPackage.LINK__DEVICE);
		}
		return inConnections;
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
	public SystemConfiguration getSystemConfiguration() {
		return org.eclipse.fordiac.ide.model.Annotations.getSystemConfiguration(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DeviceType getType() {
		//this cannot be moved to the annotation class because there we don't have the super access!!!
		org.eclipse.fordiac.ide.model.libraryElement.LibraryElement type = super.getType();
		if(type instanceof DeviceType){
			return (DeviceType) type; 
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Resource getResourceNamed(final String name) {
		return org.eclipse.fordiac.ide.model.Annotations.getResourceNamed(this, name);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Color getColorGen() {
		if (color != null && color.eIsProxy()) {
			InternalEObject oldColor = (InternalEObject)color;
			color = (Color)eResolveProxy(oldColor);
			if (color != oldColor) {
				InternalEObject newColor = (InternalEObject)color;
				NotificationChain msgs = oldColor.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.DEVICE__COLOR, null, null);
				if (newColor.eInternalContainer() == null) {
					msgs = newColor.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.DEVICE__COLOR, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.DEVICE__COLOR, oldColor, color));
			}
		}
		return color;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID,
			NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.DEVICE__RESOURCE:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getResource()).basicAdd(otherEnd, msgs);
			case LibraryElementPackage.DEVICE__IN_CONNECTIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getInConnections()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.DEVICE__COLOR:
				return basicSetColor(null, msgs);
			case LibraryElementPackage.DEVICE__VAR_DECLARATIONS:
				return ((InternalEList<?>)getVarDeclarations()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.DEVICE__RESOURCE:
				return ((InternalEList<?>)getResource()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.DEVICE__IN_CONNECTIONS:
				return ((InternalEList<?>)getInConnections()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LibraryElementPackage.DEVICE__X:
				return getX();
			case LibraryElementPackage.DEVICE__Y:
				return getY();
			case LibraryElementPackage.DEVICE__COLOR:
				if (resolve) return getColor();
				return basicGetColor();
			case LibraryElementPackage.DEVICE__VAR_DECLARATIONS:
				return getVarDeclarations();
			case LibraryElementPackage.DEVICE__RESOURCE:
				return getResource();
			case LibraryElementPackage.DEVICE__PROFILE:
				return getProfile();
			case LibraryElementPackage.DEVICE__IN_CONNECTIONS:
				return getInConnections();
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
			case LibraryElementPackage.DEVICE__X:
				setX((Integer)newValue);
				return;
			case LibraryElementPackage.DEVICE__Y:
				setY((Integer)newValue);
				return;
			case LibraryElementPackage.DEVICE__COLOR:
				setColor((Color)newValue);
				return;
			case LibraryElementPackage.DEVICE__VAR_DECLARATIONS:
				getVarDeclarations().clear();
				getVarDeclarations().addAll((Collection<? extends VarDeclaration>)newValue);
				return;
			case LibraryElementPackage.DEVICE__RESOURCE:
				getResource().clear();
				getResource().addAll((Collection<? extends Resource>)newValue);
				return;
			case LibraryElementPackage.DEVICE__PROFILE:
				setProfile((String)newValue);
				return;
			case LibraryElementPackage.DEVICE__IN_CONNECTIONS:
				getInConnections().clear();
				getInConnections().addAll((Collection<? extends Link>)newValue);
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
			case LibraryElementPackage.DEVICE__X:
				setX(X_EDEFAULT);
				return;
			case LibraryElementPackage.DEVICE__Y:
				setY(Y_EDEFAULT);
				return;
			case LibraryElementPackage.DEVICE__COLOR:
				setColor((Color)null);
				return;
			case LibraryElementPackage.DEVICE__VAR_DECLARATIONS:
				getVarDeclarations().clear();
				return;
			case LibraryElementPackage.DEVICE__RESOURCE:
				getResource().clear();
				return;
			case LibraryElementPackage.DEVICE__PROFILE:
				setProfile(PROFILE_EDEFAULT);
				return;
			case LibraryElementPackage.DEVICE__IN_CONNECTIONS:
				getInConnections().clear();
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
			case LibraryElementPackage.DEVICE__X:
				return x != X_EDEFAULT;
			case LibraryElementPackage.DEVICE__Y:
				return y != Y_EDEFAULT;
			case LibraryElementPackage.DEVICE__COLOR:
				return color != null;
			case LibraryElementPackage.DEVICE__VAR_DECLARATIONS:
				return varDeclarations != null && !varDeclarations.isEmpty();
			case LibraryElementPackage.DEVICE__RESOURCE:
				return resource != null && !resource.isEmpty();
			case LibraryElementPackage.DEVICE__PROFILE:
				return PROFILE_EDEFAULT == null ? profile != null : !PROFILE_EDEFAULT.equals(profile);
			case LibraryElementPackage.DEVICE__IN_CONNECTIONS:
				return inConnections != null && !inConnections.isEmpty();
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
		if (baseClass == PositionableElement.class) {
			switch (derivedFeatureID) {
				case LibraryElementPackage.DEVICE__X: return LibraryElementPackage.POSITIONABLE_ELEMENT__X;
				case LibraryElementPackage.DEVICE__Y: return LibraryElementPackage.POSITIONABLE_ELEMENT__Y;
				default: return -1;
			}
		}
		if (baseClass == ColorizableElement.class) {
			switch (derivedFeatureID) {
				case LibraryElementPackage.DEVICE__COLOR: return LibraryElementPackage.COLORIZABLE_ELEMENT__COLOR;
				default: return -1;
			}
		}
		if (baseClass == IVarElement.class) {
			switch (derivedFeatureID) {
				case LibraryElementPackage.DEVICE__VAR_DECLARATIONS: return LibraryElementPackage.IVAR_ELEMENT__VAR_DECLARATIONS;
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
		if (baseClass == PositionableElement.class) {
			switch (baseFeatureID) {
				case LibraryElementPackage.POSITIONABLE_ELEMENT__X: return LibraryElementPackage.DEVICE__X;
				case LibraryElementPackage.POSITIONABLE_ELEMENT__Y: return LibraryElementPackage.DEVICE__Y;
				default: return -1;
			}
		}
		if (baseClass == ColorizableElement.class) {
			switch (baseFeatureID) {
				case LibraryElementPackage.COLORIZABLE_ELEMENT__COLOR: return LibraryElementPackage.DEVICE__COLOR;
				default: return -1;
			}
		}
		if (baseClass == IVarElement.class) {
			switch (baseFeatureID) {
				case LibraryElementPackage.IVAR_ELEMENT__VAR_DECLARATIONS: return LibraryElementPackage.DEVICE__VAR_DECLARATIONS;
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
		result.append(", profile: "); //$NON-NLS-1$
		result.append(profile);
		result.append(')');
		return result.toString();
	}

	@Override
	public void setName(String newName) {
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
} // DeviceImpl
