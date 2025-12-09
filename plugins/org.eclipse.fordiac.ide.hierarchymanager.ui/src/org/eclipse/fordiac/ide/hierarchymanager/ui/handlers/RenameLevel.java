/*******************************************************************************
 * Copyright (c) 2023 Primetals Technology Austria GmbH
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
package org.eclipse.fordiac.ide.hierarchymanager.ui.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Level;
import org.eclipse.fordiac.ide.hierarchymanager.ui.operations.RenameLevelOperation;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;

public class RenameLevel extends AbstractLevelHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		if ((selection instanceof final StructuredSelection sel)
				&& (sel.getFirstElement() instanceof final Level level)) {

			final String newName = showRenameDialog(level);
			if (null != newName) {
				executeOperation(new RenameLevelOperation(level, newName));
			}
		}
		return Status.OK_STATUS;
	}

	private static String showRenameDialog(final Level level) {
		final InputDialog dialog = new InputDialog(Display.getDefault().getActiveShell(), "Rename Level",
				"Enter new level name", level.getName(), newText -> {
					if (level.getName().equals(newText)) {
						return "Level name not different!";
					}
					return null;
				});
		final int ret = dialog.open();
		if (ret == Window.OK) {
			return dialog.getValue();
		}
		return null;
	}
}
