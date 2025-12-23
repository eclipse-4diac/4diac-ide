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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.commands.CreateSubAppCrossingConnectionsCommand;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.ui.UtilityMarkerHelper;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
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
		final IInterfaceElement conTarget = getConnectionTarget(event);
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);

		if (conTarget == null || !conTarget.isIsInput() || editor == null) {
			return null;
		}

		if (UtilityMarkerHelper.getMarkedElement(getMarkerId(),
				conTarget) instanceof final IInterfaceElement connectionSource) {
			final var cmd = CreateSubAppCrossingConnectionsCommand
					.createProcessBorderCrossingConnection(connectionSource, conTarget);
			if (cmd.canExecute()) {
				HandlerHelper.getCommandStack(editor).execute(cmd);
				removeMarker(conTarget);
			}

		}
		return null;
	}

	private void removeMarker(final IInterfaceElement conTarget) {
		final var root = EcoreUtil.getRootContainer(conTarget);
		if (root instanceof final LibraryElement le && le.getTypeEntry() != null
				&& le.getTypeEntry().getFile() != null) {
			UtilityMarkerHelper.deleteElementMarker(getMarkerId(), le.getTypeEntry().getFile());
		}
	}

	private static IInterfaceElement getConnectionTarget(final ExecutionEvent event) {
		final IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);
		return (selection instanceof final IStructuredSelection sel
				&& sel.getFirstElement() instanceof final InterfaceEditPart ep) ? ep.getModel() : null;
	}

	@Override
	protected String getMarkerId() {
		return UtilityMarkerHelper.CONNECTION_SRC_MARKER_ID;
	}

	@Override
	protected String getMarkerName() {
		// explicitly use the full class name for this Message as it is pulled in from
		// another plugin
		return org.eclipse.fordiac.ide.gef.Messages.UtilityMarker_ConnectionSource;
	}

	@Override
	protected EObject getValidSelectedElement(final Object selectedObject) {
		return (selectedObject instanceof final InterfaceEditPart iep) ? iep.getModel() : null;
	}

}
