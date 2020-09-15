/*******************************************************************************
 * Copyright (c) 2018-2020 Johannes Kepler University
 * 				 2020 Primetals Technologies Germany GmbH
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
 *******************************************************************************/

package org.eclipse.fordiac.ide.application.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.commands.change.UnmapCommand;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.AdapterConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.swt.graphics.Point;

public class MoveElementFromSubappCommand extends Command {

	private final SubApp sourceSubApp;
	private final FBNetworkElement element;
	private final Rectangle bounds;
	private Point oldPos; // for undo
	private Point newPos; // for redo
	private final CompoundCommand unmappingCmds = new CompoundCommand(); // stores all needed unmap commands
	private final List<Connection> movedConns = new ArrayList<>();
	private final CompoundCommand modifiedConns = new CompoundCommand();
	private final CompoundCommand changedSubAppIEs = new CompoundCommand();

	private Position side;

	public enum Position {
		LEFT, RIGHT, BELOW
	}

	public MoveElementFromSubappCommand(SubApp sourceSubApp, FBNetworkElement element, Rectangle bounds) {
		this.sourceSubApp = sourceSubApp;
		this.element = element;
		this.bounds = bounds;
	}

	@Override
	public boolean canExecute() {
		return element != null;
	}

	@Override
	public void execute() {
		oldPos = new Point(element.getX(), element.getY());
		if (element.isMapped()) {
			unmappingCmds.add(new UnmapCommand(element));
		}
		unmappingCmds.execute();
		EList<FBNetworkElement> sourceFBNetwork = sourceSubApp.getSubAppNetwork().getNetworkElements();
		EList<FBNetworkElement> parentFBNetwork = sourceSubApp.getFbNetwork().getNetworkElements();
		sourceFBNetwork.remove(element);
		parentFBNetwork.add(element);
		side = checkElementConnections(element);
		positionElement(element, side);
		modifiedConns.execute();
	}

	@Override
	public void redo() {
		unmappingCmds.redo();
		sourceSubApp.getFbNetwork().getNetworkElements().add(element);
		movedConns.forEach(con -> sourceSubApp.getFbNetwork().addConnection(con));
		changedSubAppIEs.redo();
		modifiedConns.redo();
		element.setX(newPos.x);
		element.setY(newPos.y);
	}

	@Override
	public void undo() {
		newPos = new Point(element.getX(), element.getY());
		modifiedConns.undo();
		changedSubAppIEs.undo();
		movedConns.forEach(con -> sourceSubApp.getSubAppNetwork().addConnection(con));
		sourceSubApp.getSubAppNetwork().getNetworkElements().add(element);
		unmappingCmds.undo();
		element.setX(oldPos.x);
		element.setY(oldPos.y);
	}

	public Position getSide() {
		if (side != null) {
			return side;
		}
		return null;
	}

	public FBNetworkElement getElement() {
		return element;
	}

	private void positionElement(FBNetworkElement element, Position pos) {
		final int FB_DISTANCE = 20;
		final int FB_DISTANCE_LEFT = 70;
		int xOffset = 0;
		int yOffset = 0;
		switch (pos) {
		case LEFT:
			xOffset = -FB_DISTANCE_LEFT;
			break;
		case RIGHT:
			xOffset = FB_DISTANCE + bounds.width;
			break;
		case BELOW:
			yOffset = FB_DISTANCE + bounds.height;
			break;
		}
		element.setX(sourceSubApp.getX() + xOffset);
		element.setY(sourceSubApp.getY() + yOffset);
	}

	private Position checkElementConnections(FBNetworkElement fbNetworkElement) {
		List<Position> posList = new ArrayList<>();
		for (IInterfaceElement ie : fbNetworkElement.getInterface().getAllInterfaceElements()) {
			if (ie.isIsInput()) {
				for (Connection con : ie.getInputConnections()) {
					posList.add(checkConnection(con, con.getSource(), ie));
				}
			} else {
				for (Connection con : ie.getOutputConnections()) {
					posList.add(checkConnection(con, con.getDestination(), ie));
				}
			}
		}
		return checkPosition(posList);
	}

