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

import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugDevice;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugElement;
import org.eclipse.fordiac.ide.model.data.EventType;
import org.eclipse.fordiac.ide.model.eval.value.EventValue;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.variable.EventVariable;
import org.eclipse.fordiac.ide.model.libraryElement.Event;

public class SubAppEventWatch extends AbstractSubAppInterfaceWatch implements IEventWatch {

	public SubAppEventWatch(final String name, final Event event, final DeploymentDebugDevice debugTarget) {
		super(new EventVariable(name, (EventType) event.getType()), event, debugTarget);
	}

	@Override
	public void triggerEvent() throws DebugException {
		for (final IVariableWatch watch : getWatches()) {
			((IEventWatch) watch).triggerEvent();
		}
	}

	@Override
	protected void updateValue(final List<Value> values) {
		if (isInput()) {
			// all counts must be equal for input events (fan-out)
			if (values.stream().distinct().count() == 1) {
				updateValue(values.getFirst());
			}
		} else {
			// calculate sum of all counts for output events (fan-in)
			updateValue(new EventValue(getInternalValue().getType(),
					values.stream().map(EventValue.class::cast).mapToLong(EventValue::getCount).sum()));
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
}
