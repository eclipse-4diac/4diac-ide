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

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugDevice;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugElement;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public class FBNetworkElementValue extends DeploymentDebugElement implements IValue {

	private final FBNetworkElement element;
	private final List<IWatch> watches;

	public FBNetworkElementValue(final FBNetworkElement element, final DeploymentDebugDevice target) {
		super(target);
		this.element = element;
		watches = getSubElements().map(this::createSubWatch).toList();
	}

	private Stream<INamedElement> getSubElements() throws UnsupportedOperationException {
		return Stream.concat(element.getInterface().getAllInterfaceElements().stream(), getAdditionalSubElements())
				.sorted(Comparator.comparing(INamedElement::getName));
	}

	private Stream<? extends INamedElement> getAdditionalSubElements() throws UnsupportedOperationException {
		return switch (element) {
		case final FB fb when fb.getType() instanceof final BaseFBType baseFBType ->
			Stream.concat(baseFBType.getInternalVars().stream(), baseFBType.getInternalFbs().stream());
		case final Group group -> group.getGroupElements().stream();
		case final SubApp subapp -> subapp.loadSubAppNetwork().getNetworkElements().stream();
		case final CFBInstance cfbInstance -> cfbInstance.loadCFBNetwork().getNetworkElements().stream()
				.filter(Predicate.not(AdapterFB.class::isInstance));
		default -> Stream.empty();
		};
	}

	private IWatch createSubWatch(final INamedElement element)
			throws EvaluatorException, UnsupportedOperationException {
		return IWatch.watchFor(element.getName(), element, getDebugTarget());
	}

	public FBNetworkElement getElement() {
		return element;
	}

	public List<IWatch> getWatches() {
		return watches;
	}

	@Override
	public String getReferenceTypeName() throws DebugException {
		return element.getTypeName();
	}

	@Override
	public String getValueString() throws DebugException {
		return ""; //$NON-NLS-1$
	}

	@Override
	public boolean isAllocated() throws DebugException {
		return true;
	}

	@Override
	public IVariable[] getVariables() throws DebugException {
		return watches.toArray(IVariable[]::new);
	}

	@Override
	public boolean hasVariables() throws DebugException {
		return !watches.isEmpty();
	}

	@Override
	public DeploymentDebugDevice getDebugTarget() {
		return (DeploymentDebugDevice) super.getDebugTarget();
	}
}
