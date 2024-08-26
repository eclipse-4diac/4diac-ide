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
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory;
import org.eclipse.fordiac.ide.model.eval.variable.FBVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.Method;

public abstract class BaseFBEvaluator<T extends BaseFBType> extends FBEvaluator<T> {
	private final Map<Algorithm, Evaluator> algorithmEvaluators;
	private final Map<FB, FBEvaluator<?>> internalFBEvaluators;

	protected BaseFBEvaluator(final T type, final Variable<?> context, final Iterable<Variable<?>> variables,
			final Evaluator parent) {
		super(type, context, variables, parent);
		algorithmEvaluators = type.getAlgorithm().stream()
				.collect(Collectors.toUnmodifiableMap(Function.identity(), this::createAlgorithmEvaluator));
		internalFBEvaluators = type.getInternalFbs().stream()
				.collect(Collectors.toUnmodifiableMap(Function.identity(), this::createInternalFBEvaluator));
	}

	@Override
	public void prepare() {
		algorithmEvaluators.values().forEach(Evaluator::prepare);
		internalFBEvaluators.values().forEach(Evaluator::prepare);
	}

	@Override
	public Set<String> getDependencies() {
		return Stream
				.concat(Stream.of(super.getDependencies()),
						Stream.concat(algorithmEvaluators.values().stream(),
								getType().getMethods().stream().map(this::createMethodEvaluator))
								.map(Evaluator::getDependencies))
				.flatMap(Collection::stream).collect(Collectors.toUnmodifiableSet());
	}

	@Override
	public Map<? extends ICallable, ? extends Evaluator> getChildren() {
		return Stream.of(algorithmEvaluators, internalFBEvaluators).map(Map::entrySet).flatMap(Collection::stream)
				.collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	public Map<Algorithm, Evaluator> getAlgorithmEvaluators() {
		return algorithmEvaluators;
	}

	public Map<FB, FBEvaluator<?>> getInternalFBEvaluators() {
		return internalFBEvaluators;
	}

	protected Evaluator createAlgorithmEvaluator(final Algorithm algorithm) {
		return EvaluatorFactory.createEvaluator(algorithm,
				algorithm.eClass().getInstanceClass().asSubclass(Algorithm.class), getContext(), Collections.emptySet(),
				this);
	}

	protected Evaluator createMethodEvaluator(final Method method) {
		return EvaluatorFactory.createEvaluator(method, method.eClass().getInstanceClass().asSubclass(Method.class),
				getContext(), Collections.emptySet(), this);
	}

	protected FBEvaluator<?> createInternalFBEvaluator(final FB internalFB) {
		return (FBEvaluator<?>) EvaluatorFactory.createEvaluator(internalFB.getType(),
				internalFB.getType().eClass().getInstanceClass().asSubclass(FBType.class), getContext(),
				((FBVariable) getContext().getMembers().get(internalFB.getName())).getMembers().values(), this);
	}
}
