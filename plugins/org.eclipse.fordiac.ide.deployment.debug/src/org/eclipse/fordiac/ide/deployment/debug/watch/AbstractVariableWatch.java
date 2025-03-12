/*******************************************************************************
 * Copyright (c) 2024, 2025 Martin Erich Jobst
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

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.fordiac.ide.debug.value.IEvaluatorDebugValue;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugDevice;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugVariable;
import org.eclipse.fordiac.ide.deployment.debug.Messages;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;

public abstract class AbstractVariableWatch extends DeploymentDebugVariable implements IVariableWatch {

	private final Resource resource;
	private final ITypedElement element;
	private long aliveCount;
	private String invalidValue;
	private DeploymentDebugErrorValue cachedErrorValue;
	private boolean pinned;
	private Source source = Source.BREAKPOINT;

	protected AbstractVariableWatch(final Variable<?> variable, final ITypedElement element,
			final DeploymentDebugDevice debugTarget) throws EvaluatorException {
		this(variable, element, DeploymentDebugWatchUtils.getResource(element), debugTarget);
	}

	protected AbstractVariableWatch(final Variable<?> variable, final ITypedElement element, final Resource resource,
			final DeploymentDebugDevice debugTarget) throws EvaluatorException {
		super(variable, element.getQualifiedName(), debugTarget);
		this.resource = resource;
		this.element = element;
	}

	@Override
	public IEvaluatorDebugValue getValue() {
		if (hasError()) {
			return getErrorValue();
		}
		return super.getValue();
	}

	protected void updateValue(final String value) {
		aliveCount = getDebugTarget().getVariableUpdateCount();
		try {
			getInternalVariable().setValue(value, getDebugTarget().getTypeLibrary());
			invalidValue = null;
		} catch (final Exception e) {
			invalidValue = value;
		}
	}

	protected void updateValue(final Value value) {
		aliveCount = getDebugTarget().getVariableUpdateCount();
		try {
			getInternalVariable().setValue(value);
			invalidValue = null;
		} catch (final Exception e) {
			invalidValue = value.toString();
		}
	}

	@Override
	public boolean isAlive() {
		return aliveCount == getDebugTarget().getVariableUpdateCount();
	}

	@Override
	public boolean hasError() {
		return !isAlive() || invalidValue != null;
	}

	protected IEvaluatorDebugValue getErrorValue() {
		final String errorMessage = getErrorMessage();
		if (cachedErrorValue == null || !cachedErrorValue.getMessage().equals(errorMessage)) {
			cachedErrorValue = new DeploymentDebugErrorValue(errorMessage, getDebugTarget());
		}
		return cachedErrorValue;
	}

	protected String getErrorMessage() {
		if (!isAlive()) {
			if (!getDebugTarget().isAlive()) {
				return Messages.AbstractVariableWatch_Disconnected;
			}
			return Messages.AbstractVariableWatch_NoValue;
		}
		if (invalidValue != null) {
			return MessageFormat.format(Messages.AbstractVariableWatch_InvalidValue, invalidValue);
		}
		return Messages.AbstractVariableWatch_UnknownError;
	}

	@Override
	public boolean isPinned() {
		return pinned;
	}

	@Override
	public void setPinned(final boolean pinned) {
		this.pinned = pinned;
	}

	@Override
	public Source getSource() {
		return source;
	}

	@Override
	public void setSource(final Source source) {
		this.source = Objects.requireNonNull(source);
	}

	@Override
	public String getQualifiedName() {
		return getExpression();
	}

	@Override
	public Resource getResource() {
		return resource;
	}

	protected Resource getResourceChecked() throws DebugException {
		if (resource == null) {
			throw new DebugException(Status.error(
					MessageFormat.format(Messages.AbstractVariableWatch_ElementNotInResource, getQualifiedName())));
		}
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
