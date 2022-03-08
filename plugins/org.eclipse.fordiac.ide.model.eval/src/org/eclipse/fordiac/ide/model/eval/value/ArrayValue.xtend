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

import java.util.List
import org.eclipse.fordiac.ide.model.data.ArrayType
import org.eclipse.fordiac.ide.model.eval.variable.ArrayVariable
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.xtend.lib.annotations.Data

@Data
class ArrayValue implements AnyDerivedValue, Iterable<Value> {
	ArrayType type
	List<Variable> elements
	int start
	int end

	new(ArrayType type, List<Variable> elements) {
		this.type = type
		this.elements = elements
		start = type.subranges.head.lowerLimit
		end = type.subranges.head.upperLimit
	}

	def Variable get(int index) {
		elements.get(index - start)
	}

	def Variable get(Iterable<Integer> indices) {
		if (indices.size > 1) {
			(indices.head.get as ArrayVariable).value.get(indices.tail)
		} else {
			indices.head.get
		}
	}

	def Variable getRaw(int index) {
		elements.get(index)
	}

	override equals(Object object) {
		if (object instanceof ArrayValue) {
			elements.map[value].elementsEqual(object.elements.map[value])
		} else
			false
	}

	override hashCode() {
		elements.hashCode
	}

	override toString() { elements.toString }

	override iterator() {
		elements.map[value].iterator
	}
}
