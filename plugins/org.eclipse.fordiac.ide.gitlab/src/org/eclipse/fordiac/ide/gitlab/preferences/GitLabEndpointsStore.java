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
import java.util.List;
import java.util.Objects;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.SecurePreferencesFactory;
import org.eclipse.equinox.security.storage.StorageException;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;
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
	private static final String LEGACY_ENDPOINT_NAME = "Legacy"; //$NON-NLS-1$

	private GitLabEndpointsStore() {
	}

	public static List<GitLabEndpoint> loadEndpoints() {
		final IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(PreferenceConstants.P_GITLAB_PREFERENCE_ID);
		final List<GitLabEndpoint> storedEndpoints = loadStoredEndpoints(prefs);
		return migrateOldEndpoints(prefs, storedEndpoints);
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
	 * LEGACY migration: migrate the old URL and optional token preferences into an
	 * endpoint entry named "Legacy".
	 */
	protected static List<GitLabEndpoint> migrateOldEndpoints(final IEclipsePreferences prefs) {
		return migrateOldEndpoints(prefs, loadStoredEndpoints(prefs));
	}

	private static List<GitLabEndpoint> migrateOldEndpoints(final IEclipsePreferences prefs,
			final List<GitLabEndpoint> storedEndpoints) {
		final List<GitLabEndpoint> result = new ArrayList<>(storedEndpoints);
		final String legacyUrl = getLegacyUrl(prefs);
		final String legacyToken = prefs.get(PreferenceConstants.P_GITLAB_TOKEN, ""); //$NON-NLS-1$
		if (legacyUrl.isBlank()) {
			return result;
		}

		final String endpointName;
		final GitLabEndpoint matchingEndpoint = findMatchingEndpoint(result, legacyUrl, legacyToken);
		if (matchingEndpoint != null) {
			if (legacyToken.isBlank() || !matchingEndpoint.token().isBlank()) {
				removeLegacyPreferences(prefs);
				return result;
			}
			endpointName = matchingEndpoint.name();
		} else {
			try {
				endpointName = findLegacyEndpointName(result, legacyUrl, legacyToken);
			} catch (final StorageException | RuntimeException e) {
				FordiacLogHelper.logWarning("Finding a name for the migrated GitLab endpoint failed", e); //$NON-NLS-1$
				return result;
			}
		}

		final GitLabEndpoint migrated = new GitLabEndpoint(endpointName, legacyUrl, legacyToken);
		addOrReplaceEndpoint(result, migrated);
		if (!saveMigratedEndpoint(prefs, migrated)) {
			return result;
		}
		removeLegacyPreferences(prefs);
		return result;
	}

	private static String getLegacyUrl(final IEclipsePreferences prefs) {
		final String defaultUrl = DefaultScope.INSTANCE.getNode(PreferenceConstants.P_GITLAB_PREFERENCE_ID)
				.get(PreferenceConstants.P_GITLAB_URL, ""); //$NON-NLS-1$
		return prefs.get(PreferenceConstants.P_GITLAB_URL, defaultUrl);
	}

	private static GitLabEndpoint findMatchingEndpoint(final List<GitLabEndpoint> endpoints, final String legacyUrl,
			final String legacyToken) {
		return endpoints.stream().filter(endpoint -> endpoint.url().equals(legacyUrl))
				.filter(endpoint -> legacyToken.isBlank() || endpoint.token().isBlank()
						|| endpoint.token().equals(legacyToken))
				.findFirst().orElse(null);
	}

	private static void removeLegacyPreferences(final IEclipsePreferences prefs) {
		if (prefs.get(PreferenceConstants.P_GITLAB_URL, null) == null
				&& prefs.get(PreferenceConstants.P_GITLAB_TOKEN, null) == null) {
			return;
		}
		prefs.remove(PreferenceConstants.P_GITLAB_URL);
		prefs.remove(PreferenceConstants.P_GITLAB_TOKEN);
		try {
			prefs.flush();
		} catch (final BackingStoreException e) {
			FordiacLogHelper.logWarning("Saving migrated GitLab endpoint failed", e); //$NON-NLS-1$
		}
	}

	private static String findLegacyEndpointName(final List<GitLabEndpoint> endpoints, final String legacyUrl,
			final String legacyToken) throws StorageException {
		final ISecurePreferences secureEndpointsRoot = getSecureEndpointsRoot();
		final List<String> existingSecureNames = Arrays.asList(secureEndpointsRoot.childrenNames());
		for (int suffix = 0; suffix < Integer.MAX_VALUE; suffix++) {
			final String candidateName = createLegacyEndpointName(suffix);
			final GitLabEndpoint existingEndpoint = findEndpoint(endpoints, candidateName);
			final String existingToken = readExistingSecureToken(secureEndpointsRoot, existingSecureNames, candidateName);
			if (canUseLegacyEndpointName(existingEndpoint, existingToken, legacyUrl, legacyToken)) {
				return candidateName;
			}
		}
		throw new IllegalStateException("No name available for the migrated GitLab endpoint"); //$NON-NLS-1$
	}

	private static String createLegacyEndpointName(final int suffix) {
		return suffix == 0 ? LEGACY_ENDPOINT_NAME : LEGACY_ENDPOINT_NAME + suffix;
	}

	private static String readExistingSecureToken(final ISecurePreferences secureEndpointsRoot,
			final Collection<String> existingSecureNames, final String endpointName) throws StorageException {
		if (!existingSecureNames.contains(endpointName)) {
			return ""; //$NON-NLS-1$
		}
		return secureEndpointsRoot.node(endpointName).get(TOKEN_KEY, ""); //$NON-NLS-1$
	}

	private static boolean canUseLegacyEndpointName(final GitLabEndpoint existingEndpoint,
			final String existingToken, final String legacyUrl, final String legacyToken) {
		final boolean urlIsCompatible = existingEndpoint == null || existingEndpoint.url().equals(legacyUrl);
		final boolean tokenIsCompatible = existingToken.isBlank() || existingToken.equals(legacyToken);
		return urlIsCompatible && tokenIsCompatible;
	}

	private static GitLabEndpoint findEndpoint(final List<GitLabEndpoint> endpoints, final String name) {
		return endpoints.stream().filter(endpoint -> endpoint.name().equals(name)).findFirst().orElse(null);
	}

	private static void addOrReplaceEndpoint(final List<GitLabEndpoint> endpoints, final GitLabEndpoint endpoint) {
		final GitLabEndpoint existing = findEndpoint(endpoints, endpoint.name());
		if (existing != null) {
			endpoints.set(endpoints.indexOf(existing), endpoint);
		} else {
			endpoints.add(endpoint);
		}
	}

	private static boolean saveMigratedEndpoint(final IEclipsePreferences prefs, final GitLabEndpoint endpoint) {
		if (!endpoint.token().isBlank() && !putTokenSecure(endpoint.name(), endpoint.token())) {
			return false;
		}
		try {
			prefs.node(ENDPOINTS_NODE).node(endpoint.name()).put(URL_KEY, endpoint.url());
			prefs.flush();
			return true;
		} catch (final BackingStoreException | RuntimeException e) {
			FordiacLogHelper.logWarning("Saving migrated GitLab endpoint failed", e); //$NON-NLS-1$
			return false;
		}
	}

	private static boolean putTokenSecure(final String endpointName, final String token) {
		if (!GitLabEndpoint.isValidName(endpointName) || Objects.toString(token, "").isBlank()) { //$NON-NLS-1$
			return false;
		}
		try {
			getSecureEndpointsRoot().node(endpointName).put(TOKEN_KEY, token, true);
			SecurePreferencesFactory.getDefault().flush();
			return true;
		} catch (final StorageException | IOException | RuntimeException e) {
			FordiacLogHelper.logWarning("Saving secure token failed", e); //$NON-NLS-1$
			return false;
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
