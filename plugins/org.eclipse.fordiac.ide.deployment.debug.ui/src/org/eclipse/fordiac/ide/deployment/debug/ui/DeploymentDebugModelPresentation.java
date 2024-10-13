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
import org.eclipse.fordiac.ide.deployment.debug.watch.IVarDeclarationWatch;
import org.eclipse.fordiac.ide.deployment.debug.watch.IWatch;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

public class DeploymentDebugModelPresentation extends EvaluatorDebugModelPresentation implements IColorProvider {

	public static final String FORCE_COLOR = "org.eclipse.fordiac.ide.deployment.debug.ui.forceColor"; //$NON-NLS-1$
	public static final String WATCH_COLOR = "org.eclipse.fordiac.ide.deployment.debug.ui.watchColor"; //$NON-NLS-1$
	public static final String WATCH_ERROR_COLOR = "org.eclipse.fordiac.ide.deployment.debug.ui.watchErrorColor"; //$NON-NLS-1$

	@Override
	public Color getForeground(final Object element) {
		return null;
	}

	@Override
	public Color getBackground(final Object element) {
		if (element instanceof final IWatch watch) {
			if (!watch.isAlive()) {
				return getWatchErrorColor();
			}
			if (element instanceof final IVarDeclarationWatch variableWatch && variableWatch.isForced()) {
				return getForceColor();
			}
		}
		return null;
	}

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

	public static Color getForceColor() {
		return JFaceResources.getColorRegistry().get(FORCE_COLOR);
	}

	public static Color getWatchColor() {
		return JFaceResources.getColorRegistry().get(WATCH_COLOR);
	}

	public static Color getWatchErrorColor() {
		return JFaceResources.getColorRegistry().get(WATCH_ERROR_COLOR);
	}
}
