/**
 * Copyright (c) 2022, 2025 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.model.eval;

import java.io.Closeable;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.fordiac.ide.model.eval.fb.FBEvaluatorCountingEventQueue;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;

public abstract class AbstractEvaluator implements Evaluator {
	private final Variable<?> context;
	private final Evaluator parent;

	protected AbstractEvaluator(final Variable<?> context, final Evaluator parent) {
		this.context = context;
		this.parent = parent;
	}

	@Override
	public Variable<?> getContext() {
		return context;
	}

	@Override
	public Evaluator getParent() {
		return parent;
	}

	protected <T extends Object> T trap(final T context) throws InterruptedException {
		currentDebugger().trap(context, this);
		return context;
	}

	@SuppressWarnings("static-method")
	protected void info(final String message) {
		currentMonitors().forEach(monitor -> monitor.info(message));
	}

	@SuppressWarnings("static-method")
	protected void warn(final String message) {
		currentMonitors().forEach(monitor -> monitor.warn(message));
	}

	@SuppressWarnings("static-method")
	protected void error(final String message) {
		currentMonitors().forEach(monitor -> monitor.error(message));
	}

	@SuppressWarnings("static-method")
	protected void error(final String message, final Throwable t) {
		currentMonitors().forEach(monitor -> monitor.error(message, t));
	}

	protected void update(final Collection<? extends Variable<?>> variables) {
		currentMonitors().forEach(monitor -> monitor.update(variables, this));
	}

	public static EvaluatorDebugger currentDebugger() {
		if (Thread.currentThread() instanceof final EvaluatorThread evaluatorThread) {
			return evaluatorThread.getExecutor().getDebugger();
		}
		return DefaultEvaluatorDebugger.INSTANCE;
	}

	public static Set<EvaluatorMonitor> currentMonitors() {
		if (Thread.currentThread() instanceof final EvaluatorThread evaluatorThread) {
			return evaluatorThread.getExecutor().getMonitorSet();
		}
		return Collections.emptySet();
	}

	public static Map<String, Object> currentContext() {
		if (Thread.currentThread() instanceof final EvaluatorThread evaluatorThread) {
			return evaluatorThread.getExecutor().getContext();
		}
		return Collections.emptyMap();
	}

	public static Map<String, Closeable> getSharedResources() {
		if (!(Thread.currentThread() instanceof final EvaluatorThread evaluatorThread)) {
			throw new IllegalStateException("Cannot get shared resources without evaluator thread"); //$NON-NLS-1$
		}
		return evaluatorThread.getExecutor().getSharedResources();
	}

	public static Clock currentMonotonicClock() {
		if (Thread.currentThread() instanceof final EvaluatorThread evaluatorThread) {
			return evaluatorThread.getExecutor().getMonotonicClock();
		}
		return AbstractEvaluator.MonotonicClock.UTC;
	}

	public static void setMonotonicClock(final Clock clock) {
		if (!(Thread.currentThread() instanceof final EvaluatorThread evaluatorThread)) {
			throw new IllegalStateException("Cannot set clock without evaluator thread"); //$NON-NLS-1$
		}
		evaluatorThread.getExecutor().setMonotonicClock(clock);
	}

	public static Clock currentRealtimeClock() {
		if (Thread.currentThread() instanceof final EvaluatorThread evaluatorThread) {
			return evaluatorThread.getExecutor().getRealtimeClock();
		}
		return Clock.systemUTC();
	}

	public static void setRealtimeClock(final Clock clock) {
		if (!(Thread.currentThread() instanceof final EvaluatorThread evaluatorThread)) {
			throw new IllegalStateException("Cannot set clock without evaluator thread"); //$NON-NLS-1$
		}
		evaluatorThread.getExecutor().setRealtimeClock(clock);
	}

	public static class MonotonicClock extends Clock {
		public static final MonotonicClock UTC = new MonotonicClock(ZoneOffset.UTC);

		private final ZoneId zone;

		public MonotonicClock(final ZoneId zone) {
			this.zone = zone;
		}

		@Override
		public Instant instant() {
			final long nanoTime = System.nanoTime();
			return Instant.ofEpochSecond(nanoTime / 1000_000_000L, nanoTime % 1000_000_000L);
		}

		@Override
		public Clock withZone(final ZoneId zone) {
			return new MonotonicClock(zone);
		}

		@Override
		public ZoneId getZone() {
			return zone;
		}

		@Override
		public int hashCode() {
			return zone.hashCode();
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (!super.equals(obj)) {
				return false;
			}
			if (!(obj instanceof final MonotonicClock other)) {
				return false;
			}
			return Objects.equals(zone, other.zone);
		}

		@Override
		public String toString() {
			return String.format("%s [zone=%s]", getClass().getName(), zone); //$NON-NLS-1$
		}
	}

	public static class IntervalClock extends Clock {
		private final Instant offset;
		private final Duration interval;
		private final ZoneId zone;
		private final FBEvaluatorCountingEventQueue queue;
		private final long queueOffset;

		public IntervalClock(final Instant offset, final Duration interval, final ZoneId zone,
				final FBEvaluatorCountingEventQueue queue, final long queueOffset) {
			this.offset = Objects.requireNonNull(offset);
			this.interval = Objects.requireNonNull(interval);
			this.zone = Objects.requireNonNull(zone);
			this.queue = Objects.requireNonNull(queue);
			this.queueOffset = queueOffset;
		}

		@Override
		public Instant instant() {
			return instant(queue.getTotalInputCount().get());
		}

		private Instant instant(final long totalCount) {
			return offset.plus(interval.multipliedBy(totalCount - queueOffset));
		}

		@Override
		public Clock withZone(final ZoneId zone) {
			final long totalCount = queue.getTotalInputCount().get();
			return new IntervalClock(instant(totalCount), interval, zone, queue, totalCount);
		}

		public static Clock startingAt(final Clock clock, final Duration interval,
				final FBEvaluatorCountingEventQueue queue) {
			final long totalCount = queue.getTotalInputCount().get();
			return new IntervalClock(clock != null ? clock.instant() : Instant.EPOCH, interval,
					clock != null ? clock.getZone() : ZoneOffset.UTC, queue, totalCount);
		}

		public Instant getStart() {
			return offset;
		}

		public Duration getInterval() {
			return interval;
		}

		@Override
		public ZoneId getZone() {
			return zone;
		}

		public FBEvaluatorCountingEventQueue getQueue() {
			return queue;
		}

		public long getQueueOffset() {
			return queueOffset;
		}

		@Override
		public int hashCode() {
			return Objects.hash(offset, interval, zone, queue, Long.valueOf(queueOffset));
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (!super.equals(obj)) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final IntervalClock other = (IntervalClock) obj;
			return Objects.equals(offset, other.offset) && Objects.equals(interval, other.interval)
					&& Objects.equals(zone, other.zone) && Objects.equals(queue, other.queue)
					&& queueOffset == other.queueOffset;
		}

		@Override
		public String toString() {
			return String.format("%s [offset=%s, interval=%s, zone=%s]", getClass().getName(), offset, interval, //$NON-NLS-1$
					zone);
		}
	}
}
