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

import org.eclipse.fordiac.ide.model.data.DintType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes

final class DIntValue implements AnySignedValue {
	final int value;

	public static final DIntValue DEFAULT = new DIntValue(0)

	private new(int value) {
		this.value = value;
	}

	def static toDIntValue(int value) { new DIntValue(value) }

	def static toDIntValue(Number value) { new DIntValue(value.intValue) }

	def static toDIntValue(String value) { new DIntValue(Integer.parseInt(value)) }

	def static toDIntValue(AnyNumValue value) { value.intValue.toDIntValue }

	override DintType getType() { ElementaryTypes.DINT }

	override byteValue() { value as byte }

	override shortValue() { value as short }

	override intValue() { value }

	override longValue() { value }

	override doubleValue() { value }

	override floatValue() { value }

	override equals(Object obj) { if(obj instanceof DIntValue) value == obj.value else false }

	override hashCode() { value }

	override toString() { Integer.toString(value) }
}
