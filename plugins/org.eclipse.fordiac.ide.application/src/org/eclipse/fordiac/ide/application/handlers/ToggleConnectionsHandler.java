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

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.commands.change.HideConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
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
		if (selection instanceof IStructuredSelection) {
			final CompoundCommand commands = new CompoundCommand();
			final IEditorPart editor = HandlerUtil.getActiveEditor(event);
			final CommandStack stack = editor.getAdapter(CommandStack.class);
			final GraphicalViewer viewer = editor.getAdapter(GraphicalViewer.class);
			
			for (Object obj : ((IStructuredSelection) selection).toList()) {
				if (obj instanceof ConnectionEditPart) {
					ConnectionEditPart con = (ConnectionEditPart) obj;
					addHideCommand(commands, con.getModel(), con.getFigure().isHidden());
				} else if (obj instanceof AbstractFBNElementEditPart) {
					AbstractFBNElementEditPart fb = (AbstractFBNElementEditPart) obj;
					fb.getModel().getInterface().getAllInterfaceElements().forEach(pin -> hidePinConnections(commands, pin));
				} else if (obj instanceof InterfaceEditPart) {
					InterfaceEditPart pin = (InterfaceEditPart) obj;
					hidePinConnections(commands, pin.getModel());
				}
			}
			
			if (null != stack) {
				stack.execute(commands);
			}
			if (null != viewer) {
				((GraphicalEditPart) viewer.getRootEditPart()).getFigure().invalidateTree();
			}
			return Status.OK_STATUS;
		}
		return Status.CANCEL_STATUS;
	}

	private void hidePinConnections(final CompoundCommand commands, IInterfaceElement iel) {
			if (iel.isIsInput()) {
				iel.getInputConnections().forEach(conn -> addHideCommand(commands, conn, !conn.isVisible()));
			} else {
				iel.getOutputConnections().forEach(conn -> addHideCommand(commands, conn, !conn.isVisible()));
			}
	}

	private void addHideCommand(final CompoundCommand commands, Connection conn, boolean hide) {
		final Command hideConCmd = new HideConnectionCommand(conn, hide);
		if (hideConCmd.canExecute()) {
			commands.add(hideConCmd);
		}
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final ISelection selection = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		final IEditorPart editor = (IEditorPart) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_EDITOR_NAME);
		setBaseEnabled(selection != null && editor != null && selectionContainsConnectionsOrFbs(selection));
	}

	private static boolean selectionContainsConnectionsOrFbs(final ISelection selection) {
		if (selection instanceof IStructuredSelection && !selection.isEmpty()) {

			return ((IStructuredSelection) selection).toList().stream().anyMatch(
					ep -> isConnection(ep) || hasConnection(ep));
		}
		return false;
	}

	private static boolean hasConnection(Object ep) {
		if (ep instanceof AbstractFBNElementEditPart) {
			FBNetworkElement fb = ((AbstractFBNElementEditPart) ep).getModel();
			for (IInterfaceElement ie : fb.getInterface().getAllInterfaceElements()) {
				if(hasConnection(ie)) {
					return true;
				}
			}
		}
		return ((ep instanceof InterfaceEditPart) && (hasConnection(((InterfaceEditPart) ep).getModel())));
	}

	private static boolean hasConnection(IInterfaceElement ie) {
			return (ie.isIsInput() && !ie.getInputConnections().isEmpty() ||
					!ie.isIsInput() && !ie.getOutputConnections().isEmpty());
	}

	private static boolean isConnection(Object ep) {
		return ep instanceof ConnectionEditPart && ((ConnectionEditPart) ep).getFigure() != null;
	}

}
