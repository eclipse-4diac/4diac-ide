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
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class EvaluatorThreadGroup extends ThreadGroup {

	private EvaluatorDebugger debugger = DefaultEvaluatorDebugger.INSTANCE;
	private final Set<EvaluatorMonitor> monitorSet = ConcurrentHashMap.<EvaluatorMonitor>newKeySet();
	private Clock clock = AbstractEvaluator.MonotonicClock.UTC;

	public EvaluatorThreadGroup(final String name) {
		super(name);
	}

	public synchronized void attachDebugger(final EvaluatorDebugger debugger) {
		if (debugger != this.debugger && this.debugger != DefaultEvaluatorDebugger.INSTANCE) {
			throw new IllegalStateException("Another debugger is currently attached"); //$NON-NLS-1$
		}
		this.debugger = debugger;
	}

	public synchronized void detachDebugger(final EvaluatorDebugger debugger) {
		if (this.debugger != debugger) {
			throw new IllegalStateException("Another debugger is currently attached"); //$NON-NLS-1$
		}
		this.debugger = DefaultEvaluatorDebugger.INSTANCE;
	}

	public void addMonitor(final EvaluatorMonitor monitor) {
		monitorSet.add(monitor);
	}

	public void removeMonitor(final EvaluatorMonitor monitor) {
		monitorSet.remove(monitor);
	}

	public EvaluatorDebugger getDebugger() {
		return debugger;
	}

	public Set<EvaluatorMonitor> getMonitorSet() {
		return monitorSet;
	}

	public Clock getClock() {
		return clock;
	}

	public void setClock(final Clock clock) {
		this.clock = clock;
	}
}
