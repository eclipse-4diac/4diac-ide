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
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.commands.QualNameAffectedCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.change.RemoveElementsFromGroup;
import org.eclipse.fordiac.ide.model.commands.change.UnmapCommand;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class MoveAndReconnectCommand extends Command implements QualNameAffectedCommand {

	protected final FBNetwork sourceNetwork;
	private Position destination;
	private final FBNetwork destinationNetwork;
	protected final List<FBNetworkElement> elements;
	private final Map<FBNetworkElement, Position> oldPos = new HashMap<>(); // for undo
	private final Map<FBNetworkElement, Position> newPos = new HashMap<>(); // for redo
	private final CompoundCommand unmappingCmds = new CompoundCommand(); // stores all needed unmap commands
	private final CompoundCommand setUniqueName = new CompoundCommand();
	private final CompoundCommand removeFromGroup = new CompoundCommand();
	private CompoundCommand reconnectConnectionsCommands = null;
	private final Map<INamedElement, String> oldQualNames = new HashMap<>(); // store for plantHierchy update
	private final Map<INamedElement, String> newQualNames = new HashMap<>(); // store for plant updat

	public MoveAndReconnectCommand(final Collection<FBNetworkElement> elements, final Point destination) {
		this(elements, destination, null);
	}

	public MoveAndReconnectCommand(final Collection<FBNetworkElement> elements, final Point destination,
			final FBNetwork destinationNetwork) {
		this.elements = new ArrayList<>(elements);
		setDestination(destination);
		this.sourceNetwork = getSourceNetwork();
		if (destinationNetwork == null) {
			if (sourceNetwork != null && sourceNetwork.eContainer() instanceof final SubApp subapp) {
				this.destinationNetwork = subapp.getFbNetwork();
			} else {
				this.destinationNetwork = null;
			}
		} else {
			this.destinationNetwork = destinationNetwork;
		}
	}

	private FBNetwork getSourceNetwork() {
		return elements.isEmpty() ? null : elements.get(0).getFbNetwork();
	}

	@Override
	public boolean canExecute() {
		return (null != destinationNetwork) && destinationNetwork != sourceNetwork && allElementsFromSameNetwork()
				&& !destinationNetworkChildOfElements();
	}

	private boolean allElementsFromSameNetwork() {
		return elements.stream().allMatch(el -> sourceNetwork.equals(el.getFbNetwork()));
	}

	private boolean destinationNetworkChildOfElements() {
		EObject obj = destinationNetwork.eContainer();
		while (obj != null) {
			if (elements.contains(obj)) {
				return true;
			}
			obj = obj.eContainer();
		}
		return false;
	}

	@Override
	public void execute() {
		storeOldQualNames();
		removeElementsFromGroup();
		removeElementsFromSubapp();
		addElementsToDestination();
		reconnectConnections();
		storeNewQualNames();
	}

	private void storeNewQualNames() {
		elements.forEach(e -> newQualNames.put(e, e.getQualifiedName()));
	}

	private void storeOldQualNames() {
		elements.forEach(e -> oldQualNames.put(e, e.getQualifiedName()));
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
		sourceNetwork.getNetworkElements().remove(element);
	}

	protected void addElementsToDestination() {
		elements.forEach(this::addElementToDestination);
		elements.forEach(this::addGroupElements);
		positionElements();
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
		sourceNetwork.getNetworkElements().remove(element);
	}

	protected void redoAddElementsToDestination() {
		elements.forEach(this::redoAddElementToDestination);
		setUniqueName.redo();
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
		sourceNetwork.getNetworkElements().add(element);
	}

	protected void undoAddElementsToDestination() {
		setUniqueName.undo();
		elements.forEach(this::undoAddElementToDestination);
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
			if (destinationNetwork.eContainer() instanceof final SubApp subapp) {
				destination = subapp.getPosition();
			} else {
				destination = LibraryElementFactory.eINSTANCE.createPosition();
			}
		}
		FBNetworkHelper.moveFBNetworkToDestination(elements, destination);
	}

	private static CompoundCommand createReconnectCommand(final List<FBNetworkElement> fbElements) {
		final CompoundCommand cmd = new CompoundCommand();
		for (final FBNetworkElement fbElement : fbElements) {
			fbElement.getInterface().getAllInterfaceElements().forEach(ie -> {
				if (ie.isIsInput()) {
					ie.getInputConnections().stream()
							// filter connections between selected fbElements (handled in OutputConnections)
							.filter(conn -> !fbElements.contains(conn.getSource().eContainer().eContainer()))
							// add reconnection command to same Interface
							.forEach(conn -> cmd.add(new BorderCrossingReconnectCommand(conn.getDestination(),
									conn.getDestination(), conn, false)));
				} else {
					ie.getOutputConnections().stream().forEach(conn -> cmd
							.add(new BorderCrossingReconnectCommand(conn.getSource(), conn.getSource(), conn, true)));
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
		if (sourceNetwork != null && destinationNetwork != null) {
			return Set.of(sourceNetwork, destinationNetwork);
		}
		return Set.of();
	}

	@Override
	public String getOldQualName(final INamedElement element) {
		return oldQualNames.get(element);
	}

	@Override
	public String getNewQualName(final INamedElement element) {
		return newQualNames.get(element);
	}

	@Override
	public List<INamedElement> getChangedElements() {
		return new ArrayList<>(elements);
	}

}
