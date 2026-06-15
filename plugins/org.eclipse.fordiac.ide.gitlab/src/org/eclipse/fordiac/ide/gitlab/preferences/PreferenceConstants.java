/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *   Michael Oberlehner - added gitlab endpoint token
 *******************************************************************************/
package org.eclipse.fordiac.ide.gitlab.preferences;

public final class PreferenceConstants {

	private PreferenceConstants() {
		// Shall not be instantiated
	}

	public static final String GITLAB_ENDPOINTS_PREF_PAGE_ID = "org.eclipse.fordiac.ide.gitlab.preferences.gitlabEndpoints"; //$NON-NLS-1$

	/**
	 * Encoded list of endpoints (name|url|token), one per line. Tokens are stored
	 * here for backwards compatibility with the existing preference system.
	 *
	 * New code should use {@link GitLabEndpointsStore}.
	 */
	public static final String P_GITLAB_ENDPOINTS = "gitLabEndpoints"; //$NON-NLS-1$

	public static final String P_GITLAB_PREFERENCE_ID = "org.eclipse.fordiac.ide.gitlab"; //$NON-NLS-1$

	public static String getURL() {
		final var endpoints = GitLabEndpointsStore.loadEndpoints();
		if (!endpoints.isEmpty()) {
			return endpoints.get(0).url();
		}
		return ""; //$NON-NLS-1$
	}

	public static String getToken() {
		final var endpoints = GitLabEndpointsStore.loadEndpoints();
		if (!endpoints.isEmpty()) {
			return endpoints.get(0).token();
		}
		return ""; //$NON-NLS-1$
	}
}
