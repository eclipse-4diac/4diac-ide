/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.bulkeditor.search;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.eclipse.fordiac.ide.bulkeditor.ui.FilterComposite.Filter;

public class StringMatcher {

	private StringMatcher() {
		// utility class
	}

	public static boolean matches(final String toCheck, final Filter filter, final Pattern pattern) {
		return !filter.selected.getSelection() || compareStrings(filter, pattern, toCheck);
	}

	private static boolean compareStrings(final Filter filter, final Pattern pattern, String element) {
		String search = filter.textField.getText();
		if (search == null || element == null) {
			return false;
		}
		if (!filter.caseSensitive.getSelection()) {
			element = element.toLowerCase();
			search = search.toLowerCase();
		}
		if (filter.regularExpression.getSelection() && pattern != null) {
			return pattern.matcher(element).find();
		}
		if (filter.wholeWord.getSelection()) {
			final String searchString = search;
			return Arrays.stream(element.split("\\W+")).anyMatch(word -> word.equals(searchString)); //$NON-NLS-1$
		}
		if (filter.exactMatch.getSelection()) {
			return element.equals(search);
		}
		return element.contains(search);
	}

	public static Pattern createPattern(final Filter filter) {
		String query = filter.textField.getText();
		if (!filter.regularExpression.getSelection()) {
			return null;
		}
		if (!filter.caseSensitive.getSelection()) {
			query = query.toLowerCase();
		}
		if (filter.exactMatch.getSelection()) {
			query = "^" + query + "$"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		try {
			return Pattern.compile(query);
		} catch (final PatternSyntaxException exception) {
			return null;
		}
	}
}
