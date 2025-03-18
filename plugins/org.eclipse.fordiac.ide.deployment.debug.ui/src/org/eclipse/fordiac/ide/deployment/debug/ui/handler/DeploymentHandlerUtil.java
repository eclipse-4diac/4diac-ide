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
package org.eclipse.fordiac.ide.deployment.debug.ui.handler;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.fordiac.ide.deployment.debug.breakpoint.DeploymentWatchpoint;
import org.eclipse.fordiac.ide.deployment.debug.ui.breakpoint.DeploymentWatchpointUtil;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public final class DeploymentHandlerUtil {

	public static IInterfaceElement getInterfaceElement(final ExecutionEvent event) {
		return Adapters.adapt(HandlerUtil.getCurrentStructuredSelection(event).getFirstElement(),
				IInterfaceElement.class);
	}

	public static Stream<IInterfaceElement> getInterfaceElements(final ExecutionEvent event) {
		return getInterfaceElements(HandlerUtil.getCurrentStructuredSelection(event));
	}

	public static Stream<IInterfaceElement> getInterfaceElements(final IStructuredSelection selection) {
		return selection.stream().map(element -> Adapters.adapt(element, IInterfaceElement.class))
				.filter(Objects::nonNull);
	}

	public static Stream<DeploymentWatchpoint> getWatchpoints(final ExecutionEvent event) {
		return getWatchpoints(HandlerUtil.getCurrentStructuredSelection(event));
	}

	public static Stream<DeploymentWatchpoint> getWatchpoints(final IStructuredSelection selection) {
		return DeploymentHandlerUtil.getInterfaceElements(selection)
				.map(DeploymentWatchpointUtil::findExistingWatchpoint).flatMap(Optional::stream);
	}

	private DeploymentHandlerUtil() {
		throw new UnsupportedOperationException();
	}
}