	/*
	 * return the position where the FB should be placed in the parent network
	 * if all connections indicate a side, its returned
	 * otherwise Position.Below is returned
	 */
	private Position checkPosition(List<Position> posList) {
		Position pos = null;
		for (Position p : posList) {
			if (pos == null) {
				pos = p;
			} else if (!pos.equals(p)) {
				pos = Position.BELOW;
				break;
			}
		}
		return pos == null ? Position.BELOW : pos;
	}

	private Position checkConnection(Connection con, IInterfaceElement opposite, IInterfaceElement ie) {
		if (opposite.getFBNetworkElement() instanceof SubApp) {
			handleCrossingConnections(con, opposite, ie.isIsInput());
			return ie.isIsInput() ? Position.LEFT : Position.RIGHT;
		} else {
			handleInnerConnections(con, ie, ie.isIsInput());
			return ie.isIsInput() ? Position.RIGHT : Position.LEFT;
		}
	}

	private void handleInnerConnections(Connection con, IInterfaceElement ie, boolean isInput) {
		String subAppIEName = generateSubAppIEName(ie);
		IInterfaceElement subAppIE = sourceSubApp.getInterfaceElement(subAppIEName);
		if (null == subAppIE) {
			// negating "isInput" results in an output pin for input connections and vice
			// versa
			subAppIE = createInterfaceElement(ie, subAppIEName, !isInput);
		}

		modifiedConns.add(new DeleteConnectionCommand(con));

		createConnection(isInput ? con.getSource() : subAppIE, isInput ? subAppIE : con.getDestination(),
				con.getSource(), sourceSubApp.getSubAppNetwork());
		createConnection(isInput ? subAppIE : con.getSource(), isInput ? con.getDestination() : subAppIE,
				con.getDestination(), sourceSubApp.getFbNetwork());
	}

	private void handleCrossingConnections(Connection con, IInterfaceElement opposite, boolean isInput) {

		List<Connection> internalCons = isInput ? opposite.getOutputConnections() : opposite.getInputConnections();
		List<Connection> outCons = isInput ? opposite.getInputConnections() : opposite.getOutputConnections();

		if (1 == internalCons.size()) {
			// our connection is the last one lets remove the interface element
			modifiedConns.add(new DeleteSubAppInterfaceElementCommand(opposite));
			for (Connection outCon : outCons) {
				modifiedConns.add(new DeleteConnectionCommand(outCon));
				createConnection(isInput ? outCon.getSource() : con.getSource(),
						isInput ? con.getDestination() : outCon.getDestination(), con.getDestination(),
						sourceSubApp.getFbNetwork());
			}
		} else {
			modifiedConns.add(new DeleteConnectionCommand(con));
			for (Connection outCon : outCons) {
				modifiedConns.add(new DeleteConnectionCommand(outCon));
				createConnection(outCon.getSource(), outCon.getDestination(), con.getDestination(),
						sourceSubApp.getFbNetwork());
				createConnection(isInput ? outCon.getSource() : con.getSource(),
						isInput ? con.getDestination() : outCon.getDestination(), con.getDestination(),
						sourceSubApp.getFbNetwork());
			}
		}
	}
	
	private void createConnection(IInterfaceElement source, IInterfaceElement destination, IInterfaceElement subappIE,
			FBNetwork network) {
		AbstractConnectionCreateCommand cmd = getCreateConnectionCommand(network, subappIE);
		cmd.setSource(source);
		cmd.setDestination(destination);
		modifiedConns.add(cmd);
	}

	private IInterfaceElement createInterfaceElement(IInterfaceElement ie, String subAppIEName, boolean isInput) {
		CreateSubAppInterfaceElementCommand cmd = new CreateSubAppInterfaceElementCommand(ie.getType(),
				sourceSubApp.getInterface(), isInput, -1);
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
