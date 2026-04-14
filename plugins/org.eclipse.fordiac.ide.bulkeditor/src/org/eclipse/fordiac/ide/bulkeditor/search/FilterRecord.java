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

import org.eclipse.fordiac.ide.bulkeditor.ui.FilterComposite.Filter;

public class FilterRecord {

	private final boolean selected;

	private final Filter nameFilter;
	private final Filter typeFilter;
	private final Filter commentFilter;

	private final Pattern namePattern;
	private final Pattern typePattern;
	private final Pattern commentPattern;

	public FilterRecord(final boolean selected, final Filter nameFilter, final Filter typeFilter,
			final Filter commentFilter) {
		this.selected = selected;
		this.nameFilter = nameFilter;
		this.typeFilter = typeFilter;
		this.commentFilter = commentFilter;
		this.namePattern = StringMatcher.createPattern(nameFilter);
		this.typePattern = StringMatcher.createPattern(typeFilter);
		this.commentPattern = StringMatcher.createPattern(commentFilter);
	}

	public boolean isSelected() {
		return selected;
	}

	public boolean matches(final String name, final String type, final String comment) {
		return StringMatcher.matches(name, nameFilter, namePattern)
				&& StringMatcher.matches(type, typeFilter, typePattern)
				&& StringMatcher.matches(comment, commentFilter, commentPattern);
	}
}
