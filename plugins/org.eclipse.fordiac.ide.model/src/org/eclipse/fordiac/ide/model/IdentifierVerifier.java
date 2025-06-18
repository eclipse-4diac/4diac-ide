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
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;

/**
 * This class provides static methods to check whether a string is a valid IEC
 * 61499 compliant identifier.
 */
public final class IdentifierVerifier {

	private static final String IDENTIFIER_REGEX = "[_A-Za-z][_A-Za-z0-9]*+"; //$NON-NLS-1$
	private static final Pattern IDENTIFIER_PATTERN = Pattern.compile(IDENTIFIER_REGEX);

	private IdentifierVerifier() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Checks if the identifier is valid, and returns an Optional error message.
	 *
	 * @param identifier the identifier
	 *
	 * @return Empty Optional if the identifier is valid, otherwise the error
	 *         message is contained in the Optional
	 */
	public static Optional<String> verifyIdentifier(final String identifier) {
		return verifyIdentifier(identifier, null);
	}

	/**
	 * Checks if the identifier is valid, and returns an Optional error message.
	 *
	 * @param identifier the identifier
	 * @param context    The context in which the identifier has to be valid (may be
	 *                   {@code null})
	 *
	 * @return Empty Optional if the identifier is valid, otherwise the error
	 *         message is contained in the Optional
	 */
	public static Optional<String> verifyIdentifier(final String identifier, final Object context) {
		if (identifier == null || !IDENTIFIER_PATTERN.matcher(identifier).matches()) {
			return Optional.of(MessageFormat.format(Messages.IdentifierVerifier_NameNotAValidIdentifier, identifier));
		}
		if (identifier.contains("__")) { //$NON-NLS-1$
			return Optional.of(MessageFormat.format(Messages.IdentifierVerifier_NameConsecutiveUnderscore, identifier));
		}
		if (identifier.endsWith("_")) { //$NON-NLS-1$
			return Optional.of(MessageFormat.format(Messages.IdentifierVerifier_NameTrailingUnderscore, identifier));
		}
		if (FordiacKeywords.isReservedKeyword(identifier, context)) {
			return Optional.of(MessageFormat.format(Messages.IdentifierVerifier_NameReservedKeyWord, identifier));
		}
		return Optional.empty();
	}

	public static Optional<String> verifyPackageName(final String packageName) {
		if (packageName == null || packageName.isEmpty()) {
			return Optional.empty(); // allow empty package names
		}
		return Stream.of(packageName.split(PackageNameHelper.PACKAGE_NAME_DELIMITER, -1))
				.map(IdentifierVerifier::verifyIdentifier).flatMap(Optional::stream).map(message -> MessageFormat
						.format(Messages.IdentifierVerifier_PackageNameMessage, message, packageName))
				.findFirst();
	}
}
