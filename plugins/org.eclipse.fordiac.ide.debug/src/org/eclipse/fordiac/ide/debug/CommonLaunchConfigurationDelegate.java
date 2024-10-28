/*******************************************************************************
 * Copyright (c) 2022, 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.debug;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.fordiac.ide.model.eval.Evaluator;

public abstract class CommonLaunchConfigurationDelegate extends LaunchConfigurationDelegate {

	@SuppressWarnings("static-method")
	protected void launch(final Evaluator evaluator, final ILaunchConfiguration configuration, final String mode,
			final ILaunch launch, final IResource resource, final IProgressMonitor monitor) throws CoreException {
		if (ILaunchManager.RUN_MODE.equals(mode)) {
			final EvaluatorProcess process = new EvaluatorProcess(configuration.getName(), evaluator, launch);
			process.start();
		} else if (ILaunchManager.DEBUG_MODE.equals(mode)) {
			final EvaluatorDebugTarget debugTarget = new EvaluatorDebugTarget(configuration.getName(), evaluator,
					launch, resource);
			if (LaunchConfigurationAttributes.isStopOnFirstLine(configuration)) {
				debugTarget.getDebugger().setSuspendOnFirstLine(true);
			}
			debugTarget.start();
		} else {
			throw new CoreException(Status.error("Illegal launch mode: " + mode)); //$NON-NLS-1$
		}
	}

	@Override
	protected IProject[] getBuildOrder(final ILaunchConfiguration configuration, final String mode)
			throws CoreException {
		final IResource resource = LaunchConfigurationAttributes.getResource(configuration);
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
