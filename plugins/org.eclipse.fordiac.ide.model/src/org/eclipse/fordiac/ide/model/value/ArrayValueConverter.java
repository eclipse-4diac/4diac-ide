/**
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
 */
package org.eclipse.fordiac.ide.model.value;

import java.text.MessageFormat;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.Messages;

public class ArrayValueConverter<T> implements ValueConverter<List<T>> {
	static final Pattern ARRAY_PATTERN = Pattern.compile(",(?=(?:[^\"']*(?:(?:\"[^\"]*\")|(?:\'[^\']*\')))*[^\"']*$)"); //$NON-NLS-1$

	private final ValueConverter<T> elementValueConverter;

	public ArrayValueConverter(final ValueConverter<T> elementValueConverter) {
		this.elementValueConverter = elementValueConverter;
	}

	@Override
	public String toString(final List<T> value) {
		return "[" + value.stream().map(elementValueConverter::toString).collect(Collectors.joining(", ")) + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	@Override
	public List<T> toValue(final String string) throws IllegalArgumentException {
		final var trimmed = string.trim();
		if (!trimmed.startsWith("[") || !trimmed.endsWith("]")) { //$NON-NLS-1$ //$NON-NLS-2$
			throw new IllegalArgumentException(MessageFormat.format(Messages.VALIDATOR_ARRAY_MISSES_BRACKETS, string));
		}
		final var inner = trimmed.substring(1, trimmed.length() - 1);
		return Stream.of(ARRAY_PATTERN.split(inner)).map(String::trim).map(elementValueConverter::toValue).toList();
	}
}
