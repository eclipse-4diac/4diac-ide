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
 *   Mario Kastner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.export;

import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;

public class PackageNameMatcher {

	public static final String PACKAGE_NAME_DELIMITER = "::"; //$NON-NLS-1$

	private static final String WILDCARD_EXACTLY_ONE = "*"; //$NON-NLS-1$
	private static final String WILDCARD_AT_LEAST_ONE = "**"; //$NON-NLS-1$

	/**
	 * Checks whether the given package name matches an include/exclude pattern.
	 * {@code *} matches exactly one package segment, while {@code **} matches one
	 * or more package segments.
	 *
	 * @param packageName           the package name to match
	 * @param includeExcludePattern the pattern to match against
	 * @return {@code true} if the package name matches the pattern
	 */
	public static boolean matchesPattern(final String packageName, final String includeExcludePattern) {
		final String[] packageSegments = packageName.split(PackageNameHelper.PACKAGE_NAME_DELIMITER);
		final String[] patternSegments = includeExcludePattern.split(PackageNameHelper.PACKAGE_NAME_DELIMITER);

		int packageIndex = 0;
		int patternIndex = 0;

		int wildcardPatternIndex = -1;
		int wildcardPackageIndex = -1;

		while (packageIndex < packageSegments.length) {
			if (patternIndex < patternSegments.length && (WILDCARD_EXACTLY_ONE.equals(patternSegments[patternIndex]) // $NON-NLS-1$
					|| packageSegments[packageIndex].equals(patternSegments[patternIndex]))) {
				packageIndex++;
				patternIndex++;
			} else if (patternIndex < patternSegments.length
					&& WILDCARD_AT_LEAST_ONE.equals(patternSegments[patternIndex])) { // $NON-NLS-1$
				// wildcard matches at least one segment
				wildcardPatternIndex = patternIndex;
				patternIndex++;
				packageIndex++;
				wildcardPackageIndex = packageIndex;
			} else if (wildcardPatternIndex >= 0 && wildcardPackageIndex < packageSegments.length) {
				// Previous wildcard consumes additional segment
				patternIndex = wildcardPatternIndex + 1;
				wildcardPackageIndex++;
				packageIndex = wildcardPackageIndex;
			} else {
				return false;
			}
		}

		return patternIndex == patternSegments.length;
	}

	private PackageNameMatcher() {
		throw new UnsupportedOperationException();
	}

}
