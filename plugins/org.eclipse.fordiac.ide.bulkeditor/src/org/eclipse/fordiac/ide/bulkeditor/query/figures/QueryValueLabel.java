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

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.fordiac.ide.bulkeditor.QueryUIPreferenceConstants;
import org.eclipse.swt.graphics.Color;

/** Framed label showing an editable value of a query element. */
public final class QueryValueLabel extends Label {

	private static final Color COLOR_BACKGROUND = QueryUIPreferenceConstants.getDefaultQueryBackground();
	private static final Color COLOR_BORDER = QueryUIPreferenceConstants.getValueBorder();
	private static final int TEXT_PADDING = 10;

	private final int minimumWidth;
	private final boolean growsWithText;

	public static QueryValueLabel createFixedWidth(final int width) {
		return new QueryValueLabel(width, false);
	}

	public static QueryValueLabel createGrowing(final int minimumWidth) {
		return new QueryValueLabel(minimumWidth, true);
	}

	private QueryValueLabel(final int minimumWidth, final boolean growsWithText) {
		this.minimumWidth = minimumWidth;
		this.growsWithText = growsWithText;
		setOpaque(true);
		setBackgroundColor(COLOR_BACKGROUND);
		setBorder(new LineBorder(COLOR_BORDER, 1));
	}

	@Override
	public Dimension getPreferredSize(final int wHint, final int hHint) {
		final Dimension size = super.getPreferredSize(wHint, hHint).getCopy();
		size.width = growsWithText ? Math.max(minimumWidth, size.width + TEXT_PADDING) : minimumWidth;
		return size;
	}
}
