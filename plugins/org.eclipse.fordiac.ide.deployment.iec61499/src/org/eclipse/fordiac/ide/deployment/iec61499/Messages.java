/*******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, fortiss GmbH, 
 * 							 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Alois Zoitl, Florian Noack, Gerhard Ebenhofer, Monika Wenger 
 *  		- initial API and implementation and/or initial documentation
 *  Alois Zoitl - Harmonized deployment and monitoring communication
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
	public static String DeploymentExecutor_ReadWatchesFailed;
	public static String DeploymentExecutor_AddWatchesFailed;
	public static String DeploymentExecutor_DeleteWatchesFailed;
	public static String DeploymentExecutor_ForceValueFailed;
	public static String DeploymentExecutor_ClearForceFailed;
	public static String DeploymentExecutor_TriggerEventFailed;

	
	public static String DTL_QueryFailed;
	public static String DTL_CreateTypeFailed;
	
	public static String EthernetComHandler_InvalidMgmtID;
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
