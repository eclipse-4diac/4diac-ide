/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.interactors;

import java.util.List;

import org.eclipse.fordiac.ide.deployment.data.ConnectionDeploymentData;
import org.eclipse.fordiac.ide.deployment.data.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public abstract class AbstractDelegatingDeviceManagementInteractor implements IDeviceManagementInteractor {
	private final IDeviceManagementInteractor delegate;

	protected AbstractDelegatingDeviceManagementInteractor(final IDeviceManagementInteractor delegate) {
		this.delegate = delegate;
	}

	@Override
	public boolean isConnected() {
		return delegate.isConnected();
	}

	@Override
	public void connect() throws DeploymentException {
		delegate.connect();
	}

	@Override
	public void disconnect() throws DeploymentException {
		delegate.disconnect();
	}

	@Override
	public void addDeploymentListener(final IDeploymentListener listener) {
		delegate.addDeploymentListener(listener);
	}

	@Override
	public void removeDeploymentListener(final IDeploymentListener listener) {
		delegate.removeDeploymentListener(listener);
	}

	@Override
	public void createResource(final Resource resource) throws DeploymentException {
		delegate.createResource(resource);
	}

	@Override
	public void writeResourceParameter(final Resource resource, final String parameter, final String value)
			throws DeploymentException {
		delegate.writeResourceParameter(resource, parameter, value);
	}

	@Override
	public void writeDeviceParameter(final Device device, final String parameter, final String value)
			throws DeploymentException {
		delegate.writeDeviceParameter(device, parameter, value);
	}

	@Override
	public void createFBInstance(final FBDeploymentData fb, final Resource res) throws DeploymentException {
		delegate.createFBInstance(fb, res);
	}

	@Override
	public void writeFBParameter(final Resource resource, final String name, final String value)
			throws DeploymentException {
		delegate.writeFBParameter(resource, name, value);
	}

	@Override
	public void writeFBParameter(final Resource resource, final String value, final FBDeploymentData fb,
			final VarDeclaration varDecl) throws DeploymentException {
		delegate.writeFBParameter(resource, value, fb, varDecl);
	}

	@Override
	public void createConnection(final Resource res, final ConnectionDeploymentData connectionData)
			throws DeploymentException {
		delegate.createConnection(res, connectionData);
	}

	@Override
	public void startFB(final Resource res, final FBDeploymentData fb) throws DeploymentException {
		delegate.startFB(res, fb);
	}

	@Override
	public void startResource(final Resource res) throws DeploymentException {
		delegate.startResource(res);
	}

	@Override
	public void resetResource(final String resName) throws DeploymentException {
		delegate.resetResource(resName);
	}

	@Override
	public void killResource(final String resName) throws DeploymentException {
		delegate.killResource(resName);
	}

	@Override
	public void stopResource(final Resource res) throws DeploymentException {
		delegate.stopResource(res);
	}

	@Override
	public void startDevice(final Device dev) throws DeploymentException {
		delegate.startDevice(dev);
	}

	@Override
	public void deleteResource(final String resName) throws DeploymentException {
		delegate.deleteResource(resName);
	}

	@Override
	public void deleteFB(final Resource res, final FBDeploymentData fb) throws DeploymentException {
		delegate.deleteFB(res, fb);
	}

	@Override
	public void deleteConnection(final Resource res, final ConnectionDeploymentData con) throws DeploymentException {
		delegate.deleteConnection(res, con);
	}

	@Override
	public void killDevice(final Device dev) throws DeploymentException {
		delegate.killDevice(dev);
	}

	@Override
	public List<org.eclipse.fordiac.ide.deployment.devResponse.Resource> queryResources() throws DeploymentException {
		return delegate.queryResources();
	}

	@Override
	public Response readWatches() throws DeploymentException {
		return delegate.readWatches();
	}

	@Override
	public boolean addWatch(final Resource resource, final String name) throws DeploymentException {
		return delegate.addWatch(resource, name);
	}

	@Override
	public boolean removeWatch(final Resource resource, final String name) throws DeploymentException {
		return delegate.removeWatch(resource, name);
	}

	@Override
	public void triggerEvent(final Resource resource, final String name) throws DeploymentException {
		delegate.triggerEvent(resource, name);
	}

	@Override
	public void forceValue(final Resource resource, final String name, final String value) throws DeploymentException {
		delegate.forceValue(resource, name, value);
	}

	@Override
	public void clearForce(final Resource resource, final String name) throws DeploymentException {
		delegate.clearForce(resource, name);
	}

	protected IDeviceManagementInteractor getDelegate() {
		return delegate;
	}
}
