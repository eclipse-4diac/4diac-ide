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
import java.util.List;
import java.util.stream.Stream;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Interface List</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.InterfaceListImpl#getEventInputs <em>Event Inputs</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.InterfaceListImpl#getEventOutputs <em>Event Outputs</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.InterfaceListImpl#getInputVars <em>Input Vars</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.InterfaceListImpl#getOutputVars <em>Output Vars</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.InterfaceListImpl#getInOutVars <em>In Out Vars</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.InterfaceListImpl#getOutMappedInOutVars <em>Out Mapped In Out Vars</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.InterfaceListImpl#getPlugs <em>Plugs</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.InterfaceListImpl#getSockets <em>Sockets</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.impl.InterfaceListImpl#getErrorMarker <em>Error Marker</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InterfaceListImpl extends EObjectImpl implements InterfaceList {
	/**
	 * The cached value of the '{@link #getEventInputs() <em>Event Inputs</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getEventInputs()
	 * @generated
	 * @ordered
	 */
	protected EList<Event> eventInputs;

	/**
	 * The cached value of the '{@link #getEventOutputs() <em>Event Outputs</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getEventOutputs()
	 * @generated
	 * @ordered
	 */
	protected EList<Event> eventOutputs;

	/**
	 * The cached value of the '{@link #getInputVars() <em>Input Vars</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getInputVars()
	 * @generated
	 * @ordered
	 */
	protected EList<VarDeclaration> inputVars;

	/**
	 * The cached value of the '{@link #getOutputVars() <em>Output Vars</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getOutputVars()
	 * @generated
	 * @ordered
	 */
	protected EList<VarDeclaration> outputVars;

	/**
	 * The cached value of the '{@link #getInOutVars() <em>In Out Vars</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getInOutVars()
	 * @generated
	 * @ordered
	 */
	protected EList<VarDeclaration> inOutVars;

	/**
	 * The cached value of the '{@link #getOutMappedInOutVars() <em>Out Mapped In Out Vars</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getOutMappedInOutVars()
	 * @generated
	 * @ordered
	 */
	protected EList<VarDeclaration> outMappedInOutVars;

	/**
	 * The cached value of the '{@link #getPlugs() <em>Plugs</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPlugs()
	 * @generated
	 * @ordered
	 */
	protected EList<AdapterDeclaration> plugs;

	/**
	 * The cached value of the '{@link #getSockets() <em>Sockets</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSockets()
	 * @generated
	 * @ordered
	 */
	protected EList<AdapterDeclaration> sockets;

	/**
	 * The cached value of the '{@link #getErrorMarker() <em>Error Marker</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getErrorMarker()
	 * @generated
	 * @ordered
	 */
	protected EList<ErrorMarkerInterface> errorMarker;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected InterfaceListImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.INTERFACE_LIST;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated not
	 */
	@Override
	public EList<VarDeclaration> getInOutVars() {
		if (inOutVars == null) {
			inOutVars = new InOutVarsEList(this, LibraryElementPackage.INTERFACE_LIST__IN_OUT_VARS);
		}
		return inOutVars;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ErrorMarkerInterface> getErrorMarker() {
		if (errorMarker == null) {
			errorMarker = new EObjectContainmentEList<ErrorMarkerInterface>(ErrorMarkerInterface.class, this, LibraryElementPackage.INTERFACE_LIST__ERROR_MARKER);
		}
		return errorMarker;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IInterfaceElement> getAllInterfaceElements() {
		return InterfaceListAnnotations.getAllInterfaceElements(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Event getEvent(final String name) {
		return InterfaceListAnnotations.getEvent(this, name);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VarDeclaration getVariable(final String name) {
		return InterfaceListAnnotations.getVariable(this, name);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IInterfaceElement getInterfaceElement(final String name) {
		return InterfaceListAnnotations.getInterfaceElement(this, name);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FBNetworkElement getFBNetworkElement() {
		return InterfaceListAnnotations.getFBNetworkElement(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AdapterDeclaration getAdapter(final String name) {
		return InterfaceListAnnotations.getAdapter(this, name);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InterfaceList copy() {
		return org.eclipse.fordiac.ide.model.helpers.InterfaceListCopier.copy(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<VarDeclaration> getVisibleInputVars() {
		return getInputVars().stream().filter(org.eclipse.fordiac.ide.model.libraryElement.HiddenElement::isVisible).collect(java.util.stream.Collectors.toList());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<VarDeclaration> getVisibleOutputVars() {
		return getOutputVars().stream().filter(org.eclipse.fordiac.ide.model.libraryElement.HiddenElement::isVisible).collect(java.util.stream.Collectors.toList());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Stream<IInterfaceElement> getInputs() {
		return InterfaceListAnnotations.getInputs(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Stream<IInterfaceElement> getOutputs() {
		return InterfaceListAnnotations.getOutputs(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IInterfaceElement getInput(final String name) {
		return InterfaceListAnnotations.getInputs(this).filter(ie -> ie.getName().equals(name)).findAny().orElse(null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IInterfaceElement getOutput(final String name) {
		return InterfaceListAnnotations.getOutputs(this).filter(ie -> ie.getName().equals(name)).findAny().orElse(null);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated not
	 */
	@Override
	public EList<VarDeclaration> getOutMappedInOutVars() {
		if (outMappedInOutVars == null) {
			outMappedInOutVars = new OutMappedInOutVarsEList(this,
					LibraryElementPackage.INTERFACE_LIST__OUT_MAPPED_IN_OUT_VARS);
		}
		return outMappedInOutVars;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LibraryElementPackage.INTERFACE_LIST__EVENT_INPUTS:
				return ((InternalEList<?>)getEventInputs()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.INTERFACE_LIST__EVENT_OUTPUTS:
				return ((InternalEList<?>)getEventOutputs()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.INTERFACE_LIST__INPUT_VARS:
				return ((InternalEList<?>)getInputVars()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.INTERFACE_LIST__OUTPUT_VARS:
				return ((InternalEList<?>)getOutputVars()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.INTERFACE_LIST__IN_OUT_VARS:
				return ((InternalEList<?>)getInOutVars()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.INTERFACE_LIST__OUT_MAPPED_IN_OUT_VARS:
				return ((InternalEList<?>)getOutMappedInOutVars()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.INTERFACE_LIST__PLUGS:
				return ((InternalEList<?>)getPlugs()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.INTERFACE_LIST__SOCKETS:
				return ((InternalEList<?>)getSockets()).basicRemove(otherEnd, msgs);
			case LibraryElementPackage.INTERFACE_LIST__ERROR_MARKER:
				return ((InternalEList<?>)getErrorMarker()).basicRemove(otherEnd, msgs);
			default:
				return super.eInverseRemove(otherEnd, featureID, msgs);
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LibraryElementPackage.INTERFACE_LIST__EVENT_INPUTS:
				return getEventInputs();
			case LibraryElementPackage.INTERFACE_LIST__EVENT_OUTPUTS:
				return getEventOutputs();
			case LibraryElementPackage.INTERFACE_LIST__INPUT_VARS:
				return getInputVars();
			case LibraryElementPackage.INTERFACE_LIST__OUTPUT_VARS:
				return getOutputVars();
			case LibraryElementPackage.INTERFACE_LIST__IN_OUT_VARS:
				return getInOutVars();
			case LibraryElementPackage.INTERFACE_LIST__OUT_MAPPED_IN_OUT_VARS:
				return getOutMappedInOutVars();
			case LibraryElementPackage.INTERFACE_LIST__PLUGS:
				return getPlugs();
			case LibraryElementPackage.INTERFACE_LIST__SOCKETS:
				return getSockets();
			case LibraryElementPackage.INTERFACE_LIST__ERROR_MARKER:
				return getErrorMarker();
			default:
				return super.eGet(featureID, resolve, coreType);
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
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
			case LibraryElementPackage.INTERFACE_LIST__IN_OUT_VARS:
				getInOutVars().clear();
				getInOutVars().addAll((Collection<? extends VarDeclaration>)newValue);
				return;
			case LibraryElementPackage.INTERFACE_LIST__OUT_MAPPED_IN_OUT_VARS:
				getOutMappedInOutVars().clear();
				getOutMappedInOutVars().addAll((Collection<? extends VarDeclaration>)newValue);
				return;
			case LibraryElementPackage.INTERFACE_LIST__PLUGS:
				getPlugs().clear();
				getPlugs().addAll((Collection<? extends AdapterDeclaration>)newValue);
				return;
			case LibraryElementPackage.INTERFACE_LIST__SOCKETS:
				getSockets().clear();
				getSockets().addAll((Collection<? extends AdapterDeclaration>)newValue);
				return;
			case LibraryElementPackage.INTERFACE_LIST__ERROR_MARKER:
				getErrorMarker().clear();
				getErrorMarker().addAll((Collection<? extends ErrorMarkerInterface>)newValue);
				return;
			default:
				super.eSet(featureID, newValue);
				return;
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
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
			case LibraryElementPackage.INTERFACE_LIST__IN_OUT_VARS:
				getInOutVars().clear();
				return;
			case LibraryElementPackage.INTERFACE_LIST__OUT_MAPPED_IN_OUT_VARS:
				getOutMappedInOutVars().clear();
				return;
			case LibraryElementPackage.INTERFACE_LIST__PLUGS:
				getPlugs().clear();
				return;
			case LibraryElementPackage.INTERFACE_LIST__SOCKETS:
				getSockets().clear();
				return;
			case LibraryElementPackage.INTERFACE_LIST__ERROR_MARKER:
				getErrorMarker().clear();
				return;
			default:
				super.eUnset(featureID);
				return;
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case LibraryElementPackage.INTERFACE_LIST__EVENT_INPUTS:
				return eventInputs != null && !eventInputs.isEmpty();
			case LibraryElementPackage.INTERFACE_LIST__EVENT_OUTPUTS:
				return eventOutputs != null && !eventOutputs.isEmpty();
			case LibraryElementPackage.INTERFACE_LIST__INPUT_VARS:
				return inputVars != null && !inputVars.isEmpty();
			case LibraryElementPackage.INTERFACE_LIST__OUTPUT_VARS:
				return outputVars != null && !outputVars.isEmpty();
			case LibraryElementPackage.INTERFACE_LIST__IN_OUT_VARS:
				return inOutVars != null && !inOutVars.isEmpty();
			case LibraryElementPackage.INTERFACE_LIST__OUT_MAPPED_IN_OUT_VARS:
				return outMappedInOutVars != null && !outMappedInOutVars.isEmpty();
			case LibraryElementPackage.INTERFACE_LIST__PLUGS:
				return plugs != null && !plugs.isEmpty();
			case LibraryElementPackage.INTERFACE_LIST__SOCKETS:
				return sockets != null && !sockets.isEmpty();
			case LibraryElementPackage.INTERFACE_LIST__ERROR_MARKER:
				return errorMarker != null && !errorMarker.isEmpty();
			default:
				return super.eIsSet(featureID);
		}
	}

} // InterfaceListImpl
