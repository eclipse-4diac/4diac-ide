/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Sebastian Hollersbacher - changed to recursively delete connections
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class AggressiveDeleteConnection extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final Connection con = getSelectedConnection(HandlerUtil.getCurrentSelection(event));
		if (editor != null && con != null) {
			final CompoundCommand cmd = new CompoundCommand();
			addSourceDeleteCommand(cmd, con);
			addDestinationDeleteCommand(cmd, con);

			if (cmd.canExecute()) {
				final CommandStack commandStack = HandlerHelper.getCommandStack(editor);
				commandStack.execute(cmd);
			}
		}
		return null;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);
		setBaseEnabled(getSelectedConnection(selection) != null);
	}

	private void addSourceDeleteCommand(final CompoundCommand cmd, final Connection conn) {
		final IInterfaceElement source = conn.getSource();
		if (canRemove(conn.getSourceElement()) && source.getOutputConnections().size() == 1) {
			cmd.add(new DeleteSubAppInterfaceElementCommand(source));
			source.getInputConnections().forEach(c -> addSourceDeleteCommand(cmd, c));
		}
	}

	private void addDestinationDeleteCommand(final CompoundCommand cmd, final Connection conn) {
		final IInterfaceElement destination = conn.getDestination();
		if (canRemove(conn.getDestinationElement()) && destination.getInputConnections().size() == 1) {
			cmd.add(new DeleteSubAppInterfaceElementCommand(destination));
			destination.getOutputConnections().forEach(c -> addDestinationDeleteCommand(cmd, c));
		}
	}

	protected Connection getSelectedConnection(final Object selection) {
		if (selection instanceof final IStructuredSelection structSel && structSel.size() == 1
				&& structSel.getFirstElement() instanceof final EditPart ep
				&& ep.getModel() instanceof final Connection con
				&& (canRemove(con.getSourceElement()) || canRemove(con.getDestinationElement()))) {
			return con;
		}
		return null;
	}

	@SuppressWarnings("static-method") // for inheriting in subclasses
	protected boolean canRemove(final FBNetworkElement sourceElement) {
		return sourceElement instanceof final UntypedSubApp subapp && subapp.isUnfolded();
	}
}
