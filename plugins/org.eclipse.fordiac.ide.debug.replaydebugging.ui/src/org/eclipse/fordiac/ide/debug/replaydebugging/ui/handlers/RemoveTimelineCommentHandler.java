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
import org.eclipse.fordiac.ide.debug.replaydebugging.core.Timeline;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.command.RemoveTimelineCommentCommand;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.editpart.EventMarkerEditPart;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.editpart.TimelineEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class RemoveTimelineCommentHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		Timeline timeline = null;
		final EditPart editPart = HandlerHelper.getSelectedEditPart(event);

		switch (editPart) {
		case final EventMarkerEditPart eventMarkerEditPart ->
			timeline = eventMarkerEditPart.getModel().getParentTimeline().getTimeline();
		case final TimelineEditPart timelineEditPart -> timeline = timelineEditPart.getModel().getTimeline();
		default -> {
			return null;
		}
		}

		final var viewer = HandlerUtil.getActivePartChecked(event).getAdapter(GraphicalViewer.class);
		viewer.getEditDomain().getCommandStack().execute(new RemoveTimelineCommentCommand(timeline));
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
			String comment = null;
			switch (s.getFirstElement()) {
			case final EventMarkerEditPart eventMarkerEditPart ->
				comment = eventMarkerEditPart.getModel().getParentTimeline().getComment();
			case final TimelineEditPart timelineEditPart -> comment = timelineEditPart.getModel().getComment();
			default -> {
				// comment already set to null
			}
			}

			setBaseEnabled(comment != null);
		}
	}
}