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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugVariable;
import org.eclipse.fordiac.ide.deployment.debug.breakpoint.DeploymentWatchpoint;
import org.eclipse.fordiac.ide.deployment.debug.watch.DeploymentDebugWatchData;
import org.eclipse.fordiac.ide.deployment.debug.watch.IVarDeclarationWatch;
import org.eclipse.fordiac.ide.deployment.debug.watch.IWatch;
import org.eclipse.fordiac.ide.deployment.devResponse.Resource;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.DeviceManagementInteractorFactory;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementExecutorService;
import org.eclipse.fordiac.ide.deployment.interactors.SharedWatchDeviceManagementInteractor;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class DeploymentDebugDevice extends DeploymentDebugElement implements IDeploymentDebugTarget {

	private final Device device;
	private final boolean allowTerminate;
	private final Duration pollingInterval;
	private final IDeviceManagementExecutorService deviceManagementExecutor;
	private final Map<String, DeploymentDebugResource> resources = new ConcurrentSkipListMap<>();
	private final Map<String, IWatch> watches = new ConcurrentSkipListMap<>();
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

		DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(this);
		debugTarget.getLaunch().addDebugTarget(this);
		fireCreationEvent();
	}

	protected void terminated() {
		DebugPlugin.getDefault().getBreakpointManager().removeBreakpointListener(this);
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

	protected void updateWatches(final Response response) {
		incrementVariableUpdateCount();
		final DeploymentDebugWatchData watchData = new DeploymentDebugWatchData(response);
		watches.values().forEach(watch -> watch.updateValue(watchData));
		getPrimaryDebugTarget().updateWatches(false);
	}

	public void connect() throws DebugException {
		try {
			deviceManagementExecutor.connect();
			deviceManagementExecutor.queryResourcesPeriodically(this::updateResources,
					pollingInterval.get(ChronoUnit.NANOS), TimeUnit.NANOSECONDS);
			deviceManagementExecutor.readWatchesPeriodically(this::updateWatches, pollingInterval.get(ChronoUnit.NANOS),
					TimeUnit.NANOSECONDS);
			Stream.of(DebugPlugin.getDefault().getBreakpointManager().getBreakpoints())
					.forEachOrdered(this::breakpointAdded);
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
	public Map<String, IWatch> getWatches() {
		return Collections.unmodifiableMap(watches);
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
		return breakpoint instanceof final DeploymentWatchpoint watchpoint && watchpoint.isRelevant(getSystem());
	}

	@Override
	public void breakpointAdded(final IBreakpoint breakpoint) {
		if (breakpoint instanceof final DeploymentWatchpoint watchpoint && watchpoint.isEnabled()
				&& watchpoint.isRelevant(getSystem())) {
			addWatch(watchpoint);
		}
	}

	@Override
	public void breakpointRemoved(final IBreakpoint breakpoint, final IMarkerDelta delta) {
		if (breakpoint instanceof final DeploymentWatchpoint watchpoint && watchpoint.isRelevant(getSystem())) {
			removeWatch(watchpoint);
		}
	}

	@Override
	public void breakpointChanged(final IBreakpoint breakpoint, final IMarkerDelta delta) {
		if (breakpoint instanceof final DeploymentWatchpoint watchpoint && watchpoint.isRelevant(getSystem())) {
			if (watchpoint.isEnabledChanged(delta)) {
				if (watchpoint.isEnabled()) {
					addWatch(watchpoint);
				} else {
					removeWatch(watchpoint);
				}
			} else if (watchpoint.isForceChanged(delta)) {
				updateForce(watchpoint);
			}
		}
	}

	protected void addWatch(final DeploymentWatchpoint watchpoint) {
		final Optional<INamedElement> element = watchpoint.getTarget(getSystem());
		if (element.isPresent()) {
			try {
				final IWatch watch = watches.computeIfAbsent(element.get().getQualifiedName(),
						name -> IWatch.watchFor(name, element.get(), this));
				getPrimaryDebugTarget().updateWatches(true);
				watch.addWatch();
				if (watchpoint.isForceEnabled() && watch instanceof final IVarDeclarationWatch variableWatch) {
					variableWatch.forceValue(watchpoint.getForceValue());
				}
				watchpoint.setInstalled(true);
			} catch (CoreException | EvaluatorException e) {
				FordiacLogHelper.logWarning("Cannot create watch for watchpoint: " + watchpoint, e); //$NON-NLS-1$
			}
		}
	}

	protected void removeWatch(final DeploymentWatchpoint watchpoint) {
		final IWatch watch = watches.remove(watchpoint.getLocation());
		if (watch != null) {
			try {
				getPrimaryDebugTarget().updateWatches(true);
				if (watch instanceof final IVarDeclarationWatch variableWatch && variableWatch.isForced()) {
					variableWatch.clearForce();
				}
				watch.removeWatch();
			} catch (final DebugException e) {
				FordiacLogHelper.logWarning("Cannot remove watch for watchpoint: " + watchpoint, e); //$NON-NLS-1$
			}
		}
	}

	protected void updateForce(final DeploymentWatchpoint watchpoint) {
		final IWatch watch = watches.get(watchpoint.getLocation());
		if (watch instanceof final IVarDeclarationWatch variableWatch) {
			try {
				getPrimaryDebugTarget().updateWatches(false);
				if (watchpoint.isForceEnabled()) {
					variableWatch.forceValue(watchpoint.getForceValue());
				} else {
					variableWatch.clearForce();
				}
			} catch (final DebugException e) {
				FordiacLogHelper.logWarning("Cannot update watch for watchpoint: " + watchpoint, e); //$NON-NLS-1$
			}
		}
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
