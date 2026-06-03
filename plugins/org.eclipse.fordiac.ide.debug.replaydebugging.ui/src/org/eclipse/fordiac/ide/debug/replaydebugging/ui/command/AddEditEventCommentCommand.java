package org.eclipse.fordiac.ide.debug.replaydebugging.ui.command;

import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator.EventPosition;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.CommentsHandler;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.Messages;
import org.eclipse.gef.commands.Command;

public class AddEditEventCommentCommand extends Command {
	private final EventPosition eventPosition;
	private final String newComment;
	private String previousComment = null; // for undo

	public AddEditEventCommentCommand(final EventPosition eventPosition, final String newComment) {
		super(Messages.AddEditEventCommentCommand_Text);
		this.eventPosition = eventPosition;
		this.newComment = newComment;
	}

	@Override
	public void execute() {
		previousComment = CommentsHandler.getInstance().getComment(eventPosition);
		CommentsHandler.getInstance().setComment(eventPosition, newComment);
	}

	@Override
	public void undo() {
		CommentsHandler.getInstance().setComment(eventPosition, previousComment); // restore prior comment
	}

	@Override
	public void redo() {
		CommentsHandler.getInstance().setComment(eventPosition, newComment);
	}

	@Override
	public boolean canExecute() {
		return true;
	}
}