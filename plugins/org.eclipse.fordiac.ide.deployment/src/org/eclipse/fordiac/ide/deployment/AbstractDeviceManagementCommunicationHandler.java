/*******************************************************************************
 * Copyright (c) 2013 - 2017 fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Alois Zoitl, Monika Wenger - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.eclipse.fordiac.ide.deployment.exceptions.DisconnectException;
import org.eclipse.fordiac.ide.deployment.exceptions.InvalidMgmtID;
import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener;

/**
 * Base class for coordinating the management communication to a device
 */
public abstract class AbstractDeviceManagementCommunicationHandler {
	protected HashSet<String> fbTypes = null;
	protected HashSet<String> adapterTypes = null;

	public HashSet<String> getTypes() {
		return fbTypes;
	}

	public HashSet<String> getAdapterTypes() {
		return adapterTypes;
	}
	
	public void resetTypes() {
		fbTypes = null;
		adapterTypes = null;
	}

	abstract public void connect(String address) throws InvalidMgmtID, UnknownHostException, IOException;

	abstract public void disconnect() throws DisconnectException;

	abstract public void sendREQ(final String destination, final String request) throws IOException;

	abstract public void sendQUERY(final String destination, final String request) throws IOException;

	private final ArrayList<IDeploymentListener> listeners = new ArrayList<IDeploymentListener>();

	protected void responseReceived(final String response, final String source) {
		for (Iterator<IDeploymentListener> iterator = listeners.iterator(); iterator.hasNext();) {
			IDeploymentListener listener = iterator.next();
			listener.responseReceived(response, source);
		}
	}

	protected void postCommandSent(String info, String destination, String command) {
		for (Iterator<IDeploymentListener> iterator = listeners.iterator(); iterator.hasNext();) {
			IDeploymentListener listener = iterator.next();
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
