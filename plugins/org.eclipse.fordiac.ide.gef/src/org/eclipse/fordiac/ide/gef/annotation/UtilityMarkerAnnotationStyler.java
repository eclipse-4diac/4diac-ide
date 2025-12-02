/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.annotation;

import org.eclipse.draw2d.IFigure;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

public class UtilityMarkerAnnotationStyler implements GraphicalAnnotationStyler {

	private static final String COLOR_PREDECESSOR = "org.eclipse.fordiac.ide.ui.PredecessorMarkerColor"; //$NON-NLS-1$
	private static final String COLOR_CONNECTION_SRC = "org.eclipse.fordiac.ide.ui.ConnectionSourceMarkerColor"; //$NON-NLS-1$

	public static final String TYPE_UTIL_PREDECESSOR = "org.eclipse.fordiac.ide.model.ui.annotation.util.predecessor"; //$NON-NLS-1$
	public static final String TYPE_UTIL_CONNECTION_SRC = "org.eclipse.fordiac.ide.model.ui.annotation.util.connectionSource"; //$NON-NLS-1$

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
		case TYPE_UTIL_PREDECESSOR -> getPredecessorColor();
		case TYPE_UTIL_CONNECTION_SRC -> getConnectionSourceColor();
		default -> null;
		};
	}

	@Override
	public Image getImage(final GraphicalAnnotation annotation) {
		return null;
	}

	@Override
	public Image getOverlayImage(final GraphicalAnnotation annotation) {
		return null;
	}

	private static Color getPredecessorColor() {
		return JFaceResources.getColorRegistry().get(COLOR_PREDECESSOR);
	}

	private static Color getConnectionSourceColor() {
		return JFaceResources.getColorRegistry().get(COLOR_CONNECTION_SRC);
	}

}
