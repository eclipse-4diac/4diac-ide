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

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugDevice;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugElement;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public class BlockFBNetworkElementValue extends DeploymentDebugElement implements IValue {
	private static final String QUALIFIED_NAME_DELIMITER = "."; //$NON-NLS-1$

	private final BlockFBNetworkElement element;
	private final Resource resource;
	private final String resourceRelativeName;
	private final List<IWatch> watches;

	public BlockFBNetworkElementValue(final BlockFBNetworkElement element, final DeploymentDebugDevice target) {
		this(element, element.getResource(),
				DeploymentDebugWatchUtils.getResourceRelativeName(element, element.getResource()), target);
	}

	public BlockFBNetworkElementValue(final BlockFBNetworkElement element, final Resource resource,
			final String resourceRelativeName, final DeploymentDebugDevice target) {
		super(target);
		this.element = element;
		this.resource = resource;
		this.resourceRelativeName = resourceRelativeName;
		watches = getSubElements().map(this::createSubWatch).toList();
	}

	private Stream<INamedElement> getSubElements() throws UnsupportedOperationException {
		return Stream.concat(getInterfaceSubElements(), getAdditionalSubElements())
				.sorted(Comparator.comparing(INamedElement::getName));
	}

	private Stream<INamedElement> getInterfaceSubElements() throws UnsupportedOperationException {
		final InterfaceList interfaceList = element.getInterface();
		return Stream.of(
				// include events
				interfaceList.getEventInputs(), interfaceList.getEventOutputs(),
				// include variables (except out-mapped InOut vars)
				interfaceList.getInputVars(), interfaceList.getOutputVars(), interfaceList.getInOutVars(),
				// include adapters
				interfaceList.getSockets(), interfaceList.getPlugs()).flatMap(Collection::stream);
	}

	private Stream<? extends INamedElement> getAdditionalSubElements() throws UnsupportedOperationException {
		return switch (element) {
		case final FB fb when fb.getType() instanceof final BaseFBType baseFBType ->
			Stream.concat(baseFBType.getInternalVars().stream(), baseFBType.getInternalFbs().stream());
		case final SubApp subapp -> subapp.loadSubAppNetwork().getBlockFBNetworkElements();
		case final CFBInstance cfbInstance ->
			cfbInstance.loadCFBNetwork().getBlockFBNetworkElements().filter(Predicate.not(AdapterFB.class::isInstance));
		default -> Stream.empty();
		};
	}

	private IWatch createSubWatch(final INamedElement element)
			throws EvaluatorException, UnsupportedOperationException {
		if (EcoreUtil.getRootContainer(element) instanceof FBType) {
			return IWatch.watchFor(element.getName(), element, resource,
					resourceRelativeName + QUALIFIED_NAME_DELIMITER + element.getName(), getDebugTarget());
		}
		return IWatch.watchFor(element.getName(), element, getDebugTarget());
	}

	public BlockFBNetworkElement getElement() {
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
