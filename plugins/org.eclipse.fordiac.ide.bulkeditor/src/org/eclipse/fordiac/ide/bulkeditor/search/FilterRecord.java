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

	public static final FilterRecord INACTIVE = new FilterRecord(false, false, false, MatcherConfig.INACTIVE,
			MatcherConfig.INACTIVE, MatcherConfig.INACTIVE, MatcherConfig.INACTIVE, null, null);

	private final boolean selected;
	private final boolean negate;
	private final boolean attributeConstraint;
	private final MatcherConfig nameConfig;
	private final MatcherConfig typeConfig;
	private final MatcherConfig commentConfig;
	private final MatcherConfig valueConfig;
	private final Pattern namePattern;
	private final Pattern typePattern;
	private final Pattern commentPattern;
	private final Pattern valuePattern;
	private final FilterRecord orConstraint;
	private final FilterRecord andConstraint;

	public FilterRecord(final boolean selected, final boolean negate, final boolean attributeConstraint,
			final MatcherConfig nameConfig, final MatcherConfig typeConfig, final MatcherConfig commentConfig,
			final MatcherConfig valueConfig, final FilterRecord orConstraint, final FilterRecord andConstraint) {
		this.selected = selected;
		this.negate = negate;
		this.attributeConstraint = attributeConstraint;
		this.nameConfig = nameConfig;
		this.typeConfig = typeConfig;
		this.commentConfig = commentConfig;
		this.valueConfig = valueConfig;
		this.namePattern = StringMatcher.createPattern(nameConfig);
		this.typePattern = StringMatcher.createPattern(typeConfig);
		this.commentPattern = StringMatcher.createPattern(commentConfig);
		this.valuePattern = StringMatcher.createPattern(valueConfig);

		this.orConstraint = orConstraint;
		this.andConstraint = andConstraint;
	}

	public boolean accepts(final MatchTarget target) {
		return !selected || matches(target);
	}

	private boolean matches(final MatchTarget target) {
		if (attributeConstraint) {
			// an attribute constraint and its and/or chain have to match the same attribute
			return target.attributes().stream().map(MatchTarget::ofAttribute).anyMatch(this::matchesChain);
		}
		return matchesChain(target);
	}

	private boolean matchesChain(final MatchTarget target) {
		return (matchesFields(target) != negate && (andConstraint == null || matchesNext(andConstraint, target)))
				|| (orConstraint != null && matchesNext(orConstraint, target));
	}

	private boolean matchesNext(final FilterRecord next, final MatchTarget target) {
		return attributeConstraint ? next.matchesChain(target) : next.matches(target);
	}

	private boolean matchesFields(final MatchTarget target) {
		return StringMatcher.matches(target.name(), nameConfig, namePattern)
				&& StringMatcher.matches(target.type(), typeConfig, typePattern)
				&& StringMatcher.matches(target.comment(), commentConfig, commentPattern)
				&& StringMatcher.matches(target.value(), valueConfig, valuePattern);
	}
}
