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
import java.util.SequencedSet;

import org.eclipse.debug.core.DebugException;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugDevice;
import org.eclipse.fordiac.ide.deployment.debug.Messages;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;

public abstract class AbstractVirtualWatch extends AbstractVariableWatch {

	public record SubWatch(IVariableWatch watch, boolean negate) {

		public Value value() {
			return convertValue(watch.getInternalValue());
		}

		public Value convertValue(final Value value) {
			if (negate) {
				return ValueOperations.bitwiseNot(value);
			}
			return value;
		}
	}

	private final SequencedSet<SubWatch> watches;

	protected AbstractVirtualWatch(final Variable<?> variable, final ITypedElement element,
			final SequencedSet<SubWatch> watches, final DeploymentDebugDevice debugTarget) {
		super(variable, element, debugTarget);
		this.watches = watches;
	}

	@Override
	public void addWatch() throws DebugException {
		for (final SubWatch watch : watches) {
			watch.watch().addWatch();
		}
	}

	@Override
	public void removeWatch() throws DebugException {
		for (final SubWatch watch : watches) {
			watch.watch().removeWatch();
		}
	}

	@Override
	public void updateValue(final DeploymentDebugWatchData watchData) {
		if (watches.isEmpty()) {
			setError(Messages.AbstractVariableWatch_NoValue);
			return;
		}
		for (final SubWatch watch : watches) {
			watch.watch().updateValue(watchData);
			if (watch.watch().hasError()) {
				setError(Messages.AbstractVariableWatch_NoValue);
				return;
			}
		}
		updateValue(watches.stream().map(SubWatch::value).toList());
	}

	protected abstract void updateValue(List<Value> values);

	public SequencedSet<SubWatch> getWatches() {
		return watches;
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
