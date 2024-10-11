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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamsProxy;
import org.eclipse.fordiac.ide.deployment.DeploymentCoordinator;
import org.eclipse.fordiac.ide.deployment.DownloadRunnable;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public class DeploymentProcess extends PlatformObject implements IProcess {

	private final String name;
	private final ILaunch launch;
	private final DeploymentStreamsProxy streamsProxy = new DeploymentStreamsProxy();
	private final Map<String, String> attributes = new HashMap<>();
	private final DownloadRunnable downloadRunnable;
	private final Job job;

	public DeploymentProcess(final String name, final Set<INamedElement> selection, final ILaunch launch)
			throws DeploymentException {
		this.name = name;
		this.launch = launch;

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
		return job.getState() == Job.NONE;
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
	public String getLabel() {
		return name;
	}

	@Override
	public ILaunch getLaunch() {
		return launch;
	}

	@Override
	public IStreamsProxy getStreamsProxy() {
		return streamsProxy;
	}

	protected void fireCreationEvent() {
		fireEvent(new DebugEvent(this, DebugEvent.CREATE));
	}

	protected void fireTerminateEvent() {
		fireEvent(new DebugEvent(this, DebugEvent.TERMINATE));
	}

	protected void fireChangeEvent() {
		fireEvent(new DebugEvent(this, DebugEvent.CHANGE));
	}

	@SuppressWarnings("static-method")
	protected void fireEvent(final DebugEvent event) {
		final DebugPlugin manager = DebugPlugin.getDefault();
		if (manager != null) {
			manager.fireDebugEventSet(new DebugEvent[] { event });
		}
	}

	@Override
	public void setAttribute(final String key, final String value) {
		attributes.put(key, value);
	}

	@Override
	public String getAttribute(final String key) {
		return attributes.get(key);
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (adapter.equals(IProcess.class)) {
			return adapter.cast(this);
		}
		if (adapter.equals(IDebugTarget.class)) {
			for (final IDebugTarget target : getLaunch().getDebugTargets()) {
				if (equals(target.getProcess())) {
					return adapter.cast(target);
				}
			}
			return null;
		}
		if (adapter.equals(ILaunch.class)) {
			return adapter.cast(getLaunch());
		}
		if (adapter.equals(ILaunchConfiguration.class)) {
			return adapter.cast(getLaunch().getLaunchConfiguration());
		}
		return super.getAdapter(adapter);
	}
}
