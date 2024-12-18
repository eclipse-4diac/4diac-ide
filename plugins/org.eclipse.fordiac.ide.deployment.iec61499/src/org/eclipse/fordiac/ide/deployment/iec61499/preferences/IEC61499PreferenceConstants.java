/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.iec61499.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class IEC61499PreferenceConstants {

	public static final String DEPLOYMENT_IEC61499_PREFERENCES_ID = "org.eclipse.fordiac.ide.deployment.iec61499"; //$NON-NLS-1$

	public static final IPreferenceStore STORE = new ScopedPreferenceStore(InstanceScope.INSTANCE,
			DEPLOYMENT_IEC61499_PREFERENCES_ID);

	private IEC61499PreferenceConstants() {
	}

	static final String P_CONNECTION_TIMEOUT = "Connection Timeout"; //$NON-NLS-1$

	/* default connection timeout value in ms */
	static final int P_CONNECTION_TIMEOUT_DEFAULT = 3000;

	/*
	 * check if there is a connection timeout value set and if not return the
	 * default value
	 */
	public static int getConnectionTimeout() {
		int retVal = STORE.getInt(P_CONNECTION_TIMEOUT);
		if (0 == retVal) {
			retVal = P_CONNECTION_TIMEOUT_DEFAULT;
		}
		return retVal;
	}
}
