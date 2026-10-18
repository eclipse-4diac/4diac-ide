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
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.command.RemoveEventCommentCommand;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.command.TimelineViewCommandStack;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.editpart.CommentMarkerEditPart;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.editpart.EventMarkerEditPart;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Removes the comment of an event. Serves two sources: the timeline view
 * (selected {@link EventMarkerEditPart}) and the comments view (selected
 * {@link CommentsView.CommentRow}). In both cases the command is executed on
 * the command stack of the timeline view, so there is a single undo history.
 */
public class RemoveEventCommentHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final EventPosition eventPosition = getEventPosition(event);
		if (eventPosition == null) {
			return null;
		}
		final var stack = TimelineViewCommandStack.find(HandlerUtil.getActiveWorkbenchWindowChecked(event), true);
		if (stack != null) {
			stack.execute(new RemoveEventCommentCommand(eventPosition));
		}
		return null;
	}

	/**
	 * The event whose comment is removed: the selected row (comments view) or event
	 * marker (timeline view).
	 */
	private static EventPosition getEventPosition(final ExecutionEvent event) {
		final CommentsView.CommentRow row = CommentsView.CommentRow.of(HandlerUtil.getCurrentSelection(event));
		if (row != null) {
			return row.isTimelineComment() ? null : row.position();
		}
		if (HandlerHelper.getSelectedEditPart(event) instanceof final EventMarkerEditPart eventMarkerEditPart) {
			return new EventPosition(eventMarkerEditPart.getModel().getParentTimeline().getTimeline(),
					eventMarkerEditPart.getModel().getIndex());
		}
		if (HandlerHelper.getSelectedEditPart(event) instanceof final CommentMarkerEditPart commentMarkerEditPart) {
			return commentMarkerEditPart.getModel().getEventPosition();
		}
		return null;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (selection instanceof final IStructuredSelection s) {
			if (s.isEmpty()) {
				setBaseEnabled(false);
				return;
			}
			final Object first = s.getFirstElement();
			if (first instanceof final CommentsView.CommentRow row) {
				setBaseEnabled(!row.isTimelineComment());
			} else if (first instanceof final EventMarkerEditPart ep) {
				setBaseEnabled(ep.getModel() != null && ep.getModel().getComment() != null);
			} else if (first instanceof final CommentMarkerEditPart cmp) {
				setBaseEnabled(cmp.getModel() != null);
			} else {
				setBaseEnabled(false);
			}
		}
	}
}
