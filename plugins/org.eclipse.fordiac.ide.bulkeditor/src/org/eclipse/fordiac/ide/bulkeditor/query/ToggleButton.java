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
package org.eclipse.fordiac.ide.bulkeditor.query;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.Toggle;
import org.eclipse.fordiac.ide.bulkeditor.QueryUIPreferenceConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

public class ToggleButton extends Toggle {
	private static final Color COLOR_SELECTED_BG = QueryUIPreferenceConstants.getToggleButtonSelectedBackground();
	private static final Color COLOR_BORDER_DEFAULT = QueryUIPreferenceConstants.getToggleButtonDefaultBorder();
	private static final Color COLOR_BORDER_SELECTED = QueryUIPreferenceConstants.getToggleButtonSelectedBorder();
	private static final Color COLOR_DISABLED_BG = QueryUIPreferenceConstants.getToggleButtonDisabledBackground();

	private static final int ARC = 4;

	private Image managedImage;
	private ImageDescriptor imageDescriptor;
	private final Label label;

	public ToggleButton(final ImageDescriptor descriptor) {
		label = new Label();
		imageDescriptor = descriptor;
		managedImage = descriptor.createImage();
		label.setIcon(managedImage);
		initContents();
	}

	public ToggleButton(final String text) {
		label = new Label();
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
	protected void paintFigure(final Graphics g) {
		final var bounds = getBounds().getCopy().shrink(1, 1);

		final boolean sel = isSelected();
		final boolean en = isEnabled();

		if (!en) {
			g.setBackgroundColor(COLOR_DISABLED_BG);
			g.setAlpha(160);
			g.fillRoundRectangle(bounds, ARC, ARC);
			g.setAlpha(255);
		} else if (sel) {
			g.setBackgroundColor(COLOR_SELECTED_BG);
			g.fillRoundRectangle(bounds, ARC, ARC);
		}

		g.setForegroundColor(sel && en ? COLOR_BORDER_SELECTED : COLOR_BORDER_DEFAULT);
		g.setLineWidth(1);
		if (!en) {
			g.setLineStyle(SWT.LINE_DOT);
		}
		g.drawRoundRectangle(bounds, ARC, ARC);
		g.setLineStyle(SWT.LINE_SOLID);

		label.setEnabled(en);
	}

	@Override
	public void addNotify() {
		super.addNotify();
		if (imageDescriptor != null && (managedImage == null || managedImage.isDisposed())) {
			managedImage = imageDescriptor.createImage();
			label.setIcon(managedImage);
		}
	}

	@Override
	public void removeNotify() {
		if (managedImage != null && !managedImage.isDisposed()) {
			label.setIcon(null);
			managedImage.dispose();
			managedImage = null;
		}
		super.removeNotify();
	}
}