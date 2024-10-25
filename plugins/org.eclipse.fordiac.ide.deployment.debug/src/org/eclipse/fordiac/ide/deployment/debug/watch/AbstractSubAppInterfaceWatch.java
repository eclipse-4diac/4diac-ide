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

import java.util.LinkedHashSet;
import java.util.SequencedSet;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugDevice;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

public abstract class AbstractSubAppInterfaceWatch extends AbstractVirtualWatch implements IInterfaceElementWatch {

	private final boolean input;

	protected AbstractSubAppInterfaceWatch(final Variable<?> variable, final IInterfaceElement element,
			final DeploymentDebugDevice debugTarget) {
		super(variable, element, createWatches(element, debugTarget), debugTarget);
		input = element.isIsInput();
	}

	protected static SequencedSet<IVariableWatch> createWatches(final IInterfaceElement element,
			final DeploymentDebugDevice debugTarget) {
		return DeploymentDebugWatchUtils.resolveSubappInterfaceConnections(element).distinct()
				.map(resolved -> createSubWatch(resolved, debugTarget))
				.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	protected static IVariableWatch createSubWatch(final IInterfaceElement element,
			final DeploymentDebugDevice debugTarget) throws EvaluatorException, UnsupportedOperationException {
		return (IVariableWatch) IWatch.watchFor(element.getQualifiedName(), element, debugTarget);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		return super.equals(obj);
	}

	public boolean isInput() {
		return input;
	}

	@Override
	public IInterfaceElement getWatchedElement() {
		return (IInterfaceElement) super.getWatchedElement();
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		if (adapter == IInterfaceElement.class) {
			return adapter.cast(getWatchedElement());
		}
		return super.getAdapter(adapter);
	}
}
