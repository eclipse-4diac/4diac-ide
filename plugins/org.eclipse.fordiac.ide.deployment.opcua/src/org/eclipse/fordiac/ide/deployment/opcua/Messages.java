/*******************************************************************************
 * Copyright (c) 2022, 2024 Markus Meingast, Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Markus Meingast
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.opcua;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.deployment.opcua.messages"; //$NON-NLS-1$
	public static String OPCUADeploymentExecutor_GetMgrIDFailed;
	public static String OPCUADeploymentExecutor_CreateClientFailed;
	public static String OPCUADeploymentExecutor_CreateResourceFailed;
	public static String OPCUADeploymentExecutor_CreateFBInstanceFailed;
	public static String OPCUADeploymentExecutor_CreateFBInstanceFailedNoTypeFound;
	public static String OPCUADeploymentExecutor_CreateConnectionFailed;
	public static String OPCUADeploymentExecutor_CreateConnectionFailedNoDataFound;

	public static String OPCUADeploymentExecutor_WriteDeviceFailed;
	public static String OPCUADeploymentExecutor_WriteResourceFailed;
	public static String OPCUADeploymentExecutor_WriteFBFailed;
	public static String OPCUADeploymentExecutor_ErrorOnWriteRequest;

	public static String OPCUADeploymentExecutor_StartDeviceFailed;
	public static String OPCUADeploymentExecutor_StartResourceFailed;
	public static String OPCUADeploymentExecutor_StartFBFailed;
	public static String OPCUADeploymentExecutor_KillDeviceFailed;
	public static String OPCUADeploymentExecutor_KillResourceFailed;
	public static String OPCUADeploymentExecutor_KillFBFailed;
	public static String OPCUADeploymentExecutor_DeleteDeviceFailed;
	public static String OPCUADeploymentExecutor_DeleteResourceFailed;
	public static String OPCUADeploymentExecutor_DeleteFBFailed;
	public static String OPCUADeploymentExecutor_DeleteConnectionFailed;
	public static String OPCUADeploymentExecutor_AddWatchFailed;
	public static String OPCUADeploymentExecutor_ReadWatchesFailed;
	public static String OPCUADeploymentExecutor_RemoveWatchFailed;
	public static String OPCUADeploymentExecutor_TriggerEventFailed;
	public static String OPCUADeploymentExecutor_ForceValueFailed;
	public static String OPCUADeploymentExecutor_ClearForceFailed;
	public static String OPCUADeploymentExecutor_ErrorOnMonitoringRequest;
	public static String OPCUADeploymentExecutor_QueryResourcesFailed;
	public static String OPCUADeploymentExecutor_ErrorOnQueryResources;

	public static String OPCUADeploymentExecutor_BrowseOPCUAFailed;

	public static String OPCUADeploymentExecutor_RequestFailed;
	public static String OPCUADeploymentExecutor_RequestInterrupted;

	public static String OPCUADeploymentExecutor_CouldNotConnectToDevice;
	public static String OPCUADeploymentExecutor_CouldNotDisconnectFromDevice;
	public static String OPCUADeploymentExecutor_UnknownResponseCode;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}