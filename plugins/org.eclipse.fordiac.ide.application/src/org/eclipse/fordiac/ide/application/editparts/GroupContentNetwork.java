/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial implementation and/or documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.application.editparts;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ECollections;
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
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

/**
 * The content of a group. This class mimics as much as possible the behavior of
 * a FBNetwork so that we can utilize common code between expanded subapps and
 * groups.
 */
public class GroupContentNetwork implements FBNetwork {

	private final Group group;

	public GroupContentNetwork(final Group group) {
		this.group = group;
	}

	public Group getGroup() {
		return group;
	}

	@Override
	public EClass eClass() {
		return group.eClass();
	}

	@Override
	public Resource eResource() {
		return group.eResource();
	}

	@Override
	public EObject eContainer() {
		return group.eContainer();
	}

	@Override
	public EStructuralFeature eContainingFeature() {
		return group.eContainingFeature();
	}

	@Override
	public EReference eContainmentFeature() {
		return group.eContainmentFeature();
	}

	@Override
	public EList<EObject> eContents() {
		return group.eContents();
	}

	@Override
	public TreeIterator<EObject> eAllContents() {
		return group.eAllContents();
	}

	@Override
	public boolean eIsProxy() {
		return group.eIsProxy();
	}

	@Override
	public EList<EObject> eCrossReferences() {
		return group.eCrossReferences();
	}

	@Override
	public Object eGet(final EStructuralFeature feature) {
		return group.eGet(feature);
	}

	@Override
	public Object eGet(final EStructuralFeature feature, final boolean resolve) {
		return group.eGet(feature, resolve);
	}

	@Override
	public void eSet(final EStructuralFeature feature, final Object newValue) {
		group.eSet(feature, newValue);
	}

	@Override
	public boolean eIsSet(final EStructuralFeature feature) {
		return group.eIsSet(feature);
	}

	@Override
	public void eUnset(final EStructuralFeature feature) {
		group.eUnset(feature);
	}

	@Override
	public Object eInvoke(final EOperation operation, final EList<?> arguments) throws InvocationTargetException {
		return group.eInvoke(operation, arguments);
	}

	@Override
	public EList<Adapter> eAdapters() {
		return group.eAdapters();
	}

	@Override
	public boolean eDeliver() {
		return group.eDeliver();
	}

	@Override
	public void eSetDeliver(final boolean deliver) {
		group.eSetDeliver(deliver);
	}

	@Override
	public void eNotify(final Notification notification) {
		group.eNotify(notification);
	}

	@Override
	public EList<FBNetworkElement> getNetworkElements() {
		return group.getGroupElements();
	}

	@SuppressWarnings("unchecked")
	@Override
	public EList<DataConnection> getDataConnections() {
		// groups don't have data connections
		return (EList<DataConnection>) ECollections.EMPTY_ELIST;
	}

	@SuppressWarnings("unchecked")
	@Override
	public EList<EventConnection> getEventConnections() {
		// groups don't have event connections
		return (EList<EventConnection>) ECollections.EMPTY_ELIST;
	}

	@SuppressWarnings("unchecked")
	@Override
	public EList<AdapterConnection> getAdapterConnections() {
		// groups don't have adapter connections
		return (EList<AdapterConnection>) ECollections.EMPTY_ELIST;
	}

	@Override
	public void addConnection(final Connection connection) {
		// do nothing as groups don't have connections
	}

	@Override
	public void removeConnection(final Connection connection) {
		// do nothing as groups don't have connections
	}

	@Override
	public void addConnectionWithIndex(final Connection connection, final int index) {
		// do nothing as groups don't have connections

	}

	@Override
	public boolean isApplicationNetwork() {
		return false;
	}

	@Override
	public boolean isSubApplicationNetwork() {
		return false;
	}

	@Override
	public boolean isResourceNetwork() {
		return false;
	}

	@Override
	public boolean isCFBTypeNetwork() {
		return false;
	}

	@Override
	public AutomationSystem getAutomationSystem() {
		return group.getFbNetwork().getAutomationSystem();
	}

	@Override
	public Application getApplication() {
		return group.getFbNetwork().getApplication();
	}

	@Override
	public FB getFBNamed(final String name) {
		// FBs of groups are contained in the parent framework therefore we can delegate
		return group.getFbNetwork().getFBNamed(name);
	}

	@Override
	public SubApp getSubAppNamed(final String name) {
		// SubApps of groups are contained in the parent framework therefore we can
		// delegate
		return group.getFbNetwork().getSubAppNamed(name);
	}

	@Override
	public FBNetworkElement getElementNamed(final String name) {
		// FBNetworkElements of groups are contained in the parent framework therefore
		// we can delegate
		return group.getFbNetwork().getElementNamed(name);
	}

	@Override
	public int getConnectionIndex(final Connection connection) {
		return -1;
	}

	@Override
	public boolean validateCollisions(final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		return true;
	}
}
