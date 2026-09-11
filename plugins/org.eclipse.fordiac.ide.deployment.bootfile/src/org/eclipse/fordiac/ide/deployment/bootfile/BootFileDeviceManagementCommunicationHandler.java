/*******************************************************************************
 * Copyright (c) 2014 fortiss GmbH, Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring
 *   Franz Höpfinger - small bootfile fix.
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.bootfile;

import java.util.List;

import org.eclipse.fordiac.ide.deployment.AbstractFileManagementHandler;
import org.eclipse.fordiac.ide.deployment.DeploymentCoordinator;
import org.eclipse.swt.widgets.Shell;

public class BootFileDeviceManagementCommunicationHandler extends AbstractFileManagementHandler {

	/*
	 * Boot files always use the classic FORTE line protocol, regardless of which
	 * management protocol (e.g. OPC UA) the device is configured to use at
	 * runtime. Only the HOLOBLOC device management interactor honors an override
	 * communication handler, so it must be forced here rather than relying on
	 * the device's own Profile attribute.
	 */
	private static final String BOOT_FILE_PROFILE = "HOLOBLOC"; //$NON-NLS-1$

	public static void createBootFile(final List<Object> workList, final String fileName, final Shell shell) {
		if (null != fileName) {
			final BootFileDeviceManagementCommunicationHandler bootFileHandler = new BootFileDeviceManagementCommunicationHandler();
			DeploymentCoordinator.performDeployment(workList.toArray(), bootFileHandler, BOOT_FILE_PROFILE);
			bootFileHandler.writeToBootFile(fileName, false, shell);
		}
	}

	/*
	 * only the static function of this class should be able to create an instance
	 */
	private BootFileDeviceManagementCommunicationHandler() {
		super();
	}

}