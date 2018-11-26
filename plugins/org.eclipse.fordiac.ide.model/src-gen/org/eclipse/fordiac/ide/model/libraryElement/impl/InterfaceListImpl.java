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
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Interface List</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.InterfaceListImpl#getPlugs <em>Plugs</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.InterfaceListImpl#getSockets <em>Sockets</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.InterfaceListImpl#getEventInputs <em>Event Inputs</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.InterfaceListImpl#getEventOutputs <em>Event Outputs</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.InterfaceListImpl#getInputVars <em>Input Vars</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.InterfaceListImpl#getOutputVars <em>Output Vars</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InterfaceListImpl extends EObjectImpl implements InterfaceList {
	/**
	 * The cached value of the '{@link #getPlugs() <em>Plugs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlugs()
	 * @generated
	 * @ordered
	 */
	protected EList<AdapterDeclaration> plugs;

	/**
	 * The cached value of the '{@link #getSockets() <em>Sockets</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSockets()
	 * @generated
	 * @ordered
	 */
	protected EList<AdapterDeclaration> sockets;

	/**
	 * The cached value of the '{@link #getEventInputs() <em>Event Inputs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventInputs()
	 * @generated
	 * @ordered
	 */
	protected EList<Event> eventInputs;

	/**
	 * The cached value of the '{@link #getEventOutputs() <em>Event Outputs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventOutputs()
	 * @generated
	 * @ordered
	 */
	protected EList<Event> eventOutputs;

	/**
	 * The cached value of the '{@link #getInputVars() <em>Input Vars</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInputVars()
	 * @generated
	 * @ordered
	 */
	protected EList<VarDeclaration> inputVars;

	/**
	 * The cached value of the '{@link #getOutputVars() <em>Output Vars</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputVars()
	 * @generated
	 * @ordered
	 */
	protected EList<VarDeclaration> outputVars;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InterfaceListImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.INTERFACE_LIST;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<AdapterDeclaration> getPlugs() {
		if (plugs == null) {
			plugs = new EObjectContainmentEList<AdapterDeclaration>(AdapterDeclaration.class, this, LibraryElementPackage.INTERFACE_LIST__PLUGS);
		}
		return plugs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<AdapterDeclaration> getSockets() {
		if (sockets == null) {
			sockets = new EObjectContainmentEList<AdapterDeclaration>(AdapterDeclaration.class, this, LibraryElementPackage.INTERFACE_LIST__SOCKETS);
		}
		return sockets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Event> getEventInputs() {
		if (eventInputs == null) {
			eventInputs = new EObjectContainmentEList<Event>(Event.class, this, LibraryElementPackage.INTERFACE_LIST__EVENT_INPUTS);
		}
		return eventInputs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Event> getEventOutputs() {
		if (eventOutputs == null) {
			eventOutputs = new EObjectContainmentEList<Event>(Event.class, this, LibraryElementPackage.INTERFACE_LIST__EVENT_OUTPUTS);
		}
		return eventOutputs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VarDeclaration> getInputVars() {
		if (inputVars == null) {
			inputVars = new EObjectContainmentEList<VarDeclaration>(VarDeclaration.class, this, LibraryElementPackage.INTERFACE_LIST__INPUT_VARS);
		}
		return inputVars;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VarDeclaration> getOutputVars() {
		if (outputVars == null) {
			outputVars = new EObjectContainmentEList<VarDeclaration>(VarDeclaration.class, this, LibraryElementPackage.INTERFACE_LIST__OUTPUT_VARS);
		}
		return outputVars;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IInterfaceElement> getAllInterfaceElements() {
		return org.eclipse.fordiac.ide.model.Annotations.getAllInterfaceElements(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Event getEvent(final String name) {
		return org.eclipse.fordiac.ide.model.Annotations.getEvent(this, name);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VarDeclaration getVariable(final String name) {
		return org.eclipse.fordiac.ide.model.Annotations.getVariable(this, name);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IInterfaceElement getInterfaceElement(final String name) {
		return org.eclipse.fordiac.ide.model.Annotations.getInterfaceElement(this, name);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FBNetworkElement getFBNetworkElement() {
		return org.eclipse.fordiac.ide.model.Annotations.getFBNetworkElement(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AdapterDeclaration getAdapter(final String name) {
		return org.eclipse.fordiac.ide.model.Annotations.getAdapter(this, name);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.INTERFACE_LIST__PLUGS:
				return ((InternalEList<?>)getPlugs()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.INTERFACE_LIST__SOCKETS:
				return ((InternalEList<?>)getSockets()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.INTERFACE_LIST__EVENT_INPUTS:
				return ((InternalEList<?>)getEventInputs()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.INTERFACE_LIST__EVENT_OUTPUTS:
				return ((InternalEList<?>)getEventOutputs()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.INTERFACE_LIST__INPUT_VARS:
				return ((InternalEList<?>)getInputVars()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.INTERFACE_LIST__OUTPUT_VARS:
				return ((InternalEList<?>)getOutputVars()).basicRemove(otherEnd, msgs);
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
			case LibraryElementPackage.INTERFACE_LIST__PLUGS:
				return getPlugs();
			case LibraryElementPackage.INTERFACE_LIST__SOCKETS:
				return getSockets();
			case LibraryElementPackage.INTERFACE_LIST__EVENT_INPUTS:
				return getEventInputs();
			case LibraryElementPackage.INTERFACE_LIST__EVENT_OUTPUTS:
				return getEventOutputs();
			case LibraryElementPackage.INTERFACE_LIST__INPUT_VARS:
				return getInputVars();
			case LibraryElementPackage.INTERFACE_LIST__OUTPUT_VARS:
				return getOutputVars();
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
			case LibraryElementPackage.INTERFACE_LIST__PLUGS:
				getPlugs().clear();
				getPlugs().addAll((Collection<? extends AdapterDeclaration>)newValue);
				return;
			case LibraryElementPackage.INTERFACE_LIST__SOCKETS:
				getSockets().clear();
				getSockets().addAll((Collection<? extends AdapterDeclaration>)newValue);
				return;
			case LibraryElementPackage.INTERFACE_LIST__EVENT_INPUTS:
				getEventInputs().clear();
				getEventInputs().addAll((Collection<? extends Event>)newValue);
				return;
			case LibraryElementPackage.INTERFACE_LIST__EVENT_OUTPUTS:
				getEventOutputs().clear();
				getEventOutputs().addAll((Collection<? extends Event>)newValue);
				return;
			case LibraryElementPackage.INTERFACE_LIST__INPUT_VARS:
				getInputVars().clear();
				getInputVars().addAll((Collection<? extends VarDeclaration>)newValue);
				return;
			case LibraryElementPackage.INTERFACE_LIST__OUTPUT_VARS:
				getOutputVars().clear();
				getOutputVars().addAll((Collection<? extends VarDeclaration>)newValue);
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
			case LibraryElementPackage.INTERFACE_LIST__PLUGS:
				getPlugs().clear();
				return;
			case LibraryElementPackage.INTERFACE_LIST__SOCKETS:
				getSockets().clear();
				return;
			case LibraryElementPackage.INTERFACE_LIST__EVENT_INPUTS:
				getEventInputs().clear();
				return;
			case LibraryElementPackage.INTERFACE_LIST__EVENT_OUTPUTS:
				getEventOutputs().clear();
				return;
			case LibraryElementPackage.INTERFACE_LIST__INPUT_VARS:
				getInputVars().clear();
				return;
			case LibraryElementPackage.INTERFACE_LIST__OUTPUT_VARS:
				getOutputVars().clear();
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
			case LibraryElementPackage.INTERFACE_LIST__PLUGS:
				return plugs != null && !plugs.isEmpty();
			case LibraryElementPackage.INTERFACE_LIST__SOCKETS:
				return sockets != null && !sockets.isEmpty();
			case LibraryElementPackage.INTERFACE_LIST__EVENT_INPUTS:
				return eventInputs != null && !eventInputs.isEmpty();
			case LibraryElementPackage.INTERFACE_LIST__EVENT_OUTPUTS:
				return eventOutputs != null && !eventOutputs.isEmpty();
			case LibraryElementPackage.INTERFACE_LIST__INPUT_VARS:
				return inputVars != null && !inputVars.isEmpty();
			case LibraryElementPackage.INTERFACE_LIST__OUTPUT_VARS:
				return outputVars != null && !outputVars.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //InterfaceListImpl
