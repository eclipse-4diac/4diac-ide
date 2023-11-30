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

import java.util.List;
import java.util.stream.Stream;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.fordiac.ide.debug.breakpoint.EvaluatorLineBreakpoint;
import org.eclipse.fordiac.ide.model.eval.Evaluator;

public class EvaluatorDebugTarget extends EvaluatorDebugElement implements IDebugTarget {
	private final String name;
	private final ILaunch launch;
	private final EvaluatorProcess process;
	private final CommonEvaluatorDebugger debugger;

	public EvaluatorDebugTarget(final String name, final Evaluator evaluator, final ILaunch launch)
			throws CoreException {
		super(null);
		this.name = name;
		this.launch = launch;
		process = new EvaluatorProcess(name, evaluator, launch);
		debugger = new CommonEvaluatorDebugger(this);
		process.getExecutor().attachDebugger(debugger);
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
	public boolean canResume() {
		return debugger.getThreads().stream().anyMatch(EvaluatorDebugThread::canResume);
	}

	@Override
	public boolean canSuspend() {
		return debugger.getThreads().stream().anyMatch(EvaluatorDebugThread::canSuspend);
	}

	@Override
	public boolean isSuspended() {
		return debugger.getThreads().stream().anyMatch(EvaluatorDebugThread::isSuspended);
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
}
