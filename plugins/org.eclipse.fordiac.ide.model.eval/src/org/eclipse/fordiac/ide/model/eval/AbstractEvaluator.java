/**
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
 */
package org.eclipse.fordiac.ide.model.eval;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

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

	public static Clock currentClock() {
		if (Thread.currentThread() instanceof final EvaluatorThread evaluatorThread) {
			return evaluatorThread.getExecutor().getClock();
		}
		return AbstractEvaluator.MonotonicClock.UTC;
	}

	public static void setClock(final Clock clock) {
		if (!(Thread.currentThread() instanceof final EvaluatorThread evaluatorThread)) {
			throw new IllegalStateException("Cannot set clock without evaluator thread"); //$NON-NLS-1$
		}
		evaluatorThread.getExecutor().setClock(clock);
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
}
