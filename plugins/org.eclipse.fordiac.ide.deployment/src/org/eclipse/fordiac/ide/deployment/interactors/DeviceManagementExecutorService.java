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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.eclipse.fordiac.ide.deployment.data.ConnectionDeploymentData;
import org.eclipse.fordiac.ide.deployment.data.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class DeviceManagementExecutorService implements IDeviceManagementExecutorService {

	private final IDeviceManagementInteractor delegate;
	private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

	protected DeviceManagementExecutorService(final IDeviceManagementInteractor delegate) {
		this.delegate = delegate;
	}

	@Override
	public boolean isConnected() {
		return delegate.isConnected();
	}

	@Override
	public void connect() throws DeploymentException {
		unwrap(connectAsync());
	}

	@Override
	public void disconnect() throws DeploymentException {
		unwrap(disconnectAsync());
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
		unwrap(createResourceAsync(resource));
	}

	@Override
	public void writeResourceParameter(final Resource resource, final String parameter, final String value)
			throws DeploymentException {
		unwrap(writeResourceParameterAsync(resource, parameter, value));
	}

	@Override
	public void writeDeviceParameter(final Device device, final String parameter, final String value)
			throws DeploymentException {
		unwrap(writeDeviceParameterAsync(device, parameter, value));
	}

	@Override
	public void createFBInstance(final FBDeploymentData fb, final Resource res) throws DeploymentException {
		unwrap(createFBInstanceAsync(fb, res));
	}

	@Override
	public void writeFBParameter(final Resource resource, final String name, final String value)
			throws DeploymentException {
		unwrap(writeFBParameterAsync(resource, name, value));
	}

	@Override
	public void writeFBParameter(final Resource resource, final String value, final FBDeploymentData fb,
			final VarDeclaration varDecl) throws DeploymentException {
		unwrap(writeFBParameterAsync(resource, value, fb, varDecl));
	}

	@Override
	public void createConnection(final Resource res, final ConnectionDeploymentData connectionData)
			throws DeploymentException {
		unwrap(createConnectionAsync(res, connectionData));
	}

	@Override
	public void startFB(final Resource res, final FBDeploymentData fb) throws DeploymentException {
		unwrap(startFBAsync(res, fb));
	}

	@Override
	public void startResource(final Resource res) throws DeploymentException {
		unwrap(startResourceAsync(res));
	}

	@Override
	public void resetResource(final String resName) throws DeploymentException {
		unwrap(resetResourceAsync(resName));
	}

	@Override
	public void killResource(final String resName) throws DeploymentException {
		unwrap(killResourceAsync(resName));
	}

	@Override
	public void stopResource(final Resource res) throws DeploymentException {
		unwrap(stopResourceAsync(res));
	}

	@Override
	public void startDevice(final Device dev) throws DeploymentException {
		unwrap(startDeviceAsync(dev));
	}

	@Override
	public void deleteResource(final String resName) throws DeploymentException {
		unwrap(deleteResourceAsync(resName));
	}

	@Override
	public void deleteFB(final Resource res, final FBDeploymentData fb) throws DeploymentException {
		unwrap(deleteFBAsync(res, fb));
	}

	@Override
	public void deleteConnection(final Resource res, final ConnectionDeploymentData con) throws DeploymentException {
		unwrap(deleteConnectionAsync(res, con));
	}

	@Override
	public void killDevice(final Device dev) throws DeploymentException {
		unwrap(killDeviceAsync(dev));
	}

	@Override
	public List<org.eclipse.fordiac.ide.deployment.devResponse.Resource> queryResources() throws DeploymentException {
		return unwrap(queryResourcesAsync());
	}

	@Override
	public Response readWatches() throws DeploymentException {
		return unwrap(readWatchesAsync());
	}

	@Override
	public boolean addWatch(final Resource resource, final String name) throws DeploymentException {
		return unwrap(addWatchAsync(resource, name)).booleanValue();
	}

	@Override
	public boolean removeWatch(final Resource resource, final String name) throws DeploymentException {
		return unwrap(removeWatchAsync(resource, name)).booleanValue();
	}

	@Override
	public void triggerEvent(final Resource resource, final String name) throws DeploymentException {
		unwrap(triggerEventAsync(resource, name));
	}

	@Override
	public void forceValue(final Resource resource, final String name, final String value) throws DeploymentException {
		unwrap(forceValueAsync(resource, name, value));
	}

	@Override
	public void clearForce(final Resource resource, final String name) throws DeploymentException {
		unwrap(clearForceAsync(resource, name));
	}

	@Override
	public Future<Void> connectAsync() {
		return executorService.submit(() -> {
			delegate.connect();
			return null;
		});
	}

	@Override
	public Future<Void> disconnectAsync() {
		return executorService.submit(() -> {
			delegate.disconnect();
			return null;
		});
	}

	@Override
	public Future<Void> createResourceAsync(final Resource resource) {
		return executorService.submit(() -> {
			delegate.createResource(resource);
			return null;
		});
	}

	@Override
	public Future<Void> writeResourceParameterAsync(final Resource resource, final String parameter,
			final String value) {
		return executorService.submit(() -> {
			delegate.writeResourceParameter(resource, parameter, value);
			return null;
		});
	}

	@Override
	public Future<Void> writeDeviceParameterAsync(final Device device, final String parameter, final String value) {
		return executorService.submit(() -> {
			delegate.writeDeviceParameter(device, parameter, value);
			return null;
		});
	}

	@Override
	public Future<Void> createFBInstanceAsync(final FBDeploymentData fb, final Resource res) {
		return executorService.submit(() -> {
			delegate.createFBInstance(fb, res);
			return null;
		});
	}

	@Override
	public Future<Void> writeFBParameterAsync(final Resource resource, final String name, final String value)
			throws DeploymentException {
		return executorService.submit(() -> {
			delegate.writeFBParameter(resource, name, value);
			return null;
		});
	}

	@Override
	public Future<Void> writeFBParameterAsync(final Resource resource, final String value, final FBDeploymentData fb,
			final VarDeclaration varDecl) throws DeploymentException {
		return executorService.submit(() -> {
			delegate.writeFBParameter(resource, value, fb, varDecl);
			return null;
		});
	}

	@Override
	public Future<Void> createConnectionAsync(final Resource res, final ConnectionDeploymentData connectionData) {
		return executorService.submit(() -> {
			delegate.createConnection(res, connectionData);
			return null;
		});
	}

	@Override
	public Future<Void> startFBAsync(final Resource res, final FBDeploymentData fb) {
		return executorService.submit(() -> {
			delegate.startFB(res, fb);
			return null;
		});
	}

	@Override
	public Future<Void> startResourceAsync(final Resource res) {
		return executorService.submit(() -> {
			delegate.startResource(res);
			return null;
		});
	}

	@Override
	public Future<Void> resetResourceAsync(final String resName) {
		return executorService.submit(() -> {
			delegate.resetResource(resName);
			return null;
		});
	}

	@Override
	public Future<Void> killResourceAsync(final String resName) {
		return executorService.submit(() -> {
			delegate.killResource(resName);
			return null;
		});
	}

	@Override
	public Future<Void> stopResourceAsync(final Resource res) {
		return executorService.submit(() -> {
			delegate.stopResource(res);
			return null;
		});
	}

	@Override
	public Future<Void> startDeviceAsync(final Device dev) {
		return executorService.submit(() -> {
			delegate.startDevice(dev);
			return null;
		});
	}

	@Override
	public Future<Void> deleteResourceAsync(final String resName) {
		return executorService.submit(() -> {
			delegate.deleteResource(resName);
			return null;
		});
	}

	@Override
	public Future<Void> deleteFBAsync(final Resource res, final FBDeploymentData fb) {
		return executorService.submit(() -> {
			delegate.deleteFB(res, fb);
			return null;
		});
	}

	@Override
	public Future<Void> deleteConnectionAsync(final Resource res, final ConnectionDeploymentData con) {
		return executorService.submit(() -> {
			delegate.deleteConnection(res, con);
			return null;
		});
	}

	@Override
	public Future<Void> killDeviceAsync(final Device dev) {
		return executorService.submit(() -> {
			delegate.killDevice(dev);
			return null;
		});
	}

	@Override
	public Future<List<org.eclipse.fordiac.ide.deployment.devResponse.Resource>> queryResourcesAsync() {
		return executorService.submit(delegate::queryResources);
	}

	@Override
	@SuppressWarnings("unchecked")
	public ScheduledFuture<Void> queryResourcesPeriodically(
			final Consumer<List<org.eclipse.fordiac.ide.deployment.devResponse.Resource>> consumer, final long period,
			final TimeUnit unit) {
		return (ScheduledFuture<Void>) executorService.scheduleAtFixedRate(() -> {
			try {
				consumer.accept(delegate.queryResources());
			} catch (final DeploymentException e) {
				sneakyThrow(e); // will be wrapped in an ExecutionException anyway
			}
		}, 0, period, unit);
	}

	@Override
	public Future<Response> readWatchesAsync() {
		return executorService.submit(delegate::readWatches);
	}

	@Override
	@SuppressWarnings("unchecked")
	public ScheduledFuture<Void> readWatchesPeriodically(final Consumer<Response> consumer, final long period,
			final TimeUnit unit) {
		return (ScheduledFuture<Void>) executorService.scheduleAtFixedRate(() -> {
			try {
				consumer.accept(delegate.readWatches());
			} catch (final DeploymentException e) {
				sneakyThrow(e); // will be wrapped in an ExecutionException anyway
			}
		}, 0, period, unit);
	}

	@Override
	public Future<Boolean> addWatchAsync(final Resource resource, final String name) {
		return executorService.submit(() -> Boolean.valueOf(delegate.addWatch(resource, name)));
	}

	@Override
	public Future<Boolean> removeWatchAsync(final Resource resource, final String name) {
		return executorService.submit(() -> Boolean.valueOf(delegate.removeWatch(resource, name)));
	}

	@Override
	public Future<Void> triggerEventAsync(final Resource resource, final String name) {
		return executorService.submit(() -> {
			delegate.triggerEvent(resource, name);
			return null;
		});
	}

	@Override
	public Future<Void> forceValueAsync(final Resource resource, final String name, final String value) {
		return executorService.submit(() -> {
			delegate.forceValue(resource, name, value);
			return null;
		});
	}

	@Override
	public Future<Void> clearForceAsync(final Resource resource, final String name) {
		return executorService.submit(() -> {
			delegate.clearForce(resource, name);
			return null;
		});
	}

	@Override
	public void shutdown() {
		executorService.shutdown();
	}

	@Override
	public boolean isShutdown() {
		return executorService.isShutdown();
	}

	@Override
	public boolean isTerminated() {
		return executorService.isTerminated();
	}

	@Override
	public void close() throws Exception {
		executorService.close();
		if (delegate.isConnected()) {
			delegate.disconnect();
		}
	}

	private static <T> T unwrap(final Future<T> future) throws DeploymentException {
		try {
			return future.get();
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new DeploymentException(e.getMessage(), e);
		} catch (final ExecutionException e) {
			if (e.getCause() instanceof final DeploymentException de) {
				throw de;
			}
			throw new DeploymentException(e.getCause().getMessage(), e.getCause());
		}
	}

	@SuppressWarnings("unchecked")
	private static <E extends Throwable> void sneakyThrow(final Throwable e) throws E {
		throw (E) e;
	}
}
