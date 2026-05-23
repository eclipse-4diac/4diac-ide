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

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class HandlerHelper {
	private HandlerHelper() {
		// this class should not be instantiated
	}

	public static void execute(final ExecutionEvent event, final Request request) {
		final GraphicalViewer viewer = getViewer(event);
		if (viewer == null) {
			return;
		}
		final EditPart editPart = getSelectedEditPart(event);
		if (editPart == null) {
			return;
		}

		final var command = editPart.getCommand(request);
		if (command != null && command.canExecute()) {
			viewer.getEditDomain().getCommandStack().execute(command);
		}
	}

	public static void executeOrBubbleUp(final ExecutionEvent event, final Request request) {
		final GraphicalViewer viewer = getViewer(event);
		if (viewer == null) {
			return;
		}
		final EditPart editPart = getSelectedEditPart(event);
		final var command = bubbleForCommand(editPart, request);
		if (command != null && command.canExecute()) {
			viewer.getEditDomain().getCommandStack().execute(command);
		}
	}

	private static GraphicalViewer getViewer(final ExecutionEvent event) {
		IWorkbenchPart part;
		try {
			part = HandlerUtil.getActivePartChecked(event);
		} catch (final ExecutionException e) {
			e.printStackTrace();
			return null;
		}
		final GraphicalViewer viewer = part.getAdapter(GraphicalViewer.class);
		if (viewer == null) {
			return null;
		}
		return viewer;
	}

	private static EditPart getSelectedEditPart(final ExecutionEvent event) {
		final IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);
		if (selection.isEmpty()) {
			return null;
		}
		final Object first = selection.getFirstElement();
		return first instanceof final EditPart ep ? ep : null;
	}

	// Walks up the parent chain until a policy handles the request
	private static Command bubbleForCommand(EditPart editPart, final Request request) {
		while (editPart != null) {
			final Command command = editPart.getCommand(request);
			if (command != null && command.canExecute()) {
				return command;
			}
			editPart = editPart.getParent();
		}
		return null;
	}
}
