/*******************************************************************************
 * Copyright (c) 2022 - 2023 Martin Erich Jobst
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

import org.eclipse.fordiac.ide.model.data.WstringType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.value.WStringValueConverter

class WStringValue implements AnyStringValue, AnyWCharsValue {
	final String value;

	public static final WStringValue DEFAULT = new WStringValue("")

	private new(String value) {
		this.value = value;
	}

	def static toWStringValue(String value) { value.toWStringValue(MAX_LENGTH) }

	def static toWStringValue(String value, int maxLength) { new WStringValue(value.truncate(maxLength)) }

	def static toWStringValue(AnyCharsValue value) { value.toWStringValue(MAX_LENGTH) }

	def static toWStringValue(AnyCharsValue value, int maxLength) { value.stringValue.toWStringValue(maxLength) }

	override WCharValue charAt(int index) {
		WCharValue.toWCharValue(index > 0 && index <= length ? value.charAt(index - 1) : '\u0000')
	}

	def withCharAt(int index, WCharValue c) {
		if (index <= 0) {
			throw new StringIndexOutOfBoundsException(index)
		}
		val newValue = new StringBuilder(value)
		if (newValue.length < index) {
			newValue.length = index
		}
		newValue.setCharAt(index - 1, c.charValue)
		new WStringValue(newValue.toString)
	}

	override length() { value.length }

	override charValue() { if(value.length > 0) value.charAt(0) else '\u0000' }

	override stringValue() { value }

	override WstringType getType() { ElementaryTypes.WSTRING }

	override equals(Object obj) { if(obj instanceof WStringValue) value == obj.value else false }

	override hashCode() { value.hashCode }

	override toString() { WStringValueConverter.INSTANCE.toString(stringValue, true) }

	def private static truncate(String value, int maxLength) {
		if(value.length > maxLength) value.substring(0, maxLength) else value
	}
}
