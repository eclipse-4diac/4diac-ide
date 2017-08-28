/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Alois Zoitl, Florian Noack, Gerhard Ebenhofer, 
 *  Monika Wenger - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.iec61499;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.deployment.iec61499.messages"; //$NON-NLS-1$
	public static String DeploymentExecutor_CreateConnectionFailed;
	public static String DeploymentExecutor_CreateFBInstanceFailed;
	public static String DeploymentExecutor_CreateFBInstanceFailedNoTypeFound;
	public static String DeploymentExecutor_CreateResourceFailed;
	public static String DeploymentExecutor_DeviceConnectionClosed;
	public static String DeploymentExecutor_CreateResourceInstance;
	public static String DeploymentExecutor_CreateFBInstance;
	public static String DeploymentExecutor_CreateConnection;
	public static String DeploymentExecutor_StartingResourceFailed;
	public static String DeploymentExecutor_StartingFBFailed;
	public static String DeploymentExecutor_StartingDeviceFailed;
	public static String DeploymentExecutor_WriteFBParameterFailed;
	public static String DeploymentExecutor_WriteParameter;
	public static String DeploymentExecutor_Start;
	public static String DeploymentExecutor_StartFB;
	public static String DeploymentExecutor_KillFB;
	public static String DeploymentExecutor_KillDevice;
	public static String DeploymentExecutor_StopFB;
	public static String DeploymentExecutor_DeleteFB;
	public static String DeploymentExecutor_DeleteConnection;
	public static String DeploymentExecutor_WriteResourceParameterFailed;
	public static String DeploymentExecutor_WriteDeviceParameterFailed;
	public static String DeploymentExecutor_DisconnectFailed;
	public static String DeploymentExecutor_KillFBFailed;
	public static String DeploymentExecutor_KillDeviceFailed;
	public static String DeploymentExecutor_DeleteFBFailed;
	public static String DTL_CreateFBType;
	public static String DTL_CreateAdapterType;
	public static String DTL_QueryFBTypes;
	public static String DTL_QueryAdapterTypes;
	public static String DTL_QueryFailed;
	public static String DTL_CreateTypeFailed;
	public static String FBDK2_WriteParameter;
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
