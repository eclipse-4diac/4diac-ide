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
package org.eclipse.fordiac.ide.gef.annotation;

import org.eclipse.draw2d.IFigure;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class ProblemAnnotationStyler implements GraphicalAnnotationStyler {

	private static final String ERROR_COLOR = "org.eclipse.fordiac.ide.gef.errorColor"; //$NON-NLS-1$
	private static final String WARNING_COLOR = "org.eclipse.fordiac.ide.gef.warningColor"; //$NON-NLS-1$
	private static final String INFO_COLOR = "org.eclipse.fordiac.ide.gef.infoColor"; //$NON-NLS-1$

	@Override
	public void applyStyles(final IFigure figure, final GraphicalAnnotation annotation) {
		final Color annotationColor = getColor(annotation);
		if (annotationColor != null) {
			GraphicalAnnotationStyles.setAnnotationFeedbackBorder(figure, annotationColor);
		}
	}

	@Override
	public void removeStyles(final IFigure figure, final GraphicalAnnotation annotation) {
		GraphicalAnnotationStyles.removeAnnotationBorders(figure);
	}

	@Override
	public Color getColor(final GraphicalAnnotation annotation) {
		return switch (annotation.getType()) {
		case GraphicalAnnotation.TYPE_ERROR -> JFaceResources.getColorRegistry().get(ERROR_COLOR);
		case GraphicalAnnotation.TYPE_WARNING -> JFaceResources.getColorRegistry().get(WARNING_COLOR);
		case GraphicalAnnotation.TYPE_INFO -> JFaceResources.getColorRegistry().get(INFO_COLOR);
		default -> null;
		};
	}

	@Override
	public Image getImage(final GraphicalAnnotation annotation) {
		return switch (annotation.getType()) {
		case GraphicalAnnotation.TYPE_ERROR ->
			PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
		case GraphicalAnnotation.TYPE_WARNING ->
			PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK);
		case GraphicalAnnotation.TYPE_INFO ->
			PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_INFO_TSK);
		default -> null;
		};
	}

	@Override
	public Image getOverlayImage(final GraphicalAnnotation annotation) {
		return null;
	}

	public static Color getErrorAnnotationColor() {
		return JFaceResources.getColorRegistry().get(ERROR_COLOR);
	}
}
