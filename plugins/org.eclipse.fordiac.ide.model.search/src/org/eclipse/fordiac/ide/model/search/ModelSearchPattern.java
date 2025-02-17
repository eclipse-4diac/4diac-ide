/*******************************************************************************
 * Copyright (c) 2022, 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin
 *    - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search;

import java.util.regex.Pattern;

public class ModelSearchPattern { // extends SearchPattern

	private final Pattern searchPattern;
	private final Pattern preScannSTPattern;
	private final String preScannSTStringPattern;

	public ModelSearchPattern(final ModelQuerySpec modelQuerySpec) {
		searchPattern = Pattern.compile(convertSearchStringToPattern(modelQuerySpec.searchString()));

		preScannSTStringPattern = generatePreScanSTStringPattern(modelQuerySpec.searchString());
		preScannSTPattern = (containsRegex(preScannSTStringPattern))
				? Pattern.compile(preScannSTStringPattern, Pattern.CASE_INSENSITIVE)
				: null;
	}

	private static String convertSearchStringToPattern(final String searchString) {
		String temp = searchString;
		if (searchString.contains("?")) { //$NON-NLS-1$
			temp = searchString.replace("?", "[a-zA-Z0-9_]"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (searchString.contains("*")) { //$NON-NLS-1$
			temp = searchString.replace("*", ".*"); //$NON-NLS-1$//$NON-NLS-2$
		}
		return temp;
	}

	public boolean matchSearchString(final String toTest) {
		return searchPattern.matcher(toTest).matches();
	}

	public boolean preScanST(final String toTest) {
		if (preScannSTPattern != null) {
			return preScannSTPattern.matcher(toTest).find();
		}
		return preScanSTIgnoreCase(toTest);
	}

	private static String generatePreScanSTStringPattern(final String searchString) {
		String result = searchString;
		final int lastPackageSepeartor = result.lastIndexOf("::"); //$NON-NLS-1$
		if (lastPackageSepeartor != -1) {
			result = result.substring(lastPackageSepeartor + 2);
		}
		return convertSearchStringToPattern(result);
	}

	private static boolean containsRegex(final String pattern) {
		return pattern.matches(".*[.*+?^${}()|\\[\\]\\\\].*"); //$NON-NLS-1$
	}

	private boolean preScanSTIgnoreCase(final String toTest) {
		final int patternLength = preScannSTStringPattern.length();

		for (int i = 0; i <= toTest.length() - patternLength; i++) {
			if (toTest.regionMatches(true, i, preScannSTStringPattern, 0, patternLength)) {
				return true;
			}
		}
		return false;
	}

}
