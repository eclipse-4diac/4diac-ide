/*******************************************************************************
 * Copyright (c) 2023, 2024 Martin Erich Jobst, Primetals Technologies GmbH
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

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.CompoundBorder;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.gef.EditPart;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

public final class GraphicalAnnotationStyles {

	private record GraphicalAnnotationStyle(String annotationType, GraphicalAnnotationStyler styler, int layer)
			implements Comparable<GraphicalAnnotationStyle> {

		@Override
		public int compareTo(final GraphicalAnnotationStyle o) {
			return Integer.compare(layer, o.layer);
		}
	}

	private static final String GRAPHICAL_ANNOTATION_STYLES_EXTENSION_POINT_ID = "org.eclipse.fordiac.ide.gef.graphicalAnnotationStyle"; //$NON-NLS-1$
	private static final String ANNOTATION_TYPE_ATTRIBUTE = "annotationType"; //$NON-NLS-1$
	private static final String STYLER_ATTRIBUTE = "styler"; //$NON-NLS-1$
	private static final String LAYER_ATTRIBUTE = "layer"; //$NON-NLS-1$

	private static final Map<String, GraphicalAnnotationStyle> styles = loadStyles();

	public static Image getAnnotationImage(final GraphicalAnnotation annotation) {
		final GraphicalAnnotationStyle style = getAnnotationStyle(annotation);
		if (style != null) {
			return style.styler().getImage(annotation);
		}
		return null;
	}

	public static Image getAnnotationOverlayImage(final GraphicalAnnotation annotation) {
		final GraphicalAnnotationStyle style = getAnnotationStyle(annotation);
		if (style != null) {
			return style.styler().getOverlayImage(annotation);
		}
		return null;
	}

	public static EditPart getAnnotationEditPart(final GraphicalAnnotation annotation) {
		final GraphicalAnnotationStyle style = getAnnotationStyle(annotation);
		if (style != null) {
			return style.styler().getEditPart(annotation);
		}
		return null;
	}

	public static void updateAnnotationFeedback(final IFigure annonFigure, final Object target,
			final GraphicalAnnotationModelEvent event) {
		updateAnnotationFeedback(annonFigure, target, event, annotation -> true);
	}

	@SafeVarargs
	public static void updateAnnotationFeedback(final IFigure annonFigure, final Object target,
			final GraphicalAnnotationModelEvent event, final Predicate<GraphicalAnnotation>... filters) {
		updateAnnotationFeedback(annonFigure, target, event,
				Stream.of(filters).reduce(Predicate::or).orElse(annotation -> true));
	}

	public static void updateAnnotationFeedback(final IFigure annonFigure, final Object target,
			final GraphicalAnnotationModelEvent event, final Predicate<GraphicalAnnotation> filter) {
		// remove styles for removed annotations
		event.getRemoved().stream().filter(filter).map(GraphicalAnnotationStyles::getStyledAnnotation)
				.filter(Objects::nonNull)
				.forEachOrdered(entry -> entry.getValue().styler().removeStyles(annonFigure, entry.getKey()));
		// apply styles for all current annotations
		event.getModel().getAnnotations(target).stream().filter(filter)
				.map(GraphicalAnnotationStyles::getStyledAnnotation).filter(Objects::nonNull)
				.sorted(Map.Entry.comparingByValue())
				.forEachOrdered(entry -> entry.getValue().styler().applyStyles(annonFigure, entry.getKey()));
	}

	public static void addAnnotationBorder(final IFigure annonFigure, final AnnotationBorder annonBorder) {
		final var border = annonFigure.getBorder();
		if (border == null) {
			annonFigure.setBorder(annonBorder);
		} else {
			annonFigure.setBorder(new AnnotationCompoundBorder(border, annonBorder));
		}
	}

	public static <T extends AnnotationBorder> Optional<T> findAnnotationBorder(final IFigure annonFigure,
			final Class<T> borderClass) {
		return findAnnotationBorder(annonFigure.getBorder(), borderClass);
	}

	private static <T extends AnnotationBorder> Optional<T> findAnnotationBorder(final Border border,
			final Class<T> borderClass) {
		if (borderClass.isInstance(border)) {
			return Optional.of(borderClass.cast(border));
		}
		if (border instanceof final AnnotationCompoundBorder compBorder) {
			return findAnnotationBorder(compBorder.getOuterBorder(), borderClass)
					.or(() -> findAnnotationBorder(compBorder.getInnerBorder(), borderClass));
		}
		return Optional.empty();
	}

	public static void removeAnnotationBorders(final IFigure annonFigure) {
		var border = annonFigure.getBorder();
		while (border instanceof final AnnotationCompoundBorder compBorder) {
			border = compBorder.getOuterBorder();
		}
		if (border instanceof AnnotationBorder) {
			annonFigure.setBorder(null);
		} else {
			annonFigure.setBorder(border);
		}
	}

	public static void setAnnotationFeedbackBorder(final IFigure annonFigure, final Color color) {
		findAnnotationBorder(annonFigure, AnnotationFeedbackBorder.class)
				.ifPresentOrElse(border -> border.setColor(color), () -> {
					if (annonFigure instanceof PolylineConnection) {
						addAnnotationBorder(annonFigure, new AnnotationFeedbackConnectionBorder(color));
					} else {
						addAnnotationBorder(annonFigure, new AnnotationFeedbackBorder(color));
					}
				});
		annonFigure.repaint();
	}

	public static void setAnnotationImageBorder(final IFigure annonFigure, final Image image) {
		findAnnotationBorder(annonFigure, AnnotationImageBorder.class).ifPresentOrElse(border -> border.setImage(image),
				() -> addAnnotationBorder(annonFigure, new AnnotationImageBorder(image)));
		annonFigure.repaint();
	}

	public interface AnnotationBorder extends Border {
		// marker interface
	}

	public static class AnnotationFeedbackBorder extends LineBorder implements AnnotationBorder {
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

	public static class AnnotationFeedbackConnectionBorder extends AnnotationFeedbackBorder {
		private static final int LINE_DISTANCE = 2;

		public AnnotationFeedbackConnectionBorder(final Color color) {
			super(color);
		}

		@Override
		public void paint(final IFigure figure, final Graphics graphics, final Insets insets) {
			if (figure instanceof final PolylineConnection conn) {
				final PointList upper = new PointList();
				final PointList lower = new PointList();
				final PointList base = conn.getPoints();
				final int distance = conn.getLineWidth() / 2 + LINE_DISTANCE;
				int dx = 0;
				int dy = 0;
				Point p1 = base.getFirstPoint();
				Point p2 = p1;
				// calculate offset points for border
				for (int i = 1; i < base.size(); i++) {
					p1 = p2;
					p2 = base.getPoint(i);
					dx = calcDX(p1, p2, dx, distance);
					dy = calcDY(p1, p2, dy, distance);
					upper.addPoint(p1.x + dx, p1.y + dy);
					lower.addPoint(p1.x - dx, p1.y - dy);
				}
				// last point
				dx = calcDX(p2, p1, 0, distance);
				dy = calcDY(p2, p1, 0, distance);
				upper.addPoint(p2.x - dx, p2.y - dy);
				lower.addPoint(p2.x + dx, p2.y + dy);

				graphics.setLineStyle(Graphics.LINE_SOLID);
				graphics.setLineWidth(getWidth());
				graphics.setXORMode(false);
				graphics.setForegroundColor(getColor());
				graphics.setBackgroundColor(getColor());
				graphics.drawPolyline(upper);
				graphics.drawPolyline(lower);
				graphics.setAlpha(ANNOTATION_FILL_ALPHA);
				graphics.setLineWidth(conn.getLineWidth() * 2 + LINE_DISTANCE);
				graphics.drawPolyline(base);

			} else {
				super.paint(figure, graphics, insets);
			}
		}

		private static int calcDX(final Point p1, final Point p2, final int dx, final int distance) {
			if (p2.y - p1.y < 0) {
				return distance;
			}
			if (p2.y - p1.y > 0) {
				return -distance;
			}
			return dx;
		}

		private static int calcDY(final Point p1, final Point p2, int dy, final int distance) {
			if (p2.x - p1.x < 0) {
				dy = -distance;
			} else if (p2.x - p1.x > 0) {
				dy = distance;
			}
			return dy;
		}
	}

	public static class AnnotationImageBorder extends AbstractBorder implements AnnotationBorder {
		private static final Insets INSETS = new Insets();
		private Image image;

		public AnnotationImageBorder(final Image image) {
			this.image = Objects.requireNonNull(image);
		}

		@Override
		public Insets getInsets(final IFigure figure) {
			// we want 0 insets so that the feedback border is not changing the size of the
			// annotated figure
			return INSETS;
		}

		@Override
		public void paint(final IFigure figure, final Graphics graphics, final Insets insets) {
			final Rectangle rect = getPaintRectangle(figure, insets);
			final int x = rect.x;
			final int y = rect.y;
			graphics.drawImage(image, x, y);
		}

		public Image getImage() {
			return image;
		}

		public void setImage(final Image image) {
			this.image = image;
		}
	}

	public static final class AnnotationCompoundBorder extends CompoundBorder implements AnnotationBorder {

		public AnnotationCompoundBorder(final Border outer, final Border inner) {
			super(outer, inner);
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

	private static GraphicalAnnotationStyle getAnnotationStyle(final GraphicalAnnotation annotation) {
		return styles.get(annotation.getType());
	}

	private static Map.Entry<GraphicalAnnotation, GraphicalAnnotationStyle> getStyledAnnotation(
			final GraphicalAnnotation annotation) {
		final GraphicalAnnotationStyle style = getAnnotationStyle(annotation);
		if (style != null) {
			return Map.entry(annotation, style);
		}
		return null;
	}

	private static Map<String, GraphicalAnnotationStyle> loadStyles() {
		return Stream
				.of(Platform.getExtensionRegistry().getExtensionPoint(GRAPHICAL_ANNOTATION_STYLES_EXTENSION_POINT_ID)
						.getExtensions())
				.map(IExtension::getConfigurationElements).flatMap(Stream::of)
				.map(GraphicalAnnotationStyles::loadStyles).filter(Objects::nonNull)
				.collect(Collectors.toUnmodifiableMap(GraphicalAnnotationStyle::annotationType, Function.identity()));
	}

	private static GraphicalAnnotationStyle loadStyles(final IConfigurationElement element) {
		final String annotationType = element.getAttribute(ANNOTATION_TYPE_ATTRIBUTE);
		if (annotationType == null || annotationType.isEmpty()) {
			log("Missing annotation type", element); //$NON-NLS-1$
			return null;
		}
		final GraphicalAnnotationStyler styler = SafeRunner
				.run(() -> (GraphicalAnnotationStyler) element.createExecutableExtension(STYLER_ATTRIBUTE));
		if (styler == null) {
			log("Invalid annotation styler", element); //$NON-NLS-1$
			return null;
		}
		final int layer = Integer.parseInt(element.getAttribute(LAYER_ATTRIBUTE));
		return new GraphicalAnnotationStyle(annotationType, styler, layer);
	}

	private static void log(final String message, final IConfigurationElement element) {
		Platform.getLog(GraphicalAnnotationStyles.class)
				.error(String.format("%s in extension %s from plugin %s", message, //$NON-NLS-1$
						element.getDeclaringExtension().getExtensionPointUniqueIdentifier(),
						element.getDeclaringExtension().getContributor().getName()));
	}

	private GraphicalAnnotationStyles() {
		throw new UnsupportedOperationException();
	}
}
