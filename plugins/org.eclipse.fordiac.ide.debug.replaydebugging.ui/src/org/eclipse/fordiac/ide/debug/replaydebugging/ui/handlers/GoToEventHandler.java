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
package org.eclipse.fordiac.ide.debug.replaydebugging.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator.EventPosition;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.CommentsView;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.command.SelectEventCommand;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.command.TimelineViewCommandStack;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Moves the replay to the commented event (the first event for a timeline
 * comment). Undoable.
 */
public class GoToEventHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final CommentsView.CommentRow row = CommentsView.CommentRow.of(HandlerUtil.getCurrentSelection(event));
		final CommandStack stack = TimelineViewCommandStack.find(HandlerUtil.getActiveWorkbenchWindowChecked(event),
				true);
		if (row == null || stack == null) {
			return null;
		}
		final EventPosition position = row.isTimelineComment() ? new EventPosition(row.timeline(), 0) : row.position();
		stack.execute(new SelectEventCommand(row.navigator(), position));
		return null;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		setBaseEnabled(CommentsView.CommentRow
				.of(HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME)) != null);
	}
}
