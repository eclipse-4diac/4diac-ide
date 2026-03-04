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

public record GitLabEndpoint(String name, String url, String token) {
	public GitLabEndpoint {
		name = name != null ? name.trim() : ""; //$NON-NLS-1$
		url = url != null ? url.trim() : ""; //$NON-NLS-1$
		token = token != null ? token.trim() : ""; //$NON-NLS-1$
	}

	public boolean isValid() {
		return !name.isBlank() && !url.isBlank() && !token.isBlank();
	}
}
