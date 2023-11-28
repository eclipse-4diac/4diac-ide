/*******************************************************************************
 * Copyright (c) 2018 Johannes Kepler University
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
package org.eclipse.fordiac.ide.deployment.ui.handlers;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.fordiac.ide.deployment.devResponse.Resource;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.deployment.ui.Messages;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.ui.PlatformUI;

/**
 * The Class SendClearAction.
 */
public class FullyCleanDeviceHandler extends AbstractDeviceDeploymentCommand {

	@Override
	protected void executeCommand(final IDeviceManagementInteractor executor) throws DeploymentException {
		final List<String> resNames = executor.queryResources().stream().map(Resource::getName).toList();

		if (!resNames.isEmpty() && 0 == runDeleteQuestionDialog(resNames)) {
			deleteResources(executor, resNames);
		}
	}

	@Override
	protected String getErrorMessageHeader() {
		return Messages.FullyCleanDeviceHandler_FullyCleanDeviceError;
	}

	private int runDeleteQuestionDialog(final List<String> resNames) {
		final StringBuilder message = new StringBuilder(MessageFormat
				.format(Messages.FullyCleanDeviceHandler_DeleteResourcesFromDevice, getDevice().getName()));

		for (final String name : resNames) {
			message.append("\n\t"); //$NON-NLS-1$
			message.append(name);
		}
		final MessageDialog dialog = new MessageDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				Messages.FullyCleanDeviceHandler_FullyCleanDevice, null, message.toString(), MessageDialog.QUESTION,
				new String[] { Messages.FullyCleanDeviceHandler_Delete, SWT.getMessage("SWT_Cancel") }, 0); //$NON-NLS-1$
		return dialog.open();
	}

	private static void deleteResources(final IDeviceManagementInteractor executor, final List<String> resNames)
			throws DeploymentException {
		for (final String res : resNames) {
			executor.deleteResource(res);
		}
	}

}
