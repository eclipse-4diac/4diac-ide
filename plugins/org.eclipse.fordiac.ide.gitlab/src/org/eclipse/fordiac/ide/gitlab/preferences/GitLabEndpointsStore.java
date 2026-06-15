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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.SecurePreferencesFactory;
import org.eclipse.equinox.security.storage.StorageException;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

/**
 * Loads and saves configured GitLab endpoints.
 *
 * Plain preferences store one node per endpoint under:
 *
 * <pre>
 * /org.eclipse.fordiac.ide.gitlab/endpoints/&lt;endpoint-name&gt;/url
 * </pre>
 *
 * Tokens are stored encrypted in Eclipse Secure Storage (Equinox Secure
 * Preferences) using the same endpoint node names.
 */
public final class GitLabEndpointsStore {

	private static final String ENDPOINTS_NODE = "endpoints"; //$NON-NLS-1$
	private static final String URL_KEY = "url"; //$NON-NLS-1$
	private static final String TOKEN_KEY = "token"; //$NON-NLS-1$
	private static final String DEFAULT_ENDPOINT_NAME = "Default"; //$NON-NLS-1$

	private GitLabEndpointsStore() {
	}

	public static List<GitLabEndpoint> loadEndpoints() {
		final IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(PreferenceConstants.P_GITLAB_PREFERENCE_ID);
		final List<GitLabEndpoint> storedEndpoints = loadStoredEndpoints(prefs);
		if (!storedEndpoints.isEmpty()) {
			return storedEndpoints;
		}
		return migrateOldEndpoints(prefs);
	}

	private static List<GitLabEndpoint> loadStoredEndpoints(final IEclipsePreferences prefs) {
		final Preferences endpointsRoot = prefs.node(ENDPOINTS_NODE);
		final List<GitLabEndpoint> result = new ArrayList<>();

		try {
			final String[] children = endpointsRoot.childrenNames();
			Arrays.sort(children, String.CASE_INSENSITIVE_ORDER);
			for (final String endpointName : children) {
				if (!GitLabEndpoint.isValidName(endpointName)) {
					continue;
				}
				final Preferences endpointNode = endpointsRoot.node(endpointName);
				final String url = endpointNode.get(URL_KEY, ""); //$NON-NLS-1$
				final String token = getTokenSecure(endpointName);
				final GitLabEndpoint endpoint = new GitLabEndpoint(endpointName, url, token);
				if (!endpoint.name().isBlank() && !endpoint.url().isBlank()) {
					result.add(endpoint);
				}
			}
		} catch (final BackingStoreException e) {
			FordiacLogHelper.logWarning("Loading GitLab endpoints failed", e); //$NON-NLS-1$
		}

		return result;
	}

	public static void saveEndpoints(final List<GitLabEndpoint> endpoints) {
		final IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(PreferenceConstants.P_GITLAB_PREFERENCE_ID);
		final Preferences endpointsRoot = prefs.node(ENDPOINTS_NODE);
		final ISecurePreferences secureEndpointsRoot = getSecureEndpointsRoot();
		final List<GitLabEndpoint> normalized = normalizeEndpoints(endpoints);
		final List<String> names = normalized.stream().map(GitLabEndpoint::name).toList();

		try {
			removeMissingPlainNodes(endpointsRoot, names);
			removeMissingSecureNodes(secureEndpointsRoot, names);

			for (final GitLabEndpoint endpoint : normalized) {
				final Preferences endpointNode = endpointsRoot.node(endpoint.name());
				endpointNode.put(URL_KEY, endpoint.url());
				putTokenSecure(endpoint.name(), endpoint.token());
			}

			prefs.flush();
			SecurePreferencesFactory.getDefault().flush();
		} catch (final BackingStoreException | IOException e) {
			FordiacLogHelper.logWarning("Saving GitLab endpoints failed", e); //$NON-NLS-1$
		}
	}

