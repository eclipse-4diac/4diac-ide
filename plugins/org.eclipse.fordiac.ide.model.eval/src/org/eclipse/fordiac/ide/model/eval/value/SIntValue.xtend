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

import org.eclipse.fordiac.ide.model.data.SintType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.value.NumericValueConverter

class SIntValue implements AnySignedValue {
	final byte value;

	public static final SIntValue DEFAULT = new SIntValue(0 as byte)

	private new(byte value) {
		this.value = value;
	}

	def static toSIntValue(byte value) { new SIntValue(value) }

	def static toSIntValue(Number value) { new SIntValue(value.byteValue) }

	def static toSIntValue(String value) { (NumericValueConverter.INSTANCE.toValue(value) as Number).toSIntValue }

	def static toSIntValue(AnyMagnitudeValue value) { value.byteValue.toSIntValue }

	override SintType getType() { ElementaryTypes.SINT }
	
	override byteValue() { value }

	override shortValue() { value }

	override intValue() { value }

	override longValue() { value }

	override doubleValue() { value }

	override floatValue() { value }

	override equals(Object obj) { if(obj instanceof SIntValue) value == obj.value else false }

	override hashCode() { value }

	override toString() { Byte.toString(value) }
}
