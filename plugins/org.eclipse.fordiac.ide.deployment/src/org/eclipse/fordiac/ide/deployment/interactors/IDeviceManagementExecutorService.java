/*******************************************************************************
 * Copyright Async(c) 2024 Martin Erich Jobst
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
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.eclipse.fordiac.ide.deployment.data.ConnectionDeploymentData;
import org.eclipse.fordiac.ide.deployment.data.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public interface IDeviceManagementExecutorService extends IDeviceManagementInteractor, AutoCloseable {

	static IDeviceManagementExecutorService of(final IDeviceManagementInteractor interactor) {
		if (interactor instanceof final IDeviceManagementExecutorService concurrent) {
			return concurrent;
		}
		return new DeviceManagementExecutorService(interactor);
	}

	/**
	 * Connect asynchronously
	 *
	 * @return The future
	 * @see #connect()
	 */
	Future<Void> connectAsync();

	/**
	 * Disconnect asynchronously
	 *
	 * @return The future
	 * @see #disconnect()
	 */
	Future<Void> disconnectAsync();

	/**
	 * Create resource asynchronously
	 *
	 * @param resource the resource
	 * @return The future
	 * @see #createResource(Resource)
	 */
	Future<Void> createResourceAsync(Resource resource);

	/**
	 * Write resource parameter asynchronously
	 *
	 * @param resource  the resource
	 * @param parameter the parameter
	 * @param value     the value
	 * @return The future
	 * @see #writeResourceParameter(Resource, String, String)
	 */
	Future<Void> writeResourceParameterAsync(Resource resource, String parameter, String value);

	/**
	 * Write device parameter asynchronously
	 *
	 * @param device    the device
	 * @param parameter the parameter
	 * @param value     the value
	 * @return The future
	 * @see #writeDeviceParameter(Device, String, String)
	 */
	Future<Void> writeDeviceParameterAsync(Device device, String parameter, String value);

	/**
	 * Creates the fb instance asynchronously
	 *
	 * @param fb  the fb
	 * @param res the res
	 * @return The future
	 * @see #createFBInstance(FBDeploymentData, Resource)
	 */
	Future<Void> createFBInstanceAsync(FBDeploymentData fb, Resource res);

	/**
	 * Write fb parameter asynchronously
	 *
	 * @param resource The resource
	 * @param name     The qualified name, relative to the resource
	 * @return The future
	 * @see #writeFBParameterAsync(Resource, String, String)
	 */
	Future<Void> writeFBParameterAsync(Resource resource, String name, String value) throws DeploymentException;

	/**
	 * Write fb parameter asynchronously
	 *
	 * @param resource the resource
	 * @param value    the value
	 * @param fb       the fb
	 * @param varDecl  the var decl
	 * @return The future
	 * @see #writeFBParameter(Resource, String, FBDeploymentData, VarDeclaration)
	 */
	Future<Void> writeFBParameterAsync(Resource resource, String value, FBDeploymentData fb, VarDeclaration varDecl)
			throws DeploymentException;

	/**
	 * Creates the connection asynchronously
	 *
	 * @param res            the res
	 * @param connectionData information on the connection to create
	 * @return The future
	 * @see #createConnection(Resource, ConnectionDeploymentData)
	 */
	Future<Void> createConnectionAsync(Resource res, ConnectionDeploymentData connectionData);

	/**
	 * Start FB Instance asynchronously
	 *
	 * @param res the res
	 * @param fb  the fb
	 * @return The future
	 * @see #startFB(Resource, FBDeploymentData)
	 */
	Future<Void> startFBAsync(Resource res, FBDeploymentData fb);

	/**
	 * Start resource asynchronously
	 *
	 * @param res the res
	 * @return The future
	 * @see #startResource(Resource)
	 */
	Future<Void> startResourceAsync(Resource res);

	/**
	 * Reset resource asynchronously
	 *
	 * @param res the res
	 * @return The future
	 * @see #resetResource(String)
	 */
	Future<Void> resetResourceAsync(final String resName);

	/**
	 * Kill resource asynchronously
	 *
	 * @param res the res
	 * @return The future
	 * @see #killResource(String)
	 */
	Future<Void> killResourceAsync(final String resName);

	/**
	 * Stop resource asynchronously
	 *
	 * @param res the res
	 * @return The future
	 * @see #stopResource(Resource)
	 */
	Future<Void> stopResourceAsync(final Resource res);

	/**
	 * Start device asynchronously
	 *
	 * @param dev the dev
	 * @return The future
	 * @see #startDevice(Device)
	 */
	Future<Void> startDeviceAsync(Device dev);

	/**
	 * Delete resource asynchronously
	 *
	 * @param res the res
	 * @return The future
	 * @see #deleteResource(String)
	 */
	Future<Void> deleteResourceAsync(String resName);

	/**
	 * Delete fb instance asynchronously
	 *
	 * @param res the res
	 * @param fb  the fb instance
	 * @return The future
	 * @see #deleteFB(Resource, FBDeploymentData)
	 */
	Future<Void> deleteFBAsync(Resource res, FBDeploymentData fb);

	/**
	 * Delete connection asynchronously
	 *
	 * @param res the res
	 * @param con the connection
	 * @return The future
	 * @see #deleteConnection(Resource, ConnectionDeploymentData)
	 */
	Future<Void> deleteConnectionAsync(Resource res, ConnectionDeploymentData con);

	/**
	 * Kill device asynchronously
	 *
	 * @param dev the device
	 * @return The future
	 * @see #killDevice(Device)
	 */
	Future<Void> killDeviceAsync(Device dev);

	/********************************************************************************************
	 * Query Commands
	 ********************************************************************************************/

	/**
	 * Query resources asynchronously
	 *
	 * @return The future with the queried resources
	 * @see #queryResources()
	 */
	Future<List<org.eclipse.fordiac.ide.deployment.devResponse.Resource>> queryResourcesAsync();

	/**
	 * Query resources periodically
	 *
	 * @param consumer The consumer
	 * @param period   The period
	 * @param unit     The unit
	 * @return The future
	 * @see #queryResources()
	 */
	ScheduledFuture<Void> queryResourcesPeriodically(
			Consumer<List<org.eclipse.fordiac.ide.deployment.devResponse.Resource>> consumer, long period,
			TimeUnit unit);

	/**
	 * Query resources periodically
	 *
	 * @param consumer The consumer
	 * @param error    The error handler
	 * @param period   The period
	 * @param unit     The unit
	 * @return The future
	 * @see #queryResources()
	 */
	ScheduledFuture<Void> queryResourcesPeriodically(
			Consumer<List<org.eclipse.fordiac.ide.deployment.devResponse.Resource>> consumer,
			Consumer<DeploymentException> error, long period, TimeUnit unit);

	/***********************
	 * monitoring commands
	 ****************************************************/

	/**
	 * Read watches from device asynchronously
	 *
	 * @return The future with the response
	 * @see #readWatches()
	 */
	Future<Response> readWatchesAsync();

	/**
	 * Read watches from device periodically
	 *
	 * @param consumer The consumer
	 * @param period   The period
	 * @param unit     The unit
	 * @return The future
	 * @see #readWatches()
	 */
	ScheduledFuture<Void> readWatchesPeriodically(Consumer<Response> consumer, long period, TimeUnit unit);

	/**
	 * Read watches from device periodically
	 *
	 * @param consumer The consumer
	 * @param error    The error handler
	 * @param period   The period
	 * @param unit     The unit
	 * @return The future
	 * @see #readWatches()
	 */
	ScheduledFuture<Void> readWatchesPeriodically(Consumer<Response> consumer, Consumer<DeploymentException> error,
			long period, TimeUnit unit);

	/**
	 * Add a watch asynchronously
	 *
	 * @param resource The resource
	 * @param name     The qualified name, relative to the resource
	 * @return The future with true on success, false otherwise
	 * @see #addWatch(Resource, String)
	 */
	Future<Boolean> addWatchAsync(Resource resource, String name);

	/**
	 * Remove a watch asynchronously
	 *
	 * @param resource The resource
	 * @param name     The qualified name, relative to the resource
	 * @return The future with true on success, false otherwise
	 * @see #removeWatch(Resource, String)
	 */
	Future<Boolean> removeWatchAsync(Resource resource, String name);

	/**
	 * Trigger an event asynchronously
	 *
	 * @param resource The resource
	 * @param name     The qualified name, relative to the resource
	 * @return The future
	 * @see #triggerEvent(Resource, String)
	 */
	Future<Void> triggerEventAsync(Resource resource, String name);

	/**
	 * Force a watch value asynchronously
	 *
	 * @param resource The resource
	 * @param name     The qualified name, relative to the resource
	 * @param value    The value to force
	 * @return The future
	 * @see #forceValue(Resource, String, String)
	 */
	Future<Void> forceValueAsync(Resource resource, String name, String value);

	/**
	 * Clear the force of a watch value asynchronously
	 *
	 * @param resource The resource
	 * @param name     The qualified name, relative to the resource
	 * @return The future
	 * @see #clearForce(Resource, String)
	 */
	Future<Void> clearForceAsync(Resource resource, String name);

	/**
	 * Perform an orderly shut down of all outstanding tasks without disconnecting
	 */
	void shutdown();

	/**
	 * Check whether the execution has been shut down
	 *
	 * @return true if shut down, false otherwise
	 */
	boolean isShutdown();

	/**
	 * Check whether the execution has been shut down and all outstanding requests
	 * have finished
	 *
	 * @return true if terminated, false otherwise
	 */
	boolean isTerminated();
}
