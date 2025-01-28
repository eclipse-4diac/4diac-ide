/**
 * Copyright (c) 2025 Martin Erich Jobst
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
import java.util.Scanner;
import java.util.regex.Pattern;

import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.data.EnumeratedType;
import org.eclipse.fordiac.ide.model.data.EnumeratedValue;

public final class EnumValueConverter implements ValueConverter<EnumeratedValue> {
	private static final Pattern SCANNER_PATTERN = Pattern.compile("\\G\\w++"); //$NON-NLS-1$

	private final EnumeratedType type;

	public EnumValueConverter(final EnumeratedType type) {
		this.type = type;
	}

	@Override
	public EnumeratedValue toValue(final String string) throws IllegalArgumentException {
		return type.getEnumeratedValues().stream().filter(v -> v.getName().equals(string)).findFirst()
				.orElseThrow(() -> new IllegalArgumentException(
						MessageFormat.format(Messages.EnumValueConverter_NoSuchValue, string, type.getName())));
	}

	@Override
	public EnumeratedValue toValue(final Scanner scanner) throws IllegalArgumentException {
		return toValue(scanner, SCANNER_PATTERN);
	}

	@Override
	public String toString(final EnumeratedValue value) {
		return value.getName();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
