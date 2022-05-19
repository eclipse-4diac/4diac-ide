/*******************************************************************************
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.eval.value

import org.eclipse.fordiac.ide.model.data.WordType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.value.NumericValueConverter

class WordValue implements AnyBitValue {
	final short value;

	public static final WordValue DEFAULT = new WordValue(0 as short)

	private new(short value) {
		this.value = value;
	}

	def static toWordValue(short value) { new WordValue(value) }

	def static toWordValue(Number value) { new WordValue(value.shortValue) }

	def static toWordValue(String value) { (NumericValueConverter.INSTANCE_WORD.toValue(value) as Number).toWordValue }

	def static toWordValue(AnyBitValue value) { value.shortValue.toWordValue }

	override WordType getType() { ElementaryTypes.WORD }

	override boolValue() { value != 0 }

	override byteValue() { value as byte }

	override shortValue() { Short.toUnsignedInt(value) as short }

	override intValue() { Short.toUnsignedInt(value) }

	override longValue() { Short.toUnsignedLong(value) }

	override equals(Object obj) { if(obj instanceof WordValue) value == obj.value else false }

	override hashCode() { Short.hashCode(value) }

	override toString() { NumericValueConverter.INSTANCE_WORD.toString(intValue) }
}
