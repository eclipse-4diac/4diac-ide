/*******************************************************************************
 * Copyright (c) 2026 Vikash Kumar Sinha
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Vikash Kumar Sinha - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.ui.breakpoint;

import java.util.Set;

import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTargetFactory;
import org.eclipse.fordiac.ide.deployment.debug.ui.Messages;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;

public class DeploymentToggleBreakpointsTargetFactory implements IToggleBreakpointsTargetFactory {

	private static final String TARGET_ID = "org.eclipse.fordiac.ide.debug.ui.toggleDeploymentBreakpointTarget"; //$NON-NLS-1$

	@Override
	public Set<String> getToggleTargets(final IWorkbenchPart part, final ISelection selection) {
		if (new DeploymentToggleBreakpointsTargetExtension().canToggleWatchpoints(part, selection)) {
			return Set.of(TARGET_ID);
		}
		return Set.of();
	}

	@Override
	public String getDefaultToggleTarget(final IWorkbenchPart part, final ISelection selection) {
		if (new DeploymentToggleBreakpointsTargetExtension().canToggleWatchpoints(part, selection)) {
			return TARGET_ID;
		}
		return null;
	}

	@Override
	public IToggleBreakpointsTarget createToggleTarget(final String targetID) {
		if (TARGET_ID.equals(targetID)) {
			return new DeploymentToggleBreakpointsTargetExtension();
		}
		return null;
	}

	@Override
	public String getToggleTargetName(final String targetID) {
		return Messages.ToggleDeploymentBreakpoint_Name;
	}

	@Override
	public String getToggleTargetDescription(final String targetID) {
		return Messages.ToggleDeploymentBreakpoint_Description;
	}
}