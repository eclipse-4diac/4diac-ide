/**
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
 */
package org.eclipse.fordiac.ide.model.eval.fb;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory;
import org.eclipse.fordiac.ide.model.eval.value.ECStateValue;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;
import org.eclipse.fordiac.ide.model.eval.variable.ECStateVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;

public class BasicFBEvaluator extends BaseFBEvaluator<BasicFBType> {
	private final Map<ECTransition, Evaluator> transitionEvaluators;

	public BasicFBEvaluator(final BasicFBType type, final Variable<?> context, final Iterable<Variable<?>> variables,
			final Evaluator parent) {
		super(type, context, variables, parent);
		transitionEvaluators = type.getECC().getECTransition().stream().filter(BasicFBEvaluator::hasConditionExpression)
				.collect(Collectors.toUnmodifiableMap(Function.identity(), this::createECTransitionEvaluator));
	}

	@Override
	public void evaluate(final Event event) throws EvaluatorException, InterruptedException {
		for (ECTransition transition = evaluateTransitions(getState(),
				event); transition != null; transition = evaluateTransitions(getState(), null)) {
			evaluateState(transition.getDestination());
		}
	}

	private ECTransition evaluateTransitions(final ECState state, final Event event)
			throws EvaluatorException, InterruptedException {
		for (final ECTransition transition : state.getOutTransitions()) {
			if (matchesConditionEvent(transition, event) && matchesConditionExpression(transition)) {
				return transition;
			}
		}
		return null;
	}

	protected static boolean matchesConditionEvent(final ECTransition transition, final Event event) {
		return transition.getConditionEvent() == null || transition.getConditionEvent() == event;
	}

	protected boolean matchesConditionExpression(final ECTransition transition)
			throws EvaluatorException, InterruptedException {
		return !hasConditionExpression(transition)
				|| ValueOperations.asBoolean(transitionEvaluators.get(transition).evaluate());
	}

	private void evaluateState(final ECState state) throws EvaluatorException, InterruptedException {
		setState(state);
		trap(state);
		for (final ECAction action : state.getECAction()) {
			final Algorithm algorithm = action.getAlgorithm();
			if (algorithm != null) {
				getAlgorithmEvaluators().get(algorithm).evaluate();
			}
			final Event output = action.getOutput();
			if (output != null) {
				sendOutputEvent(output);
			}
			update(getVariables().values());
		}
	}

	public ECState getState() {
		return getECStateVariable().getValue().getState();
	}

	public void setState(final ECState state) {
		getECStateVariable().setValue(new ECStateValue(state));
	}

	protected ECStateVariable getECStateVariable() {
		return (ECStateVariable) getContext().getMembers().get(ECStateVariable.NAME);
	}

	public Map<ECTransition, Evaluator> getTransitionEvaluators() {
		return transitionEvaluators;
	}

	@Override
	public Set<String> getDependencies() {
		return Stream
				.concat(Stream.of(super.getDependencies()),
						transitionEvaluators.values().stream().map(Evaluator::getDependencies))
				.flatMap(Collection::stream).collect(Collectors.toUnmodifiableSet());
	}

	protected static boolean hasConditionExpression(final ECTransition transition) {
		return transition.getConditionExpression() != null && !transition.getConditionExpression().isBlank();
	}

	protected Evaluator createECTransitionEvaluator(final ECTransition transition) {
		return EvaluatorFactory.createEvaluator(transition,
				transition.eClass().getInstanceClass().asSubclass(ECTransition.class), getContext(),
				Collections.emptySet(), this);
	}
}
