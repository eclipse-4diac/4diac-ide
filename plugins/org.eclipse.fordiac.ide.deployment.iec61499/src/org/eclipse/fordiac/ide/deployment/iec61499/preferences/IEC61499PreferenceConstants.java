/*******************************************************************************
 * Copyright (c) 2024, 2025 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *   Martin Erich Jobst - add maximum request size
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.iec61499.preferences;

import org.eclipse.core.runtime.Platform;

public class IEC61499PreferenceConstants {

	public static final String DEPLOYMENT_IEC61499_PREFERENCES_ID = "org.eclipse.fordiac.ide.deployment.iec61499"; //$NON-NLS-1$

	private IEC61499PreferenceConstants() {
	}

	public static final String P_CONNECTION_TIMEOUT = "Connection Timeout"; //$NON-NLS-1$
	public static final String P_MAX_REQUEST_SIZE = "MaxRequestSize"; //$NON-NLS-1$

	/* default connection timeout value in ms */
	public static final int P_CONNECTION_TIMEOUT_DEFAULT = 3000;
	public static final int P_MAX_REQUEST_SIZE_DEFAULT = 1500;

	/*
	 * check if there is a connection timeout value set and if not return the
	 * default value
	 */
	public static int getConnectionTimeout() {
		return Platform.getPreferencesService().getInt(DEPLOYMENT_IEC61499_PREFERENCES_ID, P_CONNECTION_TIMEOUT,
				P_CONNECTION_TIMEOUT_DEFAULT, null);
	}

	public static int getMaxRequestSize() {
		return Platform.getPreferencesService().getInt(DEPLOYMENT_IEC61499_PREFERENCES_ID, P_MAX_REQUEST_SIZE,
				P_MAX_REQUEST_SIZE_DEFAULT, null);
	}
}
