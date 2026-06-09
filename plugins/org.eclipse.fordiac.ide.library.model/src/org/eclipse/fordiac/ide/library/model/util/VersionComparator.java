/*******************************************************************************
 * Copyright (c) 2024  Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.model.util;

import java.util.Comparator;
import java.util.regex.Pattern;

import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;

public class VersionComparator implements Comparator<String> {

	private static final Pattern VERSION_ALLOWED_CHARS_PATTERN = Pattern.compile("^[0-9.\\-\\[\\]\\(\\)\\s]+$"); //$NON-NLS-1$

	@Override
	public int compare(final String o1, final String o2) {

		if (o1 == null || o1.isBlank()) {
			return (o2 == null || o2.isBlank()) ? 0 : -1;
		}
		if (o1.equals(o2)) {
			return 0;
		}
		if (o2 == null || o2.isBlank()) {
			return 1;
		}

		final Version v1 = new Version(o1);
		final Version v2 = new Version(o2);

		return v1.compareTo(v2);
	}

	public static boolean contains(final String range, final String version) {
		if (range == null || range.isBlank() || version == null || version.isBlank()) {
			return false;
		}
		final VersionRange parsedRange = parseVersionRange(range);
		final Version parsedVersion = new Version(version);

		return parsedRange.includes(parsedVersion);
	}

	public static boolean contains(final VersionRange range, final String version) {
		if (range == null || version == null || version.isBlank()) {
			return false;
		}
		final Version parsedVersion = new Version(version);

		return range.includes(parsedVersion);
	}

	public static VersionRange parseVersionRange(final String range) {
		if (range == null || range.isBlank()) {
			return new VersionRange(VersionRange.LEFT_OPEN, Version.emptyVersion, Version.emptyVersion,
					VersionRange.RIGHT_OPEN);
		}
		final VersionRange parsedRange = new VersionRange(range.replace('-', ','));
		if (parsedRange.getRight() == null) {
			return new VersionRange(VersionRange.LEFT_CLOSED, parsedRange.getLeft(), parsedRange.getLeft(),
					VersionRange.RIGHT_CLOSED);
		}
		return parsedRange;
	}

	public static String formatVersionRange(final VersionRange range) {
		if (range.getLeftType() == VersionRange.LEFT_CLOSED && range.getRightType() == VersionRange.RIGHT_CLOSED
				&& range.getLeft().equals(range.getRight())) {
			return range.getLeft().toString();
		}
		return range.toString().replace(',', '-');
	}

	/**
	 * Checks if the input is a valid OSGi version range with the applied
	 * restrictions.
	 * <p>
	 * The input must be non-empty, contain only numeric values and valid
	 * separators, represent a non-empty range, and (if not exact) have a left bound
	 * lower than the right bound.
	 *
	 * @param input the version or version range to validate
	 * @return {@code true} if valid; {@code false} otherwise
	 */
	public static boolean isValidRange(final String input) {
		if (input == null) {
			return false;
		}

		final String versionString = input.trim();
		if (versionString.isEmpty() || !VERSION_ALLOWED_CHARS_PATTERN.matcher(versionString).matches()) {
			return false;
		}

		try {
			final VersionRange range = parseVersionRange(versionString);
			if (range.isEmpty()) {
				return false;
			}
			return range.isExact() || range.getLeft().compareTo(range.getRight()) < 0;
		} catch (final IllegalArgumentException e) {
			return false;
		}
	}

}
