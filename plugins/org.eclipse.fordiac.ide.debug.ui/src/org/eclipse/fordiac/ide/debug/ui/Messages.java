/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.ui;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = Messages.class.getPackageName() + ".messages"; //$NON-NLS-1$
	public static String EvaluatorDebugFindAction_Text;
	public static String EvaluatorDebugFindDialog_Title;
	public static String EvaluatorVariableValueEditor_Exception;
	public static String EvaluatorVariableValueEditor_Invalid;
	public static String EvaluatorVariableValueEditor_Message;
	public static String EvaluatorVariableValueEditor_Title;
	public static String EvaluatorWatchExpressionFactoryAdapter_NoExpressionForVariable;
	public static String FordiacDebugPreferencePage_Description;
	public static String FordiacDebugPreferencePage_ValueMaxDisplayLength;
	public static String MainLaunchConfigurationTab_ConfigurationError;
	public static String MainLaunchConfigurationTab_ErrorInitializingArguments;
	public static String MainLaunchConfigurationTab_ErrorUpdatingArguments;
	public static String MainLaunchConfigurationTab_InvalidValueMessage;
	public static String MainLaunchConfigurationTab_InvalidValueTitle;

	public static String FBLaunchConfigurationTab_ClockInterval;
	public static String FBLaunchConfigurationTab_DebugTime;
	public static String FBLaunchConfigurationTab_ERROR_InvalidDebugTime;
	public static String FBLaunchConfigurationTab_Event;
	public static String FBLaunchConfigurationTab_IncrementClockAfterEachEventBySpecifiedAmount;
	public static String FBLaunchConfigurationTab_KeepDebuggerRunningWhenIdle;
	public static String FBLaunchConfigurationTab_RepeatEvent;

	public static String FBLaunchConfigurationTab_UseFixedClockWithSpecifiedTime;
	public static String FBLaunchConfigurationTab_UseSystemClock;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
