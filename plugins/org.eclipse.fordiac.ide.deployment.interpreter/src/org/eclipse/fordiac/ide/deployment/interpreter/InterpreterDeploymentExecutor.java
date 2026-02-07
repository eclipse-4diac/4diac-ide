/*******************************************************************************
 * Copyright (c) 2026 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.deployment.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.resources.IResource;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.debug.replaydebugging.ReplayDebuggingDevice;
import org.eclipse.fordiac.ide.deployment.data.ConnectionDeploymentData;
import org.eclipse.fordiac.ide.deployment.data.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentLaunchConfigurationDelegate;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.GlobalConstantsEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;

/**
 * @brief Deployment executor for internal devices running on the interpreter
 */
public class InterpreterDeploymentExecutor implements IDeviceManagementInteractor {

	private final Device device;
	private ReplayDebuggingDevice interpreterDevice;
	private final List<IDeploymentListener> listeners = new ArrayList<>();

	public InterpreterDeploymentExecutor(final Device dev) {
		this.device = dev;
	}

	protected Device getDevice() {
		return device;
	}

	protected static Optional<IResource> getResource(final AutomationSystem system) {
		return Optional.ofNullable(system).map(AutomationSystem::getTypeEntry).map(TypeEntry::getFile);
	}

	protected static Optional<ReplayDebuggingDevice> findDevice(final Device device) {
		return getResource(device.getAutomationSystem()).map(DeploymentLaunchConfigurationDelegate::getActiveLaunches)
				.stream().flatMap(List::stream).map(launch -> findDevice(launch, device)).flatMap(Optional::stream)
				.findAny();
	}

	protected static Optional<ReplayDebuggingDevice> findDevice(final ILaunch launch, final Device device) {
		final var targets = launch.getDebugTargets();
		for (final var target : targets) {
			if (target instanceof final ReplayDebuggingDevice possibleDevice) {
				final var deviceName = possibleDevice.getName();
				if (deviceName.equals(device.getName())
						&& !possibleDevice.getDeviceManagementExecutorService().isTerminated()) {
					return Optional.of(possibleDevice);

				}
			}
		}
		return Optional.empty();
	}

	@Override
	public boolean isConnected() {
		return interpreterDevice != null;
	}

	@Override
	public void connect() throws DeploymentException {
		final var possibleActiveDevice = findDevice(device);
		if (possibleActiveDevice.isPresent()) {
			interpreterDevice = possibleActiveDevice.get();
			return;
		}
		throw new DeploymentException("Cannot find active interpreter device for device " + device.getName());
	}

	@Override
	public void disconnect() {
		interpreterDevice = null;
	}

	/************************************************************************
	 * Listener commands
	 ************************************************************************/

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

	@Override
	public void createResource(final Resource resource) {
		// nothing to do here
	}

	@Override
	public void writeResourceParameter(final Resource resource, final String parameter, final String value) {
		// nothing to do here
	}

	@Override
	public void writeDeviceParameter(final Device device, final String parameter, final String value) {
		// nothing to do here

	}

	@Override
	public void createFBInstance(final FBDeploymentData fb, final Resource res) throws DeploymentException {
		// nothing to do here. All needed instances were already created and initialized
		// when the resource was created.
	}

	@Override
	public void writeFBParameter(final Resource resource, final String name, final String value) {
		// nothing to do here. All needed instances were already created and initialized
		// when the resource was created.
	}

	@Override
	public void writeFBParameter(final Resource resource, final String value, final FBDeploymentData fb,
			final VarDeclaration varDecl) {
		// nothing to do here. All needed instances were already created and initialized
		// when the resource was created.

	}

	@Override
	public void createConnection(final Resource res, final ConnectionDeploymentData connectionData) {
		// nothing to do here. All needed instances were already created and initialized
		// when the resource was created.

	}

	@Override
	public void startFB(final Resource res, final FBDeploymentData fb) throws DeploymentException {
		// nothing to do here. There's no implementation of the FB's states in the
		// interpreter.
	}

	@Override
	public void startResource(final Resource res) throws DeploymentException {
		// TODO: Think about providing a resource simulator
	}

	@Override
	public void resetResource(final String resName) throws DeploymentException {
		// TODO: Think about providing a resource simulator
	}

	@Override
	public void killResource(final String resName) throws DeploymentException {
		// TODO: Think about providing a resource simulator
	}

	@Override
	public void stopResource(final Resource res) throws DeploymentException {
		// TODO: Think about providing a resource simulator
	}

	@Override
	public void deleteResource(final String resName) {
		// TODO: Think about providing a resource simulator
	}

	@Override
	public void startDevice(final Device dev) throws DeploymentException {
		// nothing to do here.
	}

	@Override
	public void deleteFB(final Resource res, final FBDeploymentData fb) throws DeploymentException {
		// not supported
	}

	@Override
	public void deleteConnection(final Resource res, final ConnectionDeploymentData con) throws DeploymentException {
		// not supported
	}

	@Override
	public void killDevice(final Device dev) throws DeploymentException {
		// nothing to do here
	}

	@Override
	public List<org.eclipse.fordiac.ide.deployment.devResponse.Resource> queryResources() throws DeploymentException {
		// this is used by the deployment interface, which we don't care about for the
		// interpreter
		return List.of();
	}

	@Override
	public Response queryFBType(final FBTypeEntry entry) throws DeploymentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response queryDataType(final DataTypeEntry entry) throws DeploymentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response queryGlobalConstType(final GlobalConstantsEntry entry) throws DeploymentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response readWatches() throws DeploymentException {
		return EcoreUtil.copy(interpreterDevice.getDeviceResponse().getResponse());
	}

	@Override
	public boolean addWatch(final Resource resource, final String name) throws DeploymentException {
		// everything is always watched anyways
		return true;
	}

	@Override
	public boolean removeWatch(final Resource resource, final String name) throws DeploymentException {
		// everything is always watched
		return true;
	}

	@Override
	public void triggerEvent(final Resource resource, final String name) throws DeploymentException {
		interpreterDevice.triggerEvent(resource, name);
	}

	@Override
	public void forceValue(final Resource resource, final String name, final String value) throws DeploymentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearForce(final Resource resource, final String name) throws DeploymentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void readTraces(final Device device, final String path) throws DeploymentException {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<String> replayNextEvent(final Resource resource) throws DeploymentException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
