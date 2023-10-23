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
import org.eclipse.fordiac.ide.model.data.LwordType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.value.NumericValueConverter

class LWordValue implements AnyBitValue {
	final long value;

	public static final LWordValue DEFAULT = new LWordValue(0)

	private new(long value) {
		this.value = value;
	}

	def static toLWordValue(long value) { new LWordValue(value) }

	def static toLWordValue(Number value) { new LWordValue(value.longValue) }

	def static toLWordValue(String value) { (NumericValueConverter.INSTANCE_LWORD.toValue(value) as Number).toLWordValue }

	def static toLWordValue(AnyBitValue value) { value.longValue.toLWordValue }

	override LwordType getType() { ElementaryTypes.LWORD }

	override boolValue() { value != 0 }

	override byteValue() { value as byte }

	override shortValue() { value as short }

	override intValue() { value as int }

	override longValue() { value }

	override BigInteger bigIntegerValue() {	new BigInteger(Long.toUnsignedString(value)) }
	
	override equals(Object obj) { if(obj instanceof LWordValue) value == obj.value else false }

	override hashCode() { Long.hashCode(value) }

	override toString() { NumericValueConverter.INSTANCE_LWORD.toString(longValue) }
}
