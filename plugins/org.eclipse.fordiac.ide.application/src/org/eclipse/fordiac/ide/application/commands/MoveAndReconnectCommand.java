/*******************************************************************************
 * Copyright (c) 2018-2020 Johannes Kepler University
 * 				 2020 Primetals Technologies Germany GmbH
 *               2021 Primetals Technologies Austria GmbH
 *               2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr - fix positioning of elements
 *   Daniel Lindhuber - connection behavior for move to parent
 *   Michael Jaeger - added drag and drop functionality
 *   Bianca Wiesmayr - cleanup and fixes, positioning
 *   Sebastian Hollersbacher - changed connection behaviour
 *******************************************************************************/

package org.eclipse.fordiac.ide.application.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.change.RemoveElementsFromGroup;
import org.eclipse.fordiac.ide.model.commands.change.UnmapCommand;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.swt.graphics.Point;

public class MoveAndReconnectCommand extends Command implements ScopedCommand {

	protected final SubApp sourceSubApp;
	private Position destination;
	private final FBNetwork destinationNetwork;
	protected final List<FBNetworkElement> elements;
	private final Map<FBNetworkElement, Position> oldPos = new HashMap<>(); // for undo
	private final Map<FBNetworkElement, Position> newPos = new HashMap<>(); // for redo
	private final CompoundCommand unmappingCmds = new CompoundCommand(); // stores all needed unmap commands
	private final CompoundCommand setUniqueName = new CompoundCommand();
	private final CompoundCommand removeFromGroup = new CompoundCommand();
	private final Set<Connection> connsMovedToParent = new HashSet<>();
	private CompoundCommand reconnectConnectionsCommands = null;

	public MoveAndReconnectCommand(final Collection<FBNetworkElement> elements, final Point destination) {
		this(elements, destination, null);
	}

	public MoveAndReconnectCommand(final Collection<FBNetworkElement> elements, final Point destination,
			final FBNetwork destinationNetwork) {
		this.elements = new ArrayList<>(elements);
		setDestination(destination);
		this.sourceSubApp = getSourceSubapp();
		if (destinationNetwork == null) {
			this.destinationNetwork = sourceSubApp != null ? sourceSubApp.getFbNetwork() : null;
		} else {
			this.destinationNetwork = destinationNetwork;
		}
	}

	private SubApp getSourceSubapp() {
		if (!elements.isEmpty() && (elements.get(0).getOuterFBNetworkElement() instanceof final SubApp fbel)) {
			return fbel;
		}
		return null;
	}

	@Override
	public boolean canExecute() {
		return (null != sourceSubApp) && allElementsFromSameSubApp();
	}

	private boolean allElementsFromSameSubApp() {
		return elements.stream().allMatch(el -> sourceSubApp.equals(el.getOuterFBNetworkElement()));
	}

	@Override
	public void execute() {
		removeElementsFromGroup();
		removeElementsFromSubapp();
		addElementsToDestination();
		reconnectConnections();
	}

	private void removeElementsFromGroup() {
		elements.forEach(element -> {
			final RemoveElementsFromGroup cmd = new RemoveElementsFromGroup(Arrays.asList(element));
			removeFromGroup.add(cmd);
		});
		removeFromGroup.execute();
	}

	protected void removeElementsFromSubapp() {
		elements.forEach(this::removeElementFromSubapp);
		// connections/interface elements are executed immediately

		elements.forEach(this::removeContainedGroupElementsFromSubapp);
	}

	private void removeContainedGroupElementsFromSubapp(final FBNetworkElement el) {
		if (el instanceof final Group group) {
			group.getGroupElements().forEach(this::removeElementFromSubapp);
		}
	}

	private void removeElementFromSubapp(final FBNetworkElement element) {
		oldPos.put(element, element.getPosition());
		if (element.isMapped()) {
			final UnmapCommand cmd = new UnmapCommand(element);
			if (cmd.canExecute()) {
				cmd.execute();
				unmappingCmds.add(cmd);
			}
		}
		sourceSubApp.getSubAppNetwork().getNetworkElements().remove(element);
	}

	protected void addElementsToDestination() {
		elements.forEach(this::addElementToDestination);
		elements.forEach(this::addGroupElements);
		positionElements();
		connsMovedToParent.forEach(destinationNetwork::addConnection);
	}

