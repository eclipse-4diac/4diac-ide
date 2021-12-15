/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.model.commands.change.HideConnectionCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class ToggleConnectionsHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection != null) {
			@SuppressWarnings("unchecked")
			final List<ConnectionEditPart> connections = (List<ConnectionEditPart>) ((IStructuredSelection) selection)
			.toList().stream().filter(sel -> sel instanceof ConnectionEditPart).collect(Collectors.toList());

			final CompoundCommand commands = new CompoundCommand();
			final IEditorPart editor = HandlerUtil.getActiveEditor(event);
			final CommandStack stack = editor.getAdapter(CommandStack.class);
			connections.forEach(
					con -> {
						final Command hideConCmd = new HideConnectionCommand(con.getModel(),
								con.getFigure().isHidden());
						if (hideConCmd.canExecute()) {
							commands.add(hideConCmd);
						}
					});
			if (null != stack) {
				stack.execute(commands);
			}
			return Status.OK_STATUS;
		}
		return Status.CANCEL_STATUS;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final ISelection selection = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		final IEditorPart editor = (IEditorPart) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_EDITOR_NAME);
		setBaseEnabled(selection != null && editor != null && selectionContainsConnections(selection));
	}

	private static boolean selectionContainsConnections(final ISelection selection) {
		if (selection instanceof IStructuredSelection && !selection.isEmpty()) {

			return ((IStructuredSelection) selection).toList().stream().anyMatch(
					con -> con instanceof ConnectionEditPart && ((ConnectionEditPart) con).getFigure() != null);
		}
		return false;
	}

}
