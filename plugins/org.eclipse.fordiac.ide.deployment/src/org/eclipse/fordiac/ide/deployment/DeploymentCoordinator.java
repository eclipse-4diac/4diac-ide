/*******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, fortiss GmbH,
 * 							 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - reworked deployment to detect if monitoring was enabled
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.deployment.data.DeviceDeploymentData;
import org.eclipse.fordiac.ide.deployment.data.ResourceDeploymentData;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.util.DeploymentHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public final class DeploymentCoordinator {

	public static void printUnsupportedDeviceProfileMessageBox(final Device device, final Resource res) {
		Display.getDefault().asyncExec(() -> {
			final MessageBox messageBox = new MessageBox(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.ICON_ERROR | SWT.OK);
			final String resName = (null != res) ? res.getName() : ""; //$NON-NLS-1$

			if (null != device.getProfile() && !device.getProfile().isEmpty()) {
				messageBox.setMessage(
						MessageFormat.format(Messages.DeploymentCoordinator_MESSAGE_DefinedProfileNotSupported,
								device.getProfile(), device.getName(), resName));
			} else {
				messageBox.setMessage(MessageFormat.format(Messages.DeploymentCoordinator_MESSAGE_ProfileNotSet,
						device.getName(), resName));
			}
			messageBox.open();
		});
	}

	/**
	 * Perform deployment.
	 *
	 * @param selection                 the selection
	 * @param overrideDevMgmCommHandler if not null this device management
	 *                                  communication should be used instead the one
	 *                                  derived from the device profile.
	 */
	public static void performDeployment(final Object[] selection,
			final IDeviceManagementCommunicationHandler overrideDevMgmCommHandler, final String profile) {
		final Shell shell = Display.getDefault().getActiveShell();
		try {
			final DownloadRunnable download = new DownloadRunnable(createDeploymentdata(selection),
					overrideDevMgmCommHandler, null, profile);
			new ProgressMonitorDialog(shell).run(true, true, download);
			if (!download.getResult().isOK()) {
				ErrorDialog.openError(shell, null, null, download.getResult());
			}
		} catch (final DeploymentException | InvocationTargetException ex) {
			MessageDialog.openError(shell, Messages.DeploymentCoordinator_DepoymentError, ex.getMessage());
		} catch (final InterruptedException ex) {
			Thread.currentThread().interrupt(); // mark interruption
			MessageDialog.openInformation(shell, Messages.DeploymentCoordinator_LABEL_DownloadAborted, ex.getMessage());
		}
	}

	public static List<DeviceDeploymentData> createDeploymentdata(final Object[] selection) throws DeploymentException {
		final List<DeviceDeploymentData> data = new ArrayList<>();
		for (final Object object : selection) {
			if (object instanceof final Resource resource) {
				final DeviceDeploymentData devData = getDevData(data, resource.getDevice());
				devData.addResourceData(new ResourceDeploymentData(resource));
			} else if (object instanceof final Device device) {
				final DeviceDeploymentData devData = getDevData(data, device);
				devData.setSeltectedDevParams(device.getVarDeclarations().stream()
						.filter(devVar -> !DeploymentHelper.MGR_ID.equalsIgnoreCase(devVar.getName())).toList());
			}
		}
		return data;
	}

	private static DeviceDeploymentData getDevData(final List<DeviceDeploymentData> data, final Device device) {
		DeviceDeploymentData retVal = null;
		for (final DeviceDeploymentData devData : data) {
			if (device == devData.getDevice()) {
				retVal = devData;
				break;
			}
		}
		if (null == retVal) {
			retVal = new DeviceDeploymentData(device);
			data.add(retVal);
		}
		return retVal;
	}

	private DeploymentCoordinator() {
		throw new UnsupportedOperationException("Utility class should not be instantiated!"); //$NON-NLS-1$
	}

}
