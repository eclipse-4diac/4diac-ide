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
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;

public class SimpleFBEvaluator extends BaseFBEvaluator<SimpleFBType> {
	public SimpleFBEvaluator(final SimpleFBType type, final Variable<?> context, final Iterable<Variable<?>> variables,
			final Evaluator parent) {
		super(type, context, variables, parent);
	}

	@Override
	public void evaluate(final Event event) throws EvaluatorException, InterruptedException {
		final Algorithm algorithm;
		if (event != null) {
			algorithm = getType().getAlgorithmNamed(event.getName());
		} else if (!getType().getAlgorithm().isEmpty()) {
			algorithm = getType().getAlgorithm().getFirst();
		} else {
			algorithm = null;
		}
		if (algorithm != null) {
			getAlgorithmEvaluators().get(algorithm).evaluate();
		}
		sendOutputEvents(getType().getInterfaceList().getEventOutputs());
		update(getVariables().values());
	}
}
