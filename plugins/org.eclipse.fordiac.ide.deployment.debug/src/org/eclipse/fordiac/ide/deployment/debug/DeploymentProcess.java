/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IStreamsProxy;
import org.eclipse.fordiac.ide.debug.AbstractLaunchProcess;
import org.eclipse.fordiac.ide.deployment.DeploymentCoordinator;
import org.eclipse.fordiac.ide.deployment.DownloadRunnable;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public class DeploymentProcess extends AbstractLaunchProcess {

	private final DeploymentStreamsProxy streamsProxy = new DeploymentStreamsProxy();
	private final DownloadRunnable downloadRunnable;
	private final Job job;
	private boolean terminated;

	public DeploymentProcess(final AutomationSystem system, final Set<INamedElement> selection, final ILaunch launch)
			throws DeploymentException {
		super(MessageFormat.format(Messages.DeploymentProcess_Name, system.getName()), launch);

		downloadRunnable = new DownloadRunnable(DeploymentCoordinator.createDeploymentdata(selection.toArray()), null,
				streamsProxy, null);

		job = Job.create(name, this::deploy);
		job.addJobChangeListener(IJobChangeListener.onDone(this::terminated));
		job.setUser(true);

		launch.addProcess(this);
		fireCreationEvent();
	}

	protected IStatus deploy(final IProgressMonitor monitor) {
		try {
			downloadRunnable.run(monitor);
			return downloadRunnable.getResult();
		} catch (final InterruptedException e) {
			streamsProxy.getErrorStreamMonitor().message(Messages.DeploymentProcess_Terminated);
			Thread.currentThread().interrupt();
			return Status.error(Messages.DeploymentProcess_Terminated);
		} catch (final Exception t) {
			streamsProxy.getErrorStreamMonitor()
					.message(MessageFormat.format(Messages.DeploymentProcess_ExeceptionOccured, t.getMessage()));
			return Status.error(MessageFormat.format(Messages.DeploymentProcess_ExeceptionOccured, t.getMessage()), t);
		}
	}

	protected void terminated(final IJobChangeEvent event) {
		terminated = true;
		fireTerminateEvent();
	}

	public void start() {
		job.schedule();
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
	public int getExitValue() throws DebugException {
		if (!isTerminated()) {
			throw new DebugException(Status.error(Messages.DeploymentProcess_StillRunning));
		}
		return job.getResult().getCode();
	}

	@Override
	public IStreamsProxy getStreamsProxy() {
		return streamsProxy;
	}

	public Job getJob() {
		return job;
	}
}
