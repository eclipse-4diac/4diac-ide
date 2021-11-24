/*******************************************************************************
 * Copyright (c) 2018 fortiss GmbH
 * 				 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Maria Jesus Cabral Lassalle, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public abstract class AbstractFileManagementHandler implements IDeviceManagementCommunicationHandler {

	private StringBuilder stringBuffer = new StringBuilder();
	private String origMgrID;

	protected StringBuilder getStringBuffer() {
		return stringBuffer;
	}

	protected void clearStringBuffer() {
		stringBuffer = new StringBuilder();
	}

	@Override
	public boolean isConnected() {
		return true;
	}

	@Override
	public void connect(final String address) throws DeploymentException {
		origMgrID = address;
	}

	@Override
	public void disconnect() throws DeploymentException {
		// For bootfile writing we need nothing to do here
	}

	@Override
	public String sendREQ(final String destination, final String request) throws IOException {
		if (request.contains("Action=\"QUERY\"")) { // return an empty list always //$NON-NLS-1$
			return "<Response ID=\"0\"/>"; //$NON-NLS-1$
		}
		stringBuffer.append(destination + ";" + request + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
		return ""; //$NON-NLS-1$
	}

	@Override
	public String getInfo(final String destination) {
		String info = origMgrID;
		if (!destination.equals("")) { //$NON-NLS-1$
			info += ": " + destination; //$NON-NLS-1$
		}
		return info;
	}

	protected boolean writeToBootFile(final String fileName, final boolean overwriteWithouAsking, final Shell shell) {
		return writeToAnyFile(fileName, stringBuffer.toString(), overwriteWithouAsking, shell);
	}

	protected static boolean writeToAnyFile(final String fileName, final String toWrite, final boolean overwriteWithouAsking,
			final Shell shell) {
		boolean returnValue = false;
		final File bootFile = createOrOverwriteFile(fileName, overwriteWithouAsking, shell);
		if (null != bootFile) {
			try (Writer boot = new OutputStreamWriter(new FileOutputStream(bootFile), StandardCharsets.UTF_8)) {
				boot.write(toWrite);
				boot.flush();
				returnValue = true;
			} catch (final IOException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
				IDeviceManagementCommunicationHandler.showErrorMessage(MessageFormat.format(
						Messages.AbstractFileManagementHandler_CouldNotWriteFile, fileName, e.getMessage()), shell);
			}
		}

		return returnValue;
	}

	private static File createOrOverwriteFile(final String fileName, final boolean overwriteWithouAsking, final Shell shell) {
		final File bootFile = new File(fileName);
		int res = SWT.YES;
		if (bootFile.exists()) {
			if (!overwriteWithouAsking) {
				final MessageBox msgBox = new MessageBox(shell, SWT.YES | SWT.NO | SWT.ICON_QUESTION);
				final String msg = MessageFormat.format(Messages.AbstractFileManagementHandler_FileExists,
						bootFile.getAbsolutePath());
				msgBox.setMessage(msg);
				res = msgBox.open();
			}
		} else {
			try {
				if (!bootFile.createNewFile()) {
					IDeviceManagementCommunicationHandler
					.showErrorMessage(Messages.AbstractFileManagementHandler_CouldnotCreateFile, shell);
					res = SWT.NO;
				}
			} catch (final IOException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
				IDeviceManagementCommunicationHandler.showErrorMessage(MessageFormat.format(
						Messages.AbstractFileManagementHandler_CouldnotCreateFileWithError, e.getMessage()), shell);
			}
		}

		return (res == SWT.YES) ? bootFile : null;
	}
}
