/*******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, fortiss GmbH,
 * 							 Johannes Kepler University
 * 				 2019        Andrea Zoitl
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Alois Zoitl, Florian Noack, Gerhard Ebenhofer, Monika Wenger
 *  		- initial API and implementation and/or initial documentation
 *  Alois Zoitl - Harmonized deployment and monitoring communication
 *  Andrea Zoitl - externalized all remaining strings
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.iec61499;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("squid:S3008")  // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.deployment.iec61499.messages"; //$NON-NLS-1$
	public static String DeploymentExecutor_CreateConnectionFailed;
	public static String DeploymentExecutor_CreateFBInstanceFailed;
	public static String DeploymentExecutor_CreateFBInstanceFailedNoTypeFound;
	public static String DeploymentExecutor_CreateResourceFailed;
	public static String DeploymentExecutor_DeviceConnectionClosed;
	public static String DeploymentExecutor_StartingResourceFailed;
	public static String DeploymentExecutor_StartingFBFailed;
	public static String DeploymentExecutor_StartingDeviceFailed;
	public static String DeploymentExecutor_WriteFBParameterFailed;

	public static String DeploymentExecutor_WriteResourceParameterFailed;
	public static String DeploymentExecutor_WriteDeviceParameterFailed;
	public static String DeploymentExecutor_DisconnectFailed;
	public static String DeploymentExecutor_KillFBFailed;
	public static String DeploymentExecutor_KillDeviceFailed;
	public static String DeploymentExecutor_DeleteFBFailed;

	public static String DeploymentExecutor_QueryResourcesFailed;

	public static String DeploymentExecutor_ReadWatchesFailed;
	public static String DeploymentExecutor_AddWatchesFailed;
	public static String DeploymentExecutor_DeleteWatchesFailed;
	public static String DeploymentExecutor_ForceValueFailed;
	public static String DeploymentExecutor_ClearForceFailed;
	public static String DeploymentExecutor_TriggerEventFailed;

	public static String DynamicTypeLoadDeploymentExecutor_QueryFailed;
	public static String DynamicTypeLoadDeploymentExecutor_CreateTypeFailed;
	public static String DynamicTypeLoadDeploymentExecutor_LUAScriptForFBTypeNotExecuted;
	public static String DynamicTypeLoadDeploymentExecutor_LUAScriptForAdapterTypeNotExecuted;

	public static String EthernetDeviceManagementCommunicationHandler_CouldNotConnectToDevice;

	public static String EthernetComHandler_InvalidMgmtID;

	public static String HoloblocDeploymentPreferences_PreferencePageDescription;
	public static String HoloblocDeploymentPreferences_ConnectionTimout;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
