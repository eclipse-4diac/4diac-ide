/*******************************************************************************
 * Copyright (c) 2019 fortiss GmbH
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.iec61499;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.deployment.Activator;
import org.eclipse.fordiac.ide.deployment.DeploymentCoordinator;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.DeviceManagementInteractorFactory;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class GetResourcesHandler extends AbstractHandler {
	@SuppressWarnings("unchecked")
	private static List<Object> getDeviceList(ExecutionEvent event) {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof StructuredSelection) {
			return ((StructuredSelection) selection).toList();
		}
		return null;
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		for (Object object : getDeviceList(event)) {
			Device device = null;
			if (object instanceof EditPart) {
				object = ((EditPart) object).getModel();
			}
			if (object instanceof Device) {
				device = (Device) object;
			}
			fetchResources(device);
		}
		return null;
	}

	private static void fetchResources(Device device) {
		IDeviceManagementInteractor interactor = DeviceManagementInteractorFactory.INSTANCE
				.getDeviceManagementInteractor(device);
		if (interactor instanceof DynamicTypeLoadDeploymentExecutor) {
			DeploymentCoordinator.INSTANCE.enableOutput(interactor);
			try {
				interactor.connect();
				((DynamicTypeLoadDeploymentExecutor) interactor).queryResourcesWithNetwork(device);
			} catch (Exception e) {
				Activator.getDefault().logError(e.getMessage(), e);
			} finally {
				try {
					interactor.disconnect();
				} catch (DeploymentException e) {
					Activator.getDefault().logError(e.getMessage(), e);
				}
			}
			DeploymentCoordinator.INSTANCE.disableOutput(interactor);
		}
	}
}
