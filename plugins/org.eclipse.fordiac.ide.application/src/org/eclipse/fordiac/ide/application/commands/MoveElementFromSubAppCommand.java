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
 *   Michael Jaeger - added drag and drop functionality
 *******************************************************************************/

package org.eclipse.fordiac.ide.application.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
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

public class MoveElementFromSubAppCommand extends Command {

	protected final SubApp sourceSubApp;
	protected final FBNetworkElement element;
	private final Rectangle targetRect;
	private org.eclipse.fordiac.ide.model.libraryElement.Position oldPos; // for undo
	private org.eclipse.fordiac.ide.model.libraryElement.Position newPos; // for redo
	private final CompoundCommand unmappingCmds = new CompoundCommand(); // stores all needed unmap commands
	private final CompoundCommand delecteConnectionsAndInterfaceElements = new CompoundCommand();
	private final CompoundCommand createConnectionsCommands = new CompoundCommand();
	private final CompoundCommand createSubAppInterfaceElementCommands = new CompoundCommand();
	private org.eclipse.draw2d.geometry.Point mouseMoveDelta;
	private Position side;
	private ChangeNameCommand setUniqueName;

	public enum Position {
		LEFT, RIGHT, BELOW
	}

	/** this enum shows if the command is executed either by right click on context menu or by drag and drop */
	public enum MoveOperation {
		DRAG_AND_DROP_TO_ROOT, DRAG_AND_DROP_TO_SUBAPP, CONTEXT_MENU
	}

	private MoveOperation moveOperation;

	public MoveElementFromSubAppCommand(final FBNetworkElement element, final Rectangle targetRect,
			final MoveOperation moveOperation) {
		this.sourceSubApp = (SubApp) element.getFbNetwork().eContainer();
		this.element = element;
		this.targetRect = targetRect;
		this.moveOperation = moveOperation;
		this.mouseMoveDelta = new org.eclipse.draw2d.geometry.Point(0, 0); // avoid NPE
	}

	@Override
	public boolean canExecute() {
		return element != null;
	}

	@Override
	public void execute() {
		removeElementFromSubapp();
		moveElementToParent();
	}

	protected void moveElementToParent() {
		final EList<FBNetworkElement> parentFBNetwork = sourceSubApp.getFbNetwork().getNetworkElements();
		parentFBNetwork.add(element);

		// ensure unique name in new network
		if (!NameRepository.isValidName(element, element.getName())) {
			final String uniqueName = NameRepository.createUniqueName(element, element.getName());
			setUniqueName = new ChangeNameCommand(element, uniqueName);
			setUniqueName.execute();
		}

		positionElement(element, side);
		createConnectionsCommands.execute();
	}

	protected void removeElementFromSubapp() {
		oldPos = element.getPosition();
		if (element.isMapped()) {
			unmappingCmds.add(new UnmapCommand(element));
		}
		unmappingCmds.execute();
		final EList<FBNetworkElement> sourceFBNetwork = sourceSubApp.getSubAppNetwork().getNetworkElements();

		sourceFBNetwork.remove(element);
		side = checkElementConnections(element);
		delecteConnectionsAndInterfaceElements.execute();
	}

	@Override
	public void redo() {
		redoMoveToParent();
		redoRemoveFromSubapp();
	}

	protected void redoRemoveFromSubapp() {
		delecteConnectionsAndInterfaceElements.redo();
		sourceSubApp.getSubAppNetwork().getNetworkElements().remove(element);
	}

	protected void redoMoveToParent() {
		unmappingCmds.redo();
		sourceSubApp.getFbNetwork().getNetworkElements().add(element);
		if (null != setUniqueName) {
			setUniqueName.redo();
		}
		createSubAppInterfaceElementCommands.redo();
		createConnectionsCommands.redo();
		element.setPosition(newPos);
	}

	@Override
	public void undo() {
		undoRemoveFromSubApp();
		undoMoveToParent();
	}

	protected void undoMoveToParent() {
		createConnectionsCommands.undo();
		if (setUniqueName != null) {
			setUniqueName.undo();
		}
		createSubAppInterfaceElementCommands.undo();
		unmappingCmds.undo();
		element.setPosition(oldPos);
	}

