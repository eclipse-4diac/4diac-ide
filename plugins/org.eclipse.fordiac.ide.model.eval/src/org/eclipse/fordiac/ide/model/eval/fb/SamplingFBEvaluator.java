/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.eval.fb;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;

public class SamplingFBEvaluator extends FBEvaluator<FBType> {

	private final FBEvaluator<?> delegate;

	public SamplingFBEvaluator(final FBType type, final Variable<?> context, final Iterable<Variable<?>> variables,
			final Evaluator parent) {
		super(type, context, variables, parent);
		delegate = (FBEvaluator<?>) EvaluatorFactory.createEvaluator(type,
				type.eClass().getInstanceClass().asSubclass(FBType.class), getContext(), Collections.emptySet(), this);
		delegate.getContext().setValue(getContext().getValue()); // assign initial values
		delegate.setEventQueue(new SamplingEventQueue());
	}

	@Override
	public void prepare() {
		delegate.prepare();
	}

	@Override
	public void evaluate(final Event event) throws EvaluatorException, InterruptedException {
		getVariables(event).forEach(this::readData);
		delegate.evaluate(event);
	}

	@Override
	protected boolean sendOutputEvent(final Event event) {
		final List<Variable<?>> variables = getVariables(event);
		variables.forEach(this::writeData);
		update(variables);
		return super.sendOutputEvent(event);
	}

	private void readData(final Variable<?> variable) {
		final Variable<?> inner = getDelegateVariable(variable);
		if (inner != null) {
			inner.setValue(variable.getValue());
		}
	}

	private void writeData(final Variable<?> variable) {
		final Variable<?> inner = getDelegateVariable(variable);
		if (inner != null) {
			variable.setValue(inner.getValue());
		}
	}

	private List<Variable<?>> getVariables(final Event event) {
		return event.getWith().stream().map(With::getVariables).map(VarDeclaration::getName)
				.<Variable<?>>map(getVariables()::get).filter(Objects::nonNull).toList();
	}

	private Variable<?> getDelegateVariable(final Variable<?> variable) {
		return delegate.getContext().getValue().get(variable.getName());
	}

	@Override
	public Set<String> getDependencies() {
		return delegate.getDependencies();
	}

	@Override
	public void reset(final Iterable<Variable<?>> variables) {
		super.reset(variables);
		delegate.reset(Collections.emptySet());
	}

	@Override
	public Map<? extends ICallable, ? extends Evaluator> getChildren() {
		return Map.of(getType(), delegate);
	}

	public FBEvaluator<?> getDelegate() {
		return delegate;
	}

	private class SamplingEventQueue implements FBEvaluatorEventQueue {

		@Override
		public Event receiveInputEvent() throws InterruptedException {
			return null;
		}

		@Override
		public boolean sendOutputEvent(final Event event) {
			return SamplingFBEvaluator.this.sendOutputEvent(event);
		}
	}
}
