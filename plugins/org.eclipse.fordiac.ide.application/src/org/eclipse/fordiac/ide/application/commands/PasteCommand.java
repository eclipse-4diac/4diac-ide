/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, AIT, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Filip Andren, Matthias Plasch
 *   - initial API and implementation and/or initial documentation
 *   Alois Zoitl - reworked paste to also handle cut elements
 *   Fabio Gandolfi - fixed pasting and positioning of different networks
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.gef.utilities.ElementSelector;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.AdapterConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.errormessages.ErrorMessenger;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.swt.graphics.Point;

/**
 * The Class PasteCommand.
 */
public class PasteCommand extends Command {

	private static final int DEFAULT_DELTA = 20;
	private final Collection<? extends Object> templates;
	private final FBNetwork dstFBNetwork;
	private FBNetwork srcFBNetwork = null;

	private final Map<FBNetworkElement, FBNetworkElement> copiedElements = new HashMap<>();

	private final List<FBNetworkElement> elementsToCopy = new ArrayList<>();

	private final Set<ConnectionReference> connectionsToCopy = new HashSet<>();

	private final CompoundCommand connCreateCmds = new CompoundCommand();

	private int xDelta;
	private int yDelta;
	private boolean calcualteDelta = false;
	private Point pasteRefPos;

	private CutAndPasteFromSubAppCommand cutPasteCmd;

	/**
	 * Instantiates a new paste command.
	 *
	 * @param templates   the elements that should be copied to the destination
	 * @param destination the destination fbnetwork where the elements should be
	 *                    copied to
	 * @param pasteRefPos the reference position for pasting the elements
	 */
	public PasteCommand(final List<? extends Object> templates, final FBNetwork destination, final Point pasteRefPos) {
		this.templates = templates;
		this.dstFBNetwork = destination;
		this.pasteRefPos = pasteRefPos;
		calcualteDelta = true;

	}

	public PasteCommand(final List<? extends Object> templates, final FBNetwork destination, final int copyDeltaX,
			final int copyDeltaY) {
		this.templates = templates;
		this.dstFBNetwork = destination;
		xDelta = copyDeltaX;
		yDelta = copyDeltaY;
	}

	@Override
	public boolean canExecute() {
		return (null != templates) && (null != dstFBNetwork);
	}

	@Override
	public void execute() {
		if (dstFBNetwork != null) {
			ErrorMessenger.pauseMessages();
			gatherCopyData();
			copyFBs();
			copyConnections();
			ElementSelector.selectViewObjects(copiedElements.values());
			if (!ErrorMessenger.unpauseMessages().isEmpty()) {
				ErrorMessenger.popUpErrorMessage(
						Messages.PasteRecreateNotPossible,
						ErrorMessenger.USE_DEFAULT_TIMEOUT);
			}
		}
	}

	@Override
	public void undo() {
		connCreateCmds.undo();
		dstFBNetwork.getNetworkElements().removeAll(copiedElements.values());
		if (cutPasteCmd != null) {
			cutPasteCmd.undo();
		}
		ElementSelector.selectViewObjects(templates);

	}

	@Override
	public void redo() {
		dstFBNetwork.getNetworkElements().addAll(copiedElements.values());
		connCreateCmds.redo();
		if (cutPasteCmd != null) {
			cutPasteCmd.redo();
		}
		ElementSelector.selectViewObjects(copiedElements.values());
	}

	private void gatherCopyData() {
		int x = Integer.MAX_VALUE;
		int y = Integer.MAX_VALUE;

		for (final Object object : templates) {
			if (object instanceof FBNetworkElement) {
				final FBNetworkElement element = (FBNetworkElement) object;
				if (null == srcFBNetwork) {
					srcFBNetwork = element.getFbNetwork();
				}
				elementsToCopy.add(element);
				final Position outermostPos = getPositionOfOutermostNetwork(element);
				x = Math.min(x, outermostPos.getX());
				y = Math.min(y, outermostPos.getY());
			} else if (object instanceof ConnectionReference) {
				connectionsToCopy.add((ConnectionReference) object);
			} else if (object instanceof FBNetwork) {
				srcFBNetwork = (FBNetwork) object;
			}
		}

		updateDelta(x, y);
	}

