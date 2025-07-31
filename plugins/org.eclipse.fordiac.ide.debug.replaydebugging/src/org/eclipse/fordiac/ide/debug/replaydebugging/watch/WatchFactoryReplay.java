/*******************************************************************************
 * Copyright (c) 2025 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.replaydebugging.watch;

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
import org.eclipse.fordiac.ide.deployment.debug.watch.AbstractContainerWatch;
import org.eclipse.fordiac.ide.deployment.debug.watch.AdapterDeclarationWatch;
import org.eclipse.fordiac.ide.deployment.debug.watch.DeploymentDebugWatchUtils;
import org.eclipse.fordiac.ide.deployment.debug.watch.EventWatch;
import org.eclipse.fordiac.ide.deployment.debug.watch.FBNetworkElementValue;
import org.eclipse.fordiac.ide.deployment.debug.watch.IWatch;
import org.eclipse.fordiac.ide.deployment.debug.watch.SubAppEventWatch;
import org.eclipse.fordiac.ide.deployment.debug.watch.SubAppVarDeclarationWatch;
import org.eclipse.fordiac.ide.deployment.debug.watch.VarDeclarationWatch;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/**
 * The sole intention of this class is to wrap the original IWacht factory
 * methods with special Watch implementations which allow setting the error to a
 * specific value. The main idea behind this is to create the specialized
 * watches for replay debugging. Specially the FBNetworkElement for replay is
 * mainly copied from the original
 */
public class WatchFactoryReplay {

	public interface IWatchWithPublicError {
		void setError(final String message);

		void clearError();
	}

	public static IWatch watchFor(final String name, final INamedElement element,
			final DeploymentDebugDevice debugTarget) throws EvaluatorException, UnsupportedOperationException {
		return switch (element) {
		case final Event event when DeploymentDebugWatchUtils.isSubAppInterfaceElement(event) ->
			new SubAppEventWatchReplay(name, event, debugTarget);
		case final Event event -> new EventWatchReplay(name, event, debugTarget);
		case final VarDeclaration varDeclaration when DeploymentDebugWatchUtils
				.isSubAppInterfaceElement(varDeclaration) ->
			new SubAppVarDeclarationWatchReplay(name, varDeclaration, debugTarget);
		case final VarDeclaration varDeclaration -> new VarDeclarationWatchReplay(name, varDeclaration, debugTarget);
		case final AdapterDeclaration adapterDeclaration ->
			new AdapterDeclarationWatch(name, adapterDeclaration, debugTarget);
		case final FBNetworkElement networkElement ->
			new FBNetworkElementWatchReplay(name, networkElement, debugTarget);
		default -> throw new UnsupportedOperationException("Unsupported element: " + element.eClass().getName());
		};
	}

	static IWatch watchFor(final String name, final INamedElement element, final Resource resource,
			final String resourceRelativeName, final DeploymentDebugDevice debugTarget)
			throws EvaluatorException, UnsupportedOperationException {
		return switch (element) {
		case final IInterfaceElement interfaceElement when DeploymentDebugWatchUtils
				.isSubAppInterfaceElement(interfaceElement) ->
			throw new UnsupportedOperationException("Unsupported element: " + element.eClass().getName()); //$NON-NLS-1$
		case final Event event -> new EventWatchReplay(name, event, resource, resourceRelativeName, debugTarget);
		case final VarDeclaration varDeclaration ->
			new VarDeclarationWatchReplay(name, varDeclaration, resource, resourceRelativeName, debugTarget);
		case final AdapterDeclaration adapterDeclaration ->
			new AdapterDeclarationWatch(name, adapterDeclaration, resource, resourceRelativeName, debugTarget);
		case final FBNetworkElement networkElement ->
			new FBNetworkElementWatchReplay(name, networkElement, resource, resourceRelativeName, debugTarget);
		default -> throw new UnsupportedOperationException("Unsupported element: " + element.eClass().getName()); //$NON-NLS-1$
		};
	}

	private static class EventWatchReplay extends EventWatch implements IWatchWithPublicError {
		public EventWatchReplay(final String name, final Event event, final DeploymentDebugDevice debugTarget) {
			super(name, event, debugTarget);
		}

		public EventWatchReplay(final String name, final Event event, final Resource resource,
				final String resourceRelativeName, final DeploymentDebugDevice debugTarget) {
			super(name, event, resource, resourceRelativeName, debugTarget);
		}

		@Override
		public void setError(final String message) {
			super.setError(message);
		}

		@Override
		public void clearError() {
			super.clearError();
		}
	}

	private static class SubAppEventWatchReplay extends SubAppEventWatch implements IWatchWithPublicError {
		public SubAppEventWatchReplay(final String name, final Event event, final DeploymentDebugDevice debugTarget) {
			super(name, event, debugTarget);
		}

		@Override
		public void setError(final String message) {
			super.setError(message);
		}

		@Override
		public void clearError() {
			super.clearError();
		}
	}

