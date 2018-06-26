/*******************************************************************************
 * Copyright (c) 2018 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.interactors;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Set;

import org.eclipse.fordiac.ide.deployment.AbstractDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.exceptions.DisconnectException;
import org.eclipse.fordiac.ide.deployment.exceptions.InvalidMgmtID;
import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener;
import org.eclipse.fordiac.ide.model.libraryElement.Device;

public abstract class AbstractDeviceManagementInteractor implements IDeviceManagementInteractor {
	
	private final AbstractDeviceManagementCommunicationHandler commHandler;
	private final Device device;
	protected Set<String> fbTypes = null;
	protected Set<String> adapterTypes = null;
	
	protected AbstractDeviceManagementInteractor(Device dev, AbstractDeviceManagementCommunicationHandler overrideHandler){
		this.device = dev;
		this.commHandler = (null != overrideHandler) ? overrideHandler : createCommunicationHandler(dev) ;
	}
	
	protected AbstractDeviceManagementCommunicationHandler getDevMgmComHandler() {
		return commHandler; 
	}
	
	@Override
	public void connect() throws InvalidMgmtID, UnknownHostException, IOException {
		getDevMgmComHandler().connect(getDeviceAddress(device));
	}

	@Override
	public void disconnect() throws DisconnectException {
		getDevMgmComHandler().disconnect();
	}
	
	@Override
	public Set<String> getTypes() {
		return fbTypes;
	}
	
	protected void setTypes(Set<String> types) {
		fbTypes = (null != types) ? types : Collections.emptySet();
	}
	
	@Override
	public Set<String> getAdapterTypes() {
		return adapterTypes;
	}
	
	protected void setAdapterTypes(Set<String> types) {
		adapterTypes = (null != types) ? types : Collections.emptySet();
	}
	
	@Override
	public void resetTypes() {
		fbTypes = null;
		adapterTypes = null;
	}
	
	@Override
	public void addDeploymentListener(final IDeploymentListener listener) {
		commHandler.addDeploymentListener(listener);
	}

	@Override
	public void removeDeploymentListener(final IDeploymentListener listener) {
		commHandler.removeDeploymentListener(listener);
	}
	
	public void sendREQ(final String destination, final String request) throws IOException {
		commHandler.sendREQ(destination, request);
		// TODO maybe an error message would be good
	}

	/** create a device managment communication handler suitable for the given device
	 * 
	 * @param dev the device to be checked
	 * @return the created handler
	 */
	protected abstract AbstractDeviceManagementCommunicationHandler createCommunicationHandler(Device dev);

	/**Provide for the given device the address needed for the commhandler to connect to it
	 * 
	 * @param device the dvice to connect to
	 * @return  the device's address
	 */
	protected abstract String getDeviceAddress(Device device);
}
