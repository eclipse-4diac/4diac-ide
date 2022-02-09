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

import org.eclipse.fordiac.ide.model.data.LintType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes

class LIntValue implements AnySignedValue {
	final long value;

	public static final LIntValue DEFAULT = new LIntValue(0)

	private new(long value) {
		this.value = value;
	}

	def static toLIntValue(long value) { new LIntValue(value) }

	def static toLIntValue(Number value) { new LIntValue(value.longValue) }

	def static toLIntValue(String value) { new LIntValue(Long.parseLong(value)) }

	def static toLIntValue(AnyNumValue value) { value.longValue.toLIntValue }

	override LintType getType() { ElementaryTypes.LINT }
	
	override byteValue() { value as byte }

	override shortValue() { value as short }

	override intValue() { value as int }

	override longValue() { value }

	override doubleValue() { value }

	override floatValue() { value }

	override equals(Object obj) { if(obj instanceof LIntValue) value == obj.value else false }

	override hashCode() { Long.hashCode(value) }

	override toString() { Long.toString(value) }
}