	private void addElementToDestination(final FBNetworkElement element) {
		destinationNetwork.getNetworkElements().add(element);

		// ensure unique name in new network
		if (!NameRepository.isValidName(element, element.getName())) {
			final String uniqueName = NameRepository.createUniqueName(element, element.getName());
			final ChangeNameCommand changeNameCommand = ChangeNameCommand.forName(element, uniqueName);
			changeNameCommand.execute();
			setUniqueName.add(changeNameCommand);
		}
	}

	private void addGroupElements(final FBNetworkElement el) {
		if (el instanceof final Group group) {
			group.getGroupElements().forEach(this::addElementToDestination);
		}
	}

	private void reconnectConnections() {
		reconnectConnectionsCommands = createReconnectCommand(this.elements);
		reconnectConnectionsCommands.execute();
	}

	@Override
	public void redo() {
		removeFromGroup.redo();
		redoRemoveElementsFromSubapp();
		redoAddElementsToDestination();
		reconnectConnectionsCommands.redo();
	}

	protected void redoRemoveElementsFromSubapp() {
		unmappingCmds.redo();
		elements.forEach(this::redoRemoveElementFromSubapp);
	}

	private void redoRemoveElementFromSubapp(final FBNetworkElement element) {
		sourceSubApp.getSubAppNetwork().getNetworkElements().remove(element);
	}

	protected void redoAddElementsToDestination() {
		elements.forEach(this::redoAddElementToDestination);
		setUniqueName.redo();
		connsMovedToParent.forEach(destinationNetwork::addConnection);
	}

	private void redoAddElementToDestination(final FBNetworkElement element) {
		destinationNetwork.getNetworkElements().add(element);
		element.setPosition(newPos.get(element));
	}

	@Override
	public void undo() {
		reconnectConnectionsCommands.undo();
		undoRemoveElementsFromSubapp();
		undoAddElementsToDestination();
		removeFromGroup.undo();
	}

	protected void undoRemoveElementsFromSubapp() {
		elements.forEach(this::undoRemoveElementFromSubapp);
		unmappingCmds.undo();
		// check for connections being displayed right (broken or not)
		elements.forEach(FBNetworkElement::checkConnections);
	}

	private void undoRemoveElementFromSubapp(final FBNetworkElement element) {
		newPos.put(element, element.getPosition());
		sourceSubApp.getSubAppNetwork().getNetworkElements().add(element);
	}

	protected void undoAddElementsToDestination() {
		setUniqueName.undo();
		elements.forEach(this::undoAddElementToDestination);
		connsMovedToParent.forEach(sourceSubApp.getFbNetwork()::addConnection);
	}

	private void undoAddElementToDestination(final FBNetworkElement element) {
		destinationNetwork.getNetworkElements().remove(element);
		element.setPosition(oldPos.get(element));
	}

	public List<FBNetworkElement> getElements() {
		return elements;
	}

	private void positionElements() {
		if (null == destination) {
			destination = sourceSubApp.getPosition();
		}
		FBNetworkHelper.moveFBNetworkToDestination(elements, destination);
	}

	private static CompoundCommand createReconnectCommand(final List<FBNetworkElement> fbElements) {
		final CompoundCommand cmd = new CompoundCommand();
		for (final FBNetworkElement fbElement : fbElements) {
			// reconnect the input connections
			fbElement.getInterface().getAllInterfaceElements().forEach(ie -> {
				if (ie.isIsInput()) {
					ie.getInputConnections().forEach(conn -> {
						if (!fbElements.contains(conn.getSource().eContainer().eContainer())) {
							cmd.add(new BorderCrossingReconnectCommand(conn.getDestination(), conn.getDestination(),
									conn, false));
						}
					});
				} else {
					ie.getOutputConnections().forEach(conn -> {
						if (!fbElements.contains(conn.getDestination().eContainer().eContainer())) {
							cmd.add(new BorderCrossingReconnectCommand(conn.getSource(), conn.getSource(), conn, true));
						}
					});

				}
			});
		}

		return cmd;
	}

	protected final void setDestination(final Point destination) {
		this.destination = CoordinateConverter.INSTANCE.createPosFromScreenCoordinates(destination.x, destination.y);
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		if (sourceSubApp != null && destinationNetwork != null) {
			return Set.of(sourceSubApp, destinationNetwork);
		}
		return Set.of();
	}
}
