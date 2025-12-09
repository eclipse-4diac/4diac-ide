/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fortelauncher;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IStreamsProxy;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.fordiac.ide.debug.AbstractLaunchProcess;
import org.eclipse.fordiac.ide.runtime.RuntimeLaunchTab;

public class StopForteLaunchDelegate extends LaunchConfigurationDelegate {

	@Override
	public void launch(final ILaunchConfiguration configuration, final String mode, final ILaunch launch,
			final IProgressMonitor monitor) throws CoreException {

		final String forteToStop = configuration.getAttribute(RuntimeLaunchTab.ATTR_LOCATION, ""); //$NON-NLS-1$
		if (forteToStop.isBlank()) {
			throw new DebugException(Status.error(Messages.StopForte_NoForteExecutableGiven));
		}

		final ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();

		final List<ILaunch> launchesToTerminate = new ArrayList<>();
		for (final ILaunch runningLaunch : manager.getLaunches()) {
			final String location = runningLaunch.getLaunchConfiguration().getAttribute(RuntimeLaunchTab.ATTR_LOCATION,
					""); //$NON-NLS-1$
			if (forteToStop.equals(location) && !runningLaunch.isTerminated()) {
				launchesToTerminate.add(runningLaunch);
			}
		}

		final StopForteProcess process = new StopForteProcess(configuration.getName(), launchesToTerminate, launch);
		process.start();
	}

	private static final class StopForteProcess extends AbstractLaunchProcess {

		private final Job job;
		private final List<ILaunch> launchesToTerminate;
		private boolean terminated;

		public StopForteProcess(final String name, final List<ILaunch> launchesToTerminate, final ILaunch launch) {
			super(name, launch);
			this.launchesToTerminate = launchesToTerminate;
			this.job = Job.create(name, this::performStopFORTEs);
			job.addJobChangeListener(IJobChangeListener.onDone(this::terminated));
			job.setUser(true);
			launch.addProcess(this);
			fireCreationEvent();
		}

		public void start() {
			job.schedule();
		}

		private IStatus performStopFORTEs(final IProgressMonitor monitor) {
			final SubMonitor subMonitor = SubMonitor.convert(monitor, launchesToTerminate.size());

			for (final ILaunch runningLaunch : launchesToTerminate) {
				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}

				if (!runningLaunch.isTerminated()) {
					try {
						runningLaunch.terminate();
					} catch (final DebugException e) {
						return Status.error(
								MessageFormat.format(Messages.StopForteProcess_ExeceptionOccured, e.getMessage()), e);
					}
				}
				subMonitor.worked(1);
			}
			return Status.OK_STATUS;
		}

		private void terminated(final IJobChangeEvent event) {
			terminated = true;
			fireTerminateEvent();
		}

		@Override
		public boolean canTerminate() {
			return !isTerminated();
		}

		@Override
		public boolean isTerminated() {
			return terminated;
		}

		@Override
		public void terminate() throws DebugException {
			job.cancel();
		}

		@Override
		public IStreamsProxy getStreamsProxy() {
			return null;
		}

		@Override
		public int getExitValue() throws DebugException {
			return 0;
		}

	}

}
