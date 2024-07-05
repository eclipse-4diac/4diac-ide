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
	private static final int REQUEST_WAIT_TIME = 1000;

	private final Thread thread;

	private final AtomicReference<DebugEvent> request = new AtomicReference<>(null);
	private final AtomicBoolean suspended = new AtomicBoolean();
	private final AtomicReference<Evaluator> currentEvaluator = new AtomicReference<>();

	public EvaluatorDebugThread(final Thread thread, final EvaluatorDebugTarget debugTarget) {
		super(debugTarget);
		this.thread = thread;
		fireCreationEvent();
	}

	protected void request(final int kind, final int detail) {
		this.request(this, kind, detail);
	}

	protected void request(final Object source, final int kind, final int detail) {
		synchronized (request) {
			request.set(new DebugEvent(source, kind, detail));
			request.notifyAll();
		}
	}

	public DebugEvent peekRequest() {
		return request.get();
	}

	public DebugEvent awaitResumeRequest() throws InterruptedException {
		DebugEvent resultingRequest = null;
		synchronized (request) {
			while ((resultingRequest = request.getAndSet(null)) == null
					|| resultingRequest.getKind() != DebugEvent.RESUME) {
				request.wait(REQUEST_WAIT_TIME); // wake up every second in case we missed a notification
			}
		}
		return resultingRequest;
	}

	public void terminated() {
		fireTerminateEvent();
	}

	public Evaluator getCurrentEvaluator() {
		return currentEvaluator.get();
	}

	public void setCurrentEvaluator(final Evaluator evaluator) {
		currentEvaluator.set(evaluator);
	}

	public Thread getThread() {
		return thread;
	}

	@Override
	public boolean canResume() {
		return isSuspended();
	}

	@Override
	public boolean canSuspend() {
		return !isSuspended();
	}

	@Override
	public boolean isSuspended() {
		return suspended.get();
	}

	public void setSuspended(final boolean suspended) {
		this.suspended.set(suspended);
	}

	@Override
	public synchronized void resume() {
		this.request(DebugEvent.RESUME, DebugEvent.CLIENT_REQUEST);
	}

	@Override
	public void suspend() {
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
		final DebugEvent req = request.get();
		return req != null && req.isStepStart();
	}

	@Override
	public void stepInto() {
		this.request(DebugEvent.RESUME, DebugEvent.STEP_INTO);
	}

	@Override
	public void stepOver() {
		this.request(DebugEvent.RESUME, DebugEvent.STEP_OVER);
	}

	@Override
	public void stepReturn() {
		this.request(DebugEvent.RESUME, DebugEvent.STEP_RETURN);
	}

	@Override
	public boolean canTerminate() {
		return !isTerminated();
	}

	@Override
	public boolean isTerminated() {
		return !thread.isAlive();
	}

	@Override
	public void terminate() {
		thread.interrupt();
	}

	@Override
	public EvaluatorDebugStackFrame getTopStackFrame() throws DebugException {
		final Evaluator evaluator = getCurrentEvaluator();
		if (evaluator == null) {
			return null;
		}
		return getDebugTarget().getDebugger().getStackFrame(evaluator, this);
	}

	@Override
	public IStackFrame[] getStackFrames() throws DebugException {
		final CommonEvaluatorDebugger debugger = getDebugTarget().getDebugger();
		final List<EvaluatorDebugStackFrame> stackFrames = new ArrayList<>();
		for (Evaluator evaluator = getCurrentEvaluator(); evaluator != null; evaluator = evaluator.getParent()) {
			stackFrames.add(debugger.getStackFrame(evaluator, this));
		}
		return stackFrames.toArray(new IStackFrame[stackFrames.size()]);
	}

	@Override
	public boolean hasStackFrames() throws DebugException {
		return getCurrentEvaluator() != null;
	}

	@Override
	public int getPriority() throws DebugException {
		return thread.getPriority();
	}

	@Override
	public String getName() throws DebugException {
		return thread.getName();
	}

	@Override
	public IBreakpoint[] getBreakpoints() {
		return new IBreakpoint[0];
	}

	@Override
	public EvaluatorDebugTarget getDebugTarget() {
		return (EvaluatorDebugTarget) super.getDebugTarget();
	}
}
