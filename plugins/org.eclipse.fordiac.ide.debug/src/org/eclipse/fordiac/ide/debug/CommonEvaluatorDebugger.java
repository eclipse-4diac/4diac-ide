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
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
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

public class CommonEvaluatorDebugger implements EvaluatorDebugger {
	private final EvaluatorDebugTarget debugTarget;
	private final ThreadMonitor threadMonitor;
	private final ConcurrentHashMap<Thread, EvaluatorDebugThread> threads = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<Evaluator, EvaluatorDebugStackFrame> stackFrames = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<Variable<?>, EvaluatorDebugVariable> variables = new ConcurrentHashMap<>();
	private final Set<IBreakpoint> breakpoints = ConcurrentHashMap.newKeySet();

	public CommonEvaluatorDebugger(final EvaluatorDebugTarget debugTarget) {
		this.debugTarget = debugTarget;
		this.threadMonitor = new ThreadMonitor(debugTarget.getProcess().getThreadGroup());
		this.threadMonitor.schedule();
		DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(debugTarget);
		this.breakpoints.addAll(List.of(DebugPlugin.getDefault().getBreakpointManager().getBreakpoints()));
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
		final EvaluatorDebugStackFrame frame = this.getStackFrame(eval, thread);
		DebugEvent request = thread.peekRequest();
		if (shouldSuspend(request, frame, context)) {
			// update model
			frame.setCurrentContext(context);
			thread.setCurrentEvaluator(eval);
			thread.setSuspended(true);
			if (request != null) {
				thread.fireEvent(new DebugEvent(thread, request.getKind(), request.getDetail()));
			} else {
				thread.fireEvent(new DebugEvent(thread, DebugEvent.SUSPEND, DebugEvent.BREAKPOINT));
			}
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
				if (request != null) {
					thread.fireEvent(new DebugEvent(thread, request.getKind(), request.getDetail()));
				} else {
					thread.fireEvent(new DebugEvent(thread, DebugEvent.RESUME, DebugEvent.UNSPECIFIED));
				}
			}
		}
	}

	protected boolean shouldSuspend(final DebugEvent request, final EvaluatorDebugStackFrame frame,
			final Object context) {
		// check user request
		if (request != null && request.getKind() == DebugEvent.SUSPEND) {
			final Object source = request.getSource();
			if (request.getDetail() == DebugEvent.STEP_END) { // suspend after step only when:
				if (source instanceof EvaluatorDebugThread) { // requested on thread (STEP_INTO)
					return true;
				}
				if (source instanceof EvaluatorDebugStackFrame) { // requested on stack frame and:
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
		// check breakpoints or otherwise keep running
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
			if ((breakpoint instanceof final EvaluatorLineBreakpoint evaluatorLineBreakpoint)
					&& (evaluatorLineBreakpoint.isApplicable(frame.getEvaluator())
							&& Objects.equal(breakpoint.getMarker().getResource(), resource)
							&& evaluatorLineBreakpoint.getLineNumber() == lineNumber)) {
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

	public IResource getResource(final Object context) {
		if (context instanceof EObject) {
			final EObject root = EcoreUtil.getRootContainer((EObject) context);
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

	@SuppressWarnings("static-method")
	public int getLineNumber(final Object context) {
		if (context instanceof EObject) {
			final ICompositeNode node = NodeModelUtils.findActualNodeFor((EObject) context);
			if (context instanceof ICallable) {
				return node.getEndLine();
			}
			if (node != null) {
				return node.getStartLine();
			}
		}
		return -1;
	}

	public void addBreakpoint(final IBreakpoint breakpoint) {
		breakpoints.add(breakpoint);
	}

	public void removeBreakpoint(final IBreakpoint breakpoint, final IMarkerDelta delta) {
		breakpoints.remove(breakpoint);
	}

	public void updateBreakpoint(final IBreakpoint breakpoint, final IMarkerDelta delta) {
		// do nothing
	}

	public List<EvaluatorDebugThread> getThreads() {
		final ThreadGroup threadGroup = this.debugTarget.getProcess().getThreadGroup();
		final Thread[] activeThreads = new Thread[threadGroup.activeCount()];
		final int count = threadGroup.enumerate(activeThreads);
		return Arrays.stream(activeThreads, 0, count).map(this::getThread).collect(Collectors.toList());
	}

	protected EvaluatorDebugThread getThread(final Thread thread) {
		return this.threads.computeIfAbsent(thread, key -> new EvaluatorDebugThread(key, debugTarget));
	}

	protected EvaluatorDebugStackFrame getStackFrame(final Evaluator evaluator, final EvaluatorDebugThread thread) {
		return this.stackFrames.computeIfAbsent(evaluator, key -> new EvaluatorDebugStackFrame(key, thread));
	}

	public EvaluatorDebugVariable getVariable(final Variable<?> variable) {
		return this.variables.computeIfAbsent(variable, key -> new EvaluatorDebugVariable(key, debugTarget));
	}

	public final class ThreadMonitor extends Job {
		private final ThreadGroup threadGroup;

		private ThreadMonitor(final ThreadGroup threadGroup) {
			super(String.format("ThreadMonitor [%s]", threadGroup.getName())); //$NON-NLS-1$
			this.threadGroup = threadGroup;
		}

		@Override
		protected IStatus run(final IProgressMonitor monitor) {
			// add new threads (on demand)
			final Thread[] activeThreads = new Thread[this.threadGroup.activeCount()];
			final int count = threadGroup.enumerate(activeThreads);
			Arrays.stream(activeThreads, 0, count).forEach(CommonEvaluatorDebugger.this::getThread);
			// notify and remove dead threads
			CommonEvaluatorDebugger.this.threads.entrySet().removeIf(entry -> {
				if (entry.getKey().getState() != Thread.State.TERMINATED) {
					return false;
				}
				if (DebugPlugin.getDefault() != null) {
					entry.getValue().fireTerminateEvent();
				}
				return true;
			});
			// notify dead group or reschedule
			if (debugTarget.getProcess().isTerminated()) {
				DebugPlugin.getDefault().getBreakpointManager()
						.removeBreakpointListener(CommonEvaluatorDebugger.this.debugTarget);
				CommonEvaluatorDebugger.this.debugTarget.fireTerminateEvent();
			} else {
				this.schedule(1000);
			}
			return Status.OK_STATUS;
		}

	}
}
