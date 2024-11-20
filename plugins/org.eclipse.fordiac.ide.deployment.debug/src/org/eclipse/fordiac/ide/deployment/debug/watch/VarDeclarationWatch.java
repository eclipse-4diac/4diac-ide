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

import java.text.MessageFormat;

import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugDevice;
import org.eclipse.fordiac.ide.deployment.debug.Messages;
import org.eclipse.fordiac.ide.deployment.devResponse.Data;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.value.AnyValue;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class VarDeclarationWatch extends AbstractRuntimeWatch implements IVarDeclarationWatch {

	private boolean forced;

	public VarDeclarationWatch(final String name, final VarDeclaration varDeclaration,
			final DeploymentDebugDevice debugTarget) throws EvaluatorException {
		super(VariableOperations.newVariable(name, VariableOperations.evaluateResultType(varDeclaration)),
				varDeclaration, debugTarget);
	}

	public VarDeclarationWatch(final String name, final VarDeclaration varDeclaration, final Resource resource,
			final String resourceRelativeName, final DeploymentDebugDevice debugTarget) throws EvaluatorException {
		super(VariableOperations.newVariable(name, VariableOperations.evaluateResultType(varDeclaration)),
				varDeclaration, resource, resourceRelativeName, debugTarget);
	}

	@Override
	public void setValue(final String expression) throws DebugException {
		super.setValue(expression);
		writeWatch();
	}

	@Override
	public void setValue(final IValue value) throws DebugException {
		super.setValue(value);
		writeWatch();
	}

	@Override
	public void setValue(final Value value) throws DebugException {
		getInternalVariable().setValue(value);
		fireContentChanged();
		writeWatch();
	}

	@Override
	protected void childValueChanged() throws DebugException {
		writeWatch();
	}

	protected void writeWatch() throws DebugException {
		try {
			getDeviceManagementExecutorService().writeFBParameter(getResourceChecked(), getResourceRelativeName(),
					getInternalVariable().toString(false));
		} catch (final DeploymentException e) {
			throw new DebugException(
					Status.error(MessageFormat.format(Messages.VarDeclarationWatch_WriteError, getQualifiedName()), e));
		}
	}

	@Override
	public void forceValue(final String value) throws DebugException {
		super.setValue(value);
		writeForce();
	}

	@Override
	public void forceValue(final Value value) throws DebugException {
		getInternalVariable().setValue(value);
		fireContentChanged();
		writeForce();
	}

	protected void writeForce() throws DebugException {
		try {
			getDeviceManagementExecutorService().forceValue(getResourceChecked(), getResourceRelativeName(),
					getInternalVariable().toString(false));
		} catch (final DeploymentException e) {
			throw new DebugException(
					Status.error(MessageFormat.format(Messages.VarDeclarationWatch_ForceError, getQualifiedName()), e));
		}
	}

	@Override
	public void clearForce() throws DebugException {
		try {
			getDeviceManagementExecutorService().clearForce(getResourceChecked(), getResourceRelativeName());
		} catch (final DeploymentException e) {
			throw new DebugException(Status
					.error(MessageFormat.format(Messages.VarDeclarationWatch_ClearForceError, getQualifiedName()), e));
		}
	}

	@Override
	public boolean isForced() {
		return forced;
	}

	protected void setForced(final boolean forced) {
		this.forced = forced;
	}

	@Override
	public void updateValue(final Data data) {
		super.updateValue(data);
		setForced(Boolean.parseBoolean(data.getForced()));
	}

	@Override
	public VarDeclaration getWatchedElement() {
		return (VarDeclaration) super.getWatchedElement();
	}

	@Override
	public AnyValue getInternalValue() {
		return (AnyValue) super.getInternalValue();
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		return super.equals(obj);
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (adapter == IInterfaceElement.class) {
			return adapter.cast(getWatchedElement());
		}
		return super.getAdapter(adapter);
	}
}
