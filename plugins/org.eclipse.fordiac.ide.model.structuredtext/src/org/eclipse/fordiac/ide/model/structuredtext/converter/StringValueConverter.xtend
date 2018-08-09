/*******************************************************************************
 * Copyright (c) 2015, 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Martin Jobst 
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.structuredtext.converter

import java.io.Reader
import java.io.StringReader
import org.eclipse.xtext.conversion.ValueConverterException
import org.eclipse.xtext.conversion.impl.AbstractNullSafeConverter
import org.eclipse.xtext.nodemodel.INode

abstract class StringValueConverter extends AbstractNullSafeConverter<String> {

	override protected internalToString(String value) {
		val reader = new StringReader(value)
		val result = new StringBuilder()
		result.append(quote)
		for (var i = reader.read; i != -1; i = reader.read) {
			switch c : i as char {
				case ' ' === c,
				case '!' === c,
				case '#' === c,
				case '%' === c,
				case '&' === c,
				case ('(' .. '/').contains(c),
				case ('0' .. '9').contains(c),
				case (':' .. '@').contains(c),
				case ('A' .. 'Z').contains(c),
				case ('[' .. '`').contains(c),
				case ('a' .. 'z').contains(c),
				case ('{' .. '~').contains(c):
					result.append(c)
				case '\'' === c,
				case '"' === c: {
					if (c == quote) {
						result.append("$")
					}
					result.append(c)
				}
				case '$' === c:
					result.append("$$")
				case '\n' === c:
					result.append("$N")
				case '\f' === c:
					result.append("$P")
				case '\r' === c:
					result.append("$R")
				case '\t' === c:
					result.append("$T")
				default:
					result.append(toHexLiteral(c as char))
			}
		}
		result.append(quote)
		result.toString
	}

	override protected internalToValue(String string, INode node) {
		if (string.empty || string.length < 2) {
			throw new ValueConverterException("Unclosed string literal", null, null)
		}
		if (string.charAt(0) !== quote || string.charAt(string.length - 1) !== quote) {
			throw new ValueConverterException("Invalid quotes for string literal", null, null)
		}
		val value = string.substring(1, string.length - 1)
		val reader = new StringReader(value)
		val result = new StringBuilder()
		for (var int i = reader.read; i !== -1; i = reader.read) {
			result.append(
				switch c : i as char {
					case ' ' === c,
					case '!' === c,
					case '#' === c,
					case '%' === c,
					case '&' === c,
					case ('(' .. '/').contains(c),
					case ('0' .. '9').contains(c),
					case (':' .. '@').contains(c),
					case ('A' .. 'Z').contains(c),
					case ('[' .. '`').contains(c),
					case ('a' .. 'z').contains(c),
					case ('{' .. '~').contains(c):
						c
					case '\'' === c,
					case '"' === c: {
						if (c === quote) {
							throw new ValueConverterException(
								"Couldn't convert value due to illegal quote character inside string", null, null)
						}
						c
					}
					case '$' === c: {
						i = reader.read
						if(i === -1) {
							throw new ValueConverterException("Couldn't convert value due to invalid escape sequence", null, null)
						}
						switch d : i as char {
							case '$' === d:
								'$'
							case 'L' === d,
							case 'l' === d:
								'\n'
							case 'N' === d,
							case 'n' === d:
								'\n'
							case 'P' === d,
							case 'p' === d:
								'\f'
							case 'R' === d,
							case 'r' === d:
								'\r'
							case 'T' === d,
							case 't' === d:
								'\t'
							case quote === d:
								quote
							default: {
								reader.skip(-1);
								parseHexLiteral(reader)
							}
						}
					}
					default:
						throw new ValueConverterException("Couldn't convert value due to invalid character", null, null)
				}
			)
		}
		result.toString
	}
	
	def operator_tripleEquals(String s, char c) {
		if (s.length != 1) {
			return false
		}
		s.charAt(0) == c
	}

	def operator_upTo(String string, String string2) {
		if (string.length != 1 || string2.length != 1) {
			throw new UnsupportedOperationException("Strings must have length 1")
		}
		string.charAt(0) .. string2.charAt(0)
	}

	def abstract char getQuote();

	def abstract char parseHexLiteral(Reader reader);

	def abstract CharSequence toHexLiteral(char c);

}
