/*******************************************************************************
 * Copyright (c) 2009, 2010, 2013, 2014 Profactor GmbH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Thomas Strasser, Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.runtime;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;

/**
 * The Class LaunchRuntimeUtils.
 */
public final class LaunchRuntimeUtils {

	public static final String ATTR_TOOL_ARGUMENTS = "org.eclipse.ui.externaltools.ATTR_TOOL_ARGUMENTS"; //$NON-NLS-1$

	/**
	 * Starts a new launch runtime configuration.
	 *
	 * @param configname the configuration name
	 * @param runtime    the path to the runtime
	 * @param location   the location of the runtime
	 * @param arguments  the runtime arguments
	 */
	public static ILaunch startRuntime(final String configname, final String runtime, final String location, final String arguments) {
		/** Launch configuration. */
		ILaunchConfiguration config;
		/** The launch configuration's working copy. */
		ILaunchConfigurationWorkingCopy wc;
		ILaunch launcht = null;

		// Get the default launch manager
		final DebugPlugin debug = DebugPlugin.getDefault();
		final ILaunchManager lm = debug.getLaunchManager();
		// Set launch configuration type to 'Program'
		final ILaunchConfigurationType configType = lm
				.getLaunchConfigurationType("org.eclipse.ui.externaltools.ProgramLaunchConfigurationType"); //$NON-NLS-1$
		try {
			wc = configType.newInstance(null, configname);
			// Set necessary attributes for the launch configuration
			wc.setAttribute("org.eclipse.debug.core.appendEnvironmentVariables", true); //$NON-NLS-1$
			wc.setAttribute("org.eclipse.ui.externaltools.ATTR_LOCATION", //$NON-NLS-1$
					runtime);
			wc.setAttribute(ATTR_TOOL_ARGUMENTS, arguments);
			wc.setAttribute("org.eclipse.ui.externaltools.ATTR_WORKING_DIRECTORY", //$NON-NLS-1$
					location);

			config = wc.doSave();
			launcht = config.launch(ILaunchManager.RUN_MODE, null);

			waitForOneSecond();
		} catch (final CoreException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		return launcht;
	}

	private static void waitForOneSecond() {
		try {
			Thread.sleep(1000);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();  // mark interruption
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}

	private LaunchRuntimeUtils() {
		throw new UnsupportedOperationException("LaunchRuntimeUtils utility class should not be instantiated!"); //$NON-NLS-1$
	}

}
