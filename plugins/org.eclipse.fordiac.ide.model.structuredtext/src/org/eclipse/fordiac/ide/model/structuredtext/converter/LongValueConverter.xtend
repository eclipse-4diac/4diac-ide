/*******************************************************************************
 * Copyright (c) 2015 fortiss GmbH
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

import org.eclipse.xtext.conversion.impl.AbstractLexerBasedConverter
import org.eclipse.xtext.nodemodel.INode
import org.eclipse.xtext.conversion.ValueConverterException

import static extension java.lang.Long.toString
import static extension java.lang.Long.parseUnsignedLong
import static extension java.lang.Long.valueOf

abstract class LongValueConverter extends AbstractLexerBasedConverter<Long> {

	override protected toEscapedString(Long value) {
		value.toString(radix);
	}

	override protected assertValidValue(Long value) {
		super.assertValidValue(value);
		if (value < 0) {
			throw new ValueConverterException(getRuleName() + "-value may not be negative (value: " + value + ").",
				null, null)
		}
	}

	override toValue(String string, INode node) {
		if (string.nullOrEmpty) {
			throw new ValueConverterException("Couldn't convert empty string to a long value.", node, null)
		}
		val prefix = prefix
		if (!string.startsWith(prefix)) {
			throw new ValueConverterException(
				"Couldn't convert input '" + string + "' without radix prefix '" + prefix + "' to a long value.", node,
				null)
		}
		val value = string.substring(prefix.length).replace("_", "")
		if (value.nullOrEmpty) {
			throw new ValueConverterException("Couldn't convert input '" + string + "' to a long value.", node, null)
		}
		try {
			val longValue = value.parseUnsignedLong(radix)
			longValue.valueOf()
		} catch (NumberFormatException e) {
			throw new ValueConverterException("Couldn't convert '" + string + "' to an int value.", node, e);
		}
	}

	def protected getPrefix() {
		Integer.toString(radix) + '#'
	}

	def protected abstract int getRadix();

}
