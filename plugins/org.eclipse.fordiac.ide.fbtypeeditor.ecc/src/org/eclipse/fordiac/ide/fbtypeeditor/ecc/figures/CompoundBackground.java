/*******************************************************************************
 * Copyright (c) 2026 IBM Corporation and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Alois Zoitl     - copied from CompoundBorder and adjusted for the needs
 *                       of an AbstractBackground
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.figures;

import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.backgrounds.AbstractBackgroundBorder;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;

/**
 * CompoundBackground allows for the nesting of two borders. The nested borders
 * are referred to as the <i>inner</i> and <i>outer</i> borders.
 *
 * This will be upstreamed to GEF Classic for the upcoming GEF Classic 2026-06
 * release.
 */
public class CompoundBackground extends AbstractBackgroundBorder {

	/** The inner Border. */
	protected Border inner;
	/** The outer Border. */
	protected Border outer;

	/**
	 * Constructs a default CompoundBackground with no borders under it.
	 */
	public CompoundBackground() {
	}

	/**
	 * Constructs a CompoundBackground with the two borders specified as input.
	 *
	 * @param outer Border which is drawn on the outside
	 * @param inner Border which is drawn inside the outer border
	 *
	 * @since 2.0
	 */
	public CompoundBackground(final Border outer, final Border inner) {
		this.outer = outer;
		this.inner = inner;
	}

	/**
	 * Returns the inner border of this CompoundBackground.
	 *
	 * @return The inner border
	 * @since 2.0
	 */
	public Border getInnerBorder() {
		return inner;
	}

	/**
	 * Returns the total insets required to hold both the inner and outer borders of
	 * this CompoundBorder.
	 *
	 * @param figure Figure for which this is the border
	 * @return The total insets for this border
	 * @since 2.0
	 */
	@Override
	public Insets getInsets(final IFigure figure) {
		Insets insets = null;
		if (inner != null) {
			insets = inner.getInsets(figure);
		} else {
			insets = new Insets();
		}
		if (outer != null) {
			final Insets moreInsets = outer.getInsets(figure);
			insets = insets.getAdded(moreInsets);
		}
		return insets;
	}

	/**
	 * @see org.eclipse.draw2d.Border#getPreferredSize(IFigure)
	 */
	@Override
	public Dimension getPreferredSize(final IFigure fig) {
		final Dimension prefSize = new Dimension(inner.getPreferredSize(fig));
		final Insets outerInsets = outer.getInsets(fig);
		prefSize.expand(outerInsets.getWidth(), outerInsets.getHeight());
		prefSize.union(outer.getPreferredSize(fig));
		return prefSize;
	}

	/**
	 * Returns the outer border of this CompoundBorder.
	 *
	 * @return The outer border
	 * @since 2.0
	 */
	public Border getOuterBorder() {
		return outer;
	}

	/**
	 * Returns <code>true</code> if this border is opaque. Return value is dependent
	 * on the opaque state of both the borders it contains. Both borders have to be
	 * opaque for this border to be opaque. In the absence of any of the borders,
	 * this border is not opaque.
	 *
	 * @return <code>true</code> if this border is opaque
	 */
	@Override
	public boolean isOpaque() {
		return (inner != null && inner.isOpaque()) && (outer != null && outer.isOpaque());
	}

	@Override
	public void paint(final IFigure figure, final Graphics g, Insets insets) {
		if (outer != null) {
			g.pushState();
			outer.paint(figure, g, insets);
			g.popState();

			insets = insets.getAdded(outer.getInsets(figure));
		}
		if (inner != null) {
			inner.paint(figure, g, insets);
		}
	}

	@Override
	public void paintBackground(final IFigure figure, final Graphics g, Insets insets) {
		if (outer instanceof final AbstractBackgroundBorder outerBg) {
			g.pushState();
			outerBg.paintBackground(figure, g, insets);
			g.popState();
		}
		if (outer != null) {
			insets = insets.getAdded(outer.getInsets(figure));
		}
		if (inner instanceof final AbstractBackgroundBorder innerBg) {
			innerBg.paintBackground(figure, g, insets);
		}
	}
}
