/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.ui.handler;

import java.text.MessageFormat;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.IStatusHandler;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentLaunchConfigurationDelegate;
import org.eclipse.fordiac.ide.deployment.debug.ui.Messages;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

public class LaunchActiveStatusHandler implements IStatusHandler {

	@Override
	public Object handleStatus(final IStatus status, final Object source) throws CoreException {
		return switch (status.getCode()) {
		case DeploymentLaunchConfigurationDelegate.ALREADY_RUNNING_CODE -> handleAlreadyRunning(source);
		default -> null;
		};
	}

	private static Object handleAlreadyRunning(final Object source) {
		return Display.getDefault()
				.syncCall(() -> Boolean.valueOf(
						MessageDialog.openConfirm(PlatformUI.getWorkbench().getModalDialogShellProvider().getShell(),
								Messages.LaunchActiveStatusHandler_LaunchActiveTitle,
								MessageFormat.format(Messages.LaunchActiveStatusHandler_LaunchActiveMessage, source))));
	}
}
