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

import java.nio.charset.StandardCharsets
import org.eclipse.fordiac.ide.model.data.StringType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import java.util.Arrays
import org.eclipse.fordiac.ide.model.value.StringValueConverter

import static extension java.util.Arrays.copyOf

class StringValue implements AnyStringValue, AnySCharsValue {
	final byte[] value;

	public static final StringValue DEFAULT = new StringValue(#[])

	private new(byte[] value) {
		this.value = value;
	}

	def static toStringValue(String value) { new StringValue(value.getBytes(StandardCharsets.UTF_8)) }

	def static toStringValue(String value, int maxLength) {
		new StringValue(value.getBytes(StandardCharsets.UTF_8).truncate(maxLength))
	}

	def static toStringValue(AnyCharsValue value) {
		switch (value) {
			CharValue: new StringValue(#[value.charValue as byte])
			StringValue: new StringValue(value.value)
			default: value.stringValue.toStringValue
		}
	}

	def static toStringValue(AnyCharsValue value, int maxLength) {
		switch (value) {
			CharValue: maxLength > 0 ? new StringValue(#[value.charValue as byte]) : DEFAULT
			StringValue: new StringValue(value.value.truncate(maxLength))
			default: value.stringValue.toStringValue(maxLength)
		}
	}

	override CharValue charAt(int index) {
		CharValue.toCharValue(index > 0 && index <= length ? value.get(index - 1) : (0 as byte))
	}

	def withCharAt(int index, CharValue c) {
		if (index > 0) {
			val newValue = value.copyOf(Math.max(value.length, index))
			newValue.set(index - 1, c.charValue as byte)
			new StringValue(newValue)
		} else
			this
	}

	override length() { value.length }

	override charValue() { if(value.length > 0) value.get(0) as char else '\u0000' }

	override stringValue() { new String(value, StandardCharsets.UTF_8) }

	override StringType getType() { ElementaryTypes.STRING }

	override equals(Object obj) { if(obj instanceof StringValue) Arrays.equals(value, obj.value) else false }

	override hashCode() { toString.hashCode }

	override toString() { StringValueConverter.INSTANCE.toString(stringValue, false) }

	def private static truncate(byte[] value, int maxLength) {
		if(value.length > maxLength) value.copyOf(maxLength) else value
	}
}
