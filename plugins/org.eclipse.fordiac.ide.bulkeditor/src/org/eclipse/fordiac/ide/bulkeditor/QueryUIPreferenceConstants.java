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
package org.eclipse.fordiac.ide.bulkeditor;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

public class QueryUIPreferenceConstants {

	private static final String HEADER_FONT = "org.eclipse.fordiac.ide.bulkeditor.query.headerFont"; //$NON-NLS-1$

	public static Font getHeaderFont() {
		return JFaceResources.getFontRegistry().get(HEADER_FONT);
	}

	private static final String BACKGROUND_COLOR = "org.eclipse.fordiac.ide.bulkeditor.query.backgroundColor"; //$NON-NLS-1$
	private static final String HEADER_BACKGROUND_COLOR = "org.eclipse.fordiac.ide.bulkeditor.query.headerBackgroundColor"; //$NON-NLS-1$
	private static final String NEGATED_HEADER_BACKGROUND_COLOR = "org.eclipse.fordiac.ide.bulkeditor.query.negatedHeaderBackgroundColor"; //$NON-NLS-1$
	private static final String HEADER_FOREGROUND_COLOR = "org.eclipse.fordiac.ide.bulkeditor.query.headerForegroundColor"; //$NON-NLS-1$
	private static final String VALUE_BORDER_COLOR = "org.eclipse.fordiac.ide.bulkeditor.query.valueBorderColor"; //$NON-NLS-1$

	public static Color getDefaultQueryBackground() {
		return JFaceResources.getColorRegistry().get(BACKGROUND_COLOR);
	}

	public static Color getHeaderBackgroundColor() {
		return JFaceResources.getColorRegistry().get(HEADER_BACKGROUND_COLOR);
	}

	public static Color getNegatedHeaderBackgroundColor() {
		return JFaceResources.getColorRegistry().get(NEGATED_HEADER_BACKGROUND_COLOR);
	}

	public static Color getHeaderForegroundColor() {
		return JFaceResources.getColorRegistry().get(HEADER_FOREGROUND_COLOR);
	}

	public static Color getValueBorder() {
		return JFaceResources.getColorRegistry().get(VALUE_BORDER_COLOR);
	}

	private static final String TB_SELECTED_BACKGROUND_COLOR = "org.eclipse.fordiac.ide.bulkeditor.query.toggleButtonSelectedBackgroundColor"; //$NON-NLS-1$
	private static final String TB_SELECTED_BORDER_COLOR = "org.eclipse.fordiac.ide.bulkeditor.query.toggleButtonSelectedBorderColor"; //$NON-NLS-1$
	private static final String TB_DEFAULT_BORDER_COLOR = "org.eclipse.fordiac.ide.bulkeditor.query.toggleButtonDefaultBorderColor"; //$NON-NLS-1$
	private static final String TB_DISABLED_BACKGROUND_COLOR = "org.eclipse.fordiac.ide.bulkeditor.query.toggleButtonDisabledBackgroundColor"; //$NON-NLS-1$

	public static Color getToggleButtonSelectedBackground() {
		return JFaceResources.getColorRegistry().get(TB_SELECTED_BACKGROUND_COLOR);
	}

	public static Color getToggleButtonSelectedBorder() {
		return JFaceResources.getColorRegistry().get(TB_SELECTED_BORDER_COLOR);
	}

	public static Color getToggleButtonDefaultBorder() {
		return JFaceResources.getColorRegistry().get(TB_DEFAULT_BORDER_COLOR);
	}

	public static Color getToggleButtonDisabledBackground() {
		return JFaceResources.getColorRegistry().get(TB_DISABLED_BACKGROUND_COLOR);
	}
}