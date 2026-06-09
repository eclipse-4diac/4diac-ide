/*******************************************************************************
 * Copyright (c) 2025 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.replaydebugging;

import java.text.MessageFormat;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.DataPointChange;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.EventChange;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator;
import org.eclipse.fordiac.ide.debug.replaydebugging.replayer.IDeviceReplayer;
import org.eclipse.fordiac.ide.debug.replaydebugging.response.DeviceResponse;
import org.eclipse.fordiac.ide.debug.replaydebugging.watch.WatchFactoryReplay;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugDevice;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugTarget;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentLaunchConfigurationAttributes.DeploymentLaunchWatchpoint;
import org.eclipse.fordiac.ide.deployment.debug.Messages;
import org.eclipse.fordiac.ide.deployment.debug.breakpoint.DeploymentWatchpoint;
import org.eclipse.fordiac.ide.deployment.debug.watch.DeploymentDebugWatchData;
import org.eclipse.fordiac.ide.deployment.debug.watch.IVarDeclarationWatch;
import org.eclipse.fordiac.ide.deployment.debug.watch.IWatch;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

/**
 * @brief A debug device for replay debugging. It handles the watches of the
 *        devices and integrates the response from the resources
 */
public class ReplayDebuggingDevice extends DeploymentDebugDevice implements ReplayDebuggingResource.UpdateListener {

	private final String tracesPath;

	private final Map<String, ReplayDebuggingResource> replayDebuggingResources = new HashMap<>();

	// to know which datapoints to mark with a different color
	private final Map<String, String> allCurentChanges = new HashMap<>();

	private DeviceResponse response = new DeviceResponse(List.of());

	// this is needed mainly to be able to set the error on the current changes so
	// they are shown with a different color
	private final Map<String, IWatch> watches = new ConcurrentSkipListMap<>();

	private final boolean remote;

	public ReplayDebuggingDevice(final Device device, final DeploymentDebugTarget debugTarget, final String tracesPath,
			final boolean remote) {
		super(device, debugTarget, true, Duration.ZERO, List.of(), "Interpreter"); //$NON-NLS-1$
		this.tracesPath = tracesPath;
		this.remote = remote;
	}

	public DeviceResponse getDeviceResponse() {
		return response;
	}

	public void triggerEvent(final Resource resource, final String name) {
		final var replayResource = replayDebuggingResources.get(resource.getName());
		if (replayResource == null) {
			return;
		}
		replayResource.triggerEvent(name);
	}

	public void forceValue(final Resource resource, final String name, final String value) {
		final var replayResource = replayDebuggingResources.get(resource.getName());
		if (replayResource == null) {
			return;
		}
		replayResource.forceValue(name, value);
	}

	public void clearForce(final Resource resource, final String name) {
		final var replayResource = replayDebuggingResources.get(resource.getName());
		if (replayResource == null) {
			return;
		}
		replayResource.clearForce(name);
	}

	/**
	 * @brief Handles reading of traces and replaying to obtain all the desired
	 *        information.
	 *
	 *        Connects to the device, trigger the reading of traces in it and
	 *        creates a ReplayDebuggingResource for each resource in the device. The
	 *        response from the resources are collected in the response object.
	 */
	@Override
	public void connect() throws DebugException {

		final IDeviceReplayer replayer = createReplayer();
		final var resourceReplayers = replayer.start();

		final Device device = getDevice();
		for (final var entry : resourceReplayers.entrySet()) {
			final var resource = entry.getKey();
			final var resourceReplayer = entry.getValue();
			final ReplayDebuggingResource replayDebuggingResource = new ReplayDebuggingResource(
					new ReplayNavigator.Identifier(device.getAutomationSystem().getName(), device.getName(),
							resource.getName()),
					resourceReplayer, this);
			replayDebuggingResource.load();
			replayDebuggingResources.put(resource.getName(), replayDebuggingResource);

		}

		response = new DeviceResponse(
				replayDebuggingResources.values().stream().map(ReplayDebuggingResource::getResourceResponse).toList());
		replayer.stop();

		// we don't need all the boilerplate from connect in the parent class, just to
		// make sure the device management executor obtains this instance
		try {
			getDeviceManagementExecutorService().connect();
		} catch (final DeploymentException e) {
			throw new DebugException(Status
					.error(MessageFormat.format(Messages.DeploymentDebugDevice_ConnectError, device.getName()), e));
		}
	}

	private IDeviceReplayer createReplayer() {
		if (remote) {
			return new org.eclipse.fordiac.ide.debug.replaydebugging.replayer.forte.DeviceReplayer(
					getDeviceManagementExecutorService(), getDevice(), tracesPath);
		}
		return new org.eclipse.fordiac.ide.debug.replaydebugging.replayer.interpreter.DeviceReplayer(getDevice(),
				tracesPath);
	}

