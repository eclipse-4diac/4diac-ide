package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.commands.create.WithCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

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
		// TODO: depending on fbtype
		// return editFBsCommand.canExecute();
		return true;
	}

	@Override
	public void execute() {
		editFBsCommand = new CompoundCommand();
		createWithsCommand = new CompoundCommand();

		withs = (isInput ? interfacelist.getInputVars() : interfacelist.getOutputVars()).stream()
				.filter(vardec -> vars.contains(vardec.getName())).map(VarDeclaration::getWiths).flatMap(List::stream)
				.map(with -> ((Event) with.eContainer()).getName()).distinct().toList();

		// Delete old Vars
		vars.forEach(var -> editFBsCommand.add(isInput ? new DeleteInterfaceCommand(interfacelist.getInput(var))
				: new DeleteInterfaceCommand(interfacelist.getOutput(var))));
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
