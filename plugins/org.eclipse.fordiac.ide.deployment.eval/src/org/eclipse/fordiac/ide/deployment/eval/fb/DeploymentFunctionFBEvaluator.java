/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.deployment.eval.fb;

import org.eclipse.fordiac.ide.deployment.devResponse.Port;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

public class DeploymentFunctionFBEvaluator extends DeploymentFBEvaluator<FunctionFBType> {

	public DeploymentFunctionFBEvaluator(final FunctionFBType type, final Variable<?> context,
			final Iterable<Variable<?>> variables, final Evaluator parent) {
		super(type, context, variables, parent);
	}

	@Override
	public Value evaluate() throws EvaluatorException, InterruptedException {
		if (getEventQueue() == null && !getType().getInterfaceList().getEventInputs().isEmpty()) {
			try {
				evaluate(getType().getInterfaceList().getEventInputs().get(0));
			} finally {
				cleanup();
			}
			final Variable<?> returnVariable = getVariables().get(""); //$NON-NLS-1$
			return returnVariable != null ? returnVariable.getValue() : null;
		}
		return super.evaluate();
	}

	@Override
	protected void updateWatch(final Port port) {
		final IInterfaceElement element = getType().getInterfaceList().getInterfaceElement(port.getName());
		if (element != null && element.isIsInput()) {
			return; // ignore inputs
		}
		super.updateWatch(port);
	}
}
