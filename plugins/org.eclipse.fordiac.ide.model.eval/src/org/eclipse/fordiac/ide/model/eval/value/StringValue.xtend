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

import java.nio.charset.StandardCharsets
import org.eclipse.fordiac.ide.model.data.StringType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import java.util.Arrays
import org.eclipse.fordiac.ide.model.value.StringValueConverter

class StringValue implements AnyStringValue {
	final byte[] value;

	public static final StringValue DEFAULT = new StringValue(#[])

	private new(byte[] value) {
		this.value = value;
	}

	def static toStringValue(byte[] value) { new StringValue(value) }

	def static toStringValue(String value) { value.getBytes(StandardCharsets.UTF_8).toStringValue }

	def static toStringValue(AnyCharsValue value) { value.stringValue.toStringValue }

	override charValue() { if(value.length > 0) value.get(0) as char else '\u0000' }

	override stringValue() { new String(value, StandardCharsets.UTF_8) }

	override StringType getType() { ElementaryTypes.STRING }

	override equals(Object obj) { if(obj instanceof StringValue) Arrays.equals(value, obj.value) else false }

	override hashCode() { toString.hashCode }

	override toString() { StringValueConverter.INSTANCE.toString(stringValue, false) }
}
