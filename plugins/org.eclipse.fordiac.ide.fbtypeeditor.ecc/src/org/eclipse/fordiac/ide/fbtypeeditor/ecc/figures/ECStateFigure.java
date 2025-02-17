/*******************************************************************************
 * Copyright (c) 2008 - 2013 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2020        Johannes Kepler University Linz
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
 *******************************************************************************/

package org.eclipse.fordiac.ide.fbtypeeditor.ecc.figures;

import static org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.FBTypeEditorPreferenceConstants.MARGIN_HORIZONTAL;
import static org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.FBTypeEditorPreferenceConstants.MARGIN_VERTICAL;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.OrderedLayout;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.FBTypeEditorPreferenceConstants;
import org.eclipse.fordiac.ide.gef.figures.HorizontalLineFigure;
import org.eclipse.fordiac.ide.gef.figures.InteractionStyleFigure;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

public final class ECStateFigure extends Figure implements InteractionStyleFigure {

	private static final int STATE_ACTION_CONNECTOR_LENGTH = 15;
	private static final Insets STATE_INSET = new Insets(MARGIN_VERTICAL, MARGIN_HORIZONTAL, MARGIN_VERTICAL,
			MARGIN_HORIZONTAL);

	private Label nameLabel;
	private final Figure stateActionConnector = new HorizontalLineFigure(STATE_ACTION_CONNECTOR_LENGTH);
	private final Figure actionContainer = new Figure() {
		@Override
		public void add(IFigure figure, Object constraint, int index) {
			super.add(figure, constraint, index);
			setConstraint(figure, new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		}
	};

	public ECStateFigure(ECState state) {
		ToolbarLayout tbLayout = new ToolbarLayout();
		tbLayout.setStretchMinorAxis(false);
		tbLayout.setHorizontal(true);
		setLayoutManager(tbLayout);

		Figure stateRect = createStateRectangle();
		stateRect.add(createStateNameLabel(state));
		stateActionConnector.setForegroundColor(FBTypeEditorPreferenceConstants.getColor(FBTypeEditorPreferenceConstants.P_ECC_STATE_TEXT_COLOR));
		stateRect.add(stateActionConnector);
		add(stateRect);

		createActionContainerFigure();
		createStateCommentToolTip();
	}

	private static Figure createStateRectangle() {
		Figure stateRect = new Figure();
		FlowLayout layout = new FlowLayout();
		layout.setStretchMinorAxis(true);
		layout.setMajorSpacing(0);
		layout.setMinorSpacing(0);
		layout.setHorizontal(true);
		layout.setMinorAlignment(OrderedLayout.ALIGN_CENTER);
		stateRect.setLayoutManager(layout);
		return stateRect;
	}

	private void createStateCommentToolTip() {
		ECStateToolTipFigure stateTooltip = new ECStateToolTipFigure();
		stateTooltip.setVisible(true);
		setToolTip(stateTooltip);
	}

	private void createActionContainerFigure() {
		add(actionContainer);

		GridLayout gl = new GridLayout(2, false);
		gl.horizontalSpacing = -1;
		gl.verticalSpacing = 1;
		gl.marginWidth = 0;
		gl.marginHeight = 0;

		actionContainer.setLayoutManager(gl);
	}

	private Label createStateNameLabel(ECState state) {
		nameLabel = new Label() {
			@Override
			public Insets getInsets() {
				if (getBorder() != null) {
					return getBorder().getInsets(this);
				}
				return STATE_INSET;
			}

			@Override
			public void setBackgroundColor(Color bg) {
				if (getBorder() instanceof StartStateBorder) {
					((StartStateBorder) getBorder()).setColor(bg);
				} else {
					super.setBackgroundColor(bg);
				}
			}
		};
		nameLabel.setText(state.getName());
		nameLabel.setForegroundColor(FBTypeEditorPreferenceConstants.getColor(FBTypeEditorPreferenceConstants.P_ECC_STATE_TEXT_COLOR));
		nameLabel.setOpaque(true);
		if (state.isStartState()) {
			nameLabel.setBorder(new StartStateBorder(FBTypeEditorPreferenceConstants.getColor(FBTypeEditorPreferenceConstants.P_ECC_STATE_COLOR)));
		} else {
			nameLabel.setBackgroundColor(FBTypeEditorPreferenceConstants.getColor(FBTypeEditorPreferenceConstants.P_ECC_STATE_COLOR));
		}
		return nameLabel;
	}

	@Override
	public ECStateToolTipFigure getToolTip() {
		return (ECStateToolTipFigure) super.getToolTip();
	}

	public void setHasAction(final boolean hasAction) {
		stateActionConnector.setVisible(hasAction);
	}

	public Figure getContentPane() {
		return actionContainer;
	}

	public Label getNameLabel() {
		return nameLabel;
	}

	public Figure getLine() {
		return stateActionConnector;
	}

	@Override
	public int getIntersectionStyle(Point location) {
		Rectangle bounds = nameLabel.getBounds().getCopy();
		bounds.x = bounds.x + 3;
		bounds.y = bounds.y + 3;
		bounds.width = bounds.width - 6;
		bounds.height = bounds.height - 6;
		if (bounds.intersects(new Rectangle(location, new Dimension(1, 1)))) {
			return InteractionStyleFigure.REGION_CONNECTION; // connection
		}
		return InteractionStyleFigure.REGION_DRAG; // move/drag
	}

	private static class StartStateBorder extends LineBorder {

		private static final int DOUBLE_BORDER_WIDTH = 4;
		private static final int START_STATE_INSET_SIZE = 2;
		private static final Insets START_STATE_INSET = new Insets(START_STATE_INSET_SIZE);
		private static final Insets DOUBLE_BORDER_INSET = new Insets(DOUBLE_BORDER_WIDTH).add(START_STATE_INSET);

		public StartStateBorder(Color color) {
			super();
			setWidth(DOUBLE_BORDER_WIDTH);
			setColor(color);
		}

		@Override
		public Insets getInsets(final IFigure figure) {
			return DOUBLE_BORDER_INSET;
		}

	}
}