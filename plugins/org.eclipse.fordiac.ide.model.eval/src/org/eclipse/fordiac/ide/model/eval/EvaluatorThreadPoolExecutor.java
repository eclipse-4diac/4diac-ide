/*******************************************************************************
 * Copyright (c) 2023, 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.eval;

import java.io.Closeable;
import java.time.Clock;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * An executor for evaluator threads
 */
public class EvaluatorThreadPoolExecutor extends ThreadPoolExecutor {

	private final String name;
	private final AtomicReference<EvaluatorDebugger> debugger = new AtomicReference<>(
			DefaultEvaluatorDebugger.INSTANCE);
	private final Set<EvaluatorMonitor> monitorSet = ConcurrentHashMap.<EvaluatorMonitor>newKeySet();
	private final Map<String, Object> context = new ConcurrentHashMap<>();
	private final Map<String, Closeable> sharedResources = new ConcurrentHashMap<>();
	private Clock monotonicClock = AbstractEvaluator.MonotonicClock.UTC;
	private Clock realtimeClock = Clock.systemUTC();

	/**
	 * Create a new evaluator thread pool executor with unlimited number of threads
	 *
	 * @param name The name of the executor
	 */
	public EvaluatorThreadPoolExecutor(final String name) {
		this(name, Integer.MAX_VALUE);
	}

	/**
	 * Create a new evaluator thread pool executor with specified number of threads
	 *
	 * @param name            The name of the executor
	 * @param maximumPoolSize The maximum number of threads
	 */
	public EvaluatorThreadPoolExecutor(final String name, final int maximumPoolSize) {
		super(0, maximumPoolSize, 0, TimeUnit.NANOSECONDS, new SynchronousQueue<>());
		this.name = name;
		setThreadFactory(createThreadFactory());
	}

	/**
	 * Attach a debugger to the threads governed by this executor
	 *
	 * @param debugger The debugger to attach
	 * @throws IllegalStateException if a debugger is currently attached
	 */
	public synchronized void attachDebugger(final EvaluatorDebugger debugger) throws IllegalStateException {
		// check if default debugger is currently attached and set to new debugger
		// (attach) if true
		if (!this.debugger.compareAndSet(DefaultEvaluatorDebugger.INSTANCE, debugger)) {
			throw new IllegalStateException("A debugger is currently attached"); //$NON-NLS-1$
		}
	}

	/**
	 * Detach a debugger from the threads governed by this executor
	 *
	 * @param debugger The debugger to detach
	 * @throws IllegalStateException if another debugger is currently attached
	 */
	public synchronized void detachDebugger(final EvaluatorDebugger debugger) throws IllegalStateException {
		// check if debugger is currently attached and set to default (detach) if true
		if (!this.debugger.compareAndSet(debugger, DefaultEvaluatorDebugger.INSTANCE)) {
			throw new IllegalStateException("Another debugger is currently attached"); //$NON-NLS-1$
		}
	}

	/**
	 * Get the currently attached debugger
	 *
	 * @return The current debugger
	 */
	public EvaluatorDebugger getDebugger() {
		return debugger.get();
	}

	/**
	 * Add a monitor to the threads governed by this executor
	 *
	 * @param monitor The monitor
	 */
	public void addMonitor(final EvaluatorMonitor monitor) {
		monitorSet.add(monitor);
	}

	/**
	 * Remove a monitor from the threads governed by this executor
	 *
	 * @param monitor The monitor
	 */
	public void removeMonitor(final EvaluatorMonitor monitor) {
		monitorSet.remove(monitor);
	}

	/**
	 * Get the set of monitors currently present
	 *
	 * @return The set of monitors
	 */
	public Set<EvaluatorMonitor> getMonitorSet() {
		return monitorSet;
	}

	/**
	 * Get the shared evaluator context
	 *
	 * @return The shared context
	 */
	public Map<String, Object> getContext() {
		return context;
	}

	/**
	 * Get the shared resources
	 *
	 * @return The shared resources
	 * @apiNote Any shared resources present when the executor terminates are
	 *          automatically {@link Closeable#close() closed}.
	 */
	public Map<String, Closeable> getSharedResources() {
		return sharedResources;
	}

	/**
	 * Get the current monotonic clock for this executor
	 *
	 * @return The monotonic clock
	 */
	public Clock getMonotonicClock() {
		return monotonicClock;
	}

	/**
	 * Set the current monotonic clock for this executor
	 *
	 * @param monotonicClock The monotonic clock or null to reset to system default
	 */
	public void setMonotonicClock(final Clock monotonicClock) {
		this.monotonicClock = Objects.requireNonNullElse(monotonicClock, AbstractEvaluator.MonotonicClock.UTC);
	}

	/**
	 * Get the current realtime clock for this executor
	 *
	 * @return The realtime clock
	 */
	public Clock getRealtimeClock() {
		return realtimeClock;
	}

	/**
	 * Set the current realtime clock for this executor
	 *
	 * @param realtimeClock The realtime clock or null to reset to system default
	 */
	public void setRealtimeClock(final Clock realtimeClock) {
		this.realtimeClock = Objects.requireNonNullElseGet(realtimeClock, Clock::systemUTC);
	}

	@Override
	protected void beforeExecute(final Thread thread, final Runnable runnable) {
		debugger.get().beforeExecute(thread, runnable, this);
	}

	@Override
	protected void afterExecute(final Runnable runnable, final Throwable throwable) {
		debugger.get().afterExecute(Thread.currentThread(), runnable, throwable, this);
	}

	@Override
	protected void terminated() {
		sharedResources.forEach((key, value) -> {
			try {
				value.close();
			} catch (final Exception e) {
				monitorSet.forEach(monitor -> monitor.error("Exception closing shared resource " + key, e)); //$NON-NLS-1$
			}
		});
		debugger.get().terminated(this);
		monitorSet.forEach(monitor -> monitor.terminated(this));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws IllegalArgumentException if the new factory is not an
	 *                                  EvaluatorThreadFactory
	 */
	@Override
	public void setThreadFactory(final ThreadFactory threadFactory) throws IllegalArgumentException {
		if (!(threadFactory instanceof EvaluatorThreadFactory)) {
			throw new IllegalArgumentException("Must use an EvaluatorThreadFactory"); //$NON-NLS-1$
		}
		super.setThreadFactory(threadFactory);
	}

	protected EvaluatorThreadFactory createThreadFactory() {
		return new EvaluatorThreadFactory();
	}

	protected class EvaluatorThreadFactory implements ThreadFactory {
		// use of ThreadGroup only for informational purposes
		// when debugging Eclipse 4diac IDE
		@SuppressWarnings("java:S3014")
		private final ThreadGroup group = new ThreadGroup(name);
		private final AtomicInteger threadNumber = new AtomicInteger(1);

		@Override
		public Thread newThread(final Runnable runnable) {
			return new EvaluatorThread(group, runnable, name + "-" + threadNumber.getAndIncrement(), //$NON-NLS-1$
					EvaluatorThreadPoolExecutor.this);
		}
	}
}
