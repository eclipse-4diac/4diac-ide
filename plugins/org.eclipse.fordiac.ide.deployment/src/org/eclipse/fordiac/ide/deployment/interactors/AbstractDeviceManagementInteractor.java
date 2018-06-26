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
import java.util.Collections;
import java.util.Set;

import org.eclipse.fordiac.ide.deployment.AbstractDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener;

public abstract class AbstractDeviceManagementInteractor implements IDeviceManagementInteractor {
	
	protected final AbstractDeviceManagementCommunicationHandler commHandler;
	protected Set<String> fbTypes = null;
	protected Set<String> adapterTypes = null;
	
	protected AbstractDeviceManagementInteractor(AbstractDeviceManagementCommunicationHandler commHandler){
		this.commHandler = commHandler;
	}
	
	@Override
	public AbstractDeviceManagementCommunicationHandler getDevMgmComHandler() {
		return commHandler; 
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


}
