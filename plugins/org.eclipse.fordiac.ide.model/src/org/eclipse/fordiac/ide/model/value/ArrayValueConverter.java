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
package org.eclipse.fordiac.ide.model.value;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eclipse.fordiac.ide.model.Messages;

public class ArrayValueConverter<T> implements ValueConverter<List<T>> {
	static final Pattern ARRAY_BEGIN_PATTERN = Pattern.compile("\\G\\[\\s*+"); //$NON-NLS-1$
	static final Pattern ARRAY_END_PATTERN = Pattern.compile("\\G\\s*+\\]"); //$NON-NLS-1$
	static final Pattern ARRAY_SEPARATOR_PATTERN = Pattern.compile("\\G\\s*+,\\s*+"); //$NON-NLS-1$
	static final Pattern ARRAY_REPEAT_BEGIN_PATTERN = Pattern.compile("\\G(\\d++)\\s*+\\("); //$NON-NLS-1$
	static final Pattern ARRAY_REPEAT_END_PATTERN = Pattern.compile("\\G\\)"); //$NON-NLS-1$

	private final ValueConverter<T> elementValueConverter;

	public ArrayValueConverter(final ValueConverter<T> elementValueConverter) {
		this.elementValueConverter = elementValueConverter;
	}

	@Override
	public String toString(final List<T> value) {
		return value.stream().map(elementValueConverter::toString).collect(Collectors.joining(", ", "[", "]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	@Override
	public List<T> toValue(final String string) throws IllegalArgumentException {
		return toValue(new Scanner(string));
	}

	@Override
	public List<T> toValue(final Scanner scanner) throws IllegalArgumentException {
		final List<T> result = new ArrayList<>();
		if (scanner.findWithinHorizon(ARRAY_BEGIN_PATTERN, 0) == null) {
			throw new IllegalArgumentException(Messages.VALIDATOR_ARRAY_MISSES_BRACKETS);
		}
		do {
			if (scanner.findWithinHorizon(ARRAY_REPEAT_BEGIN_PATTERN, 0) != null) {
				result.addAll(parseRepeatSyntax(scanner, result.size()));
			} else {
				result.add(parseValue(scanner, result.size()));
			}
		} while (scanner.findWithinHorizon(ARRAY_SEPARATOR_PATTERN, 0) != null);
		if (scanner.findWithinHorizon(ARRAY_END_PATTERN, 0) == null) {
			throw new IllegalArgumentException(Messages.VALIDATOR_ARRAY_MISSES_BRACKETS);
		}
		return result;
	}

	private List<T> parseRepeatSyntax(final Scanner scanner, final int index)
			throws NumberFormatException, IllegalArgumentException {
		final int count = Integer.parseInt(scanner.match().group(1));
		final List<T> result = new ArrayList<>();
		do {
			if (scanner.findWithinHorizon(ARRAY_REPEAT_BEGIN_PATTERN, 0) != null) {
				parseRepeatSyntax(scanner, index + result.size());
			} else {
				result.add(parseValue(scanner, index + result.size()));
			}
		} while (scanner.findWithinHorizon(ARRAY_SEPARATOR_PATTERN, 0) != null);
		if (scanner.findWithinHorizon(ARRAY_REPEAT_END_PATTERN, 0) == null) {
			throw new IllegalArgumentException(Messages.ArrayValueConverter_InvalidRepeatSyntax);
		}
		return IntStream.range(0, count).mapToObj(unused -> result).flatMap(List::stream).toList();
	}

	private T parseValue(final Scanner scanner, final int index) {
		try {
			return elementValueConverter.toValue(scanner);
		} catch (final IllegalArgumentException e) {
			throw new IllegalArgumentException(
					MessageFormat.format(Messages.ArrayValueConverter_IllegalElementValue, Integer.valueOf(index)), e);
		}
	}

	@Override
	public String toString() {
		return String.format("%s [%s]", getClass().getSimpleName(), elementValueConverter); //$NON-NLS-1$
	}
}
