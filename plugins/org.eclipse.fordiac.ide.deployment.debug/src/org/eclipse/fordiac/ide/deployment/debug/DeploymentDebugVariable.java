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
package org.eclipse.fordiac.ide.deployment.debug;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;

public class DeploymentDebugVariable extends EvaluatorDebugVariable {

	public DeploymentDebugVariable(final Variable<?> variable, final String expression,
			final IDeploymentDebugTarget debugTarget) {
		super(variable, expression, debugTarget);
	}

	protected DeploymentDebugVariable(final Variable<?> variable, final String expression,
			final EvaluatorDebugVariable parent, final IDeploymentDebugTarget debugTarget) {
		super(variable, expression, parent, debugTarget);
	}

	@Override
	public DeploymentDebugVariable createSubVariable(final Variable<?> variable, final String expression) {
		return new DeploymentDebugVariable(variable, expression, this, getDebugTarget());
	}

	@Override
	public void setValue(final String expression) throws DebugException {
		super.setValue(expression);
		notifyChildValueChanged();
	}

	@Override
	public void setValue(final IValue value) throws DebugException {
		super.setValue(value);
		notifyChildValueChanged();
	}

	protected void childValueChanged() throws DebugException {
		notifyChildValueChanged();
	}

	protected void notifyChildValueChanged() throws DebugException {
		if (getParent() instanceof final DeploymentDebugVariable parent) {
			parent.childValueChanged();
		}
	}

	@Override
	public String getModelIdentifier() {
		return DeploymentDebugElement.MODEL_IDENTIFIER;
	}

	@Override
	public IDeploymentDebugTarget getDebugTarget() {
		return (IDeploymentDebugTarget) super.getDebugTarget();
	}
}
