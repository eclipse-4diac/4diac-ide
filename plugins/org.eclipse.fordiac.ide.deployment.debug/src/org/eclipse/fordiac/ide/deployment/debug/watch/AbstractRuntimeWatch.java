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
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugDevice;
import org.eclipse.fordiac.ide.deployment.debug.Messages;
import org.eclipse.fordiac.ide.deployment.devResponse.Data;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementExecutorService;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;

public abstract class AbstractRuntimeWatch extends AbstractVariableWatch {

	private final String resourceRelativeName;

	protected AbstractRuntimeWatch(final Variable<?> variable, final ITypedElement element,
			final DeploymentDebugDevice debugTarget) throws EvaluatorException {
		super(variable, element, debugTarget);
		resourceRelativeName = DeploymentDebugWatchUtils.getResourceRelativeName(element, getResource());
	}

	@Override
	public void addWatch() throws DebugException {
		try {
			getDeviceManagementExecutorService().addWatch(getResource(), getResourceRelativeName());
		} catch (final DeploymentException e) {
			throw new DebugException(Status.error(
					MessageFormat.format(Messages.AbstractRuntimeWatch_AddWatch, getQualifiedName()),
					e));
		}
	}

	@Override
	public void removeWatch() throws DebugException {
		try {
			getDeviceManagementExecutorService().removeWatch(getResource(), getResourceRelativeName());
		} catch (final DeploymentException e) {
			throw new DebugException(Status.error(MessageFormat
					.format(Messages.AbstractRuntimeWatch_RemoveWatch, getQualifiedName()), e));
		}
	}

	@Override
	public final void updateValue(final DeploymentDebugWatchData watchData) {
		final Data data = watchData.getLastData(getResource(), getResourceRelativeName());
		if (data != null) {
			updateValue(data);
		}
	}

	protected void updateValue(final Data data) {
		updateValue(data.getValue());
	}

	protected String getResourceRelativeName() {
		return resourceRelativeName;
	}

	protected IDeviceManagementExecutorService getDeviceManagementExecutorService() {
		return getDebugTarget().getDeviceManagementExecutorService();
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		return super.equals(obj);
	}
}
