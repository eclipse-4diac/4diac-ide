/*******************************************************************************
 * Copyright (c) 2012, 2017, 2018 Profactor GbmH, fortiss GmbH,
 * 								  Johannes Keppler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.preferences;

import org.eclipse.fordiac.ide.monitoring.Activator;

/**
 * Constant definitions for plug-in preferences
 */
public final class PreferenceConstants {

	public static final String P_WATCH_COLOR = "watchColor"; //$NON-NLS-1$

	public static final String P_FORCE_COLOR = "forceColor"; //$NON-NLS-1$

	public static final String P_POLLING_INTERVAL = "pollingInterval"; //$NON-NLS-1$

	public static final int P_POLLING_INTERVAL_DEVAULT_VALUE = 300;

	public static final String P_MONITORING_TRANSPARENCY = "monitoringTransparency"; //$NON-NLS-1$

	public static final int P_MONITORING_TRANSPARENCY_VALUE = 190;

	public static final String P_MONITORING_STARTMONITORINGWITHOUTASKING = "dontAskAgain"; //$NON-NLS-1$

	public static final boolean P_MONITORING_STARTMONITORINGWITHOUTASKING_VALUE = false;

	public static final String P_MONITORING_WRITEBACKONLINEVALUES = "writeBackOnlineValue"; //$NON-NLS-1$

	public static final boolean P_MONITORING_WRITEBACKONLINEVALUES_VALUE = false;

	public static int getPollingInterval() {
		int timeout = Activator.getDefault().getPreferenceStore().getInt(PreferenceConstants.P_POLLING_INTERVAL);
		if (0 == timeout) {
			timeout = P_POLLING_INTERVAL_DEVAULT_VALUE;
		}
		return timeout;
	}

	public static int getMonitoringTransparency() {
		int transparency = Activator.getDefault().getPreferenceStore()
				.getInt(PreferenceConstants.P_MONITORING_TRANSPARENCY);
		if (0 == transparency) {
			transparency = P_MONITORING_TRANSPARENCY_VALUE;
		}
		return transparency;
	}

	private PreferenceConstants() {
		// class should not be instantiable
	}

}
