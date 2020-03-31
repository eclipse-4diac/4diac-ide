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
import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Vector;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences.PreferenceGetter;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Path;

public class ECTransitionFigure extends PolylineConnection {

	private static final double TRANSITION_CONTROL_POINT_VECTOR_LENGTH = 0.3;

	private class TransitionOrderDecorator extends Ellipse implements RotatableDecoration {

		private static final int TRANSITON_ORDER_LABEL_FONT_SIZE = 7;
		private static final String TRANSITION_ORDER_LABEL_FONT = "TransitionOrderLabelFont"; //$NON-NLS-1$
		private final Label orderLabel;

		public TransitionOrderDecorator() {
			super();
			setLayoutManager(new StackLayout());
			setFill(true);
			setAntialias(1);
			setBackgroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ECC_TRANSITION_COLOR));
			setOutline(false);

			orderLabel = new Label();
			orderLabel.setOpaque(false);
			orderLabel.setForegroundColor(ColorConstants.white);
			orderLabel.setFont(getOrderLabelFont());
			add(orderLabel);
		}

		private Font getOrderLabelFont() {
			FontData[] fontData = JFaceResources.getFontRegistry()
					.getFontData(org.eclipse.fordiac.ide.ui.preferences.PreferenceConstants.DIAGRAM_FONT).clone();
			Arrays.stream(fontData).forEach(fd -> fd.setHeight(TRANSITON_ORDER_LABEL_FONT_SIZE));
			JFaceResources.getFontRegistry().put(TRANSITION_ORDER_LABEL_FONT, fontData);
			return JFaceResources.getFont(TRANSITION_ORDER_LABEL_FONT);
		}

		@Override
		public void setReferencePoint(Point p) {
			// we don't want this decorator to be rotated so that the number keeps readable
		}

		@Override
		public void setLocation(Point p) {
			// Position the decorator centered on the start of the transition
			p.x -= getSize().width() / 2;
			p.y -= getSize().height() / 2;
			super.setLocation(p);
		}

		public void setText(String val) {
			orderLabel.setText(val);
			if ("".equals(orderLabel.getText())) { //$NON-NLS-1$
				setSize(0, 0); // this hides the decorator
			} else {
				int dim = orderLabel.getFont().getFontData()[0].getHeight() * 2;
				setSize(dim, dim);
			}
		}

	}

	private Label condition;
	private final TransitionOrderDecorator transitionOrderDecorator;

	public ECTransitionFigure(ECTransition ecTransition) {
		setAntialias(SWT.ON);
		setForegroundColor(PreferenceGetter.getColor(PreferenceConstants.P_ECC_TRANSITION_COLOR));

		setConnectionRouter(new BendpointConnectionRouter());
		updateBendPoints(ecTransition);

		transitionOrderDecorator = new TransitionOrderDecorator();
		setSourceDecoration(transitionOrderDecorator);

		createConditionLabel(ecTransition.getConditionText());

		setTargetDecoration(createTargetDecorator());

		ECTransitionToolTipFigure transitionTooltip = new ECTransitionToolTipFigure();
		transitionTooltip.setVisible(true);
		setToolTip(transitionTooltip);
	}

	public void setConditionText(String conditionText) {
		condition.setText(conditionText);
	}

	public void updateBendPoints(ECTransition ecTransition) {
		List<Bendpoint> bendPoints = new ArrayList<>();
		bendPoints.add(new AbsoluteBendpoint(ecTransition.getX(), ecTransition.getY()));
		getConnectionRouter().setConstraint(this, bendPoints);
	}

	public void setTransitionOrder(String value) {
		transitionOrderDecorator.setText(value);
	}

	private static PolygonDecoration createTargetDecorator() {
		PolygonDecoration rectDec = new PolygonDecoration();
		rectDec.setTemplate(PolygonDecoration.TRIANGLE_TIP);
		rectDec.setScale(7, 4);
		rectDec.setFill(true);
		return rectDec;
	}

	private void createConditionLabel(String conditionText) {
		condition = new Label(conditionText);
		condition.setBorder(new MarginBorder(3, 6, 3, 6));
		condition.setOpaque(true);
		add(condition, new ConnectionLocator(this, ConnectionLocator.MIDDLE));
	}

	@Override
	protected void outlineShape(Graphics g) {
		Path p = getPath();
		g.drawPath(p);
		p.dispose();
	}

	@Override
	public Rectangle getBounds() {
		float[] pathBounds = new float[4];
		Path p = getPath();
		p.getBounds(pathBounds);
		p.dispose();
		Rectangle pathRect = new Rectangle((int) pathBounds[0], (int) pathBounds[1], (int) pathBounds[2],
				(int) pathBounds[3]);
		return super.getBounds().getUnion(pathRect);
	}

	private Path getPath() {
		Path p = new Path(null);
		if (3 == getPoints().size()) {
			p.moveTo(getStart().x, getStart().y);
			Vector startEnd = new Vector((float) getEnd().x - getStart().x, (float) getEnd().y - getStart().y);
			Vector startMid = new Vector((float) getPoints().getMidpoint().x - getStart().x,
					(float) getPoints().getMidpoint().y - getStart().y);
			Vector midEnd = new Vector((float) getEnd().x - getPoints().getMidpoint().x,
					(float) getEnd().y - getPoints().getMidpoint().y);

			Vector startEndNorm = startEnd.getDivided((startEnd.getLength() < 0.0001) ? 1.0 : startEnd.getLength());
			Vector startEnd30 = startEndNorm
					.getMultiplied(startMid.getLength() * TRANSITION_CONTROL_POINT_VECTOR_LENGTH);
			Vector startMid30 = startMid.getMultiplied(TRANSITION_CONTROL_POINT_VECTOR_LENGTH);
			Vector midEnd30 = midEnd.getMultiplied(TRANSITION_CONTROL_POINT_VECTOR_LENGTH);

			p.cubicTo(getStart().x + (float) startMid30.x, getStart().y + (float) startMid30.y,
					getPoints().getMidpoint().x - (float) startEnd30.x,
					getPoints().getMidpoint().y - (float) startEnd30.y, getPoints().getMidpoint().x,
					getPoints().getMidpoint().y);

			p.cubicTo(getPoints().getMidpoint().x + (float) startEnd30.x,
					getPoints().getMidpoint().y + (float) startEnd30.y, getEnd().x - (float) midEnd30.x,
					getEnd().y - (float) midEnd30.y, getEnd().x, getEnd().y);
		}
		return p;
	}

	@Override
	public ECTransitionToolTipFigure getToolTip() {
		return (ECTransitionToolTipFigure) super.getToolTip();
	}
}