/*******************************************************************************
 * Copyright (c) 2026 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.replaydebugging.ui.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Font;

/**
 * Draws event indices along the top of a timeline.
 * <p>
 * The ruler does not create a figure per label. It paints directly and only
 * covers the part of the timeline inside the current clip, so the cost depends
 * on the visible width and not on the number of events. Event positions are
 * taken from an {@link IEventAxis}, which is queried on every paint and never
 * cached.
 * <p>
 * Labels are drawn at multiples of a stride (1, 2, 5, 10, 20, 50, ...) chosen
 * so that neighbouring labels keep a minimum distance on screen. The stride
 * follows the zoom level, and because labels are always multiples of it, they
 * stay in place while zooming. Large values are abbreviated (for example
 * {@code 1.5K} or {@code 2M}), but only when this loses no precision.
 * <p>
 * The figure must be stretched to the width of the timeline it describes. It
 * converts the axis origin into its own coordinate space, so the two figures
 * can have different parents.
 */
public class EventIndexRulerFigure extends Figure implements FigureListener {

	private static final int TICK_HEIGHT = 4;
	private static final int TEXT_PADDING = 2;

	/** Minimum distance between two labels, in screen pixels. */
	private static final int MIN_LABEL_GAP_PX = 12;

	/** Number shown for the first event. Use 1 for one-based numbering. */
	private static final int INDEX_BASE = 0;

	private static final int[] MANTISSAS = { 1, 2, 5 };

	private static final String MILLION_SUFFIX = "M"; //$NON-NLS-1$
	private static final String THOUSAND_SUFFIX = "K"; //$NON-NLS-1$
	private static final String DECIMAL_SEPARATOR = "."; //$NON-NLS-1$

	private IEventAxis axis;

	public EventIndexRulerFigure() {
		setOpaque(false);
		setForegroundColor(ColorConstants.gray);
		setFont(JFaceResources.getDefaultFont());
	}

	/**
	 * Sets the axis that provides the event positions.
	 *
	 * @param newAxis the axis, or {@code null} to clear the ruler
	 */
	public void setAxis(final IEventAxis newAxis) {
		if (axis != null) {
			axis.getFigure().removeFigureListener(this);
		}
		axis = newAxis;
		if (axis != null) {
			axis.getFigure().addFigureListener(this);
		}
		repaint();
	}

	/**
	 * Repaints when the timeline is moved or resized, e.g. after events were added.
	 */
	@Override
	public void figureMoved(final IFigure source) {
		repaint();
	}

	/** Only the height matters, the width is given by the layout. */
	@Override
	public Dimension getPreferredSize(final int wHint, final int hHint) {
		final int textHeight = FigureUtilities.getFontMetrics(getFont()).getHeight();
		return new Dimension(1, TICK_HEIGHT + TEXT_PADDING + textHeight);
	}

	@Override
	protected void paintFigure(final Graphics g) {
		super.paintFigure(g);
		if (axis == null) {
			return;
		}
		final int count = axis.getEventCount();
		final double pitch = axis.getEventPitch();
		if (count <= 0 || pitch <= 0) {
			return;
		}

		final double scale = Math.max(g.getAbsoluteScale(), 1e-6);
		final Font font = getFont();
		final double originX = toRulerX(axis.getFirstEventCenterX());

		final long stride = chooseStride(count, pitch, scale, font);
		final int maxLabelWidth = maxLabelWidth(count, stride, font);

		// Restrict the work to the visible range, widened by one label so that
		// labels only partly inside the clip are still drawn.
		final Rectangle clip = g.getClip(new Rectangle());
		final long firstIdx = Math.max(0, (long) Math.floor((clip.x - maxLabelWidth - originX) / pitch));
		final long lastIdx = Math.min(count - 1L, (long) Math.ceil((clip.right() + maxLabelWidth - originX) / pitch));

		final int top = getBounds().y;
		long value = ceilToMultiple(firstIdx + INDEX_BASE, stride);
		for (; value - INDEX_BASE <= lastIdx; value += stride) {
			final long idx = value - INDEX_BASE;
			final int x = (int) Math.round(originX + idx * pitch);
			final String label = format(value);
			final int labelWidth = FigureUtilities.getTextWidth(label, font);
			g.drawLine(x, top, x, top + TICK_HEIGHT);
			g.drawText(label, x - labelWidth / 2, top + TICK_HEIGHT + TEXT_PADDING / 2);
		}
	}

	/**
	 * Converts an X coordinate of the axis figure into this figure's coordinate
	 * space. All spacing arithmetic happens in unzoomed coordinates, only this
	 * single point is translated. A {@link PrecisionPoint} avoids rounding errors
	 * at fractional zoom levels.
	 */
	private double toRulerX(final int axisX) {
		final PrecisionPoint p = new PrecisionPoint(axisX, 0);
		axis.getFigure().translateToAbsolute(p);
		translateToRelative(p);
		return p.preciseX();
	}

	/** Largest label value that is a multiple of the stride. */
	private static long largestTick(final int count, final long stride) {
		return ((INDEX_BASE + count - 1L) / stride) * stride;
	}

	/**
	 * Upper bound for the width of any label in the range. The full number has at
	 * least as many characters as any abbreviated one, but abbreviations can use
	 * wider glyphs (K, M, separator), so both forms of the largest tick are
	 * measured.
	 */
	private static int maxLabelWidth(final int count, final long stride, final Font font) {
		final long largest = largestTick(count, stride);
		return Math.max(FigureUtilities.getTextWidth(Long.toString(largest), font),
				FigureUtilities.getTextWidth(format(largest), font));
	}

	/**
	 * Returns the smallest stride whose labels keep the minimum gap. The widest
	 * label that can occur is used for the measurement, so the choice does not
	 * change while scrolling.
	 */
	private static long chooseStride(final int count, final double pitch, final double scale, final Font font) {
		for (long magnitude = 1;; magnitude *= 10) {
			for (final int mantissa : MANTISSAS) {
				final long stride = mantissa * magnitude;
				final int labelWidth = maxLabelWidth(count, stride, font);
				if (stride * pitch >= labelWidth + MIN_LABEL_GAP_PX / scale || stride >= count) {
					return stride;
				}
			}
		}
	}

	private static long ceilToMultiple(final long value, final long stride) {
		return ((value + stride - 1) / stride) * stride;
	}

	/**
	 * Formats a label. Values are abbreviated with K or M only if the result is
	 * exact, otherwise the full number is returned.
	 */
	static String format(final long n) {
		if (n >= 1_000_000 && n % 100_000 == 0) {
			return compact(n, 1_000_000, MILLION_SUFFIX);
		}
		if (n >= 1_000 && n % 100 == 0) {
			return compact(n, 1_000, THOUSAND_SUFFIX);
		}
		return Long.toString(n);
	}

	private static String compact(final long n, final long unit, final String suffix) {
		final long whole = n / unit;
		final long frac = (n % unit) / (unit / 10);
		return frac == 0 ? whole + suffix : whole + DECIMAL_SEPARATOR + frac + suffix;
	}
}
