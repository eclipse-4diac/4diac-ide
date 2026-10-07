/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.query.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.Toggle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.bulkeditor.QueryUIPreferenceConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

/** Toggle button with a rounded frame that is filled while selected. */
public class QueryToggleButton extends Toggle {

	private static final Color COLOR_SELECTED_BG = QueryUIPreferenceConstants.getToggleButtonSelectedBackground();
	private static final Color COLOR_BORDER_DEFAULT = QueryUIPreferenceConstants.getToggleButtonDefaultBorder();
	private static final Color COLOR_BORDER_SELECTED = QueryUIPreferenceConstants.getToggleButtonSelectedBorder();
	private static final Color COLOR_DISABLED_BG = QueryUIPreferenceConstants.getToggleButtonDisabledBackground();
	private static final int ARC = 4;
	private static final int DISABLED_ALPHA = 160;

	private final Label label = new Label();
	private final ImageDescriptor imageDescriptor;
	private Image image;

	public QueryToggleButton(final ImageDescriptor imageDescriptor) {
		this.imageDescriptor = imageDescriptor;
		initContents();
	}

	public QueryToggleButton(final String text) {
		this.imageDescriptor = null;
		label.setText(text);
		initContents();
	}

	private void initContents() {
		label.setBorder(new MarginBorder(3, 5, 3, 5));
		setContents(label);
		setRolloverEnabled(true);
		setRequestFocusEnabled(false);
		setOpaque(false);
	}

	@Override
	protected void paintFigure(final Graphics graphics) {
		final Rectangle frame = getBounds().getCopy().shrink(1, 1);
		if (!isEnabled()) {
			graphics.setBackgroundColor(COLOR_DISABLED_BG);
			graphics.setAlpha(DISABLED_ALPHA);
			graphics.fillRoundRectangle(frame, ARC, ARC);
			graphics.setAlpha(255);
			graphics.setLineStyle(SWT.LINE_DOT);
		} else if (isSelected()) {
			graphics.setBackgroundColor(COLOR_SELECTED_BG);
			graphics.fillRoundRectangle(frame, ARC, ARC);
		}
		graphics.setForegroundColor(isEnabled() && isSelected() ? COLOR_BORDER_SELECTED : COLOR_BORDER_DEFAULT);
		graphics.setLineWidth(1);
		graphics.drawRoundRectangle(frame, ARC, ARC);
		graphics.setLineStyle(SWT.LINE_SOLID);
	}

	@Override
	public void addNotify() {
		super.addNotify();
		if (imageDescriptor != null && image == null) {
			image = imageDescriptor.createImage();
			label.setIcon(image);
		}
	}

	@Override
	public void removeNotify() {
		if (image != null) {
			label.setIcon(null);
			image.dispose();
			image = null;
		}
		super.removeNotify();
	}
}
