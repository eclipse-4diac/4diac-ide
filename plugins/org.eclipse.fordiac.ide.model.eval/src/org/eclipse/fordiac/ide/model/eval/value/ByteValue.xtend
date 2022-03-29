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

import org.eclipse.fordiac.ide.model.data.ByteType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.value.NumericValueConverter

class ByteValue implements AnyBitValue {
	final byte value;

	public static final ByteValue DEFAULT = new ByteValue(0 as byte)

	private new(byte value) {
		this.value = value;
	}

	def static toByteValue(byte value) { new ByteValue(value) }

	def static toByteValue(Number value) { new ByteValue(value.byteValue) }

	def static toByteValue(String value) { (NumericValueConverter.INSTANCE.toValue(value) as Number).toByteValue }

	def static toByteValue(AnyBitValue value) { value.byteValue.toByteValue }

	override ByteType getType() { ElementaryTypes.BYTE }

	override boolValue() { value != 0 }

	override byteValue() { value }

	override shortValue() { Byte.toUnsignedInt(value) as short }

	override intValue() { Byte.toUnsignedInt(value) }

	override longValue() { Byte.toUnsignedLong(value) }

	override equals(Object obj) { if(obj instanceof ByteValue) value == obj.value else false }

	override hashCode() { Byte.hashCode(value) }

	override toString() { Integer.toUnsignedString(intValue) }
}
