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

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.debug.breakpoint.EvaluatorLineBreakpoint;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorDebugger;
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory;
import org.eclipse.fordiac.ide.model.eval.EvaluatorThreadPoolExecutor;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.eval.variable.VariableEvaluator;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

import com.google.common.base.Objects;

/**
 * A common debugger implementation for evaluators based on the Eclipse
 * debugging framework
 */
public class CommonEvaluatorDebugger implements EvaluatorDebugger {
	private final EvaluatorDebugTarget debugTarget;
	private final ConcurrentHashMap<Thread, EvaluatorDebugThread> threads = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<Evaluator, EvaluatorDebugStackFrame> stackFrames = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<Variable<?>, EvaluatorDebugVariable> variables = new ConcurrentHashMap<>();
	private final Set<IBreakpoint> breakpoints = ConcurrentHashMap.newKeySet();
	private final AtomicBoolean suspendOnFirstLine = new AtomicBoolean();

	/**
	 * Create a new debugger
	 *
	 * @param debugTarget The debug target
	 */
	public CommonEvaluatorDebugger(final EvaluatorDebugTarget debugTarget) {
		this.debugTarget = debugTarget;
	}

	@Override
	public void trap(final Object context, final Evaluator eval) throws InterruptedException {
		if (Thread.currentThread().isInterrupted()) {
			throw new InterruptedException();
		}
		if (eval instanceof VariableEvaluator) {
			return; // skip evaluated variables
		}
		final EvaluatorDebugThread thread = getThread(Thread.currentThread());
		final EvaluatorDebugStackFrame frame = getStackFrame(eval, thread);
		DebugEvent request = thread.peekRequest();
		if (shouldSuspend(request, frame, context)) {
			// update model
			frame.setCurrentContext(context);
			thread.setCurrentEvaluator(eval);
			thread.suspended(request != null ? request.getDetail() : DebugEvent.BREAKPOINT);
			try {
				// wait for resume
				request = thread.awaitResumeRequest();
				// handle request
				handleResumeRequest(request, thread, frame);
			} finally {
				// update model
				thread.setCurrentEvaluator(getPersistentEvaluator(eval));
				thread.resumed(request != null ? request.getDetail() : DebugEvent.UNSPECIFIED);
			}
		}
	}

	protected boolean shouldSuspend(final DebugEvent request, final EvaluatorDebugStackFrame frame,
			final Object context) {
		// check user request
		if (request != null && request.getKind() == DebugEvent.SUSPEND) {
			if (request.getDetail() == DebugEvent.STEP_END) {
				return shouldSuspendAfterStepEnd(request.getSource(), frame);
			}
			return true; // otherwise suspend unconditionally
		}
		// check breakpoints or otherwise keep running
		return anyBreakpointMatches(frame, context);
	}

	protected static boolean shouldSuspendAfterStepEnd(final Object source, final EvaluatorDebugStackFrame frame) {
		// suspend after step end only when:
		if (source instanceof EvaluatorDebugThread) { // requested on thread (STEP_INTO)
			return true;
		}
		if (source instanceof final EvaluatorDebugStackFrame edsf) { // requested on stack frame and:
			if (source == frame) { // we reached the requested frame (STEP_OVER or STEP_RETURN)
				return true;
			}
			if (!edsf.isAncestorOf(frame)) { // we are above the requested frame
				return true;
			}
		}
		return false; // otherwise keep running
	}

	protected boolean anyBreakpointMatches(final EvaluatorDebugStackFrame frame, final Object context) {
		return DebugPlugin.getDefault().getBreakpointManager().isEnabled()
				&& breakpoints.stream().anyMatch(breakpoint -> breakpointMatches(breakpoint, frame, context));
	}

	protected boolean breakpointMatches(final IBreakpoint breakpoint, final EvaluatorDebugStackFrame frame,
			final Object context) {
		try {
			if (!breakpoint.isEnabled()) {
				return false;
			}
			final IResource resource = getResource(context);
			final int lineNumber = getLineNumber(context);
			if (breakpoint instanceof final EvaluatorLineBreakpoint evaluatorLineBreakpoint
					&& evaluatorLineBreakpoint.isApplicable(frame.getEvaluator())
					&& Objects.equal(breakpoint.getMarker().getResource(), resource)
					&& evaluatorLineBreakpoint.getLineNumber() == lineNumber) {
				if (evaluatorLineBreakpoint.isConditionEnabled()) {
					return evaluateBreakpointCondition(evaluatorLineBreakpoint, frame);
				}
				return true;
			}
		} catch (final CoreException e) {
			FordiacLogHelper.logWarning(e.getMessage(), e);
			// ignore (we don't care about broken breakpoints)
		}
		return false;
	}

	protected static boolean evaluateBreakpointCondition(final EvaluatorLineBreakpoint breakpoint,
			final EvaluatorDebugStackFrame frame) {
		try {
			final Evaluator evaluator = EvaluatorFactory.createEvaluator(breakpoint.getCondition(), String.class, null,
					null, frame.getEvaluator());
			final Value result = evaluator.evaluate();
			return ValueOperations.asBoolean(result);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			return false;
		} catch (final Exception e) {
			FordiacLogHelper.logWarning("Couldn't evaluate breakpoint condition: " + e.getMessage(), e); //$NON-NLS-1$
			return false;
		}
	}

