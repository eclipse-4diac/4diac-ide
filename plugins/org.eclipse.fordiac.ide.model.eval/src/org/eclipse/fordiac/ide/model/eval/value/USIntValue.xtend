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

import org.eclipse.fordiac.ide.model.data.UsintType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes

class USIntValue implements AnyUnsignedValue, Comparable<USIntValue> {
	final byte value;

	public static final USIntValue DEFAULT = new USIntValue(0 as byte)

	private new(byte value) {
		this.value = value;
	}

	def static toUSIntValue(byte value) { new USIntValue(value) }

	def static toUSIntValue(Number value) { new USIntValue(value.byteValue) }

	def static toUSIntValue(String value) { new USIntValue(Integer.parseUnsignedInt(value) as byte) }

	def static toUSIntValue(AnyNumValue value) { value.byteValue.toUSIntValue }

	override UsintType getType() { ElementaryTypes.USINT }
	
	override byteValue() { value }

	override shortValue() { Byte.toUnsignedInt(value) as short }

	override intValue() { Byte.toUnsignedInt(value) }

	override longValue() { Byte.toUnsignedLong(value) }

	override doubleValue() { intValue }

	override floatValue() { intValue }

	override compareTo(USIntValue o) { Byte.compareUnsigned(value, o.value) }

	override equals(Object obj) { if(obj instanceof USIntValue) value == obj.value else false }

	override hashCode() { value }

	override toString() { Integer.toUnsignedString(intValue) }
}
