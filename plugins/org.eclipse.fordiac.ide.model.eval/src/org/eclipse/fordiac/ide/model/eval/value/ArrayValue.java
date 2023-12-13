/**
 * Copyright (c) 2022, 2023 Martin Erich Jobst
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
import java.util.stream.IntStream;

import org.eclipse.fordiac.ide.model.data.ArrayType;
import org.eclipse.fordiac.ide.model.eval.variable.ArrayVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;

public final class ArrayValue implements AnyDerivedValue, Iterable<Value> {
	private static final int COLLAPSE_ELEMENTS_SIZE = 100;

	private final ArrayType type;
	private final List<Variable<?>> elements;
	private final int start;
	private final int end;

	public ArrayValue(final ArrayType type, final List<Variable<?>> elements) {
		if (type.getSubranges().isEmpty() || type.getSubranges().stream()
				.anyMatch(subrange -> !subrange.isSetLowerLimit() || !subrange.isSetUpperLimit())) {
			throw new IllegalArgumentException("Cannot instantiate array value with unknown bounds"); //$NON-NLS-1$
		}
		this.type = type;
		this.elements = elements;
		start = type.getSubranges().get(0).getLowerLimit();
		end = type.getSubranges().get(0).getUpperLimit();
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
		if (elements.size() > getCollapseElementsSize()) {
			return toCollapsedString();
		}
		return elements.toString();
	}

	public String toCollapsedString() {
		final StringJoiner result = new StringJoiner(", ", "[", "]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		int count = 0;
		Value lastValue = null;
		for (final Variable<?> element : elements) {
			if (lastValue != null && !Objects.equals(lastValue, element.getValue())) {
				result.add(toElementString(lastValue, count));
				count = 0;
			}
			lastValue = element.getValue();
			count++;
		}
		if (lastValue != null) {
			result.add(toElementString(lastValue, count));
		}
		return result.toString();
	}

	protected static CharSequence toElementString(final Value value, final int count) {
		if (count > 1) {
			final StringBuilder builder = new StringBuilder();
			builder.append(count);
			builder.append("("); //$NON-NLS-1$
			builder.append(value);
			builder.append(")"); //$NON-NLS-1$
			return builder;
		}
		return value.toString();
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

	public List<Variable<?>> getElements() {
		return elements;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}
}