	private static class VarDeclarationWatchReplay extends VarDeclarationWatch implements IWatchWithPublicError {
		public VarDeclarationWatchReplay(final String name, final VarDeclaration varDeclaration,
				final DeploymentDebugDevice debugTarget) {
			super(name, varDeclaration, debugTarget);
		}

		public VarDeclarationWatchReplay(final String name, final VarDeclaration varDeclaration,
				final Resource resource, final String resourceRelativeName, final DeploymentDebugDevice debugTarget) {
			super(name, varDeclaration, resource, resourceRelativeName, debugTarget);
		}

		@Override
		public void setError(final String message) {
			super.setError(message);
		}

		@Override
		public void clearError() {
			super.clearError();
		}
	}

	private static class SubAppVarDeclarationWatchReplay extends SubAppVarDeclarationWatch
			implements IWatchWithPublicError {
		public SubAppVarDeclarationWatchReplay(final String name, final VarDeclaration varDeclaration,
				final DeploymentDebugDevice debugTarget) {
			super(name, varDeclaration, debugTarget);
		}

		@Override
		public void setError(final String message) {
			super.setError(message);
		}

		@Override
		public void clearError() {
			super.clearError();
		}
	}

	// FBNetwork Classes

	public static class FBNetworkElementValueReplay extends FBNetworkElementValue implements IValue {
		private static final String QUALIFIED_NAME_DELIMITER = "."; //$NON-NLS-1$

		private final FBNetworkElement element;
		private final Resource resource;
		private final String resourceRelativeName;
		private final List<IWatch> watches;

		public FBNetworkElementValueReplay(final FBNetworkElement element, final DeploymentDebugDevice target) {
			this(element, element.getResource(),
					DeploymentDebugWatchUtils.getResourceRelativeName(element, element.getResource()), target);
		}

		public FBNetworkElementValueReplay(final FBNetworkElement element, final Resource resource,
				final String resourceRelativeName, final DeploymentDebugDevice target) {
			super(element, resource, resourceRelativeName, target);
			this.element = element;
			this.resource = resource;
			this.resourceRelativeName = resourceRelativeName;
			watches = getSubElementsReplay().map(this::createSubWatchReplay).toList();
		}

		private Stream<INamedElement> getSubElementsReplay() throws UnsupportedOperationException {
			return Stream.concat(getInterfaceSubElementsReplay(), getAdditionalSubElementsReplay())
					.sorted(Comparator.comparing(INamedElement::getName));
		}

		private Stream<INamedElement> getInterfaceSubElementsReplay() throws UnsupportedOperationException {
			final InterfaceList interfaceList = element.getInterface();
			return Stream.of(
					// include events
					interfaceList.getEventInputs(), interfaceList.getEventOutputs(),
					// include variables (except out-mapped InOut vars)
					interfaceList.getInputVars(), interfaceList.getOutputVars(), interfaceList.getInOutVars(),
					// include adapters
					interfaceList.getSockets(), interfaceList.getPlugs()).flatMap(Collection::stream);
		}

		private Stream<? extends INamedElement> getAdditionalSubElementsReplay() throws UnsupportedOperationException {
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

		private IWatch createSubWatchReplay(final INamedElement element)
				throws EvaluatorException, UnsupportedOperationException {
			if (EcoreUtil.getRootContainer(element) instanceof FBType) {
				return WatchFactoryReplay.watchFor(element.getName(), element, resource,
						resourceRelativeName + QUALIFIED_NAME_DELIMITER + element.getName(), getDebugTarget());
			}
			return WatchFactoryReplay.watchFor(element.getName(), element, getDebugTarget());
		}

		@Override
		public FBNetworkElement getElement() {
			return element;
		}

		@Override
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
	}

	public static class FBNetworkElementWatchReplay extends AbstractContainerWatch implements IWatchWithPublicError {

		final FBNetworkElementValueReplay value;

		public FBNetworkElementWatchReplay(final String name, final FBNetworkElement element,
				final DeploymentDebugDevice target) {
			super(name, element, target);
			value = new FBNetworkElementValueReplay(element, target);
		}

		public FBNetworkElementWatchReplay(final String name, final FBNetworkElement element, final Resource resource,
				final String resourceRelativeName, final DeploymentDebugDevice target) {
			super(name, element, target);
			value = new FBNetworkElementValueReplay(element, resource, resourceRelativeName, target);
		}

		@Override
		public FBNetworkElementValue getValue() {
			return value;
		}

		@Override
		public List<IWatch> getSubWatches() {
			return value.getWatches();
		}

		@Override
		public FBNetworkElement getWatchedElement() {
			return (FBNetworkElement) super.getWatchedElement();
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
			if (adapter == FBNetworkElement.class) {
				return adapter.cast(getWatchedElement());
			}
			return super.getAdapter(adapter);
		}

		@Override
		public void setError(final String message) {
			// nothing to do here. Each sub-watch should be set with the correct error
			// message
		}

		@Override
		public void clearError() {
			// nothing to do here. Clear the error on each sub-watch
		}
	}
}
