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
package org.eclipse.fordiac.ide.deployment.debug.ui.breakpoint;

import java.util.Optional;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.AdapterTypes;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.deployment.debug.breakpoint.DeploymentWatchpoint;
import org.eclipse.fordiac.ide.deployment.debug.watch.IWatch;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;

@AdapterTypes(adaptableClass = { INamedElement.class, EditPart.class, IEditorPart.class,
		IWatch.class }, adapterNames = { IToggleBreakpointsTarget.class })
public class DeploymentToggleBreakpointsTargetExtension implements IToggleBreakpointsTarget, IAdapterFactory {

	@Override
	public void toggleLineBreakpoints(final IWorkbenchPart part, final ISelection selection) throws CoreException {
		// unused
	}

	@Override
	public boolean canToggleLineBreakpoints(final IWorkbenchPart part, final ISelection selection) {
		return false;
	}

	@Override
	public void toggleMethodBreakpoints(final IWorkbenchPart part, final ISelection selection) throws CoreException {
		// unused
	}

	@Override
	public boolean canToggleMethodBreakpoints(final IWorkbenchPart part, final ISelection selection) {
		return false;
	}

	@Override
	public void toggleWatchpoints(final IWorkbenchPart part, final ISelection selection) throws CoreException {
		if (selection instanceof final IStructuredSelection structuredSelection) {
			for (final Object selectedElement : structuredSelection) {
				final Optional<INamedElement> element = getElementFromSelection(selectedElement);
				if (element.isPresent()) {
					toggleWatchpoint(element.get());
				}
			}
		}
	}

	protected static void toggleWatchpoint(final INamedElement element) throws CoreException {
		if (EcoreUtil.getRootContainer(element) instanceof final AutomationSystem system) {
			toggleWatchpoint(system.getTypeEntry().getFile(), element);
		}
	}

	protected static void toggleWatchpoint(final IResource resource, final INamedElement element) throws CoreException {
		final Optional<DeploymentWatchpoint> existingBreakpoint = DeploymentWatchpointUtil
				.findExistingWatchpoint(resource, element);
		if (existingBreakpoint.isPresent()) {
			existingBreakpoint.get().delete();
		} else {
			final DeploymentWatchpoint breakpoint = new DeploymentWatchpoint(resource, element);
			DebugPlugin.getDefault().getBreakpointManager().addBreakpoint(breakpoint);
		}
	}

	@Override
	public boolean canToggleWatchpoints(final IWorkbenchPart part, final ISelection selection) {
		return isApplicable(selection);
	}

	@Override
	public <T> T getAdapter(final Object adaptableObject, final Class<T> adapterType) {
		if (adaptableObject instanceof final IEditorPart editorPart
				&& editorPart.getAdapter(AutomationSystem.class) != null
				&& adapterType == IToggleBreakpointsTarget.class) {
			return adapterType.cast(this);
		}
		if (getElementFromSelection(adaptableObject).isPresent() && adapterType == IToggleBreakpointsTarget.class) {
			return adapterType.cast(this);
		}
		return null;
	}

	private static boolean isApplicable(final ISelection selection) {
		return selection instanceof final IStructuredSelection structuredSelection && structuredSelection.stream()
				.map(DeploymentToggleBreakpointsTargetExtension::getElementFromSelection).anyMatch(Optional::isPresent);
	}

	private static Optional<INamedElement> getElementFromSelection(final Object selection) {
		return getInterfaceElementFromSelection(selection).or(() -> getFBNetworkElementFromSelection(selection))
				.filter(element -> EcoreUtil.getRootContainer(element) instanceof AutomationSystem);
	}

	private static Optional<INamedElement> getInterfaceElementFromSelection(final Object selection) {
		return Optional.ofNullable(Adapters.adapt(selection, IInterfaceElement.class));
	}

	private static Optional<INamedElement> getFBNetworkElementFromSelection(final Object selection) {
		return Optional.ofNullable(Adapters.adapt(selection, FBNetworkElement.class));
	}
}