	private void updateDelta(final int x, final int y) {
		if (calcualteDelta) {
			if (null != pasteRefPos) {
				xDelta = pasteRefPos.x - x;
				yDelta = pasteRefPos.y - y;
			} else {
				xDelta = DEFAULT_DELTA;
				yDelta = DEFAULT_DELTA;
			}
		}
	}

	private static Position getPositionOfOutermostNetwork(final FBNetworkElement element) {
		final Position pos = LibraryElementFactory.eINSTANCE.createPosition();
		pos.setX(element.getPosition().getX());
		pos.setY(element.getPosition().getY());
		FBNetworkElement parent;
		if (element.getGroup() != null) {
			parent = element.getGroup();
		} else {
			parent = element.getOuterFBNetworkElement();
		}

		while (parent != null) {
			pos.setX(pos.getX() + parent.getPosition().getX());
			pos.setY(pos.getY() + parent.getPosition().getY());
			if (parent.getGroup() != null) {
				parent = parent.getGroup();
			} else {
				parent = parent.getOuterFBNetworkElement();
			}
		}
		return pos;
	}

	private void copyFBs() {
		for (final FBNetworkElement element : elementsToCopy) {
			copyAndCreateFB(element);
		}
	}

	private FBNetworkElement copyAndCreateFB(final FBNetworkElement element) {
		return copyAndCreateFB(element, false);
	}

	private FBNetworkElement copyAndCreateFB(final FBNetworkElement element, final boolean isNested) {
		final FBNetworkElement copiedElement = createElementCopyFB(element, isNested);
		copiedElements.put(element, copiedElement);
		dstFBNetwork.getNetworkElements().add(copiedElement);
		copiedElement.setName(NameRepository.createUniqueName(copiedElement, element.getName()));
		return copiedElement;
	}


	private FBNetworkElement createElementCopyFB(final FBNetworkElement element, final boolean isNested) {
		final FBNetworkElement copiedElement = EcoreUtil.copy(element);
		// clear the connection references
		for (final IInterfaceElement ie : copiedElement.getInterface().getAllInterfaceElements()) {
			if (ie.isIsInput()) {
				ie.getInputConnections().clear();
			} else {
				ie.getOutputConnections().clear();
			}
		}

		if (!isNested) {
			copiedElement.setPosition(calculatePastePos(element));
		}
		copiedElement.setMapping(null);

		if (copiedElement instanceof StructManipulator) {
			// structmanipulators may destroy the param values during copy
			checkDataValues(element, copiedElement);
		}

		// copy content of Groups
		if (element instanceof Group) {
			final Group group = (Group) element;
			group.getFbNetwork().getEventConnections()
					.forEach(con -> connectionsToCopy.add(new ConnectionReference(con)));
			group.getFbNetwork().getDataConnections()
					.forEach(con -> connectionsToCopy.add(new ConnectionReference(con)));
			group.getFbNetwork().getAdapterConnections()
					.forEach(con -> connectionsToCopy.add(new ConnectionReference(con)));

			for (final FBNetworkElement groupElement : group.getGroupElements()) {
				((Group) copiedElement).getGroupElements().add(copyAndCreateFB(groupElement, true));
			}
		}

		return copiedElement;
	}

	private static void checkDataValues(final FBNetworkElement src, final FBNetworkElement copy) {
		final EList<VarDeclaration> srcList = src.getInterface().getInputVars();
		final EList<VarDeclaration> copyList = copy.getInterface().getInputVars();

		for (int i = 0; i < srcList.size(); i++) {
			final VarDeclaration srcVar = srcList.get(i);
			final VarDeclaration copyVar = copyList.get(i);
			if (null == copyVar.getValue()) {
				copyVar.setValue(LibraryElementFactory.eINSTANCE.createValue());
			}
			if (null != srcVar.getValue()) {
				copyVar.getValue().setValue(srcVar.getValue().getValue());
			}
		}
	}

