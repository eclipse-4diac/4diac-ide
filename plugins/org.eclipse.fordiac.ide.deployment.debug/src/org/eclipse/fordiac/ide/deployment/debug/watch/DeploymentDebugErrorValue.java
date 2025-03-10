/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.deployment.debug.watch;

import java.util.Objects;

import org.eclipse.debug.core.model.IVariable;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugVariable;
import org.eclipse.fordiac.ide.debug.value.IEvaluatorDebugValue;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugElement;
import org.eclipse.fordiac.ide.deployment.debug.IDeploymentDebugTarget;
import org.eclipse.fordiac.ide.deployment.debug.Messages;

public class DeploymentDebugErrorValue extends DeploymentDebugElement implements IEvaluatorDebugValue {

	private final String message;

	public DeploymentDebugErrorValue(final String message, final IDeploymentDebugTarget target) {
		super(target);
		this.message = Objects.requireNonNull(message);
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String getReferenceTypeName() {
		return Messages.DeploymentDebugErrorValue_Error;
	}

	@Override
	public String getValueString() {
		return message;
	}

	@Override
	public boolean isAllocated() {
		return false;
	}

	@Override
	public IVariable[] getVariables() {
		return new IVariable[0];
	}

	@Override
	public boolean hasVariables() {
		return false;
	}

	@Override
	public EvaluatorDebugVariable getVariable(final String name) {
		return null;
	}

	@Override
	public int hashCode() {
		return message.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final DeploymentDebugErrorValue other = (DeploymentDebugErrorValue) obj;
		return getDebugTarget() == other.getDebugTarget() && Objects.equals(message, other.message);
	}

	@Override
	public String toString() {
		return String.format("%s [message=%s]", getClass().getName(), message); //$NON-NLS-1$
	}
}
