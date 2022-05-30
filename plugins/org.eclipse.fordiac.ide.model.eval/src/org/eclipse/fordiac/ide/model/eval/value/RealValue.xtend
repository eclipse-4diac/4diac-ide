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

import java.math.BigDecimal
import java.math.BigInteger
import org.eclipse.fordiac.ide.model.data.RealType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.value.NumericValueConverter

class RealValue implements AnyRealValue {
	final float value;

	public static final RealValue DEFAULT = new RealValue(0.0f)

	private new(float value) {
		this.value = value;
	}

	def static toRealValue(float value) { new RealValue(value) }

	def static toRealValue(Number value) { new RealValue(value.floatValue) }

	def static toRealValue(String value) { (NumericValueConverter.INSTANCE.toValue(value) as Number).toRealValue }

	def static toRealValue(AnyMagnitudeValue value) { value.floatValue.toRealValue }

	override RealType getType() { ElementaryTypes.REAL }

	override byteValue() { value as byte }

	override shortValue() { value as short }

	override intValue() { value as int }

	override longValue() { value as long }

	override doubleValue() { value }

	override floatValue() { value }

	override BigInteger bigIntegerValue() {	bigDecimalValue.toBigInteger }
	
	override BigDecimal bigDecimalValue() { BigDecimal.valueOf(value) }

	override equals(Object obj) { if(obj instanceof RealValue) value == obj.value else false }

	override hashCode() { Float.hashCode(value) }

	override toString() { Float.toString(value) }
}
