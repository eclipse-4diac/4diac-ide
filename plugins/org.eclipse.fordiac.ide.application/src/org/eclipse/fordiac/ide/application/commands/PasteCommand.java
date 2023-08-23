/*******************************************************************************
 * Copyright (c) 2008, 2023 Profactor GmbH, TU Wien ACIN, AIT, fortiss GmbH,
 *                          Johannes Kepler University Linz
 *                          Primetals Technologies Austria GmbH
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

import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.actions.CopyPasteData;
import org.eclipse.fordiac.ide.gef.utilities.ElementSelector;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.AdapterConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.util.FordiacMarkerCommandHelper;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.helpers.InterfaceListCopier;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.errormessages.ErrorMessenger;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.swt.graphics.Point;

/** The Class PasteCommand. */
public class PasteCommand extends Command {

	private static final int DEFAULT_DELTA = 20;
	private final CopyPasteData copyPasteData;
	private final FBNetwork dstFBNetwork;

	private final Map<FBNetworkElement, FBNetworkElement> copiedElements = new HashMap<>();

	private final CompoundCommand connCreateCmds = new CompoundCommand();
	private final CompoundCommand createMarkersCmds = new CompoundCommand();
	private final CompoundCommand updateTypeCmds = new CompoundCommand();

	private int xDelta;
	private int yDelta;
	private boolean calculateDelta = false;
	private Point pasteRefPos;
	private final TypeLibrary dstTypeLib;

	/** Instantiates a new paste command.
	 *
	 * @param copyPasteData the elements that should be copied to the destination
	 * @param destination   the destination fbnetwork where the elements should be copied to
	 * @param pasteRefPos   the reference position for pasting the elements */
	public PasteCommand(final CopyPasteData copyPasteData, final FBNetwork destination, final Point pasteRefPos) {
		this.copyPasteData = copyPasteData;
		this.dstFBNetwork = destination;
		this.pasteRefPos = pasteRefPos;
		calculateDelta = true;
		dstTypeLib = checkTypeLib(copyPasteData.srcNetwork(), destination);
	}

	public PasteCommand(final CopyPasteData copyPasteData, final FBNetwork destination, final int copyDeltaX,
			final int copyDeltaY) {
		this.copyPasteData = copyPasteData;
		this.dstFBNetwork = destination;
		xDelta = copyDeltaX;
		yDelta = copyDeltaY;
		dstTypeLib = checkTypeLib(copyPasteData.srcNetwork(), destination);
	}

	@Override
	public boolean canExecute() {
		return (null != copyPasteData) && !copyPasteData.isEmpty() && (null != dstFBNetwork);
	}

	@Override
	public void execute() {
		if (dstFBNetwork != null) {
			ErrorMessenger.pauseMessages();
			updateDelta();
			removeDuplicateElements();
			copyPasteData.elements().forEach(this::copyAndCreateFB);
			copyConnections();
			ElementSelector.selectViewObjects(copiedElements.values());

			if (dstTypeLib != null) {
				createUpdateTypeAndErrorMarkerCommands();
			}
			createMarkersCmds.execute();
			updateTypeCmds.execute();

			if (!ErrorMessenger.unpauseMessages().isEmpty()) {
				ErrorMessenger.popUpErrorMessage(Messages.PasteRecreateNotPossible, ErrorMessenger.USE_DEFAULT_TIMEOUT);
			}
		}
	}

	@Override
	public void undo() {
		updateTypeCmds.undo();
		createMarkersCmds.undo();
		connCreateCmds.undo();
		dstFBNetwork.getNetworkElements().removeAll(copiedElements.values());
	}

	@Override
	public void redo() {
		dstFBNetwork.getNetworkElements().addAll(copiedElements.values());
		connCreateCmds.redo();
		createMarkersCmds.redo();
		updateTypeCmds.redo();
		ElementSelector.selectViewObjects(copiedElements.values());
	}

	// remove elements, if they are already inside a selected top-level subapp.
	private void removeDuplicateElements() {
		copyPasteData.elements()
				.removeIf(element -> copyPasteData.elements().stream().filter(SubApp.class::isInstance)
						.map(SubApp.class::cast).filter(subapp -> !subapp.isTyped())
						.anyMatch(subapp -> subapp.getSubAppNetwork().getNetworkElements().contains(element)));
	}

