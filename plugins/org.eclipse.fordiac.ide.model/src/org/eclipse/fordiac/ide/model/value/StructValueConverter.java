/**
 * Copyright (c) 2024 Martin Erich Jobst
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
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.model.Messages;

public class StructValueConverter implements ValueConverter<Map<String, Object>> {

	static final Pattern STRUCT_BEGIN_PATTERN = Pattern.compile("\\G\\(\\s*+"); //$NON-NLS-1$
	static final Pattern STRUCT_END_PATTERN = Pattern.compile("\\G\\s*+\\)"); //$NON-NLS-1$
	static final Pattern STRUCT_SEPARATOR_PATTERN = Pattern.compile("\\G\\s*+,\\s*+"); //$NON-NLS-1$
	static final Pattern STRUCT_ASSIGNMENT_PATTERN = Pattern.compile("\\G(\\w++)\\s*+:=\\s*+"); //$NON-NLS-1$

	private final Function<String, ValueConverter<?>> memberValueConverter;

	public StructValueConverter(final Function<String, ValueConverter<?>> memberValueConverter) {
		this.memberValueConverter = memberValueConverter;
	}

	@Override
	public Map<String, Object> toValue(final String string) throws IllegalArgumentException {
		return toValue(new Scanner(string));
	}

	@Override
	public Map<String, Object> toValue(final Scanner scanner) throws IllegalArgumentException {
		final Map<String, Object> result = new HashMap<>();
		if (scanner.findWithinHorizon(STRUCT_BEGIN_PATTERN, 0) == null) {
			throw new IllegalArgumentException(Messages.StructValueConverter_InvalidStructLiteral);
		}
		do {
			if (scanner.findWithinHorizon(STRUCT_ASSIGNMENT_PATTERN, 0) == null) {
				throw new IllegalArgumentException(Messages.StructValueConverter_InvalidStructLiteral);
			}
			final String name = scanner.match().group(1);
			final ValueConverter<?> converter = memberValueConverter.apply(name);
			if (converter == null) {
				throw new IllegalArgumentException(
						MessageFormat.format(Messages.StructValueConverter_NoValueConverter, name));
			}
			try {
				result.put(name, converter.toValue(scanner));
			} catch (final IllegalArgumentException e) {
				throw new IllegalArgumentException(
						MessageFormat.format(Messages.StructValueConverter_IllegalMemberValue, name), e);
			}
		} while (scanner.findWithinHorizon(STRUCT_SEPARATOR_PATTERN, 0) != null);
		if (scanner.findWithinHorizon(STRUCT_END_PATTERN, 0) == null) {
			throw new IllegalArgumentException(Messages.StructValueConverter_InvalidStructLiteral);
		}
		return result;
	}

	@Override
	public String toString(final Map<String, Object> value) {
		return value.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey))
				.<String>map(entry -> toString(entry.getKey(), entry.getValue()))
				.collect(Collectors.joining(", ", "(", ")")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	@SuppressWarnings("unchecked")
	private String toString(final String name, final Object value) {
		return name + " := " + ((ValueConverter<Object>) memberValueConverter.apply(name)).toString(value); //$NON-NLS-1$
	}

	@Override
	public String toString() {
		return String.format("%s [%s]", getClass().getSimpleName(), memberValueConverter); //$NON-NLS-1$
	}
}
