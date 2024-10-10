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
package org.eclipse.fordiac.ide.deployment.debug.ui;

import java.text.MessageFormat;

import org.eclipse.core.resources.IResource;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.fordiac.ide.debug.ui.EvaluatorDebugModelPresentation;
import org.eclipse.fordiac.ide.deployment.debug.IDeploymentDebugTarget;
import org.eclipse.fordiac.ide.deployment.debug.breakpoint.DeploymentWatchpoint;
import org.eclipse.swt.graphics.Image;

public class DeploymentDebugModelPresentation extends EvaluatorDebugModelPresentation {

	@Override
	public Image getImage(final Object element) {
		if (element instanceof final DeploymentWatchpoint watchpoint) {
			return DebugUITools.getImage(getWatchpointImageKey(watchpoint));
		}
		return super.getImage(element);
	}

	private static String getWatchpointImageKey(final DeploymentWatchpoint watchpoint) {
		if (watchpoint.isForceEnabled()) {
			if (watchpoint.isEnabled()) {
				return IDebugUIConstants.IMG_OBJS_MODIFICATION_WATCHPOINT;
			}
			return IDebugUIConstants.IMG_OBJS_MODIFICATION_WATCHPOINT_DISABLED;
		}
		if (watchpoint.isEnabled()) {
			return IDebugUIConstants.IMG_OBJS_ACCESS_WATCHPOINT;
		}
		return IDebugUIConstants.IMG_OBJS_ACCESS_WATCHPOINT_DISABLED;
	}

	@Override
	public String getText(final Object element) {
		if (element instanceof final IDeploymentDebugTarget target) {
			final StringBuilder label = new StringBuilder(target.getName());
			if (target.isTerminated()) {
				label.insert(0, Messages.DeploymentDebugModelPresentation_Terminated);
			} else if (target.isDisconnected()) {
				label.insert(0, Messages.DeploymentDebugModelPresentation_Disconnected);
			}
			return label.toString();
		}
		if (element instanceof final DeploymentWatchpoint watchpoint) {
			return getWatchpointText(watchpoint);
		}
		return super.getText(element);
	}

	private static String getWatchpointText(final DeploymentWatchpoint watchpoint) {
		final IResource resource = watchpoint.getMarker().getResource();
		if (resource == null) {
			return ""; //$NON-NLS-1$
		}
		final String location = watchpoint.getLocation();
		if (location.isEmpty()) {
			return resource.getName();
		}
		return MessageFormat.format(Messages.DeploymentDebugModelPresentation_WatchpointText, resource.getName(),
				location);
	}
}
