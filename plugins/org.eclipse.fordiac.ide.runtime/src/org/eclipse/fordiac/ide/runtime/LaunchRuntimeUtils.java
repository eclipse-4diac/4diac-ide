/*******************************************************************************
 * Copyright (c) 2009, 2010, 2013, 2014 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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

	/**
	 * Starts a new launch runtime configuration.
	 * 
	 * @param configname
	 *            the configuration name
	 * @param runtime
	 *            the path to the runtime
	 * @param location
	 *            the location of the runtime
	 * @param arguments
	 *            the runtime arguments
	 */
	public static ILaunch startRuntime(String configname, String runtime,
			String location, String arguments) {
		/** Launch configuration. */
		ILaunchConfiguration config;
		/** The launch configuration's working copy. */
		ILaunchConfigurationWorkingCopy wc;
		ILaunch launcht = null;

		// Get the default launch manager
		DebugPlugin debug = DebugPlugin.getDefault();
		ILaunchManager lm = debug.getLaunchManager();
		// Set launch configuration type to 'Program'
		ILaunchConfigurationType configType = lm
				.getLaunchConfigurationType("org.eclipse.ui.externaltools.ProgramLaunchConfigurationType"); //$NON-NLS-1$
		try {
			wc = configType.newInstance(null, configname);
			// Set necessary attributes for the launch configuration
			wc.setAttribute(
					"org.eclipse.debug.core.appendEnvironmentVariables", true); //$NON-NLS-1$
			wc.setAttribute("org.eclipse.ui.externaltools.ATTR_LOCATION", //$NON-NLS-1$
					runtime);
			wc.setAttribute("org.eclipse.ui.externaltools.ATTR_TOOL_ARGUMENTS", //$NON-NLS-1$
					arguments);
			wc.setAttribute(
					"org.eclipse.ui.externaltools.ATTR_WORKING_DIRECTORY", //$NON-NLS-1$
					location);

			config = wc.doSave();
			launcht = config.launch(ILaunchManager.RUN_MODE, null);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Activator.getDefault().logError(e.getMessage(), e);
			}
		} catch (CoreException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		return launcht;
	}
	
	private LaunchRuntimeUtils() {
		throw new UnsupportedOperationException("LaunchRuntimeUtils utility class should not be instantiated!"); //$NON-NLS-1$
	}

}
