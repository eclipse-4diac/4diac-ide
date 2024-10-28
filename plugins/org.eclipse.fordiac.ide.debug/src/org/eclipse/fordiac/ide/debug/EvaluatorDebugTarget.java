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
package org.eclipse.fordiac.ide.debug;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.fordiac.ide.debug.breakpoint.EvaluatorLineBreakpoint;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorMonitor;
import org.eclipse.fordiac.ide.model.eval.EvaluatorThreadPoolExecutor;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public class EvaluatorDebugTarget extends EvaluatorDebugElement implements IEvaluatorDebugTarget {
	private final String name;
	private final ILaunch launch;
	private final IResource resource;
	private final EvaluatorProcess process;
	private final CommonEvaluatorDebugger debugger;
	private final AtomicLong variableUpdateCount = new AtomicLong();

	public EvaluatorDebugTarget(final String name, final Evaluator evaluator, final ILaunch launch,
			final IResource resource) throws CoreException {
		super(null);
		this.name = name;
		this.launch = launch;
		this.resource = resource;
		process = new EvaluatorProcess(name, evaluator, launch);
		debugger = new CommonEvaluatorDebugger(this);
		process.getExecutor().attachDebugger(debugger);
		process.getExecutor().addMonitor(new EvaluatorVariableMonitor());
		launch.addDebugTarget(this);
		DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(this);
		Stream.of(DebugPlugin.getDefault().getBreakpointManager().getBreakpoints())
				.forEachOrdered(this::breakpointAdded);
		fireCreationEvent();
	}

	public void start() {
		process.start();
	}

	public void terminated() {
		DebugPlugin.getDefault().getBreakpointManager().removeBreakpointListener(this);
		fireTerminateEvent();
	}

	public CommonEvaluatorDebugger getDebugger() {
		return debugger;
	}

	@Override
	public long getVariableUpdateCount() {
		return variableUpdateCount.get();
	}

	public long incrementVariableUpdateCount() {
		return variableUpdateCount.incrementAndGet();
	}

	@Override
	public TypeLibrary getTypeLibrary() {
		return TypeLibraryManager.INSTANCE.getTypeLibrary(resource.getProject());
	}

	@Override
	public boolean canResume() {
		return debugger.getThreads().stream().anyMatch(EvaluatorDebugThread::canResume);
	}

	@Override
	public boolean canSuspend() {
		return debugger.getThreads().stream().anyMatch(EvaluatorDebugThread::canSuspend);
	}

	@Override
	public boolean isSuspended() {
		return debugger.getThreads().stream().allMatch(EvaluatorDebugThread::isSuspended);
	}

	@Override
	public void resume() throws DebugException {
		for (final EvaluatorDebugThread thread : debugger.getThreads()) {
			thread.resume();
		}
	}

	@Override
	public void suspend() throws DebugException {
		for (final EvaluatorDebugThread thread : debugger.getThreads()) {
			thread.suspend();
		}
	}

	@Override
	public boolean supportsBreakpoint(final IBreakpoint breakpoint) {
		return breakpoint instanceof EvaluatorLineBreakpoint;
	}

	@Override
	public void breakpointAdded(final IBreakpoint breakpoint) {
		debugger.addBreakpoint(breakpoint);
	}

	@Override
	public void breakpointRemoved(final IBreakpoint breakpoint, final IMarkerDelta delta) {
		debugger.removeBreakpoint(breakpoint, delta);
	}

	@Override
	public void breakpointChanged(final IBreakpoint breakpoint, final IMarkerDelta delta) {
		debugger.updateBreakpoint(breakpoint, delta);
	}

	@Override
	public String getName() throws DebugException {
		return name;
	}

	@Override
	public IDebugTarget getDebugTarget() {
		return this;
	}

	@Override
	public ILaunch getLaunch() {
		return launch;
	}

	@Override
	public boolean canTerminate() {
		return process.canTerminate();
	}

	@Override
	public boolean isTerminated() {
		return process.isTerminated();
	}

	@Override
	public void terminate() throws DebugException {
		process.terminate();
	}

	@Override
	public boolean canDisconnect() {
		return false;
	}

	@Override
	public void disconnect() throws DebugException {
		throw new DebugException(Status.error("Disconnect is not supported")); //$NON-NLS-1$
	}

	@Override
	public boolean isDisconnected() {
		return false;
	}

	@Override
	public boolean supportsStorageRetrieval() {
		return false;
	}

	@Override
	public IMemoryBlock getMemoryBlock(final long startAddress, final long length) throws DebugException {
		throw new DebugException(Status.error("Getting memory blocks is not supported")); //$NON-NLS-1$
	}

	@Override
	public EvaluatorProcess getProcess() {
		return process;
	}

	@Override
	public IThread[] getThreads() throws DebugException {
		final List<EvaluatorDebugThread> threads = debugger.getThreads();
		return threads.toArray(new IThread[threads.size()]);
	}

	@Override
	public boolean hasThreads() throws DebugException {
		return !debugger.getThreads().isEmpty();
	}

	private class EvaluatorVariableMonitor extends Job implements EvaluatorMonitor {

		private static final int UPDATE_INTERVAL = 200;

		private final AtomicReference<Set<Variable<?>>> queue = new AtomicReference<>(ConcurrentHashMap.newKeySet());

		@Override
		protected IStatus run(final IProgressMonitor monitor) {
			incrementVariableUpdateCount();
			final DebugEvent[] events = queue.getAndSet(ConcurrentHashMap.newKeySet()).stream()
					.map(debugger::getVariable)
					.map(variable -> new DebugEvent(variable, DebugEvent.CHANGE, DebugEvent.CONTENT))
					.toArray(DebugEvent[]::new);
			if (events.length != 0) {
				DebugPlugin.getDefault().fireDebugEventSet(events);
			}
			return Status.OK_STATUS;
		}

		public EvaluatorVariableMonitor() {
			super(MessageFormat.format(Messages.EvaluatorDebugTarget_UpdateVariablesJobName, name));
		}

		@Override
		public void update(final Collection<? extends Variable<?>> variables, final Evaluator evaluator) {
			if (!variables.isEmpty() && evaluator.isPersistent()) {
				queue.get().addAll(variables);
				schedule(UPDATE_INTERVAL);
			}
		}

		@Override
		public void info(final String message) {
			// empty default
		}

		@Override
		public void warn(final String message) {
			// empty default
		}

		@Override
		public void error(final String message) {
			// empty default
		}

		@Override
		public void error(final String message, final Throwable t) {
			// empty default
		}

		@Override
		public void terminated(final EvaluatorThreadPoolExecutor executor) {
			// empty default
		}
	}
}
