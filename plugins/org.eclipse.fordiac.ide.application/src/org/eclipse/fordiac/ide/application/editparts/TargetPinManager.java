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

import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

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
				newEntry -> TargetInterfaceElement.createFor(newEntry, parentNW)));
		return targetPinChildren.values().stream().sorted().toList();
	}

	private IInterfaceElement getModel() {
		return host.getModel();
	}

	private List<IInterfaceElement> getTargetPins() {
		return getModel().getOutputConnections().stream()
				.filter(con -> (!con.isVisible() && con.getDestination() != null)).flatMap(con -> {
					if (con.getDestination().getFBNetworkElement() instanceof final SubApp subapp
							&& subapp.isUnfolded()) {
						return con.getDestination().getOutputConnections().stream();
					}
					return Stream.of(con);
				}).map(Connection::getDestination).filter(Objects::nonNull).toList();
	}

	private List<IInterfaceElement> getSourcePins() {
		return getModel().getInputConnections().stream().filter(con -> (!con.isVisible() && con.getSource() != null))
				.flatMap(con -> {
					if (con.getSource().getFBNetworkElement() instanceof final SubApp subapp && subapp.isUnfolded()) {
						return con.getSource().getInputConnections().stream();
					}
					return Stream.of(con);
				}).map(Connection::getSource).filter(Objects::nonNull).toList();
	}
}
