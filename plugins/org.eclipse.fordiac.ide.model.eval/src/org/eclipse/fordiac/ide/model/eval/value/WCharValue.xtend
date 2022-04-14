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

import org.eclipse.fordiac.ide.model.data.WcharType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.value.StringValueConverter

class WCharValue implements AnyCharValue {
	final char value;

	public static final WCharValue DEFAULT = new WCharValue(0 as char)

	private new(char value) {
		this.value = value;
	}

	def static toWCharValue(char value) { new WCharValue(value) }

	def static toWCharValue(String value) { new WCharValue(if(value.length > 0) value.charAt(0) else '\u0000') }

	def static toWCharValue(AnyCharsValue value) { value.charValue.toWCharValue }

	override length() { 1 }

	override charValue() { value }

	override stringValue() { Character.toString(value) }

	override WcharType getType() { ElementaryTypes.WCHAR }

	override equals(Object obj) { if(obj instanceof WCharValue) value == obj.value else false }

	override hashCode() { Character.hashCode(value) }

	override toString() { StringValueConverter.INSTANCE.toString(stringValue) }
}
