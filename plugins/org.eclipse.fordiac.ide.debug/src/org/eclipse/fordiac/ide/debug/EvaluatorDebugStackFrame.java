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

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.model.IRegisterGroup;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;

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
		return currentContext.get();
	}

	public void setCurrentContext(final Object context) {
		currentContext.set(context);
	}

	public Evaluator getEvaluator() {
		return evaluator;
	}

	public EvaluatorDebugStackFrame getParent() {
		final Evaluator parent = evaluator.getParent();
		if (parent != null) {
			final CommonEvaluatorDebugger debugger = getDebugTarget().getDebugger();
			return debugger.getStackFrame(parent, thread);
		}
		return null;
	}

	public boolean isAncestorOf(final EvaluatorDebugStackFrame frame) {
		if (frame == this) {
			return true;
		}
		if (frame == null) {
			return false;
		}
		return isAncestorOf(frame.getParent());
	}

	@Override
	public boolean canResume() {
		return thread.canResume();
	}

	@Override
	public boolean canSuspend() {
		return thread.canSuspend();
	}

	@Override
	public boolean isSuspended() {
		return thread.isSuspended();
	}

	@Override
	public void resume() {
		thread.resume();
	}

	@Override
	public void suspend() {
		thread.suspend();
	}

	@Override
	public boolean canStepInto() {
		return thread.canStepInto();
	}

	@Override
	public boolean canStepOver() {
		return thread.canStepOver();
	}

	@Override
	public boolean canStepReturn() {
		return thread.canStepReturn();
	}

	@Override
	public boolean isStepping() {
		return thread.isStepping();
	}

	@Override
	public void stepInto() {
		thread.request(this, DebugEvent.RESUME, DebugEvent.STEP_INTO);
	}

	@Override
	public void stepOver() {
		thread.request(this, DebugEvent.RESUME, DebugEvent.STEP_OVER);
	}

	@Override
	public void stepReturn() {
		thread.request(this, DebugEvent.RESUME, DebugEvent.STEP_RETURN);
	}

	@Override
	public boolean canTerminate() {
		return thread.canTerminate();
	}

	@Override
	public boolean isTerminated() {
		return thread.isTerminated();
	}

	@Override
	public void terminate() {
		thread.terminate();
	}

	public EvaluatorDebugVariable getVariable(final String name) {
		final Variable<?> variable = evaluator.getVariables().get(name);
		if (variable != null) {
			final CommonEvaluatorDebugger debugger = getDebugTarget().getDebugger();
			return debugger.getVariable(variable);
		}
		return null;
	}

	@Override
	public IVariable[] getVariables() {
		final CommonEvaluatorDebugger debugger = getDebugTarget().getDebugger();
		final var debugVars = evaluator.getVariables().values().stream().map(debugger::getVariable).toList();
		// split all vars into this vars and other vars
		final var thisVars = debugVars.stream().filter(variable -> Evaluator.CONTEXT_NAME.equals(variable.getName()));
		final var otherVars = debugVars.stream().filter(variable -> !Evaluator.CONTEXT_NAME.equals(variable.getName()))
				.sorted();
		// Concat in a way that this is first, then others
		return Stream.concat(thisVars, otherVars).toArray(IVariable[]::new);
	}

	@Override
	public boolean hasVariables() {
		return !evaluator.getVariables().isEmpty();
	}

	@Override
	public int getLineNumber() {
		final CommonEvaluatorDebugger debugger = getDebugTarget().getDebugger();
		final Object context = getCurrentContext();
		return debugger.getLineNumber(context);
	}

	@Override
	public int getCharStart() {
		return -1;
	}

	@Override
	public int getCharEnd() {
		return -1;
	}

	@Override
	public String getName() {
		return evaluator.getName();
	}

	@Override
	public boolean hasRegisterGroups() {
		return false;
	}

	@Override
	public IRegisterGroup[] getRegisterGroups() {
		return new IRegisterGroup[0];
	}

	@Override
	public EvaluatorDebugThread getThread() {
		return thread;
	}

	@Override
	public EvaluatorDebugTarget getDebugTarget() {
		return (EvaluatorDebugTarget) super.getDebugTarget();
	}
}
