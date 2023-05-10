/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.iec61499.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.deployment.DeploymentCoordinator;
import org.eclipse.fordiac.ide.deployment.data.DeviceDeploymentData;
import org.eclipse.fordiac.ide.deployment.data.ResourceDeploymentData;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.iec61499.executors.DynamicTypeLoadDeploymentExecutor;
import org.eclipse.fordiac.ide.deployment.interactors.DeviceManagementInteractorFactory;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.typelibrary.DeviceTypeEntry;
import org.eclipse.fordiac.ide.systemconfiguration.commands.DeviceCreateCommand;
import org.eclipse.fordiac.ide.systemconfiguration.commands.DeviceDeleteCommand;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class CompareDeviceHandler extends AbstractHandler {

	private DeviceDeploymentData differenceDataDeltaPlus;
	private DeviceDeploymentData differenceDataDeltaMinus;

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {

		if (getSelectedDevice(event) != null) {
			final Device selectedDevice = getSelectedDevice(event);
			final Device onlineDevice = fillDeviceDataFromForte(createDeviceForComparison(selectedDevice));

			if (onlineDevice != null) {
				// remove automatically created resource
				onlineDevice.getResource().remove(0);

				if (!onlineDevice.getResource().isEmpty()) {
					try {
						compareDevices(selectedDevice, onlineDevice);
						printDiffInConsole();
					} catch (final DeploymentException e) {
						e.printStackTrace();
					}
				}

				final DeviceDeleteCommand deleteDeviceCmd = new DeviceDeleteCommand(onlineDevice);
				if (deleteDeviceCmd.canExecute()) {
					deleteDeviceCmd.execute();
				}
			}
		}
		return null;
	}

	private void compareDevices(final Device offlineDevice, final Device onlineDevice) throws DeploymentException {

		final DeviceDeploymentData offlineDeviceData = DeploymentCoordinator
				.createDeploymentdata(offlineDevice.getResource().toArray()).get(0);

		final DeviceDeploymentData onlineDeviceData = DeploymentCoordinator
				.createDeploymentdata(onlineDevice.getResource().toArray()).get(0);

		differenceDataDeltaPlus = new DeviceDeploymentData(offlineDevice);
		differenceDataDeltaMinus = new DeviceDeploymentData(offlineDevice);

		for (final ResourceDeploymentData resOfflineData : offlineDeviceData.getResData()) {
			for (final ResourceDeploymentData resOnlineData : onlineDeviceData.getResData()) {
				if (resOnlineData.getRes().getName().equals(resOfflineData.getRes().getName())) {

					final ResourceDeploymentData resourceDeltaPlus = new ResourceDeploymentData(
							resOfflineData.getRes());
					resourceDeltaPlus.getFbs().clear();
					resourceDeltaPlus.getConnections().clear();
					resourceDeltaPlus.getParams().clear();
					differenceDataDeltaPlus.addResourceData(resourceDeltaPlus);

					final ResourceDeploymentData resourceDeltaMinus = new ResourceDeploymentData(
							resOfflineData.getRes());
					resourceDeltaMinus.getFbs().clear();
					resourceDeltaMinus.getConnections().clear();
					resourceDeltaMinus.getParams().clear();
					differenceDataDeltaMinus.addResourceData(resourceDeltaMinus);

					compareFBs(resOnlineData, resOfflineData);
					compareConnections(resOnlineData, resOfflineData);
					compareParameters(resOnlineData, resOfflineData);

				} else {
					System.out.println(
							"No Resource: " + resOfflineData.getRes().getName() + " not found online to compare!");
				}
			}
		}
	}

	private void compareFBs(final ResourceDeploymentData offlineResource,
			final ResourceDeploymentData onlineResource) {

		onlineResource.getFbs().forEach(onlineFB -> {
			if (offlineResource.getFbs().stream()
					.noneMatch(offlineFB -> offlineFB.getFb().getName().equals(onlineFB.getFb().getName())
							&& offlineFB.getPrefix().equals(onlineFB.getPrefix())
							&& offlineFB.getFb().getType().equals(onlineFB.getFb().getType()))) {
				differenceDataDeltaPlus.getResData().get(differenceDataDeltaPlus.getResData().size() - 1)
				.addFbs(onlineFB);
			}
		});

		offlineResource.getFbs().forEach(offlineFB -> {
			if (onlineResource.getFbs().stream()
					.noneMatch(onlineFB -> onlineFB.getFb().getName().equals(offlineFB.getFb().getName())
							&& onlineFB.getPrefix().equals(offlineFB.getPrefix())
							&& onlineFB.getFb().getType().equals(offlineFB.getFb().getType()))) {
				differenceDataDeltaMinus.getResData().get(differenceDataDeltaMinus.getResData().size() - 1)
				.addFbs(offlineFB);
			}
		});
	}

	private void compareConnections(final ResourceDeploymentData offlineResource,
			final ResourceDeploymentData onlineResource) {

		onlineResource.getConnections().forEach(onlineCon -> {
			if (offlineResource.getConnections().stream()
					.noneMatch(offlineCon -> offlineCon.getDestination().getName()
							.equals(onlineCon.getDestination().getName())
							&& offlineCon.getDestinationPrefix().equals(onlineCon.getDestinationPrefix())
							&& offlineCon.getSource().getName().equals(onlineCon.getSource().getName())
							&& offlineCon.getSourcePrefix().equals(onlineCon.getSourcePrefix()))) {
				differenceDataDeltaPlus.getResData().get(differenceDataDeltaPlus.getResData().size() - 1)
				.addConnections(onlineCon);
			}
		});

		offlineResource.getConnections().forEach(offlineCon -> {
			if (onlineResource.getConnections().stream()
					.noneMatch(onlineCon -> onlineCon.getDestination().getName()
							.equals(offlineCon.getDestination().getName())
							&& onlineCon.getDestinationPrefix().equals(offlineCon.getDestinationPrefix())
							&& onlineCon.getSource().getName().equals(offlineCon.getSource().getName())
							&& onlineCon.getSourcePrefix().equals(offlineCon.getSourcePrefix()))) {
				differenceDataDeltaMinus.getResData().get(differenceDataDeltaMinus.getResData().size() - 1)
				.addConnections(offlineCon);
			}
		});
	}

	private void compareParameters(final ResourceDeploymentData offlineResource,
			final ResourceDeploymentData onlineResource) {

		onlineResource.getParams().forEach(onlineParam -> {
			if (offlineResource.getParams().stream()
					.noneMatch(offlineParam -> offlineParam.getVar().equals(onlineParam.getVar())
							&& offlineParam.getPrefix().equals(onlineParam.getPrefix()))) {
				differenceDataDeltaPlus.getResData().get(differenceDataDeltaPlus.getResData().size() - 1)
				.addParameter(onlineParam);
			}
		});

		offlineResource.getParams().forEach(offlineParam -> {
			if (onlineResource.getParams().stream()
					.noneMatch(onlineParam -> onlineParam.getVar().equals(offlineParam.getVar())
							&& onlineParam.getPrefix().equals(offlineParam.getPrefix()))) {
				differenceDataDeltaMinus.getResData().get(differenceDataDeltaMinus.getResData().size() - 1)
				.addParameter(offlineParam);
			}
		});
	}

	private static Device getSelectedDevice(final ExecutionEvent event) {
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof final StructuredSelection structuredSelection
				&& !structuredSelection.toList().isEmpty()
				&& ((StructuredSelection) selection).toList().size() == 1) {

			if (((StructuredSelection) selection).toList().get(0) instanceof EditPart) {
				return (Device) (((EditPart) ((StructuredSelection) selection).toList().get(0)).getModel());
			} else if (((StructuredSelection) selection).toList() instanceof Device) {
				return (Device) (((StructuredSelection) selection).toList().get(0));
			}
		}
		return null;
	}

	private static Device createDeviceForComparison(final Device selectedDevice) {
		final DeviceCreateCommand createDeviceCmd = new DeviceCreateCommand(
				(DeviceTypeEntry) selectedDevice.getTypeEntry(), selectedDevice.getSystemConfiguration(),
				new Rectangle(1, 1, 1, 1));

		if (createDeviceCmd.canExecute()) {
			createDeviceCmd.execute();
			final Device newDevice = createDeviceCmd.getDevice();
			newDevice.setProfile("DynamicTypeLoad"); //$NON-NLS-1$
			return newDevice;
		}
		return null;
	}

	private static Device fillDeviceDataFromForte(final Device onlineDevice) {
		final IDeviceManagementInteractor interactor = DeviceManagementInteractorFactory.INSTANCE
				.getDeviceManagementInteractor(onlineDevice);

		if (interactor instanceof final DynamicTypeLoadDeploymentExecutor dynamicTypeLoadInteractor) {
			try {
				interactor.connect();
				dynamicTypeLoadInteractor.queryResourcesWithNetwork(onlineDevice);
			} catch (final Exception e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			} finally {
				try {
					interactor.disconnect();
				} catch (final DeploymentException e) {
					FordiacLogHelper.logError(e.getMessage(), e);
				}
			}
		}
		return onlineDevice;
	}

	private void printDiffInConsole() {
		differenceDataDeltaPlus.getResData().forEach(res -> {
			System.out.println("== Resource: " + res.getRes().getName() + " (online)");
			res.getFbs().forEach(fb -> System.out.println("++ fb: " + fb.getPrefix() + fb.getFb().getName()));
			res.getConnections().forEach(con -> System.out
					.println("++ con: " + con.getSourcePrefix() + con.getSource().getName() + " -> "
							+ con.getDestinationPrefix() + con.getDestination().getName()));
			res.getParams().forEach(con -> System.out.println("++ param: " + con.getPrefix() + con.getVar().getName()));
		});

		differenceDataDeltaMinus.getResData().forEach(res -> {
			System.out.println("== Resource: " + res.getRes().getName() + "(local)");
			res.getFbs().forEach(fb -> System.out.println("-- fb: " + fb.getPrefix() + fb.getFb().getName()));
			res.getConnections().forEach(con ->
			System.out.println("-- con: " + con.getSourcePrefix() + con.getSource().getName() + " -> "
					+ con.getDestinationPrefix() + con.getDestination().getName()));
			res.getParams().forEach(con -> System.out.println("-- param: " + con.getPrefix() + con.getVar().getName()));
		});
	}
}
