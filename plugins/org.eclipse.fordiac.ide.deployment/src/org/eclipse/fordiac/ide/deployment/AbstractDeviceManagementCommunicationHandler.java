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
import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener;

/**
 * Base class for coordinating the management communication to a device
 */
public abstract class AbstractDeviceManagementCommunicationHandler {
	
	/**Check if this communication handler is currently connected with its device
	 * 
	 * @return true if a connection is open.
	 */	
	public abstract boolean isConnected();

	public abstract void connect(String address) throws DeploymentException;

	public abstract void disconnect() throws DeploymentException;

	/**  Send a request to the device and return the response
	 * 
	 * @param destination the destination with in the device 
	 * 			- null ... the device 
	 * 			- not null ... the name of a resource
	 * @param request the request to send
	 * @return  the response received from the device
	 * @throws IOException
	 */
	public abstract String sendREQ(final String destination, final String request) throws IOException;

	private final List<IDeploymentListener> listeners = new ArrayList<>();

	protected void responseReceived(final String response, final String source) {
		for (IDeploymentListener listener : listeners) {
			listener.responseReceived(response, source);
		}
	}

	protected void postCommandSent(String info, String destination, String command) {
		for (IDeploymentListener listener : listeners) {
			listener.postCommandSent(command, info);
			listener.postCommandSent(info, destination, command);
		}
	}

	public void addDeploymentListener(final IDeploymentListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeDeploymentListener(final IDeploymentListener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}
}
