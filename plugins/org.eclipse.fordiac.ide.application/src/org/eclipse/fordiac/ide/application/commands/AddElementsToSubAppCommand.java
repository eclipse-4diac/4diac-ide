/*******************************************************************************
 * Copyright (c) 2018, 2022 Johannes Kepler University,
 *                          Primetals Technologies Austria GmbH
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
 *   Daniel Lindhuber - reuse pins for same source
 *   Fabio Gandolfi - Add elements with coordinates/moveDelta
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.gef.utilities.ElementSelector;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.change.RemoveElementsFromGroup;
import org.eclipse.fordiac.ide.model.commands.change.SetPositionCommand;
import org.eclipse.fordiac.ide.model.commands.change.UnmapCommand;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.AdapterConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper;
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
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.swt.graphics.Point;

public class AddElementsToSubAppCommand extends Command implements ScopedCommand {

	private final SubApp targetSubApp;
	private final List<FBNetworkElement> elementsToAdd = new ArrayList<>();
	private final Map<IInterfaceElement, IInterfaceElement> sourceToSubAppPin = new HashMap<>();
	private final CompoundCommand unmappingCmds = new CompoundCommand(); // stores all needed unmap commands
	private final List<Connection> movedConns = new ArrayList<>();
	private final CompoundCommand modifiedConns = new CompoundCommand();
	private final CompoundCommand changedSubAppIEs = new CompoundCommand();
	private final CompoundCommand setUniqueName = new CompoundCommand();
	private final CompoundCommand removeFromOtherGroups = new CompoundCommand();
	private final CompoundCommand setPositionCommands = new CompoundCommand();

	private org.eclipse.swt.graphics.Point moveDelta;

	public AddElementsToSubAppCommand(final SubApp targetSubApp, final List<?> selection) {
		this.targetSubApp = Objects.requireNonNull(targetSubApp);
		fillElementList(selection);
	}

	public AddElementsToSubAppCommand(final SubApp targetSubApp, final List<?> selection, final Point moveDelta) {
		this(targetSubApp, selection);
		this.moveDelta = moveDelta;
	}

	@Override
	public boolean canExecute() {
		return !targetSubApp.isTyped() && targetSubApp.getSubAppNetwork() != null && !elementsToAdd.isEmpty()
				&& FBNetworkHelper.targetSubappIsInSameFbNetwork(elementsToAdd, targetSubApp);
	}

	@Override
	public void execute() {
		collectElementsToRemoveFromGroup();
		unmappingCmds.execute();
		removeFromOtherGroups.execute();
		processElementsToAdd();
		modifiedConns.execute();
		ElementSelector.selectViewObjects(elementsToAdd);
	}

	@Override
	public void redo() {
		unmappingCmds.redo();
		removeFromOtherGroups.redo();
		elementsToAdd.forEach(element -> addToNetwork(targetSubApp.getSubAppNetwork().getNetworkElements(), element));
		movedConns.forEach(con -> targetSubApp.getSubAppNetwork().addConnection(con));
		changedSubAppIEs.redo();
		setPositionCommands.redo();
		setUniqueName.redo();
		modifiedConns.redo();
	}

	@Override
	public void undo() {
		modifiedConns.undo();
		changedSubAppIEs.undo();
		setPositionCommands.undo();
		movedConns.forEach(con -> targetSubApp.getFbNetwork().addConnection(con));
		elementsToAdd.forEach(element -> addToNetwork(targetSubApp.getFbNetwork().getNetworkElements(), element));
		setUniqueName.undo();
		removeFromOtherGroups.undo();
		unmappingCmds.undo();
		elementsToAdd.forEach(FBNetworkElement::checkConnections);
	}

	private void processElementsToAdd() {
		final EList<FBNetworkElement> fbNetwork = targetSubApp.getSubAppNetwork().getNetworkElements();

		final Position posOffset = getFBOffset();
		for (final FBNetworkElement fbNetworkElement : elementsToAdd) {
			final SetPositionCommand command = new SetPositionCommand(fbNetworkElement, posOffset.getX(),
					posOffset.getY());
			// the set position command needs to be executed before the connections are
			// checked as there interface
			// elements are added which can result in container size changes
			command.execute();
			setPositionCommands.add(command);
			addToNetwork(fbNetwork, fbNetworkElement);
			checkElementConnections(fbNetworkElement);
			ensureUniqueName(fbNetworkElement);
		}
	}

	private static void addToNetwork(final EList<FBNetworkElement> fbNetwork, final FBNetworkElement element) {
		fbNetwork.add(element);
		if (element instanceof final Group group) {
			group.getGroupElements().forEach(fbNetwork::add);
		}
	}

	private Position getFBOffset() {
		if (moveDelta != null) {
			return CoordinateConverter.INSTANCE.createPosFromScreenCoordinates(-moveDelta.x, -moveDelta.y);
		}
		final Position offset = FBNetworkHelper.getTopLeftCornerOfFBNetwork(elementsToAdd);
		offset.setX(-offset.getX());
		offset.setY(-offset.getY());
		return offset;
	}

	private void ensureUniqueName(final FBNetworkElement element) {
		// ensure unique name in new network
		if (!NameRepository.isValidName(element, element.getName())) {
			final String uniqueName = NameRepository.createUniqueName(element, element.getName());
			final ChangeNameCommand cmd = ChangeNameCommand.forName(element, uniqueName);
			cmd.execute();
			setUniqueName.add(cmd);
		}
		if (element instanceof final Group group) {
			group.getGroupElements().forEach(this::ensureUniqueName);
		}
	}

	private void fillElementList(final List<?> selection) {
		for (final Object fbNetworkElement : selection) {
			if ((fbNetworkElement instanceof final EditPart ep)
					&& (ep.getModel() instanceof final FBNetworkElement fbel)) {
				addElement(fbel);
			} else if (fbNetworkElement instanceof final FBNetworkElement fbel) {
				addElement(fbel);
			}
		}
	}

	protected void addElement(final FBNetworkElement element) {
		elementsToAdd.add(element);
		checkMapping(element);
	}

	private void checkMapping(final FBNetworkElement element) {
		if (element.isMapped()) {
			unmappingCmds.add(new UnmapCommand(element));
		}
		if (element instanceof final Group group) {
			group.getGroupElements().forEach(this::checkMapping);
		}
	}

	private void checkElementConnections(final FBNetworkElement element) {
		for (final IInterfaceElement ie : element.getInterface().getAllInterfaceElements()) {
			if (ie.isIsInput()) {
				for (final Connection con : ie.getInputConnections()) {
					checkConnection(con, con.getSource(), ie);
				}
			} else {
				for (final Connection con : ie.getOutputConnections()) {
					checkConnection(con, con.getDestination(), ie);
				}
			}
		}
		if (element instanceof final Group group) {
			group.getGroupElements().forEach(this::checkElementConnections);
		}
	}

	private void checkConnection(final Connection con, final IInterfaceElement opposite,
			final IInterfaceElement ownIE) {
		if ((opposite.getFBNetworkElement() != null) && isPartOfMove(opposite.getFBNetworkElement())) {
			moveConIntoSubApp(con);
		} else if ((opposite.getFBNetworkElement() != null) && targetSubApp.equals(opposite.getFBNetworkElement())) {
			// the connection's opposite target is within the subapp
			moveInterfaceCrossingConIntoSubApp(con, opposite, ownIE);
		} else {
			handleModifyConnection(con, ownIE);
		}
	}

	private boolean isPartOfMove(final FBNetworkElement elementToCheck) {
		if (elementsToAdd.contains(elementToCheck)) {
			return true;
		}
		// if the element is in a group check if the group is moved into the subapp
		return (elementToCheck.isInGroup() && elementsToAdd.contains(elementToCheck.getGroup()));
	}

	private void moveConIntoSubApp(final Connection con) {
		targetSubApp.getSubAppNetwork().addConnection(con);
		movedConns.add(con);
	}

	private void moveInterfaceCrossingConIntoSubApp(final Connection con, final IInterfaceElement opposite,
			final IInterfaceElement ownIE) {
		final List<Connection> internalCons = opposite.isIsInput() ? opposite.getOutputConnections()
				: opposite.getInputConnections();
		final List<Connection> outCons = opposite.isIsInput() ? opposite.getInputConnections()
				: opposite.getOutputConnections();

		if (1 == outCons.size()) {
			// our connection is the last one lets remove the interface element
			modifiedConns.add(new DeleteSubAppInterfaceElementCommand(opposite));
		} else {
			modifiedConns.add(new DeleteConnectionCommand(con));
		}

		internalCons.forEach(intConn -> {
			final AbstractConnectionCreateCommand cmd = getCreateConnectionCommand(targetSubApp.getSubAppNetwork(),
					opposite);
			cmd.setSource(opposite.isIsInput() ? ownIE : intConn.getSource());
			cmd.setDestination(opposite.isIsInput() ? intConn.getDestination() : ownIE);
			modifiedConns.add(cmd);
		});

	}

	/**
	 * we have a connection that will cross the subapp interface. Check if an
	 * interface element needs to be created and modify the connections accordingly
	 *
	 * @param con the connection to be investigated
	 * @param ie  the interface element on the inside of the subapp as reference for
	 *            creating the
	 */
	private void handleModifyConnection(final Connection con, final IInterfaceElement ie) {
		final IInterfaceElement source = con.getSource();
		// find a pin with matching source in the subapp
		final Optional<IInterfaceElement> reusablePin = targetSubApp.getInterface().getAllInterfaceElements().stream()
				.filter(pin -> pin.getInputConnections().size() == 1)
				.filter(pin -> pin.getInputConnections().get(0).getSource().equals(source)).findFirst();

		final IInterfaceElement subAppIE;
		// flag indicating if a pin is new and therefore both inside and outside
		// connections need to be created
		final boolean isNewPin = !(reusablePin.isPresent() || sourceToSubAppPin.containsKey(source));
		if (reusablePin.isPresent()) {
			// pin already exists in the target subapp (prior to command execution)
			subAppIE = reusablePin.get();
		} else if (source instanceof final VarDeclaration sourceVar && sourceVar.isInOutVar()
				&& con.getDestination() instanceof final VarDeclaration destVar && destVar.isInOutVar()) {
			if (sourceToSubAppPin.containsKey(sourceVar.getInOutVarOpposite())) {
				subAppIE = ((VarDeclaration) sourceToSubAppPin.get(sourceVar.getInOutVarOpposite()))
						.getInOutVarOpposite();
				sourceToSubAppPin.putIfAbsent(sourceVar, subAppIE);
			} else if (sourceToSubAppPin.containsKey(destVar.getInOutVarOpposite())) {
				subAppIE = ((VarDeclaration) sourceToSubAppPin.get(destVar.getInOutVarOpposite()))
						.getInOutVarOpposite();
				sourceToSubAppPin.putIfAbsent(sourceVar, subAppIE);
			} else {
				// pin has been created in the course of this command or is not present at all
				// and needs to be created
				subAppIE = sourceToSubAppPin.computeIfAbsent(source, k -> createInterfaceElement(ie, source.getName()));
			}
			sourceToSubAppPin.putIfAbsent(destVar, subAppIE);

		} else {
			// pin has been created in the course of this command or is not present at all
			// and needs to be created
			subAppIE = sourceToSubAppPin.computeIfAbsent(source, k -> createInterfaceElement(ie, source.getName()));
		}
		createConnModificationCommands(con, subAppIE, isNewPin);
	}

	private IInterfaceElement createInterfaceElement(final IInterfaceElement ie, final String srcName) {
		final boolean isInOut = ie instanceof final VarDeclaration varDecl && varDecl.isInOutVar();
		final CreateSubAppInterfaceElementCommand cmd = new CreateSubAppInterfaceElementCommand(ie.getType(), srcName,
				targetSubApp.getInterface(), ie.isIsInput(), isInOut, ArraySizeHelper.getArraySize(ie), -1);
		cmd.execute();
		changedSubAppIEs.add(cmd);
		return cmd.getCreatedElement();
	}

	private void createConnModificationCommands(final Connection con, final IInterfaceElement subAppIE,
			final boolean isNewPin) {
		modifiedConns.add(new DeleteConnectionCommand(con));
		if (isNewPin) {
			createSubAppPinConnection(targetSubApp.getFbNetwork(), subAppIE, con, false);
			createSubAppPinConnection(targetSubApp.getSubAppNetwork(), subAppIE, con, true);
		} else if (subAppIE.isIsInput()) {
			createSubAppPinConnection(targetSubApp.getSubAppNetwork(), subAppIE, con, true);
		} else {
			createSubAppPinConnection(targetSubApp.getFbNetwork(), subAppIE, con, false);
		}
	}

	private void createSubAppPinConnection(final FBNetwork network, final IInterfaceElement ie, final Connection con,
			final boolean isInsideSubApp) {
		final AbstractConnectionCreateCommand cmd = getCreateConnectionCommand(network, ie);
		if (ie.isIsInput()) {
			cmd.setSource(isInsideSubApp ? ie : con.getSource());
			cmd.setDestination(isInsideSubApp ? con.getDestination() : ie);
		} else {
			cmd.setSource(isInsideSubApp ? con.getSource() : ie);
			cmd.setDestination(isInsideSubApp ? ie : con.getDestination());
		}
		modifiedConns.add(cmd);
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

	private void collectElementsToRemoveFromGroup() {
		final Map<Group, List<FBNetworkElement>> groupMap = new HashMap<>();
		// collect all entries that are in a group and store them by group
		elementsToAdd.stream().filter(FBNetworkElement::isInGroup).forEach(el -> {
			final List<FBNetworkElement> entry = groupMap.computeIfAbsent(el.getGroup(), group -> new ArrayList<>());
			entry.add(el);
		});
		// for each entry in the map create one RemoveFromGroupCommand
		groupMap.forEach((group, list) -> removeFromOtherGroups.add(new RemoveElementsFromGroup(list)));
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(targetSubApp);
	}

	public List<FBNetworkElement> getElements() {
		return elementsToAdd;
	}

}
