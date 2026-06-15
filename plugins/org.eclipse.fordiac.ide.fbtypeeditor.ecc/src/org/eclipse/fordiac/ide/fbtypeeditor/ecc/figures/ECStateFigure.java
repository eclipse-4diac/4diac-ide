/*******************************************************************************
 * Copyright (c) 2008 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                    Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Ingo Hegny, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr, Alois Zoitl
 *     - redesigned ECC
 *   Alois Zoitl - modernized and reworked ECC look
 *******************************************************************************/

package org.eclipse.fordiac.ide.fbtypeeditor.ecc.figures;

import static org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.FBTypeEditorPreferenceConstants.MARGIN_HORIZONTAL;
import static org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.FBTypeEditorPreferenceConstants.MARGIN_VERTICAL;

import org.eclipse.draw2d.CompoundBorder;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.backgrounds.AbstractBackgroundBorder;
import org.eclipse.draw2d.backgrounds.shadows.RectangleDropShadowBorder;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.FBTypeEditorPreferenceConstants;
import org.eclipse.fordiac.ide.gef.figures.InteractionStyleFigure;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

public final class ECStateFigure extends Figure implements InteractionStyleFigure {

	private static final int ACTION_SEPERATION = 1;

	private static final int SPINE_WIDTH = 4;

	private static final Insets STATE_INSET = new Insets(MARGIN_VERTICAL, MARGIN_HORIZONTAL, MARGIN_VERTICAL,
			MARGIN_HORIZONTAL);

	private ECStateNameLabel nameLabel;
	private final Figure spine = new Figure();
	private final Figure actionContainer = new Figure() {
		@Override
		public void add(final IFigure figure, final Object constraint, final int index) {
			super.add(figure, constraint, index);
			setConstraint(figure, new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		}
	};

	public ECStateFigure(final ECState state) {
		final ToolbarLayout tbLayout = new ToolbarLayout();
		tbLayout.setStretchMinorAxis(false);
		tbLayout.setHorizontal(true);
		tbLayout.setSpacing(-SPINE_WIDTH / 2); // this makes state name label overlap the spine
		setLayoutManager(tbLayout);

		add(createStateNameLabel(state));
		add(createActionContainerFigure());
		createStateCommentToolTip();
	}

	private void createStateCommentToolTip() {
		final ECStateToolTipFigure stateTooltip = new ECStateToolTipFigure();
		stateTooltip.setVisible(true);
		setToolTip(stateTooltip);
	}

	private IFigure createActionContainerFigure() {
		final Figure actionRootFigure = new Figure();

		final ToolbarLayout layout = new ToolbarLayout(true);
		layout.setSpacing(0);
		layout.setStretchMinorAxis(true);
		actionRootFigure.setLayoutManager(layout);

		spine.setPreferredSize(new Dimension(SPINE_WIDTH, -1));
		spine.setOpaque(true);
		spine.setBackgroundColor(FBTypeEditorPreferenceConstants.getEccStateSpineColor());
		actionRootFigure.add(spine);

		final GridLayout gl = new GridLayout(2, false); // two columns one for algorithms one for output events
		gl.horizontalSpacing = ACTION_SEPERATION;
		gl.verticalSpacing = ACTION_SEPERATION;
		gl.marginWidth = 0;
		gl.marginHeight = 0;

		actionContainer.setLayoutManager(gl);
		actionContainer.setOpaque(true);
		actionContainer.setBackgroundColor(FBTypeEditorPreferenceConstants.getEccStateSpineColor());
		actionRootFigure.add(actionContainer);
		return actionRootFigure;
	}

	private Label createStateNameLabel(final ECState state) {
		nameLabel = new ECStateNameLabel();
		nameLabel.setText(state.getName());
		nameLabel.setOpaque(true);
		if (state.isStartState()) {
			nameLabel.setBorder(new CompoundBackground(new RectangleDropShadowBorder(),
					new StartStateBorder(FBTypeEditorPreferenceConstants.getEccStateColor())));
			nameLabel.setForegroundColor(FBTypeEditorPreferenceConstants.getEccAlgorithmTextColor());
			nameLabel.setBackgroundColor(FBTypeEditorPreferenceConstants.getEccAlgorithmColor());
		} else {
			nameLabel.setBorder(new RectangleDropShadowBorder());
			nameLabel.setBackgroundColor(FBTypeEditorPreferenceConstants.getEccStateColor());
			nameLabel.setForegroundColor(FBTypeEditorPreferenceConstants.getEccStateTextColor());
		}
		return nameLabel;
	}

	@Override
	public ECStateToolTipFigure getToolTip() {
		return (ECStateToolTipFigure) super.getToolTip();
	}

	public void setHasAction(final boolean hasAction) {
		spine.setVisible(hasAction);
		actionContainer.setVisible(hasAction);
	}

	public Figure getContentPane() {
		return actionContainer;
	}

	public Label getNameLabel() {
		return nameLabel;
	}

	@Override
	protected void paintChildren(final Graphics graphics) {
		super.paintChildren(graphics);
		// draw name label body last so that it partly covers the spine
		nameLabel.paintBody(graphics);
	}

	@Override
	public int getIntersectionStyle(final Point location) {
		final Rectangle bounds = nameLabel.getBounds().getCopy();
		bounds.x = bounds.x + 3;
		bounds.y = bounds.y + 3;
		bounds.width = bounds.width - 6;
		bounds.height = bounds.height - 6;
		if (bounds.intersects(new Rectangle(location, new Dimension(1, 1)))) {
			return InteractionStyleFigure.REGION_CONNECTION; // connection
		}
		return InteractionStyleFigure.REGION_DRAG; // move/drag
	}

	private static final class ECStateNameLabel extends Label {
		@Override
		public Insets getInsets() {
			if (getBorder() instanceof CompoundBorder) {
				return getBorder().getInsets(this);
			}
			return STATE_INSET;
		}

		@Override
		protected void paintFigure(final Graphics graphics) {
			// draw only background border
			if (getBorder() instanceof final AbstractBackgroundBorder abstractBackground) {
				abstractBackground.paintBackground(this, graphics, NO_INSETS);
			}
			if (getBorder() instanceof CompoundBackground) {
				// if we are the start state the body has to be painted first to be covered by
				// the start state border
				doPaintBody(graphics);
			}
		}

		public void paintBody(final Graphics graphics) {
			if (!(getBorder() instanceof CompoundBackground)) {
				// if we are not the start state we draw the body last to get the overlap with
				// the spine
				doPaintBody(graphics);
			}
		}

		private void doPaintBody(final Graphics graphics) {
			graphics.setForegroundColor(getForegroundColor());
			graphics.setBackgroundColor(getBackgroundColor());
			graphics.fillRectangle(getBounds());
			final Point curTextLocation = getTextLocation();
			final int tx = bounds.x + curTextLocation.x;
			final int ty = bounds.y + curTextLocation.y;
			graphics.drawText(getSubStringText(), tx, ty);
		}
	}

	private static class StartStateBorder extends LineBorder {

		private static final int DOUBLE_BORDER_WIDTH = 4;
		private static final int START_STATE_INSET_SIZE = 2;
		private static final Insets START_STATE_INSET = new Insets(START_STATE_INSET_SIZE);
		private static final Insets DOUBLE_BORDER_INSET = new Insets(DOUBLE_BORDER_WIDTH).add(START_STATE_INSET);

		public StartStateBorder(final Color color) {
			setWidth(DOUBLE_BORDER_WIDTH);
			setColor(color);
		}

		@Override
		public Insets getInsets(final IFigure figure) {
			return DOUBLE_BORDER_INSET;
		}

	}
}