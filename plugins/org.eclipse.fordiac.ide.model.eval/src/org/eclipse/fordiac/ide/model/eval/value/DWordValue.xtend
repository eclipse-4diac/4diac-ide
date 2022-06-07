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

import java.math.BigInteger
import org.eclipse.fordiac.ide.model.data.DwordType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.value.NumericValueConverter

class DWordValue implements AnyBitValue {
	final int value;

	public static final DWordValue DEFAULT = new DWordValue(0)

	private new(int value) {
		this.value = value;
	}

	def static toDWordValue(int value) { new DWordValue(value) }

	def static toDWordValue(Number value) { new DWordValue(value.intValue) }

	def static toDWordValue(String value) { (NumericValueConverter.INSTANCE_DWORD.toValue(value) as Number).toDWordValue }

	def static toDWordValue(AnyBitValue value) { value.intValue.toDWordValue }

	override DwordType getType() { ElementaryTypes.DWORD }

	override boolValue() { value != 0 }

	override byteValue() { value as byte }

	override shortValue() { value as short }

	override intValue() { value }

	override longValue() { Integer.toUnsignedLong(value) }

	override BigInteger bigIntegerValue() {	BigInteger.valueOf(longValue) }
	
	override equals(Object obj) { if(obj instanceof DWordValue) value == obj.value else false }

	override hashCode() { Integer.hashCode(value) }

	override toString() { NumericValueConverter.INSTANCE_DWORD.toString(intValue) }
}
