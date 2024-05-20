/*******************************************************************************
 * Copyright (c) 2024 AIMIRIM STI - https://en.aimirimsti.com.br/
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Pedro Ricardo
 *   Felipe Adriano
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.uao;

import org.eclipse.osgi.util.NLS;

public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.deployment.uao.messages"; //$NON-NLS-1$
	public static String UAODeploymentExecutor_CreateClientFailed;
	public static String UAODeploymentExecutor_ClientConnectionFailed;
	public static String UAODeploymentExecutor_URIParseFailed;
	public static String UAODeploymentExecutor_GetMgrIDFailed;
	public static String UAODeploymentExecutor_GetSSLFailed;
	public static String UAODeploymentExecutor_RequestInterrupted;
	public static String UAODeploymentExecutor_CreateFBInstanceFailedNoTypeFound;
	public static String UAODeploymentExecutor_ClientRequestTimeout;
	public static String UAODeploymentExecutor_RequestRejected;
	public static String UAODeploymentExecutor_CreateConnectionFailedNoDataFound;
	public static String UAODeploymentExecutor_InvalidCreateConnection;
	public static String UAODeploymentExecutor_FeatureNotImplemented;
	public static String UAODeploymentExecutor_CommandNotImplemented;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}