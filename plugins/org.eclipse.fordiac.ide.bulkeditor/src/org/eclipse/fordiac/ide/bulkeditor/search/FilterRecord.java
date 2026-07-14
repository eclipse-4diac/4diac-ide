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

import java.util.regex.Pattern;

public class FilterRecord {

	private final boolean selected;
	private final MatcherConfig nameConfig;
	private final MatcherConfig typeConfig;
	private final MatcherConfig commentConfig;
	private final Pattern namePattern;
	private final Pattern typePattern;
	private final Pattern commentPattern;

	public FilterRecord(final boolean selected, final MatcherConfig nameConfig, final MatcherConfig typeConfig,
			final MatcherConfig commentConfig) {
		this.selected = selected;
		this.nameConfig = nameConfig;
		this.typeConfig = typeConfig;
		this.commentConfig = commentConfig;
		this.namePattern = StringMatcher.createPattern(nameConfig);
		this.typePattern = StringMatcher.createPattern(typeConfig);
		this.commentPattern = StringMatcher.createPattern(commentConfig);
	}

	public boolean isSelected() {
		return selected;
	}

	public boolean matches(final String name, final String type, final String comment) {
		return StringMatcher.matches(name, nameConfig, namePattern)
				&& StringMatcher.matches(type, typeConfig, typePattern)
				&& StringMatcher.matches(comment, commentConfig, commentPattern);
	}
}