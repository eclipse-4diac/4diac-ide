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

import org.eclipse.fordiac.ide.model.data.UintType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes

class UIntValue implements AnyUnsignedValue {
	final short value;

	public static final UIntValue DEFAULT = new UIntValue(0 as short)

	private new(short value) {
		this.value = value;
	}

	def static toUIntValue(short value) { new UIntValue(value) }

	def static toUIntValue(Number value) { new UIntValue(value.shortValue) }

	def static toUIntValue(String value) { new UIntValue(Integer.parseUnsignedInt(value) as short) }

	def static toUIntValue(AnyMagnitudeValue value) { value.shortValue.toUIntValue }

	override UintType getType() { ElementaryTypes.UINT }
	
	override byteValue() { value as byte }

	override shortValue() { value }

	override intValue() { Short.toUnsignedInt(value) }

	override longValue() { Short.toUnsignedLong(value) }

	override doubleValue() { intValue }

	override floatValue() { intValue }

	override equals(Object obj) { if(obj instanceof UIntValue) value == obj.value else false }

	override hashCode() { value }

	override toString() { Integer.toUnsignedString(intValue) }
}
