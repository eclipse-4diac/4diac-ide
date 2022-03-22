/**
 * Copyright (c) 2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.structuredtextcore.converter

import java.math.BigDecimal
import java.math.BigInteger
import java.util.regex.Pattern
import org.eclipse.xtext.conversion.ValueConverterException
import org.eclipse.xtext.conversion.impl.AbstractNullSafeConverter
import org.eclipse.xtext.nodemodel.INode

class NumericValueConverter extends AbstractNullSafeConverter<Object> {
	static final String TRUE = "TRUE"
	static final String FALSE = "FALSE"
	static final Pattern NON_DECIMAL = Pattern.compile("(\\d+)#(\\p{XDigit}[_\\p{XDigit}]*)")

	override protected internalToString(Object value) {
		value.toString.toUpperCase
	}

	override protected internalToValue(String string, INode node) throws ValueConverterException {
		try {
			val matcher = NON_DECIMAL.matcher(string)
			if (TRUE.equalsIgnoreCase(string))
				Boolean.TRUE
			else if (FALSE.equalsIgnoreCase(string))
				Boolean.FALSE
			else if (matcher.matches()) {
				val radixString = matcher.group(1)
				val numberString = matcher.group(2).replace("_", "")
				val radix = Integer.parseInt(radixString)
				return new BigInteger(numberString, radix)
			} else if (string.contains("."))
				new BigDecimal(string.replace("_", ""))
			else
				new BigInteger(string.replace("_", ""))
		} catch (Exception e) {
			throw new ValueConverterException("Invalid numeric literal", node, e)
		}
	}
}
