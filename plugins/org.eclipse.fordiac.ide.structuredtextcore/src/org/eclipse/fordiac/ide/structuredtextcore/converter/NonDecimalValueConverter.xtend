/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.converter

import java.math.BigDecimal
import org.eclipse.xtext.conversion.ValueConverterException
import org.eclipse.xtext.conversion.impl.AbstractToStringConverter
import org.eclipse.xtext.nodemodel.INode
import java.util.regex.Pattern
import java.util.regex.Matcher
import java.math.BigInteger

class NonDecimalValueConverter extends AbstractToStringConverter<BigDecimal> {
	
	final static String NON_DECIMAL_REGEX = "([0-9]*)#(.*)"
	
	override protected internalToValue(String string, INode node) throws ValueConverterException {
		val Pattern nonDecimalPattern = Pattern.compile(NON_DECIMAL_REGEX)
		val Matcher nonDecimalMatch = nonDecimalPattern.matcher(string)
		nonDecimalMatch.matches();
		val radix = Integer.parseInt(nonDecimalMatch.group(1))
		val number = nonDecimalMatch.group(2)
		val convert = new BigInteger(number, radix)
		return new BigDecimal(convert)
	}
	
}