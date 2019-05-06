/*******************************************************************************
 * Copyright (c) 2018 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.ui.handlers;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.ui.PlatformUI;

/**
 * The Class SendClearAction.
 */
public class FullyCleanDeviceHandler extends AbstractDeviceDeploymentCommand {

	@Override
	protected void executeCommand(IDeviceManagementInteractor executor) throws DeploymentException {
		List<String> resNames = executor.queryResources().stream().map(res -> res.getName()).collect(Collectors.toList());
		
		if(!resNames.isEmpty() && 0 == runDeleteQuestionDialog(resNames)) {			
			deleteResources(executor, resNames);
		}
	}

	@Override
	protected String getErrorMessageHeader() {
		return "Fully Clean Device Error";
	}

	private int runDeleteQuestionDialog(List<String> resNames) {
		StringBuilder message = new StringBuilder(MessageFormat.format("Delete the following resources from device \"{0}\":", getDevice().getName()));
		
		for (String name : resNames) {
			message.append("\n\t");  //$NON-NLS-1$
			message.append(name);
		}
		MessageDialog dialog = new MessageDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
				"Fully clean device", null, message.toString(), MessageDialog.QUESTION, 
				new String[] { "Delete",  SWT.getMessage("SWT_Cancel") }, 0); //$NON-NLS-2$
		return dialog.open();
	}

	private static void deleteResources(IDeviceManagementInteractor executor, List<String> resNames) throws DeploymentException {
		for (String res : resNames) {
			executor.deleteResource(res);
		}
	}
	
}
