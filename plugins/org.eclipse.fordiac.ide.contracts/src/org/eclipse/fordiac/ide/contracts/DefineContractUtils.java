/*******************************************************************************
 * Copyright (c) 2023 Paul Pavlicek
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Pavlicek
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts;

import java.util.Arrays;

public final class DefineContractUtils {
	private DefineContractUtils() {
	}

	public static String[] getTimeIntervalFromString(final String text) {
		String[] s = { "0", "0" }; //$NON-NLS-1$ //$NON-NLS-2$
		if (!text.isBlank() && text.charAt(0) == '[') {
			final String str = text.replaceAll("\\D", " "); //$NON-NLS-1$ //$NON-NLS-2$
			s = str.split(" "); //$NON-NLS-1$
			if (s.length > 2) {
				s[0] = s[1];
				s[1] = s[2];
				s = Arrays.copyOf(s, s.length - 1);
			}
		}
		return s;
	}
}
