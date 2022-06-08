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
package org.eclipse.fordiac.ide.model.eval.variable

import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.eval.value.CharValue
import org.eclipse.fordiac.ide.model.eval.value.StringValue
import org.eclipse.fordiac.ide.model.eval.value.Value

import static extension org.eclipse.fordiac.ide.model.eval.value.ValueOperations.*

class StringCharacterVariable extends AbstractVariable<CharValue> {
	final Variable<StringValue> delegate
	final int index

	new(Variable<StringValue> delegate, int index) {
		super('''«delegate.name»[«index»]''', ElementaryTypes.CHAR)
		this.delegate = delegate
		this.index = index
	}

	override getValue() {
		delegate.value.charAt(index)
	}

	override setValue(Value value) {
		if (value instanceof CharValue)
			delegate.value = StringValue.toStringValue(
				delegate.value.stringValue.substring(0, index - 1) + value.charValue +
					delegate.value.stringValue.substring(index))
		else
			throw new ClassCastException('''Cannot assign value with incompatible type «value.type.name» as «type.name»''')
	}

	override setValue(String value) {
		setValue(value.parseValue(type))
	}

	override validateValue(String value) {
		try {
			value.parseValue(type)
			true
		} catch (Exception e) {
			false
		}
	}
}
