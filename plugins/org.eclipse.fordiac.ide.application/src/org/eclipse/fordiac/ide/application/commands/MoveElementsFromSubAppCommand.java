/*******************************************************************************
 * Copyright (c) 2018-2020 Johannes Kepler University
 * 				 2020 Primetals Technologies Germany GmbH
 *               2021 Primetals Technologies Austria GmbH
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
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.AdapterConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.swt.graphics.Point;

public class MoveElementsFromSubAppCommand extends Command implements ScopedCommand {

	protected final SubApp sourceSubApp;
	private Position destination;
	private final FBNetwork destinationNetwork;
	protected final List<FBNetworkElement> elements;
	private final Map<FBNetworkElement, org.eclipse.fordiac.ide.model.libraryElement.Position> oldPos = new HashMap<>(); // for
	// undo
	private final Map<FBNetworkElement, org.eclipse.fordiac.ide.model.libraryElement.Position> newPos = new HashMap<>(); // for
	// redo
	private final CompoundCommand unmappingCmds = new CompoundCommand(); // stores all needed unmap commands
	private final CompoundCommand deleteConnectionsAndInterfaceElements = new CompoundCommand();
	private final CompoundCommand createConnectionsCommands = new CompoundCommand();
	protected final CompoundCommand createSubAppInterfaceElementCommands = new CompoundCommand();
	private final CompoundCommand setUniqueName = new CompoundCommand();
	private final CompoundCommand removeFromGroup = new CompoundCommand();
	private final Set<Connection> connsMovedToParent = new HashSet<>();

	public MoveElementsFromSubAppCommand(final Collection<FBNetworkElement> elements, final Point destination) {
		this(elements, destination, null);
	}

	public MoveElementsFromSubAppCommand(final Collection<FBNetworkElement> elements, final Point destination,
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
		processElementConnections(element);
	}

	protected void addElementsToDestination() {
		elements.forEach(this::addElementToDestination);
		elements.forEach(this::addGroupElements);
		createConnectionsCommands.execute();
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

	@Override
	public void redo() {
		removeFromGroup.redo();
		redoRemoveElementsFromSubapp();
		redoAddElementsToDestination();
	}

	protected void redoRemoveElementsFromSubapp() {
		unmappingCmds.redo();
		elements.forEach(this::redoRemoveElementFromSubapp);
		deleteConnectionsAndInterfaceElements.redo();
	}

	private void redoRemoveElementFromSubapp(final FBNetworkElement element) {
		sourceSubApp.getSubAppNetwork().getNetworkElements().remove(element);
	}

	protected void redoAddElementsToDestination() {
		createSubAppInterfaceElementCommands.redo();
		elements.forEach(this::redoAddElementToDestination);
		setUniqueName.redo();
		createConnectionsCommands.redo();
		connsMovedToParent.forEach(destinationNetwork::addConnection);
	}

	private void redoAddElementToDestination(final FBNetworkElement element) {
		destinationNetwork.getNetworkElements().add(element);
		element.setPosition(newPos.get(element));
	}

	@Override
	public void undo() {
		undoRemoveElementsFromSubapp();
		undoAddElementsToDestination();
		removeFromGroup.undo();
	}

	protected void undoRemoveElementsFromSubapp() {
		deleteConnectionsAndInterfaceElements.undo();
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
		createConnectionsCommands.undo();
		setUniqueName.undo();
		elements.forEach(this::undoAddElementToDestination);
		connsMovedToParent.forEach(sourceSubApp.getFbNetwork()::addConnection);
	}

	private void undoAddElementToDestination(final FBNetworkElement element) {
		destinationNetwork.getNetworkElements().remove(element);
		element.setPosition(oldPos.get(element));
		createSubAppInterfaceElementCommands.undo();
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

	private void processElementConnections(final FBNetworkElement fbNetworkElement) {
		for (final IInterfaceElement ie : fbNetworkElement.getInterface().getAllInterfaceElements()) {
			if (ie.isIsInput()) {
				// we need to copy the connection list to avoid concurrent modification
				for (final Connection con : new ArrayList<>(ie.getInputConnections())) {
					handleConnection(con, con.getSource(), ie);
				}
			} else {
				// we need to copy the connection list to avoid concurrent modification
				for (final Connection con : new ArrayList<>(ie.getOutputConnections())) {
					handleConnection(con, con.getDestination(), ie);
				}
			}
		}
	}

	private void handleConnection(final Connection con, final IInterfaceElement opposite, final IInterfaceElement ie) {
		if ((opposite.getFBNetworkElement() instanceof SubApp) && opposite.getFBNetworkElement().equals(sourceSubApp)) {
			handleCrossingConnections(con, opposite, ie.isIsInput());
		} else if (connIsPartOfMovedNetwork(opposite)) {
			connsMovedToParent.add(con);
		} else {
			handleInnerConnections(con, ie, ie.isIsInput());
		}
	}

	private boolean connIsPartOfMovedNetwork(final IInterfaceElement opposite) {
		return elements.contains(opposite.getFBNetworkElement());
	}

	private void handleInnerConnections(final Connection con, final IInterfaceElement ie, final boolean isInput) {
		final String subAppIEName = generateSubAppIEName(ie);
		IInterfaceElement subAppIE = sourceSubApp.getInterfaceElement(subAppIEName);
		if (null == subAppIE) {
			// negating "isInput" results in an output pin for input connections and vice
			// versa
			subAppIE = createInterfaceElement(ie, subAppIEName, !isInput);
		}

		createConnection(isInput ? con.getSource() : subAppIE, isInput ? subAppIE : con.getDestination(),
				sourceSubApp.getSubAppNetwork());
		createConnection(isInput ? subAppIE : con.getSource(), isInput ? con.getDestination() : subAppIE,
				sourceSubApp.getFbNetwork());
		addDeleteConnectionOrInterfaceElementCommand(new DeleteConnectionCommand(con));
	}

	private void handleCrossingConnections(final Connection con, final IInterfaceElement opposite,
			final boolean isInput) {

		// analyze source subapp pin
		final List<Connection> internalCons = isInput ? opposite.getOutputConnections()
				: opposite.getInputConnections();
		final List<Connection> outCons = isInput ? opposite.getInputConnections() : opposite.getOutputConnections();

		// reconnect connections from to-be-deleted source subapp pin to the moved
		// fbnetworkelement
		for (final Connection outCon : new ArrayList<>(outCons)) {
			createCrossingConnection(con, isInput, outCon);
		}
		if (1 == internalCons.size()) {
			// our connection is the last one lets remove the interface element
			addDeleteConnectionOrInterfaceElementCommand(new DeleteSubAppInterfaceElementCommand(opposite));
		} else {
			// still connections left, have to keep the interface element
			addDeleteConnectionOrInterfaceElementCommand(new DeleteConnectionCommand(con));
		}
	}

	private void addDeleteConnectionOrInterfaceElementCommand(final Command cmd) {
		if (cmd.canExecute()) {
			cmd.execute();
			deleteConnectionsAndInterfaceElements.add(cmd);
		}
	}

	private void createCrossingConnection(final Connection con, final boolean isInput, final Connection outCon) {
		final IInterfaceElement sourcePin = isInput ? outCon.getSource() : con.getSource();
		final IInterfaceElement destinationPin = isInput ? con.getDestination() : outCon.getDestination();
		createConnection(sourcePin, destinationPin, destinationNetwork);
	}

	private void createConnection(final IInterfaceElement source, final IInterfaceElement destination,
			final FBNetwork network) {
		final AbstractConnectionCreateCommand cmd = getCreateConnectionCommand(network, destination);
		cmd.setSource(source);
		cmd.setDestination(destination);
		createConnectionsCommands.add(cmd);
	}

	private IInterfaceElement createInterfaceElement(final IInterfaceElement ie, final String subAppIEName,
			final boolean isInput) {
		final CreateSubAppInterfaceElementCommand createSubAppInterfaceElementCommand = new CreateSubAppInterfaceElementCommand(
				ie.getType(), sourceSubApp.getInterface(), isInput, -1);
		createSubAppInterfaceElementCommand.execute();
		createSubAppInterfaceElementCommand.getCreatedElement().setName(subAppIEName);
		if (null != createSubAppInterfaceElementCommand.getMirroredElement()) {
			createSubAppInterfaceElementCommand.getMirroredElement().getCreatedElement().setName(subAppIEName);
		}
		createSubAppInterfaceElementCommands.add(createSubAppInterfaceElementCommand);
		return createSubAppInterfaceElementCommand.getCreatedElement();
	}

	private static String generateSubAppIEName(final IInterfaceElement ie) {
		return ie.getFBNetworkElement().getName() + "_" + ie.getName(); //$NON-NLS-1$
	}

	private static AbstractConnectionCreateCommand getCreateConnectionCommand(final FBNetwork fbNetwork,
			final IInterfaceElement subAppIE) {
		AbstractConnectionCreateCommand cmd = null;
		if (subAppIE instanceof Event) {
			cmd = new EventConnectionCreateCommand(fbNetwork);
		} else if (subAppIE instanceof AdapterDeclaration) {
			cmd = new AdapterConnectionCreateCommand(fbNetwork);
		} else {
			cmd = new DataConnectionCreateCommand(fbNetwork);
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
