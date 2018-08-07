/********************************************************************************
 * Copyright (c) 2011, 2017 Profactor GmbH, fortiss GmbH, 2018 TU Vienna/ACIN
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 *  Martin Melik Merkumains - changes implementation to regex expression
 ********************************************************************************/
package org.eclipse.fordiac.ide.model;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides static methods to check whether a string is a valid IEC
 * 61499 compliant identifier.
 */
public final class IdentifierVerifyer {

	private static final String IDENTIFIER_REGEX = "[_A-Za-z][_A-Za-z\\d]*"; //$NON-NLS-1$
	private static final Pattern IDENTIFIER_PATTERN = Pattern.compile(IDENTIFIER_REGEX, Pattern.MULTILINE);
	private static final String INVALID_IDENTIFIER_REGEX = "[^_A-Za-z\\d]"; //$NON-NLS-1$
	private static final Pattern INVALID_IDENTIFIER_PATTERN = Pattern.compile(INVALID_IDENTIFIER_REGEX,
			Pattern.MULTILINE);

	private IdentifierVerifyer() {
		// we don't want this util class to be instantiable
	}

	/**
	 * Checks if is valid identifier.
	 * 
	 * @param identifier the identifier
	 * 
	 * @return true, if is valid identifier
	 */
	public static boolean isValidIdentifier(String identifier) {
		final Matcher matcher = IDENTIFIER_PATTERN.matcher(identifier);
		return matcher.matches();
	}

	/**
	 * Checks if is valid identifier.
	 * 
	 * @param identifier the identifier
	 * 
	 * @return null if it is an valid identifier otherwise an Error message
	 */
	public static String isValidIdentifierWithErrorMessage(String identifier) {
		if (isValidIdentifier(identifier)) {
			return null;
		}
		if (identifier.length() < 1) {
			return Messages.IdentifierVerifyer_ERROR_IdentifierLengthZero;
		}
		String firstChar = identifier.substring(0, 1);
		final Matcher startSymbolMatcher = IDENTIFIER_PATTERN.matcher(firstChar);
		if (!startSymbolMatcher.matches()) {
			return Messages.IdentifierVerifyer_ERROR_InvalidStartSymbol;
		}
		final Matcher invalidExpressionSymbolsMatcher = INVALID_IDENTIFIER_PATTERN.matcher(identifier);
		if (invalidExpressionSymbolsMatcher.find()) {
			return MessageFormat.format(Messages.IdentifierVerifyer_ERROR_InvalidSymbolUsedInIdentifer,
					invalidExpressionSymbolsMatcher.group(0).toString());
		}
		return Messages.IdentifierVerifyer_ERROR_UnkownExpressionError;
	}
}
