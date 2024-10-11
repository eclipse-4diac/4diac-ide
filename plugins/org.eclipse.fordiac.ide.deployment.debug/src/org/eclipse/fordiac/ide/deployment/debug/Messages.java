/*******************************************************************************
 * Copyright (c) 2024 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.deployment.debug.messages"; //$NON-NLS-1$
	public static String DeploymentDebugStackFrame_Name;
	public static String DeploymentDebugTarget_ConnectJobName;
	public static String DeploymentDebugTarget_UnsupportedOperation;
	public static String DeploymentDebugThread_Name;
	public static String DeploymentLaunchConfigurationDelegate_DeploymentError;
	public static String DeploymentLaunchConfigurationDelegate_IllegalLaunchMode;
	public static String DeploymentProcess_ExeceptionOccured;
	public static String DeploymentProcess_Name;
	public static String DeploymentProcess_StillRunning;
	public static String DeploymentProcess_Terminated;
	public static String DeploymentStreamsProxy_ConnectedToDevice;
	public static String DeploymentStreamsProxy_DeployedElements;
	public static String DeploymentStreamsProxy_Deploying;
	public static String DeploymentStreamsProxy_DisconnectedFromDevice;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
