/*******************************************************************************
 * Copyright (c) 2019 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.iec61499.handlers;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.deployment.DeploymentCoordinator;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.iec61499.executors.DynamicTypeLoadDeploymentExecutor;
import org.eclipse.fordiac.ide.deployment.interactors.DeviceManagementInteractorFactory;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class GetResourcesHandler extends AbstractHandler {

	private static List<Object> getDeviceList(final ExecutionEvent event) {
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof final StructuredSelection structuredSelection) {
			return structuredSelection.toList();
		}
		return Collections.emptyList();
	}

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		for (Object object : getDeviceList(event)) {
			if (object instanceof final EditPart editPart) {
				object = editPart.getModel();
			}
			if (object instanceof final Device device) {
				fetchResources(device);
			}
		}
		return null;
	}

	private static void fetchResources(final Device device) {
		final IDeviceManagementInteractor interactor = DeviceManagementInteractorFactory.INSTANCE
				.getDeviceManagementInteractor(device);
		if (interactor instanceof final DynamicTypeLoadDeploymentExecutor dynamicTypeLoadDeploymentExecutor) {
			DeploymentCoordinator.enableOutput(interactor);
			try {
				interactor.connect();
				dynamicTypeLoadDeploymentExecutor.queryResourcesWithNetwork(device);
			} catch (final Exception e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			} finally {
				try {
					interactor.disconnect();
				} catch (final DeploymentException e) {
					FordiacLogHelper.logError(e.getMessage(), e);
				}
			}
			DeploymentCoordinator.disableOutput(interactor);
		}
	}
}
