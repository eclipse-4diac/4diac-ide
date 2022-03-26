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
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IRegisterGroup;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

public class EvaluatorDebugStackFrame extends EvaluatorDebugElement implements IStackFrame {
	private final Evaluator evaluator;
	private final EvaluatorDebugThread thread;

	private final AtomicReference<Object> currentContext = new AtomicReference<>();

	public EvaluatorDebugStackFrame(final Evaluator evaluator, final EvaluatorDebugThread thread) {
		super(thread.getDebugTarget());
		this.evaluator = evaluator;
		this.thread = thread;
	}

	public Object getCurrentContext() {
		return this.currentContext.get();
	}

	public void setCurrentContext(final Object context) {
		this.currentContext.set(context);
	}

	public Evaluator getEvaluator() {
		return this.evaluator;
	}

	public EvaluatorDebugStackFrame getParent() {
		final Evaluator parent = this.evaluator.getParent();
		if (parent != null) {
			final CommonEvaluatorDebugger debugger = this.getDebugTarget().getDebugger();
			return debugger.getStackFrame(parent, thread);
		}
		return null;
	}

	public boolean isAncestorOf(final EvaluatorDebugStackFrame frame) {
		if (frame == this) {
			return true;
		} else if (frame == null) {
			return false;
		}
		return isAncestorOf(frame.getParent());
	}

	@Override
	public boolean canResume() {
		return this.thread.canResume();
	}

	@Override
	public boolean canSuspend() {
		return this.thread.canSuspend();
	}

	@Override
	public boolean isSuspended() {
		return this.thread.isSuspended();
	}

	@Override
	public void resume() throws DebugException {
		this.thread.resume();
	}

	@Override
	public void suspend() throws DebugException {
		this.thread.suspend();
	}

	@Override
	public boolean canStepInto() {
		return this.thread.canStepInto();
	}

	@Override
	public boolean canStepOver() {
		return this.thread.canStepOver();
	}

	@Override
	public boolean canStepReturn() {
		return this.thread.canStepReturn();
	}

	@Override
	public boolean isStepping() {
		return this.thread.isStepping();
	}

	@Override
	public void stepInto() throws DebugException {
		this.thread.request(this, DebugEvent.RESUME, DebugEvent.STEP_INTO);
	}

	@Override
	public void stepOver() throws DebugException {
		this.thread.request(this, DebugEvent.RESUME, DebugEvent.STEP_OVER);
	}

	@Override
	public void stepReturn() throws DebugException {
		this.thread.request(this, DebugEvent.RESUME, DebugEvent.STEP_RETURN);
	}

	@Override
	public boolean canTerminate() {
		return this.thread.canTerminate();
	}

	@Override
	public boolean isTerminated() {
		return this.thread.isTerminated();
	}

	@Override
	public void terminate() throws DebugException {
		this.thread.terminate();
	}

	@Override
	public IVariable[] getVariables() throws DebugException {
		final CommonEvaluatorDebugger debugger = this.getDebugTarget().getDebugger();
		final List<EvaluatorDebugVariable> variables = this.evaluator.getVariables().values().stream()
				.map(debugger::getVariable).sorted().collect(Collectors.toList());
		return variables.toArray(new IVariable[variables.size()]);
	}

	@Override
	public boolean hasVariables() throws DebugException {
		return !this.evaluator.getVariables().isEmpty();
	}

	@Override
	public int getLineNumber() throws DebugException {
		final Object context = this.getCurrentContext();
		if (context instanceof EObject) {
			final ICompositeNode node = NodeModelUtils.findActualNodeFor((EObject) context);
			if (node != null) {
				return node.getEndLine();
			}
		}
		return -1;
	}

	@Override
	public int getCharStart() throws DebugException {
		return -1;
	}

	@Override
	public int getCharEnd() throws DebugException {
		return -1;
	}

	@Override
	public String getName() throws DebugException {
		return this.evaluator.getName();
	}

	@Override
	public boolean hasRegisterGroups() throws DebugException {
		return false;
	}

	@Override
	public IRegisterGroup[] getRegisterGroups() throws DebugException {
		return new IRegisterGroup[0];
	}

	@Override
	public EvaluatorDebugThread getThread() {
		return this.thread;
	}

	@Override
	public EvaluatorDebugTarget getDebugTarget() {
		return (EvaluatorDebugTarget) super.getDebugTarget();
	}
}
