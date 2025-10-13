/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial implementation and/or documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.application.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.application.commands.CreateSubAppCrossingConnectionsCommand;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.fordiac.util.marker.MarkerDescriptor;
import org.eclipse.fordiac.util.marker.UtilityMarkerHelper;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class MarkConnectionSourceHandler extends AbstractMarkerHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		// triggered by shortcut or context menu
		if (event.getTrigger() != null) {
			return super.execute(event);
		}

		// establish connection : triggered by handler service
		final IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);
		final InterfaceEditPart connectionTarget = (selection instanceof final IStructuredSelection sel
				&& sel.getFirstElement() instanceof final InterfaceEditPart ep) ? ep : null;
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);

		if (connectionTarget == null || !connectionTarget.isInput() || editor == null) {
			return null;
		}

		if (UtilityMarkerHelper.getMarkedElement(getDescriptor(),
				getRootResource(connectionTarget)) instanceof final IInterfaceElement connectionSource) {
			HandlerHelper.getCommandStack(editor).execute(CreateSubAppCrossingConnectionsCommand
					.createProcessBorderCrossingConnection(connectionSource, connectionTarget.getModel()));
		}
		return null;
	}

	@Override
	protected MarkerDescriptor getDescriptor() {
		return MarkerDescriptor.CONNECTION_SOURCE;
	}

	@Override
	protected GraphicalEditPart getValidSelectedElement(final ISelection selection) {
		if (selection instanceof final IStructuredSelection structSel
				&& structSel.getFirstElement() instanceof final InterfaceEditPart iep) {
			return iep;
		}
		return null;
	}

}
