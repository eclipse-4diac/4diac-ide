/*******************************************************************************
 * Copyright (c) 2022 - 2023 Martin Erich Jobst
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

import java.util.Map
import org.eclipse.fordiac.ide.model.data.StructuredType
import org.eclipse.fordiac.ide.model.eval.value.StructValue
import org.eclipse.fordiac.ide.model.eval.value.Value
import org.eclipse.xtend.lib.annotations.Accessors

import static extension org.eclipse.fordiac.ide.model.eval.variable.VariableOperations.*

class StructVariable extends AbstractVariable<StructValue> implements Iterable<Variable<?>> {
	@Accessors final Map<String, Variable<?>> members
	@Accessors final StructValue value

	new(String name, StructuredType type) {
		super(name, type)
		members = type.memberVariables.map[newVariable].toMap[getName].immutableCopy
		value = new StructValue(type, members)
	}

	new(String name, StructuredType type, String value) {
		this(name, type)
		if(!value.nullOrEmpty) this.value = value
	}

	new(String name, StructuredType type, Value value) {
		this(name, type)
		if(value !== null) this.value = value
	}

	override setValue(Value value) {
		if (value instanceof StructValue) {
			// type object references are not unique, whereas typeEntries seem to be
			if (value.type.typeEntry != type.typeEntry) {
				throw new IllegalArgumentException('''Cannot assign structured value with different type «value.type.name» to structured value of type «type.name»''')
			}
			value.members.forEach[name, variable|members.get(name).value = variable.value]
		} else
			throw new ClassCastException('''Cannot assign value with incompatible type «value.type.name» as «type.name»''')
	}

	override setValue(String value) {
		value = VariableOperations.evaluateValue(type, value)
	}

	override validateValue(String value) {
		VariableOperations.validateValue(type, value).nullOrEmpty
	}

	override StructuredType getType() {
		super.type as StructuredType
	}

	override iterator() {
		members.values.iterator
	}
}
