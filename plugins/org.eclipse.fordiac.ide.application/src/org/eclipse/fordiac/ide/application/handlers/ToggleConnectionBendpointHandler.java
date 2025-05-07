/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.commands.ToggleConnectionBendpointCommand;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.menus.UIElement;

public class ToggleConnectionBendpointHandler extends AbstractHandler implements IElementUpdater {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof final IStructuredSelection structSel) {
			final IEditorPart editor = HandlerUtil.getActiveEditor(event);
			final CommandStack cmdStack = editor.getAdapter(CommandStack.class);

			final var compoundCommand = new CompoundCommand();
			structSel.stream().filter(ConnectionEditPart.class::isInstance).map(ConnectionEditPart.class::cast)
					.forEach(connectionEditPart -> compoundCommand
							.add(ToggleConnectionBendpointCommand.createCommand(connectionEditPart.getModel())));
			cmdStack.execute(compoundCommand);

			return Status.OK_STATUS;
		}
		return Status.CANCEL_STATUS;
	}

	@Override
	public void updateElement(final UIElement element, final Map parameters) {
		final IEditorPart currentActiveEditor = EditorUtils.getCurrentActiveEditor();
		if (currentActiveEditor != null) {
			final GraphicalViewer viewer = currentActiveEditor.getAdapter(GraphicalViewer.class);
			if (viewer != null) {
				final var editParts = viewer.getSelectedEditParts();

				final List<Boolean> options = editParts.stream().filter(ConnectionEditPart.class::isInstance)
						.map(ConnectionEditPart.class::cast)
						.map(connectionEditPart -> connectionEditPart.getModel().getRoutingData().is5SegementData())
						.distinct().toList();

				if (options.size() == 1) {
					element.setText(Boolean.TRUE.equals(options.get(0)) ? Messages.ToggleSegments_3
							: Messages.ToggleSegments_5);
				} else {
					element.setText(Messages.ToggleSegments);
				}
			}
		}
	}
}
