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

import org.eclipse.fordiac.ide.model.Messages;

public final class BoolValueConverter implements ValueConverter<Boolean> {
	public static final BoolValueConverter INSTANCE = new BoolValueConverter();

	private static final String TRUE = "TRUE"; //$NON-NLS-1$
	private static final String FALSE = "FALSE"; //$NON-NLS-1$

	private BoolValueConverter() {
	}

	@Override
	public Boolean toValue(final String string) throws IllegalArgumentException {
		if (TRUE.equalsIgnoreCase(string) || "1".equals(string)) { //$NON-NLS-1$
			return Boolean.TRUE;
		} else if (FALSE.equalsIgnoreCase(string) || "0".equals(string)) { //$NON-NLS-1$
			return Boolean.FALSE;
		} else {
			throw new IllegalArgumentException(Messages.VALIDATOR_INVALID_BOOL_LITERAL);
		}
	}
}
