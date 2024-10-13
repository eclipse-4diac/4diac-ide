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
package org.eclipse.fordiac.ide.deployment.debug.watch;

import java.util.Objects;

import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugDevice;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugVariable;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public abstract class AbstractVariableWatch extends DeploymentDebugVariable implements IVariableWatch {

	private final Resource resource;
	private final ITypedElement element;
	private long aliveCount;

	protected AbstractVariableWatch(final Variable<?> variable, final ITypedElement element,
			final DeploymentDebugDevice debugTarget) throws EvaluatorException {
		super(variable, element.getQualifiedName(), debugTarget);
		resource = Objects.requireNonNull(DeploymentDebugWatchUtils.getResource(element), "element not in a resource"); //$NON-NLS-1$
		this.element = element;
	}

	protected void updateValue(final String value) {
		try {
			getInternalVariable().setValue(value);
			aliveCount = getDebugTarget().getVariableUpdateCount();
		} catch (final Exception e) {
			FordiacLogHelper.logWarning("Invalid watch value for " + getQualifiedName() + ": " + value, e); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	protected void updateValue(final Value value) {
		try {
			getInternalVariable().setValue(value);
			aliveCount = getDebugTarget().getVariableUpdateCount();
		} catch (final Exception e) {
			FordiacLogHelper.logWarning("Invalid watch value for " + getQualifiedName() + ": " + value, e); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	@Override
	public boolean isAlive() {
		return aliveCount == getDebugTarget().getVariableUpdateCount();
	}

	@Override
	public String getQualifiedName() {
		return getExpression();
	}

	@Override
	public Resource getResource() {
		return resource;
	}

	@Override
	public ITypedElement getWatchedElement() {
		return element;
	}

	@Override
	public Value getInternalValue() {
		return getInternalVariable().getValue();
	}

	@Override
	public DeploymentDebugDevice getDebugTarget() {
		return (DeploymentDebugDevice) super.getDebugTarget();
	}

	@Override
	public boolean hasValueChanged() {
		return false; // prevents annoying flickering in variables view
	}

	@Override
	public int hashCode() {
		return getQualifiedName().hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final AbstractVariableWatch other = (AbstractVariableWatch) obj;
		return Objects.equals(getQualifiedName(), other.getQualifiedName());
	}
}
