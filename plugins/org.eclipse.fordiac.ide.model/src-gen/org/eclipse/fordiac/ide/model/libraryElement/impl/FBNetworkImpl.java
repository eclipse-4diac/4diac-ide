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

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>FB Network</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.FBNetworkImpl#getNetworkElements <em>Network Elements</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.FBNetworkImpl#getDataConnections <em>Data Connections</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.FBNetworkImpl#getEventConnections <em>Event Connections</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.FBNetworkImpl#getAdapterConnections <em>Adapter Connections</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FBNetworkImpl extends EObjectImpl implements FBNetwork {
	/**
	 * The cached value of the '{@link #getNetworkElements() <em>Network Elements</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNetworkElements()
	 * @generated
	 * @ordered
	 */
	protected EList<FBNetworkElement> networkElements;

	/**
	 * The cached value of the '{@link #getDataConnections() <em>Data Connections</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataConnections()
	 * @generated
	 * @ordered
	 */
	protected EList<DataConnection> dataConnections;

	/**
	 * The cached value of the '{@link #getEventConnections() <em>Event Connections</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventConnections()
	 * @generated
	 * @ordered
	 */
	protected EList<EventConnection> eventConnections;

	/**
	 * The cached value of the '{@link #getAdapterConnections()
	 * <em>Adapter Connections</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAdapterConnections()
	 * @generated
	 * @ordered
	 */
	protected EList<AdapterConnection> adapterConnections;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected FBNetworkImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.FB_NETWORK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FBNetworkElement> getNetworkElements() {
		if (networkElements == null) {
			networkElements = new EObjectContainmentEList<FBNetworkElement>(FBNetworkElement.class, this, LibraryElementPackage.FB_NETWORK__NETWORK_ELEMENTS);
		}
		return networkElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DataConnection> getDataConnections() {
		if (dataConnections == null) {
			dataConnections = new EObjectContainmentEList<DataConnection>(DataConnection.class, this, LibraryElementPackage.FB_NETWORK__DATA_CONNECTIONS);
		}
		return dataConnections;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EventConnection> getEventConnections() {
		if (eventConnections == null) {
			eventConnections = new EObjectContainmentEList.Resolving<EventConnection>(EventConnection.class, this, LibraryElementPackage.FB_NETWORK__EVENT_CONNECTIONS);
		}
		return eventConnections;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AdapterConnection> getAdapterConnections() {
		if (adapterConnections == null) {
			adapterConnections = new EObjectContainmentEList<AdapterConnection>(AdapterConnection.class, this, LibraryElementPackage.FB_NETWORK__ADAPTER_CONNECTIONS);
		}
		return adapterConnections;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void addConnection(final Connection connection) {
		if (connection instanceof EventConnection) {
			getEventConnections().add((EventConnection)connection);
		}
		if (connection instanceof DataConnection) {
			getDataConnections().add((DataConnection)connection);
		}
		if (connection instanceof AdapterConnection) {
			getAdapterConnections().add((AdapterConnection)connection);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void removeConnection(final Connection connection) {
		if (connection instanceof EventConnection) {
			getEventConnections().remove(connection);
		}
		if (connection instanceof DataConnection) {
			getDataConnections().remove(connection);
		}
		if (connection instanceof AdapterConnection) {
			getAdapterConnections().remove(connection);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isApplicationNetwork() {
		return eContainer() instanceof Application;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSubApplicationNetwork() {
		return eContainer() instanceof SubApp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isResourceNetwork() {
		return eContainer() instanceof Resource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isCFBTypeNetwork() {
		return eContainer() instanceof CompositeFBType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AutomationSystem getAutomationSystem() {
		if(isApplicationNetwork() || isSubApplicationNetwork()){
			return getApplication().getAutomationSystem();
		}
		if(isResourceNetwork()){
			return ((Resource)eContainer()).getAutomationSystem();
		}else if(isCFBTypeNetwork()){
			return ((CompositeFBType)eContainer()).getPaletteEntry().getGroup().getPallete().getAutomationSystem();
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Application getApplication() {
		if(isApplicationNetwork()){
			// no null check is need as this is already done in isApplicationNetwork
			return (Application)eContainer();
		}else if(isSubApplicationNetwork()){
			return ((SubApp) eContainer()).getFbNetwork().getApplication();
		}
		return null;		
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FB getFBNamed(final String name) {
		for (FBNetworkElement element : getNetworkElements()) {
			if((element instanceof FB) && (element.getName().equals(name))){
				return (FB)element;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SubApp getSubAppNamed(final String name) {
		for (FBNetworkElement element : getNetworkElements()) {
			if(element instanceof SubApp){ 
				if(element.getName().equals(name)){
					return (SubApp)element; 
				}
				
				SubApp retVal = ((SubApp)element).getSubAppNetwork().getSubAppNamed(name);
				if(retVal != null){
					return retVal;
				}
			}
		}	
		
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FBNetworkElement getElementNamed(final String name) {
		for (FBNetworkElement element : getNetworkElements()) {
			if(element.getName().equals(name)){
				return element;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.FB_NETWORK__NETWORK_ELEMENTS:
				return ((InternalEList<?>)getNetworkElements()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.FB_NETWORK__DATA_CONNECTIONS:
				return ((InternalEList<?>)getDataConnections()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.FB_NETWORK__EVENT_CONNECTIONS:
				return ((InternalEList<?>)getEventConnections()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.FB_NETWORK__ADAPTER_CONNECTIONS:
				return ((InternalEList<?>)getAdapterConnections()).basicRemove(otherEnd, msgs);
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
			case LibraryElementPackage.FB_NETWORK__NETWORK_ELEMENTS:
				return getNetworkElements();
			case LibraryElementPackage.FB_NETWORK__DATA_CONNECTIONS:
				return getDataConnections();
			case LibraryElementPackage.FB_NETWORK__EVENT_CONNECTIONS:
				return getEventConnections();
			case LibraryElementPackage.FB_NETWORK__ADAPTER_CONNECTIONS:
				return getAdapterConnections();
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
			case LibraryElementPackage.FB_NETWORK__NETWORK_ELEMENTS:
				getNetworkElements().clear();
				getNetworkElements().addAll((Collection<? extends FBNetworkElement>)newValue);
				return;
			case LibraryElementPackage.FB_NETWORK__DATA_CONNECTIONS:
				getDataConnections().clear();
				getDataConnections().addAll((Collection<? extends DataConnection>)newValue);
				return;
			case LibraryElementPackage.FB_NETWORK__EVENT_CONNECTIONS:
				getEventConnections().clear();
				getEventConnections().addAll((Collection<? extends EventConnection>)newValue);
				return;
			case LibraryElementPackage.FB_NETWORK__ADAPTER_CONNECTIONS:
				getAdapterConnections().clear();
				getAdapterConnections().addAll((Collection<? extends AdapterConnection>)newValue);
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
			case LibraryElementPackage.FB_NETWORK__NETWORK_ELEMENTS:
				getNetworkElements().clear();
				return;
			case LibraryElementPackage.FB_NETWORK__DATA_CONNECTIONS:
				getDataConnections().clear();
				return;
			case LibraryElementPackage.FB_NETWORK__EVENT_CONNECTIONS:
				getEventConnections().clear();
				return;
			case LibraryElementPackage.FB_NETWORK__ADAPTER_CONNECTIONS:
				getAdapterConnections().clear();
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
			case LibraryElementPackage.FB_NETWORK__NETWORK_ELEMENTS:
				return networkElements != null && !networkElements.isEmpty();
			case LibraryElementPackage.FB_NETWORK__DATA_CONNECTIONS:
				return dataConnections != null && !dataConnections.isEmpty();
			case LibraryElementPackage.FB_NETWORK__EVENT_CONNECTIONS:
				return eventConnections != null && !eventConnections.isEmpty();
			case LibraryElementPackage.FB_NETWORK__ADAPTER_CONNECTIONS:
				return adapterConnections != null && !adapterConnections.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // FBNetworkImpl
