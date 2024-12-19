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
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

public interface ValueConverter<T> {
	Pattern ANY_PATTERN = Pattern.compile(".*", Pattern.DOTALL); //$NON-NLS-1$

	default String toString(final T value) {
		return Objects.toString(value).toUpperCase();
	}

	T toValue(String string) throws IllegalArgumentException;

	T toValue(Scanner scanner) throws IllegalArgumentException;

	default T toValue(final Scanner scanner, final Pattern pattern) throws IllegalArgumentException {
		final String string = scanner.findWithinHorizon(pattern, 0);
		if (string == null) {
			throw new IllegalArgumentException(
					MessageFormat.format("Invalid value: {0}", scanner.findWithinHorizon(ANY_PATTERN, 0))); //$NON-NLS-1$
		}
		return toValue(string);
	}
}
