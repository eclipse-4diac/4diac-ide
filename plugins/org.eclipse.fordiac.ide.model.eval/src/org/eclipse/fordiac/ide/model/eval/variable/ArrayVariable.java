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
package org.eclipse.fordiac.ide.model.eval.variable;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.ArrayType;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.Subrange;
import org.eclipse.fordiac.ide.model.eval.value.ArrayValue;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;

public class ArrayVariable extends AbstractVariable<ArrayValue> implements Iterable<Variable<?>> {
	private final ArrayValue value;

	public ArrayVariable(final String name, final ArrayType type) {
		super(name, type);
		value = new ArrayValue(type);
	}

	public ArrayVariable(final String name, final ArrayType type, final String value) {
		super(name, type);
		this.value = new ArrayValue((ArrayValue) ValueOperations.parseValue(value, type, null));
	}

	public ArrayVariable(final String name, final ArrayType type, final Value value) {
		super(name, ArrayVariable.withKnownBounds(type, value));
		this.value = new ArrayValue(checkValue(value));
	}

	@Override
	public void setValue(final Value value) {
		final ArrayValue arrayValue = checkValue(value);
		IntStream.rangeClosed(//
				Math.max(this.value.getStart(), arrayValue.getStart()),
				Math.min(this.value.getEnd(), arrayValue.getEnd()))
				.forEach(index -> this.value.get(index).setValue(arrayValue.get(index).getValue()));
	}

	protected ArrayValue checkValue(final Value value) {
		if (!(value instanceof final ArrayValue arrayValue)) {
			throw createCastException(value);
		}
		return arrayValue;
	}

	@Override
	public ArrayType getType() {
		return (ArrayType) super.getType();
	}

	protected static ArrayType withKnownBounds(final ArrayType type, final Value value) {
		if (value instanceof final ArrayValue arrayValue) {
			return ArrayVariable.withKnownBounds(type, arrayValue.getType().getSubranges());
		}
		if (value != null) {
			throw new ClassCastException("Cannot assign value with incompatible type " + value.getType().getName() //$NON-NLS-1$
					+ " as " + type.getName()); //$NON-NLS-1$
		}
		return type;
	}

	protected static ArrayType withKnownBounds(final ArrayType type, final List<Subrange> knownSubranges) {
		if (type.getSubranges().size() != knownSubranges.size()) {
			throw new ClassCastException("Cannot assign value with incompatible subranges " + knownSubranges //$NON-NLS-1$
					+ " as " + type.getName()); //$NON-NLS-1$
		}
		if (type.getSubranges().stream()
				.anyMatch(subrange -> !subrange.isSetLowerLimit() || !subrange.isSetUpperLimit())) {
			return newArrayType(type.getBaseType(),
					IntStream.range(0, type.getSubranges().size())
							.mapToObj(index -> mergeSubrange(type.getSubranges().get(index), knownSubranges.get(index)))
							.map(EcoreUtil::copy).toList());
		}
		return type;
	}

	private static Subrange mergeSubrange(final Subrange typeSubrange, final Subrange valueSubrange) {
		return typeSubrange.isSetLowerLimit() && typeSubrange.isSetUpperLimit() ? typeSubrange : valueSubrange;
	}

	public static ArrayType newArrayType(final DataType arrayBaseType, final Subrange... arraySubranges) {
		return ArrayVariable.newArrayType(arrayBaseType, List.of(arraySubranges));
	}

	public static ArrayType newArrayType(final DataType arrayBaseType, final List<Subrange> arraySubranges) {
		final ArrayType arrayType = DataFactory.eINSTANCE.createArrayType();
		arrayType.setName("ARRAY " + arraySubranges.stream() //$NON-NLS-1$
				.map(subrange -> subrange.isSetLowerLimit() && subrange.isSetUpperLimit()
						? subrange.getLowerLimit() + ".." + subrange.getUpperLimit() //$NON-NLS-1$
						: "*") //$NON-NLS-1$
				.collect(Collectors.joining(", ", "[", "]")) + " OF " + arrayBaseType.getName()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		arrayType.setBaseType(arrayBaseType);
		arrayType.getSubranges().addAll(arraySubranges);
		return arrayType;
	}

	public static Subrange newSubrange(final int lower, final int upper) {
		final Subrange subrange = DataFactory.eINSTANCE.createSubrange();
		subrange.setLowerLimit(lower);
		subrange.setUpperLimit(upper);
		return subrange;
	}

	@Override
	public Iterator<Variable<?>> iterator() {
		return value.getElements().iterator();
	}

	public DataType getElementType() {
		return value.getElementType();
	}

	public List<Variable<?>> getElements() {
		return value.getElements();
	}

	@Override
	public Stream<Variable<?>> getChildren() {
		return getElements().stream();
	}

	@Override
	public ArrayValue getValue() {
		return value;
	}
}
