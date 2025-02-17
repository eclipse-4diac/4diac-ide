/**
 * Copyright (c) 2022, 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.model.eval.value;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.ArrayType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.eval.variable.ArrayVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;

@SuppressWarnings("java:S1452")
public final class ArrayValue implements AnyDerivedValue, Iterable<Value> {
	private static final int COLLAPSE_ELEMENTS_SIZE = 100;

	private final ArrayType type;
	private final DataType elementType;
	private final int start;
	private final int end;
	private final List<Variable<?>> elements;

	public ArrayValue(final ArrayType type) {
		this.type = checkType(type);
		elementType = getElementType(type);
		start = type.getSubranges().get(0).getLowerLimit();
		end = type.getSubranges().get(0).getUpperLimit();
		elements = IntStream.rangeClosed(start, end).<Variable<?>>mapToObj(this::initializeElement).toList();
	}

	public ArrayValue(final ArrayType type, final List<?> values) {
		this.type = checkType(type);
		elementType = getElementType(type);
		start = type.getSubranges().get(0).getLowerLimit();
		end = type.getSubranges().get(0).getUpperLimit();
		elements = IntStream.rangeClosed(start, end).<Variable<?>>mapToObj(index -> initializeElement(index, values))
				.toList();
	}

	public ArrayValue(final ArrayValue value) {
		type = value.getType();
		elementType = value.getElementType();
		start = value.getStart();
		end = value.getEnd();
		elements = value.getElements().stream().<Variable<?>>map(VariableOperations::newVariable).toList();
	}

	protected static ArrayType checkType(final ArrayType type) {
		if (type.getSubranges().isEmpty() || type.getSubranges().stream()
				.anyMatch(subrange -> !subrange.isSetLowerLimit() || !subrange.isSetUpperLimit())) {
			throw new IllegalArgumentException("Cannot instantiate array value with unknown bounds"); //$NON-NLS-1$
		}
		return type;
	}

	protected static DataType getElementType(final ArrayType type) {
		if (type.getSubranges().size() > 1) {
			return ArrayVariable.newArrayType(type.getBaseType(),
					type.getSubranges().stream().skip(1).map(EcoreUtil::copy).toList());
		}
		return type.getBaseType();
	}

	protected Variable<?> initializeElement(final int index) {
		return VariableOperations.newVariable(Integer.toString(index), elementType);
	}

	protected Variable<?> initializeElement(final int index, final Object value) {
		return VariableOperations.newVariable(Integer.toString(index), elementType,
				ValueOperations.wrapValue(value, elementType));
	}

	protected Variable<?> initializeElement(final int index, final List<?> values) {
		return index - start < values.size() ? initializeElement(index, values.get(index - start))
				: initializeElement(index);
	}

	public Variable<?> get(final int index) {
		return elements.get(index - start);
	}

	public List<Variable<?>> subList(final int fromIndex, final int toIndex) {
		return elements.subList(fromIndex - start, toIndex - start);
	}

	public Variable<?> get(final List<Integer> indices) {
		if (indices.size() > 1) {
			return ((ArrayVariable) get(indices.get(0).intValue())).getValue().get(indices.subList(1, indices.size()));
		}
		return get(indices.get(0).intValue());
	}

	public Variable<?> getRaw(final int index) {
		return elements.get(index);
	}

	@Override
	public int hashCode() {
		return elements.stream().map(Variable::getValue).mapToInt(Objects::hashCode).sum();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ArrayValue other = (ArrayValue) obj;
		return start == other.start && end == other.end && IntStream.rangeClosed(start, end)
				.allMatch(index -> Objects.equals(get(index).getValue(), other.get(index).getValue()));
	}

	@Override
	public String toString() {
		return toString(true);
	}

	@Override
	public String toString(final boolean pretty) {
		if (elements.size() > getCollapseElementsSize()) {
			return toCollapsedString(pretty);
		}
		return elements.stream().map(element -> element.toString(pretty))
				.collect(Collectors.joining(pretty ? ", " : ",", "[", "]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}

	public String toCollapsedString() {
		return toCollapsedString(true);
	}

	public String toCollapsedString(final boolean pretty) {
		final StringJoiner result = new StringJoiner(pretty ? ", " : ",", "[", "]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		int count = 0;
		Value lastValue = null;
		for (final Variable<?> element : elements) {
			if (lastValue != null && !Objects.equals(lastValue, element.getValue())) {
				result.add(toElementString(lastValue, count, pretty));
				count = 0;
			}
			lastValue = element.getValue();
			count++;
		}
		if (lastValue != null) {
			result.add(toElementString(lastValue, count, pretty));
		}
		return result.toString();
	}

	protected static CharSequence toElementString(final Value value, final int count, final boolean pretty) {
		if (count > 1) {
			final StringBuilder builder = new StringBuilder();
			builder.append(count);
			builder.append("("); //$NON-NLS-1$
			builder.append(value.toString(pretty));
			builder.append(")"); //$NON-NLS-1$
			return builder;
		}
		return value.toString(pretty);
	}

	@SuppressWarnings("static-method")
	public int getCollapseElementsSize() {
		return ArrayValue.COLLAPSE_ELEMENTS_SIZE;
	}

	@Override
	public Iterator<Value> iterator() {
		return elements.stream().<Value>map(Variable::getValue).iterator();
	}

	@Override
	public ArrayType getType() {
		return type;
	}

	public DataType getElementType() {
		return elementType;
	}

	public List<Variable<?>> getElements() {
		return elements;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public int size() {
		return elements.size();
	}
}
