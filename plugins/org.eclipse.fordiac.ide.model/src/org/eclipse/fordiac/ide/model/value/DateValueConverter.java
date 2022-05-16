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

import java.time.LocalDate;

import org.eclipse.fordiac.ide.model.Messages;

public final class DateValueConverter implements ValueConverter<LocalDate> {
	public static final DateValueConverter INSTANCE = new DateValueConverter();

	private DateValueConverter() {
	}

	@Override
	public LocalDate toValue(final String string) throws IllegalArgumentException {
		try {
			return LocalDate.parse(string);
		} catch (final Exception e) {
			throw new IllegalArgumentException(Messages.VALIDATOR_INVALID_DATE_FORMAT, e);
		}
	}
}
