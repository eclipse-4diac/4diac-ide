/*******************************************************************************
 * Copyright (c) 2026 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.replaydebugging.ui;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public class Messages extends NLS {
	private static final String BUNDLE_NAME = Messages.class.getPackageName() + ".messages"; //$NON-NLS-1$
	public static String AddToComparisonCommand_Text;
	public static String RemoveFromComparisonCommand_Text;
	public static String OpenStatesComparisonHandler_OpenTableViewError;
	public static String StatesComparisonView_AlphabeticallyAscendingOrderLabel;
	public static String StatesComparisonView_AlphabeticallyDescendingOrderLabel;
	public static String StatesComparisonView_ColumnEnabledLabel;
	public static String StatesComparisonView_DatapointsHeader;
	public static String StatesComparisonView_RemoveAllColumnsLabel;
	public static String StatesComparisonView_RemoveColumnLabel;
	public static String StatesComparisonView_ShowOnlyDiffLabel;
	public static String StatesComparisonView_SortByDiffLabel;
	public static String StatesComparisonView_HideColumnColor;
	public static String StatesComparisonView_ShowColumnColor;
	public static String DeleteEventsCommand_Label;
	public static String DeleteTimelineCommand_Label;
	public static String LaunchConfigurationTab_BrowseButtonText;
	public static String LaunchConfigurationTab_ComponentsSelectionText;
	public static String LaunchConfigurationTab_LaunchConfigurationTabName;
	public static String LaunchConfigurationTab_RemoteText;
	public static String LaunchConfigurationTab_ReplayerSectionText;
	public static String LaunchConfigurationTab_SelectPathDialogText;
	public static String LaunchConfigurationTab_SystemSelectionButtonText;
	public static String LaunchConfigurationTab_TracePathSelectionText;
	public static String MoveDownCommand_Label;
	public static String MoveLeftCommand_Label;
	public static String MoveRightCommand_Label;
	public static String MoveUpCommand_Label;
	public static String NavigationRequest_Type;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
