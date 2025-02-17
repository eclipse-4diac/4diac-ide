/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2019 - 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Ingo Hengy, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - extracted TransitionFigure code and changed to cubic spline
 *   Bianca Wiesmayr, Ernst Blecha - added tooltip
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.figures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.FBTypeEditorPreferenceConstants;
import org.eclipse.fordiac.ide.gef.draw2d.SetableAlphaLabel;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;

public class ECTransitionFigure extends SplineConnection {

	private static class TransitionOrderDecorator extends Ellipse implements RotatableDecoration {

		private static final int TRANSITON_ORDER_LABEL_FONT_SIZE = 7;
		private static final String TRANSITION_ORDER_LABEL_FONT = "TransitionOrderLabelFont"; //$NON-NLS-1$
		private final Label orderLabel;

		public TransitionOrderDecorator() {
			setLayoutManager(new StackLayout());
			setFill(true);
			setAntialias(1);
			setBackgroundColor(FBTypeEditorPreferenceConstants.getColor(FBTypeEditorPreferenceConstants.P_ECC_TRANSITION_COLOR));
			setOutline(false);

			orderLabel = new Label();
			orderLabel.setOpaque(false);
			orderLabel.setForegroundColor(ColorConstants.white);
			orderLabel.setFont(getOrderLabelFont());
			add(orderLabel);
		}

		private static Font getOrderLabelFont() {
			final FontData[] fontData = JFaceResources.getFontRegistry()
					.getFontData(org.eclipse.fordiac.ide.ui.preferences.UIPreferenceConstants.DIAGRAM_FONT).clone();
			Arrays.stream(fontData).forEach(fd -> fd.setHeight(TRANSITON_ORDER_LABEL_FONT_SIZE));
			JFaceResources.getFontRegistry().put(TRANSITION_ORDER_LABEL_FONT, fontData);
			return JFaceResources.getFont(TRANSITION_ORDER_LABEL_FONT);
		}

		@Override
		public void setReferencePoint(final Point p) {
			// we don't want this decorator to be rotated so that the number keeps readable
		}

		@Override
		public void setLocation(final Point p) {
			// Position the decorator centered on the start of the transition
			p.x -= getSize().width() / 2;
			p.y -= getSize().height() / 2;
			super.setLocation(p);
		}

		public void setText(final String val) {
			orderLabel.setText(val);
			if ("".equals(orderLabel.getText())) { //$NON-NLS-1$
				setSize(0, 0); // this hides the decorator
			} else {
				final int dim = orderLabel.getFont().getFontData()[0].getHeight() * 2;
				setSize(dim, dim);
			}
		}

	}

	private SetableAlphaLabel conditionBackground;
	private Label condition;
	private final TransitionOrderDecorator transitionOrderDecorator;

	public ECTransitionFigure(final ECTransition ecTransition) {
		setAntialias(SWT.ON);
		setForegroundColor(FBTypeEditorPreferenceConstants.getColor(FBTypeEditorPreferenceConstants.P_ECC_TRANSITION_COLOR));

		updateBendPoints(ecTransition);

		transitionOrderDecorator = new TransitionOrderDecorator();
		setSourceDecoration(transitionOrderDecorator);

		createConditionLabel(ecTransition.getConditionText());

		setTargetDecoration(createTargetDecorator());

		final ECTransitionToolTipFigure transitionTooltip = new ECTransitionToolTipFigure();
		transitionTooltip.setVisible(true);
		setToolTip(transitionTooltip);
	}

	public void setConditionText(final String conditionText) {
		condition.setText(conditionText);
		conditionBackground.setText(conditionText);
	}

	public void updateBendPoints(final ECTransition ecTransition) {
		final List<Bendpoint> bendPoints = new ArrayList<>();
		bendPoints.add(new AbsoluteBendpoint(ecTransition.getPosition().toScreenPoint()));
		getConnectionRouter().setConstraint(this, bendPoints);
	}

	public void setTransitionOrder(final String value) {
		transitionOrderDecorator.setText(value);
	}

	private static PolygonDecoration createTargetDecorator() {
		final PolygonDecoration rectDec = new PolygonDecoration();
		rectDec.setTemplate(PolygonDecoration.TRIANGLE_TIP);
		rectDec.setScale(7, 4);
		rectDec.setFill(true);
		return rectDec;
	}

	private static final int VERTICAL_MARGIN = 2;
	private static final int HORIZONTAL_MARGIN = 4;

	private void createConditionLabel(final String conditionText) {
		condition = new Label(conditionText);
		condition.setBorder(new MarginBorder(VERTICAL_MARGIN, HORIZONTAL_MARGIN, VERTICAL_MARGIN, HORIZONTAL_MARGIN));
		condition.setOpaque(false);

		conditionBackground = new SetableAlphaLabel();
		conditionBackground.setText(conditionText); // needed for correct size
		conditionBackground
				.setBorder(new MarginBorder(VERTICAL_MARGIN, HORIZONTAL_MARGIN, VERTICAL_MARGIN, HORIZONTAL_MARGIN));
		conditionBackground.setAlpha(190);
		conditionBackground.setOpaque(true);

		add(conditionBackground, new ConnectionLocator(this, ConnectionLocator.MIDDLE));
		add(condition, new ConnectionLocator(this, ConnectionLocator.MIDDLE));
	}

	@Override
	public ECTransitionToolTipFigure getToolTip() {
		return (ECTransitionToolTipFigure) super.getToolTip();
	}

	public Label getLabel() {
		return condition;
	}
}