	protected void handleResumeRequest(final DebugEvent request, final EvaluatorDebugThread thread,
			final EvaluatorDebugStackFrame frame) {
		// suspend again (stepping)?
		if (request.isStepStart()) {
			switch (request.getDetail()) {
			default:
			case DebugEvent.STEP_INTO:
				thread.request(thread, DebugEvent.SUSPEND, DebugEvent.STEP_END);
				break;
			case DebugEvent.STEP_OVER: {
				final Object resumeSource = request.getSource();
				final Object suspendSource = resumeSource instanceof EvaluatorDebugStackFrame ? resumeSource : frame;
				thread.request(suspendSource, DebugEvent.SUSPEND, DebugEvent.STEP_END);
				break;
			}
			case DebugEvent.STEP_RETURN: {
				final Object resumeSource = request.getSource();
				final Object resumeSourceParent = resumeSource instanceof final EvaluatorDebugStackFrame edsf
						? edsf.getParent()
						: frame.getParent();
				final Object suspendSource = resumeSourceParent != null ? resumeSourceParent : debugTarget;
				thread.request(suspendSource, DebugEvent.SUSPEND, DebugEvent.STEP_END);
				break;
			}
			}
		}
	}

	protected IResource getResource(final Object context) {
		if (context instanceof final EObject eo) {
			final EObject root = EcoreUtil.getRootContainer(eo);
			if (root instanceof final FBType fbType) {
				return fbType.getTypeEntry().getFile();
			}
			return getResource(((EObject) context).eResource());
		}
		if (context instanceof final Resource resource) {
			final URI uri = resource.getURI();
			if (uri.isPlatformResource()) {
				final String path = uri.toPlatformString(true);
				return ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(path));
			}
		}
		return null;
	}

	protected static Evaluator getPersistentEvaluator(Evaluator eval) {
		while (eval != null && !eval.isPersistent()) {
			eval = eval.getParent();
		}
		return eval;
	}

	/**
	 * Get the line number for a context object
	 *
	 * @param context The context object
	 * @return The line number or -1 if not found
	 */
	@SuppressWarnings("static-method")
	public int getLineNumber(final Object context) {
		if (context instanceof final EObject eo) {
			final ICompositeNode node = NodeModelUtils.findActualNodeFor(eo);
			if (context instanceof ICallable) {
				return node.getEndLine();
			}
			if (node != null) {
				return node.getStartLine();
			}
		}
		return -1;
	}

	/**
	 * Add a breakpoint to this debugger
	 *
	 * @param breakpoint The breakpoint
	 */
	public void addBreakpoint(final IBreakpoint breakpoint) {
		breakpoints.add(breakpoint);
	}

	/**
	 * Remove a breakpoint from this debugger
	 *
	 * @param breakpoint The breakpoint
	 * @param delta      The marker delta
	 */
	public void removeBreakpoint(final IBreakpoint breakpoint, final IMarkerDelta delta) {
		breakpoints.remove(breakpoint);
	}

	/**
	 * Update a breakpoint in this debugger
	 *
	 * @param breakpoint The breakpoint
	 * @param delta      The marker delta
	 */
	public void updateBreakpoint(final IBreakpoint breakpoint, final IMarkerDelta delta) {
		// do nothing
	}

	/**
	 * Check whether to suspend a new thread on the first line
	 *
	 * @return stop on first line flag
	 */
	public boolean isSuspendOnFirstLine() {
		return suspendOnFirstLine.get();
	}

	/**
	 * Set suspend on first line for a new thread
	 *
	 * @param suspendOnFirstLine whether to suspend on the first line
	 * @apiNote this flag is automatically reset when the thread is suspended
	 */
	public void setSuspendOnFirstLine(final boolean suspendOnFirstLine) {
		this.suspendOnFirstLine.set(suspendOnFirstLine);
	}

	/**
	 * Get the list of threads currently known to this debugger
	 *
	 * @return A copy of the list of threads
	 */
	public List<EvaluatorDebugThread> getThreads() {
		return List.copyOf(threads.values());
	}

	/**
	 * Get a debug thread for the specified thread of the Java VM
	 *
	 * @param thread The Java thread
	 * @return The debug thread (demand created if none exists)
	 */
	public EvaluatorDebugThread getThread(final Thread thread) {
		return threads.computeIfAbsent(thread, key -> new EvaluatorDebugThread(key, debugTarget));
	}

	/**
	 * Get a debug stack frame for the specified evaluator and debug thread
	 *
	 * @param evaluator The evaluator
	 * @param thread    The debug thread
	 * @return The stack frame (demand created if none exists)
	 */
	protected EvaluatorDebugStackFrame getStackFrame(final Evaluator evaluator, final EvaluatorDebugThread thread) {
		return stackFrames.computeIfAbsent(evaluator, key -> new EvaluatorDebugStackFrame(key, thread));
	}

	/**
	 * Get a debug variable for the specified evaluator variable
	 *
	 * @param variable The evaluator variable
	 * @return The debug variable (demand created if none exists)
	 */
	public EvaluatorDebugVariable getVariable(final Variable<?> variable) {
		return variables.computeIfAbsent(variable, key -> debugTarget.createVariable(key, key.getName()));
	}

	@Override
	public void beforeExecute(final Thread thread, final Runnable runnable,
			final EvaluatorThreadPoolExecutor executor) {
		final EvaluatorDebugThread debugThread = getThread(thread); // trigger debug thread demand creation
		if (suspendOnFirstLine.getAndSet(false)) {
			debugThread.suspend();
		}
	}

	@Override
	public void afterExecute(final Thread thread, final Runnable runnable, final Throwable throwable,
			final EvaluatorThreadPoolExecutor executor) {
		final EvaluatorDebugThread debugThread = threads.remove(thread);
		if (debugThread != null) {
			debugThread.terminated();
		}
	}

	@Override
	public void terminated(final EvaluatorThreadPoolExecutor executor) {
		debugTarget.terminated();
	}
}
