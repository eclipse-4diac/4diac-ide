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

import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleECAction;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleECState;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;

public class SimpleFBEvaluator extends BaseFBEvaluator<SimpleFBType> {
	public SimpleFBEvaluator(final SimpleFBType type, final Variable<?> context, final Iterable<Variable<?>> variables,
			final Evaluator parent) {
		super(type, context, variables, parent);
	}

	@Override
	public void evaluate(final Event event) throws EvaluatorException, InterruptedException {
		evaluateState(getType().getSimpleECStates().stream().filter(state -> state.getInputEvent().equals(event))
				.findAny().orElseThrow());
	}

	private void evaluateState(final SimpleECState state) throws EvaluatorException, InterruptedException {
		for (final SimpleECAction action : state.getSimpleECActions()) {
			final Algorithm algorithm = getType().getAlgorithmNamed(action.getAlgorithm());
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
}
