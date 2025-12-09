/*******************************************************************************
 * Copyright (c) 2014 - 2018 fortiss GmbH, Johannes Kepler University
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.bootfile;

import java.util.List;

import org.eclipse.fordiac.ide.deployment.AbstractFileManagementHandler;
import org.eclipse.fordiac.ide.deployment.DeploymentCoordinator;
import org.eclipse.swt.widgets.Shell;

public class BootFileDeviceManagementCommunicationHandler extends AbstractFileManagementHandler {

	public static void createBootFile(final List<Object> workList, final String fileName, final Shell shell) {
		if (null != fileName) {
			final BootFileDeviceManagementCommunicationHandler bootFileHandler = new BootFileDeviceManagementCommunicationHandler();
			DeploymentCoordinator.performDeployment(workList.toArray(), bootFileHandler, null);
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