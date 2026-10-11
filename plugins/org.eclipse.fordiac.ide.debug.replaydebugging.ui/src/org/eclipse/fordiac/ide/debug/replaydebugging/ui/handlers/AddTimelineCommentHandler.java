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
import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.Timeline;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.CommentsHandler;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.CommentsView;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.CommonConstants;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.Messages;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.command.AddEditTimelineCommentCommand;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.command.TimelineViewCommandStack;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.editpart.EventMarkerEditPart;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.editpart.TimelineEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class AddTimelineCommentHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {

		Timeline timeline = null;
		ReplayNavigator replayNavigator = null;

		final CommentsView.CommentRow row = CommentsView.CommentRow.of(HandlerUtil.getCurrentSelection(event));
		if (row != null) {
			if (!row.isTimelineComment()) {
				return null;
			}
			timeline = row.timeline();
			replayNavigator = row.navigator();
		} else {
			final EditPart editPart = HandlerHelper.getSelectedEditPart(event);
			switch (editPart) {
			case final EventMarkerEditPart eventMarkerEditPart -> {
				timeline = eventMarkerEditPart.getModel().getParentTimeline().getTimeline();
				replayNavigator = eventMarkerEditPart.getModel().getParentTimeline().getResource().getReplayNavigator();
			}
			case final TimelineEditPart timelineEditPart -> {
				timeline = timelineEditPart.getModel().getTimeline();
				replayNavigator = timelineEditPart.getModel().getResource().getReplayNavigator();
			}
			default -> {
				return null;
			}
			}
		}

		// The comments view supplies the new text (edited in its table), otherwise ask
		// the user.
		String text = event.getParameter(CommonConstants.COMMENT_TEXT_PARAMETER_ID);
		if (text == null) {
			final Shell shell = HandlerUtil.getActiveShellChecked(event);
			final String currentText = CommentsHandler.getInstance().getComment(timeline);
			final InputDialog dialog = new InputDialog(shell, Messages.AddTimelineCommentHandler_DialogTitle,
					Messages.AddTimelineCommentHandler_HintForTheUser, currentText, null);
			if (dialog.open() != Window.OK) {
				return null;
			}
			text = dialog.getValue();
		}

		final var stack = TimelineViewCommandStack.find(HandlerUtil.getActiveWorkbenchWindowChecked(event), true);
		if (stack != null) {
			stack.execute(new AddEditTimelineCommentCommand(timeline, text.trim(), replayNavigator));
		}
		return null;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (selection instanceof final IStructuredSelection s) {
			final Object first = s.getFirstElement();
			setBaseEnabled(!s.isEmpty() && (first instanceof final CommentsView.CommentRow row ? row.isTimelineComment()
					: first instanceof EventMarkerEditPart || first instanceof TimelineEditPart));
		}
	}
}
