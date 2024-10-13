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
	public static String AbstractRuntimeWatch_AddWatch;
	public static String AbstractRuntimeWatch_RemoveWatch;
	public static String DeploymentDebugDevice_ConnectError;
	public static String DeploymentDebugDevice_DisconnectError;
	public static String DeploymentDebugDevice_ReadWatchesError;
	public static String DeploymentDebugDevice_TerminateError;
	public static String EventWatch_TriggerEventError;
	public static String DeploymentDebugResource_ResumeError;
	public static String DeploymentDebugResource_SuspendError;
	public static String DeploymentDebugResource_TerminateError;
	public static String DeploymentDebugStackFrame_Name;
	public static String DeploymentDebugTarget_ConnectJobName;
	public static String DeploymentDebugTarget_UnsupportedOperation;
	public static String DeploymentDebugThread_Name;
	public static String DeploymentLaunchConfigurationAttributes_AllowTerminate_Always;
	public static String DeploymentLaunchConfigurationAttributes_AllowTerminate_DebugOnly;
	public static String DeploymentLaunchConfigurationAttributes_AllowTerminate_Never;
	public static String DeploymentLaunchConfigurationDelegate_CannotFindSystem;
	public static String DeploymentLaunchConfigurationDelegate_DeploymentError;
	public static String DeploymentLaunchConfigurationDelegate_IllegalLaunchMode;
	public static String DeploymentLaunchConfigurationDelegate_LaunchNotTerminated;
	public static String DeploymentProcess_ExeceptionOccured;
	public static String DeploymentProcess_Name;
	public static String DeploymentProcess_StillRunning;
	public static String DeploymentProcess_Terminated;
	public static String DeploymentStreamsProxy_ConnectedToDevice;
	public static String DeploymentStreamsProxy_DeployedElements;
	public static String DeploymentStreamsProxy_Deploying;
	public static String DeploymentStreamsProxy_DisconnectedFromDevice;
	public static String VarDeclarationWatch_ClearForceError;
	public static String VarDeclarationWatch_ForceError;
	public static String VarDeclarationWatch_WriteError;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
