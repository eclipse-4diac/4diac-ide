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

import java.util.Map
import org.eclipse.fordiac.ide.model.data.StructuredType
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.xtend.lib.annotations.Data

@Data
class StructValue implements AnyDerivedValue, Iterable<Value> {
	StructuredType type
	Map<String, Variable> members

	def Variable get(String key) {
		members.get(key)
	}

	override equals(Object object) {
		if (object instanceof StructValue) {
			members.size == object.members.size && members.entrySet.forall[value.value == object.members.get(key).value]
		} else
			false
	}

	override hashCode() {
		members.hashCode
	}

	override toString() { members.toString }

	override iterator() {
		members.values.sortBy[name].map[value].iterator
	}
}
