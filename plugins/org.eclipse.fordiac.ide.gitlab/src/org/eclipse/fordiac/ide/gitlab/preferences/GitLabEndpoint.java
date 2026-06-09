/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gitlab.preferences;

import java.util.Objects;
import java.util.regex.Pattern;

public record GitLabEndpoint(String name, String url, String token) {

	private static final Pattern VALID_NAME_PATTERN = Pattern.compile("[\\p{L}\\p{N}._ -]+"); //$NON-NLS-1$

	public GitLabEndpoint {
		name = Objects.toString(name, "").trim(); //$NON-NLS-1$
		url = Objects.toString(url, "").trim(); //$NON-NLS-1$
		token = Objects.toString(token, "").trim(); //$NON-NLS-1$
	}

	public static boolean isValidName(final String value) {
		final String trimmed = Objects.toString(value, "").trim(); //$NON-NLS-1$
		return !trimmed.isBlank() && VALID_NAME_PATTERN.matcher(trimmed).matches();
	}

	public boolean isValid() {
		return isValidName(name) && !url.isBlank() && !token.isBlank();
	}
}
