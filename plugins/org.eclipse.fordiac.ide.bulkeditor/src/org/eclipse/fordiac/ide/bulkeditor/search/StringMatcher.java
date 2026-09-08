/*******************************************************************************
 * Copyright (c) 2025, 2026 Primetals Technologies Austria GmbH
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

public class StringMatcher {

	private StringMatcher() {
		// utility class
	}

	public static boolean matches(final String toCheck, final MatcherConfig config, final Pattern pattern) {
		return !config.active() || compareStrings(config, pattern, toCheck);
	}

	private static boolean compareStrings(final MatcherConfig config, final Pattern pattern, String element) {
		String search = config.value();
		if (search == null || element == null) {
			return false;
		}
		if (!config.caseSensitive()) {
			element = element.toLowerCase();
			search = search.toLowerCase();
		}
		if (config.regex() && pattern != null) {
			return pattern.matcher(element).find();
		}
		if (config.wholeWord()) {
			final String searchString = search;
			return Arrays.stream(element.split("\\W+")).anyMatch(word -> word.equals(searchString)); //$NON-NLS-1$
		}
		if (config.exactMatch()) {
			return element.equals(search);
		}
		return element.contains(search);
	}

	public static Pattern createPattern(final MatcherConfig config) {
		String query = config.value();
		if (!config.regex()) {
			return null;
		}
		if (!config.caseSensitive()) {
			query = query.toLowerCase();
		}
		if (config.exactMatch()) {
			query = "^" + query + "$"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		try {
			return Pattern.compile(query);
		} catch (final PatternSyntaxException exception) {
			return null;
		}
	}
}
