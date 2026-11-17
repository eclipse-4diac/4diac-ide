/*******************************************************************************
 * Copyright (c) 2026 Osoft e.U.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.ui.handler;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.IStatusHandler;
import org.eclipse.fordiac.ide.deployment.debug.ui.Messages;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

/** Warns before a snapshot with missing values is debugged. */
public class IncompleteSnapshotStatusHandler implements IStatusHandler {

	@Override
	public Object handleStatus(final IStatus status, final Object source) {
		Display.getDefault()
				.syncExec(() -> MessageDialog.openWarning(
						PlatformUI.getWorkbench().getModalDialogShellProvider().getShell(),
						Messages.SnapshotDebugHandler_Title, status.getMessage()));
		return null;
	}
}
