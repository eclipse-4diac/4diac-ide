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
import java.time.LocalDate;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.eclipse.fordiac.ide.model.Messages;

public final class DateValueConverter implements ValueConverter<LocalDate> {
	public static final DateValueConverter INSTANCE = new DateValueConverter();

	private static final Pattern SCANNER_PATTERN = Pattern.compile("\\G\\d[_\\d]++-\\d[_\\d]++-\\d[_\\d]++"); //$NON-NLS-1$

	private DateValueConverter() {
	}

	@Override
	public LocalDate toValue(final String string) throws IllegalArgumentException {
		if (string.indexOf("__") != -1) { //$NON-NLS-1$
			throw new IllegalArgumentException(
					MessageFormat.format(Messages.VALIDATOR_CONSECUTIVE_UNDERSCORES_ERROR_MESSAGE, string));
		}
		try {
			return LocalDate.parse(string.replace("_", "")); //$NON-NLS-1$ //$NON-NLS-2$
		} catch (final Exception e) {
			throw new IllegalArgumentException(MessageFormat.format(Messages.VALIDATOR_INVALID_DATE_FORMAT, string), e);
		}
	}

	@Override
	public LocalDate toValue(final Scanner scanner) throws IllegalArgumentException {
		return toValue(scanner, SCANNER_PATTERN);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
