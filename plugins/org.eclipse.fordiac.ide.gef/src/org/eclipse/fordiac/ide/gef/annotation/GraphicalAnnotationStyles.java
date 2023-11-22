/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst, Primetals Technologies GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Alois Zoitl  - added annotation style for GEF Figures
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.annotation;

import java.util.Set;
import java.util.function.Predicate;

import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.CompoundBorder;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public final class GraphicalAnnotationStyles {

	public static final Color WARNING_YELLOW = new Color(246, 211, 89);
	public static final Color ERROR_RED = new Color(255, 42, 69);

	public static Color getAnnotationColor(final Set<GraphicalAnnotation> annotations) {
		return getAnnotationColor(annotations, annotation -> true);
	}

	public static Color getAnnotationColor(final Set<GraphicalAnnotation> annotations,
			final Predicate<GraphicalAnnotation> filter) {
		if (containsType(annotations, filter, GraphicalAnnotation.TYPE_ERROR)) {
			return ERROR_RED;
		}
		if (containsType(annotations, filter, GraphicalAnnotation.TYPE_WARNING)) {
			return WARNING_YELLOW;
		}
		return null;
	}

	public static Color getAnnotationColor(final GraphicalAnnotation annotation) {
		if (annotation.getType().equals(GraphicalAnnotation.TYPE_ERROR)) {
			return ERROR_RED;
		}
		if (annotation.getType().equals(GraphicalAnnotation.TYPE_WARNING)) {
			return WARNING_YELLOW;
		}
		return null;
	}

	public static Image getAnnotationImage(final Set<GraphicalAnnotation> annotations) {
		return getAnnotationImage(annotations, annotation -> true);
	}

	public static Image getAnnotationImage(final Set<GraphicalAnnotation> annotations,
			final Predicate<GraphicalAnnotation> filter) {
		if (containsType(annotations, filter, GraphicalAnnotation.TYPE_ERROR)) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
		}
		if (containsType(annotations, filter, GraphicalAnnotation.TYPE_WARNING)) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK);
		}
		if (containsType(annotations, filter, GraphicalAnnotation.TYPE_INFO)) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_INFO_TSK);
		}
		return null;
	}

	public static Image getAnnotationImage(final GraphicalAnnotation annotation) {
		if (annotation.getType().equals(GraphicalAnnotation.TYPE_ERROR)) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
		}
		if (annotation.getType().equals(GraphicalAnnotation.TYPE_WARNING)) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK);
		}
		if (annotation.getType().equals(GraphicalAnnotation.TYPE_INFO)) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_INFO_TSK);
		}
		return null;
	}

	public static Styler getAnnotationStyle(final Set<GraphicalAnnotation> annotations) {
		return getAnnotationStyle(annotations, annotation -> true);
	}

	public static Styler getAnnotationStyle(final Set<GraphicalAnnotation> annotations,
			final Predicate<GraphicalAnnotation> filter) {
		if (containsType(annotations, filter, GraphicalAnnotation.TYPE_ERROR)) {
			return ErrorStyler.ERROR_STYLE;
		}
		if (containsType(annotations, filter, GraphicalAnnotation.TYPE_WARNING)) {
			return ErrorStyler.WARNING_STYLE;
		}
		return null;
	}

	public static Styler getAnnotationStyle(final GraphicalAnnotation annotation) {
		if (annotation.getType().equals(GraphicalAnnotation.TYPE_ERROR)) {
			return ErrorStyler.ERROR_STYLE;
		}
		if (annotation.getType().equals(GraphicalAnnotation.TYPE_WARNING)) {
			return ErrorStyler.WARNING_STYLE;
		}
		return null;
	}

	public static void updateAnnotationFeedback(final IFigure annonFigure, final Object target,
			final GraphicalAnnotationModelEvent event) {
		updateAnnotationFeedback(annonFigure, target, event, annotation -> true);
	}

	public static void updateAnnotationFeedback(final IFigure annonFigure, final Object target,
			final GraphicalAnnotationModelEvent event, final Predicate<GraphicalAnnotation> filter) {
		final Color annotationColor = GraphicalAnnotationStyles
				.getAnnotationColor(event.getModel().getAnnotations(target), filter);
		if (annotationColor != null) {
			setAnnotationBorder(annonFigure, annotationColor);
		} else {
			removeAnnotationBorder(annonFigure);
		}
	}

	private static void setAnnotationBorder(final IFigure annonFigure, final Color annotationColor) {
		var border = annonFigure.getBorder();
		if (border == null || border instanceof AnnotationFeedbackBorder) {
			annonFigure.setBorder(new AnnotationFeedbackBorder(annotationColor));
		} else {
			if (border instanceof final AnnotationCompoundBorder compBorder) {
				// we are updating the annotation border of an annotated figure
				border = compBorder.getOuterBorder();
			}
			annonFigure.setBorder(new AnnotationCompoundBorder(border, annotationColor));
		}
	}

	private static void removeAnnotationBorder(final IFigure annonFigure) {
		final var border = annonFigure.getBorder();
		if (border instanceof AnnotationFeedbackBorder) {
			annonFigure.setBorder(null);
		} else if (border instanceof final AnnotationCompoundBorder compBorder) {
			annonFigure.setBorder(compBorder.getOuterBorder());
		}
	}

	private static boolean containsType(final Set<GraphicalAnnotation> annotations,
			final Predicate<GraphicalAnnotation> filter, final String type) {
		return annotations.stream().filter(filter).anyMatch(annotation -> annotation.getType().equals(type));
	}

	private static class ErrorStyler extends Styler {

		private static final Styler ERROR_STYLE = new ErrorStyler(ERROR_RED);
		private static final Styler WARNING_STYLE = new ErrorStyler(WARNING_YELLOW);

		private final Color underlineColor;

		private ErrorStyler(final Color underlineColor) {
			this.underlineColor = underlineColor;
		}

		@Override
		public void applyStyles(final TextStyle textStyle) {
			textStyle.underline = true;
			textStyle.underlineColor = underlineColor;
			textStyle.underlineStyle = SWT.UNDERLINE_ERROR;
		}
	}

	public static final class AnnotationFeedbackBorder extends LineBorder {
		private static final Insets INSETS = new Insets();
		private static final int FEEDBACK_BORDER_LINE_WIDTH = 2;
		public static final int ANNOTATION_FILL_ALPHA = 90;

		public AnnotationFeedbackBorder(final Color color) {
			super(color, FEEDBACK_BORDER_LINE_WIDTH);
		}

		@Override
		public Insets getInsets(final IFigure figure) {
			// we want 0 insets so that the feedback border is not changing the size of the
			// annotated figure
			return INSETS;
		}

		@Override
		public void paint(final IFigure figure, final Graphics graphics, final Insets insets) {
			tempRect.setBounds(getPaintRectangle(figure, insets));
			tempRect.expand(1, 1);
			graphics.setClip(tempRect.getExpanded(getWidth(), getWidth()));
			graphics.setLineStyle(Graphics.LINE_SOLID);
			graphics.setLineWidth(getWidth());
			graphics.setXORMode(false);
			graphics.setForegroundColor(getColor());
			graphics.setBackgroundColor(getColor());
			graphics.drawRoundRectangle(tempRect, DiagramPreferences.CORNER_DIM, DiagramPreferences.CORNER_DIM);
			graphics.setAlpha(ANNOTATION_FILL_ALPHA);
			graphics.fillRoundRectangle(tempRect, DiagramPreferences.CORNER_DIM, DiagramPreferences.CORNER_DIM);
		}
	}

	public static final class AnnotationCompoundBorder extends CompoundBorder {

		public AnnotationCompoundBorder(final Border outer, final Color annotationColor) {
			super(outer, new AnnotationFeedbackBorder(annotationColor));
		}

		@Override
		public void paint(final IFigure figure, final Graphics g, final Insets insets) {
			// the paint method of the CompoundBorder reduces the size for the inner by the
			// size of the outer border. As we want to draw the outer on top of everything
			// for visual consistency we need an own paint method
			if (outer != null) {
				g.pushState();
				outer.paint(figure, g, insets);
				g.popState();
			}
			if (inner != null) {
				inner.paint(figure, g, insets);
			}
		}
	}

	private GraphicalAnnotationStyles() {
		throw new UnsupportedOperationException();
	}
}
