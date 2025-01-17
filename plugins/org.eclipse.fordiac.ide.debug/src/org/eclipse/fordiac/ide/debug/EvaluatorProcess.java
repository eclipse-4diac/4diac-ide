/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Alois Zoitl  - extracted AbstractLaunchProcess
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IStreamsProxy;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorCache;
import org.eclipse.fordiac.ide.model.eval.EvaluatorMonitor;
import org.eclipse.fordiac.ide.model.eval.EvaluatorThreadPoolExecutor;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class EvaluatorProcess extends AbstractLaunchProcess implements Callable<IStatus> {

	private final Evaluator evaluator;
	private final FutureTask<IStatus> task;
	private final EvaluatorThreadPoolExecutor executor;
	private final EvaluatorStreamsProxy streamsProxy;

	public EvaluatorProcess(final String name, final Evaluator evaluator, final ILaunch launch) throws CoreException {
		super(evaluator.getClass().getSimpleName(), launch);
		this.evaluator = evaluator;
		task = new FutureTask<>(this);
		executor = new EvaluatorThreadPoolExecutor(name);
		executor.getContext().putAll(launch.getLaunchConfiguration().getAttributes());
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
			FordiacLogHelper.logWarning("Exception occurred while evaluating " + name, t); //$NON-NLS-1$
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
	public IStreamsProxy getStreamsProxy() {
		return streamsProxy;
	}

	private class EvaluatorTerminationMonitor extends EvaluatorMonitor.NullEvaluatorMonitor {

		@Override
		public void terminated(final EvaluatorThreadPoolExecutor executor) {
			fireTerminateEvent();
		}
	}
}
