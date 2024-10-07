package org.eclipse.fordiac.ide.gef.nat;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;

public class HideInOutPinCommand extends Command implements ScopedCommand {

	private final VarDeclaration varDeclaration; // The VarDeclaration instance
	private final boolean visibleIn; // Visibility for input
	private final boolean visibleOut; // Visibility for output
	private final boolean previousVisibleIn; // Previous visibility for input
	private final boolean previousVisibleOut; // Previous visibility for output

	public HideInOutPinCommand(final VarDeclaration varDeclaration, final boolean visibleIn, final boolean visibleOut) {
		this.varDeclaration = Objects.requireNonNull(varDeclaration);
		this.visibleIn = visibleIn;
		this.visibleOut = visibleOut;

		// Save previous state of visibility
		this.previousVisibleIn = VisibilityManager.getInputVisibility(varDeclaration);
		this.previousVisibleOut = VisibilityManager.getOutputVisibility(varDeclaration);
	}

	@Override
	public void execute() {
		// Set visibility for Input and Output using VisibilityManager
		VisibilityManager.setInputVisibility(varDeclaration, visibleIn);
		VisibilityManager.setOutputVisibility(varDeclaration, visibleOut);
	}

	@Override
	public void redo() {
		execute();
	}

	@Override
	public void undo() {
		// Restore the previous visibility states for input and output
		VisibilityManager.setInputVisibility(varDeclaration, previousVisibleIn);
		VisibilityManager.setOutputVisibility(varDeclaration, previousVisibleOut);
	}

	@Override
	public boolean canExecute() {
		// Allow command execution only for VarDeclarations
		return varDeclaration.getInputConnections().isEmpty() || varDeclaration.getOutputConnections().isEmpty();
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(varDeclaration);
	}
}
