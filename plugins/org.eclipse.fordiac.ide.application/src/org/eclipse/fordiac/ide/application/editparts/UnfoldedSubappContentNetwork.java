/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr, Alois Zoitl
 *      - initial implementation and/or documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.application.editparts;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

/**
 * The content of an unfolded subapp is its subapp network. This class forwards
 * all FBNetwork methods to the encapsulated subapp network.
 *
 */
public class UnfoldedSubappContentNetwork implements FBNetwork {

	private final FBNetwork subappContent;

	public UnfoldedSubappContentNetwork(SubApp unfoldedSubapp) {
		if (unfoldedSubapp.getType() != null) {
			subappContent = unfoldedSubapp.getType().getFBNetwork();
		} else {
			subappContent = unfoldedSubapp.getSubAppNetwork();
		}
	}

	public FBNetwork getSubappContent() {
		return subappContent;
	}

	@Override
	public EClass eClass() {
		return subappContent.eClass();
	}

	@Override
	public Resource eResource() {
		return subappContent.eResource();
	}

	@Override
	public EObject eContainer() {
		return subappContent.eContainer();
	}

	@Override
	public EStructuralFeature eContainingFeature() {
		return subappContent.eContainingFeature();
	}

	@Override
	public EReference eContainmentFeature() {
		return subappContent.eContainmentFeature();
	}

	@Override
	public EList<EObject> eContents() {
		return subappContent.eContents();
	}

	@Override
	public TreeIterator<EObject> eAllContents() {
		return subappContent.eAllContents();
	}

	@Override
	public boolean eIsProxy() {
		return subappContent.eIsProxy();
	}

	@Override
	public EList<EObject> eCrossReferences() {
		return subappContent.eCrossReferences();
	}

	@Override
	public Object eGet(EStructuralFeature feature) {
		return subappContent.eGet(feature);
	}

	@Override
	public Object eGet(EStructuralFeature feature, boolean resolve) {
		return subappContent.eGet(feature, resolve);
	}

	@Override
	public void eSet(EStructuralFeature feature, Object newValue) {
		subappContent.eSet(feature, newValue);
	}

	@Override
	public boolean eIsSet(EStructuralFeature feature) {
		return subappContent.eIsSet(feature);
	}

	@Override
	public void eUnset(EStructuralFeature feature) {
		subappContent.eUnset(feature);
	}

	@Override
	public Object eInvoke(EOperation operation, EList<?> arguments) throws InvocationTargetException {
		return subappContent.eInvoke(operation, arguments);
	}

	@Override
	public EList<Adapter> eAdapters() {
		return subappContent.eAdapters();
	}

	@Override
	public boolean eDeliver() {
		return subappContent.eDeliver();
	}

	@Override
	public void eSetDeliver(boolean deliver) {
		subappContent.eSetDeliver(deliver);
	}

	@Override
	public void eNotify(Notification notification) {
		subappContent.eNotify(notification);
	}

	@Override
	public EList<FBNetworkElement> getNetworkElements() {
		return subappContent.getNetworkElements();
	}

	@Override
	public EList<DataConnection> getDataConnections() {
		return subappContent.getDataConnections();
	}

	@Override
	public EList<EventConnection> getEventConnections() {
		return subappContent.getEventConnections();
	}

	@Override
	public EList<AdapterConnection> getAdapterConnections() {
		return subappContent.getAdapterConnections();
	}

	@Override
	public void addConnection(Connection connection) {
		subappContent.addConnection(connection);
	}

	@Override
	public void removeConnection(Connection connection) {
		subappContent.removeConnection(connection);
	}

	@Override
	public boolean isApplicationNetwork() {
		return subappContent.isApplicationNetwork();
	}

	@Override
	public boolean isSubApplicationNetwork() {
		return subappContent.isSubApplicationNetwork();
	}

	@Override
	public boolean isResourceNetwork() {
		return subappContent.isResourceNetwork();
	}

	@Override
	public boolean isCFBTypeNetwork() {
		return subappContent.isCFBTypeNetwork();
	}

	@Override
	public AutomationSystem getAutomationSystem() {
		return subappContent.getAutomationSystem();
	}

	@Override
	public Application getApplication() {
		return subappContent.getApplication();
	}

	@Override
	public FB getFBNamed(String name) {
		return subappContent.getFBNamed(name);
	}

	@Override
	public SubApp getSubAppNamed(String name) {
		return subappContent.getSubAppNamed(name);
	}

	@Override
	public FBNetworkElement getElementNamed(String name) {
		return subappContent.getElementNamed(name);
	}
}
