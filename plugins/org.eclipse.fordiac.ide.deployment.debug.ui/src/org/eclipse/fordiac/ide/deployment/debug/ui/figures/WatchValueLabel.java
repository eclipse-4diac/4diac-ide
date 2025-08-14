/*******************************************************************************
 * Copyright (c)  2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial implementation
 *******************************************************************************/

package org.eclipse.fordiac.ide.deployment.debug.ui.figures;

import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.OrderedLayout;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.gef.draw2d.SetableAlphaLabel;
import org.eclipse.fordiac.ide.gef.figures.AbstractFreeformFigure;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.ui.preferences.ConnectionPreferenceValues;
import org.eclipse.fordiac.ide.ui.preferences.UIPreferenceConstants;

public class WatchValueLabel extends org.eclipse.draw2d.Container {

	public static final int MONITORING_VALUE_LR_MARGIN = 5;
	private static final Dimension NEGATION_POINT_DIMENSION = new Dimension(
			ConnectionPreferenceValues.NEGATION_POINT_DIAMETER, ConnectionPreferenceValues.NEGATION_POINT_DIAMETER);

	private Label labelFigure;

	public Label getLabelFigure() {
		return labelFigure;
	}

	public static IFigure getLabel(final IInterfaceElement model) {
		if (!hasNegatedInputConnection(model)) {
			return createLabelFigure(model.isIsInput());
		}
		final WatchValueLabel container = new WatchValueLabel(createContainerLayout());
		container.add(createNegationPointFigure());
		container.addLabel(createLabelFigure(true));
		return container;
	}

	private static Label createLabelFigure(final boolean isInput) {
		final SetableAlphaLabel label = new SetableAlphaLabel();
		label.setOpaque(true);
		label.setMinimumSize(new Dimension(50, 1));
		if (isInput) {
			label.setLabelAlignment(PositionConstants.RIGHT);
			label.setTextAlignment(PositionConstants.RIGHT);
		} else {
			label.setTextAlignment(PositionConstants.LEFT);
			label.setLabelAlignment(PositionConstants.LEFT);
		}
		label.setText(Messages.MonitoringEditPart_Not_Available);
		label.setBorder(new MarginBorder(0, MONITORING_VALUE_LR_MARGIN, 0, MONITORING_VALUE_LR_MARGIN));
		return label;
	}

	private static IFigure createNegationPointFigure() {
		return new AbstractFreeformFigure() {

			@Override
			protected Rectangle calculateFreeformExtent() {
				return new Rectangle(0, 0, 0, 0);
			}

			@Override
			protected void paintFigure(final Graphics graphics) {
				super.paintFigure(graphics);

				final Rectangle bounds = getBounds().getCopy();
				final Point ovalOrigin = bounds.getTopRight().translate(
						-ConnectionPreferenceValues.NEGATION_POINT_DIAMETER,
						bounds.height / 2 - ConnectionPreferenceValues.NEGATION_POINT_DIAMETER / 2);

				graphics.setBackgroundColor(UIPreferenceConstants.getBoolConnectorColor());
				graphics.fillOval(ovalOrigin.x, ovalOrigin.y, ConnectionPreferenceValues.NEGATION_POINT_DIAMETER,
						ConnectionPreferenceValues.NEGATION_POINT_DIAMETER);
			}

			@Override
			public Dimension getPreferredSize(final int wHint, final int hHint) {
				return NEGATION_POINT_DIMENSION;
			}
		};
	}

	private static boolean hasNegatedInputConnection(final IInterfaceElement ie) {
		if (ie == null || !ie.isIsInput() || ie.getInputConnections().isEmpty()) {
			return false;
		}
		return ie.getInputConnections().getFirst().isNegated();
	}

	private static FlowLayout createContainerLayout() {
		final FlowLayout layout = new FlowLayout();
		layout.setHorizontal(true);
		layout.setMinorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);
		layout.setMajorAlignment(OrderedLayout.ALIGN_BOTTOMRIGHT);
		layout.setMajorSpacing(0);
		layout.setMinorSpacing(0);
		layout.setStretchMinorAxis(true);
		return layout;
	}

	private WatchValueLabel(final FlowLayout layout) {
		super(layout);
	}

	private void addLabel(final Label label) {
		this.labelFigure = label;
		add(label);
	}
}
