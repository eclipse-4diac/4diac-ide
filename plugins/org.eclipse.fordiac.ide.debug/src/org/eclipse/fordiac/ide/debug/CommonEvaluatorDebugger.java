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

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorDebugger;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;

public class CommonEvaluatorDebugger implements EvaluatorDebugger {
	private final EvaluatorDebugTarget debugTarget;
	private final ThreadMonitor threadMonitor;
	private final ConcurrentHashMap<Thread, EvaluatorDebugThread> threads = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<Evaluator, EvaluatorDebugStackFrame> stackFrames = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<Variable, EvaluatorDebugVariable> variables = new ConcurrentHashMap<>();

	public CommonEvaluatorDebugger(final EvaluatorDebugTarget debugTarget) {
		this.debugTarget = debugTarget;
		this.threadMonitor = new ThreadMonitor(debugTarget.getProcess().getThreadGroup());
		this.threadMonitor.schedule();
	}

	@Override
	public void trap(final Object context, final Evaluator eval) throws InterruptedException {
		final EvaluatorDebugThread thread = getThread(Thread.currentThread());
		final EvaluatorDebugStackFrame frame = this.getStackFrame(eval, thread);
		if (thread.getThread().isInterrupted()) {
			throw new InterruptedException();
		}
		DebugEvent request = thread.peekRequest();
		if (shouldSuspend(request, frame)) {
			// update model
			frame.setCurrentContext(context);
			thread.setCurrentEvaluator(eval);
			thread.setSuspended(true);
			thread.fireEvent(new DebugEvent(thread, request.getKind(), request.getDetail()));
			try {
				// wait for resume
				request = thread.awaitResumeRequest();
				// suspend again (stepping)?
				if (request.isStepStart()) {
					switch (request.getDetail()) {
					default:
					case DebugEvent.STEP_INTO:
						thread.request(thread, DebugEvent.SUSPEND, DebugEvent.STEP_END);
						break;
					case DebugEvent.STEP_OVER: {
						final Object resumeSource = request.getSource();
						final Object suspendSource = resumeSource instanceof EvaluatorDebugStackFrame ? resumeSource
								: frame;
						thread.request(suspendSource, DebugEvent.SUSPEND, DebugEvent.STEP_END);
						break;
					}
					case DebugEvent.STEP_RETURN: {
						final Object resumeSource = request.getSource();
						final Object resumeSourceParent = resumeSource instanceof EvaluatorDebugStackFrame
								? ((EvaluatorDebugStackFrame) resumeSource).getParent()
										: frame.getParent();
						final Object suspendSource = resumeSourceParent != null ? resumeSourceParent : debugTarget;
						thread.request(suspendSource, DebugEvent.SUSPEND, DebugEvent.STEP_END);
						break;
					}
					}
				}
			} finally {
				// update model
				thread.setSuspended(false);
				thread.setCurrentEvaluator(null);
				thread.fireEvent(new DebugEvent(thread, request.getKind(), request.getDetail()));
			}
		}
	}

	protected boolean shouldSuspend(final DebugEvent request, final EvaluatorDebugStackFrame frame) {
		if (request != null && request.getKind() == DebugEvent.SUSPEND) {
			final Object source = request.getSource();
			if (request.getDetail() == DebugEvent.STEP_END) { // suspend after step only when:
				if (source instanceof EvaluatorDebugThread) { // requested on thread (STEP_INTO)
					return true;
				} else if (source instanceof EvaluatorDebugStackFrame) { // requested on stack frame and:
					if (source == frame) { // we reached the requested frame (STEP_OVER or STEP_RETURN)
						return true;
					}
					if (!((EvaluatorDebugStackFrame) source).isAncestorOf(frame)) { // we are above the requested frame
						return true;
					}
				}
				return false; // otherwise keep running
			}
			return true; // otherwise suspend unconditionally
		}
		return false; // otherwise keep running
	}

	public List<EvaluatorDebugThread> getThreads() {
		final ThreadGroup threadGroup = this.debugTarget.getProcess().getThreadGroup();
		final Thread[] threads = new Thread[threadGroup.activeCount()];
		final int count = threadGroup.enumerate(threads);
		return Arrays.stream(threads, 0, count).map(this::getThread).collect(Collectors.toList());
	}

	protected EvaluatorDebugThread getThread(final Thread thread) {
		return this.threads.computeIfAbsent(thread, key -> new EvaluatorDebugThread(key, debugTarget));
	}

	protected EvaluatorDebugStackFrame getStackFrame(final Evaluator evaluator, final EvaluatorDebugThread thread) {
		return this.stackFrames.computeIfAbsent(evaluator, key -> new EvaluatorDebugStackFrame(key, thread));
	}

	protected EvaluatorDebugVariable getVariable(final Variable variable) {
		return this.variables.computeIfAbsent(variable, key -> new EvaluatorDebugVariable(key, debugTarget));
	}

	public class ThreadMonitor extends Job {
		private final ThreadGroup threadGroup;

		private ThreadMonitor(final ThreadGroup threadGroup) {
			super(String.format("ThreadMonitor [%s]", threadGroup.getName()));
			this.threadGroup = threadGroup;
		}

		@Override
		protected IStatus run(final IProgressMonitor monitor) {
			// add new threads (on demand)
			final Thread[] threads = new Thread[this.threadGroup.activeCount()];
			final int count = threadGroup.enumerate(threads);
			Arrays.stream(threads, 0, count).forEach(CommonEvaluatorDebugger.this::getThread);
			// notify and remove dead threads
			CommonEvaluatorDebugger.this.threads.entrySet().removeIf(entry -> {
				if (entry.getKey().isAlive()) {
					return false;
				}
				entry.getValue().fireTerminateEvent();
				return true;
			});
			// notify dead group or reschedule
			if (threadGroup.isDestroyed()) {
				CommonEvaluatorDebugger.this.debugTarget.fireTerminateEvent();
			} else {
				this.schedule(1000);
			}
			return Status.OK_STATUS;
		}

	}
}
