package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;

public class HideInOutPinCommand extends Command implements ScopedCommand {

	private final VarDeclaration inputVarInOut;
	private final boolean visible;

	public HideInOutPinCommand(final VarDeclaration inputVarInOut, final boolean visible) {
		this.inputVarInOut = Objects.requireNonNull(inputVarInOut);
		this.visible = visible;
	}

	@Override
	public void execute() {
		inputVarInOut.setVisible(visible);
	}

	@Override
	public void redo() {
		execute();
	}

	@Override
	public void undo() {
		inputVarInOut.setVisible(!visible);
	}

	@Override
	public boolean canExecute() {
		return (inputVarInOut.isInOutVar() && inputVarInOut.getInputConnections().isEmpty()
				&& inputVarInOut.getOutputConnections().isEmpty());
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(inputVarInOut);
	}
}