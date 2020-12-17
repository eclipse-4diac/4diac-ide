/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr, Lukas Wais, Virendra Ashiwal
 *     - initial implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.commands.create;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class CreateStructFromInterfaceElementsCommand extends Command {
	private List<VarDeclaration> elementsToRemove = new ArrayList<>();
	private final int position; // new struct should be at top-most selected pin position
	private boolean isInput;
	private final DataType datatype;

	private final CompoundCommand deleteCmds = new CompoundCommand();
	private Command addStructCmd;

	public CreateStructFromInterfaceElementsCommand(List<VarDeclaration> selection, DataType datatype) {
		elementsToRemove = Collections.unmodifiableList(selection);
		if (!elementsToRemove.isEmpty()) {
			isInput = elementsToRemove.get(0).isIsInput();
		}
		position = elementsToRemove.isEmpty() ? 0 : getTopMostPosition();
		this.datatype = datatype;
	}

	private int getTopMostPosition() {
		int topPos = getInterfaceElementList().size(); // default: at bottom
		int currPos;
		final List<VarDeclaration> il = getInterfaceElementList();
		for (final VarDeclaration varDecl : elementsToRemove) {
			currPos = il.indexOf(varDecl);
			if (currPos < topPos) {
				topPos = currPos;
			}
		}
		return topPos;
	}

	private List<VarDeclaration> getInterfaceElementList() {
		if (isInput) {
			return getInterfaceList().getInputVars();
		} else {
			return getInterfaceList().getOutputVars();
		}
	}

	private InterfaceList getInterfaceList() {
		return (InterfaceList) elementsToRemove.get(0).eContainer();
	}

	@Override
	public boolean canExecute() {
		// elements must be available
		if (elementsToRemove.isEmpty()) {
			return false;
		}
		// FBNElement must be untyped subapp
		final InterfaceList il = getInterfaceList();
		if (il.getFBNetworkElement().getType() != null) { // cannot edit instance of typed elements
			return false;
		}

		final boolean isInputs = elementsToRemove.get(0).isIsInput();
		IInterfaceElement element;
		for (final VarDeclaration varDecl : elementsToRemove) {
			// interfaces elements are either only inputs or only outputs
			if (varDecl.isIsInput() != isInputs) {
				return false;
			}
			// interfaces elements are unconnected
			element = il.getVariable(varDecl.getName());
			if (!element.getInputConnections().isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void execute() {
		for (final VarDeclaration varDecl : elementsToRemove) {
			deleteCmds.add(new DeleteInterfaceCommand(varDecl));
		}
		addStructCmd = new CreateInterfaceElementCommand(datatype,
				getInterfaceList(), isInput, position);

		deleteCmds.execute();
		addStructCmd.execute();
	}

	@Override
	public void undo() {
		addStructCmd.undo();
		deleteCmds.undo();
	}

	@Override
	public void redo() {
		deleteCmds.redo();
		addStructCmd.redo();
	}
}
