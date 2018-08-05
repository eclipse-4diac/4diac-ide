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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides static methods to check whether a string is a valid IEC
 * 61499 compliant identifier.
 */
public final class IdentifierVerifyer {

	private static final String IDENTIFIER_HAS_TO_START_WITH_UNDERSCORE_OR_A_CHARACTER = "Identifier has to start with '_' or a character";
	private final static String IDENTIFIER_LENGTH_LOWER_THAN_ONE = "Length < 1";
	private final static String IdentifierRegex = "[_A-Za-z][_A-Za-z\\d]*";
	private final static Pattern IdentifierPattern = Pattern.compile(IdentifierRegex, Pattern.MULTILINE);
	private final static String InvalidIdentifierRegex = "[^_A-Za-z\\d]";
	private final static Pattern InvalidIdentifierPattern = Pattern.compile(InvalidIdentifierRegex, Pattern.MULTILINE);
	
	private IdentifierVerifyer() {
		//we don't want this util class to be instantiable
	}

	/**
	 * Checks if is valid identifier.
	 * 
	 * @param identifier the identifier
	 * 
	 * @return true, if is valid identifier
	 */
	public static boolean isValidIdentifier(String identifier) {
		final Matcher matcher = IdentifierPattern.matcher(identifier);
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
		if(isValidIdentifier(identifier)) {
			return null;
		}
		if (identifier.length() < 1) {
			return IDENTIFIER_LENGTH_LOWER_THAN_ONE;
		}
		String firstChar = identifier.substring(0, 1);
		final Matcher startSymbolMatcher = IdentifierPattern.matcher(firstChar);
		if (!startSymbolMatcher.matches()) {
			return IDENTIFIER_HAS_TO_START_WITH_UNDERSCORE_OR_A_CHARACTER;
		}
		final Matcher invalidExpressionSymbolsMatcher = InvalidIdentifierPattern.matcher(identifier);
		if(invalidExpressionSymbolsMatcher.find()) {
			return "Invalid symbol " + invalidExpressionSymbolsMatcher.group(0).toString() + " used in identifier";
		}
		return "Unkown expression error";
	}
}
