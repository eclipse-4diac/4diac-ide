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
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.AdapterConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.util.ElementSelector;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.swt.graphics.Point;

/**
 * The Class PasteCommand.
 */
public class PasteCommand extends Command {

	private static final int DEFAULT_DELTA = 20;
	@SuppressWarnings("rawtypes")
	private final List templates;
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

	/**
	 * Instantiates a new paste command.
	 *
	 * @param templates   the elements that should be copied to the destination
	 * @param destination the destination fbnetwork where the elements should be
	 *                    copied to
	 * @param pasteRefPos the reference position for pasting the elements
	 */
	@SuppressWarnings("rawtypes")
	public PasteCommand(List templates, FBNetwork destination, Point pasteRefPos) {
		this.templates = templates;
		this.dstFBNetwork = destination;
		this.pasteRefPos = pasteRefPos;
		calcualteDelta = true;
	}

	@SuppressWarnings("rawtypes")
	public PasteCommand(List templates, FBNetwork destination, int copyDeltaX, int copyDeltaY) {
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
			gatherCopyData();
			copyFBs();
			copyConnections();
			ElementSelector.selectViewObjects(copiedElements.values());
		}
	}

	@Override
	public void undo() {
		connCreateCmds.undo();
		dstFBNetwork.getNetworkElements().removeAll(copiedElements.values());
		ElementSelector.selectViewObjects(templates);
	}

	@Override
	public void redo() {
		dstFBNetwork.getNetworkElements().addAll(copiedElements.values());
		connCreateCmds.redo();
		ElementSelector.selectViewObjects(copiedElements.values());
	}

	private void gatherCopyData() {
		int x = Integer.MAX_VALUE;
		int y = Integer.MAX_VALUE;

		for (Object object : templates) {
			if (object instanceof FBNetworkElement) {
				FBNetworkElement element = (FBNetworkElement) object;
				if (null == srcFBNetwork) {
					srcFBNetwork = element.getFbNetwork();
				}
				elementsToCopy.add(element);
				x = Math.min(x, element.getX());
				y = Math.min(y, element.getY());
			} else if (object instanceof ConnectionReference) {
				connectionsToCopy.add((ConnectionReference) object);
			} else if (object instanceof FBNetwork) {
				srcFBNetwork = (FBNetwork) object;
			}
		}

		updateDelta(x, y);
	}

	private void updateDelta(int x, int y) {
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

	private void copyFBs() {
		for (FBNetworkElement element : elementsToCopy) {
			FBNetworkElement copiedElement = createElementCopyFB(element);
			copiedElements.put(element, copiedElement);
			dstFBNetwork.getNetworkElements().add(copiedElement);
			copiedElement.setName(NameRepository.createUniqueName(copiedElement, element.getName()));
		}
	}

	private FBNetworkElement createElementCopyFB(FBNetworkElement element) {
		FBNetworkElement copiedElement = EcoreUtil.copy(element);
		// clear the connection references
		for (IInterfaceElement ie : copiedElement.getInterface().getAllInterfaceElements()) {
			if (ie.isIsInput()) {
				ie.getInputConnections().clear();
			} else {
				ie.getOutputConnections().clear();
			}
		}
		Point pastePos = calculatePastePos(element);
		copiedElement.setX(pastePos.x);
		copiedElement.setY(pastePos.y);

		copiedElement.setMapping(null);

		if (copiedElement instanceof StructManipulator) {
			// structmanipulators may destroy the param values during copy
			checkDataValues(element, copiedElement);
		}

		return copiedElement;
	}

	private static void checkDataValues(FBNetworkElement src, FBNetworkElement copy) {
		EList<VarDeclaration> srcList = src.getInterface().getInputVars();
		EList<VarDeclaration> copyList = copy.getInterface().getInputVars();

		for (int i = 0; i < srcList.size(); i++) {
			VarDeclaration srcVar = srcList.get(i);
			VarDeclaration copyVar = copyList.get(i);
			if (null == copyVar.getValue()) {
				copyVar.setValue(LibraryElementFactory.eINSTANCE.createValue());
			}
			if (null != srcVar.getValue()) {
				copyVar.getValue().setValue(srcVar.getValue().getValue());
			}
		}
	}

	private void copyConnections() {
		for (ConnectionReference connRef : connectionsToCopy) {
			FBNetworkElement copiedSrc = copiedElements.get(connRef.getSourceElement());
			FBNetworkElement copiedDest = copiedElements.get(connRef.getDestinationElement());

			if ((null != copiedSrc) || (null != copiedDest)) {
				// Only copy if one end of the connection is copied as well otherwise we will
				// get a duplicate connection
				AbstractConnectionCreateCommand cmd = getConnectionCreateCmd(connRef.getSource());
				if (null != cmd) {
					copyConnection(connRef, copiedSrc, copiedDest, cmd);
					if (cmd.canExecute()) { // checks if the resulting connection is valid
						connCreateCmds.add(cmd);
					}
				}
			}
		}
		connCreateCmds.execute();
	}

	private AbstractConnectionCreateCommand getConnectionCreateCmd(IInterfaceElement source) {
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

	private void copyConnection(ConnectionReference connRef, FBNetworkElement copiedSrc, FBNetworkElement copiedDest,
			AbstractConnectionCreateCommand cmd) {
		IInterfaceElement source = getInterfaceElement(connRef.getSource(), copiedSrc);
		IInterfaceElement destination = getInterfaceElement(connRef.getDestination(), copiedDest);

		cmd.setSource(source);
		cmd.setDestination(destination);
		cmd.setArrangementConstraints(connRef.getDx1(), connRef.getDx2(), connRef.getDy());
	}

	private IInterfaceElement getInterfaceElement(IInterfaceElement orig, FBNetworkElement copiedElement) {
		if (null != copiedElement) {
			// we have a copied connection target get the interface element from it
			return copiedElement.getInterfaceElement(orig.getName());
		} else if (dstFBNetwork.equals(srcFBNetwork)) {
			// we have a connection target to an existing FBNElement, only retrieve the
			// interface element if the target FBNetwrok is the same as the source. In this
			// case it is save to return the original interface element.
			return orig;
		}
		return null;
	}

	private Point calculatePastePos(FBNetworkElement element) {
		return new Point(element.getX() + xDelta, element.getY() + yDelta);
	}

}
