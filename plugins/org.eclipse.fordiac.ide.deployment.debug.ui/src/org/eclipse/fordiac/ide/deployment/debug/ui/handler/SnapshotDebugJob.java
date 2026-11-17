/*******************************************************************************
 * Copyright (c) 2026 Osoft e.U.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.ui.handler;

import java.text.MessageFormat;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugDevice;
import org.eclipse.fordiac.ide.deployment.debug.snapshot.FBSnapshotReader;
import org.eclipse.fordiac.ide.deployment.debug.snapshot.FBSnapshotTarget;
import org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotFBLaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.deployment.debug.snapshot.SnapshotFBLaunchConfigurations;
import org.eclipse.fordiac.ide.deployment.debug.ui.Messages;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.model.libraryElement.Device;

/**
 * Reads a snapshot of a function block instance through the running monitoring
 * session, stores it in the snapshot launch configuration and starts a debug
 * session with it.
 */
public final class SnapshotDebugJob extends Job {

	private final FBSnapshotTarget target;
	private final IDeviceManagementInteractor interactor;
	private final ILaunch previousLaunch;

	private SnapshotDebugJob(final String name, final FBSnapshotTarget target,
			final IDeviceManagementInteractor interactor, final ILaunch previousLaunch) {
		super(name);
		this.target = target;
		this.interactor = interactor;
		this.previousLaunch = previousLaunch;
		setUser(true);
	}

	/**
	 * Create a job that debugs the given instance with a new snapshot.
	 *
	 * @param target     the function block instance
	 * @param interactor the connection of the running monitoring session
	 * @return the job, not yet scheduled
	 */
	public static SnapshotDebugJob forTarget(final FBSnapshotTarget target,
			final IDeviceManagementInteractor interactor) {
		return new SnapshotDebugJob(MessageFormat.format(Messages.SnapshotDebugJob_Name, target.instance().getName()),
				target, interactor, null);
	}

	/**
	 * Create a job that starts the given snapshot launch again with a new snapshot
	 * and then terminates it. If the new snapshot cannot be read, it keeps running.
	 *
	 * @param launch the snapshot launch
	 * @return the job, not yet scheduled
	 */
	public static SnapshotDebugJob forRelaunch(final ILaunch launch) {
		return new SnapshotDebugJob(
				MessageFormat.format(Messages.SnapshotDebugJob_Name, launch.getLaunchConfiguration().getName()), null,
				null, launch);
	}

	@Override
	protected IStatus run(final IProgressMonitor monitor) {
		final SubMonitor progress = SubMonitor.convert(monitor, 2);
		try {
			final ILaunchConfiguration configuration = storeSnapshot(progress.split(1));
			final String mode = previousLaunch != null ? previousLaunch.getLaunchMode() : ILaunchManager.DEBUG_MODE;
			final ILaunch launch = DebugUITools.buildAndLaunch(configuration, mode, progress.split(1));
			// keep the previous session until the new one runs with the new snapshot; a
			// launch that was declined or canceled is returned without children
			if (previousLaunch != null && launch.hasChildren() && previousLaunch.canTerminate()) {
				previousLaunch.terminate();
			}
			return Status.OK_STATUS;
		} catch (final CoreException e) {
			return e.getStatus();
		} catch (final DeploymentException e) {
			return Status.error(e.getMessage(), e);
		} catch (final OperationCanceledException e) {
			return Status.CANCEL_STATUS;
		}
	}

	/** Read a new snapshot and store it in the launch configuration. */
	private ILaunchConfiguration storeSnapshot(final IProgressMonitor monitor)
			throws CoreException, DeploymentException {
		if (previousLaunch == null) {
			return SnapshotFBLaunchConfigurations.createOrUpdate(target,
					FBSnapshotReader.read(target, interactor, monitor));
		}
		final ILaunchConfiguration configuration = previousLaunch.getLaunchConfiguration();
		final FBSnapshotTarget relaunchTarget = SnapshotFBLaunchConfigurationAttributes.getTarget(configuration);
		final Device device = relaunchTarget.device();
		final IDeviceManagementInteractor monitoring = AbstractDeploymentHandler.findActiveDevice(device)
				.map(DeploymentDebugDevice::getDeviceManagementExecutorService)
				.orElseThrow(() -> new DeploymentException(MessageFormat.format(Messages.SnapshotDebugJob_NotMonitored,
						device.getAutomationSystem().getName())));
		return SnapshotFBLaunchConfigurations.update(configuration, relaunchTarget,
				FBSnapshotReader.read(relaunchTarget, monitoring, monitor));
	}
}
