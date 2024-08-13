/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mathias Garstenauer - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.connection.commands;

import java.util.Objects;

import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.StructDataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.gef.commands.Command;

/**
 * A Command to creates and connects a StructManipulator to a specified Port. If
 * a Multiplexer or Demultiplexer is needed, is determined by the Type of the
 * Port. This Command does not reroute EventConnections.
 */
public class InsertStructManipulatorCommand extends Command {
	private final StructuredType structType;
	private final boolean isMUX;
	private final IInterfaceElement port;
	private FBCreateCommand muxcreate;
	private ChangeStructCommand changeStruct;
	private StructDataConnectionCreateCommand createCon;

	/**
	 * Creates a new Instance
	 *
	 * @param port The specific Port where to connect the StructManipulator
	 */
	public InsertStructManipulatorCommand(final IInterfaceElement port) {
		this.port = Objects.requireNonNull(port);
		this.structType = (StructuredType) port.getType();
		this.isMUX = port.isIsInput();
	}

	@Override
	public boolean canExecute() {
		return port.getType() instanceof StructuredType && port.getFBNetworkElement().getTypeLibrary()
				.getFBTypeEntry(isMUX ? "STRUCT_MUX" : "STRUCT_DEMUX") != null;
	}

	@Override
	public void execute() {
		final FBNetworkElement element = port.getFBNetworkElement();

		final Position pos = LibraryElementFactory.eINSTANCE.createPosition();
		pos.setX((int) element.getPosition().getX() + (isMUX ? (-1000.0) : 1000.0));
		pos.setY((int) element.getPosition().getY());
		muxcreate = new FBCreateCommand(element.getTypeLibrary().getFBTypeEntry(isMUX ? "STRUCT_MUX" : "STRUCT_DEMUX"),
				element.getFbNetwork(), pos);
		muxcreate.execute();

		changeStruct = new ChangeStructCommand((StructManipulator) muxcreate.getElement(), structType);
		changeStruct.execute();
		createCon = new StructDataConnectionCreateCommand(element.getFbNetwork());

		final IInterfaceElement src = port;
		final IInterfaceElement dest = isMUX ? changeStruct.getNewElement().getInterface().getOutputVars().getFirst()
				: changeStruct.getNewElement().getInterface().getInputVars().getFirst();
		createCon.setSource(src);
		createCon.setDestination(dest);
		createCon.execute();
	}

	public FBNetworkElement getNewElement() {
		return changeStruct.getNewElement();
	}

	@Override
	public boolean canUndo() {
		return createCon.canUndo() && changeStruct.canUndo() && muxcreate.canUndo();
	}

	@Override
	public void undo() {
		createCon.undo();
		changeStruct.undo();
		muxcreate.undo();
	}

	@Override
	public boolean canRedo() {
		return muxcreate.canRedo() && changeStruct.canRedo() && createCon.canRedo();
	}

	@Override
	public void redo() {
		muxcreate.redo();
		changeStruct.redo();
		createCon.redo();
	}
}
