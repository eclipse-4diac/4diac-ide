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
package org.eclipse.fordiac.ide.deployment.debug;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugVariable;
import org.eclipse.fordiac.ide.deployment.devResponse.Resource;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.DeviceManagementInteractorFactory;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementExecutorService;
import org.eclipse.fordiac.ide.deployment.interactors.SharedWatchDeviceManagementInteractor;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;

public class DeploymentDebugDevice extends DeploymentDebugElement implements IDeploymentDebugTarget {

	private final Device device;
	private final boolean allowTerminate;
	private final Duration pollingInterval;
	private final IDeviceManagementExecutorService deviceManagementExecutor;
	private final Map<String, DeploymentDebugResource> resources = new ConcurrentSkipListMap<>();
	private final AtomicLong variableUpdateCount = new AtomicLong();

	private boolean terminate;

	public DeploymentDebugDevice(final Device device, final DeploymentDebugTarget debugTarget,
			final boolean allowTerminate, final Duration pollingInterval) {
		super(debugTarget);
		this.device = Objects.requireNonNull(device);
		this.allowTerminate = allowTerminate;
		this.pollingInterval = pollingInterval;

		deviceManagementExecutor = IDeviceManagementExecutorService.of(new SharedWatchDeviceManagementInteractor(
				DeviceManagementInteractorFactory.INSTANCE.getDeviceManagementInteractor(device)));

		debugTarget.getLaunch().addDebugTarget(this);
		fireCreationEvent();
	}

	protected void terminated() {
		fireTerminateEvent();
	}

	protected void updateResources(final List<Resource> response) {
		if (resources.keySet().retainAll(response.stream().map(Resource::getName).collect(Collectors.toSet()))) {
			fireChangeEvent(DebugEvent.CONTENT);
		}
		// added resources will fire their own CREATE events
		response.forEach(devResource -> resources.computeIfAbsent(devResource.getName(),
				name -> Optional.ofNullable(device.getResourceNamed(name))
						.map(resource -> new DeploymentDebugResource(resource, this, allowTerminate)).orElse(null)));
	}

	public void connect() throws DebugException {
		try {
			deviceManagementExecutor.connect();
			deviceManagementExecutor.queryResourcesPeriodically(this::updateResources,
					pollingInterval.get(ChronoUnit.NANOS), TimeUnit.NANOSECONDS);
		} catch (final DeploymentException e) {
			throw new DebugException(Status
					.error(MessageFormat.format(Messages.DeploymentDebugDevice_ConnectError, device.getName()), e));
		} finally {
			fireChangeEvent(DebugEvent.STATE);
		}
	}

	@Override
	public void disconnect() throws DebugException {
		try {
			deviceManagementExecutor.close();
		} catch (final Exception e) {
			throw new DebugException(Status
					.error(MessageFormat.format(Messages.DeploymentDebugDevice_DisconnectError, device.getName()), e));
		} finally {
			terminated();
		}
	}

	@Override
	public void terminate() throws DebugException {
		deviceManagementExecutor.killDeviceAsync(device);
		terminate = true;
		disconnect();
	}

	@Override
	public long getVariableUpdateCount() {
		return variableUpdateCount.get();
	}

	public long incrementVariableUpdateCount() {
		return variableUpdateCount.incrementAndGet();
	}

	@Override
	public EvaluatorDebugVariable createVariable(final Variable<?> variable, final String expression) {
		return new DeploymentDebugVariable(variable, expression, this);
	}

	@Override
	public boolean canResume() {
		return resources.values().stream().anyMatch(DeploymentDebugResource::canResume);
	}

	@Override
	public boolean canSuspend() {
		return resources.values().stream().anyMatch(DeploymentDebugResource::canSuspend);
	}

	@Override
	public boolean isSuspended() {
		return !resources.isEmpty() && resources.values().stream().allMatch(DeploymentDebugResource::isSuspended);
	}

	@Override
	public void resume() throws DebugException {
		for (final DeploymentDebugResource resource : resources.values()) {
			if (resource.canResume()) {
				resource.resume();
			}
		}
	}

	@Override
	public void suspend() throws DebugException {
		for (final DeploymentDebugResource resource : resources.values()) {
			if (resource.canSuspend()) {
				resource.suspend();
			}
		}
	}

	@Override
	public boolean supportsBreakpoint(final IBreakpoint breakpoint) {
		return false; // none yet
	}

	@Override
	public void breakpointAdded(final IBreakpoint breakpoint) {
		// do nothing
	}

	@Override
	public void breakpointRemoved(final IBreakpoint breakpoint, final IMarkerDelta delta) {
		// do nothing
	}

	@Override
	public void breakpointChanged(final IBreakpoint breakpoint, final IMarkerDelta delta) {
		// do nothing
	}

	@Override
	public String getName() {
		return device.getName();
	}

	@Override
	public IDeploymentDebugTarget getDebugTarget() {
		return this;
	}

	public DeploymentDebugTarget getPrimaryDebugTarget() {
		return (DeploymentDebugTarget) super.getDebugTarget();
	}

	public Device getDevice() {
		return device;
	}

	@Override
	public ILaunch getLaunch() {
		return getPrimaryDebugTarget().getLaunch();
	}

	@Override
	public AutomationSystem getSystem() {
		return device.getAutomationSystem();
	}

	public IDeviceManagementExecutorService getDeviceManagementExecutorService() {
		return deviceManagementExecutor;
	}

	public Duration getPollingInterval() {
		return pollingInterval;
	}

	public boolean isAllowTerminate() {
		return allowTerminate;
	}

	@Override
	public boolean canTerminate() {
		return !deviceManagementExecutor.isTerminated() && allowTerminate;
	}

	@Override
	public boolean isTerminated() {
		return terminate && deviceManagementExecutor.isTerminated();
	}

	@Override
	public boolean canDisconnect() {
		return !isDisconnected();
	}

	@Override
	public boolean isDisconnected() {
		return !terminate && deviceManagementExecutor.isTerminated();
	}

	public boolean isAlive() {
		return !deviceManagementExecutor.isShutdown() && deviceManagementExecutor.isConnected();
	}

	@Override
	public boolean supportsStorageRetrieval() {
		return false;
	}

	@Override
	public IMemoryBlock getMemoryBlock(final long startAddress, final long length) throws DebugException {
		throw createUnsupportedOperationException();
	}

	@Override
	public IProcess getProcess() {
		return null;
	}

	@Override
	public IThread[] getThreads() {
		return resources.values().toArray(IThread[]::new);
	}

	@Override
	public boolean hasThreads() {
		return isAlive() && !resources.isEmpty();
	}
}
