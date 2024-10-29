/*******************************************************************************
 * Copyright (c) 2023, 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.annotation;

import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;

import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.AnnotationPreference;

public final class TextualAnnotationStyles {

	public static Styler getAnnotationStyle(final Set<GraphicalAnnotation> annotations) {
		return getAnnotationStyle(annotations, annotation -> true);
	}

	public static Styler getAnnotationStyle(final Set<GraphicalAnnotation> annotations,
			final Predicate<GraphicalAnnotation> filter) {
		return annotations.stream().filter(filter).map(TextualAnnotationStyles::lookupAnnotationPreference)
				.filter(TextualAnnotationStyles::isEnabled).filter(TextualAnnotationStyles::hasTextStylePreference)
				.filter(TextualAnnotationStyles::hasColorPreference)
				.max(Comparator.comparingInt(AnnotationPreference::getPresentationLayer))
				.map(TextualAnnotationStyles::createStyler).orElse(null);
	}

	private static AnnotationPreference lookupAnnotationPreference(final GraphicalAnnotation annotation) {
		return EditorsUI.getAnnotationPreferenceLookup().getAnnotationPreference(annotation.getType());
	}

	private static boolean hasColorPreference(final AnnotationPreference preference) {
		return preference.getColorPreferenceKey() != null || preference.getColorPreferenceValue() != null;
	}

	private static boolean hasTextStylePreference(final AnnotationPreference preference) {
		return preference.getTextStylePreferenceKey() != null || preference.getTextStyleValue() != null;
	}

	private static boolean isEnabled(final AnnotationPreference preference) {
		final String key = preference.getTextPreferenceKey();
		if (key != null) {
			return EditorsUI.getPreferenceStore().getBoolean(key);
		}
		return preference.getTextPreferenceValue();
	}

	private static String getTextStyle(final AnnotationPreference preference) {
		final String key = preference.getTextStylePreferenceKey();
		if (key != null) {
			final String value = EditorsUI.getPreferenceStore().getString(key);
			if (value != null) {
				return value;
			}
		}
		return preference.getTextStyleValue();
	}

	private static Color getColor(final AnnotationPreference preference) {
		final String key = preference.getColorPreferenceKey();
		if (key != null) {
			final RGB value = PreferenceConverter.getColor(EditorsUI.getPreferenceStore(), key);
			if (value != null) {
				return EditorsUI.getSharedTextColors().getColor(value);
			}
		}
		return EditorsUI.getSharedTextColors().getColor(preference.getColorPreferenceValue());
	}

	private static Styler createStyler(final AnnotationPreference preference) {
		final String style = getTextStyle(preference);
		if (style == null) {
			return null;
		}
		final Color color = getColor(preference);
		if (color == null) {
			return null;
		}
		return createStyler(style, color);
	}

	private static Styler createStyler(final String style, final Color color) {
		return switch (style) {
		case AnnotationPreference.STYLE_SQUIGGLES -> new UnderlineStyler(SWT.UNDERLINE_SQUIGGLE, color);
		case AnnotationPreference.STYLE_PROBLEM_UNDERLINE -> new UnderlineStyler(SWT.UNDERLINE_ERROR, color);
		case AnnotationPreference.STYLE_BOX -> new BoxStyler(SWT.BORDER_SOLID, color);
		case AnnotationPreference.STYLE_DASHED_BOX -> new BoxStyler(SWT.BORDER_DASH, color);
		case AnnotationPreference.STYLE_UNDERLINE -> new UnderlineStyler(SWT.UNDERLINE_SINGLE, color);
		default -> null;
		};
	}

	public static class UnderlineStyler extends Styler {

		private final int style;
		private final Color underlineColor;

		private UnderlineStyler(final int style, final Color underlineColor) {
			this.style = style;
			this.underlineColor = underlineColor;
		}

		@Override
		public void applyStyles(final TextStyle textStyle) {
			textStyle.underline = true;
			textStyle.underlineColor = underlineColor;
			textStyle.underlineStyle = style;
		}
	}

	public static final class BoxStyler extends Styler {

		private final int style;
		private final Color borderColor;

		public BoxStyler(final int style, final Color borderColor) {
			this.style = style;
			this.borderColor = borderColor;
		}

		@Override
		public void applyStyles(final TextStyle textStyle) {
			textStyle.borderStyle = style;
			textStyle.borderColor = borderColor;
		}
	}

	private TextualAnnotationStyles() {
		throw new UnsupportedOperationException();
	}
}
