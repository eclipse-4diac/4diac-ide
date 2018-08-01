/********************************************************************************
 * Copyright (c) 2011, 2017 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model;

/**
 * This class provides static methods to check whether a string is a valid IEC
 * 61499 compliant identifier.
 */
public final class IdentifierVerifyer {
	
	private IdentifierVerifyer() {
		//we don't want this util class to be instantatable
	}

	/**
	 * Checks if is valid identifier.
	 * 
	 * @param identifier the identifier
	 * 
	 * @return true, if is valid identifier
	 */
	public static boolean isValidIdentifier(String identifier) {
		return (null == isValidIdentifierWithErrorMessage(identifier));
	}
	/**
	 * Checks if is valid identifier.
	 * 
	 * @param identifier the identifier
	 * 
	 * @return null if it is an valid identifier otherwise an Error message
	 */
	public static String isValidIdentifierWithErrorMessage(String identifier) {
		if (identifier.length() < 1) {
			return "Length < 1";
		}
		char firstChar = identifier.charAt(0);
		if (firstChar != '_' && !isIdentifierChar(firstChar)) {
			return "Identifier has to start with '_' or a character";
		}
		for (int i = 0; i < identifier.length(); i++) {
			Character myChar = identifier.charAt(i);
			if ((!isIdentifierChar(myChar) && !Character.isDigit(myChar) && myChar != '_')) {
				return "The char: " + myChar + " is not allowed within identifiers";
			}
		}
		return null;
	}
	
	public static boolean isIdentifierChar(char character){
		char toLower = Character.toLowerCase(character);
		return (('a' == toLower) || 
				('b' == toLower) || 
				('c' == toLower) || 
				('d' == toLower) || 
				('e' == toLower) || 
				('f' == toLower) || 
				('g' == toLower) || 
				('h' == toLower) || 
				('i' == toLower) || 
				('j' == toLower) || 
				('k' == toLower) || 
				('l' == toLower) || 
				('m' == toLower) || 
				('n' == toLower) || 
				('o' == toLower) || 
				('p' == toLower) || 
				('q' == toLower) || 
				('r' == toLower) || 
				('s' == toLower) || 
				('t' == toLower) || 
				('u' == toLower) || 
				('v' == toLower) || 
				('w' == toLower) || 
				('x' == toLower) || 
				('y' == toLower) || 
				('z' == toLower));
	}
}
