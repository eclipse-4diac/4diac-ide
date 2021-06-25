/*******************************************************************************
 * Copyright (c) 2018-2020 Johannes Kepler University,
 * 				 2021 Primetals Technologies Austria GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.change.UnmapCommand;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.AdapterConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class AddElementsToSubAppCommand extends Command {

	private final SubApp targetSubApp;
	private final List<FBNetworkElement> elementsToAdd = new ArrayList<>();
	private final Map<IInterfaceElement, IInterfaceElement> sourceToSubAppPin = new HashMap<>();
	private final CompoundCommand unmappingCmds = new CompoundCommand(); // stores all needed unmap commands
	private final List<Connection> movedConns = new ArrayList<>();
	private final CompoundCommand modifiedConns = new CompoundCommand();
	private final CompoundCommand changedSubAppIEs = new CompoundCommand();
	private final CompoundCommand setUniqueName = new CompoundCommand();
	private org.eclipse.swt.graphics.Point offset;

	public AddElementsToSubAppCommand(final SubApp targetSubApp, final List<?> selection) {
		this.targetSubApp = targetSubApp;
		fillElementList(selection);
	}

	@Override
	public boolean canExecute() {
		return !elementsToAdd.isEmpty() && targetSubappIsInSameFbNetwork();
	}

	private boolean targetSubappIsInSameFbNetwork() {
		for (final FBNetworkElement block : elementsToAdd) {
			if (!block.getFbNetwork().getNetworkElements().contains(targetSubApp)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void execute() {
		unmappingCmds.execute();
		final EList<FBNetworkElement> fbNetwork = targetSubApp.getSubAppNetwork().getNetworkElements();
		offset = FBNetworkHelper.removeXYOffsetForFBNetwork(elementsToAdd);
		for (final FBNetworkElement fbNetworkElement : elementsToAdd) {
			fbNetwork.add(fbNetworkElement);
			checkElementConnections(fbNetworkElement);
			ensureUniqueName(fbNetworkElement);
		}
		setUniqueName.execute();
		modifiedConns.execute();
	}

	private void ensureUniqueName(final FBNetworkElement element) {
		// ensure unique name in new network
		if (!NameRepository.isValidName(element, element.getName())) {
			final String uniqueName = NameRepository.createUniqueName(element, element.getName());
			setUniqueName.add(new ChangeNameCommand(element, uniqueName));
		}
	}

	@Override
	public void redo() {
		unmappingCmds.redo();
		FBNetworkHelper.removeXYOffsetForFBNetwork(elementsToAdd);
		elementsToAdd.forEach(element -> targetSubApp.getSubAppNetwork().getNetworkElements().add(element));
		movedConns.forEach(con -> targetSubApp.getSubAppNetwork().addConnection(con));
		changedSubAppIEs.redo();
		setUniqueName.redo();
		modifiedConns.redo();
	}

	@Override
	public void undo() {
		modifiedConns.undo();
		changedSubAppIEs.undo();
		movedConns.forEach(con -> targetSubApp.getFbNetwork().addConnection(con));

		FBNetworkHelper.moveFBNetworkByOffset(elementsToAdd, getOriginalPositionX(), getOriginalPositionY());

		elementsToAdd.forEach(element -> targetSubApp.getFbNetwork().getNetworkElements().add(element));
		setUniqueName.undo();
		unmappingCmds.undo();
	}

	private int getOriginalPositionX() {
		return offset.x - FBNetworkHelper.X_OFFSET_FROM_TOP_LEFT_CORNER;
	}

	private int getOriginalPositionY() {
		return offset.y - FBNetworkHelper.Y_OFFSET_FROM_TOP_LEFT_CORNER;
	}

	private void fillElementList(final List<?> selection) {
		for (final Object fbNetworkElement : selection) {
			if ((fbNetworkElement instanceof EditPart)
					&& (((EditPart) fbNetworkElement).getModel() instanceof FBNetworkElement)) {
				addElement((FBNetworkElement) ((EditPart) fbNetworkElement).getModel());
			} else if (fbNetworkElement instanceof FBNetworkElement) {
				addElement((FBNetworkElement) fbNetworkElement);
			}
		}
	}

	protected void addElement(final FBNetworkElement element) {
		elementsToAdd.add(element);
		if (element.isMapped()) {
			unmappingCmds.add(new UnmapCommand(element));
		}
	}

	private void checkElementConnections(final FBNetworkElement fbNetworkElement) {
		for (final IInterfaceElement ie : fbNetworkElement.getInterface().getAllInterfaceElements()) {
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

	}

	private void checkConnection(final Connection con, final IInterfaceElement opposite,
			final IInterfaceElement ownIE) {
		if ((opposite.getFBNetworkElement() != null) && elementsToAdd.contains(opposite.getFBNetworkElement())) {
			moveConIntoSubApp(con);
		} else if ((opposite.getFBNetworkElement() != null) && targetSubApp.equals(opposite.getFBNetworkElement())) {
			// the connection's opposite target is within the subapp
			moveInterfaceCrossingConIntoSubApp(con, opposite, ownIE);
		} else {
			handleModifyConnection(con, ownIE);
		}
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

	/** we have a connection that will cross the subapp interface. Check if an interface element needs to be created and
	 * modify the connections accordingly
	 *
	 * @param con the connection to be investigated
	 * @param ie  the interface element on the inside of the subapp as reference for creating the */
	private void handleModifyConnection(final Connection con, final IInterfaceElement ie) {
		final IInterfaceElement source = con.getSource();
		// find a pin with matching source in the subapp
		final Optional<IInterfaceElement> reusablePin = targetSubApp.getInterface().getAllInterfaceElements().stream()
			.filter(pin -> pin.getInputConnections().size() == 1)
			.filter(pin -> pin.getInputConnections().get(0).getSource().equals(source))
			.findFirst();
		
		final IInterfaceElement subAppIE;
		// flag indicating if a pin is new and therefore both inside and outside connections need to be created
		final boolean isNewPin = !(reusablePin.isPresent() || sourceToSubAppPin.containsKey(source));
		if (reusablePin.isPresent()) {
			// pin already exists in the target subapp (prior to command execution)
			subAppIE = reusablePin.get();
		} else {
			// pin has been created in the course of this command or is not present at all and needs to be created
			subAppIE = sourceToSubAppPin.computeIfAbsent(source, k -> createInterfaceElement(ie, generateSubAppIEName(ie)));
		}
		createConnModificationCommands(con, subAppIE, isNewPin);
	}

	private IInterfaceElement createInterfaceElement(final IInterfaceElement ie, final String subAppIEName) {
		final CreateSubAppInterfaceElementCommand cmd = new CreateSubAppInterfaceElementCommand(ie.getType(),
				targetSubApp.getInterface(), ie.isIsInput(), -1);
		cmd.execute();
		cmd.getCreatedElement().setName(subAppIEName);
		if (null != cmd.getMirroredElement()) {
			cmd.getMirroredElement().getCreatedElement().setName(subAppIEName);
		}
		changedSubAppIEs.add(cmd);
		return cmd.getCreatedElement();
	}

	private static String generateSubAppIEName(final IInterfaceElement ie) {
		return ie.getFBNetworkElement().getName() + "_" + ie.getName(); //$NON-NLS-1$
	}

	private void createConnModificationCommands(final Connection con, final IInterfaceElement subAppIE, boolean isNewPin) {
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

	private void createSubAppPinConnection(FBNetwork network, IInterfaceElement ie, Connection con, boolean isInsideSubApp) {
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

}
