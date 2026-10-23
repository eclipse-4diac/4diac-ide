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
	private final Figure body;
	private IFigure header;

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
		contentPane.setLayoutManager(new ToolbarLayout(false));
		contentPane.setOpaque(true);
		contentPane.setOutline(false);
		contentPane.setLineWidth(0);
		contentPane.setBorder(null);

		// header (ruler) and content share one zero-spacing column,
		// so the ruler sits directly on top of the events
		body = new Figure();
		final var bodyLayout = new ToolbarLayout(false);
		bodyLayout.setStretchMinorAxis(true);
		bodyLayout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
		body.setLayoutManager(bodyLayout);
		body.add(contentPane);

		add(nameLabel);
		add(body);
	}

	@Override
	public Dimension getPreferredSize(final int wHint, final int hHint) {
		final Dimension labelSize = nameLabel.getPreferredSize();
		final Dimension bodySize = body.getPreferredSize();

		final int width = Math.max(labelSize.width, bodySize.width) + SPACING;
		final int height = labelSize.height + bodySize.height + SPACING * 2;

		return new Dimension(width, height);
	}

	public IFigure getContentPane() {
		return contentPane;
	}

	/**
	 * Optional figure shown between the name and the content (e.g. an index ruler).
	 */
	public void setHeader(final IFigure newHeader) {
		if (header != null) {
			body.remove(header);
		}
		header = newHeader;
		if (header != null) {
			body.add(header, 0);
		}
	}

	public void setName(final String name) {
		nameLabel.setText(name);
	}
}
