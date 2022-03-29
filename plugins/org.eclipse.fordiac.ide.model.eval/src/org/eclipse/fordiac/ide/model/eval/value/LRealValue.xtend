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

import org.eclipse.fordiac.ide.model.data.LrealType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.value.NumericValueConverter

class LRealValue implements AnyRealValue {
	final double value;
	
	public static final LRealValue DEFAULT = new LRealValue(0.0)

	private new(double value) {
		this.value = value;
	}

	def static toLRealValue(double value) { new LRealValue(value) }

	def static toLRealValue(Number value) { new LRealValue(value.doubleValue) }

	def static toLRealValue(String value) { (NumericValueConverter.INSTANCE.toValue(value) as Number).toLRealValue }

	def static toLRealValue(AnyMagnitudeValue value) { value.doubleValue.toLRealValue }

	override LrealType getType() { ElementaryTypes.LREAL }
	
	override byteValue() { value as byte }

	override shortValue() { value as short }

	override intValue() { value as int }

	override longValue() { value as long }

	override doubleValue() { value }

	override floatValue() { value as float }

	override equals(Object obj) { if(obj instanceof LRealValue) value == obj.value else false }

	override hashCode() { Double.hashCode(value) }

	override toString() { Double.toString(value) }
}
