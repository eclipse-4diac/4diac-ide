/*******************************************************************************
 * Copyright (c) 2020, 2021 Johannes Kepler University Linz
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Muddasir Shakil - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr - reworked for breadcrumb editor
 *   Lukas Wais - implemented progress bar
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class UpdateAllFBTypesHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final CompoundCommand command = createCommand();

		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final FBNetwork fbnetwork = editor.getAdapter(FBNetwork.class);
		final CommandStack stack = editor.getAdapter(CommandStack.class);

		for (final FBNetworkElement element : fbnetwork.getNetworkElements()) {
			if (element.getType() != null) {
				final Command updateFBTypeCmd = UpdateFBTypeHandler.getUpdateCommand(element);
				if (updateFBTypeCmd.canExecute()) {
					command.add(updateFBTypeCmd);
				}
			}
		}
		if (stack != null) {
			stack.execute(command);
		}

		return Status.OK_STATUS;
	}

	private static CompoundCommand createCommand() {
		return new CompoundCommand() {
			@Override
			public void execute() {
				final ProgressMonitorDialog dialog = new ProgressMonitorDialog(Display.getDefault().getActiveShell());
				try {
					dialog.run(true, false, monitor -> {
						monitor.beginTask("Updating", getCommands().size()); //$NON-NLS-1$
						for (final Command cmd : getCommands()) {
							Display.getDefault().syncExec(cmd::execute);
							monitor.worked(1);
						}
						monitor.done();
					});
				} catch (final InvocationTargetException | InterruptedException e) {
					FordiacLogHelper.logError(e.getMessage(), e);
					// Restore interrupted state
					Thread.currentThread().interrupt();
				}
			}
		};
	}
}
