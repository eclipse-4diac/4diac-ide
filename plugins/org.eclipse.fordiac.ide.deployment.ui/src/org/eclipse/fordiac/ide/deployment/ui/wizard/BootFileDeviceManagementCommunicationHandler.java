/*******************************************************************************
 * Copyright (c) 2014 - 2018 fortiss GmbH, Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.ui.wizard;

import java.util.List;

import org.eclipse.fordiac.ide.deployment.AbstractFileManagementHandler;
import org.eclipse.fordiac.ide.deployment.DeploymentCoordinator;
import org.eclipse.swt.widgets.Shell;

public class BootFileDeviceManagementCommunicationHandler extends AbstractFileManagementHandler {
	
	public static void createBootFile(List<Object> workList, String fileName, Shell shell) {
		if (null != fileName) {
			DeploymentCoordinator deployment = DeploymentCoordinator.getInstance();
			BootFileDeviceManagementCommunicationHandler bootFileHandler = new BootFileDeviceManagementCommunicationHandler();
			deployment.performDeployment(workList.toArray(), bootFileHandler, null);
			bootFileHandler.writeToBootFile(fileName, false, shell);
		}
	}
	
	/*
	 * only the static function of this class should be able to create an
	 * instance
	 */
	private BootFileDeviceManagementCommunicationHandler() {
		super();
	}
	
}