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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.fordiac.ide.model.eval.Evaluator;

public class EvaluatorDebugThread extends EvaluatorDebugElement implements IThread {
	private final Thread thread;

	private final AtomicReference<DebugEvent> request = new AtomicReference<>(null);
	private final AtomicBoolean suspended = new AtomicBoolean();
	private final AtomicReference<Evaluator> currentEvaluator = new AtomicReference<>();

	public EvaluatorDebugThread(final Thread thread, final EvaluatorDebugTarget debugTarget) {
		super(debugTarget);
		this.thread = thread;
		this.fireCreationEvent();
	}

	protected void request(final int kind, final int detail) {
		this.request(this, kind, detail);
	}

	protected void request(final Object source, final int kind, final int detail) {
		synchronized (this.request) {
			this.request.set(new DebugEvent(source, kind, detail));
			this.request.notifyAll();
		}
	}

	public DebugEvent peekRequest() {
		return this.request.get();
	}

	public DebugEvent awaitResumeRequest() throws InterruptedException {
		DebugEvent resultingRequest = null;
		synchronized (this.request) {
			while ((resultingRequest = this.request.getAndSet(null)) == null || resultingRequest.getKind() != DebugEvent.RESUME) {
				this.request.wait(1000); // wake up every second in case we missed a notification
			}
		}
		return resultingRequest;
	}

	public Evaluator getCurrentEvaluator() {
		return this.currentEvaluator.get();
	}

	public void setCurrentEvaluator(final Evaluator evaluator) {
		this.currentEvaluator.set(evaluator);
	}

	public Thread getThread() {
		return this.thread;
	}

	@Override
	public boolean canResume() {
		return this.isSuspended();
	}

	@Override
	public boolean canSuspend() {
		return !this.isSuspended();
	}

	@Override
	public boolean isSuspended() {
		return this.suspended.get();
	}

	public void setSuspended(final boolean suspended) {
		this.suspended.set(suspended);
	}

	@Override
	public synchronized void resume() throws DebugException {
		this.request(DebugEvent.RESUME, DebugEvent.CLIENT_REQUEST);
	}

	@Override
	public void suspend() throws DebugException {
		this.request(DebugEvent.SUSPEND, DebugEvent.CLIENT_REQUEST);
	}

	@Override
	public boolean canStepInto() {
		return isSuspended();
	}

	@Override
	public boolean canStepOver() {
		return isSuspended();
	}

	@Override
	public boolean canStepReturn() {
		return isSuspended();
	}

	@Override
	public boolean isStepping() {
		final DebugEvent request = this.request.get();
		return request != null && request.isStepStart();
	}

	@Override
	public void stepInto() throws DebugException {
		this.request(DebugEvent.RESUME, DebugEvent.STEP_INTO);
	}

	@Override
	public void stepOver() throws DebugException {
		this.request(DebugEvent.RESUME, DebugEvent.STEP_OVER);
	}

	@Override
	public void stepReturn() throws DebugException {
		this.request(DebugEvent.RESUME, DebugEvent.STEP_RETURN);
	}

	@Override
	public boolean canTerminate() {
		return !this.isTerminated();
	}

	@Override
	public boolean isTerminated() {
		return !this.thread.isAlive();
	}

	@Override
	public void terminate() throws DebugException {
		this.thread.interrupt();
	}

	@Override
	public EvaluatorDebugStackFrame getTopStackFrame() throws DebugException {
		final Evaluator evaluator = this.getCurrentEvaluator();
		if (evaluator == null) {
			return null;
		}
		return this.getDebugTarget().getDebugger().getStackFrame(evaluator, this);
	}

	@Override
	public IStackFrame[] getStackFrames() throws DebugException {
		final CommonEvaluatorDebugger debugger = this.getDebugTarget().getDebugger();
		final List<EvaluatorDebugStackFrame> stackFrames = new ArrayList<>();
		for (Evaluator evaluator = this.getCurrentEvaluator(); evaluator != null; evaluator = evaluator.getParent()) {
			stackFrames.add(debugger.getStackFrame(evaluator, this));
		}
		return stackFrames.toArray(new IStackFrame[stackFrames.size()]);
	}

	@Override
	public boolean hasStackFrames() throws DebugException {
		return isSuspended();
	}

	@Override
	public int getPriority() throws DebugException {
		return this.thread.getPriority();
	}

	@Override
	public String getName() throws DebugException {
		return this.thread.getName();
	}

	@Override
	public IBreakpoint[] getBreakpoints() {
		return null;
	}

	@Override
	public EvaluatorDebugTarget getDebugTarget() {
		return (EvaluatorDebugTarget) super.getDebugTarget();
	}
}
