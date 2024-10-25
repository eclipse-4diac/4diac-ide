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
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;

public abstract class AbstractVirtualWatch extends AbstractVariableWatch {

	private final SequencedSet<IVariableWatch> watches;

	protected AbstractVirtualWatch(final Variable<?> variable, final ITypedElement element,
			final SequencedSet<IVariableWatch> watches, final DeploymentDebugDevice debugTarget) {
		super(variable, element, debugTarget);
		this.watches = watches;
	}

	@Override
	public void addWatch() throws DebugException {
		for (final IWatch watch : watches) {
			watch.addWatch();
		}
	}

	@Override
	public void removeWatch() throws DebugException {
		for (final IWatch watch : watches) {
			watch.removeWatch();
		}
	}

	@Override
	public void updateValue(final DeploymentDebugWatchData watchData) {
		for (final IWatch watch : watches) {
			watch.updateValue(watchData);
		}
		updateValue(watches.stream().map(IVariableWatch::getInternalValue).toList());
	}

	protected abstract void updateValue(List<Value> values);

	public SequencedSet<IVariableWatch> getWatches() {
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
