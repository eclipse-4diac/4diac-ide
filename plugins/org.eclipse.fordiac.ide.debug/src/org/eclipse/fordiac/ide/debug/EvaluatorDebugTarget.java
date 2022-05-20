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
package org.eclipse.fordiac.ide.debug;

import java.util.List;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
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

	public EvaluatorDebugTarget(final String name, final Evaluator evaluator, final ILaunch launch) {
		super(null);
		this.name = name;
		this.launch = launch;
		this.process = new EvaluatorProcess(name, evaluator, launch);
		this.debugger = new CommonEvaluatorDebugger(this);
		this.process.getThreadGroup().attachDebugger(debugger);
		launch.addDebugTarget(this);
		this.fireCreationEvent();
	}

	public void start() {
		this.process.start();
	}

	public CommonEvaluatorDebugger getDebugger() {
		return debugger;
	}

	@Override
	public boolean canResume() {
		return this.debugger.getThreads().stream().anyMatch(EvaluatorDebugThread::canResume);
	}

	@Override
	public boolean canSuspend() {
		return this.debugger.getThreads().stream().anyMatch(EvaluatorDebugThread::canSuspend);
	}

	@Override
	public boolean isSuspended() {
		return this.debugger.getThreads().stream().anyMatch(EvaluatorDebugThread::isSuspended);
	}

	@Override
	public void resume() throws DebugException {
		for (final EvaluatorDebugThread thread : this.debugger.getThreads()) {
			thread.resume();
		}
	}

	@Override
	public void suspend() throws DebugException {
		for (final EvaluatorDebugThread thread : this.debugger.getThreads()) {
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
		return this.name;
	}

	@Override
	public IDebugTarget getDebugTarget() {
		return this;
	}

	@Override
	public ILaunch getLaunch() {
		return this.launch;
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
		return this.process;
	}

	@Override
	public IThread[] getThreads() throws DebugException {
		final List<EvaluatorDebugThread> threads = this.debugger.getThreads();
		return threads.toArray(new IThread[threads.size()]);
	}

	@Override
	public boolean hasThreads() throws DebugException {
		return this.process.getThreadGroup().activeCount() != 0;
	}
}
