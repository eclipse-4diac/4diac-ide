/*******************************************************************************
 * Copyright (c) 2013 - 2016 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.systemmanagement.ui.Messages;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;

public class RenameApplication extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		if ((selection instanceof TreeSelection)
				&& (((TreeSelection) selection).getFirstElement() instanceof Application)) {
			final Application application = (Application) ((TreeSelection) selection).getFirstElement();
			final String newName = showRenameDialog(application);
			if (null != newName) {
				performApplicationRename(application, newName);
			}
		}
		return null;
	}

	private static String showRenameDialog(final Application application) {
		final InputDialog dialog = new InputDialog(Display.getDefault().getActiveShell(), "Rename Application",
				"Enter new application name", application.getName(), newText -> {
					if (application.getName().equals(newText)) {
						return "Application name not different!";
					}
					if (!NameRepository.isValidName(application, newText)) {
						return Messages.NewApplicationPage_ErrorMessageInvalidAppName;
					}
					return null;
				});
		final int ret = dialog.open();
		if (ret == Window.OK) {
			return dialog.getValue();
		}
		return null;
	}

	private static void performApplicationRename(final Application application, final String newName) {
		final ChangeNameCommand cmd = ChangeNameCommand.forName(application, newName);
		final CommandStack cmdStack = application.getAutomationSystem().getCommandStack();
		cmdStack.execute(cmd);
	}
}