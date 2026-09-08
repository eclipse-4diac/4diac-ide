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
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.CommentsHandler;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.Messages;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.command.AddEditEventCommentCommand;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.editpart.EventMarkerEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class AddEventCommentHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {

		final EditPart editPart = HandlerHelper.getSelectedEditPart(event);
		if (!(editPart instanceof final EventMarkerEditPart eventMarkerEditPart)) {
			return null;
		}

		// Open the dialog
		final Shell shell = HandlerUtil.getActiveShellChecked(event);
		final var eventPosition = new EventPosition(eventMarkerEditPart.getModel().getParentTimeline().getTimeline(),
				eventMarkerEditPart.getModel().getIndex());
		final String currentText = CommentsHandler.getInstance().getComment(eventPosition);
		final InputDialog dialog = new InputDialog(shell, Messages.AddEventCommentHandler_DialogTitle,
				Messages.AddEventCommentHandler_HintForTheUser, currentText, null);

		if (dialog.open() != Window.OK) {
			return null;
		}

		final String text = dialog.getValue().trim();

		final var viewer = HandlerUtil.getActivePartChecked(event).getAdapter(GraphicalViewer.class);
		viewer.getEditDomain().getCommandStack().execute(new AddEditEventCommentCommand(eventPosition, text));
		return null;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (selection instanceof final IStructuredSelection s) {
			setBaseEnabled(!s.isEmpty() && s.getFirstElement() instanceof EventMarkerEditPart);
		}
	}
}