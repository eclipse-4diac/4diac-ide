/*******************************************************************************
 * Copyright (c) 2022, 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.debug;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.core.runtime.IStatus;
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
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorCache;
import org.eclipse.fordiac.ide.model.eval.EvaluatorMonitor;
import org.eclipse.fordiac.ide.model.eval.EvaluatorThreadPoolExecutor;
import org.eclipse.fordiac.ide.model.eval.value.Value;

public class EvaluatorProcess extends PlatformObject implements IProcess, Callable<IStatus> {

	private final String name;
	private final Evaluator evaluator;
	private final ILaunch launch;
	private final FutureTask<IStatus> task;
	private final EvaluatorThreadPoolExecutor executor;
	private final EvaluatorStreamsProxy streamsProxy;
	private final Map<String, String> attributes = new HashMap<>();

	public EvaluatorProcess(final String name, final Evaluator evaluator, final ILaunch launch) {
		this.name = evaluator.getClass().getSimpleName();
		this.evaluator = evaluator;
		this.launch = launch;
		task = new FutureTask<>(this);
		executor = new EvaluatorThreadPoolExecutor(name);
		executor.addMonitor(new EvaluatorTerminationMonitor());
		streamsProxy = new EvaluatorStreamsProxy(executor);
		launch.addProcess(this);
		fireCreationEvent();
	}

	@Override
	public IStatus call() throws Exception {
		try (EvaluatorCache cache = EvaluatorCache.open()) {
			evaluator.prepare();
			final long start = System.nanoTime();
			final Value result = evaluator.evaluate();
			if (result != null) {
				streamsProxy.getOutputStreamMonitor().info(String.format("Result: %s", result)); //$NON-NLS-1$
			}
			final long finish = System.nanoTime();
			final Duration elapsed = Duration.ofNanos(finish - start);
			streamsProxy.getOutputStreamMonitor().info(String.format("Elapsed: %d:%02d:%02d:%03d", //$NON-NLS-1$
					Long.valueOf(elapsed.toHours()), Integer.valueOf(elapsed.toMinutesPart()),
					Integer.valueOf(elapsed.toSecondsPart()), Integer.valueOf(elapsed.toMillisPart())));
			return Status.OK_STATUS;
		} catch (final InterruptedException e) {
			streamsProxy.getErrorStreamMonitor().error("Terminated"); //$NON-NLS-1$
			Thread.currentThread().interrupt();
			return Status.error("Terminated"); //$NON-NLS-1$
		} catch (final Exception t) {
			streamsProxy.getErrorStreamMonitor().error("Exception occurred", t); //$NON-NLS-1$
			return Status.error("Exception occurred", t); //$NON-NLS-1$
		} finally {
			executor.shutdown();
		}
	}

	public void start() {
		executor.execute(task);
	}

	public Evaluator getEvaluator() {
		return evaluator;
	}

	public EvaluatorThreadPoolExecutor getExecutor() {
		return executor;
	}

	@Override
	public boolean canTerminate() {
		return !isTerminated();
	}

	@Override
	public boolean isTerminated() {
		return executor.isTerminated();
	}

	@Override
	public void terminate() throws DebugException {
		executor.shutdownNow();
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

	private class EvaluatorTerminationMonitor extends EvaluatorMonitor.NullEvaluatorMonitor {

		@Override
		public void terminated(final EvaluatorThreadPoolExecutor executor) {
			fireTerminateEvent();
		}
	}
}
