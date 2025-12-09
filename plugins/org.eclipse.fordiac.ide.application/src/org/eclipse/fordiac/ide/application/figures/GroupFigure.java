/*******************************************************************************
 * Copyright (c) 2022, 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.figures;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.draw2d.AdvancedLineBorder;
import org.eclipse.fordiac.ide.gef.figures.AbstractShadowBorder;
import org.eclipse.fordiac.ide.gef.figures.BorderedRoundedRectangle;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstants;
import org.eclipse.fordiac.ide.model.CoordinateConverter;

public class GroupFigure extends Figure {

	private final RoundedRectangle mainFigure = new BorderedRoundedRectangle();
	private InstanceCommentFigure commentFigure;
	private RoundedRectangle nameFigure;

	public GroupFigure() {
		createFigure();
	}

	private void createFigure() {
		createMainFigure();

		mainFigure.add(createCommentFigure(), new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));

		final GridLayout rootLayout = new GridLayout(1, false);
		rootLayout.verticalSpacing = -1; // this brings a slight overlap between name and main area to avoid drawing
											// artifacts on certain scaling factors
		rootLayout.horizontalSpacing = 0;
		rootLayout.marginHeight = 0;
		rootLayout.marginWidth = 0;
		setLayoutManager(rootLayout);
		add(mainFigure, new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL));

		createNameFigure();
		add(nameFigure, 0);

		setBorder(new GroupShadowBorder());
	}

	private void createMainFigure() {
		mainFigure.setOutline(false);
		mainFigure.setCornerDimensions(new Dimension(GefPreferenceConstants.CORNER_DIM, GefPreferenceConstants.CORNER_DIM));
		mainFigure.setFillXOR(false);
		mainFigure.setOpaque(false);

		final GridLayout mainLayout = new GridLayout(1, true);
		mainLayout.marginHeight = 0;
		mainLayout.marginWidth = 0;
		mainLayout.verticalSpacing = 0;
		mainLayout.horizontalSpacing = 0;
		mainFigure.setLayoutManager(mainLayout);
	}

	private IFigure createCommentFigure() {
		commentFigure = new InstanceCommentFigure();
		commentFigure.setCursor(Cursors.SIZEALL);
		final AdvancedLineBorder commentBorder = new AdvancedLineBorder(PositionConstants.SOUTH);
		commentFigure.setBorder(commentBorder);

		final Figure commentContainer = new Figure();
		commentContainer.setLayoutManager(new ToolbarLayout());

		final int lineHeight = (int) CoordinateConverter.INSTANCE.getLineHeight();
		int top = lineHeight / 2;
		final int bottom = top;
		if (top + bottom != lineHeight) {
			// we have a rounding error
			top += lineHeight - (top + bottom);
		}
		commentContainer.setBorder(new MarginBorder(top, 5, bottom, 5));
		commentContainer.add(commentFigure);
		return commentContainer;
	}

	private void createNameFigure() {
		final GridLayout nameLayout = new GridLayout(1, false);
		nameLayout.verticalSpacing = 0;
		nameLayout.horizontalSpacing = 0;
		nameLayout.marginHeight = 0;
		nameFigure = new BorderedRoundedRectangle();
		nameFigure.setCornerDimensions(new Dimension(GefPreferenceConstants.CORNER_DIM, GefPreferenceConstants.CORNER_DIM));
		nameFigure.setOutline(false);
		nameFigure.setLayoutManager(nameLayout);
	}

	public InstanceCommentFigure getCommentFigure() {
		return commentFigure;
	}

	public void setCommentFigure(final InstanceCommentFigure commentFigure) {
		this.commentFigure = commentFigure;
	}

	public RoundedRectangle getNameFigure() {
		return nameFigure;
	}

	public RoundedRectangle getMainFigure() {
		return mainFigure;
	}

	private static class GroupShadowBorder extends AbstractShadowBorder {

		private static Rectangle nameShadowRect = new Rectangle();
		private static Rectangle mainShadowRect = new Rectangle();

		@Override
		public void paintBackground(final IFigure figure, final Graphics graphics, final Insets insets) {
			Assert.isTrue(figure instanceof GroupFigure);
			final GroupFigure groupFigure = (GroupFigure) figure;

			final var backgroundColor = graphics.getBackgroundColor();
			final var alpha = graphics.getAlpha();
			graphics.setBackgroundColor(figure.getForegroundColor());

			nameShadowRect.setBounds(groupFigure.getNameFigure().getBounds()).expand(2, 2);
			mainShadowRect.setBounds(groupFigure.getMainFigure().getBounds()).expand(2, 2);

			drawShadowHalo(graphics, nameShadowRect, mainShadowRect);
			drawDropShadow(graphics, nameShadowRect, mainShadowRect);

			graphics.setBackgroundColor(backgroundColor);
			graphics.setAlpha(alpha);
		}

		private static void drawShadowHalo(final Graphics graphics, final Rectangle nameShadowRect,
				final Rectangle mainShadowRect) {
			graphics.setAlpha(SHADOW_ALPHA);
			drawShadowFigure(graphics, nameShadowRect, mainShadowRect);
			nameShadowRect.shrink(1, 1);
			mainShadowRect.shrink(1, 1);
			graphics.setAlpha(2 * SHADOW_ALPHA);
			drawShadowFigure(graphics, nameShadowRect, mainShadowRect);
		}

		private static void drawDropShadow(final Graphics graphics, final Rectangle topShadowRect,
				final Rectangle middleShadowRect) {
			graphics.setAlpha(SHADOW_ALPHA);
			final double horInc = 0.7; // emulate a roughly 30° shadow angle
			double horI = 0;
			for (int i = 0; i < SHADOW_SIZE; i++) {
				horI += horInc;
				topShadowRect.translate((int) horI, 1);
				middleShadowRect.translate((int) horI, 1);
				drawShadowFigure(graphics, topShadowRect, middleShadowRect);
				if (horI >= 1.0) {
					horI -= 1.0;
				}
			}
		}

		private static void drawShadowFigure(final Graphics graphics, final Rectangle topShadowRect,
				final Rectangle middleShadowRect) {
			graphics.fillRoundRectangle(topShadowRect, SHADOW_CORNER_RADIUS, SHADOW_CORNER_RADIUS);
			graphics.fillRoundRectangle(middleShadowRect, SHADOW_CORNER_RADIUS, SHADOW_CORNER_RADIUS);
		}

	}

}
