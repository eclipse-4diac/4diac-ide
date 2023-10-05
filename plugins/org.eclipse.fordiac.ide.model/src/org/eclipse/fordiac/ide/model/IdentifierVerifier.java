/********************************************************************************
 * Copyright (c) 2011, 2023 Profactor GmbH, fortiss GmbH, 2018 TU Vienna/ACIN
 *                          Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 *  Martin Melik Merkumains - changed implementation to regex expression, changed
 *  	isValidIdentifier to verifyIdentifier, which returns Optional error string
 *  	instead of a boolean
 *  Martin Jobst
 *    - add allowed contexts
 ********************************************************************************/
package org.eclipse.fordiac.ide.model;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;

/** This class provides static methods to check whether a string is a valid IEC 61499 compliant identifier. */
public final class IdentifierVerifier {

	private static final String IDENTIFIER_REGEX = "[_A-Za-z][_A-Za-z\\d]*"; //$NON-NLS-1$
	private static final Pattern IDENTIFIER_PATTERN = Pattern.compile(IDENTIFIER_REGEX, Pattern.MULTILINE);
	private static final String INVALID_IDENTIFIER_REGEX = "[^_A-Za-z\\d]"; //$NON-NLS-1$
	private static final Pattern INVALID_IDENTIFIER_PATTERN = Pattern.compile(INVALID_IDENTIFIER_REGEX,
			Pattern.MULTILINE);

	private static final String PACKAGE_NAME_REGEX = IDENTIFIER_REGEX + "(?:::" + IDENTIFIER_REGEX + ")*"; //$NON-NLS-1$ //$NON-NLS-2$
	private static final Pattern PACKAGE_NAME_PATTERN = Pattern.compile(PACKAGE_NAME_REGEX, Pattern.MULTILINE);

	private IdentifierVerifier() {
		throw new UnsupportedOperationException();
	}

	/** Checks if the identifier is valid, and returns an Optional error message.
	 *
	 * @param identifier the identifier
	 *
	 * @return Empty Optional if the identifier is valid, otherwise the error message is contained in the Optional */
	public static Optional<String> verifyIdentifier(final String identifier) {
		return verifyIdentifier(identifier, null);
	}

	/** Checks if the identifier is valid, and returns an Optional error message.
	 *
	 * @param identifier the identifier
	 * @param context    The context in which the identifier has to be valid (may be {@code null})
	 *
	 * @return Empty Optional if the identifier is valid, otherwise the error message is contained in the Optional */
	public static Optional<String> verifyIdentifier(final String identifier, final Object context) {
		if (identifier == null) {
			return Optional.of(MessageFormat.format(Messages.NameRepository_NameNotAValidIdentifier, identifier));
		}
		if (identifier.length() < 1) {
			return Optional.of(Messages.IdentifierVerifier_ERROR_IdentifierLengthZero);
		}
		if (!IDENTIFIER_PATTERN.matcher(identifier).matches()) {
			return Optional.of(MessageFormat.format(Messages.NameRepository_NameNotAValidIdentifier, identifier));
		}
		if (!IDENTIFIER_PATTERN.matcher(identifier.substring(0, 1)).matches()) {
			return Optional.of(Messages.IdentifierVerifier_ERROR_InvalidStartSymbol);
		}
		final Matcher invalidExpressionSymbolsMatcher = INVALID_IDENTIFIER_PATTERN.matcher(identifier);
		if (invalidExpressionSymbolsMatcher.find()) {
			return Optional.of(MessageFormat.format(Messages.IdentifierVerifier_ERROR_InvalidSymbolUsedInIdentifer,
					invalidExpressionSymbolsMatcher.group(0)));
		}
		if (FordiacKeywords.isReservedKeyword(identifier, context)) {
			return Optional.of(MessageFormat.format(Messages.NameRepository_NameReservedKeyWord, identifier));
		}
		return Optional.empty();
	}

	public static Optional<String> verifyPackageName(final String packageName) {
		if (packageName == null || packageName.isBlank()) {
			return Optional.empty(); // allow empty package names
		}
		if (!PACKAGE_NAME_PATTERN.matcher(packageName).matches()) {
			return Optional
					.of(MessageFormat.format(Messages.IdentifierVerifier_PackageNameNotAValidIdentifier, packageName));
		}
		return Stream.of(packageName.split(PackageNameHelper.PACKAGE_NAME_DELIMITER))
				.filter(FordiacKeywords::isReservedKeyword).map(identifier -> MessageFormat
						.format(Messages.IdentifierVerifier_PackageNameReservedKeyword, packageName, identifier))
				.findFirst();
	}
}
