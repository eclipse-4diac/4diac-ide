/*******************************************************************************
 * Copyright (c) 2014, 2021 Profactor GbmH, fortiss GmbH,
 *               2023       Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Muddasir Shakil - Added double line for Adapter and Struct connection
 *   Prankur Agarwal - Added handling for truncating label according to the
 *   				   max size preference option value
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Font;

public class FBNetworkConnectionLabel extends Figure implements RotatableDecoration {

	public static final int HIDDEN_CON_LABEL_ALPHA = 150;
	private final Label label;
	private final boolean srcLabel;

	public FBNetworkConnectionLabel(final boolean srcLabel) {
		this.srcLabel = srcLabel;
		setLayoutManager(new ToolbarLayout(true));
		final RoundedRectangle rect = createBaseShape();
		add(rect);
		label = createLabel();
		rect.add(label);
	}

	protected static RoundedRectangle createBaseShape() {
		final RoundedRectangle rect = new RoundedRectangle();
		rect.setLayoutManager(new StackLayout());
		rect.setFill(true);
		rect.setAntialias(1);
		rect.setOutline(false);
		rect.setCornerDimensions(new Dimension(10, 10));
		rect.setAlpha(HIDDEN_CON_LABEL_ALPHA);
		return rect;
	}

	protected boolean isSrcLabel() {
		return srcLabel;
	}

	private static Label createLabel() {
		final Label label = new Label();
		label.setOpaque(false);
		label.setForegroundColor(ColorConstants.white);
		label.setFont(getLabelFont());
		label.setBorder(new MarginBorder(0, 2, 0, 2));
		return label;
	}

	public Label getLabel() {
		return label;
	}

	private static Font getLabelFont() {
		return JFaceResources.getFontRegistry()
				.get(org.eclipse.fordiac.ide.ui.preferences.UIPreferenceConstants.DIAGRAM_FONT);
	}

	@Override
	public void setReferencePoint(final Point p) {
		// we don't want this decorator to be rotated so that the number keeps readable
	}

	@Override
	public void setLocation(final Point p) {
		final Dimension preferredSize = super.getPreferredSize();
		if (!srcLabel) {
			p.x -= preferredSize.width;
		}
		p.y -= preferredSize.height / 2;
		super.setBounds(new Rectangle(p, preferredSize));
	}

}