	// set the watches of the current changes to error so they are marked with a
	// different color
	private void setError(final IWatch watch) {
		if (watch instanceof final WatchFactoryReplay.IWatchWithPublicError watchWithError) {
			if (watch instanceof final WatchFactoryReplay.FBNetworkElementWatchReplay fbWatch) {
				// recursively set the error on all subwatches
				for (final IWatch subWatch : fbWatch.getSubWatches()) {
					setError(subWatch);
				}
			} else if (allCurentChanges.containsKey(watch.getQualifiedName())) {
				watchWithError.setError(allCurentChanges.get(watch.getQualifiedName()));
			} else {
				watchWithError.clearError();
			}
		}
	}

	@Override
	public void disconnect() throws DebugException {
		super.disconnect();
		unloadAllReplayDebuggingResources();
	}

	@Override
	public void terminate() throws DebugException {
		super.terminate();
		unloadAllReplayDebuggingResources();
	}

	private void unloadAllReplayDebuggingResources() {
		for (final ReplayDebuggingResource replayDebuggingResource : replayDebuggingResources.values()) {
			replayDebuggingResource.unload();
		}
		replayDebuggingResources.clear();
	}

	// when any replay debugging resource is updated, we update the current changes
	// and the watches
	@Override
	public void onUpdate(final ReplayDebuggingResource notUsed) {
		allCurentChanges.clear();
		for (final ReplayDebuggingResource replayDebuggingResource : replayDebuggingResources.values()) {
			final EventChange eventChange = replayDebuggingResource.getCurrentEventChange();
			if (eventChange == null) {
				continue;
			}
			for (final DataPointChange dataPointChange : eventChange.newValues()) {
				final String datapoint = dataPointChange.datapoint();
				final String newValue = dataPointChange.newValue();
				allCurentChanges.put(datapoint, newValue);
			}
		}
		updateWatches();
	}

	// From here on, all the methods are almost a copy from the parent,
	// but errors are set, and the creates watches are of type IWatchReplay
	// and we need to act on the local watches and not from the parent class.
	protected void updateWatches() {
		incrementVariableUpdateCount();
		final DeploymentDebugWatchData watchData = new DeploymentDebugWatchData(response.getResponse());
		watches.values().forEach(watch -> {
			watch.updateValue(watchData);
			setError(watch);
		});
		getPrimaryDebugTarget().updateWatches(false);
	}

	@Override
	protected void terminated() {
		watches.values().forEach(IWatch::disconnected);
		getPrimaryDebugTarget().updateWatches(false);
		fireTerminateEvent();
	}

	@Override
	public Map<String, IWatch> getWatches() {
		return Collections.unmodifiableMap(watches);
	}

	@Override
	protected void removeWatch(final DeploymentWatchpoint watchpoint) {
		final IWatch watch = watches.remove(watchpoint.getLocation());
		if (watch != null) {
			try {
				getPrimaryDebugTarget().updateWatches(true);
				watch.removeWatch();
			} catch (final DebugException e) {
				FordiacLogHelper.logWarning("Cannot remove watch for watchpoint: " + watchpoint, e); //$NON-NLS-1$
			}
		}
	}

	@Override
	protected void updatePinned(final DeploymentWatchpoint watchpoint) {
		final IWatch watch = watches.get(watchpoint.getLocation());
		if (watch != null) {
			watch.setPinned(watchpoint.isPinned());
			getPrimaryDebugTarget().updateWatches(true);
		}
	}

	@Override
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
	protected void addWatch(final DeploymentLaunchWatchpoint watchpoint) {
		final Optional<INamedElement> element = watchpoint.getTarget(getDevice());
		if (element.isPresent()) {
			try {
				final IWatch watch = watches.computeIfAbsent(element.get().getQualifiedName(),
						name -> WatchFactoryReplay.watchFor(name, element.get(), this));
				watch.setSource(IWatch.Source.LAUNCH);
				getPrimaryDebugTarget().updateWatches(true);
				watch.addWatch();
				if (watchpoint.isForceEnabled() && watch instanceof final IVarDeclarationWatch variableWatch) {
					variableWatch.forceValue(watchpoint.forceValue());
				}
				updateWatches();
			} catch (final CoreException e) {
				FordiacLogHelper.logWarning("Cannot create watch for watchpoint: " + watchpoint, e); //$NON-NLS-1$
			}
		}

	}

	@Override
	protected void addWatch(final DeploymentWatchpoint watchpoint) {
		final Optional<INamedElement> element = watchpoint.getTarget(getDevice());
		if (element.isPresent()) {
			try {
				final IWatch watch = watches.computeIfAbsent(element.get().getQualifiedName(),
						name -> WatchFactoryReplay.watchFor(name, element.get(), this));
				watch.setSource(IWatch.Source.BREAKPOINT);
				watch.setPinned(watchpoint.isPinned());
				getPrimaryDebugTarget().updateWatches(true);
				watch.addWatch();
				watchpoint.setInstalled(true);
				if (watchpoint.isForceEnabled() && watch instanceof final IVarDeclarationWatch variableWatch) {
					variableWatch.forceValue(watchpoint.getForceValue());
				}
				updateWatches();
			} catch (final CoreException e) {
				FordiacLogHelper.logWarning("Cannot create watch for watchpoint: " + watchpoint, e); //$NON-NLS-1$
			}
		}
	}
}
