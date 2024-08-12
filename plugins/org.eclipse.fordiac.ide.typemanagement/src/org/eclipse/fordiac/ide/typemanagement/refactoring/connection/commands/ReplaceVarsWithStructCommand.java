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

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.commands.create.WithCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

/**
 * A Command for replacing multiple In/-Outputs with one single StructuredType
 * This command ignores existing DataConnections.
 *
 * @see org.eclipse.fordiac.ide.model.commands.create.CreateStructFromInterfaceElementsCommand
 */
public class ReplaceVarsWithStructCommand extends Command {
	private final Collection<String> vars;
	private final DataType struct;
	private final String name;
	private final InterfaceList interfacelist;
	private final boolean isInput;
	private final int position;
	List<String> withs;
	CreateInterfaceElementCommand createStruct;

	private CompoundCommand editFBsCommand;
	private CompoundCommand createWithsCommand;

	/**
	 * Creates a new Instance.
	 *
	 * @param vars          Names of the Variables which should be removed
	 * @param struct        Type of the Struct to be created
	 * @param name          Name of the new port
	 * @param interfacelist InterfaceList to be modified
	 * @param isInput       Whether it is a Input (or Output otherwise)
	 * @param position      Where to insert the new port.
	 */
	public ReplaceVarsWithStructCommand(final Collection<String> vars, final DataType struct, final String name,
			final InterfaceList interfacelist, final boolean isInput, final int position) {
		this.vars = Objects.requireNonNull(vars);
		this.struct = Objects.requireNonNull(struct);
		this.name = Objects.requireNonNull(name);
		this.interfacelist = Objects.requireNonNull(interfacelist);
		this.isInput = isInput;
		this.position = position;

	}

	@Override
	public boolean canExecute() {
		return !(interfacelist.eContainer() instanceof ServiceInterfaceFBType);
	}

	@Override
	public void execute() {
		editFBsCommand = new CompoundCommand();
		createWithsCommand = new CompoundCommand();

		withs = (isInput ? interfacelist.getInputVars() : interfacelist.getOutputVars()).stream()
				.filter(vardec -> vars.contains(vardec.getName())).map(VarDeclaration::getWiths).flatMap(List::stream)
				.map(with -> ((Event) with.eContainer()).getName()).distinct().toList();

		// Delete old Vars
		vars.forEach(varName -> editFBsCommand.add(isInput ? new DeleteInterfaceCommand(interfacelist.getInput(varName))
				: new DeleteInterfaceCommand(interfacelist.getOutput(varName))));
		// Create Struct Vars
		createStruct = new CreateInterfaceElementCommand(struct, name, interfacelist, isInput, position);
		editFBsCommand.add(createStruct);

		editFBsCommand.execute();

		// Create Withs
		withs.forEach(width -> createWithsCommand.add(new WithCreateCommand(interfacelist.getEvent(width),
				(VarDeclaration) createStruct.getCreatedElement())));
		createWithsCommand.execute();
	}

	@Override
	public boolean canUndo() {
		return editFBsCommand.canUndo() && createWithsCommand.canUndo();
	}

	@Override
	public void undo() {
		createWithsCommand.undo();
		editFBsCommand.undo();
	}

	@Override
	public boolean canRedo() {
		return editFBsCommand.canRedo() && createWithsCommand.canRedo();
	}

	@Override
	public void redo() {
		editFBsCommand.redo();
		createWithsCommand.redo();
	}
}
