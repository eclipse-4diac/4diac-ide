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

	public CommonEvaluatorDebugger(EvaluatorDebugTarget debugTarget) {
		this.debugTarget = debugTarget;
		this.threadMonitor = new ThreadMonitor(debugTarget.getProcess().getThreadGroup());
		this.threadMonitor.schedule();
	}

	@Override
	public void trap(Object context, Evaluator eval) throws InterruptedException {
		EvaluatorDebugThread thread = getThread(Thread.currentThread());
		if (thread.getThread().isInterrupted()) {
			throw new InterruptedException();
		}
		DebugEvent request = thread.peekRequest();
		if (request != null && request.getKind() == DebugEvent.SUSPEND) {
			// update model
			this.getStackFrame(eval, thread).setCurrentContext(context);
			thread.setCurrentEvaluator(eval);
			thread.setSuspended(true);
			thread.fireEvent(request);
			try {
				// wait for resume
				request = thread.awaitResumeRequest();
				// suspend again (stepping)?
				if (request.isStepStart()) {
					thread.request(DebugEvent.SUSPEND, DebugEvent.STEP_END);
				}
			} finally {
				// update model
				thread.setSuspended(false);
				thread.setCurrentEvaluator(null);
				this.getStackFrame(eval, thread).setCurrentContext(null);
				thread.fireEvent(request);
			}
		}
	}

	public List<EvaluatorDebugThread> getThreads() {
		ThreadGroup threadGroup = this.debugTarget.getProcess().getThreadGroup();
		Thread[] threads = new Thread[threadGroup.activeCount()];
		int count = threadGroup.enumerate(threads);
		return Arrays.stream(threads, 0, count).map(this::getThread).collect(Collectors.toList());
	}

	protected EvaluatorDebugThread getThread(Thread thread) {
		return this.threads.computeIfAbsent(thread, key -> new EvaluatorDebugThread(key, debugTarget));
	}

	protected EvaluatorDebugStackFrame getStackFrame(Evaluator evaluator, EvaluatorDebugThread thread) {
		return this.stackFrames.computeIfAbsent(evaluator, key -> new EvaluatorDebugStackFrame(key, thread));
	}

	protected EvaluatorDebugVariable getVariable(Variable variable) {
		return this.variables.computeIfAbsent(variable, key -> new EvaluatorDebugVariable(key, debugTarget));
	}

	public class ThreadMonitor extends Job {
		private ThreadGroup threadGroup;

		private ThreadMonitor(ThreadGroup threadGroup) {
			super(String.format("ThreadMonitor [%s]", threadGroup.getName()));
			this.threadGroup = threadGroup;
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			// add new threads (on demand)
			Thread[] threads = new Thread[this.threadGroup.activeCount()];
			int count = threadGroup.enumerate(threads);
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
