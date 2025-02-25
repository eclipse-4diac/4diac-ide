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
 *   Paul Stemmer - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.model.commands.change.NegateConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.menus.UIElement;

public class NegateConnectionsHandler extends AbstractHandler implements IElementUpdater {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection selection = HandlerUtil.getCurrentSelection(event);

		if (selection instanceof final IStructuredSelection structSel) {
			final IEditorPart editor = HandlerUtil.getActiveEditor(event);
			final CommandStack stack = editor.getAdapter(CommandStack.class);

			for (final Object obj : structSel.toList()) {
				if (obj instanceof final ConnectionEditPart conEP) {
					final Connection connection = conEP.getModel();
					final boolean newNegationState = !connection.isNegated();

					stack.execute(new NegateConnectionCommand(connection, newNegationState));
				}
			}
			return Status.OK_STATUS;
		}

		return Status.CANCEL_STATUS;
	}

	@Override
	public void updateElement(final UIElement element, final Map parameters) {
		final IEditorPart currentActiveEditor = EditorUtils.getCurrentActiveEditor();
		if (currentActiveEditor != null) {
			final GraphicalViewer viewer = currentActiveEditor.getAdapter(GraphicalViewer.class);
			if ((viewer != null) && (viewer.getSelectedEditParts().size() == 1)
					&& (viewer.getSelectedEditParts().get(0) instanceof final ConnectionEditPart connectionEditPart)) {
				final boolean isNegated = connectionEditPart.getModel().isNegated();
				element.setText(isNegated ? Messages.Connection_Unnegate : Messages.Connection_Negate);
			}
		}
	}

}
