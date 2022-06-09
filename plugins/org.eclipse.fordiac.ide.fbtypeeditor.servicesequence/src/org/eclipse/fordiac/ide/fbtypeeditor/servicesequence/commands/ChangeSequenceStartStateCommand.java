package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.gef.commands.Command;

public class ChangeSequenceStartStateCommand extends Command {
	private final ServiceSequence sequence;
	private final String startState;
	private String oldStartState;

	public ChangeSequenceStartStateCommand(final String startState, final ServiceSequence sequence) {
		this.sequence = sequence;
		this.startState = startState;
	}

	@Override
	public void execute() {
		oldStartState = sequence.getStartState();
		setStartState(startState);
	}

	@Override
	public void undo() {
		setStartState(oldStartState);
	}

	@Override
	public void redo() {
		setStartState(startState);
	}

	private void setStartState(final String startState) {
		sequence.setStartState(startState);
	}
}