	protected void undoRemoveFromSubApp() {
		newPos = element.getPosition();
		delecteConnectionsAndInterfaceElements.undo();
		sourceSubApp.getSubAppNetwork().getNetworkElements().add(element);
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

	private void positionElement(final FBNetworkElement element, final Position pos) {
		switch (moveOperation) {
		case DRAG_AND_DROP_TO_ROOT:
			element.updatePosition(
					(sourceSubApp.getPosition().getX() + mouseMoveDelta.x + element.getPosition().getX()),
					(sourceSubApp.getPosition().getY() + mouseMoveDelta.y + element.getPosition().getY()));
			break;
		case DRAG_AND_DROP_TO_SUBAPP:
		case CONTEXT_MENU:
			final int FB_DISTANCE = 20;
			final int FB_DISTANCE_LEFT = 70;
			int xOffset = 0;
			int yOffset = 0;
			switch (pos) {
			case LEFT:
				xOffset = -FB_DISTANCE_LEFT;
				break;
			case RIGHT:
				xOffset = FB_DISTANCE + targetRect.width;
				break;
			case BELOW:
				yOffset = FB_DISTANCE + targetRect.height;
				break;
			default:
				break;
			}
			element.updatePosition((sourceSubApp.getPosition().getX() + xOffset),
					(sourceSubApp.getPosition().getY() + yOffset));
			break;
		default:
			break;

		}

	}

	private Position checkElementConnections(final FBNetworkElement fbNetworkElement) {
		final List<Position> posList = new ArrayList<>();
		for (final IInterfaceElement ie : fbNetworkElement.getInterface().getAllInterfaceElements()) {
			if (ie.isIsInput()) {
				for (final Connection con : ie.getInputConnections()) {
					posList.add(checkConnection(con, con.getSource(), ie));
				}
			} else {
				for (final Connection con : ie.getOutputConnections()) {
					posList.add(checkConnection(con, con.getDestination(), ie));
				}
			}
		}
		return checkPosition(posList);
	}

	/* return the position where the FB should be placed in the parent network if all connections indicate a side, its
	 * returned otherwise Position.Below is returned */
	private static Position checkPosition(final List<Position> posList) {
		Position pos = null;
		for (final Position p : posList) {
			if (pos == null) {
				pos = p;
			} else if (!pos.equals(p)) {
				pos = Position.BELOW;
				break;
			}
		}
		return pos == null ? Position.BELOW : pos;
	}

	private Position checkConnection(final Connection con, final IInterfaceElement opposite,
			final IInterfaceElement ie) {
		if ((opposite.getFBNetworkElement() instanceof SubApp) && opposite.getFBNetworkElement().equals(sourceSubApp)) {
			handleCrossingConnections(con, opposite, ie.isIsInput());
			return ie.isIsInput() ? Position.LEFT : Position.RIGHT;
		}
		handleInnerConnections(con, ie, ie.isIsInput());
		return ie.isIsInput() ? Position.RIGHT : Position.LEFT;
	}

	private void handleInnerConnections(final Connection con, final IInterfaceElement ie, final boolean isInput) {
		final String subAppIEName = generateSubAppIEName(ie);
		IInterfaceElement subAppIE = sourceSubApp.getInterfaceElement(subAppIEName);
		if (null == subAppIE) {
			// negating "isInput" results in an output pin for input connections and vice
			// versa
			subAppIE = createInterfaceElement(ie, subAppIEName, !isInput);
		}

		delecteConnectionsAndInterfaceElements.add(new DeleteConnectionCommand(con));

		createConnection(isInput ? con.getSource() : subAppIE, isInput ? subAppIE : con.getDestination(),
				con.getSource(), sourceSubApp.getSubAppNetwork());
		createConnection(isInput ? subAppIE : con.getSource(), isInput ? con.getDestination() : subAppIE,
				con.getDestination(), sourceSubApp.getFbNetwork());
	}

	private void handleCrossingConnections(final Connection con, final IInterfaceElement opposite,
			final boolean isInput) {

		final List<Connection> internalCons = isInput ? opposite.getOutputConnections()
				: opposite.getInputConnections();
		final List<Connection> outCons = isInput ? opposite.getInputConnections() : opposite.getOutputConnections();

		if (1 == internalCons.size()) {
			// our connection is the last one lets remove the interface element
			delecteConnectionsAndInterfaceElements.add(new DeleteSubAppInterfaceElementCommand(opposite));
			for (final Connection outCon : outCons) {
				delecteConnectionsAndInterfaceElements.add(new DeleteConnectionCommand(outCon));
				createConnection(isInput ? outCon.getSource() : con.getSource(),
						isInput ? con.getDestination() : outCon.getDestination(), con.getDestination(),
								sourceSubApp.getFbNetwork());
			}
		} else {
			delecteConnectionsAndInterfaceElements.add(new DeleteConnectionCommand(con));
			for (final Connection outCon : outCons) {
				delecteConnectionsAndInterfaceElements.add(new DeleteConnectionCommand(outCon));
				createConnection(outCon.getSource(), outCon.getDestination(), con.getDestination(),
						sourceSubApp.getFbNetwork());
				createConnection(isInput ? outCon.getSource() : con.getSource(),
						isInput ? con.getDestination() : outCon.getDestination(), con.getDestination(),
								sourceSubApp.getFbNetwork());
			}
		}
	}

	private void createConnection(final IInterfaceElement source, final IInterfaceElement destination,
			final IInterfaceElement subappIE, final FBNetwork network) {
		final AbstractConnectionCreateCommand cmd = getCreateConnectionCommand(network, subappIE);
		cmd.setSource(source);
		cmd.setDestination(destination);
		createConnectionsCommands.add(cmd);
	}

	private IInterfaceElement createInterfaceElement(final IInterfaceElement ie, final String subAppIEName,
			final boolean isInput) {
		final CreateSubAppInterfaceElementCommand createSubAppInterfaceElementCommand = new CreateSubAppInterfaceElementCommand(
				ie.getType(), sourceSubApp.getInterface(), isInput, -1);
		createSubAppInterfaceElementCommand.execute();
		createSubAppInterfaceElementCommand.getInterfaceElement().setName(subAppIEName);
		if (null != createSubAppInterfaceElementCommand.getMirroredElement()) {
			createSubAppInterfaceElementCommand.getMirroredElement().getInterfaceElement().setName(subAppIEName);
		}
		createSubAppInterfaceElementCommands.add(createSubAppInterfaceElementCommand);
		return createSubAppInterfaceElementCommand.getInterfaceElement();
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

	public void setMouseMoveDelta(final org.eclipse.draw2d.geometry.Point mouseMoveDelta) {
		this.mouseMoveDelta = mouseMoveDelta;
	}

	public void setMoveOperation(final MoveOperation moveOperation) {
		this.moveOperation = moveOperation;
	}

}
