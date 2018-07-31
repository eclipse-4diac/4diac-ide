/*******************************************************************************
 * Copyright (c) 2013 - 2018 fortiss GmbH, Johannes Kepler University
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment;

import java.io.IOException;

import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

/**
 * Base class for coordinating the management communication to a device
 */
public interface IDeviceManagementCommunicationHandler {
	
	void connect(String address) throws DeploymentException;

	void disconnect() throws DeploymentException;
	
	/**Check if this communication handler is currently connected with its device
	 * 
	 * @return true if a connection is open.
	 */	
	boolean isConnected();
	
	String getInfo(String destination);

	/**  Send a request to the device and return the response
	 * 
	 * @param destination the destination with in the device 
	 * 			- null ... the device 
	 * 			- not null ... the name of a resource
	 * @param request the request to send
	 * @return  the response received from the device
	 * @throws IOException
	 */
	String sendREQ(final String destination, final String request) throws IOException;
	
	public static void showErrorMessage(String message, Shell shell) {
		MessageBox msgBox = new MessageBox(shell, SWT.OK | SWT.ICON_ERROR);
		msgBox.setMessage(message);
		msgBox.open();
	}
	
}
