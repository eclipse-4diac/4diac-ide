/*******************************************************************************
 * Copyright (c) 2026 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

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
		if (previousComment == null) {
			CommentsHandler.getInstance().removeComment(eventPosition);
		} else {
			CommentsHandler.getInstance().setComment(eventPosition, previousComment); // restore prior comment
		}
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