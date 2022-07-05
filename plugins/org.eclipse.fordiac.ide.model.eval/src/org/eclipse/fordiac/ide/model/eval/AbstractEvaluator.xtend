/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.eval

import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.Set
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.Data
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

@FinalFieldsConstructor
abstract class AbstractEvaluator implements Evaluator {
	@Accessors final Variable<?> context
	@Accessors final Evaluator parent

	def protected <T> T trap(T context) {
		currentDebugger.trap(context, this)
		return context
	}

	def protected void info(String message) {
		currentMonitors.forEach[info(message)]
	}

	def protected void warn(String message) {
		currentMonitors.forEach[warn(message)]
	}

	def protected void error(String message) {
		currentMonitors.forEach[error(message)]
	}

	def protected void error(String message, Throwable t) {
		currentMonitors.forEach[error(message, t)]
	}

	def static EvaluatorDebugger currentDebugger() {
		val threadGroup = Thread.currentThread.threadGroup
		if (threadGroup instanceof EvaluatorThreadGroup) {
			return threadGroup.debugger
		}
		return DefaultEvaluatorDebugger.INSTANCE
	}

	def static Set<EvaluatorMonitor> currentMonitors() {
		val threadGroup = Thread.currentThread.threadGroup
		if (threadGroup instanceof EvaluatorThreadGroup) {
			return threadGroup.monitorSet
		}
		return emptySet
	}

	def static Clock currentClock() {
		val threadGroup = Thread.currentThread.threadGroup
		if (threadGroup instanceof EvaluatorThreadGroup) {
			return threadGroup.clock
		}
		return MonotonicClock.UTC
	}

	def static void setClock(Clock clock) {
		val threadGroup = Thread.currentThread.threadGroup
		if (threadGroup instanceof EvaluatorThreadGroup) {
			threadGroup.clock = clock
		} else {
			throw new IllegalStateException("Cannot set clock without evaluator thread group")
		}
	}

	@Data
	static class MonotonicClock extends Clock {
		public static final MonotonicClock UTC = new MonotonicClock(ZoneOffset.UTC)

		@Accessors final ZoneId zone;

		override instant() {
			val nanoTime = System.nanoTime
			Instant.ofEpochSecond(nanoTime / 1000_000_000L, nanoTime % 1000_000_000L)
		}

		override withZone(ZoneId zone) {
			new MonotonicClock(zone)
		}
	}
}
