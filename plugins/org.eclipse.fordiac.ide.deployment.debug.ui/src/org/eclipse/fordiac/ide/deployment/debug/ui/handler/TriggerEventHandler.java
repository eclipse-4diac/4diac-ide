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
package org.eclipse.fordiac.ide.deployment.debug.ui.handler;

import java.util.List;

import org.eclipse.core.runtime.Adapters;
import org.eclipse.fordiac.ide.deployment.debug.watch.DeploymentDebugWatchUtils;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.swt.widgets.Shell;

public class TriggerEventHandler extends AbstractDeploymentHandler<Event> {

	@Override
	protected void perform(final Event target, final Resource resource, final IDeviceManagementInteractor interactor,
			final Shell shell) throws DeploymentException {
		interactor.triggerEvent(resource, DeploymentDebugWatchUtils.getResourceRelativeName(target, resource));
	}

	@Override
	protected List<Event> getTargets(final Object selectedElement) {
		return Adapters.adapt(selectedElement, IInterfaceElement.class) instanceof final Event event
				? DeploymentDebugWatchUtils.resolveSubappInterfaceConnections(event).toList()
				: null;
	}
}
