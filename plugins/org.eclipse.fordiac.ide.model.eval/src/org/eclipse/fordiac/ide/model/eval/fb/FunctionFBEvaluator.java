/**
 * Copyright (c) 2023, 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.model.eval.fb;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionBody;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;

public class FunctionFBEvaluator extends FBEvaluator<FunctionFBType> {
	private final Evaluator functionEvaluator;

	public FunctionFBEvaluator(final FunctionFBType type, final Variable<?> context,
			final Iterable<Variable<?>> variables, final Evaluator parent) {
		super(type, context, variables, parent);
		functionEvaluator = createFunctionEvaluator(type.getBody());
	}

	@Override
	public void prepare() {
		functionEvaluator.prepare();
	}

	@Override
	public Value evaluate() throws EvaluatorException, InterruptedException {
		if (getEventQueue() == null) {
			final Value result = functionEvaluator.evaluate();
			update(getVariables().values());
			return result;
		}
		return super.evaluate();
	}

	@Override
	public void evaluate(final Event event) throws EvaluatorException, InterruptedException {
		functionEvaluator.evaluate();
		sendOutputEvents(getType().getInterfaceList().getEventOutputs());
		update(getVariables().values());
	}

	@Override
	public Set<String> getDependencies() {
		return Stream.of(super.getDependencies(), functionEvaluator.getDependencies()).flatMap(Collection::stream)
				.collect(Collectors.toUnmodifiableSet());
	}

	@Override
	public Map<? extends ICallable, ? extends Evaluator> getChildren() {
		return Collections.emptyMap();
	}

	public Evaluator getFunctionEvaluator() {
		return functionEvaluator;
	}

	protected Evaluator createFunctionEvaluator(final FunctionBody body) {
		return EvaluatorFactory.createEvaluator(body, body.eClass().getInstanceClass().asSubclass(FunctionBody.class),
				getContext(), getVariables().values(), this);
	}
}
