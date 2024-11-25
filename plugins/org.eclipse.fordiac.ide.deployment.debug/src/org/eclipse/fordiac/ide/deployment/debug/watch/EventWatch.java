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
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugDevice;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugElement;
import org.eclipse.fordiac.ide.deployment.debug.Messages;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.model.data.EventType;
import org.eclipse.fordiac.ide.model.eval.value.EventValue;
import org.eclipse.fordiac.ide.model.eval.variable.EventVariable;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

public class EventWatch extends AbstractRuntimeWatch implements IEventWatch {

	public EventWatch(final String name, final Event event, final DeploymentDebugDevice debugTarget) {
		super(new EventVariable(name, (EventType) event.getType()), event, debugTarget);
	}

	@Override
	public void triggerEvent() throws DebugException {
		try {
			getDeviceManagementExecutorService().triggerEvent(getResourceChecked(), getResourceRelativeName());
		} catch (final DeploymentException e) {
			throw new DebugException(
					Status.error(MessageFormat.format(Messages.EventWatch_TriggerEventError, getQualifiedName()), e));
		} finally {
			fireChangeEvent(DebugEvent.CONTENT);
		}
	}

	@Override
	public Event getWatchedElement() {
		return (Event) super.getWatchedElement();
	}

	@Override
	public EventValue getInternalValue() {
		return (EventValue) super.getInternalValue();
	}

	@Override
	public void setValue(final String expression) throws DebugException {
		throw DeploymentDebugElement.createUnsupportedOperationException();
	}

	@Override
	public void setValue(final IValue value) throws DebugException {
		throw DeploymentDebugElement.createUnsupportedOperationException();
	}

	@Override
	public boolean supportsValueModification() {
		return false;
	}

	@Override
	public boolean verifyValue(final String expression) {
		return false;
	}

	@Override
	public boolean verifyValue(final IValue value) {
		return false;
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (adapter == IInterfaceElement.class) {
			return adapter.cast(getWatchedElement());
		}
		return super.getAdapter(adapter);
	}
}