	private void copyConnections() {
		for (final ConnectionReference connRef : connectionsToCopy) {
			final FBNetworkElement copiedSrc = copiedElements.get(connRef.getSourceElement());
			final FBNetworkElement copiedDest = copiedElements.get(connRef.getDestinationElement());

			if ((null != copiedSrc) || (null != copiedDest)) {
				// Only copy if one end of the connection is copied as well otherwise we will
				// get a duplicate connection

				if ((dstFBNetwork.isSubApplicationNetwork() || srcFBNetwork.isSubApplicationNetwork())) {
					final Command cmd = copyConnectionToSubApp(connRef, copiedSrc, copiedDest);
					if (cmd != null && cmd.canExecute()) { // checks if the resulting connection is valid
						connCreateCmds.add(cmd);
					}
				} else {
					final AbstractConnectionCreateCommand cmd = getConnectionCreateCmd(connRef.getSource());
					if (null != cmd) {
						copyConnection(connRef, copiedSrc, copiedDest, cmd);
						if (cmd.canExecute()) { // checks if the resulting connection is valid
							connCreateCmds.add(cmd);
						}
					}
				}
			}
		}
		connCreateCmds.execute();
	}

	private AbstractConnectionCreateCommand getConnectionCreateCmd(final IInterfaceElement source) {
		AbstractConnectionCreateCommand cmd = null;
		if (source instanceof Event) {
			cmd = new EventConnectionCreateCommand(dstFBNetwork);
		} else if (source instanceof AdapterDeclaration) {
			cmd = new AdapterConnectionCreateCommand(dstFBNetwork);
		} else if (source instanceof VarDeclaration) {
			cmd = new DataConnectionCreateCommand(dstFBNetwork);
		}
		return cmd;
	}

	private void copyConnection(final ConnectionReference connRef, final FBNetworkElement copiedSrc, final FBNetworkElement copiedDest,
			final AbstractConnectionCreateCommand cmd) {
		final IInterfaceElement source = getInterfaceElement(connRef.getSource(), copiedSrc);
		final IInterfaceElement destination = getInterfaceElement(connRef.getDestination(), copiedDest);

		cmd.setSource(source);
		cmd.setDestination(destination);
		cmd.setArrangementConstraints(connRef.getRoutingData());
		cmd.setVisible(connRef.isVisible());
	}

	private Command copyConnectionToSubApp(final ConnectionReference connRef, final FBNetworkElement copiedSrc,
			final FBNetworkElement copiedDest) {
		final IInterfaceElement source = getInterfaceElement(connRef.getSource(), copiedSrc);
		final IInterfaceElement destination = getInterfaceElement(connRef.getDestination(), copiedDest);

		if (source != null && destination != null) {
			final Application sourceApp = source.getFBNetworkElement().getFbNetwork().getApplication();
			final Application destApp = destination.getFBNetworkElement().getFbNetwork().getApplication();
			if ((sourceApp != null && destApp != null && sourceApp.equals(destApp))
					|| (FBNetworkHelper.getRootType(source) != null && FBNetworkHelper.getRootType(destination) != null
					&& FBNetworkHelper.getRootType(source).equals(FBNetworkHelper.getRootType(destination)))) {
				return CreateSubAppCrossingConnectionsCommand.createProcessBorderCrossingConnection(source,
						destination);
			}
		}
		return null;
	}

	private IInterfaceElement getInterfaceElement(final IInterfaceElement orig, final FBNetworkElement copiedElement) {
		if (null != copiedElement) {
			// we have a copied connection target get the interface element from it
			return copiedElement.getInterfaceElement(orig.getName());
		} else if (dstFBNetwork.equals(srcFBNetwork)
				|| (dstFBNetwork.isSubApplicationNetwork() || srcFBNetwork.isSubApplicationNetwork())) {
			// we have a connection target to an existing FBNElement, only retrieve the
			// interface element if the target FBNetwrok is the same as the source. In this
			// case it is save to return the original interface element.
			return orig;
		}
		return null;
	}

	private Position calculatePastePos(final FBNetworkElement element) {
		final Position pastePos = LibraryElementFactory.eINSTANCE.createPosition();
		final Position outermostPos = getPositionOfOutermostNetwork(element);
		pastePos.setX(outermostPos.getX() + xDelta);
		pastePos.setY(outermostPos.getY() + yDelta);
		return pastePos;
	}

	protected CutAndPasteFromSubAppCommand getCutPasteCmd() {
		return cutPasteCmd;
	}

	public void setCutPasteCmd(final CutAndPasteFromSubAppCommand cutPasteCmd) {
		this.cutPasteCmd = cutPasteCmd;
	}

	public Collection<? extends Object> getTemplates() {
		return templates;
	}

	public Collection<FBNetworkElement> getCopiedFBs() {
		return copiedElements.values();
	}

}
