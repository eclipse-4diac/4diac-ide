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

import org.eclipse.fordiac.ide.model.data.UdintType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes

class UDIntValue implements AnyUnsignedValue, Comparable<UDIntValue> {
	final int value;

	public static final UDIntValue DEFAULT = new UDIntValue(0)

	private new(int value) {
		this.value = value;
	}

	def static toUDIntValue(int value) { new UDIntValue(value) }

	def static toUDIntValue(Number value) { new UDIntValue(value.intValue) }

	def static toUDIntValue(String value) { new UDIntValue(Integer.parseUnsignedInt(value)) }

	def static toUDIntValue(AnyNumValue value) { value.intValue.toUDIntValue }

	override UdintType getType() { ElementaryTypes.UDINT }

	override byteValue() { value as byte }

	override shortValue() { value as short }

	override intValue() { value }

	override longValue() { Integer.toUnsignedLong(value) }

	override doubleValue() { longValue }

	override floatValue() { longValue }

	override compareTo(UDIntValue o) { Integer.compareUnsigned(value, o.value) }

	override equals(Object obj) { if(obj instanceof UDIntValue) value == obj.value else false }

	override hashCode() { value }

	override toString() { Integer.toUnsignedString(value) }
}
