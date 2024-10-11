/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.deployment.debug;

import java.text.MessageFormat;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public class DeploymentLaunchConfigurationDelegate extends LaunchConfigurationDelegate {

	@Override
	public void launch(final ILaunchConfiguration configuration, final String mode, final ILaunch launch,
			final IProgressMonitor monitor) throws CoreException {
		final AutomationSystem system = DeploymentLaunchConfigurationAttributes.getSystem(configuration);
		final Set<INamedElement> selection = DeploymentLaunchConfigurationAttributes.getSelection(configuration,
				system);

		try {
			if (ILaunchManager.RUN_MODE.equals(mode)) {
				final DeploymentProcess process = new DeploymentProcess(system, selection, launch);
				process.start();
			} else if (ILaunchManager.DEBUG_MODE.equals(mode)) {
				final DeploymentDebugTarget debugTarget = new DeploymentDebugTarget(system, selection, launch, true);
				debugTarget.start();
			} else {
				throw new CoreException(Status.error(
						MessageFormat.format(Messages.DeploymentLaunchConfigurationDelegate_IllegalLaunchMode, mode)));
			}
		} catch (final DeploymentException e) {
			throw new CoreException(Status.error(Messages.DeploymentLaunchConfigurationDelegate_DeploymentError, e));
		}
	}

	@Override
	protected IProject[] getBuildOrder(final ILaunchConfiguration configuration, final String mode)
			throws CoreException {
		final IResource resource = DeploymentLaunchConfigurationAttributes.getSystemResource(configuration);
		if (resource != null) {
			final IProject project = resource.getProject();
			if (project != null) {
				return computeReferencedBuildOrder(new IProject[] { project });
			}
		}
		return super.getBuildOrder(configuration, mode);
	}

	@Override
	protected IProject[] getProjectsForProblemSearch(final ILaunchConfiguration configuration, final String mode)
			throws CoreException {
		return getBuildOrder(configuration, mode);
	}
}
