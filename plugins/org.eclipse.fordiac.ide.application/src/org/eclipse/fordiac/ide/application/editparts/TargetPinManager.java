/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;

public class TargetPinManager {

	private final Map<IInterfaceElement, TargetInterfaceElement> targetPinChildren = new HashMap<>();
	private final InterfaceEditPart host;

	public TargetPinManager(final InterfaceEditPart host) {
		this.host = host;
	}

	List<TargetInterfaceElement> getModelChildren() {
		final List<IInterfaceElement> pins = host.isInput() ? getSourcePins() : getTargetPins();

		// remove entries from our map if they are not in the list anymore
		targetPinChildren.keySet().removeIf(key -> !pins.contains(key));

		// add any missing entries
		final FBNetwork parentNW = getModel().getFBNetworkElement().getFbNetwork();
		pins.forEach(pin -> targetPinChildren.computeIfAbsent(pin,
				newEntry -> TargetInterfaceElement.createFor(getModel(), newEntry, parentNW)));
		return targetPinChildren.values().stream().sorted().toList();
	}

	private IInterfaceElement getModel() {
		return host.getModel();
	}

	private List<IInterfaceElement> getTargetPins() {
		return getModel().getOutputConnections().stream()
				.filter(con -> (!con.isVisible() && con.getDestination() != null))
				.flatMap(TargetPinManager::getTargetPins).filter(Objects::nonNull).toList();
	}

	private static Stream<IInterfaceElement> getTargetPins(final Connection con) {
		final IInterfaceElement destination = con.getDestination();
		if (destination != null
				&& followConnections(destination.getFBNetworkElement(), destination.getOutputConnections())) {
			return destination.getOutputConnections().stream().flatMap(TargetPinManager::getTargetPins);
		}
		return Stream.of(destination);
	}

	private List<IInterfaceElement> getSourcePins() {
		return getModel().getInputConnections().stream().filter(con -> (!con.isVisible() && con.getSource() != null))
				.flatMap(TargetPinManager::getSourcePins).filter(Objects::nonNull).toList();
	}

	private static Stream<IInterfaceElement> getSourcePins(final Connection con) {
		final IInterfaceElement source = con.getSource();
		if (source != null && followConnections(source.getFBNetworkElement(), source.getInputConnections())) {
			return source.getInputConnections().stream().flatMap(TargetPinManager::getSourcePins);
		}
		return Stream.of(source);
	}

	static boolean followConnections(final FBNetworkElement fbnEl, final EList<Connection> conList) {
		return fbnEl instanceof final UntypedSubApp subapp && !isContainedInUnfoldedSubapp(subapp)
				&& !conList.isEmpty();
	}

	private static boolean isContainedInUnfoldedSubapp(final UntypedSubApp subapp) {
		return subapp.getOuterFBNetworkElement() instanceof final SubApp outerSubApp && outerSubApp.isUnfolded();
	}
}
