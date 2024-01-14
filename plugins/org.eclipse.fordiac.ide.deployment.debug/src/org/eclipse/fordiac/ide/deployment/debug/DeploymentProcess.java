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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.core.runtime.Status;
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

public class DeploymentProcess extends PlatformObject implements IProcess, Callable<IStatus> {

	private final String name;
	private final DownloadRunnable runnable;
	private final ILaunch launch;
	private final FutureTask<IStatus> task;
	private final DeploymentStreamsProxy streamsProxy = new DeploymentStreamsProxy();
	private final Map<String, String> attributes = new HashMap<>();

	public DeploymentProcess(final String name, final Set<INamedElement> selection, final ILaunch launch)
			throws DeploymentException {
		this.name = name;
		this.launch = launch;
		runnable = new DownloadRunnable(DeploymentCoordinator.createDeploymentdata(selection.toArray()), null,
				streamsProxy, null);
		task = new FutureTask<>(this);
		launch.addProcess(this);
		fireCreationEvent();
	}

	@Override
	public IStatus call() throws Exception {
		try {
			runnable.run(new NullProgressMonitor());
			return Status.OK_STATUS;
		} catch (final InterruptedException e) {
			streamsProxy.getErrorStreamMonitor().message("Terminated\n"); //$NON-NLS-1$
			Thread.currentThread().interrupt();
			return Status.error("Terminated"); //$NON-NLS-1$
		} catch (final Exception t) {
			streamsProxy.getErrorStreamMonitor().message("Exception occurred: " + t.getMessage() + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
			return Status.error("Exception occurred", t); //$NON-NLS-1$
		} finally {
			fireTerminateEvent();
		}
	}

	public void start() {
		ForkJoinPool.commonPool().execute(task);
	}

	@Override
	public boolean canTerminate() {
		return !isTerminated();
	}

	@Override
	public boolean isTerminated() {
		return task.isDone();
	}

	@Override
	public void terminate() throws DebugException {
		task.cancel(true);
	}

	@Override
	public int getExitValue() throws DebugException {
		try {
			return task.get(-1, TimeUnit.NANOSECONDS).getCode();
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new DebugException(Status.error("Couldn't get exit code", e)); //$NON-NLS-1$
		} catch (ExecutionException | TimeoutException e) {
			throw new DebugException(Status.error("Couldn't get exit code", e)); //$NON-NLS-1$
		} catch (final CancellationException e) {
			return -1;
		}
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

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (adapter.equals(IProcess.class)) {
			return (T) this;
		}
		if (adapter.equals(IDebugTarget.class)) {
			for (final IDebugTarget target : getLaunch().getDebugTargets()) {
				if (equals(target.getProcess())) {
					return (T) target;
				}
			}
			return null;
		}
		if (adapter.equals(ILaunch.class)) {
			return (T) getLaunch();
		}
		if (adapter.equals(ILaunchConfiguration.class)) {
			return (T) getLaunch().getLaunchConfiguration();
		}
		return super.getAdapter(adapter);
	}
}
