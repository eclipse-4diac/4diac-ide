/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.model.commands.change.TransferCommentsOfConnectedPinsCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class TransferCommentsOfConnectedPinsHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection sel = HandlerUtil.getCurrentStructuredSelection(event);
		final FBNetworkElement element = (FBNetworkElement) ((EditPart) sel.getFirstElement()).getModel();

		final Map<IInterfaceElement, List<IInterfaceElement>> commentsToCopy = findConnectedPins(element);

		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final CommandStack commandStack = editor.getAdapter(CommandStack.class);
		final TransferCommentsOfConnectedPinsCommand cmd = new TransferCommentsOfConnectedPinsCommand(commentsToCopy);
		commandStack.execute(cmd);
		return null;
	}

	private static Map<IInterfaceElement, List<IInterfaceElement>> findConnectedPins(final FBNetworkElement src) {
		final Map<IInterfaceElement, List<IInterfaceElement>> commentsToCopy = new HashMap<>();

		for (final IInterfaceElement outVar : src.getInterface().getOutputVars()) {
			if (!outVar.getOutputConnections().isEmpty()) {
				commentsToCopy.put(outVar,
						outVar.getOutputConnections().stream().map(Connection::getDestination).toList());
			}
		}

		for (final IInterfaceElement event : src.getInterface().getEventOutputs()) {
			if (!event.getOutputConnections().isEmpty()) {
				commentsToCopy.put(event, event.getOutputConnections().stream().map(Connection::getDestination).toList());
			}
		}

		return commentsToCopy;
	}
}
