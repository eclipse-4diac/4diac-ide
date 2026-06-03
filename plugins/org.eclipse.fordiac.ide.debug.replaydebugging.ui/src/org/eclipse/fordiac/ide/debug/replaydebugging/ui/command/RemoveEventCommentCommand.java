package org.eclipse.fordiac.ide.debug.replaydebugging.ui.command;

import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator.EventPosition;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.CommentsHandler;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.Messages;
import org.eclipse.gef.commands.Command;

public class RemoveEventCommentCommand extends Command {
	private final EventPosition eventPosition;
	private String previousComment = null; // for undo

	public RemoveEventCommentCommand(final EventPosition eventPosition) {
		super(Messages.RemoveEventCommentCommand_Text);
		this.eventPosition = eventPosition;
	}

	@Override
	public void execute() {
		previousComment = CommentsHandler.getInstance().getComment(eventPosition);
		CommentsHandler.getInstance().removeComment(eventPosition);
	}

	@Override
	public void undo() {
		CommentsHandler.getInstance().setComment(eventPosition, previousComment); // restore prior comment
	}

	@Override
	public void redo() {
		CommentsHandler.getInstance().removeComment(eventPosition);
	}

	@Override
	public boolean canExecute() {
		return CommentsHandler.getInstance().getComment(eventPosition) != null;
	}
}