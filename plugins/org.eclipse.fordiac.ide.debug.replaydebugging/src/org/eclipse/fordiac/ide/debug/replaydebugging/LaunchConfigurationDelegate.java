/*******************************************************************************
 * Copyright (c) 2025 Jose Cabral
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
package org.eclipse.fordiac.ide.debug.replaydebugging;

import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentLaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentLaunchConfigurationDelegate;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

/**
 * Launch delegate for the Replay Debugging configuration.
 *
 * Reads the configuration from the user and create the objects needed for
 * replay debugging
 */
public class LaunchConfigurationDelegate extends DeploymentLaunchConfigurationDelegate {

	public static final String ATTR_TRACE_PATH = "org.eclipse.fordiac.ide.debug.replaydebugging.ATTR_TRACE_PATH"; //$NON-NLS-1$
	public static final String ATTR_TRACE_PATH_DEFAULT = ""; //$NON-NLS-1$

	public static final String ATTR_REMOTE = "org.eclipse.fordiac.ide.debug.replaydebugging.ATTR_REMOTE"; //$NON-NLS-1$
	public static final boolean ATTR_REMOTE_DEFAULT = false;

	@Override
	public void launch(final ILaunchConfiguration configuration, final String mode, final ILaunch launch,
			final org.eclipse.core.runtime.IProgressMonitor monitor) {
		if (!ILaunchManager.DEBUG_MODE.equals(mode)) {
			return;
		}
		try {
			// To be able to handle several devices, this must be a list of paths, but also
			// matched to the
			// respective device
			final String path = configuration.getAttribute(ATTR_TRACE_PATH, ATTR_TRACE_PATH_DEFAULT);

			final AutomationSystem system = DeploymentLaunchConfigurationAttributes.getSystem(configuration);
			if (system == null) {
				throw new CoreException(Status.error("Cannot find system"));
			}
			final Set<INamedElement> selection = DeploymentLaunchConfigurationAttributes.getSelection(configuration,
					system);

			final boolean remote = configuration.getAttribute(ATTR_REMOTE, ATTR_REMOTE_DEFAULT);

			final IResource resource = DeploymentLaunchConfigurationAttributes.getSystemResource(configuration);
			launch.setAttribute(SYSTEM_FILE_ATTRIBUTE, resource.getFullPath().toString());

			final ReplayDebuggingTarget debugTarget = new ReplayDebuggingTarget(system, selection, launch, true, path,
					remote);
			debugTarget.start();

		} catch (final Exception e) {
			FordiacLogHelper.logError("Couldn't launch replay debugging target!", e); //$NON-NLS-1$
		}
	}
}