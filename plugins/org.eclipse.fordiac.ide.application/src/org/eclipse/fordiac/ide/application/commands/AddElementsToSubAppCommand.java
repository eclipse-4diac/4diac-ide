/*******************************************************************************
 * Copyright (c) 2018-2020 Johannes Kepler University
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
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
	private final CompoundCommand unmappingCmds = new CompoundCommand(); // stores all needed unmap commands
	private final List<Connection> movedConns = new ArrayList<>();
	private final CompoundCommand modifiedConns = new CompoundCommand();
	private final CompoundCommand changedSubAppIEs = new CompoundCommand();
	private org.eclipse.swt.graphics.Point offset;

	public AddElementsToSubAppCommand(SubApp targetSubApp, List<?> selection) {
		this.targetSubApp = targetSubApp;
		fillElementList(selection);
	}

	@Override
	public boolean canExecute() {
		return !elementsToAdd.isEmpty() && targetSubappIsInSameFbNetwork();
	}

	private boolean targetSubappIsInSameFbNetwork() {
		for (FBNetworkElement block : elementsToAdd) {
			if (!block.getFbNetwork().getNetworkElements().contains(targetSubApp)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void execute() {
		unmappingCmds.execute();
		EList<FBNetworkElement> fbNetwork = targetSubApp.getSubAppNetwork().getNetworkElements();
		offset = FBNetworkHelper.removeXYOffsetForFBNetwork(elementsToAdd);
		for (FBNetworkElement fbNetworkElement : elementsToAdd) {
			fbNetwork.add(fbNetworkElement);
			checkElementConnections(fbNetworkElement);
		}
		modifiedConns.execute();
	}

	@Override
	public void redo() {
		unmappingCmds.redo();
		FBNetworkHelper.removeXYOffsetForFBNetwork(elementsToAdd);
		elementsToAdd.forEach(element -> targetSubApp.getSubAppNetwork().getNetworkElements().add(element));
		movedConns.forEach(con -> targetSubApp.getSubAppNetwork().addConnection(con));
		changedSubAppIEs.redo();
		modifiedConns.redo();
	}

	@Override
	public void undo() {
		modifiedConns.undo();
		changedSubAppIEs.undo();
		movedConns.forEach(con -> targetSubApp.getFbNetwork().addConnection(con));

		FBNetworkHelper.moveFBNetworkByOffset(elementsToAdd, getOriginalPositionX(), getOriginalPositionY());

		elementsToAdd.forEach(element -> targetSubApp.getFbNetwork().getNetworkElements().add(element));
		unmappingCmds.undo();
	}

	private int getOriginalPositionX() {
		return -offset.x + FBNetworkHelper.X_OFFSET_FROM_TOP_LEFT_CORNER;
	}

	private int getOriginalPositionY() {
		return -offset.y + FBNetworkHelper.Y_OFFSET_FROM_TOP_LEFT_CORNER;
	}

	private void fillElementList(List<?> selection) {
		for (Object ne : selection) {
			if ((ne instanceof EditPart) && (((EditPart) ne).getModel() instanceof FBNetworkElement)) {
				FBNetworkElement element = (FBNetworkElement) ((EditPart) ne).getModel();
				elementsToAdd.add(element);
				if (element.isMapped()) {
					unmappingCmds.add(new UnmapCommand(element));
				}
			}
		}
	}

	private void checkElementConnections(FBNetworkElement fbNetworkElement) {
		for (IInterfaceElement ie : fbNetworkElement.getInterface().getAllInterfaceElements()) {
			if (ie.isIsInput()) {
				for (Connection con : ie.getInputConnections()) {
					checkConnection(con, con.getSource(), ie);
				}
			} else {
				for (Connection con : ie.getOutputConnections()) {
					checkConnection(con, con.getDestination(), ie);
				}
			}
		}

	}

	private void checkConnection(Connection con, IInterfaceElement opposite, IInterfaceElement ownIE) {
		if (elementsToAdd.contains(opposite.getFBNetworkElement())) {
			moveConIntoSubApp(con);
		} else if (targetSubApp.equals(opposite.getFBNetworkElement())) {
			// the connection's opposite target is within the subapp
			moveInterfaceCrossingConIntoSubApp(con, opposite, ownIE);
		} else {
			handleModifyConnection(con, ownIE);
		}
	}

	private void moveConIntoSubApp(Connection con) {
		targetSubApp.getSubAppNetwork().addConnection(con);
		movedConns.add(con);
	}

	private void moveInterfaceCrossingConIntoSubApp(Connection con, IInterfaceElement opposite,
			IInterfaceElement ownIE) {
		List<Connection> internalCons = opposite.isIsInput() ? opposite.getOutputConnections()
				: opposite.getInputConnections();
		List<Connection> outCons = opposite.isIsInput() ? opposite.getInputConnections()
				: opposite.getOutputConnections();

		if (1 == outCons.size()) {
			// our connection is the last one lets remove the interface element
			modifiedConns.add(new DeleteSubAppInterfaceElementCommand(opposite));
		} else {
			modifiedConns.add(new DeleteConnectionCommand(con));
		}

		internalCons.forEach(intConn -> {
			AbstractConnectionCreateCommand cmd = getCreateConnectionCommand(targetSubApp.getSubAppNetwork(), opposite);
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
	private void handleModifyConnection(Connection con, IInterfaceElement ie) {
		String subAppIEName = generateSubAppIEName(ie);
		IInterfaceElement subAppIE = targetSubApp.getInterfaceElement(subAppIEName);
		boolean addInternalCon = false;
		if (null == subAppIE) {
			// we need to create a subAppInterface element
			subAppIE = createInterfaceElement(ie, subAppIEName);
			addInternalCon = true;
		}
		createConnModificationCommands(con, subAppIE, addInternalCon);
	}

	private IInterfaceElement createInterfaceElement(IInterfaceElement ie, String subAppIEName) {
		CreateSubAppInterfaceElementCommand cmd = new CreateSubAppInterfaceElementCommand(ie.getType(),
				targetSubApp.getInterface(), ie.isIsInput(), -1);
		cmd.execute();
		cmd.getInterfaceElement().setName(subAppIEName);
		if (null != cmd.getMirroredElement()) {
			cmd.getMirroredElement().getInterfaceElement().setName(subAppIEName);
		}
		changedSubAppIEs.add(cmd);
		return cmd.getInterfaceElement();
	}

	private static String generateSubAppIEName(IInterfaceElement ie) {
		return ie.getFBNetworkElement().getName() + "_" + ie.getName(); //$NON-NLS-1$
	}

	void createConnModificationCommands(Connection con, IInterfaceElement subAppIE, boolean addInternalCon) {
		modifiedConns.add(new DeleteConnectionCommand(con));

		AbstractConnectionCreateCommand connectionCreateCmd = getCreateConnectionCommand(targetSubApp.getFbNetwork(),
				subAppIE);
		if (subAppIE.isIsInput()) {
			connectionCreateCmd.setSource(con.getSource());
			connectionCreateCmd.setDestination(subAppIE);
		} else {
			connectionCreateCmd.setSource(subAppIE);
			connectionCreateCmd.setDestination(con.getDestination());
		}
		modifiedConns.add(connectionCreateCmd);

		if (addInternalCon) {
			AbstractConnectionCreateCommand internalConCreateCmd = getCreateConnectionCommand(
					targetSubApp.getSubAppNetwork(), subAppIE);
			if (subAppIE.isIsInput()) {
				internalConCreateCmd.setSource(subAppIE);
				internalConCreateCmd.setDestination(con.getDestination());
			} else {
				internalConCreateCmd.setSource(con.getSource());
				internalConCreateCmd.setDestination(subAppIE);
			}
			modifiedConns.add(internalConCreateCmd);
		}
	}

	private static AbstractConnectionCreateCommand getCreateConnectionCommand(FBNetwork fbNetwork,
			IInterfaceElement subAppIE) {
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
