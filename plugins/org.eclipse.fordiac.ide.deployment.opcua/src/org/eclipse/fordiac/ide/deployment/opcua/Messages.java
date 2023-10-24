/*******************************************************************************
 * Copyright (c) 2023, 2024 Primetals Technologies Austria GmbH
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

public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.deployment.opcua.messages";
	public static String OPCUADeploymentExecutor_CreateResourceFailed;
	public static String OPCUADeploymentExecutor_CreateFBInstanceFailed;
	public static String OPCUADeploymentExecutor_CreateFBInstanceFailedNoTypeFound;
	public static String OPCUADeploymentExecutor_CreateConnectionFailed;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}