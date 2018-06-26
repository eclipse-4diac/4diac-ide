/*******************************************************************************
 * Copyright (c) 2014, 2015, 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.ui.wizard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.List;

import org.eclipse.fordiac.ide.deployment.AbstractDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.DeploymentCoordinator;
import org.eclipse.fordiac.ide.deployment.exceptions.DisconnectException;
import org.eclipse.fordiac.ide.deployment.exceptions.InvalidMgmtID;
import org.eclipse.fordiac.ide.deployment.ui.Activator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class BootFileDeviceManagementCommunicationHandler extends AbstractDeviceManagementCommunicationHandler {
	StringBuffer stringBuffer = new StringBuffer();
	public String origMgrID;

	static public void createBootFile(List<Object> workList, String fileName, Shell shell) {
		if (null != fileName) {
			DeploymentCoordinator deployment = DeploymentCoordinator.getInstance();
			BootFileDeviceManagementCommunicationHandler bootFileHandler = new BootFileDeviceManagementCommunicationHandler();
			deployment.performDeployment(workList.toArray(), bootFileHandler);
			bootFileHandler.writeToBootFile(fileName, shell);
		}
	}

	/*
	 * only the static function of this class should be able to create an
	 * instance
	 */
	private BootFileDeviceManagementCommunicationHandler() {
		super();
	}

	@Override
	public void connect(String address) throws InvalidMgmtID, UnknownHostException, IOException {
		origMgrID = address;
	}

	@Override
	public void disconnect() throws DisconnectException {
		// For bootfile writing we need nothing to do here
	}

	@Override
	public void sendREQ(String destination, String request) throws IOException {
		stringBuffer.append(destination + ";" + request + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
		String info = origMgrID;
		if (!destination.equals("")) { //$NON-NLS-1$
			info += ": " + destination; //$NON-NLS-1$
		}
		postCommandSent(info, destination, request);
	}

	/*
	 * take the current state of the string buffer and write it to the given
	 * file
	 */
	public void writeToBootFile(String fileName, Shell shell) {
		File bootFile = new File(fileName);
		int res = SWT.YES;
		if (bootFile.exists()) {
			MessageBox msgBox = new MessageBox(shell, SWT.YES | SWT.NO | SWT.ICON_QUESTION);
			String msg = MessageFormat.format("File Exists, overwrite {0}?",
					new Object[] { bootFile.getAbsolutePath() });
			msgBox.setMessage(msg);
			res = msgBox.open();
		} else {
			try {
				bootFile.createNewFile();
			} catch (IOException e) {
				Activator.getDefault().logError(e.getMessage(), e);
			}
		}
		if (res == SWT.YES) {
			try {
				PrintWriter boot = new PrintWriter(new FileOutputStream(bootFile));
				boot.write(stringBuffer.toString());
				boot.flush();
				boot.close();
			} catch (FileNotFoundException e) {
				Activator.getDefault().logError(e.getMessage(), e);
			}
		}
	}

	public void clearStringBuffer() {
		stringBuffer = new StringBuffer();
	}

}