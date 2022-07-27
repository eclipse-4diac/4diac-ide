/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("squid:S3008")  // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.monitoring.messages"; //$NON-NLS-1$
	public static String MonitoringManagerUtils_NoSubappAnchor;
	public static String MonitoringManagerUtils_SelectionFilteringActive;
	public static String MonitoringManagerUtils_SelectionFilteringForce;
	public static String MonitoringManagerUtils_ExpandAll;
	public static String MonitoringManagerUtils_CollapseAll;

	public static String MonitoringPreferences_WatchColor;
	public static String MonitoringPreferences_ForceColor;
	public static String MonitoringPreferences_TransparencyLevel;
	public static String MonitoringPreferences_PollingIntervallMs;
	public static String MonitoringPreferences_SettingsTitle;
	public static String MonitoringPreferences_StartMonitoringWithoutAsking;

	public static String MonitoringDialog_EnableMonitoring;
	public static String MonitoringDialog_EnableMonitoringQuestion;
	public static String MonitoringDialog_Enable;
	public static String MonitoringDialog_No;

	public static String MonitoringWatchesView_WatchedElement;
	public static String MonitoringWatchesView_Type;
	public static String MonitoringWatchesView_Value;
	public static String MonitoringWatchesView_Comment;
	public static String MonitoringWatchesView_Force;
	public static String MonitoringWatchesView_ClearForce;
	public static String MonitoringWatchesView_ForceValue;
	public static String MonitoringWatchesView_TriggerEvent;

	public static String StructForceDialog_Title;
	public static String StructForceDialog_Force_Button_Text;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
