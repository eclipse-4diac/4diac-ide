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

import org.eclipse.fordiac.ide.model.data.IntType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes

class IntValue implements AnySignedValue {
	final short value;

	public static final IntValue DEFAULT = new IntValue(0 as short)

	private new(short value) {
		this.value = value;
	}

	def static toIntValue(short value) { new IntValue(value) }

	def static toIntValue(Number value) { new IntValue(value.shortValue) }

	def static toIntValue(String value) { new IntValue(Short.parseShort(value)) }

	def static toIntValue(AnyMagnitudeValue value) { value.shortValue.toIntValue }

	override IntType getType() { ElementaryTypes.INT }
	
	override byteValue() { value as byte }

	override shortValue() { value }

	override intValue() { value }

	override longValue() { value }

	override doubleValue() { value }

	override floatValue() { value }

	override equals(Object obj) { if(obj instanceof IntValue) value == obj.value else false }

	override hashCode() { value }

	override toString() { Short.toString(value) }
}
