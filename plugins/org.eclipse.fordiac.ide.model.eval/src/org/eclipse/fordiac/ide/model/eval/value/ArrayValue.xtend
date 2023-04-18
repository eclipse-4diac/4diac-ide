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
package org.eclipse.fordiac.ide.model.eval.value

import java.util.List
import org.eclipse.fordiac.ide.model.data.ArrayType
import org.eclipse.fordiac.ide.model.eval.variable.ArrayVariable
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.xtend.lib.annotations.Data
import java.util.StringJoiner

@Data
class ArrayValue implements AnyDerivedValue, Iterable<Value> {
	static final int COLLAPSE_ELEMENTS_SIZE = 100

	ArrayType type
	List<Variable<?>> elements
	int start
	int end

	new(ArrayType type, List<Variable<?>> elements) {
		if (!type.subranges.forall[setLowerLimit && setUpperLimit]) {
			throw new IllegalArgumentException("Cannot instantiate array value with unknown bounds")
		}
		this.type = type
		this.elements = elements
		start = type.subranges.head.lowerLimit
		end = type.subranges.head.upperLimit
	}

	def Variable<?> get(int index) {
		elements.get(index - start)
	}

	def List<Variable<?>> subList(int fromIndex, int toIndex) {
		elements.subList(fromIndex - start, toIndex - start);
	}

	def Variable<?> get(Iterable<Integer> indices) {
		if (indices.size > 1) {
			(indices.head.get as ArrayVariable).value.get(indices.tail)
		} else {
			indices.head.get
		}
	}

	def Variable<?> getRaw(int index) {
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

	override toString() {
		if (elements.size > collapseElementsSize)
			toCollapsedString
		else
			elements.toString
	}

	def String toCollapsedString() {
		val result = new StringJoiner(", ", "[", "]")
		var int count = 0
		var Value lastValue = null
		for (element : elements) {
			if (lastValue !== null && lastValue != element.value) {
				result.add(lastValue.toElementString(count))
				count = 0
			}
			lastValue = element.value
			count++
		}
		if (lastValue !== null) {
			result.add(lastValue.toElementString(count))
		}
		result.toString
	}

	def protected toElementString(Value value, int count) {
		if (count > 1) '''«count»(«value.toString»)''' else value.toString
	}

	def getCollapseElementsSize() { COLLAPSE_ELEMENTS_SIZE }

	override iterator() {
		elements.map[value].iterator
	}
}
