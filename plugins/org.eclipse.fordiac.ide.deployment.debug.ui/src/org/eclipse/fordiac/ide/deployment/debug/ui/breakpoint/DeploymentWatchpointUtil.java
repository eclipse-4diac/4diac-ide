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
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.fordiac.ide.deployment.debug.breakpoint.DeploymentWatchpoint;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public final class DeploymentWatchpointUtil {

	public static Optional<DeploymentWatchpoint> findExistingWatchpoint(final IResource resource,
			final INamedElement element) {
		return findExistingWatchpoint(resource, element.getQualifiedName());
	}

	public static Optional<DeploymentWatchpoint> findExistingWatchpoint(final IResource resource,
			final String qualifiedName) {
		final IBreakpointManager manager = DebugPlugin.getDefault().getBreakpointManager();
		final IBreakpoint[] breakpoints = manager.getBreakpoints(DeploymentWatchpoint.DEBUG_MODEL);
		for (final IBreakpoint breakpoint : breakpoints) {
			if (breakpoint instanceof final DeploymentWatchpoint watchpoint
					&& watchpoint.getMarker().getResource().equals(resource)
					&& watchpoint.getLocation().equals(qualifiedName)) {
				return Optional.of(watchpoint);
			}
		}
		return Optional.empty();
	}

	private DeploymentWatchpointUtil() {
		throw new UnsupportedOperationException();
	}
}
