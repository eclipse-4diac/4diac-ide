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
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.application.policies.DeleteTargetInterfaceElementPolicy;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
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
		if (editor != null) {
			final Connection con = getSelectedConnection(HandlerUtil.getCurrentSelection(event));
			final Command cmd = (isEpxandedSubapp(con.getSourceElement())) ? createTargetConDeletionCmds(con)
					: createSourceConDeletionCmds(con);
			if (cmd.canExecute()) {
				final CommandStack commandStack = HandlerHelper.getCommandStack(editor);
				commandStack.execute(cmd);
			}
		}
		return Status.OK_STATUS;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);
		setBaseEnabled(getSelectedConnection(selection) != null);
	}

	private static Command createSourceConDeletionCmds(final Connection con) {
		final CompoundCommand compoundCmd = new CompoundCommand();

		final IInterfaceElement destination = con.getDestination();
		compoundCmd.add(new DeleteSubAppInterfaceElementCommand(destination));

		destination.getOutputConnections().stream() // -
				.filter(c -> isEpxandedSubapp(c.getDestinationElement())) // only treat dests which are expanded subapps
				.forEach(c -> compoundCmd.add(new DeleteSubAppInterfaceElementCommand(c.getDestination())));

		return compoundCmd;
	}

	private static Command createTargetConDeletionCmds(final Connection con) {
		return DeleteTargetInterfaceElementPolicy.createOutputSideDeleteCommand(con.getDestination());
	}

	private static Connection getSelectedConnection(final Object selection) {
		if (selection instanceof final IStructuredSelection structSel && !structSel.isEmpty() && (structSel.size() == 1)
				&& structSel.getFirstElement() instanceof final EditPart ep) {
			final Object model = ep.getModel();
			if (model instanceof final Connection con
					&& (isEpxandedSubapp(con.getSourceElement()) || isEpxandedSubapp(con.getDestinationElement()))) {
				return con;
			}
		}
		return null;
	}

	private static boolean isEpxandedSubapp(final FBNetworkElement sourceElement) {
		return sourceElement instanceof final SubApp subapp && subapp.isUnfolded();
	}

}
