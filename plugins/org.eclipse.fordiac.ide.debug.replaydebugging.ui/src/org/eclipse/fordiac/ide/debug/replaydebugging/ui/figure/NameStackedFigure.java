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
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.jface.resource.JFaceResources;

/**
 * @brief Figure representing a named container with a label on top and a
 *        content pane below it.
 *
 *        This figure is used to represent the devices and resources.
 */
public class NameStackedFigure extends RoundedRectangle {

	private final Label nameLabel;
	private final RoundedRectangle contentPane;

	private static final int SPACING = 20;
	private static final int MARGIN = 5;
	private static final Dimension CORNER_DIMENSION = new Dimension(15, 15);

	public NameStackedFigure(final String name) {
		final var layout = new ToolbarLayout(false);
		layout.setSpacing(SPACING);
		setLayoutManager(layout);
		setBorder(new MarginBorder(MARGIN));

		setCornerDimensions(CORNER_DIMENSION);
		setBackgroundColor(ColorConstants.white);

		nameLabel = new Label(name);
		nameLabel.setFont(JFaceResources.getHeaderFont());

		contentPane = new RoundedRectangle();
		final var contentLayout = new ToolbarLayout(false);
		contentPane.setLayoutManager(contentLayout);
		contentPane.setOpaque(true);
		contentPane.setOutline(false);
		contentPane.setLineWidth(0);
		contentPane.setBorder(null);

		// Add label first, then timeline container
		add(nameLabel);
		add(contentPane);
	}

	@Override
	public Dimension getPreferredSize(final int wHint, final int hHint) {
		final Dimension labelSize = nameLabel.getPreferredSize();
		final Dimension contentSize = contentPane.getPreferredSize();

		final int width = Math.max(labelSize.width, contentSize.width) + SPACING;
		final int height = labelSize.height + contentSize.height + SPACING * 2;

		return new Dimension(width, height);
	}

	public IFigure getContentPane() {
		return contentPane;
	}

	public void setName(final String name) {
		nameLabel.setText(name);
	}
}