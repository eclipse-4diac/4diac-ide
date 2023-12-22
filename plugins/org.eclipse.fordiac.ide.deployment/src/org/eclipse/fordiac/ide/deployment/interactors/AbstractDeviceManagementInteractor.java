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
 *  Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.interactors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.fordiac.ide.deployment.IDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.util.DeploymentHelper;
import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener;
import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener2;
import org.eclipse.fordiac.ide.model.libraryElement.Device;

public abstract class AbstractDeviceManagementInteractor implements IDeviceManagementInteractor {

	private final IDeviceManagementCommunicationHandler commHandler;
	private final Device device;
	private Set<String> fbTypes = Collections.emptySet();
	private Set<String> adapterTypes = Collections.emptySet();

	private final List<IDeploymentListener> listeners = new ArrayList<>();

	@Override
	public boolean isConnected() {
		return commHandler.isConnected();
	}

	@Override
	public void connect() throws DeploymentException {
		commHandler.connect(DeploymentHelper.getMgrID(device));
		for (final IDeploymentListener listener : listeners) {
			listener.connectionOpened(device);
		}
	}

	@Override
	public void disconnect() throws DeploymentException {
		commHandler.disconnect();
		for (final IDeploymentListener listener : listeners) {
			listener.connectionClosed(device);
		}
	}

	public synchronized String sendREQ(final String destination, final String request) throws IOException {
		final String info = commHandler.getInfo(destination);
		final String response = commHandler.sendREQ(destination, request);
		for (final IDeploymentListener listener : listeners) {
			listener.postCommandSent(info, destination, request); // do something with info
		}
		if (0 != response.length()) {
			for (final IDeploymentListener listener : listeners) {
				if (listener instanceof final IDeploymentListener2 listener2) {
					listener2.postResponseReceived(info, request, response, destination);
				} else {
					listener.postResponseReceived(response, destination);
				}
			}
		}
		return response;
	}

	@Override
	public void addDeploymentListener(final IDeploymentListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	@Override
	public void removeDeploymentListener(final IDeploymentListener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

	protected AbstractDeviceManagementInteractor(final Device dev,
			final IDeviceManagementCommunicationHandler overrideHandler) {
		this.device = dev;
		resetTypes();
		this.commHandler = (null != overrideHandler) ? overrideHandler : createCommunicationHandler(dev);
	}

	protected Device getDevice() {
		return device;
	}

	public Set<String> getTypes() {
		return fbTypes;
	}

	protected void setTypes(final Set<String> types) {
		fbTypes = (null != types) ? types : Collections.emptySet();
	}

	public Set<String> getAdapterTypes() {
		return adapterTypes;
	}

	protected void setAdapterTypes(final Set<String> types) {
		adapterTypes = (null != types) ? types : Collections.emptySet();
	}

	protected final void resetTypes() {
		fbTypes = Collections.emptySet();
		adapterTypes = Collections.emptySet();
	}

	/**
	 * create a device managment communication handler suitable for the given device
	 *
	 * @param dev the device to be checked
	 * @return the created handler
	 */
	protected abstract IDeviceManagementCommunicationHandler createCommunicationHandler(Device dev);

}
