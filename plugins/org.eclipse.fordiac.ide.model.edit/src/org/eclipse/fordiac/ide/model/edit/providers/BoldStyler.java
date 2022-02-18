/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.edit.providers;

import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.TextStyle;

public class BoldStyler extends Styler {

	public static final BoldStyler INSTANCE_DEFAULT = new BoldStyler(JFaceResources.DEFAULT_FONT, null, null);

	private final String fontName;
	private final String foregroundColorName;
	private final String backgroundColorName;

	public BoldStyler(final String fontName, final String foregroundColorName, final String backgroundColorName) {
		this.fontName = fontName;
		this.foregroundColorName = foregroundColorName;
		this.backgroundColorName = backgroundColorName;
	}

	@Override
	public void applyStyles(final TextStyle textStyle) {
		final ColorRegistry colorRegistry = JFaceResources.getColorRegistry();
		final Font font = JFaceResources.getFontRegistry().getBold(fontName);
		if (foregroundColorName != null) {
			textStyle.foreground = colorRegistry.get(foregroundColorName);
		}
		if (backgroundColorName != null) {
			textStyle.background = colorRegistry.get(backgroundColorName);
		}
		textStyle.font = font;
	}
}