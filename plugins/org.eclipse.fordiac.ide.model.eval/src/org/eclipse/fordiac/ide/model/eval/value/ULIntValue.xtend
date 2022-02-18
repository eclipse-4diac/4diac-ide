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

import org.eclipse.fordiac.ide.model.data.UlintType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes

class ULIntValue implements AnyUnsignedValue {
	final long value;

	public static final ULIntValue DEFAULT = new ULIntValue(0)

	private new(long value) {
		this.value = value;
	}

	def static toULIntValue(long value) { new ULIntValue(value) }

	def static toULIntValue(Number value) { new ULIntValue(value.longValue) }

	def static toULIntValue(String value) { new ULIntValue(Long.parseUnsignedLong(value)) }

	def static toULIntValue(AnyNumValue value) { value.longValue.toULIntValue }

	override UlintType getType() { ElementaryTypes.ULINT }
	
	override byteValue() { value as byte }

	override shortValue() { value as short }

	override intValue() { value as int }

	override longValue() { value }

	override doubleValue() { value }

	override floatValue() { value }

	override equals(Object obj) { if(obj instanceof ULIntValue) value == obj.value else false }

	override hashCode() { Long.hashCode(value) }

	override toString() { Long.toUnsignedString(value) }
}