	private void updateDelta() {
		int x = Integer.MAX_VALUE;
		int y = Integer.MAX_VALUE;

		for (final FBNetworkElement element : copyPasteData.elements()) {
			final Position outermostPos = getPositionOfOutermostNetwork(element);
			x = Math.min(x, outermostPos.getX());
			y = Math.min(y, outermostPos.getY());
		}

		if (calculateDelta) {
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
		final FBNetworkElement copiedElement = createCopiedElement(element);

		if (!isNested) {
			copiedElement.setPosition(calculatePastePos(element));
		}
		copiedElement.setMapping(null);

		if (copiedElement instanceof StructManipulator) {
			// structmanipulators may destroy the param values during copy
			checkDataValues(element, copiedElement);
		}

		// copy content of Groups
		if (element instanceof final Group group) {
			for (final FBNetworkElement groupElement : group.getGroupElements()) {
				((Group) copiedElement).getGroupElements().add(copyAndCreateFB(groupElement, true));
			}
		}

		return copiedElement;
	}

	private FBNetworkElement createCopiedElement(final FBNetworkElement element) {
		FBNetworkElement copiedElement = EcoreUtil.copy(element);
		if (dstTypeLib != null && element.getTypeEntry() != null) {
			// we are copying between projects and it is a typed FBNetworkElement
			final TypeEntry dstTypeEntry = dstTypeLib.getFBOrSubAppType(element.getFullTypeName());
			if (dstTypeEntry != null) {
				// the target project has the type
				copiedElement.setTypeEntry(dstTypeEntry);
			} else {
				copiedElement = FordiacMarkerHelper.createTypeErrorMarkerFB(copiedElement.getName(), dstTypeLib,
						element.getTypeEntry().getType().eClass());
				copiedElement.setInterface(InterfaceListCopier.copy(element.getInterface()));
			}
		} else {
			// clear the connection references
			for (final IInterfaceElement ie : copiedElement.getInterface().getAllInterfaceElements()) {
				if (ie.isIsInput()) {
					ie.getInputConnections().clear();
				} else {
					ie.getOutputConnections().clear();
				}
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
		for (final ConnectionReference connRef : copyPasteData.conns()) {
			final FBNetworkElement copiedSrc = copiedElements.get(connRef.sourceElement());
			final FBNetworkElement copiedDest = copiedElements.get(connRef.destinationElement());

			if ((null != copiedSrc) || (null != copiedDest)) {
				// Only copy if one end of the connection is copied as well otherwise we will
				// get a duplicate connection

				if ((dstFBNetwork.isSubApplicationNetwork() || copyPasteData.srcNetwork().isSubApplicationNetwork())) {
					final Command cmd = copyConnectionToSubApp(connRef, copiedSrc, copiedDest);
					if (cmd != null && cmd.canExecute()) { // checks if the resulting connection is valid
						connCreateCmds.add(cmd);
					}
				} else {
					final AbstractConnectionCreateCommand cmd = getConnectionCreateCmd(connRef.source());
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

	private void copyConnection(final ConnectionReference connRef, final FBNetworkElement copiedSrc,
			final FBNetworkElement copiedDest, final AbstractConnectionCreateCommand cmd) {
		final IInterfaceElement source = getInterfaceElement(connRef.source(), copiedSrc);
		final IInterfaceElement destination = getInterfaceElement(connRef.destination(), copiedDest);

		cmd.setSource(source);
		cmd.setDestination(destination);
		cmd.setArrangementConstraints(connRef.routingData());
		cmd.setVisible(connRef.visible());
	}

	private Command copyConnectionToSubApp(final ConnectionReference connRef, final FBNetworkElement copiedSrc,
			final FBNetworkElement copiedDest) {
		final IInterfaceElement source = getInterfaceElement(connRef.source(), copiedSrc);
		final IInterfaceElement destination = getInterfaceElement(connRef.destination(), copiedDest);

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
		}
		if (dstFBNetwork.equals(copyPasteData.srcNetwork())
				|| (dstFBNetwork.isSubApplicationNetwork() || copyPasteData.srcNetwork().isSubApplicationNetwork())) {
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

	public Collection<FBNetworkElement> getCopiedFBs() {
		return copiedElements.values();
	}

	private static TypeLibrary checkTypeLib(final FBNetwork srcNetwork, final FBNetwork destNetwork) {
		final EObject srcRoot = EcoreUtil.getRootContainer(srcNetwork);
		final EObject dstRoot = EcoreUtil.getRootContainer(destNetwork);

		if (srcRoot instanceof final LibraryElement srcLibEl && dstRoot instanceof final LibraryElement dstLibEl
				&& !srcLibEl.getTypeLibrary().getProject().equals(dstLibEl.getTypeLibrary().getProject())) {
			// we copy between projects
			return dstLibEl.getTypeLibrary();
		}
		return null;
	}

	private void createUpdateTypeAndErrorMarkerCommands() {
		copiedElements.values().forEach(fbnEl -> {
			if (fbnEl instanceof ErrorMarkerFBNElement) {
				final ErrorMarkerBuilder builder = ErrorMarkerBuilder
						.createErrorMarkerBuilder(MessageFormat.format("Type ({0}) could not be loaded for FB: {1}", //$NON-NLS-1$
								fbnEl.getTypeName(), fbnEl.getName()))
						.setTarget(fbnEl);
				createMarkersCmds.add(FordiacMarkerCommandHelper.newCreateMarkersCommand(builder));
			} else if (fbnEl.getTypeEntry() != null) {
				// we only need to update the type if we have a type entry
				updateTypeCmds.add(new UpdateFBTypeCommand(fbnEl));
			}
		});
	}

}
