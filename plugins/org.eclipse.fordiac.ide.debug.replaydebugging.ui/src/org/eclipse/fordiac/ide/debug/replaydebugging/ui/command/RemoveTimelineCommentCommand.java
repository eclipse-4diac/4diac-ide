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

import org.eclipse.fordiac.ide.debug.replaydebugging.core.Timeline;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.CommentsHandler;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.Messages;
import org.eclipse.gef.commands.Command;

public class RemoveTimelineCommentCommand extends Command {
	private final Timeline timeline;
	private String previousComment = null; // for undo

	public RemoveTimelineCommentCommand(final Timeline eventPosition) {
		super(Messages.RemoveTimelineCommentCommand_Text);
		this.timeline = eventPosition;
	}

	@Override
	public void execute() {
		previousComment = CommentsHandler.getInstance().getComment(timeline);
		CommentsHandler.getInstance().removeComment(timeline);
	}

	@Override
	public void undo() {
		CommentsHandler.getInstance().setComment(timeline, previousComment); // restore prior comment
	}

	@Override
	public void redo() {
		CommentsHandler.getInstance().removeComment(timeline);
	}

	@Override
	public boolean canExecute() {
		return CommentsHandler.getInstance().getComment(timeline) != null;
	}
}