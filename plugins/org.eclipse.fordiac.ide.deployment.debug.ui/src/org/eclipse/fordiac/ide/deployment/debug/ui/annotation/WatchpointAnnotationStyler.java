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
package org.eclipse.fordiac.ide.deployment.debug.ui.annotation;

import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.IFigure;
import org.eclipse.fordiac.ide.deployment.debug.breakpoint.DeploymentWatchpoint;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotation;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationStyler;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationStyles;
import org.eclipse.fordiac.ide.gef.draw2d.OverlayAlphaLabel;
import org.eclipse.fordiac.ide.gef.figures.FBShape;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

public class WatchpointAnnotationStyler implements GraphicalAnnotationStyler {

	@Override
	public void applyStyles(final IFigure figure, final GraphicalAnnotation annotation) {
		if (figure instanceof final FBShape fbShape && FigureUtilities.isAncestor(fbShape, fbShape.getTypeLabel())) {
			fbShape.getTypeLabel().setOverlayIcon(getOverlayImage(annotation));
		} else {
			final Image image = getImage(annotation);
			if (image != null) {
				GraphicalAnnotationStyles.setAnnotationImageBorder(figure, image);
			}
		}
	}

	@Override
	public void removeStyles(final IFigure figure, final GraphicalAnnotation annotation) {
		if (figure instanceof final OverlayAlphaLabel overlayLabel) {
			overlayLabel.setOverlayIcon(null);
		} else {
			GraphicalAnnotationStyles.removeAnnotationBorders(figure);
		}
	}

	@Override
	public Color getColor(final GraphicalAnnotation annotation) {
		return null;
	}

	@Override
	public Image getImage(final GraphicalAnnotation annotation) {
		final boolean disabled = isDisabled(annotation);
		final boolean force = isForce(annotation);
		if (force) {
			if (disabled) {
				return DebugUITools.getImage(IDebugUIConstants.IMG_OBJS_MODIFICATION_WATCHPOINT_DISABLED);
			}
			return DebugUITools.getImage(IDebugUIConstants.IMG_OBJS_MODIFICATION_WATCHPOINT);
		}
		if (disabled) {
			return FordiacImage.ICON_WATCHPOINT_DISABLED.getImage();
		}
		return FordiacImage.ICON_WATCHPOINT.getImage();
	}

	@Override
	public Image getOverlayImage(final GraphicalAnnotation annotation) {
		final boolean disabled = isDisabled(annotation);
		final boolean force = isForce(annotation);
		if (force) {
			if (disabled) {
				return FordiacImage.ICON_WATCHPOINT_FORCE_DISABLED_OVERLAY.getImage();
			}
			return FordiacImage.ICON_WATCHPOINT_FORCE_OVERLAY.getImage();
		}
		if (disabled) {
			return FordiacImage.ICON_WATCHPOINT_DISABLED_OVERLAY.getImage();
		}
		return FordiacImage.ICON_WATCHPOINT_OVERLAY.getImage();
	}

	private static boolean isDisabled(final GraphicalAnnotation annotation) {
		return annotation.getAttribute(IBreakpoint.ENABLED) instanceof final Boolean enabled && !enabled.booleanValue();
	}

	private static boolean isForce(final GraphicalAnnotation annotation) {
		return annotation.getAttribute(DeploymentWatchpoint.FORCE_ENABLED) instanceof final Boolean forceEnabled
				&& forceEnabled.booleanValue();
	}
}
