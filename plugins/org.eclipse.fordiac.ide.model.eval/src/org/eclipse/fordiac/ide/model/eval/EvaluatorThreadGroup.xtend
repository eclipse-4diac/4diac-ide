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
import java.util.Set
import java.util.concurrent.ConcurrentHashMap
import org.eclipse.xtend.lib.annotations.Accessors

class EvaluatorThreadGroup extends ThreadGroup {
	@Accessors(PUBLIC_GETTER) EvaluatorDebugger debugger = DefaultEvaluatorDebugger.INSTANCE
	@Accessors final Set<EvaluatorMonitor> monitorSet = ConcurrentHashMap.newKeySet
	@Accessors Clock clock = AbstractEvaluator.MonotonicClock.UTC

	new(String name) {
		super(name)
		daemon = true
	}

	def synchronized void attachDebugger(EvaluatorDebugger debugger) {
		if (debugger !== this.debugger && this.debugger !== DefaultEvaluatorDebugger.INSTANCE) {
			throw new IllegalStateException("Another debugger is currently attached")
		}
		this.debugger = debugger
	}

	def synchronized void detachDebugger(EvaluatorDebugger debugger) {
		if (this.debugger !== debugger) {
			throw new IllegalStateException("Another debugger is currently attached")
		}
		this.debugger = DefaultEvaluatorDebugger.INSTANCE
	}

	def void addMonitor(EvaluatorMonitor monitor) {
		monitorSet.add(monitor)
	}

	def void removeMonitor(EvaluatorMonitor monitor) {
		monitorSet.remove(monitor)
	}
}
