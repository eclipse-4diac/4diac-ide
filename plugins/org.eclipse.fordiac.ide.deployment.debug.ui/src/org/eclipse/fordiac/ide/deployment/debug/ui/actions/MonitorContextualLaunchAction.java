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
package org.eclipse.fordiac.ide.deployment.debug.ui.actions;

import org.eclipse.debug.ui.actions.ContextualLaunchAction;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentLaunchConfigurationDelegate;

public class MonitorContextualLaunchAction extends ContextualLaunchAction {

	public MonitorContextualLaunchAction() {
		super(DeploymentLaunchConfigurationDelegate.MONITOR_MODE);
	}
}
