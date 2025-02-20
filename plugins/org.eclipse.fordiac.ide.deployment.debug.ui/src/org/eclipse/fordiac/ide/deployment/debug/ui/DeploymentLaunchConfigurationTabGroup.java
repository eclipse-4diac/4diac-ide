/*******************************************************************************
 * Copyright (c) 2024, 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.deployment.debug.ui;

import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentLaunchConfigurationDelegate;

public class DeploymentLaunchConfigurationTabGroup extends AbstractLaunchConfigurationTabGroup {

	@Override
	public void createTabs(final ILaunchConfigurationDialog dialog, final String mode) {
		if (ILaunchManager.DEBUG_MODE.equals(mode) || DeploymentLaunchConfigurationDelegate.MONITOR_MODE.equals(mode)) {
			setTabs(new DeploymentLaunchConfigurationTab(), new DeploymentLaunchWatchesTab(), new CommonTab());
		} else {
			setTabs(new DeploymentLaunchConfigurationTab(), new CommonTab());
		}
	}
}