	private static List<GitLabEndpoint> normalizeEndpoints(final List<GitLabEndpoint> endpoints) {
		if (endpoints == null || endpoints.isEmpty()) {
			return List.of();
		}
		final List<GitLabEndpoint> normalized = new ArrayList<>();
		for (final GitLabEndpoint endpoint : endpoints) {
			if (endpoint != null && endpoint.isValid()) {
				normalized.add(new GitLabEndpoint(endpoint.name(), endpoint.url(), endpoint.token()));
			}
		}
		return normalized;
	}

	private static void removeMissingPlainNodes(final Preferences endpointsRoot, final Collection<String> names)
			throws BackingStoreException {
		for (final String existingChild : endpointsRoot.childrenNames()) {
			if (!names.contains(existingChild)) {
				endpointsRoot.node(existingChild).removeNode();
			}
		}
	}

	private static void removeMissingSecureNodes(final ISecurePreferences secureEndpointsRoot,
			final Collection<String> names) {
		for (final String existingChild : secureEndpointsRoot.childrenNames()) {
			if (!names.contains(existingChild)) {
				secureEndpointsRoot.node(existingChild).removeNode();
			}
		}
	}

	public static void removeToken(final String endpointName) {
		if (!GitLabEndpoint.isValidName(endpointName)) {
			return;
		}
		try {
			getSecureEndpointsRoot().node(endpointName).removeNode();
			SecurePreferencesFactory.getDefault().flush();
		} catch (final IOException e) {
			FordiacLogHelper.logWarning("Removing secure token failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * LEGACY migration: migrate old single URL/token preferences into a single
	 * endpoint entry named "Default".
	 */
	protected static List<GitLabEndpoint> migrateOldEndpoints(final IEclipsePreferences prefs) {
		final String legacyUrl = prefs.get(PreferenceConstants.P_GITLAB_URL, ""); //$NON-NLS-1$
		final String legacyToken = prefs.get(PreferenceConstants.P_GITLAB_TOKEN, ""); //$NON-NLS-1$
		if (legacyUrl != null && !legacyUrl.isBlank() && legacyToken != null && !legacyToken.isBlank()) {
			final GitLabEndpoint migrated = new GitLabEndpoint(DEFAULT_ENDPOINT_NAME, legacyUrl, legacyToken);
			saveEndpoints(List.of(migrated));
			prefs.remove(PreferenceConstants.P_GITLAB_URL);
			prefs.remove(PreferenceConstants.P_GITLAB_TOKEN);
			try {
				prefs.flush();
			} catch (final BackingStoreException e) {
				FordiacLogHelper.logWarning("Saving migrated GitLab endpoint failed", e); //$NON-NLS-1$
			}
			return List.of(new GitLabEndpoint(DEFAULT_ENDPOINT_NAME, legacyUrl, getTokenSecure(DEFAULT_ENDPOINT_NAME)));
		}
		return Collections.emptyList();
	}

	private static void putTokenSecure(final String endpointName, final String token) {
		if (!GitLabEndpoint.isValidName(endpointName) || Objects.toString(token, "").isBlank()) { //$NON-NLS-1$
			return;
		}
		try {
			getSecureEndpointsRoot().node(endpointName).put(TOKEN_KEY, token, true);
			SecurePreferencesFactory.getDefault().flush();
		} catch (final StorageException | IOException | RuntimeException e) {
			FordiacLogHelper.logWarning("Saving secure token failed", e); //$NON-NLS-1$
		}
	}

	private static String getTokenSecure(final String endpointName) {
		if (!GitLabEndpoint.isValidName(endpointName)) {
			return ""; //$NON-NLS-1$
		}
		try {
			return getSecureEndpointsRoot().node(endpointName).get(TOKEN_KEY, ""); //$NON-NLS-1$
		} catch (final StorageException e) {
			return ""; //$NON-NLS-1$
		}
	}

	private static ISecurePreferences getSecureEndpointsRoot() {
		return SecurePreferencesFactory.getDefault().node(PreferenceConstants.P_GITLAB_PREFERENCE_ID)
				.node(ENDPOINTS_NODE);
	}
}
