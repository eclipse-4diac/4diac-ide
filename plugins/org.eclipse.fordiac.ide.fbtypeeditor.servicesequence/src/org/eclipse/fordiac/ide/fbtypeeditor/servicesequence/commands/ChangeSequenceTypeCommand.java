package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.gef.commands.Command;

public class ChangeSequenceTypeCommand extends Command {
	private final ServiceSequence sequence;
	private final String type;
	private String oldType;

	public ChangeSequenceTypeCommand(final String type, final ServiceSequence sequence) {
		this.sequence = sequence;
		this.type = type;
	}

	@Override
	public void execute() {
		oldType = sequence.getServiceSequenceType();
		setType(type);
	}

	@Override
	public void undo() {
		setType(oldType);
	}

	@Override
	public void redo() {
		setType(type);
	}

	private void setType(final String type) {
		sequence.setServiceSequenceType(type);
	}
}
