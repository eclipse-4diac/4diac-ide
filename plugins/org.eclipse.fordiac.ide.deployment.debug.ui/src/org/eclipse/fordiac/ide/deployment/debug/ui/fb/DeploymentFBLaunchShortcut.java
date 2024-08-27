/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.deployment.debug.ui.fb;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.fordiac.ide.debug.ui.LaunchShortcut;
import org.eclipse.fordiac.ide.deployment.debug.fb.DeploymentFBLaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class DeploymentFBLaunchShortcut extends LaunchShortcut {

	@Override
	public void launch(final IResource resource, final ILaunchConfiguration configuration, final String mode) {
		try {
			configuration.launch(mode, new NullProgressMonitor());
		} catch (final CoreException e) {
			FordiacLogHelper.logError(
					String.format("Could not launch resource %s with mode %s", resource.getFullPath().toString(), mode), //$NON-NLS-1$
					e);
		}
	}

	@Override
	public String getLaunchConfigurationId() {
		return DeploymentFBLaunchConfigurationAttributes.ID;
	}
}
