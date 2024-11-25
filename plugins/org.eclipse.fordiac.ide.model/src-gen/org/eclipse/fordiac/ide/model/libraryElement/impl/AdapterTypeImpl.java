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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.impl.DataTypeImpl;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Service;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Adapter Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterTypeImpl#getInterfaceList <em>Interface List</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterTypeImpl#getService <em>Service</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterTypeImpl#getPlugType <em>Plug Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AdapterTypeImpl extends DataTypeImpl implements AdapterType {
	/**
	 * The cached value of the '{@link #getInterfaceList() <em>Interface List</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInterfaceList()
	 * @generated
	 * @ordered
	 */
	protected InterfaceList interfaceList;

	/**
	 * The cached value of the '{@link #getService() <em>Service</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getService()
	 * @generated
	 * @ordered
	 */
	protected Service service;

	/**
	 * The cached value of the '{@link #getPlugType() <em>Plug Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlugType()
	 * @generated
	 * @ordered
	 */
	protected AdapterType plugType;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AdapterTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.ADAPTER_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InterfaceList getInterfaceList() {
		return interfaceList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInterfaceList(InterfaceList newInterfaceList, NotificationChain msgs) {
		InterfaceList oldInterfaceList = interfaceList;
		interfaceList = newInterfaceList;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ADAPTER_TYPE__INTERFACE_LIST, oldInterfaceList, newInterfaceList);
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
	public void setInterfaceList(InterfaceList newInterfaceList) {
		if (newInterfaceList != interfaceList) {
			NotificationChain msgs = null;
			if (interfaceList != null)
				msgs = ((InternalEObject)interfaceList).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.ADAPTER_TYPE__INTERFACE_LIST, null, msgs);
			if (newInterfaceList != null)
				msgs = ((InternalEObject)newInterfaceList).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.ADAPTER_TYPE__INTERFACE_LIST, null, msgs);
			msgs = basicSetInterfaceList(newInterfaceList, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ADAPTER_TYPE__INTERFACE_LIST, newInterfaceList, newInterfaceList));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Service getService() {
		return service;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetService(Service newService, NotificationChain msgs) {
		Service oldService = service;
		service = newService;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ADAPTER_TYPE__SERVICE, oldService, newService);
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
	public void setService(Service newService) {
		if (newService != service) {
			NotificationChain msgs = null;
			if (service != null)
				msgs = ((InternalEObject)service).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.ADAPTER_TYPE__SERVICE, null, msgs);
			if (newService != null)
				msgs = ((InternalEObject)newService).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.ADAPTER_TYPE__SERVICE, null, msgs);
			msgs = basicSetService(newService, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ADAPTER_TYPE__SERVICE, newService, newService));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AdapterType getPlugType() {
		if (plugType != null && plugType.eIsProxy()) {
			InternalEObject oldPlugType = (InternalEObject)plugType;
			plugType = (AdapterType)eResolveProxy(oldPlugType);
			if (plugType != oldPlugType) {
				InternalEObject newPlugType = (InternalEObject)plugType;
				NotificationChain msgs = oldPlugType.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.ADAPTER_TYPE__PLUG_TYPE, null, null);
				if (newPlugType.eInternalContainer() == null) {
					msgs = newPlugType.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.ADAPTER_TYPE__PLUG_TYPE, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LibraryElementPackage.ADAPTER_TYPE__PLUG_TYPE, oldPlugType, plugType));
			}
		}
		return plugType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AdapterType basicGetPlugType() {
		return plugType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPlugType(AdapterType newPlugType, NotificationChain msgs) {
		AdapterType oldPlugType = plugType;
		plugType = newPlugType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ADAPTER_TYPE__PLUG_TYPE, oldPlugType, newPlugType);
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
	public void setPlugType(AdapterType newPlugType) {
		if (newPlugType != plugType) {
			NotificationChain msgs = null;
			if (plugType != null)
				msgs = ((InternalEObject)plugType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.ADAPTER_TYPE__PLUG_TYPE, null, msgs);
			if (newPlugType != null)
				msgs = ((InternalEObject)newPlugType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LibraryElementPackage.ADAPTER_TYPE__PLUG_TYPE, null, msgs);
			msgs = basicSetPlugType(newPlugType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LibraryElementPackage.ADAPTER_TYPE__PLUG_TYPE, newPlugType, newPlugType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<INamedElement> getInputParameters() {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.CallableAnnotations.getInputParameters(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<INamedElement> getOutputParameters() {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.CallableAnnotations.getOutputParameters(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<INamedElement> getInOutParameters() {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.CallableAnnotations.getInOutParameters(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DataType getReturnType() {
		return org.eclipse.fordiac.ide.model.libraryElement.impl.CallableAnnotations.getReturnType(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.ADAPTER_TYPE__INTERFACE_LIST:
				return basicSetInterfaceList(null, msgs);
			case LibraryElementPackage.ADAPTER_TYPE__SERVICE:
				return basicSetService(null, msgs);
			case LibraryElementPackage.ADAPTER_TYPE__PLUG_TYPE:
				return basicSetPlugType(null, msgs);
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
			case LibraryElementPackage.ADAPTER_TYPE__INTERFACE_LIST:
				return getInterfaceList();
			case LibraryElementPackage.ADAPTER_TYPE__SERVICE:
				return getService();
			case LibraryElementPackage.ADAPTER_TYPE__PLUG_TYPE:
				if (resolve) return getPlugType();
				return basicGetPlugType();
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
			case LibraryElementPackage.ADAPTER_TYPE__INTERFACE_LIST:
				setInterfaceList((InterfaceList)newValue);
				return;
			case LibraryElementPackage.ADAPTER_TYPE__SERVICE:
				setService((Service)newValue);
				return;
			case LibraryElementPackage.ADAPTER_TYPE__PLUG_TYPE:
				setPlugType((AdapterType)newValue);
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
			case LibraryElementPackage.ADAPTER_TYPE__INTERFACE_LIST:
				setInterfaceList((InterfaceList)null);
				return;
			case LibraryElementPackage.ADAPTER_TYPE__SERVICE:
				setService((Service)null);
				return;
			case LibraryElementPackage.ADAPTER_TYPE__PLUG_TYPE:
				setPlugType((AdapterType)null);
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
			case LibraryElementPackage.ADAPTER_TYPE__INTERFACE_LIST:
				return interfaceList != null;
			case LibraryElementPackage.ADAPTER_TYPE__SERVICE:
				return service != null;
			case LibraryElementPackage.ADAPTER_TYPE__PLUG_TYPE:
				return plugType != null;
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
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == ICallable.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == FBType.class) {
			switch (derivedFeatureID) {
				case LibraryElementPackage.ADAPTER_TYPE__INTERFACE_LIST: return LibraryElementPackage.FB_TYPE__INTERFACE_LIST;
				case LibraryElementPackage.ADAPTER_TYPE__SERVICE: return LibraryElementPackage.FB_TYPE__SERVICE;
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
		if (baseClass == ICallable.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == FBType.class) {
			switch (baseFeatureID) {
				case LibraryElementPackage.FB_TYPE__INTERFACE_LIST: return LibraryElementPackage.ADAPTER_TYPE__INTERFACE_LIST;
				case LibraryElementPackage.FB_TYPE__SERVICE: return LibraryElementPackage.ADAPTER_TYPE__SERVICE;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //AdapterTypeImpl
